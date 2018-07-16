package com.cedar.cp.impl.user.loggedinusers;

import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.user.loggedinusers.LoggedInUsers;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersImpl;
import com.cedar.cp.dto.user.loggedinusers.LoggedInUsersPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.user.loggedinusers.LoggedInUsersEditorSessionImpl;

import java.util.List;

public class LoggedInUsersAdapter implements LoggedInUsers {

   private LoggedInUsersImpl mEditorData;
   private LoggedInUsersEditorSessionImpl mEditorSessionImpl;


   public LoggedInUsersAdapter(LoggedInUsersEditorSessionImpl e, LoggedInUsersImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected LoggedInUsersEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected LoggedInUsersImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(LoggedInUsersPK paramKey) {
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
