// Decompiled by: Fernflower v0.8.6
// Date: 12.08.2012 13:05:20
// Copyright: 2008-2012, Stiver
// Home page: http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.xmlform;

import com.cedar.cp.api.base.Destroyable;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.api.xmlform.XmlForm;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.xmlform.XmlFormImpl;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.impl.xmlform.XmlFormEditorSessionImpl;
import java.util.List;

public class XmlFormAdapter implements XmlForm, Destroyable {

	private XmlFormImpl mEditorData;
	private XmlFormEditorSessionImpl mEditorSessionImpl;

	public void destroy(){
		mEditorData.setDefinition(null);
		mEditorData.setExcelFile(null);
		mEditorData=null;
		
		mEditorSessionImpl=null;
	}
	
	public XmlFormAdapter(XmlFormEditorSessionImpl e, XmlFormImpl editorData) {
		this.mEditorData = editorData;
		this.mEditorSessionImpl = e;
	}

	public void setPrimaryKey(Object key) {
		this.mEditorData.setPrimaryKey(key);
	}

	protected XmlFormEditorSessionImpl getEditorSessionImpl() {
		return this.mEditorSessionImpl;
	}

	protected XmlFormImpl getEditorData() {
		return this.mEditorData;
	}

	public Object getPrimaryKey() {
		return this.mEditorData.getPrimaryKey();
	}

	void setPrimaryKey(XmlFormPK paramKey) {
		this.mEditorData.setPrimaryKey(paramKey);
	}

	public String getVisId() {
		return this.mEditorData.getVisId();
	}

	public String getDescription() {
		return this.mEditorData.getDescription();
	}

	public int getType() {
		return this.mEditorData.getType();
	}

	public boolean isDesignMode() {
		return this.mEditorData.isDesignMode();
	}

	public int getFinanceCubeId() {
		return this.mEditorData.getFinanceCubeId();
	}

	public String getDefinition() {
		return this.mEditorData.getDefinition();
	}

	public boolean isSecurityAccess() {
		return this.mEditorData.isSecurityAccess();
	}

	public void setVisId(String p) {
		this.mEditorData.setVisId(p);
	}

	public void setDescription(String p) {
		this.mEditorData.setDescription(p);
	}

	public void setType(int p) {
		this.mEditorData.setType(p);
	}

	public void setDesignMode(boolean p) {
		this.mEditorData.setDesignMode(p);
	}

	public void setFinanceCubeId(int p) {
		this.mEditorData.setFinanceCubeId(p);
	}

	public void setDefinition(String p) {
		this.mEditorData.setDefinition(p);
	}

	public void setSecurityAccess(boolean p) {
		this.mEditorData.setSecurityAccess(p);
	}

	public List getUserList() {
		return this.mEditorData.getUserList();
	}
	
	public List getNamesList() {
		return this.mEditorData.getNamesList();
	}

	public EntityList getAvailableUsers() {
		EntityList list = this.mEditorSessionImpl.getConnection().getListHelper().getAllUsers();
		int size = list.getNumRows();
		AllUsersELO avail = new AllUsersELO();
		List selected = this.getUserList();

		for (int i = 0; i < size; ++i) {
			UserRef ref = (UserRef) list.getValueAt(i, "User");
			if (!selected.contains(ref)) {
				String name = list.getValueAt(i, "FullName").toString();
				avail.add(ref, name, false);
			}
		}

		return avail;
	}

	/**
	 * Get excel file
	 * 
	 * @return
	 */
	public byte[] getExcelFile() {
		return this.mEditorData.getExcelFile();
	}

	/**
	 * Set excel file
	 * 
	 * @param p
	 * @return
	 */
	public void setExcelFile(byte[] p) {
		this.mEditorData.setExcelFile(p);
	}
	
	public String getJsonForm() {
		return this.mEditorData.getJsonForm();
	}

	public void setJsonForm(String mJsonForm) {
		this.mEditorData.setJsonForm(mJsonForm);
	}
	
	@Override
	public int incrementVersionNumber() {
		return this.mEditorData.incrementVersionNumber();
	}
}
