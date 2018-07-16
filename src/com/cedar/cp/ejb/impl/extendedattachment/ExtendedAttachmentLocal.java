// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extendedattachment;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK;
import com.cedar.cp.ejb.impl.extendedattachment.ExtendedAttachmentEVO;
import javax.ejb.EJBLocalObject;

public interface ExtendedAttachmentLocal extends EJBLocalObject {

   ExtendedAttachmentEVO getDetails(String var1) throws ValidationException;

   ExtendedAttachmentPK generateKeys();

   void setDetails(ExtendedAttachmentEVO var1);

   ExtendedAttachmentEVO setAndGetDetails(ExtendedAttachmentEVO var1, String var2);
}
