package loschmidt.clustering.hierarchical;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import loschmidt.clustering.IndexCluster;

/**
 *
 * @author Jan Stourac
 */
public class Tree {

	private final SortedSet<Merge> merges_ = new TreeSet<>();
	private boolean mergeIDsGenerated_ = false;

	public void add(Merge merge) {
		merges_.add(merge);
		mergeIDsGenerated_ = false;
	}

	public void add(int x, int y, float d) {
		add(new Merge(x, y, d));
	}

	public Set<Merge> getMerges() {
		return merges_;
	}

	public void generateMergeIDs() {
		if (mergeIDsGenerated_) {
			return;
		}

		int id = merges_.size() + 1;
		for (Merge merge : merges_) {
			merge.setID(id++);
		}
		mergeIDsGenerated_ = true;
	}

	public List<IndexCluster> cut(double threshold) {
		SortedMap<Integer, IndexCluster> clusters = new TreeMap<>();

		for (Merge merge : merges_) {
			if (!clusters.containsKey(merge.getX())) {
				IndexCluster ic = new IndexCluster(merge.getX());
				ic.add(merge.getX());
				clusters.put(merge.getX(), ic);
			}
			if (!clusters.containsKey(merge.getY())) {
				IndexCluster ic = new IndexCluster(merge.getY());
				ic.add(merge.getY());
				clusters.put(merge.getY(), ic);
			}
		}

		for (Merge merge : merges_) {
			if (merge.getDistance() >= threshold) {
				break;
			}

			int x = merge.getX();
			int y = merge.getY();

			IndexCluster a = clusters.get(x);
			IndexCluster b = clusters.get(y);
			IndexCluster c = a.merge(merge.getID(), b);
			clusters.remove(y);
			clusters.put(x, c);
		}

		List<IndexCluster> clustersList = new ArrayList<>(clusters.values());
		Collections.sort(clustersList);

		return clustersList;
	}
}
