package com.cedar.cp.dto.user;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.user.User;
import com.cedar.cp.api.user.UserPreference;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.dimension.StructureElementPK;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.Icon;

public class UserImpl implements User, Serializable, Cloneable
{
    private Map mUserPreferences;
    private List mUserChallenges;
    private String mPassword;
    private String mMobilePIN;
    private String mSalt;
    private List mRoles;
    private boolean mPasswordChanged;
    private String mExternalSystemPassword;
    private Object mPrimaryKey;
    private String mName;
    private String mFullName;
    private String mEMailAddress;
    private String mPasswordBytes;
    private Timestamp mPasswordDate;
    private boolean mChangePassword;
    private int mResetStrikes;
    private boolean mUserReadOnly;
    private boolean mUserNewFeaturesEnabled;
    private boolean mUserDisabled;
    private boolean mPasswordNeverExpires;
    private boolean mButtonsVisible;
    private boolean mRoadMapAvailable;
    private String mExternalSystemUserName;
    private String mLogonAlias;
    private int mVersionNum;
    private List mUserSecurityAssignments = new ArrayList();
    private List mUserAdminAppRoles = new ArrayList();
    private List<List<Object>> mUserXmlForms = new ArrayList();
    private boolean mShowBudgetActivity;
    private boolean mNewView;

    public UserImpl(Object paramKey)
    {
        mPrimaryKey = paramKey;
        mName = "";
        mFullName = "";
        mEMailAddress = "";
        mPasswordBytes = "";
        mPasswordDate = null;
        mChangePassword = false;
        mResetStrikes = 0;
        mUserReadOnly = false;
        mUserNewFeaturesEnabled = false;
        mButtonsVisible = false;
        mShowBudgetActivity = false;
        mNewView = false;
        mRoadMapAvailable = false;
        mUserDisabled = false;
        mPasswordNeverExpires = false;
        mExternalSystemUserName = "";
        mLogonAlias = "";

        mUserPreferences = new HashMap();
        mRoles = new ArrayList();
    }

    public Object getPrimaryKey()
    {
        return mPrimaryKey;
    }

    public void setPrimaryKey(Object paramKey)
    {
        mPrimaryKey = ((UserPK) paramKey);
    }

    public String getName()
    {
        return mName;
    }

    public String getFullName()
    {
        return mFullName;
    }

    public String getEMailAddress()
    {
        return mEMailAddress;
    }

    public String getPasswordBytes()
    {
        return mPasswordBytes;
    }

    public Timestamp getPasswordDate()
    {
        return mPasswordDate;
    }

    public boolean isChangePassword()
    {
        return mChangePassword;
    }

    public int getResetStrikes()
    {
        return mResetStrikes;
    }

    public boolean isUserDisabled()
    {
        return mUserDisabled;
    }

    public boolean isPasswordNeverExpires()
    {
        return mPasswordNeverExpires;
    }

    public String getExternalSystemUserName()
    {
        return mExternalSystemUserName;
    }

    public String getLogonAlias()
    {
        return mLogonAlias;
    }

    public void setVersionNum(int p)
    {
        mVersionNum = p;
    }

    public int getVersionNum()
    {
        return mVersionNum;
    }

    public void setName(String paramName)
    {
        mName = paramName;
    }

    public void setFullName(String paramFullName)
    {
        mFullName = paramFullName;
    }

    public void setEMailAddress(String paramEMailAddress)
    {
        mEMailAddress = paramEMailAddress;
    }

    public void setPasswordBytes(String paramPasswordBytes)
    {
        mPasswordBytes = paramPasswordBytes;
    }

    public void setPasswordDate(Timestamp paramPasswordDate)
    {
        mPasswordDate = paramPasswordDate;
    }

    public void setChangePassword(boolean paramChangePassword)
    {
        mChangePassword = paramChangePassword;
    }

    public void setResetStrikes(int paramResetStrikes)
    {
        mResetStrikes = paramResetStrikes;
    }

    public void setUserDisabled(boolean paramUserDisabled)
    {
        mUserDisabled = paramUserDisabled;
    }

    public void setPasswordNeverExpires(boolean paramPasswordNeverExpires)
    {
        mPasswordNeverExpires = paramPasswordNeverExpires;
    }

    public void setExternalSystemUserName(String paramExternalSystemUserName)
    {
        mExternalSystemUserName = paramExternalSystemUserName;
    }

    public void setLogonAlias(String paramLogonAlias)
    {
        mLogonAlias = paramLogonAlias;
    }

    public Icon getImageIcon()
    {
        return null;
    }

    public void setImageIcon(Icon icon)
    {
    }

    public Map getUserPreferences()
    {
        return mUserPreferences;
    }

    public void setUserPreferences(Map uPreference) {
        mUserPreferences = uPreference;
    }

    public List getUserChallenges() {
        return mUserChallenges;
    }

    public void setUserChallenges(List userChallenges) {
        mUserChallenges = userChallenges;
    }

    public UserRef getRef()
    {
        return new UserRefImpl((UserPK) mPrimaryKey, mName);
    }

    public String getPassword()
    {
        return mPassword;
    }

    public void setPassword(String paramPassword)
    {
        mPassword = paramPassword;
    }
    
    public String getMobilePIN(){
    	return mMobilePIN;
    }
    
    public void setMobilePIN(String mobilePIN){
    	this.mMobilePIN = mobilePIN;
    }
    
    public String getSalt(){
    	return this.mSalt;
    }
    
    public void setSalt(String salt){
    	this.mSalt = salt;
    }

    public List getRoles()
    {
        return mRoles;
    }

    public void setRoles(List roles)
    {
        mRoles = roles;
    }

    public boolean isPasswordChanged()
    {
        return mPasswordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged)
    {
        mPasswordChanged = passwordChanged;
    }

    public String getExternalSystemPassword()
    {
        return mExternalSystemPassword;
    }

    public void setExternalSystemPassword(String externalSystemPassword)
    {
        mExternalSystemPassword = externalSystemPassword;
    }

    public String getUserPreferenceValue(String key)
    {
        if ((mUserPreferences != null) && (key != null))
        {
            UserPreference pref = (UserPreference) mUserPreferences.get(key);
            if (pref != null)
                return pref.getPrefValue();
        }
        return null;
    }

    public List<Object[]> getUserAssignments() {
        return mUserSecurityAssignments;
    }

    public void setUserAssignments(List mUserAssignments) {
        this.mUserSecurityAssignments = mUserAssignments;
    }

    public boolean hasUserAssignment(Object o) {
        for (Object[] data: getUserAssignments()) {
            if (o instanceof StructureElementRef && data[2] instanceof StructureElementRef) {
                // compare StructureElementIds
                if (((StructureElementPK) ((StructureElementRef) o).getPrimaryKey()).getStructureElementId() == (((StructureElementPK) ((StructureElementRef) data[2]).getPrimaryKey()).getStructureElementId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeUserAssignment(Object o) {
        List<Object[]> list = getUserAssignments();
        Iterator<Object[]> it = list.iterator();
        while (it.hasNext()) {
            Object element = it.next()[2];
            if (o instanceof StructureElementRef && element instanceof StructureElementRef) {
                // compare StructureElementIds
                if (((StructureElementPK) ((StructureElementRef) o).getPrimaryKey()).getStructureElementId() == (((StructureElementPK) ((StructureElementRef) element).getPrimaryKey()).getStructureElementId())) {
                    it.remove();
                    break;
                }
            }
        }
        setUserAssignments(list);
    }

    public List getUserAdminAppRoles() {
        return mUserAdminAppRoles;
    }

    public void setUserAdminAppRoles(List mUserAdminAppRoles) {
        this.mUserAdminAppRoles = mUserAdminAppRoles;
    }

    public List<List<Object>> getUserXmlForms() {
        return mUserXmlForms;
    }

    public void setUserXmlForms(List<List<Object>> mUserXmlForms) {
        this.mUserXmlForms = mUserXmlForms;
    }

    public boolean isUserReadOnly() {
        return mUserReadOnly;
    }

    public void setUserReadOnly(boolean paramUserReadOnly)
    {
        mUserReadOnly = paramUserReadOnly;
    }

    public boolean isNewFeaturesEnabled() {
        return mUserNewFeaturesEnabled;
    }

    public void setNewFeaturesEnabled(boolean mUserNewFeaturesEnabled) {
        this.mUserNewFeaturesEnabled = mUserNewFeaturesEnabled;
    }

    public boolean areButtonsVisible() {
        return mButtonsVisible;
    }

    public void setButtonsVisible(boolean buttonsVisible) {
        this.mButtonsVisible = buttonsVisible;
    }
    
    public boolean getRoadMapAvailable() {
        return mRoadMapAvailable;
    }

    public void setRoadMapAvailable(boolean roadMap) {
        this.mRoadMapAvailable = roadMap;
    }

    public boolean isShowBudgetActivity() {
        return mShowBudgetActivity;
    }

    public void setShowBudgetActivity(boolean mShowBudgetActivity) {
        this.mShowBudgetActivity = mShowBudgetActivity;
    }

    public boolean isNewView() {
        return mNewView;
    }

    public void setNewView(boolean mNewView) {
        this.mNewView = mNewView;
    }
}