package com.tentcoo.pineapple.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

public class PoolQueue implements Queue<Map<String, Object>> {
	/**
	 * 队列长度，实例化类的时候指定
	 */
	private int limit;

	Queue<Map<String, Object>> queue = new LinkedList<Map<String, Object>>();

	public PoolQueue(int limit) {
		this.limit = limit;
	}

	@Override
	public boolean addAll(Collection<? extends Map<String, Object>> arg0) {
		return queue.addAll(arg0);
	}

	@Override
	public void clear() {
		queue.clear();
	}

	public Object getObject(String key) {
		Iterator<Map<String, Object>> iterator = iterator();
		while (iterator.hasNext()) {
			Map<String, Object> next = iterator.next();
			Object value = next.get(key);
			if (value != null) {
				System.out.println(next);
				return value;
			}
		}
		return null;
	}

	public Object getTypeObject(String type) {
		Iterator<Map<String, Object>> iterator = iterator();
		Object obj = null;
		boolean isBreak = true;
		while (iterator.hasNext() && isBreak) {
			Map<String, Object> next = iterator.next();
			for (Entry<String, Object> entry : next.entrySet()) {
				obj = entry.getValue();
				if (obj != null) {
					isBreak = false;
					break;
				}
			}

		}
		return obj;
	}

	public ArrayList<Object> getTypeObjectList(String type) {
		ArrayList<Object> objects = new ArrayList<Object>();
		Iterator<Map<String, Object>> iterator = iterator();
		Object obj = null;
		while (iterator.hasNext()) {
			Map<String, Object> next = iterator.next();
			for (Entry<String, Object> entry : next.entrySet()) {
				obj = entry.getValue();
				if (obj != null) {
					objects.add(obj);
				}
			}

		}
		return objects;
	}

	@Override
	public boolean contains(Object object) {
		return queue.contains(object);
	}

	@Override
	public boolean containsAll(Collection<?> arg0) {
		return queue.containsAll(arg0);
	}

	@Override
	public boolean isEmpty() {
		return queue.size() == 0 ? true : false;
	}

	@Override
	public Iterator<Map<String, Object>> iterator() {
		return queue.iterator();
	}

	@Override
	public boolean remove(Object o) {
		return queue.remove(o);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return queue.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return queue.retainAll(c);
	}

	@Override
	public Object[] toArray() {
		return queue.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return queue.toArray(a);
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public boolean add(Map<String, Object> e) {
		return queue.add(e);
	}

	@Override
	public Map<String, Object> element() {
		return queue.element();
	}

	@Override
	public boolean offer(Map<String, Object> e) {
		if (queue.size() >= limit) {
			// 如果超出长度,入队时,先出队
			queue.poll();
		}
		return queue.offer(e);
	}

	@Override
	public Map<String, Object> peek() {
		return null;
	}

	@Override
	public Map<String, Object> poll() {
		return queue.poll();
	}

	@Override
	public Map<String, Object> remove() {
		return queue.remove();
	}

	/**
	 * 获取队列
	 * 
	 * @return
	 * @author SHANHY
	 * @date 2015年11月9日
	 */
	public Queue<Map<String, Object>> getQueue() {
		return queue;
	}

	@Override
	public String toString() {
		return "PoolQueue [queue=" + queue + "]";
	}
	
	
	
}
