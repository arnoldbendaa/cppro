// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.recharge;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.recharge.RechargeCellDataVO;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.tree.TreeModel;

public interface Recharge {

   int PERIOD_MOVEMENT = 0;
   int PERIOD_BALANCE = 1;


   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   String getReason();

   String getReference();

   BigDecimal getAllocationPercentage();

   boolean isManualRatios();

   int getAllocationDataTypeId();

   boolean isDiffAccount();

   int getAccountStructureId();

   int getAccountStructureElementId();

   int getRatioType();

   ModelRef getModelRef();

   TreeModel getAccountTree();

   Object getAccountStructureElementRef();

   EntityList getTableModelHeadings();

   List<RechargeCellDataVO> getSelectedSourceCells();

   List<RechargeCellDataVO> getSelectedTargetCells();

   List<RechargeCellDataVO> getSelectedOffsetCells();

   TreeModel[] getCellPickerModel(boolean var1);

   TreeModel[] getCellPickerModel(boolean var1, boolean var2);
}
