package com.cedar.cp.dto.message;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MessageUserDetailELO extends AbstractELO implements Serializable {

	public MessageUserDetailELO() {
		super(new String[] { "Name", "FullName" });
	}

	public void add(String name, String fullName) {
		List l = new ArrayList();
		l.add(name);
		l.add(fullName);
		mCollection.add(l);
	}

	public void next() {
		if (mIterator == null) {
			reset();
		}

		mCurrRowIndex += 1;
		List l = (List) mIterator.next();
		int index = 0;
	}
}