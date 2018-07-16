// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.message;

import com.cedar.cp.api.message.MessageRef;
import com.cedar.cp.api.message.MessageUserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllMessagesToUserELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"MessageUser", "Message"};
   private transient MessageUserRef mMessageUserEntityRef;
   private transient MessageRef mMessageEntityRef;
   private transient long mMessageId;
   private transient long mMessageUserId;
   private transient String mUserId;


   public AllMessagesToUserELO() {
      super(new String[]{"MessageUser", "Message", "MessageId", "MessageUserId", "UserId"});
   }

   public void add(MessageUserRef eRefMessageUser, MessageRef eRefMessage, long col1, long col2, String col3) {
      ArrayList l = new ArrayList();
      l.add(eRefMessageUser);
      l.add(eRefMessage);
      l.add(new Long(col1));
      l.add(new Long(col2));
      l.add(col3);
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
      this.mMessageUserEntityRef = (MessageUserRef)l.get(index);
      this.mMessageEntityRef = (MessageRef)l.get(var4++);
      this.mMessageId = ((Long)l.get(var4++)).longValue();
      this.mMessageUserId = ((Long)l.get(var4++)).longValue();
      this.mUserId = (String)l.get(var4++);
   }

   public MessageUserRef getMessageUserEntityRef() {
      return this.mMessageUserEntityRef;
   }

   public MessageRef getMessageEntityRef() {
      return this.mMessageEntityRef;
   }

   public long getMessageId() {
      return this.mMessageId;
   }

   public long getMessageUserId() {
      return this.mMessageUserId;
   }

   public String getUserId() {
      return this.mUserId;
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
