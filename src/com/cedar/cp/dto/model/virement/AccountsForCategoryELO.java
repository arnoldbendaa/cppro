// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementAccountRef;
import com.cedar.cp.api.model.virement.VirementCategoryRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountsForCategoryELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"VirementAccount", "VirementCategory", "Model", "StructureElement"};
   private transient VirementAccountRef mVirementAccountEntityRef;
   private transient VirementCategoryRef mVirementCategoryEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient StructureElementRef mStructureElementEntityRef;
   private transient String mVisId;
   private transient String mDescription;
   private transient long mTranLimit;
   private transient long mTotalLimitIn;
   private transient long mTotalLimitOut;
   private transient boolean mInFlag;
   private transient boolean mOutFlag;


   public AccountsForCategoryELO() {
      super(new String[]{"VirementAccount", "VirementCategory", "Model", "StructureElement", "VisId", "Description", "TranLimit", "TotalLimitIn", "TotalLimitOut", "InFlag", "OutFlag"});
   }

   public void add(VirementAccountRef eRefVirementAccount, VirementCategoryRef eRefVirementCategory, ModelRef eRefModel, StructureElementRef eRefStructureElement, String col1, String col2, long col3, long col4, long col5, boolean col6, boolean col7) {
      ArrayList l = new ArrayList();
      l.add(eRefVirementAccount);
      l.add(eRefVirementCategory);
      l.add(eRefModel);
      l.add(eRefStructureElement);
      l.add(col1);
      l.add(col2);
      l.add(new Long(col3));
      l.add(new Long(col4));
      l.add(new Long(col5));
      l.add(new Boolean(col6));
      l.add(new Boolean(col7));
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
      this.mVirementAccountEntityRef = (VirementAccountRef)l.get(index);
      this.mVirementCategoryEntityRef = (VirementCategoryRef)l.get(var4++);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mStructureElementEntityRef = (StructureElementRef)l.get(var4++);
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mTranLimit = ((Long)l.get(var4++)).longValue();
      this.mTotalLimitIn = ((Long)l.get(var4++)).longValue();
      this.mTotalLimitOut = ((Long)l.get(var4++)).longValue();
      this.mInFlag = ((Boolean)l.get(var4++)).booleanValue();
      this.mOutFlag = ((Boolean)l.get(var4++)).booleanValue();
   }

   public VirementAccountRef getVirementAccountEntityRef() {
      return this.mVirementAccountEntityRef;
   }

   public VirementCategoryRef getVirementCategoryEntityRef() {
      return this.mVirementCategoryEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public StructureElementRef getStructureElementEntityRef() {
      return this.mStructureElementEntityRef;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public long getTranLimit() {
      return this.mTranLimit;
   }

   public long getTotalLimitIn() {
      return this.mTotalLimitIn;
   }

   public long getTotalLimitOut() {
      return this.mTotalLimitOut;
   }

   public boolean getInFlag() {
      return this.mInFlag;
   }

   public boolean getOutFlag() {
      return this.mOutFlag;
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
