package loschmidt.clustering;

import java.util.ArrayList;
import java.util.List;
import loschmidt.clustering.distance.DistanceProvider;

/**
 *
 * @author Jan Stourac
 */
public class ClusterResolver<T> {

	private final DistanceProvider<T> elements_;
	private final List<IndexCluster>[] clusterHierarchy_;

	public ClusterResolver(DistanceProvider<T> elements, List<IndexCluster>... clusterHierarchy) {
		this.elements_ = elements;
		this.clusterHierarchy_ = clusterHierarchy;
	}

	public List<Integer> getElementIndices(int cluster) {
		List<Integer> indices = new ArrayList<>();
		findElementIndices(getTopLayerIndex(), cluster, indices);
		return indices;
	}

	public List<Integer> getElementIndices(IndexCluster cluster) {
		if (!getTopLayer().contains(cluster)) {
			throw new IllegalArgumentException("Cluster is not in the top level");
		}

		return getElementIndices(getTopLayer().indexOf(cluster));
	}

	public Cluster<T> resolveElements(int cluster) {
		List<T> elms = new ArrayList<>();
		findElements(getTopLayerIndex(), cluster, elms);
		return new Cluster<>(getTopLayer().get(cluster).getID(), elms);
	}

	public Cluster<T> resolveElements(IndexCluster cluster) {
		if (!getTopLayer().contains(cluster)) {
			throw new IllegalArgumentException("Cluster is not in the top level");
		}

		return resolveElements(getTopLayer().indexOf(cluster));
	}

	public List<Cluster<T>> resolveElements() {
		List<Cluster<T>> resolved = new ArrayList<>();

		for (IndexCluster cluster : getTopLayer()) {
			resolved.add(resolveElements(cluster));
		}

		return resolved;
	}

	public List<IndexCluster> getTopLayer() {
		return clusterHierarchy_[clusterHierarchy_.length - 1];
	}

	public List<IndexCluster> getLayer(int layer) {
		return clusterHierarchy_[layer];
	}

	private int getTopLayerIndex() {
		return clusterHierarchy_.length - 1;
	}

	private void findElementIndices(int level, int cluster, List<Integer> out) {
		IndexCluster c = clusterHierarchy_[level].get(cluster);

		if (level == 0) {
			out.addAll(c.getMembers());
			return;
		}

		for (int clusterIndex : c.getMembers()) {
			findElementIndices(level - 1, clusterIndex, out);
		}
	}

	private void findElements(int level, int cluster, List<T> out) {
		IndexCluster c = clusterHierarchy_[level].get(cluster);

		if (level == 0) {
			for (int i : c.getMembers()) {
				out.add(elements_.get(i));
			}
			return;
		}

		for (int clusterIndex : c.getMembers()) {
			findElements(level - 1, clusterIndex, out);
		}
	}

}
