// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extendedattachment;

import com.cedar.cp.api.extendedattachment.ExtendedAttachmentRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK;
import java.io.Serializable;

public class ExtendedAttachmentRefImpl extends EntityRefImpl implements ExtendedAttachmentRef, Serializable {

   public ExtendedAttachmentRefImpl(ExtendedAttachmentPK key, String narrative) {
      super(key, narrative);
   }

   public ExtendedAttachmentPK getExtendedAttachmentPK() {
      return (ExtendedAttachmentPK)this.mKey;
   }
}
