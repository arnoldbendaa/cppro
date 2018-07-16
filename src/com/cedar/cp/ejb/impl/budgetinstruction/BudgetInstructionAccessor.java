// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:07
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.budgetinstruction;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.base.CompositeKey;
import com.cedar.cp.dto.base.PrimaryKey;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionAssignmentsELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForCycleELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForLocationELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForModelELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsWebELO;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionCK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionAssignmentDAO;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionDAO;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionEVO;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionLocal;
import com.cedar.cp.ejb.impl.budgetinstruction.BudgetInstructionLocalHome;
import java.io.Serializable;
import java.util.Hashtable;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class BudgetInstructionAccessor implements Serializable {

   private BudgetInstructionLocalHome mLocalHome;
   private Hashtable mLocals = new Hashtable();
   private transient InitialContext mInitialContext;
   public static final String GET_BUDGET_INSTRUCTION_ASSIGNMENTS = "<0>";
   public static final String GET_ALL_DEPENDANTS = "<0>";


   public BudgetInstructionAccessor(InitialContext ctx) {
      this.mInitialContext = ctx;
   }

   private BudgetInstructionLocalHome getLocalHome() {
      try {
         if(this.mLocalHome != null) {
            return this.mLocalHome;
         } else {
            this.mLocalHome = (BudgetInstructionLocalHome)this.mInitialContext.lookup("java:comp/env/ejb/BudgetInstructionLocalHome");
            return this.mLocalHome;
         }
      } catch (NamingException var2) {
         throw new RuntimeException("error looking up BudgetInstructionLocalHome", var2);
      }
   }

   private BudgetInstructionLocal getLocal(BudgetInstructionPK pk) throws Exception {
      BudgetInstructionLocal local = (BudgetInstructionLocal)this.mLocals.get(pk);
      if(local == null) {
         local = this.getLocalHome().findByPrimaryKey(pk);
         this.mLocals.put(pk, local);
      }

      return local;
   }

   public BudgetInstructionEVO create(BudgetInstructionEVO evo) throws Exception {
      BudgetInstructionLocal local = this.getLocalHome().create(evo);
      BudgetInstructionEVO newevo = local.getDetails("<UseLoadedEVOs>");
      BudgetInstructionPK pk = newevo.getPK();
      this.mLocals.put(pk, local);
      return newevo;
   }

   public void remove(BudgetInstructionPK pk) throws Exception {
      this.getLocal(pk).remove();
   }

   public BudgetInstructionEVO getDetails(Object paramKey, String dependants) throws Exception {
      Object key = paramKey;
      if(paramKey instanceof EntityRef) {
         key = ((EntityRef)paramKey).getPrimaryKey();
      }

      if(key instanceof BudgetInstructionCK) {
         BudgetInstructionPK pk = ((BudgetInstructionCK)key).getBudgetInstructionPK();
         return this.getLocal(pk).getDetails((BudgetInstructionCK)key, dependants);
      } else {
         return key instanceof BudgetInstructionPK?this.getLocal((BudgetInstructionPK)key).getDetails(dependants):null;
      }
   }

   public CompositeKey getCKForDependantPK(PrimaryKey key) {
      return null;
   }

   public void setDetails(BudgetInstructionEVO evo) throws Exception {
      BudgetInstructionPK pk = evo.getPK();
      this.getLocal(pk).setDetails(evo);
   }

   public BudgetInstructionEVO setAndGetDetails(BudgetInstructionEVO evo, String dependants) throws Exception {
      return this.getLocal(evo.getPK()).setAndGetDetails(evo, dependants);
   }

   public BudgetInstructionPK generateKeys(BudgetInstructionPK pk) throws Exception {
      return this.getLocal(pk).generateKeys();
   }

   public AllBudgetInstructionsELO getAllBudgetInstructions() {
      BudgetInstructionDAO dao = new BudgetInstructionDAO();
      return dao.getAllBudgetInstructions();
   }

   public AllBudgetInstructionsWebELO getAllBudgetInstructionsWeb() {
      BudgetInstructionDAO dao = new BudgetInstructionDAO();
      return dao.getAllBudgetInstructionsWeb();
   }

   public AllBudgetInstructionsForModelELO getAllBudgetInstructionsForModel(int param1) {
      BudgetInstructionDAO dao = new BudgetInstructionDAO();
      return dao.getAllBudgetInstructionsForModel(param1);
   }

   public AllBudgetInstructionsForCycleELO getAllBudgetInstructionsForCycle(int param1) {
      BudgetInstructionDAO dao = new BudgetInstructionDAO();
      return dao.getAllBudgetInstructionsForCycle(param1);
   }

   public AllBudgetInstructionsForLocationELO getAllBudgetInstructionsForLocation(int param1) {
      BudgetInstructionDAO dao = new BudgetInstructionDAO();
      return dao.getAllBudgetInstructionsForLocation(param1);
   }

   public AllBudgetInstructionAssignmentsELO getAllBudgetInstructionAssignments() {
      BudgetInstructionAssignmentDAO dao = new BudgetInstructionAssignmentDAO();
      return dao.getAllBudgetInstructionAssignments();
   }
}
