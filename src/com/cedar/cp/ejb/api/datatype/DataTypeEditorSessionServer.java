// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.datatype;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.datatype.DataTypeEditorSessionCSO;
import com.cedar.cp.dto.datatype.DataTypeEditorSessionSSO;
import com.cedar.cp.dto.datatype.DataTypeImpl;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.datatype.DataTypeEditorSessionHome;
import com.cedar.cp.ejb.api.datatype.DataTypeEditorSessionLocal;
import com.cedar.cp.ejb.api.datatype.DataTypeEditorSessionLocalHome;
import com.cedar.cp.ejb.api.datatype.DataTypeEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class DataTypeEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/DataTypeEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/DataTypeEditorSessionLocalHome";
   protected DataTypeEditorSessionRemote mRemote;
   protected DataTypeEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public DataTypeEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public DataTypeEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private DataTypeEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            DataTypeEditorSessionHome e = (DataTypeEditorSessionHome)this.getHome(jndiName, DataTypeEditorSessionHome.class);
            this.mRemote = e.create();
         } catch (CreateException var3) {
            this.removeFromCache(jndiName);
            var3.printStackTrace();
            throw new CPException("getRemote " + jndiName + " CreateException", var3);
         } catch (RemoteException var4) {
            this.removeFromCache(jndiName);
            var4.printStackTrace();
            throw new CPException("getRemote " + jndiName + " RemoteException", var4);
         }
      }

      return this.mRemote;
   }

   private DataTypeEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            DataTypeEditorSessionLocalHome e = (DataTypeEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
            this.mLocal = e.create();
         } catch (CreateException var2) {
            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
         }
      }

      return this.mLocal;
   }

   public void removeSession() throws CPException {}

   public void delete(Object primaryKey_) throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().delete(this.getUserId(), primaryKey_);
         } else {
            this.getLocal().delete(this.getUserId(), primaryKey_);
         }

         if(timer != null) {
            timer.logDebug("delete", primaryKey_.toString());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public DataTypeEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         DataTypeEditorSessionSSO e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getNewItemData(this.getUserId());
         } else {
            e = this.getLocal().getNewItemData(this.getUserId());
         }

         if(timer != null) {
            timer.logDebug("getNewItemData", "");
         }

         return e;
      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public DataTypeEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         DataTypeEditorSessionSSO e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getItemData(this.getUserId(), key);
         } else {
            e = this.getLocal().getItemData(this.getUserId(), key);
         }

         if(timer != null) {
            timer.logDebug("getItemData", key.toString());
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public DataTypePK insert(DataTypeImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         DataTypePK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new DataTypeEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new DataTypeEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public DataTypePK copy(DataTypeImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         DataTypePK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new DataTypeEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new DataTypeEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(DataTypeImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new DataTypeEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new DataTypeEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public int issueCreateAllExternalViews(int userId) throws ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         int result;
         if(this.isRemoteConnection()) {
            result = this.getRemote().issueCreateAllExternalViews(userId);
         } else {
            result = this.getLocal().issueCreateAllExternalViews(userId);
         }

         if(timer != null) {
            timer.logDebug("issueCreateAllExternalViews", "taskId=" + result);
         }

         return result;
      } catch (Exception var5) {
         throw this.unravelException(var5);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/DataTypeEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/DataTypeEditorSessionLocalHome";
   }
}
