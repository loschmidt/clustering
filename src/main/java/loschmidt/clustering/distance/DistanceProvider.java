package loschmidt.clustering.distance;

/**
 *
 * @author Jan Stourac
 */
public interface DistanceProvider<T> {

	float getDistance(int x, int y);

	int size();

	T get(int x);

	int getElementSize(int x);
}
