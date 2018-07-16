// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.base;

import com.cedar.cp.dto.base.AbstractELO$Itr;
import com.cedar.cp.dto.base.EntityListImpl;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractELO extends EntityListImpl implements Serializable, Collection {

   protected transient Iterator mIterator;


   protected AbstractELO() {}

   public AbstractELO(String[] headings) {
      super(headings, new Object[0][headings.length]);
   }

   public void reset() {
      this.mIterator = this.mCollection.iterator();
      this.mCurrRowIndex = -1;
   }

   public boolean hasNext() {
      if(this.mIterator == null) {
         this.reset();
      }

      return this.mIterator.hasNext();
   }

   public abstract void next();

   public int size() {
      return this.mCollection.size();
   }

   public boolean isEmpty() {
      return this.mCollection.size() < 1;
   }

   public boolean contains(Object o) {
      return this.mCollection.contains(o);
   }

   public Iterator iterator() {
      return new AbstractELO$Itr(this, this);
   }

   public Object[] toArray() {
      return this.mCollection.toArray();
   }

   public Object[] toArray(Object[] a) {
      return this.mCollection.toArray(a);
   }

   public boolean add(Object o) {
      throw new UnsupportedOperationException();
   }

   public boolean remove(Object o) {
      throw new UnsupportedOperationException();
   }

   public boolean containsAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean addAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean removeAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public boolean retainAll(Collection c) {
      throw new UnsupportedOperationException();
   }

   public void clear() {
      throw new UnsupportedOperationException();
   }
}
