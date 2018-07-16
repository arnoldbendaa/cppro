// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.base.AbstractELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForLocationELO;
import com.cedar.cp.dto.model.BudgetDetailsForUserLevel4ELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BudgetDetailsForUserLevel3ELO extends AbstractELO implements Serializable {

   private transient Integer mStructureElementId;
   private transient String mVisId;
   private transient String mDescription;
   private transient Integer mState;
   private transient int mDepth;
   private transient BudgetDetailsForUserLevel4ELO mChildLocations;
   private transient AllBudgetInstructionsForLocationELO mBudgetInstructions;
   private transient Integer mUserCount;
   private transient Boolean mSubmitable;
   private transient Boolean mRejectable;
   private transient Integer mLastUpdatedById;
   private transient Timestamp mEndDate;
   private transient Boolean mFullRights;


   public BudgetDetailsForUserLevel3ELO() {
      super(new String[]{"State", "StructureElementId", "VisId", "Description", "Depth", "ChildLocations", "BudgetInstructions", "UserCount", "Submitable", "Rejectable", "LastUpdatedById", "EndDate", "FullRights"});
   }

   public void add(Integer budgetState, int structureElementId, String visId, String description, int elemDepth, BudgetDetailsForUserLevel4ELO childLocations, AllBudgetInstructionsForLocationELO budgetInstructions, int locationUserCount, boolean submitable, boolean rejectable,int lastUpdatedById, Timestamp endDate) {
      ArrayList l = new ArrayList();
      l.add(budgetState);
      l.add(new Integer(structureElementId));
      l.add(visId);
      l.add(description);
      l.add(new Integer(elemDepth));
      l.add(childLocations);
      l.add(budgetInstructions);
      l.add(new Integer(locationUserCount));
      l.add(new Boolean(submitable));
      l.add(new Boolean(rejectable));
      l.add(new Integer(lastUpdatedById));
      l.add(endDate);
      l.add(true);
      this.mCollection.add(l);
   }
   
   public void add(Integer budgetState, int structureElementId, String visId, String description, int elemDepth, BudgetDetailsForUserLevel4ELO childLocations, AllBudgetInstructionsForLocationELO budgetInstructions, int locationUserCount, boolean submitable, boolean rejectable,int lastUpdatedById, Timestamp endDate, Boolean fullRights) {
       ArrayList l = new ArrayList();
       l.add(budgetState);
       l.add(new Integer(structureElementId));
       l.add(visId);
       l.add(description);
       l.add(new Integer(elemDepth));
       l.add(childLocations);
       l.add(budgetInstructions);
       l.add(new Integer(locationUserCount));
       l.add(new Boolean(submitable));
       l.add(new Boolean(rejectable));
       l.add(new Integer(lastUpdatedById));
       l.add(endDate);
       l.add(fullRights);
       this.mCollection.add(l);
    }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mState = (Integer)l.get(index);
      this.mStructureElementId = (Integer)l.get(var4++);
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mDepth = ((Integer)l.get(var4++)).intValue();
      this.mChildLocations = (BudgetDetailsForUserLevel4ELO)l.get(var4++);
      this.mBudgetInstructions = (AllBudgetInstructionsForLocationELO)l.get(var4++);
      this.mUserCount = (Integer)l.get(var4++);
      this.mSubmitable = (Boolean)l.get(var4++);
      this.mRejectable = (Boolean)l.get(var4++);
      this.mLastUpdatedById = ((Integer)l.get(index++));
      this.mEndDate = (Timestamp)l.get(var4++);
      this.mFullRights = (Boolean)l.get(var4++);
   }

   public int getStructureElementId() {
      return this.mStructureElementId.intValue();
   }

   public String getVisId() {
      return this.mVisId;
   }

   public Integer getState() {
      return this.mState;
   }

   public int getDepth() {
      return this.mDepth;
   }

   public BudgetDetailsForUserLevel4ELO getChildLocations() {
      return this.mChildLocations;
   }

   public AllBudgetInstructionsForLocationELO getBudgetInstructions() {
      return this.mBudgetInstructions;
   }

   public Integer getUserCount() {
      return this.mUserCount;
   }

   public Boolean isSubmitable() {
      return this.mSubmitable;
   }

   public Boolean isRejectable() {
      return this.mRejectable;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Timestamp getEndDate() {
      return this.mEndDate;
   }
   
   public Boolean getSubmitable() {
     return mSubmitable;
   }

   public Boolean getRejectable() {
     return mRejectable;
   }

   public Integer getLastUpdatedById() {
     return mLastUpdatedById;
   }

    public Boolean getFullRights() {
        return mFullRights;
    }
}
