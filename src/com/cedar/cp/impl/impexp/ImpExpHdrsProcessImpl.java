// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.impexp;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataEntry.DataExtract;
import com.cedar.cp.api.impexp.ImpExpHdrsProcess;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.api.impexp.ImportExportServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;

public class ImpExpHdrsProcessImpl extends BusinessProcessImpl implements ImpExpHdrsProcess {

   private Log mLog = new Log(this.getClass());


   public ImpExpHdrsProcessImpl(CPConnection connection) {
      super(connection);
   }

   public EntityList getAllImpExpHdrs() {
      try {
         return this.getConnection().getListHelper().getAllImpExpHdrs();
      } catch (Exception var2) {
         var2.printStackTrace();
         throw new RuntimeException("can\'t get AllImpExpHdrs", var2);
      }
   }

   public String getProcessName() {
      String ret = "Processing ImpExpHdr";
      return ret;
   }

   protected int getProcessID() {
      return 55;
   }

   public int issueImportTask(int financeCubeId, DataExtract dataExtract) throws CPException, ValidationException {
      dataExtract.setUserId(((UserPK)this.getConnection().getUserContext().getPrimaryKey()).getUserId());
      ImportExportServer server = new ImportExportServer(this.getConnection());
      return server.issueImportTask(financeCubeId, dataExtract);
   }
}
