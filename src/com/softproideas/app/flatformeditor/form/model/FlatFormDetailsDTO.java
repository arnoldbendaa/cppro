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
/**
 * 
 */
package com.softproideas.app.flatformeditor.form.model;

import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.softproideas.app.core.users.model.UserCoreDTO;

/**
 * <p> 
 * Represents a flat form. Contains meta data, security data, JSON and XML form.
 * </p>
 *
 * @author Jacek Kurasiewicz
 * @email jk@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class FlatFormDetailsDTO {
    
    private int flatFormId;
    private String visId;
    private int financeCubeId;
    private int type;
    private String description;
    
    private List<UserCoreDTO> users=new ArrayList<UserCoreDTO>();
    private List<UserCoreDTO> availableUsers=new ArrayList<UserCoreDTO>();
    private boolean securityAccess;
    private int versionNum;
    
    private String jsonForm;
    private WorkbookDTO xmlForm;
    
    private boolean designMode;

    /**
     * @param flatFormId
     * @param visId
     * @param financeCubeId
     * @param type
     * @param description
     * @param users
     * @param securityAccess
     * @param versionNum
     * @param jsonForm
     * @param xmlForm
     * @param designMode
     */
    public FlatFormDetailsDTO(int flatFormId, String visId, int financeCubeId, int type, String description, List<UserCoreDTO> users, List<UserCoreDTO> availableUsers, boolean securityAccess, int versionNum, String jsonForm, WorkbookDTO xmlForm, boolean designMode) {
        super();
        this.flatFormId = flatFormId;
        this.visId = visId;
        this.financeCubeId = financeCubeId;
        this.type = type;
        this.description = description;
        this.users = users;
        this.availableUsers = availableUsers;
        this.securityAccess = securityAccess;
        this.versionNum = versionNum;
        this.jsonForm = jsonForm;
        this.xmlForm = xmlForm;
        this.designMode = designMode;
    }

    /**
     * Default constructor
     */
    public FlatFormDetailsDTO() {
        super();
    }

    /**
     * @return the flatFormId
     */
    public int getFlatFormId() {
        return flatFormId;
    }

    /**
     * @param flatFormId the flatFormId to set
     */
    public void setFlatFormId(int flatFormId) {
        this.flatFormId = flatFormId;
    }

    /**
     * @return the mVisId
     */
    public String getVisId() {
        return visId;
    }

    /**
     * @param mVisId the mVisId to set
     */
    public void setVisId(String mVisId) {
        this.visId = mVisId;
    }

    /**
     * @return the financeCubeId
     */
    public int getFinanceCubeId() {
        return financeCubeId;
    }

    /**
     * @param financeCubeId the financeCubeId to set
     */
    public void setFinanceCubeId(int financeCubeId) {
        this.financeCubeId = financeCubeId;
    }

    /**
     * @return the mType
     */
    public int getType() {
        return type;
    }

    /**
     * @param mType the mType to set
     */
    public void setType(int mType) {
        this.type = mType;
    }

    /**
     * @return the mDescription
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param mDescription the mDescription to set
     */
    public void setDescription(String mDescription) {
        this.description = mDescription;
    }

    /**
     * @return the users
     */
    public List<UserCoreDTO> getUsers() {
        return users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(List<UserCoreDTO> users) {
        this.users = users;
    }

    /**
     * @return the availableUsers
     */
    public List<UserCoreDTO> getAvailableUsers() {
        return availableUsers;
    }

    /**
     * @param availableUsers to set
     */
    public void setAvailableUsers(List<UserCoreDTO> availableUsers) {
        this.availableUsers = availableUsers;
    }    
    
    /**
     * @return the securityAccess
     */
    public boolean isSecurityAccess() {
        return securityAccess;
    }

    /**
     * @param securityAccess the securityAccess to set
     */
    public void setSecurityAccess(boolean securityAccess) {
        this.securityAccess = securityAccess;
    }

    /**
     * @return the versionNum
     */
    public int getVersionNum() {
        return versionNum;
    }

    /**
     * @param versionNum the versionNum to set
     */
    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

    /**
     * @return the jsonForm
     */
    public String getJsonForm() {
        return jsonForm;
    }

    /**
     * @param jsonForm the jsonForm to set
     */
    public void setJsonForm(String jsonForm) {
        this.jsonForm = jsonForm;
    }

    /**
     * @return the xmlForm
     */
    public WorkbookDTO getXmlForm() {
        return xmlForm;
    }

    /**
     * @param xmlForm the xmlForm to set
     */
    public void setXmlForm(WorkbookDTO xmlForm) {
        this.xmlForm = xmlForm;
    }

    /**
     * @return the designMode
     */
    public boolean isDesignMode() {
        return designMode;
    }

    /**
     * @param designMode the designMode to set
     */
    public void setDesignMode(boolean designMode) {
        this.designMode = designMode;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (designMode ? 1231 : 1237);
        result = prime * result + financeCubeId;
        result = prime * result + flatFormId;
        result = prime * result + ((jsonForm == null) ? 0 : jsonForm.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + type;
        result = prime * result + ((visId == null) ? 0 : visId.hashCode());
        result = prime * result + (securityAccess ? 1231 : 1237);
        result = prime * result + ((users == null) ? 0 : users.hashCode());
        result = prime * result + ((availableUsers == null) ? 0 : availableUsers.hashCode());
        result = prime * result + versionNum;
        result = prime * result + ((xmlForm == null) ? 0 : xmlForm.hashCode());
        return result;
    }
    
}
