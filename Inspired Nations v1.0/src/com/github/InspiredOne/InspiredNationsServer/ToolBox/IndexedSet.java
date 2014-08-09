package com.github.InspiredOne.InspiredNationsServer.ToolBox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;


/**
 * Will not put an item in the array that is already in the array. Simple as that.
 * @author Jedidiah Phillips
 *
 * @param <E>
 */
public class IndexedSet<E> extends ArrayList<E> implements Collection<E>, Iterable<E>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4405715470001224827L;
	
	@Override
	public boolean add(E e) {
		if(this.contains(e)) {
			return true;
		}
		else {
			this.add(0, e);
			return false;
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		boolean output = false;
		for(E e:c) {
			if(!this.contains(e)) {
				this.add(e);
				output = true;
			}
		}
		return output;
	}

}
