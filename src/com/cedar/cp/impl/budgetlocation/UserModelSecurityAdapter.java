package com.cedar.cp.impl.budgetlocation;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.budgetlocation.UserModelSecurity;
import com.cedar.cp.dto.budgetlocation.UserModelSecurityImpl;
import com.cedar.cp.dto.model.ModelPK;
import java.util.List;

public class UserModelSecurityAdapter implements UserModelSecurity {

	private UserModelSecurityImpl mEditorData;
	private UserModelSecurityEditorSessionImpl mEditorSessionImpl;

	public UserModelSecurityAdapter(UserModelSecurityEditorSessionImpl e, UserModelSecurityImpl editorData) {
		mEditorData = editorData;
		mEditorSessionImpl = e;
	}

	public void setPrimaryKey(Object key) {
		mEditorData.setPrimaryKey(key);
	}

	protected UserModelSecurityEditorSessionImpl getEditorSessionImpl() {
		return mEditorSessionImpl;
	}

	protected UserModelSecurityImpl getEditorData() {
		return mEditorData;
	}

	public Object getPrimaryKey() {
		return mEditorData.getPrimaryKey();
	}

	void setPrimaryKey(ModelPK paramKey) {
		mEditorData.setPrimaryKey(paramKey);
	}

	public List getUserModelElementAccess() {
		return mEditorData.getUserModelElementAccess();
	}

	public EntityList getModelsAndRAHierarchies() {
		return mEditorData.getModelsAndRAHierarchies();
	}
}