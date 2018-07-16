package com.cedar.cp.impl.xmlform.convert;

import java.io.InputStream;
import java.net.URI;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlform.convert.ExcelIOEditorSession;
import com.cedar.cp.api.xmlform.convert.ExcelIOProcess;
import com.cedar.cp.ejb.api.xmlform.convert.ExcelIOEditorSessionServer;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.util.Log;

public class ExcelIOProcessImpl extends BusinessProcessImpl implements ExcelIOProcess {

    private Log mLog = new Log(this.getClass());

    public ExcelIOProcessImpl(CPConnection connection) {
        super(connection);
    }

    public ExcelIOEditorSession getExcelIOEditorSession(Object key) throws ValidationException {
        ExcelIOEditorSessionImpl sess = new ExcelIOEditorSessionImpl(this, key);
        this.mActiveSessions.add(sess);
        return sess;
    }

    public String getProcessName() {
        String ret = "Processing ExcelIO";
        return ret;
    }

    protected int getProcessID() {
        return 109;
    }

    public int convertAllXlsToJsonTask() throws ValidationException {
        ExcelIOEditorSessionServer server = new ExcelIOEditorSessionServer(this.getConnection());
        return server.convertAllXlsToJsonTask();
    }

    @Override
    public String convertXlsToJson(byte[] bytes, String password, Boolean... flags) throws ValidationException {
        return new ExcelIOEditorSessionServer(this.getConnection()).convertXlsToJson(bytes, password, flags);
    }

    @Override
    public InputStream convertJsonToXls(String json, String password, Boolean... flags) throws ValidationException {
        return new ExcelIOEditorSessionServer(this.getConnection()).convertJsonToXls(json, password, flags);
    }

    @Override
    public InputStream convertJsonToXlsx(String json, String password, Boolean... flags) throws ValidationException {
        return new ExcelIOEditorSessionServer(this.getConnection()).convertJsonToXlsx(json, password, flags);
    }
}
