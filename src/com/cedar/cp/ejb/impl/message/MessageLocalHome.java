// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:43
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.message;

import com.cedar.cp.dto.message.MessagePK;
import com.cedar.cp.ejb.impl.message.MessageEVO;
import com.cedar.cp.ejb.impl.message.MessageLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface MessageLocalHome extends EJBLocalHome {

   MessageLocal create(MessageEVO var1) throws EJBException, CreateException;

   MessageLocal findByPrimaryKey(MessagePK var1) throws FinderException;
}
