package com.cedar.cp.ejb.impl.importtask;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.importtask.AllImportTasksELO;
import com.cedar.cp.dto.importtask.OrderedChildrenELO;
import com.cedar.cp.dto.importtask.ImportTaskCK;
import com.cedar.cp.dto.importtask.ImportTaskPK;
import com.cedar.cp.dto.recalculate.AllRecalculateBatchTasksELO;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.ejb.impl.importtask.ImportTaskDAO;
import com.cedar.cp.ejb.impl.importtask.ImportTaskEVO;
import com.cedar.cp.ejb.impl.importtask.ImportTaskLinkDAO;
import com.cedar.cp.ejb.impl.importtask.ImportTaskLocal;
import com.cedar.cp.ejb.impl.importtask.ImportTaskLocalHome;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskDAO;

import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ImportTaskAccessor implements Serializable {

   private ImportTaskLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_IMPORT_TASKS_EVENTS = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";
   ImportTaskEEJB importEjb = new ImportTaskEEJB();

   public ImportTaskAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private ImportTaskLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (ImportTaskLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/ImportTaskLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up ImportTaskLocalHome", var2);
      }
   }

   private ImportTaskEEJB getLocal(ImportTaskPK pk) throws Exception {
//      ImportTaskLocal local = (ImportTaskLocal)this.mLocals.get(pk);
//      if(local == null) {
//         local = this.getLocalHome().findByPrimaryKey(pk);
//         this.mLocals.put(pk, local);
//      }
//
//      return local;
	   return importEjb;
   }

   public ImportTaskEVO create(ImportTaskEVO evo) throws Exception {
//      ImportTaskLocal local = this.getLocalHome().create(evo);
//      ImportTaskEVO newevo = local.getDetails("<UseLoadedEVOs>");
//      ImportTaskPK pk = newevo.getPK();
//      this.mLocals.put(pk, local);
//      return newevo;
	   importEjb.ejbCreate(evo);
	   ImportTaskEVO newevo = importEjb.getDetails("<UseLoadedEVOs>");
	   ImportTaskPK pk = newevo.getPK();
	   this.mLocals.put(pk, importEjb);
	   return newevo;
   }

   public void remove(ImportTaskPK pk) throws Exception {
      this.getLocal(pk).ejbRemove();
   }

   public ImportTaskEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof ImportTaskCK) {
         ImportTaskPK pk = ((ImportTaskCK)key).getImportTaskPK();
         return this.getLocal(pk).getDetails((ImportTaskCK)key, dependants);
      } else {
//         return key instanceof ImportTaskPK?this.getLocal((ImportTaskPK)key).getDetails(dependants):null;
    	  ImportTaskPK pk = (ImportTaskPK)key;
    	  ImportTaskCK ck = new ImportTaskCK(pk);
    	  if(key instanceof ImportTaskPK)
    		  return importEjb.getDetails(ck,dependants);
    	  else 
    		  return null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(ImportTaskEVO evo) throws Exception {
      ImportTaskPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
      this.getLocal(pk).ejbFindByPrimaryKey(pk);
      this.getLocal(pk).ejbStore();
   }

   public ImportTaskEVO setAndGetDetails(ImportTaskEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public ImportTaskPK generateKeys(ImportTaskPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllImportTasksELO getAllImportTasks() {
      ImportTaskDAO dao = new ImportTaskDAO();
      return dao.getAllImportTasks();
   }
   
   public AllRecalculateBatchTasksELO getAllRecalculateBatchTasks() {
	   RecalculateBatchTaskDAO dao = new RecalculateBatchTaskDAO();
	   return dao.getAllRecalculateBatchTasks();
   }

   public OrderedChildrenELO getOrderedChildren(int param1) {
      ImportTaskLinkDAO dao = new ImportTaskLinkDAO();
      return dao.getOrderedChildren(param1);
   }
}
