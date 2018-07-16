package com.cedar.cp.api.importtask;

import java.util.List;

import com.cedar.cp.api.extsys.ExternalSystemRef;

public interface ImportTask {

   Object getPrimaryKey();

   String getVisId();

   String getDescription();
   
   int getExternalSystemId();
   
   ExternalSystemRef getExternalSystemRef();

   List getTaskList();
}
