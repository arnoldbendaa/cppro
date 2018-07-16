package com.cedar.cp.ejb.impl.user;

import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.reset.ChallengeQuestionPK;
import com.cedar.cp.dto.reset.UserResetLinkPK;
import com.cedar.cp.dto.user.DataEntryProfilePK;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserPreferencePK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.dto.user.UserRolePK;
import com.cedar.cp.ejb.impl.reset.ChallengeQuestionEVO;
import com.cedar.cp.ejb.impl.reset.UserResetLinkEVO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserEVO implements Serializable
{
	private transient UserPK mPK;
	private int mUserId;
	private String mName;
	private String mFullName;
	private String mEMailAddress;
	private String mPasswordBytes;
	private String mMobilePIN;
	private String mSalt;
	private Timestamp mPasswordDate;
	private boolean mChangePassword;
	private int mResetStrikes;
	private boolean mUserReadOnly;
	private boolean mUserDisabled;
	private boolean mNewFeaturesEnabled;
    private boolean mButtonsVisible;
    private boolean mRoadMapAvailable;
    private boolean mShowBudgetActivity;
    private boolean mNewView;
	private boolean mPasswordNeverExpires;
	private String mExternalSystemUserName;
	private String mLogonAlias;
	private int mVersionNum;
	private int mUpdatedByUserId;
	private Timestamp mUpdatedTime;
	private Timestamp mCreatedTime;
	private Map<UserRolePK, UserRoleEVO> mUserRoles;
	private List mUserXmlForms;

	protected boolean mUserRolesAllItemsLoaded;
	private Map<UserPreferencePK, UserPreferenceEVO> mUserPreferences;
	protected boolean mUserPreferencesAllItemsLoaded;
	private Map<DataEntryProfilePK, DataEntryProfileEVO> mDataEntryProfiles;
	protected boolean mDataEntryProfilesAllItemsLoaded;
	private Map<ChallengeQuestionPK, ChallengeQuestionEVO> mChallengeQuestions;
	protected boolean mChallengeQuestionsAllItemsLoaded;
	private Map<UserResetLinkPK, UserResetLinkEVO> mResetLink;
	private Map<UserRolePK, UserRoleEVO> mUserAdminAppRoles;
	protected boolean mResetLinkAllItemsLoaded;
	private boolean mModified;

	public UserEVO()
	{
	}

	public UserEVO(int newUserId, String newName, String newFullName, String newEMailAddress, String newPasswordBytes, Timestamp newPasswordDate, boolean newChangePassword, int newResetStrikes, boolean newUserReadOnly, boolean newUserDisabled, boolean newPasswordNeverExpires, String newExternalSystemUserName, String newLogonAlias, int newVersionNum, Collection newUserRoles, Collection newUserPreferences, Collection newDataEntryProfiles, Collection newChallengeQuestions, Collection newResetLink, boolean newNewFeaturesEnabled, boolean newButtonsVisible, boolean newShowBudgetActivity, boolean newNewView, boolean roadmapAvailable, String mobilePIN, String salt)
	{
		mUserId = newUserId;
		mName = newName;
		mFullName = newFullName;
		mEMailAddress = newEMailAddress;
		mPasswordBytes = newPasswordBytes;
		mMobilePIN = mobilePIN;
		mSalt = salt;
		mPasswordDate = newPasswordDate;
		mChangePassword = newChangePassword;
		mResetStrikes = newResetStrikes;
		mUserReadOnly = newUserReadOnly;
		mNewFeaturesEnabled = newNewFeaturesEnabled;
        mButtonsVisible = newButtonsVisible;
        mRoadMapAvailable = roadmapAvailable;
        mShowBudgetActivity = newShowBudgetActivity;
        mNewView = newNewView;
		mUserDisabled = newUserDisabled;
		mPasswordNeverExpires = newPasswordNeverExpires;
		mExternalSystemUserName = newExternalSystemUserName;
		mLogonAlias = newLogonAlias;
		mVersionNum = newVersionNum;
		setUserRoles(newUserRoles);
		setUserPreferences(newUserPreferences);
		setDataEntryProfiles(newDataEntryProfiles);
		setChallengeQuestions(newChallengeQuestions);
		setResetLink(newResetLink);
	}

	public void setUserRoles(Collection<UserRoleEVO> items)
	{
		if (items != null)
		{
			if (mUserRoles == null)
				mUserRoles = new HashMap();
			else {
				mUserRoles.clear();
			}
			for (UserRoleEVO child : items) {
				mUserRoles.put(child.getPK(), child);
			}

		}
		else
		{
			mUserRoles = null;
		}
	}

	public void setUserPreferences(Collection<UserPreferenceEVO> items)
	{
		if (items != null)
		{
			if (mUserPreferences == null)
				mUserPreferences = new HashMap();
			else {
				mUserPreferences.clear();
			}
			for (UserPreferenceEVO child : items) {
				mUserPreferences.put(child.getPK(), child);
			}

		}
		else
		{
			mUserPreferences = null;
		}
	}

	public void setDataEntryProfiles(Collection<DataEntryProfileEVO> items)
	{
		if (items != null)
		{
			if (mDataEntryProfiles == null)
				mDataEntryProfiles = new HashMap();
			else {
				mDataEntryProfiles.clear();
			}
			for (DataEntryProfileEVO child : items) {
				mDataEntryProfiles.put(child.getPK(), child);
			}

		}
		else
		{
			mDataEntryProfiles = null;
		}
	}

	public void setChallengeQuestions(Collection<ChallengeQuestionEVO> items)
	{
		if (items != null)
		{
			if (mChallengeQuestions == null)
				mChallengeQuestions = new HashMap();
			else {
				mChallengeQuestions.clear();
			}
			for (ChallengeQuestionEVO child : items) {
				mChallengeQuestions.put(child.getPK(), child);
			}

		}
		else
		{
			mChallengeQuestions = null;
		}
	}

	public void setResetLink(Collection<UserResetLinkEVO> items)
	{
		if (items != null)
		{
			if (mResetLink == null)
				mResetLink = new HashMap();
			else {
				mResetLink.clear();
			}
			for (UserResetLinkEVO child : items) {
				mResetLink.put(child.getPK(), child);
			}

		}
		else
		{
			mResetLink = null;
		}
	}

	public UserPK getPK()
	{
		if (mPK == null)
		{
			mPK = new UserPK(mUserId);
		}

		return mPK;
	}

	public boolean isModified()
	{
		return mModified;
	}

	public int getUserId()
	{
		return mUserId;
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

	public String getMobilePIN()
	{
		return mMobilePIN;
	}
	
	public String getSalt()
	{
		return mSalt;
	}
	
	public Timestamp getPasswordDate()
	{
		return mPasswordDate;
	}

	public boolean getChangePassword()
	{
		return mChangePassword;
	}

	public int getResetStrikes()
	{
		return mResetStrikes;
	}

	public boolean getUserReadOnly()
	{
		return mUserReadOnly;
	}

	public boolean getUserDisabled()
	{
		return mUserDisabled;
	}

	public boolean getPasswordNeverExpires()
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

	public int getVersionNum()
	{
		return mVersionNum;
	}

	public int getUpdatedByUserId()
	{
		return mUpdatedByUserId;
	}

	public Timestamp getUpdatedTime()
	{
		return mUpdatedTime;
	}

	public Timestamp getCreatedTime()
	{
		return mCreatedTime;
	}

	public void setUserId(int newUserId)
	{
		if (mUserId == newUserId)
			return;
		mModified = true;
		mUserId = newUserId;
		mPK = null;
	}

	public void setChangePassword(boolean newChangePassword)
	{
		if (mChangePassword == newChangePassword)
			return;
		mModified = true;
		mChangePassword = newChangePassword;
	}

	public void setResetStrikes(int newResetStrikes)
	{
		if (mResetStrikes == newResetStrikes)
			return;
		mModified = true;
		mResetStrikes = newResetStrikes;
	}
	
	public void setUserReadOnly(boolean newUserReadOnly)
	{
		if (mUserReadOnly == newUserReadOnly)
			return;
		mModified = true;
		mUserReadOnly = newUserReadOnly;
	}

	public void setUserDisabled(boolean newUserDisabled)
	{
		if (mUserDisabled == newUserDisabled)
			return;
		mModified = true;
		mUserDisabled = newUserDisabled;
	}

	public void setPasswordNeverExpires(boolean newPasswordNeverExpires)
	{
		if (mPasswordNeverExpires == newPasswordNeverExpires)
			return;
		mModified = true;
		mPasswordNeverExpires = newPasswordNeverExpires;
	}

	public void setVersionNum(int newVersionNum)
	{
		if (mVersionNum == newVersionNum)
			return;
		mModified = true;
		mVersionNum = newVersionNum;
	}

	public void setUpdatedByUserId(int newUpdatedByUserId)
	{
		mUpdatedByUserId = newUpdatedByUserId;
	}

	public void setName(String newName)
	{
		if (((mName != null) && (newName == null)) || ((mName == null) && (newName != null)) || ((mName != null) && (newName != null) && (!mName.equals(newName))))
		{
			mName = newName;
			mModified = true;
		}
	}

	public void setFullName(String newFullName)
	{
		if (((mFullName != null) && (newFullName == null)) || ((mFullName == null) && (newFullName != null)) || ((mFullName != null) && (newFullName != null) && (!mFullName.equals(newFullName))))
		{
			mFullName = newFullName;
			mModified = true;
		}
	}

	public void setEMailAddress(String newEMailAddress)
	{
		if (((mEMailAddress != null) && (newEMailAddress == null)) || ((mEMailAddress == null) && (newEMailAddress != null)) || ((mEMailAddress != null) && (newEMailAddress != null) && (!mEMailAddress.equals(newEMailAddress))))
		{
			mEMailAddress = newEMailAddress;
			mModified = true;
		}
	}

	public void setPasswordBytes(String newPasswordBytes)
	{
		if (((mPasswordBytes != null) && (newPasswordBytes == null)) || ((mPasswordBytes == null) && (newPasswordBytes != null)) || ((mPasswordBytes != null) && (newPasswordBytes != null) && (!mPasswordBytes.equals(newPasswordBytes))))
		{
			mPasswordBytes = newPasswordBytes;
			mModified = true;
		}
	}
	
	public void setMobilePIN(String newMobilePIN)
	{
		if (((mMobilePIN != null) && (newMobilePIN == null)) || ((mMobilePIN == null) && (newMobilePIN != null)) || ((mMobilePIN != null) && (newMobilePIN != null) && (!mMobilePIN.equals(newMobilePIN))))
		{
			mMobilePIN = newMobilePIN;
			mModified = true;
		}
	}
	
	public void setSalt(String newSalt)
	{
		if (((mSalt != null) && (newSalt == null)) || ((mSalt == null) && (newSalt != null)) || ((mSalt != null) && (newSalt != null) && (!mSalt.equals(newSalt))))
		{
			mSalt = newSalt;
			mModified = true;
		}
	}

	public void setPasswordDate(Timestamp newPasswordDate)
	{
		if (((mPasswordDate != null) && (newPasswordDate == null)) || ((mPasswordDate == null) && (newPasswordDate != null)) || ((mPasswordDate != null) && (newPasswordDate != null) && (!mPasswordDate.equals(newPasswordDate))))
		{
			mPasswordDate = newPasswordDate;
			mModified = true;
		}
	}

	public void setExternalSystemUserName(String newExternalSystemUserName)
	{
		if (((mExternalSystemUserName != null) && (newExternalSystemUserName == null)) || ((mExternalSystemUserName == null) && (newExternalSystemUserName != null)) || ((mExternalSystemUserName != null) && (newExternalSystemUserName != null) && (!mExternalSystemUserName.equals(newExternalSystemUserName))))
		{
			mExternalSystemUserName = newExternalSystemUserName;
			mModified = true;
		}
	}

	public void setLogonAlias(String newLogonAlias)
	{
		if (((mLogonAlias != null) && (newLogonAlias == null)) || ((mLogonAlias == null) && (newLogonAlias != null)) || ((mLogonAlias != null) && (newLogonAlias != null) && (!mLogonAlias.equals(newLogonAlias))))
		{
			mLogonAlias = newLogonAlias;
			mModified = true;
		}
	}

	protected void setUpdatedTime(Timestamp newUpdatedTime)
	{
		mUpdatedTime = newUpdatedTime;
	}

	protected void setCreatedTime(Timestamp newCreatedTime)
	{
		mCreatedTime = newCreatedTime;
	}

	public void setDetails(UserEVO newDetails)
	{
		setUserId(newDetails.getUserId());
		setName(newDetails.getName());
		setFullName(newDetails.getFullName());
		setEMailAddress(newDetails.getEMailAddress());
		setPasswordBytes(newDetails.getPasswordBytes());
		setPasswordDate(newDetails.getPasswordDate());
		setChangePassword(newDetails.getChangePassword());
		setResetStrikes(newDetails.getResetStrikes());
		setUserReadOnly(newDetails.getUserReadOnly());
		setNewFeaturesEnabled(newDetails.isNewFeaturesEnabled());
        setButtonsVisible(newDetails.areButtonsVisible());
        setRoadMapAvailable(newDetails.getRoadMapAvailable());
        setShowBudgetActivity(newDetails.isShowBudgetActivity());
        setNewView(newDetails.isNewView());
		setUserDisabled(newDetails.getUserDisabled());
		setPasswordNeverExpires(newDetails.getPasswordNeverExpires());
		setExternalSystemUserName(newDetails.getExternalSystemUserName());
		setLogonAlias(newDetails.getLogonAlias());
		setVersionNum(newDetails.getVersionNum());
		setUpdatedByUserId(newDetails.getUpdatedByUserId());
		setUpdatedTime(newDetails.getUpdatedTime());
		setCreatedTime(newDetails.getCreatedTime());
	}

	public UserEVO deepClone()
	{
		UserEVO cloned = null;
		try
		{
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);

			oos.writeObject(this);
			oos.flush();
			oos.close();

			ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			cloned = (UserEVO) ois.readObject();
			ois.close();
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
		return cloned;
	}

	public void prepareForInsert()
	{
		boolean newKey = false;

		if (mUserId > 0)
		{
			newKey = true;
			mUserId = 0;
		}
		else if (mUserId < 1) {
			newKey = true;
		}

		setVersionNum(0);

		if (mUserRoles != null)
		{
			Iterator iter = new ArrayList(mUserRoles.values()).iterator();
			while (iter.hasNext())
			{
				UserRoleEVO item = (UserRoleEVO) iter.next();
				if (newKey)
					item.setInsertPending();
				item.prepareForInsert(this);
			}
		}
		if (mUserPreferences != null)
		{
			Iterator iter = new ArrayList(mUserPreferences.values()).iterator();
			while (iter.hasNext())
			{
				UserPreferenceEVO item = (UserPreferenceEVO) iter.next();
				if (newKey)
					item.setInsertPending();
				item.prepareForInsert(this);
			}
		}
		if (mDataEntryProfiles != null)
		{
			Iterator iter = new ArrayList(mDataEntryProfiles.values()).iterator();
			while (iter.hasNext())
			{
				DataEntryProfileEVO item = (DataEntryProfileEVO) iter.next();
				if (newKey)
					item.setInsertPending();
				item.prepareForInsert(this);
			}
		}
		if (mChallengeQuestions != null)
		{
			Iterator iter = new ArrayList(mChallengeQuestions.values()).iterator();
			while (iter.hasNext())
			{
				ChallengeQuestionEVO item = (ChallengeQuestionEVO) iter.next();
				if (newKey)
					item.setInsertPending();
				item.prepareForInsert(this);
			}
		}
		if (mResetLink != null)
		{
			Iterator iter = new ArrayList(mResetLink.values()).iterator();
			while (iter.hasNext())
			{
				UserResetLinkEVO item = (UserResetLinkEVO) iter.next();
				if (newKey)
					item.setInsertPending();
				item.prepareForInsert(this);
			}
		}
	}

	public int getInsertCount(int startCount)
	{
		int returnCount = startCount;

		if (mUserId < 1)
			returnCount++;
		if (mUserRoles != null)
		{
			Iterator iter = mUserRoles.values().iterator();
			while (iter.hasNext())
			{
				UserRoleEVO item = (UserRoleEVO) iter.next();
				returnCount = item.getInsertCount(returnCount);
			}
		}
		if (mUserPreferences != null)
		{
			Iterator iter = mUserPreferences.values().iterator();
			while (iter.hasNext())
			{
				UserPreferenceEVO item = (UserPreferenceEVO) iter.next();
				returnCount = item.getInsertCount(returnCount);
			}
		}
		if (mDataEntryProfiles != null)
		{
			Iterator iter = mDataEntryProfiles.values().iterator();
			while (iter.hasNext())
			{
				DataEntryProfileEVO item = (DataEntryProfileEVO) iter.next();
				returnCount = item.getInsertCount(returnCount);
			}
		}
		if (mChallengeQuestions != null)
		{
			Iterator iter = mChallengeQuestions.values().iterator();
			while (iter.hasNext())
			{
				ChallengeQuestionEVO item = (ChallengeQuestionEVO) iter.next();
				returnCount = item.getInsertCount(returnCount);
			}
		}
		if (mResetLink != null)
		{
			Iterator iter = mResetLink.values().iterator();
			while (iter.hasNext())
			{
				UserResetLinkEVO item = (UserResetLinkEVO) iter.next();
				returnCount = item.getInsertCount(returnCount);
			}
		}
		return returnCount;
	}

	public int assignNextKey(int startKey)
	{
		int nextKey = startKey;

		if (mUserId < 1)
		{
			mUserId = nextKey;
			nextKey++;
		}

		if (mUserRoles != null)
		{
			Iterator iter = new ArrayList(mUserRoles.values()).iterator();
			while (iter.hasNext())
			{
				UserRoleEVO item = (UserRoleEVO) iter.next();

				changeKey(item, mUserId, item.getRoleId());

				nextKey = item.assignNextKey(this, nextKey);
			}

		}

		if (mUserPreferences != null)
		{
			Iterator iter = new ArrayList(mUserPreferences.values()).iterator();
			while (iter.hasNext())
			{
				UserPreferenceEVO item = (UserPreferenceEVO) iter.next();

				item.setUserId(mUserId);

				nextKey = item.assignNextKey(this, nextKey);
			}

		}

		if (mDataEntryProfiles != null)
		{
			Iterator iter = new ArrayList(mDataEntryProfiles.values()).iterator();
			while (iter.hasNext())
			{
				DataEntryProfileEVO item = (DataEntryProfileEVO) iter.next();

				item.setUserId(mUserId);

				nextKey = item.assignNextKey(this, nextKey);
			}

		}

		if (mChallengeQuestions != null)
		{
			Iterator iter = new ArrayList(mChallengeQuestions.values()).iterator();
			while (iter.hasNext())
			{
				ChallengeQuestionEVO item = (ChallengeQuestionEVO) iter.next();

				changeKey(item, mUserId, item.getQuestionText());

				nextKey = item.assignNextKey(this, nextKey);
			}

		}

		if (mResetLink != null)
		{
			Iterator iter = new ArrayList(mResetLink.values()).iterator();
			while (iter.hasNext())
			{
				UserResetLinkEVO item = (UserResetLinkEVO) iter.next();

				changeKey(item, mUserId, item.getPwdLink());

				nextKey = item.assignNextKey(this, nextKey);
			}
		}

		return nextKey;
	}

	public Collection<UserRoleEVO> getUserRoles()
	{
		return mUserRoles != null ? mUserRoles.values() : null;
	}

	public Map<UserRolePK, UserRoleEVO> getUserRolesMap()
	{
		return mUserRoles;
	}

	public void loadUserRolesItem(UserRoleEVO newItem)
	{
		if (mUserRoles == null) {
			mUserRoles = new HashMap();
		}
		mUserRoles.put(newItem.getPK(), newItem);
	}

	public void addUserRolesItem(UserRoleEVO newItem)
	{
		if (mUserRoles == null) {
			mUserRoles = new HashMap();
		}
		UserRolePK newPK = newItem.getPK();

		if (getUserRolesItem(newPK) != null) {
			throw new RuntimeException("addUserRolesItem: key already in list");
		}
		newItem.setInsertPending();

		mUserRoles.put(newPK, newItem);
	}

	public void changeUserRolesItem(UserRoleEVO changedItem)
	{
		if (mUserRoles == null) {
			throw new RuntimeException("changeUserRolesItem: no items in collection");
		}
		UserRolePK changedPK = changedItem.getPK();

		UserRoleEVO listItem = getUserRolesItem(changedPK);
		if (listItem == null) {
			throw new RuntimeException("changeUserRolesItem: item not in list");
		}
		listItem.setDetails(changedItem);
	}

	public void deleteUserRolesItem(UserRolePK removePK)
	{
		UserRoleEVO listItem = getUserRolesItem(removePK);

		if (listItem == null) {
			throw new RuntimeException("removeUserRolesItem: item not in list");
		}
		listItem.setDeletePending();
	}

	public UserRoleEVO getUserRolesItem(UserRolePK pk)
	{
		return (UserRoleEVO) mUserRoles.get(pk);
	}

	public UserRoleEVO getUserRolesItem()
	{
		if (mUserRoles.size() != 1) {
			throw new RuntimeException("should be 1 item but size=" + mUserRoles.size());
		}
		Iterator iter = mUserRoles.values().iterator();
		return (UserRoleEVO) iter.next();
	}

	public Collection<UserPreferenceEVO> getUserPreferences()
	{
		return mUserPreferences != null ? mUserPreferences.values() : null;
	}

	public Map<UserPreferencePK, UserPreferenceEVO> getUserPreferencesMap()
	{
		return mUserPreferences;
	}

	public void loadUserPreferencesItem(UserPreferenceEVO newItem)
	{
		if (mUserPreferences == null) {
			mUserPreferences = new HashMap();
		}
		mUserPreferences.put(newItem.getPK(), newItem);
	}

	public void addUserPreferencesItem(UserPreferenceEVO newItem)
	{
		if (mUserPreferences == null) {
			mUserPreferences = new HashMap();
		}
		UserPreferencePK newPK = newItem.getPK();

		if (getUserPreferencesItem(newPK) != null) {
			throw new RuntimeException("addUserPreferencesItem: key already in list");
		}
		newItem.setInsertPending();

		mUserPreferences.put(newPK, newItem);
	}

	public void changeUserPreferencesItem(UserPreferenceEVO changedItem)
	{
		if (mUserPreferences == null) {
			throw new RuntimeException("changeUserPreferencesItem: no items in collection");
		}
		UserPreferencePK changedPK = changedItem.getPK();

		UserPreferenceEVO listItem = getUserPreferencesItem(changedPK);
		if (listItem == null) {
			throw new RuntimeException("changeUserPreferencesItem: item not in list");
		}
		listItem.setDetails(changedItem);
	}

	public void deleteUserPreferencesItem(UserPreferencePK removePK)
	{
		UserPreferenceEVO listItem = getUserPreferencesItem(removePK);

		if (listItem == null) {
			throw new RuntimeException("removeUserPreferencesItem: item not in list");
		}
		listItem.setDeletePending();
	}

	public UserPreferenceEVO getUserPreferencesItem(UserPreferencePK pk)
	{
		return (UserPreferenceEVO) mUserPreferences.get(pk);
	}

	public UserPreferenceEVO getUserPreferencesItem()
	{
		if (mUserPreferences.size() != 1) {
			throw new RuntimeException("should be 1 item but size=" + mUserPreferences.size());
		}
		Iterator iter = mUserPreferences.values().iterator();
		return (UserPreferenceEVO) iter.next();
	}

	public Collection<DataEntryProfileEVO> getDataEntryProfiles()
	{
		return mDataEntryProfiles != null ? mDataEntryProfiles.values() : null;
	}

	public Map<DataEntryProfilePK, DataEntryProfileEVO> getDataEntryProfilesMap()
	{
		return mDataEntryProfiles;
	}

	public void loadDataEntryProfilesItem(DataEntryProfileEVO newItem)
	{
		if (mDataEntryProfiles == null) {
			mDataEntryProfiles = new HashMap();
		}
		mDataEntryProfiles.put(newItem.getPK(), newItem);
	}

	public void addDataEntryProfilesItem(DataEntryProfileEVO newItem)
	{
		if (mDataEntryProfiles == null) {
			mDataEntryProfiles = new HashMap();
		}
		DataEntryProfilePK newPK = newItem.getPK();

		if (getDataEntryProfilesItem(newPK) != null) {
			throw new RuntimeException("addDataEntryProfilesItem: key already in list");
		}
		newItem.setInsertPending();

		mDataEntryProfiles.put(newPK, newItem);
	}

	public void changeDataEntryProfilesItem(DataEntryProfileEVO changedItem)
	{
		if (mDataEntryProfiles == null) {
			throw new RuntimeException("changeDataEntryProfilesItem: no items in collection");
		}
		DataEntryProfilePK changedPK = changedItem.getPK();

		DataEntryProfileEVO listItem = getDataEntryProfilesItem(changedPK);
		if (listItem == null) {
			throw new RuntimeException("changeDataEntryProfilesItem: item not in list");
		}
		listItem.setDetails(changedItem);
	}

	public void deleteDataEntryProfilesItem(DataEntryProfilePK removePK)
	{
		DataEntryProfileEVO listItem = getDataEntryProfilesItem(removePK);

		if (listItem == null) {
			throw new RuntimeException("removeDataEntryProfilesItem: item not in list");
		}
		listItem.setDeletePending();
	}

	public DataEntryProfileEVO getDataEntryProfilesItem(DataEntryProfilePK pk)
	{
		return (DataEntryProfileEVO) mDataEntryProfiles.get(pk);
	}

	public DataEntryProfileEVO getDataEntryProfilesItem()
	{
		if (mDataEntryProfiles.size() != 1) {
			throw new RuntimeException("should be 1 item but size=" + mDataEntryProfiles.size());
		}
		Iterator iter = mDataEntryProfiles.values().iterator();
		return (DataEntryProfileEVO) iter.next();
	}

	public Collection<ChallengeQuestionEVO> getChallengeQuestions()
	{
		return mChallengeQuestions != null ? mChallengeQuestions.values() : null;
	}

	public Map<ChallengeQuestionPK, ChallengeQuestionEVO> getChallengeQuestionsMap()
	{
		return mChallengeQuestions;
	}

	public void loadChallengeQuestionsItem(ChallengeQuestionEVO newItem)
	{
		if (mChallengeQuestions == null) {
			mChallengeQuestions = new HashMap();
		}
		mChallengeQuestions.put(newItem.getPK(), newItem);
	}

	public void addChallengeQuestionsItem(ChallengeQuestionEVO newItem)
	{
		if (mChallengeQuestions == null) {
			mChallengeQuestions = new HashMap();
		}
		ChallengeQuestionPK newPK = newItem.getPK();

		if (getChallengeQuestionsItem(newPK) != null) {
			throw new RuntimeException("addChallengeQuestionsItem: key already in list");
		}
		newItem.setInsertPending();

		mChallengeQuestions.put(newPK, newItem);
	}

	public void changeChallengeQuestionsItem(ChallengeQuestionEVO changedItem)
	{
		if (mChallengeQuestions == null) {
			throw new RuntimeException("changeChallengeQuestionsItem: no items in collection");
		}
		ChallengeQuestionPK changedPK = changedItem.getPK();

		ChallengeQuestionEVO listItem = getChallengeQuestionsItem(changedPK);
		if (listItem == null) {
			throw new RuntimeException("changeChallengeQuestionsItem: item not in list");
		}
		listItem.setDetails(changedItem);
	}

	public void deleteChallengeQuestionsItem(ChallengeQuestionPK removePK)
	{
		ChallengeQuestionEVO listItem = getChallengeQuestionsItem(removePK);

		if (listItem == null) {
			throw new RuntimeException("removeChallengeQuestionsItem: item not in list");
		}
		listItem.setDeletePending();
	}

	public ChallengeQuestionEVO getChallengeQuestionsItem(ChallengeQuestionPK pk)
	{
		return (ChallengeQuestionEVO) mChallengeQuestions.get(pk);
	}

	public ChallengeQuestionEVO getChallengeQuestionsItem()
	{
		if (mChallengeQuestions.size() != 1) {
			throw new RuntimeException("should be 1 item but size=" + mChallengeQuestions.size());
		}
		Iterator iter = mChallengeQuestions.values().iterator();
		return (ChallengeQuestionEVO) iter.next();
	}

	public Collection<UserResetLinkEVO> getResetLink()
	{
		return mResetLink != null ? mResetLink.values() : null;
	}

	public Map<UserResetLinkPK, UserResetLinkEVO> getResetLinkMap()
	{
		return mResetLink;
	}

	public void loadResetLinkItem(UserResetLinkEVO newItem)
	{
		if (mResetLink == null) {
			mResetLink = new HashMap();
		}
		mResetLink.put(newItem.getPK(), newItem);
	}

	public void addResetLinkItem(UserResetLinkEVO newItem)
	{
		if (mResetLink == null) {
			mResetLink = new HashMap();
		}
		UserResetLinkPK newPK = newItem.getPK();

		if (getResetLinkItem(newPK) != null) {
			throw new RuntimeException("addResetLinkItem: key already in list");
		}
		newItem.setInsertPending();

		mResetLink.put(newPK, newItem);
	}

	public void changeResetLinkItem(UserResetLinkEVO changedItem)
	{
		if (mResetLink == null) {
			throw new RuntimeException("changeResetLinkItem: no items in collection");
		}
		UserResetLinkPK changedPK = changedItem.getPK();

		UserResetLinkEVO listItem = getResetLinkItem(changedPK);
		if (listItem == null) {
			throw new RuntimeException("changeResetLinkItem: item not in list");
		}
		listItem.setDetails(changedItem);
	}

	public void deleteResetLinkItem(UserResetLinkPK removePK)
	{
		UserResetLinkEVO listItem = getResetLinkItem(removePK);

		if (listItem == null) {
			throw new RuntimeException("removeResetLinkItem: item not in list");
		}
		listItem.setDeletePending();
	}

	public UserResetLinkEVO getResetLinkItem(UserResetLinkPK pk)
	{
		return (UserResetLinkEVO) mResetLink.get(pk);
	}

	public UserResetLinkEVO getResetLinkItem()
	{
		if (mResetLink.size() != 1) {
			throw new RuntimeException("should be 1 item but size=" + mResetLink.size());
		}
		Iterator iter = mResetLink.values().iterator();
		return (UserResetLinkEVO) iter.next();
	}

	protected void reset()
	{
		mModified = false;
	}

	public UserRef getEntityRef()
	{
		return new UserRefImpl(getPK(), mName);
	}

	public void postCreateInit()
	{
		mUserRolesAllItemsLoaded = true;
		if (mUserRoles == null)
			mUserRoles = new HashMap();
		mUserPreferencesAllItemsLoaded = true;
		if (mUserPreferences == null)
			mUserPreferences = new HashMap();
		mDataEntryProfilesAllItemsLoaded = true;
		if (mDataEntryProfiles == null) {
			mDataEntryProfiles = new HashMap();
		}
		else {
			for (DataEntryProfileEVO child : mDataEntryProfiles.values()) {
				child.postCreateInit();
			}

		}

		mChallengeQuestionsAllItemsLoaded = true;
		if (mChallengeQuestions == null)
			mChallengeQuestions = new HashMap();
		mResetLinkAllItemsLoaded = true;
		if (mResetLink == null)
			mResetLink = new HashMap();
	}

	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("UserId=");
		sb.append(String.valueOf(mUserId));
		sb.append(' ');
		sb.append("Name=");
		sb.append(String.valueOf(mName));
		sb.append(' ');
		sb.append("FullName=");
		sb.append(String.valueOf(mFullName));
		sb.append(' ');
		sb.append("EMailAddress=");
		sb.append(String.valueOf(mEMailAddress));
		sb.append(' ');
		sb.append("MobilePIN=");
		sb.append(String.valueOf(mMobilePIN));
		sb.append(' ');
		sb.append("Salt=");
		sb.append(String.valueOf(mSalt));
		sb.append(' ');
		sb.append("PasswordBytes=");
		sb.append(String.valueOf(mPasswordBytes));
		sb.append(' ');
		sb.append("PasswordDate=");
		sb.append(String.valueOf(mPasswordDate));
		sb.append(' ');
		sb.append("ChangePassword=");
		sb.append(String.valueOf(mChangePassword));
		sb.append(' ');
		sb.append("ResetStrikes=");
		sb.append(String.valueOf(mResetStrikes));
		sb.append(' ');
		sb.append("UserDisabled=");
		sb.append(String.valueOf(mUserDisabled));
		sb.append(' ');
		sb.append("PasswordNeverExpires=");
		sb.append(String.valueOf(mPasswordNeverExpires));
		sb.append(' ');
		sb.append("ExternalSystemUserName=");
		sb.append(String.valueOf(mExternalSystemUserName));
		sb.append(' ');
		sb.append("LogonAlias=");
		sb.append(String.valueOf(mLogonAlias));
		sb.append(' ');
		sb.append("VersionNum=");
		sb.append(String.valueOf(mVersionNum));
		sb.append(' ');
		sb.append("UpdatedByUserId=");
		sb.append(String.valueOf(mUpdatedByUserId));
		sb.append(' ');
		sb.append("UpdatedTime=");
		sb.append(String.valueOf(mUpdatedTime));
		sb.append(' ');
		sb.append("CreatedTime=");
		sb.append(String.valueOf(mCreatedTime));
		sb.append(' ');
		if (mModified)
			sb.append("modified ");
		return sb.toString();
	}

	public String print()
	{
		return print(0);
	}

	public String print(int indent)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < indent; i++)
			sb.append(' ');
		sb.append("User: ");
		sb.append(toString());
		if ((mUserRolesAllItemsLoaded) || (mUserRoles != null))
		{
			sb.delete(indent, sb.length());
			sb.append(" - UserRoles: allItemsLoaded=");
			sb.append(String.valueOf(mUserRolesAllItemsLoaded));
			sb.append(" items=");
			if (mUserRoles == null)
				sb.append("null");
			else
				sb.append(String.valueOf(mUserRoles.size()));
		}
		if ((mUserPreferencesAllItemsLoaded) || (mUserPreferences != null))
		{
			sb.delete(indent, sb.length());
			sb.append(" - UserPreferences: allItemsLoaded=");
			sb.append(String.valueOf(mUserPreferencesAllItemsLoaded));
			sb.append(" items=");
			if (mUserPreferences == null)
				sb.append("null");
			else
				sb.append(String.valueOf(mUserPreferences.size()));
		}
		if ((mDataEntryProfilesAllItemsLoaded) || (mDataEntryProfiles != null))
		{
			sb.delete(indent, sb.length());
			sb.append(" - DataEntryProfiles: allItemsLoaded=");
			sb.append(String.valueOf(mDataEntryProfilesAllItemsLoaded));
			sb.append(" items=");
			if (mDataEntryProfiles == null)
				sb.append("null");
			else
				sb.append(String.valueOf(mDataEntryProfiles.size()));
		}
		if ((mChallengeQuestionsAllItemsLoaded) || (mChallengeQuestions != null))
		{
			sb.delete(indent, sb.length());
			sb.append(" - ChallengeQuestions: allItemsLoaded=");
			sb.append(String.valueOf(mChallengeQuestionsAllItemsLoaded));
			sb.append(" items=");
			if (mChallengeQuestions == null)
				sb.append("null");
			else
				sb.append(String.valueOf(mChallengeQuestions.size()));
		}
		if ((mResetLinkAllItemsLoaded) || (mResetLink != null))
		{
			sb.delete(indent, sb.length());
			sb.append(" - ResetLink: allItemsLoaded=");
			sb.append(String.valueOf(mResetLinkAllItemsLoaded));
			sb.append(" items=");
			if (mResetLink == null)
				sb.append("null");
			else
				sb.append(String.valueOf(mResetLink.size()));
		}
		if (mUserRoles != null)
			for (UserRoleEVO listItem : mUserRoles.values())
				listItem.print(indent + 4);
		if (mUserPreferences != null)
			for (UserPreferenceEVO listItem : mUserPreferences.values())
				listItem.print(indent + 4);
		if (mDataEntryProfiles != null)
			for (DataEntryProfileEVO listItem : mDataEntryProfiles.values())
				listItem.print(indent + 4);
		if (mChallengeQuestions != null)
			for (ChallengeQuestionEVO listItem : mChallengeQuestions.values())
				listItem.print(indent + 4);
		if (mResetLink != null)
			for (UserResetLinkEVO listItem : mResetLink.values())
				listItem.print(indent + 4);
		return sb.toString();
	}

	public void changeKey(UserRoleEVO child, int newUserId, int newRoleId)
	{
		if (getUserRolesItem(child.getPK()) != child)
			throw new IllegalStateException("changeKey child not found in parent");
		mUserRoles.remove(child.getPK());
		child.setUserId(newUserId);
		child.setRoleId(newRoleId);
		mUserRoles.put(child.getPK(), child);
	}

	public void changeKey(UserPreferenceEVO child, int newUserPrefId)
	{
		if (getUserPreferencesItem(child.getPK()) != child)
			throw new IllegalStateException("changeKey child not found in parent");
		mUserPreferences.remove(child.getPK());
		child.setUserPrefId(newUserPrefId);
		mUserPreferences.put(child.getPK(), child);
	}

	public void changeKey(DataEntryProfileEVO child, int newDataEntryProfileId)
	{
		if (getDataEntryProfilesItem(child.getPK()) != child)
			throw new IllegalStateException("changeKey child not found in parent");
		mDataEntryProfiles.remove(child.getPK());
		child.setDataEntryProfileId(newDataEntryProfileId);
		mDataEntryProfiles.put(child.getPK(), child);
	}

	public void changeKey(ChallengeQuestionEVO child, int newUserId, String newQuestionText)
	{
		if (getChallengeQuestionsItem(child.getPK()) != child)
			throw new IllegalStateException("changeKey child not found in parent");
		mChallengeQuestions.remove(child.getPK());
		child.setUserId(newUserId);
		child.setQuestionText(newQuestionText);
		mChallengeQuestions.put(child.getPK(), child);
	}

	public void changeKey(UserResetLinkEVO child, int newUserId, String newPwdLink)
	{
		if (getResetLinkItem(child.getPK()) != child)
			throw new IllegalStateException("changeKey child not found in parent");
		mResetLink.remove(child.getPK());
		child.setUserId(newUserId);
		child.setPwdLink(newPwdLink);
		mResetLink.put(child.getPK(), child);
	}

	public void setUserRolesAllItemsLoaded(boolean allItemsLoaded)
	{
		mUserRolesAllItemsLoaded = allItemsLoaded;
	}

	public boolean isUserRolesAllItemsLoaded() {
		return mUserRolesAllItemsLoaded;
	}

	public void setUserPreferencesAllItemsLoaded(boolean allItemsLoaded)
	{
		mUserPreferencesAllItemsLoaded = allItemsLoaded;
	}

	public boolean isUserPreferencesAllItemsLoaded() {
		return mUserPreferencesAllItemsLoaded;
	}

	public void setDataEntryProfilesAllItemsLoaded(boolean allItemsLoaded)
	{
		mDataEntryProfilesAllItemsLoaded = allItemsLoaded;
	}

	public boolean isDataEntryProfilesAllItemsLoaded() {
		return mDataEntryProfilesAllItemsLoaded;
	}

	public void setChallengeQuestionsAllItemsLoaded(boolean allItemsLoaded)
	{
		mChallengeQuestionsAllItemsLoaded = allItemsLoaded;
	}

	public boolean isChallengeQuestionsAllItemsLoaded() {
		return mChallengeQuestionsAllItemsLoaded;
	}

	public void setResetLinkAllItemsLoaded(boolean allItemsLoaded)
	{
		mResetLinkAllItemsLoaded = allItemsLoaded;
	}

	public boolean isResetLinkAllItemsLoaded() {
		return mResetLinkAllItemsLoaded;
	}

	public Map<UserRolePK, UserRoleEVO> getUserAdminAppRoles() {
		return mUserAdminAppRoles;
	}

	public void setUserAdminAppRoles(Map<UserRolePK, UserRoleEVO> mUserAdminAppRoles) {
		this.mUserAdminAppRoles = mUserAdminAppRoles;
	}

    public boolean isNewFeaturesEnabled() {
        return mNewFeaturesEnabled;
    }

    public void setNewFeaturesEnabled(boolean mNewFeaturesEnabled) {
        if (this.mNewFeaturesEnabled == mNewFeaturesEnabled)
            return;
        mModified = true;
        this.mNewFeaturesEnabled = mNewFeaturesEnabled;
    }

    public boolean areButtonsVisible() {
        return mButtonsVisible;
    }

    public void setButtonsVisible(boolean mButtonsVisible) {
        if (this.mButtonsVisible == mButtonsVisible)
            return;
        mModified = true;
        this.mButtonsVisible = mButtonsVisible;
    }

    public boolean isShowBudgetActivity() {
        return mShowBudgetActivity;
    }

    public void setShowBudgetActivity(boolean mShowBudgetActivity) {
        if (this.mShowBudgetActivity == mShowBudgetActivity)
            return;
        mModified = true;
        this.mShowBudgetActivity = mShowBudgetActivity;
    }
    
    public boolean isNewView() {
        return mNewView;
    }

    public void setNewView(boolean mNewView) {
        if (this.mNewView == mNewView)
            return;
        mModified = true;
        this.mNewView = mNewView;
    }

    public boolean getRoadMapAvailable() {
        return mRoadMapAvailable;
    }

    public void setRoadMapAvailable(boolean mNewRoadMapAvailable) {
        if(this.mRoadMapAvailable == mNewRoadMapAvailable)
               return;
        mModified = true;
        this.mRoadMapAvailable = mNewRoadMapAvailable;
    }
}