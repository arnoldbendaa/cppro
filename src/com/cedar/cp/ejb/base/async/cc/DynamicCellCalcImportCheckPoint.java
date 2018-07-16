// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:00
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.cc;

import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.DynamicCellCalcImport;
import java.util.Arrays;
import java.util.List;

public class DynamicCellCalcImportCheckPoint extends AbstractTaskCheckpoint {

   private static final String[] sStepTitle = new String[]{"Validate and create import grid header", "Import text data into work tables", "Store details of existing calcs in update scope", "Perform any import data pre-processing", "Import cell data into calculators", "Update extraneous cell calculators", "Tidy work tables"};
   public static final int sSTEP_PARSE_CREATE_GRID = 0;
   public static final int sSTEP_LOAD_CELL_DATA_WS = 1;
   public static final int sSTEP_SAVE_CALC_DETAILS_IN_SCOPE = 2;
   public static final int sSTEP_COLUMN_PREPROCESS = 3;
   public static final int sSTEP_PROCESS_CELL_DATA = 4;
   public static final int sSTEP_PROCESS_EXTRANEOUS_DATA = 5;
   public static final int sSTEP_DELETE_CELL_DATA_WS = 6;
   private int mStep;
   private int mLinesPreProcessed;
   private int mLinesImported;
   private int mCalcsRemoved;
   private DynamicCellCalcImport mDynamicCellCalcImport;


   public List toDisplay() {
      return Arrays.asList(new String[]{"Dynamic Cell calc import - " + this.getStepText()});
   }

   public int getLinesImported() {
      return this.mLinesImported;
   }

   public void setLinesImported(int linesImported) {
      this.mLinesImported = linesImported;
   }

   public int getStep() {
      return this.mStep;
   }

   public void setStep(int step) {
      this.mStep = step;
   }

   public DynamicCellCalcImport getDynamicCellCalcImport() {
      return this.mDynamicCellCalcImport;
   }

   public void setDynamicCellCalcImport(DynamicCellCalcImport dynamicCellCalcImport) {
      this.mDynamicCellCalcImport = dynamicCellCalcImport;
   }

   public int getCalcsRemoved() {
      return this.mCalcsRemoved;
   }

   public void setCalcsRemoved(int calcsRemoved) {
      this.mCalcsRemoved = calcsRemoved;
   }

   private String getStepText() {
      return this.getStep() > 0 && this.getStep() < 7?sStepTitle[this.getStep()]:"";
   }

   public int getLinesPreProcessed() {
      return this.mLinesPreProcessed;
   }

   public void setLinesPreProcessed(int linesPreProcessed) {
      this.mLinesPreProcessed = linesPreProcessed;
   }

}
