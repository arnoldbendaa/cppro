// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.BudgetDetailsForUserELO;
import com.cedar.cp.dto.model.BudgetDetailsForUserLevel2ELO;
import com.cedar.cp.dto.model.BudgetDetailsForUserLevel3ELO;
import com.cedar.cp.dto.model.BudgetDetailsForUserLevel4ELO;
import com.cedar.cp.impl.base.IssueTaskBase;
import com.cedar.cp.util.ParamHelper;
import java.util.Iterator;

public class TestBudgetUsersELO extends IssueTaskBase {

   public static void main(String[] args) {
      System.out.println("short max=32767");
      System.out.println("int max=2147483647");
      System.out.println("long max=9223372036854775807");
      TestBudgetUsersELO app = new TestBudgetUsersELO();
      int exitCode = app.processArgs(args);
      System.exit(exitCode);
   }

   public String[][] getParameterInfo() {
      return new String[][]{{"-s{erver}", "String", "Host name"}, {"-u{ser}", "string", "User name"}, {"-pass{word}", "string", "Password"}};
   }

   public void checkSpecificParams(ParamHelper ph) {
      this.mLog.info("go", this.mServer + " " + this.mUserName + " " + this.mPassword);
   }

   public void issueRequest() {
      BudgetDetailsForUserELO elo = (BudgetDetailsForUserELO)this.mConnection.getBudgetUsersProcess().getBudgetDetailsForUser(1);
      System.out.println("rows returned=" + elo.size());
      Iterator iter = elo.iterator();

      while(iter.hasNext()) {
         elo = (BudgetDetailsForUserELO)iter.next();
         this.printModel(elo.getModelEntityRef());
         BudgetDetailsForUserLevel2ELO bcelo = elo.getBudgetCycles();
         Iterator iter2 = bcelo.iterator();

         while(iter2.hasNext()) {
            bcelo = (BudgetDetailsForUserLevel2ELO)iter2.next();
            this.printBudgetCycle(bcelo.getBudgetCycleRef());
            BudgetDetailsForUserLevel3ELO blelo = bcelo.getLocations();
            Iterator iter3 = blelo.iterator();

            while(iter3.hasNext()) {
               blelo = (BudgetDetailsForUserLevel3ELO)iter3.next();
               this.printBudgetLocation(blelo.getState(), blelo.getVisId(), blelo.getStructureElementId());
               this.printChildLocations(blelo.getChildLocations());
            }
         }
      }

   }

   private void printModel(ModelRef mr) {
      System.out.println("model: " + mr.getPrimaryKey() + " - " + mr.getNarrative());
   }

   private void printBudgetCycle(BudgetCycleRef bcr) {
      System.out.println("    budgetCycle: " + bcr.getPrimaryKey() + " - " + bcr.getNarrative());
   }

   private void printBudgetLocation(Integer state, String visId, int structureElementId) {
      System.out.println("      structureElementId: " + structureElementId + " - " + visId);
      System.out.println("\t               state: " + (state != null?state.toString():"not set for this location"));
   }

   private void printChildLocations(BudgetDetailsForUserLevel4ELO childElo) {
      boolean first = true;
      Iterator iter4 = childElo.iterator();
      if(iter4.hasNext()) {
         System.out.println("\t               \t\tchild locations (count=" + childElo.size() + ")");
      }

      while(iter4.hasNext()) {
         childElo = (BudgetDetailsForUserLevel4ELO)iter4.next();
         System.out.println("\t\t\t\t\t\t\t" + (childElo.getState() != null?" state: " + childElo.getState():"") + (childElo.getUserCount() > 0?" users: " + childElo.getUserCount():"") + " structureElementId: " + childElo.getStructureElementId() + " visId: " + childElo.getVisId());
      }

   }
}
