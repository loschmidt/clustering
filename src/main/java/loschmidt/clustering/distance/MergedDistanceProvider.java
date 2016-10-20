package loschmidt.clustering.distance;

/**
 *
 * @author Jan Stourac
 */
public class MergedDistanceProvider<T> implements DistanceProvider<T> {

	private final DistanceProvider<T> first_;
	private final DistanceProvider<T> second_;
	private final DistanceCalculator<T> distanceCalculator_;
	private boolean useOriginalData_ = true;

	public MergedDistanceProvider(DistanceProvider<T> first, DistanceProvider<T> second, DistanceCalculator<T> distanceCalculator) {
		this.first_ = first;
		this.second_ = second;
		this.distanceCalculator_ = distanceCalculator;
	}

	public DistanceProvider<T> getFirstProvider() {
		return first_;
	}

	public DistanceProvider<T> getSecondProvider() {
		return second_;
	}

	public DistanceCalculator<T> getDistanceCalculator() {
		return distanceCalculator_;
	}

	/**
	 * Sets whether it should use data from original distance provider.	 * This is useful especially when original matrix has e.g. pre-calculated
	 * distances
	 * or element sizes.
	 *
	 * @param useOriginalData
	 */
	public void setUseOriginalMeta(boolean useOriginalData) {
		this.useOriginalData_ = useOriginalData;
	}

	public boolean getUseOriginalMeta() {
		return useOriginalData_;
	}

	@Override
	public float getDistance(int x, int y) {
		if (useOriginalData_ && isFromFirst(x) && isFromFirst(y)) {
			return first_.getDistance(x, y);
		}

		if (useOriginalData_ && !isFromFirst(x) && !isFromFirst(y)) {
			return second_.getDistance(x, y);
		}

		return distanceCalculator_.calculate(get(x), get(y));
	}

	@Override
	public int size() {
		return first_.size() + second_.size();
	}

	@Override
	public T get(int x) {
		return isFromFirst(x) ? first_.get(x) : second_.get(x);
	}

	@Override
	public int getElementSize(int x) {
		return isFromFirst(x) ? first_.getElementSize(x) : second_.getElementSize(x);
	}

	private boolean isFromFirst(int x) {
		return x < first_.size();
	}

}
