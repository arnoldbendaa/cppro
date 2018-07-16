// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.currency;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.currency.Currency;
import com.cedar.cp.api.currency.CurrencyEditor;
import com.cedar.cp.dto.currency.CurrencyEditorSessionSSO;
import com.cedar.cp.dto.currency.CurrencyImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.currency.CurrencyAdapter;
import com.cedar.cp.impl.currency.CurrencyEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class CurrencyEditorImpl extends BusinessEditorImpl implements CurrencyEditor {

   private CurrencyEditorSessionSSO mServerSessionData;
   private CurrencyImpl mEditorData;
   private CurrencyAdapter mEditorDataAdapter;


   public CurrencyEditorImpl(CurrencyEditorSessionImpl session, CurrencyEditorSessionSSO serverSessionData, CurrencyImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(CurrencyEditorSessionSSO serverSessionData, CurrencyImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setVisId(String newVisId) throws ValidationException {
      if(newVisId != null) {
         newVisId = StringUtils.rtrim(newVisId);
      }

      this.validateVisId(newVisId);
      if(this.mEditorData.getVisId() == null || !this.mEditorData.getVisId().equals(newVisId)) {
         this.setContentModified();
         this.mEditorData.setVisId(newVisId);
      }
   }

   public void setDescription(String newDescription) throws ValidationException {
      if(newDescription != null) {
         newDescription = StringUtils.rtrim(newDescription);
      }

      this.validateDescription(newDescription);
      if(this.mEditorData.getDescription() == null || !this.mEditorData.getDescription().equals(newDescription)) {
         this.setContentModified();
         this.mEditorData.setDescription(newDescription);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a Currency");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a Currency");
      }
   }

   public Currency getCurrency() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new CurrencyAdapter((CurrencyEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}
}
