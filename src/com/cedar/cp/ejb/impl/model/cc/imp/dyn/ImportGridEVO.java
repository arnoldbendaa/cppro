// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:42
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn;

import com.cedar.cp.api.model.cc.imp.dyn.ImportGridRef;
import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridCK;
import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridPK;
import com.cedar.cp.dto.model.cc.imp.dyn.ImportGridRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.Serializable;

public class ImportGridEVO implements Serializable {

   private transient ImportGridPK mPK;
   private int mModelId;
   private int mGridId;
   private int mUserId;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public ImportGridEVO() {}

   public ImportGridEVO(int newModelId, int newGridId, int newUserId) {
      this.mModelId = newModelId;
      this.mGridId = newGridId;
      this.mUserId = newUserId;
   }

   public ImportGridPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ImportGridPK(this.mModelId, this.mGridId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getGridId() {
      return this.mGridId;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
         this.mPK = null;
      }
   }

   public void setGridId(int newGridId) {
      if(this.mGridId != newGridId) {
         this.mModified = true;
         this.mGridId = newGridId;
         this.mPK = null;
      }
   }

   public void setUserId(int newUserId) {
      if(this.mUserId != newUserId) {
         this.mModified = true;
         this.mUserId = newUserId;
      }
   }

   public void setDetails(ImportGridEVO newDetails) {
      this.setModelId(newDetails.getModelId());
      this.setGridId(newDetails.getGridId());
      this.setUserId(newDetails.getUserId());
   }

   public ImportGridEVO deepClone() {
      ImportGridEVO cloned = new ImportGridEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mModelId = this.mModelId;
      cloned.mGridId = this.mGridId;
      cloned.mUserId = this.mUserId;
      return cloned;
   }

   public void prepareForInsert(ModelEVO parent) {
      boolean newKey = this.insertPending();
   }

   public int getInsertCount(int startCount) {
      return startCount;
   }

   public int assignNextKey(ModelEVO parent, int startKey) {
      return startKey;
   }

   public void setInsertPending() {
      this.mInsertPending = true;
   }

   public boolean insertPending() {
      return this.mInsertPending;
   }

   public void setDeletePending() {
      this.mDeletePending = true;
   }

   public boolean deletePending() {
      return this.mDeletePending;
   }

   protected void reset() {
      this.mModified = false;
      this.mInsertPending = false;
   }

   public ImportGridRef getEntityRef(ModelEVO evoModel, String entityText) {
      return new ImportGridRefImpl(new ImportGridCK(evoModel.getPK(), this.getPK()), entityText);
   }

   public ImportGridCK getCK(ModelEVO evoModel) {
      return new ImportGridCK(evoModel.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("GridId=");
      sb.append(String.valueOf(this.mGridId));
      sb.append(' ');
      sb.append("UserId=");
      sb.append(String.valueOf(this.mUserId));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
      }

      if(this.mInsertPending) {
         sb.append("insertPending ");
      }

      if(this.mDeletePending) {
         sb.append("deletePending ");
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

      sb.append("ImportGrid: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
