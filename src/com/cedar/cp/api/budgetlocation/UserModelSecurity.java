package com.cedar.cp.api.budgetlocation;

import com.cedar.cp.api.base.EntityList;
import java.util.List;

public interface UserModelSecurity {
	
  List<UserModelElementAssignment> getUserModelElementAccess();
  
  EntityList getModelsAndRAHierarchies();
}