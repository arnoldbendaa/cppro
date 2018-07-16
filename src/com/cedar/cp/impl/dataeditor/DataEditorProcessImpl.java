package com.cedar.cp.impl.dataeditor;

import java.util.List;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataeditor.DataEditorProcess;
import com.cedar.cp.ejb.api.dataeditor.DataEditorEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;

public class DataEditorProcessImpl extends BusinessProcessImpl implements DataEditorProcess {

    private Log mLog = new Log(getClass());

    public DataEditorProcessImpl(CPConnection connection) {
        super(connection);
    }

    public List<Object[]> getDataEditorData(List<Integer> fcIds, Object[] costCenters, Object[] expenseCodes, List<String> dataTypes, int fromYear, int fromPeriod, int toYear, int toPeriod) throws ValidationException {
        return (new DataEditorEditorSessionServer(this.getConnection())).getDataEditorData(fcIds, costCenters, expenseCodes, dataTypes, fromYear, fromPeriod, toYear, toPeriod);
    }

    public EntityList getAllFinanceCubesForLoggedUser() {
        try {
            return getConnection().getListHelper().getAllFinanceCubesForLoggedUser();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("can't get AllFinanceCubesForLoggedUser", e);
        }
    }

    public void saveData(String xml, int userId) throws CPException, ValidationException {
        (new DataEditorEditorSessionServer(this.getConnection())).saveData(xml, userId);
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
        String ret = "Processing Data Editor";
        return ret;
    }

    protected int getProcessID() {
        return 108;
    }
}