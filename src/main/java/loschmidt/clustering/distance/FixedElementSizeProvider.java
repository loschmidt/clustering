package loschmidt.clustering.distance;

/**
 *
 * @author Jan Stourac
 */
public class FixedElementSizeProvider<T> implements ElementSizeProvider<T> {

	public static final FixedElementSizeProvider SIZE_ONE = new FixedElementSizeProvider(1);

	private final int elementSize_;

	public FixedElementSizeProvider(int elementSize) {
		this.elementSize_ = elementSize;
	}

	@Override
	public int size(T element) {
		return elementSize_;
	}

}
