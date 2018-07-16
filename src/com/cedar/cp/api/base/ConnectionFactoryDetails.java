package com.cedar.cp.api.base;

import com.coa.idm.UserRepository;
import com.coa.portal.client.PortalPrincipal;
import edu.umich.auth.cosign.CosignPrincipal;
import jcifs.smb.NtlmPasswordAuthentication;

public class ConnectionFactoryDetails
{
  private String mProtocol;
  private String mSubProtocol;
  private Object mAuthObject;
  private boolean mRemote;
  private CPConnection.ConnectionContext mConnectionContext;

  public String getProtocol()
  {
    return mProtocol;
  }

  public void setProtocol(String protocol)
  {
    mProtocol = protocol;
  }

  public String getSubProtocol()
  {
    return mSubProtocol;
  }

  public void setSubProtocol(String subProtocol)
  {
    mSubProtocol = subProtocol;
  }

  public Object getAuthObject()
  {
    return mAuthObject;
  }

  public void setAuthObject(Object authObject)
  {
    mAuthObject = authObject;
  }

  public boolean isRemote()
  {
    return mRemote;
  }

  public void setRemote(boolean remote)
  {
    mRemote = remote;
  }

  public CPConnection.ConnectionContext getConnectionContext()
  {
    return mConnectionContext;
  }

  public void setConnectionContext(CPConnection.ConnectionContext connectionContext)
  {
    mConnectionContext = connectionContext;
  }

  public String getUserId()
  {
    if ((getAuthObject() instanceof CPPrincipal))
    {
      return ((CPPrincipal)getAuthObject()).getUserId();
    }
    if ((getAuthObject() instanceof CosignPrincipal))
    {
      return ((CosignPrincipal)getAuthObject()).getName();
    }
    if ((getAuthObject() instanceof UserRepository))
    {
      return ((UserRepository)getAuthObject()).getLogonIdentity();
    }
    if ((getAuthObject() instanceof PortalPrincipal))
    {
      return ((PortalPrincipal)getAuthObject()).getApplicationUserId();
    }
    if ((getAuthObject() instanceof NtlmPasswordAuthentication))
    {
      return ((NtlmPasswordAuthentication)getAuthObject()).getUsername();
    }

    return null;
  }

  public String getPassword()
  {
    if ((getAuthObject() instanceof CPPrincipal))
    {
      return ((CPPrincipal)getAuthObject()).getPassword();
    }

    return null;
  }
}