// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.base;

import com.cedar.cp.api.base.AdminOnlyException;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.ConnectionFactoryDetails;
import com.cedar.cp.api.base.InvalidCredentialsException;
import com.cedar.cp.api.base.UserDisabledException;
import com.cedar.cp.api.base.UserLicenseException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.CPConnection.ConnectionContext;
import com.cedar.cp.impl.base.CPConnectionImpl;
import com.coa.idm.UserRepository;
import com.coa.portal.client.PortalPrincipal;
import edu.umich.auth.cosign.CosignPrincipal;
import java.util.StringTokenizer;
import jcifs.smb.NtlmPasswordAuthentication;

public class ConnectionFactory implements com.cedar.cp.api.base.DriverManager.ConnectionFactory {

   public CPConnection getConnection(String protocol, String subProtocol, String user, String password, boolean remote, boolean rmiHttpTunnel, ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      StringTokenizer st = new StringTokenizer(subProtocol, ":");
      String host = st.nextToken();
      if(host == null) {
         throw new IllegalArgumentException("host can not be null");
      } else {
         String port = st.nextToken();
         if(port == null) {
            throw new IllegalArgumentException("port can not be null");
         } else {
            int iPort = Integer.parseInt(port);
            return new CPConnectionImpl(protocol, host, iPort, user, password, remote, rmiHttpTunnel, connectionContext);
         }
      }
   }

   public CPConnection getConnection(String protocol, String subProtocol, CosignPrincipal cosignPrincipal, boolean remote, boolean rmiHttpTunnel, ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      StringTokenizer st = new StringTokenizer(subProtocol, ":");
      String host = st.nextToken();
      if(host == null) {
         throw new IllegalArgumentException("host can not be null");
      } else {
         String port = st.nextToken();
         if(port == null) {
            throw new IllegalArgumentException("port can not be null");
         } else {
            int iPort = Integer.parseInt(port);
            return new CPConnectionImpl(protocol, host, iPort, cosignPrincipal, remote, rmiHttpTunnel);
         }
      }
   }

   public CPConnection getConnection(String protocol, String subProtocol, UserRepository ssoPrincipal, boolean remote, boolean rmiHttpTunnel, ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      StringTokenizer st = new StringTokenizer(subProtocol, ":");
      String host = st.nextToken();
      if(host == null) {
         throw new IllegalArgumentException("host can not be null");
      } else {
         String port = st.nextToken();
         if(port == null) {
            throw new IllegalArgumentException("port can not be null");
         } else {
            int iPort = Integer.parseInt(port);
            return new CPConnectionImpl(protocol, host, iPort, ssoPrincipal, remote, rmiHttpTunnel, connectionContext);
         }
      }
   }

   public CPConnection getConnection(String protocol, String subProtocol, PortalPrincipal portalPrincipal, boolean remote, boolean rmiHttpTunnel, ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      StringTokenizer st = new StringTokenizer(subProtocol, ":");
      String host = st.nextToken();
      if(host == null) {
         throw new IllegalArgumentException("host can not be null");
      } else {
         String port = st.nextToken();
         if(port == null) {
            throw new IllegalArgumentException("port can not be null");
         } else {
            int iPort = Integer.parseInt(port);
            return new CPConnectionImpl(protocol, host, iPort, portalPrincipal, remote, rmiHttpTunnel);
         }
      }
   }

   public CPConnection getConnection(String protocol, String subProtocol, NtlmPasswordAuthentication ntlmPrincipal, boolean remote, boolean rmiHttpTunnel, ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      StringTokenizer st = new StringTokenizer(subProtocol, ":");
      String host = st.nextToken();
      if(host == null) {
         throw new IllegalArgumentException("host can not be null");
      } else {
         String port = st.nextToken();
         if(port == null) {
            throw new IllegalArgumentException("port can not be null");
         } else {
            int iPort = Integer.parseInt(port);
            return new CPConnectionImpl(protocol, host, iPort, ntlmPrincipal, remote, rmiHttpTunnel);
         }
      }
   }

@Override
	public CPConnection getConnection(ConnectionFactoryDetails cfd) throws InvalidCredentialsException, UserLicenseException, AdminOnlyException, UserDisabledException, ValidationException {
		return new CPConnectionImpl(cfd, cfd.getSubProtocol());
	}
}
