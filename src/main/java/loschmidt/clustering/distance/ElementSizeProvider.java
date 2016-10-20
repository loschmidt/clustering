package loschmidt.clustering.distance;

/**
 *
 * @author Jan Stourac
 */
public interface ElementSizeProvider<T> {

	int size(T element);
}
