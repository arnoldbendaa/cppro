// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.authenticationpolicy;

import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicyEditor;
import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicyEditorSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyEditorSessionSSO;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyImpl;
import com.cedar.cp.ejb.api.authenticationpolicy.AuthenticationPolicyEditorSessionServer;
import com.cedar.cp.impl.authenticationpolicy.AuthenticationPolicyEditorImpl;
import com.cedar.cp.impl.authenticationpolicy.AuthenticationPolicysProcessImpl;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.util.Log;

public class AuthenticationPolicyEditorSessionImpl extends BusinessSessionImpl implements AuthenticationPolicyEditorSession {

   protected AuthenticationPolicyEditorSessionSSO mServerSessionData;
   protected AuthenticationPolicyImpl mEditorData;
   protected AuthenticationPolicyEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public AuthenticationPolicyEditorSessionImpl(AuthenticationPolicysProcessImpl process, Object key) throws ValidationException {
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
         throw new RuntimeException("Can\'t get AuthenticationPolicy", var5);
      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected AuthenticationPolicyEditorSessionServer getSessionServer() throws CPException {
      return new AuthenticationPolicyEditorSessionServer(this.getConnection());
   }

   public AuthenticationPolicyEditor getAuthenticationPolicyEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new AuthenticationPolicyEditorImpl(this, this.mServerSessionData, this.mEditorData);
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

   public EntityList getSecurityAdminList() {
      return this.getConnection().getListHelper().getUsersWithSecurityString("AUTHENTICATION_POLICY_PROCESS.Save");
   }
}
