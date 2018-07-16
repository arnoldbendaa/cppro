// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.udwp;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.udwp.WeightingDeploymentEditor;
import com.cedar.cp.api.model.udwp.WeightingDeploymentEditorSession;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentEditorSessionSSO;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentImpl;
import com.cedar.cp.ejb.api.model.udwp.WeightingDeploymentEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.model.udwp.WeightingDeploymentEditorImpl;
import com.cedar.cp.impl.model.udwp.WeightingDeploymentsProcessImpl;
import com.cedar.cp.util.Log;

public class WeightingDeploymentEditorSessionImpl extends BusinessSessionImpl implements WeightingDeploymentEditorSession {

   protected WeightingDeploymentEditorSessionSSO mServerSessionData;
   protected WeightingDeploymentImpl mEditorData;
   protected WeightingDeploymentEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public WeightingDeploymentEditorSessionImpl(WeightingDeploymentsProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get WeightingDeployment", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected WeightingDeploymentEditorSessionServer getSessionServer() throws CPException {
      return new WeightingDeploymentEditorSessionServer(this.getConnection());
   }

   public WeightingDeploymentEditor getWeightingDeploymentEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new WeightingDeploymentEditorImpl(this, this.mServerSessionData, this.mEditorData);
         this.mActiveEditors.add(this.mClientEditor);
      }

      return this.mClientEditor;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   public EntityList getAvailableWeightingProfiles() {
      try {
         return this.getSessionServer().getAvailableWeightingProfiles();
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
}
