package com.cedar.cp.api.budgetlocation;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;

public interface UserModelSecurityProcess extends BusinessProcess {

	UserModelSecurityEditorSession getUserModelSecurityEditorSession(Object paramObject) throws ValidationException;

    EntityList getUserModelSecurity();
}