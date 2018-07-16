// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

import com.cedar.cp.utc.common.CPForm;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

public class DataEntryForm extends CPForm {

   private int mTopNodeId;
   private int mModelId;
   private int mBudgetCycleId;
   private String mSelectionString;
   private String mDataType;
   private int mLayoutId;
   private int mCurrencyId;
   private int mPreviousCurrencyId;
   private String mScaleFactor;
   private String mXRate;
   private int mOrganisationId;


   public void reset(ActionMapping mapping, HttpServletRequest request) {
      this.mTopNodeId = 0;
      this.mModelId = 0;
      this.mBudgetCycleId = 0;
      this.mSelectionString = "";
      this.mDataType = "";
      this.mLayoutId = 0;
      this.mCurrencyId = 0;
      this.mPreviousCurrencyId = 0;
      this.mScaleFactor = "1.0";
      this.mXRate = "1.0";
      this.mOrganisationId = 0;
   }

   public void setTopNodeId(int id) {
      this.mTopNodeId = id;
   }

   public int getTopNodeId() {
      return this.mTopNodeId;
   }

   public void setModelId(int id) {
      this.mModelId = id;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public void setBudgetCycleId(int id) {
      this.mBudgetCycleId = id;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public void setOrganisationId(int id) {
      this.mOrganisationId = id;
   }

   public int getOrganisationId() {
      return this.mOrganisationId;
   }

   public void setSelectionString(String selections) {
      this.mSelectionString = selections;
   }

   public String getSelectionString() {
      return this.mSelectionString;
   }

   public void setDataType(String dataType) {
      this.mDataType = dataType;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public void setCurrencyId(int id) {
      this.mCurrencyId = id;
   }

   public int getCurrencyId() {
      return this.mCurrencyId;
   }

   public void setPreviousCurrencyId(int id) {
      this.mPreviousCurrencyId = id;
   }

   public int getPreviousCurrencyId() {
      return this.mPreviousCurrencyId;
   }

   public void setExchangeRate(String xrate) {
      this.mXRate = xrate;
   }

   public String getExchangeRate() {
      return this.mXRate;
   }

   public void setLayoutId(int id) {
      this.mLayoutId = id;
   }

   public int getLayoutId() {
      return this.mLayoutId;
   }

   public void setScaleFactor(String factor) {
      this.mScaleFactor = factor;
   }

   public String getScaleFactor() {
      return this.mScaleFactor;
   }
}
