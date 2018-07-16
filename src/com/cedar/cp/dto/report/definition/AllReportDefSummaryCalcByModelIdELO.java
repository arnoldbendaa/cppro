// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.definition;

import com.cedar.cp.api.report.definition.ReportDefSummaryCalcRef;
import com.cedar.cp.api.report.definition.ReportDefinitionRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllReportDefSummaryCalcByModelIdELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ReportDefSummaryCalc", "ReportDefinition"};
   private transient ReportDefSummaryCalcRef mReportDefSummaryCalcEntityRef;
   private transient ReportDefinitionRef mReportDefinitionEntityRef;
   private transient int mReportDefinitionId;
   private transient int mModelId;
   private transient int mStructureElementId;
   private transient int mCcDeploymentId;
   private transient int mReportTemplateId;
   private transient String mColumnMap;


   public AllReportDefSummaryCalcByModelIdELO() {
      super(new String[]{"ReportDefSummaryCalc", "ReportDefinition", "ReportDefinitionId", "ModelId", "StructureElementId", "CcDeploymentId", "ReportTemplateId", "ColumnMap"});
   }

   public void add(ReportDefSummaryCalcRef eRefReportDefSummaryCalc, ReportDefinitionRef eRefReportDefinition, int col1, int col2, int col3, int col4, int col5, String col6) {
      ArrayList l = new ArrayList();
      l.add(eRefReportDefSummaryCalc);
      l.add(eRefReportDefinition);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(new Integer(col3));
      l.add(new Integer(col4));
      l.add(new Integer(col5));
      l.add(col6);
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
      this.mReportDefSummaryCalcEntityRef = (ReportDefSummaryCalcRef)l.get(index);
      this.mReportDefinitionEntityRef = (ReportDefinitionRef)l.get(var4++);
      this.mReportDefinitionId = ((Integer)l.get(var4++)).intValue();
      this.mModelId = ((Integer)l.get(var4++)).intValue();
      this.mStructureElementId = ((Integer)l.get(var4++)).intValue();
      this.mCcDeploymentId = ((Integer)l.get(var4++)).intValue();
      this.mReportTemplateId = ((Integer)l.get(var4++)).intValue();
      this.mColumnMap = (String)l.get(var4++);
   }

   public ReportDefSummaryCalcRef getReportDefSummaryCalcEntityRef() {
      return this.mReportDefSummaryCalcEntityRef;
   }

   public ReportDefinitionRef getReportDefinitionEntityRef() {
      return this.mReportDefinitionEntityRef;
   }

   public int getReportDefinitionId() {
      return this.mReportDefinitionId;
   }

   public int getModelId() {
      return this.mModelId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public int getCcDeploymentId() {
      return this.mCcDeploymentId;
   }

   public int getReportTemplateId() {
      return this.mReportTemplateId;
   }

   public String getColumnMap() {
      return this.mColumnMap;
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
