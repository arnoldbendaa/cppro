// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extendedattachment;

import com.cedar.cp.api.extendedattachment.ExtendedAttachmentRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllExtendedAttachmentsELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"ExtendedAttachment"};
   private transient ExtendedAttachmentRef mExtendedAttachmentEntityRef;
   private transient int mExtendedAttachmentId;


   public AllExtendedAttachmentsELO() {
      super(new String[]{"ExtendedAttachment", "ExtendedAttachmentId"});
   }

   public void add(ExtendedAttachmentRef eRefExtendedAttachment, int col1) {
      ArrayList l = new ArrayList();
      l.add(eRefExtendedAttachment);
      l.add(new Integer(col1));
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
      this.mExtendedAttachmentEntityRef = (ExtendedAttachmentRef)l.get(index);
      this.mExtendedAttachmentId = ((Integer)l.get(var4++)).intValue();
   }

   public ExtendedAttachmentRef getExtendedAttachmentEntityRef() {
      return this.mExtendedAttachmentEntityRef;
   }

   public int getExtendedAttachmentId() {
      return this.mExtendedAttachmentId;
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
