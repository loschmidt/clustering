package loschmidt.clustering.distance;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Jan Stourac
 */
public class RandomDistanceProvider implements DistanceProvider<DataPoint> {

	private final int details = 1000 * 1000;
	private final double coordinateMax = 100;

	private final List<DataPoint> points_ = new ArrayList<>();
	private DistanceCalculator<DataPoint> distanceCalculator_ = new EuclideanDistanceCalculator<>();

	public RandomDistanceProvider(Random random, int dimensions, int size) {
		for (int i = 0; i < size; i++) {
			double[] coords = new double[dimensions];
			for (int j = 0; j < dimensions; j++) {
				coords[j] = (double) random.nextInt(details) / details * coordinateMax;
			}
			points_.add(new DataPoint(coords));
		}
	}

	public void setDistanceCalculator(DistanceCalculator<DataPoint> distanceCalculator) {
		this.distanceCalculator_ = distanceCalculator;
	}

	public DistanceCalculator<DataPoint> getDistanceCalculator() {
		return distanceCalculator_;
	}

	@Override
	public DataPoint get(int i) {
		return points_.get(i);
	}

	@Override
	public float getDistance(int x, int y) {
		return distanceCalculator_.calculate(get(x), get(y));
	}

	@Override
	public int size() {
		return points_.size();
	}

	@Override
	public int getElementSize(int x) {
		return 1;
	}

}
