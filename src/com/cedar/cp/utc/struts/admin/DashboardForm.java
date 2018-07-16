package com.cedar.cp.utc.struts.admin;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.softproideas.common.models.FormDashboardDTO;

public class DashboardForm extends ActionForm {
    private boolean auctionAccess;
    private boolean hierarchyAccess;
    private boolean freeFormAccess;
    private ArrayList<FormDashboardDTO> dashboards;
    
    public ArrayList<FormDashboardDTO> getDashboards() {
        return dashboards;
    }

    public void setDashboards(ArrayList<FormDashboardDTO> dashboards) {
        this.dashboards = dashboards;
    }
    
    public void addDashboard(FormDashboardDTO dashboard) {
        if (dashboards == null) {
            dashboards = new ArrayList<FormDashboardDTO>();
        }
        dashboards.add(dashboard);
    }

    public boolean isAuctionAccess() {
        return auctionAccess;
    }

    public void setAuctionAccess(boolean auctionAccess) {
        this.auctionAccess = auctionAccess;
    }

    public boolean isHierarchyAccess() {
        return hierarchyAccess;
    }

    public void setHierarchyAccess(boolean hierarchyAccess) {
        this.hierarchyAccess = hierarchyAccess;
    }

    public boolean isFreeFormAccess() {
        return freeFormAccess;
    }

    public void setFreeFormAccess(boolean freeFormAccess) {
        this.freeFormAccess = freeFormAccess;
    }
}
