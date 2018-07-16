// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.topdown;

import com.cedar.cp.utc.struts.topdown.MassUpdateForm;

public class DimensionForm extends MassUpdateForm {

   private int mNoOfDims;
   private String mAction;
   private String mRequiredDataType;
   private boolean mDataTypeMultiSelect;
   private boolean mWriteableDataTypes;
   private String mHeaders;
   private String mSelectedIds;
   private String mSelectedIdentifiers;
   private String mSelectedDescriptions;
   private String mSelectedStructureIds;


   public int getNoOfDims() {
      return this.mNoOfDims;
   }

   public void setNoOfDims(int noOfDims) {
      this.mNoOfDims = noOfDims;
   }

   public String getAction() {
      return this.mAction;
   }

   public void setAction(String action) {
      this.mAction = action;
   }

   public boolean isRequireDataType() {
      return this.mRequiredDataType != null && this.mRequiredDataType.trim().length() > 0;
   }

   public String getRequiredDataType() {
      return this.mRequiredDataType;
   }

   public void setRequiredDataType(String requiredDataType) {
      this.mRequiredDataType = requiredDataType;
   }

   public String getHeaders() {
      return this.mHeaders;
   }

   public void setHeaders(String headers) {
      this.mHeaders = headers;
   }

   public String getSelectedIds() {
      return this.mSelectedIds;
   }

   public void setSelectedIds(String selectedIds) {
      this.mSelectedIds = selectedIds;
   }

   public String getSelectedIdentifiers() {
      return this.mSelectedIdentifiers;
   }

   public void setSelectedIdentifiers(String selectedIdentifiers) {
      this.mSelectedIdentifiers = selectedIdentifiers;
   }

   public String getSelectedDescriptions() {
      return this.mSelectedDescriptions;
   }

   public void setSelectedDescriptions(String selectedDescriptions) {
      this.mSelectedDescriptions = selectedDescriptions;
   }

   public String getSelectedStructureIds() {
      return this.mSelectedStructureIds;
   }

   public void setSelectedStructureIds(String selectedStructureIds) {
      this.mSelectedStructureIds = selectedStructureIds;
   }

   public boolean isDataTypeMultiSelect() {
      return this.mDataTypeMultiSelect;
   }

   public void setDataTypeMultiSelect(boolean dataTypeMultiSelect) {
      this.mDataTypeMultiSelect = dataTypeMultiSelect;
   }

   public boolean isWriteableDataTypes() {
      return this.mWriteableDataTypes;
   }

   public void setWriteableDataTypes(boolean writeableDataTypes) {
      this.mWriteableDataTypes = writeableDataTypes;
   }
}
