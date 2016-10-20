package loschmidt.clustering.hierarchical.murtagh;

import loschmidt.clustering.IndexCluster;

/**
 *
 * @author Jan Stourac
 */
public class MurtaghCluster extends IndexCluster {

	private int size_ = 0;

	public MurtaghCluster(int id) {
		super(id);
	}

	public void setSize(int size) {
		this.size_ = size;
	}

	@Override
	public int size() {
		return size_;
	}

	@Override
	public IndexCluster merge(int newId, IndexCluster c) {
		MurtaghCluster gc = new MurtaghCluster(newId);

		gc.addAll(getMembers());
		gc.addAll(c.getMembers());
		gc.setSize(size() + c.size());

		return gc;
	}
}
