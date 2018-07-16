// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.xmlform.rebuild;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.xmlform.rebuild.FormRebuild;
import java.sql.Timestamp;

public interface FormRebuildEditor extends BusinessEditor {

   void setXmlformId(int var1) throws ValidationException;

   void setBudgetCycleId(int var1) throws ValidationException;

   void setStructureId0(int var1) throws ValidationException;

   void setStructureIdArray(int[] var1) throws ValidationException;

   void setStructureId1(int var1) throws ValidationException;

   void setStructureId2(int var1) throws ValidationException;

   void setStructureId3(int var1) throws ValidationException;

   void setStructureId4(int var1) throws ValidationException;

   void setStructureId5(int var1) throws ValidationException;

   void setStructureId6(int var1) throws ValidationException;

   void setStructureId7(int var1) throws ValidationException;

   void setStructureId8(int var1) throws ValidationException;

   void setStructureElementId0(int var1) throws ValidationException;

   void setStructureElementIdArray(int[] var1) throws ValidationException;

   void setStructureElementId1(int var1) throws ValidationException;

   void setStructureElementId2(int var1) throws ValidationException;

   void setStructureElementId3(int var1) throws ValidationException;

   void setStructureElementId4(int var1) throws ValidationException;

   void setStructureElementId5(int var1) throws ValidationException;

   void setStructureElementId6(int var1) throws ValidationException;

   void setStructureElementId7(int var1) throws ValidationException;

   void setStructureElementId8(int var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setLastSubmit(Timestamp var1) throws ValidationException;

   void setDataType(String var1) throws ValidationException;

   void setModelRef(ModelRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   FormRebuild getFormRebuild();

   void setBudgetCycle(EntityRef var1);

   void setXmlForm(EntityRef var1);

   void setStructures(EntityRef[] var1);
}
