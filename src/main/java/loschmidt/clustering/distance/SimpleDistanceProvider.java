package loschmidt.clustering.distance;

import java.util.Iterator;
import loschmidt.clustering.util.ArrayIterator;

/**
 *
 * @author Jan Stourac
 */
public class SimpleDistanceProvider<T> implements DistanceProvider<T>, Iterable<T> {

	private final DistanceCalculator<T> distanceCalculator_;
	private final ElementSizeProvider<T> elementSizeProvider_;
	private Object[] data_;

	public SimpleDistanceProvider(DistanceCalculator<T> distanceCalculator, int elementSize) {
		this(distanceCalculator, new FixedElementSizeProvider<T>(elementSize));
	}

	public SimpleDistanceProvider(DistanceCalculator<T> distanceCalculator, ElementSizeProvider<T> elementSizeProvider) {
		this.distanceCalculator_ = distanceCalculator;
		this.elementSizeProvider_ = elementSizeProvider;
	}

	public void setData(T[] data) {
		this.data_ = data;
	}

	public DistanceCalculator<T> getDistanceCalculator() {
		return distanceCalculator_;
	}

	@Override
	public T get(int index) {
		return (T) data_[index];
	}

	@Override
	public int size() {
		return data_.length;
	}

	@Override
	public float getDistance(int x, int y) {
		return distanceCalculator_.calculate(get(x), get(y));
	}

	@Override
	public Iterator<T> iterator() {
		return new ArrayIterator<>(data_);
	}

	@Override
	public int getElementSize(int x) {
		return elementSizeProvider_.size(get(x));
	}

}
