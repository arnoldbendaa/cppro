// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.currency;

import com.cedar.cp.dto.currency.CurrencyImpl;
import java.io.Serializable;

public class CurrencyEditorSessionCSO implements Serializable {

   private int mUserId;
   private CurrencyImpl mEditorData;


   public CurrencyEditorSessionCSO(int userId, CurrencyImpl editorData) {
      this.mUserId = userId;
      this.mEditorData = editorData;
   }

   public CurrencyImpl getEditorData() {
      return this.mEditorData;
   }

   public int getUserId() {
      return this.mUserId;
   }
}
