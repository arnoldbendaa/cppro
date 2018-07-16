// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import java.math.BigDecimal;
import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;

public class CellPostingRuleSet extends RuleSetBase {

   public void addRuleInstances(Digester digester) {
      digester.addCallMethod("CubeUpdate/FinanceCubeId", "setFinanceCubeId", 1, new Class[]{Integer.class});
      digester.addCallParam("CubeUpdate/FinanceCubeId", 0);
      digester.addCallMethod("CubeUpdate/INVERT_NUMBERS_VALUE", "setInvertNumbers", 1, new Class[]{Boolean.class});
      digester.addCallParam("CubeUpdate/INVERT_NUMBERS_VALUE", 0);
      digester.addCallMethod("CubeUpdate/EXCLUDE_DATA_TYPES", "setExcludedDataTypes", 1, new Class[]{String.class});
      digester.addCallParam("CubeUpdate/EXCLUDE_DATA_TYPES", 0);
      digester.addCallMethod("CubeUpdate/UserId", "setUserId", 1, new Class[]{Integer.class});
      digester.addCallParam("CubeUpdate/UserId", 0);
      digester.addCallMethod("CubeUpdate/UpdateType", "setUpdateType", 1, new Class[]{Integer.class});
      digester.addCallParam("CubeUpdate/UpdateType", 0);
      digester.addCallMethod("CubeUpdate/BudgetCycleId", "setBudgetCycleId", 1, new Class[]{Integer.class});
      digester.addCallParam("CubeUpdate/BudgetCycleId", 0);
      digester.addCallMethod("CubeUpdate/AbsoluteValues", "setAbsoluteValues", 1, new Class[]{Boolean.class});
      digester.addCallParam("CubeUpdate/AbsoluteValues", 0);
      digester.addCallMethod("CubeUpdate/Audit", "setAudit", 1, new Class[]{Boolean.class});
      digester.addCallParam("CubeUpdate/Audit", 0);
      digester.addObjectCreate("CubeUpdate/Cells/Cell", "com.cedar.cp.ejb.base.cube.CellPosting");
      digester.addSetProperties("CubeUpdate/Cells/Cell", new String[]{"Addr"}, new String[]{"setAddr"});
      digester.addSetNext("CubeUpdate/Cells/Cell", "addCell", "com.cedar.cp.ejb.base.cube.CellPosting");
      digester.addObjectCreate("CubeUpdate/Cells/CellNote", "com.cedar.cp.ejb.base.cube.CellNote");
      digester.addSetProperties("CubeUpdate/Cells/CellNote", new String[]{"Addr"}, new String[]{"setAddr"});
      digester.addCallMethod("CubeUpdate/Cells/CellNote", "setNote", 1);
      digester.addCallParam("CubeUpdate/Cells/CellNote", 0);
      digester.addSetNext("CubeUpdate/Cells/CellNote", "addCellNote", "com.cedar.cp.ejb.base.cube.CellNote");
      digester.addObjectCreate("CubeUpdate/Cells/CellCalcLink", "com.cedar.cp.ejb.base.cube.CellCalcLink");
      digester.addSetProperties("CubeUpdate/Cells/CellCalcLink", new String[]{"Addr"}, new String[]{"setAddr"});
      digester.addSetNext("CubeUpdate/Cells/CellCalcLink", "addCellCalcLink", "com.cedar.cp.ejb.base.cube.CellCalcLink");
      digester.addObjectCreate("CubeUpdate/Cells/CellCalc", "com.cedar.cp.ejb.base.cube.CellCalc");
      digester.addSetProperties("CubeUpdate/Cells/CellCalc", new String[]{"Addr"}, new String[]{"setAddr"});
      digester.addSetNext("CubeUpdate/Cells/CellCalc", "addCellCalc", "com.cedar.cp.ejb.base.cube.CellCalc");
      digester.addObjectCreate("CubeUpdate/Cells/CellCalc/CellCalcData", "com.cedar.cp.ejb.base.cube.CellCalcData");
      digester.addSetProperties("CubeUpdate/Cells/CellCalc/CellCalcData");
      digester.addCallMethod("CubeUpdate/Cells/CellCalc/CellCalcData/StringValue", "setStringValue", 1, new Class[]{String.class});
      digester.addCallParam("CubeUpdate/Cells/CellCalc/CellCalcData/StringValue", 0);
      digester.addCallMethod("CubeUpdate/Cells/CellCalc/CellCalcData/NumericValue", "setNumericValue", 1, new Class[]{BigDecimal.class});
      digester.addCallParam("CubeUpdate/Cells/CellCalc/CellCalcData/NumericValue", 0);
      digester.addSetNext("CubeUpdate/Cells/CellCalc/CellCalcData", "addCellCalcData", "com.cedar.cp.ejb.base.cube.CellCalcData");
      digester.addObjectCreate("CubeUpdate/FormNotes/FormNote", "com.cedar.cp.ejb.base.cube.FormNote");
      digester.addSetProperties("CubeUpdate/FormNotes/FormNote");
      digester.addCallMethod("CubeUpdate/FormNotes/FormNote", "setNote", 1);
      digester.addCallParam("CubeUpdate/FormNotes/FormNote", 0);
      digester.addSetNext("CubeUpdate/FormNotes/FormNote", "addFormNote", "com.cedar.cp.ejb.base.cube.FormNote");
   }
}
