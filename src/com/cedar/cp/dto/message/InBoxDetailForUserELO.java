package com.cedar.cp.dto.message;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InBoxDetailForUserELO extends AbstractELO implements Serializable {
	private static final String[] mEntity = { "Message", "MessageUser", "MessageAttatch", "MessageUser" };

	public InBoxDetailForUserELO() {
		super(new String[] { "MessageId", "Subject", "CreatedTime", "Attach", "MessageUserId", "Read", "FromDetail", "ToDetail" });
	}

	public void add(long messageId, String subject, Timestamp createdTime, boolean attach, long messageUserId, boolean read, EntityList fromDetail, EntityList toDetail) {
		List l = new ArrayList();
		l.add(Long.valueOf(messageId));
		l.add(subject);
		l.add(createdTime);
		l.add(Boolean.valueOf(attach));
		l.add(Long.valueOf(messageUserId));
		l.add(Boolean.valueOf(read));
		l.add(fromDetail);
		l.add(toDetail);
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

	public boolean includesEntity(String name) {
		for (int i = 0; i < mEntity.length; i++)
			if (name.equals(mEntity[i]))
				return true;
		return false;
	}
}