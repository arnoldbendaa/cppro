package com.cedar.cp.utc.struts.admin.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpSession;

public class ActiveUserDTO  implements Comparable {
	
  private int mPK;
  private String mId;
  private String mName;
  private boolean mYou;
  private boolean mAdministrator;
  private boolean mCanIM;
  private String mClientIP;
  private String mClientHost;
  private HttpSession mSession;
  public static SimpleDateFormat sFormat = new SimpleDateFormat("dd/MMM H:mm a");

  public int getPK()
  {
    return mPK;
  }

  public void setPK(int PK)
  {
    mPK = PK;
  }

  public String getId()
  {
    return mId;
  }

  public void setId(String id)
  {
    mId = id;
  }

  public String getName()
  {
    return mName;
  }

  public void setName(String name)
  {
    mName = name;
  }

  public boolean isYou()
  {
    return mYou;
  }

  public void setYou(boolean you)
  {
    mYou = you;
  }

  public boolean isCanIM()
  {
    return mCanIM;
  }

  public void setCanIM(boolean canIM)
  {
    mCanIM = canIM;
  }

  public boolean isAdministrator()
  {
    return mAdministrator;
  }

  public void setAdministrator(boolean administrator)
  {
    mAdministrator = administrator;
  }

  public String getClientIP()
  {
    return mClientIP;
  }

  public void setClientIP(String clientIP)
  {
    mClientIP = clientIP;
  }

  public String getClientHost()
  {
    return mClientHost;
  }

  public void setClientHost(String clientHost)
  {
    mClientHost = clientHost;
  }

  public HttpSession getSession()
  {
    return mSession;
  }

  public void setSession(HttpSession session)
  {
    mSession = session;
  }

  public String getCreationTime()
  {
    try
    {
      return sFormat.format(new Date(mSession.getCreationTime()));
    }
    catch (Exception e) {
    }
    return "";
  }

  public String getLastAccessed()
  {
    try
    {
      return sFormat.format(new Date(mSession.getLastAccessedTime()));
    }
    catch (Exception e) {
    }
    return "";
  }

  public int compareTo(Object o)
  {
    String name = ((ActiveUserDTO)o).getName();

    return getName().compareTo(name);
  }
}