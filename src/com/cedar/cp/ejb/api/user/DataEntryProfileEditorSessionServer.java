// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:22
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.user;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.user.DataEntryProfileCK;
import com.cedar.cp.dto.user.DataEntryProfileEditorSessionCSO;
import com.cedar.cp.dto.user.DataEntryProfileEditorSessionSSO;
import com.cedar.cp.dto.user.DataEntryProfileImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.user.DataEntryProfileEditorSessionHome;
import com.cedar.cp.ejb.api.user.DataEntryProfileEditorSessionLocal;
import com.cedar.cp.ejb.api.user.DataEntryProfileEditorSessionLocalHome;
import com.cedar.cp.ejb.api.user.DataEntryProfileEditorSessionRemote;
import com.cedar.cp.ejb.impl.user.DataEntryProfileEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class DataEntryProfileEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/DataEntryProfileEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/DataEntryProfileEditorSessionLocalHome";
   protected DataEntryProfileEditorSessionSEJB mRemote;
   protected DataEntryProfileEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());


   public DataEntryProfileEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public DataEntryProfileEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private DataEntryProfileEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         //            DataEntryProfileEditorSessionHome e = (DataEntryProfileEditorSessionHome)this.getHome(jndiName, DataEntryProfileEditorSessionHome.class);
//            this.mRemote = e.create();
		 this.mRemote = new DataEntryProfileEditorSessionSEJB();
      }

      return this.mRemote;
   }

   private DataEntryProfileEditorSessionLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            DataEntryProfileEditorSessionLocalHome e = (DataEntryProfileEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
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

   public EntityList getAvailableUsers() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      EntityList ret = this.getConnection().getListHelper().getAllUsers();
      if(timer != null) {
         timer.logDebug("getUserList", "");
      }

      return ret;
   }

   public XmlFormRef[] getAvailableXmlFormRefs() throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;
      XmlFormRef[] ret = (XmlFormRef[])((XmlFormRef[])this.getConnection().getListHelper().getAllXmlForms().getValues("XmlForm"));
      if(timer != null) {
         timer.logDebug("getAvailableXmlFormRefs", "");
      }

      return ret;
   }

   public EntityList getOwnershipRefs(Object pk_) throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         EntityList e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getOwnershipData(this.getUserId(), pk_);
         } else {
            e = this.getLocal().getOwnershipData(this.getUserId(), pk_);
         }

         if(timer != null) {
            timer.logDebug("getOwnershipRefs", pk_ != null?pk_.toString():"null");
         }

         return e;
      } catch (Exception var4) {
         throw new CPException("unable to getOwnershipRefs(" + pk_ + ") from server " + var4.getMessage(), var4);
      }
   }

   public DataEntryProfileEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         DataEntryProfileEditorSessionSSO e = null;
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

   public DataEntryProfileEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         DataEntryProfileEditorSessionSSO e = null;
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

   public DataEntryProfileCK insert(DataEntryProfileImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         DataEntryProfileCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new DataEntryProfileEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new DataEntryProfileEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public DataEntryProfileCK copy(DataEntryProfileImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         DataEntryProfileCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new DataEntryProfileEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new DataEntryProfileEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(DataEntryProfileImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new DataEntryProfileEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new DataEntryProfileEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/DataEntryProfileEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/DataEntryProfileEditorSessionLocalHome";
   }
}
