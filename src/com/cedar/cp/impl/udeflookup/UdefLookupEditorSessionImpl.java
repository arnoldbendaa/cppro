// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.udeflookup;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.udeflookup.UdefLookupEditor;
import com.cedar.cp.api.udeflookup.UdefLookupEditorSession;
import com.cedar.cp.dto.udeflookup.UdefLookupEditorSessionSSO;
import com.cedar.cp.dto.udeflookup.UdefLookupImpl;
import com.cedar.cp.ejb.api.udeflookup.UdefLookupEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.udeflookup.UdefLookupEditorImpl;
import com.cedar.cp.impl.udeflookup.UdefLookupsProcessImpl;
import com.cedar.cp.util.Log;

public class UdefLookupEditorSessionImpl extends BusinessSessionImpl implements UdefLookupEditorSession {

   protected UdefLookupEditorSessionSSO mServerSessionData;
   protected UdefLookupImpl mEditorData;
   protected UdefLookupEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public UdefLookupEditorSessionImpl(UdefLookupsProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get UdefLookup", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected UdefLookupEditorSessionServer getSessionServer() throws CPException {
      return new UdefLookupEditorSessionServer(this.getConnection());
   }

   public UdefLookupEditor getUdefLookupEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new UdefLookupEditorImpl(this, this.mServerSessionData, this.mEditorData);
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

   public void saveTableData() throws ValidationException {
      try {
         this.getBusinessProcess().getConnection().fireNetworkActivity(true);
         this.getSessionServer().saveTableData(this.mEditorData);
      } catch (ValidationException var6) {
         throw var6;
      } catch (CPException var7) {
         var7.printStackTrace();
         throw new RuntimeException("unable to persist");
      } finally {
         this.getBusinessProcess().getConnection().fireNetworkActivity(false);
      }

   }

   public void issueRebuild(int userId) throws ValidationException {
      try {
         this.getBusinessProcess().getConnection().fireNetworkActivity(true);
         this.getSessionServer().issueRebuild(userId);
      } catch (ValidationException var7) {
         throw var7;
      } catch (CPException var8) {
         var8.printStackTrace();
         throw new RuntimeException("unable to persist");
      } finally {
         this.getBusinessProcess().getConnection().fireNetworkActivity(false);
      }

   }
}
