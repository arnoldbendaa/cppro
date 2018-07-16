// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.currency;

import com.cedar.cp.api.currency.Currency;
import com.cedar.cp.dto.currency.CurrencyImpl;
import com.cedar.cp.dto.currency.CurrencyPK;
import com.cedar.cp.impl.currency.CurrencyEditorSessionImpl;

public class CurrencyAdapter implements Currency {

   private CurrencyImpl mEditorData;
   private CurrencyEditorSessionImpl mEditorSessionImpl;


   public CurrencyAdapter(CurrencyEditorSessionImpl e, CurrencyImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected CurrencyEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected CurrencyImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(CurrencyPK paramKey) {
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
}
