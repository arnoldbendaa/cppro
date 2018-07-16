// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.message;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.message.MessageEditor;
import com.cedar.cp.dto.message.MessageEditorSessionCSO;
import com.cedar.cp.dto.message.MessageImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.message.MessageHelperHome;
import com.cedar.cp.ejb.api.message.MessageHelperLocal;
import com.cedar.cp.ejb.api.message.MessageHelperLocalHome;
import com.cedar.cp.ejb.api.message.MessageHelperRemote;
import com.cedar.cp.ejb.impl.message.MessageHelperSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class MessageHelperServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/MessageHelperRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/MessageHelperLocalHome";
   protected MessageHelperRemote mRemote;
   protected MessageHelperLocal mLocal;
   private Log mLog = new Log(this.getClass());

   public static MessageHelperSEJB server = new MessageHelperSEJB();
   public MessageHelperServer(CPConnection conn_) {
      super(conn_);
   }

   public MessageHelperServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private MessageHelperSEJB getRemote() throws CreateException, RemoteException, CPException {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            MessageHelperHome e = (MessageHelperHome)this.getHome(jndiName, MessageHelperHome.class);
//            this.mRemote = e.create();
//         } catch (CreateException var3) {
//            this.removeFromCache(jndiName);
//            var3.printStackTrace();
//            throw new CPException("getRemote " + jndiName + " CreateException", var3);
//         } catch (RemoteException var4) {
//            this.removeFromCache(jndiName);
//            var4.printStackTrace();
//            throw new CPException("getRemote " + jndiName + " RemoteException", var4);
//         }
//      }
//
//      return this.mRemote;
	   return server;
   }

   private MessageHelperSEJB getLocal() throws CPException {
//      if(this.mLocal == null) {
//         try {
//            MessageHelperLocalHome e = (MessageHelperLocalHome)this.getLocalHome(this.getLocalJNDIName());
//            this.mLocal = e.create();
//         } catch (CreateException var2) {
//            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
//         }
//      }
//
//      return this.mLocal;
	   return server;
   }

   public void removeSession() throws CPException {}

   public Object createNewMessage(MessageEditor data) throws ValidationException {
      return this.createNewMessage((MessageImpl)data.getMessageImpl());
   }

   public Object createNewMessage(MessageImpl data) throws ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().createNewMessage(data):this.getLocal().createNewMessage(data);
      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public Object autonomousInsert(MessageImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         Object e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().autonomousInsert(new MessageEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().autonomousInsert(new MessageEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }
   
	public void emptyFolder(int folderType, String userId) {
		Timer timer = mLog.isDebugEnabled() ? new Timer(mLog) : null;
		try {
			if (isRemoteConnection())
				getRemote().emptyFolder(folderType, userId);
			else
				getLocal().emptyFolder(folderType, userId);
			if (timer != null)
				timer.logDebug("emptyFolder", "folder typr = " + folderType + " user id  = " + userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

   public String getRemoteJNDIName() {
      return "ejb/MessageHelperRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/MessageHelperLocalHome";
   }
}
