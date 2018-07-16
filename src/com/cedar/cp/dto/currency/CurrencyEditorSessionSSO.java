// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:47
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.currency;

import com.cedar.cp.dto.currency.CurrencyImpl;
import java.io.Serializable;

public class CurrencyEditorSessionSSO implements Serializable {

   private CurrencyImpl mEditorData;


   public CurrencyEditorSessionSSO() {}

   public CurrencyEditorSessionSSO(CurrencyImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public void setEditorData(CurrencyImpl paramEditorData) {
      this.mEditorData = paramEditorData;
   }

   public CurrencyImpl getEditorData() {
      return this.mEditorData;
   }
}
