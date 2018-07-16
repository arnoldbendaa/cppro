// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.udeflookup;

import com.cedar.cp.dto.udeflookup.UdefLookupImpl;
import java.io.Serializable;

public class UdefLookupEditorSessionCSO implements Serializable {

   private int mUserId;
   private UdefLookupImpl mEditorData;


   public UdefLookupEditorSessionCSO(int userId, UdefLookupImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public UdefLookupImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
