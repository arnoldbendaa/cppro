// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:21
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.xmlform;

import com.cedar.cp.api.base.EntityList;
import java.util.List;

public interface XmlForm {

   int TYPE_FIXED_ROW = 1;
   int TYPE_DYNAMIC_ROW = 2;
   int TYPE_FINANCE = 3;
   int TYPE_FLAT = 4;
   int TYPE_XLS = 6;
   int TYPE_XLSX = 7;
   
   Object getPrimaryKey();

   String getVisId();

   String getDescription();

   int getType();

   boolean isDesignMode();

   int getFinanceCubeId();

   String getDefinition();
   
   byte[] getExcelFile();
   
   String getJsonForm();
		   
   boolean isSecurityAccess();

   List getUserList();
   
   List getNamesList();

   EntityList getAvailableUsers();
   
   int incrementVersionNumber();
}
