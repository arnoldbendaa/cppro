// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.ContextColumnsElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcAbstractElementProcessor;
import com.cedar.cp.util.Pair;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContextColumnElementProcessor extends DynamicCellCalcAbstractElementProcessor {

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      String name = this.getAttributeStringValue(attributes, "name");
      String dimVisId = this.getOptionalAttributeStringValue(attributes, "dimVisId");
      String dataTypeInd = this.getOptionalAttributeStringValue(attributes, "dataType");
      ContextColumnsElementProcessor contextColumnElementsProcessor = (ContextColumnsElementProcessor)this.getParser().findElementProcessor(ContextColumnsElementProcessor.class);
      if(contextColumnElementsProcessor == null) {
         throw new SAXException("Failed to locate <context-columns/> element processor");
      } else {
         if(dataTypeInd != null && Boolean.parseBoolean(dataTypeInd)) {
            contextColumnElementsProcessor.setDataTypeContextColumn(name);
         } else {
            contextColumnElementsProcessor.getContextColumnList().add(new Pair(name, dimVisId));
         }

      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {}
}
