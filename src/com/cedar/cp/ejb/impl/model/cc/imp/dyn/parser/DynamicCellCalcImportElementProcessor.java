// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcAbstractElementProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class DynamicCellCalcImportElementProcessor extends DynamicCellCalcAbstractElementProcessor {

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.getParser().setModel(this.getAttributeStringValue(attributes, "modelVisId"));
      this.getParser().setFinanceCube(this.getAttributeStringValue(attributes, "financeCubeVisId"));
      this.getParser().setBudgetCycle(this.getAttributeStringValue(attributes, "budgetCycleVisId"));
      this.getParser().setUpdateType(this.getAttributeStringValue(attributes, "updateType"));
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {}
}
