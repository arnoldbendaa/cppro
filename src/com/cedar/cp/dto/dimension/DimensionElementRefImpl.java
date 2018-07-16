// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:49
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.dimension;

import com.cedar.cp.api.dimension.DimensionElementRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.dimension.DimensionElementCK;
import com.cedar.cp.dto.dimension.DimensionElementPK;
import java.io.Serializable;

public class DimensionElementRefImpl extends EntityRefImpl implements DimensionElementRef, Serializable {

   int mCreditDebit;


   public DimensionElementRefImpl(DimensionElementCK key, String narrative, int paramCreditDebit) {
      super(key, narrative);
      this.mCreditDebit = paramCreditDebit;
   }

   public DimensionElementRefImpl(DimensionElementPK key, String narrative, int paramCreditDebit) {
      super(key, narrative);
      this.mCreditDebit = paramCreditDebit;
   }

   public DimensionElementPK getDimensionElementPK() {
      return this.mKey instanceof DimensionElementCK?((DimensionElementCK)this.mKey).getDimensionElementPK():(DimensionElementPK)this.mKey;
   }

   public int getCreditDebit() {
      return this.mCreditDebit;
   }

   public String toString() {
      return this.getNarrative();
   }
}
