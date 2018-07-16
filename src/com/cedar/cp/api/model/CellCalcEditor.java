// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.CellCalc;
import com.cedar.cp.api.model.ModelRef;

public interface CellCalcEditor extends BusinessEditor {

   void setModelId(int var1) throws ValidationException;

   void setXmlformId(int var1) throws ValidationException;

   void setAccessDefinitionId(int var1) throws ValidationException;

   void setDataTypeId(int var1) throws ValidationException;

   void setSummaryPeriodAssociation(boolean var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setModelRef(ModelRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   CellCalc getCellCalc();

   void addAccountElementAssociation(EntityRef var1, String var2);

   void removeAccountElementAssociation(EntityRef var1);
}
