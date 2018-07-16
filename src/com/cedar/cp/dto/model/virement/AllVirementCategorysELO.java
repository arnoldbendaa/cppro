// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementCategoryRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllVirementCategorysELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"VirementCategory", "Model", "VirementLocation", "VirementAccount"};
   private transient VirementCategoryRef mVirementCategoryEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient String mDescription;


   public AllVirementCategorysELO() {
      super(new String[]{"VirementCategory", "Model", "Description"});
   }

   public void add(VirementCategoryRef eRefVirementCategory, ModelRef eRefModel, String col1) {
      ArrayList l = new ArrayList();
      l.add(eRefVirementCategory);
      l.add(eRefModel);
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
      this.mVirementCategoryEntityRef = (VirementCategoryRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
   }

   public VirementCategoryRef getVirementCategoryEntityRef() {
      return this.mVirementCategoryEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
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
