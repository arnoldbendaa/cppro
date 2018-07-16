// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:25
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.task;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.task.AllWebTasksELO;
import com.cedar.cp.dto.task.AllWebTasksForUserELO;
import com.cedar.cp.dto.task.TaskCK;
import com.cedar.cp.dto.task.TaskPK;
import com.cedar.cp.dto.task.WebTasksDetailsELO;
import com.cedar.cp.ejb.impl.task.TaskDAO;
import com.cedar.cp.ejb.impl.task.TaskEVO;
import com.cedar.cp.ejb.impl.task.TaskLocal;
import com.cedar.cp.ejb.impl.task.TaskLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TaskAccessor implements Serializable {

   private TaskLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;

   public static TaskEEJB server = new TaskEEJB(); 
   public TaskAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private TaskLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (TaskLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/TaskLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up TaskLocalHome", var2);
      }
   }

   private TaskEEJB getLocal(TaskPK pk) throws Exception {
//      TaskLocal local = (TaskLocal)this.mLocals.get(pk);
//      if(local == null) {
//         local = this.getLocalHome().findByPrimaryKey(pk);
//         this.mLocals.put(pk, local);
//      }
//
//      return local;
	   return server;
   }

   public TaskEVO create(TaskEVO evo) throws Exception {
//      TaskLocal local = this.getLocalHome().create(evo);
	   server.ejbCreate(evo);
	      TaskEVO newevo = server.getDetails("<UseLoadedEVOs>");
	      TaskPK pk = newevo.getPK();
	      this.mLocals.put(pk, server);
	      return newevo;   }

   public void remove(TaskPK pk) throws Exception {
      this.getLocal(pk).ejbRemove();
   }

   public TaskEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

//    return key instanceof TaskPK?this.getLocal((TaskPK)key).getDetails(dependants):null;
    if(key instanceof TaskPK){
  	  TaskPK pk = (TaskPK)key;
  	  TaskCK ck = new TaskCK(pk);
  	  return this.getLocal(pk).getDetails(ck, dependants);
    }else 
  	  return null;
    }

   public void setDetails(TaskEVO evo) throws Exception {
      TaskPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
      this.getLocal(pk).ejbStore();
   }

   public TaskEVO setAndGetDetails(TaskEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public TaskPK generateKeys(TaskPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllWebTasksELO getAllWebTasks() {
      TaskDAO dao = new TaskDAO();
      return dao.getAllWebTasks();
   }

   public AllWebTasksForUserELO getAllWebTasksForUser(String param1) {
      TaskDAO dao = new TaskDAO();
      return dao.getAllWebTasksForUser(param1);
   }

   public WebTasksDetailsELO getWebTasksDetails(int param1) {
      TaskDAO dao = new TaskDAO();
      return dao.getWebTasksDetails(param1);
   }

   public void logEvent(int taskId, int eventType, String eventText) {
      (new TaskDAO()).logEvent(taskId, eventType, eventText);
   }

   public EntityList getIncompleteTasks() {
      return (new TaskDAO()).getIncompleteTasks();
   }

   public boolean areAllIssuedTasksComplete(int originalTaskId, int finishingTaskId) {
      return (new TaskDAO()).areAllIssuedTasksComplete(originalTaskId, finishingTaskId);
   }

   public EntityList getRestartableTaskGroups() {
      return (new TaskDAO()).getRestartableTaskGroups();
   }
}
