// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.recharge;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.recharge.Recharge;
import java.math.BigDecimal;

public interface RechargeEditor extends BusinessEditor {

   void setManualRatios(boolean var1) throws ValidationException;

   void setAllocationDataTypeId(int var1) throws ValidationException;

   void setDiffAccount(boolean var1) throws ValidationException;

   void setAccountStructureId(int var1) throws ValidationException;

   void setAccountStructureElementId(int var1) throws ValidationException;

   void setRatioType(int var1) throws ValidationException;

   void setVisId(String var1) throws ValidationException;

   void setDescription(String var1) throws ValidationException;

   void setReason(String var1) throws ValidationException;

   void setReference(String var1) throws ValidationException;

   void setAllocationPercentage(BigDecimal var1) throws ValidationException;

   void setModelRef(ModelRef var1) throws ValidationException;

   EntityList getOwnershipRefs();

   Recharge getRecharge();

   void setAccountStructureElementRef(StructureElementRef var1) throws ValidationException;

   void addSourceCell(EntityRef[] var1) throws ValidationException;

   void removeSourceCells(int[] var1) throws ValidationException;

   void addTargetCell(EntityRef[] var1, BigDecimal var2) throws ValidationException;

   void removeTargetCells(int[] var1) throws ValidationException;

   void addOffsetCell(EntityRef[] var1) throws ValidationException;

   void removeOffsetCells(int[] var1) throws ValidationException;

   boolean ensureManualRatios();
}
