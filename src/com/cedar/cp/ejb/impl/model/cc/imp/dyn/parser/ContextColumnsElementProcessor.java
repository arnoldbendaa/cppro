// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.dyn.parser.DynamicCellCalcAbstractElementProcessor;
import com.cedar.cp.util.Pair;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ContextColumnsElementProcessor extends DynamicCellCalcAbstractElementProcessor {

   private List<Pair<String, String>> mContextColumnList = new ArrayList();
   private String mDataTypeContextColumn;


   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.mContextColumnList.clear();
      this.mDataTypeContextColumn = null;
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      this.getParser().registerContextColumns(this.mContextColumnList, this.mDataTypeContextColumn);
   }

   public List<Pair<String, String>> getContextColumnList() {
      return this.mContextColumnList;
   }

   public void setContextColumnList(List<Pair<String, String>> contextColumnList) {
      this.mContextColumnList = contextColumnList;
   }

   public String getDataTypeContextColumn() {
      return this.mDataTypeContextColumn;
   }

   public void setDataTypeContextColumn(String dataTypeContextColumn) {
      this.mDataTypeContextColumn = dataTypeContextColumn;
   }
}
