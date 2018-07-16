// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:58
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.SecurityAccessDef;
import com.cedar.cp.dto.model.SecurityAccessDefCK;
import com.cedar.cp.dto.model.SecurityAccessDefPK;
import java.io.Serializable;

public class SecurityAccessDefImpl implements SecurityAccessDef, Serializable, Cloneable {

   private Object mPrimaryKey;
   private String mVisId;
   private String mDescription;
   private int mAccessMode;
   private String mExpression;
   private int mVersionNum;
   private ModelRef mModelRef;


   public SecurityAccessDefImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mVisId = "";
      this.mDescription = "";
      this.mAccessMode = 0;
      this.mExpression = "";
      this.mAccessMode = 2;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (SecurityAccessDefPK)paramKey;
   }

   public void setPrimaryKey(SecurityAccessDefCK paramKey) {
      this.mPrimaryKey = paramKey;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getAccessMode() {
      return this.mAccessMode;
   }

   public String getExpression() {
      return this.mExpression;
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

   public void setAccessMode(int paramAccessMode) {
      this.mAccessMode = paramAccessMode;
   }

   public void setExpression(String paramExpression) {
      this.mExpression = paramExpression;
   }
}
