package com.github.InspiredOne.InspiredNationsServer.ToolBox;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * My own implementation of a map that maps to a Set. If the key is not present, then
 * the map adds a Set with a single entry. Every subsequent addition to the key
 * is appened to the Set.
 * @author Jedidiah Phillips
 *
 * @param <T>	Key
 * @param <K>	Value
 */
public class MultiMap<T, K> implements Map<T, List<K>>, Serializable, Iterable<K> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5789101682525659411L;
	
	protected HashMap<T, List<K>> set = new HashMap<T, List<K>>();

	@Override
	public void clear() {
		set = new HashMap<T, List<K>>();
		
	}

	@Override
	public boolean containsKey(Object arg0) {
		return set.containsKey(arg0);
	}

	@Override
	public boolean containsValue(Object arg0) {
		boolean output = false;
		
		for(Iterator<List<K>> iter = set.values().iterator();iter.hasNext();) {
			List<K> searchColl = iter.next();
			for(Iterator<K> iter2 = searchColl.iterator(); iter2.hasNext();) {
				K value = iter2.next();
				if(value == arg0) {
					output = true;
					break;
				}
			}
		}
		
		return output;
	}

	@Override
	public Set<Entry<T, List<K>>> entrySet() {
		
		Set<Entry<T, List<K>>> output = new HashSet<Entry<T,List<K>>>();
		
		for(Iterator<T> iter1 = set.keySet().iterator(); iter1.hasNext();) {
			T key = iter1.next();
			for(Iterator<K> iter2 = set.get(key).iterator(); iter2.hasNext();) {
				K value = iter2.next();
				List<K> input = new ArrayList<K>();
				input.add(value);
				output.add(new AbstractMap.SimpleEntry<T,List<K>>(key, input));
			}
		}
		return output;
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public Set<T> keySet() {
		return set.keySet();
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public Collection<List<K>> values() {
		Collection<List<K>> values = new ArrayList<List<K>>();
		for(Iterator<T> iter1 = set.keySet().iterator(); iter1.hasNext();) {
			T key = iter1.next();
			values.add(set.get(key));
		}
		return values;
	}

	@Override
	public List<K> get(Object key) {
		return set.get(key);
	}

	@Override
	public List<K> put(T key, List<K> value) {
		return set.put(key, value);
	}
	
	public void putValue(T key, K value) {
		if(set.containsKey(key)) {
			set.get(key).add(value);
		}
		else {
			List<K> setval = new ArrayList<K>();
			setval.add(value);
			set.put(key, setval);
		}
	}

	@Override
	public List<K> remove(Object key) {
		return set.remove(key);
	}

	public K removeValue(Object value) {
		K valueRemoved = null;
		for(T key:this.keySet()) {
			for(K val:this.get(key)) {
				if(val.equals(value)) {
					List<K> temp = this.get(key);
					temp.remove(value);
					valueRemoved = val;
					this.put(key, temp);
				}
			}
		}
		return valueRemoved;
	}
	
	@Override
	public void putAll(Map<? extends T, ? extends List<K>> m) {
		for(Iterator<? extends T> iter = m.keySet().iterator(); iter.hasNext();) {
			T key = iter.next();
			set.put(key, m.get(key));
		}
	}

	@Override
	public Iterator<K> iterator() {
		return new MultiMapIterator<K>(this);
	}
}
