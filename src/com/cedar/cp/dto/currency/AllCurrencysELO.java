// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.currency;

import com.cedar.cp.api.currency.CurrencyRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllCurrencysELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Currency", "Model"};
   private transient CurrencyRef mCurrencyEntityRef;
   private transient String mDescription;


   public AllCurrencysELO() {
      super(new String[]{"Currency", "Description"});
   }

   public void add(CurrencyRef eRefCurrency, String col1) {
      ArrayList l = new ArrayList();
      l.add(eRefCurrency);
      l.add(col1);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mCurrencyEntityRef = (CurrencyRef)l.get(index);
      this.mDescription = (String)l.get(var4++);
   }

   public CurrencyRef getCurrencyEntityRef() {
      return this.mCurrencyEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
