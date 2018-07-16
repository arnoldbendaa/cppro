// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:52
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.extendedattachment;

import com.cedar.cp.api.extendedattachment.ExtendedAttachment;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK;
import java.io.Serializable;

public class ExtendedAttachmentImpl implements ExtendedAttachment, Serializable, Cloneable {

   private Object mPrimaryKey;
   private String mFileName;
   private byte[] mAttatch;


   public ExtendedAttachmentImpl(Object paramKey) {
      this.mPrimaryKey = paramKey;
      this.mFileName = "";
      this.mAttatch = null;
   }

   public Object getPrimaryKey() {
      return this.mPrimaryKey;
   }

   public void setPrimaryKey(Object paramKey) {
      this.mPrimaryKey = (ExtendedAttachmentPK)paramKey;
   }

   public String getFileName() {
      return this.mFileName;
   }

   public byte[] getAttatch() {
      return this.mAttatch;
   }

   public void setFileName(String paramFileName) {
      this.mFileName = paramFileName;
   }

   public void setAttatch(byte[] paramAttatch) {
      this.mAttatch = paramAttatch;
   }
}
