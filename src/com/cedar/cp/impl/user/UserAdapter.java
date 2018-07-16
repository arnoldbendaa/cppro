package com.cedar.cp.impl.user;

import com.cedar.cp.api.user.User;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.user.UserImpl;
import com.cedar.cp.dto.user.UserPK;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import javax.swing.Icon;

public class UserAdapter implements User {

    private UserImpl mEditorData;
    private UserEditorSessionImpl mEditorSessionImpl;

    public UserAdapter(UserEditorSessionImpl e, UserImpl editorData) {
        mEditorData = editorData;
        mEditorSessionImpl = e;
    }

    public void setPrimaryKey(Object key) {
        mEditorData.setPrimaryKey(key);
    }

    protected UserEditorSessionImpl getEditorSessionImpl() {
        return mEditorSessionImpl;
    }

    protected UserImpl getEditorData() {
        return mEditorData;
    }

    public Object getPrimaryKey() {
        return mEditorData.getPrimaryKey();
    }

    void setPrimaryKey(UserPK paramKey) {
        mEditorData.setPrimaryKey(paramKey);
    }

    public String getName() {
        return mEditorData.getName();
    }

    public String getFullName() {
        return mEditorData.getFullName();
    }

    public String getEMailAddress() {
        return mEditorData.getEMailAddress();
    }

    public String getPasswordBytes() {
        return mEditorData.getPasswordBytes();
    }

    public Timestamp getPasswordDate() {
        return mEditorData.getPasswordDate();
    }

    public boolean isChangePassword() {
        return mEditorData.isChangePassword();
    }

    public int getResetStrikes() {
        return mEditorData.getResetStrikes();
    }

    public boolean isUserDisabled() {
        return mEditorData.isUserDisabled();
    }

    public boolean isPasswordNeverExpires() {
        return mEditorData.isPasswordNeverExpires();
    }

    public String getExternalSystemUserName() {
        return mEditorData.getExternalSystemUserName();
    }

    public String getLogonAlias() {
        return mEditorData.getLogonAlias();
    }

    public void setName(String p) {
        mEditorData.setName(p);
    }

    public void setFullName(String p) {
        mEditorData.setFullName(p);
    }

    public void setEMailAddress(String p) {
        mEditorData.setEMailAddress(p);
    }

    public void setPasswordBytes(String p) {
        mEditorData.setPasswordBytes(p);
    }

    public void setPasswordDate(Timestamp p) {
        mEditorData.setPasswordDate(p);
    }

    public void setChangePassword(boolean p) {
        mEditorData.setChangePassword(p);
    }

    public void setResetStrikes(int p) {
        mEditorData.setResetStrikes(p);
    }

    public void setUserDisabled(boolean p) {
        mEditorData.setUserDisabled(p);
    }

    public void setPasswordNeverExpires(boolean p) {
        mEditorData.setPasswordNeverExpires(p);
    }

    public void setExternalSystemUserName(String p) {
        mEditorData.setExternalSystemUserName(p);
    }

    public void setLogonAlias(String p) {
        mEditorData.setLogonAlias(p);
    }

    public Icon getImageIcon() {
        return mEditorData.getImageIcon();
    }

    public void setImageIcon(Icon icon) {
        mEditorData.setImageIcon(icon);
    }

    public List getRoles() {
        return mEditorData.getRoles();
    }

    public Map getUserPreferences() {
        return mEditorData.getUserPreferences();
    }

    public List getUserChallenges() {
        return mEditorData.getUserChallenges();
    }

    public void setUserPreferences(Map uPreference) {
        mEditorData.setUserPreferences(uPreference);
    }

    public String getPassword() {
        return mEditorData.getPassword();
    }

    public UserRef getRef() {
        return mEditorData.getRef();
    }

    public boolean isPasswordChanged() {
        return mEditorData.isPasswordChanged();
    }

    public String getUserPreferenceValue(String key) {
        return mEditorData.getUserPreferenceValue(key);
    }

    public List getUserAssignments() {
        return mEditorData.getUserAssignments();
    }

    public boolean isUserReadOnly() {
        return mEditorData.isUserReadOnly();
    }

    @Override
    public boolean isNewFeaturesEnabled() {
        return mEditorData.isNewFeaturesEnabled();
    }

    @Override
    public boolean areButtonsVisible() {
        return mEditorData.areButtonsVisible();
    }
    
    @Override
    public boolean getRoadMapAvailable() {
        return mEditorData.getRoadMapAvailable();
    }
    
    @Override
    public boolean isShowBudgetActivity() {
        return mEditorData.isShowBudgetActivity();
    }
    @Override
    public boolean isNewView() {
        return mEditorData.isNewView();
    }
}