// Decompiled by:       Fernflower v0.8.6
// Date:                11.08.2012 20:22:33
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.util.xmlform.swing;


public interface ValidationMessageStore {

   void registerValidationMessage(Object var1, String var2, String var3);

   void clearValidationMessage(Object var1, String var2);

   void clearValidationMessage(Object var1);

   void clearAllValidationMessages();

   String getValidationMessage(Object var1, String var2);

   boolean hasValidationMessages();

   void adjustValidationMessagesForRowsInserted(int var1, int var2);

   void adjustValidationMessagesForRowsDelete(int var1, int var2);
}
