package loschmidt.clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import loschmidt.clustering.distance.DistanceProvider;
import loschmidt.clustering.distance.HasSize;
import loschmidt.clustering.util.DynamicDistribution;

/**
 *
 * @author Jan Stourac
 */
public class IndexCluster implements HasSize, Comparable<IndexCluster> {

	public static final int NONE_ID = -1;

	protected int id_ = NONE_ID;
	protected final List<Integer> members_ = new ArrayList<>();

	public IndexCluster() {
	}

	public IndexCluster(int id) {
		this.id_ = id;
	}

	public void setID(int id) {
		this.id_ = id;
	}

	public int getID() {
		return id_;
	}

	public void add(Integer member) {
		this.members_.add(member);
	}

	public void addAll(Collection<Integer> members) {
		this.members_.addAll(members);
	}

	public List<Integer> getMembers() {
		return members_;
	}

	@Override
	public int size() {
		return members_.size();
	}

	public IndexCluster merge(int newId, IndexCluster c) {
		IndexCluster gc = new IndexCluster(newId);

		gc.addAll(getMembers());
		gc.addAll(c.getMembers());

		return gc;
	}

	public IndexCluster merge(IndexCluster c) {
		return merge(NONE_ID, c);
	}

	public DynamicDistribution computeDistanceDistribution(DistanceProvider matrix) {
		DynamicDistribution distribution = new DynamicDistribution();
		for (int x = 0; x < size(); x++) {
			double nn = Double.POSITIVE_INFINITY;
			int count = 0;
			double sum = 0;
			for (int y = 0; y < x; y++) {
				double d = matrix.getDistance(members_.get(x), members_.get(y));
				if (d < nn) {
					nn = d;
				}
				sum += d;
				count++;
			}
			if (distribution.getMostDistantNearestNeighbour() < nn) {
				distribution.setMostDistantNearestNeighbour(nn);
			}
			distribution.add(sum / count);
		}

		return distribution;
	}

	@Override
	public String toString() {
		List<Integer> es = new ArrayList<>(getMembers());
		Collections.sort(es);
		StringBuilder sb = new StringBuilder();
		for (int i : es) {
			sb.append(i).append(" ");
		}
		return sb.toString().trim();
	}

	@Override
	public int compareTo(IndexCluster o) {
		return Integer.compare(id_, o.getID());
	}
}
