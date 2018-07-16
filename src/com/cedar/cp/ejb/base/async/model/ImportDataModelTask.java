package com.cedar.cp.ejb.base.async.model;

import java.util.Arrays;
import java.util.List;

import javax.naming.InitialContext;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.ImportDataModelTaskRequest;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.base.async.AbstractTask;
import com.cedar.cp.ejb.base.async.AbstractTaskCheckpoint;
import com.cedar.cp.ejb.impl.model.ModelDAO;
import com.cedar.cp.util.Log;

public class ImportDataModelTask extends AbstractTask {

    private static final long serialVersionUID = -1899557923767606205L;
    private transient InitialContext mInitialContext;
    private transient Log mLog;
    protected CPConnection mConnection;

    public ImportDataModelTask() {
        mLog = new Log(getClass());
    }

    public int getReportType() {
        return 5;
    }

    public ImportDataModelCheckpoint getCheckpoint() {
        return (ImportDataModelCheckpoint) super.getCheckpoint();
    }

    public String getEntityName() {
        return "ImportDataModelTask";
    }

    public void runUnitOfWork(InitialContext initialContext) throws Exception {
        this.mInitialContext = initialContext;

        if (!(this.getRequest() instanceof ImportDataModelTaskRequest)) {
            return;
        }

        mLog.debug("runUnitOfWork", "Starting ImportDataModelTask");
        setCheckpoint(new ImportDataModelCheckpoint());

        ImportDataModelTaskRequest request = (ImportDataModelTaskRequest) this.getRequest();
        int globalModelId = request.getGlobalModelId();
        List<ModelRef> models = request.getModels();
        List<DataTypeRef> dataTypes = request.getDataTypes();

        int numModels = models.size();
        int numDataTypes = dataTypes.size();
        float allElements = numModels * numDataTypes;
        float actualElement = 1;

        logAllModelsImport(models.toString(), dataTypes.toString());

        for (ModelRef modelRef: models) {
            int modelId = ((ModelPK) modelRef.getPrimaryKey()).getModelId();
            logModelImport(modelRef.getDisplayText());

            for (DataTypeRef dataTypeRef: dataTypes) {
                String dataType = dataTypeRef.getNarrative();
                logDataTypeImport(dataType, actualElement / allElements);

                // Import data
                importData(globalModelId, modelId, dataType);

                actualElement = actualElement + 1;
            }
        }

        this.log(" ");
        this.log(" ");
        mLog.debug("runUnitOfWork", "Ending  task");
        setCheckpoint(null);
    }

    /**
     * Import data by call procedure on database.
     * 
     * @param globalModelId
     *            target model
     * @param modelId
     *            source model
     * @param dataType
     *            specified data type
     */
    private void importData(int globalModelId, int modelId, String dataType) {
        ModelDAO dao = new ModelDAO();
        dao.importDataModel(globalModelId, modelId, dataType);
    }

    public InitialContext getInitialContext() {
        return this.mInitialContext;
    }

    public boolean mustComplete() {
        return false;
    }

    static class ImportDataModelCheckpoint extends AbstractTaskCheckpoint {
        public List toDisplay() {
            return Arrays.asList(new String[] { "ImportDataModel Task Checkpoint number:" + getCheckpointNumber() });
        }
    }

    private void logAllModelsImport(String models, String dataTypes) {
        this.log(" ");
        this.log(" - - - - - Start import data models - - - - - ");
        this.log(" All models = " + models);
        this.log(" All data types = " + dataTypes);
        this.log(" ");
    }

    private void logModelImport(String model) {
        this.log(" ");
        this.log("  - - - - - Import model \"" + model + "\" - - - - - ");
        this.log(" ");
    }

    private void logDataTypeImport(String dataType, float percent) {
        this.log(" [" + String.format("%.02f", percent * 100) + "%] Import data with data type \"" + dataType + "\"");
    }
}