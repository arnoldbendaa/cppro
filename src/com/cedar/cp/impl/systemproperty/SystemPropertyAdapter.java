// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.systemproperty;

import com.cedar.cp.api.systemproperty.SystemProperty;
import com.cedar.cp.dto.systemproperty.SystemPropertyImpl;
import com.cedar.cp.dto.systemproperty.SystemPropertyPK;
import com.cedar.cp.impl.systemproperty.SystemPropertyEditorSessionImpl;

public class SystemPropertyAdapter implements SystemProperty {

   private SystemPropertyImpl mEditorData;
   private SystemPropertyEditorSessionImpl mEditorSessionImpl;


   public SystemPropertyAdapter(SystemPropertyEditorSessionImpl e, SystemPropertyImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected SystemPropertyEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected SystemPropertyImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(SystemPropertyPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getProperty() {
      return this.mEditorData.getProperty();
   }

   public String getValue() {
      return this.mEditorData.getValue();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public String getValidateExp() {
      return this.mEditorData.getValidateExp();
   }

   public String getValidateTxt() {
      return this.mEditorData.getValidateTxt();
   }

   public void setProperty(String p) {
      this.mEditorData.setProperty(p);
   }

   public void setValue(String p) {
      this.mEditorData.setValue(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setValidateExp(String p) {
      this.mEditorData.setValidateExp(p);
   }

   public void setValidateTxt(String p) {
      this.mEditorData.setValidateTxt(p);
   }
}
