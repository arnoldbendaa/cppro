// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:27
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task.group;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.task.group.AllTaskGroupsELO;
import com.cedar.cp.dto.task.group.TaskGroupCK;
import com.cedar.cp.dto.task.group.TaskGroupPK;
import com.cedar.cp.dto.task.group.TaskGroupRICheckELO;
import com.cedar.cp.ejb.impl.task.group.TaskGroupDAO;
import com.cedar.cp.ejb.impl.task.group.TaskGroupEVO;
import com.cedar.cp.ejb.impl.task.group.TaskGroupLocal;
import com.cedar.cp.ejb.impl.task.group.TaskGroupLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TaskGroupAccessor implements Serializable {

   private TaskGroupLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_TASK_GROUP_ROWS = "<0>";
   public static final String GET_T_G_ROWS_PARAMS = "<1>";
   public static final String GET_ALL_DEPENDANTS = "<0><1>";


   public TaskGroupAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private TaskGroupLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (TaskGroupLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/TaskGroupLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up TaskGroupLocalHome", var2);
      }
   }

   private TaskGroupLocal getLocal(TaskGroupPK pk) throws Exception {
      TaskGroupLocal local = (TaskGroupLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public TaskGroupEVO create(TaskGroupEVO evo) throws Exception {
      TaskGroupLocal local = this.getLocalHome().create(evo);
      TaskGroupEVO newevo = local.getDetails("<UseLoadedEVOs>");
      TaskGroupPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(TaskGroupPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public TaskGroupEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof TaskGroupCK) {
         TaskGroupPK pk = ((TaskGroupCK)key).getTaskGroupPK();
         return this.getLocal(pk).getDetails((TaskGroupCK)key, dependants);
      } else {
         return key instanceof TaskGroupPK?this.getLocal((TaskGroupPK)key).getDetails(dependants):null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(TaskGroupEVO evo) throws Exception {
      TaskGroupPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public TaskGroupEVO setAndGetDetails(TaskGroupEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public TaskGroupPK generateKeys(TaskGroupPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllTaskGroupsELO getAllTaskGroups() {
      TaskGroupDAO dao = new TaskGroupDAO();
      return dao.getAllTaskGroups();
   }

   public TaskGroupRICheckELO getTaskGroupRICheck(int param1) {
      TaskGroupDAO dao = new TaskGroupDAO();
      return dao.getTaskGroupRICheck(param1);
   }
}
