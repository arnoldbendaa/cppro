// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.performance;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.performance.PerformanceProcess;
import com.cedar.cp.ejb.api.performance.PerformanceServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.performance.PerformanceData;
import com.cedar.cp.util.performance.PerformanceDatum;
import com.cedar.cp.util.performance.PerformanceType;

public class PerformanceProcessImpl extends BusinessProcessImpl implements PerformanceProcess {

   private Log mLog = new Log(this.getClass());


   public PerformanceProcessImpl(CPConnection connection) {
      super(connection);
   }

   protected int getProcessID() {
      return 53;
   }

   public PerformanceData getPerformanceData() throws ValidationException, CPException {
      PerformanceServer server = new PerformanceServer(this.getConnection());
      return server.getPerformanceData();
   }

   public PerformanceType getPerformanceType(String type) throws ValidationException, CPException {
      PerformanceServer server = new PerformanceServer(this.getConnection());
      return server.getPerformanceType(type);
   }

   public PerformanceDatum getPerformanceDatum(String id) throws ValidationException, CPException {
      PerformanceServer server = new PerformanceServer(this.getConnection());
      return server.getPerformanceDatum(id);
   }
}
