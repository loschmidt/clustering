package loschmidt.clustering.distance;

/**
 *
 * @author Jan Stourac
 */
public class MeasurableDistanceProvider<T extends Measurable<T>> extends SimpleDistanceProvider<T> {

	public MeasurableDistanceProvider(int elementSize) {
		super(new MeasurableDistanceCalculator<T>(), elementSize);
	}

	public MeasurableDistanceProvider(ElementSizeProvider<T> elementSizeProvider) {
		super(new MeasurableDistanceCalculator<T>(), elementSizeProvider);
	}

}
