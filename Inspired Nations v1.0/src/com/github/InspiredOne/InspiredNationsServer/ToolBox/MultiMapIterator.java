package com.github.InspiredOne.InspiredNationsServer.ToolBox;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class MultiMapIterator<T> implements Iterator<T> {

	private MultiMap <?, T> map;
	private Iterator<List<T>> HashIter;
	private Iterator<T> govIter;
	private T value;
	public MultiMapIterator(MultiMap<?, T> map) {
		this.map = map;
		HashIter = map.values().iterator();
		if(HashIter.hasNext()) {
			govIter = HashIter.next().iterator();
		}
	}

	@Override
	public boolean hasNext() {
		if (govIter.hasNext()) {
			return true;
		}
		else if(HashIter.hasNext()) {
			govIter = HashIter.next().iterator();
			return this.hasNext();
		}
		else {
			return false;
		}
	}

	@Override
	public T next() {
		if(!this.hasNext()) {
			throw new NoSuchElementException();
		}
		else {

			value = govIter.next();
			return value;
		}
	}

	@Override
	public void remove() {
		map.remove(value);
	}
}
