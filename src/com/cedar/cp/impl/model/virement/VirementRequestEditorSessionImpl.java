// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.virement.VirementRequestEditor;
import com.cedar.cp.api.model.virement.VirementRequestEditorSession;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.model.virement.VirementRequestEditorSessionSSO;
import com.cedar.cp.dto.model.virement.VirementRequestImpl;
import com.cedar.cp.dto.model.virement.VirementRequestPK;
import com.cedar.cp.ejb.api.model.virement.VirementRequestEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.model.virement.VirementRequestEditorImpl;
import com.cedar.cp.impl.model.virement.VirementRequestsProcessImpl;
import com.cedar.cp.util.Log;
import java.util.Iterator;

public class VirementRequestEditorSessionImpl extends BusinessSessionImpl implements VirementRequestEditorSession {

   protected VirementRequestEditorSessionSSO mServerSessionData;
   protected VirementRequestImpl mEditorData;
   protected VirementRequestEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public VirementRequestEditorSessionImpl(VirementRequestsProcessImpl process, Object key) throws ValidationException {
      super(process);
      if(key instanceof Integer) {
         key = new VirementRequestPK(((Integer)key).intValue());
      }

      try {
         if(key == null) {
            this.mServerSessionData = this.getSessionServer().getNewItemData();
         } else {
            this.mServerSessionData = this.getSessionServer().getItemData(key);
         }
      } catch (ValidationException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new RuntimeException("Can\'t get VirementRequest", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected VirementRequestEditorSessionServer getSessionServer() throws CPException {
      return new VirementRequestEditorSessionServer(this.getConnection());
   }

   public VirementRequestEditor getVirementRequestEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new VirementRequestEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public EntityList getAvailableModels() {
      try {
         return this.getSessionServer().getAvailableModels();
      } catch (Exception var2) {
         throw new RuntimeException("unexpected exceptio", var2);
      }
   }

   public UserRef[] getAvailableOwningUserRefs() {
      try {
         return this.getSessionServer().getAvailableOwningUserRefs();
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

   public void terminate() {}

   public int saveAndSubmit() throws CPException, ValidationException {
      try {
         this.getBusinessProcess().getConnection().fireNetworkActivity(true);
         if(this.mClientEditor != null) {
            this.mClientEditor.saveModifications();
         }

         Iterator e = this.mActiveEditors.iterator();

         while(e.hasNext()) {
            BusinessEditor editor = (BusinessEditor)e.next();
            editor.commit();
         }

         int editor1 = this.getSessionServer().saveAndSubmit(this.mEditorData);
         return editor1;
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
