// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementRequestGroupRef;
import com.cedar.cp.api.model.virement.VirementRequestRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllVirementRequestGroupsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"VirementRequestGroup", "VirementRequest", "Model", "VirementRequestLine", "VirementLineSpread"};
   private transient VirementRequestGroupRef mVirementRequestGroupEntityRef;
   private transient VirementRequestRef mVirementRequestEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mRequestGroupId;
   private transient int mRequestId;
   private transient String mNotes;


   public AllVirementRequestGroupsELO() {
      super(new String[]{"VirementRequestGroup", "VirementRequest", "Model", "RequestGroupId", "RequestId", "Notes"});
   }

   public void add(VirementRequestGroupRef eRefVirementRequestGroup, VirementRequestRef eRefVirementRequest, ModelRef eRefModel, int col1, int col2, String col3) {
      ArrayList l = new ArrayList();
      l.add(eRefVirementRequestGroup);
      l.add(eRefVirementRequest);
      l.add(eRefModel);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(col3);
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
      this.mVirementRequestGroupEntityRef = (VirementRequestGroupRef)l.get(index);
      this.mVirementRequestEntityRef = (VirementRequestRef)l.get(var4++);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mRequestGroupId = ((Integer)l.get(var4++)).intValue();
      this.mRequestId = ((Integer)l.get(var4++)).intValue();
      this.mNotes = (String)l.get(var4++);
   }

   public VirementRequestGroupRef getVirementRequestGroupEntityRef() {
      return this.mVirementRequestGroupEntityRef;
   }

   public VirementRequestRef getVirementRequestEntityRef() {
      return this.mVirementRequestEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getRequestGroupId() {
      return this.mRequestGroupId;
   }

   public int getRequestId() {
      return this.mRequestId;
   }

   public String getNotes() {
      return this.mNotes;
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
