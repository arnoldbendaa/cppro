// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.message;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.message.MessageEditor;
import com.cedar.cp.api.message.MessageEditorSession;

public interface MessagesProcess extends BusinessProcess {

   EntityList getAllMessages();

   EntityList getInBoxForUser(String var1);

   EntityList getUnreadInBoxForUser(String var1);

   EntityList getSentItemsForUser(String var1);

   EntityList getMessageForId(long var1, String var3);

   EntityList getMessageForIdSentItem(long var1, String var3);

   EntityList getMessageCount(long var1);

   MessageEditorSession getMessageEditorSession(Object var1) throws ValidationException;

   MessageEditorSession getMessageEditorSession(long var1) throws ValidationException;

   void markAsRead(long var1, long var3) throws ValidationException;

   void deleteObject(long var1, long var3) throws ValidationException;

   Object createNewMessage(MessageEditor var1) throws ValidationException;
   
   void emptyFolder(int paramInt, String paramString) throws ValidationException;
}
