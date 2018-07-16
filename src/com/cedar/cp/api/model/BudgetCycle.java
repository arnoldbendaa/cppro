// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import java.sql.Timestamp;
import java.util.List;
import javax.swing.tree.TreeModel;

public interface BudgetCycle {

    int TYPE_YEARLY_PREPARATION = 1;
    int TYPE_MONTHLY_OUTTURN = 2;
    int STATUS_INITIATED = 0;
    int STATUS_IN_PROGRESS = 1;
    int STATUS_COMPLETE = 2;
    int STATUS_ARCHIVED = 3;

    Object getPrimaryKey();

    int getModelId();

    ModelRef getModelRef();

    String getVisId();

    String getDescription();

    int getType();

    int getXmlFormId();

    String getXmlFormDataType();

    int getPeriodId();

    String getPeriodFromVisId();

    EntityRef getPeriodRef();

    int getPeriodIdTo();

    String getPeriodToVisId();

    EntityRef getPeriodToRef();

    Timestamp getPlannedEndDate();

    Timestamp getStartDate();

    Timestamp getEndDate();

    int getStatus();

    XmlFormRef getFinanceFormRef();

    EntityRef getRootElementEntityRef();

    void setRootElementEntityRef(EntityRef var1);

    TreeModel getCalanderModel();

    List getLevelDates();

    void setDateChanged(boolean var1);

    List getXmlForms();
}
