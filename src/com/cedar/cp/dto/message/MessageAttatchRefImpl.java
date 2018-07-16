// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.message;

import com.cedar.cp.api.message.MessageAttatchRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.message.MessageAttatchCK;
import com.cedar.cp.dto.message.MessageAttatchPK;
import java.io.Serializable;

public class MessageAttatchRefImpl extends EntityRefImpl implements MessageAttatchRef, Serializable {

   public MessageAttatchRefImpl(MessageAttatchCK key, String narrative) {
      super(key, narrative);
   }

   public MessageAttatchRefImpl(MessageAttatchPK key, String narrative) {
      super(key, narrative);
   }

   public MessageAttatchPK getMessageAttatchPK() {
      return this.mKey instanceof MessageAttatchCK?((MessageAttatchCK)this.mKey).getMessageAttatchPK():(MessageAttatchPK)this.mKey;
   }
}
