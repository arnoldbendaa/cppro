// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.clipboard;

import com.cedar.cp.util.flatform.model.clipboard.CellData;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class CellSelection implements Transferable, ClipboardOwner {

   private static DataFlavor sDataFlavor = new DataFlavor(CellSelection.class, "Worksheet cells");
   private String mWorksheet;
   private int mStartRow;
   private int mStartColumn;
   private int mEndRow;
   private int mEndColumn;
   private List<CellData> mCellData;


   public CellSelection(String worksheet, int startRow, int startColumn, int endRow, int endColumn, List<CellData> cellData) {
      this.mWorksheet = worksheet;
      this.mStartRow = startRow;
      this.mStartColumn = startColumn;
      this.mEndRow = endRow;
      this.mEndColumn = endColumn;
      this.mCellData = cellData;
   }

   public DataFlavor[] getTransferDataFlavors() {
      return new DataFlavor[]{getCellSelectionDataFlavor(), DataFlavor.stringFlavor};
   }

   public boolean isDataFlavorSupported(DataFlavor flavor) {
      return flavor.equals(getCellSelectionDataFlavor());
   }

   public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
      if(flavor.equals(getCellSelectionDataFlavor())) {
         return this;
      } else if(flavor.equals(DataFlavor.stringFlavor)) {
         return this.queryStringFlavor();
      } else {
         throw new UnsupportedFlavorException(flavor);
      }
   }

   public void lostOwnership(Clipboard clipboard, Transferable contents) {}

   public static DataFlavor getCellSelectionDataFlavor() {
      return sDataFlavor;
   }

   public String getWorksheet() {
      return this.mWorksheet;
   }

   public int getStartRow() {
      return this.mStartRow;
   }

   public int getStartColumn() {
      return this.mStartColumn;
   }

   public int getEndRow() {
      return this.mEndRow;
   }

   public int getEndColumn() {
      return this.mEndColumn;
   }

   public List<CellData> getCellData() {
      return this.mCellData;
   }

   public CellData getCell(int row, int column) {
      Iterator i$ = this.mCellData.iterator();

      CellData cellData;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         cellData = (CellData)i$.next();
      } while(cellData.getRow() != row || cellData.getColumn() != column);

      return cellData;
   }

   private String queryStringFlavor() {
      StringBuilder sb = new StringBuilder();

      for(int row = this.mStartRow; row <= this.mEndRow; ++row) {
         for(int col = this.mStartColumn; col <= this.mEndColumn; ++col) {
            CellData cellData = this.getCell(row, col);
            if(cellData != null) {
               sb.append(cellData.getText());
            }

            sb.append('\t');
         }

         sb.append('\r');
      }

      return sb.toString();
   }

}
