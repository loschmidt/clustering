package loschmidt.clustering.hierarchical.murtagh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import loschmidt.clustering.IndexCluster;
import loschmidt.clustering.distance.DistanceProvider;
import loschmidt.clustering.hierarchical.HierarchicalClusteringAlgorithm;
import loschmidt.clustering.hierarchical.HierarchicalClusteringOutput;
import loschmidt.clustering.hierarchical.Merge;
import loschmidt.clustering.hierarchical.Tree;
import loschmidt.clustering.util.DistanceMatrix;

/**
 * Performs agglomerative hierarchical clustering by average, complete or single
 * linkage with O(n^2) time and space complexity.
 * 
 * Based on the C code by Fionn Murtagh downloaded from
 * http://www.classification-society.org/csna/mda-sw/ and the paper Murtagh F
 * (1984). "Complexities of Hierarchic Clustering Algorithms: the state of the
 * art". Computational Statistics Quarterly. 1: 101–113.
 * 
 * Adds the solution to the case when two distances are equal, their order is
 * then decided arbitrarily but consistently using the indices of clusters.
 * 
 * Furthermore, in case the matrix is too big for available RAM, the computation
 * runs at the beginning with linear memory at the cost of increased time
 * complexity (probably O(n^4)).This is more practical than it looks, allowing
 * to cluster over 100.000 of instances within an hour even though the RAM
 * allows to cluster only 60.000.
 * 
 * Complete and single linkage are also supported. Please note that an algorithm
 * with O(n) space complexity exists for single linkage with space complexity
 * O(n).
 * 
 */
public class Murtagh implements HierarchicalClusteringAlgorithm<MurtaghParams> {

	private final DistanceProvider matrix_;
	private final MurtaghParams params_;
	private DistanceMatrix triangle_;
	private Tree tree = new Tree();
	private boolean ran_ = false;
	private long maxMemory_;
	private long freeMemory_;

	public Murtagh(DistanceProvider matrix, MurtaghParams params) {
		this.matrix_ = matrix;
		this.params_ = params;
		if (params_.getDistanceMatrixThreshold() == null) {
			params_.setDistanceMatrixThreshold(estimateMatrixSize(matrix.size()));
		}
	}

	public MurtaghParams getParams() {
		return params_;
	}

	public long getMaxMemory() {
		return maxMemory_;
	}

	public long getFreeMemory() {
		return freeMemory_;
	}

	private int estimateMatrixSize(int tunnels) {
		System.gc();
		long free = Runtime.getRuntime().freeMemory();
		free = free * 2 / 3; // safe reserve
		System.out.println("Free memory: " + free);
		int matrixSize = (int) Math.floor((1 + Math.sqrt(1 + free * 2)) / 2);
		return matrixSize;
	}

	@Override
	public HierarchicalClusteringOutput cluster() {
		contructTree();
		return new HierarchicalClusteringOutput(tree.cut(params_.getThreshold()), tree);
	}

	public Tree contructTree() {
		if (ran_) {
			throw new IllegalStateException("Cannot run murtaghHybrid twice.");
		} else {
			ran_ = true;
		}
		int n = matrix_.size();

		IndexCluster[] map = new IndexCluster[n];
		// clusters at actual level of hierarchy, indeces to map and matrix
		List<IndexCluster> clusters = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			MurtaghCluster c = new MurtaghCluster(i);
			c.setSize(matrix_.getElementSize(i));
			c.add(i);
			map[i] = c;
			clusters.add(c);
		}

		List<Integer> chain = new ArrayList<>(); // nearest neighbour chain
		boolean first = true;
		int[] idMatrix = null;
		int[] matrixId;
		boolean ram = false;
		while (2 <= clusters.size()) {
			if (clusters.size() <= params_.getDistanceMatrixThreshold() && first) { // go
																					// for
																					// efficiency
				int m = clusters.size();
				idMatrix = new int[n];
				matrixId = new int[m];
				Arrays.fill(idMatrix, -1);
				for (int i = 0; i < clusters.size(); i++) { // create mapping id
															// -> matrix index
					idMatrix[clusters.get(i).getID()] = i;
					matrixId[i] = clusters.get(i).getID();
				}
				triangle_ = new DistanceMatrix(m);
				for (int x = 0; x < m; x++) { // iteration through clusters
					for (int y = 0; y < x; y++) {
						double d = params_.getLinkage().getDistance(matrix_, clusters.get(x), clusters.get(y));
						triangle_.set(x, y, (float) d);
					}
				}
				ram = true;
				first = false;
			}
			if (0 == chain.size()) {
				int x = clusters.get(0).getID();
				chain.add(x);
			}
			int x = chain.get(chain.size() - 1);
			IndexCluster a = map[x];
			assert null != a;
			double dmin = Double.MAX_VALUE;
			int nn = -1;
			for (IndexCluster cy : clusters) {
				int y = cy.getID();
				if (x == y) {
					continue;
				}
				IndexCluster b = map[y];
				double d;
				if (ram) {
					int xi = idMatrix[x];
					int yi = idMatrix[y];
					d = triangle_.get(xi, yi);
				} else {
					d = params_.getLinkage().getDistance(matrix_, a, b);
				}
				if (d < dmin || (d == dmin && isSmaller(x, y, x, nn))) {
					// isSmaller guarantees that no two distances are equal
					dmin = d;
					nn = y;
				}
			}
			assert 0 <= nn;
			if (2 <= chain.size()) {
				int z = chain.get(chain.size() - 2); // NN chain: ... z x nn
				if (nn == z) { // reciprocal nearest neighbour pair found

					tree.add(new Merge(x, z, (float) dmin));

					IndexCluster i = map[x];
					IndexCluster j = map[z];

					if (ram) {
						for (IndexCluster wc : clusters) {
							int w = wc.getID();
							if (w == x || w == z) {
								continue;
							}

							assert (0 < i.size());
							assert (0 < j.size());

							int xi = idMatrix[x];
							int zi = idMatrix[z];
							int wi = idMatrix[w];

							// double d = (triangle_.get(xi, wi) * i.size()
							// + triangle_.get(zi, wi) * j.size())
							// / (i.size() + j.size());

							double dxw = triangle_.get(xi, wi);
							double dzw = triangle_.get(zi, wi);
							double d = params_.getLinkage().getDistanceUpdate(dxw, dzw, i.size(), j.size());

							assert !Double.isNaN(d);
							assert !Double.isInfinite(d) : triangle_.get(xi, wi) + " " + triangle_.get(zi, wi);

							triangle_.set(wi, xi, (float) d);
						}
					}

					IndexCluster k = i.merge(i.getID(), j);

					map[z] = null;
					map[x] = k;

					clusters.remove(i);
					clusters.remove(j);
					clusters.add(k);

					chain.remove(chain.size() - 1);
					chain.remove(chain.size() - 1);

				} else {
					chain.add(nn);
				}
			} else {
				chain.add(nn);
			}
		}
		return tree;
	}

	private boolean isSmaller(int a, int b, int c, int d) {
		int temp;
		if (b < a) {
			temp = a;
			a = b;
			b = temp;
		}
		if (d < c) {
			temp = c;
			c = d;
			d = temp;
		}
		if (a == c && b == d) {
			throw new RuntimeException(a + " " + b);
		}
		boolean r = false;
		if (a < c) {
			r = true;
		}
		if (a == c && b < d) {
			r = true;
		}
		return r;

	}
}
