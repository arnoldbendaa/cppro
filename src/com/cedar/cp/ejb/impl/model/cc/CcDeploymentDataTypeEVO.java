// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:37
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc;

import com.cedar.cp.api.model.cc.CcDeploymentDataTypeRef;
import com.cedar.cp.dto.model.cc.CcDeploymentDataTypeCK;
import com.cedar.cp.dto.model.cc.CcDeploymentDataTypePK;
import com.cedar.cp.dto.model.cc.CcDeploymentDataTypeRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentEVO;
import com.cedar.cp.ejb.impl.model.cc.CcDeploymentLineEVO;
import java.io.Serializable;

public class CcDeploymentDataTypeEVO implements Serializable {

   private transient CcDeploymentDataTypePK mPK;
   private int mCcDeploymentDataTypeId;
   private int mCcDeploymentLineId;
   private int mDataTypeId;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public CcDeploymentDataTypeEVO() {}

   public CcDeploymentDataTypeEVO(int newCcDeploymentDataTypeId, int newCcDeploymentLineId, int newDataTypeId) {
      this.mCcDeploymentDataTypeId = newCcDeploymentDataTypeId;
      this.mCcDeploymentLineId = newCcDeploymentLineId;
      this.mDataTypeId = newDataTypeId;
   }

   public CcDeploymentDataTypePK getPK() {
      if(this.mPK == null) {
         this.mPK = new CcDeploymentDataTypePK(this.mCcDeploymentDataTypeId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getCcDeploymentDataTypeId() {
      return this.mCcDeploymentDataTypeId;
   }

   public int getCcDeploymentLineId() {
      return this.mCcDeploymentLineId;
   }

   public int getDataTypeId() {
      return this.mDataTypeId;
   }

   public void setCcDeploymentDataTypeId(int newCcDeploymentDataTypeId) {
      if(this.mCcDeploymentDataTypeId != newCcDeploymentDataTypeId) {
         this.mModified = true;
         this.mCcDeploymentDataTypeId = newCcDeploymentDataTypeId;
         this.mPK = null;
      }
   }

   public void setCcDeploymentLineId(int newCcDeploymentLineId) {
      if(this.mCcDeploymentLineId != newCcDeploymentLineId) {
         this.mModified = true;
         this.mCcDeploymentLineId = newCcDeploymentLineId;
      }
   }

   public void setDataTypeId(int newDataTypeId) {
      if(this.mDataTypeId != newDataTypeId) {
         this.mModified = true;
         this.mDataTypeId = newDataTypeId;
      }
   }

   public void setDetails(CcDeploymentDataTypeEVO newDetails) {
      this.setCcDeploymentDataTypeId(newDetails.getCcDeploymentDataTypeId());
      this.setCcDeploymentLineId(newDetails.getCcDeploymentLineId());
      this.setDataTypeId(newDetails.getDataTypeId());
   }

   public CcDeploymentDataTypeEVO deepClone() {
      CcDeploymentDataTypeEVO cloned = new CcDeploymentDataTypeEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mCcDeploymentDataTypeId = this.mCcDeploymentDataTypeId;
      cloned.mCcDeploymentLineId = this.mCcDeploymentLineId;
      cloned.mDataTypeId = this.mDataTypeId;
      return cloned;
   }

   public void prepareForInsert(CcDeploymentLineEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mCcDeploymentDataTypeId > 0) {
         newKey = true;
         if(parent == null) {
            this.setCcDeploymentDataTypeId(-this.mCcDeploymentDataTypeId);
         } else {
            parent.changeKey(this, -this.mCcDeploymentDataTypeId);
         }
      } else if(this.mCcDeploymentDataTypeId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mCcDeploymentDataTypeId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(CcDeploymentLineEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mCcDeploymentDataTypeId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      return nextKey;
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

   public CcDeploymentDataTypeRef getEntityRef(ModelEVO evoModel, CcDeploymentEVO evoCcDeployment, CcDeploymentLineEVO evoCcDeploymentLine, String entityText) {
      return new CcDeploymentDataTypeRefImpl(new CcDeploymentDataTypeCK(evoModel.getPK(), evoCcDeployment.getPK(), evoCcDeploymentLine.getPK(), this.getPK()), entityText);
   }

   public CcDeploymentDataTypeCK getCK(ModelEVO evoModel, CcDeploymentEVO evoCcDeployment, CcDeploymentLineEVO evoCcDeploymentLine) {
      return new CcDeploymentDataTypeCK(evoModel.getPK(), evoCcDeployment.getPK(), evoCcDeploymentLine.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("CcDeploymentDataTypeId=");
      sb.append(String.valueOf(this.mCcDeploymentDataTypeId));
      sb.append(' ');
      sb.append("CcDeploymentLineId=");
      sb.append(String.valueOf(this.mCcDeploymentLineId));
      sb.append(' ');
      sb.append("DataTypeId=");
      sb.append(String.valueOf(this.mDataTypeId));
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

      sb.append("CcDeploymentDataType: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
