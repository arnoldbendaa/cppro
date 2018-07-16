// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.flatform.model.parser;

import com.cedar.cp.util.Pair;
import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.parser.CellRangeRef;
import com.cedar.cp.util.flatform.model.parser.CellRef;
import com.cedar.cp.util.flatform.model.parser.FormulaEngine;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Formula implements Serializable {

   private String mFormula;
   private Object mParseTree;
   private Set<CellRangeRef> mRefs;
   private List<Pair<CellRef, int[]>> mRefOffsets;
   private String mOriginFormulaText;
   private int mOriginRow;
   private int mOriginColumn;
   private String mOriginWorksheet;
   private int mDeploymentCount;


   public String getFormula() {
      return this.mFormula;
   }

   public void setFormula(String formula) {
      this.mFormula = formula;
   }

   public Object getParseTree() {
      return this.mParseTree;
   }

   public void setParseTree(Object parseTree) {
      this.mParseTree = parseTree;
   }

   public Set<CellRangeRef> getRefs() {
      return this.mRefs;
   }

   public void setRefs(Set<CellRangeRef> refs) {
      this.mRefs = refs;
   }

   public boolean hasNoRefs() {
      return this.mRefs == null || this.mRefs.isEmpty();
   }

   public int getDeploymentCount() {
      return this.mDeploymentCount;
   }

   public void incDeploymentCount() {
      ++this.mDeploymentCount;
   }

   public void decDeploymentCount() {
      --this.mDeploymentCount;
   }

   public String getOriginFormulaText() {
      return this.mOriginFormulaText;
   }

   public void setOriginFormulaText(String originFormulaText) {
      this.mOriginFormulaText = originFormulaText;
   }

   public int getOriginRow() {
      return this.mOriginRow;
   }

   public void setOriginRow(int originRow) {
      this.mOriginRow = originRow;
   }

   public int getOriginColumn() {
      return this.mOriginColumn;
   }

   public void setOriginColumn(int originColumn) {
      this.mOriginColumn = originColumn;
   }

   public String getOriginWorksheet() {
      return this.mOriginWorksheet;
   }

   public void setOriginWorksheet(String originWorksheet) {
      this.mOriginWorksheet = originWorksheet;
   }

   public List<Pair<CellRef, int[]>> getRefOffsets() {
      return this.mRefOffsets;
   }

   public void setRefOffsets(List<Pair<CellRef, int[]>> refOffsets) {
      this.mRefOffsets = refOffsets;
   }

   public boolean renameSheetReference(Worksheet worksheet, String newName) {
      boolean result = false;
      if(this.mRefs != null) {
         Iterator originalFormulaSheetReference = this.mRefs.iterator();

         while(originalFormulaSheetReference.hasNext()) {
            CellRangeRef newFormulaSheetReference = (CellRangeRef)originalFormulaSheetReference.next();
            if(newFormulaSheetReference.renameWorksheetReference(worksheet.getName(), newName)) {
               result = true;
            }
         }
      }

      String originalFormulaSheetReference1 = '\'' + worksheet.getName() + "\'!";
      String newFormulaSheetReference1 = '\'' + newName + "\'!";
      if(this.mOriginFormulaText.indexOf(originalFormulaSheetReference1) != -1) {
         this.mOriginFormulaText = this.mOriginFormulaText.replace(originalFormulaSheetReference1, newFormulaSheetReference1);
         result = true;
      }

      return result;
   }

   public boolean isInsertColumnRelevant(Cell cell, Worksheet targetWorksheet, int columnIndex, int numColumns) {
      if(cell.getColumn() >= columnIndex) {
         return true;
      } else {
         if(this.mRefs != null && !this.mRefs.isEmpty()) {
            Iterator i$ = this.mRefs.iterator();

            while(i$.hasNext()) {
               CellRangeRef ref = (CellRangeRef)i$.next();
               if(ref.isInsertColumnRelevent(cell.getWorksheet(), cell.getRow(), cell.getColumn(), targetWorksheet, columnIndex, numColumns)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean isRemoveColumnRelevant(Cell cell, Worksheet targetWorksheet, int columnIndex, int numColumns) {
      if(cell.getColumn() >= columnIndex) {
         return true;
      } else {
         if(this.mRefs != null && !this.mRefs.isEmpty()) {
            Iterator i$ = this.mRefs.iterator();

            while(i$.hasNext()) {
               CellRangeRef ref = (CellRangeRef)i$.next();
               if(ref.isRemoveColumnRelevent(cell.getWorksheet(), cell.getRow(), cell.getColumn(), targetWorksheet, columnIndex, numColumns)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean isInsertRowRelevant(Cell cell, Worksheet targetWorksheet, int rowIndex, int numRows) {
      if(cell.getRow() - numRows >= rowIndex) {
         return true;
      } else {
         if(this.mRefs != null && !this.mRefs.isEmpty()) {
            Iterator i$ = this.mRefs.iterator();

            while(i$.hasNext()) {
               CellRangeRef ref = (CellRangeRef)i$.next();
               if(ref.isInsertRowRelevent(cell.getWorksheet(), cell.getRow(), cell.getColumn(), targetWorksheet, rowIndex, numRows)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public boolean isRemoveRowRelevant(Cell cell, Worksheet targetWorksheet, int rowIndex, int numRows) {
      if(cell.getRow() >= rowIndex) {
         return true;
      } else {
         if(this.mRefs != null && !this.mRefs.isEmpty()) {
            Iterator i$ = this.mRefs.iterator();

            while(i$.hasNext()) {
               CellRangeRef ref = (CellRangeRef)i$.next();
               if(ref.isRemoveRowRelevent(cell.getWorksheet(), cell.getRow(), cell.getColumn(), targetWorksheet, rowIndex, numRows)) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public void getNewFormulaForColumnInsert(Cell cell, Worksheet targetWorksheet, int startColumnIndex, int numColumns) {
      StringBuilder sb = new StringBuilder();
      int pos = 0;
      int length = this.mOriginFormulaText.length();

      int endOffset;
      for(Iterator newFormula = this.mRefOffsets.iterator(); newFormula.hasNext(); pos = endOffset + 1) {
         Pair e = (Pair)newFormula.next();
         CellRef ref = (CellRef)e.getChild1();
         int[] offsets = (int[])e.getChild2();
         int startOffset = offsets[1];
         endOffset = offsets[3];
         if(ref.getWorksheet(cell.getWorksheet()) == null) {
            return;
         }

         if(ref.isInsertColumnRelevent(cell.getWorksheet(), cell.getRow(), cell.getColumn(), targetWorksheet, startColumnIndex, numColumns)) {
            try {
               ref = (CellRef)ref.clone();
               ref.insertColumn(cell.getWorksheet(), cell.getRow(), cell.getColumn(), targetWorksheet, startColumnIndex, numColumns);
            } catch (CloneNotSupportedException var16) {
               throw new IllegalStateException("clone on cell range ref threw clonenotsupported exception");
            }
         }

         if(pos < startOffset) {
            sb.append(this.mOriginFormulaText.substring(pos, startOffset));
         }

         ref.getSheetText(sb, cell.getWorksheet(), cell.getRow(), cell.getColumn());
      }

      if(pos < length) {
         sb.append(this.mOriginFormulaText.substring(pos));
      }

      String newFormula1 = sb.toString();

      try {
         cell.getWorksheet().updateCellFormula(cell, newFormula1);
      } catch (ParseException var15) {
         throw new IllegalStateException("Failed to re-compile formula after column insert");
      }
   }

   public void getNewFormulaForColumnRemoval(Cell cell, Worksheet targetWorksheet, int startColumnIndex, int numColumns) {
      StringBuilder sb = new StringBuilder();
      int pos = 0;
      int length = this.mOriginFormulaText.length();

      int endOffset;
      for(Iterator newFormula = this.mRefOffsets.iterator(); newFormula.hasNext(); pos = endOffset + 1) {
         Pair e = (Pair)newFormula.next();
         CellRef ref = (CellRef)e.getChild1();
         int[] offsets = (int[])e.getChild2();
         int startOffset = offsets[1];
         endOffset = offsets[3];
         if(ref.getWorksheet(cell.getWorksheet()) == null) {
            return;
         }

         if(ref.isRemoveColumnRelevent(cell.getWorksheet(), cell.getRow(), cell.getColumn(), targetWorksheet, startColumnIndex, numColumns)) {
            try {
               ref = (CellRef)ref.clone();
               ref.removeColumn(cell.getWorksheet(), cell.getRow(), cell.getColumn(), targetWorksheet, startColumnIndex, numColumns);
            } catch (CloneNotSupportedException var16) {
               throw new IllegalStateException("clone on cell range ref threw clonenotsupported exception");
            }
         }

         if(pos < startOffset) {
            sb.append(this.mOriginFormulaText.substring(pos, startOffset));
         }

         ref.getSheetText(sb, cell.getWorksheet(), cell.getRow(), cell.getColumn());
      }

      if(pos < length) {
         sb.append(this.mOriginFormulaText.substring(pos));
      }

      String newFormula1 = sb.toString();

      try {
         cell.getWorksheet().updateCellFormula(cell, newFormula1);
      } catch (ParseException var15) {
         throw new IllegalStateException("Failed to re-compile formula after column insert");
      }
   }

   public void getNewFormulaForRowInsert(Cell cell, Worksheet targetWorksheet, int startRowIndex, int numRows) {
      StringBuilder sb = new StringBuilder();
      int pos = 0;
      int length = this.mOriginFormulaText.length();

      int endOffset;
      for(Iterator newFormula = this.mRefOffsets.iterator(); newFormula.hasNext(); pos = endOffset + 1) {
         Pair e = (Pair)newFormula.next();
         CellRef ref = (CellRef)e.getChild1();
         int[] offsets = (int[])e.getChild2();
         int startOffset = offsets[1];
         endOffset = offsets[3];
         if(ref.getWorksheet(cell.getWorksheet()) == null) {
            return;
         }

         if(ref.isInsertRowRelevent(cell.getWorksheet(), cell.getRow(), cell.getColumn(), targetWorksheet, startRowIndex, numRows)) {
            try {
               ref = (CellRef)ref.clone();
               ref.insertRow(cell.getWorksheet(), cell.getRow(), cell.getColumn(), targetWorksheet, startRowIndex, numRows);
            } catch (CloneNotSupportedException var16) {
               throw new IllegalStateException("clone on cell range ref threw clonenotsupported exception");
            }
         }

         if(pos < startOffset) {
            sb.append(this.mOriginFormulaText.substring(pos, startOffset));
         }

         ref.getSheetText(sb, cell.getWorksheet(), cell.getRow(), cell.getColumn());
      }

      if(pos < length) {
         sb.append(this.mOriginFormulaText.substring(pos));
      }

      String newFormula1 = sb.toString();

      try {
         cell.getWorksheet().updateCellFormula(cell, newFormula1);
      } catch (ParseException var15) {
         throw new IllegalStateException("Failed to re-compile formula after column insert");
      }
   }

   public void getNewFormulaForRowRemoval(Cell cell, Worksheet targetWorksheet, int startRowIndex, int numRows) {
      StringBuilder sb = new StringBuilder();
      int pos = 0;
      int length = this.mOriginFormulaText.length();

      int endOffset;
      for(Iterator newFormula = this.mRefOffsets.iterator(); newFormula.hasNext(); pos = endOffset + 1) {
         Pair e = (Pair)newFormula.next();
         CellRef ref = (CellRef)e.getChild1();
         int[] offsets = (int[])e.getChild2();
         int startOffset = offsets[1];
         endOffset = offsets[3];
         if(ref.getWorksheet(cell.getWorksheet()) == null) {
            return;
         }

         if(ref.isRemoveRowRelevent(cell.getWorksheet(), cell.getRow(), cell.getColumn(), targetWorksheet, startRowIndex, numRows)) {
            try {
               ref = (CellRef)ref.clone();
               ref.removeRow(cell.getWorksheet(), cell.getRow(), cell.getColumn(), targetWorksheet, startRowIndex, numRows);
            } catch (CloneNotSupportedException var16) {
               throw new IllegalStateException("clone on cell range ref threw clonenotsupported exception");
            }
         }

         if(pos < startOffset) {
            sb.append(this.mOriginFormulaText.substring(pos, startOffset));
         }

         ref.getSheetText(sb, cell.getWorksheet(), cell.getRow(), cell.getColumn());
      }

      if(pos < length) {
         sb.append(this.mOriginFormulaText.substring(pos));
      }

      String newFormula1 = sb.toString();

      try {
         cell.getWorksheet().updateCellFormula(cell, newFormula1);
      } catch (ParseException var15) {
         throw new IllegalStateException("Failed to re-compile formula after column insert");
      }
   }

   public static String rewriteFormula(Worksheet sourceSheet, int srcRow, int srcColumn, String origFormula, int targetRow, int targetColumn) throws ParseException {
      FormulaEngine engine = sourceSheet.getWorkbook().getFormulaEngine();
      engine.compile(sourceSheet, origFormula, srcRow, srcColumn);
      ArrayList refOffsets = new ArrayList(engine.getRefOffsets());
      StringBuilder sb = new StringBuilder();
      int pos = 0;
      int length = origFormula.length();

      int endOffset;
      for(Iterator i$ = refOffsets.iterator(); i$.hasNext(); pos = endOffset + 1) {
         Pair refPair = (Pair)i$.next();
         CellRef ref = (CellRef)refPair.getChild1();
         int[] offsets = (int[])refPair.getChild2();
         int startOffset = offsets[1];
         endOffset = offsets[3];
         if(pos < startOffset) {
            sb.append(origFormula.substring(pos, startOffset));
         }

         ref.getSheetText(sb, sourceSheet, targetRow, targetColumn);
      }

      if(pos < length) {
         sb.append(origFormula.substring(pos));
      }

      return sb.toString();
   }

   public String toString() {
      StringBuilder sb = (new StringBuilder()).append("formula:").append(this.mOriginFormulaText);
      Iterator i$;
      if(this.mRefs != null) {
         i$ = this.mRefs.iterator();

         while(i$.hasNext()) {
            CellRangeRef refPair = (CellRangeRef)i$.next();
            sb.append(" ref:" + refPair);
         }
      }

      if(this.mRefOffsets != null) {
         i$ = this.mRefOffsets.iterator();

         while(i$.hasNext()) {
            Pair refPair1 = (Pair)i$.next();
            sb.append(" Ref:" + refPair1.getChild1());
            sb.append(" offset:" + ((int[])refPair1.getChild2())[1]);
         }
      }

      return sb.toString();
   }
}
