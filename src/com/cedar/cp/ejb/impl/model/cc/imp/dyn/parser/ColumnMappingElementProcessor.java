// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:42
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.dyn.ColumnMappingEntry;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcAbstractElementProcessor;
import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ColumnMappingElementProcessor extends DynamicCellCalcAbstractElementProcessor {

   private String mAutoMap;
   private Map<String, ColumnMappingEntry> mColumnMappings = new HashMap();


   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.mColumnMappings.clear();
      this.mAutoMap = null;
      this.mAutoMap = this.getAttributeStringValue(attributes, "autoMap");
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      if(this.mAutoMap != null) {
         this.getParser().setColumnMappingAutoMap(this.mAutoMap);
      }

      this.getParser().setColumnMappings(this.mColumnMappings);
   }

   public String getAutoMap() {
      return this.mAutoMap;
   }

   public void setAutoMap(String autoMap) {
      this.mAutoMap = autoMap;
   }

   public Map<String, ColumnMappingEntry> getColumnMappings() {
      return this.mColumnMappings;
   }

   public void setColumnMappings(Map<String, ColumnMappingEntry> columnMappings) {
      this.mColumnMappings = columnMappings;
   }
}
