// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.base;

import com.cedar.cp.dto.base.EntityListImpl;
import java.io.Serializable;
import java.util.Iterator;

public class AbstractELO extends EntityListImpl implements Serializable {

   protected transient Iterator mIterator;


   public AbstractELO(String[] headings) {
      super(headings, new Object[0][headings.length]);
   }

   public int size() {
      return this.mCollection.size();
   }

   public void reset() {
      this.mIterator = this.mCollection.iterator();
   }

   public boolean hasNext() {
      if(this.mIterator == null) {
         this.reset();
      }

      return this.mIterator.hasNext();
   }
}
