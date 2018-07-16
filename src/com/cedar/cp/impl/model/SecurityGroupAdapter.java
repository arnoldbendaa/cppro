// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.SecurityAccessDefRef;
import com.cedar.cp.api.model.SecurityGroup;
import com.cedar.cp.dto.model.SecurityGroupImpl;
import com.cedar.cp.dto.model.SecurityGroupPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.SecurityGroupEditorSessionImpl;
import java.util.List;

public class SecurityGroupAdapter implements SecurityGroup {

   private SecurityGroupImpl mEditorData;
   private SecurityGroupEditorSessionImpl mEditorSessionImpl;


   public SecurityGroupAdapter(SecurityGroupEditorSessionImpl e, SecurityGroupImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected SecurityGroupEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected SecurityGroupImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(SecurityGroupPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
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

   public List getUsers() {
      return this.mEditorData.getUsers();
   }

   public SecurityAccessDefRef getSecurityAccessDef() {
      return this.mEditorData.getSecurityAccessDef();
   }
}
