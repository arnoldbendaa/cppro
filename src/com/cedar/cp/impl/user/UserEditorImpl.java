// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.user;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.Dimension;
import com.cedar.cp.api.role.RoleRef;
import com.cedar.cp.api.user.User;
import com.cedar.cp.api.user.UserEditor;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.user.UserEditorSessionSSO;
import com.cedar.cp.dto.user.UserImpl;
import com.cedar.cp.dto.user.UserPreferenceImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.user.UserAdapter;
import com.cedar.cp.impl.user.UserEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;

public class UserEditorImpl extends BusinessEditorImpl implements UserEditor {

    private Integer mAuthenicationTechnique;
    private UserEditorSessionSSO mServerSessionData;
    private UserImpl mEditorData;
    private UserAdapter mEditorDataAdapter;

    public UserEditorImpl(UserEditorSessionImpl session, UserEditorSessionSSO serverSessionData, UserImpl editorData) {
        super(session);
        this.mServerSessionData = serverSessionData;
        this.mEditorData = editorData;
    }

    public void updateEditorData(UserEditorSessionSSO serverSessionData, UserImpl editorData) {
        this.mServerSessionData = serverSessionData;
        this.mEditorData = editorData;
    }

    public void setChangePassword(boolean newChangePassword) throws ValidationException {
        this.validateChangePassword(newChangePassword);
        if (this.mEditorData.isChangePassword() != newChangePassword) {
            this.setContentModified();
            this.mEditorData.setChangePassword(newChangePassword);
        }
    }

    public void setUserDisabled(boolean newUserDisabled) throws ValidationException {
        this.validateUserDisabled(newUserDisabled);
        if (this.mEditorData.isUserDisabled() != newUserDisabled) {
            this.setContentModified();
            this.mEditorData.setUserDisabled(newUserDisabled);
        }
    }

    public void setUserReadOnly(boolean newUserReadOnly) throws ValidationException {
        if (this.mEditorData.isUserReadOnly() != newUserReadOnly) {
            this.setContentModified();
            this.mEditorData.setUserReadOnly(newUserReadOnly);
        }
    }

    public void setPasswordNeverExpires(boolean newPasswordNeverExpires) throws ValidationException {
        this.validatePasswordNeverExpires(newPasswordNeverExpires);
        if (this.mEditorData.isPasswordNeverExpires() != newPasswordNeverExpires) {
            this.setContentModified();
            this.mEditorData.setPasswordNeverExpires(newPasswordNeverExpires);
        }
    }

    public void setName(String newName) throws ValidationException {
        if (newName != null) {
            newName = StringUtils.rtrim(newName);
        }

        this.validateName(newName);
        if (this.mEditorData.getName() == null || !this.mEditorData.getName().equals(newName)) {
            this.setContentModified();
            this.mEditorData.setName(newName);
        }
    }

    public void setFullName(String newFullName) throws ValidationException {
        if (newFullName != null) {
            newFullName = StringUtils.rtrim(newFullName);
        }

        this.validateFullName(newFullName);
        if (this.mEditorData.getFullName() == null || !this.mEditorData.getFullName().equals(newFullName)) {
            this.setContentModified();
            this.mEditorData.setFullName(newFullName);
        }
    }

    public void setEMailAddress(String newEMailAddress) throws ValidationException {
        if (newEMailAddress != null) {
            newEMailAddress = StringUtils.rtrim(newEMailAddress);
        }

        this.validateEMailAddress(newEMailAddress);
        if (this.mEditorData.getEMailAddress() == null || !this.mEditorData.getEMailAddress().equals(newEMailAddress)) {
            this.setContentModified();
            this.mEditorData.setEMailAddress(newEMailAddress);
        }
    }

    public void setPasswordBytes(String newPasswordBytes) throws ValidationException {
        if (newPasswordBytes != null) {
            newPasswordBytes = StringUtils.rtrim(newPasswordBytes);
        }

        this.validatePasswordBytes(newPasswordBytes);
        if (this.mEditorData.getPasswordBytes() == null || !this.mEditorData.getPasswordBytes().equals(newPasswordBytes)) {
            this.setContentModified();
            this.mEditorData.setPasswordBytes(newPasswordBytes);
        }
    }

    public void setPasswordDate(Timestamp newPasswordDate) throws ValidationException {
        this.validatePasswordDate(newPasswordDate);
        if (this.mEditorData.getPasswordDate() == null || !this.mEditorData.getPasswordDate().equals(newPasswordDate)) {
            this.setContentModified();
            this.mEditorData.setPasswordDate(newPasswordDate);
        }
    }

    public void setExternalSystemUserName(String newExternalSystemUserName) throws ValidationException {
        if (newExternalSystemUserName != null) {
            newExternalSystemUserName = StringUtils.rtrim(newExternalSystemUserName);
        }

        this.validateExternalSystemUserName(newExternalSystemUserName);
        if (this.mEditorData.getExternalSystemUserName() == null || !this.mEditorData.getExternalSystemUserName().equals(newExternalSystemUserName)) {
            this.setContentModified();
            this.mEditorData.setExternalSystemUserName(newExternalSystemUserName);
        }
    }

    public void setLogonAlias(String newLogonAlias) throws ValidationException {
        if (newLogonAlias != null) {
            newLogonAlias = StringUtils.rtrim(newLogonAlias);
        }

        this.validateLogonAlias(newLogonAlias);
        if (this.mEditorData.getLogonAlias() == null || !this.mEditorData.getLogonAlias().equals(newLogonAlias)) {
            this.setContentModified();
            this.mEditorData.setLogonAlias(newLogonAlias);
        }
    }

    public void validateName(String newName) throws ValidationException {
        if (newName != null && newName.length() > 255) {
            throw new ValidationException("length (" + newName.length() + ") of Name must not exceed 255 on a User");
        } else if (newName == null || newName.length() == 0) {
            throw new ValidationException("User Name cannot be empty");
        }
    }

    public void validateFullName(String newFullName) throws ValidationException {
        if (newFullName != null && newFullName.length() > 255) {
            throw new ValidationException("length (" + newFullName.length() + ") of FullName must not exceed 255 on a User");
        } else if (newFullName == null || newFullName.length() == 0) {
            throw new ValidationException("Full Name cannot be empty");
        }
    }

    public void validateEMailAddress(String newEMailAddress) throws ValidationException {
        if (newEMailAddress != null && newEMailAddress.length() > 255) {
            throw new ValidationException("length (" + newEMailAddress.length() + ") of EMailAddress must not exceed 255 on a User");
        }
    }

    public void validatePasswordBytes(String newPasswordBytes) throws ValidationException {
        if (newPasswordBytes != null && newPasswordBytes.length() > 100) {
            throw new ValidationException("length (" + newPasswordBytes.length() + ") of PasswordBytes must not exceed 100 on a User");
        }
    }

    public void validatePasswordDate(Timestamp newPasswordDate) throws ValidationException {
    }

    public void validateChangePassword(boolean newChangePassword) throws ValidationException {
    }

    public void validateUserDisabled(boolean newUserDisabled) throws ValidationException {
    }

    public void validatePasswordNeverExpires(boolean newPasswordNeverExpires) throws ValidationException {
    }

    public void validateExternalSystemUserName(String newExternalSystemUserName) throws ValidationException {
        if (newExternalSystemUserName != null && newExternalSystemUserName.length() > 255) {
            throw new ValidationException("length (" + newExternalSystemUserName.length() + ") of ExternalSystemUserName must not exceed 255 on a User");
        }
    }

    public void validateLogonAlias(String newLogonAlias) throws ValidationException {
        if (newLogonAlias != null && newLogonAlias.length() > 255) {
            throw new ValidationException("length (" + newLogonAlias.length() + ") of LogonAlias must not exceed 255 on a User");
        }
    }

    public User getUser() {
        if (this.mEditorDataAdapter == null) {
            this.mEditorDataAdapter = new UserAdapter((UserEditorSessionImpl) this.getBusinessSession(), this.mEditorData);
        }

        return this.mEditorDataAdapter;
    }

    public void saveModifications() throws ValidationException {
        this.saveValidation();
    }

    private void saveValidation() throws ValidationException {
        if (this.mEditorData.getRoles().size() == 0) {
            throw new ValidationException("One or more roles must be selected");
        } else if (this.mEditorData.getFullName() != null && this.mEditorData.getFullName().length() > 0) {
            if (this.mEditorData.getName() != null && this.mEditorData.getName().length() > 0) {
                if (this.mEditorData.getPasswordDate() == null) {
                    this.mEditorData.setPasswordDate(new Timestamp(System.currentTimeMillis()));
                }

            } else {
                throw new ValidationException("Logon ID cannot be empty");
            }
        } else {
            throw new ValidationException("Full Name cannot be empty");
        }
    }

    public void setImageIcon(Icon newIcon) throws ValidationException {
        if (this.mEditorData.getImageIcon() == null || !this.mEditorData.getImageIcon().equals(newIcon)) {
            this.setContentModified();
            this.mEditorData.setImageIcon(newIcon);
        }
    }

    public void setUserPreferences(Map newUserPreferences) throws ValidationException {
        if (this.mEditorData.getUserPreferences() == null || !this.mEditorData.getUserPreferences().equals(newUserPreferences)) {
            this.setContentModified();
            this.mEditorData.setUserPreferences(newUserPreferences);
        }
    }

    public void setUserPreferenceValue(String name, String value) throws ValidationException {
        Object userPreferences = this.mEditorData.getUserPreferences();
        if (userPreferences == null) {
            userPreferences = new HashMap();
            this.setContentModified();
        }

        UserPreferenceImpl userPreference = (UserPreferenceImpl) ((Map) userPreferences).get(name);
        if (userPreference == null) {
            userPreference = new UserPreferenceImpl();
            userPreference.setContentModified(false);
            userPreference.setHelpId("user.preference");
            userPreference.setPrefName(name);
            userPreference.setPrefType(1);
            userPreference.setPrefValue(value);
            userPreference.setUserRef(this.getUser().getRef());
            userPreference.setVersionNum(1);
            ((Map) userPreferences).put(name, userPreference);
            this.setContentModified();
        }

        if (!userPreference.getPrefValue().equals(value)) {
            userPreference.setPrefValue(value);
            userPreference.setContentModified(true);
            this.setContentModified();
        }

    }

    public synchronized Map getModelDimensions() throws CPException {
        return null;
    }

    public synchronized Dimension getDimension(Object key) throws CPException {
        return null;
    }

    public UserRef getRef() {
        return this.mEditorData.getRef();
    }

    public int getMinimumPasswordSize() {
        UserEditorSessionImpl session = (UserEditorSessionImpl) this.getBusinessSession();
        return session.getMinimumPasswordSize();
    }

    public boolean canChangePassword() {
        UserEditorSessionImpl session = (UserEditorSessionImpl) this.getBusinessSession();
        return session.canChangePassword();
    }

    public void setPassword(String newPassword) throws ValidationException {
        this.validatePassword(newPassword);
        if (this.mEditorData.getPassword() == null || !this.mEditorData.getPassword().equals(newPassword)) {
            this.setContentModified();
            this.mEditorData.setPasswordChanged(true);
            this.mEditorData.setPassword(newPassword);
        }
    }

    public void validatePassword(String newPassword) throws ValidationException {
    }

    public void addRole(RoleRef role) {
        this.mEditorData.getRoles().add(role);
        this.setContentModified();
    }

    public void removeRole(RoleRef role) {
        this.mEditorData.getRoles().remove(role);
        this.setContentModified();
    }

    public void removeRole(int index) {
        this.mEditorData.getRoles().remove(index);
        this.setContentModified();
    }

    public void setPasswordChanged(boolean changedByUser) {
        this.mEditorData.setPasswordChanged(changedByUser);
    }

    public boolean isPasswordProtected() {
        return this.getAuthenticationTechnque() != 1;
    }

    public boolean isRoleAdminDisabled() {
        return this.getAuthenticationTechnque() == 5;
    }

    public boolean isEmailAddressProtected() {
        return this.getAuthenticationTechnque() == 5;
    }

    public String getRestrictedBannerText() {
        return this.getAuthenticationTechnque() == 1 ? null : "Enterprise single signon authentication active.";
    }

    private int getAuthenticationTechnque() {
        if (this.mAuthenicationTechnique == null) {
            this.mAuthenicationTechnique = Integer.valueOf(this.getBusinessSession().getBusinessProcess().getConnection().getAuthenticationPolicysProcess().getActiveAuthenticationTechnique());
        }

        return this.mAuthenicationTechnique.intValue();
    }

    public void setUserChallenges(List challenges) {
        if ((mEditorData.getUserChallenges() != null) && (mEditorData.getUserChallenges().equals(challenges))) {
            return;
        }

        setContentModified();
        mEditorData.setUserChallenges(challenges);
    }

    public EntityList getAllModels() throws CPException {
        return this.getConnection().getListHelper().getAllModels();
    }

    public EntityList getAllBudgetHierarchies() {
        return this.getConnection().getListHelper().getAllBudgetHierarchies();
    }

    public EntityList getImmediateChildren(Object pk) {
        int elementId = ((StructureElementPK) pk).getStructureElementId();
        int structureId = ((StructureElementPK) pk).getStructureId();
        return this.getConnection().getListHelper().getImmediateChildren(structureId, elementId);
    }

    public List<Object[]> getUserAssignments() {
        return mEditorData.getUserAssignments();
    }

    public void setUserAssignments(List dmtns) {
        mEditorData.setUserAssignments(dmtns);
    }

    public boolean hasUserAssignment(Object o) {
        return mEditorData.hasUserAssignment(o);
    }

    public void removeUserAssignment(Object o) {
        mEditorData.removeUserAssignment(o);
    }

    public List getUserAdminAppRoles() {
        return mEditorData.getUserAdminAppRoles();
    }

    public void setUserAdminAppRoles(List roleRefs) {
        mEditorData.setUserAdminAppRoles(roleRefs);
    }

    public List<List<Object>> getUserXmlForms() {
        return mEditorData.getUserXmlForms();
    }

    public void setUserXmlForms(List<List<Object>> mUserXmlForms) {
        this.mEditorData.setUserXmlForms(mUserXmlForms);
    }

    @Override
    public boolean isNewFeaturesEnabled() {
        return this.mEditorData.isNewFeaturesEnabled();
    }

    @Override
    public void setNewFeaturesEnabled(boolean bool) {
        if (isNewFeaturesEnabled() != bool) {
            this.setContentModified();
            this.mEditorData.setNewFeaturesEnabled(bool);
        }
    }

    @Override
    public boolean areButtonsVisible() {
        return this.mEditorData.areButtonsVisible();
    }

    @Override
    public void setButtonsVisible(boolean bool) {
        if (areButtonsVisible() != bool) {
            this.setContentModified();
            this.mEditorData.setButtonsVisible(bool);
        }
    }
    
    @Override
    public boolean getRoadMapAvailable() {
        return this.mEditorData.getRoadMapAvailable();
    }
    
    @Override
    public void setRoadMapAvailable(boolean bool) {
        if (areButtonsVisible() != bool) {
            this.setContentModified();
            this.mEditorData.setRoadMapAvailable(bool);
        }
    }
    
    @Override
    public boolean isShowBudgetActivity() {
        return this.mEditorData.isShowBudgetActivity();
    }

    @Override
    public void setShowBudgetActivity(boolean bool) {
        if (isShowBudgetActivity() != bool) {
            this.setContentModified();
            this.mEditorData.setShowBudgetActivity(bool);
        }
    }
    
    @Override
    public boolean isNewView() {
        return this.mEditorData.isNewView();
    }

    @Override
    public void setNewView(boolean bool) {
        if (isNewView() != bool) {
            this.setContentModified();
            this.mEditorData.setNewView(bool);
        }
    }
}
