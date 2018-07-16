// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.type;

import com.cedar.cp.api.report.type.ReportTypeRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllReportTypesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ReportType", "ReportTypeParam"};
   private transient ReportTypeRef mReportTypeEntityRef;
   private transient int mReportTypeId;
   private transient String mDescription;
   private transient int mType;


   public AllReportTypesELO() {
      super(new String[]{"ReportType", "ReportTypeId", "Description", "Type"});
   }

   public void add(ReportTypeRef eRefReportType, int col1, String col2, int col3) {
      ArrayList l = new ArrayList();
      l.add(eRefReportType);
      l.add(new Integer(col1));
      l.add(col2);
      l.add(new Integer(col3));
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
      this.mReportTypeEntityRef = (ReportTypeRef)l.get(index);
      this.mReportTypeId = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
      this.mType = ((Integer)l.get(var4++)).intValue();
   }

   public ReportTypeRef getReportTypeEntityRef() {
      return this.mReportTypeEntityRef;
   }

   public int getReportTypeId() {
      return this.mReportTypeId;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public int getType() {
      return this.mType;
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
