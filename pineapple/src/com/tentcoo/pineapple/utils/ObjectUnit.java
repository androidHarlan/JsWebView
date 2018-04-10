package com.tentcoo.pineapple.utils;

import java.util.ArrayList;
import java.util.List;
 
public class ObjectUnit<T> {
    private Class<T> type;
    private List<T> items = new ArrayList<T>();
    private List<Boolean> checkedOut = new ArrayList<Boolean>();
    private int semaphore;
    private int maxQueueSize;
    public ObjectUnit(Class<T> type,int maxQueueSize) {
       this.type = type;
       this.maxQueueSize=maxQueueSize;
    }
 
    public synchronized T addItem() {
       T obj;
       try {
    	   //创建对象
           obj = type.newInstance();
       } catch (Exception e) {
           throw new RuntimeException(e);
       }
       if(items.size()<maxQueueSize){
    	   //添加对象于池中
    	   items.add(obj);
           checkedOut.add(false);
       }
       return obj;
    }
 
    public synchronized T checkOut() {
       if (semaphore < items.size()) {
           semaphore++;
           return getItem();
       } else
           return addItem();
    }
 
    public synchronized void checkIn(T x) {
       if (releaseItem(x))
           semaphore--;
    }
 
    private synchronized T getItem() {
       for (int index = 0; index < checkedOut.size(); index++)
           if (!checkedOut.get(index)) {
              checkedOut.set(index, true);
              return items.get(index);
           }
       return null;
    }
 
    private synchronized boolean releaseItem(T item) {
       int index = items.indexOf(item);
       if (index == -1)
           return false; // Not in the list
       if (checkedOut.get(index)) {
           checkedOut.set(index, false);
           return true;
       }
       return false;
    }
}