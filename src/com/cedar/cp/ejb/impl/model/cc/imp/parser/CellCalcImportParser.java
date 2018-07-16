// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.parser;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcImport;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcImportProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.AbstractImportParser;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.AddressElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.CellCalcAbstractElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.CellCalcElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.CellCalcImportElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.CellCalcImportParser$MyContextHandler;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.ColumnElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.DataElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.DataTypeElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.DimElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.IdElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.IndexElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.RowElementProcessor;
import com.cedar.cp.ejb.impl.model.cc.imp.parser.ValueElementProcessor;
import org.xml.sax.ContentHandler;

public class CellCalcImportParser extends AbstractImportParser {

   private CellCalcImportProcessor mCellCalcImportProcessor;


   public CellCalcImportParser(CellCalcImportProcessor importProcessor) {
      for(int i = 0; i < 10; ++i) {
         this.registerElementProcessor("dim" + i, DimElementProcessor.class);
      }

      this.registerElementProcessor("dataType", DataTypeElementProcessor.class);
      this.registerElementProcessor("value", ValueElementProcessor.class);
      this.registerElementProcessor("id", IdElementProcessor.class);
      this.registerElementProcessor("data", DataElementProcessor.class);
      this.registerElementProcessor("column", ColumnElementProcessor.class);
      this.registerElementProcessor("row", RowElementProcessor.class);
      this.registerElementProcessor("address", AddressElementProcessor.class);
      this.registerElementProcessor("cell-calc-import", CellCalcImportElementProcessor.class);
      this.registerElementProcessor("cell-calc", CellCalcElementProcessor.class);
      this.registerElementProcessor("index", IndexElementProcessor.class);
      this.mCellCalcImportProcessor = importProcessor;
   }

   public ContentHandler getContentHandler() {
      return new CellCalcImportParser$MyContextHandler(this);
   }

   private CellCalcAbstractElementProcessor getProcessrForElement(String elementName) throws ValidationException, Exception {
      Class c = this.getElementProcessor(elementName);
      return c != null?(CellCalcAbstractElementProcessor)c.newInstance():null;
   }

   public CellCalcImport getCellCalcImport() {
      CellCalcElementProcessor processor = (CellCalcElementProcessor)this.findElementProcessor(CellCalcElementProcessor.class);
      if(processor == null) {
         throw new IllegalStateException("Failed to locate CellCalcElementProcessor in context processing stack");
      } else {
         return processor.getCellCalcImport();
      }
   }

   public void processCellCalcUpdate(CellCalcImport cellCalc) throws ValidationException {
      try {
         cellCalc.setModel(this.getModel());
         cellCalc.setFinanceCube(this.getFinanceCube());
         cellCalc.setBudgetCycle(this.getBudgetCycle());
         if(this.mCellCalcImportProcessor.processCalcUpdate(cellCalc) && this.getPrintWriter() != null) {
            this.getPrintWriter().print("Processed cell calc update for address:" + cellCalc.getFormattedAddress());
         }

      } catch (ValidationException var3) {
         if(this.getPrintWriter() != null) {
            this.getPrintWriter().println("ERROR processing cell calc update for address:" + cellCalc.getFormattedAddress());
            this.getPrintWriter().println(var3.getMessage());
         }

         throw var3;
      } catch (Exception var4) {
         var4.printStackTrace();
         throw new RuntimeException(var4.getMessage(), var4);
      }
   }

   public void startBatch() {
      this.mCellCalcImportProcessor.startBatch();
   }

   public void endBatch() {
      this.mCellCalcImportProcessor.endBatch();
   }

   public String getEntityName() {
      return this.getClass().getName();
   }

   // $FF: synthetic method
   static CellCalcAbstractElementProcessor accessMethod000(CellCalcImportParser x0, String x1) throws ValidationException, Exception {
      return x0.getProcessrForElement(x1);
   }
}
