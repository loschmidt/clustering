package loschmidt.clustering.util;

import java.util.ArrayList;
import java.util.List;

/*
 * Processes observed numerical values and computes their statistical
 * properties.
 */
public class DynamicDistribution {

	private final List<Double> values_ = new ArrayList<>();
	private double mostDistantNearestNeighbour_;

    public void add(double d) {
		values_.add(d);
	}

	public void setMostDistantNearestNeighbour(double mostDistantNearestNeighbour) {
		this.mostDistantNearestNeighbour_ = mostDistantNearestNeighbour;
	}

	public double getMostDistantNearestNeighbour() {
		return mostDistantNearestNeighbour_;
	}

	public List<Double> getValues() {
		return values_;
	}

	private double[] getArray() {
		double[] a = new double[values_.size()];
        for (int i = 0; i < a.length; i++) {
			a[i] = values_.get(i);
        }
        return a;
    }

	public double average() {
		return Utils.average(getArray());
    }

    public int greaterOrEqual(double value) {
        int count = 0;
		for (double d : values_) {
            if (value <= d) {
                count++;
            }
        }
        return count;
    }
}
