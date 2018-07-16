// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:29
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.udeflookup;

import com.cedar.cp.api.udeflookup.UdefLookupColumnDefRef;
import com.cedar.cp.dto.udeflookup.UdefLookupColumnDefCK;
import com.cedar.cp.dto.udeflookup.UdefLookupColumnDefPK;
import com.cedar.cp.dto.udeflookup.UdefLookupColumnDefRefImpl;
import com.cedar.cp.ejb.impl.udeflookup.UdefLookupEVO;
import java.io.Serializable;

public class UdefLookupColumnDefEVO implements Serializable {

   private transient UdefLookupColumnDefPK mPK;
   private int mUdefLookupId;
   private int mColumnDefId;
   private int mColumnDefIndex;
   private String mName;
   private String mTitle;
   private int mType;
   private Integer mColumnSize;
   private Integer mColumnDp;
   private boolean mOptional;
   private boolean mInsertPending;
   private boolean mDeletePending;
   private boolean mModified;
   public static final int TYPE_STRING = 0;
   public static final int TYPE_NUMBER = 1;
   public static final int TYPE_BOOLEAN = 2;
   public static final int TYPE_DATE = 3;


   public UdefLookupColumnDefEVO() {}

   public UdefLookupColumnDefEVO(int newUdefLookupId, int newColumnDefId, int newColumnDefIndex, String newName, String newTitle, int newType, Integer newColumnSize, Integer newColumnDp, boolean newOptional) {
      this.mUdefLookupId = newUdefLookupId;
      this.mColumnDefId = newColumnDefId;
      this.mColumnDefIndex = newColumnDefIndex;
      this.mName = newName;
      this.mTitle = newTitle;
      this.mType = newType;
      this.mColumnSize = newColumnSize;
      this.mColumnDp = newColumnDp;
      this.mOptional = newOptional;
   }

   public UdefLookupColumnDefPK getPK() {
      if(this.mPK == null) {
         this.mPK = new UdefLookupColumnDefPK(this.mUdefLookupId, this.mColumnDefId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getUdefLookupId() {
      return this.mUdefLookupId;
   }

   public int getColumnDefId() {
      return this.mColumnDefId;
   }

   public int getColumnDefIndex() {
      return this.mColumnDefIndex;
   }

   public String getName() {
      return this.mName;
   }

   public String getTitle() {
      return this.mTitle;
   }

   public int getType() {
      return this.mType;
   }

   public Integer getColumnSize() {
      return this.mColumnSize;
   }

   public Integer getColumnDp() {
      return this.mColumnDp;
   }

   public boolean getOptional() {
      return this.mOptional;
   }

   public void setUdefLookupId(int newUdefLookupId) {
      if(this.mUdefLookupId != newUdefLookupId) {
         this.mModified = true;
         this.mUdefLookupId = newUdefLookupId;
         this.mPK = null;
      }
   }

   public void setColumnDefId(int newColumnDefId) {
      if(this.mColumnDefId != newColumnDefId) {
         this.mModified = true;
         this.mColumnDefId = newColumnDefId;
         this.mPK = null;
      }
   }

   public void setColumnDefIndex(int newColumnDefIndex) {
      if(this.mColumnDefIndex != newColumnDefIndex) {
         this.mModified = true;
         this.mColumnDefIndex = newColumnDefIndex;
      }
   }

   public void setType(int newType) {
      if(this.mType != newType) {
         this.mModified = true;
         this.mType = newType;
      }
   }

   public void setOptional(boolean newOptional) {
      if(this.mOptional != newOptional) {
         this.mModified = true;
         this.mOptional = newOptional;
      }
   }

   public void setName(String newName) {
      if(this.mName != null && newName == null || this.mName == null && newName != null || this.mName != null && newName != null && !this.mName.equals(newName)) {
         this.mName = newName;
         this.mModified = true;
      }

   }

   public void setTitle(String newTitle) {
      if(this.mTitle != null && newTitle == null || this.mTitle == null && newTitle != null || this.mTitle != null && newTitle != null && !this.mTitle.equals(newTitle)) {
         this.mTitle = newTitle;
         this.mModified = true;
      }

   }

   public void setColumnSize(Integer newColumnSize) {
      if(this.mColumnSize != null && newColumnSize == null || this.mColumnSize == null && newColumnSize != null || this.mColumnSize != null && newColumnSize != null && !this.mColumnSize.equals(newColumnSize)) {
         this.mColumnSize = newColumnSize;
         this.mModified = true;
      }

   }

   public void setColumnDp(Integer newColumnDp) {
      if(this.mColumnDp != null && newColumnDp == null || this.mColumnDp == null && newColumnDp != null || this.mColumnDp != null && newColumnDp != null && !this.mColumnDp.equals(newColumnDp)) {
         this.mColumnDp = newColumnDp;
         this.mModified = true;
      }

   }

   public void setDetails(UdefLookupColumnDefEVO newDetails) {
      this.setUdefLookupId(newDetails.getUdefLookupId());
      this.setColumnDefId(newDetails.getColumnDefId());
      this.setColumnDefIndex(newDetails.getColumnDefIndex());
      this.setName(newDetails.getName());
      this.setTitle(newDetails.getTitle());
      this.setType(newDetails.getType());
      this.setColumnSize(newDetails.getColumnSize());
      this.setColumnDp(newDetails.getColumnDp());
      this.setOptional(newDetails.getOptional());
   }

   public UdefLookupColumnDefEVO deepClone() {
      UdefLookupColumnDefEVO cloned = new UdefLookupColumnDefEVO();
      cloned.mModified = this.mModified;
      cloned.mInsertPending = this.mInsertPending;
      cloned.mDeletePending = this.mDeletePending;
      cloned.mUdefLookupId = this.mUdefLookupId;
      cloned.mColumnDefId = this.mColumnDefId;
      cloned.mColumnDefIndex = this.mColumnDefIndex;
      cloned.mType = this.mType;
      cloned.mOptional = this.mOptional;
      if(this.mName != null) {
         cloned.mName = this.mName;
      }

      if(this.mTitle != null) {
         cloned.mTitle = this.mTitle;
      }

      if(this.mColumnSize != null) {
         cloned.mColumnSize = Integer.valueOf(this.mColumnSize.toString());
      }

      if(this.mColumnDp != null) {
         cloned.mColumnDp = Integer.valueOf(this.mColumnDp.toString());
      }

      return cloned;
   }

   public void prepareForInsert(UdefLookupEVO parent) {
      boolean newKey = this.insertPending();
      if(this.mColumnDefId > 0) {
         newKey = true;
         if(parent == null) {
            this.setColumnDefId(-this.mColumnDefId);
         } else {
            parent.changeKey(this, this.mUdefLookupId, -this.mColumnDefId);
         }
      } else if(this.mColumnDefId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mColumnDefId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(UdefLookupEVO parent, int startKey) {
      int nextKey = startKey;
      if(this.mColumnDefId < 1) {
         parent.changeKey(this, this.mUdefLookupId, startKey);
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

   public UdefLookupColumnDefRef getEntityRef(UdefLookupEVO evoUdefLookup, String entityText) {
      return new UdefLookupColumnDefRefImpl(new UdefLookupColumnDefCK(evoUdefLookup.getPK(), this.getPK()), entityText);
   }

   public UdefLookupColumnDefCK getCK(UdefLookupEVO evoUdefLookup) {
      return new UdefLookupColumnDefCK(evoUdefLookup.getPK(), this.getPK());
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("UdefLookupId=");
      sb.append(String.valueOf(this.mUdefLookupId));
      sb.append(' ');
      sb.append("ColumnDefId=");
      sb.append(String.valueOf(this.mColumnDefId));
      sb.append(' ');
      sb.append("ColumnDefIndex=");
      sb.append(String.valueOf(this.mColumnDefIndex));
      sb.append(' ');
      sb.append("Name=");
      sb.append(String.valueOf(this.mName));
      sb.append(' ');
      sb.append("Title=");
      sb.append(String.valueOf(this.mTitle));
      sb.append(' ');
      sb.append("Type=");
      sb.append(String.valueOf(this.mType));
      sb.append(' ');
      sb.append("ColumnSize=");
      sb.append(String.valueOf(this.mColumnSize));
      sb.append(' ');
      sb.append("ColumnDp=");
      sb.append(String.valueOf(this.mColumnDp));
      sb.append(' ');
      sb.append("Optional=");
      sb.append(String.valueOf(this.mOptional));
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

      sb.append("UdefLookupColumnDef: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
