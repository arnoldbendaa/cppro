// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.amm;

import com.cedar.cp.api.model.amm.AmmCubeMap;
import java.util.List;

public interface AmmModelMap {

   Integer getId();

   String getVisId();

   boolean isTargetRefreshNeeded();

   List<AmmCubeMap> getCubeMaps();

   String toString();

   void print();

   Integer getParentId();

   Integer getAmmModelId();

   List<AmmModelMap> getModelMaps();
}
