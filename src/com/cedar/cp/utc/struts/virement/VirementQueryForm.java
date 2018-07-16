// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.utc.common.CPForm;
import com.cedar.cp.utc.struts.virement.LazyList;
import com.cedar.cp.utc.struts.virement.VirementQueryForm$1;
import com.cedar.cp.utc.struts.virement.VirementStatusDTO;
import com.cedar.cp.util.StringLexer;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletRequest;
import org.apache.struts.action.ActionMapping;

public class VirementQueryForm extends CPForm {

   private int mModelId;
   private String mModelVisId;
   private int mFinanceCubeId;
   private String mFinanceCubeVisId;
   private int mNoOfDims;
   private boolean mDisableRASecurity;
   private String mOwner;
   private String mAuthoriser;
   private List<UserRef> mOriginators;
   private List<UserRef> mAuthorisers;
   private String mRequestId;
   private String mFromValue;
   private String mToValue;
   private String mFromCreationDate;
   private String mToCreationDate;
   private String mHeaders;
   private String mSelectedIds;
   private String mSelectedStructureIds;
   private String mSelectedIdentifiers;
   private String mSelectedDescriptions;
   private String mSelectedCell;
   private String mRootRa;
   private String mUserAction;
   private String mOutputText;
   private String mStatus;
   private List<VirementStatusDTO> mStati = new ArrayList();
   private LazyList mModelsAndFinanceCubes = new LazyList(new VirementQueryForm$1(this));
   private String mBudgetCycleId = null;


   public VirementQueryForm() {
      this.mStati.add(new VirementStatusDTO(-1, "Any Status"));
      this.mStati.add(new VirementStatusDTO(0, "Not Submitted"));
      this.mStati.add(new VirementStatusDTO(1, "Not Authorised"));
      this.mStati.add(new VirementStatusDTO(2, "Authorised"));
      this.mStati.add(new VirementStatusDTO(3, "Processed"));
   }

   public void reset(ActionMapping mapping, ServletRequest servletRequest) {
      this.mModelId = 0;
      this.mModelVisId = null;
      this.mFinanceCubeId = 0;
      this.mFinanceCubeVisId = null;
      this.mNoOfDims = 0;
      this.mDisableRASecurity = false;
      this.mOwner = null;
      this.mAuthoriser = null;
      this.mOriginators = null;
      this.mAuthorisers = null;
      this.mRequestId = null;
      this.mFromValue = null;
      this.mToValue = null;
      this.mFromCreationDate = null;
      this.mToCreationDate = null;
      this.mHeaders = null;
      this.mSelectedIds = null;
      this.mSelectedStructureIds = null;
      this.mSelectedIdentifiers = null;
      this.mSelectedDescriptions = null;
      this.mSelectedCell = null;
      this.mRootRa = null;
      this.mUserAction = null;
      this.mOutputText = null;
      this.mModelsAndFinanceCubes.clear();
      super.reset(mapping, servletRequest);
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }

   public String getModelVisId() {
      return this.mModelVisId;
   }

   public void setModelVisId(String modelVisId) {
      this.mModelVisId = modelVisId;
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public void setFinanceCubeId(int financeCubeId) {
      this.mFinanceCubeId = financeCubeId;
   }

   public String getFinanceCubeVisId() {
      return this.mFinanceCubeVisId;
   }

   public void setFinanceCubeVisId(String financeCubeVisId) {
      this.mFinanceCubeVisId = financeCubeVisId;
   }

   public List getModelsAndFinanceCubes() {
      return this.mModelsAndFinanceCubes;
   }

   public int getNoOfDims() {
      return this.mNoOfDims;
   }

   public void setNoOfDims(int noOfDims) {
      this.mNoOfDims = noOfDims;
   }

   public boolean isDisableRASecurity() {
      return this.mDisableRASecurity;
   }

   public void setDisableRASecurity(boolean disableRASecurity) {
      this.mDisableRASecurity = disableRASecurity;
   }

   public List<UserRef> getOriginators() {
      return this.mOriginators;
   }

   public void setOriginators(List<UserRef> originators) {
      this.mOriginators = originators;
   }

   public List<UserRef> getAuthorisers() {
      return this.mAuthorisers;
   }

   public void setAuthorisers(List<UserRef> authorisers) {
      this.mAuthorisers = authorisers;
   }

   public String getOwner() {
      return this.mOwner;
   }

   public void setOwner(String owner) {
      this.mOwner = owner;
   }

   public String getAuthoriser() {
      return this.mAuthoriser;
   }

   public void setAuthoriser(String authoriser) {
      this.mAuthoriser = authoriser;
   }

   public String getRequestId() {
      return this.mRequestId;
   }

   public void setRequestId(String requestId) {
      this.mRequestId = requestId;
   }

   public String getFromValue() {
      return this.mFromValue;
   }

   public void setFromValue(String fromValue) {
      this.mFromValue = fromValue;
   }

   public String getToValue() {
      return this.mToValue;
   }

   public void setToValue(String toValue) {
      this.mToValue = toValue;
   }

   public String getFromCreationDate() {
      return this.mFromCreationDate;
   }

   public void setFromCreationDate(String fromCreationDate) {
      this.mFromCreationDate = fromCreationDate;
   }

   public String getToCreationDate() {
      return this.mToCreationDate;
   }

   public void setToCreationDate(String toCreationDate) {
      this.mToCreationDate = toCreationDate;
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

   public String getSelectedStructureIds() {
      return this.mSelectedStructureIds;
   }

   public void setSelectedStructureIds(String selectedStructureIds) {
      this.mSelectedStructureIds = selectedStructureIds;
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

   public String getRootRa() {
      return this.mRootRa;
   }

   public void setRootRa(String rootRa) {
      this.mRootRa = rootRa;
   }

   public String getUserAction() {
      return this.mUserAction;
   }

   public void setUserAction(String userAction) {
      this.mUserAction = userAction;
   }

   public List<int[]> getStructureElements() {
      ArrayList result = new ArrayList();
      StringLexer seTokens = new StringLexer(this.getSelectedIds(), "_*_");
      StringLexer sTokens = new StringLexer(this.getSelectedStructureIds(), "_*_");

      while(seTokens.hasMoreTokens()) {
         int seId = Integer.valueOf(seTokens.nextToken()).intValue();
         int sId = Integer.valueOf(sTokens.nextToken()).intValue();
         result.add(new int[]{sId, seId});
      }

      return result;
   }

   public String getOutputText() {
      return this.mOutputText;
   }

   public void setOutputText(String outputText) {
      this.mOutputText = outputText;
   }

   public String getStatus() {
      return this.mStatus;
   }

   public void setStatus(String status) {
      this.mStatus = status;
   }

   public List<VirementStatusDTO> getStati() {
      return this.mStati;
   }

   public void setStati(List<VirementStatusDTO> stati) {
      this.mStati = stati;
   }

   public String getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public void setBudgetCycleId(String budgetCycleId) {
      this.mBudgetCycleId = budgetCycleId;
   }
}
