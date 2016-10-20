package loschmidt.clustering.util;

import loschmidt.clustering.ClusteringAlgorithm;
import loschmidt.clustering.ClusteringException;
import loschmidt.clustering.distance.DistanceProvider;

/**
 *
 * @author Jan Stourac
 */
public class LoadedAlgorithm<T extends ClusteringAlgorithm<U>, U> {

	private final Class<T> algorithm_;
	private final U settings_;

	public LoadedAlgorithm(Class<T> algorithm, U settings) {
		this.algorithm_ = algorithm;
		this.settings_ = settings;
	}

	public T newInstance(DistanceProvider provider) throws ClusteringException {
		return Utils.newInstance(algorithm_, provider, settings_);
	}
}
