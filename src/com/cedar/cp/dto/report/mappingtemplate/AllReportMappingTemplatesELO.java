// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.mappingtemplate;

import com.cedar.cp.api.report.mappingtemplate.ReportMappingTemplateRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllReportMappingTemplatesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ReportMappingTemplate"};
   private transient ReportMappingTemplateRef mReportMappingTemplateEntityRef;
   private transient int mReportMappingTemplateId;
   private transient String mDescription;


   public AllReportMappingTemplatesELO() {
      super(new String[]{"ReportMappingTemplate", "ReportMappingTemplateId", "Description"});
   }

   public void add(ReportMappingTemplateRef eRefReportMappingTemplate, int col1, String col2) {
      ArrayList l = new ArrayList();
      l.add(eRefReportMappingTemplate);
      l.add(new Integer(col1));
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
      this.mReportMappingTemplateEntityRef = (ReportMappingTemplateRef)l.get(index);
      this.mReportMappingTemplateId = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
   }

   public ReportMappingTemplateRef getReportMappingTemplateEntityRef() {
      return this.mReportMappingTemplateEntityRef;
   }

   public int getReportMappingTemplateId() {
      return this.mReportMappingTemplateId;
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
