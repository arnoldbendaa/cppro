// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.common.CPForm;
import com.cedar.cp.utc.struts.virement.VirementDataEntryDTO;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts.action.ActionMapping;

public class VirementDataEntryForm extends CPForm {

   private VirementDataEntryDTO mData = new VirementDataEntryDTO();
   private int mCurrentTab;
   private int mCurrentGroup;
   private int mCurrentLine;
   private String mCurrentField;
   private String mUserAction;
   private boolean mHasData;


   public VirementDataEntryDTO getData() {
      return this.mData;
   }

   public void setData(VirementDataEntryDTO dto) {
      this.mData = dto;
   }

   public void setdTO(VirementDataEntryDTO dTO) {
      this.mData = dTO;
   }

   public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
      super.reset(actionMapping, httpServletRequest);
      this.setUserAction("");
      this.setCurrentGroup(0);
      this.setCurrentTab(0);
      this.setCurrentLine(0);
      if(this.mData == null) {
         this.mData = new VirementDataEntryDTO();
      }

      this.setHasData(false);
   }

   public int getCurrentTab() {
      return this.mCurrentTab;
   }

   public void setCurrentTab(int currentTab) {
      this.mCurrentTab = currentTab;
   }

   public Collection getGroups() {
      return this.mData.getGroups();
   }

   public void setGroups(Collection groups) {
      this.mData.setGroups(groups);
   }

   public int getCurrentGroup() {
      return this.mCurrentGroup;
   }

   public void setCurrentGroup(int currentGroup) {
      this.mCurrentGroup = currentGroup;
   }

   public int getCurrentLine() {
      return this.mCurrentLine;
   }

   public void setCurrentLine(int currentLine) {
      this.mCurrentLine = currentLine;
   }

   public String getUserAction() {
      return this.mUserAction;
   }

   public void setUserAction(String userAction) {
      this.mUserAction = userAction;
   }

   public String getCurrentField() {
      return this.mCurrentField;
   }

   public void setCurrentField(String currentField) {
      this.mCurrentField = currentField;
   }

   public boolean isHasData() {
      return this.mHasData;
   }

   public void setHasData(boolean hasData) {
      this.mHasData = hasData;
   }
}
