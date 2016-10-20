package loschmidt.clustering.distance;

import loschmidt.clustering.util.DistanceMatrix;

/**
 *
 * @author Jan Stourac
 */
public class MemoryDistanceProvider<T> implements DistanceProvider<T> {

	private final Object[] data_;
	private final DistanceMatrix distances_;
	private final ElementSizeProvider<T> elementSizeProvider_;

	public MemoryDistanceProvider(T[] data, DistanceMatrix distances, int elementSize) {
		this(data, distances, new FixedElementSizeProvider<T>(elementSize));
	}

	public MemoryDistanceProvider(T[] data, DistanceMatrix distances, ElementSizeProvider<T> elementSizeProvider) {
		this.data_ = data;
		this.distances_ = distances;
		this.elementSizeProvider_ = elementSizeProvider;
	}

	@Override
	public float getDistance(int x, int y) {
		return (float) distances_.get(x, y);
	}

	@Override
	public int size() {
		return data_.length;
	}

	@Override
	public T get(int x) {
		return (T) data_[x];
	}

	@Override
	public int getElementSize(int x) {
		return elementSizeProvider_.size(get(x));
	}

}
