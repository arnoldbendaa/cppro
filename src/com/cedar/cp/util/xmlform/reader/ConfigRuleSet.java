// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.reader;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

public class ConfigRuleSet extends RuleSetBase {

   public void addRuleInstances(Digester digester) {
      digester.addSetProperties("cp-form");
      digester.addObjectCreate("cp-form/inputs/tableInput", "com.cedar.cp.util.xmlform.TableInput");
      digester.addSetProperties("cp-form/inputs/tableInput");
      digester.addSetNext("cp-form/inputs/tableInput", "addTableInput", "com.cedar.cp.util.xmlform.TableInput");
      digester.addObjectCreate("cp-form/inputs/tableInput/inputColumnKey", "com.cedar.cp.util.xmlform.InputColumnKey");
      digester.addSetProperties("cp-form/inputs/tableInput/inputColumnKey");
      digester.addSetNext("cp-form/inputs/tableInput/inputColumnKey", "addColumnKey", "com.cedar.cp.util.xmlform.InputColumnKey");
      digester.addObjectCreate("cp-form/inputs/tableInput/inputColumnValue", "com.cedar.cp.util.xmlform.InputColumnValue");
      digester.addSetProperties("cp-form/inputs/tableInput/inputColumnValue");
      digester.addSetNext("cp-form/inputs/tableInput/inputColumnValue", "addColumnValue", "com.cedar.cp.util.xmlform.InputColumnValue");
      digester.addObjectCreate("cp-form/inputs/lookupInput", "com.cedar.cp.util.xmlform.LookupInput");
      digester.addSetProperties("cp-form/inputs/lookupInput");
      digester.addSetNext("cp-form/inputs/lookupInput", "addLookupInput", "com.cedar.cp.util.xmlform.LookupInput");
      digester.addObjectCreate("cp-form/inputs/lookupInput/subset", "com.cedar.cp.util.xmlform.Subset");
      digester.addSetProperties("cp-form/inputs/lookupInput/subset");
      digester.addSetNext("cp-form/inputs/lookupInput/subset", "addSubset", "com.cedar.cp.util.xmlform.Subset");
      digester.addObjectCreate("cp-form/inputs/lookupInput/inputColumnKey", "com.cedar.cp.util.xmlform.InputColumnKey");
      digester.addSetProperties("cp-form/inputs/lookupInput/inputColumnKey");
      digester.addSetNext("cp-form/inputs/lookupInput/inputColumnKey", "addColumnKey", "com.cedar.cp.util.xmlform.InputColumnKey");
      digester.addObjectCreate("cp-form/inputs/lookupInput/inputColumnValue", "com.cedar.cp.util.xmlform.InputColumnValue");
      digester.addSetProperties("cp-form/inputs/lookupInput/inputColumnValue");
      digester.addSetNext("cp-form/inputs/lookupInput/inputColumnValue", "addColumnValue", "com.cedar.cp.util.xmlform.InputColumnValue");
      digester.addObjectCreate("cp-form/inputs/financeCubeInput", "com.cedar.cp.util.xmlform.FinanceCubeInput");
      digester.addSetProperties("cp-form/inputs/financeCubeInput");
      digester.addSetNext("cp-form/inputs/financeCubeInput", "addFinanceCubeInput", "com.cedar.cp.util.xmlform.FinanceCubeInput");
      digester.addObjectCreate("cp-form/inputs/financeCubeInput/fixedColumnValue", "com.cedar.cp.util.xmlform.FixedColumnValue");
      digester.addSetProperties("cp-form/inputs/financeCubeInput/fixedColumnValue");
      digester.addSetNext("cp-form/inputs/financeCubeInput/fixedColumnValue", "addFixedColumnValue", "com.cedar.cp.util.xmlform.FixedColumnValue");
      digester.addObjectCreate("cp-form/inputs/financeCubeInput/structureColumnValue", "com.cedar.cp.util.xmlform.StructureColumnValue");
      digester.addSetProperties("cp-form/inputs/financeCubeInput/structureColumnValue");
      digester.addSetNext("cp-form/inputs/financeCubeInput/structureColumnValue", "addStructureColumnValue", "com.cedar.cp.util.xmlform.StructureColumnValue");
      digester.addObjectCreate("cp-form/inputs/financeCubeInput/dataTypeColumnValue", "com.cedar.cp.util.xmlform.DataTypeColumnValue");
      digester.addSetProperties("cp-form/inputs/financeCubeInput/dataTypeColumnValue");
      digester.addSetNext("cp-form/inputs/financeCubeInput/dataTypeColumnValue", "addDataTypeColumnValue", "com.cedar.cp.util.xmlform.DataTypeColumnValue");
      digester.addObjectCreate("cp-form/inputs/rowInput", "com.cedar.cp.util.xmlform.RowInput");
      digester.addSetProperties("cp-form/inputs/rowInput");
      digester.addSetNext("cp-form/inputs/rowInput", "addRowInput", "com.cedar.cp.util.xmlform.RowInput");
      digester.addObjectCreate("cp-form/inputs/rowInput/row", "com.cedar.cp.util.xmlform.Row");
      digester.addSetProperties("cp-form/inputs/rowInput/row");
      digester.addSetNext("cp-form/inputs/rowInput/row", "addRow", "com.cedar.cp.util.xmlform.Row");
      digester.addObjectCreate("cp-form/inputs/rowInput/row/inputColumnValue", "com.cedar.cp.util.xmlform.InputColumnValue");
      digester.addSetProperties("cp-form/inputs/rowInput/row/inputColumnValue");
      digester.addSetNext("cp-form/inputs/rowInput/row/inputColumnValue", "addInputColumnValue", "com.cedar.cp.util.xmlform.InputColumnValue");
      digester.addObjectCreate("cp-form/header", "com.cedar.cp.util.xmlform.Header");
      digester.addSetProperties("cp-form/header");
      digester.addSetNext("cp-form/header", "setHeader", "com.cedar.cp.util.xmlform.Header");
      digester.addObjectCreate("cp-form/header/onFormLoad", "com.cedar.cp.util.xmlform.OnFormLoad");
      digester.addSetProperties("cp-form/header/onFormLoad");
      digester.addSetNext("cp-form/header/onFormLoad", "setOnFormLoad", "com.cedar.cp.util.xmlform.OnFormLoad");
      digester.addCallMethod("cp-form/header/onFormLoad", "setFormula", 1);
      digester.addCallParam("cp-form/header/onFormLoad/formula", 0);
      digester.addObjectCreate("cp-form/header/text", "com.cedar.cp.util.xmlform.Text");
      digester.addSetProperties("cp-form/header/text");
      digester.addSetNext("cp-form/header/text", "addText", "com.cedar.cp.util.xmlform.Text");
      digester.addObjectCreate("cp-form/header/field", "com.cedar.cp.util.xmlform.Field");
      digester.addSetProperties("cp-form/header/field");
      digester.addSetNext("cp-form/header/field", "addField", "com.cedar.cp.util.xmlform.Field");
      digester.addObjectCreate("cp-form/body", "com.cedar.cp.util.xmlform.Body");
      digester.addSetProperties("cp-form/body");
      digester.addSetNext("cp-form/body", "setBody", "com.cedar.cp.util.xmlform.Body");
      digester.addObjectCreate("cp-form/body/rowHeader", "com.cedar.cp.util.xmlform.RowHeader");
      digester.addSetProperties("cp-form/body/rowHeader");
      digester.addSetNext("cp-form/body/rowHeader", "setRowHeader", "com.cedar.cp.util.xmlform.RowHeader");
      digester.addObjectCreate("cp-form/body/autoPopulate", "com.cedar.cp.util.xmlform.AutoPopulate");
      digester.addSetProperties("cp-form/body/autoPopulate");
      digester.addSetNext("cp-form/body/autoPopulate", "setAutoPopulate", "com.cedar.cp.util.xmlform.AutoPopulate");
      digester.addObjectCreate("*/columnGroup", "com.cedar.cp.util.xmlform.ColumnGroup");
      digester.addSetProperties("*/columnGroup");
      digester.addSetNext("*/columnGroup", "addColumnGroup", "com.cedar.cp.util.xmlform.ColumnGroup");
      digester.addObjectCreate("*/column", "com.cedar.cp.util.xmlform.Column");
      digester.addSetProperties("*/column");
      digester.addSetNext("*/column", "addColumn", "com.cedar.cp.util.xmlform.Column");
      digester.addObjectCreate("*/trafficLight", "com.cedar.cp.util.xmlform.TrafficLight");
      digester.addSetProperties("*/trafficLight");
      digester.addSetNext("*/trafficLight", "addTrafficLight", "com.cedar.cp.util.xmlform.TrafficLight");
      digester.addObjectCreate("*/defaultTrafficLight", "com.cedar.cp.util.xmlform.DefaultTrafficLight");
      digester.addSetProperties("*/defaultTrafficLight");
      digester.addSetNext("*/defaultTrafficLight", "setDefaultTrafficLight", "com.cedar.cp.util.xmlform.DefaultTrafficLight");
      digester.addObjectCreate("*/column/trafficLights", "com.cedar.cp.util.xmlform.TrafficLights");
      digester.addSetProperties("*/column/trafficLights");
      digester.addSetNext("*/column/trafficLights", "setTrafficLights", "com.cedar.cp.util.xmlform.TrafficLights");
      digester.addCallMethod("*/column", "setFormula", 1);
      digester.addCallParam("*/column/formula", 0);
      digester.addObjectCreate("cp-form/body/row", "com.cedar.cp.util.xmlform.Row");
      digester.addSetProperties("cp-form/body/row");
      digester.addSetNext("cp-form/body/row", "addRow", "com.cedar.cp.util.xmlform.Row");
      digester.addObjectCreate("cp-form/body/row/columnValue", "com.cedar.cp.util.xmlform.ColumnValue");
      digester.addSetProperties("cp-form/body/row/columnValue");
      digester.addSetNext("cp-form/body/row/columnValue", "addColumnValue", "com.cedar.cp.util.xmlform.ColumnValue");
      digester.addObjectCreate("cp-form/body/totals", "com.cedar.cp.util.xmlform.Totals");
      digester.addSetProperties("cp-form/body/totals");
      digester.addSetNext("cp-form/body/totals", "setTotals", "com.cedar.cp.util.xmlform.Totals");
      digester.addObjectCreate("cp-form/body/totals/columnTotal", "com.cedar.cp.util.xmlform.ColumnTotal");
      digester.addSetProperties("cp-form/body/totals/columnTotal");
      digester.addSetNext("cp-form/body/totals/columnTotal", "addColumnTotal", "com.cedar.cp.util.xmlform.ColumnTotal");
      digester.addCallMethod("cp-form/body/totals/columnTotal", "setFormula", 1);
      digester.addCallParam("cp-form/body/totals/columnTotal/formula", 0);
      digester.addObjectCreate("cp-form/footer", "com.cedar.cp.util.xmlform.Footer");
      digester.addSetProperties("cp-form/footer");
      digester.addSetNext("cp-form/footer", "setFooter", "com.cedar.cp.util.xmlform.Footer");
      digester.addObjectCreate("cp-form/footer/summary", "com.cedar.cp.util.xmlform.Summary");
      digester.addSetProperties("cp-form/footer/summary");
      digester.addSetNext("cp-form/footer/summary", "addSummary", "com.cedar.cp.util.xmlform.Summary");
      digester.addCallMethod("cp-form/footer/summary", "setFormula", 1);
      digester.addCallParam("cp-form/footer/summary/formula", 0);
      digester.addObjectCreate("cp-form/footer/summary/summarySpreads", "com.cedar.cp.util.xmlform.SummarySpreads");
      digester.addSetProperties("cp-form/footer/summary/summarySpreads");
      digester.addSetNext("cp-form/footer/summary/summarySpreads", "setSummarySpreads", "com.cedar.cp.util.xmlform.SummarySpreads");
      digester.addObjectCreate("cp-form/footer/summary/summarySpreads/spread", "com.cedar.cp.util.xmlform.Spread");
      digester.addSetProperties("cp-form/footer/summary/summarySpreads/spread");
      digester.addSetNext("cp-form/footer/summary/summarySpreads/spread", "addSpread", "com.cedar.cp.util.xmlform.Spread");
      digester.addCallMethod("cp-form/footer/summary/summarySpreads/spread", "setFormula", 1);
      digester.addCallParam("cp-form/footer/summary/summarySpreads/spread/formula", 0);
   }
}
