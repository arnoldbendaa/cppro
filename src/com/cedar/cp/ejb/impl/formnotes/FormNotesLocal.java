// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:08:40
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.formnotes;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.formnotes.FormNotesPK;
import com.cedar.cp.ejb.impl.formnotes.FormNotesEVO;
import javax.ejb.EJBLocalObject;

public interface FormNotesLocal extends EJBLocalObject {

   FormNotesEVO getDetails(String var1) throws ValidationException;

   FormNotesPK generateKeys();

   void setDetails(FormNotesEVO var1);

   FormNotesEVO setAndGetDetails(FormNotesEVO var1, String var2);
}
