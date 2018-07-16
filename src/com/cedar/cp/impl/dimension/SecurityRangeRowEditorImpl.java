// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:11
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.dimension;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.SecurityRangeRow;
import com.cedar.cp.api.dimension.SecurityRangeRowEditor;
import com.cedar.cp.dto.dimension.SecurityRangeRowImpl;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.dimension.SecurityRangeEditorImpl;

public class SecurityRangeRowEditorImpl extends SubBusinessEditorImpl implements SecurityRangeRowEditor {

   private String mFrom;
   private String mTo;
   private SecurityRangeRowImpl mRow;
   private boolean mInsert;


   public SecurityRangeRowEditorImpl(SecurityRangeEditorImpl parent, SecurityRangeRowImpl row) {
      super(parent.getBusinessSession(), parent);
      this.mRow = row;
      if(this.mRow != null) {
         this.mFrom = this.mRow.getFrom();
         this.mTo = this.mRow.getTo();
         this.mInsert = false;
      } else {
         this.mInsert = true;
         this.mRow = new SecurityRangeRowImpl();
      }

   }

   public void setFrom(String from) throws ValidationException {
      this.validateFrom(from);
      this.mFrom = from;
      this.setContentModified();
   }

   public void setTo(String to) throws ValidationException {
      this.validateTo(to);
      this.mTo = to;
      this.setContentModified();
   }

   private void validateFrom(String from) throws ValidationException {
      if(from == null || from.trim().length() == 0) {
         throw new ValidationException("A \'from\' value must be supplied");
      }
   }

   private void validateTo(String to) throws ValidationException {
      if(to == null || to.trim().length() == 0) {
         throw new ValidationException("A \'to\' value must be supplied");
      }
   }

   protected void undoModifications() throws CPException {}

   protected void saveModifications() throws ValidationException {
      this.validateFrom(this.mFrom);
      this.validateTo(this.mTo);
      if(this.mFrom.compareTo(this.mTo) > 0) {
         throw new ValidationException("\'From\' value must be less than or equal to \'To\' value");
      } else {
         SecurityRangeEditorImpl owner = (SecurityRangeEditorImpl)this.getOwner();
         if(this.mInsert) {
            this.mRow.setFrom(this.mFrom);
            this.mRow.setTo(this.mTo);
            owner.insertRow(this.mRow);
         } else {
            owner.updateRow(this.mRow, this.mFrom, this.mTo);
         }

      }
   }

   public SecurityRangeRow getSecurityRangeRow() {
      return this.mRow;
   }
}
