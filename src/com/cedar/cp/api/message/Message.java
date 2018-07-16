// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.message;

import java.util.List;

public interface Message {

   int SYSTEM_MESSAGE = 0;
   int EMAIL = 1;
   int BOTH = 2;
   String sCPSystemIdentifier = "System";


   Object getPrimaryKey();

   String getSubject();

   String getContent();

   int getMessageType();

   List getMessageUsers();

   List getFromUsers();

   List getToUsers();

   List getAttachments();

   String getFromEmailAddress();

   String getToEmailAddress();
}
