// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MassUpdateTask$MyCheckpoint extends AbstractTaskCheckpoint {

   private int mStepNumber;
   private int mNumDimensions;
   private String[] mDimNames;
   private String[] mDimDescrs;
   private long mCurrentValue;
   private long mChangePercent;
   private long mChangeBy;
   private long mChangeTo;
   private long mPosting;
   private List mSourceDataTypes = new ArrayList();


   public List toDisplay() {
      ArrayList l = new ArrayList();
      if(this.getStepNumber() == 0) {
         l.add("check worktables don\'t exist");
      } else if(this.getStepNumber() == 1) {
         l.add("create worktables");
      } else if(this.getStepNumber() == 2) {
         l.add("update");
      } else {
         l.add("drop worktables");
      }

      return l;
   }

   public int getStepNumber() {
      return this.mStepNumber;
   }

   public void setStepNumberUp() {
      ++this.mStepNumber;
   }

   public void setNumDimensions(int numDims) {
      this.mNumDimensions = numDims;
   }

   public int getNumDimensions() {
      return this.mNumDimensions;
   }

   public void setDimNames(String[] dimNames) {
      this.mDimNames = dimNames;
   }

   public String[] getDimNames() {
      return this.mDimNames;
   }

   public void setDimDescrs(String[] dimDescrs) {
      this.mDimDescrs = dimDescrs;
   }

   public String[] getDimDescrs() {
      return this.mDimDescrs;
   }

   public void setCurrentValue(long cv) {
      this.mCurrentValue = cv;
   }

   public long getCurrentValue() {
      return this.mCurrentValue;
   }

   public void setPosting(long post) {
      this.mPosting = post;
   }

   public long getPosting() {
      return this.mPosting;
   }

   public void addSourceDataType(String dataTypeDescr) {
      String dataTypeVisId = dataTypeDescr.substring(0, 2);
      if(!this.mSourceDataTypes.contains(dataTypeVisId)) {
         this.mSourceDataTypes.add(dataTypeVisId);
      }

   }

   public List getSourceDataTypes() {
      return this.mSourceDataTypes;
   }

   public boolean isDataTypeInSource(String dataType) {
      Iterator i = this.getSourceDataTypes().iterator();

      String src;
      do {
         if(!i.hasNext()) {
            return false;
         }

         src = (String)i.next();
      } while(!src.equals(dataType));

      return true;
   }
}
