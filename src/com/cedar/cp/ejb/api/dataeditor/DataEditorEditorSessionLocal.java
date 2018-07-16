package com.cedar.cp.ejb.api.dataeditor;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;

public interface DataEditorEditorSessionLocal extends EJBLocalObject {

    List<Object[]> getDataEditorData(List<Integer> fcIds, Object[] costCenters, Object[] expenseCodes, List<String> dataTypes, int fromYear, int fromPeriod, int toYear, int toPeriod) throws ValidationException, EJBException;

    void saveData(String xml, int userId) throws ValidationException, CPException, EJBException;

}
