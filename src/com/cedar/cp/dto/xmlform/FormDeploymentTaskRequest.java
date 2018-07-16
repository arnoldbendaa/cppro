// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform;

import com.cedar.cp.api.task.TaskRequest;
import com.cedar.cp.api.xmlform.FormDeploymentData;
import com.cedar.cp.dto.base.AbstractTaskRequest;
import com.softproideas.common.models.FormDeploymentDataDTO;

import java.util.ArrayList;
import java.util.List;

public class FormDeploymentTaskRequest extends AbstractTaskRequest implements TaskRequest {

    private int mXmlFormId;
    private FormDeploymentData mData;
    private FormDeploymentDataDTO dto;

    public List toDisplay() {
        ArrayList l = new ArrayList();
        l.add("form id=" + this.getXmlFormId());
        //l.add("deployemt id=" + this.mData.getIdentifier());
        return l;
    }

    public String getService() {
        return "com.cedar.cp.ejb.base.async.formdeployment.DeploymentTask";
    }

    public int getXmlFormId() {
        return this.mXmlFormId;
    }

    public void setXmlFormId(int xmlFormId) {
        this.mXmlFormId = xmlFormId;
    }

    public FormDeploymentData getData() {
        return this.mData;
    }

    public void setData(FormDeploymentData data) {
        this.mData = data;
    }

    public FormDeploymentDataDTO getDto() {
        return dto;
    }

    public void setDto(FormDeploymentDataDTO dto) {
        this.dto = dto;
    }
}
