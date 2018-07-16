// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.homepage;

import com.cedar.cp.utc.struts.homepage.BudgetLocationDTO;
import com.cedar.cp.utc.struts.message.MessageDTO;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class BLChildDTO implements Serializable {

   private int mState;
   private int mStructureElementId;
   private String mIdentifier;
   private String mDescription;
   private int mUserCount;
   private int mOtherUserCount;
   private BudgetLocationDTO mParent;
   private boolean mSubmitable;
   private boolean mRejectable;
   private int mLastUpdateById;
   private Date mEndDate;
   private Calendar mLateDate;
   private boolean mFullRights;

   public int getState() {
      return this.mState;
   }

   public void setState(int state) {
      this.mState = state;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public void setStructureElementId(int structureElementId) {
      this.mStructureElementId = structureElementId;
   }

   public String getIdentifier() {
      return this.mIdentifier;
   }

   public void setIdentifier(String identifier) {
      this.mIdentifier = identifier;
   }

   public int getUserCount() {
      return this.mUserCount;
   }

   public void setUserCount(int userCount) {
      this.mUserCount = userCount;
   }

   public boolean isBudgetUser() {
      return this.getUserCount() > 0;
   }

   public BudgetLocationDTO getParent() {
      return this.mParent;
   }

   public void setParent(BudgetLocationDTO parent) {
      this.mParent = parent;
   }

   public boolean isShowStart() {
      return (this.isBudgetUser() || this.mParent.isBudgetUser()) && this.getState() == 0;
   }

   public boolean isShowSubmit() {
      return (this.isBudgetUser() || this.mParent.isBudgetUser()) && this.getState() == 2 && this.isSubmitable();
   }

   public boolean isShowAgree() {
      return (this.isBudgetUser() || this.mParent.isBudgetUser()) && this.getState() == 3;
   }

   public boolean isShowReject() {
      return this.mParent.getState() > 2?false:(this.isBudgetUser() || this.mParent.isBudgetUser()) && this.getState() == 4 && this.isRejectable();
   }

   public boolean isSubmitable() {
      return this.mSubmitable;
   }

   public void setSubmitable(boolean submitable) {
      this.mSubmitable = submitable;
   }

   public boolean isRejectable() {
      return this.mRejectable;
   }

   public void setRejectable(boolean rejectable) {
      this.mRejectable = rejectable;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public int getOtherUserCount() {
      return this.mOtherUserCount;
   }

   public void setOtherUserCount(int otherUserCount) {
      this.mOtherUserCount = otherUserCount;
   }

   public Date getEndDate() {
      return this.mEndDate;
   }

   public void setEndDate(Date endDate) {
      this.mEndDate = endDate;
   }

   public boolean isLate() {
      return this.mEndDate == null?false:this.mEndDate.before(this.getLateDate().getTime()) && this.getState() < 3;
   }

   public Calendar getLateDate() {
      return this.mLateDate;
   }

   public void setLateDate(Calendar lateDate) {
      this.mLateDate = lateDate;
   }

   private boolean isOverDue() {
      return this.mEndDate == null?false:this.mEndDate.before(Calendar.getInstance().getTime());
   }

   public String getLateMessage() {
      StringBuffer text = new StringBuffer(40);
      if(this.isOverDue()) {
         text.append("Overdue - ");
      } else {
         text.append("Due - ");
      }

      text.append(MessageDTO.sFormat.format(this.mEndDate));
      return text.toString();
   }

   public String getWarningImage() {
      String gif = "warnings";
      if(this.isOverDue()) {
         gif = "warning_red";
      }

      return gif;
   }
   
   public int getLastUpdateById() {
     return mLastUpdateById;
   }

   public void setLastUpdateById(int lastUpdateById) {
     mLastUpdateById = lastUpdateById;
   }
   
   public boolean isFullRights() {
       return mFullRights;
   }

   public void setFullRights(boolean mFullRights) {
       this.mFullRights = mFullRights;
   }
}
