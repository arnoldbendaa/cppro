// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcAbstractElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.RowKeyElementProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class KeyColumnElementProcessor extends DynamicCellCalcAbstractElementProcessor {

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      String name = this.getAttributeStringValue(attributes, "name");
      RowKeyElementProcessor rowKeyElementProcessor = (RowKeyElementProcessor)this.getParser().findElementProcessor(RowKeyElementProcessor.class);
      if(rowKeyElementProcessor == null) {
         throw new SAXException("Unable to locate parent <row-key/> element processor.");
      } else {
         rowKeyElementProcessor.getRowKeys().add(name);
      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {}
}
