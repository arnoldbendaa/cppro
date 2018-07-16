// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:18
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.report.distribution;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.report.distribution.Distribution;
import com.cedar.cp.api.report.distribution.DistributionEditor;
import com.cedar.cp.dto.report.distribution.DistributionEditorSessionSSO;
import com.cedar.cp.dto.report.distribution.DistributionImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.report.distribution.DistributionAdapter;
import com.cedar.cp.impl.report.distribution.DistributionEditorSessionImpl;
import com.cedar.cp.util.StringUtils;

public class DistributionEditorImpl extends BusinessEditorImpl implements DistributionEditor {

   private DistributionEditorSessionSSO mServerSessionData;
   private DistributionImpl mEditorData;
   private DistributionAdapter mEditorDataAdapter;


   public DistributionEditorImpl(DistributionEditorSessionImpl session, DistributionEditorSessionSSO serverSessionData, DistributionImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(DistributionEditorSessionSSO serverSessionData, DistributionImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setRaDistribution(boolean newRaDistribution) throws ValidationException {
      this.validateRaDistribution(newRaDistribution);
      if(this.mEditorData.isRaDistribution() != newRaDistribution) {
         this.setContentModified();
         this.mEditorData.setRaDistribution(newRaDistribution);
      }
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

   public void setDirRoot(String newDirRoot) throws ValidationException {
      if(newDirRoot != null) {
         newDirRoot = StringUtils.rtrim(newDirRoot);
      }

      this.validateDirRoot(newDirRoot);
      if(this.mEditorData.getDirRoot() == null || !this.mEditorData.getDirRoot().equals(newDirRoot)) {
         this.setContentModified();
         this.mEditorData.setDirRoot(newDirRoot);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a Distribution");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a Distribution");
      }
   }

   public void validateRaDistribution(boolean newRaDistribution) throws ValidationException {}

   public void validateDirRoot(String newDirRoot) throws ValidationException {
      if(newDirRoot != null && newDirRoot.length() > 50) {
         throw new ValidationException("length (" + newDirRoot.length() + ") of DirRoot must not exceed 50 on a Distribution");
      }
   }

   public Distribution getDistribution() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new DistributionAdapter((DistributionEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {}

   public void addExternalDist(Object o) {
      this.mEditorData.getExternalDestinationList().add(o);
      this.setContentModified();
   }

   public void addInternalDist(Object o) {
      this.mEditorData.getInternalDestinationList().add(o);
      this.setContentModified();
   }
}
