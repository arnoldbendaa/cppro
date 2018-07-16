// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:39
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extsys.xmlimp;

import com.cedar.cp.ejb.impl.extsys.ExtSysHierElemFeedDAO;
import com.cedar.cp.ejb.impl.extsys.ExtSysHierElemFeedEVO;
import com.cedar.cp.ejb.impl.extsys.xmlimp.AbstractElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalHierarchyElementProcessor;
import com.cedar.cp.ejb.impl.extsys.xmlimp.ExternalSystemXMLDefLoader;
import java.io.PrintWriter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ExternalHierarchyElementFeedProcessor extends AbstractElementProcessor {

   private ExtSysHierElemFeedEVO mExtSysHierElemFeedEVO;
   private ExtSysHierElemFeedDAO mExtSysHierElemFeedDAO;


   public ExternalHierarchyElementFeedProcessor(ExternalSystemXMLDefLoader loader) {
      super(loader);
   }

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      if(!qName.equalsIgnoreCase("hierarchyElementFeed")) {
         throw new SAXException("Expected element \'hierarchy\'");
      } else {
         this.mExtSysHierElemFeedEVO = new ExtSysHierElemFeedEVO();
         this.mExtSysHierElemFeedEVO.setExternalSystemId(this.getExternalSystemDefLoader().getExternalSystemId());
         this.mExtSysHierElemFeedEVO.setCompanyVisId(this.getExternalSystemDefLoader().getExternalCompanyVisId());
         this.mExtSysHierElemFeedEVO.setLedgerVisId(this.getExternalSystemDefLoader().getExternalLedgerVisId());
         this.mExtSysHierElemFeedEVO.setDimensionVisId(this.getExternalSystemDefLoader().getExternalDimensionVisId());
         this.mExtSysHierElemFeedEVO.setHierarchyVisId(this.getExternalSystemDefLoader().getExternalHierarchyVisId());
         this.mExtSysHierElemFeedEVO.setHierElementVisId(this.getExternalSystemDefLoader().getExternalHierarchyElementVisId());
         this.mExtSysHierElemFeedEVO.setDimElementVisId(this.getAttributeStringValue(attributes, "dimensionElementVisId"));
         ExternalHierarchyElementProcessor processor = (ExternalHierarchyElementProcessor)this.getExternalSystemDefLoader().findElementProcessor(ExternalHierarchyElementProcessor.class);
         this.mExtSysHierElemFeedEVO.setIdx(processor.getChildIndex());
         processor.addToBatch(this.mExtSysHierElemFeedEVO);
      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      this.mExtSysHierElemFeedEVO = null;
   }

   public int checkConstraints(int externalSystemId, int maximum, PrintWriter log) {
      return this.getExtSysHierElemFeedDAO().checkConstraintViolations(externalSystemId, maximum, log);
   }

   public ExtSysHierElemFeedDAO getExtSysHierElemFeedDAO() {
      if(this.mExtSysHierElemFeedDAO == null) {
         this.mExtSysHierElemFeedDAO = new ExtSysHierElemFeedDAO();
      }

      return this.mExtSysHierElemFeedDAO;
   }
}
