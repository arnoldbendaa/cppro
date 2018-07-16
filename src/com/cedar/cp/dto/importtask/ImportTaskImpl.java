package com.cedar.cp.dto.importtask;

import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.importtask.ImportTask;
import com.cedar.cp.dto.importtask.ImportTaskPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ImportTaskImpl implements ImportTask, Serializable, Cloneable {

	private List mTaskList;
	private Object mPrimaryKey;
	private String mVisId;
	private String mDescription;
	private int mExternalSystemId;
	private ExternalSystemRef mExternalSystemRef;
	private int mVersionNum;

	public ImportTaskImpl(Object paramKey) {
		this.mPrimaryKey = paramKey;
		this.mVisId = "";
		this.mDescription = "";
		this.mExternalSystemId = 0;
	}

	public Object getPrimaryKey() {
		return this.mPrimaryKey;
	}

	public void setPrimaryKey(Object paramKey) {
		this.mPrimaryKey = (ImportTaskPK) paramKey;
	}

	public String getVisId() {
		return this.mVisId;
	}

	public String getDescription() {
		return this.mDescription;
	}
	
	public int getExternalSystemId() {
		return this.mExternalSystemId;
	}

	public ExternalSystemRef getExternalSystemRef() {
		return this.mExternalSystemRef;
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
	
	public void setExternalSystemId(int paramExternalSystemId) {
		this.mExternalSystemId = paramExternalSystemId;
	}
	
	public void setExternalSystemRef(ExternalSystemRef ref) {
		this.mExternalSystemRef = ref;
	}
	 
	public List getTaskList() {
		if (this.mTaskList == null) {
			this.mTaskList = new ArrayList();
		}

		return this.mTaskList;
	}

	public void setImportList(List tidyList) {
		this.mTaskList = tidyList;
	}
}
