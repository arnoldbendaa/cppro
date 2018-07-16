// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:48
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.datatype;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.datatype.DataTypeNode;
import com.cedar.cp.api.datatype.DataTypeRef;
import java.io.Serializable;

public class DataTypeNodeImpl implements DataTypeNode, Serializable {

   private DataTypeRef mDataTypeRef;
   private String mVisId;
   private String mDescription;
   private int mSubType;


   public DataTypeNodeImpl(EntityList list) {
      this.mDataTypeRef = (DataTypeRef)list.getValueAt(0, "DataType");
      this.mVisId = this.mDataTypeRef.toString();
      this.mDescription = list.getValueAt(0, "Description").toString();
      this.mSubType = ((Integer)list.getValueAt(0, "SubType")).intValue();
   }

   public DataTypeNodeImpl(DataTypeRef dataTypeRef, String visId, String description) {
      this.mDataTypeRef = dataTypeRef;
      this.mVisId = visId;
      this.mDescription = description;
   }

   public DataTypeRef getDataTypeRef() {
      return this.mDataTypeRef;
   }

   public void setDataTypeRef(DataTypeRef dataTypeRef) {
      this.mDataTypeRef = dataTypeRef;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String visId) {
      this.mVisId = visId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public int getSubType() {
      return this.mSubType;
   }

   public void setSubType(int subType) {
      this.mSubType = subType;
   }

   public String toString() {
      return this.mDescription == null?this.mVisId:this.mVisId + " - " + this.mDescription;
   }
}
