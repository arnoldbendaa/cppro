// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.message;

import com.cedar.cp.api.message.MessageRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllMessagesELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Message", "MessageUser", "MessageAttatch"};
   private transient MessageRef mMessageEntityRef;
   private transient long mMessageId;


   public AllMessagesELO() {
      super(new String[]{"Message", "MessageId"});
   }

   public void add(MessageRef eRefMessage, long col1) {
      ArrayList l = new ArrayList();
      l.add(eRefMessage);
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
      this.mMessageId = ((Long)l.get(var4++)).longValue();
   }

   public MessageRef getMessageEntityRef() {
      return this.mMessageEntityRef;
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
