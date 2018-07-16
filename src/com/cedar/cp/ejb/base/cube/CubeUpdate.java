// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube;

import com.cedar.cp.ejb.base.cube.CellCalc;
import com.cedar.cp.ejb.base.cube.CellCalcLink;
import com.cedar.cp.ejb.base.cube.CellNote;
import com.cedar.cp.ejb.base.cube.CellPosting;
import com.cedar.cp.ejb.base.cube.FormNote;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CubeUpdate implements Serializable {

   private int mUserId;
   private int mFinanceCubeId;
   private int mBudgetCycleId;
   private int mUpdateType;
   private int mActivityUpdateTypeId = -1;
   private boolean mAbsoluteValues;
   private List mCells = new ArrayList();
   private List mCellNotes = new ArrayList();
   private List mCellCalcLinks = new ArrayList();
   private List mCellCalcs = new ArrayList();
   private List mFormNotes = new ArrayList();
   private boolean mInvertNumbers;
   private String mExcludedDataTypes;


   public int getUserId() {
      return this.mUserId;
   }

   public void setUserId(int user) {
      this.mUserId = user;
   }

   public void setFinanceCubeId(int id) {
      this.mFinanceCubeId = id;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public void addCell(CellPosting c) {
      this.mCells.add(c);
   }

   public List getCells() {
      return this.mCells;
   }

   public int getCellCount() {
      return this.mCells.size();
   }

   public void addCellNote(CellNote c) {
      this.mCellNotes.add(c);
   }

   public List getCellNotes() {
      return this.mCellNotes;
   }

   public int getCellNoteCount() {
      return this.mCellNotes.size();
   }

   public void addCellCalcLink(CellCalcLink cellCalcLink) {
      this.mCellCalcLinks.add(cellCalcLink);
   }

   public List getCellCalcLinks() {
      return this.mCellCalcLinks;
   }

   public int getCellCalcLinkCount() {
      return this.mCellCalcLinks.size();
   }

   public void addCellCalc(CellCalc cellCalc) {
      this.mCellCalcs.add(cellCalc);
   }

   public List getCellCalcs() {
      return this.mCellCalcs;
   }

   public int getCellCalcsCount() {
      return this.mCellCalcs.size();
   }

   public boolean isAbsoluteValues() {
      return this.mAbsoluteValues;
   }

   public void setAbsoluteValues(boolean absoluteValues) {
      this.mAbsoluteValues = absoluteValues;
   }

   public int getUpdateType() {
      return this.mUpdateType;
   }

   public void setUpdateType(int updateType) {
      this.mUpdateType = updateType;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public void setBudgetCycleId(int budgetCycleId) {
      this.mBudgetCycleId = budgetCycleId;
   }

   public List getFormNotes() {
      return this.mFormNotes;
   }

   public void setFormNotes(List formNotes) {
      this.mFormNotes = formNotes;
   }

   public void addFormNote(FormNote note) {
      this.mFormNotes.add(note);
   }

   public int getFormNotesCount() {
      return this.mFormNotes.size();
   }

   public int getActivityUpdateTypeId() {
      return this.mActivityUpdateTypeId;
   }

   public void setActivityUpdateTypeId(int activityUpdateTypeId) {
      this.mActivityUpdateTypeId = activityUpdateTypeId;
   }

    public boolean isInvertNumbers() {
        return mInvertNumbers;
    }
    
    public void setInvertNumbers(boolean mInvertNumbers) {
        this.mInvertNumbers = mInvertNumbers;
    }

    public String getExcludedDataTypes() {
        return mExcludedDataTypes;
    }

    public void setExcludedDataTypes(String mExcludedDataTypes) {
        if (mExcludedDataTypes.equals("null")) {
            this.mExcludedDataTypes = null;
        } else {
            this.mExcludedDataTypes = mExcludedDataTypes;
        }
    }
}
