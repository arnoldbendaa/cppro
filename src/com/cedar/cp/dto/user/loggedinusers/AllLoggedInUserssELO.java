package com.cedar.cp.dto.user.loggedinusers;

import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllLoggedInUserssELO extends AbstractELO implements Serializable {

	private static final String[] mEntity = new String[] { "LoggedInUsers", "TidyTaskLink" };
	private transient ExternalSystemRef mExternalSystemEntityRef;
	private transient String mDescription;

	public AllLoggedInUserssELO() {
		super(new String[] { "LoggedInUsers", "ExternalSystem", "Description" });
	}

	public void add(ExternalSystemRef eRefExternalSystem, String description) {
		ArrayList l = new ArrayList();
		l.add(eRefExternalSystem);
		l.add(description);
		this.mCollection.add(l);
	}

	public void next() {
		if (this.mIterator == null) {
			this.reset();
		}

		++this.mCurrRowIndex;
		List l = (List) this.mIterator.next();
		byte index = 0;
		int var4 = index + 1;
		this.mExternalSystemEntityRef = (ExternalSystemRef) l.get(var4++);
		this.mDescription = (String) l.get(var4++);
	}

	public ExternalSystemRef getExternalSystemEntityRef() {
		return this.mExternalSystemEntityRef;
	}

	public String getDescription() {
		return this.mDescription;
	}

	public boolean includesEntity(String name) {
		for (int i = 0; i < mEntity.length; ++i) {
			if (name.equals(mEntity[i])) {
				return true;
			}
		}

		return false;
	}

}
