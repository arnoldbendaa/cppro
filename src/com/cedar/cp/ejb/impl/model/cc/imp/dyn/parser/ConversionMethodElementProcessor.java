// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.ColumnMapElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcAbstractElementProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ConversionMethodElementProcessor extends DynamicCellCalcAbstractElementProcessor {

   private String mConversionCode;


   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.mConversionCode = null;
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      if(this.mConversionCode == null) {
         this.mConversionCode = new String(ch, start, length);
      } else {
         this.mConversionCode = this.mConversionCode + new String(ch, start, length);
      }

   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      if(this.mConversionCode != null) {
         ColumnMapElementProcessor cep = (ColumnMapElementProcessor)this.getParser().findElementProcessor(ColumnMapElementProcessor.class);
         if(cep == null) {
            throw new SAXException("Failed to locate parent <column-map> element in <conversion-method>");
         }

         cep.setConversionCode(this.mConversionCode);
      }

   }
}
