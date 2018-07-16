// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.formula;

import com.cedar.cp.ejb.base.cube.formula.FormulaCompiler;
//import com.cedar.cp.ejb.base.cube.formula.parser.SemanticChecker;
import com.cedar.cp.ejb.base.cube.formula.tablemeta.MetaTable;

class FormulaCompiler$1  {

   // $FF: synthetic field
   final FormulaCompiler this$0;


   FormulaCompiler$1(FormulaCompiler var1) {
      this.this$0 = var1;
   }

   public MetaTable queryLookupTable(String lookupTableName) {
      return this.this$0.queryLookupTable(lookupTableName);
   }

   public int getDataTypeScaling(String dataType) {
      return this.this$0.getDataTypeScaling(dataType);
   }
}
