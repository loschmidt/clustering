package loschmidt.clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import loschmidt.clustering.distance.HasSize;

/**
 *
 * @author Jan Stourac
 */
public class Cluster<T> implements HasSize, Comparable<Cluster<T>> {

	public static final int NONE_ID = -1;

	private int id_ = NONE_ID;
	private final List<T> members_ = new ArrayList<>();

	public Cluster() {
	}

	public Cluster(int id) {
		this.id_ = id;
	}

	public Cluster(int id, Collection<T> members) {
		this.id_ = id;
		addAll(members);
	}

	public void setID(int id) {
		this.id_ = id;
	}

	public int getID() {
		return id_;
	}

	public void add(T member) {
		this.members_.add(member);
	}

	public void addAll(Collection<T> members) {
		this.members_.addAll(members);
	}

	public List<T> getMembers() {
		return members_;
	}

	@Override
	public int size() {
		return members_.size();
	}

	public Cluster<T> merge(int newId, Cluster<T> c) {
		Cluster<T> gc = new Cluster<>(newId);

		gc.addAll(getMembers());
		gc.addAll(c.getMembers());

		return gc;
	}

	public Cluster<T> merge(Cluster<T> c) {
		return merge(NONE_ID, c);
	}

	@Override
	public int compareTo(Cluster<T> o) {
		return Integer.compare(id_, o.getID());
	}
}
