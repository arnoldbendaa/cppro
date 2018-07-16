// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.util.awt.QListModel;

public interface VirementCategory {

   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   long getTranLimit();

   long getTotalLimitIn();

   long getTotalLimitOut();

   ModelRef getModelRef();

   QListModel getResponsibilityAreas();

   QListModel getAccounts();

   double getTranLimitAsDouble();

   double getTotalLimitInAsDouble();

   double getTotalLimitOutAsDouble();
}
