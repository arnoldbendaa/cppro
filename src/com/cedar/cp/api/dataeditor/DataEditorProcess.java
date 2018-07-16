package com.cedar.cp.api.dataeditor;

import java.util.List;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;

public interface DataEditorProcess extends BusinessProcess {

	List<Object[]> getDataEditorData(List<Integer> fcIds, Object[] costCenters, Object[] expenseCodes, List<String> dataTypes, int fromYear, int fromPeriod, int toYear, int toPeriod) throws ValidationException;
	EntityList getAllFinanceCubesForLoggedUser();
	EntityList getHierarchiesForModel(int modelId);
	void saveData(String xml, int userId) throws CPException, ValidationException;
}