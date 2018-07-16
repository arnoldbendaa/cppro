// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.amm;

import com.cedar.cp.api.model.amm.AmmCubeMap;
import java.sql.Timestamp;

public interface AmmDataTypeMap {

   Integer getAmmDataTypeId();

   Integer getTargetId();

   String getTargetVisId();

   String getTargetDescr();

   Timestamp getTargetLastUpdate();

   Integer getSourceId();

   String getSourceVisId();

   String getSourceDescr();

   Timestamp getSourceLastUpdate();

   boolean isTargetRefreshNeeded();

   String getTimeDifferenceText();

   AmmCubeMap getParentCubeMap();

   void print();

   String toString();
}
