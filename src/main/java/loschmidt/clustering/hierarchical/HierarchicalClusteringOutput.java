package loschmidt.clustering.hierarchical;

import java.util.Collection;
import java.util.List;
import loschmidt.clustering.ClusteringOutput;
import loschmidt.clustering.IndexCluster;

/**
 *
 * @author Jan Stourac
 */
public class HierarchicalClusteringOutput extends ClusteringOutput {

	private final Tree tree_;

	public HierarchicalClusteringOutput(Collection<IndexCluster> clusters, Tree tree) {
		super(clusters);
		this.tree_ = tree;
	}

	public Tree getTree() {
		return tree_;
	}

	public List<IndexCluster> cutTree(double threshold) {
		return tree_.cut(threshold);
	}

}
