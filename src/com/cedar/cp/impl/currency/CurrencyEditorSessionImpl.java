// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.currency;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.currency.CurrencyEditor;
import com.cedar.cp.api.currency.CurrencyEditorSession;
import com.cedar.cp.dto.currency.CurrencyEditorSessionSSO;
import com.cedar.cp.dto.currency.CurrencyImpl;
import com.cedar.cp.ejb.api.currency.CurrencyEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.currency.CurrencyEditorImpl;
import com.cedar.cp.impl.currency.CurrencysProcessImpl;
import com.cedar.cp.util.Log;

public class CurrencyEditorSessionImpl extends BusinessSessionImpl implements CurrencyEditorSession {

   protected CurrencyEditorSessionSSO mServerSessionData;
   protected CurrencyImpl mEditorData;
   protected CurrencyEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public CurrencyEditorSessionImpl(CurrencysProcessImpl process, Object key) throws ValidationException {
      super(process);

      try {
         if(key == null) {
            this.mServerSessionData = this.getSessionServer().getNewItemData();
         } else {
            this.mServerSessionData = this.getSessionServer().getItemData(key);
         }
      } catch (ValidationException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new RuntimeException("Can\'t get Currency", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected CurrencyEditorSessionServer getSessionServer() throws CPException {
      return new CurrencyEditorSessionServer(this.getConnection());
   }

   public CurrencyEditor getCurrencyEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new CurrencyEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public Object persistModifications(boolean cloneOnSave) throws CPException, ValidationException {
      if(this.mClientEditor != null) {
         this.mClientEditor.saveModifications();
      }

      if(this.mEditorData.getPrimaryKey() == null) {
         this.mEditorData.setPrimaryKey(this.getSessionServer().insert(this.mEditorData));
      } else if(cloneOnSave) {
         this.mEditorData.setPrimaryKey(this.getSessionServer().copy(this.mEditorData));
      } else {
         this.getSessionServer().update(this.mEditorData);
      }

      return this.mEditorData.getPrimaryKey();
   }

   public void terminate() {}
}
