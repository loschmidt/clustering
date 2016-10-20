package loschmidt.clustering.distance;

/**
 *
 * @author Jan Stourac
 */
public class EuclideanDistanceCalculator<T extends HasCoords> implements DistanceCalculator<T> {

	@Override
	public float calculate(T o1, T o2) {
		double[] a = o1.getCoords();
		double[] b = o2.getCoords();

		if (a.length != b.length) {
			throw new IllegalStateException("Dimension of the elements is not the same!");
		}

		double sum = 0;
		for (int i = 0; i < a.length; i++) {
			double diff = a[i] - b[i];
			sum += diff * diff;
		}

		return (float) Math.sqrt(sum);
	}

}
