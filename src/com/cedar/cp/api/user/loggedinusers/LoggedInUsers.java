package com.cedar.cp.api.user.loggedinusers;

import java.util.List;

import com.cedar.cp.api.extsys.ExternalSystemRef;

public interface LoggedInUsers {

   Object getPrimaryKey();

   String getVisId();

   String getDescription();
   
   int getExternalSystemId();
   
   ExternalSystemRef getExternalSystemRef();

   List getTaskList();
}
