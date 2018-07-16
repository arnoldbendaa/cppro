// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:15
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.mapping;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.MappingKey;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.mapping.MappedCalendarEditor;
import com.cedar.cp.api.model.mapping.MappedDimension;
import com.cedar.cp.api.model.mapping.MappedDimensionEditor;
import com.cedar.cp.api.model.mapping.MappedFinanceCube;
import com.cedar.cp.api.model.mapping.MappedFinanceCubeEditor;
import com.cedar.cp.api.model.mapping.MappedModel;
import com.cedar.cp.api.model.mapping.MappedModelEditor;
import com.cedar.cp.api.model.mapping.extsys.ExternalSystem;
import com.cedar.cp.api.model.mapping.extsys.FinanceCompany;
import com.cedar.cp.api.model.mapping.extsys.FinanceLedger;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.model.mapping.MappedCalendarImpl;
import com.cedar.cp.dto.model.mapping.MappedDimensionImpl;
import com.cedar.cp.dto.model.mapping.MappedDimensionPK;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubeImpl;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubePK;
import com.cedar.cp.dto.model.mapping.MappedModelEditorSessionSSO;
import com.cedar.cp.dto.model.mapping.MappedModelImpl;
import com.cedar.cp.dto.model.mapping.extsys.ExternalSystemImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.impl.base.CPConnectionImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.model.mapping.MappedCalendarEditorImpl;
import com.cedar.cp.impl.model.mapping.MappedDimensionEditorImpl;
import com.cedar.cp.impl.model.mapping.MappedFinanceCubeEditorImpl;
import com.cedar.cp.impl.model.mapping.MappedModelAdapter;
import com.cedar.cp.impl.model.mapping.MappedModelEditorSessionImpl;
import com.cedar.cp.impl.model.mapping.extsys.ProxyFactory;
import com.cedar.cp.util.StringUtils;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MappedModelEditorImpl extends BusinessEditorImpl implements MappedModelEditor, SubBusinessEditorOwner {

   private transient List<ExternalSystem> mExternalSystems;
   private MappedDimensionEditor mDimensionMappingEditor;
   private MappedCalendarEditorImpl mMappedCalendarEditor;
   private int mNextKey = -1;
   private MappedModelEditorSessionSSO mServerSessionData;
   private MappedModelImpl mEditorData;
   private MappedModelAdapter mEditorDataAdapter;


   public MappedModelEditorImpl(MappedModelEditorSessionImpl session, MappedModelEditorSessionSSO serverSessionData, MappedModelImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(MappedModelEditorSessionSSO serverSessionData, MappedModelImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setModelId(int newModelId) throws ValidationException {
      this.validateModelId(newModelId);
      if(this.mEditorData.getModelId() != newModelId) {
         this.setContentModified();
         this.mEditorData.setModelId(newModelId);
      }
   }

   public void setExternalSystemId(int newExternalSystemId) throws ValidationException {
      this.validateExternalSystemId(newExternalSystemId);
      if(this.mEditorData.getExternalSystemId() != newExternalSystemId) {
         if(!this.mEditorData.isNew()) {
            throw new ValidationException("The external system id may only be changed when inserting a new mapped model");
         } else {
            this.setContentModified();
            this.mEditorData.setExternalSystemId(newExternalSystemId);
         }
      }
   }

   public void setCompanyVisId(String newCompanyVisId) throws ValidationException {
      if(newCompanyVisId != null) {
         newCompanyVisId = StringUtils.rtrim(newCompanyVisId);
      }

      this.validateCompanyVisId(newCompanyVisId);
      if(this.mEditorData.getCompanyVisId() == null || !this.mEditorData.getCompanyVisId().equals(newCompanyVisId)) {
         if(!this.mEditorData.isNew()) {
            throw new ValidationException("The external company may only be changed when inserting a new mapped model");
         } else {
            this.setContentModified();
            this.mEditorData.setCompanyVisId(newCompanyVisId);
         }
      }
   }

   public void setLedgerVisId(String newLedgerVisId) throws ValidationException {
      if(newLedgerVisId != null) {
         newLedgerVisId = StringUtils.rtrim(newLedgerVisId);
      }

      this.validateLedgerVisId(newLedgerVisId);
      if(this.mEditorData.getLedgerVisId() == null || !this.mEditorData.getLedgerVisId().equals(newLedgerVisId)) {
         if(!this.mEditorData.isNew()) {
            throw new ValidationException("The external ledger may only be changed when inserting a new mapped model");
         } else {
            this.setContentModified();
            this.mEditorData.setLedgerVisId(newLedgerVisId);
         }
      }
   }

   public void validateModelId(int newModelId) throws ValidationException {}

   public void validateExternalSystemId(int newExternalSystemId) throws ValidationException {}

   public void validateCompanyVisId(String newCompanyVisId) throws ValidationException {
      if(newCompanyVisId != null && newCompanyVisId.length() > 128) {
         throw new ValidationException("length (" + newCompanyVisId.length() + ") of CompanyVisId must not exceed 128 on a MappedModel");
      }
   }

   public void validateLedgerVisId(String newLedgerVisId) throws ValidationException {
      if(newLedgerVisId != null && newLedgerVisId.length() > 128) {
         throw new ValidationException("length (" + newLedgerVisId.length() + ") of LedgerVisId must not exceed 128 on a MappedModel");
      }
   }

   public void setOwningModelRef(ModelRef ref) throws ValidationException {
      ModelRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getModelEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getOwningModelRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getOwningModelRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      if(!this.mEditorData.isNew()) {
         throw new ValidationException("The external system may only be changed when inserting a new mapped model");
      } else {
         this.mEditorData.setOwningModelRef(actualRef);
         this.setContentModified();
      }
   }

   public void setExternalSystemRef(ExternalSystemRef ref) throws ValidationException {
      ExternalSystemRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getExternalSystemEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getExternalSystemRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getExternalSystemRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setExternalSystemRef(actualRef);
      this.setContentModified();
   }

   public MappedModel getMappedModel() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new MappedModelAdapter((MappedModelEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {
      if(this.mEditorData.isNew() && this.mEditorData.getResponisbilityAreaHierarchy() == null) {
         throw new ValidationException("The responsibility area hierarchy must be selected for the model");
      }
   }

   public List<ExternalSystem> getExternalSystems() {
      if(this.mExternalSystems == null) {
         this.mExternalSystems = new ArrayList();
         EntityList externalSystems = this.getConnection().getListHelper().getAllExternalSystems();

         for(int row = 0; row < externalSystems.getNumRows(); ++row) {
            int type = ((Integer)externalSystems.getValueAt(row, "SystemType")).intValue();
            EntityRefImpl extSysEntityRef = (EntityRefImpl)externalSystems.getValueAt(row, "ExternalSystem");
            boolean enabled = ((Boolean)externalSystems.getValueAt(row, "Enabled")).booleanValue();
            String description = (String)externalSystems.getValueAt(row, "Description");
            String location = (String)externalSystems.getValueAt(row, "Location");
            ExternalSystemImpl extsys = new ExternalSystemImpl(type, extSysEntityRef, location, description, enabled);
            this.mExternalSystems.add(ProxyFactory.newProxy((CPConnectionImpl)this.getConnection(), extsys));
         }
      }

      return this.mExternalSystems;
   }

   public ExternalSystem findExternalSystem(EntityRef extSysRef) {
      Iterator i$ = this.getExternalSystems().iterator();

      ExternalSystem es;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         es = (ExternalSystem)i$.next();
      } while(!es.getEntityRef().equals(extSysRef));

      return es;
   }

   public ExternalSystem getExternalSystem() {
      return this.mEditorData.getExternalSystemRef() == null?null:this.findExternalSystem(this.mEditorData.getExternalSystemRef());
   }

   public FinanceCompany getFinanceCompany() {
      MappingKey mappingKey = this.mEditorData.getFinanceCompanyKey();
      if(mappingKey == null) {
         return null;
      } else {
         ExternalSystem extSys = this.getExternalSystem();
         if(extSys == null) {
            return null;
         } else {
            Iterator i$ = extSys.getCompanies().iterator();

            FinanceCompany fc;
            do {
               if(!i$.hasNext()) {
                  return null;
               }

               fc = (FinanceCompany)i$.next();
            } while(!fc.getEntityRef().getPrimaryKey().equals(mappingKey));

            return fc;
         }
      }
   }

   public void setModelVisId(String modelVisId) throws ValidationException {
      if(modelVisId != null && modelVisId.trim().length() != 0) {
         if(modelVisId.length() > 20) {
            throw new ValidationException("The model vis id must be 1 to 20 characters long.");
         } else {
            this.mEditorData.setModelVisId(modelVisId);
            this.setContentModified();
         }
      } else {
         throw new ValidationException("A model vis id must be supplied.");
      }
   }

   public void setModelDescription(String modelDescription) throws ValidationException {
      if(modelDescription != null && modelDescription.trim().length() != 0) {
         if(modelDescription.length() > 128) {
            throw new ValidationException("The model description must be leass than 128 characters");
         } else {
            this.mEditorData.setModelDescription(modelDescription);
            this.setContentModified();
         }
      } else {
         throw new ValidationException("A model description must be supplied.");
      }
   }

   public MappedDimensionEditor getDimensionMappingEditor(Object key) throws ValidationException {
      if(this.mDimensionMappingEditor != null) {
         throw new ValidationException("Only one sub editor allowed.");
      } else {
         MappedDimensionImpl mapping = this.findDimensionMapping(key);
         if(mapping == null) {
            if(key != null) {
               throw new ValidationException("Unable to locate dimension mapping with key:" + key);
            }

            mapping = new MappedDimensionImpl(new MappedDimensionPK(--this.mNextKey), this.mEditorData);
         }

         this.mDimensionMappingEditor = new MappedDimensionEditorImpl(this.getBusinessSession(), this, mapping);
         return this.mDimensionMappingEditor;
      }
   }

   public void removeMappedDimension(Object key) throws ValidationException {
      MappedDimensionImpl mappedDimension = this.findDimensionMapping(key);
      if(mappedDimension == null) {
         throw new ValidationException("Unable to find mapped dimension with key:" + key);
      } else if(!this.mEditorData.isNew()) {
         throw new ValidationException("A dimension cannot be removed from a model mapping once created.");
      } else {
         this.mEditorData.getDimensionMappings().remove(mappedDimension);
         this.setContentModified();
      }
   }

   public void removeSubBusinessEditor(SubBusinessEditor editor) throws CPException {
      if(editor instanceof MappedDimensionEditor) {
         this.mDimensionMappingEditor = null;
      }

   }

   void updateMappedDimension(MappedDimensionImpl newItem) {
      MappedDimensionImpl orig = this.findDimensionMapping(newItem.getKey());
      if(orig != null) {
         orig.setDimensionDescription(newItem.getDimensionDescription());
         orig.setDimensionRef(newItem.getDimension());
         orig.setDimensionVisId(newItem.getDimensionVisId());
         orig.setDimensionType(newItem.getDimensionType());
         orig.setMappedHierarchies(newItem.getMappedHierarchies());
         orig.setMappedDimensionElements(newItem.getMappedDimensionElements());
         orig.setMappedModel(newItem.getModelMapping());
         orig.setPathVisId(newItem.getPathVisId());
         orig.setNullDimensionElementVisId(newItem.getNullDimensionElementVisId());
         orig.setNullDimensionElementDescription(newItem.getNullDimensionElementDescription());
         orig.setNullDimensionElementCreditDebit(newItem.getNullDimensionElementCreditDebit());
         orig.setDisabledLeafNodesExcluded(newItem.isDisabledLeafNodesExcluded());
      } else {
         this.mEditorData.getDimensionMappings().add(newItem);
      }

      if(newItem.isResponsibilityAreaDimension()) {
         Iterator i$ = this.mEditorData.getDimensionMappings().iterator();

         while(i$.hasNext()) {
            MappedDimension md = (MappedDimension)i$.next();
            if(!md.getKey().equals(newItem.getKey())) {
               ((MappedDimensionImpl)md).resetResponsibilityAreaHierarchyFlag();
            }
         }
      }

      this.setContentModified();
   }

   private MappedDimensionImpl findDimensionMapping(Object key) {
      Iterator i$ = this.mEditorData.getDimensionMappings().iterator();

      MappedDimension mapping;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         mapping = (MappedDimension)i$.next();
      } while(mapping.getKey() == null || !mapping.getKey().equals(key));

      return (MappedDimensionImpl)mapping;
   }

   public MappedCalendarEditor getMappedCalendarEditor() throws ValidationException {
      FinanceCompany fc = this.getFinanceCompany();
      this.mMappedCalendarEditor = new MappedCalendarEditorImpl((BusinessSessionImpl)this.getBusinessSession(), (MappedCalendarImpl)this.mEditorData.getMappedCalendar(), fc);
      return this.mMappedCalendarEditor;
   }

   public MappedFinanceCubeEditor getMappedFinanceCubeEditor(Object key) throws ValidationException {
      MappedFinanceCubeImpl mfc;
      if(key != null) {
         mfc = (MappedFinanceCubeImpl)this.mEditorData.findMappedFinanceCube(key);
         if(mfc == null) {
            throw new ValidationException("Failed to locate mapped finance cube with key:" + key);
         }
      } else {
         mfc = new MappedFinanceCubeImpl(this.mEditorData);
         mfc.setKey(new MappedFinanceCubePK(this.mEditorData.getNextKey()));
      }

      return new MappedFinanceCubeEditorImpl(this.getBusinessSession(), this, mfc);
   }

   public void update(MappedFinanceCubeImpl updatedFinanceCube) {
      MappedFinanceCubeImpl origFinanceCube = (MappedFinanceCubeImpl)this.mEditorData.findMappedFinanceCube(updatedFinanceCube.getKey());
      if(origFinanceCube != null) {
         int index = this.mEditorData.getMappedFinanceCubes().indexOf(origFinanceCube);
         this.mEditorData.getMappedFinanceCubes().remove(origFinanceCube);
         this.mEditorData.getMappedFinanceCubes().add(index, updatedFinanceCube);
      } else {
         this.mEditorData.getMappedFinanceCubes().add(updatedFinanceCube);
      }

      this.setContentModified();
   }

   public void removeMappedFinanceCube(Object key) throws ValidationException {
      MappedFinanceCube mfc = this.mEditorData.findMappedFinanceCube(key);
      if(mfc == null) {
         throw new ValidationException("Failed to locate mapped finance cube with key:" + key);
      } else if(!mfc.isNew()) {
         throw new ValidationException("Mappings to finance cubes which have been created cannot be removed.");
      } else {
         this.mEditorData.getMappedFinanceCubes().remove(mfc);
         this.setContentModified();
      }
   }

   public FinanceLedger getFinanceLedger() {
      FinanceCompany fc = this.getFinanceCompany();
      if(fc == null) {
         return null;
      } else if(this.mEditorData.getFinanceLedgerKey() == null) {
         return null;
      } else {
         Iterator i$ = fc.getFinanceLedgers().iterator();

         FinanceLedger fl;
         do {
            if(!i$.hasNext()) {
               return null;
            }

            fl = (FinanceLedger)i$.next();
         } while(!fl.getEntityRef().getPrimaryKey().equals(this.mEditorData.getFinanceLedgerKey()));

         return fl;
      }
   }

   public FinanceLedger findFinanceLedger(MappingKey key) {
      FinanceCompany fc = this.getFinanceCompany();
      if(fc == null) {
         return null;
      } else {
         Iterator i$ = fc.getFinanceLedgers().iterator();

         FinanceLedger fl;
         do {
            if(!i$.hasNext()) {
               return null;
            }

            fl = (FinanceLedger)i$.next();
         } while(!fl.getEntityRef().getPrimaryKey().equals(key));

         return fl;
      }
   }

   public int getNextKey() {
      return this.mEditorData.getNextKey();
   }

   public String getXMLSummary() throws IOException {
      CharArrayWriter writer = new CharArrayWriter();
      this.mEditorData.writeXml(writer);
      return writer.toString();
   }

   public void setFinanceCompanyMappingKey(MappingKey key) throws ValidationException {
      if(key instanceof MappingKeyImpl) {
         MappingKeyImpl mk = (MappingKeyImpl)key;
         this.mEditorData.setCompanyVisId((String)mk.get(0));
      } else {
         throw new ValidationException("Invalid mapping key type key:" + key);
      }
   }

   public void setFinanceLedgerMappingKey(MappingKey key) throws ValidationException {
      if(key instanceof MappingKeyImpl) {
         MappingKeyImpl mk = (MappingKeyImpl)key;
         this.mEditorData.setLedgerVisId((String)mk.get(0));
      } else {
         throw new ValidationException("Invalid mapping key key:" + key);
      }
   }

   public MappedModelImpl getMappedModelImpl() {
      return this.mEditorData;
   }
}
