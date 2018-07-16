// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.parser;

import com.cedar.cp.ejb.impl.base.sax.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.CellCalcImportParser;

public abstract class CellCalcAbstractElementProcessor extends AbstractElementProcessor {

   private CellCalcImportParser mParser;


   public CellCalcImportParser getEngine() {
      return this.mParser;
   }

   public void setEngine(CellCalcImportParser parser) {
      this.mParser = parser;
   }
}
