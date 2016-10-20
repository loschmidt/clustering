package loschmidt.clustering.util;

import java.util.Iterator;

/**
 *
 * @author Jan Stourac
 */
public class ArrayIterator<T> implements Iterator<T> {

	private final Object[] array_;
	private int index = 0;

	public ArrayIterator(Object[] array) {
		this.array_ = array;
	}

	@Override
	public boolean hasNext() {
		return index < array_.length;
	}

	@Override
	public T next() {
		return (T) array_[index++];
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Removing elements is not supported.");
	}

}
