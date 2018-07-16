// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.flatform;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

public class WorkbookUpdateRuleSet extends RuleSetBase {

   public void addRuleInstances(Digester digester) {
      digester.addSetProperties("WorkbookUpdate");
      digester.addCallMethod("WorkbookUpdate/UserId", "setUserId", 1, new Class[]{Integer.class});
      digester.addCallParam("WorkbookUpdate/UserId", 0);
      digester.addCallMethod("WorkbookUpdate/BudgetCycleId", "setBudgetCycleId", 1, new Class[]{Integer.class});
      digester.addCallParam("WorkbookUpdate/BudgetCycleId", 0);
      digester.addObjectCreate("WorkbookUpdate/Parameters/Parameter", "com.cedar.cp.ejb.base.cube.flatform.Property");
      digester.addSetProperties("WorkbookUpdate/Parameters/Parameter");
      digester.addSetNext("WorkbookUpdate/Parameters/Parameter", "addParameter", "com.cedar.cp.ejb.base.cube.flatform.Property");
      digester.addObjectCreate("WorkbookUpdate/Worksheet", "com.cedar.cp.ejb.base.cube.flatform.WorksheetUpdate");
      digester.addSetProperties("WorkbookUpdate/Worksheet");
      digester.addSetNext("WorkbookUpdate/Worksheet", "addWorksheetUpdate", "com.cedar.cp.ejb.base.cube.flatform.WorksheetUpdate");
      digester.addObjectCreate("WorkbookUpdate/Worksheet/Properties/Property", "com.cedar.cp.ejb.base.cube.flatform.Property");
      digester.addSetProperties("WorkbookUpdate/Worksheet/Properties/Property");
      digester.addSetNext("WorkbookUpdate/Worksheet/Properties/Property", "addProperty", "com.cedar.cp.ejb.base.cube.flatform.Property");
      digester.addObjectCreate("WorkbookUpdate/Worksheet/Cells/Cell", "com.cedar.cp.ejb.base.cube.flatform.WorksheetCellUpdate");
      digester.addSetProperties("WorkbookUpdate/Worksheet/Cells/Cell");
      digester.addSetProperties("WorkbookUpdate/Worksheet/Cells/Cell", new String[]{"Addr"}, new String[]{"setAddr"});
      digester.addSetNext("WorkbookUpdate/Worksheet/Cells/Cell", "addWorksheetCellUpdate", "com.cedar.cp.ejb.base.cube.flatform.WorksheetCellUpdate");
   }
}
