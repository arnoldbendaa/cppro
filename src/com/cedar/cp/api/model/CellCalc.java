// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.util.ValueMapping;
import java.util.Map;

public interface CellCalc {

   Object getPrimaryKey();

   int getModelId();

   String getVisId();

   String getDescription();

   int getXmlformId();

   int getAccessDefinitionId();

   int getDataTypeId();

   boolean isSummaryPeriodAssociation();

   ModelRef getModelRef();

   ValueMapping getFormMapping();

   ValueMapping getAccessDef(int var1);

   ValueMapping getDataTypes(int var1);

   Map getTableData();

   EntityList getAccountDimensionElements();
}
