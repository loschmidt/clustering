package loschmidt.clustering.hierarchical;

import loschmidt.clustering.IndexCluster;
import loschmidt.clustering.distance.DistanceProvider;

/**
 *
 * @author Jan Stourac
 */
public class CompleteLinkage<T> implements Linkage<T>, SatisfyReducibility {

	@Override
	public double getDistanceUpdate(double distAD, double distBD, int sizeA, int sizeB) {
		return Math.max(distAD, distBD);
	}

	@Override
	public double getDistance(DistanceProvider<T> matrix, IndexCluster c1, IndexCluster c2) {
		double d = 0;

		for (int i : c1.getMembers()) {
			for (int j : c2.getMembers()) {
				d = Math.max(d, matrix.getDistance(i, j));
			}
		}

		return d;
	}

}
