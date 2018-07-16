package com.cedar.cp.ejb.api.user.loggedinusers;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersEditorSessionSSO;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersEditorSessionCSO;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface LoggedInUsersEditorSessionLocal extends EJBLocalObject {

	LoggedInUsersEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

	LoggedInUsersEditorSessionSSO getNewItemData(int var1) throws EJBException;

	LoggedInUsersPK insert(LoggedInUsersEditorSessionCSO var1) throws ValidationException, EJBException;

	LoggedInUsersPK copy(LoggedInUsersEditorSessionCSO var1) throws ValidationException, EJBException;

	void update(LoggedInUsersEditorSessionCSO var1) throws ValidationException, EJBException;

	void delete(int var1, Object var2) throws ValidationException, EJBException;
}
