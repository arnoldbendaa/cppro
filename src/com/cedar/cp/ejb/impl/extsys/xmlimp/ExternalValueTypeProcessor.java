// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.extsys.ExtSysValueTypeDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysValueTypeEVO;
import com.cedar.cp.ejb.impl.extsys.xmlimp.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExternalValueTypeProcessor extends AbstractElementProcessor {

   private ExtSysValueTypeEVO mExtSysValueTypeEVO;
   private ExtSysValueTypeDAO mExtSysValueTypeDAO;


   public ExternalValueTypeProcessor(ExternalSystemXMLDefLoader loader) {
      super(loader);
   }

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if(!qName.equalsIgnoreCase("valueType")) {
         throw new SAXException("Expected element \'valueType\'");
      } else {
         this.mExtSysValueTypeEVO = new ExtSysValueTypeEVO();
         this.mExtSysValueTypeEVO.setExternalSystemId(this.getExternalSystemDefLoader().getExternalSystemId());
         this.mExtSysValueTypeEVO.setCompanyVisId(this.getExternalSystemDefLoader().getExternalCompanyVisId());
         this.mExtSysValueTypeEVO.setLedgerVisId(this.getExternalSystemDefLoader().getExternalLedgerVisId());
         this.mExtSysValueTypeEVO.setValueTypeVisId(this.getAttributeStringValue(attributes, "valueTypeVisId"));
         this.mExtSysValueTypeEVO.setDescription(this.getAttributeStringValue(attributes, "description"));

         try {
            this.getExtSysValueTypeDAO().insert(this.mExtSysValueTypeEVO);
         } catch (DuplicateNameValidationException var6) {
            throw new SAXException("Duplicate value type:" + this.mExtSysValueTypeEVO.getValueTypeVisId());
         } catch (ValidationException var7) {
            throw new SAXException(var7.getMessage(), var7);
         }
      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      this.mExtSysValueTypeEVO = null;
   }

   private ExtSysValueTypeDAO getExtSysValueTypeDAO() {
      if(this.mExtSysValueTypeDAO == null) {
         this.mExtSysValueTypeDAO = new ExtSysValueTypeDAO();
      }

      return this.mExtSysValueTypeDAO;
   }

   public String getExternalValueTypeVisId() {
      return this.mExtSysValueTypeEVO != null?this.mExtSysValueTypeEVO.getValueTypeVisId():null;
   }
}
