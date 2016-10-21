package loschmidt.clustering.hierarchical;

import loschmidt.clustering.IndexCluster;
import loschmidt.clustering.distance.DistanceProvider;

/**
 * Primarily intended to implement average, complete and single linkage criteria
 * in agglomerative hierarchical clustering (see Linkage criteria in
 * https://en.wikipedia.org/wiki/Hierarchical_clustering).
 *
 * @author Jan Stourac
 * @author Antonin Pavelka
 */
public interface Linkage<T> {

	/**
	 * Defines the distance between clusters D and C, given that C was created
	 * by merging clusters A and B.
	 * 
	 * @param distAD
	 *            distance between cluster A and D
	 * @param distBD
	 *            distance between cluster B and D
	 * @param sizeA
	 *            number of instances in cluster A
	 * @param sizeB
	 *            number of instances in cluster B
	 * @return distance between a cluster D and the cluster C newly created by
	 *         merging the clusters A and B
	 */
	double getDistanceUpdate(double distAD, double distBD, int sizeA, int sizeB);

	/**
	 * Defines what is the distance between two clusters. This is used in
	 * situations such as creation of the initial matrix of distances, when
	 * hierarchical clustering is starting to merge clusters created by a
	 * different algorithm.
	 */
	double getDistance(DistanceProvider<T> matrix, IndexCluster c1, IndexCluster c2);

}
