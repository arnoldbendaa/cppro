// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.extsys;

import java.util.List;
import java.util.Map;

public interface ExternalSystem {

   int TYPE_E5 = 5;
   int TYPE_EFINANCIALS = 3;
   int TYPE_OPEN_ACCOUNTS = 10;
   int TYPE_GENERIC = 20;


   Object getPrimaryKey();

   int getSystemType();

   String getVisId();

   String getDescription();

   String getLocation();

   String getConnectorClass();

   String getImportSource();

   String getExportTarget();

   boolean isEnabled();

   Map<String, String> getProperties();

   List getAvailableColumnList(int var1);

   List getSelectedColumnList(int var1);
}
