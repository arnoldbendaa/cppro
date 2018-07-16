package com.cedar.cp.utc.struts.admin;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.utc.common.CPContext;
import com.softproideas.common.models.FormDashboardDTO;

public class DashboardMenu extends Action {

    public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        CPContext context = (CPContext) session.getAttribute("cpContext");
        CPConnection conn = context.getCPConnection();
        Set<String> roles = context.getUserContext().getUserRoles();

        Boolean isAdmin = context.isSystemAdministrator();
        EntityList dashboardForms = conn.getListHelper().getDashboardForms(context.getIntUserId(), isAdmin);
        int size = dashboardForms.getNumRows();

        DashboardForm dashboardForm = (DashboardForm) form;

        dashboardForm.setAuctionAccess(roles.contains("Auction Open") ? true : false);
        dashboardForm.setFreeFormAccess(roles.contains("Dashboard Free Form Open") ? true : false);
        dashboardForm.setHierarchyAccess(roles.contains("Dashboard Hierarchy Open") ? true : false);
        FormDashboardDTO empty = new FormDashboardDTO();
        dashboardForm.addDashboard(empty);
        for (int i = 0; i < size; i++) {
            String uuid = (String) dashboardForms.getValueAt(i, "dashboard_uuid");
            String title = (String) dashboardForms.getValueAt(i, "dashboard_title");
            String type = (String) dashboardForms.getValueAt(i, "dashboard_type");
            FormDashboardDTO tmp = new FormDashboardDTO();
            tmp.setsUUID(uuid);
            tmp.setDashboardName(title);
            tmp.setDashboardType(type);
            dashboardForm.addDashboard(tmp);
        }

        return mapping.findForward("success");

    }

}
