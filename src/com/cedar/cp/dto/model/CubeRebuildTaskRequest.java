// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:56
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import java.util.ArrayList;
import java.util.List;

public class CubeRebuildTaskRequest extends AbstractTaskRequest {

   private List<EntityRef> mRebuildList;


   public CubeRebuildTaskRequest(List<EntityRef> rebuildList) {
      this.mRebuildList = rebuildList;
   }

   public List<EntityRef> getRebuildList() {
      return this.mRebuildList;
   }

   public String getService() {
      return "com.cedar.cp.ejb.base.async.cubeadmin.CubeRebuildTask";
   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("Cube Rebuild");
      return l;
   }
}
