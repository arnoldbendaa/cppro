// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.parser;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcImport;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcUpdateType;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.CellCalcAbstractElementProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class CellCalcElementProcessor extends CellCalcAbstractElementProcessor {

   private CellCalcImport mCellCalcImport;


   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.mCellCalcImport = new CellCalcImport();
      String updateTypeStr = this.getAttributeStringValue(attributes, "updateType");
      CellCalcUpdateType updateType = CellCalcUpdateType.parse(updateTypeStr);
      if(updateType == null) {
         throw new SAXException("Failed to parse update type:" + updateTypeStr);
      } else {
         this.mCellCalcImport.setUpdateType(updateType);
      }
   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      try {
         this.getEngine().processCellCalcUpdate(this.mCellCalcImport);
      } catch (ValidationException var5) {
         throw new SAXException(var5);
      }

      this.mCellCalcImport = null;
   }

   public CellCalcImport getCellCalcImport() {
      return this.mCellCalcImport;
   }
}
