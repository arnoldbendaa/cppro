package com.cedar.cp.ejb.api.dataeditor;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.EJBObject;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;

public interface DataEditorEditorSessionRemote extends EJBObject {

    List<Object[]> getDataEditorData(List<Integer> fcIds, Object[] costCenters, Object[] expenseCodes, List<String> dataTypes, int fromYear, int fromPeriod, int toYear, int toPeriod) throws ValidationException, RemoteException;

    void saveData(String xml, int userId) throws ValidationException, CPException, RemoteException;

}
