// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.destination.internal;

import com.cedar.cp.dto.report.destination.internal.InternalDestinationImpl;
import java.io.Serializable;

public class InternalDestinationEditorSessionSSO implements Serializable {

   private InternalDestinationImpl mEditorData;


   public InternalDestinationEditorSessionSSO() {}

   public InternalDestinationEditorSessionSSO(InternalDestinationImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(InternalDestinationImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public InternalDestinationImpl getEditorData() {
      return this.mEditorData;
   }
}
