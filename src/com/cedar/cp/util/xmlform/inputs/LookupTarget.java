// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:32
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.inputs;

import com.cedar.cp.util.xmlform.LookupInput;
import com.cedar.cp.util.xmlform.inputs.LookupData;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class LookupTarget implements Serializable {

   private String mId;
   private String mLookupTableName;
   private LookupData mLookupData;
   private String mLookupValueColumn;
   private int mValueColumnIndex;


   public LookupTarget(LookupInput lui, LookupData data) {
      this.mId = lui.getId();
      this.mLookupValueColumn = lui.getValue();
      this.mLookupTableName = lui.getLookupTableName();
      this.mLookupData = data;

      try {
         this.mValueColumnIndex = this.mLookupData.getColumnNameIndex(this.mLookupValueColumn);
      } catch (Exception var4) {
         throw new IllegalStateException("lookupTable: " + this.mLookupTableName + " - can\'t find value column: " + this.mLookupValueColumn, var4);
      }
   }

   public String getLookupId() {
      return this.mId;
   }

   public Object lookup(Object partition, Object key, Date date) throws Exception {
      return this.mLookupData.getValue(this.mValueColumnIndex, partition, key, date, this.getLookupId());
   }

   public Map getMapping(Object partition) throws Exception {
      return this.mLookupData.getMapping(partition, this.mValueColumnIndex, this.getLookupId());
   }

   public Map getMapping() throws Exception {
      return this.mLookupData.getMapping(this.mValueColumnIndex, this.getLookupId());
   }

   public List getKeysInUserSeq(Date paramDate) throws Exception {
      return this.mLookupData.getKeysInUserSeq(paramDate);
   }
}
