package com.cedar.cp.api.importtask;

import com.cedar.cp.api.base.EntityRef;

public interface ImportTaskLinkRef extends EntityRef {

   int UPDATE = 0;
   int QUERY = 1;
   int REPORT_PACKAGE = 2;
   int CLASS = 3;
   int UPDATE_PACKAGE = 4;

}