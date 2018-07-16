// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DeploymentsElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcAbstractElementProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class DeploymentElementProcessor extends DynamicCellCalcAbstractElementProcessor {

   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      String deployment = this.getAttributeStringValue(attributes, "visId");
      DeploymentsElementProcessor deploymentsElementProcessor = (DeploymentsElementProcessor)this.getParser().findElementProcessor(DeploymentsElementProcessor.class);
      if(deploymentsElementProcessor == null) {
         throw new IllegalStateException("Failed to locate deployments element processor");
      } else {
         deploymentsElementProcessor.getDeployments().add(deployment);
      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {}
}
