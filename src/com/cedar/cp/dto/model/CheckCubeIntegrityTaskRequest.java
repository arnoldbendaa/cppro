// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class CheckCubeIntegrityTaskRequest extends AbstractTaskRequest {

   private List<EntityRef> mCubeList;


   public CheckCubeIntegrityTaskRequest(List<EntityRef> cubeList) {
      this.mCubeList = cubeList;
   }

   public List<EntityRef> getCubeList() {
      return this.mCubeList;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.cubeadmin.CheckCubeIntegrityTask";
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("Check Cube Integrity");
      return l;
   }
}
