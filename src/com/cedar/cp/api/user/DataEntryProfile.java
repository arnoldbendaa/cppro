// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.user;

import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.api.xmlform.XmlFormRef;

public interface DataEntryProfile {

   Object getPrimaryKey();

   String getVisId();

   int getUserId();

   int getModelId();
   
   int getBudgetCycleId();

   int getAutoOpenDepth();

   String getDescription();

   int getXmlformId();

   boolean isFillDisplayArea();

   boolean isShowBoldSummaries();

   boolean isShowHorizontalLines();

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

   String getElementLabel0();

   String[] getElementLabelArray();

   String getElementLabel1();

   String getElementLabel2();

   String getElementLabel3();

   String getElementLabel4();

   String getElementLabel5();

   String getElementLabel6();

   String getElementLabel7();

   String getElementLabel8();

   String getDataType();

   UserRef getUserRef();

   XmlFormRef getXmlFormRef();
   
}
