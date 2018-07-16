// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.definition;

import com.cedar.cp.api.report.definition.ReportDefinitionRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportDefinitionForVisIdELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ReportDefinition", "ReportDefForm", "ReportDefMappedExcel", "ReportDefCalculator", "ReportDefSummaryCalc"};
   private transient ReportDefinitionRef mReportDefinitionEntityRef;
   private transient String mVisId;
   private transient String mDescription;


   public ReportDefinitionForVisIdELO() {
      super(new String[]{"ReportDefinition", "VisId", "Description"});
   }

   public void add(ReportDefinitionRef eRefReportDefinition, String col1, String col2) {
      ArrayList l = new ArrayList();
      l.add(eRefReportDefinition);
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
      this.mReportDefinitionEntityRef = (ReportDefinitionRef)l.get(index);
      this.mVisId = (String)l.get(var4++);
      this.mDescription = (String)l.get(var4++);
   }

   public ReportDefinitionRef getReportDefinitionEntityRef() {
      return this.mReportDefinitionEntityRef;
   }

   public String getVisId() {
      return this.mVisId;
   }

   public String getDescription() {
      return this.mDescription;
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
