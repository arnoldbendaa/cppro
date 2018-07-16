// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemDAO;
import com.cedar.cp.ejb.impl.extsys.xmlimp.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExternalSystemProcessor extends AbstractElementProcessor {

   private ExternalSystemDAO mExternalSystemDAO;
   private int mExternalSystemId;
   private String mExternalSystemVisId;
   private String mExtractDateTime;


   public ExternalSystemProcessor(ExternalSystemXMLDefLoader loader) {
      super(loader);
   }

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if(!qName.equalsIgnoreCase("genericImport")) {
         throw new SAXException("Expected generic-import element");
      } else {
         this.mExternalSystemVisId = this.getAttributeStringValue(attributes, "externalSystemVisId");
         this.log("Importing into external system identfied by visual id : " + this.mExternalSystemVisId);
         this.mExtractDateTime = this.getAttributeStringValue(attributes, "extractDateTime");
         this.mExternalSystemId = this.getExternalSystemDAO().lookupExternalSystemId(this.mExternalSystemVisId);
         if(this.mExternalSystemId < 0) {
            throw new SAXException("External system [" + this.mExternalSystemVisId + "] not found.");
         } else {
            this.getExternalSystemDAO().deleteExternalSystemDefintion(this.mExternalSystemId);
         }
      }
   }

   public void processEndElement(String namespace, String localname, String type) throws SAXException {
      try {
         int e = this.getExternalSystemDefLoader().checkConstraints(this.mExternalSystemId, 500);
         this.getExternalSystemDefLoader().setConstraintsViolated(e > 0);
      } catch (ValidationException var5) {
         throw new SAXException("checkConstraints failed:", var5);
      }

      this.mExternalSystemId = 0;
      this.mExtractDateTime = null;
      this.mExternalSystemVisId = null;
   }

   private ExternalSystemDAO getExternalSystemDAO() {
      if(this.mExternalSystemDAO == null) {
         this.mExternalSystemDAO = new ExternalSystemDAO();
      }

      return this.mExternalSystemDAO;
   }

   public int getExternalSystemId() {
      return this.mExternalSystemId;
   }
}
