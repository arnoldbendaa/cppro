package com.cedar.cp.api.model;

import java.util.List;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;

public interface BudgetUsersProcess extends BusinessProcess {

   EntityList getAllBudgetUsers();

   EntityList getCheckUserAccessToModel(int var1, int var2);

   EntityList getCheckUserAccess(int var1, int var2);

   EntityList getCheckUser(int var1);

   EntityList getBudgetUsersForNode(int var1, int var2);

   EntityList getNodesForUserAndCycle(int var1, int var2);

   EntityList getNodesForUserAndModel(int var1, int var2);

   EntityList getUsersForModelAndElement(int var1, int var2);

   EntityList getBudgetDetailsForUser(int var1);

   EntityList getBudgetDetailsForUser(int var1, int var2);

   EntityList getBudgetDetailsForUser(int var1, boolean var2, int var3, int var4);

   EntityList getBudgetUserDetails(int var1, int[] var2);

   EntityList getBudgetUserDetailsNodeDown(int var1, int var2, int var3);

   EntityList getBudgetUserAuthDetailsNodeUp(int var1, int var2, int var3);

   EntityList getPickerStartUpDetails(int var1, int[] var2, int var3);
   
   void copyAssignments(int paramInt, Object paramObject, List<Object> paramList) throws ValidationException;
   
   EntityList getUserModelSecurity();
}
