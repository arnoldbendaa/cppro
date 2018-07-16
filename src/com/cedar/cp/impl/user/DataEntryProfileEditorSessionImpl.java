// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.user;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.user.DataEntryProfileEditor;
import com.cedar.cp.api.user.DataEntryProfileEditorSession;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.user.DataEntryProfileEditorSessionSSO;
import com.cedar.cp.dto.user.DataEntryProfileImpl;
import com.cedar.cp.ejb.api.user.DataEntryProfileEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.user.DataEntryProfileEditorImpl;
import com.cedar.cp.impl.user.DataEntryProfilesProcessImpl;
import com.cedar.cp.util.Log;

public class DataEntryProfileEditorSessionImpl extends BusinessSessionImpl implements DataEntryProfileEditorSession {

   protected DataEntryProfileEditorSessionSSO mServerSessionData;
   protected DataEntryProfileImpl mEditorData;
   protected DataEntryProfileEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public DataEntryProfileEditorSessionImpl(DataEntryProfilesProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get DataEntryProfile", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected DataEntryProfileEditorSessionServer getSessionServer() throws CPException {
      return new DataEntryProfileEditorSessionServer(this.getConnection());
   }

   public DataEntryProfileEditor getDataEntryProfileEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new DataEntryProfileEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public EntityList getAvailableUsers() {
      try {
         return this.getSessionServer().getAvailableUsers();
      } catch (Exception var2) {
         throw new RuntimeException("unexpected exceptio", var2);
      }
   }

   public XmlFormRef[] getAvailableXmlFormRefs() {
      try {
         return this.getSessionServer().getAvailableXmlFormRefs();
      } catch (Exception var2) {
         throw new RuntimeException("unexpected exceptio", var2);
      }
   }

   public EntityList getOwnershipRefs() {
      try {
         return this.getSessionServer().getOwnershipRefs(this.mEditorData.getPrimaryKey());
      } catch (Exception var2) {
         throw new RuntimeException("unexpected exceptio", var2);
      }
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

   public void terminate() {
	   this.mServerSessionData = null;
	   this.mEditorData = null;
	   this.mClientEditor = null;
   }
}
