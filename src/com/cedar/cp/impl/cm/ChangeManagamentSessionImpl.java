// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.cm;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cm.ChangeManagementSession;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.ejb.api.cm.ChangeManagementServer;

public class ChangeManagamentSessionImpl implements ChangeManagementSession {

   private CPConnection mConnection;


   public ChangeManagamentSessionImpl(CPConnection connection) {
      this.mConnection = connection;
   }

   public void registerUpdateRequest(String xmlRequest) throws ValidationException {
      this.getServer().registerUpdateRequest(xmlRequest);
   }

   public int issueUpdateTask(ModelRef model) throws ValidationException {
      return this.getServer().issueUpdateTask(model);
   }

   public ChangeManagementServer getServer() {
      return new ChangeManagementServer(this.mConnection);
   }
}
