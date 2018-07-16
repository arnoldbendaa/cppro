// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 18:23:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform;

import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.CommonInput;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.InputColumnKey;
import com.cedar.cp.tc.apps.metadataimpexp.util.digester.xmlform.InputColumnValue;

public class ImpExpLookupInput extends CommonInput {

   private String mTableName;
   private String mLookupName;
   private String mKey;
   private String mValue;
   private String mPartitionColumn;
   private InputColumnKey mInputColumnKey;
   private InputColumnValue[] inputColumnValue = new InputColumnValue[3];


   public String getTableName() {
      return this.mTableName;
   }

   public void setTableName(String tableName) {
      this.mTableName = tableName;
   }

   public String getLookupName() {
      return this.mLookupName;
   }

   public void setLookupName(String lookupName) {
      this.mLookupName = lookupName;
   }

   public String getKey() {
      return this.mKey;
   }

   public void setKey(String key) {
      this.mKey = key;
   }

   public String getValue() {
      return this.mValue;
   }

   public void setValue(String value) {
      this.mValue = value;
   }

   public String getPartitionColumn() {
      return this.mPartitionColumn;
   }

   public void setPartitionColumn(String partitionColumn) {
      this.mPartitionColumn = partitionColumn;
   }

   public InputColumnKey getInputColumnKey() {
      return this.mInputColumnKey;
   }

   public void setInputColumnKey(InputColumnKey inputColumnKey) {
      this.mInputColumnKey = inputColumnKey;
   }

   public InputColumnValue[] getInputColumnValue() {
      return this.inputColumnValue;
   }

   public void setInputColumnValue(InputColumnValue[] inputColumnValue) {
      this.inputColumnValue = inputColumnValue;
   }
}
