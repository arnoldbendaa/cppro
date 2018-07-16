// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report;

import com.cedar.cp.api.report.ReportRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AllReportsForAdminELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Report", "CubePendingTran"};
   private transient ReportRef mReportEntityRef;
   private transient int mReportId;
   private transient int mReportType;
   private transient int mTaskId;
   private transient Timestamp mCreatedTime;
   private transient int mUserId;
   private transient boolean mHasUpdates;
   private transient boolean mUpdatesApplied;


   public AllReportsForAdminELO() {
      super(new String[]{"Report", "ReportId", "ReportType", "TaskId", "CreatedTime", "UserId", "HasUpdates", "UpdatesApplied"});
   }

   public void add(ReportRef eRefReport, int col1, int col2, int col3, Timestamp col4, int col5, boolean col6, boolean col7) {
      ArrayList l = new ArrayList();
      l.add(eRefReport);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(new Integer(col3));
      l.add(col4);
      l.add(new Integer(col5));
      l.add(new Boolean(col6));
      l.add(new Boolean(col7));
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
      this.mReportEntityRef = (ReportRef)l.get(index);
      this.mReportId = ((Integer)l.get(var4++)).intValue();
      this.mReportType = ((Integer)l.get(var4++)).intValue();
      this.mTaskId = ((Integer)l.get(var4++)).intValue();
      this.mCreatedTime = (Timestamp)l.get(var4++);
      this.mUserId = ((Integer)l.get(var4++)).intValue();
      this.mHasUpdates = ((Boolean)l.get(var4++)).booleanValue();
      this.mUpdatesApplied = ((Boolean)l.get(var4++)).booleanValue();
   }

   public ReportRef getReportEntityRef() {
      return this.mReportEntityRef;
   }

   public int getReportId() {
      return this.mReportId;
   }

   public int getReportType() {
      return this.mReportType;
   }

   public int getTaskId() {
      return this.mTaskId;
   }

   public Timestamp getCreatedTime() {
      return this.mCreatedTime;
   }

   public int getUserId() {
      return this.mUserId;
   }

   public boolean getHasUpdates() {
      return this.mHasUpdates;
   }

   public boolean getUpdatesApplied() {
      return this.mUpdatesApplied;
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
