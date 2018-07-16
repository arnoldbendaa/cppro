// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:59
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.admin.tidytask;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.admin.tidytask.AllTidyTasksELO;
import com.cedar.cp.dto.admin.tidytask.OrderedChildrenELO;
import com.cedar.cp.dto.admin.tidytask.TidyTaskCK;
import com.cedar.cp.dto.admin.tidytask.TidyTaskPK;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskDAO;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskEVO;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskLinkDAO;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskLocal;
import com.cedar.cp.ejb.impl.admin.tidytask.TidyTaskLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class TidyTaskAccessor implements Serializable {

   private TidyTaskLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_TIDY_TASKS_EVENTS = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";


   public TidyTaskAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private TidyTaskLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (TidyTaskLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/TidyTaskLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up TidyTaskLocalHome", var2);
      }
   }

   private TidyTaskLocal getLocal(TidyTaskPK pk) throws Exception {
      TidyTaskLocal local = (TidyTaskLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public TidyTaskEVO create(TidyTaskEVO evo) throws Exception {
      TidyTaskLocal local = this.getLocalHome().create(evo);
      TidyTaskEVO newevo = local.getDetails("<UseLoadedEVOs>");
      TidyTaskPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(TidyTaskPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public TidyTaskEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof TidyTaskCK) {
         TidyTaskPK pk = ((TidyTaskCK)key).getTidyTaskPK();
         return this.getLocal(pk).getDetails((TidyTaskCK)key, dependants);
      } else {
         return key instanceof TidyTaskPK?this.getLocal((TidyTaskPK)key).getDetails(dependants):null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(TidyTaskEVO evo) throws Exception {
      TidyTaskPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public TidyTaskEVO setAndGetDetails(TidyTaskEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public TidyTaskPK generateKeys(TidyTaskPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllTidyTasksELO getAllTidyTasks() {
      TidyTaskDAO dao = new TidyTaskDAO();
      return dao.getAllTidyTasks();
   }

   public OrderedChildrenELO getOrderedChildren(int param1) {
      TidyTaskLinkDAO dao = new TidyTaskLinkDAO();
      return dao.getOrderedChildren(param1);
   }
}
