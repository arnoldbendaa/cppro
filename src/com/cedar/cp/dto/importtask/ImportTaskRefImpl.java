package com.cedar.cp.dto.importtask;

import com.cedar.cp.api.importtask.ImportTaskRef;
import com.cedar.cp.dto.importtask.ImportTaskPK;
import com.cedar.cp.dto.base.EntityRefImpl;
import java.io.Serializable;

public class ImportTaskRefImpl extends EntityRefImpl implements ImportTaskRef, Serializable {

	public ImportTaskRefImpl(ImportTaskPK key, String narrative) {
		super(key, narrative);
	}

	public ImportTaskPK getImportTaskPK() {
		return (ImportTaskPK) this.mKey;
	}
}
