// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BudgetCycleDetailedForIdELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"BudgetCycle", "Model", "BudgetState", "BudgetStateHistory", "LevelDate", "BudgetInstructionAssignment"};
   private transient BudgetCycleRef mBudgetCycleEntityRef;
   private transient ModelRef mModelEntityRef;
   private transient int mModelId;
   private transient int mBudgetCycleId;
   private transient String mDescription;
   private transient int mStatus;
   private transient int mType;
   private transient Timestamp mPlannedEndDate;
   private transient Timestamp mStartDate;
   private transient Timestamp mEndDate;
   private transient XmlFormRef mXmlFormEntityRef;
   private transient String mXmlFormDescription;
   private transient String mDataType;
   
   public BudgetCycleDetailedForIdELO() {
      super(new String[]{"BudgetCycle", "Model", "ModelId", "BudgetCycleId", "Description", "Status", "Type", "PlannedEndDate", "StartDate", "EndDate", "XmlForm", "XmlFormDescription", "DataType"});
   }

   public void add(BudgetCycleRef eRefBudgetCycle, ModelRef eRefModel, int col1, int col2, String col3, int col4, int col5, Timestamp col6, Timestamp col7, Timestamp col8, XmlFormRef eRefXmlForm, String xmlFormDescription, String dataType) {
      ArrayList l = new ArrayList();
      l.add(eRefBudgetCycle);
      l.add(eRefModel);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(col3);
      l.add(new Integer(col4));
      l.add(new Integer(col5));
      l.add(col6);
      l.add(col7);
      l.add(col8);
      l.add(eRefXmlForm);
      l.add(xmlFormDescription);
      l.add(dataType);
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
      this.mBudgetCycleEntityRef = (BudgetCycleRef)l.get(index);
      this.mModelEntityRef = (ModelRef)l.get(var4++);
      this.mModelId = ((Integer)l.get(var4++)).intValue();
      this.mBudgetCycleId = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
      this.mStatus = ((Integer)l.get(var4++)).intValue();
      this.mType = ((Integer)l.get(var4++)).intValue();
      this.mPlannedEndDate = (Timestamp)l.get(var4++);
      this.mStartDate = (Timestamp)l.get(var4++);
      this.mEndDate = (Timestamp)l.get(var4++);
      this.mXmlFormEntityRef = (XmlFormRef)l.get((var4++));
      this.mXmlFormDescription = (String)l.get((var4++));
      this.mDataType = (String)l.get((var4++));
   }

   public BudgetCycleRef getBudgetCycleEntityRef() {
      return this.mBudgetCycleEntityRef;
   }

   public ModelRef getModelEntityRef() {
      return this.mModelEntityRef;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getStatus() {
      return this.mStatus;
   }

   public int getType() {
      return this.mType;
   }

   public Timestamp getPlannedEndDate() {
      return this.mPlannedEndDate;
   }

   public Timestamp getStartDate() {
      return this.mStartDate;
   }

   public Timestamp getEndDate() {
      return this.mEndDate;
   }
   
   public XmlFormRef getXmlFormEntityRef() {
	   return this.mXmlFormEntityRef;
   }
   
   public String getXmlFormDescription() {
	   return this.mXmlFormDescription;
   }
   
   public String getDataType() {
	   return this.mDataType;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
