// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.SecurityAccessDef;
import com.cedar.cp.dto.model.SecurityAccessDefImpl;
import com.cedar.cp.dto.model.SecurityAccessDefPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.SecurityAccessDefEditorSessionImpl;

public class SecurityAccessDefAdapter implements SecurityAccessDef {

   private SecurityAccessDefImpl mEditorData;
   private SecurityAccessDefEditorSessionImpl mEditorSessionImpl;


   public SecurityAccessDefAdapter(SecurityAccessDefEditorSessionImpl e, SecurityAccessDefImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected SecurityAccessDefEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected SecurityAccessDefImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(SecurityAccessDefPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public int getAccessMode() {
      return this.mEditorData.getAccessMode();
   }

   public String getExpression() {
      return this.mEditorData.getExpression();
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

   public void setAccessMode(int p) {
      this.mEditorData.setAccessMode(p);
   }

   public void setExpression(String p) {
      this.mEditorData.setExpression(p);
   }
}
