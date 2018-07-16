// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:03
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.virement;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.virement.VirementAccount;
import com.cedar.cp.api.model.virement.VirementCategory;
import com.cedar.cp.api.model.virement.VirementLocation;
import com.cedar.cp.dto.model.virement.VirementAccountImpl;
import com.cedar.cp.dto.model.virement.VirementCategoryCK;
import com.cedar.cp.dto.model.virement.VirementCategoryPK;
import com.cedar.cp.dto.model.virement.VirementLocationImpl;
import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.awt.QListModel;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VirementCategoryImpl implements VirementCategory, Serializable, Cloneable {

   private transient QListModel mResponsibilityAreas;
   private transient QListModel mAccounts;
   private List mResponisbilityAreaList;
   private List mAccountAreaList;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private long mTranLimit;
   private long mTotalLimitIn;
   private long mTotalLimitOut;
   private int mVersionNum;
   private ModelRef mModelRef;


   public VirementCategoryImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mTranLimit = 0L;
      this.mTotalLimitIn = 0L;
      this.mTotalLimitOut = 0L;
      this.mAccountAreaList = new ArrayList();
      this.mResponisbilityAreaList = new ArrayList();
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (VirementCategoryPK)paramKey;
   }

   public void setPrimaryKey(VirementCategoryCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public long getTranLimit() {
      return this.mTranLimit;
   }

   public long getTotalLimitIn() {
      return this.mTotalLimitIn;
   }

   public long getTotalLimitOut() {
      return this.mTotalLimitOut;
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public void setModelRef(ModelRef ref) {
      this.mModelRef = ref;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setVisId(String paramVisId) {
      this.mVisId = paramVisId;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setTranLimit(long paramTranLimit) {
      this.mTranLimit = paramTranLimit;
   }

   public void setTotalLimitIn(long paramTotalLimitIn) {
      this.mTotalLimitIn = paramTotalLimitIn;
   }

   public void setTotalLimitOut(long paramTotalLimitOut) {
      this.mTotalLimitOut = paramTotalLimitOut;
   }

   public void setTranLimit(double paramTranLimit) {
      this.mTranLimit = GeneralUtils.convertFinancialValueToDB(paramTranLimit);
   }

   public void setTotalLimitIn(double paramTotalLimit) {
      this.mTotalLimitIn = GeneralUtils.convertFinancialValueToDB(paramTotalLimit);
   }

   public double getTotalLimitInAsDouble() {
      return GeneralUtils.convertDBToFinancialValue(this.mTotalLimitIn);
   }

   public void setTotalLimitOut(double paramTotalLimit) {
      this.mTotalLimitOut = GeneralUtils.convertFinancialValueToDB(paramTotalLimit);
   }

   public double getTotalLimitOutAsDouble() {
      return GeneralUtils.convertDBToFinancialValue(this.mTotalLimitOut);
   }

   public double getTranLimitAsDouble() {
      return GeneralUtils.convertDBToFinancialValue(this.mTranLimit);
   }

   public QListModel getResponsibilityAreas() {
      if(this.mResponsibilityAreas == null) {
         this.mResponsibilityAreas = new QListModel(this.mResponisbilityAreaList);
      }

      return this.mResponsibilityAreas;
   }

   public QListModel getAccounts() {
      if(this.mAccounts == null) {
         this.mAccounts = new QListModel(this.mAccountAreaList);
      }

      return this.mAccounts;
   }

   public void removeResponsibilityArea(VirementLocation location) throws ValidationException {
      int index = this.getResponsibilityAreas().indexOf(location);
      if(index < 0) {
         throw new ValidationException("Not found");
      } else {
         this.getResponsibilityAreas().remove(index);
      }
   }

   public VirementLocationImpl findVirementLocation(int structureElementId) {
      Iterator i = this.mResponisbilityAreaList.iterator();

      VirementLocationImpl loc;
      do {
         if(!i.hasNext()) {
            return null;
         }

         loc = (VirementLocationImpl)i.next();
      } while(loc.getKey().getStructureElementId() != structureElementId);

      return loc;
   }

   public VirementAccountImpl findVirementAccount(int structureElementId) {
      Iterator i = this.mAccountAreaList.iterator();

      VirementAccountImpl acct;
      do {
         if(!i.hasNext()) {
            return null;
         }

         acct = (VirementAccountImpl)i.next();
      } while(acct.getKey().getStructureElementId() != structureElementId);

      return acct;
   }

   public void removeVirementAccount(VirementAccount virementAccount) throws ValidationException {
      int index = this.getAccounts().indexOf(virementAccount);
      if(index < 0) {
         throw new ValidationException("Not found");
      } else {
         this.getAccounts().remove(index);
      }
   }

   public void setResponisbilityAreaList(List responisbilityAreaList) {
      this.mResponisbilityAreaList = responisbilityAreaList;
      this.mResponsibilityAreas = null;
   }

   public void setAccountAreaList(List accountAreaList) {
      this.mAccountAreaList = accountAreaList;
      this.mAccounts = null;
   }
}
