// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.udeflookup;

import com.cedar.cp.api.udeflookup.UdefLookup;
import com.cedar.cp.dto.udeflookup.UdefColumnImpl;
import com.cedar.cp.dto.udeflookup.UdefLookupPK;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class UdefLookupImpl implements UdefLookup, Serializable, Cloneable {

   private List mColumnDef;
   private List mRemoveKeys;
   private List mTableData;
   private Object mPrimaryKey;
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


   public UdefLookupImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mGenTableName = "";
      this.mAutoSubmit = false;
      this.mScenario = false;
      this.mTimeLvl = 0;
      this.mYearStartMonth = 0;
      this.mTimeRange = false;
      this.mLastSubmit = null;
      this.mDataUpdated = null;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (UdefLookupPK)paramKey;
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

   public boolean isAutoSubmit() {
      return this.mAutoSubmit;
   }

   public boolean isScenario() {
      return this.mScenario;
   }

   public int getTimeLvl() {
      return this.mTimeLvl;
   }

   public int getYearStartMonth() {
      return this.mYearStartMonth;
   }

   public boolean isTimeRange() {
      return this.mTimeRange;
   }

   public Timestamp getLastSubmit() {
      return this.mLastSubmit;
   }

   public Timestamp getDataUpdated() {
      return this.mDataUpdated;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setGenTableName(String paramGenTableName) {
      this.mGenTableName = paramGenTableName;
   }

   public void setAutoSubmit(boolean paramAutoSubmit) {
      this.mAutoSubmit = paramAutoSubmit;
   }

   public void setScenario(boolean paramScenario) {
      this.mScenario = paramScenario;
   }

   public void setTimeLvl(int paramTimeLvl) {
      this.mTimeLvl = paramTimeLvl;
   }

   public void setYearStartMonth(int paramYearStartMonth) {
      this.mYearStartMonth = paramYearStartMonth;
   }

   public void setTimeRange(boolean paramTimeRange) {
      this.mTimeRange = paramTimeRange;
   }

   public void setLastSubmit(Timestamp paramLastSubmit) {
      this.mLastSubmit = paramLastSubmit;
   }

   public void setDataUpdated(Timestamp paramDataUpdated) {
      this.mDataUpdated = paramDataUpdated;
   }

   public List getColumnDef() {
      if(this.mColumnDef == null) {
         this.mColumnDef = new ArrayList();
      }

      return this.mColumnDef;
   }

   public int getNextIndex() {
      int value = 0;
      Iterator iter = this.getColumnDef().iterator();

      while(iter.hasNext()) {
         UdefColumnImpl col = (UdefColumnImpl)iter.next();
         if(value < col.getIndex().intValue()) {
            value = col.getIndex().intValue();
         }
      }

      ++value;
      return value;
   }

   public void setColumnDef(List columnDef) {
      this.mColumnDef = columnDef;
   }

   public void addRemoveKey(Object o) {
      if(this.mRemoveKeys == null) {
         this.mRemoveKeys = new ArrayList();
      }

      this.mRemoveKeys.add(o);
   }

   public List getRemoveKeys() {
      return this.mRemoveKeys == null?Collections.EMPTY_LIST:this.mRemoveKeys;
   }

   public List getTableData() {
      return this.mTableData;
   }

   public void setTableData(List tableData) {
      this.mTableData = tableData;
   }
}
