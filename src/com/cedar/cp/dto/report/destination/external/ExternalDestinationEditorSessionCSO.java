// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.destination.external;

import com.cedar.cp.dto.report.destination.external.ExternalDestinationImpl;
import java.io.Serializable;

public class ExternalDestinationEditorSessionCSO implements Serializable {

   private int mUserId;
   private ExternalDestinationImpl mEditorData;


   public ExternalDestinationEditorSessionCSO(int userId, ExternalDestinationImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public ExternalDestinationImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
