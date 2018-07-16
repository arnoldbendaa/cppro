package com.cedar.cp.dto.importtask;

import com.cedar.cp.dto.base.PrimaryKey;
import java.io.Serializable;

public class ImportTaskLinkCK extends ImportTaskCK
  implements Serializable
{
  protected ImportTaskLinkPK mImportTaskLinkPK;

  public ImportTaskLinkCK(ImportTaskPK paramImportTaskPK, ImportTaskLinkPK paramImportTaskLinkPK)
  {
    super(paramImportTaskPK);

    mImportTaskLinkPK = paramImportTaskLinkPK;
  }

  public ImportTaskLinkPK getImportTaskLinkPK()
  {
    return mImportTaskLinkPK;
  }

  public PrimaryKey getPK()
  {
    return mImportTaskLinkPK;
  }

  public int hashCode()
  {
    return mImportTaskLinkPK.hashCode();
  }

  public boolean equals(Object obj)
  {
    if ((obj instanceof ImportTaskLinkPK)) {
      return obj.equals(this);
    }
    if (!(obj instanceof ImportTaskLinkCK)) {
      return false;
    }
    ImportTaskLinkCK other = (ImportTaskLinkCK)obj;
    boolean eq = true;

    eq = (eq) && (mImportTaskPK.equals(other.mImportTaskPK));
    eq = (eq) && (mImportTaskLinkPK.equals(other.mImportTaskLinkPK));

    return eq;
  }

  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(super.toString());
    sb.append("[");
    sb.append(mImportTaskLinkPK);
    sb.append("]");
    return sb.toString();
  }

  public String toTokens()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("ImportTaskLinkCK|");
    sb.append(super.getPK().toTokens());
    sb.append('|');
    sb.append(mImportTaskLinkPK.toTokens());
    return sb.toString();
  }

  public static ImportTaskCK getKeyFromTokens(String extKey)
  {
    String[] token = extKey.split("[|]");
    int i = 0;
    checkExpected("ImportTaskLinkCK", token[(i++)]);
    checkExpected("ImportTaskPK", token[(i++)]);
    i++;
    checkExpected("ImportTaskLinkPK", token[(i++)]);
    i = 1;
    return new ImportTaskLinkCK(ImportTaskPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]), ImportTaskLinkPK.getKeyFromTokens(token[(i++)] + '|' + token[(i++)]));
  }

  private static void checkExpected(String expected, String found)
  {
    if (!expected.equals(found))
      throw new IllegalArgumentException("expected=" + expected + " found=" + found);
  }
}