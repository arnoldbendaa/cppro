// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.udeflookup;

import com.cedar.cp.dto.udeflookup.UdefLookupImpl;
import java.io.Serializable;

public class UdefLookupEditorSessionSSO implements Serializable {

   private UdefLookupImpl mEditorData;


   public UdefLookupEditorSessionSSO() {}

   public UdefLookupEditorSessionSSO(UdefLookupImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(UdefLookupImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public UdefLookupImpl getEditorData() {
      return this.mEditorData;
   }
}
