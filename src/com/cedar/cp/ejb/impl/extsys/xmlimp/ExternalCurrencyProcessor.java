// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.extsys.ExtSysCurrencyDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysCurrencyEVO;
import com.cedar.cp.ejb.impl.extsys.xmlimp.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExternalCurrencyProcessor extends AbstractElementProcessor {

   private ExtSysCurrencyEVO mExtSysCurrencyEVO;
   private ExtSysCurrencyDAO mExtSysCurrencyDAO;


   public ExternalCurrencyProcessor(ExternalSystemXMLDefLoader loader) {
      super(loader);
   }

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if(!qName.equalsIgnoreCase("currency")) {
         throw new SAXException("Expected element \'valueType\'");
      } else {
         this.mExtSysCurrencyEVO = new ExtSysCurrencyEVO();
         this.mExtSysCurrencyEVO.setExternalSystemId(this.getExternalSystemDefLoader().getExternalSystemId());
         this.mExtSysCurrencyEVO.setCompanyVisId(this.getExternalSystemDefLoader().getExternalCompanyVisId());
         this.mExtSysCurrencyEVO.setLedgerVisId(this.getExternalSystemDefLoader().getExternalLedgerVisId());
         this.mExtSysCurrencyEVO.setCurrencyVisId(this.getAttributeStringValue(attributes, "currencyVisId"));
         this.mExtSysCurrencyEVO.setDescription(this.getAttributeStringValue(attributes, "description"));

         try {
            this.getExtSysCurrencyDAO().insert(this.mExtSysCurrencyEVO);
         } catch (DuplicateNameValidationException var6) {
            throw new SAXException("Duplicate currency:" + this.mExtSysCurrencyEVO.getCurrencyVisId());
         } catch (ValidationException var7) {
            throw new SAXException(var7.getMessage(), var7);
         }
      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      this.mExtSysCurrencyEVO = null;
   }

   private ExtSysCurrencyDAO getExtSysCurrencyDAO() {
      if(this.mExtSysCurrencyDAO == null) {
         this.mExtSysCurrencyDAO = new ExtSysCurrencyDAO();
      }

      return this.mExtSysCurrencyDAO;
   }

   public String getExternalCurrencyVisId() {
      return this.mExtSysCurrencyEVO != null?this.mExtSysCurrencyEVO.getCurrencyVisId():null;
   }
}
