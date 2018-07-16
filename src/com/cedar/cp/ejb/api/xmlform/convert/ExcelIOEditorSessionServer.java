package com.cedar.cp.ejb.api.xmlform.convert;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.api.base.AbstractSession;
import com.cedar.cp.ejb.impl.xmlform.convert.ExcelIOEditorSessionSEJB;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.Timer;

import java.io.InputStream;
import java.net.URI;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.naming.Context;

public class ExcelIOEditorSessionServer extends AbstractSession {

    private static final String REMOTE_JNDI_NAME = "ejb/ExcelIOEditorSessionRemoteHome";
    private static final String LOCAL_JNDI_NAME = "ejb/ExcelIOEditorSessionLocalHome";
    protected ExcelIOEditorSessionSEJB mRemote;
    protected ExcelIOEditorSessionSEJB mLocal;
    private Log mLog = new Log(this.getClass());

    public ExcelIOEditorSessionServer(CPConnection conn_) {
        super(conn_);
    }

    public ExcelIOEditorSessionServer(Context context_, boolean remote) {
        super(context_, remote);
    }

    private ExcelIOEditorSessionSEJB getRemote() throws CreateException, RemoteException, CPException {
        if (this.mRemote == null) {
            
            this.mRemote = new ExcelIOEditorSessionSEJB();
        }

        return this.mRemote;
    }

    private ExcelIOEditorSessionSEJB getLocal() throws CPException {
        if (this.mLocal == null) {
            this.mLocal = new ExcelIOEditorSessionSEJB();
        }

        return this.mLocal;
    }

    public int convertAllXlsToJsonTask() throws CPException, ValidationException {
        Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

        try {
            int taskId;
            if (this.isRemoteConnection()) {
                taskId = this.getRemote().convertAllXlsToJsonTask(getConnection().getUserContext().getUserId());
            } else {
                taskId = this.getLocal().convertAllXlsToJsonTask(getConnection().getUserContext().getUserId());
            }

            if (timer != null) {
                timer.logDebug("convertAllXlsToJsonTask");
            }

            return taskId;
        } catch (Exception e) {
            throw this.unravelException(e);
        }
    }

    public String convertXlsToJson(byte[] bytes, String password, Boolean... flags) throws CPException, ValidationException {
        Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

        try {
            String json;
            if (this.isRemoteConnection()) {
                json = this.getRemote().convertXlsToJson(new URI(this.getConnection().getSystemPropertysProcess().getSystemProperty("WEB: ExcelIO Service URL").getValueAt(0, "Value").toString()), bytes, password, flags);
            } else {
                json = this.getLocal().convertXlsToJson(new URI(this.getConnection().getSystemPropertysProcess().getSystemProperty("WEB: ExcelIO Service URL").getValueAt(0, "Value").toString()), bytes, password, flags);
            }

            if (timer != null) {
                timer.logDebug("convertXlsToJson");
            }
            return json;
        } catch (Exception e) {
            throw this.unravelException(e);
        }
    }

    public InputStream convertJsonToXls(String json, String password, Boolean... flags) throws CPException, ValidationException {
        Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

        try {
            InputStream xls;
            if (this.isRemoteConnection()) {
                xls = this.getRemote().convertJsonToXls(new URI(this.getConnection().getSystemPropertysProcess().getSystemProperty("WEB: ExcelIO Service URL").getValueAt(0, "Value").toString()), json, password, flags);
            } else {
                xls = this.getLocal().convertJsonToXls(new URI(this.getConnection().getSystemPropertysProcess().getSystemProperty("WEB: ExcelIO Service URL").getValueAt(0, "Value").toString()), json, password, flags);
            }

            if (timer != null) {
                timer.logDebug("convertXlsToJson");
            }
            return xls;
        } catch (Exception e) {
            throw this.unravelException(e);
        }
    }

    public InputStream convertJsonToXlsx(String json, String password, Boolean... flags) throws CPException, ValidationException {
        Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;

        try {
            InputStream xls;
            if (this.isRemoteConnection()) {
                xls = this.getRemote().convertJsonToXlsx(new URI(this.getConnection().getSystemPropertysProcess().getSystemProperty("WEB: ExcelIO Service URL").getValueAt(0, "Value").toString()), json, password, flags);
            } else {
                xls = this.getLocal().convertJsonToXlsx(new URI(this.getConnection().getSystemPropertysProcess().getSystemProperty("WEB: ExcelIO Service URL").getValueAt(0, "Value").toString()), json, password, flags);
            }

            if (timer != null) {
                timer.logDebug("convertJsonToXlsx");
            }
            return xls;
        } catch (Exception e) {
            throw this.unravelException(e);
        }
    }

    public String getRemoteJNDIName() {
        return "ejb/ExcelIOEditorSessionRemoteHome";
    }

    public String getLocalJNDIName() {
        return "ejb/ExcelIOEditorSessionLocalHome";
    }

    public EntityList getXcellForms() throws CPException {
        Timer timer = this.mLog.isDebugEnabled() ? new Timer(this.mLog) : null;
        EntityList ret = this.getConnection().getListHelper().getAllXcellXmlForms();
        if (timer != null) {
            timer.logDebug("getXcellForms", "");
        }
        return ret;
    }
}
