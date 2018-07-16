// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.BudgetCycle;
import com.cedar.cp.api.model.BudgetCycleEditor;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.BudgetCycleEditorSessionSSO;
import com.cedar.cp.dto.model.BudgetCycleImpl;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.model.BudgetCycleAdapter;
import com.cedar.cp.impl.model.BudgetCycleEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

import java.sql.Timestamp;
import java.util.List;

public class BudgetCycleEditorImpl extends BusinessEditorImpl implements BudgetCycleEditor {

    // private BudgetCycleEditorSessionSSO mServerSessionData;
    private BudgetCycleImpl mEditorData;
    private BudgetCycleAdapter mEditorDataAdapter;

    public BudgetCycleEditorImpl(BudgetCycleEditorSessionImpl session, BudgetCycleEditorSessionSSO serverSessionData, BudgetCycleImpl editorData) {
        super(session);
        // this.mServerSessionData = serverSessionData;
        this.mEditorData = editorData;
    }

    public void updateEditorData(BudgetCycleEditorSessionSSO serverSessionData, BudgetCycleImpl editorData) {
        // this.mServerSessionData = serverSessionData;
        this.mEditorData = editorData;
    }

    public void setModelId(int newModelId) throws ValidationException {
        this.validateModelId(newModelId);
        if (this.mEditorData.getModelId() != newModelId) {
            this.setContentModified();
            this.mEditorData.setModelId(newModelId);
        }
    }

    public void setType(int newType) throws ValidationException {
        this.validateType(newType);
        if (this.mEditorData.getType() != newType) {
            this.setContentModified();
            this.mEditorData.setType(newType);
        }
    }

    public void setXmlFormId(int newXmlFormId) throws ValidationException {
        this.validateXmlFormId(newXmlFormId);
        if (this.mEditorData.getXmlFormId() != newXmlFormId) {
            this.setContentModified();
            this.mEditorData.setXmlFormId(newXmlFormId);
        }
    }

    public void setPeriodId(int newPeriodId) throws ValidationException {
        this.validatePeriodId(newPeriodId);
        if (this.mEditorData.getPeriodId() != newPeriodId) {
            this.setContentModified();
            this.mEditorData.setPeriodId(newPeriodId);
        }
    }

    public void setPeriodFromVisId(String newPeriodFromVisId) throws ValidationException {
        if (!this.mEditorData.getPeriodFromVisId().equals(newPeriodFromVisId)) {
            this.setContentModified();
            this.mEditorData.setPeriodFromVisId(newPeriodFromVisId);
        }
    }

    public void setPeriodToVisId(String newPeriodToVisId) throws ValidationException {
        if (!this.mEditorData.getPeriodToVisId().equals(newPeriodToVisId)) {
            this.setContentModified();
            this.mEditorData.setPeriodToVisId(newPeriodToVisId);
        }
    }

    public void setStatus(int newStatus) throws ValidationException {
        this.validateStatus(newStatus);
        if (this.mEditorData.getStatus() != newStatus) {
            this.setContentModified();
            this.mEditorData.setStatus(newStatus);
        }
    }

    public void setVisId(String newVisId) throws ValidationException {
        if (newVisId != null) {
            newVisId = StringUtils.rtrim(newVisId);
        }

        this.validateVisId(newVisId);
        if (this.mEditorData.getVisId() == null || !this.mEditorData.getVisId().equals(newVisId)) {
            this.setContentModified();
            this.mEditorData.setVisId(newVisId);
        }
    }

    public void setDescription(String newDescription) throws ValidationException {
        if (newDescription != null) {
            newDescription = StringUtils.rtrim(newDescription);
        }

        this.validateDescription(newDescription);
        if (this.mEditorData.getDescription() == null || !this.mEditorData.getDescription().equals(newDescription)) {
            this.setContentModified();
            this.mEditorData.setDescription(newDescription);
        }
    }

    public void setXmlFormDataType(String newXmlFormDataType) throws ValidationException {
        if (newXmlFormDataType != null) {
            newXmlFormDataType = StringUtils.rtrim(newXmlFormDataType);
        }

        this.validateXmlFormDataType(newXmlFormDataType);
        if (this.mEditorData.getXmlFormDataType() == null || !this.mEditorData.getXmlFormDataType().equals(newXmlFormDataType)) {
            this.setContentModified();
            this.mEditorData.setXmlFormDataType(newXmlFormDataType);
        }
    }

    public void setPlannedEndDate(Timestamp newPlannedEndDate) throws ValidationException {
        this.validatePlannedEndDate(newPlannedEndDate);
        if (this.mEditorData.getPlannedEndDate() == null || !this.mEditorData.getPlannedEndDate().equals(newPlannedEndDate)) {
            this.setContentModified();
            this.mEditorData.setPlannedEndDate(newPlannedEndDate);
        }
    }

    public void setStartDate(Timestamp newStartDate) throws ValidationException {
        this.validateStartDate(newStartDate);
        if (this.mEditorData.getStartDate() == null || !this.mEditorData.getStartDate().equals(newStartDate)) {
            this.setContentModified();
            this.mEditorData.setStartDate(newStartDate);
        }
    }

    public void setEndDate(Timestamp newEndDate) throws ValidationException {
        this.validateEndDate(newEndDate);
        if (this.mEditorData.getEndDate() == null || !this.mEditorData.getEndDate().equals(newEndDate)) {
            this.setContentModified();
            this.mEditorData.setEndDate(newEndDate);
        }
    }

    public void validateModelId(int newModelId) throws ValidationException {
    }

    public void validateVisId(String newVisId) throws ValidationException {
        if (newVisId != null && newVisId.length() > 20) {
            throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a BudgetCycle");
        }
    }

    public void validateDescription(String newDescription) throws ValidationException {
        if (newDescription != null && newDescription.length() > 128) {
            throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a BudgetCycle");
        }
    }

    public void validateType(int newType) throws ValidationException {
    }

    public void validateXmlFormId(int newXmlFormId) throws ValidationException {
    }

    public void validateXmlFormDataType(String newXmlFormDataType) throws ValidationException {
    }

    public void validatePeriodId(int newPeriodId) throws ValidationException {
    }

    public void validatePlannedEndDate(Timestamp newPlannedEndDate) throws ValidationException {
    }

    public void validateStartDate(Timestamp newStartDate) throws ValidationException {
    }

    public void validateEndDate(Timestamp newEndDate) throws ValidationException {
    }

    public void validateStatus(int newStatus) throws ValidationException {
        if (newStatus < 0 || newStatus > 3) {
            throw new ValidationException("State can only be 0:initiated 1:in progress 2:complete");
        }
    }

    public void setModelRef(ModelRef ref) throws ValidationException {
        ModelRef actualRef = ref;
        if (ref != null) {
            try {
                actualRef = this.getConnection().getListHelper().getModelEntityRef(ref);
            } catch (Exception var4) {
                throw new ValidationException(var4.getMessage());
            }
        }

        if (this.mEditorData.getModelRef() == null) {
            if (actualRef == null) {
                return;
            }
        } else if (actualRef != null && this.mEditorData.getModelRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
            return;
        }

        this.mEditorData.setModelRef(actualRef);
        this.setContentModified();
    }

    public EntityList getOwnershipRefs() {
        return ((BudgetCycleEditorSessionImpl) this.getBusinessSession()).getOwnershipRefs();
    }

    public BudgetCycle getBudgetCycle() {
        if (this.mEditorDataAdapter == null) {
            this.mEditorDataAdapter = new BudgetCycleAdapter((BudgetCycleEditorSessionImpl) this.getBusinessSession(), this.mEditorData);
        }

        return this.mEditorDataAdapter;
    }

    public void saveModifications() throws ValidationException {
        this.saveValidation();
    }

    private void saveValidation() throws ValidationException {
        this.validateXmlFormId(this.mEditorData.getXmlFormId());
        this.validateXmlFormDataType(this.mEditorData.getXmlFormDataType());
    }

    public void setXmlFormId(EntityRef ref) throws ValidationException {
        XmlFormPK pk = (XmlFormPK) ref.getPrimaryKey();
        this.setXmlFormId(pk.getXmlFormId());
    }

    public void setPeriodRef(EntityRef ref) throws ValidationException {
        this.mEditorData.setPeriodRef(ref);
        if (ref instanceof StructureElementRef) {
            StructureElementPK pk = (StructureElementPK) ((StructureElementRef) ref).getPrimaryKey();
            this.mEditorData.setPeriodId(pk.getStructureElementId());
        }

        this.setContentModified();
    }

    public void setPeriodToId(EntityRef ref) throws ValidationException {
        this.mEditorData.setPeriodToRef(ref);
        if (ref instanceof StructureElementRef) {
            StructureElementPK pk = (StructureElementPK) ((StructureElementRef) ref).getPrimaryKey();
            this.mEditorData.setPeriodIdTo(pk.getStructureElementId());
        }
        this.setContentModified();
    }

    public void addXmlForm(Object[] element) {
        this.mEditorData.getXmlForms().add(element);
        this.setContentModified();
    }

    public void removeXmlForm(Object[] element) {
        this.mEditorData.getXmlForms().remove(element);
        this.setContentModified();
    }

    public void removeXmlForm(int index) {
        this.mEditorData.getXmlForms().remove(index);
        this.setContentModified();
    }

    public void setXmlForm(int index, Object[] element) {
        List<Object[]> xmlForms = this.mEditorData.getXmlForms();
        xmlForms.set(index, element);
        this.mEditorData.setXmlForms(xmlForms);
        this.setContentModified();
    }

    public void setDateChanged(boolean changed) {
        this.mEditorData.setDateChanged(changed);
        this.setContentModified();
    }

    public String[] getCalendarDetails() {
        if (this.mEditorDataAdapter != null) {
            return this.mEditorDataAdapter.getCalendarDetails();
        }
        return null;
    }
}
