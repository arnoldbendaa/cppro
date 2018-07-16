// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcImport;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.CellCalcAbstractElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.RowElementProcessor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class AddressElementProcessor extends CellCalcAbstractElementProcessor {

   private Map<String, String> mColumns = new HashMap();
   private Integer mIndex;


   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      RowElementProcessor rowElementProcessor = (RowElementProcessor)this.getEngine().findElementProcessor(RowElementProcessor.class);
      if(rowElementProcessor == null) {
         CellCalcImport cellCalcImport = this.getEngine().getCellCalcImport();
         TreeMap dimMap = new TreeMap();

         for(int entry = 0; entry < attributes.getLength(); ++entry) {
            String name = attributes.getQName(entry);
            String value = attributes.getValue(entry);
            if(name.equals("dataType")) {
               cellCalcImport.setDataType(String.valueOf(value));
            } else if(name.startsWith("dim")) {
               dimMap.put(name, String.valueOf(value));
            }
         }

         for(Entry var11 = dimMap.firstEntry(); var11 != null; var11 = dimMap.higherEntry(var11.getKey())) {
            cellCalcImport.getAddress().add((String) var11.getValue());
         }
      } else {
         this.mIndex = this.getOptionalAttributeIntValue(attributes, "index");
      }

   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      RowElementProcessor rowElementProcessor = (RowElementProcessor)this.getEngine().findElementProcessor(RowElementProcessor.class);
      if(rowElementProcessor != null) {
         rowElementProcessor.getCellCalcRowUpdate().getAddress().putAll(this.mColumns);
         rowElementProcessor.getCellCalcRowUpdate().setIndex(this.mIndex);
      }

      this.mColumns.clear();
      this.mIndex = null;
   }

   public void addColumn(String id, String value) {
      this.mColumns.put(id, value);
   }

   public void setDimAddress(int dimIndex, String address) {
      CellCalcImport cellCalcImport = this.getEngine().getCellCalcImport();
      List addrList = cellCalcImport.getAddress();

      while(addrList.size() <= dimIndex) {
         addrList.add("");
      }

      addrList.set(dimIndex, address);
   }

   public void setDataType(String dataType) {
      CellCalcImport cellCalcImport = this.getEngine().getCellCalcImport();
      cellCalcImport.setDataType(dataType);
   }

   public void setIndex(Integer index) {
      this.mIndex = index;
   }
}
