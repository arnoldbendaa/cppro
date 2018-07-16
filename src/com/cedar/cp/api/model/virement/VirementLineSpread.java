// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.dimension.StructureElementRef;
import java.io.Serializable;

public interface VirementLineSpread extends Serializable {

   int SCALE_FACTOR = 10000;


   String getKeyAsText();

   Object getKey();

   StructureElementRef getStructureElementRef();

   int getIndex();

   int getWeighting();

   boolean isHeld();

   double getTransferValue();

   long getTransferValueAsLong();
}
