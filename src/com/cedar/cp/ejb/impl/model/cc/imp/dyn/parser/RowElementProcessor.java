// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcAbstractElementProcessor;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class RowElementProcessor extends DynamicCellCalcAbstractElementProcessor {

   private Map<String, String> mRowMap = new HashMap();


   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.mRowMap.clear();
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {}

   public void addValue(String id, String value) {
      this.mRowMap.put(id, value);
   }
}
