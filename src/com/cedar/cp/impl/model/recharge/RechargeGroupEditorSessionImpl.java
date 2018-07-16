// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.recharge;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.recharge.RechargeGroupEditor;
import com.cedar.cp.api.model.recharge.RechargeGroupEditorSession;
import com.cedar.cp.dto.model.recharge.RechargeGroupEditorSessionSSO;
import com.cedar.cp.dto.model.recharge.RechargeGroupImpl;
import com.cedar.cp.ejb.api.model.recharge.RechargeGroupEditorSessionServer;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.model.recharge.RechargeGroupEditorImpl;
import com.cedar.cp.impl.model.recharge.RechargeGroupsProcessImpl;
import com.cedar.cp.util.Log;

public class RechargeGroupEditorSessionImpl extends BusinessSessionImpl implements RechargeGroupEditorSession {

   protected RechargeGroupEditorSessionSSO mServerSessionData;
   protected RechargeGroupImpl mEditorData;
   protected RechargeGroupEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public RechargeGroupEditorSessionImpl(RechargeGroupsProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get RechargeGroup", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected RechargeGroupEditorSessionServer getSessionServer() throws CPException {
      return new RechargeGroupEditorSessionServer(this.getConnection());
   }

   public RechargeGroupEditor getRechargeGroupEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new RechargeGroupEditorImpl(this, this.mServerSessionData, this.mEditorData);
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
