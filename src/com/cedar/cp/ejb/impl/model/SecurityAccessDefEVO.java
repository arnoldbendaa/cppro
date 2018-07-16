// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:28
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.SecurityAccessDefRef;
import com.cedar.cp.dto.model.SecurityAccRngRelPK;
import com.cedar.cp.dto.model.SecurityAccessDefCK;
import com.cedar.cp.dto.model.SecurityAccessDefPK;
import com.cedar.cp.dto.model.SecurityAccessDefRefImpl;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import com.cedar.cp.ejb.impl.model.SecurityAccRngRelEVO;
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

public class SecurityAccessDefEVO implements Serializable {

   private transient SecurityAccessDefPK mPK;
   private int mSecurityAccessDefId;
   private int mModelId;
   private String mVisId;
   private String mDescription;
   private int mAccessMode;
   private String mExpression;
   private int mVersionNum;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<SecurityAccRngRelPK, SecurityAccRngRelEVO> mSecurityRangesForAccessDef;
   protected boolean mSecurityRangesForAccessDefAllItemsLoaded;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int READ_ACCESS = 1;
   public static final int WRITE_ACCESS = 2;


   public SecurityAccessDefEVO() {}

   public SecurityAccessDefEVO(int newSecurityAccessDefId, int newModelId, String newVisId, String newDescription, int newAccessMode, String newExpression, int newVersionNum, Collection newSecurityRangesForAccessDef) {
      this.mSecurityAccessDefId = newSecurityAccessDefId;
      this.mModelId = newModelId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mAccessMode = newAccessMode;
      this.mExpression = newExpression;
      this.mVersionNum = newVersionNum;
      this.setSecurityRangesForAccessDef(newSecurityRangesForAccessDef);
   }

   public void setSecurityRangesForAccessDef(Collection<SecurityAccRngRelEVO> items) {
      if(items != null) {
         if(this.mSecurityRangesForAccessDef == null) {
            this.mSecurityRangesForAccessDef = new HashMap();
         } else {
            this.mSecurityRangesForAccessDef.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            SecurityAccRngRelEVO child = (SecurityAccRngRelEVO)i$.next();
            this.mSecurityRangesForAccessDef.put(child.getPK(), child);
         }
      } else {
         this.mSecurityRangesForAccessDef = null;
      }

   }

   public SecurityAccessDefPK getPK() {
      if(this.mPK == null) {
         this.mPK = new SecurityAccessDefPK(this.mSecurityAccessDefId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getSecurityAccessDefId() {
      return this.mSecurityAccessDefId;
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

   public int getAccessMode() {
      return this.mAccessMode;
   }

   public String getExpression() {
      return this.mExpression;
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

   public void setSecurityAccessDefId(int newSecurityAccessDefId) {
      if(this.mSecurityAccessDefId != newSecurityAccessDefId) {
         this.mModified = true;
         this.mSecurityAccessDefId = newSecurityAccessDefId;
         this.mPK = null;
      }
   }

   public void setModelId(int newModelId) {
      if(this.mModelId != newModelId) {
         this.mModified = true;
         this.mModelId = newModelId;
      }
   }

   public void setAccessMode(int newAccessMode) {
      if(this.mAccessMode != newAccessMode) {
         this.mModified = true;
         this.mAccessMode = newAccessMode;
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

   public void setExpression(String newExpression) {
      if(this.mExpression != null && newExpression == null || this.mExpression == null && newExpression != null || this.mExpression != null && newExpression != null && !this.mExpression.equals(newExpression)) {
         this.mExpression = newExpression;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(SecurityAccessDefEVO newDetails) {
      this.setSecurityAccessDefId(newDetails.getSecurityAccessDefId());
      this.setModelId(newDetails.getModelId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setAccessMode(newDetails.getAccessMode());
      this.setExpression(newDetails.getExpression());
      this.setVersionNum(newDetails.getVersionNum());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public SecurityAccessDefEVO deepClone() {
      SecurityAccessDefEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (SecurityAccessDefEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert(ModelEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mSecurityAccessDefId > 0) {
         newKey = true;
         if(parent == null) {
            this.setSecurityAccessDefId(-this.mSecurityAccessDefId);
         } else {
            parent.changeKey(this, -this.mSecurityAccessDefId);
         }
      } else if(this.mSecurityAccessDefId < 1) {
         newKey = true;
      }

      this.setVersionNum(0);
      SecurityAccRngRelEVO item;
      if(this.mSecurityRangesForAccessDef != null) {
         for(Iterator iter = (new ArrayList(this.mSecurityRangesForAccessDef.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (SecurityAccRngRelEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mSecurityAccessDefId < 1) {
         returnCount = startCount + 1;
      }

      SecurityAccRngRelEVO item;
      if(this.mSecurityRangesForAccessDef != null) {
         for(Iterator iter = this.mSecurityRangesForAccessDef.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (SecurityAccRngRelEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(ModelEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mSecurityAccessDefId < 1) {
         parent.changeKey(this, startKey);
         nextKey = startKey + 1;
      }

      SecurityAccRngRelEVO item;
      if(this.mSecurityRangesForAccessDef != null) {
         for(Iterator iter = (new ArrayList(this.mSecurityRangesForAccessDef.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (SecurityAccRngRelEVO)iter.next();
            this.changeKey(item, this.mSecurityAccessDefId, item.getSecurityRangeId());
         }
      }

      return nextKey;
   }

   public Collection<SecurityAccRngRelEVO> getSecurityRangesForAccessDef() {
      return this.mSecurityRangesForAccessDef != null?this.mSecurityRangesForAccessDef.values():null;
   }

   public Map<SecurityAccRngRelPK, SecurityAccRngRelEVO> getSecurityRangesForAccessDefMap() {
      return this.mSecurityRangesForAccessDef;
   }

   public void loadSecurityRangesForAccessDefItem(SecurityAccRngRelEVO newItem) {
      if(this.mSecurityRangesForAccessDef == null) {
         this.mSecurityRangesForAccessDef = new HashMap();
      }

      this.mSecurityRangesForAccessDef.put(newItem.getPK(), newItem);
   }

   public void addSecurityRangesForAccessDefItem(SecurityAccRngRelEVO newItem) {
      if(this.mSecurityRangesForAccessDef == null) {
         this.mSecurityRangesForAccessDef = new HashMap();
      }

      SecurityAccRngRelPK newPK = newItem.getPK();
      if(this.getSecurityRangesForAccessDefItem(newPK) != null) {
         throw new RuntimeException("addSecurityRangesForAccessDefItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mSecurityRangesForAccessDef.put(newPK, newItem);
      }
   }

   public void changeSecurityRangesForAccessDefItem(SecurityAccRngRelEVO changedItem) {
      if(this.mSecurityRangesForAccessDef == null) {
         throw new RuntimeException("changeSecurityRangesForAccessDefItem: no items in collection");
      } else {
         SecurityAccRngRelPK changedPK = changedItem.getPK();
         SecurityAccRngRelEVO listItem = this.getSecurityRangesForAccessDefItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeSecurityRangesForAccessDefItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteSecurityRangesForAccessDefItem(SecurityAccRngRelPK removePK) {
      SecurityAccRngRelEVO listItem = this.getSecurityRangesForAccessDefItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeSecurityRangesForAccessDefItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public SecurityAccRngRelEVO getSecurityRangesForAccessDefItem(SecurityAccRngRelPK pk) {
      return (SecurityAccRngRelEVO)this.mSecurityRangesForAccessDef.get(pk);
   }

   public SecurityAccRngRelEVO getSecurityRangesForAccessDefItem() {
      if(this.mSecurityRangesForAccessDef.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mSecurityRangesForAccessDef.size());
      } else {
         Iterator iter = this.mSecurityRangesForAccessDef.values().iterator();
         return (SecurityAccRngRelEVO)iter.next();
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

   public SecurityAccessDefRef getEntityRef(ModelEVO evoModel) {
      return new SecurityAccessDefRefImpl(new SecurityAccessDefCK(evoModel.getPK(), this.getPK()), this.mVisId);
   }

   public SecurityAccessDefCK getCK(ModelEVO evoModel) {
      return new SecurityAccessDefCK(evoModel.getPK(), this.getPK());
   }

   public void postCreateInit() {
      this.mSecurityRangesForAccessDefAllItemsLoaded = true;
      if(this.mSecurityRangesForAccessDef == null) {
         this.mSecurityRangesForAccessDef = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("SecurityAccessDefId=");
      sb.append(String.valueOf(this.mSecurityAccessDefId));
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
      sb.append("AccessMode=");
      sb.append(String.valueOf(this.mAccessMode));
      sb.append(' ');
      sb.append("Expression=");
      sb.append(String.valueOf(this.mExpression));
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

      sb.append("SecurityAccessDef: ");
      sb.append(this.toString());
      if(this.mSecurityRangesForAccessDefAllItemsLoaded || this.mSecurityRangesForAccessDef != null) {
         sb.delete(indent, sb.length());
         sb.append(" - SecurityRangesForAccessDef: allItemsLoaded=");
         sb.append(String.valueOf(this.mSecurityRangesForAccessDefAllItemsLoaded));
         sb.append(" items=");
         if(this.mSecurityRangesForAccessDef == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mSecurityRangesForAccessDef.size()));
         }
      }

      if(this.mSecurityRangesForAccessDef != null) {
         Iterator var5 = this.mSecurityRangesForAccessDef.values().iterator();

         while(var5.hasNext()) {
            SecurityAccRngRelEVO listItem = (SecurityAccRngRelEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(SecurityAccRngRelEVO child, int newSecurityAccessDefId, int newSecurityRangeId) {
      if(this.getSecurityRangesForAccessDefItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mSecurityRangesForAccessDef.remove(child.getPK());
         child.setSecurityAccessDefId(newSecurityAccessDefId);
         child.setSecurityRangeId(newSecurityRangeId);
         this.mSecurityRangesForAccessDef.put(child.getPK(), child);
      }
   }

   public void setSecurityRangesForAccessDefAllItemsLoaded(boolean allItemsLoaded) {
      this.mSecurityRangesForAccessDefAllItemsLoaded = allItemsLoaded;
   }

   public boolean isSecurityRangesForAccessDefAllItemsLoaded() {
      return this.mSecurityRangesForAccessDefAllItemsLoaded;
   }
}
