// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.message;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.message.MessageEditorSessionCSO;
import com.cedar.cp.dto.message.MessageImpl;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface MessageHelperLocal extends EJBLocalObject {

   Object createNewMessage(MessageImpl var1) throws EJBException;

   Object autonomousInsert(MessageEditorSessionCSO var1) throws ValidationException, EJBException;
   
   void emptyFolder(int paramInt, String paramString) throws EJBException;
}
