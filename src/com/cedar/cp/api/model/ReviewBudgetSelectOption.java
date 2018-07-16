// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import java.io.Serializable;

public interface ReviewBudgetSelectOption extends Serializable {

   String getLabel();

   int getValue();

   int getDepth();

   String toString();

   int getCalElemType();

   int getPosition();
}
