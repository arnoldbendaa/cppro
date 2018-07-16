// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.BudgetCycle;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.dimension.StructureElementNodeImpl;
import com.cedar.cp.dto.dimension.StructureElementValuesELO;
import com.cedar.cp.dto.model.BudgetCycleImpl;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.impl.base.BusinessProcessImpl;
import com.cedar.cp.impl.model.BudgetCycleEditorSessionImpl;
import com.cedar.cp.util.OnDemandMutableTreeNode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

public class BudgetCycleAdapter implements BudgetCycle {

    private BudgetCycleImpl mEditorData;
    private BudgetCycleEditorSessionImpl mEditorSessionImpl;

    public BudgetCycleAdapter(BudgetCycleEditorSessionImpl e, BudgetCycleImpl editorData) {
        this.mEditorData = editorData;
        this.mEditorSessionImpl = e;
    }

    protected BudgetCycleEditorSessionImpl getEditorSessionImpl() {
        return this.mEditorSessionImpl;
    }

    protected BudgetCycleImpl getEditorData() {
        return this.mEditorData;
    }

    public Object getPrimaryKey() {
        return this.mEditorData.getPrimaryKey();
    }

    public void setPrimaryKey(Object key) {
        this.mEditorData.setPrimaryKey(key);
    }

    void setPrimaryKey(BudgetCyclePK paramKey) {
        this.mEditorData.setPrimaryKey(paramKey);
    }

    public int getModelId() {
        return this.mEditorData.getModelId();
    }

    public ModelRef getModelRef() {
        if (this.mEditorData.getModelRef() != null) {
            if (this.mEditorData.getModelRef().getNarrative() != null && this.mEditorData.getModelRef().getNarrative().length() > 0) {
                return this.mEditorData.getModelRef();
            } else {
                try {
                    ModelRef e = ((BusinessProcessImpl) this.getEditorSessionImpl().getBusinessProcess()).getConnection().getListHelper().getModelEntityRef(this.mEditorData.getModelRef());
                    this.mEditorData.setModelRef(e);
                    return e;
                } catch (Exception var2) {
                    throw new RuntimeException(var2.getMessage());
                }
            }
        } else {
            return null;
        }
    }

    public void setModelRef(ModelRef ref) {
        this.mEditorData.setModelRef(ref);
    }

    public void setModelId(int p) {
        this.mEditorData.setModelId(p);
    }

    public String getVisId() {
        return this.mEditorData.getVisId();
    }

    public void setVisId(String p) {
        this.mEditorData.setVisId(p);
    }

    public String getDescription() {
        return this.mEditorData.getDescription();
    }

    public void setDescription(String p) {
        this.mEditorData.setDescription(p);
    }

    public int getType() {
        return this.mEditorData.getType();
    }

    public void setType(int p) {
        this.mEditorData.setType(p);
    }

    public int getXmlFormId() {
        return this.mEditorData.getXmlFormId();
    }

    public void setXmlFormId(int p) {
        this.mEditorData.setXmlFormId(p);
    }

    public String getXmlFormDataType() {
        return this.mEditorData.getXmlFormDataType();
    }

    public void setXmlFormDataType(String p) {
        this.mEditorData.setXmlFormDataType(p);
    }

    public int getPeriodId() {
        return this.mEditorData.getPeriodId();
    }

    public void setPeriodId(int p) {
        this.mEditorData.setPeriodId(p);
    }

    public String getPeriodFromVisId() {
        return this.mEditorData.getPeriodFromVisId();
    }

    public void setPeriodFromVisId(String periodFromVisId) {
        this.mEditorData.setPeriodFromVisId(periodFromVisId);
    }

    public EntityRef getPeriodRef() {
        return this.mEditorData.getPeriodRef();
    }

    public int getPeriodIdTo() {
        return this.mEditorData.getPeriodIdTo();
    }

    public String getPeriodToVisId() {
        return this.mEditorData.getPeriodToVisId();
    }

    public EntityRef getPeriodToRef() {
        return this.mEditorData.getPeriodToRef();
    }

    public Timestamp getPlannedEndDate() {
        return this.mEditorData.getPlannedEndDate();
    }

    public void setPlannedEndDate(Timestamp p) {
        this.mEditorData.setPlannedEndDate(p);
    }

    public Timestamp getStartDate() {
        return this.mEditorData.getStartDate();
    }

    public void setStartDate(Timestamp p) {
        this.mEditorData.setStartDate(p);
    }

    public Timestamp getEndDate() {
        return this.mEditorData.getEndDate();
    }

    public void setEndDate(Timestamp p) {
        this.mEditorData.setEndDate(p);
    }

    public int getStatus() {
        return this.mEditorData.getStatus();
    }

    public void setStatus(int p) {
        this.mEditorData.setStatus(p);
    }

    public XmlFormRef getFinanceFormRef() {
        if (this.mEditorData.getModelRef() != null && this.mEditorData.getXmlFormId() != 0) {
            try {
                XmlFormRef e = this.getEditorSessionImpl().getBusinessProcess().getConnection().getListHelper().getXmlFormEntityRef(new XmlFormPK(this.mEditorData.getXmlFormId()));
                this.mEditorData.setXmlFormRef(e);
                return e;
            } catch (Exception var2) {
                throw new RuntimeException(var2.getMessage());
            }
        } else {
            return null;
        }
    }

    public EntityRef getRootElementEntityRef() {
        if (this.mEditorData.getRootElementEntityRef() == null) {
            ModelRef modelRef = this.mEditorData.getModelRef();
            int modelId = ((ModelPK) modelRef.getPrimaryKey()).getModelId();
            StructureElementValuesELO seELO = (StructureElementValuesELO) this.getEditorSessionImpl().getBusinessProcess().getConnection().getListHelper().getStructureElementIdFromModel(modelId);
            seELO.next();
            StructureElementRef ref = seELO.getStructureElementEntityRef();
            this.mEditorData.setRootElementEntityRef(ref);
        }

        return this.mEditorData.getRootElementEntityRef();
    }

    public void setRootElementEntityRef(EntityRef pRootElementEntityRef) {
        this.mEditorData.setRootElementEntityRef(pRootElementEntityRef);
    }

    public TreeModel getCalanderModel() {
        if (this.mEditorData.getCalanderModel() == null) {
            CPConnection conn = this.mEditorSessionImpl.getConnection();
            EntityList list = conn.getModelsProcess().getTreeInfoForDimTypeInModel(((ModelPK) this.getModelRef().getPrimaryKey()).getModelId(), 3);
            int size = list.getNumRows();
            DefaultMutableTreeNode root = null;

            for (int model = 0; model < size; ++model) {
                root = new DefaultMutableTreeNode(list.getValueAt(model, "VisId"));
                EntityList hierList = (EntityList) list.getValueAt(model, "Hierarchy");
                int hierSize = hierList.getNumRows();

                for (int j = 0; j < hierSize; ++j) {
                    DefaultMutableTreeNode hier = new DefaultMutableTreeNode(hierList.getValueAt(j, "HierarchyVisId"));
                    EntityList elementList = (EntityList) hierList.getValueAt(j, "StructureElement");
                    int elementsize = elementList.getNumRows();

                    for (int k = 0; k < elementsize; ++k) {
                        StructureElementNodeImpl node = new StructureElementNodeImpl(this.getEditorSessionImpl().getConnection(), elementList, "BudgetCycleAdapter");
                        OnDemandMutableTreeNode elem = new OnDemandMutableTreeNode(node, "com.cedar.cp.impl.dimension.StructureElementProxyNode");
                        hier.add(elem);
                    }

                    root.add(hier);
                }
            }

            DefaultTreeModel var15 = new DefaultTreeModel(root);
            this.mEditorData.setCalanderModel(var15);
        }

        return this.mEditorData.getCalanderModel();
    }

    public List getLevelDates() {
        if (this.mEditorData.getLevelDates() == null || this.mEditorData.isDateChanged()) {
            EntityList list = this.mEditorSessionImpl.getConnection().getListHelper().getMaxDepthForBudgetHierarchy(((ModelPK) this.getModelRef().getPrimaryKey()).getModelId());
            Integer maxDepth = Integer.valueOf(0);
            if (list.getNumRows() > 0) {
                maxDepth = (Integer) list.getValueAt(0, "col2");
            }

            maxDepth = Integer.valueOf(maxDepth.intValue() + 1);
            long today = System.currentTimeMillis();
            long planned = this.getPlannedEndDate().getTime();
            long dif = planned - today;
            long millsPerDay = 86400000L;
            Long numberOfDays = Long.valueOf(dif / millsPerDay);
            Long intervalDays = Long.valueOf(numberOfDays.longValue() / (long) maxDepth.intValue());
            Long intervalDaysMillis = Long.valueOf(intervalDays.longValue() * millsPerDay);
            ArrayList splitDays = new ArrayList(maxDepth.intValue());

            for (int i = 0; i < maxDepth.intValue(); ++i) {
                if (i == 0) {
                    splitDays.add(new Date(planned));
                } else {
                    long subTime = intervalDaysMillis.longValue() * (long) i;
                    splitDays.add(new Date(planned - subTime));
                }
            }

            this.mEditorData.setLevelDates(splitDays);
            this.mEditorData.setDateChanged(false);
        }

        return this.mEditorData.getLevelDates();
    }

    public void setDateChanged(boolean changed) {
        this.mEditorData.setDateChanged(changed);
    }

    public List getXmlForms() {
        return this.mEditorData.getXmlForms();
    }

    public String[] getCalendarDetails() {
        try {
            String[] s = this.getEditorSessionImpl().getBusinessProcess().getConnection().getBudgetCyclesProcess().getCalendarDetailsLabels(new int[] { this.mEditorData.getPeriodId(), this.mEditorData.getPeriodIdTo() });
            return s;
        } catch (Exception var2) {
            throw new RuntimeException(var2.getMessage());
        }
    }

}
