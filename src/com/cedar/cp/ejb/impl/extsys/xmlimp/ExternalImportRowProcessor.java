// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.ejb.impl.extsys.ExtSysImportRowDAO;
import com.cedar.cp.ejb.impl.extsys.xmlimp.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExtSysImportRow;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalValuesProcessor;
import java.io.PrintWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExternalImportRowProcessor extends AbstractElementProcessor {

   private ExtSysImportRowDAO mExtSysImportRowDAO;
   private ExtSysImportRow mExtSysImportRow = new ExtSysImportRow();


   public ExternalImportRowProcessor(ExternalSystemXMLDefLoader loader) {
      super(loader);
   }

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if(!qName.equalsIgnoreCase("value")) {
         throw new SAXException("Expected element \'value\'");
      } else {
         this.mExtSysImportRow.setExternalSystemId(this.getExternalSystemDefLoader().getExternalSystemId());
         ExternalValuesProcessor processor = (ExternalValuesProcessor)this.getExternalSystemDefLoader().findElementProcessor(ExternalValuesProcessor.class);
         this.mExtSysImportRow.setCompanyVisId(processor.getCompanyVisId());
         this.mExtSysImportRow.setLedgerVisId(processor.getLedgerVisId());
         this.mExtSysImportRow.setCalendarYearVisId(this.getAttributeStringValue(attributes, "calendarYearVisId"));
         String[] visId = this.mExtSysImportRow.getVisId();

         for(int i = 0; i < 10; ++i) {
            String v = attributes.getValue("visId" + i);
            visId[i] = v;
         }

         this.mExtSysImportRow.setValueTypeVisId(this.getAttributeStringValue(attributes, "valueTypeVisId"));
         this.mExtSysImportRow.setCurrencyVisId(this.getAttributeStringValue(attributes, "currencyVisId"));
         this.mExtSysImportRow.setValue(this.getAttributeLongValue(attributes, "value"));
         processor.addToBatch(this.mExtSysImportRow);
      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {}

   public int checkConstraints(int externalSystemId, int maximum, PrintWriter log) {
      return this.getExtSysImportRowDAO().checkConstraintViolations(externalSystemId, maximum, log);
   }

   public ExtSysImportRowDAO getExtSysImportRowDAO() {
      if(this.mExtSysImportRowDAO == null) {
         this.mExtSysImportRowDAO = new ExtSysImportRowDAO();
      }

      return this.mExtSysImportRowDAO;
   }
}
