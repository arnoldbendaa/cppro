// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.destination.external;

import com.cedar.cp.dto.report.destination.external.ExternalDestinationImpl;
import java.io.Serializable;

public class ExternalDestinationEditorSessionSSO implements Serializable {

   private ExternalDestinationImpl mEditorData;


   public ExternalDestinationEditorSessionSSO() {}

   public ExternalDestinationEditorSessionSSO(ExternalDestinationImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(ExternalDestinationImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public ExternalDestinationImpl getEditorData() {
      return this.mEditorData;
   }
}
