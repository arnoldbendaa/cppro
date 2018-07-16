// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.report.type;

import com.cedar.cp.api.report.type.ReportTypeRef;
import com.cedar.cp.dto.report.type.ReportTypePK;
import com.cedar.cp.dto.report.type.ReportTypeRefImpl;
import com.cedar.cp.dto.report.type.param.ReportTypeParamPK;
import com.cedar.cp.ejb.impl.report.type.param.ReportTypeParamEVO;
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

public class ReportTypeEVO implements Serializable {

   private transient ReportTypePK mPK;
   private int mReportTypeId;
   private String mVisId;
   private String mDescription;
   private int mType;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<ReportTypeParamPK, ReportTypeParamEVO> mReportTypeParams;
   protected boolean mReportTypeParamsAllItemsLoaded;
   private boolean mModified;
   public static final int XML_FORM = 0;
   public static final int EXCEL_TEMPLATE = 1;


   public ReportTypeEVO() {}

   public ReportTypeEVO(int newReportTypeId, String newVisId, String newDescription, int newType, int newVersionNum, Collection newReportTypeParams) {
      this.mReportTypeId = newReportTypeId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mType = newType;
      this.mVersionNum = newVersionNum;
      this.setReportTypeParams(newReportTypeParams);
   }

   public void setReportTypeParams(Collection<ReportTypeParamEVO> items) {
      if(items != null) {
         if(this.mReportTypeParams == null) {
            this.mReportTypeParams = new HashMap();
         } else {
            this.mReportTypeParams.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            ReportTypeParamEVO child = (ReportTypeParamEVO)i$.next();
            this.mReportTypeParams.put(child.getPK(), child);
         }
      } else {
         this.mReportTypeParams = null;
      }

   }

   public ReportTypePK getPK() {
      if(this.mPK == null) {
         this.mPK = new ReportTypePK(this.mReportTypeId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getReportTypeId() {
      return this.mReportTypeId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getType() {
      return this.mType;
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

   public void setReportTypeId(int newReportTypeId) {
      if(this.mReportTypeId != newReportTypeId) {
         this.mModified = true;
         this.mReportTypeId = newReportTypeId;
         this.mPK = null;
      }
   }

   public void setType(int newType) {
      if(this.mType != newType) {
         this.mModified = true;
         this.mType = newType;
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

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(ReportTypeEVO newDetails) {
      this.setReportTypeId(newDetails.getReportTypeId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setType(newDetails.getType());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public ReportTypeEVO deepClone() {
      ReportTypeEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (ReportTypeEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mReportTypeId > 0) {
         newKey = true;
         this.mReportTypeId = 0;
      } else if(this.mReportTypeId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      ReportTypeParamEVO item;
      if(this.mReportTypeParams != null) {
         for(Iterator iter = (new ArrayList(this.mReportTypeParams.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (ReportTypeParamEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mReportTypeId < 1) {
         returnCount = startCount + 1;
      }

      ReportTypeParamEVO item;
      if(this.mReportTypeParams != null) {
         for(Iterator iter = this.mReportTypeParams.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (ReportTypeParamEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mReportTypeId < 1) {
         this.mReportTypeId = startKey;
         nextKey = startKey + 1;
      }

      ReportTypeParamEVO item;
      if(this.mReportTypeParams != null) {
         for(Iterator iter = (new ArrayList(this.mReportTypeParams.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (ReportTypeParamEVO)iter.next();
            this.changeKey(item, item.getReportTypeParamId(), this.mReportTypeId);
         }
      }

      return nextKey;
   }

   public Collection<ReportTypeParamEVO> getReportTypeParams() {
      return this.mReportTypeParams != null?this.mReportTypeParams.values():null;
   }

   public Map<ReportTypeParamPK, ReportTypeParamEVO> getReportTypeParamsMap() {
      return this.mReportTypeParams;
   }

   public void loadReportTypeParamsItem(ReportTypeParamEVO newItem) {
      if(this.mReportTypeParams == null) {
         this.mReportTypeParams = new HashMap();
      }

      this.mReportTypeParams.put(newItem.getPK(), newItem);
   }

   public void addReportTypeParamsItem(ReportTypeParamEVO newItem) {
      if(this.mReportTypeParams == null) {
         this.mReportTypeParams = new HashMap();
      }

      ReportTypeParamPK newPK = newItem.getPK();
      if(this.getReportTypeParamsItem(newPK) != null) {
         throw new RuntimeException("addReportTypeParamsItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mReportTypeParams.put(newPK, newItem);
      }
   }

   public void changeReportTypeParamsItem(ReportTypeParamEVO changedItem) {
      if(this.mReportTypeParams == null) {
         throw new RuntimeException("changeReportTypeParamsItem: no items in collection");
      } else {
         ReportTypeParamPK changedPK = changedItem.getPK();
         ReportTypeParamEVO listItem = this.getReportTypeParamsItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeReportTypeParamsItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteReportTypeParamsItem(ReportTypeParamPK removePK) {
      ReportTypeParamEVO listItem = this.getReportTypeParamsItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeReportTypeParamsItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public ReportTypeParamEVO getReportTypeParamsItem(ReportTypeParamPK pk) {
      return (ReportTypeParamEVO)this.mReportTypeParams.get(pk);
   }

   public ReportTypeParamEVO getReportTypeParamsItem() {
      if(this.mReportTypeParams.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mReportTypeParams.size());
      } else {
         Iterator iter = this.mReportTypeParams.values().iterator();
         return (ReportTypeParamEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public ReportTypeRef getEntityRef() {
      return new ReportTypeRefImpl(this.getPK(), this.mVisId);
   }

   public void postCreateInit() {
      this.mReportTypeParamsAllItemsLoaded = true;
      if(this.mReportTypeParams == null) {
         this.mReportTypeParams = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("ReportTypeId=");
      sb.append(String.valueOf(this.mReportTypeId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("Type=");
      sb.append(String.valueOf(this.mType));
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

      sb.append("ReportType: ");
      sb.append(this.toString());
      if(this.mReportTypeParamsAllItemsLoaded || this.mReportTypeParams != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ReportTypeParams: allItemsLoaded=");
         sb.append(String.valueOf(this.mReportTypeParamsAllItemsLoaded));
         sb.append(" items=");
         if(this.mReportTypeParams == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mReportTypeParams.size()));
         }
      }

      if(this.mReportTypeParams != null) {
         Iterator var5 = this.mReportTypeParams.values().iterator();

         while(var5.hasNext()) {
            ReportTypeParamEVO listItem = (ReportTypeParamEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(ReportTypeParamEVO child, int newReportTypeParamId, int newReportTypeId) {
      if(this.getReportTypeParamsItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mReportTypeParams.remove(child.getPK());
         child.setReportTypeParamId(newReportTypeParamId);
         child.setReportTypeId(newReportTypeId);
         this.mReportTypeParams.put(child.getPK(), child);
      }
   }

   public void setReportTypeParamsAllItemsLoaded(boolean allItemsLoaded) {
      this.mReportTypeParamsAllItemsLoaded = allItemsLoaded;
   }

   public boolean isReportTypeParamsAllItemsLoaded() {
      return this.mReportTypeParamsAllItemsLoaded;
   }
}
