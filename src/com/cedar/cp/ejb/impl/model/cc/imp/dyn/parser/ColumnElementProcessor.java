// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:42
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.ColumnsElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcAbstractElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.RowElementProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ColumnElementProcessor extends DynamicCellCalcAbstractElementProcessor {

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      String id = this.getAttributeStringValue(attributes, "id");
      String value = this.getOptionalAttributeStringValue(attributes, "value");
      RowElementProcessor rowElementProcessor = (RowElementProcessor)this.getParser().findElementProcessor(RowElementProcessor.class);
      if(rowElementProcessor != null) {
         rowElementProcessor.addValue(id, value);
      } else {
         ColumnsElementProcessor columnsElementProcessor = (ColumnsElementProcessor)this.getParser().findElementProcessor(ColumnsElementProcessor.class);
         if(columnsElementProcessor == null) {
            throw new SAXException(" Failed to locate parent <row> or <columns> element in <column>");
         }

         columnsElementProcessor.getColumns().add(id);
      }

   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {}
}
