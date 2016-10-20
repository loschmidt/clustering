package loschmidt.clustering.method;

import loschmidt.clustering.ClusteringAlgorithm;
import loschmidt.clustering.ClusteringException;
import loschmidt.clustering.ClusteringOutput;
import loschmidt.clustering.distance.DistanceProvider;
import loschmidt.clustering.distance.RestrictedDistanceProvider;

/**
 *
 * @author Antonin Pavelka
 */
public class GrainAcceleratedClustering<T> {

	private final DistanceProvider matrix_;
	private final GrainAcceleratedClusteringParams params_;

	private boolean inMemory = true;

	public GrainAcceleratedClustering(DistanceProvider<T> matrix, GrainAcceleratedClusteringParams params) {
		this.matrix_ = matrix;
		this.params_ = params;
	}

	public GrainAcceleratedClusteringOutput cluster() throws ClusteringException {
		long t1 = System.nanoTime();

		ClusteringAlgorithm gc = params_.getGrainsAlgorithm().newInstance(matrix_);
		ClusteringOutput gcs = gc.cluster();

		long t2 = System.nanoTime();
		System.out.println(gcs.size() + " grains (from " + matrix_.size()
				+ " elements) in " + ((t2 - t1) / 1000 / 1000) + " ms.");

		t1 = System.nanoTime();

		DistanceProvider submatrix = new RestrictedDistanceProvider(gcs, params_.getClusterDistanceCalculator());

		ClusteringOutput cao;
		if (inMemory) {
			ClusteringAlgorithm ca = params_.getClusteringAlgorithm().newInstance(submatrix);
			cao = ca.cluster();
		} else {
			throw new RuntimeException("Only inMemory option supported.");
		}

		t2 = System.nanoTime();

		System.out.println(cao.size() + " clusters in " + ((t2 - t1) / 1000 / 1000) + " ms.");

		return new GrainAcceleratedClusteringOutput(matrix_, gcs, cao);
	}
}
