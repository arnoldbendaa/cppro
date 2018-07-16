// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.message;

import com.cedar.cp.api.message.MessageRef;
import com.cedar.cp.api.message.MessageUserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageCountELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Message", "MessageUser", "MessageAttatch", "MessageUser"};
   private transient MessageRef mMessageEntityRef;
   private transient MessageUserRef mMessageUserEntityRef;
   private transient long mMessageId;


   public MessageCountELO() {
      super(new String[]{"Message", "MessageUser", "MessageId"});
   }

   public void add(MessageRef eRefMessage, MessageUserRef eRefMessageUser, long col1) {
      ArrayList l = new ArrayList();
      l.add(eRefMessage);
      l.add(eRefMessageUser);
      l.add(new Long(col1));
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mMessageEntityRef = (MessageRef)l.get(index);
      this.mMessageUserEntityRef = (MessageUserRef)l.get(var4++);
      this.mMessageId = ((Long)l.get(var4++)).longValue();
   }

   public MessageRef getMessageEntityRef() {
      return this.mMessageEntityRef;
   }

   public MessageUserRef getMessageUserEntityRef() {
      return this.mMessageUserEntityRef;
   }

   public long getMessageId() {
      return this.mMessageId;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
