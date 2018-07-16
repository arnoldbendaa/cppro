package com.cedar.cp.ejb.api.budgetlocation;

import java.rmi.RemoteException;
import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.EJBObject;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityEditorSessionCSO;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityEditorSessionSSO;

public interface UserModelSecurityEditorSessionRemote extends EJBObject {

	UserModelSecurityEditorSessionSSO getItemData(Object paramObject) throws ValidationException, RemoteException, EJBException;

	void update(UserModelSecurityEditorSessionCSO paramUserModelSecurityEditorSessionCSO) throws ValidationException, EJBException, RemoteException;

	void doImport(List<String[]> paramList) throws ValidationException, RemoteException, EJBException;
}