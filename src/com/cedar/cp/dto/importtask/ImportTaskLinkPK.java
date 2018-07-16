package com.cedar.cp.dto.importtask;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class ImportTaskLinkPK extends PrimaryKey
  implements Serializable
{
  private int mHashCode = -2147483648;
  int mImportTaskId;
  int mImportTaskLinkId;

  public ImportTaskLinkPK(int newImportTaskId, int newImportTaskLinkId)
  {
    mImportTaskId = newImportTaskId;
    mImportTaskLinkId = newImportTaskLinkId;
  }

  public int getImportTaskId()
  {
    return mImportTaskId;
  }

  public int getImportTaskLinkId()
  {
    return mImportTaskLinkId;
  }

  public int hashCode()
  {
    if (mHashCode == -2147483648)
    {
      mHashCode += String.valueOf(mImportTaskId).hashCode();
      mHashCode += String.valueOf(mImportTaskLinkId).hashCode();
    }

    return mHashCode;
  }

  public boolean equals(Object obj)
  {
    ImportTaskLinkPK other = null;

    if ((obj instanceof ImportTaskLinkCK)) {
      other = ((ImportTaskLinkCK)obj).getImportTaskLinkPK();
    }
    else if ((obj instanceof ImportTaskLinkPK))
      other = (ImportTaskLinkPK)obj;
    else {
      return false;
    }
    boolean eq = true;

    eq = (eq) && (mImportTaskId == other.mImportTaskId);
    eq = (eq) && (mImportTaskLinkId == other.mImportTaskLinkId);

    return eq;
  }

  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(" ImportTaskId=");
    sb.append(mImportTaskId);
    sb.append(",ImportTaskLinkId=");
    sb.append(mImportTaskLinkId);
    return sb.toString().substring(1);
  }

  public String toTokens()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(" ");
    sb.append(mImportTaskId);
    sb.append(",");
    sb.append(mImportTaskLinkId);
    return "ImportTaskLinkPK|" + sb.toString().substring(1);
  }

  public static ImportTaskLinkPK getKeyFromTokens(String extKey)
  {
    String[] extValues = extKey.split("[|]");

    if (extValues.length != 2) {
      throw new IllegalStateException(extKey + ": format incorrect");
    }
    if (!extValues[0].equals("ImportTaskLinkPK")) {
      throw new IllegalStateException(extKey + ": format incorrect - must start with 'ImportTaskLinkPK|'");
    }
    extValues = extValues[1].split(",");

    int i = 0;
    int pImportTaskId = new Integer(extValues[(i++)]).intValue();
    int pImportTaskLinkId = new Integer(extValues[(i++)]).intValue();
    return new ImportTaskLinkPK(pImportTaskId, pImportTaskLinkId);
  }
}