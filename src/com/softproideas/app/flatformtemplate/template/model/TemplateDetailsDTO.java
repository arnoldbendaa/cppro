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
package com.softproideas.app.flatformtemplate.template.model;

import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.softproideas.app.core.users.model.UserCoreDTO;
import com.softproideas.commons.model.tree.NodeStateDTO;

public class TemplateDetailsDTO extends TemplateDTO {

    private String jsonForm;
    private WorkbookDTO workbook;
    private List<UserCoreDTO> users=new ArrayList<UserCoreDTO>();
    private List<UserCoreDTO> availableUsers=new ArrayList<UserCoreDTO>();
    private List<TemplateDetailsDTO> children;
    private String text;
    private NodeStateDTO state;

    /*-----------------------------------------------------------------------------------------------------------*/
    
    

    public void setStateOpened(boolean opened) {
        if (state == null) {
            state = new NodeStateDTO();
        }
        state.setOpened(opened);
    }

    public String getJsonForm() {
        return jsonForm;
    }

    public List<TemplateDetailsDTO> getChildren() {
        return children;
    }

    public String getText() {
        return text;
    }

    public NodeStateDTO getState() {
        return state;
    }

    public void setJsonForm(String jsonForm) {
        this.jsonForm = jsonForm;
    }

    public void setChildren(List<TemplateDetailsDTO> children) {
        this.children = children;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setState(NodeStateDTO state) {
        this.state = state;
    }

    public WorkbookDTO getWorkbook() {
        return workbook;
    }

    public void setWorkbook(WorkbookDTO workbook) {
        this.workbook = workbook;
    }

    public List<UserCoreDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserCoreDTO> users) {
        this.users = users;
    }

    public List<UserCoreDTO> getAvailableUsers() {
        return availableUsers;
    }

    public void setAvailableUsers(List<UserCoreDTO> availableUsers) {
        this.availableUsers = availableUsers;
    }


    
}
