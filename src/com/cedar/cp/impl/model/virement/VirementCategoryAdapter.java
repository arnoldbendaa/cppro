// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementCategory;
import com.cedar.cp.dto.model.virement.VirementCategoryImpl;
import com.cedar.cp.dto.model.virement.VirementCategoryPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.virement.VirementCategoryEditorSessionImpl;
import com.cedar.cp.util.awt.QListModel;

public class VirementCategoryAdapter implements VirementCategory {

   private VirementCategoryImpl mEditorData;
   private VirementCategoryEditorSessionImpl mEditorSessionImpl;


   public VirementCategoryAdapter(VirementCategoryEditorSessionImpl e, VirementCategoryImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected VirementCategoryEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected VirementCategoryImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(VirementCategoryPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public long getTranLimit() {
      return this.mEditorData.getTranLimit();
   }

   public long getTotalLimitIn() {
      return this.mEditorData.getTotalLimitIn();
   }

   public long getTotalLimitOut() {
      return this.mEditorData.getTotalLimitOut();
   }

   public ModelRef getModelRef() {
      if(this.mEditorData.getModelRef() != null) {
         if(this.mEditorData.getModelRef().getNarrative() != null && this.mEditorData.getModelRef().getNarrative().length() > 0) {
            return this.mEditorData.getModelRef();
         } else {
            try {
               ModelRef e = ((BusinessProcessImpl)this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getModelEntityRef(this.mEditorData.getModelRef());
               this.mEditorData.setModelRef(e);
               return e;
            } catch (Exception var2) {
               throw new RuntimeException(var2.getMessage());
            }
         }
      } else {
         return null;
      }
   }

   public void setModelRef(ModelRef ref) {
      this.mEditorData.setModelRef(ref);
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setTranLimit(long p) {
      this.mEditorData.setTranLimit(p);
   }

   public void setTotalLimitIn(long p) {
      this.mEditorData.setTotalLimitIn(p);
   }

   public void setTotalLimitOut(long p) {
      this.mEditorData.setTotalLimitOut(p);
   }

   public QListModel getResponsibilityAreas() {
      return this.mEditorData.getResponsibilityAreas();
   }

   public QListModel getAccounts() {
      return this.mEditorData.getAccounts();
   }

   public double getTotalLimitInAsDouble() {
      return this.mEditorData.getTotalLimitInAsDouble();
   }

   public double getTotalLimitOutAsDouble() {
      return this.mEditorData.getTotalLimitOutAsDouble();
   }

   public double getTranLimitAsDouble() {
      return this.mEditorData.getTranLimitAsDouble();
   }
}
