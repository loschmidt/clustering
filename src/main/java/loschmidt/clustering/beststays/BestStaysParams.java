package loschmidt.clustering.beststays;

import java.util.Comparator;

/**
 *
 * @author Jan Stourac
 */
public class BestStaysParams {

	private double threshold_;
	private Comparator comparator_ = new ComparableComparator();

	public BestStaysParams setThreshold(double threshold) {
		this.threshold_ = threshold;
		return this;
	}

	public double getThreshold() {
		return threshold_;
	}

	public BestStaysParams setComparator(Comparator comparator) {
		this.comparator_ = comparator;
		return this;
	}

	public Comparator getComparator() {
		return comparator_;
	}

	private static class ComparableComparator implements Comparator<Comparable> {

		@Override
		public int compare(Comparable o1, Comparable o2) {
			return o1.compareTo(o2);
		}

	}
}
