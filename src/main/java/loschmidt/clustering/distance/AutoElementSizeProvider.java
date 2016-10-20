package loschmidt.clustering.distance;

/**
 *
 * @author Jan Stourac
 */
public class AutoElementSizeProvider<T extends HasSize> implements ElementSizeProvider<T> {

	@Override
	public int size(T element) {
		return element.size();
	}

}
