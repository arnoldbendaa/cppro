// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dataEntry;

import java.io.Serializable;
import java.util.List;

public interface MassUpdateParameters extends Serializable {

   int getModelId();

   String getModelVisId();

   int getFinanceCubeId();

   String getFinanceCubeVisId();

   String getReason();

   String getReference();

   String getChangeType();

   List getChangeCells();

   String getCurrentValue();

   String getChangePercent();

   String getChangeBy();

   String getChangeTo();

   int getCalId();

   int getDataTypeId();

   String getDataTypeVisId();

   int getRoundUnits();

   boolean isHoldNegative();

   boolean isHoldPositive();

   List getHoldCells();

   boolean isReport();

   boolean isCellPosting();

   List getDimensionHeader();

   String getLastAction();
}
