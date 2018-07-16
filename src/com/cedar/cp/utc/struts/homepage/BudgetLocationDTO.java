// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.homepage;

import com.cedar.cp.utc.struts.homepage.BLChildDTO;
import com.cedar.cp.utc.struts.message.MessageDTO;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class BudgetLocationDTO implements Serializable {

    private int mState;
    private int mStructureElementId;
    private String mIdentifier;
    private String mDescription;
    private int mDepth;
    private Date mEndDate;
    private List mChildren;
    private int mChildNotStarted = 0;
    private int mChildPreparing = 0;
    private int mChildSubmited = 0;
    private int mChildAgreed = 0;
    private List mBudgetInstruction;
    private int mUserCount;
    private boolean mSubmitable;
    private boolean mMassSubmitable;
    private boolean mRejectable;
    private boolean mMassRejectable;
    private boolean mController;
    private boolean mAgreeable;
    private int mOldDepth;
    private Calendar mLateDate;
    private Boolean mLateResult = null;
    private Boolean mOverDueResult = null;
    private int mLastUpdatedById;
    private boolean mFullRights;


    public int getState() {
        return this.mState;
    }

    public void setState(int state) {
        this.mState = state;
    }

    public int getStructureElementId() {
        return this.mStructureElementId;
    }

    public void setStructureElementId(int structureElementId) {
        this.mStructureElementId = structureElementId;
    }

    public String getIdentifier() {
        return this.mIdentifier;
    }

    public void setIdentifier(String identifier) {
        this.mIdentifier = identifier;
    }

    public int getDepth() {
        return this.mDepth;
    }

    public void setDepth(int depth) {
        this.mDepth = depth;
    }

    public List getChildren() {
        return this.mChildren;
    }

    public void setChildren(List children) {
        this.mChildren = children;
    }

    public int getChildNotStarted() {
        if (this.mChildNotStarted == 0) {
            this.setChildNotStarted(this.getCount(0));
        }

        return this.mChildNotStarted;
    }

    public void setChildNotStarted(int childNotStarted) {
        this.mChildNotStarted = childNotStarted;
    }

    public int getChildPreparing() {
        if (this.mChildPreparing == 0) {
            this.setChildPreparing(this.getCount(2));
        }

        return this.mChildPreparing;
    }

    public void setChildPreparing(int childPreparing) {
        this.mChildPreparing = childPreparing;
    }

    public int getChildSubmited() {
        if (this.mChildSubmited == 0) {
            this.setChildSubmited(this.getCount(3));
        }

        return this.mChildSubmited;
    }

    public void setChildSubmited(int childSubmited) {
        this.mChildSubmited = childSubmited;
    }

    public int getChildAgreed() {
        if (this.mChildAgreed == 0) {
            this.setChildAgreed(this.getCount(4));
        }

        return this.mChildAgreed;
    }

    public void setChildAgreed(int childAgreed) {
        this.mChildAgreed = childAgreed;
    }

    public List getBudgetInstruction() {
        return this.mBudgetInstruction;
    }

    public void setBudgetInstruction(List budgetInstruction) {
        this.mBudgetInstruction = budgetInstruction;
    }

    private int getCount(int state) {
        int count = 0;
        Iterator i$ = this.mChildren.iterator();

        while (i$.hasNext()) {
            Object aMChildren = i$.next();
            BLChildDTO dto = (BLChildDTO) aMChildren;
            if (dto.getState() == state) {
                ++count;
            }
        }

        return count;
    }

    public int getUserCount() {
        return this.mUserCount;
    }

    public void setUserCount(int userCount) {
        this.mUserCount = userCount;
    }

    public boolean isBudgetUser() {
        return this.getUserCount() > 0;
    }

    public boolean isShowStart() {
        return this.isController() && this.getDepth() == 1 && this.getState() == 0 ? true : this.isBudgetUser() && this.getState() == 0;
    }

    public boolean isShowSubmit() {
        return this.isController() && this.getDepth() == 1 && this.getState() == 2 && this.isSubmitable() ? true : this.isBudgetUser() && this.getState() == 2 && this.isSubmitable();
    }

    public boolean isShowAgree() {
        return this.isController() && this.getDepth() == 1 && this.getState() == 3 ? true : this.isBudgetUser() && this.getState() == 3 && this.getDepth() != 1 && this.isAgreeable();
    }

    public boolean isShowReject() {
        return this.isController() && this.getDepth() == 1 && this.getState() == 4 && this.isRejectable() ? true : this.isBudgetUser() && this.getState() == 4 && this.getDepth() != 1 && this.isRejectable() && this.isAgreeable();
    }

    public boolean isSubmitable() {
        return this.mSubmitable;
    }

    public void setSubmitable(boolean submitable) {
        this.mSubmitable = submitable;
    }

    public boolean isController() {
        return this.mController;
    }

    public void setController(boolean controller) {
        this.mController = controller;
    }

    public boolean isRejectable() {
        return this.mRejectable;
    }

    public void setRejectable(boolean rejectable) {
        this.mRejectable = rejectable;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public boolean isAgreeable() {
        return this.mAgreeable;
    }

    public void setAgreeable(boolean agreeable) {
        this.mAgreeable = agreeable;
    }

    public int getOldDepth() {
        return this.mOldDepth;
    }

    public void setOldDepth(int oldDepth) {
        this.mOldDepth = oldDepth;
    }

    public Date getEndDate() {
        return this.mEndDate;
    }

    public void setEndDate(Date endDate) {
        this.mEndDate = endDate;
    }

    public boolean isLate() {
        if (this.mLateResult == null) {
            if (this.mEndDate == null) {
                this.mLateResult = Boolean.valueOf(false);
            } else {
                this.mLateResult = Boolean.valueOf(this.mEndDate.before(this.getLateDate().getTime()) && this.getState() < 3);
            }
        }

        return this.mLateResult.booleanValue();
    }

    public Calendar getLateDate() {
        return this.mLateDate;
    }

    public void setLateDate(Calendar lateDate) {
        this.mLateDate = lateDate;
    }

    public boolean isMassSubmitable() {
        return this.mMassSubmitable;
    }

    public void setMassSubmitable(boolean massSubmitable) {
        this.mMassSubmitable = massSubmitable;
    }

    public boolean isMassRejectable() {
        return this.mMassRejectable;
    }

    public void setMassRejectable(boolean massRejectable) {
        this.mMassRejectable = massRejectable;
    }

    private boolean isOverDue() {
        if (this.mOverDueResult == null) {
            if (this.mEndDate == null) {
                this.mOverDueResult = Boolean.valueOf(false);
            } else {
                this.mOverDueResult = Boolean.valueOf(this.mEndDate.before(Calendar.getInstance().getTime()));
            }
        }

        return this.mOverDueResult.booleanValue();
    }

    public String getLateMessage() {
        StringBuffer text = new StringBuffer(40);
        if (this.isOverDue()) {
            text.append("Overdue - ");
        } else {
            text.append("Due - ");
        }

        text.append(MessageDTO.sFormat.format(this.mEndDate));
        return text.toString();
    }

    public String getWarningImage() {
        String gif = "warnings";
        if (this.isOverDue()) {
            gif = "warning_red";
        }

        return gif;
    }

    public int getLastUpdatedById() {
        return mLastUpdatedById;
    }

    public void setLastUpdatedById(int lastUpdatedById) {
        mLastUpdatedById = lastUpdatedById;
    }

    public boolean isFullRights() {
        return mFullRights;
    }

    public void setFullRights(boolean mFullRights) {
        this.mFullRights = mFullRights;
    }
}
