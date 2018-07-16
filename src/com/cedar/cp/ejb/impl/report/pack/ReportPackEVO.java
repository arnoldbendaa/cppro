// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.pack;

import com.cedar.cp.api.report.pack.ReportPackRef;
import com.cedar.cp.dto.report.pack.ReportPackLinkPK;
import com.cedar.cp.dto.report.pack.ReportPackPK;
import com.cedar.cp.dto.report.pack.ReportPackRefImpl;
import com.cedar.cp.ejb.impl.report.pack.ReportPackLinkEVO;
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

public class ReportPackEVO implements Serializable {

   private transient ReportPackPK mPK;
   private int mReportPackId;
   private String mVisId;
   private String mDescription;
   private boolean mGroupAttachment;
   private String mParamExample;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<ReportPackLinkPK, ReportPackLinkEVO> mReportPackDefinitionDistributionList;
   protected boolean mReportPackDefinitionDistributionListAllItemsLoaded;
   private boolean mModified;


   public ReportPackEVO() {}

   public ReportPackEVO(int newReportPackId, String newVisId, String newDescription, boolean newGroupAttachment, String newParamExample, int newVersionNum, Collection newReportPackDefinitionDistributionList) {
      this.mReportPackId = newReportPackId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mGroupAttachment = newGroupAttachment;
      this.mParamExample = newParamExample;
      this.mVersionNum = newVersionNum;
      this.setReportPackDefinitionDistributionList(newReportPackDefinitionDistributionList);
   }

   public void setReportPackDefinitionDistributionList(Collection<ReportPackLinkEVO> items) {
      if(items != null) {
         if(this.mReportPackDefinitionDistributionList == null) {
            this.mReportPackDefinitionDistributionList = new HashMap();
         } else {
            this.mReportPackDefinitionDistributionList.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ReportPackLinkEVO child = (ReportPackLinkEVO)i$.next();
            this.mReportPackDefinitionDistributionList.put(child.getPK(), child);
         }
      } else {
         this.mReportPackDefinitionDistributionList = null;
      }

   }

   public ReportPackPK getPK() {
      if(this.mPK == null) {
         this.mPK = new ReportPackPK(this.mReportPackId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getReportPackId() {
      return this.mReportPackId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public boolean getGroupAttachment() {
      return this.mGroupAttachment;
   }

   public String getParamExample() {
      return this.mParamExample;
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

   public void setReportPackId(int newReportPackId) {
      if(this.mReportPackId != newReportPackId) {
         this.mModified = true;
         this.mReportPackId = newReportPackId;
         this.mPK = null;
      }
   }

   public void setGroupAttachment(boolean newGroupAttachment) {
      if(this.mGroupAttachment != newGroupAttachment) {
         this.mModified = true;
         this.mGroupAttachment = newGroupAttachment;
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

   public void setParamExample(String newParamExample) {
      if(this.mParamExample != null && newParamExample == null || this.mParamExample == null && newParamExample != null || this.mParamExample != null && newParamExample != null && !this.mParamExample.equals(newParamExample)) {
         this.mParamExample = newParamExample;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(ReportPackEVO newDetails) {
      this.setReportPackId(newDetails.getReportPackId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setGroupAttachment(newDetails.getGroupAttachment());
      this.setParamExample(newDetails.getParamExample());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ReportPackEVO deepClone() {
      ReportPackEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (ReportPackEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mReportPackId > 0) {
         newKey = true;
         this.mReportPackId = 0;
      } else if(this.mReportPackId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      ReportPackLinkEVO item;
      if(this.mReportPackDefinitionDistributionList != null) {
         for(Iterator iter = (new ArrayList(this.mReportPackDefinitionDistributionList.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (ReportPackLinkEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mReportPackId < 1) {
         returnCount = startCount + 1;
      }

      ReportPackLinkEVO item;
      if(this.mReportPackDefinitionDistributionList != null) {
         for(Iterator iter = this.mReportPackDefinitionDistributionList.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (ReportPackLinkEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mReportPackId < 1) {
         this.mReportPackId = startKey;
         nextKey = startKey + 1;
      }

      ReportPackLinkEVO item;
      if(this.mReportPackDefinitionDistributionList != null) {
         for(Iterator iter = (new ArrayList(this.mReportPackDefinitionDistributionList.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (ReportPackLinkEVO)iter.next();
            this.changeKey(item, this.mReportPackId, item.getReportPackLinkId());
         }
      }

      return nextKey;
   }

   public Collection<ReportPackLinkEVO> getReportPackDefinitionDistributionList() {
      return this.mReportPackDefinitionDistributionList != null?this.mReportPackDefinitionDistributionList.values():null;
   }

   public Map<ReportPackLinkPK, ReportPackLinkEVO> getReportPackDefinitionDistributionListMap() {
      return this.mReportPackDefinitionDistributionList;
   }

   public void loadReportPackDefinitionDistributionListItem(ReportPackLinkEVO newItem) {
      if(this.mReportPackDefinitionDistributionList == null) {
         this.mReportPackDefinitionDistributionList = new HashMap();
      }

      this.mReportPackDefinitionDistributionList.put(newItem.getPK(), newItem);
   }

   public void addReportPackDefinitionDistributionListItem(ReportPackLinkEVO newItem) {
      if(this.mReportPackDefinitionDistributionList == null) {
         this.mReportPackDefinitionDistributionList = new HashMap();
      }

      ReportPackLinkPK newPK = newItem.getPK();
      if(this.getReportPackDefinitionDistributionListItem(newPK) != null) {
         throw new RuntimeException("addReportPackDefinitionDistributionListItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mReportPackDefinitionDistributionList.put(newPK, newItem);
      }
   }

   public void changeReportPackDefinitionDistributionListItem(ReportPackLinkEVO changedItem) {
      if(this.mReportPackDefinitionDistributionList == null) {
         throw new RuntimeException("changeReportPackDefinitionDistributionListItem: no items in collection");
      } else {
         ReportPackLinkPK changedPK = changedItem.getPK();
         ReportPackLinkEVO listItem = this.getReportPackDefinitionDistributionListItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeReportPackDefinitionDistributionListItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteReportPackDefinitionDistributionListItem(ReportPackLinkPK removePK) {
      ReportPackLinkEVO listItem = this.getReportPackDefinitionDistributionListItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeReportPackDefinitionDistributionListItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ReportPackLinkEVO getReportPackDefinitionDistributionListItem(ReportPackLinkPK pk) {
      return (ReportPackLinkEVO)this.mReportPackDefinitionDistributionList.get(pk);
   }

   public ReportPackLinkEVO getReportPackDefinitionDistributionListItem() {
      if(this.mReportPackDefinitionDistributionList.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mReportPackDefinitionDistributionList.size());
      } else {
         Iterator iter = this.mReportPackDefinitionDistributionList.values().iterator();
         return (ReportPackLinkEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public ReportPackRef getEntityRef() {
      return new ReportPackRefImpl(this.getPK(), this.mVisId);
   }

   public void postCreateInit() {
      this.mReportPackDefinitionDistributionListAllItemsLoaded = true;
      if(this.mReportPackDefinitionDistributionList == null) {
         this.mReportPackDefinitionDistributionList = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ReportPackId=");
      sb.append(String.valueOf(this.mReportPackId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("GroupAttachment=");
      sb.append(String.valueOf(this.mGroupAttachment));
      sb.append(' ');
      sb.append("ParamExample=");
      sb.append(String.valueOf(this.mParamExample));
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

      sb.append("ReportPack: ");
      sb.append(this.toString());
      if(this.mReportPackDefinitionDistributionListAllItemsLoaded || this.mReportPackDefinitionDistributionList != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ReportPackDefinitionDistributionList: allItemsLoaded=");
         sb.append(String.valueOf(this.mReportPackDefinitionDistributionListAllItemsLoaded));
         sb.append(" items=");
         if(this.mReportPackDefinitionDistributionList == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mReportPackDefinitionDistributionList.size()));
         }
      }

      if(this.mReportPackDefinitionDistributionList != null) {
         Iterator var5 = this.mReportPackDefinitionDistributionList.values().iterator();

         while(var5.hasNext()) {
            ReportPackLinkEVO listItem = (ReportPackLinkEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(ReportPackLinkEVO child, int newReportPackId, int newReportPackLinkId) {
      if(this.getReportPackDefinitionDistributionListItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mReportPackDefinitionDistributionList.remove(child.getPK());
         child.setReportPackId(newReportPackId);
         child.setReportPackLinkId(newReportPackLinkId);
         this.mReportPackDefinitionDistributionList.put(child.getPK(), child);
      }
   }

   public void setReportPackDefinitionDistributionListAllItemsLoaded(boolean allItemsLoaded) {
      this.mReportPackDefinitionDistributionListAllItemsLoaded = allItemsLoaded;
   }

   public boolean isReportPackDefinitionDistributionListAllItemsLoaded() {
      return this.mReportPackDefinitionDistributionListAllItemsLoaded;
   }
}
