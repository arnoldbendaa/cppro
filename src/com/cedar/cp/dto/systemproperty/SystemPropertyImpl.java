// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.systemproperty;

import com.cedar.cp.api.systemproperty.SystemProperty;
import com.cedar.cp.dto.systemproperty.SystemPropertyPK;
import java.io.Serializable;

public class SystemPropertyImpl implements SystemProperty, Serializable, Cloneable {

   private Object mPrimaryKey;
   private String mProperty;
   private String mValue;
   private String mDescription;
   private String mValidateExp;
   private String mValidateTxt;
   private int mVersionNum;


   public SystemPropertyImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mProperty = "";
      this.mValue = "";
      this.mDescription = "";
      this.mValidateExp = "";
      this.mValidateTxt = "";
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (SystemPropertyPK)paramKey;
   }

   public String getProperty() {
      return this.mProperty;
   }

   public String getValue() {
      return this.mValue;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getValidateExp() {
      return this.mValidateExp;
   }

   public String getValidateTxt() {
      return this.mValidateTxt;
   }

   public void setVersionNum(int p) {
      this.mVersionNum = p;
   }

   public int getVersionNum() {
      return this.mVersionNum;
   }

   public void setProperty(String paramProperty) {
      this.mProperty = paramProperty;
   }

   public void setValue(String paramValue) {
      this.mValue = paramValue;
   }

   public void setDescription(String paramDescription) {
      this.mDescription = paramDescription;
   }

   public void setValidateExp(String paramValidateExp) {
      this.mValidateExp = paramValidateExp;
   }

   public void setValidateTxt(String paramValidateTxt) {
      this.mValidateTxt = paramValidateTxt;
   }
}
