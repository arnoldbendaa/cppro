// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.message;

import com.cedar.cp.api.message.MessageAttatchRef;
import com.cedar.cp.api.message.MessageRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AttatchmentForMessageELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"MessageAttatch", "Message"};
   private transient MessageAttatchRef mMessageAttatchEntityRef;
   private transient MessageRef mMessageEntityRef;
   private transient long mMessageAttatchId;
   private transient byte[] mAttatch;
   private transient String mAttatchName;


   public AttatchmentForMessageELO() {
      super(new String[]{"MessageAttatch", "Message", "MessageAttatchId", "Attatch", "AttatchName"});
   }

   public void add(MessageAttatchRef eRefMessageAttatch, MessageRef eRefMessage, long col1, byte[] col2, String col3) {
      ArrayList l = new ArrayList();
      l.add(eRefMessageAttatch);
      l.add(eRefMessage);
      l.add(new Long(col1));
      l.add(col2);
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
      this.mMessageAttatchEntityRef = (MessageAttatchRef)l.get(index);
      this.mMessageEntityRef = (MessageRef)l.get(var4++);
      this.mMessageAttatchId = ((Long)l.get(var4++)).longValue();
      this.mAttatch = (byte[])((byte[])l.get(var4++));
      this.mAttatchName = (String)l.get(var4++);
   }

   public MessageAttatchRef getMessageAttatchEntityRef() {
      return this.mMessageAttatchEntityRef;
   }

   public MessageRef getMessageEntityRef() {
      return this.mMessageEntityRef;
   }

   public long getMessageAttatchId() {
      return this.mMessageAttatchId;
   }

   public byte[] getAttatch() {
      return this.mAttatch;
   }

   public String getAttatchName() {
      return this.mAttatchName;
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
