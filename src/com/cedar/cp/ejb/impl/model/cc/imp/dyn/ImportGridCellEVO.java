// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:42
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn;

import com.cedar.cp.api.model.cc.imp.dyn.ImportGridCellRef;
import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCellPK;
import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCellRefImpl;
import java.io.Serializable;

public class ImportGridCellEVO implements Serializable {

   private transient ImportGridCellPK mPK;
   private int mGridId;
   private int mRowNumber;
   private int mColumnNumber;
   private String mUserData;
   private boolean mModified;


   public ImportGridCellEVO() {}

   public ImportGridCellEVO(int newGridId, int newRowNumber, int newColumnNumber, String newUserData) {
      this.mGridId = newGridId;
      this.mRowNumber = newRowNumber;
      this.mColumnNumber = newColumnNumber;
      this.mUserData = newUserData;
   }

   public ImportGridCellPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ImportGridCellPK(this.mGridId, this.mRowNumber, this.mColumnNumber);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getGridId() {
      return this.mGridId;
   }

   public int getRowNumber() {
      return this.mRowNumber;
   }

   public int getColumnNumber() {
      return this.mColumnNumber;
   }

   public String getUserData() {
      return this.mUserData;
   }

   public void setGridId(int newGridId) {
      if(this.mGridId != newGridId) {
         this.mModified = true;
         this.mGridId = newGridId;
         this.mPK = null;
      }
   }

   public void setRowNumber(int newRowNumber) {
      if(this.mRowNumber != newRowNumber) {
         this.mModified = true;
         this.mRowNumber = newRowNumber;
         this.mPK = null;
      }
   }

   public void setColumnNumber(int newColumnNumber) {
      if(this.mColumnNumber != newColumnNumber) {
         this.mModified = true;
         this.mColumnNumber = newColumnNumber;
         this.mPK = null;
      }
   }

   public void setUserData(String newUserData) {
      if(this.mUserData != null && newUserData == null || this.mUserData == null && newUserData != null || this.mUserData != null && newUserData != null && !this.mUserData.equals(newUserData)) {
         this.mUserData = newUserData;
         this.mModified = true;
      }

   }

   public void setDetails(ImportGridCellEVO newDetails) {
      this.setGridId(newDetails.getGridId());
      this.setRowNumber(newDetails.getRowNumber());
      this.setColumnNumber(newDetails.getColumnNumber());
      this.setUserData(newDetails.getUserData());
   }

   public ImportGridCellEVO deepClone() {
      ImportGridCellEVO cloned = new ImportGridCellEVO();
      cloned.mModified = this.mModified;
      cloned.mGridId = this.mGridId;
      cloned.mRowNumber = this.mRowNumber;
      cloned.mColumnNumber = this.mColumnNumber;
      if(this.mUserData != null) {
         cloned.mUserData = this.mUserData;
      }

      return cloned;
   }

   public void prepareForInsert() {
      boolean newKey = false;
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(int startKey) {
      return startKey;
   }

   protected void reset() {
      this.mModified = false;
   }

   public ImportGridCellRef getEntityRef(String entityText) {
      return new ImportGridCellRefImpl(this.getPK(), entityText);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("GridId=");
      sb.append(String.valueOf(this.mGridId));
      sb.append(' ');
      sb.append("RowNumber=");
      sb.append(String.valueOf(this.mRowNumber));
      sb.append(' ');
      sb.append("ColumnNumber=");
      sb.append(String.valueOf(this.mColumnNumber));
      sb.append(' ');
      sb.append("UserData=");
      sb.append(String.valueOf(this.mUserData));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
      }

      return sb.toString();
   }

   public String print() {
      return this.print(0);
   }

   public String print(int indent) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < indent; ++i) {
         sb.append(' ');
      }

      sb.append("ImportGridCell: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
