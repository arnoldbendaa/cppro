// Decompiled by: Fernflower v0.8.6
// Date: 12.08.2012 13:06:12
// Copyright: 2008-2012, Stiver
// Home page: http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.xmlform;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.xmlform.XmlForm;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XmlFormImpl implements XmlForm, Serializable, Cloneable {

	private List mUserList;
	private List mNamesList;
	EntityList mAvailableUsers;
	private Object mPrimaryKey;
	private String mVisId;
	private String mDescription;
	private byte[] mExcelFile;
	private String mJsonForm;
	private int mType;
	private boolean mDesignMode;
	private int mFinanceCubeId;
	private String mDefinition;
	private boolean mSecurityAccess;
	private int mVersionNum;

	public XmlFormImpl(Object paramKey) {
		this.mPrimaryKey = paramKey;
		this.mVisId = "";
		this.mDescription = "";
		this.mExcelFile = null;
		this.mType = 0;
		this.mDesignMode = false;
		this.mFinanceCubeId = 0;
		this.mDefinition = "";
		this.mSecurityAccess = false;
	}

	public Object getPrimaryKey() {
		return this.mPrimaryKey;
	}

	public void setPrimaryKey(Object paramKey) {
		this.mPrimaryKey = (XmlFormPK) paramKey;
	}

	public String getVisId() {
		return this.mVisId;
	}

	public String getDescription() {
		return this.mDescription;
	}

	public int getType() {
		return this.mType;
	}

	public boolean isDesignMode() {
		return this.mDesignMode;
	}

	public int getFinanceCubeId() {
		return this.mFinanceCubeId;
	}

	public String getDefinition() {
		return this.mDefinition;
	}

	public boolean isSecurityAccess() {
		return this.mSecurityAccess;
	}

	public void setVersionNum(int p) {
		this.mVersionNum = p;
	}

	public int getVersionNum() {
		return this.mVersionNum;
	}

	public void setVisId(String paramVisId) {
		this.mVisId = paramVisId;
	}

	public void setDescription(String paramDescription) {
		this.mDescription = paramDescription;
	}

	public void setType(int paramType) {
		this.mType = paramType;
	}

	public void setDesignMode(boolean paramDesignMode) {
		this.mDesignMode = paramDesignMode;
	}

	public void setFinanceCubeId(int paramFinanceCubeId) {
		this.mFinanceCubeId = paramFinanceCubeId;
	}

	public void setDefinition(String paramDefinition) {
		this.mDefinition = paramDefinition;
	}

	public void setSecurityAccess(boolean paramSecurityAccess) {
		this.mSecurityAccess = paramSecurityAccess;
	}

	public void setUserList(List userList) {
		this.mUserList = userList;
	}

	public List getUserList() {
		if (this.mUserList == null) {
			this.setUserList(new ArrayList());
		}
		return this.mUserList;
	}

	public void setAvailableUsers(EntityList availableUsers) {
		this.mAvailableUsers = availableUsers;
	}

	public EntityList getAvailableUsers() {
		return this.mAvailableUsers;
	}

	/**
	 * Get excel file
	 * 
	 * @return
	 */
	public byte[] getExcelFile() {
		return mExcelFile;
	}

	/**
	 * Set excel file
	 * 
	 * @param mExcelFile
	 * @return
	 */
	public void setExcelFile(byte[] mExcelFile) {
		this.mExcelFile = mExcelFile;
	}

	/**
	 * Increments version number
	 */
	@Override
	public int incrementVersionNumber() {
		return this.mVersionNum++;
	}

	public List getNamesList() {
		if (mNamesList == null) {
			setNamesList(new ArrayList());
		}
		return mNamesList;
	}

	public void setNamesList(List mNamesList) {
		this.mNamesList = mNamesList;
	}

	public String getJsonForm() {
		return mJsonForm;
	}

	public void setJsonForm(String mJsonForm) {
		this.mJsonForm = mJsonForm;
	}

}
