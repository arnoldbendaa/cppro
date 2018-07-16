// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:08
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.report.pack;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.report.definition.WebReportOption;
import com.cedar.cp.api.report.pack.ReportPackOption;
import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class PackTaskRequest extends AbstractTaskRequest implements TaskRequest {

   private EntityRef mPackRef;
   private ReportPackOption mOption;
   private WebReportOption mWebOptions;


   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("ReportPack = " + this.getPackRef());
      return l;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.reportpack.PackTask";
   }

   public ReportPackOption getOption() {
      return this.mOption;
   }

   public void setOption(ReportPackOption option) {
      this.mOption = option;
   }

   public EntityRef getPackRef() {
      return this.mPackRef;
   }

   public void setPackRef(EntityRef packRef) {
      this.mPackRef = packRef;
   }

   public WebReportOption getWebOptions() {
      return this.mWebOptions;
   }

   public void setWebOptions(WebReportOption webOptions) {
      this.mWebOptions = webOptions;
   }
}
