// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model.udwp;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.udwp.WeightingDeployment;
import com.cedar.cp.api.model.udwp.WeightingProfileRef;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentCK;
import com.cedar.cp.dto.model.udwp.WeightingDeploymentPK;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class WeightingDeploymentImpl implements WeightingDeployment, Serializable, Cloneable {

   private Map mAccountElments = new HashMap();
   private Map mBusinessElements = new HashMap();
   private Set mDataTypes = new HashSet();
   private String mProfileDescription;
   private Object mPrimaryKey;
   private int mProfileId;
   private boolean mAnyAccount;
   private boolean mAnyBusiness;
   private boolean mAnyDataType;
   private int mWeighting;
   private WeightingProfileRef mWeightingProfileRef;
   private ModelRef mModelRef;


   public WeightingDeploymentImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mProfileId = 0;
      this.mAnyAccount = false;
      this.mAnyBusiness = false;
      this.mAnyDataType = false;
      this.mWeighting = 0;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (WeightingDeploymentPK)paramKey;
   }

   public void setPrimaryKey(WeightingDeploymentCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public int getProfileId() {
      return this.mProfileId;
   }

   public boolean isAnyAccount() {
      return this.mAnyAccount;
   }

   public boolean isAnyBusiness() {
      return this.mAnyBusiness;
   }

   public boolean isAnyDataType() {
      return this.mAnyDataType;
   }

   public int getWeighting() {
      return this.mWeighting;
   }

   public WeightingProfileRef getWeightingProfileRef() {
      return this.mWeightingProfileRef;
   }

   public ModelRef getModelRef() {
      return this.mModelRef;
   }

   public void setWeightingProfileRef(WeightingProfileRef ref) {
      this.mWeightingProfileRef = ref;
   }

   public void setModelRef(ModelRef ref) {
      this.mModelRef = ref;
   }

   public void setProfileId(int paramProfileId) {
      this.mProfileId = paramProfileId;
   }

   public void setAnyAccount(boolean paramAnyAccount) {
      this.mAnyAccount = paramAnyAccount;
   }

   public void setAnyBusiness(boolean paramAnyBusiness) {
      this.mAnyBusiness = paramAnyBusiness;
   }

   public void setAnyDataType(boolean paramAnyDataType) {
      this.mAnyDataType = paramAnyDataType;
   }

   public void setWeighting(int paramWeighting) {
      this.mWeighting = paramWeighting;
   }

   public Map getAccountElements() {
      return this.mAccountElments;
   }

   public void addAccountElement(Object element, Boolean selected) {
      this.mAccountElments.put(element, selected);
   }

   public Object removeAccountElement(Object element) {
      return this.mAccountElments.remove(element);
   }

   public Map getBusinessElements() {
      return this.mBusinessElements;
   }

   public Object removeBusinessElement(Object element) {
      return this.mBusinessElements.remove(element);
   }

   public void addBusinessElement(Object element, Boolean selected) {
      this.mBusinessElements.put(element, selected);
   }

   public void addDataType(Object dataType) {
      this.mDataTypes.add(dataType);
   }

   public boolean removeDataType(Object dataType) {
      return this.mDataTypes.remove(dataType);
   }

   public Set getDataTypes() {
      return this.mDataTypes;
   }

   public String getProfileDescription() {
      return this.mProfileDescription;
   }

   public void setProfileDescription(String profileDescription) {
      this.mProfileDescription = profileDescription;
   }

   public boolean hasAccountEntry() {
      if(this.mAccountElments.isEmpty() && !this.mAnyAccount) {
         return false;
      } else {
         Iterator i$ = this.mAccountElments.entrySet().iterator();

         Entry entry;
         do {
            if(!i$.hasNext()) {
               return this.mAnyAccount;
            }

            entry = (Entry)i$.next();
         } while(!((Boolean)entry.getValue()).booleanValue());

         return true;
      }
   }

   public boolean hasBusinessEntry() {
      if(this.mBusinessElements.isEmpty() && !this.mAnyBusiness) {
         return false;
      } else {
         Iterator i$ = this.mBusinessElements.entrySet().iterator();

         Entry entry;
         do {
            if(!i$.hasNext()) {
               return this.mAnyBusiness;
            }

            entry = (Entry)i$.next();
         } while(!((Boolean)entry.getValue()).booleanValue());

         return true;
      }
   }
}
