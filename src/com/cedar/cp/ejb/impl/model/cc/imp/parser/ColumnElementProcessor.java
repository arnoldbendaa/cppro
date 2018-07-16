// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.parser.AddressElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.CellCalcAbstractElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.RowElementProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ColumnElementProcessor extends CellCalcAbstractElementProcessor {

   private String mId;
   private String mValue;


   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.mId = this.getOptionalAttributeStringValue(attributes, "id");
      this.mValue = this.getOptionalAttributeStringValue(attributes, "value");
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      if(this.mId == null) {
         throw new SAXException("Mandatory element/attribute \'id\' missing.");
      } else if(this.mValue == null) {
         throw new SAXException("Mandatory element/attribute \'value\' missing.");
      } else {
         AddressElementProcessor addreeElementProcessor = (AddressElementProcessor)this.getEngine().findElementProcessor(AddressElementProcessor.class);
         if(addreeElementProcessor != null) {
            addreeElementProcessor.addColumn(this.mId, this.mValue);
         } else {
            RowElementProcessor rowElementProcessor = (RowElementProcessor)this.getEngine().findElementProcessor(RowElementProcessor.class);
            if(rowElementProcessor == null) {
               throw new SAXException("Failed to locate parent row element processor for column");
            }

            rowElementProcessor.getCellCalcRowUpdate().getValues().put(this.mId, this.mValue);
         }

         this.mId = null;
         this.mValue = null;
      }
   }

   public String getId() {
      return this.mId;
   }

   public void setId(String id) {
      this.mId = id;
   }

   public String getValue() {
      return this.mValue;
   }

   public void setValue(String value) {
      this.mValue = value;
   }
}
