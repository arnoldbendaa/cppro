// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.BudgetCycle;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCyclePK;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.swing.tree.TreeModel;

@SuppressWarnings("serial")
public class BudgetCycleImpl implements BudgetCycle, Serializable, Cloneable {

    private transient XmlFormRef mFormRef;
    private EntityRef mPeriodRef;
    private EntityRef mPeriodToRef;
    private DimensionRef mDimensionRef;
    private EntityRef mRootElementEntityRef;
    private transient TreeModel mCalanderModel;
    private List<Timestamp> mLevelDates;
    private List<Object[]> mXmlForms;
    private boolean mDateChanged;
    private Object mPrimaryKey;
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
    private ModelRef mModelRef;
    private String mCategory;

    public BudgetCycleImpl(Object paramKey) {
        this.mPrimaryKey = paramKey;
        this.mModelId = 0;
        this.mVisId = "";
        this.mDescription = "";
        this.mType = 0;
        this.mXmlFormId = 0;
        this.mXmlFormDataType = "";
        this.mPeriodId = 0;
        this.mPeriodIdTo = 0;
        this.mPeriodFromVisId = "";
        this.mPeriodToVisId = "";
        this.mPlannedEndDate = null;
        this.mStartDate = null;
        this.mEndDate = null;
        this.mStatus = 0;
        this.mCategory = "B";

        mXmlForms = new ArrayList<Object[]>();
    }

    public Object getPrimaryKey() {
        return this.mPrimaryKey;
    }

    public void setPrimaryKey(Object paramKey) {
        this.mPrimaryKey = (BudgetCyclePK) paramKey;
    }

    public void setPrimaryKey(BudgetCycleCK paramKey) {
        this.mPrimaryKey = paramKey;
    }

    public int getModelId() {
        return this.mModelId;
    }

    public void setModelId(int paramModelId) {
        this.mModelId = paramModelId;
    }

    public ModelRef getModelRef() {
        return this.mModelRef;
    }

    public void setModelRef(ModelRef ref) {
        this.mModelRef = ref;
    }

    public String getVisId() {
        return this.mVisId;
    }

    public void setVisId(String paramVisId) {
        this.mVisId = paramVisId;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public void setDescription(String paramDescription) {
        this.mDescription = paramDescription;
    }

    public int getType() {
        return this.mType;
    }

    public void setType(int paramType) {
        this.mType = paramType;
    }

    public int getXmlFormId() {
        return this.mXmlFormId;
    }

    public void setXmlFormId(int paramXmlFormId) {
        this.mXmlFormId = paramXmlFormId;
    }

    public String getXmlFormDataType() {
        return this.mXmlFormDataType;
    }

    public void setXmlFormDataType(String paramXmlFormDataType) {
        this.mXmlFormDataType = paramXmlFormDataType;
    }

    public int getPeriodId() {
        return this.mPeriodId;
    }

    public void setPeriodId(int paramPeriodId) {
        this.mPeriodId = paramPeriodId;
    }

    public String getPeriodFromVisId() {
        return mPeriodFromVisId;
    }

    public void setPeriodFromVisId(String mPeriodFromVisId) {
        this.mPeriodFromVisId = mPeriodFromVisId;
    }

    public EntityRef getPeriodRef() {
        return this.mPeriodRef;
    }

    public void setPeriodRef(EntityRef ref) {
        this.mPeriodRef = ref;
    }

    public int getPeriodIdTo() {
        return this.mPeriodIdTo;
    }

    public void setPeriodIdTo(int paramPeriodId) {
        this.mPeriodIdTo = paramPeriodId;
    }

    public String getPeriodToVisId() {
        return mPeriodToVisId;
    }

    public void setPeriodToVisId(String mPeriodToVisId) {
        this.mPeriodToVisId = mPeriodToVisId;
    }

    public EntityRef getPeriodToRef() {
        return this.mPeriodToRef;
    }

    public void setPeriodToRef(EntityRef ref) {
        this.mPeriodToRef = ref;
    }

    public Timestamp getPlannedEndDate() {
        return this.mPlannedEndDate;
    }

    public void setPlannedEndDate(Timestamp paramPlannedEndDate) {
        this.mPlannedEndDate = paramPlannedEndDate;
    }

    public Timestamp getStartDate() {
        return this.mStartDate;
    }

    public void setStartDate(Timestamp paramStartDate) {
        this.mStartDate = paramStartDate;
    }

    public Timestamp getEndDate() {
        return this.mEndDate;
    }

    public void setEndDate(Timestamp paramEndDate) {
        this.mEndDate = paramEndDate;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public void setStatus(int paramStatus) {
        this.mStatus = paramStatus;
    }

    public XmlFormRef getFinanceFormRef() {
        return this.mFormRef;
    }

    public void setXmlFormRef(XmlFormRef ref) {
        this.mFormRef = ref;
    }

    public EntityRef getRootElementEntityRef() {
        return this.mRootElementEntityRef;
    }

    public void setRootElementEntityRef(EntityRef pRootElementEntityRef) {
        this.mRootElementEntityRef = pRootElementEntityRef;
    }

    public DimensionRef getDimensionRef() {
        return this.mDimensionRef;
    }

    public void setDimensionRef(DimensionRef pDimensionRef) {
        this.mDimensionRef = pDimensionRef;
    }

    public TreeModel getCalanderModel() {
        return this.mCalanderModel;
    }

    public void setCalanderModel(TreeModel calanderModel) {
        this.mCalanderModel = calanderModel;
    }

    public List<Timestamp> getLevelDates() {
        return this.mLevelDates;
    }

    public void setLevelDates(List<Timestamp> levelDates) {
        this.mLevelDates = levelDates;
    }

    public boolean isDateChanged() {
        return this.mDateChanged;
    }

    public void setDateChanged(boolean dateChanged) {
        this.mDateChanged = dateChanged;
    }

    public List<Object[]> getXmlForms() {
        return this.mXmlForms;
    }

    public void setXmlForms(List<Object[]> xmlForms) {
        this.mXmlForms = xmlForms;
    }

    public int getVersionNum() {
        return this.mVersionNum;
    }

    public void setVersionNum(int p) {
        this.mVersionNum = p;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }

}