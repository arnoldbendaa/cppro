// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.type.param;

import com.cedar.cp.api.report.type.ReportTypeRef;
import com.cedar.cp.api.report.type.param.ReportTypeParamRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllReportTypeParamsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ReportTypeParam", "ReportType"};
   private transient ReportTypeParamRef mReportTypeParamEntityRef;
   private transient ReportTypeRef mReportTypeEntityRef;
   private transient int mSeq;
   private transient int mControl;
   private transient String mValidationExp;
   private transient String mValidationTxt;


   public AllReportTypeParamsELO() {
      super(new String[]{"ReportTypeParam", "ReportType", "Seq", "Control", "ValidationExp", "ValidationTxt"});
   }

   public void add(ReportTypeParamRef eRefReportTypeParam, ReportTypeRef eRefReportType, int col1, int col2, String col3, String col4) {
      ArrayList l = new ArrayList();
      l.add(eRefReportTypeParam);
      l.add(eRefReportType);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(col3);
      l.add(col4);
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
      this.mReportTypeParamEntityRef = (ReportTypeParamRef)l.get(index);
      this.mReportTypeEntityRef = (ReportTypeRef)l.get(var4++);
      this.mSeq = ((Integer)l.get(var4++)).intValue();
      this.mControl = ((Integer)l.get(var4++)).intValue();
      this.mValidationExp = (String)l.get(var4++);
      this.mValidationTxt = (String)l.get(var4++);
   }

   public ReportTypeParamRef getReportTypeParamEntityRef() {
      return this.mReportTypeParamEntityRef;
   }

   public ReportTypeRef getReportTypeEntityRef() {
      return this.mReportTypeEntityRef;
   }

   public int getSeq() {
      return this.mSeq;
   }

   public int getControl() {
      return this.mControl;
   }

   public String getValidationExp() {
      return this.mValidationExp;
   }

   public String getValidationTxt() {
      return this.mValidationTxt;
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
