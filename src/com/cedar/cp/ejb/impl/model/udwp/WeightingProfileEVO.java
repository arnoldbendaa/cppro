// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.udwp;

import com.cedar.cp.api.model.udwp.WeightingProfileRef;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentPK;
import com.cedar.cp.dto.model.udwp.WeightingProfileCK;
import com.cedar.cp.dto.model.udwp.WeightingProfileLinePK;
import com.cedar.cp.dto.model.udwp.WeightingProfilePK;
import com.cedar.cp.dto.model.udwp.WeightingProfileRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingDeploymentEVO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileLineEVO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WeightingProfileEVO implements Serializable {

   private transient WeightingProfilePK mPK;
   private int mProfileId;
   private int mModelId;
   private String mVisId;
   private String mDescription;
   private int mStartLevel;
   private int mLeafLevel;
   private int mProfileType;
   private int mDynamicOffset;
   private Integer mDynamicDataTypeId;
   private Boolean mDynamicEsIfWfbtoz;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<WeightingProfileLinePK, WeightingProfileLineEVO> mWeightingLines;
   protected boolean mWeightingLinesAllItemsLoaded;
   private Map<WeightingDeploymentPK, WeightingDeploymentEVO> mWeightingDeployments;
   protected boolean mWeightingDeploymentsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int WP_STATIC = 0;
   public static final int WP_DYNAMIC = 1;
   public static final int WP_FORCED = 2;
   public static final int WP_REPEAT = 3;


   public WeightingProfileEVO() {}

   public WeightingProfileEVO(int newProfileId, int newModelId, String newVisId, String newDescription, int newStartLevel, int newLeafLevel, int newProfileType, int newDynamicOffset, Integer newDynamicDataTypeId, Boolean newDynamicEsIfWfbtoz, int newVersionNum, Collection newWeightingLines, Collection newWeightingDeployments) {
      this.mProfileId = newProfileId;
      this.mModelId = newModelId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mStartLevel = newStartLevel;
      this.mLeafLevel = newLeafLevel;
      this.mProfileType = newProfileType;
      this.mDynamicOffset = newDynamicOffset;
      this.mDynamicDataTypeId = newDynamicDataTypeId;
      this.mDynamicEsIfWfbtoz = newDynamicEsIfWfbtoz;
      this.mVersionNum = newVersionNum;
      this.setWeightingLines(newWeightingLines);
      this.setWeightingDeployments(newWeightingDeployments);
   }

   public void setWeightingLines(Collection<WeightingProfileLineEVO> items) {
      if(items != null) {
         if(this.mWeightingLines == null) {
            this.mWeightingLines = new HashMap();
         } else {
            this.mWeightingLines.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            WeightingProfileLineEVO child = (WeightingProfileLineEVO)i$.next();
            this.mWeightingLines.put(child.getPK(), child);
         }
      } else {
         this.mWeightingLines = null;
      }

   }

   public void setWeightingDeployments(Collection<WeightingDeploymentEVO> items) {
      if(items != null) {
         if(this.mWeightingDeployments == null) {
            this.mWeightingDeployments = new HashMap();
         } else {
            this.mWeightingDeployments.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            WeightingDeploymentEVO child = (WeightingDeploymentEVO)i$.next();
            this.mWeightingDeployments.put(child.getPK(), child);
         }
      } else {
         this.mWeightingDeployments = null;
      }

   }

   public WeightingProfilePK getPK() {
      if(this.mPK == null) {
         this.mPK = new WeightingProfilePK(this.mProfileId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getProfileId() {
      return this.mProfileId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getStartLevel() {
      return this.mStartLevel;
   }

   public int getLeafLevel() {
      return this.mLeafLevel;
   }

   public int getProfileType() {
      return this.mProfileType;
   }

   public int getDynamicOffset() {
      return this.mDynamicOffset;
   }

   public Integer getDynamicDataTypeId() {
      return this.mDynamicDataTypeId;
   }

   public Boolean getDynamicEsIfWfbtoz() {
      return this.mDynamicEsIfWfbtoz;
   }

   public int getVersionNum() {
      return this.mVersionNum;
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

   public void setProfileId(int newProfileId) {
      if(this.mProfileId != newProfileId) {
         this.mModified = true;
         this.mProfileId = newProfileId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setStartLevel(int newStartLevel) {
      if(this.mStartLevel != newStartLevel) {
         this.mModified = true;
         this.mStartLevel = newStartLevel;
      }
   }

   public void setLeafLevel(int newLeafLevel) {
      if(this.mLeafLevel != newLeafLevel) {
         this.mModified = true;
         this.mLeafLevel = newLeafLevel;
      }
   }

   public void setProfileType(int newProfileType) {
      if(this.mProfileType != newProfileType) {
         this.mModified = true;
         this.mProfileType = newProfileType;
      }
   }

   public void setDynamicOffset(int newDynamicOffset) {
      if(this.mDynamicOffset != newDynamicOffset) {
         this.mModified = true;
         this.mDynamicOffset = newDynamicOffset;
      }
   }

   public void setVersionNum(int newVersionNum) {
      if(this.mVersionNum != newVersionNum) {
         this.mModified = true;
         this.mVersionNum = newVersionNum;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setVisId(String newVisId) {
      if(this.mVisId != null && newVisId == null || this.mVisId == null && newVisId != null || this.mVisId != null && newVisId != null && !this.mVisId.equals(newVisId)) {
         this.mVisId = newVisId;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setDynamicDataTypeId(Integer newDynamicDataTypeId) {
      if(this.mDynamicDataTypeId != null && newDynamicDataTypeId == null || this.mDynamicDataTypeId == null && newDynamicDataTypeId != null || this.mDynamicDataTypeId != null && newDynamicDataTypeId != null && !this.mDynamicDataTypeId.equals(newDynamicDataTypeId)) {
         this.mDynamicDataTypeId = newDynamicDataTypeId;
         this.mModified = true;
      }

   }

   public void setDynamicEsIfWfbtoz(Boolean newDynamicEsIfWfbtoz) {
      if(this.mDynamicEsIfWfbtoz != null && newDynamicEsIfWfbtoz == null || this.mDynamicEsIfWfbtoz == null && newDynamicEsIfWfbtoz != null || this.mDynamicEsIfWfbtoz != null && newDynamicEsIfWfbtoz != null && !this.mDynamicEsIfWfbtoz.equals(newDynamicEsIfWfbtoz)) {
         this.mDynamicEsIfWfbtoz = newDynamicEsIfWfbtoz;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(WeightingProfileEVO newDetails) {
      this.setProfileId(newDetails.getProfileId());
      this.setModelId(newDetails.getModelId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setStartLevel(newDetails.getStartLevel());
      this.setLeafLevel(newDetails.getLeafLevel());
      this.setProfileType(newDetails.getProfileType());
      this.setDynamicOffset(newDetails.getDynamicOffset());
      this.setDynamicDataTypeId(newDetails.getDynamicDataTypeId());
      this.setDynamicEsIfWfbtoz(newDetails.getDynamicEsIfWfbtoz());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public WeightingProfileEVO deepClone() {
      WeightingProfileEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (WeightingProfileEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(ModelEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mProfileId > 0) {
         newKey = true;
         if(parent == null) {
            this.setProfileId(-this.mProfileId);
         } else {
            parent.changeKey(this, -this.mProfileId);
         }
      } else if(this.mProfileId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      Iterator iter;
      WeightingProfileLineEVO item;
      if(this.mWeightingLines != null) {
         for(iter = (new ArrayList(this.mWeightingLines.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (WeightingProfileLineEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

      WeightingDeploymentEVO item1;
      if(this.mWeightingDeployments != null) {
         for(iter = (new ArrayList(this.mWeightingDeployments.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
            item1 = (WeightingDeploymentEVO)iter.next();
            if(newKey) {
               item1.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mProfileId < 1) {
         returnCount = startCount + 1;
      }

      Iterator iter;
      WeightingProfileLineEVO item;
      if(this.mWeightingLines != null) {
         for(iter = this.mWeightingLines.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (WeightingProfileLineEVO)iter.next();
         }
      }

      WeightingDeploymentEVO item1;
      if(this.mWeightingDeployments != null) {
         for(iter = this.mWeightingDeployments.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
            item1 = (WeightingDeploymentEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(ModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mProfileId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      Iterator iter;
      WeightingProfileLineEVO item;
      if(this.mWeightingLines != null) {
         for(iter = (new ArrayList(this.mWeightingLines.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (WeightingProfileLineEVO)iter.next();
            this.changeKey(item, this.mProfileId, item.getLineIdx());
         }
      }

      WeightingDeploymentEVO item1;
      if(this.mWeightingDeployments != null) {
         for(iter = (new ArrayList(this.mWeightingDeployments.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
            item1 = (WeightingDeploymentEVO)iter.next();
            item1.setProfileId(this.mProfileId);
         }
      }

      return nextKey;
   }

   public Collection<WeightingProfileLineEVO> getWeightingLines() {
      return this.mWeightingLines != null?this.mWeightingLines.values():null;
   }

   public Map<WeightingProfileLinePK, WeightingProfileLineEVO> getWeightingLinesMap() {
      return this.mWeightingLines;
   }

   public void loadWeightingLinesItem(WeightingProfileLineEVO newItem) {
      if(this.mWeightingLines == null) {
         this.mWeightingLines = new HashMap();
      }

      this.mWeightingLines.put(newItem.getPK(), newItem);
   }

   public void addWeightingLinesItem(WeightingProfileLineEVO newItem) {
      if(this.mWeightingLines == null) {
         this.mWeightingLines = new HashMap();
      }

      WeightingProfileLinePK newPK = newItem.getPK();
      if(this.getWeightingLinesItem(newPK) != null) {
         throw new RuntimeException("addWeightingLinesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mWeightingLines.put(newPK, newItem);
      }
   }

   public void changeWeightingLinesItem(WeightingProfileLineEVO changedItem) {
      if(this.mWeightingLines == null) {
         throw new RuntimeException("changeWeightingLinesItem: no items in collection");
      } else {
         WeightingProfileLinePK changedPK = changedItem.getPK();
         WeightingProfileLineEVO listItem = this.getWeightingLinesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeWeightingLinesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteWeightingLinesItem(WeightingProfileLinePK removePK) {
      WeightingProfileLineEVO listItem = this.getWeightingLinesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeWeightingLinesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public WeightingProfileLineEVO getWeightingLinesItem(WeightingProfileLinePK pk) {
      return (WeightingProfileLineEVO)this.mWeightingLines.get(pk);
   }

   public WeightingProfileLineEVO getWeightingLinesItem() {
      if(this.mWeightingLines.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mWeightingLines.size());
      } else {
         Iterator iter = this.mWeightingLines.values().iterator();
         return (WeightingProfileLineEVO)iter.next();
      }
   }

   public Collection<WeightingDeploymentEVO> getWeightingDeployments() {
      return this.mWeightingDeployments != null?this.mWeightingDeployments.values():null;
   }

   public Map<WeightingDeploymentPK, WeightingDeploymentEVO> getWeightingDeploymentsMap() {
      return this.mWeightingDeployments;
   }

   public void loadWeightingDeploymentsItem(WeightingDeploymentEVO newItem) {
      if(this.mWeightingDeployments == null) {
         this.mWeightingDeployments = new HashMap();
      }

      this.mWeightingDeployments.put(newItem.getPK(), newItem);
   }

   public void addWeightingDeploymentsItem(WeightingDeploymentEVO newItem) {
      if(this.mWeightingDeployments == null) {
         this.mWeightingDeployments = new HashMap();
      }

      WeightingDeploymentPK newPK = newItem.getPK();
      if(this.getWeightingDeploymentsItem(newPK) != null) {
         throw new RuntimeException("addWeightingDeploymentsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mWeightingDeployments.put(newPK, newItem);
      }
   }

   public void changeWeightingDeploymentsItem(WeightingDeploymentEVO changedItem) {
      if(this.mWeightingDeployments == null) {
         throw new RuntimeException("changeWeightingDeploymentsItem: no items in collection");
      } else {
         WeightingDeploymentPK changedPK = changedItem.getPK();
         WeightingDeploymentEVO listItem = this.getWeightingDeploymentsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeWeightingDeploymentsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteWeightingDeploymentsItem(WeightingDeploymentPK removePK) {
      WeightingDeploymentEVO listItem = this.getWeightingDeploymentsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeWeightingDeploymentsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public WeightingDeploymentEVO getWeightingDeploymentsItem(WeightingDeploymentPK pk) {
      return (WeightingDeploymentEVO)this.mWeightingDeployments.get(pk);
   }

   public WeightingDeploymentEVO getWeightingDeploymentsItem() {
      if(this.mWeightingDeployments.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mWeightingDeployments.size());
      } else {
         Iterator iter = this.mWeightingDeployments.values().iterator();
         return (WeightingDeploymentEVO)iter.next();
      }
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

   public WeightingProfileRef getEntityRef(ModelEVO evoModel) {
      return new WeightingProfileRefImpl(new WeightingProfileCK(evoModel.getPK(), this.getPK()), this.mVisId);
   }

   public WeightingProfileCK getCK(ModelEVO evoModel) {
      return new WeightingProfileCK(evoModel.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mWeightingLinesAllItemsLoaded = true;
      if(this.mWeightingLines == null) {
         this.mWeightingLines = new HashMap();
      }

      this.mWeightingDeploymentsAllItemsLoaded = true;
      if(this.mWeightingDeployments == null) {
         this.mWeightingDeployments = new HashMap();
      } else {
         Iterator i$ = this.mWeightingDeployments.values().iterator();

         while(i$.hasNext()) {
            WeightingDeploymentEVO child = (WeightingDeploymentEVO)i$.next();
            child.postCreateInit();
         }
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ProfileId=");
      sb.append(String.valueOf(this.mProfileId));
      sb.append(' ');
      sb.append("ModelId=");
      sb.append(String.valueOf(this.mModelId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("StartLevel=");
      sb.append(String.valueOf(this.mStartLevel));
      sb.append(' ');
      sb.append("LeafLevel=");
      sb.append(String.valueOf(this.mLeafLevel));
      sb.append(' ');
      sb.append("ProfileType=");
      sb.append(String.valueOf(this.mProfileType));
      sb.append(' ');
      sb.append("DynamicOffset=");
      sb.append(String.valueOf(this.mDynamicOffset));
      sb.append(' ');
      sb.append("DynamicDataTypeId=");
      sb.append(String.valueOf(this.mDynamicDataTypeId));
      sb.append(' ');
      sb.append("DynamicEsIfWfbtoz=");
      sb.append(String.valueOf(this.mDynamicEsIfWfbtoz));
      sb.append(' ');
      sb.append("VersionNum=");
      sb.append(String.valueOf(this.mVersionNum));
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

      for(int i$ = 0; i$ < indent; ++i$) {
         sb.append(' ');
      }

      sb.append("WeightingProfile: ");
      sb.append(this.toString());
      if(this.mWeightingLinesAllItemsLoaded || this.mWeightingLines != null) {
         sb.delete(indent, sb.length());
         sb.append(" - WeightingLines: allItemsLoaded=");
         sb.append(String.valueOf(this.mWeightingLinesAllItemsLoaded));
         sb.append(" items=");
         if(this.mWeightingLines == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mWeightingLines.size()));
         }
      }

      if(this.mWeightingDeploymentsAllItemsLoaded || this.mWeightingDeployments != null) {
         sb.delete(indent, sb.length());
         sb.append(" - WeightingDeployments: allItemsLoaded=");
         sb.append(String.valueOf(this.mWeightingDeploymentsAllItemsLoaded));
         sb.append(" items=");
         if(this.mWeightingDeployments == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mWeightingDeployments.size()));
         }
      }

      Iterator var5;
      if(this.mWeightingLines != null) {
         var5 = this.mWeightingLines.values().iterator();

         while(var5.hasNext()) {
            WeightingProfileLineEVO listItem = (WeightingProfileLineEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      if(this.mWeightingDeployments != null) {
         var5 = this.mWeightingDeployments.values().iterator();

         while(var5.hasNext()) {
            WeightingDeploymentEVO var6 = (WeightingDeploymentEVO)var5.next();
            var6.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(WeightingProfileLineEVO child, int newProfileId, int newLineIdx) {
      if(this.getWeightingLinesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mWeightingLines.remove(child.getPK());
         child.setProfileId(newProfileId);
         child.setLineIdx(newLineIdx);
         this.mWeightingLines.put(child.getPK(), child);
      }
   }

   public void changeKey(WeightingDeploymentEVO child, int newDeploymentId) {
      if(this.getWeightingDeploymentsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mWeightingDeployments.remove(child.getPK());
         child.setDeploymentId(newDeploymentId);
         this.mWeightingDeployments.put(child.getPK(), child);
      }
   }

   public void setWeightingLinesAllItemsLoaded(boolean allItemsLoaded) {
      this.mWeightingLinesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isWeightingLinesAllItemsLoaded() {
      return this.mWeightingLinesAllItemsLoaded;
   }

   public void setWeightingDeploymentsAllItemsLoaded(boolean allItemsLoaded) {
      this.mWeightingDeploymentsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isWeightingDeploymentsAllItemsLoaded() {
      return this.mWeightingDeploymentsAllItemsLoaded;
   }
}
