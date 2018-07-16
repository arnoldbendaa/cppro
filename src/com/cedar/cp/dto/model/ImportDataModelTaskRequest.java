package com.cedar.cp.dto.model;

import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;

public class ImportDataModelTaskRequest extends AbstractTaskRequest implements TaskRequest {

    private static final long serialVersionUID = 2738631961506041770L;
    private List<String> mMessages;

    private int mUserId;
    private int mGlobalModelId;
    private List<ModelRef> mModels;
    private List<DataTypeRef> mDataTypes;

    public ImportDataModelTaskRequest(List<ModelRef> models, List<DataTypeRef> dataTypes, int globalModelId, int userId) {
        this.mMessages = new ArrayList<String>();
        this.mMessages.add("Processing ImportDataModelTask...");

        this.mUserId = userId;
        this.mGlobalModelId = globalModelId;
        this.mModels = models;
        this.mDataTypes = dataTypes;
    }

    public List<String> toDisplay() {
        return this.mMessages;
    }

    public String getService() {
        return "com.cedar.cp.ejb.base.async.model.ImportDataModelTask";
    }

    public int getGlobalModelId() {
        return mGlobalModelId;
    }

    public int getUserId() {
        return mUserId;
    }

    public List<ModelRef> getModels() {
        return mModels;
    }

    public List<DataTypeRef> getDataTypes() {
        return mDataTypes;
    }

}
