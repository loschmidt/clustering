package loschmidt.clustering.hierarchical.murtagh;

import loschmidt.clustering.hierarchical.Linkage;
import loschmidt.clustering.hierarchical.SatisfyReducibility;
import loschmidt.clustering.hierarchical.SingleLinkage;

/**
 *
 * @author Jan Stourac
 */
public class MurtaghParams {

	private double threshold_ = 0;
	private Linkage linkage_ = new SingleLinkage();
	private Integer distanceMatrixThreshold_ = null; // matrix is generated when number of clusters reaches this or lower value

	public MurtaghParams setThreshold(double threshold) {
		this.threshold_ = threshold;
		return this;
	}

	public double getThreshold() {
		return threshold_;
	}

	public <T extends Linkage & SatisfyReducibility> MurtaghParams setLinkage(T linkage) {
		this.linkage_ = linkage;
		return this;
	}

	public Linkage getLinkage() {
		return linkage_;
	}

	public MurtaghParams setDistanceMatrixThreshold(Integer distanceMatrixThreshold) {
		this.distanceMatrixThreshold_ = distanceMatrixThreshold;
		return this;
	}

	public Integer getDistanceMatrixThreshold() {
		return distanceMatrixThreshold_;
	}

}
