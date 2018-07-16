// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.udwp;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.udwp.WeightingProfileEditor;
import com.cedar.cp.api.model.udwp.WeightingProfileEditorSession;
import com.cedar.cp.dto.model.udwp.WeightingProfileEditorSessionSSO;
import com.cedar.cp.dto.model.udwp.WeightingProfileImpl;
import com.cedar.cp.ejb.api.model.udwp.WeightingProfileEditorSessionServer;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileEditorSessionSEJB;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.model.udwp.WeightingProfileEditorImpl;
import com.cedar.cp.impl.model.udwp.WeightingProfilesProcessImpl;
import com.cedar.cp.util.Log;

public class WeightingProfileEditorSessionImpl extends BusinessSessionImpl implements WeightingProfileEditorSession {

   protected WeightingProfileEditorSessionSSO mServerSessionData;
   protected WeightingProfileImpl mEditorData;
   protected WeightingProfileEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());
   int userId;
   WeightingProfileEditorSessionSEJB sessionServer = new WeightingProfileEditorSessionSEJB();

   public WeightingProfileEditorSessionImpl(WeightingProfilesProcessImpl process, Object key,int userId) throws ValidationException {
      super(process);
      this.userId = userId;
      try {
         if(key == null) {
//            this.mServerSessionData = this.getSessionServer().getNewItemData();
        	 this.mServerSessionData = sessionServer.getNewItemData(userId);
         } else {
//            this.mServerSessionData = this.getSessionServer().getItemData(key);
        	 this.mServerSessionData = sessionServer.getItemData(userId, key);
         }
      } catch (ValidationException var4) {
         throw var4;
      } catch (Exception var5) {
         throw new RuntimeException("Can\'t get WeightingProfile", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected WeightingProfileEditorSessionServer getSessionServer() throws CPException {
      return new WeightingProfileEditorSessionServer(this.getConnection());
   }

   public WeightingProfileEditor getWeightingProfileEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new WeightingProfileEditorImpl(this, this.mServerSessionData, this.mEditorData);
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

   public EntityList getOwnershipRefs() {
      try {
//         return this.getSessionServer().getOwnershipRefs(this.mEditorData.getPrimaryKey());
    	  return sessionServer.getOwnershipData(userId, this.mEditorData.getPrimaryKey());
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

   public Object[][] queryWeightingInfo(ModelRef modelRef) throws CPException, ValidationException {
      return this.getSessionServer().queryWeightingInfo(modelRef);
   }
}
