// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.amm;

import com.cedar.cp.api.model.amm.AmmDataTypeMap;
import java.util.List;

public interface AmmCubeMap {

   Integer getTargetId();

   String getTargetVisId();

   Integer getSourceId();

   String getSourceVisId();

   boolean isTargetRefreshNeeded();

   void print();

   List<AmmDataTypeMap> getDataTypeMaps();

   Integer getAmmModelId();
}
