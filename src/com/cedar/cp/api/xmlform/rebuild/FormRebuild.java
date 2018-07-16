// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.xmlform.rebuild;

import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import java.sql.Timestamp;
import javax.swing.tree.TreeModel;

public interface FormRebuild {

   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   Timestamp getLastSubmit();

   int getXmlformId();

   int getBudgetCycleId();

   int getStructureId0();

   int[] getStructureIdArray();

   int getStructureId1();

   int getStructureId2();

   int getStructureId3();

   int getStructureId4();

   int getStructureId5();

   int getStructureId6();

   int getStructureId7();

   int getStructureId8();

   int getStructureElementId0();

   int[] getStructureElementIdArray();

   int getStructureElementId1();

   int getStructureElementId2();

   int getStructureElementId3();

   int getStructureElementId4();

   int getStructureElementId5();

   int getStructureElementId6();

   int getStructureElementId7();

   int getStructureElementId8();

   String getDataType();

   ModelRef getModelRef();

   int getModelId();

   BudgetCycleRef getBudgetCycleRef();

   XmlFormRef getXmlFormRef();

   String getSelectionAsText();

   TreeModel[] getCellPickerModel();
}
