// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.base.AbstractELO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllBudgetCyclesELO extends AbstractELO implements Serializable {

    private static final String[] mEntity = new String[] { "BudgetCycle", "Model", "BudgetState", "BudgetStateHistory", "LevelDate", "BudgetInstructionAssignment" };
    private transient BudgetCycleRef mBudgetCycleEntityRef;
    private transient ModelRef mModelEntityRef;
    private transient String mDescription;
    private transient int mStatus;
    private transient int mPeriodFrom;
    private transient int mPeriodTo;
    private transient String mPeriodFromVisId;
    private transient String mPeriodToVisId;
    private transient String mCategory;

    public AllBudgetCyclesELO() {
        super(new String[] { "BudgetCycle", "Model", "Description", "Status", "PeriodFrom", "PeriodTo", "PeriodFromVisId", "PeriodToVisId", "Category" });
    }

    public AllBudgetCyclesELO(BudgetCycleRef budgetCycleEntityRef, String description) {
        super(new String[] { "BudgetCycle", "Description" });
        this.mBudgetCycleEntityRef = budgetCycleEntityRef;
        this.mDescription = description;
    }

    public void add(BudgetCycleRef eRefBudgetCycle, ModelRef eRefModel, String col1, int col2, int periodFrom, int periodTo, String periodFromVisId, String periodToVisId, String category) {
        ArrayList l = new ArrayList();
        l.add(eRefBudgetCycle);
        l.add(eRefModel);
        l.add(col1);
        l.add(new Integer(col2));
        l.add(periodFrom);
        l.add(periodTo);
        l.add(periodFromVisId);
        l.add(periodToVisId);
        l.add(category);
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
        this.mBudgetCycleEntityRef = (BudgetCycleRef) l.get(index);
        this.mModelEntityRef = (ModelRef) l.get(var4++);
        this.mDescription = (String) l.get(var4++);
        this.mStatus = ((Integer) l.get(var4++)).intValue();

        this.mPeriodFrom = ((Integer) l.get(var4++)).intValue();
        this.mPeriodTo = ((Integer) l.get(var4++)).intValue();
        this.mPeriodFromVisId = (String) l.get(var4++);
        this.mPeriodToVisId = (String) l.get(var4++);
        this.mCategory = (String) l.get(var4++);
    }

    public BudgetCycleRef getBudgetCycleEntityRef() {
        return this.mBudgetCycleEntityRef;
    }

    public ModelRef getModelEntityRef() {
        return this.mModelEntityRef;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public int getStatus() {
        return this.mStatus;
    }

    public boolean includesEntity(String name) {
        for (int i = 0; i < mEntity.length; ++i) {
            if (name.equals(mEntity[i])) {
                return true;
            }
        }

        return false;
    }

    public String getPeriodFromVisId() {
        return mPeriodFromVisId;
    }

    public void setPeriodFromVisId(String periodFromVisId) {
        this.mPeriodFromVisId = periodFromVisId;
    }

    public String getPeriodToVisId() {
        return mPeriodToVisId;
    }

    public void setPeriodToVisId(String periodToVisId) {
        this.mPeriodToVisId = periodToVisId;
    }

    public int getPeriodFrom() {
        return mPeriodFrom;
    }

    public void setPeriodFrom(int periodFrom) {
        this.mPeriodFrom = periodFrom;
    }

    public int getPeriodTo() {
        return mPeriodTo;
    }

    public void setPeriodTo(int periodTo) {
        this.mPeriodTo = periodTo;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }
}