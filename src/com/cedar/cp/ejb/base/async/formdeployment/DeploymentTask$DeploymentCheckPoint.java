// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:01
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.async.formdeployment;

import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import com.cedar.cp.ejb.impl.user.UserEVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeploymentTask$DeploymentCheckPoint extends AbstractTaskCheckpoint {

   private Map<UserEVO, List<String>> mProfiles = new HashMap();


   public Map<UserEVO, List<String>> getProfiles() {
      return this.mProfiles;
   }

   public void setProfiles(Map<UserEVO, List<String>> profiles) {
      this.mProfiles = profiles;
   }

   public void addToMap(UserEVO evo, String id) {
      if(this.mProfiles.keySet().contains(evo)) {
         List data = (List)this.mProfiles.get(evo);
         data.add(id);
      } else {
         ArrayList data1 = new ArrayList();
         data1.add(id);
         this.mProfiles.put(evo, data1);
      }

   }

   public List toDisplay() {
      ArrayList l = new ArrayList();
      l.add("profile size =" + this.getProfiles().size());
      return l;
   }
}
