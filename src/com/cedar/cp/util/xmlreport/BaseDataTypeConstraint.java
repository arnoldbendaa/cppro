package com.cedar.cp.util.xmlreport;

import com.cedar.cp.util.XmlUtils;
import java.io.IOException;

public abstract class BaseDataTypeConstraint extends Constraint
  implements Comparable
{
  protected String mVisId;
  protected String mDescription;
  protected String mValue;
  protected boolean mVisable = true;
  protected Integer mOrder;

  public String getVisId()
  {
    return mVisId;
  }

  public void setVisId(String visId) {
    mVisId = visId;
  }

  public String getDescription() {
    return mDescription;
  }

  public void setDescription(String description) {
    mDescription = description;
  }

  public String getValue()
  {
    return mValue;
  }

  public void setValue(String value)
  {
    mValue = value;
  }

  public boolean isVisable()
  {
    return mVisable;
  }

  public void setVisable(boolean visable)
  {
    mVisable = visable;
  }

  public Integer getOrder()
  {
    return mOrder;
  }

  public void setOrder(Integer order)
  {
    mOrder = order;
  }

  protected String getXML() throws IOException
  {
    StringBuilder sb = new StringBuilder();
    sb.append(" value=\"").append(XmlUtils.escapeStringForXML(getValue())).append("\" ");
    sb.append(" visable=\"").append(XmlUtils.escapeStringForXML(Boolean.valueOf(isVisable()))).append("\" ");

    if (getVisId() != null)
      sb.append(" visId=\"").append(XmlUtils.escapeStringForXML(getVisId())).append("\" ");
    if (getDescription() != null)
      sb.append(" description=\"").append(XmlUtils.escapeStringForXML(getDescription())).append("\" ");
    if (getOrder() != null) {
      sb.append(" order=\"").append(XmlUtils.escapeStringForXML(getOrder())).append("\" ");
    }
    return sb.toString();
  }

  public boolean equals(Object o)
  {
    if (this == o)
    {
      return true;
    }
    if (!(o instanceof BaseDataTypeConstraint))
    {
      return false;
    }

    BaseDataTypeConstraint that = (BaseDataTypeConstraint)o;

    if (mDescription != null ? !mDescription.equals(that.mDescription) : that.mDescription != null)
    {
      return false;
    }
    if (mValue != null ? !mValue.equals(that.mValue) : that.mValue != null)
    {
      return false;
    }
    if (mVisId != null ? !mVisId.equals(that.mVisId) : that.mVisId != null)
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    int result = mVisId != null ? mVisId.hashCode() : 0;
    result = 31 * result + (mDescription != null ? mDescription.hashCode() : 0);
    result = 31 * result + (mValue != null ? mValue.hashCode() : 0);
    return result;
  }

  public int compareTo(Object o)
  {
    if (getOrder() == null) {
      return 0;
    }
    BaseDataTypeConstraint that = (BaseDataTypeConstraint)o;
    if (that.getOrder() == null) {
      return 1;
    }
    return getOrder().compareTo(that.getOrder());
  }
}