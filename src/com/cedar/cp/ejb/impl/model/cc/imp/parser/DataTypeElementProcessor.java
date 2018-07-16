// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.parser.AddressElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.CellCalcAbstractElementProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class DataTypeElementProcessor extends CellCalcAbstractElementProcessor {

   private String mDataType;


   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.mDataType = null;
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      if(this.mDataType == null) {
         this.mDataType = new String(ch, start, length);
      } else {
         this.mDataType = this.mDataType + new String(ch, start, length);
      }

   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      if(this.mDataType == null) {
         throw new SAXException("Mandatory element/attribute \'dataType\' missing.");
      } else {
         AddressElementProcessor addreeElementProcessor = (AddressElementProcessor)this.getEngine().findElementProcessor(AddressElementProcessor.class);
         addreeElementProcessor.setDataType(this.mDataType);
         this.mDataType = null;
      }
   }
}
