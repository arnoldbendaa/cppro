package com.cedar.cp.api.recalculate;

import java.util.List;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.recalculate.RecalculateBatchTask;
import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.util.OnDemandMutableTreeNode;

public interface RecalculateBatchTaskEditor extends BusinessEditor {

	List<RecalculateBatchTaskAssignment> getRecalculateBatchTaskAssignments();
	
	List<Integer> getRecalculateBatchTaskRespArea();

	RecalculateBatchTaskAssignment getRecalculateBatchTaskAssignmentItem(int var1);

	void setRecalculateBatchTaskAssignmentItems(List var1);

	RecalculateBatchTask getRecalculateBatchTask();
	
	List<DataEntryProfileRef> getRecalculateBatchTaskForms();
	
	List<DataEntryProfileRef> getNewRecalculateBatchTaskForms();

	OnDemandMutableTreeNode getNodes(int hierId);

	boolean isModelSelected(EntityRef var1);

	boolean isResponsibilityAreaSelected(EntityRef var1, EntityRef var2);

	boolean isResponsibilityAreaAndChildrenSelected(EntityRef var1, EntityRef var2);

	void addResponsibilityArea(EntityRef var1, EntityRef var2, List var3);

	void removeModel(EntityRef var1);

	void removeResponsibilityArea(EntityRef var1, EntityRef var2);

	boolean isChildSelected(EntityRef var1);

	boolean isChildSelected(EntityRef var1, EntityRef var2);

	void removeRecalculateBatchTaskAssignmentItem(Object var1);

	List getRespAreaAccess();
	
	void addXmlForm(DataEntryProfileRef deRef);
	
	void removeXmlForm(DataEntryProfileRef deRef);
	
	void clearData();
	
	boolean containsRecalculateBatchTaskFormItem(DataEntryProfileRef depRef);

	EntityList getImmediateChildren(Object primaryKey);
	
	void setBudgetCycleId(int budgetCycleId);
	
	int getBudgetCycleId();
	
	void setIdentifier(String identifier);
	
	void setDescription(String description);
	
	String getIdentifier();
	
	String getDescription();
	
}
