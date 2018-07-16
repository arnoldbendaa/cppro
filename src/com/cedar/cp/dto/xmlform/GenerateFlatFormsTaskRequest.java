package com.cedar.cp.dto.xmlform;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import com.cedar.cp.dto.user.UserRefImpl;

public class GenerateFlatFormsTaskRequest extends AbstractTaskRequest implements TaskRequest {

    private static final long serialVersionUID = 2380417003699059029L;

    private Map<Integer, String> visIds;
    private Map<Integer, String> definitions;
    private String description;
    private byte[] excelFile;
    private boolean override;
    private List<UserRefImpl> userIds;

    public GenerateFlatFormsTaskRequest() {

    }

    public List<String> toDisplay() {
        ArrayList<String> l = new ArrayList<String>();
        l.add("Generate FlatForms");
        return l;
    }

    public String getService() {
        return "com.cedar.cp.ejb.base.async.formgenerate.GenerateFlatFormsTask";
    }

    public Map<Integer, String> getVisIds() {
        return visIds;
    }

    public void setVisIds(Map<Integer, String> visIds) {
        this.visIds = visIds;
    }

    public Map<Integer, String> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(Map<Integer, String> definitions) {
        this.definitions = definitions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(byte[] excelFile) {
        this.excelFile = excelFile;
    }

    public boolean isOverride() {
        return override;
    }

    public void setOverride(boolean override) {
        this.override = override;
    }

    public List<UserRefImpl> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<UserRefImpl> userIds) {
        this.userIds = userIds;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

}
