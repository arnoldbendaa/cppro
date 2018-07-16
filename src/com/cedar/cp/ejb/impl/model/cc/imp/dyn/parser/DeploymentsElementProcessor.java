// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcAbstractElementProcessor;
import java.util.HashSet;
import java.util.Set;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class DeploymentsElementProcessor extends DynamicCellCalcAbstractElementProcessor {

   private Set<String> mDeployments = new HashSet();


   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.mDeployments.clear();
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      this.getParser().setDeployments(this.mDeployments);
   }

   public Set<String> getDeployments() {
      return this.mDeployments;
   }
}
