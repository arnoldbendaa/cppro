// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.ejb.impl.extsys.ExtSysCalElementDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysCalElementEVO;
import com.cedar.cp.ejb.impl.extsys.xmlimp.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalCalendarYearProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import java.io.PrintWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExternalCalendarElementProcessor extends AbstractElementProcessor {

   private ExtSysCalElementEVO mExtSysCalElementEVO;
   private ExtSysCalElementDAO mExtSysCalElementDAO;


   public ExternalCalendarElementProcessor(ExternalSystemXMLDefLoader loader) {
      super(loader);
   }

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if(!qName.equalsIgnoreCase("calendarElement")) {
         throw new SAXException("Expected element \'calendarElement\'");
      } else {
         this.mExtSysCalElementEVO = new ExtSysCalElementEVO();
         this.mExtSysCalElementEVO.setExternalSystemId(this.getExternalSystemDefLoader().getExternalSystemId());
         this.mExtSysCalElementEVO.setCompanyVisId(this.getExternalSystemDefLoader().getExternalCompanyVisId());
         this.mExtSysCalElementEVO.setCalElementVisId(this.getAttributeStringValue(attributes, "calendarElementVisId"));
         this.mExtSysCalElementEVO.setDescription(this.getAttributeStringValue(attributes, "description"));
         this.mExtSysCalElementEVO.setCalendarYearVisId(this.getExternalSystemDefLoader().getExternalCalendarYearVisId());
         ExternalCalendarYearProcessor processor = (ExternalCalendarYearProcessor)this.getExternalSystemDefLoader().findElementProcessor(ExternalCalendarYearProcessor.class);
         this.mExtSysCalElementEVO.setIdx(this.getAttributeIntValue(attributes, "period"));
         processor.addToBatch(this.mExtSysCalElementEVO);
      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      this.mExtSysCalElementEVO = null;
   }

   public int checkConstraints(int externalSystemId, int maximum, PrintWriter log) {
      return this.getExtSysCalElementDAO().checkConstraintViolations(externalSystemId, maximum, log);
   }

   public String getExternalCalElementVisId() {
      return this.mExtSysCalElementEVO != null?this.mExtSysCalElementEVO.getCalElementVisId():null;
   }

   public ExtSysCalElementDAO getExtSysCalElementDAO() {
      if(this.mExtSysCalElementDAO == null) {
         this.mExtSysCalElementDAO = new ExtSysCalElementDAO();
      }

      return this.mExtSysCalElementDAO;
   }
}
