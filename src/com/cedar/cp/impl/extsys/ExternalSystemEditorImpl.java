// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:12
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.extsys;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.ExternalSystem;
import com.cedar.cp.api.extsys.ExternalSystemEditor;
import com.cedar.cp.api.extsys.RemoteFileSystemResource;
import com.cedar.cp.dto.extsys.ExternalSystemEditorSessionSSO;
import com.cedar.cp.dto.extsys.ExternalSystemImpl;
import com.cedar.cp.dto.extsys.RemoteFileSystemResourceImpl;
import com.cedar.cp.ejb.api.extsys.ExternalSystemEditorSessionServer;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.extsys.ExternalSystemAdapter;
import com.cedar.cp.impl.extsys.ExternalSystemEditorSessionImpl;
import com.cedar.cp.impl.extsys.RemoteFileSystemResourceProxy;
import com.cedar.cp.util.StringUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExternalSystemEditorImpl extends BusinessEditorImpl implements ExternalSystemEditor {

   private ExternalSystemEditorSessionSSO mServerSessionData;
   private ExternalSystemImpl mEditorData;
   private ExternalSystemAdapter mEditorDataAdapter;


   public ExternalSystemEditorImpl(ExternalSystemEditorSessionImpl session, ExternalSystemEditorSessionSSO serverSessionData, ExternalSystemImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(ExternalSystemEditorSessionSSO serverSessionData, ExternalSystemImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setSystemType(int newSystemType) throws ValidationException {
      this.validateSystemType(newSystemType);
      if(this.mEditorData.getSystemType() != newSystemType) {
         this.setContentModified();
         this.mEditorData.setSystemType(newSystemType);
      }
   }

   public void setEnabled(boolean newEnabled) throws ValidationException {
      this.validateEnabled(newEnabled);
      if(this.mEditorData.isEnabled() != newEnabled) {
         this.setContentModified();
         this.mEditorData.setEnabled(newEnabled);
      }
   }

   public void setVisId(String newVisId) throws ValidationException {
      if(newVisId != null) {
         newVisId = StringUtils.rtrim(newVisId);
      }

      this.validateVisId(newVisId);
      if(this.mEditorData.getVisId() == null || !this.mEditorData.getVisId().equals(newVisId)) {
         this.validateRequiredString(newVisId, "A visual id must be supplied");
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
         this.validateRequiredString(newDescription, "A description must be supplied");
         this.setContentModified();
         this.mEditorData.setDescription(newDescription);
      }
   }

   public void setLocation(String newLocation) throws ValidationException {
      if(newLocation != null) {
         newLocation = StringUtils.rtrim(newLocation);
      }

      this.validateLocation(newLocation);
      if(this.mEditorData.getLocation() == null || !this.mEditorData.getLocation().equals(newLocation)) {
         this.validateRequiredString(newLocation, "A location must be supplied");
         this.setContentModified();
         this.mEditorData.setLocation(newLocation);
      }
   }

   public void setConnectorClass(String newConnectorClass) throws ValidationException {
      if(newConnectorClass != null) {
         newConnectorClass = StringUtils.rtrim(newConnectorClass);
      }

      this.validateConnectorClass(newConnectorClass);
      if(this.mEditorData.getConnectorClass() == null || !this.mEditorData.getConnectorClass().equals(newConnectorClass)) {
         this.setContentModified();
         this.mEditorData.setConnectorClass(newConnectorClass);
      }
   }

   public void setImportSource(String newImportSource) throws ValidationException {
      if(newImportSource != null) {
         newImportSource = StringUtils.rtrim(newImportSource);
      }

      this.validateImportSource(newImportSource);
      if(this.mEditorData.getImportSource() == null || !this.mEditorData.getImportSource().equals(newImportSource)) {
         this.setContentModified();
         this.mEditorData.setImportSource(newImportSource);
      }
   }

   public void setExportTarget(String newExportTarget) throws ValidationException {
      if(newExportTarget != null) {
         newExportTarget = StringUtils.rtrim(newExportTarget);
      }

      this.validateExportTarget(newExportTarget);
      if(this.mEditorData.getExportTarget() == null || !this.mEditorData.getExportTarget().equals(newExportTarget)) {
         this.setContentModified();
         this.mEditorData.setExportTarget(newExportTarget);
      }
   }

   public void validateSystemType(int newSystemType) throws ValidationException {}

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 29) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 29 on a ExternalSystem");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a ExternalSystem");
      }
   }

   public void validateLocation(String newLocation) throws ValidationException {
      if(newLocation != null && newLocation.length() > 128) {
         throw new ValidationException("length (" + newLocation.length() + ") of Location must not exceed 128 on a ExternalSystem");
      }
   }

   public void validateConnectorClass(String newConnectorClass) throws ValidationException {
      if(newConnectorClass != null && newConnectorClass.length() > 512) {
         throw new ValidationException("length (" + newConnectorClass.length() + ") of ConnectorClass must not exceed 512 on a ExternalSystem");
      }
   }

   public void validateImportSource(String newImportSource) throws ValidationException {
      if(newImportSource != null && newImportSource.length() > 1024) {
         throw new ValidationException("length (" + newImportSource.length() + ") of ImportSource must not exceed 1024 on a ExternalSystem");
      }
   }

   public void validateExportTarget(String newExportTarget) throws ValidationException {
      if(newExportTarget != null && newExportTarget.length() > 1024) {
         throw new ValidationException("length (" + newExportTarget.length() + ") of ExportTarget must not exceed 1024 on a ExternalSystem");
      }
   }

   public void validateEnabled(boolean newEnabled) throws ValidationException {}

   public ExternalSystem getExternalSystem() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new ExternalSystemAdapter((ExternalSystemEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {
      if(this.getExternalSystem().getLocation() == null || this.getExternalSystem().getLocation().trim().length() == 0) {
         throw new ValidationException("A location must be supplied");
      }
   }

   public List<RemoteFileSystemResource> queryRemoteFileSystem() throws ValidationException {
      List roots = (new ExternalSystemEditorSessionServer(this.getConnection())).queryRemoteFileSystem((String)null);
      ArrayList proxyRoots = new ArrayList();
      Iterator i$ = roots.iterator();

      while(i$.hasNext()) {
         RemoteFileSystemResource root = (RemoteFileSystemResource)i$.next();
         proxyRoots.add(RemoteFileSystemResourceProxy.newInstance(this, (RemoteFileSystemResourceImpl)root));
      }

      return proxyRoots;
   }

   public List<RemoteFileSystemResource> queryRemoteFileSystem(String dir) throws ValidationException {
      return (new ExternalSystemEditorSessionServer(this.getConnection())).queryRemoteFileSystem(dir);
   }

   public EntityList queryAllXactSubsystems(Object externalSystemPK) throws ValidationException {
      try {
         return (new ExternalSystemEditorSessionServer(this.getConnection())).queryAllXactSubsystems(externalSystemPK);
      } catch (CPException var3) {
         throw new RuntimeException("can\'t queryAllXactSubsystems()", var3);
      }
   }

   public List queryAllXactAvailableColumns(int subsystemId) throws ValidationException {
      try {
         List e = null;
         e = this.mEditorData.getAvailableColumnList(subsystemId);
         if(e == null) {
            e = (new ExternalSystemEditorSessionServer(this.getConnection())).queryAllXactAvailableColumns(subsystemId).getDataAsList();
         }

         return e;
      } catch (CPException var3) {
         throw new RuntimeException("can\'t queryAllXactAvailableColumns()", var3);
      }
   }

   public List queryXactColumnSelection(int subsystemId) throws ValidationException {
      try {
         List e = null;
         e = this.mEditorData.getSelectedColumnList(subsystemId);
         if(e == null) {
            e = (new ExternalSystemEditorSessionServer(this.getConnection())).queryXactColumnSelection(subsystemId).getDataAsList();
         }

         return e;
      } catch (CPException var3) {
         throw new RuntimeException("can\'t queryXactColumnSelection()", var3);
      }
   }

   public void updateXactColumnSelection(int subsystemId, List available, List selection) throws ValidationException {
      this.mEditorData.addAvailableColumns(subsystemId, available);
      this.mEditorData.addSelectedColumns(subsystemId, selection);
      this.setContentModified();
   }

   public void setProperty(String name, String value) {
      if(value == null) {
         if(this.mEditorData.getProperties().remove(name) != null) {
            this.setContentModified();
         }
      } else {
         String oldValue = (String)this.mEditorData.getProperties().get(name);
         this.mEditorData.getProperties().put(name, value);
         if(oldValue == null || !oldValue.equals(value)) {
            this.setContentModified();
         }
      }

   }
}
