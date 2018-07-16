// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.parser.CellCalcAbstractElementProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CellCalcImportElementProcessor extends CellCalcAbstractElementProcessor {

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.getEngine().startBatch();
      this.getEngine().setModel(this.getAttributeStringValue(attributes, "modelVisId"));
      this.getEngine().setFinanceCube(this.getAttributeStringValue(attributes, "financeCubeVisId"));
      this.getEngine().setBudgetCycle(this.getAttributeStringValue(attributes, "budgetCycleVisId"));
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      this.getEngine().endBatch();
   }
}
