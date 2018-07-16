// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:09
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.base;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.UserContext;
import com.cedar.cp.api.logon.AuthenticationResult;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class UserContextImpl implements UserContext {

    private CPConnection mConnection;
    private AuthenticationResult mAuthenticationResult;
    private UserRefImpl mUserRef;
    private String mSystemName;
    private String mLogonId;
    private String mUserName;
    private String mPassword;
    private int[] mAllowedBusinessProcesses;
    private Set mSecurityStrings;
    private Map<String, Set<String>> mUserRoleSecurityStrings;
    private Map<String, String> mRoleDescriptions;
    private Set<String> mUserRoles;
    private Map mUserPreferences;
    private boolean mNewFeaturesEnabled;
    private boolean mButtonsVisible;
    private boolean mRoadMapAvailable;
    private boolean mShowBudgetActivity;
    private boolean mNewView;

    public UserContextImpl(CPConnection connection, String logonId, String userName, int[] businessProcesses, AuthenticationResult res) {
        this.mConnection = connection;
        this.mLogonId = logonId;
        this.mUserName = userName;
        this.mSystemName = res.getSystemName();
        this.mAllowedBusinessProcesses = businessProcesses;
        this.mRoleDescriptions = res.getRoleDescriptions();
        this.mUserRoleSecurityStrings = res.getUserRoleSecurityStrings();
        this.mUserRoles = new TreeSet(this.mUserRoleSecurityStrings.keySet());
        this.mSecurityStrings = res.getUserSecurityStrings();
        this.mAuthenticationResult = res;
        this.mUserPreferences = Collections.EMPTY_MAP;
        this.mNewFeaturesEnabled = res.isNewFeaturesEnabled();
        this.mButtonsVisible = res.areButtonsVisible();
        this.mRoadMapAvailable = res.getRoadMapAvailable();
        this.mShowBudgetActivity = res.isShowBudgetActivity();
        this.mNewView = res.isNewView();
    }

    public CPConnection getConnection() {
        return this.mConnection;
    }

    public String getSystemName() {
        return this.mSystemName;
    }

    public Object getPrimaryKey() {
        return this.mAuthenticationResult.getPrimaryKey();
    }

    public String getLogonId() {
        return this.mAuthenticationResult.getLogonID();
    }

    public int getUserId() {
        Integer theInt = new Integer(this.mAuthenticationResult.getLogonID());
        return theInt.intValue();
    }

    public String getUserName() {
        return this.mUserName;
    }

    public boolean userMustChangePassword() {
        return this.mAuthenticationResult.getResult() == 4;
    }

    public boolean userCanChangePassword() {
        return this.mAuthenticationResult.getResult() != 3;
    }

    public int getPasswordExpiryDays() {
        return (int) this.mAuthenticationResult.getExpiryDays();
    }

    public int getConnectionResult() {
        return this.mAuthenticationResult.getResult();
    }

    public int getMinimumPasswordSize() {
        return this.mAuthenticationResult.getMinPasswordSize();
    }

    public String getLogonString() {
        return this.mLogonId;
    }

    public int[] getAllowedBusinessProcesses() {
        return this.mAllowedBusinessProcesses;
    }

    public Set<String> getUserRoles() {
        return this.mUserRoles;
    }

    public Set<String> getUserRoleSecurityStrings(String role) {
        return (Set) this.mUserRoleSecurityStrings.get(role);
    }

    public Map<String, String> getRoleDescriptions() {
        return this.mRoleDescriptions;
    }

    public boolean hasSecurity(int processId, String action) {
        String prefix = sProcessSecurityPrefixes[processId];
        return this.mSecurityStrings.contains(prefix + "." + action);
    }

    public boolean hasSecurity(String appAction) {
        return this.mSecurityStrings.contains(appAction);
    }

    public boolean isAdmin() {
        return this.hasSecurity("WEB_PROCESS.SystemAdmin");
    }

    public AuthenticationResult getAuthenticationResult() {
        return this.mAuthenticationResult;
    }

    public UserRef getUserRef() {
        if (this.mUserRef == null) {
            this.mUserRef = new UserRefImpl(new UserPK(this.getUserId()), this.mLogonId);
        }

        return this.mUserRef;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public Map getUserPreferenceValues() {
        return this.mUserPreferences;
    }

    public void setUserPreferenceValues(Map userPreferences) {
        this.mUserPreferences = userPreferences;
    }

    @Override
    public boolean isNewFeaturesEnabled() {
        return mNewFeaturesEnabled;
    }

    public void setNewFeaturesEnabled(boolean mNewFeaturesEnabled) {
        this.mNewFeaturesEnabled = mNewFeaturesEnabled;
    }

    @Override
    public boolean areButtonsVisible() {
        return mButtonsVisible;
    }
    
    public void setButtonsVisible(boolean mButtonsVisible) {
        this.mButtonsVisible = mButtonsVisible;
    }
    
    @Override
    public boolean getRoadMapAvailable() {
        return mRoadMapAvailable;
    }
    
    public void setRoadMapAvailable(boolean value) {
        this.mRoadMapAvailable = value;
    }
    
    @Override
    public boolean isShowBudgetActivity() {
        return mShowBudgetActivity;
    }
    
    public void setShowBudgetActivity(boolean mShowBudgetActivity) {
        this.mShowBudgetActivity = mShowBudgetActivity;
    }
    
    @Override
    public boolean isNewView() {
        return mNewView;
    }
    
    public void setNewView(boolean mNewView) {
        this.mNewView = mNewView;
    }
    
}
