// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:44
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model;

import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCycleLinkPK;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.dto.model.BudgetStatePK;
import com.cedar.cp.dto.model.LevelDatePK;
import com.cedar.cp.ejb.impl.model.BudgetStateEVO;
import com.cedar.cp.ejb.impl.model.LevelDateEVO;
import com.cedar.cp.ejb.impl.model.ModelEVO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BudgetCycleEVO implements Serializable {

    private transient BudgetCyclePK mPK;
    private int mBudgetCycleId;
    private int mModelId;
    private String mVisId;
    private String mDescription;
    private int mType;
    private int mXmlFormId;
    private String mXmlFormDataType;
    private int mPeriodId;
    private int mPeriodIdTo;
    private String mPeriodFromVisId;
    private String mPeriodToVisId;
    private Timestamp mPlannedEndDate;
    private Timestamp mStartDate;
    private Timestamp mEndDate;
    private int mStatus;
    private int mVersionNum;
    private int mUpdatedByUserId;
    private Timestamp mUpdatedTime;
    private Timestamp mCreatedTime;
    private Map<BudgetStatePK, BudgetStateEVO> mBudgetCycleStates;
    protected boolean mBudgetCycleStatesAllItemsLoaded;
    private Map<LevelDatePK, LevelDateEVO> mBudgetCycleLevlDates;
    protected boolean mBudgetCycleLevlDatesAllItemsLoaded;
    private Map<BudgetCycleLinkPK, BudgetCycleLinkEVO> mBudgetCycleLinks;
    protected boolean mBudgetCycleLinksAllItemsLoaded;
    private boolean mInsertPending;
    private boolean mDeletePending;
    private boolean mModified;
    private String mCategory;
    public static final int TYPE_YEARLY_PREPARATION = 1;
    public static final int TYPE_MONTHLY_OUTTURN = 2;

    public BudgetCycleEVO() {
    }

    public BudgetCycleEVO(int newBudgetCycleId, int newModelId, String newVisId, String newDescription, int newType, int newXmlFormId, String newXmlFormDataType, int newPeriodId, int newPeriodIdTo, String newPeriodFromVisId, String newPeriodToVisId, Timestamp newPlannedEndDate, Timestamp newStartDate, Timestamp newEndDate, int newStatus, int newVersionNum, Collection newBudgetCycleStates, Collection newBudgetCycleLevlDates, Collection newBudgetCycleLinks, String newCategory) {
        this.mBudgetCycleId = newBudgetCycleId;
        this.mModelId = newModelId;
        this.mVisId = newVisId;
        this.mDescription = newDescription;
        this.mType = newType;
        this.mXmlFormId = newXmlFormId;
        this.mXmlFormDataType = newXmlFormDataType;
        this.mPeriodId = newPeriodId;
        this.mPeriodIdTo = newPeriodIdTo;
        this.mPeriodFromVisId = newPeriodFromVisId;
        this.mPeriodToVisId = newPeriodToVisId;
        this.mPlannedEndDate = newPlannedEndDate;
        this.mStartDate = newStartDate;
        this.mEndDate = newEndDate;
        this.mStatus = newStatus;
        this.mVersionNum = newVersionNum;
        this.setBudgetCycleStates(newBudgetCycleStates);
        this.setBudgetCycleLevlDates(newBudgetCycleLevlDates);
        this.setBudgetCycleLinks(newBudgetCycleLinks);
        this.mCategory = newCategory;
    }

    public void setBudgetCycleStates(Collection<BudgetStateEVO> items) {
        if (items != null) {
            if (this.mBudgetCycleStates == null) {
                this.mBudgetCycleStates = new HashMap();
            } else {
                this.mBudgetCycleStates.clear();
            }

            Iterator i$ = items.iterator();

            while (i$.hasNext()) {
                BudgetStateEVO child = (BudgetStateEVO) i$.next();
                this.mBudgetCycleStates.put(child.getPK(), child);
            }
        } else {
            this.mBudgetCycleStates = null;
        }

    }

    public void setBudgetCycleLevlDates(Collection<LevelDateEVO> items) {
        if (items != null) {
            if (this.mBudgetCycleLevlDates == null) {
                this.mBudgetCycleLevlDates = new HashMap();
            } else {
                this.mBudgetCycleLevlDates.clear();
            }

            Iterator i$ = items.iterator();

            while (i$.hasNext()) {
                LevelDateEVO child = (LevelDateEVO) i$.next();
                this.mBudgetCycleLevlDates.put(child.getPK(), child);
            }
        } else {
            this.mBudgetCycleLevlDates = null;
        }

    }

    public void setBudgetCycleLinks(Collection<BudgetCycleLinkEVO> items) {
        if (items != null) {
            if (this.mBudgetCycleLinks == null)
                this.mBudgetCycleLinks = new HashMap();
            else {
                this.mBudgetCycleLinks.clear();
            }
            for (BudgetCycleLinkEVO child: items) {
                this.mBudgetCycleLinks.put(child.getPK(), child);
            }

        } else {
            this.mBudgetCycleLinks = null;
        }
    }

    public BudgetCyclePK getPK() {
        if (this.mPK == null) {
            this.mPK = new BudgetCyclePK(this.mBudgetCycleId);
        }

        return this.mPK;
    }

    public boolean isModified() {
        return this.mModified;
    }

    public int getBudgetCycleId() {
        return this.mBudgetCycleId;
    }

    public void setBudgetCycleId(int newBudgetCycleId) {
        if (this.mBudgetCycleId != newBudgetCycleId) {
            this.mModified = true;
            this.mBudgetCycleId = newBudgetCycleId;
            this.mPK = null;
        }
    }

    public int getModelId() {
        return this.mModelId;
    }

    public void setModelId(int newModelId) {
        if (this.mModelId != newModelId) {
            this.mModified = true;
            this.mModelId = newModelId;
        }
    }

    public String getVisId() {
        return this.mVisId;
    }

    public void setVisId(String newVisId) {
        if (this.mVisId != null && newVisId == null || this.mVisId == null && newVisId != null || this.mVisId != null && newVisId != null && !this.mVisId.equals(newVisId)) {
            this.mVisId = newVisId;
            this.mModified = true;
        }
    }

    public String getDescription() {
        return this.mDescription;
    }

    public void setDescription(String newDescription) {
        if (this.mDescription != null && newDescription == null || this.mDescription == null && newDescription != null || this.mDescription != null && newDescription != null && !this.mDescription.equals(newDescription)) {
            this.mDescription = newDescription;
            this.mModified = true;
        }
    }

    public int getType() {
        return this.mType;
    }

    public void setType(int newType) {
        if (this.mType != newType) {
            this.mModified = true;
            this.mType = newType;
        }
    }

    public int getXmlFormId() {
        return this.mXmlFormId;
    }

    public void setXmlFormId(int newXmlFormId) {
        if (this.mXmlFormId != newXmlFormId) {
            this.mModified = true;
            this.mXmlFormId = newXmlFormId;
        }
    }

    public String getXmlFormDataType() {
        return this.mXmlFormDataType;
    }

    public void setXmlFormDataType(String newXmlFormDataType) {
        if (this.mXmlFormDataType != null && newXmlFormDataType == null || this.mXmlFormDataType == null && newXmlFormDataType != null || this.mXmlFormDataType != null && newXmlFormDataType != null && !this.mXmlFormDataType.equals(newXmlFormDataType)) {
            this.mXmlFormDataType = newXmlFormDataType;
            this.mModified = true;
        }
    }

    public int getPeriodId() {
        return this.mPeriodId;
    }

    public void setPeriodId(int newPeriodId) {
        if (this.mPeriodId != newPeriodId) {
            this.mModified = true;
            this.mPeriodId = newPeriodId;
        }
    }

    public int getPeriodIdTo() {
        return this.mPeriodIdTo;
    }

    public void setPeriodIdTo(int newPeriodId) {
        if (this.mPeriodIdTo != newPeriodId) {
            this.mModified = true;
            this.mPeriodIdTo = newPeriodId;
        }
    }

    public String getPeriodFromVisId() {
        return this.mPeriodFromVisId;
    }

    public void setPeriodFromVisId(String newPeriodFromVisId) {
        if (this.mPeriodFromVisId != null && newPeriodFromVisId == null || this.mPeriodFromVisId == null && newPeriodFromVisId != null || this.mPeriodFromVisId != null && newPeriodFromVisId != null && !this.mPeriodFromVisId.equals(newPeriodFromVisId)) {
            this.mModified = true;
            this.mPeriodFromVisId = newPeriodFromVisId;
        }
    }

    public String getPeriodToVisId() {
        return this.mPeriodToVisId;
    }

    public void setPeriodToVisId(String newPeriodToVisId) {
        if (this.mPeriodToVisId != null && newPeriodToVisId == null || this.mPeriodToVisId == null && newPeriodToVisId != null || this.mPeriodToVisId != null && newPeriodToVisId != null && !this.mPeriodToVisId.equals(newPeriodToVisId)) {
            this.mModified = true;
            this.mPeriodToVisId = newPeriodToVisId;
        }
    }

    public Timestamp getPlannedEndDate() {
        return this.mPlannedEndDate;
    }

    public void setPlannedEndDate(Timestamp newPlannedEndDate) {
        if (this.mPlannedEndDate != null && newPlannedEndDate == null || this.mPlannedEndDate == null && newPlannedEndDate != null || this.mPlannedEndDate != null && newPlannedEndDate != null && !this.mPlannedEndDate.equals(newPlannedEndDate)) {
            this.mPlannedEndDate = newPlannedEndDate;
            this.mModified = true;
        }
    }

    public Timestamp getStartDate() {
        return this.mStartDate;
    }

    public void setStartDate(Timestamp newStartDate) {
        if (this.mStartDate != null && newStartDate == null || this.mStartDate == null && newStartDate != null || this.mStartDate != null && newStartDate != null && !this.mStartDate.equals(newStartDate)) {
            this.mStartDate = newStartDate;
            this.mModified = true;
        }
    }

    public Timestamp getEndDate() {
        return this.mEndDate;
    }

    public void setEndDate(Timestamp newEndDate) {
        if (this.mEndDate != null && newEndDate == null || this.mEndDate == null && newEndDate != null || this.mEndDate != null && newEndDate != null && !this.mEndDate.equals(newEndDate)) {
            this.mEndDate = newEndDate;
            this.mModified = true;
        }
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void setStatus(int newStatus) {
        if (this.mStatus != newStatus) {
            this.mModified = true;
            this.mStatus = newStatus;
        }
    }

    public int getVersionNum() {
        return this.mVersionNum;
    }

    public void setVersionNum(int newVersionNum) {
        if (this.mVersionNum != newVersionNum) {
            this.mModified = true;
            this.mVersionNum = newVersionNum;
        }
    }

    public int getUpdatedByUserId() {
        return this.mUpdatedByUserId;
    }

    public void setUpdatedByUserId(int newUpdatedByUserId) {
        this.mUpdatedByUserId = newUpdatedByUserId;
    }

    public Timestamp getUpdatedTime() {
        return this.mUpdatedTime;
    }

    protected void setUpdatedTime(Timestamp newUpdatedTime) {
        this.mUpdatedTime = newUpdatedTime;
    }

    public Timestamp getCreatedTime() {
        return this.mCreatedTime;
    }

    protected void setCreatedTime(Timestamp newCreatedTime) {
        this.mCreatedTime = newCreatedTime;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String newCategory) {
        if(this.mCategory != null && newCategory == null || this.mCategory == null && newCategory != null || this.mCategory != null && newCategory != null && !this.mCategory.equals(newCategory)) {
            this.mModified = true;
            this.mCategory = newCategory;
        }
    }

    public void setDetails(BudgetCycleEVO newDetails) {
        this.setBudgetCycleId(newDetails.getBudgetCycleId());
        this.setModelId(newDetails.getModelId());
        this.setVisId(newDetails.getVisId());
        this.setDescription(newDetails.getDescription());
        this.setType(newDetails.getType());
        this.setXmlFormId(newDetails.getXmlFormId());
        this.setXmlFormDataType(newDetails.getXmlFormDataType());
        this.setPeriodId(newDetails.getPeriodId());
        this.setPlannedEndDate(newDetails.getPlannedEndDate());
        this.setPeriodFromVisId(newDetails.getPeriodFromVisId());
        this.setPeriodToVisId(newDetails.getPeriodToVisId());
        this.setStartDate(newDetails.getStartDate());
        this.setEndDate(newDetails.getEndDate());
        this.setStatus(newDetails.getStatus());
        this.setVersionNum(newDetails.getVersionNum());
        this.setUpdatedByUserId(newDetails.getUpdatedByUserId());
        this.setUpdatedTime(newDetails.getUpdatedTime());
        this.setCreatedTime(newDetails.getCreatedTime());
        this.setCategory(newDetails.getCategory());
    }

    public BudgetCycleEVO deepClone() {
        BudgetCycleEVO cloned = null;

        try {
            ByteArrayOutputStream e = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(e);
            oos.writeObject(this);
            oos.flush();
            oos.close();
            ByteArrayInputStream bis = new ByteArrayInputStream(e.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis);
            cloned = (BudgetCycleEVO) ois.readObject();
            ois.close();
            return cloned;
        } catch (Exception var6) {
            throw new RuntimeException(var6);
        }
    }

    public void prepareForInsert(ModelEVO parent) {
        boolean newKey = this.insertPending();
        if (this.mBudgetCycleId > 0) {
            newKey = true;
            if (parent == null) {
                this.setBudgetCycleId(-this.mBudgetCycleId);
            } else {
                parent.changeKey(this, -this.mBudgetCycleId);
            }
        } else if (this.mBudgetCycleId < 1) {
            newKey = true;
        }

        this.setVersionNum(0);

        if (this.mBudgetCycleLinks != null) {
            Iterator iter = new ArrayList(this.mBudgetCycleLinks.values()).iterator();
            while (iter.hasNext()) {
                BudgetCycleLinkEVO item = (BudgetCycleLinkEVO) iter.next();
                if (newKey) {
                    item.setInsertPending();
                }
                item.prepareForInsert(this);
            }
        }

        Iterator iter;
        BudgetStateEVO item;
        if (this.mBudgetCycleStates != null) {
            for (iter = (new ArrayList(this.mBudgetCycleStates.values())).iterator(); iter.hasNext(); item.prepareForInsert(this)) {
                item = (BudgetStateEVO) iter.next();
                if (newKey) {
                    item.setInsertPending();
                }
            }
        }

        LevelDateEVO item1;
        if (this.mBudgetCycleLevlDates != null) {
            for (iter = (new ArrayList(this.mBudgetCycleLevlDates.values())).iterator(); iter.hasNext(); item1.prepareForInsert(this)) {
                item1 = (LevelDateEVO) iter.next();
                if (newKey) {
                    item1.setInsertPending();
                }
            }
        }
    }

    public int getInsertCount(int startCount) {
        int returnCount = startCount;
        if (this.mBudgetCycleId < 1) {
            returnCount = startCount + 1;
        }

        if (this.mBudgetCycleLinks != null) {
            Iterator iter = this.mBudgetCycleLinks.values().iterator();
            while (iter.hasNext()) {
                BudgetCycleLinkEVO item = (BudgetCycleLinkEVO) iter.next();
                returnCount = item.getInsertCount(returnCount);
            }
        }

        Iterator iter;
        BudgetStateEVO item;
        if (this.mBudgetCycleStates != null) {
            for (iter = this.mBudgetCycleStates.values().iterator(); iter.hasNext(); returnCount = item.getInsertCount(returnCount)) {
                item = (BudgetStateEVO) iter.next();
            }
        }

        LevelDateEVO item1;
        if (this.mBudgetCycleLevlDates != null) {
            for (iter = this.mBudgetCycleLevlDates.values().iterator(); iter.hasNext(); returnCount = item1.getInsertCount(returnCount)) {
                item1 = (LevelDateEVO) iter.next();
            }
        }

        return returnCount;
    }

    public int assignNextKey(ModelEVO parent, int startKey) {
        int nextKey = startKey;
        if (this.mBudgetCycleId < 1) {
            parent.changeKey(this, startKey);
            nextKey = startKey + 1;
        }

        if (this.mBudgetCycleLinks != null) {
            Iterator iter = new ArrayList(this.mBudgetCycleLinks.values()).iterator();
            while (iter.hasNext()) {
                BudgetCycleLinkEVO item = (BudgetCycleLinkEVO) iter.next();
                this.changeKey(item, this.mBudgetCycleId, item.getXmlFormId());
                nextKey = item.assignNextKey(this, nextKey);
            }
        }

        Iterator iter;
        BudgetStateEVO item;
        if (this.mBudgetCycleStates != null) {
            for (iter = (new ArrayList(this.mBudgetCycleStates.values())).iterator(); iter.hasNext(); nextKey = item.assignNextKey(this, nextKey)) {
                item = (BudgetStateEVO) iter.next();
                this.changeKey(item, this.mBudgetCycleId, item.getStructureElementId());
            }
        }

        LevelDateEVO item1;
        if (this.mBudgetCycleLevlDates != null) {
            for (iter = (new ArrayList(this.mBudgetCycleLevlDates.values())).iterator(); iter.hasNext(); nextKey = item1.assignNextKey(this, nextKey)) {
                item1 = (LevelDateEVO) iter.next();
                this.changeKey(item1, this.mBudgetCycleId, item1.getDepth());
            }
        }

        return nextKey;
    }

    public Collection<BudgetStateEVO> getBudgetCycleStates() {
        return this.mBudgetCycleStates != null ? this.mBudgetCycleStates.values() : null;
    }

    public Map<BudgetStatePK, BudgetStateEVO> getBudgetCycleStatesMap() {
        return this.mBudgetCycleStates;
    }

    public void loadBudgetCycleStatesItem(BudgetStateEVO newItem) {
        if (this.mBudgetCycleStates == null) {
            this.mBudgetCycleStates = new HashMap();
        }

        this.mBudgetCycleStates.put(newItem.getPK(), newItem);
    }

    public void addBudgetCycleStatesItem(BudgetStateEVO newItem) {
        if (this.mBudgetCycleStates == null) {
            this.mBudgetCycleStates = new HashMap();
        }

        BudgetStatePK newPK = newItem.getPK();
        if (this.getBudgetCycleStatesItem(newPK) != null) {
            throw new RuntimeException("addBudgetCycleStatesItem: key already in list");
        } else {
            newItem.setInsertPending();
            this.mBudgetCycleStates.put(newPK, newItem);
        }
    }

    public void changeBudgetCycleStatesItem(BudgetStateEVO changedItem) {
        if (this.mBudgetCycleStates == null) {
            throw new RuntimeException("changeBudgetCycleStatesItem: no items in collection");
        } else {
            BudgetStatePK changedPK = changedItem.getPK();
            BudgetStateEVO listItem = this.getBudgetCycleStatesItem(changedPK);
            if (listItem == null) {
                throw new RuntimeException("changeBudgetCycleStatesItem: item not in list");
            } else {
                listItem.setDetails(changedItem);
            }
        }
    }

    public void deleteBudgetCycleStatesItem(BudgetStatePK removePK) {
        BudgetStateEVO listItem = this.getBudgetCycleStatesItem(removePK);
        if (listItem == null) {
            throw new RuntimeException("removeBudgetCycleStatesItem: item not in list");
        } else {
            listItem.setDeletePending();
        }
    }

    public BudgetStateEVO getBudgetCycleStatesItem(BudgetStatePK pk) {
        return (BudgetStateEVO) this.mBudgetCycleStates.get(pk);
    }

    public BudgetStateEVO getBudgetCycleStatesItem() {
        if (this.mBudgetCycleStates.size() != 1) {
            throw new RuntimeException("should be 1 item but size=" + this.mBudgetCycleStates.size());
        } else {
            Iterator iter = this.mBudgetCycleStates.values().iterator();
            return (BudgetStateEVO) iter.next();
        }
    }

    public Collection<LevelDateEVO> getBudgetCycleLevlDates() {
        return this.mBudgetCycleLevlDates != null ? this.mBudgetCycleLevlDates.values() : null;
    }

    public Map<LevelDatePK, LevelDateEVO> getBudgetCycleLevlDatesMap() {
        return this.mBudgetCycleLevlDates;
    }

    public Collection<BudgetCycleLinkEVO> getBudgetCycleLinks() {
        return this.mBudgetCycleLinks != null ? this.mBudgetCycleLinks.values() : null;
    }

    public Map<BudgetCycleLinkPK, BudgetCycleLinkEVO> getBudgetCycleLinksMap() {
        return mBudgetCycleLinks;
    }

    public void loadBudgetCycleLinkItem(BudgetCycleLinkEVO newItem) {
        if (this.mBudgetCycleLinks == null) {
            this.mBudgetCycleLinks = new HashMap();
        }

        this.mBudgetCycleLinks.put(newItem.getPK(), newItem);
    }

    public void addBudgetCycleLinkItem(BudgetCycleLinkEVO newItem) {
        if (this.mBudgetCycleLinks == null) {
            this.mBudgetCycleLinks = new HashMap();
        }
        BudgetCycleLinkPK newPK = newItem.getPK();

        if (getBudgetCycleLinkItem(newPK) != null) {
            throw new RuntimeException("addBudgetCycleLinkItem: key already in list");
        }
        newItem.setInsertPending();

        this.mBudgetCycleLinks.put(newPK, newItem);
    }

    public void changeBudgetCycleLinkItem(BudgetCycleLinkEVO changedItem) {
        if (this.mBudgetCycleLinks == null) {
            throw new RuntimeException("changeBudgetCycleLinkItem: no items in collection");
        }
        BudgetCycleLinkPK changedPK = changedItem.getPK();

        BudgetCycleLinkEVO listItem = getBudgetCycleLinkItem(changedPK);
        if (listItem == null) {
            throw new RuntimeException("changeBudgetCycleLinkItem: item not in list");
        }
        listItem.setDetails(changedItem);
    }

    public void deleteBudgetCycleLinkItem(BudgetCycleLinkPK removePK) {
        BudgetCycleLinkEVO listItem = getBudgetCycleLinkItem(removePK);

        if (listItem == null) {
            throw new RuntimeException("deleteBudgetCycleLinkItem: item not in list");
        }
        listItem.setDeletePending();
    }

    public BudgetCycleLinkEVO getBudgetCycleLinkItem(BudgetCycleLinkPK pk) {
        return (BudgetCycleLinkEVO) this.mBudgetCycleLinks.get(pk);
    }

    public BudgetCycleLinkEVO getUserRolesItem() {
        if (this.mBudgetCycleLinks.size() != 1) {
            throw new RuntimeException("should be 1 item but size=" + this.mBudgetCycleLinks.size());
        }
        Iterator iter = this.mBudgetCycleLinks.values().iterator();
        return (BudgetCycleLinkEVO) iter.next();
    }

    public void loadBudgetCycleLevlDatesItem(LevelDateEVO newItem) {
        if (this.mBudgetCycleLevlDates == null) {
            this.mBudgetCycleLevlDates = new HashMap();
        }

        this.mBudgetCycleLevlDates.put(newItem.getPK(), newItem);
    }

    public void addBudgetCycleLevlDatesItem(LevelDateEVO newItem) {
        if (this.mBudgetCycleLevlDates == null) {
            this.mBudgetCycleLevlDates = new HashMap();
        }

        LevelDatePK newPK = newItem.getPK();
        if (this.getBudgetCycleLevlDatesItem(newPK) != null) {
            throw new RuntimeException("addBudgetCycleLevlDatesItem: key already in list");
        } else {
            newItem.setInsertPending();
            this.mBudgetCycleLevlDates.put(newPK, newItem);
        }
    }

    public void changeBudgetCycleLevlDatesItem(LevelDateEVO changedItem) {
        if (this.mBudgetCycleLevlDates == null) {
            throw new RuntimeException("changeBudgetCycleLevlDatesItem: no items in collection");
        } else {
            LevelDatePK changedPK = changedItem.getPK();
            LevelDateEVO listItem = this.getBudgetCycleLevlDatesItem(changedPK);
            if (listItem == null) {
                throw new RuntimeException("changeBudgetCycleLevlDatesItem: item not in list");
            } else {
                listItem.setDetails(changedItem);
            }
        }
    }

    public void deleteBudgetCycleLevlDatesItem(LevelDatePK removePK) {
        LevelDateEVO listItem = this.getBudgetCycleLevlDatesItem(removePK);
        if (listItem == null) {
            throw new RuntimeException("removeBudgetCycleLevlDatesItem: item not in list");
        } else {
            listItem.setDeletePending();
        }
    }

    public LevelDateEVO getBudgetCycleLevlDatesItem(LevelDatePK pk) {
        return (LevelDateEVO) this.mBudgetCycleLevlDates.get(pk);
    }

    public LevelDateEVO getBudgetCycleLevlDatesItem() {
        if (this.mBudgetCycleLevlDates.size() != 1) {
            throw new RuntimeException("should be 1 item but size=" + this.mBudgetCycleLevlDates.size());
        } else {
            Iterator iter = this.mBudgetCycleLevlDates.values().iterator();
            return (LevelDateEVO) iter.next();
        }
    }

    public void setInsertPending() {
        this.mInsertPending = true;
    }

    public boolean insertPending() {
        return this.mInsertPending;
    }

    public void setDeletePending() {
        this.mDeletePending = true;
    }

    public boolean deletePending() {
        return this.mDeletePending;
    }

    protected void reset() {
        this.mModified = false;
        this.mInsertPending = false;
    }

    public BudgetCycleRef getEntityRef(ModelEVO evoModel) {
        return new BudgetCycleRefImpl(new BudgetCycleCK(evoModel.getPK(), this.getPK()), this.mVisId);
    }

    public BudgetCycleCK getCK(ModelEVO evoModel) {
        return new BudgetCycleCK(evoModel.getPK(), this.getPK());
    }

    public void postCreateInit() {
        this.mBudgetCycleLinksAllItemsLoaded = true;
        if (this.mBudgetCycleLinks == null) {
            this.mBudgetCycleLinks = new HashMap();
        }

        this.mBudgetCycleStatesAllItemsLoaded = true;
        if (this.mBudgetCycleStates == null) {
            this.mBudgetCycleStates = new HashMap();
        } else {
            Iterator i$ = this.mBudgetCycleStates.values().iterator();

            while (i$.hasNext()) {
                BudgetStateEVO child = (BudgetStateEVO) i$.next();
                child.postCreateInit();
            }
        }

        this.mBudgetCycleLevlDatesAllItemsLoaded = true;
        if (this.mBudgetCycleLevlDates == null) {
            this.mBudgetCycleLevlDates = new HashMap();
        }

    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("BudgetCycleId=");
        sb.append(String.valueOf(this.mBudgetCycleId));
        sb.append(' ');
        sb.append("ModelId=");
        sb.append(String.valueOf(this.mModelId));
        sb.append(' ');
        sb.append("VisId=");
        sb.append(String.valueOf(this.mVisId));
        sb.append(' ');
        sb.append("Description=");
        sb.append(String.valueOf(this.mDescription));
        sb.append(' ');
        sb.append("Type=");
        sb.append(String.valueOf(this.mType));
        sb.append(' ');
        sb.append("XmlFormId=");
        sb.append(String.valueOf(this.mXmlFormId));
        sb.append(' ');
        sb.append("XmlFormDataType=");
        sb.append(String.valueOf(this.mXmlFormDataType));
        sb.append(' ');
        sb.append("PeriodId=");
        sb.append(String.valueOf(this.mPeriodId));
        sb.append(' ');
        sb.append("PlannedEndDate=");
        sb.append(String.valueOf(this.mPlannedEndDate));
        sb.append(' ');
        sb.append("StartDate=");
        sb.append(String.valueOf(this.mStartDate));
        sb.append(' ');
        sb.append("EndDate=");
        sb.append(String.valueOf(this.mEndDate));
        sb.append(' ');
        sb.append("Status=");
        sb.append(String.valueOf(this.mStatus));
        sb.append(' ');
        sb.append("VersionNum=");
        sb.append(String.valueOf(this.mVersionNum));
        sb.append(' ');
        sb.append("UpdatedByUserId=");
        sb.append(String.valueOf(this.mUpdatedByUserId));
        sb.append(' ');
        sb.append("UpdatedTime=");
        sb.append(String.valueOf(this.mUpdatedTime));
        sb.append(' ');
        sb.append("CreatedTime=");
        sb.append(String.valueOf(this.mCreatedTime));
        sb.append(' ');
        sb.append("Category=");
        sb.append(String.valueOf(this.mCategory));
        sb.append(' ');
        if (this.mModified) {
            sb.append("modified ");
        }
        if (this.mInsertPending) {
            sb.append("insertPending ");
        }
        if (this.mDeletePending) {
            sb.append("deletePending ");
        }
        return sb.toString();
    }

    public String print() {
        return this.print(0);
    }

    public String print(int indent) {
        StringBuffer sb = new StringBuffer();

        for (int i$ = 0; i$ < indent; ++i$) {
            sb.append(' ');
        }

        sb.append("BudgetCycle: ");
        sb.append(this.toString());
        if (this.mBudgetCycleLinksAllItemsLoaded || this.mBudgetCycleLinks != null) {
            sb.delete(indent, sb.length());
            sb.append(" - BudgetCycleLinks: allItemsLoaded=");
            sb.append(String.valueOf(this.mBudgetCycleLinksAllItemsLoaded));
            sb.append(" items=");
            if (this.mBudgetCycleLinks == null) {
                sb.append("null");
            } else {
                sb.append(String.valueOf(this.mBudgetCycleLinks.size()));
            }
        }

        if (this.mBudgetCycleStatesAllItemsLoaded || this.mBudgetCycleStates != null) {
            sb.delete(indent, sb.length());
            sb.append(" - BudgetCycleStates: allItemsLoaded=");
            sb.append(String.valueOf(this.mBudgetCycleStatesAllItemsLoaded));
            sb.append(" items=");
            if (this.mBudgetCycleStates == null) {
                sb.append("null");
            } else {
                sb.append(String.valueOf(this.mBudgetCycleStates.size()));
            }
        }

        if (this.mBudgetCycleLevlDatesAllItemsLoaded || this.mBudgetCycleLevlDates != null) {
            sb.delete(indent, sb.length());
            sb.append(" - BudgetCycleLevlDates: allItemsLoaded=");
            sb.append(String.valueOf(this.mBudgetCycleLevlDatesAllItemsLoaded));
            sb.append(" items=");
            if (this.mBudgetCycleLevlDates == null) {
                sb.append("null");
            } else {
                sb.append(String.valueOf(this.mBudgetCycleLevlDates.size()));
            }
        }

        Iterator var5;
        if (this.mBudgetCycleLinks != null) {
            var5 = this.mBudgetCycleLinks.values().iterator();

            while (var5.hasNext()) {
                BudgetCycleLinkEVO listItem = (BudgetCycleLinkEVO) var5.next();
                listItem.print(indent + 4);
            }
        }

        if (this.mBudgetCycleStates != null) {
            var5 = this.mBudgetCycleStates.values().iterator();

            while (var5.hasNext()) {
                BudgetStateEVO listItem = (BudgetStateEVO) var5.next();
                listItem.print(indent + 4);
            }
        }

        if (this.mBudgetCycleLevlDates != null) {
            var5 = this.mBudgetCycleLevlDates.values().iterator();

            while (var5.hasNext()) {
                LevelDateEVO listItem = (LevelDateEVO) var5.next();
                listItem.print(indent + 4);
            }
        }

        return sb.toString();
    }

    public void changeKey(BudgetStateEVO child, int newBudgetCycleId, int newStructureElementId) {
        if (this.getBudgetCycleStatesItem(child.getPK()) != child) {
            throw new IllegalStateException("changeKey child not found in parent");
        } else {
            this.mBudgetCycleStates.remove(child.getPK());
            child.setBudgetCycleId(newBudgetCycleId);
            child.setStructureElementId(newStructureElementId);
            this.mBudgetCycleStates.put(child.getPK(), child);
        }
    }

    public void changeKey(BudgetCycleLinkEVO child, int newBudgetCycleId, int newXmlFormId) {
        if (this.getBudgetCycleLinkItem(child.getPK()) != child) {
            throw new IllegalStateException("changeKey child not found in parent");
        } else {
            this.mBudgetCycleLinks.remove(child.getPK());
            child.setBudgetCycleId(newBudgetCycleId);
            child.setXmlFormId(newXmlFormId);
            this.mBudgetCycleLinks.put(child.getPK(), child);
        }
    }

    public void changeKey(LevelDateEVO child, int newBudgetCycleId, int newDepth) {
        if (this.getBudgetCycleLevlDatesItem(child.getPK()) != child) {
            throw new IllegalStateException("changeKey child not found in parent");
        } else {
            this.mBudgetCycleLevlDates.remove(child.getPK());
            child.setBudgetCycleId(newBudgetCycleId);
            child.setDepth(newDepth);
            this.mBudgetCycleLevlDates.put(child.getPK(), child);
        }
    }

    public void setBudgetCycleStatesAllItemsLoaded(boolean allItemsLoaded) {
        this.mBudgetCycleStatesAllItemsLoaded = allItemsLoaded;
    }

    public boolean isBudgetCycleStatesAllItemsLoaded() {
        return this.mBudgetCycleStatesAllItemsLoaded;
    }

    public void setBudgetCycleLevlDatesAllItemsLoaded(boolean allItemsLoaded) {
        this.mBudgetCycleLevlDatesAllItemsLoaded = allItemsLoaded;
    }

    public boolean isBudgetCycleLevlDatesAllItemsLoaded() {
        return this.mBudgetCycleLevlDatesAllItemsLoaded;
    }

    public void setBudgetCycleLinksAllItemsLoaded(boolean allItemsLoaded) {
        this.mBudgetCycleLinksAllItemsLoaded = allItemsLoaded;
    }

    public boolean isBudgetCycleLinksAllItemsLoaded() {
        return this.mBudgetCycleLinksAllItemsLoaded;
    }
}