// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:27:37
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.xmlform;

import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.dto.xmlform.XmlFormRefImpl;
import com.cedar.cp.dto.xmlform.XmlFormUserLinkPK;
import com.cedar.cp.ejb.impl.xmlform.XmlFormUserLinkEVO;
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
import java.util.Map;

public class XmlFormEVO implements Serializable {

	private boolean mForceDelete = false;
	private transient XmlFormPK mPK;
	private int mXmlFormId;
	private String mVisId;
	private String mDescription;
	private byte[] mExcelFile = new byte[0];
	private String mJsonForm;
	private int mType;
	private boolean mDesignMode;
	private int mFinanceCubeId;
	private String mDefinition;
	private boolean mSecurityAccess;
	private int mVersionNum;
	private int mUpdatedByUserId;
	private Timestamp mUpdatedTime;
	private Timestamp mCreatedTime;
	private Map<XmlFormUserLinkPK, XmlFormUserLinkEVO> mUserList;
	protected boolean mUserListAllItemsLoaded;
	private boolean mModified;

	public XmlFormEVO() {
	}

	public XmlFormEVO(int newXmlFormId, String newVisId, String newDescription, int newType, boolean newDesignMode, int newFinanceCubeId, String newDefinition, byte[] newExcelFile, String newJsonForm, boolean newSecurityAccess, int newVersionNum, Collection newUserList) {
		this.mXmlFormId = newXmlFormId;
		this.mVisId = newVisId;
		this.mDescription = newDescription;
		this.mType = newType;
		this.mDesignMode = newDesignMode;
		this.mFinanceCubeId = newFinanceCubeId;
		this.mDefinition = newDefinition;
		this.mExcelFile = newExcelFile;
		this.mJsonForm = newJsonForm;
		this.mSecurityAccess = newSecurityAccess;
		this.mVersionNum = newVersionNum;
		this.setUserList(newUserList);
	}

	public void setUserList(Collection<XmlFormUserLinkEVO> items) {
		if (items != null) {
			if (this.mUserList == null) {
				this.mUserList = new HashMap();
			} else {
				this.mUserList.clear();
			}

			Iterator i$ = items.iterator();

			while (i$.hasNext()) {
				XmlFormUserLinkEVO child = (XmlFormUserLinkEVO) i$.next();
				this.mUserList.put(child.getPK(), child);
			}
		} else {
			this.mUserList = null;
		}

	}

	public XmlFormPK getPK() {
		if (this.mPK == null) {
			this.mPK = new XmlFormPK(this.mXmlFormId);
		}

		return this.mPK;
	}

	public boolean isModified() {
		return this.mModified;
	}

	public int getXmlFormId() {
		return this.mXmlFormId;
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

	public boolean getDesignMode() {
		return this.mDesignMode;
	}

	public int getFinanceCubeId() {
		return this.mFinanceCubeId;
	}

	public String getDefinition() {
		return this.mDefinition;
	}

	public boolean getSecurityAccess() {
		return this.mSecurityAccess;
	}

	public int getVersionNum() {
		return this.mVersionNum;
	}

	public int getUpdatedByUserId() {
		return this.mUpdatedByUserId;
	}

	public Timestamp getUpdatedTime() {
		return this.mUpdatedTime;
	}

	public Timestamp getCreatedTime() {
		return this.mCreatedTime;
	}

	public void setXmlFormId(int newXmlFormId) {
		if (this.mXmlFormId != newXmlFormId) {
			this.mModified = true;
			this.mXmlFormId = newXmlFormId;
			this.mPK = null;
		}
	}

	public void setType(int newType) {
		if (this.mType != newType) {
			this.mModified = true;
			this.mType = newType;
		}
	}

	public void setDesignMode(boolean newDesignMode) {
		if (this.mDesignMode != newDesignMode) {
			this.mModified = true;
			this.mDesignMode = newDesignMode;
		}
	}

	public void setFinanceCubeId(int newFinanceCubeId) {
		if (this.mFinanceCubeId != newFinanceCubeId) {
			this.mModified = true;
			this.mFinanceCubeId = newFinanceCubeId;
		}
	}

	public void setSecurityAccess(boolean newSecurityAccess) {
		if (this.mSecurityAccess != newSecurityAccess) {
			this.mModified = true;
			this.mSecurityAccess = newSecurityAccess;
		}
	}

	public void setVersionNum(int newVersionNum) {
		if (this.mVersionNum != newVersionNum) {
			this.mModified = true;
			this.mVersionNum = newVersionNum;
		}
	}

	public void setUpdatedByUserId(int newUpdatedByUserId) {
		this.mUpdatedByUserId = newUpdatedByUserId;
	}

	public void setVisId(String newVisId) {
		if (this.mVisId != null && newVisId == null || this.mVisId == null && newVisId != null || this.mVisId != null && newVisId != null && !this.mVisId.equals(newVisId)) {
			this.mVisId = newVisId;
			this.mModified = true;
		}

	}

	public void setDescription(String newDescription) {
		if (this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
			this.mDescription = newDescription;
			this.mModified = true;
		}

	}

	public void setDefinition(String newDefinition) {
		if (this.mDefinition != null && newDefinition == null || this.mDefinition == null && newDefinition != null || this.mDefinition != null && newDefinition != null && !this.mDefinition.equals(newDefinition)) {
			this.mDefinition = newDefinition;
			this.mModified = true;
		}

	}

	protected void setUpdatedTime(Timestamp newUpdatedTime) {
		this.mUpdatedTime = newUpdatedTime;
	}

	protected void setCreatedTime(Timestamp newCreatedTime) {
		this.mCreatedTime = newCreatedTime;
	}

	public void setDetails(XmlFormEVO newDetails) {
		this.setXmlFormId(newDetails.getXmlFormId());
		this.setVisId(newDetails.getVisId());
		this.setDescription(newDetails.getDescription());
		this.setType(newDetails.getType());
		this.setDesignMode(newDetails.getDesignMode());
		this.setFinanceCubeId(newDetails.getFinanceCubeId());
		this.setDefinition(newDetails.getDefinition());
		this.setExcelFile(newDetails.getExcelFile());
		this.setJsonForm(newDetails.getJsonForm());
		this.setSecurityAccess(newDetails.getSecurityAccess());
		this.setVersionNum(newDetails.getVersionNum());
		this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
		this.setUpdatedTime(newDetails.getUpdatedTime());
		this.setCreatedTime(newDetails.getCreatedTime());
	}

	public XmlFormEVO deepClone() {
		XmlFormEVO cloned = null;

		try {
			ByteArrayOutputStream e = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(e);
			oos.writeObject(this);
			oos.flush();
			oos.close();
			ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bis);
			cloned = (XmlFormEVO) ois.readObject();
			ois.close();
			return cloned;
		} catch (Exception var6) {
			throw new RuntimeException(var6);
		}
	}

	public void prepareForInsert() {
		boolean newKey = false;
		if (this.mXmlFormId > 0) {
			newKey = true;
			this.mXmlFormId = 0;
		} else if (this.mXmlFormId < 1) {
			newKey = true;
		}

		this.setVersionNum(0);
		XmlFormUserLinkEVO item;
		if (this.mUserList != null) {
			for (Iterator iter = (new ArrayList(this.mUserList.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
				item = (XmlFormUserLinkEVO) iter.next();
				if (newKey) {
					item.setInsertPending();
				}
			}
		}

	}

	public int getInsertCount(int startCount) {
		int returnCount = startCount;
		if (this.mXmlFormId < 1) {
			returnCount = startCount + 1;
		}

		XmlFormUserLinkEVO item;
		if (this.mUserList != null) {
			for (Iterator iter = this.mUserList.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
				item = (XmlFormUserLinkEVO) iter.next();
			}
		}

		return returnCount;
	}

	public int assignNextKey(int startKey) {
		int nextKey = startKey;
		if (this.mXmlFormId < 1) {
			this.mXmlFormId = startKey;
			nextKey = startKey + 1;
		}

		XmlFormUserLinkEVO item;
		if (this.mUserList != null) {
			for (Iterator iter = (new ArrayList(this.mUserList.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
				item = (XmlFormUserLinkEVO) iter.next();
				this.changeKey(item, this.mXmlFormId, item.getUserId());
			}
		}

		return nextKey;
	}

	public Collection<XmlFormUserLinkEVO> getUserList() {
		return this.mUserList != null ? this.mUserList.values() : null;
	}

	public Map<XmlFormUserLinkPK, XmlFormUserLinkEVO> getUserListMap() {
		return this.mUserList;
	}

	public void loadUserListItem(XmlFormUserLinkEVO newItem) {
		if (this.mUserList == null) {
			this.mUserList = new HashMap();
		}

		this.mUserList.put(newItem.getPK(), newItem);
	}

	public void addUserListItem(XmlFormUserLinkEVO newItem) {
		if (this.mUserList == null) {
			this.mUserList = new HashMap();
		}

		XmlFormUserLinkPK newPK = newItem.getPK();
		if (this.getUserListItem(newPK) != null) {
			throw new RuntimeException("addUserListItem: key already in list");
		} else {
			newItem.setInsertPending();
			this.mUserList.put(newPK, newItem);
		}
	}

	public void changeUserListItem(XmlFormUserLinkEVO changedItem) {
		if (this.mUserList == null) {
			throw new RuntimeException("changeUserListItem: no items in collection");
		} else {
			XmlFormUserLinkPK changedPK = changedItem.getPK();
			XmlFormUserLinkEVO listItem = this.getUserListItem(changedPK);
			if (listItem == null) {
				throw new RuntimeException("changeUserListItem: item not in list");
			} else {
				listItem.setDetails(changedItem);
			}
		}
	}

	public void deleteUserListItem(XmlFormUserLinkPK removePK) {
		XmlFormUserLinkEVO listItem = this.getUserListItem(removePK);
		if (listItem == null) {
			throw new RuntimeException("removeUserListItem: item not in list");
		} else {
			listItem.setDeletePending();
		}
	}

	public XmlFormUserLinkEVO getUserListItem(XmlFormUserLinkPK pk) {
		return (XmlFormUserLinkEVO) this.mUserList.get(pk);
	}

	public XmlFormUserLinkEVO getUserListItem() {
		if (this.mUserList.size() != 1) {
			throw new RuntimeException("should be 1 item but size=" + this.mUserList.size());
		} else {
			Iterator iter = this.mUserList.values().iterator();
			return (XmlFormUserLinkEVO) iter.next();
		}
	}

	protected void reset() {
		this.mModified = false;
	}

	public XmlFormRef getEntityRef() {
		return new XmlFormRefImpl(this.getPK(), this.mVisId);
	}

	public void postCreateInit() {
		this.mUserListAllItemsLoaded = true;
		if (this.mUserList == null) {
			this.mUserList = new HashMap();
		}

	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("XmlFormId=");
		sb.append(String.valueOf(this.mXmlFormId));
		sb.append(' ');
		sb.append("VisId=");
		sb.append(String.valueOf(this.mVisId));
		sb.append(' ');
		sb.append("Description=");
		sb.append(String.valueOf(this.mDescription));
		sb.append(' ');
		sb.append("Type=");
		sb.append(String.valueOf(this.mType));
		sb.append(' ');
		sb.append("DesignMode=");
		sb.append(String.valueOf(this.mDesignMode));
		sb.append(' ');
		sb.append("FinanceCubeId=");
		sb.append(String.valueOf(this.mFinanceCubeId));
		sb.append(' ');
		sb.append("Definition=");
		sb.append(String.valueOf(this.mDefinition));
		sb.append(' ');
		sb.append("ExcelFile=");
		sb.append(String.valueOf(this.mExcelFile));
		sb.append(' ');
		sb.append("SecurityAccess=");
		sb.append(String.valueOf(this.mSecurityAccess));
		sb.append(' ');
		sb.append("VersionNum=");
		sb.append(String.valueOf(this.mVersionNum));
		sb.append(' ');
		sb.append("UpdatedByUserId=");
		sb.append(String.valueOf(this.mUpdatedByUserId));
		sb.append(' ');
		sb.append("UpdatedTime=");
		sb.append(String.valueOf(this.mUpdatedTime));
		sb.append(' ');
		sb.append("CreatedTime=");
		sb.append(String.valueOf(this.mCreatedTime));
		sb.append(' ');
		if (this.mModified) {
			sb.append("modified ");
		}

		return sb.toString();
	}

	public String print() {
		return this.print(0);
	}

	public String print(int indent) {
		StringBuffer sb = new StringBuffer();

		for (int i$ = 0; i$ < indent; ++i$) {
			sb.append(' ');
		}

		sb.append("XmlForm: ");
		sb.append(this.toString());
		if (this.mUserListAllItemsLoaded || this.mUserList != null) {
			sb.delete(indent, sb.length());
			sb.append(" - UserList: allItemsLoaded=");
			sb.append(String.valueOf(this.mUserListAllItemsLoaded));
			sb.append(" items=");
			if (this.mUserList == null) {
				sb.append("null");
			} else {
				sb.append(String.valueOf(this.mUserList.size()));
			}
		}

		if (this.mUserList != null) {
			Iterator var5 = this.mUserList.values().iterator();

			while (var5.hasNext()) {
				XmlFormUserLinkEVO listItem = (XmlFormUserLinkEVO) var5.next();
				listItem.print(indent + 4);
			}
		}

		return sb.toString();
	}

	public void changeKey(XmlFormUserLinkEVO child, int newXmlFormId, int newUserId) {
		if (this.getUserListItem(child.getPK()) != child) {
			throw new IllegalStateException("changeKey child not found in parent");
		} else {
			this.mUserList.remove(child.getPK());
			child.setXmlFormId(newXmlFormId);
			child.setUserId(newUserId);
			this.mUserList.put(child.getPK(), child);
		}
	}

	public boolean isForceDelete() {
		return this.mForceDelete;
	}

	public void setForceDelete(boolean forceDelete) {
		this.mForceDelete = forceDelete;
	}

	public void setUserListAllItemsLoaded(boolean allItemsLoaded) {
		this.mUserListAllItemsLoaded = allItemsLoaded;
	}

	public boolean isUserListAllItemsLoaded() {
		return this.mUserListAllItemsLoaded;
	}

	/**
	 * Set excel file
	 * 
	 * @param newExcelFile
	 */
	public void setExcelFile(byte[] newExcelFile) {
		if (this.mExcelFile != null && newExcelFile == null || this.mExcelFile == null && newExcelFile != null || this.mExcelFile != null && newExcelFile != null && !this.mExcelFile.equals(newExcelFile)) {
			this.mExcelFile = newExcelFile;
			this.mModified = true;
		}
	}

	/**
	 * Get excel file
	 * 
	 * @return
	 */
	public byte[] getExcelFile() {
		return this.mExcelFile;
	}

	public String getJsonForm() {
		return mJsonForm;
	}

	public void setJsonForm(String newJsonForm) {
		if (this.mJsonForm != null && newJsonForm == null || this.mJsonForm == null && newJsonForm != null || this.mJsonForm != null && newJsonForm != null && !this.mJsonForm.equals(newJsonForm)) {
			this.mJsonForm = newJsonForm;
			this.mModified = true;
		}
	}
}
