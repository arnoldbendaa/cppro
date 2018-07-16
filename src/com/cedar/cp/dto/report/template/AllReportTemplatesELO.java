// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.template;

import com.cedar.cp.api.report.template.ReportTemplateRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllReportTemplatesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ReportTemplate"};
   private transient ReportTemplateRef mReportTemplateEntityRef;
   private transient int mReportTemplateId;
   private transient String mDescription;


   public AllReportTemplatesELO() {
      super(new String[]{"ReportTemplate", "ReportTemplateId", "Description"});
   }

   public void add(ReportTemplateRef eRefReportTemplate, int col1, String col2) {
      ArrayList l = new ArrayList();
      l.add(eRefReportTemplate);
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
      this.mReportTemplateEntityRef = (ReportTemplateRef)l.get(index);
      this.mReportTemplateId = ((Integer)l.get(var4++)).intValue();
      this.mDescription = (String)l.get(var4++);
   }

   public ReportTemplateRef getReportTemplateEntityRef() {
      return this.mReportTemplateEntityRef;
   }

   public int getReportTemplateId() {
      return this.mReportTemplateId;
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
