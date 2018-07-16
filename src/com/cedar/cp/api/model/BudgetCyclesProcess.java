// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.api.model.BudgetCycleEditorSession;
import com.cedar.cp.api.model.ChangeBudgetStateResult;
import com.cedar.cp.api.model.OnDemandFinanceFormData;
import com.cedar.cp.api.model.ReviewBudgetDetails;
import com.cedar.cp.api.model.budgetlimit.BudgetLimitCheck;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.FinanceCubeInput;
import java.util.Map;

public interface BudgetCyclesProcess extends BusinessProcess {

   EntityList getAllBudgetCycles();

   EntityList getAllBudgetCyclesWeb();
   
   EntityList getAllBudgetCyclesWebForLoggedUser();

   EntityList getAllBudgetCyclesWebDetailed();

   EntityList getBudgetCyclesForModel(int var1);

   EntityList getBudgetCyclesForModelWithState(int var1, int var2);

   EntityList getBudgetCycleIntegrity();

   EntityList getBudgetCycleDetailedForId(int var1);
   
   EntityList getBudgetCycleXmlFormsForId(int var1,int userId);
   
   EntityList getBudgetTransferBudgetCycles();

   BudgetCycleEditorSession getBudgetCycleEditorSession(Object var1) throws ValidationException;

   ReviewBudgetDetails getReviewBudgetDetails(int userId, String levelPrefix, int modelId, int budgetCycleId, Map userSelections, String dataType, int formId, Map contextVariables) throws ValidationException;

   ReviewBudgetDetails getReviewBudgetDetails(int userId, String levelPrefix, int topNodeId, int modelId, int budgetCycleId, Object dataEntryProfilePrimaryKey, boolean hasDesignModeSecurity, Map selections, String dataType, Map contextVariables) throws ValidationException;
   
   ReviewBudgetDetails getReviewBudgetDetails(int userId, String levelPrefix, int topNodeId, int modelId, int budgetCycleId, Object dataEntryProfilePrimaryKey, boolean hasDesignModeSecurity, Map selections, String dataType, Map contextVariables, CPConnection conn) throws ValidationException;
   
   String[] getCalendarDetailsLabels(int[] ids) throws ValidationException;

   OnDemandFinanceFormData getFinanceFormDataRows(int var1, int var2, int var3, FinanceCubeInput var4, int var5, int[] var6, String var7, int var8, int[] var9, int var10, boolean var11, boolean var12, int[] var13, Map<String, DataType> var14, Map<Integer, EntityList> var15, CalendarInfo var16) throws ValidationException;

   ChangeBudgetStateResult setBudgetState(int var1, int var2, int var3, int var4, int var5, BudgetLimitCheck var6, Integer var7, Integer var8) throws ValidationException;
   
   void updateDataEntryProfileHistory(int userId, int modelId, int budgetCycleId, int structureElementId) throws ValidationException;
   
   EntityList getCycleStateHistory(int var1, int var2, int var3) throws ValidationException;

   int issueBudgetStateTask(int var1, int var2) throws ValidationException;

   int issueBudgetStateRebuildTask(EntityRef var1, int var2) throws ValidationException;
   
   void updatePeriods(int bcId, int periodId, int fromTo) throws ValidationException;
   
   void clearBudgetCycleServerCache(Object pk) throws ValidationException;
}
