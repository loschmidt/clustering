package loschmidt.clustering.grain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import loschmidt.clustering.ClusteringOutput;
import loschmidt.clustering.distance.DistanceProvider;
import loschmidt.clustering.IndexCluster;

public class GrainClustering implements GrainClusteringAlgorithm<GrainClusteringParams> {

    private class Element implements Comparable<Element> {

        int index_;
        double value_;

        public Element(int index, double value) {
            index_ = index;
            value_ = value;
        }

        @Override
        public int compareTo(Element e) {
            return new Double(value_).compareTo(e.value_);
        }

        @Override
        public boolean equals(Object o) {
            Element e = (Element) o;
            return this.index_ == e.index_;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + this.index_;
            return hash;
        }
    }

	private final DistanceProvider matrix_;
	private final int[] order_;
	GrainClusteringParams params_;
    /*
     * order - ordering of elements to cluster according their importance, i.e.
     * probability they are real
     */

	public GrainClustering(DistanceProvider matrix, GrainClusteringParams gcs) {
		matrix_ = matrix;
		order_ = new int[matrix_.size()];
		params_ = gcs;

		order();
	}

	@Override
	public ClusteringOutput cluster() {

		switch (params_.getMethod()) {
			case MAX_DIST:
				return granulate();
			case AVG_DIST:
				return granulateByAverageDistance();
			case AVG_DIST_INCREMENTAL:
				return granulateByMinIncrement();
			case AVG_DIST_INCREMENTAL_NO_DELETION:
				return granulateByMinIncrementNoDeletion();
			case AVG_DIST_NO_DELETION:
				return granulateByAverageDistanceNoDeletion();
			default:
				throw new RuntimeException();
		}
	}

	private void order() {
		if (params_.getOrderComparator() == null) {
			for (int i = 0; i < matrix_.size(); ++i) {
				order_[i] = i;
			}
			return;
		}

		SortedMap<Object, Integer> elms = new TreeMap<>(params_.getOrderComparator());
		for (int i = 0; i < matrix_.size(); ++i) {
			elms.put(matrix_.get(i), i);
		}
		int i = 0;
		for (Integer val : elms.values()) {
			order_[i++] = val;
		}
	}

    private double[] getDensities() {
        double[] distanceSum = new double[matrix_.size()];
        for (int x = 0; x < matrix_.size(); x++) {
            for (int y = 0; y < matrix_.size(); y++) {
                distanceSum[x] += matrix_.getDistance(x, y);
            }
        }
        return distanceSum;
    }

    private void deriveOrdering() {
        double[] sums = getDensities();

        Element[] es = new Element[sums.length];
        for (int i = 0; i < sums.length; i++) {
            es[i] = new Element(i, sums[i]);
        }

        Arrays.sort(es);

        for (int i = 0; i < es.length; i++) {
            Element e = es[i];

            order_[i] = e.index_;
        }
    }

	private ClusteringOutput granulate() {
        // deriveOrdering(); // TRY density with 10 % closest?

		ClusteringOutput clusters = new ClusteringOutput();
        boolean[] clustered = new boolean[matrix_.size()];
        for (int x : order_) {
            // x is the core of the cluster
            if (clustered[x]) {
                continue;
            }
			GrainIndexCluster c = new GrainIndexCluster(x);
            clustered[x] = true;
            for (int y = 0; y < matrix_.size(); y++) {
                // evaluate if y belongs to cluster defined by x
                if (clustered[y]) {
                    continue;
                }
				if (matrix_.getDistance(x, y) <= params_.getThreshold()) {
                    c.add(y);
                    clustered[y] = true;
                }
            }
            if (c.size() == 0) {
                throw new RuntimeException();
            }
            clusters.add(c);
        }

        return clusters;
    }

	private ClusteringOutput granulateByAverageDistance() {

		ClusteringOutput clusters = new ClusteringOutput();
        boolean[] clustered = new boolean[matrix_.size()];
        for (int x : order_) {
            // x is the core of the cluster
            if (clustered[x]) {
                continue;
            }

			GrainIndexCluster c = new GrainIndexCluster(x);
            clustered[x] = true;
            int n = 0;
            ArrayList<ValuedInteger> list = new ArrayList<ValuedInteger>();
            for (int y = 0; y < matrix_.size(); y++) {
                // evaluate if y belongs to cluster defined by x
                if (clustered[y] || x == y) {
                    continue;
                }

                double d = matrix_.getDistance(x, y);
                list.add(new ValuedInteger(y, d));
            }

            double sum = 0;
            int count = 0;
            List<Integer> include = new ArrayList<Integer>();
            include.add(x);
            Collections.sort(list);
            for (ValuedInteger vi : list) {
                for (Integer old : include) {
                    sum += matrix_.getDistance(vi.i_, old);
                }
                count++;
                double avg = sum / count;


				if (avg <= params_.getThreshold()) {
                    include.add(vi.i_);
                } else {
                    //System.out.println("size: " + include.size() + " " + avg + " " + sum + " " + count);
                    break;
                }

            }

            for (int i : include) {
                c.add(i);
                clustered[i] = true;
            }

            if (c.size() == 0) {
                throw new RuntimeException();
            }
            clusters.add(c);
        }

        return clusters;
    }

	private ClusteringOutput granulateByAverageDistanceNoDeletion() {

		ClusteringOutput clustering = new ClusteringOutput();
        boolean[] clustered = new boolean[matrix_.size()];
        for (int x : order_) {
            // x is the core of the cluster
            if (clustered[x]) {
                continue;
            }

			GrainIndexCluster c = new GrainIndexCluster(x);
            clustered[x] = true;
            int n = 0;
            ArrayList<ValuedInteger> list = new ArrayList<ValuedInteger>();
            for (int y = 0; y < matrix_.size(); y++) {
                // evaluate if y belongs to cluster defined by x
                if (x == y) { // HERE IS DIFF
                    continue;
                }

                double d = matrix_.getDistance(x, y);
                list.add(new ValuedInteger(y, d));
            }

            double sum = 0;
            int count = 0;
            List<Integer> include = new ArrayList<Integer>();
            include.add(x);
            Collections.sort(list);
            for (ValuedInteger vi : list) {
                for (Integer old : include) {
                    sum += matrix_.getDistance(vi.i_, old);
                }
                count++;
                double avg = sum / count;


				if (avg <= params_.getThreshold()) {
                    include.add(vi.i_);
                } else {
                    //System.out.println("size: " + include.size() + " " + avg + " " + sum + " " + count);
                    break;
                }

            }

            for (int i : include) {
                c.add(i);
                clustered[i] = true;
            }

            if (c.size() == 0) {
                throw new RuntimeException();
            }
            clustering.add(c);
        }

        clustering = resolveOverlaps(clustering);

        return clustering;
    }

	private ClusteringOutput granulateByMinIncrement() {

		ClusteringOutput clusters = new ClusteringOutput();
        boolean[] clustered = new boolean[matrix_.size()];
        for (int x : order_) {
            // x is the core of the cluster
            if (clustered[x]) {
                continue;
            }

			GrainIndexCluster c = new GrainIndexCluster(x);
            clustered[x] = true;
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int y = 0; y < matrix_.size(); y++) {
                // evaluate if y belongs to cluster defined by x
                if (clustered[y] || x == y) {
                    continue;
                }

                double d = matrix_.getDistance(x, y);
                list.add(y);
            }

            double sum = 0;
            List<Integer> include = new ArrayList<Integer>();
            include.add(x);

            while (!list.isEmpty()) {
                // choose element with min. increase in cluster size
                double minSum = Double.MAX_VALUE;
                Integer minI = null;
                int minIndex = -1;
                for (int k = 0; k < list.size(); k++) {
                    int i = list.get(k);
                    double s = sum;

                    for (Integer old : include) {
                        s += matrix_.getDistance(i, old);
                    }
                    if (s < minSum) {
                        minSum = s;
                        minI = i;
                        minIndex = k;
                    }
                }

                double avg = minSum / include.size();

				if (avg <= params_.getThreshold()) {
                    sum = minSum;
                    list.remove(minIndex);
                    include.add(minI);
                } else {
                    break;
                }

            }

            for (int i : include) {
                c.add(i);
                clustered[i] = true;
            }

            if (c.size() == 0) {
                throw new RuntimeException();
            }
            clusters.add(c);
        }

        return clusters;
    }

    /*
     * Like a Voronoi diagram.
     */
	private ClusteringOutput resolveOverlaps(ClusteringOutput list) {

		for (IndexCluster cx : list) {
			GrainIndexCluster x = (GrainIndexCluster) cx;
			for (IndexCluster cy : list) {
				GrainIndexCluster y = (GrainIndexCluster) cy;
                if (x == y) {
                    continue;
                }

				List<Integer> ys = y.getMembers();
                int xCore = x.getCore();
                int yCore = y.getCore();
                List<Integer> removeX = null;
                List<Integer> removeY = null;
				for (int e : x.getMembers()) {
                    if (ys.contains(e)) {
                        float xd = matrix_.getDistance(e, xCore);
                        float yd = matrix_.getDistance(e, yCore);
                        if (xd < yd) {
                            if (null == removeX) {
                                removeX = new ArrayList<Integer>();
                            }
                            removeX.add(e);
                            //x.remove(e);
                        } else {
                            if (null == removeY) {
                                removeY = new ArrayList<Integer>();
                            }
                            removeY.add(e);
                            //y.remove(e);
                        }
                    }
                }

                if (null != removeX) {
                    for (int i : removeX) {
                        x.remove(i);
                    }
                }
                if (null != removeY) {
                    for (int i : removeY) {
                        y.remove(i);
                    }
                }
            }
        }

		ClusteringOutput clusters = new ClusteringOutput();
		for (IndexCluster c : list) {
            if (0 < c.size()) {
                clusters.add(c);
            }
        }
        return clusters;
    }

	private ClusteringOutput granulateByMinIncrementNoDeletion() {

		ClusteringOutput clusters = new ClusteringOutput();
        boolean[] clustered = new boolean[matrix_.size()];
        for (int x : order_) {
            // x is the core of the cluster
            if (clustered[x]) {
                continue;
            }

			GrainIndexCluster c = new GrainIndexCluster(x);
            clustered[x] = true;
            ArrayList<Integer> list = new ArrayList<Integer>();
            for (int y = 0; y < matrix_.size(); y++) {
                // evaluate if y belongs to cluster defined by x
                if (x == y) {
                    continue;
                }
                list.add(y);
            }

            double sum = 0;
            List<Integer> include = new ArrayList<Integer>();
            include.add(x);

            while (!list.isEmpty()) {
                // choose element with min. increase in cluster size
                double minSum = Double.MAX_VALUE;
                Integer minI = null;
                int minIndex = -1;
                for (int k = 0; k < list.size(); k++) {
                    int i = list.get(k);
                    double s = sum;

                    for (Integer old : include) {
                        s += matrix_.getDistance(i, old);
                    }
                    if (s < minSum) {
                        minSum = s;
                        minI = i;
                        minIndex = k;
                    }
                }

                double avg = minSum / include.size();

				if (avg <= params_.getThreshold()) {
                    sum = minSum;
                    list.remove(minIndex);
                    include.add(minI);
                } else {
                    break;
                }

            }

            for (int i : include) {
                c.add(i);
                clustered[i] = true;
            }

            clusters.add(c);
        }

        clusters = resolveOverlaps(clusters);

        return clusters;
    }
}
