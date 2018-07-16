// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.udwp;

import com.cedar.cp.api.model.udwp.WeightingDeploymentRef;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentCK;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentLinePK;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentPK;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingDeploymentLineEVO;
import com.cedar.cp.ejb.impl.model.udwp.WeightingProfileEVO;
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

public class WeightingDeploymentEVO implements Serializable {

   private transient WeightingDeploymentPK mPK;
   private int mDeploymentId;
   private int mProfileId;
   private boolean mAnyAccount;
   private boolean mAnyBusiness;
   private boolean mAnyDataType;
   private int mWeighting;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<WeightingDeploymentLinePK, WeightingDeploymentLineEVO> mDeploymentLines;
   protected boolean mDeploymentLinesAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public WeightingDeploymentEVO() {}

   public WeightingDeploymentEVO(int newDeploymentId, int newProfileId, boolean newAnyAccount, boolean newAnyBusiness, boolean newAnyDataType, int newWeighting, Collection newDeploymentLines) {
      this.mDeploymentId = newDeploymentId;
      this.mProfileId = newProfileId;
      this.mAnyAccount = newAnyAccount;
      this.mAnyBusiness = newAnyBusiness;
      this.mAnyDataType = newAnyDataType;
      this.mWeighting = newWeighting;
      this.setDeploymentLines(newDeploymentLines);
   }

   public void setDeploymentLines(Collection<WeightingDeploymentLineEVO> items) {
      if(items != null) {
         if(this.mDeploymentLines == null) {
            this.mDeploymentLines = new HashMap();
         } else {
            this.mDeploymentLines.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            WeightingDeploymentLineEVO child = (WeightingDeploymentLineEVO)i$.next();
            this.mDeploymentLines.put(child.getPK(), child);
         }
      } else {
         this.mDeploymentLines = null;
      }

   }

   public WeightingDeploymentPK getPK() {
      if(this.mPK == null) {
         this.mPK = new WeightingDeploymentPK(this.mDeploymentId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getDeploymentId() {
      return this.mDeploymentId;
   }

   public int getProfileId() {
      return this.mProfileId;
   }

   public boolean getAnyAccount() {
      return this.mAnyAccount;
   }

   public boolean getAnyBusiness() {
      return this.mAnyBusiness;
   }

   public boolean getAnyDataType() {
      return this.mAnyDataType;
   }

   public int getWeighting() {
      return this.mWeighting;
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

   public void setDeploymentId(int newDeploymentId) {
      if(this.mDeploymentId != newDeploymentId) {
         this.mModified = true;
         this.mDeploymentId = newDeploymentId;
         this.mPK = null;
      }
   }

   public void setProfileId(int newProfileId) {
      if(this.mProfileId != newProfileId) {
         this.mModified = true;
         this.mProfileId = newProfileId;
      }
   }

   public void setAnyAccount(boolean newAnyAccount) {
      if(this.mAnyAccount != newAnyAccount) {
         this.mModified = true;
         this.mAnyAccount = newAnyAccount;
      }
   }

   public void setAnyBusiness(boolean newAnyBusiness) {
      if(this.mAnyBusiness != newAnyBusiness) {
         this.mModified = true;
         this.mAnyBusiness = newAnyBusiness;
      }
   }

   public void setAnyDataType(boolean newAnyDataType) {
      if(this.mAnyDataType != newAnyDataType) {
         this.mModified = true;
         this.mAnyDataType = newAnyDataType;
      }
   }

   public void setWeighting(int newWeighting) {
      if(this.mWeighting != newWeighting) {
         this.mModified = true;
         this.mWeighting = newWeighting;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(WeightingDeploymentEVO newDetails) {
      this.setDeploymentId(newDetails.getDeploymentId());
      this.setProfileId(newDetails.getProfileId());
      this.setAnyAccount(newDetails.getAnyAccount());
      this.setAnyBusiness(newDetails.getAnyBusiness());
      this.setAnyDataType(newDetails.getAnyDataType());
      this.setWeighting(newDetails.getWeighting());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public WeightingDeploymentEVO deepClone() {
      WeightingDeploymentEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (WeightingDeploymentEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(WeightingProfileEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mDeploymentId > 0) {
         newKey = true;
         if(parent == null) {
            this.setDeploymentId(-this.mDeploymentId);
         } else {
            parent.changeKey(this, -this.mDeploymentId);
         }
      } else if(this.mDeploymentId < 1) {
         newKey = true;
      }

      WeightingDeploymentLineEVO item;
      if(this.mDeploymentLines != null) {
         for(Iterator iter = (new ArrayList(this.mDeploymentLines.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (WeightingDeploymentLineEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mDeploymentId < 1) {
         returnCount = startCount + 1;
      }

      WeightingDeploymentLineEVO item;
      if(this.mDeploymentLines != null) {
         for(Iterator iter = this.mDeploymentLines.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (WeightingDeploymentLineEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(WeightingProfileEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mDeploymentId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      WeightingDeploymentLineEVO item;
      if(this.mDeploymentLines != null) {
         for(Iterator iter = (new ArrayList(this.mDeploymentLines.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (WeightingDeploymentLineEVO)iter.next();
            this.changeKey(item, this.mDeploymentId, item.getLineIdx());
         }
      }

      return nextKey;
   }

   public Collection<WeightingDeploymentLineEVO> getDeploymentLines() {
      return this.mDeploymentLines != null?this.mDeploymentLines.values():null;
   }

   public Map<WeightingDeploymentLinePK, WeightingDeploymentLineEVO> getDeploymentLinesMap() {
      return this.mDeploymentLines;
   }

   public void loadDeploymentLinesItem(WeightingDeploymentLineEVO newItem) {
      if(this.mDeploymentLines == null) {
         this.mDeploymentLines = new HashMap();
      }

      this.mDeploymentLines.put(newItem.getPK(), newItem);
   }

   public void addDeploymentLinesItem(WeightingDeploymentLineEVO newItem) {
      if(this.mDeploymentLines == null) {
         this.mDeploymentLines = new HashMap();
      }

      WeightingDeploymentLinePK newPK = newItem.getPK();
      if(this.getDeploymentLinesItem(newPK) != null) {
         throw new RuntimeException("addDeploymentLinesItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mDeploymentLines.put(newPK, newItem);
      }
   }

   public void changeDeploymentLinesItem(WeightingDeploymentLineEVO changedItem) {
      if(this.mDeploymentLines == null) {
         throw new RuntimeException("changeDeploymentLinesItem: no items in collection");
      } else {
         WeightingDeploymentLinePK changedPK = changedItem.getPK();
         WeightingDeploymentLineEVO listItem = this.getDeploymentLinesItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeDeploymentLinesItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteDeploymentLinesItem(WeightingDeploymentLinePK removePK) {
      WeightingDeploymentLineEVO listItem = this.getDeploymentLinesItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeDeploymentLinesItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public WeightingDeploymentLineEVO getDeploymentLinesItem(WeightingDeploymentLinePK pk) {
      return (WeightingDeploymentLineEVO)this.mDeploymentLines.get(pk);
   }

   public WeightingDeploymentLineEVO getDeploymentLinesItem() {
      if(this.mDeploymentLines.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mDeploymentLines.size());
      } else {
         Iterator iter = this.mDeploymentLines.values().iterator();
         return (WeightingDeploymentLineEVO)iter.next();
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

   public WeightingDeploymentRef getEntityRef(ModelEVO evoModel, WeightingProfileEVO evoWeightingProfile, String entityText) {
      return new WeightingDeploymentRefImpl(new WeightingDeploymentCK(evoModel.getPK(), evoWeightingProfile.getPK(), this.getPK()), entityText);
   }

   public WeightingDeploymentCK getCK(ModelEVO evoModel, WeightingProfileEVO evoWeightingProfile) {
      return new WeightingDeploymentCK(evoModel.getPK(), evoWeightingProfile.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mDeploymentLinesAllItemsLoaded = true;
      if(this.mDeploymentLines == null) {
         this.mDeploymentLines = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("DeploymentId=");
      sb.append(String.valueOf(this.mDeploymentId));
      sb.append(' ');
      sb.append("ProfileId=");
      sb.append(String.valueOf(this.mProfileId));
      sb.append(' ');
      sb.append("AnyAccount=");
      sb.append(String.valueOf(this.mAnyAccount));
      sb.append(' ');
      sb.append("AnyBusiness=");
      sb.append(String.valueOf(this.mAnyBusiness));
      sb.append(' ');
      sb.append("AnyDataType=");
      sb.append(String.valueOf(this.mAnyDataType));
      sb.append(' ');
      sb.append("Weighting=");
      sb.append(String.valueOf(this.mWeighting));
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

      sb.append("WeightingDeployment: ");
      sb.append(this.toString());
      if(this.mDeploymentLinesAllItemsLoaded || this.mDeploymentLines != null) {
         sb.delete(indent, sb.length());
         sb.append(" - DeploymentLines: allItemsLoaded=");
         sb.append(String.valueOf(this.mDeploymentLinesAllItemsLoaded));
         sb.append(" items=");
         if(this.mDeploymentLines == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mDeploymentLines.size()));
         }
      }

      if(this.mDeploymentLines != null) {
         Iterator var5 = this.mDeploymentLines.values().iterator();

         while(var5.hasNext()) {
            WeightingDeploymentLineEVO listItem = (WeightingDeploymentLineEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(WeightingDeploymentLineEVO child, int newDeploymentId, int newLineIdx) {
      if(this.getDeploymentLinesItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mDeploymentLines.remove(child.getPK());
         child.setDeploymentId(newDeploymentId);
         child.setLineIdx(newLineIdx);
         this.mDeploymentLines.put(child.getPK(), child);
      }
   }

   public WeightingDeploymentLineEVO findLineForAccountElement(int structureId, int structureElementId) {
      if(this.getDeploymentLines() == null) {
         return null;
      } else {
         Iterator iterator = this.getDeploymentLines().iterator();

         WeightingDeploymentLineEVO lineEVO;
         do {
            if(!iterator.hasNext()) {
               return null;
            }

            lineEVO = (WeightingDeploymentLineEVO)iterator.next();
         } while(lineEVO.getAccountStructureId() == null || lineEVO.getAccountStructureId().intValue() != structureId || lineEVO.getAccountStructureElementId() == null || lineEVO.getAccountStructureElementId().intValue() != structureElementId);

         return lineEVO;
      }
   }

   public WeightingDeploymentLineEVO findFreeLineForAccount() {
      if(this.getDeploymentLines() == null) {
         return null;
      } else {
         int curIdx = Integer.MAX_VALUE;
         WeightingDeploymentLineEVO curLine = null;
         Iterator iterator = this.getDeploymentLines().iterator();

         while(iterator.hasNext()) {
            WeightingDeploymentLineEVO lineEVO = (WeightingDeploymentLineEVO)iterator.next();
            if(lineEVO.getAccountStructureId() == null && lineEVO.getLineIdx() < curIdx) {
               curIdx = lineEVO.getLineIdx();
               curLine = lineEVO;
            }
         }

         return curLine;
      }
   }

   public WeightingDeploymentLineEVO findLineForBusinessElement(int structureId, int structureElementId) {
      if(this.getDeploymentLines() == null) {
         return null;
      } else {
         Iterator iterator = this.getDeploymentLines().iterator();

         WeightingDeploymentLineEVO lineEVO;
         do {
            if(!iterator.hasNext()) {
               return null;
            }

            lineEVO = (WeightingDeploymentLineEVO)iterator.next();
         } while(lineEVO.getBusinessStructureId() == null || lineEVO.getBusinessStructureId().intValue() != structureId || lineEVO.getBusinessStructureId() == null || lineEVO.getBusinessStructureElementId().intValue() != structureElementId);

         return lineEVO;
      }
   }

   public WeightingDeploymentLineEVO findFreeLineForBusiness() {
      if(this.getDeploymentLines() == null) {
         return null;
      } else {
         int curIdx = Integer.MAX_VALUE;
         WeightingDeploymentLineEVO curLine = null;
         Iterator iterator = this.getDeploymentLines().iterator();

         while(iterator.hasNext()) {
            WeightingDeploymentLineEVO lineEVO = (WeightingDeploymentLineEVO)iterator.next();
            if(lineEVO.getBusinessStructureId() == null && lineEVO.getLineIdx() < curIdx) {
               curIdx = lineEVO.getLineIdx();
               curLine = lineEVO;
            }
         }

         return curLine;
      }
   }

   public WeightingDeploymentLineEVO findLineForDataType(int dataTypeId) {
      if(this.getDeploymentLines() == null) {
         return null;
      } else {
         Iterator iterator = this.getDeploymentLines().iterator();

         WeightingDeploymentLineEVO lineEVO;
         do {
            if(!iterator.hasNext()) {
               return null;
            }

            lineEVO = (WeightingDeploymentLineEVO)iterator.next();
         } while(lineEVO.getDataTypeId() == null || lineEVO.getDataTypeId().intValue() != dataTypeId);

         return lineEVO;
      }
   }

   public WeightingDeploymentLineEVO findFreeLineForDataType() {
      if(this.getDeploymentLines() == null) {
         return null;
      } else {
         int curIdx = Integer.MAX_VALUE;
         WeightingDeploymentLineEVO curLine = null;
         Iterator iterator = this.getDeploymentLines().iterator();

         while(iterator.hasNext()) {
            WeightingDeploymentLineEVO lineEVO = (WeightingDeploymentLineEVO)iterator.next();
            if(lineEVO.getDataTypeId() == null && lineEVO.getLineIdx() < curIdx) {
               curLine = lineEVO;
               curIdx = lineEVO.getLineIdx();
            }
         }

         return curLine;
      }
   }

   public int queryNextDeploymentLineIdx() {
      int idx;
      for(idx = 0; this.getLineIdx(idx) != null; ++idx) {
         ;
      }

      return idx;
   }

   public WeightingDeploymentLineEVO getLineIdx(int idx) {
      if(this.getDeploymentLines() == null) {
         return null;
      } else {
         Iterator iterator = this.getDeploymentLines().iterator();

         WeightingDeploymentLineEVO lineEVO;
         do {
            if(!iterator.hasNext()) {
               return null;
            }

            lineEVO = (WeightingDeploymentLineEVO)iterator.next();
         } while(lineEVO.getLineIdx() != idx);

         return lineEVO;
      }
   }

   public void removeAccountRef(WeightingDeploymentLineEVO lineEVO) {
      int lineIdx = lineEVO.getLineIdx();
      int max = this.queryNextDeploymentLineIdx() - 1;
      lineEVO.setAccountStructureId((Integer)null);
      lineEVO.setAccountStructureElementId((Integer)null);

      for(int i = lineIdx; i < max; ++i) {
         WeightingDeploymentLineEVO curLine = this.getLineIdx(i);
         WeightingDeploymentLineEVO nextLine = this.getLineIdx(i + 1);
         curLine.setAccountStructureId(nextLine.getAccountStructureId());
         curLine.setAccountStructureElementId(nextLine.getAccountStructureElementId());
         if(nextLine.getAccountStructureId() == null) {
            break;
         }
      }

      lineEVO = this.getLineIdx(max);
      lineEVO.setAccountStructureId((Integer)null);
      lineEVO.setAccountStructureElementId((Integer)null);
      lineEVO.setAccountSelectionFlag(Boolean.valueOf(false));
   }

   public void removeBusinessRef(WeightingDeploymentLineEVO lineEVO) {
      int lineIdx = lineEVO.getLineIdx();
      int max = this.queryNextDeploymentLineIdx() - 1;
      lineEVO.setBusinessStructureId((Integer)null);
      lineEVO.setBusinessStructureElementId((Integer)null);

      for(int i = lineIdx; i < max; ++i) {
         WeightingDeploymentLineEVO curLine = this.getLineIdx(i);
         WeightingDeploymentLineEVO nextLine = this.getLineIdx(i + 1);
         curLine.setBusinessStructureId(nextLine.getBusinessStructureId());
         curLine.setBusinessStructureElementId(nextLine.getBusinessStructureElementId());
         if(nextLine.getBusinessStructureId() == null) {
            break;
         }
      }

      lineEVO = this.getLineIdx(max);
      lineEVO.setBusinessStructureId((Integer)null);
      lineEVO.setBusinessStructureElementId((Integer)null);
      lineEVO.setBusinessSelectionFlag(Boolean.valueOf(false));
   }

   public void removeDataTypeRef(WeightingDeploymentLineEVO lineEVO) {
      int lineIdx = lineEVO.getLineIdx();
      int max = this.queryNextDeploymentLineIdx() - 1;
      lineEVO.setDataTypeId((Integer)null);

      for(int i = lineIdx; i < max; ++i) {
         WeightingDeploymentLineEVO curLine = this.getLineIdx(i);
         WeightingDeploymentLineEVO nextLine = this.getLineIdx(i + 1);
         curLine.setDataTypeId(nextLine.getDataTypeId());
         if(nextLine.getDataTypeId() == null) {
            break;
         }
      }

      this.getLineIdx(max).setDataTypeId((Integer)null);
      this.dumpLines("endRemoveDataType");
   }

   public void dumpLines(String msg) {
      System.out.println("*** dumpLines:ENTRY(" + msg + ")");
      int total = this.queryNextDeploymentLineIdx();

      for(int row = 0; row < total; ++row) {
         WeightingDeploymentLineEVO curLine = this.getLineIdx(row);
         System.out.println(curLine.toString());
      }

      System.out.println("*** dumpLines:ENTRY");
   }

   public void setDeploymentLinesAllItemsLoaded(boolean allItemsLoaded) {
      this.mDeploymentLinesAllItemsLoaded = allItemsLoaded;
   }

   public boolean isDeploymentLinesAllItemsLoaded() {
      return this.mDeploymentLinesAllItemsLoaded;
   }
}
