// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.extendedattachment;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentEditorSessionCSO;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentEditorSessionSSO;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

public interface ExtendedAttachmentEditorSessionLocal extends EJBLocalObject {

   ExtendedAttachmentEditorSessionSSO getItemData(int var1, Object var2) throws ValidationException, EJBException;

   ExtendedAttachmentEditorSessionSSO getNewItemData(int var1) throws EJBException;

   ExtendedAttachmentPK insert(ExtendedAttachmentEditorSessionCSO var1) throws ValidationException, EJBException;

   ExtendedAttachmentPK copy(ExtendedAttachmentEditorSessionCSO var1) throws ValidationException, EJBException;

   void update(ExtendedAttachmentEditorSessionCSO var1) throws ValidationException, EJBException;

   void delete(int var1, Object var2) throws ValidationException, EJBException;
}
