package com.cedar.cp.impl.importtask;

import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.importtask.ImportTask;
import com.cedar.cp.dto.importtask.ImportTaskImpl;
import com.cedar.cp.dto.importtask.ImportTaskPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.importtask.ImportTaskEditorSessionImpl;
import java.util.List;

public class ImportTaskAdapter implements ImportTask {

   private ImportTaskImpl mEditorData;
   private ImportTaskEditorSessionImpl mEditorSessionImpl;


   public ImportTaskAdapter(ImportTaskEditorSessionImpl e, ImportTaskImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected ImportTaskEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected ImportTaskImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(ImportTaskPK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

	public int getExternalSystemId() {
		return this.mEditorData.getExternalSystemId();
	}
   
	public ExternalSystemRef getExternalSystemRef() {
		if (this.mEditorData.getExternalSystemRef() != null) {
			try {
				ExternalSystemRef e = ((BusinessProcessImpl) this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getExternalSystemEntityRef(this.mEditorData.getExternalSystemRef());
				this.mEditorData.setExternalSystemRef(e);
				return e;
			} catch (Exception var2) {
				var2.printStackTrace();
				throw new RuntimeException(var2.getMessage());
			}
		} else {
			return null;
		}
	}
	   
   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }
   
	public void setExternalSystemId(int p) {
		this.mEditorData.setExternalSystemId(p);
	}
	
	public void setExternalSystemRef(ExternalSystemRef ref) {
		this.mEditorData.setExternalSystemRef(ref);
	}

   public List getTaskList() {
      return this.mEditorData.getTaskList();
   }
}
