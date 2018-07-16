// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.model.virement.VirementAccount;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.virement.VirementAccountPK;
import com.cedar.cp.util.GeneralUtils;
import java.io.Serializable;

public class VirementAccountImpl implements VirementAccount, Serializable, Cloneable {

   private VirementAccountPK mKey;
   private StructureElementPK mStructureElementKey;
   private String mVisId;
   private String mDescription;
   private long mTotalLimitIn;
   private long mTotalLimitOut;
   private long mTranLimit;
   private boolean mInputAllowed;
   private boolean mOutputAllowed;


   public VirementAccountImpl(VirementAccountPK key, String visId, String description, long tranLimit, long totalLimitIn, long totalLimitOut, boolean outputAllowed, boolean inputAllowed) {
      this.mVisId = visId;
      this.mTranLimit = tranLimit;
      this.mTotalLimitIn = totalLimitIn;
      this.mTotalLimitOut = totalLimitOut;
      this.mOutputAllowed = outputAllowed;
      this.mKey = key;
      this.mInputAllowed = inputAllowed;
      this.mDescription = description;
   }

   public VirementAccountImpl(VirementAccountPK key, String visId, String description, double tranLimit, double totalLimitIn, double totalLimitOut, boolean outputAllowed, boolean inputAllowed) {
      this(key, visId, description, GeneralUtils.convertFinancialValueToDB(tranLimit), GeneralUtils.convertFinancialValueToDB(totalLimitIn), GeneralUtils.convertFinancialValueToDB(totalLimitOut), outputAllowed, inputAllowed);
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Object getPrimaryKey() {
      return this.mKey;
   }

   public boolean isInputAllowed() {
      return this.mInputAllowed;
   }

   public void setInputAllowed(boolean inputAllowed) {
      this.mInputAllowed = inputAllowed;
   }

   public VirementAccountPK getKey() {
      return this.mKey;
   }

   public void setKey(VirementAccountPK key) {
      this.mKey = key;
   }

   public boolean isOutputAllowed() {
      return this.mOutputAllowed;
   }

   public void setOutputAllowed(boolean outputAllowed) {
      this.mOutputAllowed = outputAllowed;
   }

   public long getTotalLimitInAsLong() {
      return this.mTotalLimitIn;
   }

   public double getTotalLimitIn() {
      return GeneralUtils.convertDBToFinancialValue(this.mTotalLimitIn);
   }

   public void setTotalLimitIn(double totalLimitIn) {
      this.mTotalLimitIn = GeneralUtils.convertFinancialValueToDB(totalLimitIn);
   }

   public long getTotalLimitOutAsLong() {
      return this.mTotalLimitOut;
   }

   public double getTotalLimitOut() {
      return GeneralUtils.convertDBToFinancialValue(this.mTotalLimitOut);
   }

   public void setTotalLimitOut(double totalLimitOut) {
      this.mTotalLimitOut = GeneralUtils.convertFinancialValueToDB(totalLimitOut);
   }

   public long getTranLimitAsLong() {
      return this.mTranLimit;
   }

   public double getTranLimit() {
      return GeneralUtils.convertDBToFinancialValue(this.mTranLimit);
   }

   public void setTranLimit(double tranLimit) {
      this.mTranLimit = GeneralUtils.convertFinancialValueToDB(tranLimit);
   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String visId) {
      this.mVisId = visId;
   }

   public void setAll(VirementAccountImpl src) {
      this.mKey = src.mKey;
      this.mVisId = src.mVisId;
      this.mDescription = src.mDescription;
      this.mTotalLimitIn = src.mTotalLimitIn;
      this.mTotalLimitOut = src.mTotalLimitOut;
      this.mTranLimit = src.mTranLimit;
      this.mInputAllowed = src.mInputAllowed;
      this.mOutputAllowed = src.mOutputAllowed;
   }

   public Object getStructureElementKey() {
      return new StructureElementPK(this.mKey.mStructureId, this.mKey.mStructureElementId);
   }

   public Object clone() throws CloneNotSupportedException {
      VirementAccountImpl vaImpl = (VirementAccountImpl)super.clone();
      vaImpl.setAll(this);
      return vaImpl;
   }
}
