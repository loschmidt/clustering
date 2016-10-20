package loschmidt.clustering.hierarchical;

import loschmidt.clustering.IndexCluster;
import loschmidt.clustering.distance.DistanceProvider;

/**
 *
 * @author Jan Stourac
 */
public class SingleLinkage implements Linkage, SatisfyReducibility {

	@Override
	public double getDistance(DistanceProvider matrix, IndexCluster c1, IndexCluster c2) {
		double d = 0;

		for (int i : c1.getMembers()) {
			for (int j : c2.getMembers()) {
				d = Math.min(d, (double) matrix.getDistance(i, j));
			}
		}

		return d;
	}

}
