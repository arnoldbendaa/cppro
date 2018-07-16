// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.recharge;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.recharge.RechargeGroup;
import com.cedar.cp.dto.model.recharge.RechargeGroupPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RechargeGroupImpl implements RechargeGroup, Serializable, Cloneable {

   List mSelectedRecharge;
   int mModelId;
   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private int mVersionNum;


   public RechargeGroupImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (RechargeGroupPK)paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
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

   public List getSelectedRecharge() {
      return this.mSelectedRecharge;
   }

   public void setSelectedRecharge(List selectedRecharge) {
      this.mSelectedRecharge = selectedRecharge;
   }

   public void removeSelectedRecharge(int row) throws ValidationException {
      if(this.mSelectedRecharge == null) {
         throw new ValidationException("No Recharges to remove from parent");
      } else {
         this.mSelectedRecharge.remove(row);
      }
   }

   public void addSelectedRecharge(List row) {
      if(this.mSelectedRecharge == null) {
         this.mSelectedRecharge = new ArrayList();
      }

      this.mSelectedRecharge.add(row);
   }

   public EntityList getAvailableRecharge() {
      return null;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void setModelId(int modelId) {
      this.mModelId = modelId;
   }
}
