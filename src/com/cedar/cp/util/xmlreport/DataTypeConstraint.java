package com.cedar.cp.util.xmlreport;

import com.cedar.cp.util.XmlUtils;
import java.io.IOException;
import java.io.Writer;

public class DataTypeConstraint extends BaseDataTypeConstraint
{
  int mType;
  String mStartPath;
  Integer mStartYearOffSet;
  Integer mStartPeriodOffSet;
  String mEndPath;
  Integer mEndYearOffSet;
  Integer mEndPeriodOffSet;

  public String getVisId()
  {
    if ((mVisId == null) || (mVisId.length() == 0)) {
      return getValue();
    }
    return mVisId;
  }

  public int getType()
  {
    return mType;
  }

  public void setType(int type)
  {
    mType = type;
  }

  public String getStartPath()
  {
    return mStartPath;
  }

  public void setStartPath(String startPath)
  {
    mStartPath = startPath;
  }

  public Integer getStartYearOffSet()
  {
    return mStartYearOffSet;
  }

  public void setStartYearOffSet(Integer startYearOffSet)
  {
    mStartYearOffSet = startYearOffSet;
  }

  public Integer getStartPeriodOffSet()
  {
    return mStartPeriodOffSet;
  }

  public void setStartPeriodOffSet(Integer startPeriodOffSet)
  {
    mStartPeriodOffSet = startPeriodOffSet;
  }

  public String getEndPath()
  {
    return mEndPath;
  }

  public void setEndPath(String endPath)
  {
    mEndPath = endPath;
  }

  public Integer getEndYearOffSet()
  {
    return mEndYearOffSet;
  }

  public void setEndYearOffSet(Integer endYearOffSet)
  {
    mEndYearOffSet = endYearOffSet;
  }

  public Integer getEndPeriodOffSet()
  {
    return mEndPeriodOffSet;
  }

  public void setEndPeriodOffSet(Integer endPeriodOffSet)
  {
    mEndPeriodOffSet = endPeriodOffSet;
  }

  public void writeXml(Writer out)
    throws IOException
  {
    out.write("<dataType");
    out.write(" andConstraint=\"" + isAndConstraint() + "\" ");
    out.write(" type=\"" + XmlUtils.escapeStringForXML(Integer.valueOf(mType)) + "\" ");

    out.write(getXML());

    if (mStartPath != null)
      out.write(" startPath=\"" + XmlUtils.escapeStringForXML(mStartPath) + "\" ");
    if (mStartYearOffSet != null)
      out.write(" startYearOffSet=\"" + XmlUtils.escapeStringForXML(mStartYearOffSet) + "\" ");
    if (mStartPeriodOffSet != null) {
      out.write(" startPeriodOffSet=\"" + XmlUtils.escapeStringForXML(mStartPeriodOffSet) + "\" ");
    }
    if (mEndPath != null)
      out.write(" endPath=\"" + XmlUtils.escapeStringForXML(mEndPath) + "\" ");
    if (mEndYearOffSet != null)
      out.write(" endYearOffSet=\"" + XmlUtils.escapeStringForXML(mEndYearOffSet) + "\" ");
    if (mEndPeriodOffSet != null) {
      out.write(" endPeriodOffSet=\"" + XmlUtils.escapeStringForXML(mEndPeriodOffSet) + "\" ");
    }
    out.write(" />");
  }

  public String getSqlPredicate(String col)
  {
    StringBuffer sb = new StringBuffer();
    sb.append(isAndConstraint() ? " and " : " or ");
    sb.append(col);
    sb.append(" = ? ");

    return sb.toString();
  }

  public String[] getBindVariables()
  {
    return new String[] { getValue() };
  }

  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!(o instanceof DataTypeConstraint))
    {
      return false;
    }
    if (!super.equals(o))
    {
      return false;
    }

    DataTypeConstraint that = (DataTypeConstraint)o;

    if (mType != that.mType)
    {
      return false;
    }
    if (mEndPath != null ? !mEndPath.equals(that.mEndPath) : that.mEndPath != null)
    {
      return false;
    }
    if (mEndPeriodOffSet != null ? !mEndPeriodOffSet.equals(that.mEndPeriodOffSet) : that.mEndPeriodOffSet != null)
    {
      return false;
    }
    if (mEndYearOffSet != null ? !mEndYearOffSet.equals(that.mEndYearOffSet) : that.mEndYearOffSet != null)
    {
      return false;
    }
    if (mStartPath != null ? !mStartPath.equals(that.mStartPath) : that.mStartPath != null)
    {
      return false;
    }
    if (mStartPeriodOffSet != null ? !mStartPeriodOffSet.equals(that.mStartPeriodOffSet) : that.mStartPeriodOffSet != null)
    {
      return false;
    }
    if (mStartYearOffSet != null ? !mStartYearOffSet.equals(that.mStartYearOffSet) : that.mStartYearOffSet != null)
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    int result = super.hashCode();
    result = 31 * result + mType;
    result = 31 * result + (mStartPath != null ? mStartPath.hashCode() : 0);
    result = 31 * result + (mStartYearOffSet != null ? mStartYearOffSet.hashCode() : 0);
    result = 31 * result + (mStartPeriodOffSet != null ? mStartPeriodOffSet.hashCode() : 0);
    result = 31 * result + (mEndPath != null ? mEndPath.hashCode() : 0);
    result = 31 * result + (mEndYearOffSet != null ? mEndYearOffSet.hashCode() : 0);
    result = 31 * result + (mEndPeriodOffSet != null ? mEndPeriodOffSet.hashCode() : 0);
    return result;
  }
}