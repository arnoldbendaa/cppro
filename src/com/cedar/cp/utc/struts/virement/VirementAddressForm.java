// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.struts.virement.VirementDataEntryForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

public class VirementAddressForm extends VirementDataEntryForm {

   private String mKey;
   private int mNoOfDims;
   private boolean mDisableRASecurity;
   private String mHeaders;
   private String mSelectedIds;
   private String mSelectedStructureIds;
   private String mSelectedIdentifiers;
   private String mSelectedDescriptions;
   private String mSelectedCell;
   private String mRootRa;
   private boolean mAddGroup;
   private String mTransferType = "T";


   public boolean isAddGroup() {
      return this.mAddGroup;
   }

   public void setAddGroup(boolean addGroup) {
      this.mAddGroup = addGroup;
   }

   public int getNoOfDims() {
      return this.mNoOfDims;
   }

   public void setNoOfDims(int noOfDims) {
      this.mNoOfDims = noOfDims;
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

   public String getSelectedCell() {
      return this.mSelectedCell;
   }

   public void setSelectedCell(String selectedCell) {
      this.mSelectedCell = selectedCell;
   }

   public String getTransferType() {
      return this.mTransferType;
   }

   public void setTransferType(String transferType) {
      this.mTransferType = transferType;
   }

   public String getSelectedStructureIds() {
      return this.mSelectedStructureIds;
   }

   public void setSelectedStructureIds(String selectedStructureIds) {
      this.mSelectedStructureIds = selectedStructureIds;
   }

   public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
      super.reset(actionMapping, httpServletRequest);
      this.setNoOfDims(0);
      this.setHeaders((String)null);
      this.setSelectedIds((String)null);
      this.setSelectedIdentifiers((String)null);
      this.setSelectedStructureIds((String)null);
      this.setRootRa("0");
      this.setSelectedCell((String)null);
      this.setAddGroup(false);
      this.setTransferType("T");
   }

   public String getKey() {
      return this.mKey;
   }

   public void setKey(String key) {
      this.mKey = key;
   }

   public String getRootRa() {
      return this.mRootRa;
   }

   public void setRootRa(String rootRa) {
      this.mRootRa = rootRa;
   }

   public boolean isDisableRASecurity() {
      return this.mDisableRASecurity;
   }

   public void setDisableRASecurity(boolean disableRASecurity) {
      this.mDisableRASecurity = disableRASecurity;
   }
}
