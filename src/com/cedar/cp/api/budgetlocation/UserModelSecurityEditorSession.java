package com.cedar.cp.api.budgetlocation;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.ValidationException;
import java.util.List;

public interface UserModelSecurityEditorSession extends BusinessSession {
   
   UserModelSecurityEditor getUserModelSecurityEditor();

   void doImport(List<String[]> paramList) throws ValidationException;
}