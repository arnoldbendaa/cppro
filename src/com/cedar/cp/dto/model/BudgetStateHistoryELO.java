// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BudgetStateHistoryELO extends AbstractELO implements Serializable {

   private transient Integer mStructureId;
   private transient Integer mStructureElementId;
   private transient String mVisId;
   private transient String mDescription;
   private transient Integer mDepth;
   private transient Boolean mLeaf;
   private transient Integer mState;
   private transient Timestamp mLastChanged;
   private transient Boolean mContact;
   private transient Timestamp mPlannedEndDate;


   public BudgetStateHistoryELO() {
      super(new String[]{"StructureId", "SructureElementId", "Vis_Id", "Description", "Depth", "Leaf", "State", "LastChanged", "Contact", "PlannedEndDate"});
   }

   public void add(int structurId, int structureElementId, String visId, String description, int depth, boolean leaf, int state, Timestamp lastChanged, boolean contact, Timestamp plannedEndDate) {
      ArrayList l = new ArrayList();
      l.add(new Integer(structurId));
      l.add(new Integer(structureElementId));
      l.add(visId);
      l.add(description);
      l.add(new Integer(depth));
      l.add(new Boolean(leaf));
      l.add(new Integer(state));
      l.add(lastChanged);
      l.add(new Boolean(contact));
      l.add(plannedEndDate);
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.mCurrRowIndex = -1;
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mStructureId = (Integer)l.get(index);
      this.mStructureElementId = (Integer)l.get(var4++);
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
      this.mDepth = (Integer)l.get(var4++);
      this.mLeaf = (Boolean)l.get(var4++);
      this.mState = (Integer)l.get(var4++);
      this.mLastChanged = (Timestamp)l.get(var4++);
      this.mContact = (Boolean)l.get(var4++);
      this.mPlannedEndDate = (Timestamp)l.get(var4);
   }

   public Integer getStructureId() {
      return this.mStructureId;
   }

   public Integer getStructureElementId() {
      return this.mStructureElementId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public Integer getDepth() {
      return this.mDepth;
   }

   public Boolean getLeaf() {
      return this.mLeaf;
   }

   public Integer getState() {
      return this.mState;
   }

   public Timestamp getLastChanged() {
      return this.mLastChanged;
   }

   public Boolean getContact() {
      return this.mContact;
   }

   public void setContact(Boolean contact) {
      this.mContact = contact;
   }

   public Timestamp getPlannedEndDate() {
      return this.mPlannedEndDate;
   }

   public void setPlannedEndDate(Timestamp plannedEndDate) {
      this.mPlannedEndDate = plannedEndDate;
   }
}
