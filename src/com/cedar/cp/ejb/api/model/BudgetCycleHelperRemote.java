// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.model;

import com.cedar.cp.api.base.BudgetStatusException;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.api.model.ChangeBudgetStateResult;
import com.cedar.cp.api.model.OnDemandFinanceFormData;
import com.cedar.cp.api.model.ReviewBudgetDetails;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitCheck;
import com.cedar.cp.dto.dimension.StructureElementELO;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.FinanceCubeInput;
import java.rmi.RemoteException;
import java.util.Map;
import javax.ejb.EJBObject;

public interface BudgetCycleHelperRemote extends EJBObject {

   ReviewBudgetDetails getReviewBudgetDetails(int var1, String var2, int var3, int var4, Map var5, String var6, int var7, Map var8) throws RemoteException;

   ReviewBudgetDetails getReviewBudgetDetails(int var1, String var2, int var3, int var4, int var5, Object var6, boolean var7, Map var8, String var9, Map var10) throws RemoteException;
   
   ReviewBudgetDetails getReviewBudgetDetails(int var1, String var2, int var3, int var4, int var5, Object var6, boolean var7, Map var8, String var9, Map var10, CPConnection conn) throws RemoteException;
   
   StructureElementELO[] getCalendarDetailsLabels(int[] selections) throws RemoteException;

   OnDemandFinanceFormData getFinanceFormDataRows(int var1, int var2, int var3, FinanceCubeInput var4, int var5, int[] var6, String var7, int var8, int[] var9, int var10, boolean var11, boolean var12, int[] var13, Map<String, DataType> var14, Map<Integer, EntityList> var15, CalendarInfo var16) throws RemoteException;

   ChangeBudgetStateResult setBudgetState(int var1, int var2, int var3, int var4, int var5, BudgetLimitCheck var6, Integer var7, Integer var8) throws BudgetStatusException, RemoteException;
   
   void updateDataEntryProfileHistory(int userId, int modelId, int budgetCycleId, int structureElementId) throws RemoteException;
   
   EntityList getCycleStateHistory(int var1, int var2, int var3) throws RemoteException;

   int issueBudgetStateTask(int var1, int var2, int var3) throws RemoteException;

   int issueBudgetStateRebuildTask(EntityRef var1, int var2) throws RemoteException;

   void updatePeriods(int bcId, int periodId, int fromTo) throws RemoteException;
}
