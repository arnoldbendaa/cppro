// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.user;

import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserMessageAttributesForIdELO extends AbstractELO implements Serializable {

	private static final String[] mEntity = { "User", "UserRole", "UserPreference", "DataEntryProfile", "DataEntryProfileHistory", "ChallengeQuestion", "UserResetLink", "VirementRequest" };
	private transient UserRef mUserEntityRef;
	private transient String mName;
	private transient String mFullName;
	private transient boolean mUserDisabled;
	private transient String mEMailAddress;

	public UserMessageAttributesForIdELO() {
		super(new String[] { "User", "Name", "FullName", "UserDisabled", "EMailAddress" });
	}

	public void add(UserRef eRefUser, String col1, String col2, boolean col3, String col4) {
		List l = new ArrayList();
		l.add(eRefUser);
		l.add(col1);
		l.add(col2);
		l.add(new Boolean(col3));
		l.add(col4);
		mCollection.add(l);
	}

	public void next() {
		if (mIterator == null) {
			reset();
		}
		mCurrRowIndex += 1;
		List l = (List) mIterator.next();
		int index = 0;
		mUserEntityRef = ((UserRef) l.get(index++));
		mName = ((String) l.get(index++));
		mFullName = ((String) l.get(index++));
		mUserDisabled = ((Boolean) l.get(index++)).booleanValue();
		mEMailAddress = ((String) l.get(index++));
	}

	public UserRef getUserEntityRef() {
		return mUserEntityRef;
	}

	public String getName()	{
		return mName;
	}

	public String getFullName()	{
		return mFullName;
	}

	public boolean getUserDisabled() {
		return mUserDisabled;
	}

	public String getEMailAddress()	{
		return mEMailAddress;
	}

	public boolean includesEntity(String name) {
		for (int i = 0; i < mEntity.length; i++)
			if (name.equals(mEntity[i]))
				return true;
		return false;
	}

}
