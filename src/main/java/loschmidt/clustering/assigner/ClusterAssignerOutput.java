package loschmidt.clustering.assigner;

import loschmidt.clustering.ClusteringOutput;
import loschmidt.clustering.IndexCluster;

/**
 *
 * @author Jan Stourac
 */
public class ClusterAssignerOutput extends ClusteringOutput {

	private final int JUNK_ID = 999999;

	private final IndexCluster junk_ = new IndexCluster(JUNK_ID);

	public IndexCluster getJunk() {
		return junk_;
	}

}
