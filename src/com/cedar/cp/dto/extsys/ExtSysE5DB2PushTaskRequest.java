// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extsys;

import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class ExtSysE5DB2PushTaskRequest extends AbstractTaskRequest {

   private List<FinanceCubeRef> mFinanceCubes;


   public ExtSysE5DB2PushTaskRequest(List<FinanceCubeRef> financeCubes) {
      this.mFinanceCubes = financeCubes;
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("E5 DB2 Push Task");
      return l;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.extsys.ExtSysE5DB2PushTask";
   }

   public List<FinanceCubeRef> getFinanceCubes() {
      return this.mFinanceCubes;
   }
}
