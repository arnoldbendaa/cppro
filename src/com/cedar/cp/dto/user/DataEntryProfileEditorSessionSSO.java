// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.dto.user.DataEntryProfileImpl;
import java.io.Serializable;

public class DataEntryProfileEditorSessionSSO implements Serializable {

   private DataEntryProfileImpl mEditorData;


   public DataEntryProfileEditorSessionSSO() {}

   public DataEntryProfileEditorSessionSSO(DataEntryProfileImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(DataEntryProfileImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public DataEntryProfileImpl getEditorData() {
      return this.mEditorData;
   }
}
