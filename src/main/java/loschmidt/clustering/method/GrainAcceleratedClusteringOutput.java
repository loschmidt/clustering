package loschmidt.clustering.method;

import java.util.List;
import loschmidt.clustering.Cluster;
import loschmidt.clustering.ClusterResolver;
import loschmidt.clustering.ClusteringOutput;
import loschmidt.clustering.distance.DistanceProvider;

/**
 *
 * @author Jan Stourac
 */
public class GrainAcceleratedClusteringOutput<T> {

	private final ClusteringOutput grains_;
	private final ClusteringOutput clustering_;
	private final List<Cluster<T>> clusters_;

	public GrainAcceleratedClusteringOutput(DistanceProvider<T> matrix, ClusteringOutput grains, ClusteringOutput clustering) {
		this.grains_ = grains;
		this.clustering_ = clustering;
		this.clusters_ = new ClusterResolver<T>(matrix, grains.getClusters(), clustering.getClusters()).resolveElements();
	}

	public ClusteringOutput getGrainsOutput() {
		return grains_;
	}

	public int getGrainsNumber() {
		return grains_.size();
	}

	public ClusteringOutput getClusteringOutput() {
		return clustering_;
	}

	public List<Cluster<T>> getClusters() {
		return clusters_;
	}
}
