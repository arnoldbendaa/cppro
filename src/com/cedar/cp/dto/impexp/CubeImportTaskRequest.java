// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.impexp;

import com.cedar.cp.api.dataEntry.DataExtract;
import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class CubeImportTaskRequest extends AbstractTaskRequest implements TaskRequest {

   private int mFinanceCubeId;
   private DataExtract mDataExtract;


   public CubeImportTaskRequest(int financeCubeId, DataExtract dataExtract) {
      this.mFinanceCubeId = financeCubeId;
      this.mDataExtract = dataExtract;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.impexp.CubeImportTask";
   }

   public int getFinanceCubeId() {
      return this.mFinanceCubeId;
   }

   public DataExtract getDataExtract() {
      return this.mDataExtract;
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("Cube Import");
      return l;
   }
}
