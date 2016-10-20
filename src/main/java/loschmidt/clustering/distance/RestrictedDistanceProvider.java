package loschmidt.clustering.distance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import loschmidt.clustering.ClusteringOutput;
import loschmidt.clustering.IndexCluster;

/**
 *
 * @author Jan Stourac
 */
public class RestrictedDistanceProvider implements ClusterDistanceProvider {

	private final List<IndexCluster> clusters_ = new ArrayList<>();
	private final ClusterDistanceCalculator distanceCalculator_;

	public RestrictedDistanceProvider(ClusteringOutput out, ClusterDistanceCalculator distanceCalculator) {
		this(out.getClusters(), distanceCalculator);
	}

	public RestrictedDistanceProvider(List<IndexCluster> clusters, ClusterDistanceCalculator distanceCalculator) {
		this.clusters_.addAll(clusters);
		this.distanceCalculator_ = distanceCalculator;
	}

	@Override
	public float getDistance(int x, int y) {
		return distanceCalculator_.calculate(clusters_.get(x), clusters_.get(y));
	}

	@Override
	public int size() {
		return clusters_.size();
	}

	@Override
	public IndexCluster get(int x) {
		return clusters_.get(x);
	}

	@Override
	public int getElementSize(int x) {
		return get(x).size();
	}

	public void restrict(List<IndexCluster> clusters) {
		Map<Integer, IndexCluster> clusterMap = new HashMap<>();

		for (IndexCluster c : clusters_) {
			clusterMap.put(c.getID(), c);
		}

		clusters_.clear();
		for (IndexCluster cluster : clusters) {
			IndexCluster newCluster = new IndexCluster(cluster.getID());
			for (int i : cluster.getMembers()) {
				newCluster.addAll(clusterMap.get(i).getMembers());
			}
			clusters_.add(newCluster);
		}
	}

	public List<IndexCluster> getClusters() {
		return clusters_;
	}

}
