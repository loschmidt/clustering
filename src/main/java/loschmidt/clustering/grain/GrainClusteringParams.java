package loschmidt.clustering.grain;

import java.util.Comparator;

public class GrainClusteringParams {

    public enum Method {

        MAX_DIST, AVG_DIST, AVG_DIST_INCREMENTAL, AVG_DIST_INCREMENTAL_NO_DELETION, AVG_DIST_NO_DELETION
    };
    private Method method_ = Method.AVG_DIST_INCREMENTAL;
	private double threshold_ = -100;
	private Comparator elementComparator_;

    public Method getMethod() {
        return method_;
    }

    public void setMethod(Method method) {
        this.method_ = method;
    }

    public double getThreshold() {
        return threshold_;
    }

    public void setThreshold(double threshold) {
        this.threshold_ = threshold;
	}

	public void setOrderComparator(Comparator comparator) {
		this.elementComparator_ = comparator;
	}

	public Comparator getOrderComparator() {
		return elementComparator_;
	}
}
