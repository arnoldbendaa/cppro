// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:53
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.dto.formnotes;

import com.cedar.cp.api.formnotes.FormNotesRef;
import com.cedar.cp.dto.base.AbstractELO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllFormNotesForBudgetLocationELO extends AbstractELO implements Serializable {

   private static final String[] mEntity = new String[]{"FormNotes"};
   private transient FormNotesRef mFormNotesEntityRef;
   private transient int mFormId;
   private transient int mStructureElementId;
   private transient String mNote;
   private transient int mAttachmentId;


   public AllFormNotesForBudgetLocationELO() {
      super(new String[]{"FormNotes", "FormId", "StructureElementId", "Note", "AttachmentId"});
   }

   public void add(FormNotesRef eRefFormNotes, int col1, int col2, String col3, int col4) {
      ArrayList l = new ArrayList();
      l.add(eRefFormNotes);
      l.add(new Integer(col1));
      l.add(new Integer(col2));
      l.add(col3);
      l.add(new Integer(col4));
      this.mCollection.add(l);
   }

   public void next() {
      if(this.mIterator == null) {
         this.reset();
      }

      ++this.mCurrRowIndex;
      List l = (List)this.mIterator.next();
      byte index = 0;
      int var4 = index + 1;
      this.mFormNotesEntityRef = (FormNotesRef)l.get(index);
      this.mFormId = ((Integer)l.get(var4++)).intValue();
      this.mStructureElementId = ((Integer)l.get(var4++)).intValue();
      this.mNote = (String)l.get(var4++);
      this.mAttachmentId = ((Integer)l.get(var4++)).intValue();
   }

   public FormNotesRef getFormNotesEntityRef() {
      return this.mFormNotesEntityRef;
   }

   public int getFormId() {
      return this.mFormId;
   }

   public int getStructureElementId() {
      return this.mStructureElementId;
   }

   public String getNote() {
      return this.mNote;
   }

   public int getAttachmentId() {
      return this.mAttachmentId;
   }

   public boolean includesEntity(String name) {
      for(int i = 0; i < mEntity.length; ++i) {
         if(name.equals(mEntity[i])) {
            return true;
         }
      }

      return false;
   }

}
