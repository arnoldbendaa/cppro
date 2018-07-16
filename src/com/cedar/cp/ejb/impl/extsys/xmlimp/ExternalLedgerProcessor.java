// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.extsys.ExtSysLedgerDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysLedgerEVO;
import com.cedar.cp.ejb.impl.extsys.xmlimp.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import java.io.PrintWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExternalLedgerProcessor extends AbstractElementProcessor {

   private ExtSysLedgerEVO mExtSysLedgerEVO;
   private ExtSysLedgerDAO mExtSysLedgerDAO;


   public ExternalLedgerProcessor(ExternalSystemXMLDefLoader loader) {
      super(loader);
   }

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if(!qName.equalsIgnoreCase("ledger")) {
         throw new SAXException("Expected element \'ledger\'");
      } else {
         this.mExtSysLedgerEVO = new ExtSysLedgerEVO();
         this.mExtSysLedgerEVO.setExternalSystemId(this.getExternalSystemDefLoader().getExternalSystemId());
         this.mExtSysLedgerEVO.setCompanyVisId(this.getExternalSystemDefLoader().getExternalCompanyVisId());
         this.mExtSysLedgerEVO.setLedgerVisId(this.getAttributeStringValue(attributes, "ledgerVisId"));
         this.mExtSysLedgerEVO.setDescription(this.getAttributeStringValue(attributes, "description"));
         this.mExtSysLedgerEVO.setDummy(this.getAttributeBooleanValue(attributes, "dummy"));

         try {
            this.log("Importing ledger : " + this.mExtSysLedgerEVO);
            this.getExtSysLedgerDAO().insert(this.mExtSysLedgerEVO);
         } catch (DuplicateNameValidationException var6) {
            throw new SAXException("Duplicate ledger:" + this.mExtSysLedgerEVO.getLedgerVisId());
         } catch (ValidationException var7) {
            throw new SAXException(var7.getMessage(), var7);
         }
      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      this.mExtSysLedgerEVO = null;
   }

   public int checkConstraints(int externalSystemId, int maximum, PrintWriter log) {
      return this.getExtSysLedgerDAO().checkConstraintViolations(externalSystemId, maximum, log);
   }

   private ExtSysLedgerDAO getExtSysLedgerDAO() {
      if(this.mExtSysLedgerDAO == null) {
         this.mExtSysLedgerDAO = new ExtSysLedgerDAO();
      }

      return this.mExtSysLedgerDAO;
   }

   public String getExternalLedgerVisId() {
      return this.mExtSysLedgerEVO != null?this.mExtSysLedgerEVO.getLedgerVisId():null;
   }
}
