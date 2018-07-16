// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:34
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlreport.reader;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

public class ConfigRuleSet extends RuleSetBase {

   public void addRuleInstances(Digester digester) {
      digester.addSetProperties("cp-report");
      digester.addObjectCreate("cp-report/layout", "com.cedar.cp.util.xmlreport.Layout");
      digester.addSetProperties("cp-report/layout");
      digester.addSetNext("cp-report/layout", "setLayout");
      digester.addObjectCreate("cp-report/layout/filterStructure", "com.cedar.cp.util.xmlreport.FilterStructure");
      digester.addSetProperties("cp-report/layout/filterStructure");
      digester.addSetNext("cp-report/layout/filterStructure", "addFilterStructure", "com.cedar.cp.util.xmlreport.FilterStructure");
      digester.addObjectCreate("cp-report/layout/columnStructure", "com.cedar.cp.util.xmlreport.ColumnStructure");
      digester.addSetProperties("cp-report/layout/columnStructure");
      digester.addSetNext("cp-report/layout/columnStructure", "addColumnStructure", "com.cedar.cp.util.xmlreport.ColumnStructure");
      digester.addObjectCreate("cp-report/layout/rowStructure", "com.cedar.cp.util.xmlreport.RowStructure");
      digester.addSetProperties("cp-report/layout/rowStructure");
      digester.addSetNext("cp-report/layout/rowStructure", "addRowStructure", "com.cedar.cp.util.xmlreport.RowStructure");
      digester.addObjectCreate("cp-report/display", "com.cedar.cp.util.xmlreport.Display");
      digester.addSetProperties("cp-report/display");
      digester.addSetNext("cp-report/display", "setDisplay");
      digester.addObjectCreate("cp-report/display/displayStructure", "com.cedar.cp.util.xmlreport.DisplayStructure");
      digester.addSetProperties("cp-report/display/displayStructure");
      digester.addSetNext("cp-report/display/displayStructure", "addDisplayStructure", "com.cedar.cp.util.xmlreport.DisplayStructure");
      digester.addObjectCreate("cp-report/display/displayStructure/inConstraint", "com.cedar.cp.util.xmlreport.InConstraint");
      digester.addSetProperties("cp-report/display/displayStructure/inConstraint");
      digester.addSetNext("cp-report/display/displayStructure/inConstraint", "addConstraint", "com.cedar.cp.util.xmlreport.Constraint");
      digester.addObjectCreate("cp-report/display/displayStructure/betweenConstraint", "com.cedar.cp.util.xmlreport.BetweenConstraint");
      digester.addSetProperties("cp-report/display/displayStructure/betweenConstraint");
      digester.addSetNext("cp-report/display/displayStructure/betweenConstraint", "addConstraint", "com.cedar.cp.util.xmlreport.Constraint");
      digester.addObjectCreate("cp-report/display/displayStructure/containsConstraint", "com.cedar.cp.util.xmlreport.ContainsConstraint");
      digester.addSetProperties("cp-report/display/displayStructure/containsConstraint");
      digester.addSetNext("cp-report/display/displayStructure/containsConstraint", "addConstraint", "com.cedar.cp.util.xmlreport.Constraint");
      digester.addObjectCreate("cp-report/display/displayStructure/startsWithConstraint", "com.cedar.cp.util.xmlreport.StartsWithConstraint");
      digester.addSetProperties("cp-report/display/displayStructure/startsWithConstraint");
      digester.addSetNext("cp-report/display/displayStructure/startsWithConstraint", "addConstraint", "com.cedar.cp.util.xmlreport.Constraint");
      digester.addObjectCreate("cp-report/display/displayStructure/equalConstraint", "com.cedar.cp.util.xmlreport.EqualConstraint");
      digester.addSetProperties("cp-report/display/displayStructure/equalConstraint");
      digester.addSetNext("cp-report/display/displayStructure/equalConstraint", "addConstraint", "com.cedar.cp.util.xmlreport.Constraint");
      digester.addObjectCreate("cp-report/display/displayStructure/notEqualConstraint", "com.cedar.cp.util.xmlreport.NotEqualConstraint");
      digester.addSetProperties("cp-report/display/displayStructure/notEqualConstraint");
      digester.addSetNext("cp-report/display/displayStructure/notEqualConstraint", "addConstraint", "com.cedar.cp.util.xmlreport.Constraint");
      digester.addObjectCreate("cp-report/display/displayStructure/lessConstraint", "com.cedar.cp.util.xmlreport.LessConstraint");
      digester.addSetProperties("cp-report/display/displayStructure/lessConstraint");
      digester.addSetNext("cp-report/display/displayStructure/lessConstraint", "addConstraint", "com.cedar.cp.util.xmlreport.Constraint");
      digester.addObjectCreate("cp-report/display/displayStructure/lessEqualConstraint", "com.cedar.cp.util.xmlreport.LessEqualConstraint");
      digester.addSetProperties("cp-report/display/displayStructure/lessEqualConstraint");
      digester.addSetNext("cp-report/display/displayStructure/lessEqualConstraint", "addConstraint", "com.cedar.cp.util.xmlreport.Constraint");
      digester.addObjectCreate("cp-report/display/displayStructure/greaterConstraint", "com.cedar.cp.util.xmlreport.GreaterConstraint");
      digester.addSetProperties("cp-report/display/displayStructure/greaterConstraint");
      digester.addSetNext("cp-report/display/displayStructure/greaterConstraint", "addConstraint", "com.cedar.cp.util.xmlreport.Constraint");
      digester.addObjectCreate("cp-report/display/displayStructure/greaterEqualConstraint", "com.cedar.cp.util.xmlreport.GreaterEqualConstraint");
      digester.addSetProperties("cp-report/display/displayStructure/greaterEqualConstraint");
      digester.addSetNext("cp-report/display/displayStructure/greaterEqualConstraint", "addConstraint", "com.cedar.cp.util.xmlreport.Constraint");
   }
}
