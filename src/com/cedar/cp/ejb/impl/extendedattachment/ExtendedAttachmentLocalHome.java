// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:30
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.extendedattachment;

import com.cedar.cp.dto.extendedattachment.ExtendedAttachmentPK;
import com.cedar.cp.ejb.impl.extendedattachment.ExtendedAttachmentEVO;
import com.cedar.cp.ejb.impl.extendedattachment.ExtendedAttachmentLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface ExtendedAttachmentLocalHome extends EJBLocalHome {

   ExtendedAttachmentLocal create(ExtendedAttachmentEVO var1) throws EJBException, CreateException;

   ExtendedAttachmentLocal findByPrimaryKey(ExtendedAttachmentPK var1) throws FinderException;
}
