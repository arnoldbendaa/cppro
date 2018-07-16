// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.model.virement;


public interface VirementAccount {

   Object getStructureElementKey();

   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   double getTranLimit();

   double getTotalLimitIn();

   double getTotalLimitOut();

   boolean isInputAllowed();

   boolean isOutputAllowed();
}
