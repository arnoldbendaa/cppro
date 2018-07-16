// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.awt;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import javax.swing.AbstractListModel;

public class QListModel extends AbstractListModel implements List {

   private List mList;


   public QListModel(List items) {
      this.mList = items;
   }

   public int getSize() {
      return this.mList.size();
   }

   public Object getElementAt(int index) {
      return this.mList.get(index);
   }

   public boolean add(Object o) {
      int index = this.mList.size();
      boolean added = this.mList.add(o);
      if(added) {
         this.fireIntervalAdded(this, index, index);
      }

      return added;
   }

   public void add(int index, Object o) {
      this.mList.add(index, o);
      this.fireIntervalAdded(this, index, index + 1);
   }

   public boolean remove(Object o) {
      int index = this.mList.indexOf(o);
      boolean removed = this.mList.remove(o);
      if(removed) {
         this.fireContentsChanged(this, index, index);
      }

      return removed;
   }

   public Object remove(int index) {
      Object old = this.mList.remove(index);
      this.fireIntervalRemoved(this, index, index);
      return old;
   }

   public boolean contains(Object o) {
      return this.mList.contains(o);
   }

   public int size() {
      return this.mList.size();
   }

   public void clear() {
      int size = this.size();
      this.mList.clear();
      this.fireIntervalRemoved(this, 0, size - 1);
   }

   public boolean isEmpty() {
      return this.mList.isEmpty();
   }

   public Object[] toArray() {
      return this.mList.toArray();
   }

   public Object get(int index) {
      return this.mList.get(index);
   }

   public int indexOf(Object o) {
      return this.mList.indexOf(o);
   }

   public int lastIndexOf(Object o) {
      return this.mList.lastIndexOf(o);
   }

   public boolean addAll(int index, Collection c) {
      boolean added = this.mList.addAll(index, c);
      if(added) {
         this.fireIntervalAdded(this, index, index + c.size() - 1);
      }

      return added;
   }

   public boolean addAll(Collection c) {
      boolean added = this.mList.addAll(c);
      if(added) {
         this.fireContentsChanged(this, 0, this.size() - 1);
      }

      return added;
   }

   public boolean containsAll(Collection c) {
      return this.mList.containsAll(c);
   }

   public boolean removeAll(Collection c) {
      boolean removed = this.mList.removeAll(c);
      if(removed) {
         this.fireContentsChanged(this, 0, this.size() - 1);
      }

      return removed;
   }

   public boolean retainAll(Collection c) {
      boolean changed = this.mList.retainAll(c);
      if(changed) {
         this.fireContentsChanged(this, 0, this.size() - 1);
      }

      return changed;
   }

   public Iterator iterator() {
      return this.mList.iterator();
   }

   public List subList(int fromIndex, int toIndex) {
      return this.mList.subList(fromIndex, toIndex);
   }

   public ListIterator listIterator() {
      return this.mList.listIterator();
   }

   public ListIterator listIterator(int index) {
      return this.mList.listIterator(index);
   }

   public Object set(int index, Object element) {
      Object o = this.mList.set(index, element);
      this.fireContentsChanged(this, index, index);
      return o;
   }

   public Object[] toArray(Object[] a) {
      return this.mList.toArray(a);
   }

   public List getList() {
      return this.mList;
   }
}
