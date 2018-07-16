package com.cedar.cp.api.budgetlocation;

import java.io.Serializable;
import com.cedar.cp.api.base.EntityRef;

public interface UserModelElementAssignment extends Serializable {

	String toString();

	String getVisId();

	String getDescription();

	Boolean getReadOnly();

	void setReadOnly(Boolean paramBoolean);

	Object getStructureElementPK();

	EntityRef getModel();

	EntityRef getStructureElementRef();

	EntityRef getUser();
}