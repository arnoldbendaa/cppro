// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:29:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.workbench;

import com.cedar.cp.api.model.ChangeBudgetStateResult;
import com.cedar.cp.utc.struts.workbench.ModelWorkBenchForm;
import java.util.List;

public class WorkBenchForm extends ModelWorkBenchForm {

   private int mStateFilter;
   private int mOldUserCount;
   private int mOldDepth;
   protected ChangeBudgetStateResult mChangeStateResult;
   protected String mAddId;
   protected String mOldId;
   protected String mStructureElementList = "";
   protected String mOldUserCountList = "";
   protected String mOldDepthList = "";
   protected String mVisIdList = "";
   protected String mDescriptionList = "";
   protected List mCrumbs;
   protected int mCrumbSize;


   public int getStateFilter() {
      return this.mStateFilter;
   }

   public void setStateFilter(int stateFilter) {
      this.mStateFilter = stateFilter;
   }

   public int getOldUserCount() {
      return this.mOldUserCount;
   }

   public void setOldUserCount(int oldUserCount) {
      this.mOldUserCount = oldUserCount;
   }

   public int getOldDepth() {
      return this.mOldDepth;
   }

   public void setOldDepth(int oldDepth) {
      this.mOldDepth = oldDepth;
   }

   public ChangeBudgetStateResult getChangeStateResult() {
      return this.mChangeStateResult;
   }

   public void setChangeStateResult(ChangeBudgetStateResult changeStateResult) {
      this.mChangeStateResult = changeStateResult;
   }

   public String getAddId() {
      return this.mAddId;
   }

   public void setAddId(String addId) {
      this.mAddId = addId;
   }

   public String getOldId() {
      return this.mOldId;
   }

   public void setOldId(String oldId) {
      this.mOldId = oldId;
   }

   public String getStructureElementList() {
      return this.mStructureElementList;
   }

   public void setStructureElementList(String structureElementList) {
      this.mStructureElementList = structureElementList;
   }

   public String getOldUserCountList() {
      return this.mOldUserCountList;
   }

   public void setOldUserCountList(String oldUserCountList) {
      this.mOldUserCountList = oldUserCountList;
   }

   public String getOldDepthList() {
      return this.mOldDepthList;
   }

   public void setOldDepthList(String oldDepthList) {
      this.mOldDepthList = oldDepthList;
   }

   public String getVisIdList() {
      return this.mVisIdList;
   }

   public void setVisIdList(String visIdList) {
      this.mVisIdList = visIdList;
   }

   public String getDescriptionList() {
      return this.mDescriptionList;
   }

   public void setDescriptionList(String descriptionList) {
      this.mDescriptionList = descriptionList;
   }

   public List getCrumbs() {
      return this.mCrumbs;
   }

   public void setCrumbs(List crumbs) {
      if(crumbs != null) {
         this.setCrumbSize(crumbs.size() - 1);
      }

      this.mCrumbs = crumbs;
   }

   public int getCrumbSize() {
      return this.mCrumbSize;
   }

   public void setCrumbSize(int crumbSize) {
      this.mCrumbSize = crumbSize;
   }
}
