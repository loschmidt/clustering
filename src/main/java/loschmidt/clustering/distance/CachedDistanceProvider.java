package loschmidt.clustering.distance;

import loschmidt.clustering.util.DistanceMatrix;

/**
 *
 * @author Jan Stourac
 */
public class CachedDistanceProvider<T> extends SimpleDistanceProvider<T> {

	private DistanceMatrix cache_;

	public CachedDistanceProvider(DistanceCalculator<T> distanceCalculator, int elementSize) {
		this(distanceCalculator, new FixedElementSizeProvider<T>(elementSize));
	}

	public CachedDistanceProvider(DistanceCalculator<T> distanceCalculator, ElementSizeProvider<T> elementSizeProvider) {
		super(distanceCalculator, elementSizeProvider);
	}

	@Override
	public void setData(T[] data) {
		super.setData(data);
		cache_ = new DistanceMatrix(data.length);
	}

	@Override
	public float getDistance(int x, int y) {
		float dist = (float) cache_.get(x, y);

		if (dist == DistanceMatrix.DEFAULT_VALUE) {
			dist = super.getDistance(x, y);
			cache_.set(x, y, dist);
		}

		return dist;
	}

}
