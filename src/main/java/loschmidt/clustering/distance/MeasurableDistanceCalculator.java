package loschmidt.clustering.distance;

/**
 *
 * @author Jan Stourac
 */
public class MeasurableDistanceCalculator<T extends Measurable<T>> implements DistanceCalculator<T> {

	@Override
	public float calculate(T o1, T o2) {
		return o1.getDistance(o2);
	}

}
