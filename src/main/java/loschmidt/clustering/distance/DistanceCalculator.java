package loschmidt.clustering.distance;

/**
 *
 * @author Jan Stourac
 */
public interface DistanceCalculator<T> {

	float calculate(T o1, T o2);
}
