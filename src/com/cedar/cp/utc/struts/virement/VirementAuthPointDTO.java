// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:35:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.virement;

import com.cedar.cp.utc.picker.ElementDTO;
import com.cedar.cp.utc.struts.virement.LazyList;
import com.cedar.cp.utc.struts.virement.UserDTO;
import com.cedar.cp.utc.struts.virement.VirementAuthPointDTO$1;
import com.cedar.cp.utc.struts.virement.VirementAuthPointDTO$2;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VirementAuthPointDTO implements Serializable {

   private static String[] STATUS_NAMES = new String[]{"Awaiting Authorisation", "Authorised", "Rejected"};
   private Object mKey;
   private String mKeyText;
   private List mLines;
   private List mAvailableAuthorisers;
   private UserDTO mAuthUser;
   private String mNotes;
   private boolean mUserCanAuth;
   private int mStatus;
   private ElementDTO mBudgetLocation;


   public VirementAuthPointDTO() {
      this.mLines = new LazyList(new VirementAuthPointDTO$1(this));
      this.mAvailableAuthorisers = new LazyList(new VirementAuthPointDTO$2(this));
      this.mBudgetLocation = new ElementDTO();
      this.mAuthUser = new UserDTO();
   }

   public VirementAuthPointDTO(UserDTO authUser, List availableAuthorisers, ElementDTO budgetLocation, Object key, String keyText, List lines, String notes, int status) {
      this.mAuthUser = authUser;
      this.mAvailableAuthorisers = availableAuthorisers;
      this.mBudgetLocation = budgetLocation;
      this.mKey = key;
      this.mKeyText = keyText;
      this.mLines = lines;
      this.mNotes = notes;
      this.mStatus = status;
   }

   public UserDTO getAuthUser() {
      return this.mAuthUser;
   }

   public void setAuthUser(UserDTO authUser) {
      this.mAuthUser = authUser;
   }

   public List getAvailableAuthorisers() {
      return this.mAvailableAuthorisers;
   }

   public void setAvailableAuthorisers(List availableAuthorisers) {
      this.mAvailableAuthorisers = availableAuthorisers;
   }

   public ElementDTO getBudgetLocation() {
      return this.mBudgetLocation;
   }

   public void setBudgetLocation(ElementDTO budgetLocation) {
      this.mBudgetLocation = budgetLocation;
   }

   public Object getKey() {
      return this.mKey;
   }

   public void setKey(Object key) {
      this.mKey = key;
   }

   public List getLines() {
      return this.mLines;
   }

   public void setLines(List lines) {
      this.mLines = lines;
   }

   public String getNotes() {
      return this.mNotes;
   }

   public void setNotes(String notes) {
      this.mNotes = notes;
   }

   public String getStatusText() {
      return STATUS_NAMES[this.getStatus()];
   }

   public int getStatus() {
      return this.mStatus;
   }

   public void setStatus(int status) {
      this.mStatus = status;
   }

   public String getKeyText() {
      return this.mKeyText;
   }

   public void setKeyText(String keyText) {
      this.mKeyText = keyText;
   }

   public int getMaxRows() {
      return this.mLines.size() > this.mAvailableAuthorisers.size()?this.mLines.size():this.mAvailableAuthorisers.size();
   }

   public boolean isUserCanAuth() {
      return this.mUserCanAuth;
   }

   public void setUserCanAuth(boolean userCanAuth) {
      this.mUserCanAuth = userCanAuth;
   }

   public void update(VirementAuthPointDTO src) {
      this.mKey = src.mKey;
      this.mKeyText = src.mKeyText;
      this.mLines = new ArrayList(src.mLines);
      this.mAvailableAuthorisers = new ArrayList(src.mAvailableAuthorisers);
      this.mAuthUser = src.mAuthUser;
      this.mNotes = src.mNotes;
      this.mUserCanAuth = src.mUserCanAuth;
      this.mStatus = src.mStatus;
      this.mBudgetLocation = src.mBudgetLocation;
   }

}
