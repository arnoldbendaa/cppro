// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.recharge;

import com.cedar.cp.api.base.EntityList;
import java.util.List;

public interface RechargeGroup {

   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   List getSelectedRecharge();

   EntityList getAvailableRecharge();

   int getModelId();
}
