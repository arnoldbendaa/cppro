// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.perftest;

import com.cedar.cp.api.perftest.PerfTestRef;
import com.cedar.cp.dto.perftest.PerfTestPK;
import com.cedar.cp.dto.perftest.PerfTestRefImpl;
import java.io.Serializable;

public class PerfTestEVO implements Serializable {

   private transient PerfTestPK mPK;
   private int mPerfTestId;
   private String mVisId;
   private String mDescription;
   private String mClassName;
   private boolean mModified;


   public PerfTestEVO() {}

   public PerfTestEVO(int newPerfTestId, String newVisId, String newDescription, String newClassName) {
      this.mPerfTestId = newPerfTestId;
      this.mVisId = newVisId;
      this.mDescription = newDescription;
      this.mClassName = newClassName;
   }

   public PerfTestPK getPK() {
      if(this.mPK == null) {
         this.mPK = new PerfTestPK(this.mPerfTestId);
      }

      return this.mPK;
   }

   public boolean isModified() {
      return this.mModified;
   }

   public int getPerfTestId() {
      return this.mPerfTestId;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getClassName() {
      return this.mClassName;
   }

   public void setPerfTestId(int newPerfTestId) {
      if(this.mPerfTestId != newPerfTestId) {
         this.mModified = true;
         this.mPerfTestId = newPerfTestId;
         this.mPK = null;
      }
   }

   public void setVisId(String newVisId) {
      if(this.mVisId != null && newVisId == null || this.mVisId == null && newVisId != null || this.mVisId != null && newVisId != null && !this.mVisId.equals(newVisId)) {
         this.mVisId = newVisId;
         this.mModified = true;
      }

   }

   public void setDescription(String newDescription) {
      if(this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
         this.mDescription = newDescription;
         this.mModified = true;
      }

   }

   public void setClassName(String newClassName) {
      if(this.mClassName != null && newClassName == null || this.mClassName == null && newClassName != null || this.mClassName != null && newClassName != null && !this.mClassName.equals(newClassName)) {
         this.mClassName = newClassName;
         this.mModified = true;
      }

   }

   public void setDetails(PerfTestEVO newDetails) {
      this.setPerfTestId(newDetails.getPerfTestId());
      this.setVisId(newDetails.getVisId());
      this.setDescription(newDetails.getDescription());
      this.setClassName(newDetails.getClassName());
   }

   public PerfTestEVO deepClone() {
      PerfTestEVO cloned = new PerfTestEVO();
      cloned.mModified = this.mModified;
      cloned.mPerfTestId = this.mPerfTestId;
      if(this.mVisId != null) {
         cloned.mVisId = this.mVisId;
      }

      if(this.mDescription != null) {
         cloned.mDescription = this.mDescription;
      }

      if(this.mClassName != null) {
         cloned.mClassName = this.mClassName;
      }

      return cloned;
   }

   public void prepareForInsert() {
      boolean newKey = false;
      if(this.mPerfTestId > 0) {
         newKey = true;
         this.mPerfTestId = 0;
      } else if(this.mPerfTestId < 1) {
         newKey = true;
      }

   }

   public int getInsertCount(int startCount) {
      int returnCount = startCount;
      if(this.mPerfTestId < 1) {
         returnCount = startCount + 1;
      }

      return returnCount;
   }

   public int assignNextKey(int startKey) {
      int nextKey = startKey;
      if(this.mPerfTestId < 1) {
         this.mPerfTestId = startKey;
         nextKey = startKey + 1;
      }

      return nextKey;
   }

   protected void reset() {
      this.mModified = false;
   }

   public PerfTestRef getEntityRef(String entityText) {
      return new PerfTestRefImpl(this.getPK(), entityText);
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("PerfTestId=");
      sb.append(String.valueOf(this.mPerfTestId));
      sb.append(' ');
      sb.append("VisId=");
      sb.append(String.valueOf(this.mVisId));
      sb.append(' ');
      sb.append("Description=");
      sb.append(String.valueOf(this.mDescription));
      sb.append(' ');
      sb.append("ClassName=");
      sb.append(String.valueOf(this.mClassName));
      sb.append(' ');
      if(this.mModified) {
         sb.append("modified ");
      }

      return sb.toString();
   }

   public String print() {
      return this.print(0);
   }

   public String print(int indent) {
      StringBuffer sb = new StringBuffer();

      for(int i = 0; i < indent; ++i) {
         sb.append(' ');
      }

      sb.append("PerfTest: ");
      sb.append(this.toString());
      return sb.toString();
   }
}
