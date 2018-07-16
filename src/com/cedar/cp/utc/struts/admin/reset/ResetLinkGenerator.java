package com.cedar.cp.utc.struts.admin.reset;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResetLinkGenerator
{
  private StringBuilder mRootURL;
  public static DateFormat sLinkFormat = new SimpleDateFormat("yyMMddHHmmss");
  private Date mDate;

  public ResetLinkGenerator(String url)
  {
    mRootURL = new StringBuilder(url);

    if (!mRootURL.toString().endsWith("/"))
    {
      mRootURL.append("/");
    }

    mRootURL.append("/passwordReset.do?unqid=");
    mDate = new Date();
  }

  public String generateLink(String email)
  {
    mRootURL.append(getHashedMail(email)).append(getTimeStamp());
    return mRootURL.toString();
  }

  public String getHashedMail(String email)
  {
    String result = "";
    try
    {
      result = Integer.toString(email.hashCode());
    }
    catch (NumberFormatException nfe)
    {
      nfe.printStackTrace();
    }

    return result;
  }

  public String getTimeStamp()
  {
    return sLinkFormat.format(mDate);
  }
}