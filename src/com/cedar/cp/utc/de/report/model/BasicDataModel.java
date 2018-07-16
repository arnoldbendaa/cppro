package com.cedar.cp.utc.de.report.model;

import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.TreeModel;

public abstract interface BasicDataModel
{
  public abstract void setAggregationRule(String paramString);

  public abstract void setOrganisedTrees(Enumeration paramEnumeration1, Enumeration paramEnumeration2, Enumeration paramEnumeration3);

  public abstract int getRowHeadingStructureCount();

  public abstract int getColumnHeadingStructureCount();

  public abstract int getFilterStructureCount();

  public abstract int[] getRowHeadings();

  public abstract int[] getColumnHeadings();

  public abstract int[] getFilterHeadings();

  public abstract int[] getRowMaxDepths();

  public abstract TreeModel getRowHeadingTreeModel(int paramInt);

  public abstract TreeModel getColumnHeadingTreeModel(int paramInt);

  public abstract TreeModel getFilterTreeModel(int paramInt);

  public abstract Object getRowHeadingAnchor(int paramInt);

  public abstract Object getColumnHeadingAnchor(int paramInt);

  public abstract Object getFilterHeadingAnchor(int paramInt);

  public abstract int getStructureCount();

  public abstract TreeModel getStructureTreeModel(int paramInt);

  public abstract int getDisplayLocation(int paramInt);

  public abstract void setStructureConstraints(int paramInt, List paramList, boolean paramBoolean);

  public abstract List getStructureConstraints(int paramInt);

  public abstract void setAutoExpandDepth(int paramInt, Integer paramInteger);

  public abstract Integer getAutoExpandDepth(int paramInt);

  public abstract String getAggregationRule();
}