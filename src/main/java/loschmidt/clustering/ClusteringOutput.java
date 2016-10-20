package loschmidt.clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

class SizeComparator implements Comparator<IndexCluster> {

	@Override
	public int compare(IndexCluster a, IndexCluster b) {
		return -1 * Integer.compare(a.size(), b.size());
	}
}

public class ClusteringOutput implements Iterable<IndexCluster> {

	private final List<IndexCluster> clusters = new ArrayList<>();

	public ClusteringOutput() {
	}

	public ClusteringOutput(Collection<IndexCluster> c) {
		clusters.addAll(c);
	}

	public void add(IndexCluster c) {
		clusters.add(c);
	}

	public void addAll(Collection<IndexCluster> c) {
		clusters.addAll(c);
	}

	@Override
	public Iterator<IndexCluster> iterator() {
		return clusters.iterator();
	}

	public void sortBySize() {
		Collections.sort(clusters, new SizeComparator());
	}

	public int size() {
		return clusters.size();
	}

	public IndexCluster get(int i) {
		return clusters.get(i);
	}

	public List<IndexCluster> getClusters() {
		return clusters;
	}

	public IndexCluster getByID(int id) {
		for (IndexCluster cluster : this) {
			if (cluster.getID() == id) {
				return cluster;
			}
		}

		return null;
	}

	@Override
	public boolean equals(Object o) {
		ClusteringOutput clustering = (ClusteringOutput) o;

		List<String> a = new ArrayList<>();
		List<String> b = new ArrayList<>();

		for (IndexCluster c : clusters) {
			a.add(c.toString());
		}
		for (IndexCluster c : clustering.clusters) {
			b.add(c.toString());
		}

		Collections.sort(a);
		Collections.sort(b);

		for (int i = 0; i < Math.min(a.size(), b.size()); i++) {
			String as = a.get(i);
			String bs = b.get(i);
			if (!as.equals(bs)) {

				System.out.println(as);
				System.out.println("|||");
				System.out.println(bs);
				return false;
			}
		}

		if (a.size() != b.size()) {
			return false;
		}
		return true;
	}

	public void print() {

		List<String> a = new ArrayList<>();

		for (IndexCluster c : clusters) {
			a.add(c.toString());
		}

		Collections.sort(a);

		for (int i = 0; i < a.size(); i++) {
			String as = a.get(i);
			System.out.print(as + "|");
		}
		System.out.println();
	}

	@Override
	public int hashCode() {
		throw new UnsupportedOperationException();
	}
}
