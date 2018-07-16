// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:28:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.utc.struts.approver;

import com.cedar.cp.api.model.ChangeBudgetStateResult;
import com.cedar.cp.utc.struts.approver.CrumbDTO;
import com.cedar.cp.utc.struts.homepage.HomePageForm;
import java.util.List;

public class BudgetCycleStatusForm extends HomePageForm {

    private int mBudgetCycleId;
    private int mStructureElementId;
    private int mStructureId;
    private boolean controller;
    private boolean holder;
    private boolean approver;
    private int stateFilter;
    private int oldUserCount;
    private int oldDepth;
    protected String mAddId;
    protected String mOldId;
    protected String mStructureElementList = "";
    protected String mOldUserCountList = "";
    protected String mOldDepthList = "";
    protected String mVisIdList = "";
    protected String mDescriptionList = "";
    protected List mCrumbs;
    protected int mCrumbSize;
    private String mFull = "true";
    protected ChangeBudgetStateResult mChangeStateResult;
    private Integer mFromState = Integer.valueOf(0);
    private Integer mToState = Integer.valueOf(0);

    public void setBudgetCycleId(int id) {
        this.mBudgetCycleId = id;
    }

    public int getBudgetCycleId() {
        return this.mBudgetCycleId;
    }

    public void setStructureElementId(int id) {
        this.mStructureElementId = id;
    }

    public int getStructureElementId() {
        return this.mStructureElementId;
    }

    public boolean isController() {
        return this.controller;
    }

    public void setController(boolean controller) {
        this.controller = controller;
    }

    public boolean isHolder() {
        return this.holder;
    }

    public void setHolder(boolean holder) {
        this.holder = holder;
    }

    public boolean isApprover() {
        return this.approver;
    }

    public void setApprover(boolean approver) {
        this.approver = approver;
    }

    public int getOldUserCount() {
        return this.oldUserCount;
    }

    public void setOldUserCount(int oldUserCount) {
        this.oldUserCount = oldUserCount;
    }

    public int getStateFilter() {
        return this.stateFilter;
    }

    public void setStateFilter(int stateFilter) {
        this.stateFilter = stateFilter;
    }

    public int getOldDepth() {
        return this.oldDepth;
    }

    public void setOldDepth(int oldDepth) {
        this.oldDepth = oldDepth;
    }

    public String getAddId() {
        return this.mAddId;
    }

    public void setAddId(String addId) {
        this.mAddId = addId;
    }

    public String getOldId() {
        return this.mOldId;
    }

    public void setOldId(String oldId) {
        this.mOldId = oldId;
    }

    public String getStructureElementList() {
        return this.mStructureElementList;
    }

    public void setStructureElementList(String structureElementList) {
        this.mStructureElementList = structureElementList;
    }

    public String getOldUserCountList() {
        return this.mOldUserCountList;
    }

    public void setOldUserCountList(String oldUserCountList) {
        this.mOldUserCountList = oldUserCountList;
    }

    public String getOldDepthList() {
        return this.mOldDepthList;
    }

    public void setOldDepthList(String oldDepthList) {
        this.mOldDepthList = oldDepthList;
    }

    public String getVisIdList() {
        return this.mVisIdList;
    }

    public void setVisIdList(String visIdList) {
        this.mVisIdList = visIdList;
    }

    public List getCrumbs() {
        return this.mCrumbs;
    }

    public void setCrumbs(List crumbs) {
        if (crumbs != null) {
            this.setCrumbSize(crumbs.size());
        }

        this.mCrumbs = crumbs;
    }

    public String getHTMLCrumbs() {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < this.getCrumbSize(); ++i) {
            CrumbDTO dto = (CrumbDTO) this.getCrumbs().get(i);
            if(i!=0){
                sb.append(" < ");
            }
            sb.append("<a style=\"font-weight:bold; color:#636396\" href=\"#\" onclick=\"budgetStatus(").append(dto.getStructureElementId()).append("");
            sb.append("").append(")\" title=\"").append(dto.getDescription()).append("\">").append(dto.getVisId()).append(" ").append("</a>");
        }

        return sb.toString();
    }

    public int getCrumbSize() {
        return this.mCrumbSize;
    }

    public void setCrumbSize(int crumbSize) {
        this.mCrumbSize = crumbSize;
    }

    public String getDescriptionList() {
        return this.mDescriptionList;
    }

    public void setDescriptionList(String descriptionList) {
        this.mDescriptionList = descriptionList;
    }

    public int getStructureId() {
        return this.mStructureId;
    }

    public void setStructureId(int structureId) {
        this.mStructureId = structureId;
    }

    public ChangeBudgetStateResult getChangeStateResult() {
        return this.mChangeStateResult;
    }

    public void setChangeStateResult(ChangeBudgetStateResult changeStateResult) {
        this.mChangeStateResult = changeStateResult;
    }

    public Integer getFromState() {
        return this.mFromState;
    }

    public void setFromState(Integer fromState) {
        this.mFromState = fromState;
    }

    public Integer getToState() {
        return this.mToState;
    }

    public void setToState(Integer toState) {
        this.mToState = toState;
    }

    public String getFull() {
        return mFull;
    }

    public void setFull(String full) {
        mFull = full;
    }
}
