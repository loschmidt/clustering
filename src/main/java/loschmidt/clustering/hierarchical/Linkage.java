package loschmidt.clustering.hierarchical;

import loschmidt.clustering.IndexCluster;
import loschmidt.clustering.distance.DistanceProvider;

/**
 *
 * @author Jan Stourac
 */
public interface Linkage {

	double getDistance(DistanceProvider matrix, IndexCluster c1, IndexCluster c2);
}
