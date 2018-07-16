// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.impexp;

import com.cedar.cp.api.impexp.ImpExpHdrRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AllImpExpHdrsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ImpExpHdr", "ImpExpRow"};
   private transient ImpExpHdrRef mImpExpHdrEntityRef;
   private transient Timestamp mBatchTs;
   private transient int mFinanceCubeId;
   private transient int mBatchType;


   public AllImpExpHdrsELO() {
      super(new String[]{"ImpExpHdr", "BatchTs", "FinanceCubeId", "BatchType"});
   }

   public void add(ImpExpHdrRef eRefImpExpHdr, Timestamp col1, int col2, int col3) {
      ArrayList l = new ArrayList();
      l.add(eRefImpExpHdr);
      l.add(col1);
      l.add(new Integer(col2));
      l.add(new Integer(col3));
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
      this.mImpExpHdrEntityRef = (ImpExpHdrRef)l.get(index);
      this.mBatchTs = (Timestamp)l.get(var4++);
      this.mFinanceCubeId = ((Integer)l.get(var4++)).intValue();
      this.mBatchType = ((Integer)l.get(var4++)).intValue();
   }

   public ImpExpHdrRef getImpExpHdrEntityRef() {
      return this.mImpExpHdrEntityRef;
   }

   public Timestamp getBatchTs() {
      return this.mBatchTs;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public int getBatchType() {
      return this.mBatchType;
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
