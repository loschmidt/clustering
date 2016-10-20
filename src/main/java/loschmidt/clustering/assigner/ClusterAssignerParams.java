package loschmidt.clustering.assigner;

import java.util.List;
import loschmidt.clustering.IndexCluster;

/**
 *
 * @author Jan Stourac
 */
public class ClusterAssignerParams {

	private double threshold_ = Double.MAX_VALUE;
	private List<IndexCluster> clusters_;
	private boolean includeOriginalMembers_ = false;

	public ClusterAssignerParams setThreshold(double threshold) {
		this.threshold_ = threshold;
		return this;
	}

	public double getThreshold() {
		return threshold_;
	}

	public ClusterAssignerParams setInitialClusters(List<IndexCluster> clusters) {
		this.clusters_ = clusters;
		return this;
	}

	public List<IndexCluster> getInitialClusters() {
		return clusters_;
	}

	/**
	 * Also includes members from original clusters to final clusters.
	 *
	 * @param includeOriginalMembers
	 */
	public ClusterAssignerParams setIncludeOriginalMembers(boolean includeOriginalMembers) {
		this.includeOriginalMembers_ = includeOriginalMembers;
		return this;
	}

	public boolean getIncludeOriginalMembers() {
		return includeOriginalMembers_;
	}
}
