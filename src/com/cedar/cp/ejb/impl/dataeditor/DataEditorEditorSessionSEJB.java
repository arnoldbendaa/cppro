package com.cedar.cp.ejb.impl.dataeditor;

import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.SessionContext;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataEntry.DataEntryProcess;
import com.cedar.cp.ejb.base.common.cache.DAGContext;
import com.cedar.cp.ejb.impl.base.AbstractSession;
import com.cedar.cp.ejb.impl.dataentry.DataEntryDAO;

public class DataEditorEditorSessionSEJB extends AbstractSession {

	private SessionContext mSessionContext;
	private DAGContext mDAGContext;


	public DataEditorEditorSessionSEJB() {
		try {
			mDAGContext = new DAGContext(getInitialContext());
		} catch (Exception e) {
			throw new EJBException(e);
		}
	}
	
	public List<Object[]> getDataEditorData(List<Integer> fcIds, Object[] costCenters, Object[] expenseCodes, List<String> dataTypes, int fromYear, int fromPeriod, int toYear, int toPeriod) {
		DataEntryDAO dao = new DataEntryDAO();
		return dao.getDataEditorData(fcIds, costCenters, expenseCodes, dataTypes, fromYear, fromPeriod, toYear, toPeriod);
	}
	
	public void saveData(String xml, int userId) throws ValidationException {
		setUserId(userId);
		DataEntryProcess dataEntryProcess = getCPConnection().getDataEntryProcess();	
		try {
			// Update cells
			dataEntryProcess.executeForegroundFlatFormUpdate(xml);
		} catch (CPException e) {
			e.printStackTrace();
		}
	}

	public void ejbCreate() throws EJBException {
	}

	public void ejbRemove() {
	}

	public void setSessionContext(SessionContext context) {
		mSessionContext = context;
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

}