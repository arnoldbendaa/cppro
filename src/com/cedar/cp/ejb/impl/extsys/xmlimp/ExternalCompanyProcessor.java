// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.api.base.DuplicateNameValidationException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.extsys.ExtSysCompanyDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysCompanyEVO;
import com.cedar.cp.ejb.impl.extsys.xmlimp.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import java.io.PrintWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExternalCompanyProcessor extends AbstractElementProcessor {

   private ExtSysCompanyEVO mExtSysCompanyEVO;
   private ExtSysCompanyDAO mExtSysCompanyDAO;


   public ExternalCompanyProcessor(ExternalSystemXMLDefLoader loader) {
      super(loader);
   }

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if(!qName.equalsIgnoreCase("company")) {
         throw new SAXException("Expected element \'company\'");
      } else {
         this.mExtSysCompanyEVO = new ExtSysCompanyEVO();
         this.mExtSysCompanyEVO.setExternalSystemId(this.getExternalSystemDefLoader().getExternalSystemId());
         this.mExtSysCompanyEVO.setCompanyVisId(this.getAttributeStringValue(attributes, "companyVisId"));
         this.mExtSysCompanyEVO.setDescription(this.getAttributeStringValue(attributes, "description"));
         this.mExtSysCompanyEVO.setDummy(this.getAttributeBooleanValue(attributes, "dummy"));
         this.mExtSysCompanyEVO.setImportColumnCalendarIndex(this.getAttributeIntValue(attributes, "importCalendarColumnIndex"));

         try {
            this.log("Importing details for company - " + this.mExtSysCompanyEVO.toString());
            this.getExtSysCompanyDAO().insert(this.mExtSysCompanyEVO);
         } catch (DuplicateNameValidationException var6) {
            throw new SAXException("Duplicate company:" + this.mExtSysCompanyEVO.getCompanyVisId());
         } catch (ValidationException var7) {
            throw new SAXException(var7.getMessage(), var7);
         }
      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      this.mExtSysCompanyEVO = null;
   }

   public int checkConstraints(int externalSystemId, int maximum, PrintWriter log) {
      return this.getExtSysCompanyDAO().checkConstraintViolations(externalSystemId, maximum, log);
   }

   private ExtSysCompanyDAO getExtSysCompanyDAO() {
      if(this.mExtSysCompanyDAO == null) {
         this.mExtSysCompanyDAO = new ExtSysCompanyDAO();
      }

      return this.mExtSysCompanyDAO;
   }

   public String getExternalCompanyVisId() {
      return this.mExtSysCompanyEVO != null?this.mExtSysCompanyEVO.getCompanyVisId():null;
   }
}
