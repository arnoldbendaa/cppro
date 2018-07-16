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
package com.softproideas.app.admin.users.model;

import java.util.List;

import com.softproideas.app.admin.profiles.model.ProfileDTO;
import com.softproideas.app.core.flatform.model.FlatFormExtendedCoreDTO;
import com.softproideas.app.core.roles.model.RoleCoreDTO;
import com.softproideas.app.core.users.model.UserCoreDTO;
import com.softproideas.common.models.FormDeploymentDataDTO;
import com.softproideas.commons.model.tree.NodeStaticDTO;

public class UserDetailsDTO extends UserCoreDTO {

    private String emailAddress;
    private List<RoleCoreDTO> roles;
    private String logonAlias;
    private String password;
    private boolean isPasswordNeverExpires;
    private String externalSystemUserName;
    private List<UserSecurityNodeDTO> userSecurityAssignments;
    private boolean isUserReadOnly;
    private boolean isNewFeaturesEnabled;
    private boolean areButtonsVisible;
    private boolean roadMapAvailable;
    private List<NodeStaticDTO> userAdminApp;
    private List<RoleCoreDTO> hiddenRoles;
    private List<FlatFormExtendedCoreDTO> xmlForm;
    private List<FlatFormExtendedCoreDTO> availableXmlForm;
    private String confirmPassword;
    private boolean isShowBudgetActivity;
    private boolean isNewView;
    private List<ProfileDTO> mobileProfiles;
    private List<FormDeploymentDataDTO> profilesToSave;

    public List<NodeStaticDTO> getUserAdminApp() {
        return userAdminApp;
    }

    public void setUserAdminApp(List<NodeStaticDTO> userAdminApp) {
        this.userAdminApp = userAdminApp;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<RoleCoreDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleCoreDTO> roles) {
        this.roles = roles;
    }

    public String getLogonAlias() {
        return logonAlias;
    }

    public void setLogonAlias(String logonAlias) {
        this.logonAlias = logonAlias;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isPasswordNeverExpires() {
        return isPasswordNeverExpires;
    }

    public void setPasswordNeverExpires(boolean isPasswordNeverExpires) {
        this.isPasswordNeverExpires = isPasswordNeverExpires;
    }

    public String getExternalSystemUserName() {
        return externalSystemUserName;
    }

    public void setExternalSystemUserName(String externalSystemUserName) {
        this.externalSystemUserName = externalSystemUserName;
    }

    public List<UserSecurityNodeDTO> getUserSecurityAssignments() {
        return userSecurityAssignments;
    }

    public void setUserSecurityAssignments(List<UserSecurityNodeDTO> userSecurityAssignments) {
        this.userSecurityAssignments = userSecurityAssignments;
    }

    public boolean isUserReadOnly() {
        return isUserReadOnly;
    }

    public void setUserReadOnly(boolean isUserReadOnly) {
        this.isUserReadOnly = isUserReadOnly;
    }

    public boolean isNewFeaturesEnabled() {
        return isNewFeaturesEnabled;
    }

    public void setNewFeaturesEnabled(boolean isNewFeaturesEnabled) {
        this.isNewFeaturesEnabled = isNewFeaturesEnabled;
    }

    public boolean isAreButtonsVisible() {
        return areButtonsVisible;
    }

    public void setAreButtonsVisible(boolean areButtonsVisible) {
        this.areButtonsVisible = areButtonsVisible;
    }

    public List<RoleCoreDTO> getHiddenRoles() {
        return hiddenRoles;
    }

    public void setHiddenRoles(List<RoleCoreDTO> hiddenRoles) {
        this.hiddenRoles = hiddenRoles;
    }

    public List<FlatFormExtendedCoreDTO> getXmlForm() {
        return xmlForm;
    }

    public void setXmlForm(List<FlatFormExtendedCoreDTO> xmlForm) {
        this.xmlForm = xmlForm;
    }

    public List<FlatFormExtendedCoreDTO> getAvailableXmlForm() {
        return availableXmlForm;
    }

    public void setAvailableXmlForm(List<FlatFormExtendedCoreDTO> availableXmlForm) {
        this.availableXmlForm = availableXmlForm;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isShowBudgetActivity() {
        return isShowBudgetActivity;
    }

    public void setShowBudgetActivity(boolean isShowBudgetActivity) {
        this.isShowBudgetActivity = isShowBudgetActivity;
    }

    public boolean isNewView() {
        return isNewView;
    }

    public void setNewView(boolean isNewView) {
        this.isNewView = isNewView;
    }

    public boolean getRoadMapAvailable() {
        return roadMapAvailable;
    }

    public void setRoadMapAvailable(boolean roadmapAvailable) {
        this.roadMapAvailable = roadmapAvailable;
    }

    public List<ProfileDTO> getMobileProfiles() {
        return mobileProfiles;
    }

    public void setMobileProfiles(List<ProfileDTO> mobileProfiles) {
        this.mobileProfiles = mobileProfiles;
    }

    public List<FormDeploymentDataDTO> getProfilesToSave() {
        return profilesToSave;
    }

    public void setProfilesToSave(List<FormDeploymentDataDTO> profilesToSave) {
        this.profilesToSave = profilesToSave;
    }

}