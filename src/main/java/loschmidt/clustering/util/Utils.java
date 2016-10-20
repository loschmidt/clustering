package loschmidt.clustering.util;

import loschmidt.clustering.ClusteringAlgorithm;
import loschmidt.clustering.ClusteringException;
import loschmidt.clustering.distance.DistanceProvider;

/**
 *
 * @author Jan Stourac
 */
public class Utils {

	public static double average(double[] a) {
		double sum = 0;
		int count = 0;
		for (double d : a) {
			sum += d;
			count++;
		}
		return sum / count;
	}

	private static double standardDeviation(double[] a) {
		double sum = 0;
		int count = 0;
		double average = average(a);
		for (double d : a) {
			sum += (d - average) * (d - average);
			count++;
		}
		return Math.sqrt(sum / count);
	}

	private static double max(double[] a) {
		double max = Double.NEGATIVE_INFINITY;
		for (double d : a) {
			if (max < d) {
				max = d;
			}
		}
		return max;
	}

	public static <T extends ClusteringAlgorithm<U>, U> T newInstance(Class<T> clazz, DistanceProvider matrix, U params) throws ClusteringException {
		try {
			return clazz.getDeclaredConstructor(DistanceProvider.class, params.getClass()).newInstance(matrix, params);
		} catch (Exception ex) {
			throw new ClusteringException("Unable to initialize '" + clazz.getSimpleName() + "'", ex);
		}
	}

	private Utils() {
		throw new AssertionError();
	}
}
