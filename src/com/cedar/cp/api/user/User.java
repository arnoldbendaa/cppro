package com.cedar.cp.api.user;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public abstract interface User
{
    public abstract Object getPrimaryKey();

    public abstract String getName();

    public abstract String getFullName();

    public abstract String getEMailAddress();

    public abstract String getPasswordBytes();

    public abstract Timestamp getPasswordDate();

    public abstract boolean isChangePassword();

    public abstract int getResetStrikes();

    public abstract boolean isUserDisabled();

    public abstract boolean isPasswordNeverExpires();

    public abstract String getExternalSystemUserName();

    public abstract String getLogonAlias();

    public abstract UserRef getRef();

    public abstract Map getUserPreferences();

    public abstract List getUserChallenges();

    public abstract String getUserPreferenceValue(String paramString);

    public abstract String getPassword();

    public abstract boolean isPasswordChanged();

    public abstract List getRoles();

    public abstract boolean isUserReadOnly();

    public abstract boolean isNewFeaturesEnabled();
    
    public abstract boolean areButtonsVisible();
    
    public abstract boolean getRoadMapAvailable();
    
    public abstract boolean isShowBudgetActivity();
    
    public abstract boolean isNewView();
}