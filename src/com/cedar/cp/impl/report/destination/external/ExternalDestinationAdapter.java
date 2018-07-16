// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.destination.external;

import com.cedar.cp.api.report.destination.external.ExternalDestination;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationImpl;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationPK;
import com.cedar.cp.impl.report.destination.external.ExternalDestinationEditorSessionImpl;
import java.util.List;

public class ExternalDestinationAdapter implements ExternalDestination {

   private ExternalDestinationImpl mEditorData;
   private ExternalDestinationEditorSessionImpl mEditorSessionImpl;


   public ExternalDestinationAdapter(ExternalDestinationEditorSessionImpl e, ExternalDestinationImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected ExternalDestinationEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected ExternalDestinationImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(ExternalDestinationPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public List getUserList() {
      return this.mEditorData.getUserList();
   }
}
