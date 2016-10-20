package loschmidt.clustering.method;

import java.util.List;
import loschmidt.clustering.Cluster;
import loschmidt.clustering.ClusterResolver;
import loschmidt.clustering.ClusteringAlgorithm;
import loschmidt.clustering.ClusteringException;
import loschmidt.clustering.ClusteringOutput;
import loschmidt.clustering.distance.DistanceProvider;
import loschmidt.clustering.util.Utils;

/**
 *
 * @author Jan Stourac
 */
public class SimpleClustering<T> {

	private final DistanceProvider<T> matrix_;
	private final ClusteringAlgorithm algorithm_;
	private ClusteringOutput clusteringOutput_;

	public <R extends ClusteringAlgorithm<S>, S> SimpleClustering(DistanceProvider<T> matrix, Class<R> algorithm, S params) throws ClusteringException {
		this.matrix_ = matrix;
		this.algorithm_ = Utils.newInstance(algorithm, matrix, params);
	}

	public List<Cluster<T>> cluster() {
		this.clusteringOutput_ = algorithm_.cluster();
		return new ClusterResolver<>(matrix_, clusteringOutput_.getClusters()).resolveElements();
	}

	public ClusteringOutput getClusteringOutput() {
		return clusteringOutput_;
	}
}
