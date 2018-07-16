// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:16
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementRequestRef;
import com.cedar.cp.api.user.UserRef;
import java.util.List;

public class VirementSummaryDTO {

   private static final String[] STATUS = new String[]{"Not Submitted", "Awaiting Authorisation", "Authorised"};
   private VirementRequestRef mRequestRef;
   private UserRef mOwnerRef;
   private ModelRef mModelRef;
   private int mRequestId;
   private String mDateSubmitted;
   private String mDateCreated;
   private List mAuthorisers;
   private String mReason;
   private String mReference;
   private int mStatus;


   public VirementSummaryDTO(VirementRequestRef requestRef, UserRef owner, List authorisers, String dateCreated, String dateSubmitted, ModelRef modelRef, String reason, String reference, int requestId, int status) {
      this.mRequestRef = requestRef;
      this.mOwnerRef = owner;
      this.mAuthorisers = authorisers;
      this.mDateCreated = dateCreated;
      this.mDateSubmitted = dateSubmitted;
      this.mModelRef = modelRef;
      this.mReason = reason;
      this.mReference = reference;
      this.mRequestId = requestId;
      this.mStatus = status;
   }

   public UserRef getOwnerRef() {
      return this.mOwnerRef;
   }

   public List getAuthorisers() {
      return this.mAuthorisers;
   }

   public String getDateCreated() {
      return this.mDateCreated;
   }

   public String getDateSubmitted() {
      return this.mDateSubmitted;
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public String getReason() {
      return this.mReason;
   }

   public int getRequestId() {
      return this.mRequestId;
   }

   public int getStatus() {
      return this.mStatus;
   }

   public String getStatusText() {
      return STATUS[this.getStatus()];
   }

   public VirementRequestRef getRequestRef() {
      return this.mRequestRef;
   }

   public String getReference() {
      return this.mReference;
   }

   public void setReference(String reference) {
      this.mReference = reference;
   }

}
