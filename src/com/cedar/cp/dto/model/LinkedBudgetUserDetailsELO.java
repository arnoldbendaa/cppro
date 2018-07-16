package com.cedar.cp.dto.model;

import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.dto.base.AbstractELO;

public class LinkedBudgetUserDetailsELO extends AbstractELO {

	private transient Integer mUserId;
	private transient String mName;
	private transient String mFullName;
	private transient String mEmail;
	private transient List<String> mElemIds;

	public LinkedBudgetUserDetailsELO() {
		super(new String[]{"UserId", "Name", "FullName", "EMailAddress", "StructureElementIds"});
	}

	public void add(int userId, String name, String fullName, String email, List<String> elemIds) {
		List l = new ArrayList();
		l.add(new Integer(userId));
		l.add(name);
		l.add(fullName);
		l.add(email);
		l.add(elemIds);
		mCollection.add(l);
	}

	public void next() {
		if (mIterator == null) {
			mCurrRowIndex = -1;
			reset();
		}

		mCurrRowIndex += 1;
		List l = (List) mIterator.next();
		int index = 0;
		mUserId = ((Integer) l.get(index++));
		mName = ((String) l.get(index++));
		mFullName = ((String) l.get(index++));
		mEmail = ((String) l.get(index++));
		mElemIds = ((List) l.get(index++));
	}
}