// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.reader;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

public class ConfigRuleSet extends RuleSetBase {

   public void addRuleInstances(Digester digester) {
      digester.addSetProperties("workbook");
      digester.addObjectCreate("workbook/worksheet", "com.cedar.cp.util.flatform.model.Worksheet");
      digester.addSetProperties("workbook/worksheet");
      digester.addSetNext("workbook/worksheet", "addWorksheet", "com.cedar.cp.util.flatform.model.Worksheet");
      digester.addObjectCreate("workbook/worksheet/cell", "com.cedar.cp.util.flatform.model.Cell");
      digester.addSetProperties("workbook/worksheet/cell");
      digester.addSetNext("workbook/worksheet/cell", "addCell", "com.cedar.cp.util.flatform.model.Cell");
      digester.addObjectCreate("workbook/worksheet/cell/chart", "com.cedar.cp.util.flatform.model.CPChartFactory");
      digester.addSetProperties("workbook/worksheet/cell/chart");
      digester.addCallMethod("workbook/worksheet/cell/chart", "setChartBackground", 1);
      digester.addCallParam("workbook/worksheet/cell/chart", 0, "background");
      digester.addCallMethod("workbook/worksheet/cell/chart/chartTitle", "setTitle", 1);
      digester.addCallParam("workbook/worksheet/cell/chart/chartTitle", 0, "text");
      digester.addCallMethod("workbook/worksheet/cell/chart/chartTitle", "setTitleColor", 1);
      digester.addCallParam("workbook/worksheet/cell/chart/chartTitle", 0, "color");
      digester.addCallMethod("workbook/worksheet/cell/chart/chartTitle/font", "setTitleFont", 3, new Class[]{String.class, Integer.TYPE, Integer.TYPE});
      digester.addCallParam("workbook/worksheet/cell/chart/chartTitle/font", 0, "name");
      digester.addCallParam("workbook/worksheet/cell/chart/chartTitle/font", 1, "style");
      digester.addCallParam("workbook/worksheet/cell/chart/chartTitle/font", 2, "size");
      digester.addCallMethod("workbook/worksheet/cell/chart/plot", "setDomainLabel", 1);
      digester.addCallParam("workbook/worksheet/cell/chart/plot", 0, "domainLabel");
      digester.addCallMethod("workbook/worksheet/cell/chart/plot", "setRangeLabel", 1);
      digester.addCallParam("workbook/worksheet/cell/chart/plot", 0, "rangeLabel");
      digester.addCallMethod("workbook/worksheet/cell/chart/plot", "setPlotBackground", 1);
      digester.addCallParam("workbook/worksheet/cell/chart/plot", 0, "background");
      digester.addCallMethod("workbook/worksheet/cell/chart/plot", "setOrientation", 1);
      digester.addCallParam("workbook/worksheet/cell/chart/plot", 0, "orientation");
      digester.addSetNext("workbook/worksheet/cell/chart", "setCPChartFactory", "com.cedar.cp.util.flatform.model.CPChartFactory");
      digester.addObjectCreate("workbook/worksheet/cell/chart/WorksheetDataset", "com.cedar.cp.util.flatform.model.WorksheetDataset");
      digester.addSetProperties("workbook/worksheet/cell/chart/WorksheetDataset");
      digester.addSetNext("workbook/worksheet/cell/chart/WorksheetDataset", "setDataset", "com.cedar.cp.util.flatform.model.WorksheetDataset");
      digester.addObjectCreate("workbook/worksheet/cell/image", "com.cedar.cp.util.flatform.model.CPImageFactory");
      digester.addSetProperties("workbook/worksheet/cell/image");
      digester.addSetNext("workbook/worksheet/cell/image", "setCPImageFactory", "com.cedar.cp.util.flatform.model.CPImageFactory");
      digester.addObjectCreate("workbook/worksheet/columnFormat", "com.cedar.cp.util.flatform.model.ColumnFormat");
      digester.addSetProperties("workbook/worksheet/columnFormat");
      digester.addSetNext("workbook/worksheet/columnFormat", "addColumnFormat", "com.cedar.cp.util.flatform.model.ColumnFormat");
      digester.addObjectCreate("workbook/worksheet/cellFormatGroup", "com.cedar.cp.util.flatform.model.CellFormatGroup");
      digester.addSetProperties("workbook/worksheet/cellFormatGroup");
      digester.addSetNext("workbook/worksheet/cellFormatGroup", "addFormatGroup", "com.cedar.cp.util.flatform.model.CellFormatGroup");
      digester.addObjectCreate("workbook/worksheet/cellFormatGroup/rect", "com.cedar.cp.util.RTree$Rect");
      digester.addSetProperties("workbook/worksheet/cellFormatGroup/rect");
      digester.addSetNext("workbook/worksheet/cellFormatGroup/rect", "setRect", "com.cedar.cp.util.RTree$Rect");
      digester.addObjectCreate("workbook/worksheet/cellFormatGroup/border", "com.cedar.cp.util.awt.LinesBorder");
      digester.addSetProperties("workbook/worksheet/cellFormatGroup/border");
      digester.addCallMethod("workbook/worksheet/cellFormatGroup/border", "setNorthColor", 1);
      digester.addCallParam("workbook/worksheet/cellFormatGroup/border", 0, "northColor");
      digester.addCallMethod("workbook/worksheet/cellFormatGroup/border", "setEastColor", 1);
      digester.addCallParam("workbook/worksheet/cellFormatGroup/border", 0, "eastColor");
      digester.addCallMethod("workbook/worksheet/cellFormatGroup/border", "setWestColor", 1);
      digester.addCallParam("workbook/worksheet/cellFormatGroup/border", 0, "westColor");
      digester.addCallMethod("workbook/worksheet/cellFormatGroup/border", "setSouthColor", 1);
      digester.addCallParam("workbook/worksheet/cellFormatGroup/border", 0, "southColor");
      digester.addSetNext("workbook/worksheet/cellFormatGroup/border", "setBorder", "com.cedar.cp.util.awt.LinesBorder");
      digester.addObjectCreate("workbook/worksheet/mergeRect", "com.cedar.cp.util.RTree$Rect");
      digester.addSetProperties("workbook/worksheet/mergeRect");
      digester.addSetNext("workbook/worksheet/mergeRect", "addMergeRect", "com.cedar.cp.util.RTree$Rect");
      digester.addObjectCreate("workbook/worksheet/properties", "com.cedar.cp.util.flatform.model.Properties");
      digester.addObjectCreate("workbook/worksheet/properties/entry", "com.cedar.cp.util.flatform.model.Properties$PropEntry");
      digester.addSetProperties("workbook/worksheet/properties/entry");
      digester.addSetNext("workbook/worksheet/properties/entry", "addPropEntry", "com.cedar.cp.util.flatform.model.Properties$PropEntry");
      digester.addSetNext("workbook/worksheet/properties", "setProperties", "com.cedar.cp.util.flatform.model.Properties");
      digester.addObjectCreate("workbook/properties", "com.cedar.cp.util.flatform.model.Properties");
      digester.addObjectCreate("workbook/properties/entry", "com.cedar.cp.util.flatform.model.Properties$PropEntry");
      digester.addSetProperties("workbook/properties/entry");
      digester.addSetNext("workbook/properties/entry", "addPropEntry", "com.cedar.cp.util.flatform.model.Properties$PropEntry");
      digester.addSetNext("workbook/properties", "setProperties", "com.cedar.cp.util.flatform.model.Properties");
   }
}
