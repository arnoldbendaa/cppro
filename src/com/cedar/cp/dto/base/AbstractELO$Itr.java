// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:46
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.base;

import com.cedar.cp.dto.base.AbstractELO;
import java.util.Iterator;

class AbstractELO$Itr implements Iterator {

   private AbstractELO mElo;
   // $FF: synthetic field
   final AbstractELO this$0;


   public AbstractELO$Itr(AbstractELO var1, AbstractELO elo) {
      this.this$0 = var1;
      this.mElo = elo;
      this.mElo.reset();
   }

   public boolean hasNext() {
      return this.mElo.hasNext();
   }

   public Object next() {
      this.mElo.next();
      return this.mElo;
   }

   public void remove() {
      throw new UnsupportedOperationException();
   }
}
