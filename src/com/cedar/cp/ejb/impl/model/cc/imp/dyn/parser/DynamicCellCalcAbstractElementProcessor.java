// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.ejb.impl.base.sax.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcImportParser;

public abstract class DynamicCellCalcAbstractElementProcessor extends AbstractElementProcessor {

   private DynamicCellCalcImportParser mParser;


   public DynamicCellCalcImportParser getParser() {
      return this.mParser;
   }

   public void setParser(DynamicCellCalcImportParser parser) {
      this.mParser = parser;
   }
}
