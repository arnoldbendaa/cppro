// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:55
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.homepage;

import java.io.Serializable;
import java.util.List;

public class BudgetCycleDTO implements Serializable {

    private Object budgetCycle;
    private int modelId;
    private int budgetCycleId;
    private List budgetLocations;
    private int hierachyId;
    private List budgetCycleInstructions;
    private String mCategory;

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        this.mCategory = category;
    }

    public String getBudgetCycle() {
        String result = this.budgetCycle.toString();
        if (result == null) {
            result = "No Description";
        }

        return result;
    }

    public void setBudgetCycle(Object budgetCycle) {
        this.budgetCycle = budgetCycle;
    }

    public int getModelId() {
        return this.modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getBudgetCycleId() {
        return this.budgetCycleId;
    }

    public void setBudgetCycleId(int budgetCycleId) {
        this.budgetCycleId = budgetCycleId;
    }

    public List getBudgetLocations() {
        return this.budgetLocations;
    }

    public void setBudgetLocations(List budgetLocations) {
        this.budgetLocations = budgetLocations;
    }

    public int getHierachyId() {
        return this.hierachyId;
    }

    public void setHierachyId(int hierachyId) {
        this.hierachyId = hierachyId;
    }

    public int getStructureId() {
        return this.hierachyId;
    }

    public void setStructureId(int structureId) {
        this.hierachyId = structureId;
    }

    public List getBudgetCycleInstructions() {
        return this.budgetCycleInstructions;
    }

    public void setBudgetCycleInstructions(List budgetCycleInstructions) {
        this.budgetCycleInstructions = budgetCycleInstructions;
    }

}
