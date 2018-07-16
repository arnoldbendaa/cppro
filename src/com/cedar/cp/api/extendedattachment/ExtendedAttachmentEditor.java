// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.extendedattachment;

import com.cedar.cp.api.base.BusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extendedattachment.ExtendedAttachment;

public interface ExtendedAttachmentEditor extends BusinessEditor {

   void setFileName(String var1) throws ValidationException;

   void setAttatch(byte[] var1) throws ValidationException;

   ExtendedAttachment getExtendedAttachment();
}
