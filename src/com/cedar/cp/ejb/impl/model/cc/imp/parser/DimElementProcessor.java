// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.parser.AddressElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.CellCalcAbstractElementProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class DimElementProcessor extends CellCalcAbstractElementProcessor {

   private String mDim;
   private int mDimIndex = -1;


   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.mDimIndex = Integer.parseInt(qName.substring(3));
   }

   public void characters(char[] ch, int start, int length) throws SAXException {
      if(this.mDim == null) {
         this.mDim = new String(ch, start, length);
      } else {
         this.mDim = this.mDim + new String(ch, start, length);
      }

   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      if(this.mDim == null) {
         throw new SAXException("Mandatory element/attribute \'id\' missing.");
      } else if(this.mDimIndex == -1) {
         throw new SAXException("Unable to deteremine dimension for element:" + qName);
      } else {
         AddressElementProcessor addreeElementProcessor = (AddressElementProcessor)this.getEngine().findElementProcessor(AddressElementProcessor.class);
         addreeElementProcessor.setDimAddress(this.mDimIndex, this.mDim);
         this.mDim = null;
      }
   }
}
