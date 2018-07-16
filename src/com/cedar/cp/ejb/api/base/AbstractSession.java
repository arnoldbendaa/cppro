// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.base;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.EJBLocalHome;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

public abstract class AbstractSession {

   private CPConnection mConnection = null;
   private Context mContext;
   private boolean mRemoteConnection = false;
   private Log mLog = new Log(this.getClass());


   public AbstractSession(CPConnection conn_) {
      this.mConnection = conn_;
      this.mContext = (Context)conn_.getServerContext().getContext();
      this.mRemoteConnection = conn_.isRemoteConnection();
   }

   public AbstractSession(Context context_) {
      this.mContext = context_;
      this.mRemoteConnection = false;
   }

   public AbstractSession(Context context, boolean remote) {
      this.mContext = context;
      this.mRemoteConnection = remote;
   }

   protected int getUserId() {
      return this.getConnection() != null?this.getConnection().getUserContext().getUserId():0;
   }

   protected CPConnection getConnection() {
      return this.mConnection;
   }

   protected boolean isRemoteConnection() {
      return this.mRemoteConnection;
   }

   private String getCacheKey(String jndiName_) {
      return "Home " + jndiName_;
   }

   protected void removeFromCache(String jndiName_) {
      try {
         if(this.mConnection != null) {
            this.mConnection.getClientCache().remove(this.getCacheKey(jndiName_));
         }
      } catch (Exception var3) {
         ;
      }

   }

   protected EJBHome getHome(String jndiName_, Class class_) throws CPException {
      String cacheKey = this.getCacheKey(jndiName_);
      if(this.mConnection != null) {
         EJBHome timer = (EJBHome)this.mConnection.getClientCache().get(cacheKey);
         if(timer != null) {
            return timer;
         }
      }

      Timer timer1 = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      Object o;
      while(true) {
         o = null;

         try {
            o = this.mContext.lookup(jndiName_);
            break;
         } catch (NamingException var8) {
            if(!this.isRemoteConnection() || this.mConnection.getErrorPolicyHandler() == null || this.mConnection.getErrorPolicyHandler().queryHandlingCommunicationError(var8) != 1) {
               throw new CPException("can\'t get home: " + jndiName_, var8);
            }
         }
      }

      Object oo = PortableRemoteObject.narrow(o, class_);
      EJBHome home = (EJBHome)oo;
      if(this.mConnection != null) {
         this.mConnection.getClientCache().put(cacheKey, home);
      }

      if(timer1 != null) {
         timer1.logDebug("getHome", jndiName_);
      }

      return home;
   }

   protected EJBLocalHome getLocalHome(String jndiName_) throws CPException {
      Timer timer = this.mLog.isDebugEnabled()?new Timer(this.mLog):null;

      try {
         EJBLocalHome e = (EJBLocalHome)this.mContext.lookup("java:comp/env/" + jndiName_);
         
         if(timer != null) {
            timer.logDebug("getHome", jndiName_);
         }

         return e;
      } catch (NamingException var4) {
         this.mLog.error("getLocalHome", "cant get :" + jndiName_);
         throw new CPException("can\'t get local home " + jndiName_, var4);
      }
   }

   protected void handleConnectionException(String methodName_, String message_) {
      this.getConnection().getClientCache().clear();
      byte sleepSeconds = 10;
      this.mLog.warn(methodName_, message_);
      this.mLog.warn(methodName_, "retrying in " + sleepSeconds);

      try {
         Thread.currentThread();
         Thread.sleep((long)(sleepSeconds * 1000));
      } catch (Exception var5) {
         ;
      }

   }

   protected ValidationException unravelException(Exception e) {
      Object t = e;

      while(!(t instanceof ValidationException)) {
         if(t instanceof EJBException && ((EJBException)t).getCausedByException() != null) {
            t = ((EJBException)t).getCausedByException();
         } else {
            if(((Throwable)t).getCause() == null) {
               if(t != null) {
                  throw new CPException(((Throwable)t).getMessage(), (Throwable)t);
               }

               throw new CPException(e.getMessage(), e);
            }

            t = ((Throwable)t).getCause();
         }
      }

      return (ValidationException)t;
   }

   protected CPException unravelFatalException(Exception e) {
      if(e instanceof CPException) {
         return (CPException)e;
      } else {
         Object t = e;

         while(true) {
            while(!(t instanceof EJBException) || ((EJBException)t).getCausedByException() == null) {
               if(((Throwable)t).getCause() == null) {
                  if(t != null) {
                     throw new CPException(((Throwable)t).getMessage(), (Throwable)t);
                  }

                  throw new CPException(e.getMessage(), e);
               }

               t = ((Throwable)t).getCause();
            }

            t = ((EJBException)t).getCausedByException();
         }
      }
   }
}
