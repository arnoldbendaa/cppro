package com.cedar.cp.dto.budgetlocation;

import java.io.Serializable;
import java.util.List;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.budgetlocation.BudgetLocation;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.ImmediateChildrenELO;
import com.cedar.cp.dto.user.AllUsersELO;

public class BudgetLocationImpl implements BudgetLocation, Serializable, Cloneable {

	private Object mPrimaryKey;
	private String mVisId;
	private ModelRef mModelRef;
	private DimensionRef mDimensionRef;
	private Object mRootElementEntityRef;
	private EntityList mRootChildren;
	private EntityRef mHierarchyRef;
	private boolean mDeployForms;
	private List<UserModelElementAssignment> mModelUserElementAccess;
	private EntityList mAllUsers;

	public BudgetLocationImpl(Object paramKey) {
		mPrimaryKey = paramKey;
		mVisId = "";
	}

	public Object getPrimaryKey() {
		return mPrimaryKey;
	}

	public void setPrimaryKey(Object paramKey) {
		mPrimaryKey = paramKey;
	}

	public String getVisId() {
		return mVisId;
	}

	public void setModelRef(ModelRef modelRef) {
		mModelRef = modelRef;
	}

	public ModelRef getModelRef() {
		return mModelRef;
	}

	public void setDimensionRef(DimensionRef dimensionRef) {
		mDimensionRef = dimensionRef;
	}

	public DimensionRef getDimensionRef() {
		return mDimensionRef;
	}

	public void setRootElementEntityRef(StructureElementRef entityRef) {
		mRootElementEntityRef = entityRef;
	}

	public Object getRootElementEntityRef() {
		return mRootElementEntityRef;
	}

	public void setRootChildren(ImmediateChildrenELO rootChildren) {
		mRootChildren = rootChildren;
	}

	public EntityList getRootChildren() {
		return mRootChildren;
	}

	public void setHierarchyRef(HierarchyRef pEntityRef) {
		mHierarchyRef = pEntityRef;
	}

	public EntityRef getHierarchyRef() {
		return mHierarchyRef;
	}

	public List<UserModelElementAssignment> getModelUserElementAccess() {
		return mModelUserElementAccess;
	}

	public void setModelUserElementAccess(List<UserModelElementAssignment> l) {
		mModelUserElementAccess = l;
	}

	public boolean isDeployForms() {
		return mDeployForms;
	}

	public void setDeployForms(boolean deployForms) {
		mDeployForms = deployForms;
	}

	public void setAllUsers(AllUsersELO allUsers) {
		mAllUsers = allUsers;
	}

	public EntityList getAllUsers() {
		return mAllUsers;
	}
}