package loschmidt.clustering.distance;

/**
 *
 * @author Jan Stourac
 */
public class ExtendedDistanceProvider<T> implements DistanceProvider<T> {

	private final DistanceProvider<T> original_;
	private final DistanceCalculator<T> distanceCalculator_;
	private final ElementSizeProvider<T> elementSizeProvider_;
	private T[] data_;
	private boolean useOriginalData_ = true;

	public ExtendedDistanceProvider(DistanceProvider<T> original, DistanceCalculator<T> distanceCalculator, int elementSize) {
		this(original, distanceCalculator, new FixedElementSizeProvider<T>(elementSize));
	}

	public ExtendedDistanceProvider(DistanceProvider<T> original, DistanceCalculator<T> distanceCalculator, ElementSizeProvider<T> elementSizeProvider) {
		this.original_ = original;
		this.distanceCalculator_ = distanceCalculator;
		this.elementSizeProvider_ = elementSizeProvider;
	}

	public void setExtendingData(T[] data) {
		this.data_ = data;
	}

	public DistanceProvider<T> getOriginalData() {
		return original_;
	}

	public DistanceCalculator<T> getDistanceCalculator() {
		return distanceCalculator_;
	}

	public ElementSizeProvider<T> getElementSizeProvider() {
		return elementSizeProvider_;
	}

	/**
	 * Sets whether it should use data from original distance provider.	* This
	 * is useful especially when original matrix has e.g. pre-calculated
	 * distances or element sizes.
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
		if (useOriginalData_ && isFromOriginal(x) && isFromOriginal(y)) {
			return original_.getDistance(x, y);
		}

		return distanceCalculator_.calculate(get(x), get(y));
	}

	@Override
	public int size() {
		return original_.size() + data_.length;
	}

	@Override
	public T get(int x) {
		return isFromOriginal(x) ? original_.get(x) : data_[x - original_.size()];
	}

	@Override
	public int getElementSize(int x) {
		return useOriginalData_ && isFromOriginal(x) ? original_.getElementSize(x) : elementSizeProvider_.size(get(x));
	}

	private boolean isFromOriginal(int x) {
		return x < original_.size();
	}

}
