// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.udeflookup;

import com.cedar.cp.api.udeflookup.UdefLookupRef;
import com.cedar.cp.dto.udeflookup.UdefLookupColumnDefPK;
import com.cedar.cp.dto.udeflookup.UdefLookupPK;
import com.cedar.cp.dto.udeflookup.UdefLookupRefImpl;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupColumnDefEVO;
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

public class UdefLookupEVO implements Serializable {

   private transient UdefLookupPK mPK;
   private int mUdefLookupId;
   private String mVisId;
   private String mDescription;
   private String mGenTableName;
   private boolean mAutoSubmit;
   private boolean mScenario;
   private int mTimeLvl;
   private int mYearStartMonth;
   private boolean mTimeRange;
   private Timestamp mLastSubmit;
   private Timestamp mDataUpdated;
   private int mUpdatedByUserId;
   private Timestamp mUpdatedTime;
   private Timestamp mCreatedTime;
   private Map<UdefLookupColumnDefPK, UdefLookupColumnDefEVO> mColumnDef;
   protected boolean mColumnDefAllItemsLoaded;
   private boolean mModified;
   public static final int TIME_LVL_NONE = 0;
   public static final int TIME_LVL_YEAR = 1;
   public static final int TIME_LVL_MONTH = 2;
   public static final int TIME_LVL_DAY = 3;


   public UdefLookupEVO() {}

   public UdefLookupEVO(int newUdefLookupId, String newVisId, String newDescription, String newGenTableName, boolean newAutoSubmit, boolean newScenario, int newTimeLvl, int newYearStartMonth, boolean newTimeRange, Timestamp newLastSubmit, Timestamp newDataUpdated, Collection newColumnDef) {
      this.mUdefLookupId = newUdefLookupId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mGenTableName = newGenTableName;
      this.mAutoSubmit = newAutoSubmit;
      this.mScenario = newScenario;
      this.mTimeLvl = newTimeLvl;
      this.mYearStartMonth = newYearStartMonth;
      this.mTimeRange = newTimeRange;
      this.mLastSubmit = newLastSubmit;
      this.mDataUpdated = newDataUpdated;
      this.setColumnDef(newColumnDef);
   }

   public void setColumnDef(Collection<UdefLookupColumnDefEVO> items) {
      if(items != null) {
         if(this.mColumnDef == null) {
            this.mColumnDef = new HashMap();
         } else {
            this.mColumnDef.clear();
         }

         Iterator i$ = items.iterator();

         while(i$.hasNext()) {
            UdefLookupColumnDefEVO child = (UdefLookupColumnDefEVO)i$.next();
            this.mColumnDef.put(child.getPK(), child);
         }
      } else {
         this.mColumnDef = null;
      }

   }

   public UdefLookupPK getPK() {
      if(this.mPK == null) {
         this.mPK = new UdefLookupPK(this.mUdefLookupId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getUdefLookupId() {
      return this.mUdefLookupId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getGenTableName() {
      return this.mGenTableName;
   }

   public boolean getAutoSubmit() {
      return this.mAutoSubmit;
   }

   public boolean getScenario() {
      return this.mScenario;
   }

   public int getTimeLvl() {
      return this.mTimeLvl;
   }

   public int getYearStartMonth() {
      return this.mYearStartMonth;
   }

   public boolean getTimeRange() {
      return this.mTimeRange;
   }

   public Timestamp getLastSubmit() {
      return this.mLastSubmit;
   }

   public Timestamp getDataUpdated() {
      return this.mDataUpdated;
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

   public void setUdefLookupId(int newUdefLookupId) {
      if(this.mUdefLookupId != newUdefLookupId) {
         this.mModified = true;
         this.mUdefLookupId = newUdefLookupId;
         this.mPK = null;
      }
   }

   public void setAutoSubmit(boolean newAutoSubmit) {
      if(this.mAutoSubmit != newAutoSubmit) {
         this.mModified = true;
         this.mAutoSubmit = newAutoSubmit;
      }
   }

   public void setScenario(boolean newScenario) {
      if(this.mScenario != newScenario) {
         this.mModified = true;
         this.mScenario = newScenario;
      }
   }

   public void setTimeLvl(int newTimeLvl) {
      if(this.mTimeLvl != newTimeLvl) {
         this.mModified = true;
         this.mTimeLvl = newTimeLvl;
      }
   }

   public void setYearStartMonth(int newYearStartMonth) {
      if(this.mYearStartMonth != newYearStartMonth) {
         this.mModified = true;
         this.mYearStartMonth = newYearStartMonth;
      }
   }

   public void setTimeRange(boolean newTimeRange) {
      if(this.mTimeRange != newTimeRange) {
         this.mModified = true;
         this.mTimeRange = newTimeRange;
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

   public void setGenTableName(String newGenTableName) {
      if(this.mGenTableName != null && newGenTableName == null || this.mGenTableName == null && newGenTableName != null || this.mGenTableName != null && newGenTableName != null && !this.mGenTableName.equals(newGenTableName)) {
         this.mGenTableName = newGenTableName;
         this.mModified = true;
      }

   }

   public void setLastSubmit(Timestamp newLastSubmit) {
      if(this.mLastSubmit != null && newLastSubmit == null || this.mLastSubmit == null && newLastSubmit != null || this.mLastSubmit != null && newLastSubmit != null && !this.mLastSubmit.equals(newLastSubmit)) {
         this.mLastSubmit = newLastSubmit;
         this.mModified = true;
      }

   }

   public void setDataUpdated(Timestamp newDataUpdated) {
      if(this.mDataUpdated != null && newDataUpdated == null || this.mDataUpdated == null && newDataUpdated != null || this.mDataUpdated != null && newDataUpdated != null && !this.mDataUpdated.equals(newDataUpdated)) {
         this.mDataUpdated = newDataUpdated;
         this.mModified = true;
      }

   }

   protected void setUpdatedTime(Timestamp newUpdatedTime) {
      this.mUpdatedTime = newUpdatedTime;
   }

   protected void setCreatedTime(Timestamp newCreatedTime) {
      this.mCreatedTime = newCreatedTime;
   }

   public void setDetails(UdefLookupEVO newDetails) {
      this.setUdefLookupId(newDetails.getUdefLookupId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setGenTableName(newDetails.getGenTableName());
      this.setAutoSubmit(newDetails.getAutoSubmit());
      this.setScenario(newDetails.getScenario());
      this.setTimeLvl(newDetails.getTimeLvl());
      this.setYearStartMonth(newDetails.getYearStartMonth());
      this.setTimeRange(newDetails.getTimeRange());
      this.setLastSubmit(newDetails.getLastSubmit());
      this.setDataUpdated(newDetails.getDataUpdated());
      this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
      this.setUpdatedTime(newDetails.getUpdatedTime());
      this.setCreatedTime(newDetails.getCreatedTime());
   }

   public UdefLookupEVO deepClone() {
      UdefLookupEVO cloned = null;

      try {
         ByteArrayOutputStream e = new ByteArrayOutputStream();
         ObjectOutputStream oos = new ObjectOutputStream(e);
         oos.writeObject(this);
         oos.flush();
         oos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
         ObjectInputStream ois = new ObjectInputStream(bis);
         cloned = (UdefLookupEVO)ois.readObject();
         ois.close();
         return cloned;
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mUdefLookupId > 0) {
         newKey = true;
         this.mUdefLookupId = 0;
      } else if(this.mUdefLookupId < 1) {
         newKey = true;
      }

      UdefLookupColumnDefEVO item;
      if(this.mColumnDef != null) {
         for(Iterator iter = (new ArrayList(this.mColumnDef.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
            item = (UdefLookupColumnDefEVO)iter.next();
            if(newKey) {
               item.setInsertPending();
            }
         }
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mUdefLookupId < 1) {
         returnCount = startCount + 1;
      }

      UdefLookupColumnDefEVO item;
      if(this.mColumnDef != null) {
         for(Iterator iter = this.mColumnDef.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
            item = (UdefLookupColumnDefEVO)iter.next();
         }
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mUdefLookupId < 1) {
         this.mUdefLookupId = startKey;
         nextKey = startKey + 1;
      }

      UdefLookupColumnDefEVO item;
      if(this.mColumnDef != null) {
         for(Iterator iter = (new ArrayList(this.mColumnDef.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
            item = (UdefLookupColumnDefEVO)iter.next();
            this.changeKey(item, this.mUdefLookupId, item.getColumnDefId());
         }
      }

      return nextKey;
   }

   public Collection<UdefLookupColumnDefEVO> getColumnDef() {
      return this.mColumnDef != null?this.mColumnDef.values():null;
   }

   public Map<UdefLookupColumnDefPK, UdefLookupColumnDefEVO> getColumnDefMap() {
      return this.mColumnDef;
   }

   public void loadColumnDefItem(UdefLookupColumnDefEVO newItem) {
      if(this.mColumnDef == null) {
         this.mColumnDef = new HashMap();
      }

      this.mColumnDef.put(newItem.getPK(), newItem);
   }

   public void addColumnDefItem(UdefLookupColumnDefEVO newItem) {
      if(this.mColumnDef == null) {
         this.mColumnDef = new HashMap();
      }

      UdefLookupColumnDefPK newPK = newItem.getPK();
      if(this.getColumnDefItem(newPK) != null) {
         throw new RuntimeException("addColumnDefItem: key already in list");
      } else {
         newItem.setInsertPending();
         this.mColumnDef.put(newPK, newItem);
      }
   }

   public void changeColumnDefItem(UdefLookupColumnDefEVO changedItem) {
      if(this.mColumnDef == null) {
         throw new RuntimeException("changeColumnDefItem: no items in collection");
      } else {
         UdefLookupColumnDefPK changedPK = changedItem.getPK();
         UdefLookupColumnDefEVO listItem = this.getColumnDefItem(changedPK);
         if(listItem == null) {
            throw new RuntimeException("changeColumnDefItem: item not in list");
         } else {
            listItem.setDetails(changedItem);
         }
      }
   }

   public void deleteColumnDefItem(UdefLookupColumnDefPK removePK) {
      UdefLookupColumnDefEVO listItem = this.getColumnDefItem(removePK);
      if(listItem == null) {
         throw new RuntimeException("removeColumnDefItem: item not in list");
      } else {
         listItem.setDeletePending();
      }
   }

   public UdefLookupColumnDefEVO getColumnDefItem(UdefLookupColumnDefPK pk) {
      return (UdefLookupColumnDefEVO)this.mColumnDef.get(pk);
   }

   public UdefLookupColumnDefEVO getColumnDefItem() {
      if(this.mColumnDef.size() != 1) {
         throw new RuntimeException("should be 1 item but size=" + this.mColumnDef.size());
      } else {
         Iterator iter = this.mColumnDef.values().iterator();
         return (UdefLookupColumnDefEVO)iter.next();
      }
   }

   protected void reset() {
      this.mModified = false;
   }

   public UdefLookupRef getEntityRef() {
      return new UdefLookupRefImpl(this.getPK(), this.mVisId);
   }

   public void postCreateInit() {
      this.mColumnDefAllItemsLoaded = true;
      if(this.mColumnDef == null) {
         this.mColumnDef = new HashMap();
      }

   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("UdefLookupId=");
      sb.append(String.valueOf(this.mUdefLookupId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("GenTableName=");
      sb.append(String.valueOf(this.mGenTableName));
      sb.append(' ');
      sb.append("AutoSubmit=");
      sb.append(String.valueOf(this.mAutoSubmit));
      sb.append(' ');
      sb.append("Scenario=");
      sb.append(String.valueOf(this.mScenario));
      sb.append(' ');
      sb.append("TimeLvl=");
      sb.append(String.valueOf(this.mTimeLvl));
      sb.append(' ');
      sb.append("YearStartMonth=");
      sb.append(String.valueOf(this.mYearStartMonth));
      sb.append(' ');
      sb.append("TimeRange=");
      sb.append(String.valueOf(this.mTimeRange));
      sb.append(' ');
      sb.append("LastSubmit=");
      sb.append(String.valueOf(this.mLastSubmit));
      sb.append(' ');
      sb.append("DataUpdated=");
      sb.append(String.valueOf(this.mDataUpdated));
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

      sb.append("UdefLookup: ");
      sb.append(this.toString());
      if(this.mColumnDefAllItemsLoaded || this.mColumnDef != null) {
         sb.delete(indent, sb.length());
         sb.append(" - ColumnDef: allItemsLoaded=");
         sb.append(String.valueOf(this.mColumnDefAllItemsLoaded));
         sb.append(" items=");
         if(this.mColumnDef == null) {
            sb.append("null");
         } else {
            sb.append(String.valueOf(this.mColumnDef.size()));
         }
      }

      if(this.mColumnDef != null) {
         Iterator var5 = this.mColumnDef.values().iterator();

         while(var5.hasNext()) {
            UdefLookupColumnDefEVO listItem = (UdefLookupColumnDefEVO)var5.next();
            listItem.print(indent + 4);
         }
      }

      return sb.toString();
   }

   public void changeKey(UdefLookupColumnDefEVO child, int newUdefLookupId, int newColumnDefId) {
      if(this.getColumnDefItem(child.getPK()) != child) {
         throw new IllegalStateException("changeKey child not found in parent");
      } else {
         this.mColumnDef.remove(child.getPK());
         child.setUdefLookupId(newUdefLookupId);
         child.setColumnDefId(newColumnDefId);
         this.mColumnDef.put(child.getPK(), child);
      }
   }

   public void setColumnDefAllItemsLoaded(boolean allItemsLoaded) {
      this.mColumnDefAllItemsLoaded = allItemsLoaded;
   }

   public boolean isColumnDefAllItemsLoaded() {
      return this.mColumnDefAllItemsLoaded;
   }
}
