// Decompiled by: Fernflower v0.8.6
// Date: 12.08.2012 13:05:55
// Copyright: 2008-2012, Stiver
// Home page: http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class BudgetDetailsForUserLevel4ELO extends AbstractELO	implements Serializable 
{
	private transient Integer mState;
	private transient Integer mStructureElementId;
	private transient String mVisId;
	private transient String mDescription;
	private transient Integer mUserCount;
	private transient Integer mOtherUserCount;
	private transient Boolean mSubmitable;
	private transient Boolean mRejectable;
	private transient Integer mLastUpdatedBy;
	private transient Boolean mLeaf;
	private transient Timestamp mEndDate;
	private transient Boolean mFullRights;

	public BudgetDetailsForUserLevel4ELO() {
		super(new String[] { "State", "StructureElementId", "ElementVisId", "Description", "UserCount", "OtherUserCount", "Submitable", "Rejectable", "LastUpdateById", "Leaf", "EndDate", "FullRights" });
	}

	public void add(Integer state, int structureElementId, String visId, String description, int userCount, int otherUserCount, boolean submitable, boolean rejectable, int lastupdateById, boolean leaf, Timestamp endDate)
	{
		List l = new ArrayList();
		l.add(state);
		l.add(new Integer(structureElementId));
		l.add(visId);
		l.add(description);
		l.add(new Integer(userCount));
		l.add(new Integer(otherUserCount));
		l.add(new Boolean(submitable));
		l.add(new Boolean(rejectable));
		l.add(new Integer(lastupdateById));
		l.add(new Boolean(leaf));
		l.add(endDate);
		l.add(true);
		mCollection.add(l);
	}
	
	public void add(Integer state, int structureElementId, String visId, String description, int userCount, int otherUserCount, boolean submitable, boolean rejectable, int lastupdateById, boolean leaf, Timestamp endDate, Boolean fullRights)
    {
        List l = new ArrayList();
        l.add(state);
        l.add(new Integer(structureElementId));
        l.add(visId);
        l.add(description);
        l.add(new Integer(userCount));
        l.add(new Integer(otherUserCount));
        l.add(new Boolean(submitable));
        l.add(new Boolean(rejectable));
        l.add(new Integer(lastupdateById));
        l.add(new Boolean(leaf));
        l.add(endDate);
        l.add(fullRights);
        mCollection.add(l);
    }

	public void next() {
		if (mIterator == null)
		{
			reset();
		}

		mCurrRowIndex += 1;
		List l = (List) mIterator.next();
		int index = 0;
		mState = ((Integer) l.get(index++));
		mStructureElementId = ((Integer) l.get(index++));
		mVisId = ((String) l.get(index++));
		mDescription = ((String) l.get(index++));
		mUserCount = ((Integer) l.get(index++));
		mOtherUserCount = ((Integer) l.get(index++));
		mSubmitable = ((Boolean) l.get(index++));
		mRejectable = ((Boolean) l.get(index++));
		mLastUpdatedBy = ((Integer) l.get(index++));
		mLeaf = ((Boolean) l.get(index++));
		mEndDate = ((Timestamp) l.get(index++));
		mFullRights = ((Boolean) l.get(index++));
	}

	public Integer getState() {
		return mState;
	}

	public int getStructureElementId() {
		return mStructureElementId.intValue();
	}

	public String getVisId() {
		return mVisId;
	}

	public String getDescription() {
		return mDescription;
	}

	public int getUserCount() {
		return mUserCount.intValue();
	}

	public Boolean getSubmitable() {
		return mSubmitable;
	}

	public Boolean getRejectable() {
		return mRejectable;
	}

	public Boolean getLeaf() {
		return mLeaf;
	}

	public Integer getOtherUserCount() {
		return mOtherUserCount;
	}

	public Timestamp getEndDate() {
		return mEndDate;
	}

	public Integer getLastUpdatedBy() {
		return mLastUpdatedBy;
	}

    public Boolean getFullRights() {
        return mFullRights;
    }
}
