// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.message;

import com.cedar.cp.api.message.MessageRef;
import com.cedar.cp.api.message.MessageUserRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class InBoxForUserELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"Message", "MessageUser", "MessageAttatch", "MessageUser"};
   private transient MessageRef mMessageEntityRef;
   private transient MessageUserRef mMessageUserEntityRef;
   private transient long mMessageId;
   private transient long mMessageUserId;
   private transient String mSubject;
   private transient boolean mRead;
   private transient Timestamp mCreatedTime;
   private transient boolean mCol6;


   public InBoxForUserELO() {
      super(new String[]{"Message", "MessageUser", "MessageId", "MessageUserId", "Subject", "Read", "CreatedTime", "col6"});
   }

   public void add(MessageRef eRefMessage, MessageUserRef eRefMessageUser, long col1, long col2, String col3, boolean col4, Timestamp col5, boolean col6) {
      ArrayList l = new ArrayList();
      l.add(eRefMessage);
      l.add(eRefMessageUser);
      l.add(new Long(col1));
      l.add(new Long(col2));
      l.add(col3);
      l.add(new Boolean(col4));
      l.add(col5);
      l.add(new Boolean(col6));
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
      this.mMessageUserId = ((Long)l.get(var4++)).longValue();
      this.mSubject = (String)l.get(var4++);
      this.mRead = ((Boolean)l.get(var4++)).booleanValue();
      this.mCreatedTime = (Timestamp)l.get(var4++);
      this.mCol6 = ((Boolean)l.get(var4++)).booleanValue();
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

   public long getMessageUserId() {
      return this.mMessageUserId;
   }

   public String getSubject() {
      return this.mSubject;
   }

   public boolean getRead() {
      return this.mRead;
   }

   public Timestamp getCreatedTime() {
      return this.mCreatedTime;
   }

   public boolean getCol6() {
      return this.mCol6;
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
