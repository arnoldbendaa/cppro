// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:54
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.message;

import com.cedar.cp.api.message.MessageRef;
import com.cedar.cp.api.message.MessageUserRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MessageToUserELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"MessageUser", "Message", "User"};
   private transient MessageUserRef mMessageUserEntityRef;
   private transient MessageRef mMessageEntityRef;
   private transient UserRef mUserEntityRef;
   private transient long mMessageId;
   private transient String mUserId;
   private transient String mName;
   private transient String mFullName;


   public MessageToUserELO() {
      super(new String[]{"MessageUser", "Message", "User", "MessageId", "UserId", "Name", "FullName"});
   }

   public void add(MessageUserRef eRefMessageUser, MessageRef eRefMessage, UserRef eRefUser, long col1, String col2, String col3, String col4) {
      ArrayList l = new ArrayList();
      l.add(eRefMessageUser);
      l.add(eRefMessage);
      l.add(eRefUser);
      l.add(new Long(col1));
      l.add(col2);
      l.add(col3);
      l.add(col4);
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
      this.mUserEntityRef = (UserRef)l.get(var4++);
      this.mMessageId = ((Long)l.get(var4++)).longValue();
      this.mUserId = (String)l.get(var4++);
      this.mName = (String)l.get(var4++);
      this.mFullName = (String)l.get(var4++);
   }

   public MessageUserRef getMessageUserEntityRef() {
      return this.mMessageUserEntityRef;
   }

   public MessageRef getMessageEntityRef() {
      return this.mMessageEntityRef;
   }

   public UserRef getUserEntityRef() {
      return this.mUserEntityRef;
   }

   public long getMessageId() {
      return this.mMessageId;
   }

   public String getUserId() {
      return this.mUserId;
   }

   public String getName() {
      return this.mName;
   }

   public String getFullName() {
      return this.mFullName;
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
