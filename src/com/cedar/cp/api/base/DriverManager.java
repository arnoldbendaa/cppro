// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.base;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import jcifs.smb.NtlmPasswordAuthentication;

import com.coa.idm.UserRepository;
import com.coa.portal.client.PortalPrincipal;

import edu.umich.auth.cosign.CosignPrincipal;

public class DriverManager {

   private static Map sFactories = new HashMap();

   
   public static CPConnection getConnection(String url, Object authenticationObject, CPConnection.ConnectionContext connectionContext)
		    throws InvalidCredentialsException, UserLicenseException, UserDisabledException, AdminOnlyException, IllegalArgumentException, ValidationException
		  {
		    return getConnection(url, authenticationObject, true, connectionContext);
		  }
   
   public static CPConnection getConnection(String url, Object authenticationObject, boolean remote, CPConnection.ConnectionContext connectionContext)
		    throws InvalidCredentialsException, UserLicenseException, UserDisabledException, AdminOnlyException, IllegalArgumentException, ValidationException
		  {
		    if (url == null)
		    {
		      throw new IllegalArgumentException("url is null");
		    }

		    String[] parts = url.split(":");
		    String type = parts[0];

		    if ((type == null) || (!type.equals("cpapi")))
		    {
		      throw new IllegalArgumentException("unknown url type " + parts[0]);
		    }

		    String protocol = parts[1];
		    if (protocol == null)
		    {
		      throw new IllegalArgumentException("protocol can not be null");
		    }

		    String subProtocol = url.substring(url.indexOf(protocol) + protocol.length() + 1);

		    if (subProtocol == null)
		    {
		      throw new IllegalArgumentException("sub protocol can not be null");
		    }

		    if (authenticationObject == null)
		    {
		      throw new InvalidCredentialsException();
		    }

		    ConnectionFactoryDetails cfd = new ConnectionFactoryDetails();
		    cfd.setProtocol(protocol);
		    cfd.setSubProtocol(subProtocol);
		    cfd.setAuthObject(authenticationObject);
		    cfd.setRemote(remote);
		    cfd.setConnectionContext(connectionContext);

		    if (protocol.equals("test"))
		    {
		      return getConnection("com.cedar.cp.testimpl.base.ConnectionFactory", cfd);
		    }

		    return getConnection("com.cedar.cp.impl.base.ConnectionFactory", cfd);
		  }

		  private static CPConnection getConnection(String className, ConnectionFactoryDetails cfd)
		    throws InvalidCredentialsException, UserLicenseException, AdminOnlyException, UserDisabledException, ValidationException
		  {
		    ConnectionFactory cf;
		    synchronized (sFactories)
		    {
		      cf = (ConnectionFactory)sFactories.get(className);

		      if (cf == null)
		      {
		        try
		        {
		          Class c = Class.forName(className);
		          cf = (ConnectionFactory)c.newInstance();
		          sFactories.put(className, cf);
		        }
		        catch (Exception e)
		        {
		          e.printStackTrace();
		          throw new CPException("Can't create the connection factory", e);
		        }
		      }
		    }
		    return cf.getConnection(cfd);
		  }

   public static CPConnection getConnection(String url, String user, String password, CPConnection.ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      return getConnection(url, user, password, true, false, connectionContext);
   }

   public static CPConnection getConnection(String url, String user, String password, boolean useRMIHttpTunnel, CPConnection.ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      return getConnection(url, user, password, true, useRMIHttpTunnel, connectionContext);
   }

   public static CPConnection getConnection(String url, String user, String password, boolean remote, boolean useRMIHttpTunnel, CPConnection.ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, IllegalArgumentException, ValidationException {
      if(url == null) {
         throw new IllegalArgumentException("url is null");
      } else {
         StringTokenizer st = new StringTokenizer(url, ":");
         String type = st.nextToken();
         if(type != null && type.equals("cpapi")) {//arnold

            String protocol = st.nextToken();
            if(protocol == null) {
               throw new IllegalArgumentException("protocol can not be null");
            } else {
               String nextTok = st.nextToken();
               String remainder = st.hasMoreElements()?st.nextToken(""):"";
               String subProtocol = nextTok + remainder;
               if(subProtocol == null) {
                  throw new IllegalArgumentException("sub protocol can not be null");
               } else if(user != null && user.trim().length() != 0) {
                  if((connectionContext == CPConnection.ConnectionContext.INTERACTIVE_GUI || connectionContext == CPConnection.ConnectionContext.INTERACTIVE_WEB) && (password == null || password.trim().length() == 0)) {
                     throw new InvalidCredentialsException();
                  } else {
                     return protocol.equals("test") ? 
                    	getConnection("com.cedar.cp.testimpl.base.ConnectionFactory", protocol, subProtocol, user, password, remote, useRMIHttpTunnel, connectionContext) : 
                    	getConnection("com.cedar.cp.impl.base.ConnectionFactory", protocol, subProtocol, user, password, remote, useRMIHttpTunnel, connectionContext);
                  }
               } else {
                  throw new InvalidCredentialsException();
               }
            }
         } else {
            throw new IllegalArgumentException("unknown url type " + type);
         }
      }
   }

   public static CPConnection getConnection(String url, CosignPrincipal cosignPrincipal, boolean remote, boolean useRMIHttpTunnel, CPConnection.ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, IllegalArgumentException, ValidationException {
      if(url == null) {
         throw new IllegalArgumentException("url is null");
      } else {
         StringTokenizer st = new StringTokenizer(url, ":");
         String type = st.nextToken();
         if(type != null && type.equals("cpapi")) {
            String protocol = st.nextToken();
            if(protocol == null) {
               throw new IllegalArgumentException("protocol can not be null");
            } else {
               String nextTok = st.nextToken();
               String remainder = st.hasMoreElements()?st.nextToken(""):"";
               String subProtocol = nextTok + remainder;
               if(subProtocol == null) {
                  throw new IllegalArgumentException("sub protocol can not be null");
               } else if(cosignPrincipal == null) {
                  throw new InvalidCredentialsException();
               } else {
                  return protocol.equals("test")?getConnection("com.cedar.cp.testimpl.base.ConnectionFactory", protocol, subProtocol, cosignPrincipal, remote, useRMIHttpTunnel, connectionContext):getConnection("com.cedar.cp.impl.base.ConnectionFactory", protocol, subProtocol, cosignPrincipal, remote, useRMIHttpTunnel, connectionContext);
               }
            }
         } else {
            throw new IllegalArgumentException("unknown url type " + type);
         }
      }
   }

   public static CPConnection getConnection(String url, UserRepository ssoPrincipal, boolean remote, boolean useRMIHttpTunnel, CPConnection.ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, IllegalArgumentException, ValidationException {
      if(url == null) {
         throw new IllegalArgumentException("url is null");
      } else {
         StringTokenizer st = new StringTokenizer(url, ":");
         String type = st.nextToken();
         if(type != null && type.equals("cpapi")) {
            String protocol = st.nextToken();
            if(protocol == null) {
               throw new IllegalArgumentException("protocol can not be null");
            } else {
               String nextTok = st.nextToken();
               String remainder = st.hasMoreElements()?st.nextToken(""):"";
               String subProtocol = nextTok + remainder;
               if(subProtocol == null) {
                  throw new IllegalArgumentException("sub protocol can not be null");
               } else if(ssoPrincipal == null) {
                  throw new InvalidCredentialsException();
               } else {
                  return protocol.equals("test")?getConnection("com.cedar.cp.testimpl.base.ConnectionFactory", protocol, subProtocol, ssoPrincipal, remote, useRMIHttpTunnel, connectionContext):getConnection("com.cedar.cp.impl.base.ConnectionFactory", protocol, subProtocol, ssoPrincipal, remote, useRMIHttpTunnel, connectionContext);
               }
            }
         } else {
            throw new IllegalArgumentException("unknown url type " + type);
         }
      }
   }

   public static CPConnection getConnection(String url, PortalPrincipal portalPrincipal, boolean remote, boolean useRMIHttpTunnel, CPConnection.ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, IllegalArgumentException, ValidationException {
      if(url == null) {
         throw new IllegalArgumentException("url is null");
      } else {
         StringTokenizer st = new StringTokenizer(url, ":");
         String type = st.nextToken();
         if(type != null && type.equals("cpapi")) {
            String protocol = st.nextToken();
            if(protocol == null) {
               throw new IllegalArgumentException("protocol can not be null");
            } else {
               String nextTok = st.nextToken();
               String remainder = st.hasMoreElements()?st.nextToken(""):"";
               String subProtocol = nextTok + remainder;
               if(subProtocol == null) {
                  throw new IllegalArgumentException("sub protocol can not be null");
               } else if(portalPrincipal == null) {
                  throw new InvalidCredentialsException();
               } else {
                  return protocol.equals("test")?getConnection("com.cedar.cp.testimpl.base.ConnectionFactory", protocol, subProtocol, portalPrincipal, remote, useRMIHttpTunnel, connectionContext):getConnection("com.cedar.cp.impl.base.ConnectionFactory", protocol, subProtocol, portalPrincipal, remote, useRMIHttpTunnel, connectionContext);
               }
            }
         } else {
            throw new IllegalArgumentException("unknown url type " + type);
         }
      }
   }

   public static CPConnection getConnection(String url, NtlmPasswordAuthentication ntlmPrincipal, boolean remote, boolean useRMIHttpTunnel, CPConnection.ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, IllegalArgumentException, ValidationException {
      if(url == null) {
         throw new IllegalArgumentException("url is null");
      } else {
         StringTokenizer st = new StringTokenizer(url, ":");
         String type = st.nextToken();
         if(type != null && type.equals("cpapi")) {
            String protocol = st.nextToken();
            if(protocol == null) {
               throw new IllegalArgumentException("protocol can not be null");
            } else {
               String nextTok = st.nextToken();
               String remainder = st.hasMoreElements()?st.nextToken(""):"";
               String subProtocol = nextTok + remainder;
               if(subProtocol == null) {
                  throw new IllegalArgumentException("sub protocol can not be null");
               } else if(ntlmPrincipal == null) {
                  throw new InvalidCredentialsException();
               } else {
                  return protocol.equals("test")?getConnection("com.cedar.cp.testimpl.base.ConnectionFactory", protocol, subProtocol, ntlmPrincipal, remote, useRMIHttpTunnel, connectionContext):getConnection("com.cedar.cp.impl.base.ConnectionFactory", protocol, subProtocol, ntlmPrincipal, remote, useRMIHttpTunnel, connectionContext);
               }
            }
         } else {
            throw new IllegalArgumentException("unknown url type " + type);
         }
      }
   }

   private static CPConnection getConnection(String className, String protocol, String subProtocol, String user, String password, boolean remote, boolean useRMIHttpTunnel, CPConnection.ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      ConnectionFactory cf = null;
      Map var9 = sFactories;
      synchronized(sFactories) {
         cf = (ConnectionFactory)sFactories.get(className);
         if(cf == null) {
            try {
               Class e = Class.forName(className);
               cf = (ConnectionFactory)e.newInstance();
               sFactories.put(className, cf);
            } catch (Exception var12) {
               var12.printStackTrace();
               throw new CPException("Can\'t create the connection factory", var12);
            }
         }
      }

      return cf.getConnection(protocol, subProtocol, user, password, remote, useRMIHttpTunnel, connectionContext);
   }

   private static CPConnection getConnection(String className, String protocol, String subProtocol, CosignPrincipal cosignPrincipal, boolean remote, boolean useRMIHttpTunnel, CPConnection.ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      ConnectionFactory cf = null;
      Map var8 = sFactories;
      synchronized(sFactories) {
         cf = (ConnectionFactory)sFactories.get(className);
         if(cf == null) {
            try {
               Class e = Class.forName(className);
               cf = (ConnectionFactory)e.newInstance();
               sFactories.put(className, cf);
            } catch (Exception var11) {
               var11.printStackTrace();
               throw new CPException("Can\'t create the connection factory", var11);
            }
         }
      }

      return cf.getConnection(protocol, subProtocol, cosignPrincipal, remote, useRMIHttpTunnel, connectionContext);
   }

   private static CPConnection getConnection(String className, String protocol, String subProtocol, UserRepository ssoPrincipal, boolean remote, boolean useRMIHttpTunnel, CPConnection.ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      ConnectionFactory cf = null;
      Map var8 = sFactories;
      synchronized(sFactories) {
         cf = (ConnectionFactory)sFactories.get(className);
         if(cf == null) {
            try {
               Class e = Class.forName(className);
               cf = (ConnectionFactory)e.newInstance();
               sFactories.put(className, cf);
            } catch (Exception var11) {
               var11.printStackTrace();
               throw new CPException("Can\'t create the connection factory", var11);
            }
         }
      }

      return cf.getConnection(protocol, subProtocol, ssoPrincipal, remote, useRMIHttpTunnel, connectionContext);
   }

   private static CPConnection getConnection(String className, String protocol, String subProtocol, PortalPrincipal portalPrincipal, boolean remote, boolean useRMIHttpTunnel, CPConnection.ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      ConnectionFactory cf = null;
      Map var8 = sFactories;
      synchronized(sFactories) {
         cf = (ConnectionFactory)sFactories.get(className);
         if(cf == null) {
            try {
               Class e = Class.forName(className);
               cf = (ConnectionFactory)e.newInstance();
               sFactories.put(className, cf);
            } catch (Exception var11) {
               var11.printStackTrace();
               throw new CPException("Can\'t create the connection factory", var11);
            }
         }
      }

      return cf.getConnection(protocol, subProtocol, portalPrincipal, remote, useRMIHttpTunnel, connectionContext);
   }

   private static CPConnection getConnection(String className, String protocol, String subProtocol, NtlmPasswordAuthentication ntlmPrincipal, boolean remote, boolean useRMIHttpTunnel, CPConnection.ConnectionContext connectionContext) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException {
      ConnectionFactory cf = null;
      Map var8 = sFactories;
      synchronized(sFactories) {
         cf = (ConnectionFactory)sFactories.get(className);
         if(cf == null) {
            try {
               Class e = Class.forName(className);
               cf = (ConnectionFactory)e.newInstance();
               sFactories.put(className, cf);
            } catch (Exception var11) {
               var11.printStackTrace();
               throw new CPException("Can\'t create the connection factory", var11);
            }
         }
      }

      return cf.getConnection(protocol, subProtocol, ntlmPrincipal, remote, useRMIHttpTunnel, connectionContext);
   }

   public interface ConnectionFactory {

	   CPConnection getConnection(String var1, String var2, String var3, String var4, boolean var5, boolean var6, CPConnection.ConnectionContext var7) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException;

	   CPConnection getConnection(String var1, String var2, CosignPrincipal var3, boolean var4, boolean var5, CPConnection.ConnectionContext var6) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException;

	   CPConnection getConnection(String var1, String var2, UserRepository var3, boolean var4, boolean var5, CPConnection.ConnectionContext var6) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException;

	   CPConnection getConnection(String var1, String var2, PortalPrincipal var3, boolean var4, boolean var5, CPConnection.ConnectionContext var6) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException;

	   CPConnection getConnection(String var1, String var2, NtlmPasswordAuthentication var3, boolean var4, boolean var5, CPConnection.ConnectionContext var6) throws InvalidCredentialsException, UserLicenseException, UserDisabledException, ValidationException;
	
	   public abstract CPConnection getConnection(ConnectionFactoryDetails paramConnectionFactoryDetails)
			      throws InvalidCredentialsException, UserLicenseException, AdminOnlyException, UserDisabledException, ValidationException;
   }
}
