// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.formnotes;

import com.cedar.cp.dto.formnotes.FormNotesPK;
import com.cedar.cp.ejb.impl.formnotes.FormNotesEVO;
import com.cedar.cp.ejb.impl.formnotes.FormNotesLocal;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.FinderException;

public interface FormNotesLocalHome extends EJBLocalHome {

   FormNotesLocal create(FormNotesEVO var1) throws EJBException, CreateException;

   FormNotesLocal findByPrimaryKey(FormNotesPK var1) throws FinderException;
}
