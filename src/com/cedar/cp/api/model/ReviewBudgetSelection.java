// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model;

import java.io.Serializable;
import java.util.List;

public interface ReviewBudgetSelection extends Serializable {

   String getLabel();

   List getSelectOptions();

   int getCurrentOption();

   int getCurrentOption(int var1);

   void setCurrentOption(int var1);
}
