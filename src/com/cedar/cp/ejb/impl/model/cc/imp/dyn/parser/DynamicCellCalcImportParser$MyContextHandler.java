// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.ejb.impl.base.sax.ElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcAbstractElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcImportParser;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class DynamicCellCalcImportParser$MyContextHandler extends DefaultHandler {

   // $FF: synthetic field
   final DynamicCellCalcImportParser this$0;


   DynamicCellCalcImportParser$MyContextHandler(DynamicCellCalcImportParser var1) {
      this.this$0 = var1;
   }

   public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      try {
         DynamicCellCalcAbstractElementProcessor e = DynamicCellCalcImportParser.accessMethod000(this.this$0, qName);
         e.setParser(this.this$0);
         e.processStartElement(uri, localName, qName, attributes);
         this.this$0.getProcessingStack().push(e);
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new SAXException(var6);
      }
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      ElementProcessor processor = (ElementProcessor)this.this$0.getProcessingStack().peek();
      processor.characters(ch, start, length);
   }

   public void endElement(String uri, String localName, String qName) throws SAXException {
      ElementProcessor processor = (ElementProcessor)this.this$0.getProcessingStack().pop();
      processor.processEndElement(uri, localName, qName);
   }
}
