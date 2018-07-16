// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.message;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.CPFileWrapper;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.message.Message;

public interface MessageEditor extends BusinessEditor {

   void setMessageType(int var1) throws ValidationException;

   void setSubject(String var1) throws ValidationException;

   void setContent(String var1) throws ValidationException;

   Message getMessage();

   void addMessageUser(Object var1);

   void addFromUser(String var1);

   void addToUser(String var1);

   void setRead(long var1, boolean var3);

   void setDeleteId(long var1);

   void setFromEmailAddress(String var1);

   void setFromEmailAddress(Object var1);

   void setToEmailAddress(String var1);

   void setToEmailAddress(Object var1);

   void addToEmailAddress(String var1);

   void addToEmailAddress(Object var1);

   void addAttachment(CPFileWrapper var1);

   void addAttachment(String var1, byte[] var2);

   Object getMessageImpl();
}
