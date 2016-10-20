package loschmidt.clustering.distance;

/**
 *
 * @author Jan Stourac
 */
public class DataPoint implements HasCoords, HasSize {

	private final double[] coords_;
	private final int size_;

	public DataPoint(double[] coords) {
		this(coords, 1);
	}

	public DataPoint(double[] coords, int size) {
		this.coords_ = coords;
		this.size_ = size;
	}

	@Override
	public double[] getCoords() {
		return coords_;
	}

	@Override
	public int size() {
		return size_;
	}

}
