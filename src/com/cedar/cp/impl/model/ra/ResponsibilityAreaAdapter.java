// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.ra;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.ra.ResponsibilityArea;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaImpl;
import com.cedar.cp.dto.model.ra.ResponsibilityAreaPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.ra.ResponsibilityAreaEditorSessionImpl;

public class ResponsibilityAreaAdapter implements ResponsibilityArea {

   private ResponsibilityAreaImpl mEditorData;
   private ResponsibilityAreaEditorSessionImpl mEditorSessionImpl;


   public ResponsibilityAreaAdapter(ResponsibilityAreaEditorSessionImpl e, ResponsibilityAreaImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected ResponsibilityAreaEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected ResponsibilityAreaImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(ResponsibilityAreaPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public int getModelId() {
      return this.mEditorData.getModelId();
   }

   public int getStructureId() {
      return this.mEditorData.getStructureId();
   }

   public int getStructureElementId() {
      return this.mEditorData.getStructureElementId();
   }

   public int getVirementAuthStatus() {
      return this.mEditorData.getVirementAuthStatus();
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

   public StructureElementRef getOwningStructureElementRef() {
      return this.mEditorData.getOwningStructureElementRef();
   }

   public void setModelRef(ModelRef ref) {
      this.mEditorData.setModelRef(ref);
   }

   public void setOwningStructureElementRef(StructureElementRef ref) {
      this.mEditorData.setOwningStructureElementRef(ref);
   }

   public void setModelId(int p) {
      this.mEditorData.setModelId(p);
   }

   public void setStructureId(int p) {
      this.mEditorData.setStructureId(p);
   }

   public void setStructureElementId(int p) {
      this.mEditorData.setStructureElementId(p);
   }

   public void setVirementAuthStatus(int p) {
      this.mEditorData.setVirementAuthStatus(p);
   }
}
