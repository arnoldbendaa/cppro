// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:05
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report;

import com.cedar.cp.api.report.ReportRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllReportsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Report", "CubePendingTran"};
   private transient ReportRef mReportEntityRef;
   private transient int mReportType;
   private transient int mTaskId;


   public AllReportsELO() {
      super(new String[]{"Report", "ReportType", "TaskId"});
   }

   public void add(ReportRef eRefReport, int col1, int col2) {
      ArrayList l = new ArrayList();
      l.add(eRefReport);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
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
      this.mReportType = ((Integer)l.get(var4++)).intValue();
      this.mTaskId = ((Integer)l.get(var4++)).intValue();
   }

   public ReportRef getReportEntityRef() {
      return this.mReportEntityRef;
   }

   public int getReportType() {
      return this.mReportType;
   }

   public int getTaskId() {
      return this.mTaskId;
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
