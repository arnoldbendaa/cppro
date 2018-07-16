// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcAbstractElementProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class DataElementProcessor extends DynamicCellCalcAbstractElementProcessor {

   private String mCSVFilename;
   private String mEncoding;
   private String mExcelFilename;


   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.mCSVFilename = this.getOptionalAttributeStringValue(attributes, "csvFile");
      this.mEncoding = this.getOptionalAttributeStringValue(attributes, "encoding");
      this.mExcelFilename = this.getOptionalAttributeStringValue(attributes, "excelFile");
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      if(this.mCSVFilename != null) {
         int rows = this.getParser().getImportProcessor().importCSVFile(this.getParser().getImportProcessor().getModelId(), this.mCSVFilename, this.mEncoding);
         if(rows > 0) {
            this.getParser().log("Imported " + rows + " row(s) from " + this.mCSVFilename + ".");
         }
      } else if(this.mExcelFilename != null) {
         this.getParser().getImportProcessor().importExcelFile(this.getParser().getImportProcessor().getModelId(), this.mExcelFilename);
      }

   }
}
