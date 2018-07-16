// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.virement;

import com.cedar.cp.api.model.virement.VirementRequestLineRef;
import com.cedar.cp.dto.model.virement.VirementLineSpreadPK;
import com.cedar.cp.dto.model.virement.VirementRequestLineCK;
import com.cedar.cp.dto.model.virement.VirementRequestLinePK;
import com.cedar.cp.dto.model.virement.VirementRequestLineRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementLineSpreadEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestEVO;
import com.cedar.cp.ejb.impl.model.virement.VirementRequestGroupEVO;
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

public class VirementRequestLineEVO implements Serializable {

   private transient VirementRequestLinePK mPK;
   private int mRequestLineId;
   private int mRequestGroupId;
   private boolean mTarget;
   private int mSpreadProfileId;
   private int mLineIdx;
   private long mTransferValue;
   private int mDataTypeId;
   private int mDim0;
   private int mDim1;
   private int mDim2;
   private int mDim3;
   private int mDim4;
   private int mDim5;
   private int mDim6;
   private int mDim7;
   private int mDim8;
   private int mDim9;
   private Long mRepeatValue;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<VirementLineSpreadPK, VirementLineSpreadEVO> mSpreads;
   protected boolean mSpreadsAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;


   public VirementRequestLineEVO() {}

   public VirementRequestLineEVO(int newRequestLineId, int newRequestGroupId, boolean newTarget, int newSpreadProfileId, int newLineIdx, long newTransferValue, int newDataTypeId, int newDim0, int newDim1, int newDim2, int newDim3, int newDim4, int newDim5, int newDim6, int newDim7, int newDim8, int newDim9, Long newRepeatValue, Collection newSpreads) {
      this.mRequestLineId = newRequestLineId;
      this.mRequestGroupId = newRequestGroupId;
      this.mTarget = newTarget;
      this.mSpreadProfileId = newSpreadProfileId;
      this.mLineIdx = newLineIdx;
      this.mTransferValue = newTransferValue;
      this.mDataTypeId = newDataTypeId;
      this.mDim0 = newDim0;
      this.mDim1 = newDim1;
      this.mDim2 = newDim2;
      this.mDim3 = newDim3;
      this.mDim4 = newDim4;
      this.mDim5 = newDim5;
      this.mDim6 = newDim6;
      this.mDim7 = newDim7;
      this.mDim8 = newDim8;
      this.mDim9 = newDim9;
      this.mRepeatValue = newRepeatValue;
      this.setSpreads(newSpreads);
   }

   public void setSpreads(Collection<VirementLineSpreadEVO> items) {
      if(items != null) {
         if(this.mSpreads == null) {
            this.mSpreads = new HashMap();
         } else {
            this.mSpreads.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            VirementLineSpreadEVO child = (VirementLineSpreadEVO)i$.next();
            this.mSpreads.put(child.getPK(), child);
         }
      } else {
         this.mSpreads = null;
      }

   }

   public VirementRequestLinePK getPK() {
      if(this.mPK == null) {
         this.mPK = new VirementRequestLinePK(this.mRequestLineId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getRequestLineId() {
      return this.mRequestLineId;
   }

   public int getRequestGroupId() {
      return this.mRequestGroupId;
   }

   public boolean getTarget() {
      return this.mTarget;
   }

   public int getSpreadProfileId() {
      return this.mSpreadProfileId;
   }

   public int getLineIdx() {
      return this.mLineIdx;
   }

   public long getTransferValue() {
      return this.mTransferValue;
   }

   public int getDataTypeId() {
      return this.mDataTypeId;
   }

   public int getDim0() {
      return this.mDim0;
   }

   public int getDim1() {
      return this.mDim1;
   }

   public int getDim2() {
      return this.mDim2;
   }

   public int getDim3() {
      return this.mDim3;
   }

   public int getDim4() {
      return this.mDim4;
   }

   public int getDim5() {
      return this.mDim5;
   }

   public int getDim6() {
      return this.mDim6;
   }

   public int getDim7() {
      return this.mDim7;
   }

   public int getDim8() {
      return this.mDim8;
   }

   public int getDim9() {
      return this.mDim9;
   }

   public Long getRepeatValue() {
      return this.mRepeatValue;
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

   public void setRequestLineId(int newRequestLineId) {
      if(this.mRequestLineId != newRequestLineId) {
         this.mModified = true;
         this.mRequestLineId = newRequestLineId;
         this.mPK = null;
      }
   }

   public void setRequestGroupId(int newRequestGroupId) {
      if(this.mRequestGroupId != newRequestGroupId) {
         this.mModified = true;
         this.mRequestGroupId = newRequestGroupId;
      }
   }

   public void setTarget(boolean newTarget) {
      if(this.mTarget != newTarget) {
         this.mModified = true;
         this.mTarget = newTarget;
      }
   }

   public void setSpreadProfileId(int newSpreadProfileId) {
      if(this.mSpreadProfileId != newSpreadProfileId) {
         this.mModified = true;
         this.mSpreadProfileId = newSpreadProfileId;
      }
   }

   public void setLineIdx(int newLineIdx) {
      if(this.mLineIdx != newLineIdx) {
         this.mModified = true;
         this.mLineIdx = newLineIdx;
      }
   }

   public void setTransferValue(long newTransferValue) {
      if(this.mTransferValue != newTransferValue) {
         this.mModified = true;
         this.mTransferValue = newTransferValue;
      }
   }

   public void setDataTypeId(int newDataTypeId) {
      if(this.mDataTypeId != newDataTypeId) {
         this.mModified = true;
         this.mDataTypeId = newDataTypeId;
      }
   }

   public void setDim0(int newDim0) {
      if(this.mDim0 != newDim0) {
         this.mModified = true;
         this.mDim0 = newDim0;
      }
   }

   public void setDim1(int newDim1) {
      if(this.mDim1 != newDim1) {
         this.mModified = true;
         this.mDim1 = newDim1;
      }
   }

   public void setDim2(int newDim2) {
      if(this.mDim2 != newDim2) {
         this.mModified = true;
         this.mDim2 = newDim2;
      }
   }

   public void setDim3(int newDim3) {
      if(this.mDim3 != newDim3) {
         this.mModified = true;
         this.mDim3 = newDim3;
      }
   }

   public void setDim4(int newDim4) {
      if(this.mDim4 != newDim4) {
         this.mModified = true;
         this.mDim4 = newDim4;
      }
   }

   public void setDim5(int newDim5) {
      if(this.mDim5 != newDim5) {
         this.mModified = true;
         this.mDim5 = newDim5;
      }
   }

   public void setDim6(int newDim6) {
      if(this.mDim6 != newDim6) {
         this.mModified = true;
         this.mDim6 = newDim6;
      }
   }

   public void setDim7(int newDim7) {
      if(this.mDim7 != newDim7) {
         this.mModified = true;
         this.mDim7 = newDim7;
      }
   }

   public void setDim8(int newDim8) {
      if(this.mDim8 != newDim8) {
         this.mModified = true;
         this.mDim8 = newDim8;
      }
   }

   public void setDim9(int newDim9) {
      if(this.mDim9 != newDim9) {
         this.mModified = true;
         this.mDim9 = newDim9;
      }
   }

   public void setUpdatedByUserId(int newUpdatedByUserId) {
      this.mUpdatedByUserId = newUpdatedByUserId;
   }

   public void setRepeatValue(Long newRepeatValue) {
      if(this.mRepeatValue != null && newRepeatValue == null || this.mRepeatValue == null && newRepeatValue != null || this.mRepeatValue != null && newRepeatValue != null && !this.mRepeatValue.equals(newRepeatValue)) {
         this.mRepeatValue = newRepeatValue;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(VirementRequestLineEVO newDetails) {
      this.setRequestLineId(newDetails.getRequestLineId());
      this.setRequestGroupId(newDetails.getRequestGroupId());
      this.setTarget(newDetails.getTarget());
      this.setSpreadProfileId(newDetails.getSpreadProfileId());
      this.setLineIdx(newDetails.getLineIdx());
      this.setTransferValue(newDetails.getTransferValue());
      this.setDataTypeId(newDetails.getDataTypeId());
      this.setDim0(newDetails.getDim0());
      this.setDim1(newDetails.getDim1());
      this.setDim2(newDetails.getDim2());
      this.setDim3(newDetails.getDim3());
      this.setDim4(newDetails.getDim4());
      this.setDim5(newDetails.getDim5());
      this.setDim6(newDetails.getDim6());
      this.setDim7(newDetails.getDim7());
      this.setDim8(newDetails.getDim8());
      this.setDim9(newDetails.getDim9());
      this.setRepeatValue(newDetails.getRepeatValue());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public VirementRequestLineEVO deepClone() {
      VirementRequestLineEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (VirementRequestLineEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(VirementRequestGroupEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mRequestLineId > 0) {
         newKey = true;
         if(parent == null) {
            this.setRequestLineId(-this.mRequestLineId);
         } else {
            parent.changeKey(this, -this.mRequestLineId);
         }
      } else if(this.mRequestLineId < 1) {
         newKey = true;
      }

      VirementLineSpreadEVO item;
      if(this.mSpreads != null) {
         for(Iterator iter = (new ArrayList(this.mSpreads.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (VirementLineSpreadEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mRequestLineId < 1) {
         returnCount = startCount + 1;
      }

      VirementLineSpreadEVO item;
      if(this.mSpreads != null) {
         for(Iterator iter = this.mSpreads.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (VirementLineSpreadEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(VirementRequestGroupEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mRequestLineId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      VirementLineSpreadEVO item;
      if(this.mSpreads != null) {
         for(Iterator iter = (new ArrayList(this.mSpreads.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (VirementLineSpreadEVO)iter.next();
            item.setRequestLineId(this.mRequestLineId);
         }
      }

      return nextKey;
   }

   public Collection<VirementLineSpreadEVO> getSpreads() {
      return this.mSpreads != null?this.mSpreads.values():null;
   }

   public Map<VirementLineSpreadPK, VirementLineSpreadEVO> getSpreadsMap() {
      return this.mSpreads;
   }

   public void loadSpreadsItem(VirementLineSpreadEVO newItem) {
      if(this.mSpreads == null) {
         this.mSpreads = new HashMap();
      }

      this.mSpreads.put(newItem.getPK(), newItem);
   }

   public void addSpreadsItem(VirementLineSpreadEVO newItem) {
      if(this.mSpreads == null) {
         this.mSpreads = new HashMap();
      }

      VirementLineSpreadPK newPK = newItem.getPK();
      if(this.getSpreadsItem(newPK) != null) {
         throw new RuntimeException("addSpreadsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mSpreads.put(newPK, newItem);
      }
   }

   public void changeSpreadsItem(VirementLineSpreadEVO changedItem) {
      if(this.mSpreads == null) {
         throw new RuntimeException("changeSpreadsItem: no items in collection");
      } else {
         VirementLineSpreadPK changedPK = changedItem.getPK();
         VirementLineSpreadEVO listItem = this.getSpreadsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeSpreadsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteSpreadsItem(VirementLineSpreadPK removePK) {
      VirementLineSpreadEVO listItem = this.getSpreadsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeSpreadsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public VirementLineSpreadEVO getSpreadsItem(VirementLineSpreadPK pk) {
      return (VirementLineSpreadEVO)this.mSpreads.get(pk);
   }

   public VirementLineSpreadEVO getSpreadsItem() {
      if(this.mSpreads.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mSpreads.size());
      } else {
         Iterator iter = this.mSpreads.values().iterator();
         return (VirementLineSpreadEVO)iter.next();
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

   public VirementRequestLineRef getEntityRef(ModelEVO evoModel, VirementRequestEVO evoVirementRequest, VirementRequestGroupEVO evoVirementRequestGroup, String entityText) {
      return new VirementRequestLineRefImpl(new VirementRequestLineCK(evoModel.getPK(), evoVirementRequest.getPK(), evoVirementRequestGroup.getPK(), this.getPK()), entityText);
   }

   public VirementRequestLineCK getCK(ModelEVO evoModel, VirementRequestEVO evoVirementRequest, VirementRequestGroupEVO evoVirementRequestGroup) {
      return new VirementRequestLineCK(evoModel.getPK(), evoVirementRequest.getPK(), evoVirementRequestGroup.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mSpreadsAllItemsLoaded = true;
      if(this.mSpreads == null) {
         this.mSpreads = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("RequestLineId=");
      sb.append(String.valueOf(this.mRequestLineId));
      sb.append(' ');
      sb.append("RequestGroupId=");
      sb.append(String.valueOf(this.mRequestGroupId));
      sb.append(' ');
      sb.append("Target=");
      sb.append(String.valueOf(this.mTarget));
      sb.append(' ');
      sb.append("SpreadProfileId=");
      sb.append(String.valueOf(this.mSpreadProfileId));
      sb.append(' ');
      sb.append("LineIdx=");
      sb.append(String.valueOf(this.mLineIdx));
      sb.append(' ');
      sb.append("TransferValue=");
      sb.append(String.valueOf(this.mTransferValue));
      sb.append(' ');
      sb.append("DataTypeId=");
      sb.append(String.valueOf(this.mDataTypeId));
      sb.append(' ');
      sb.append("Dim0=");
      sb.append(String.valueOf(this.mDim0));
      sb.append(' ');
      sb.append("Dim1=");
      sb.append(String.valueOf(this.mDim1));
      sb.append(' ');
      sb.append("Dim2=");
      sb.append(String.valueOf(this.mDim2));
      sb.append(' ');
      sb.append("Dim3=");
      sb.append(String.valueOf(this.mDim3));
      sb.append(' ');
      sb.append("Dim4=");
      sb.append(String.valueOf(this.mDim4));
      sb.append(' ');
      sb.append("Dim5=");
      sb.append(String.valueOf(this.mDim5));
      sb.append(' ');
      sb.append("Dim6=");
      sb.append(String.valueOf(this.mDim6));
      sb.append(' ');
      sb.append("Dim7=");
      sb.append(String.valueOf(this.mDim7));
      sb.append(' ');
      sb.append("Dim8=");
      sb.append(String.valueOf(this.mDim8));
      sb.append(' ');
      sb.append("Dim9=");
      sb.append(String.valueOf(this.mDim9));
      sb.append(' ');
      sb.append("RepeatValue=");
      sb.append(String.valueOf(this.mRepeatValue));
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

      sb.append("VirementRequestLine: ");
      sb.append(this.toString());
      if(this.mSpreadsAllItemsLoaded || this.mSpreads != null) {
         sb.delete(indent, sb.length());
         sb.append(" - Spreads: allItemsLoaded=");
         sb.append(String.valueOf(this.mSpreadsAllItemsLoaded));
         sb.append(" items=");
         if(this.mSpreads == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mSpreads.size()));
         }
      }

      if(this.mSpreads != null) {
         Iterator var5 = this.mSpreads.values().iterator();

         while(var5.hasNext()) {
            VirementLineSpreadEVO listItem = (VirementLineSpreadEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(VirementLineSpreadEVO child, int newLineSpreadId) {
      if(this.getSpreadsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mSpreads.remove(child.getPK());
         child.setLineSpreadId(newLineSpreadId);
         this.mSpreads.put(child.getPK(), child);
      }
   }

   public void setSpreadsAllItemsLoaded(boolean allItemsLoaded) {
      this.mSpreadsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isSpreadsAllItemsLoaded() {
      return this.mSpreadsAllItemsLoaded;
   }
}
