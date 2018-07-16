// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.ejb.impl.extsys.ExtSysDimElementDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysDimElementEVO;
import com.cedar.cp.ejb.impl.extsys.xmlimp.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalDimensionProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import java.io.PrintWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExternalDimensionElementProcessor extends AbstractElementProcessor {

   private ExtSysDimElementDAO mExtSysDimElementDAO;
   private ExtSysDimElementEVO mExtSysDimElementEVO;


   public ExternalDimensionElementProcessor(ExternalSystemXMLDefLoader loader) {
      super(loader);
   }

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if(!qName.equalsIgnoreCase("dimensionElement")) {
         throw new SAXException("Expected element \'dimensionElement\'");
      } else {
         this.mExtSysDimElementEVO = new ExtSysDimElementEVO();
         this.mExtSysDimElementEVO.setExternalSystemId(this.getExternalSystemDefLoader().getExternalSystemId());
         this.mExtSysDimElementEVO.setCompanyVisId(this.getExternalSystemDefLoader().getExternalCompanyVisId());
         this.mExtSysDimElementEVO.setLedgerVisId(this.getExternalSystemDefLoader().getExternalLedgerVisId());
         this.mExtSysDimElementEVO.setDimensionVisId(this.getExternalSystemDefLoader().getExternalDimensionVisId());
         this.mExtSysDimElementEVO.setDimElementVisId(this.getAttributeStringValue(attributes, "dimensionElementVisId"));
         this.mExtSysDimElementEVO.setDescription(this.getAttributeStringValue(attributes, "description"));
         String creditDebit = attributes.getValue("creditDebit");
         if(creditDebit != null) {
            this.mExtSysDimElementEVO.setCreditDebit(this.decodeCreditDebit(creditDebit));
         }

         String notPlannable = attributes.getValue("notPlannable");
         if(notPlannable != null) {
            this.mExtSysDimElementEVO.setNotPlannable(Boolean.parseBoolean(notPlannable));
         }

         String disabled = attributes.getValue("disabled");
         if(disabled != null) {
            this.mExtSysDimElementEVO.setDisabled(Boolean.parseBoolean(disabled));
         }

         ExternalDimensionProcessor deProcessor = (ExternalDimensionProcessor)this.getExternalSystemDefLoader().findElementProcessor(ExternalDimensionProcessor.class);
         deProcessor.addToBatch(this.mExtSysDimElementEVO);
      }
   }

   private int decodeCreditDebit(String value) throws SAXException {
      if(value.equalsIgnoreCase("credit")) {
         return 1;
      } else if(value.equalsIgnoreCase("debit")) {
         return 2;
      } else {
         throw new SAXException("Invalid dimension element credit/debit specified must be one of: \'credit\', \'debit\'");
      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      this.mExtSysDimElementEVO = null;
   }

   public int checkConstraints(int externalSystemId, int maximum, PrintWriter log) {
      return this.getExtSysDimElementDAO().checkConstraintViolations(externalSystemId, maximum, log);
   }

   public String getExternalDimElementVisId() {
      return this.mExtSysDimElementEVO != null?this.mExtSysDimElementEVO.getDimElementVisId():null;
   }

   private ExtSysDimElementDAO getExtSysDimElementDAO() {
      if(this.mExtSysDimElementDAO == null) {
         this.mExtSysDimElementDAO = new ExtSysDimElementDAO();
      }

      return this.mExtSysDimElementDAO;
   }
}
