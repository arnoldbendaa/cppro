// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:42
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.dyn.ColumnMappingEntry;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.ColumnMappingElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcAbstractElementProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ColumnMapElementProcessor extends DynamicCellCalcAbstractElementProcessor {

   private String mImportColumn;
   private String mCalcColumn;
   private String mConversionCode;


   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.mImportColumn = this.getAttributeStringValue(attributes, "importColumn");
      this.mCalcColumn = this.getOptionalAttributeStringValue(attributes, "calcColumn");
      this.mConversionCode = null;
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      ColumnMappingElementProcessor columnMappingElementProcessor = (ColumnMappingElementProcessor)this.getParser().findElementProcessor(ColumnMappingElementProcessor.class);
      if(columnMappingElementProcessor == null) {
         throw new SAXException("Failed to locate parent <column-mapping/> element processor.");
      } else {
         ColumnMappingEntry mappingEntry = new ColumnMappingEntry(this.mImportColumn, this.mCalcColumn, this.mConversionCode);
         columnMappingElementProcessor.getColumnMappings().put(mappingEntry.getImportColumn(), mappingEntry);
      }
   }

   public void setConversionCode(String code) {
      this.mConversionCode = code;
   }
}
