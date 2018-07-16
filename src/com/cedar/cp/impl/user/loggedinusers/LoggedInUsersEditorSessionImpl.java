package com.cedar.cp.impl.user.loggedinusers;

import com.cedar.cp.api.user.loggedinusers.LoggedInUsersEditor;
import com.cedar.cp.api.user.loggedinusers.LoggedInUsersEditorSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersEditorSessionSSO;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersImpl;
import com.cedar.cp.impl.base.BusinessSessionImpl;
//import com.cedar.cp.ejb.api.user.logoutusers.LoggedInUsersEditorSessionServer;
import com.cedar.cp.impl.user.loggedinusers.LoggedInUsersEditorImpl;
import com.cedar.cp.impl.user.loggedinusers.LoggedInUsersProcessImpl;
import com.cedar.cp.util.Log;

public class LoggedInUsersEditorSessionImpl extends BusinessSessionImpl implements LoggedInUsersEditorSession {

   protected LoggedInUsersEditorSessionSSO mServerSessionData;
   protected LoggedInUsersImpl mEditorData;
   protected LoggedInUsersEditorImpl mClientEditor;
   private Log mLog = new Log(this.getClass());


   public LoggedInUsersEditorSessionImpl(LoggedInUsersProcessImpl process, Object key) throws ValidationException {
      super(process);

//      try {
////         if(key == null) {
////            this.mServerSessionData = this.getSessionServer().getNewItemData();
////         } else {
////            this.mServerSessionData = this.getSessionServer().getItemData(key);
////         }
//      } catch (ValidationException var4) {
//         throw var4;
//      } catch (Exception var5) {
//         throw new RuntimeException("Can\'t get LoggedInUsers", var5);
//      }

      this.mEditorData = this.mServerSessionData.getEditorData();
   }

   protected LoggedInUsersEditorSessionServer getSessionServer() throws CPException {
//      return new LoggedInUsersEditorSessionServer(this.getConnection());
      return null;
   }

   public LoggedInUsersEditor getLoggedInUsersEditor() {
      if(this.mClientEditor == null) {
         this.mClientEditor = new LoggedInUsersEditorImpl(this, this.mServerSessionData, this.mEditorData);
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
//         this.mEditorData.setPrimaryKey(this.getSessionServer().insert(this.mEditorData));
//      } else if(cloneOnSave) {
//         this.mEditorData.setPrimaryKey(this.getSessionServer().copy(this.mEditorData));
//      } else {
//         this.getSessionServer().update(this.mEditorData);
      }

      return this.mEditorData.getPrimaryKey();
   }

   public void terminate() {}
}
