// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.message;

import com.cedar.cp.api.message.MessageRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.message.MessagePK;
import java.io.Serializable;

public class MessageRefImpl extends EntityRefImpl implements MessageRef, Serializable {

   public MessageRefImpl(MessagePK key, String narrative) {
      super(key, narrative);
   }

   public MessagePK getMessagePK() {
      return (MessagePK)this.mKey;
   }
}
