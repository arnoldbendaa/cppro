// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.cyclehistory;

import java.util.Date;

public class DetailedBudgetStateLineDTO implements Comparable {

   private int mStructureId;
   private int mStructureElementId;
   private String mVisId;
   private String mDescription;
   private int mDepth;
   private boolean mLeaf;
   private int mNewState;
   private Date mDateChanged;
   private boolean mContact;


   public int getStructureId() {
      return this.mStructureId;
   }

   public void setStructureId(int structureId) {
      this.mStructureId = structureId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public void setStructureElementId(int structureElementId) {
      this.mStructureElementId = structureElementId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public void setVisId(String visId) {
      this.mVisId = visId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public void setDescription(String description) {
      this.mDescription = description;
   }

   public int getDepth() {
      return this.mDepth;
   }

   public void setDepth(int depth) {
      this.mDepth = depth;
   }

   public boolean isLeaf() {
      return this.mLeaf;
   }

   public void setLeaf(boolean leaf) {
      this.mLeaf = leaf;
   }

   public int getNewState() {
      return this.mNewState == 6?5:this.mNewState;
   }

   public void setNewState(int newState) {
      this.mNewState = newState;
   }

   public Date getDateChanged() {
      return this.mDateChanged;
   }

   public void setDateChanged(Date dateChanged) {
      this.mDateChanged = dateChanged;
   }

   public boolean isContact() {
      return this.mContact;
   }

   public void setContact(boolean contact) {
      this.mContact = contact;
   }

   public int compareTo(Object o) {
      if(o instanceof DetailedBudgetStateLineDTO) {
         DetailedBudgetStateLineDTO check = (DetailedBudgetStateLineDTO)o;
         return this.getVisId().compareTo(check.getVisId());
      } else {
         return 0;
      }
   }
}
