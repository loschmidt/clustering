package loschmidt.clustering.assigner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import loschmidt.clustering.ClusteringAlgorithm;
import loschmidt.clustering.IndexCluster;
import loschmidt.clustering.distance.DistanceProvider;
import loschmidt.clustering.util.DynamicDistribution;

/**
 *
 * @author Jan Stourac
 */
public class ClusterAssigner implements ClusteringAlgorithm<ClusterAssignerParams> {

	private final DistanceProvider matrix_;
	private final ClusterAssignerParams params_;
	private final Map<Integer, DynamicDistribution> distributions_ = new HashMap<>();

	public ClusterAssigner(DistanceProvider matrix, ClusterAssignerParams params) {
		this.matrix_ = matrix;
		this.params_ = params;
	}

	@Override
	public ClusterAssignerOutput cluster() {
		boolean[] skip = new boolean[matrix_.size()];
		Map<Integer, IndexCluster> newClusters = new HashMap<>();

		for (IndexCluster cluster : params_.getInitialClusters()) {
			IndexCluster newCluster = new IndexCluster(cluster.getID());

			if (params_.getIncludeOriginalMembers()) {
				newCluster.addAll(cluster.getMembers());
			}

			newClusters.put(newCluster.getID(), newCluster);

			for (int i : cluster.getMembers()) {
				skip[i] = true;
			}
		}

		ClusterAssignerOutput out = new ClusterAssignerOutput();

		computeDistanceDistributions();

		for (int x = 0; x < matrix_.size(); ++x) {
			if (skip[x]) {
				continue;
			}

			IndexCluster bestC = null;
			double bestD = Double.POSITIVE_INFINITY;

			for (IndexCluster c : params_.getInitialClusters()) {
				for (int y : c.getMembers()) {
					double d = matrix_.getDistance(x, y);
					if (d < bestD) {
						bestC = c;
						bestD = d;
					}
				}
			}

			// now bestC is cluster containing nearest to xt
            /*
			 * verify average distance corresponds to distribution of internal
			 * cluster average distances
			 */
			double smallestDistance = Double.POSITIVE_INFINITY;
			double sum = 0;
			List<Integer> yt = bestC.getMembers();
			for (int y : yt) {
				double d = matrix_.getDistance(x, y);
				if (d < smallestDistance) {
					smallestDistance = d;
				}
				sum += d;
			}

			double averageDistance = sum / yt.size();
			if (belongsTo(bestC, averageDistance, smallestDistance)) {
				newClusters.get(bestC.getID()).add(x);
			} else {
				out.getJunk().add(x);
			}
		}

		for (IndexCluster ic : newClusters.values()) {
			out.add(ic);
		}

		return out;
	}

	private void computeDistanceDistributions() {
		System.out.println("Computing cluster internal distance distributions...");
		for (IndexCluster c : params_.getInitialClusters()) {
			distributions_.put(c.getID(), c.computeDistanceDistribution(matrix_));
		}
		System.out.println("...done.");
	}

	private boolean belongsTo(IndexCluster ic, double averageDistance, double smallestDistance) {
		if (ic.size() == 1) {
			return smallestDistance <= params_.getThreshold();
		} else {
			DynamicDistribution averageDistances = distributions_.get(ic.getID());
			int ge = averageDistances.greaterOrEqual(averageDistance);
			return 1 <= ge && smallestDistance <= averageDistances.getMostDistantNearestNeighbour();
		}
	}
}
