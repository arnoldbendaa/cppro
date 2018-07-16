// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.logon;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.CPPrincipal;
import com.cedar.cp.api.base.InvalidCredentialsException;
import com.cedar.cp.api.base.UserDisabledException;
import com.cedar.cp.api.base.UserLicenseException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.logon.AuthenticationResult;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.logon.LogonHome;
import com.cedar.cp.ejb.api.logon.LogonLocal;
import com.cedar.cp.ejb.api.logon.LogonLocalHome;
import com.cedar.cp.ejb.api.logon.LogonRemote;
import com.cedar.cp.ejb.impl.logon.LogonSEJB;
import com.cedar.cp.util.Log;
import com.coa.idm.UserRepository;
import com.coa.portal.client.PortalPrincipal;
import edu.umich.auth.cosign.CosignPrincipal;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;
import jcifs.smb.NtlmPasswordAuthentication;

public class LogonServer extends AbstractSession {

   private static final String REMOTE_JNDI_NAME = "ejb/LogonRemoteHome";
   private static final String LOCAL_JNDI_NAME = "ejb/LogonLocalHome";
   protected LogonRemote mRemote;
   protected LogonLocal mLocal;
   private Log mLog = new Log(this.getClass());
   LogonSEJB server = new LogonSEJB();

   public LogonServer(CPConnection conn_) {
      super(conn_);
   }

   public LogonServer(Context context_, boolean remote) {
      super(context_, remote);
   }

   private LogonRemote getRemote() throws CreateException, RemoteException, CPException {
      if(this.mRemote == null) {
         String jndiName = this.getRemoteJNDIName();

         try {
            LogonHome e = (LogonHome)this.getHome(jndiName, LogonHome.class);
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

   private LogonLocal getLocal() throws CPException {
      if(this.mLocal == null) {
         try {
            LogonLocalHome e = (LogonLocalHome)this.getLocalHome(this.getLocalJNDIName());
            this.mLocal = e.create();
         } catch (CreateException var2) {
            throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
         }
      }

      return this.mLocal;
   }

   public void removeSession() throws CPException {}

   public void add2LogonHistory(int state, String username) throws ValidationException, CPException {
      try {
//         if(this.isRemoteConnection()) {
//            this.getRemote().add2LogonHistory(state, username);
//         } else {
//            this.getLocal().add2LogonHistory(state, username);
//         }
    	  server.add2LogonHistory(state,username);

      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public AuthenticationResult authenticateUser(String user, String password, boolean trustAuthentication) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      try {
    	  LogonSEJB logonEjb = new LogonSEJB();
    	  return logonEjb.authenticateUser(user, password, trustAuthentication);
//         return this.isRemoteConnection()?this.getRemote().authenticateUser(user, password, trustAuthentication):this.getLocal().authenticateUser(user, password, trustAuthentication);
      } catch (InvalidCredentialsException var5) {
         throw var5;
      } catch (UserLicenseException var6) {
         throw var6;
      } catch (UserDisabledException var7) {
         throw var7;
      } catch (Exception var8) {
         throw this.unravelException(var8);
      }
   }

   public AuthenticationResult authenticateUser(CosignPrincipal principal) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().authenticateUser(principal):this.getLocal().authenticateUser(principal);
      } catch (InvalidCredentialsException var3) {
         throw var3;
      } catch (UserLicenseException var4) {
         throw var4;
      } catch (UserDisabledException var5) {
         throw var5;
      } catch (Exception var6) {
         throw this.unravelException(var6);
      }
   }

   public AuthenticationResult authenticateUser(UserRepository principal) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().authenticateUser(principal):this.getLocal().authenticateUser(principal);
      } catch (InvalidCredentialsException var3) {
         throw var3;
      } catch (UserLicenseException var4) {
         throw var4;
      } catch (UserDisabledException var5) {
         throw var5;
      } catch (Exception var6) {
         throw this.unravelException(var6);
      }
   }

   public AuthenticationResult authenticateUser(PortalPrincipal principal) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().authenticateUser(principal):this.getLocal().authenticateUser(principal);
      } catch (InvalidCredentialsException var3) {
         throw var3;
      } catch (UserLicenseException var4) {
         throw var4;
      } catch (UserDisabledException var5) {
         throw var5;
      } catch (Exception var6) {
         throw this.unravelException(var6);
      }
   }

   public AuthenticationResult authenticateUser(NtlmPasswordAuthentication principal) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      try {
         return this.isRemoteConnection()?this.getRemote().authenticateUser(principal):this.getLocal().authenticateUser(principal);
      } catch (InvalidCredentialsException var3) {
         throw var3;
      } catch (UserLicenseException var4) {
         throw var4;
      } catch (UserDisabledException var5) {
         throw var5;
      } catch (Exception var6) {
         throw this.unravelException(var6);
      }
   }

   public AuthenticationResult changePassword(String logonId, String newPassword) throws ValidationException, CPException {
      try {
         return this.isRemoteConnection()?this.getRemote().changePassword(logonId, newPassword):this.getLocal().changePassword(logonId, newPassword);
      } catch (Exception var4) {
         throw this.unravelException(var4);
      }
   }

   public void disableUser(String logonId) throws ValidationException, CPException {
      try {
         if(this.isRemoteConnection()) {
            this.getRemote().disableUser(logonId);
         } else {
            this.getLocal().disableUser(logonId);
         }

      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public AuthenticationResult createUser(UserRepository ssoPrincipal) throws ValidationException, CPException {
      try {
         return this.isRemoteConnection()?this.getRemote().createUser(ssoPrincipal):this.getLocal().createUser(ssoPrincipal);
      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public void syncCPUserDetails(UserRepository ssoPrincipal) throws ValidationException, CPException {
      try {
         if(this.isRemoteConnection()) {
            this.getRemote().syncCPUserDetails(ssoPrincipal);
         } else {
            this.getLocal().syncCPUserDetails(ssoPrincipal);
         }

      } catch (Exception var3) {
         throw this.unravelException(var3);
      }
   }

   public String getRemoteJNDIName() {
      return "ejb/LogonRemoteHome";
   }

   public String getLocalJNDIName() {
      return "ejb/LogonLocalHome";
   }
   
	public AuthenticationResult authenticateUser(Object authObject, boolean trustAuthentication)
			throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException	{
		  
		AuthenticationResult result = null;
		if ((authObject instanceof CPPrincipal))
		    {
		      CPPrincipal principal = (CPPrincipal)authObject;
		      result = authenticateUser(principal.getUserId(), ((CPPrincipal)authObject).getPassword(), trustAuthentication);
		    }
		    else if ((authObject instanceof UserRepository))
		    {
		      UserRepository principal = (UserRepository)authObject;
		      result = authenticateUser(principal);
		    }
		    else if ((authObject instanceof NtlmPasswordAuthentication))
		    {
		      NtlmPasswordAuthentication principal = (NtlmPasswordAuthentication)authObject;
		      result = authenticateUser(principal);
		    }
		    else if ((authObject instanceof CosignPrincipal))
		    {
		      CosignPrincipal principal = (CosignPrincipal)authObject;
		      result = authenticateUser(principal);
		    }
		    else if ((authObject instanceof PortalPrincipal))
		    {
		      PortalPrincipal principal = (PortalPrincipal)authObject;
		      result = authenticateUser(principal);
		    }
		
		if (result == null) {
			throw new CPException("unsupported auth type");
		}
		//result.setCluster(CPContextFactory.getInstance().isCluster());
		
		return result;
	}
}
