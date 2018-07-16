// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:06
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.definition;

import com.cedar.cp.api.report.definition.ReportDefFormRef;
import com.cedar.cp.api.report.definition.ReportDefinitionRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckFormIsUsedELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ReportDefForm", "ReportDefinition"};
   private transient ReportDefFormRef mReportDefFormEntityRef;
   private transient ReportDefinitionRef mReportDefinitionEntityRef;
   private transient int mReportDefinitionId;
   private transient int mModelId;
   private transient int mFormId;


   public CheckFormIsUsedELO() {
      super(new String[]{"ReportDefForm", "ReportDefinition", "ReportDefinitionId", "ModelId", "FormId"});
   }

   public void add(ReportDefFormRef eRefReportDefForm, ReportDefinitionRef eRefReportDefinition, int col1, int col2, int col3) {
      ArrayList l = new ArrayList();
      l.add(eRefReportDefForm);
      l.add(eRefReportDefinition);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
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
      this.mReportDefFormEntityRef = (ReportDefFormRef)l.get(index);
      this.mReportDefinitionEntityRef = (ReportDefinitionRef)l.get(var4++);
      this.mReportDefinitionId = ((Integer)l.get(var4++)).intValue();
      this.mModelId = ((Integer)l.get(var4++)).intValue();
      this.mFormId = ((Integer)l.get(var4++)).intValue();
   }

   public ReportDefFormRef getReportDefFormEntityRef() {
      return this.mReportDefFormEntityRef;
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

   public int getFormId() {
      return this.mFormId;
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
