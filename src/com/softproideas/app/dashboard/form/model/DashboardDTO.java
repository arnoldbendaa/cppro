/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
package com.softproideas.app.dashboard.form.model;

import java.util.ArrayList;
import java.util.UUID;

import com.softproideas.app.dashboard.form.DashboardType;

public class DashboardDTO {
    private UUID dashboardUUID;
    private String dashboardTitle;
    private int formId;
    private ArrayList<DashboardTab> tabs;
    private DashboardType type;
	private Integer modelId;
    private ContextData contextData;
    
    public UUID getDashboardUUID() {
        return dashboardUUID;
    }
    public void setDashboardUUID(UUID dashboardUUID) {
        this.dashboardUUID = dashboardUUID;
    }
    public int getFormId() {
        return formId;
    }
    public void setFormId(int formId) {
        this.formId = formId;
    }
    public DashboardType getType() {
        return type;
    }
    public void setType(String type) {
        if (type.equals("FREEFORM")){
            this.type = DashboardType.FREEFORM;
        }
        if (type.equals("HIERARCHY")){
            this.type = DashboardType.HIERARCHY;
        }
    }
    public String getDashboardTitle() {
        return dashboardTitle;
    }
    public void setDashboardTitle(String dashboardTitle) {
        this.dashboardTitle = dashboardTitle;
    }
   
    public ArrayList<DashboardTab> getTabs() {
        return tabs;
    }
    public void setTabs(ArrayList<DashboardTab> tabs) {
        this.tabs = tabs;
    }
	public ContextData getContextData() {
		return contextData;
	}
	public void setContextData(ContextData contextData) {
		this.contextData = contextData;
	}
	public Integer getModelId() {
		return modelId;
	}
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
}
