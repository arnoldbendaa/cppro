// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.virement;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.virement.VirementGroup;
import com.cedar.cp.api.model.virement.VirementGroupEditor;
import com.cedar.cp.api.model.virement.VirementLineEditor;
import com.cedar.cp.api.model.virement.VirementRequest;
import com.cedar.cp.dto.model.virement.VirementAuthPointImpl;
import com.cedar.cp.dto.model.virement.VirementGroupImpl;
import com.cedar.cp.dto.model.virement.VirementLineImpl;
import com.cedar.cp.dto.model.virement.VirementRequestLinePK;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.model.virement.VirementLineEditorImpl;
import com.cedar.cp.impl.model.virement.VirementRequestEditorImpl;
import java.util.List;

public class VirementGroupEditorImpl extends SubBusinessEditorImpl implements VirementGroupEditor, SubBusinessEditorOwner {

   private VirementGroupImpl mVirementGroup;
   private VirementGroupImpl mOriginalGroup;


   public VirementGroupEditorImpl(BusinessSession sess, SubBusinessEditorOwner owner, VirementGroupImpl virementGroup) {
      super(sess, owner);

      try {
         this.mOriginalGroup = virementGroup;
         if(virementGroup != null) {
            this.mVirementGroup = (VirementGroupImpl)virementGroup.clone();
         } else {
            this.mVirementGroup = new VirementGroupImpl();
         }

      } catch (CloneNotSupportedException var5) {
         throw new CPException("Failed to clone VirementGroupImpl");
      }
   }

   private VirementRequestEditorImpl getVirementRequestEditor() {
      return (VirementRequestEditorImpl)this.getOwner();
   }

   protected void saveModifications() throws ValidationException {
      List l = this.getVirementRequestEditor().getVirementRequest().getVirementGroups();
      int index;
      if(this.mOriginalGroup != null && (index = l.indexOf(this.mOriginalGroup)) != -1) {
         l.remove(index);
         l.add(index, this.mVirementGroup);
      } else {
         l.add(this.mVirementGroup);
      }

      this.getVirementRequestEditor().setContentModified();
   }

   protected void undoModifications() throws CPException {}

   public VirementLineEditor getEditor(Object key) {
      VirementLineImpl line = this.mVirementGroup.findLine(key);
      if(line == null && key != null && key instanceof Number && ((Number)key).intValue() > 0) {
         throw new IllegalStateException("Failed to locate line with key:" + key);
      } else {
         return new VirementLineEditorImpl(this.getBusinessSession(), this, line);
      }
   }

   public VirementGroup getVirementGroup() {
      return this.mVirementGroup;
   }

   public void remove(VirementRequest request, Object lineKey) throws ValidationException {
      VirementLineImpl line = this.mVirementGroup.findLine(lineKey);
      if(line == null) {
         throw new ValidationException("Unable to find virement line to remove with key:" + lineKey);
      } else {
         VirementAuthPointImpl authPoint = (VirementAuthPointImpl)request.queryAuthPointForLine(line);
         if(authPoint != null) {
            authPoint.removeLine(line.getPK().getRequestLineId());
         }

         this.mVirementGroup.getRows().remove(line);
         this.setContentModified();
      }
   }

   public void setNotes(String notes) {
      if(this.mVirementGroup.getNotes() == null && notes != null || this.mVirementGroup.getNotes() != null && notes == null || this.mVirementGroup.getNotes() != null && notes != null && !this.mVirementGroup.getNotes().equals(notes)) {
         this.mVirementGroup.setNotes(notes);
         this.setContentModified();
      }

   }

   public void removeSubBusinessEditor(SubBusinessEditor editor) throws CPException {}

   public Object decodeLineKey(String keyStr) {
      return keyStr != null && keyStr.trim().length() != 0?VirementRequestLinePK.getKeyFromTokens(keyStr):null;
   }
}
