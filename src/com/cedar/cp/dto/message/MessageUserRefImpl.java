// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.message;

import com.cedar.cp.api.message.MessageUserRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.message.MessageUserCK;
import com.cedar.cp.dto.message.MessageUserPK;
import java.io.Serializable;

public class MessageUserRefImpl extends EntityRefImpl implements MessageUserRef, Serializable {

   public MessageUserRefImpl(MessageUserCK key, String narrative) {
      super(key, narrative);
   }

   public MessageUserRefImpl(MessageUserPK key, String narrative) {
      super(key, narrative);
   }

   public MessageUserPK getMessageUserPK() {
      return this.mKey instanceof MessageUserCK?((MessageUserCK)this.mKey).getMessageUserPK():(MessageUserPK)this.mKey;
   }
}
