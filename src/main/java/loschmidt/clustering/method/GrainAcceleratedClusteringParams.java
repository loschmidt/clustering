package loschmidt.clustering.method;

import loschmidt.clustering.distance.ClusterDistanceCalculator;
import loschmidt.clustering.ClusteringAlgorithm;
import loschmidt.clustering.grain.GrainClusteringAlgorithm;
import loschmidt.clustering.util.LoadedAlgorithm;

public class GrainAcceleratedClusteringParams {

	private LoadedAlgorithm grainsAlgorithm_;
	private LoadedAlgorithm clusteringAlgorithm_;
	private ClusterDistanceCalculator clusterDistanceCalculator_;

	public <T extends GrainClusteringAlgorithm<U>, U> void setGrainsAlgorithm(Class<T> algorithm, U settings) {
		this.grainsAlgorithm_ = new LoadedAlgorithm(algorithm, settings);
	}

	public LoadedAlgorithm getGrainsAlgorithm() {
		return grainsAlgorithm_;
	}

	public <T extends ClusteringAlgorithm<U>, U> void setClusteringAlgorithm(Class<T> algorithm, U settings) {
		this.clusteringAlgorithm_ = new LoadedAlgorithm(algorithm, settings);
	}

	public LoadedAlgorithm getClusteringAlgorithm() {
		return clusteringAlgorithm_;
	}

	public void setClusterDistanceCalculator(ClusterDistanceCalculator clusterDistanceCalculator) {
		this.clusterDistanceCalculator_ = clusterDistanceCalculator;
	}

	public ClusterDistanceCalculator getClusterDistanceCalculator() {
		return clusterDistanceCalculator_;
	}
}
