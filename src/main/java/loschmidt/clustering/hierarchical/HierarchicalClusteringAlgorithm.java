package loschmidt.clustering.hierarchical;

import loschmidt.clustering.ClusteringAlgorithm;

/**
 *
 * @author Jan Stourac
 */
public interface HierarchicalClusteringAlgorithm<T> extends ClusteringAlgorithm<T> {

	@Override
	HierarchicalClusteringOutput cluster();
}
