package loschmidt.clustering.beststays;

import loschmidt.clustering.IndexCluster;

/**
 *
 * @author Jan Stourac
 */
public class BestStaysCluster extends IndexCluster {

	private final int core_;

	public BestStaysCluster(int id, int core) {
		super(id);
		this.core_ = core;
		add(core);
	}

	public int getCore() {
		return core_;
	}
}
