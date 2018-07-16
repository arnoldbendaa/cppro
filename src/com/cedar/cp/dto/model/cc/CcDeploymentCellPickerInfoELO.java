// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.cc;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.cc.CcDeploymentRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CcDeploymentCellPickerInfoELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"CcDeployment", "Model", "CcDeploymentLine", "CcDeploymentEntry", "CcDeploymentDataType", "CcMappingLine", "CcMappingEntry"};
   private transient CcDeploymentRef mCcDeploymentEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mModelId;


   public CcDeploymentCellPickerInfoELO() {
      super(new String[]{"CcDeployment", "Model", "ModelId"});
   }

   public void add(CcDeploymentRef eRefCcDeployment, ModelRef eRefModel, int col1) {
      ArrayList l = new ArrayList();
      l.add(eRefCcDeployment);
      l.add(eRefModel);
      l.add(new Integer(col1));
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
      this.mCcDeploymentEntityRef = (CcDeploymentRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mModelId = ((Integer)l.get(var4++)).intValue();
   }

   public CcDeploymentRef getCcDeploymentEntityRef() {
      return this.mCcDeploymentEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getModelId() {
      return this.mModelId;
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
