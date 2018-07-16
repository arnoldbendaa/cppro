// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.extsys;

import com.cedar.cp.api.extsys.ExternalSystem;
import com.cedar.cp.dto.extsys.ExternalSystemImpl;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.impl.extsys.ExternalSystemEditorSessionImpl;
import java.util.List;
import java.util.Map;

public class ExternalSystemAdapter implements ExternalSystem {

   private ExternalSystemImpl mEditorData;
   private ExternalSystemEditorSessionImpl mEditorSessionImpl;


   public ExternalSystemAdapter(ExternalSystemEditorSessionImpl e, ExternalSystemImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected ExternalSystemEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected ExternalSystemImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(ExternalSystemPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public int getSystemType() {
      return this.mEditorData.getSystemType();
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public String getLocation() {
      return this.mEditorData.getLocation();
   }

   public String getConnectorClass() {
      return this.mEditorData.getConnectorClass();
   }

   public String getImportSource() {
      return this.mEditorData.getImportSource();
   }

   public String getExportTarget() {
      return this.mEditorData.getExportTarget();
   }

   public boolean isEnabled() {
      return this.mEditorData.isEnabled();
   }

   public void setSystemType(int p) {
      this.mEditorData.setSystemType(p);
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setLocation(String p) {
      this.mEditorData.setLocation(p);
   }

   public void setConnectorClass(String p) {
      this.mEditorData.setConnectorClass(p);
   }

   public void setImportSource(String p) {
      this.mEditorData.setImportSource(p);
   }

   public void setExportTarget(String p) {
      this.mEditorData.setExportTarget(p);
   }

   public void setEnabled(boolean p) {
      this.mEditorData.setEnabled(p);
   }

   public Map<String, String> getProperties() {
      return this.mEditorData.getProperties();
   }

   public List getAvailableColumnList(int subsystemId) {
      return this.mEditorData.getAvailableColumnList(subsystemId);
   }

   public List getSelectedColumnList(int subsystemId) {
      return this.mEditorData.getSelectedColumnList(subsystemId);
   }
}
