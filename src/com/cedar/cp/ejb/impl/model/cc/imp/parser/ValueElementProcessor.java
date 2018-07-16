// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.parser.CellCalcAbstractElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.ColumnElementProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ValueElementProcessor extends CellCalcAbstractElementProcessor {

   private String mValue;


   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.mValue = null;
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      if(this.mValue == null) {
         this.mValue = new String(ch, start, length);
      } else {
         this.mValue = this.mValue + new String(ch, start, length);
      }

   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      ColumnElementProcessor processor = (ColumnElementProcessor)this.getEngine().findElementProcessor(ColumnElementProcessor.class);
      processor.setValue(this.mValue);
      this.mValue = null;
   }
}
