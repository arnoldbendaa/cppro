// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.logon;

import com.cedar.cp.api.logon.AuthenticationResult;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AuthenticationResultImpl implements AuthenticationResult, Serializable {

    private static final long serialVersionUID = 1116564756595512939L;
    private int mResult;
    private long mExpiryDays;
    private Object mPrimaryKey;
    private String mFullUserName;
    private String mUserName;
    private String mLogonID;
    private Set mSecurityStrings;
    private Map<String, Set<String>> mUserRoleSecurityStrings;
    private Map<String, String> mRoleDescriptions;
    private String mSystemName;
    private int mMinPasswordSize;
    private String mPassword;
    private String mValidationErrorMsg;
    private boolean mCluster;
    private boolean mNewFeaturesEnabled;
	private boolean mButtonsVisible;
	private boolean mShowBudgetActivity;
	private boolean mNewView;
	private boolean mRoadMapAvailable;

    public boolean isNewView() {
        return mNewView;
    }

    public void setNewView(boolean mNewView) {
        this.mNewView = mNewView;
    }

    public boolean isShowBudgetActivity() {
        return mShowBudgetActivity;
    }

    public void setShowBudgetActivity(boolean mShowBudgetActivity) {
        this.mShowBudgetActivity = mShowBudgetActivity;
    }

    public AuthenticationResultImpl() {
        this.mResult = 5;
        this.mExpiryDays = -1L;
        this.mFullUserName = "";
        this.mSecurityStrings = Collections.EMPTY_SET;
        this.mUserRoleSecurityStrings = Collections.EMPTY_MAP;
    }

    public AuthenticationResultImpl(int res, int exp, String fName, String id, Object primaryKey, Map<String, Set<String>> sStrings) {
        this.mResult = res;
        this.mExpiryDays = (long) exp;
        this.mFullUserName = fName;
        this.mLogonID = id;
        this.mPrimaryKey = primaryKey;
        this.mUserRoleSecurityStrings = sStrings;
        this.mSecurityStrings = new HashSet();
        Iterator i$ = sStrings.values().iterator();

        while (i$.hasNext()) {
            Set securityStrings = (Set) i$.next();
            this.mSecurityStrings.addAll(securityStrings);
        }

    }

    public int getResult() {
        return this.mResult;
    }

    public long getExpiryDays() {
        return this.mExpiryDays;
    }

    public int getMinPasswordSize() {
        return this.mMinPasswordSize;
    }

    public Object getPrimaryKey() {
        return this.mPrimaryKey;
    }

    public String getFullUserName() {
        return this.mFullUserName;
    }

    public String getUserName() {
        return this.mUserName;
    }

    public void setUserName(String userName) {
        this.mUserName = userName;
    }

    public String getLogonID() {
        return this.mLogonID;
    }

    public Set getUserSecurityStrings() {
        return this.mSecurityStrings;
    }

    public Map<String, Set<String>> getUserRoleSecurityStrings() {
        return this.mUserRoleSecurityStrings;
    }

    public Map<String, String> getRoleDescriptions() {
        return this.mRoleDescriptions;
    }

    public void setResult(int result) {
        this.mResult = result;
    }

    public void setExpiryDays(long exp) {
        this.mExpiryDays = exp;
    }

    public void setPrimaryKey(Object pk) {
        this.mPrimaryKey = pk;
    }

    public void setFullUserName(String name) {
        this.mFullUserName = name;
    }

    public void setLogonID(String id) {
        this.mLogonID = id;
    }

    public void setUserRoleSecurityStrings(Map<String, Set<String>> userRoleSecurityStrings) {
        this.mUserRoleSecurityStrings = userRoleSecurityStrings;
        this.mSecurityStrings = new HashSet();
        Iterator i$ = userRoleSecurityStrings.values().iterator();

        while (i$.hasNext()) {
            Set securityStrings = (Set) i$.next();
            this.mSecurityStrings.addAll(securityStrings);
        }

    }

    public void setRoleDescriptions(Map<String, String> roleDescriptions) {
        this.mRoleDescriptions = roleDescriptions;
    }

    public void setMinimumPasswordSize(int size) {
        this.mMinPasswordSize = size;
    }

    public String getSystemName() {
        return this.mSystemName;
    }

    public void setSystemName(String systemName) {
        this.mSystemName = systemName;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getValidationErrorMsg() {
        return this.mValidationErrorMsg;
    }

    public void setValidationErrorMsg(String msg) {
        this.mValidationErrorMsg = msg;
    }

    public boolean isCluster()
    {
        return mCluster;
    }

    public void setCluster(boolean cluster)
    {
        mCluster = cluster;
    }

    public boolean isNewFeaturesEnabled() {
        return mNewFeaturesEnabled;
    }

    public void setNewFeaturesEnabled(boolean mNewFeaturesEnabled) {
        this.mNewFeaturesEnabled = mNewFeaturesEnabled;
    }

	public void setButtonsVisible(boolean areButtonsVisible) {
		this.mButtonsVisible = areButtonsVisible;
	}
	
    public boolean areButtonsVisible() {
        return mButtonsVisible;
    }	
	
    public boolean getRoadMapAvailable() {
        return mRoadMapAvailable;
    }
    
    public void setRoadMapAvailable(boolean value) {
        this.mRoadMapAvailable = value;
    }
}
