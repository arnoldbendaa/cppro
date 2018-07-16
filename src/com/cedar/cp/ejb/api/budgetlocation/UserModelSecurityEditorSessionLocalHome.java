package com.cedar.cp.ejb.api.budgetlocation;

import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;

public interface UserModelSecurityEditorSessionLocalHome extends EJBLocalHome {

	UserModelSecurityEditorSessionLocal create() throws CreateException;
}