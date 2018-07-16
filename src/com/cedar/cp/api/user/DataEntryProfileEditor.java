// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.user;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.user.DataEntryProfile;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.api.xmlform.XmlFormRef;

public interface DataEntryProfileEditor extends BusinessEditor {

   void setUserId(int var1) throws ValidationException;

   void setModelId(int var1) throws ValidationException;
   
   void setBudgetCycleId(int var1) throws ValidationException;

   void setAutoOpenDepth(int var1) throws ValidationException;

   void setXmlformId(int var1) throws ValidationException;
   
   void setFillDisplayArea(boolean var1) throws ValidationException;

   void setShowBoldSummaries(boolean var1) throws ValidationException;

   void setShowHorizontalLines(boolean var1) throws ValidationException;

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

   void setElementLabel0(String var1) throws ValidationException;

   void setElementLabelArray(String[] var1) throws ValidationException;

   void setElementLabel1(String var1) throws ValidationException;

   void setElementLabel2(String var1) throws ValidationException;

   void setElementLabel3(String var1) throws ValidationException;

   void setElementLabel4(String var1) throws ValidationException;

   void setElementLabel5(String var1) throws ValidationException;

   void setElementLabel6(String var1) throws ValidationException;

   void setElementLabel7(String var1) throws ValidationException;

   void setElementLabel8(String var1) throws ValidationException;

   void setDataType(String var1) throws ValidationException;

   void setUserRef(UserRef var1) throws ValidationException;

   void setXmlFormRef(XmlFormRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   DataEntryProfile getDataEntryProfile();
}
