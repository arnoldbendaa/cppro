package com.cedar.cp.ejb.api.budgetlocation;

import java.util.List;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityEditorSessionCSO;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityEditorSessionSSO;

public interface UserModelSecurityEditorSessionLocal extends EJBLocalObject {

    void update(UserModelSecurityEditorSessionCSO paramUserModelSecurityEditorSessionCSO) throws ValidationException, EJBException;

    void doImport(List<String[]> paramList) throws ValidationException, EJBException;

    UserModelSecurityEditorSessionSSO getItemData(Object paramObject) throws ValidationException;
}