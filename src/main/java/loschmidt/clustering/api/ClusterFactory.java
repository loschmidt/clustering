package loschmidt.clustering.api;

import java.util.List;

import loschmidt.clustering.IndexCluster;
import loschmidt.clustering.distance.DistanceProvider;
import loschmidt.clustering.hierarchical.Tree;
import loschmidt.clustering.hierarchical.murtagh.Murtagh;
import loschmidt.clustering.hierarchical.murtagh.MurtaghParams;

public class ClusterFactory<T> {

	private final DistanceProvider<T> matrix;		
	private MurtaghParams params;

	public ClusterFactory(DistanceProvider<T> matrix, MurtaghParams params) {
		this.matrix = matrix;
		this.params = params;
	}

	public Clusters<T> cluster(double clusteringTheshold) {
		Clusters<T> clusters = new Clusters<>();		
		Murtagh a = new Murtagh(matrix, params);
		Tree tree = a.contructTree();
		List<IndexCluster> indexClusters = tree.cut(clusteringTheshold);
		for (IndexCluster c : indexClusters) {
			Cluster<T> cluster = new Cluster<>();
			for (int i : c.getMembers()) {
				cluster.add(matrix.get(i));
			}
			clusters.add(cluster);
		}
		return clusters;
	}

}
