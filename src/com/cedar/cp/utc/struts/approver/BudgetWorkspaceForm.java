// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.struts.approver.BudgetCycleStatusForm;
import com.cedar.cp.utc.struts.approver.BudgetWorkspaceForm$SelectionOption;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

public class BudgetWorkspaceForm extends BudgetCycleStatusForm {

   private int mTopNodeId;
   private int mModelId;
   private int mBudgetCycleId;
   private int mSelectedStructureElementId;
   private int mLayoutId;
   private int mCurrencyId;
   private int mPreviousCurrencyId;
   private String mScaleFactor;
   private String mXRate;
   private String mDataType;
   private String mSelectedDataType;
   private Map mSelectOptions;
   private String mSubmitModelName;
   private String mSubmitCycleName;
   private EntityList mDataEntryProfiles;
   private EntityList mXmlReports;
   private String mRaName;
   private int mState;


   public void reset(ActionMapping mapping, HttpServletRequest request) {
      this.mTopNodeId = 0;
      this.mModelId = 0;
      this.mBudgetCycleId = 0;
      this.mSelectedStructureElementId = 0;
      this.mLayoutId = 0;
      this.mCurrencyId = 0;
      this.mPreviousCurrencyId = 0;
      this.mScaleFactor = "1.0";
      this.mXRate = "1.0";
      this.mDataType = "";
      this.mSelectedDataType = "";
      this.mSelectOptions = new HashMap();
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

   public void setSelectedStructureElementId(int id) {
      this.mSelectedStructureElementId = id;
      BudgetWorkspaceForm$SelectionOption opt = (BudgetWorkspaceForm$SelectionOption)this.getSelection(0);
      opt.setCurrentOption(this.mSelectedStructureElementId);
   }

   public int getSelectedStructureElementId() {
      return this.mSelectedStructureElementId;
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

   public void setSelectedDataType(String dataType) {
      this.mSelectedDataType = dataType;
      this.mDataType = this.extractPrefix(dataType);
   }

   private String extractPrefix(Object obj) {
      String prefix = obj.toString();
      if(prefix != null && prefix.trim().length() != 0) {
         StringTokenizer st = new StringTokenizer(prefix, " ");
         return st.nextToken();
      } else {
         return prefix;
      }
   }

   public String getSelectedDataType() {
      return this.mSelectedDataType;
   }

   public void setDataType(String dataType) {
      this.mDataType = dataType;
   }

   public String getDataType() {
      return this.mDataType;
   }

   public Object getSelection(int i) {
      Integer key = new Integer(i);
      BudgetWorkspaceForm$SelectionOption opt = (BudgetWorkspaceForm$SelectionOption)this.mSelectOptions.get(key);
      if(opt == null) {
         opt = new BudgetWorkspaceForm$SelectionOption();
         this.mSelectOptions.put(key, opt);
      }

      return opt;
   }

   public Map getCurrentSelections() {
      return this.mSelectOptions;
   }

   public String getSubmitModelName() {
      return this.mSubmitModelName;
   }

   public void setSubmitModelName(String submitModelName) {
      this.mSubmitModelName = submitModelName;
   }

   public String getSubmitCycleName() {
      return this.mSubmitCycleName;
   }

   public void setSubmitCycleName(String submitCycleName) {
      this.mSubmitCycleName = submitCycleName;
   }

   public EntityList getDataEntryProfiles() {
      return this.mDataEntryProfiles;
   }

   public void setDataEntryProfiles(EntityList dataEntryProfiles) {
      this.mDataEntryProfiles = dataEntryProfiles;
   }

   public EntityList getXmlReports() {
      return this.mXmlReports;
   }

   public void setXmlReports(EntityList xmlReports) {
      this.mXmlReports = xmlReports;
   }

   public String getRaName() {
      return this.mRaName;
   }

   public void setRaName(String raName) {
      this.mRaName = raName;
   }

   public int getState() {
      return this.mState;
   }

   public void setState(int state) {
      this.mState = state;
   }
}
