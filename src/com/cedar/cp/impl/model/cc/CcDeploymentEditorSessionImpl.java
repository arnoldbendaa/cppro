// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.cc;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.cc.CcDeploymentEditor;
import com.cedar.cp.api.model.cc.CcDeploymentEditorSession;
import com.cedar.cp.dto.model.cc.CcDeploymentEditorSessionSSO;
import com.cedar.cp.dto.model.cc.CcDeploymentImpl;
import com.cedar.cp.ejb.api.model.cc.CcDeploymentEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.model.cc.CcDeploymentEditorImpl;
import com.cedar.cp.impl.model.cc.CcDeploymentsProcessImpl;
import com.cedar.cp.util.Log;

public class CcDeploymentEditorSessionImpl extends BusinessSessionImpl implements CcDeploymentEditorSession {

   protected CcDeploymentEditorSessionSSO mServerSessionData;
   protected CcDeploymentImpl mEditorData;
   protected CcDeploymentEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public CcDeploymentEditorSessionImpl(CcDeploymentsProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get CcDeployment", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected CcDeploymentEditorSessionServer getSessionServer() throws CPException {
      return new CcDeploymentEditorSessionServer(this.getConnection());
   }

   public CcDeploymentEditor getCcDeploymentEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new CcDeploymentEditorImpl(this, this.mServerSessionData, this.mEditorData);
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

   public String testDeployment(StructureElementRef[] origin, boolean[] cascade) throws ValidationException {
      return this.getSessionServer().testDeployment(this.mEditorData.getModelId(), origin, cascade);
   }
   
   public EntityList getAllModelsForLoggedUser() throws CPException {
      return this.getConnection().getListHelper().getAllModelsForLoggedUser();
   }
}
