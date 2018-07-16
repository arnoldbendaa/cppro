// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.dimension.calendar;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.calendar.CalendarEditorSessionCSO;
import com.cedar.cp.dto.dimension.calendar.CalendarEditorSessionSSO;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.dimension.calendar.CalendarEditorSessionHome;
import com.cedar.cp.ejb.api.dimension.calendar.CalendarEditorSessionLocal;
import com.cedar.cp.ejb.api.dimension.calendar.CalendarEditorSessionLocalHome;
import com.cedar.cp.ejb.api.dimension.calendar.CalendarEditorSessionRemote;
import com.cedar.cp.ejb.impl.dimension.calendar.CalendarEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class CalendarEditorSessionServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/CalendarEditorSessionRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/CalendarEditorSessionLocalHome";
   protected CalendarEditorSessionRemote mRemote;
   protected CalendarEditorSessionLocal mLocal;
   private Log mLog = new Log(this.getClass());
   static CalendarEditorSessionSEJB calServer = new CalendarEditorSessionSEJB(); 

   public CalendarEditorSessionServer(CPConnection conn_) {
      super(conn_);
   }

   public CalendarEditorSessionServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private CalendarEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//      if(this.mRemote == null) {
//         String jndiName = this.getRemoteJNDIName();
//
//         try {
//            CalendarEditorSessionHome e = (CalendarEditorSessionHome)this.getHome(jndiName, CalendarEditorSessionHome.class);
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
	   
	   return calServer;
   }

   private CalendarEditorSessionSEJB getLocal() throws CPException {
//      if(this.mLocal == null) {
//         try {
//            CalendarEditorSessionLocalHome e = (CalendarEditorSessionLocalHome)this.getLocalHome(this.getLocalJNDIName());
//            this.mLocal = e.create();
//         } catch (CreateException var2) {
//            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
//         }
//      }
//
//      return this.mLocal;
	   return calServer;
   }

   public void removeSession() throws CPException {}

   public CalendarEditorSessionSSO getNewItemData() throws ValidationException, CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         CalendarEditorSessionSSO e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().getNewItemData(this.getUserId());
         } else {
            e = this.getLocal().getNewItemData(this.getUserId());
         }

         if(timer != null) {
            timer.logDebug("getNewItemData", "");
         }

         return e;
      } catch (CreateException var3) {
         throw this.unravelException(var3);
      } catch (RemoteException var4) {
         throw this.unravelException(var4);
      }
   }

   public CalendarEditorSessionSSO getItemData(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         CalendarEditorSessionSSO e = null;
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

   public HierarchyCK insert(CalendarImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         HierarchyCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().insert(new CalendarEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().insert(new CalendarEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("insert", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public HierarchyCK copy(CalendarImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         HierarchyCK e = null;
         if(this.isRemoteConnection()) {
            e = this.getRemote().copy(new CalendarEditorSessionCSO(this.getUserId(), editorData));
         } else {
            e = this.getLocal().copy(new CalendarEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("copy", e);
         }

         return e;
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void update(CalendarImpl editorData) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().update(new CalendarEditorSessionCSO(this.getUserId(), editorData));
         } else {
            this.getLocal().update(new CalendarEditorSessionCSO(this.getUserId(), editorData));
         }

         if(timer != null) {
            timer.logDebug("update", editorData.getPrimaryKey());
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void delete(Object key) throws CPException, ValidationException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         if(this.isRemoteConnection()) {
            this.getRemote().delete(this.getUserId(), key);
         } else {
            this.getLocal().delete(this.getUserId(), key);
         }

         if(timer != null) {
            timer.logDebug("delete", key);
         }

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/CalendarEditorSessionRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/CalendarEditorSessionLocalHome";
   }
}
