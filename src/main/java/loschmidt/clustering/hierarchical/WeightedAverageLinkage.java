package loschmidt.clustering.hierarchical;

import loschmidt.clustering.IndexCluster;
import loschmidt.clustering.distance.DistanceProvider;

/**
 *
 * @author Jan Stourac
 */
public class WeightedAverageLinkage<T> implements Linkage<T>, SatisfyReducibility {

	@Override
	public double getDistanceUpdate(double distAD, double distBD, int sizeA, int sizeB) {
		return (distAD * sizeA + distBD * sizeB) / (sizeA + sizeB);
	}

	@Override
	public double getDistance(DistanceProvider<T> matrix, IndexCluster c1, IndexCluster c2) {
		double d = 0;
		int totalDistances = 0;

		for (int i : c1.getMembers()) {
			for (int j : c2.getMembers()) {
				int distances = matrix.getElementSize(i) * matrix.getElementSize(j);
				d += matrix.getDistance(i, j) * distances;
				totalDistances += distances;
			}
		}

		return d / totalDistances;
	}

}
