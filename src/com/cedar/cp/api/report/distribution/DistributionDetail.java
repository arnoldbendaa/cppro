// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.report.distribution;

import com.cedar.cp.api.report.distribution.DistributionDetailUser;
import java.util.List;

public interface DistributionDetail {

   int sInternalList = 0;
   int sExternalList = 1;
   int sRAList = 2;
   String sDelim = ";";


   int getDistributionType();

   int getMessageType();

   List getUsers();

   String getListAsString();

   String getListAsString(String var1);

   DistributionDetailUser getDistributionDetailUser(int var1);
}
