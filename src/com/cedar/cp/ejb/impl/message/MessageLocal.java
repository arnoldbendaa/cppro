// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.message;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.message.MessageCK;
import com.cedar.cp.dto.message.MessagePK;
import com.cedar.cp.ejb.impl.message.MessageEVO;
import javax.ejb.EJBLocalObject;

public interface MessageLocal extends EJBLocalObject {

   MessageEVO getDetails(String var1) throws ValidationException;

   MessageEVO getDetails(MessageCK var1, String var2) throws ValidationException;

   MessagePK generateKeys();

   void setDetails(MessageEVO var1);

   MessageEVO setAndGetDetails(MessageEVO var1, String var2);
}
