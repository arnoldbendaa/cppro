// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementCategoryRef;
import com.cedar.cp.api.model.virement.VirementLocationRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocationsForCategoryELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"VirementLocation", "VirementCategory", "Model", "StructureElement"};
   private transient VirementLocationRef mVirementLocationEntityRef;
   private transient VirementCategoryRef mVirementCategoryEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient StructureElementRef mStructureElementEntityRef;
   private transient String mVisId;
   private transient String mDescription;


   public LocationsForCategoryELO() {
      super(new String[]{"VirementLocation", "VirementCategory", "Model", "StructureElement", "VisId", "Description"});
   }

   public void add(VirementLocationRef eRefVirementLocation, VirementCategoryRef eRefVirementCategory, ModelRef eRefModel, StructureElementRef eRefStructureElement, String col1, String col2) {
      ArrayList l = new ArrayList();
      l.add(eRefVirementLocation);
      l.add(eRefVirementCategory);
      l.add(eRefModel);
      l.add(eRefStructureElement);
      l.add(col1);
      l.add(col2);
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
      this.mVirementLocationEntityRef = (VirementLocationRef)l.get(index);
      this.mVirementCategoryEntityRef = (VirementCategoryRef)l.get(var4++);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mStructureElementEntityRef = (StructureElementRef)l.get(var4++);
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
   }

   public VirementLocationRef getVirementLocationEntityRef() {
      return this.mVirementLocationEntityRef;
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

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
