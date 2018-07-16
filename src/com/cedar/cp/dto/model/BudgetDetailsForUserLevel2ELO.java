// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.model;

import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.dto.base.AbstractELO;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsForCycleELO;
import com.cedar.cp.dto.model.BudgetDetailsForUserLevel3ELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BudgetDetailsForUserLevel2ELO extends AbstractELO implements Serializable {

    private transient BudgetCycleRef mBudgetCycleRef;
    private transient Integer mBudgetCycleId;
    private transient Integer mModelId;
    private transient BudgetDetailsForUserLevel3ELO mLocations;
    private transient Integer mHierarchyId;
    private transient AllBudgetInstructionsForCycleELO mCycleInstructions;
    private transient String mCategory;

    public BudgetDetailsForUserLevel2ELO() {
        super(new String[] { "BudgetCycle", "BudgetCycleId", "ModelId", "BudgetLocations", "HierarchyId", "CycleInstructions", "Category" });
    }

    public void add(BudgetCycleRef eRefBudgetCycle, Integer budgetCycleId, Integer modelId, BudgetDetailsForUserLevel3ELO locations, Integer hierarchyId, AllBudgetInstructionsForCycleELO cycleInstructions, String category) {
        ArrayList l = new ArrayList();
        l.add(eRefBudgetCycle);
        l.add(budgetCycleId);
        l.add(modelId);
        l.add(locations);
        l.add(hierarchyId);
        l.add(cycleInstructions);
        l.add(category);
        this.mCollection.add(l);
    }

    public void next() {
        if (this.mIterator == null) {
            this.mCurrRowIndex = -1;
            this.reset();
        }

        ++this.mCurrRowIndex;
        List l = (List) this.mIterator.next();
        byte index = 0;
        int var4 = index + 1;
        this.mBudgetCycleRef = (BudgetCycleRef) l.get(index);
        this.mBudgetCycleId = (Integer) l.get(var4++);
        this.mModelId = (Integer) l.get(var4++);
        this.mLocations = (BudgetDetailsForUserLevel3ELO) l.get(var4++);
        this.mHierarchyId = (Integer) l.get(var4++);
        this.mCycleInstructions = (AllBudgetInstructionsForCycleELO) l.get(var4++);
        this.mCategory = (String) l.get(var4++);
    }

    public BudgetCycleRef getBudgetCycleRef() {
        return this.mBudgetCycleRef;
    }

    public Integer getBudgetCycleId() {
        return this.mBudgetCycleId;
    }

    public Integer getModelId() {
        return this.mModelId;
    }

    public BudgetDetailsForUserLevel3ELO getLocations() {
        return this.mLocations;
    }

    public Integer getHierarchyId() {
        return this.mHierarchyId;
    }

    public AllBudgetInstructionsForCycleELO getCycleInstruction() {
        return this.mCycleInstructions;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }
}
