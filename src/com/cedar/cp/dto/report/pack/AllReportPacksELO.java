// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.pack;

import com.cedar.cp.api.report.pack.ReportPackRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllReportPacksELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ReportPack", "ReportPackLink"};
   private transient ReportPackRef mReportPackEntityRef;
   private transient String mDescription;
   private transient String mParamExample;


   public AllReportPacksELO() {
      super(new String[]{"ReportPack", "Description", "ParamExample"});
   }

   public void add(ReportPackRef eRefReportPack, String col1, String col2) {
      ArrayList l = new ArrayList();
      l.add(eRefReportPack);
      l.add(col1);
      l.add(col2);
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
      this.mReportPackEntityRef = (ReportPackRef)l.get(index);
      this.mDescription = (String)l.get(var4++);
      this.mParamExample = (String)l.get(var4++);
   }

   public ReportPackRef getReportPackEntityRef() {
      return this.mReportPackEntityRef;
   }

   public String getDescription() {
      return this.mDescription;
   }

   public String getParamExample() {
      return this.mParamExample;
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
