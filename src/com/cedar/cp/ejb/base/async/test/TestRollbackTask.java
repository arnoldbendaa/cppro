// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:02
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.test;

import com.cedar.cp.ejb.base.async.AbstractTask;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.naming.InitialContext;

public class TestRollbackTask extends AbstractTask {

   public int getReportType() {
      return 0;
   }

   public String getEntityName() {
      return "TestRollbackTask";
   }

   public void runUnitOfWork(InitialContext initialContext) throws Exception {
      Connection connection = this.getConnection();
      PreparedStatement ps = connection.prepareStatement("update rollback_task_test set name=\'THIS SHOULD NOT BE HERE\'");
      ps.executeUpdate();
      ps.close();
      ps = connection.prepareStatement("this is not sql and will fail...");
      ps.executeUpdate();
      ps.close();
   }
}
