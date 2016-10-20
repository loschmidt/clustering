package loschmidt.clustering.beststays;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import loschmidt.clustering.ClusteringAlgorithm;
import loschmidt.clustering.distance.DistanceProvider;

/**
 * Performs simple clustering procedure (see below).
 *
 * Starts with cheapest element and iteratively removes all elements within
 * distance. Then continues with the next remaining cheapest element until all
 * elements are either sorted out or reported as cluster representant.
 *
 * @author Jan Stourac
 * @author Antonin Pavelka
 */
public class BestStaysClustering implements ClusteringAlgorithm<BestStaysParams> {

	private final DistanceProvider matrix_;
	private final BestStaysParams params_;

	public BestStaysClustering(DistanceProvider matrix, BestStaysParams params) {
		this.matrix_ = matrix;
		this.params_ = params;
	}

	@Override
	public BestStaysOutput cluster() {
		final Map<Object, Integer> indices = new HashMap<>();
		final List<Object> sorted = new ArrayList<>();
		for (int i = 0; i < matrix_.size(); i++) {
			Object o = matrix_.get(i);
			indices.put(matrix_.get(i), i);
			sorted.add(o);
		}

		System.out.println("sorting ....");
		Collections.sort(sorted, params_.getComparator());
		System.out.println("... sorted ");

		BestStaysOutput out = new BestStaysOutput();
		int id = 0;
		while (!sorted.isEmpty()) {
			int a = indices.get(sorted.remove(0));
			BestStaysCluster cluster = new BestStaysCluster(id++, a);
			List<Object> toRemove = new ArrayList<>();

			for (int i = 0; i < sorted.size(); i++) {
				Object elm = sorted.get(i);
				int b = indices.get(elm);
				float d = matrix_.getDistance(a, b);
				if (d < params_.getThreshold()) {
					cluster.add(i);
					toRemove.add(elm);
				}
			}

			sorted.removeAll(toRemove);
			out.add(cluster);
		}

		System.out.println("/// best stays clustered ");

		return out;
	}

}
