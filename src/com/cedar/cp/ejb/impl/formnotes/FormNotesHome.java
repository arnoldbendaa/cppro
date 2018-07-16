// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.formnotes;

import com.cedar.cp.dto.formnotes.FormNotesPK;
import com.cedar.cp.ejb.impl.formnotes.FormNotesEVO;
import com.cedar.cp.ejb.impl.formnotes.FormNotesRemote;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.EJBHome;
import javax.ejb.FinderException;

public interface FormNotesHome extends EJBHome {

   FormNotesRemote create(FormNotesEVO var1) throws EJBException, CreateException, RemoteException;

   FormNotesRemote findByPrimaryKey(FormNotesPK var1) throws EJBException, FinderException, RemoteException;
}
