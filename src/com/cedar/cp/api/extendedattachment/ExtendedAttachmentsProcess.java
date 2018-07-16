// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.extendedattachment;

import com.cedar.cp.api.base.BusinessProcess;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extendedattachment.ExtendedAttachmentEditorSession;

public interface ExtendedAttachmentsProcess extends BusinessProcess {

   EntityList getAllExtendedAttachments();

   EntityList getExtendedAttachmentsForId(int var1);

   EntityList getAllImageExtendedAttachments();

   ExtendedAttachmentEditorSession getExtendedAttachmentEditorSession(Object var1) throws ValidationException;

   ExtendedAttachmentEditorSession getExtendedAttachmentEditorSessionForId(Integer var1) throws ValidationException;
}
