// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.formnotes;

import com.cedar.cp.api.formnotes.FormNotesRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.formnotes.FormNotesPK;
import java.io.Serializable;

public class FormNotesRefImpl extends EntityRefImpl implements FormNotesRef, Serializable {

   public FormNotesRefImpl(FormNotesPK key, String narrative) {
      super(key, narrative);
   }

   public FormNotesPK getFormNotesPK() {
      return (FormNotesPK)this.mKey;
   }
}
