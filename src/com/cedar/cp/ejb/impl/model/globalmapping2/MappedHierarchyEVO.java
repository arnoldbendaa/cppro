package com.cedar.cp.ejb.impl.model.globalmapping2;

import com.cedar.cp.api.model.globalmapping2.MappedHierarchyRef;
import com.cedar.cp.dto.model.globalmapping2.MappedHierarchyCK;
import com.cedar.cp.dto.model.globalmapping2.MappedHierarchyPK;
import com.cedar.cp.dto.model.globalmapping2.MappedHierarchyRefImpl;
import com.cedar.cp.ejb.impl.model.globalmapping2.MappedDimensionEVO;
import com.cedar.cp.ejb.impl.model.globalmapping2.GlobalMappedModel2EVO;
import java.io.Serializable;
import java.sql.Timestamp;

public class MappedHierarchyEVO implements Serializable {

   private transient MappedHierarchyPK mPK;
   private int mMappedHierarchyId;
   private int mMappedDimensionId;
   private int mHierarchyId;
   private String mHierarchyVisId1;
   private String mHierarchyVisId2;
   private String mTmpHierarchyVisId;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   private String mCompanies;


   public MappedHierarchyEVO() {}

   public MappedHierarchyEVO(int newMappedHierarchyId, int newMappedDimensionId, int newHierarchyId, String newHierarchyVisId1, String newHierarchyVisId2, String newTmpHierarchyVisId) {
      this.mMappedHierarchyId = newMappedHierarchyId;
      this.mMappedDimensionId = newMappedDimensionId;
      this.mHierarchyId = newHierarchyId;
      this.mHierarchyVisId1 = newHierarchyVisId1;
      this.mHierarchyVisId2 = newHierarchyVisId2;
      this.mTmpHierarchyVisId = newTmpHierarchyVisId;
      this.mCompanies = "";
   }
   
	public MappedHierarchyEVO(int newMappedHierarchyId, int newMappedDimensionId, int newHierarchyId, String newHierarchyVisId1, String newHierarchyVisId2, String newTmpHierarchyVisId, String companies) {
		this.mMappedHierarchyId = newMappedHierarchyId;
		this.mMappedDimensionId = newMappedDimensionId;
		this.mHierarchyId = newHierarchyId;
		this.mHierarchyVisId1 = newHierarchyVisId1;
		this.mHierarchyVisId2 = newHierarchyVisId2;
		this.mTmpHierarchyVisId = newTmpHierarchyVisId;
		this.mCompanies = companies;
	}

   public MappedHierarchyPK getPK() {
      if(this.mPK == null) {
         this.mPK = new MappedHierarchyPK(this.mMappedHierarchyId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getMappedHierarchyId() {
      return this.mMappedHierarchyId;
   }

   public int getMappedDimensionId() {
      return this.mMappedDimensionId;
   }

   public int getHierarchyId() {
      return this.mHierarchyId;
   }

   public String getHierarchyVisId1() {
      return this.mHierarchyVisId1;
   }

   public String getHierarchyVisId2() {
      return this.mHierarchyVisId2;
   }

   public String getTmpHierarchyVisId() {
      return this.mTmpHierarchyVisId;
   }
   
   public String getCompanies() {
	   return this.mCompanies;
   }

   public int getUpdatedByUserId() {
      return this.mUpdatedByUserId;
   }

   public Timestamp getUpdatedTime() {
      return this.mUpdatedTime;
   }

   public Timestamp getCreatedTime() {
      return this.mCreatedTime;
   }

   public void setMappedHierarchyId(int newMappedHierarchyId) {
      if(this.mMappedHierarchyId != newMappedHierarchyId) {
         this.mModified = true;
         this.mMappedHierarchyId = newMappedHierarchyId;
         this.mPK = null;
      }
   }

   public void setMappedDimensionId(int newMappedDimensionId) {
      if(this.mMappedDimensionId != newMappedDimensionId) {
         this.mModified = true;
         this.mMappedDimensionId = newMappedDimensionId;
      }
   }

   public void setHierarchyId(int newHierarchyId) {
      if(this.mHierarchyId != newHierarchyId) {
         this.mModified = true;
         this.mHierarchyId = newHierarchyId;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setHierarchyVisId1(String newHierarchyVisId1) {
      if(this.mHierarchyVisId1 != null && newHierarchyVisId1 == null || this.mHierarchyVisId1 == null && newHierarchyVisId1 != null || this.mHierarchyVisId1 != null && newHierarchyVisId1 != null && !this.mHierarchyVisId1.equals(newHierarchyVisId1)) {
         this.mHierarchyVisId1 = newHierarchyVisId1;
         this.mModified = true;
      }

   }

   public void setHierarchyVisId2(String newHierarchyVisId2) {
      if(this.mHierarchyVisId2 != null && newHierarchyVisId2 == null || this.mHierarchyVisId2 == null && newHierarchyVisId2 != null || this.mHierarchyVisId2 != null && newHierarchyVisId2 != null && !this.mHierarchyVisId2.equals(newHierarchyVisId2)) {
         this.mHierarchyVisId2 = newHierarchyVisId2;
         this.mModified = true;
      }

   }

   public void setTmpHierarchyVisId(String newTmpHierarchyVisId) {
      if(this.mTmpHierarchyVisId != null && newTmpHierarchyVisId == null || this.mTmpHierarchyVisId == null && newTmpHierarchyVisId != null || this.mTmpHierarchyVisId != null && newTmpHierarchyVisId != null && !this.mTmpHierarchyVisId.equals(newTmpHierarchyVisId)) {
         this.mTmpHierarchyVisId = newTmpHierarchyVisId;
         this.mModified = true;
      }

   }
   
   public void setCompanies(String newCompanies) {
	   if (this.mCompanies != null && newCompanies == null || this.mCompanies == null && newCompanies != null || this.mCompanies != null && newCompanies != null && !this.mCompanies.equals(newCompanies)) {
		   this.mCompanies = newCompanies;
		   this.mModified = true;
	   }
   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(MappedHierarchyEVO newDetails) {
      this.setMappedHierarchyId(newDetails.getMappedHierarchyId());
      this.setMappedDimensionId(newDetails.getMappedDimensionId());
      this.setHierarchyId(newDetails.getHierarchyId());
      this.setHierarchyVisId1(newDetails.getHierarchyVisId1());
      this.setHierarchyVisId2(newDetails.getHierarchyVisId2());
      this.setTmpHierarchyVisId(newDetails.getTmpHierarchyVisId());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public MappedHierarchyEVO deepClone() {
      MappedHierarchyEVO cloned = new MappedHierarchyEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mMappedHierarchyId = this.mMappedHierarchyId;
      cloned.mMappedDimensionId = this.mMappedDimensionId;
      cloned.mHierarchyId = this.mHierarchyId;
      cloned.mUpdatedByUserId = this.mUpdatedByUserId;
      if(this.mHierarchyVisId1 != null) {
         cloned.mHierarchyVisId1 = this.mHierarchyVisId1;
      }

      if(this.mHierarchyVisId2 != null) {
         cloned.mHierarchyVisId2 = this.mHierarchyVisId2;
      }

      if(this.mTmpHierarchyVisId != null) {
         cloned.mTmpHierarchyVisId = this.mTmpHierarchyVisId;
      }
      
      if(this.mCompanies != null) {
    	  cloned.mCompanies = this.mCompanies;
      }

      if(this.mUpdatedTime != null) {
         cloned.mUpdatedTime = Timestamp.valueOf(this.mUpdatedTime.toString());
      }

      if(this.mCreatedTime != null) {
         cloned.mCreatedTime = Timestamp.valueOf(this.mCreatedTime.toString());
      }

      return cloned;
   }

   public void prepareForInsert(MappedDimensionEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mMappedHierarchyId > 0) {
         newKey = true;
         if(parent == null) {
            this.setMappedHierarchyId(-this.mMappedHierarchyId);
         } else {
            parent.changeKey(this, -this.mMappedHierarchyId);
         }
      } else if(this.mMappedHierarchyId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mMappedHierarchyId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(MappedDimensionEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mMappedHierarchyId < 1) {
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

   public MappedHierarchyRef getEntityRef(GlobalMappedModel2EVO evoMappedModel, MappedDimensionEVO evoMappedDimension, String entityText) {
      return new MappedHierarchyRefImpl(new MappedHierarchyCK(evoMappedModel.getPK(), evoMappedDimension.getPK(), this.getPK()), entityText);
   }

   public MappedHierarchyCK getCK(GlobalMappedModel2EVO evoMappedModel, MappedDimensionEVO evoMappedDimension) {
      return new MappedHierarchyCK(evoMappedModel.getPK(), evoMappedDimension.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("MappedHierarchyId=");
      sb.append(String.valueOf(this.mMappedHierarchyId));
      sb.append(' ');
      sb.append("MappedDimensionId=");
      sb.append(String.valueOf(this.mMappedDimensionId));
      sb.append(' ');
      sb.append("HierarchyId=");
      sb.append(String.valueOf(this.mHierarchyId));
      sb.append(' ');
      sb.append("HierarchyVisId1=");
      sb.append(String.valueOf(this.mHierarchyVisId1));
      sb.append(' ');
      sb.append("HierarchyVisId2=");
      sb.append(String.valueOf(this.mHierarchyVisId2));
      sb.append(' ');
      sb.append("TmpHierarchyVisId=");
      sb.append(String.valueOf(this.mTmpHierarchyVisId));
      sb.append(' ');
      sb.append("Companies=");
      sb.append(String.valueOf(this.mCompanies));
      sb.append(' ');
      sb.append("UpdatedByUserId=");
      sb.append(String.valueOf(this.mUpdatedByUserId));
      sb.append(' ');
      sb.append("UpdatedTime=");
      sb.append(String.valueOf(this.mUpdatedTime));
      sb.append(' ');
      sb.append("CreatedTime=");
      sb.append(String.valueOf(this.mCreatedTime));
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

      sb.append("MappedHierarchy: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
