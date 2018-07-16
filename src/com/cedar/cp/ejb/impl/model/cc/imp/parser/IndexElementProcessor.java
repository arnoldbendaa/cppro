// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.parser.AddressElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.CellCalcAbstractElementProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class IndexElementProcessor extends CellCalcAbstractElementProcessor {

   private String mIndexText;


   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.mIndexText = null;
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      if(this.mIndexText == null) {
         this.mIndexText = new String(ch, start, length);
      } else {
         this.mIndexText = this.mIndexText + new String(ch, start, length);
      }

   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      AddressElementProcessor processor = (AddressElementProcessor)this.getEngine().findElementProcessor(AddressElementProcessor.class);
      if(this.mIndexText != null) {
         processor.setIndex(Integer.valueOf(Integer.parseInt(this.mIndexText)));
      }

      this.mIndexText = null;
   }
}
