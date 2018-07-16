// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.currency;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.currency.CurrencyEditorSessionCSO;
import com.cedar.cp.dto.currency.CurrencyEditorSessionSSO;
import com.cedar.cp.dto.currency.CurrencyImpl;
import com.cedar.cp.dto.currency.CurrencyPK;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.currency.CurrencyEditorSessionHome;
import com.cedar.cp.ejb.api.currency.CurrencyEditorSessionLocal;
import com.cedar.cp.ejb.api.currency.CurrencyEditorSessionLocalHome;
import com.cedar.cp.ejb.api.currency.CurrencyEditorSessionRemote;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class CurrencyEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/CurrencyEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/CurrencyEditorSessionLocalHome";
   protected CurrencyEditorSessionRemote mRemote;
   protected CurrencyEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public CurrencyEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public CurrencyEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private CurrencyEditorSessionRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            CurrencyEditorSessionHome e = (CurrencyEditorSessionHome)this.getHome(jndiName, CurrencyEditorSessionHome.class);
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

   private CurrencyEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            CurrencyEditorSessionLocalHome e = (CurrencyEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public CurrencyEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         CurrencyEditorSessionSSO e = null;
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

   public CurrencyEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         CurrencyEditorSessionSSO e = null;
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

   public CurrencyPK insert(CurrencyImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         CurrencyPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new CurrencyEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new CurrencyEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public CurrencyPK copy(CurrencyImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         CurrencyPK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new CurrencyEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new CurrencyEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(CurrencyImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new CurrencyEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new CurrencyEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/CurrencyEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/CurrencyEditorSessionLocalHome";
   }
}
