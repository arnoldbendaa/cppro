package com.cedar.cp.impl.notes;

import java.util.ArrayList;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.notes.NotesEditorSession;
import com.cedar.cp.api.notes.NotesProcess;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;

public class NotesProcessImpl extends BusinessProcessImpl implements NotesProcess {

	private Log mLog = new Log(getClass());

	public NotesProcessImpl(CPConnection connection) {
		super(connection);
	}

	public void deleteObject(Object primaryKey) throws ValidationException {
	}

	public ArrayList<Object[]> getNotesForCostCenters(ArrayList<Integer> costCenters, int financeCubeId, String fromDate, String toDate) {
		try {
			return (ArrayList<Object[]>) getConnection().getListHelper().getNotesForCostCenters(costCenters, financeCubeId, fromDate, toDate);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("can't get getModelUserSecurity", e);
		}
	}
	
	public EntityList getAllFinanceCubesForLoggedUser() {
		try {
			return getConnection().getListHelper().getAllFinanceCubesForLoggedUser();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("can't get AllFinanceCubesForLoggedUser", e);
		}
	}
	
	public EntityList getHierarchiesForModel(int var1) {
		try {
			return getConnection().getListHelper().getHierarchiesForModel(var1);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("can't get HierarchiesForModel", e);
		}
	}


	public String getProcessName() {
		String ret = "Processing Note Search";
		return ret;
	}

	protected int getProcessID() {
		return 107;
	}
}