// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.parser;

import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcRowUpdate;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcUpdateType;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.CellCalcAbstractElementProcessor;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class RowElementProcessor extends CellCalcAbstractElementProcessor {

   private CellCalcRowUpdate mCellCalcRowUpdate;


   public void processStartElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
      this.mCellCalcRowUpdate = new CellCalcRowUpdate();
      String updateType = this.getAttributeStringValue(attributes, "updateType");
      if(updateType != null) {
         this.mCellCalcRowUpdate.setUpdateType(CellCalcUpdateType.parse(updateType));
      }

   }

   public void processEndElement(String uri, String localName, String qName) throws SAXException {
      this.getEngine().getCellCalcImport().getRowUpdates().add(this.mCellCalcRowUpdate);
      this.mCellCalcRowUpdate = null;
   }

   public CellCalcRowUpdate getCellCalcRowUpdate() {
      return this.mCellCalcRowUpdate;
   }
}
