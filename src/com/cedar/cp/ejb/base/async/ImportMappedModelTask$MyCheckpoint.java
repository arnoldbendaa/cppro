// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async;

import com.cedar.cp.dto.cm.ChangeManagementTaskRequest;
import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import com.cedar.cp.ejb.base.async.cm.ChangeManagementCheckPoint;
import java.util.ArrayList;
import java.util.List;

public class ImportMappedModelTask$MyCheckpoint extends AbstractTaskCheckpoint {

   private ChangeManagementTaskRequest mCMRequest = null;
   private ChangeManagementCheckPoint mCMCheckpoint = null;
   private int mExternalSystemType;


   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add((String)this.mCMCheckpoint.toDisplay().get(0));
      return l;
   }

   public void setCMRequest(ChangeManagementTaskRequest req) {
      this.mCMRequest = req;
   }

   public ChangeManagementTaskRequest getCMRequest() {
      return this.mCMRequest;
   }

   public void setCMCheckpoint(ChangeManagementCheckPoint check) {
      this.mCMCheckpoint = check;
   }

   public ChangeManagementCheckPoint getCMCheckpoint() {
      return this.mCMCheckpoint;
   }

   public void setExternalSystemType(int extsysType) {
      this.mExternalSystemType = extsysType;
   }

   public int getExternalSystemType() {
      return this.mExternalSystemType;
   }
}
