package loschmidt.clustering.grain;

import loschmidt.clustering.IndexCluster;

/**
 *
 * @author Jan Stourac
 */
public class GrainIndexCluster extends IndexCluster {

	public GrainIndexCluster(int id) {
		super(id);
		add(id);
	}

	public int getCore() {
		return getID();
	}

	public void remove(int e) {
		members_.remove(e);
	}

}
