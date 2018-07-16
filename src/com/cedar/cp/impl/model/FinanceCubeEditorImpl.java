// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:13
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.base.CPConnection.ConnectionContext;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.FinanceCube;
import com.cedar.cp.api.model.FinanceCubeEditor;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.RollUpRuleLine;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.model.FinanceCubeEditorSessionSSO;
import com.cedar.cp.dto.model.FinanceCubeImpl;
import com.cedar.cp.dto.model.RollUpRuleImpl;
import com.cedar.cp.dto.model.RollUpRuleLineImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.model.FinanceCubeAdapter;
import com.cedar.cp.impl.model.FinanceCubeEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.sql.Timestamp;
import java.util.ArrayList;

public class FinanceCubeEditorImpl extends BusinessEditorImpl implements FinanceCubeEditor {

   private FinanceCubeEditorSessionSSO mServerSessionData;
   private FinanceCubeImpl mEditorData;
   private FinanceCubeAdapter mEditorDataAdapter;


   public FinanceCubeEditorImpl(FinanceCubeEditorSessionImpl session, FinanceCubeEditorSessionSSO serverSessionData, FinanceCubeImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
      if(this.getConnection().getConnectionContext() == ConnectionContext.CHANGE_MANAGEMENT) {
         this.mEditorData.setInsideChangeManagement(true);
      }

   }

   public void updateEditorData(FinanceCubeEditorSessionSSO serverSessionData, FinanceCubeImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setHasData(boolean newHasData) throws ValidationException {
      this.validateHasData(newHasData);
      if(this.mEditorData.isHasData() != newHasData) {
         this.setContentModified();
         this.mEditorData.setHasData(newHasData);
      }
   }

   public void setAudited(boolean newAudited) throws ValidationException {
      this.validateAudited(newAudited);
      if(this.mEditorData.isAudited() != newAudited) {
         this.setContentModified();
         this.mEditorData.setAudited(newAudited);
      }
   }

   public void setCubeFormulaEnabled(boolean newCubeFormulaEnabled) throws ValidationException {
      this.validateCubeFormulaEnabled(newCubeFormulaEnabled);
      if(this.mEditorData.isCubeFormulaEnabled() != newCubeFormulaEnabled) {
         this.setContentModified();
         this.mEditorData.setCubeFormulaEnabled(newCubeFormulaEnabled);
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

   public void setLockedByTaskId(Integer newLockedByTaskId) throws ValidationException {
      this.validateLockedByTaskId(newLockedByTaskId);
      if(this.mEditorData.getLockedByTaskId() == null || !this.mEditorData.getLockedByTaskId().equals(newLockedByTaskId)) {
         this.setContentModified();
         this.mEditorData.setLockedByTaskId(newLockedByTaskId);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 55) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 55 on a FinanceCube");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a FinanceCube");
      }
   }

   public void validateLockedByTaskId(Integer newLockedByTaskId) throws ValidationException {}

   public void validateHasData(boolean newHasData) throws ValidationException {}

   public void validateAudited(boolean newAudited) throws ValidationException {}

   public void validateCubeFormulaEnabled(boolean newCubeFormulaEnabled) throws ValidationException {}

   public void setModelRef(ModelRef ref) throws ValidationException {
      ModelRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getModelEntityRef(ref);
         } catch (Exception var4) {
            throw new ValidationException(var4.getMessage());
         }
      }

      if(this.mEditorData.getModelRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getModelRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setModelRef(actualRef);
      this.setContentModified();
   }

   public EntityList getOwnershipRefs() {
      return ((FinanceCubeEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public FinanceCube getFinanceCube() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new FinanceCubeAdapter((FinanceCubeEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {
      if(this.mEditorData.getSelectedDataTypeRefs().size() == 0) {
         throw new ValidationException("FinaceCube must contain at least one datatype");
      }
   }

   public void addSelectedDataTypeRef(DataTypeRef datatypeRef) throws ValidationException {
      if(!this.mEditorData.isInsideChangeManagement() && this.mEditorData.isChangeManagementOutstanding()) {
         throw new ValidationException("Change managemt requests are pending for this model.");
      } else {
         if(!this.mEditorData.getSelectedDataTypeRefs().containsKey(datatypeRef)) {
            this.mEditorData.addSelectedDataTypeRef(datatypeRef, (Timestamp)null);
            if(datatypeRef.allowsConfigrableRollUp()) {
               this.getFinanceCube();
               DimensionRef[] dimensions = this.mEditorDataAdapter.getDimensions();
               ArrayList rollUpRules = new ArrayList();
               RollUpRuleLineImpl rurLine = new RollUpRuleLineImpl(this.mEditorData, (DataTypeRefImpl)datatypeRef, rollUpRules);

               for(int i = 0; i < dimensions.length; ++i) {
                  rollUpRules.add(new RollUpRuleImpl(rurLine, (DimensionRefImpl)dimensions[i], false));
               }

               this.mEditorData.addRollUpRuleLine(rurLine);
            }

            this.setContentModified();
         }

      }
   }

   public void removeSelectedDataTypeRef(DataTypeRef datatypeRef) throws ValidationException {
      if(!this.mEditorData.isInsideChangeManagement() && this.mEditorData.isChangeManagementOutstanding()) {
         throw new ValidationException("Change managemt requests are pending for this model.");
      } else if(this.mEditorData.isMappedDataType(datatypeRef)) {
         throw new ValidationException("Data Type " + datatypeRef.getNarrative() + " is used in a mapped model.");
      } else if(this.mEditorData.isAggregatedDataType(datatypeRef)) {
         throw new ValidationException("Data Type " + datatypeRef.getNarrative() + " is used in an aggregated model.");
      } else {
         if(datatypeRef.allowsConfigrableRollUp()) {
            RollUpRuleLine rurl = this.mEditorData.getRollUpRuleLine(datatypeRef);
            if(rurl != null) {
               this.mEditorData.removeRollUpRuleLine((RollUpRuleLineImpl)rurl);
            }
         }

         this.mEditorData.removeSelectedDatatypeRef(datatypeRef);
         this.setContentModified();
      }
   }

   public void addCellNote(int cellNoteId) {}

   public void setRollUpRule(DataTypeRef dataType, DimensionRef dimension, boolean value) throws ValidationException {
      if(!this.mEditorData.isInsideChangeManagement() && this.mEditorData.isChangeManagementOutstanding()) {
         throw new ValidationException("Change managemt requests are pending for this model.");
      } else {
         RollUpRuleLine line = this.mEditorData.getRollUpRuleLine(dataType);
         if(line == null) {
            throw new ValidationException("Unable to locate roll up rule line for data type;" + dataType);
         } else {
            RollUpRuleLineImpl lineImpl = (RollUpRuleLineImpl)line;
            RollUpRuleImpl rur = (RollUpRuleImpl)lineImpl.getRollUpRule(dimension);
            if(rur == null) {
               throw new ValidationException("Unable to locate roll up rule for data type:" + dataType + " and dimension:" + dimension);
            } else {
               if(rur.isRollUp() != value) {
                  rur.setRollUp(value);
                  this.setContentModified();
               }

            }
         }
      }
   }

   public void setInsideChangeManagement(boolean b) {
      this.mEditorData.setInsideChangeManagement(b);
   }

   public void setSubmitChangeManagement(boolean b) {
      this.mEditorData.setSubmitChangeManagement(b);
   }
}
