package com.github.InspiredOne.InspiredNationsServer.ToolBox;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import com.github.InspiredOne.InspiredNationsServer.Debug;

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
		Debug.print("inside hasNext of MultiMapIterator 1");
		if(govIter == null) {
			return false;
		}
		else if (govIter.hasNext()) {
			Debug.print("inside hasNext of MultiMapIterator 2");
			return true;
		}
		else if(HashIter.hasNext()) {
			Debug.print("inside hasNext of MultiMapIterator 3");
			govIter = HashIter.next().iterator();
			Debug.print("inside hasNext of MultiMapIterator 4");
			return this.hasNext();
		}
		else {
			Debug.print("inside hasNext of MultiMapIterator 5");
			return false;
		}
	}

	@Override
	public T next() {
		Debug.print("inside next() of MultiMapIterator 3");
		if(!this.hasNext()) {
			Debug.print("inside next() of MultiMapIterator 2");
			throw new NoSuchElementException();
		}
		else {
			Debug.print("inside next() of MultiMapIterator 4");
			value = govIter.next();
			Debug.print("inside next() of MultiMapIterator 5");
			return value;
		}
	}

	@Override
	public void remove() {
		map.remove(value);
	}
}
