package com.cedar.cp.utc.struts.admin;

import java.io.IOException;
import java.sql.Date;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
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

public class RoadMapMenu extends Action {
    
    public class revisionsComparator implements Comparator<RoadMapDTO> {
        @Override
        public int compare(RoadMapDTO object1, RoadMapDTO object2) {
            return object2.getRevision().compareTo(object1.getRevision());
        }
    }
    
    
    public ActionForward execute(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        
        HttpSession session = request.getSession();
        CPContext context = (CPContext)session.getAttribute("cpContext");
        CPConnection conn = context.getCPConnection();
        EntityList revisionEntityList = conn.getListHelper().getAllRevisions();
        int size = revisionEntityList.getNumRows();
        
        RoadMapForm roadMapForm = (RoadMapForm) form;
        for(int i = 0; i < size; i++) {
            Integer id = (Integer)revisionEntityList.getValueAt(i, "id");
            Integer revision = (Integer)revisionEntityList.getValueAt(i, "revision");
            Date vDate = (Date)revisionEntityList.getValueAt(i, "version_date");
            String desc = (String)revisionEntityList.getValueAt(i, "description");
            RoadMapDTO tmp = new RoadMapDTO(id, revision, vDate, desc);
            roadMapForm.addRoadMap(tmp);
        }
        Collections.sort(roadMapForm.getRoadMapElements(), new revisionsComparator());
        
        try{
            Cookie cookie = new Cookie("lastRevision", roadMapForm.getRoadMapElements().get(0).getRevision().toString());
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (NullPointerException e) {}
        
        
        return mapping.findForward("success");
        
    }
}
