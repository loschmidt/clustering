package loschmidt.clustering.distance;

import loschmidt.clustering.IndexCluster;

/**
 *
 * @author Jan Stourac
 */
public class AvgClusterDistanceCalculator implements ClusterDistanceCalculator {

	private final DistanceProvider distanceProvider_;

	public AvgClusterDistanceCalculator(DistanceProvider distanceProvider) {
		this.distanceProvider_ = distanceProvider;
	}

	@Override
	public float calculate(IndexCluster o1, IndexCluster o2) {
		int count = 0;
		double sum = 0;
		for (int i : o1.getMembers()) {
			for (int j : o2.getMembers()) {
				float d = distanceProvider_.getDistance(i, j);
				sum += d;
				count++;
			}
		}

		return (float) (sum / count);
	}

}
