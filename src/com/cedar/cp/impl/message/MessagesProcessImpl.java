// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.message;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.message.MessageEditor;
import com.cedar.cp.api.message.MessageEditorSession;
import com.cedar.cp.api.message.MessagesProcess;
import com.cedar.cp.dto.message.MessagePK;
import com.cedar.cp.ejb.api.message.MessageEditorSessionServer;
import com.cedar.cp.ejb.api.message.MessageHelperServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.message.MessageEditorSessionImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

public class MessagesProcessImpl extends BusinessProcessImpl implements MessagesProcess {

   private Log mLog = new Log(this.getClass());


   public MessagesProcessImpl(CPConnection connection) {
      super(connection);
   }

   public void deleteObject(Object primaryKey) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      MessageEditorSessionServer es = new MessageEditorSessionServer(this.getConnection());

      try {
         es.delete(primaryKey);
      } catch (ValidationException var5) {
         throw var5;
      } catch (CPException var6) {
         throw new RuntimeException("can\'t delete " + primaryKey, var6);
      }

      if(timer != null) {
         timer.logDebug("deleteObject", primaryKey);
      }

   }

   public MessageEditorSession getMessageEditorSession(Object key) throws ValidationException {
      MessageEditorSessionImpl sess = new MessageEditorSessionImpl(this, key);
      this.mActiveSessions.add(sess);
      return sess;
   }

   public EntityList getAllMessages() {
      try {
         return this.getConnection().getListHelper().getAllMessages();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllMessages", var2);
      }
   }

   public EntityList getInBoxForUser(String param1) {
      try {
         return this.getConnection().getListHelper().getInBoxForUser(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get InBoxForUser", var3);
      }
   }

   public EntityList getUnreadInBoxForUser(String param1) {
      try {
         return this.getConnection().getListHelper().getUnreadInBoxForUser(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get UnreadInBoxForUser", var3);
      }
   }

   public EntityList getSentItemsForUser(String param1) {
      try {
         return this.getConnection().getListHelper().getSentItemsForUser(param1);
      } catch (Exception var3) {
         var3.printStackTrace();
         throw new RuntimeException("can\'t get SentItemsForUser", var3);
      }
   }

   public EntityList getMessageForId(long param1, String param2) {
      try {
         return this.getConnection().getListHelper().getMessageForId(param1, param2);
      } catch (Exception var5) {
         var5.printStackTrace();
         throw new RuntimeException("can\'t get MessageForId", var5);
      }
   }

   public EntityList getMessageForIdSentItem(long param1, String param2) {
      try {
         return this.getConnection().getListHelper().getMessageForIdSentItem(param1, param2);
      } catch (Exception var5) {
         var5.printStackTrace();
         throw new RuntimeException("can\'t get MessageForIdSentItem", var5);
      }
   }

   public EntityList getMessageCount(long param1) {
      try {
         return this.getConnection().getListHelper().getMessageCount(param1);
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new RuntimeException("can\'t get MessageCount", var4);
      }
   }

   public String getProcessName() {
      String ret = "Processing Message";
      return ret;
   }

   protected int getProcessID() {
      return 66;
   }

   public MessageEditorSession getMessageEditorSession(long key) throws ValidationException {
      MessagePK pk = new MessagePK(key);
      return this.getMessageEditorSession(pk);
   }

   public void markAsRead(long key, long userKey) throws ValidationException {
      MessageEditorSession session = this.getMessageEditorSession(key);
      MessageEditor editor = session.getMessageEditor();
      editor.setRead(userKey, true);
      editor.commit();
      session.commit(false);
      this.terminateSession(session);
   }

   public void deleteObject(long key) throws ValidationException {
      MessagePK pk = new MessagePK(key);
      this.deleteObject(pk);
   }

   public void deleteObject(long key, long userKey) throws ValidationException {
      EntityList list = this.getConnection().getListHelper().getMessageCount(key);
      if(list.getNumRows() > 1) {
         MessageEditorSession session = this.getMessageEditorSession(key);
         MessageEditor editor = session.getMessageEditor();
         editor.setDeleteId(userKey);
         editor.commit();
         session.commit(false);
         this.terminateSession(session);
      } else {
         this.deleteObject(key);
      }

   }

   public Object createNewMessage(MessageEditor data) throws ValidationException {
      return (new MessageHelperServer(this.getConnection())).createNewMessage(data);
   }
   
   public void emptyFolder(int folderType, String userId) throws ValidationException {
     MessageHelperServer messageHelper = new MessageHelperServer(getConnection());
     messageHelper.emptyFolder(folderType, userId);
   }
}
