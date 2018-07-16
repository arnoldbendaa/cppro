package com.cedar.cp.ejb.base.async.formgenerate;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.dto.xmlform.AllXcellXmlFormsELO;
import com.cedar.cp.dto.xmlform.GenerateFlatFormsTaskRequest;
import com.cedar.cp.dto.xmlform.XmlFormEditorSessionSSO;
import com.cedar.cp.dto.xmlform.XmlFormImpl;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.ejb.api.base.ListSessionServer;
import com.cedar.cp.ejb.api.xmlform.XmlFormEditorSessionServer;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.flatform.model.Properties;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.flatform.reader.XMLReader;

public class GenerateFlatFormsTask extends AbstractTask {

    private static final long serialVersionUID = 8558624934244871044L;
    private transient InitialContext mInitialContext;
    private transient Log mLog;

    public GenerateFlatFormsTask() {
        mLog = new Log(getClass());
    }

    public int getReportType() {
        return 5;
    }

    public String getEntityName() {
        return "GenerateXcellFormsTask";
    }

    public void runUnitOfWork(InitialContext initialContext) {
        this.mInitialContext = initialContext;

        if (!(this.getRequest() instanceof GenerateFlatFormsTaskRequest)) {
            this.logInfo("Wrong type of request");
            return;
        }

        this.log("Starting GenerateXcellFormsTask");
        setCheckpoint(new GenerateCheckpoint());

        CPConnection cpConnection = getCPConnection();
        ListSessionServer listSessionServer = new ListSessionServer(cpConnection);
        AllXcellXmlFormsELO flatForms = listSessionServer.getAllXcellXmlFormsForLoggedUser();

        XmlFormEditorSessionServer server = new XmlFormEditorSessionServer(cpConnection);
        GenerateFlatFormsTaskRequest request = (GenerateFlatFormsTaskRequest) this.getRequest();

        byte[] excelFile = request.getExcelFile();
        String jsonForm = null;
        this.log("-- Convertion Excel File to JSON has been started.");
        try {
            jsonForm = toJsonForm(excelFile, cpConnection);
            this.log("-- Convertion Excel File to JSON has been finished.");
        } catch (ValidationException e) {
            this.log("-- Convertion Excel File to JSON has been failed.");
        }
        if (jsonForm == null) {
            mLog.debug("runUnitOfWork", "Ending  task");
            setCheckpoint(null);
            return;
        }

        this.log("-- Process of insert/update flat form has been started.");
        Map<Integer, String> visIds = request.getVisIds();
        Map<Integer, String> definitions = request.getDefinitions();
        List<UserRefImpl> userIds = request.getUserIds();

        Iterator<Integer> iterator = visIds.keySet().iterator();
        while (iterator.hasNext()) {
            int financeCubeId = iterator.next();
            String visId = visIds.get(financeCubeId);
            String newDefinition = definitions.get(financeCubeId);

            int flatFormId = getFlatFormId(flatForms, visId);
            XmlFormImpl xmlFormImpl = null;
            try {
                xmlFormImpl = getXmlFormFromServer(server, flatFormId);
            } catch (Exception e) {
                this.log("-- Flat form with name = [" + visId + "] hasn't been found.");
            }
            if (xmlFormImpl != null) {
                if (flatFormId == -1) { // insert
                    
                    xmlFormImpl.setUserList(userIds);
                    xmlFormImpl.setType(6);
                    xmlFormImpl.setVisId(visId);
                    xmlFormImpl.setDescription(request.getDescription());
                    xmlFormImpl.setFinanceCubeId(financeCubeId);
                    xmlFormImpl.setJsonForm(jsonForm);
                    xmlFormImpl.setDefinition(newDefinition);
                    xmlFormImpl.setExcelFile(excelFile);
                    this.log("----- Inserting flat form with name = [" + visId + "].");
                    try {
                        server.insert(xmlFormImpl);
                    } catch (Exception e) {
                        this.log("----- Inserting flat form with name = [" + visId + "] has failed.");
                    }
                } else if (flatFormId > -1 && request.isOverride()) { // update
                    if ((xmlFormImpl.getUserList() == null) || (xmlFormImpl.getUserList().size() == 0)){
                        xmlFormImpl.setUserList(userIds);
                    }
                    xmlFormImpl.setJsonForm(jsonForm);
                    
                    // Don`t override exclude data types if exist
                    try {
                        String oldDefinition = xmlFormImpl.getDefinition();
                        Workbook oldWorkbook = loadWorkbookFromXML(oldDefinition);
                        Properties oldWorkbookProperties = oldWorkbook.getProperties();
                        if (oldWorkbookProperties != null && !oldWorkbookProperties.isEmpty()) {
                            Workbook newWorkbook = loadWorkbookFromXML(newDefinition);
                            
                            String excludeDataTypes = oldWorkbookProperties.get(WorkbookProperties.EXCLUDE_DATA_TYPES.toString());
                            if (excludeDataTypes != null && !excludeDataTypes.isEmpty()) {
                                newWorkbook.setProperty(WorkbookProperties.EXCLUDE_DATA_TYPES.toString(), excludeDataTypes);
                            }
                            
                            String modelVisId = oldWorkbookProperties.get(WorkbookProperties.MODEL_VISID.toString());
                            if (modelVisId != null && !modelVisId.isEmpty()) {
                                newWorkbook.setProperty(WorkbookProperties.MODEL_VISID.toString(), modelVisId);
                            }
                            
                            String invertNumberValue = oldWorkbookProperties.get(WorkbookProperties.INVERT_NUMBERS_VALUE.toString());
                            if (invertNumberValue != null && !invertNumberValue.isEmpty()) {
                                newWorkbook.setProperty(WorkbookProperties.INVERT_NUMBERS_VALUE.toString(), invertNumberValue);
                            }
                            
                            newDefinition = writeXML(newWorkbook);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    
                    xmlFormImpl.setDefinition(newDefinition);
                    xmlFormImpl.setExcelFile(excelFile);
                    this.log("----- Updating flat form with name = [" + visId + "].");
                    try {
                        server.update(xmlFormImpl);
                    } catch (Exception e) {
                        this.log("----- Updating flat form with name = [" + visId + "] has failed.");
                    }
                }
            }
        }
        this.log("-- Process of insert/update flat form has been finished.");

        mLog.debug("runUnitOfWork", "Ending  task");
        setCheckpoint(null);
    }
    
    private com.cedar.cp.util.flatform.model.Workbook loadWorkbookFromXML(String xml) throws Exception {
        XMLReader reader = new XMLReader();
        reader.init();
        StringReader sr = new StringReader(xml);
        reader.parseConfigFile(sr);
        return reader.getWorkbook();
     }
    
    /**
     * Transforms Workbook object to string
     * 
     * @param workbook
     * @return
     */
    private String writeXML(Workbook workbook) {
        try {
            StringWriter sw = new StringWriter();
            workbook.writeXml(sw);
            return sw.toString();
        } catch (IOException var11) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private int getFlatFormId(AllXcellXmlFormsELO elo, String visId) {
        int xmlFormId = -1;
        Iterator<AllXcellXmlFormsELO> it = elo.iterator();
        while (it.hasNext()) {
            AllXcellXmlFormsELO row = it.next();
            XmlFormRef ref = row.getXmlFormEntityRef();
            String narrative = ref.getNarrative();
            if (narrative.equals(visId)) {
                xmlFormId = ((XmlFormPK) ref.getPrimaryKey()).getXmlFormId();
                return xmlFormId;
            }
        }
        return xmlFormId;
    }

    public XmlFormImpl getXmlFormFromServer(XmlFormEditorSessionServer server, int flatFormId) throws CPException, ValidationException {
        XmlFormEditorSessionSSO sso = null;

        if (flatFormId != -1) {
            XmlFormPK xmlFormPK = new XmlFormPK(flatFormId);
            sso = server.getItemData(xmlFormPK);
        } else {
            sso = server.getNewItemData();
        }
        XmlFormImpl xmlFormImpl = sso.getEditorData();
        return xmlFormImpl;
    }

    private String toJsonForm(byte[] file, CPConnection cpConnection) throws ValidationException {
        return cpConnection.getExcelIOProcess().convertXlsToJson(file, "");
    }

    public InitialContext getInitialContext() {
        return this.mInitialContext;
    }

    public void setInitialContext(InitialContext initialContext) throws Exception {
        if (initialContext == null) {
            this.mInitialContext = new InitialContext();
        } else {
            this.mInitialContext = initialContext;
        }
    }

    public boolean mustComplete() {
        return false;
    }

    public GenerateCheckpoint getCheckpoint() {
        return (GenerateCheckpoint) super.getCheckpoint();
    }

    static class GenerateCheckpoint extends AbstractTaskCheckpoint {
        private static final long serialVersionUID = -1966101416452885297L;

        public List<String> toDisplay() {
            return Arrays.asList(new String[] { "GenerateFlatFormsTask Checkpoint number:" + getCheckpointNumber() });
        }
    }
}