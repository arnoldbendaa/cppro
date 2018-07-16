package com.cedar.cp.ejb.api.dataeditor;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.api.dataeditor.DataEditorEditorSessionHome;
import com.cedar.cp.ejb.api.dataeditor.DataEditorEditorSessionLocal;
import com.cedar.cp.ejb.api.dataeditor.DataEditorEditorSessionLocalHome;
import com.cedar.cp.ejb.api.dataeditor.DataEditorEditorSessionRemote;
import com.cedar.cp.ejb.impl.dataeditor.DataEditorEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

import java.rmi.RemoteException;
import java.util.List;

import javax.ejb.CreateException;
import javax.naming.Context;

public class DataEditorEditorSessionServer extends AbstractSession {

    private static final String REMOTE_JNDI_NAME = "ejb/DataEditorEditorSessionRemoteHome";
    private static final String LOCAL_JNDI_NAME = "ejb/DataEditorEditorSessionLocalHome";
    protected DataEditorEditorSessionRemote mRemote;
    protected DataEditorEditorSessionLocal mLocal;
    private Log mLog = new Log(this.getClass());
    public DataEditorEditorSessionSEJB server = new DataEditorEditorSessionSEJB();

    public DataEditorEditorSessionServer(CPConnection conn_) {
        super(conn_);
    }

    public DataEditorEditorSessionServer(Context context_, boolean remote) {
        super(context_, remote);
    }

    private DataEditorEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
//        if (this.mRemote == null) {
//            String jndiName = this.getRemoteJNDIName();
//
//            try {
//                DataEditorEditorSessionHome e = (DataEditorEditorSessionHome) this.getHome(jndiName, DataEditorEditorSessionHome.class);
//                this.mRemote = e.create();
//            } catch (CreateException var3) {
//                this.removeFromCache(jndiName);
//                var3.printStackTrace();
//                throw new CPException("getRemote " + jndiName + " CreateException", var3);
//            } catch (RemoteException var4) {
//                this.removeFromCache(jndiName);
//                var4.printStackTrace();
//                throw new CPException("getRemote " + jndiName + " RemoteException", var4);
//            }
//        }
//
//        return this.mRemote;
    	return this.server;
    }

    private DataEditorEditorSessionSEJB getLocal() throws CPException {
//        if (this.mLocal == null) {
//            try {
//                DataEditorEditorSessionLocalHome e = (DataEditorEditorSessionLocalHome) this.getLocalHome(this.getLocalJNDIName());
//                this.mLocal = e.create();
//            } catch (CreateException var2) {
//                throw new CPException("can\'t create local session for " + this.getLocalJNDIName(), var2);
//            }
//        }
//
//        return this.mLocal;
    	return this.server;
    }

    public void removeSession() throws CPException {
    }

    public List<Object[]> getDataEditorData(List<Integer> fcIds, Object[] costCenters, Object[] expenseCodes, List<String> dataTypes, int fromYear, int fromPeriod, int toYear, int toPeriod) throws ValidationException, CPException {
        Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

        try {
            List<Object[]> e = null;
            if (this.isRemoteConnection()) {
                e = this.getRemote().getDataEditorData(fcIds, costCenters, expenseCodes, dataTypes, fromYear, fromPeriod, toYear, toPeriod);
            } else {
                e = this.getLocal().getDataEditorData(fcIds, costCenters, expenseCodes, dataTypes, fromYear, fromPeriod, toYear, toPeriod);
            }

            if (timer != null) {
                timer.logDebug("getDataEditorData", "");
            }

            return e;
        } catch (Exception var3) {
            throw this.unravelException(var3);
        }
    }

    public void saveData(String xml, int userId) throws CPException, ValidationException {
        Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

        try {
            if (this.isRemoteConnection()) {
                this.getRemote().saveData(xml, userId);
            } else {
                this.getLocal().saveData(xml, userId);
            }
            if (timer != null) {
                timer.logDebug("saveData", "");
            }
        } catch (Exception var3) {
            throw this.unravelException(var3);
        }
    }

    public String getRemoteJNDIName() {
        return "ejb/DataEditorEditorSessionRemoteHome";
    }

    public String getLocalJNDIName() {
        return "ejb/DataEditorEditorSessionLocalHome";
    }
}
