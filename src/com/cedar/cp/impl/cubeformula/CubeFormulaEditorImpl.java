// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.cubeformula;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.cubeformula.CubeFormula;
import com.cedar.cp.api.cubeformula.CubeFormulaEditor;
import com.cedar.cp.api.cubeformula.FormulaDeploymentLineEditor;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.cubeformula.CubeFormulaCK;
import com.cedar.cp.dto.cubeformula.CubeFormulaEditorSessionSSO;
import com.cedar.cp.dto.cubeformula.CubeFormulaImpl;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentLineImpl;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentLinePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.cubeformula.CubeFormulaAdapter;
import com.cedar.cp.impl.cubeformula.CubeFormulaEditorSessionImpl;
import com.cedar.cp.impl.cubeformula.FormulaDeploymentLineEditorImpl;
import com.cedar.cp.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CubeFormulaEditorImpl extends BusinessEditorImpl implements CubeFormulaEditor, SubBusinessEditorOwner {

   private List<DataTypeRef> mAvailableDataTypes;
   private int mNextId = -1;
   private CubeFormulaEditorSessionSSO mServerSessionData;
   private CubeFormulaImpl mEditorData;
   private CubeFormulaAdapter mEditorDataAdapter;


   public CubeFormulaEditorImpl(CubeFormulaEditorSessionImpl session, CubeFormulaEditorSessionSSO serverSessionData, CubeFormulaImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(CubeFormulaEditorSessionSSO serverSessionData, CubeFormulaImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setDeploymentInd(boolean newDeploymentInd) throws ValidationException {
      this.validateDeploymentInd(newDeploymentInd);
      if(this.mEditorData.isDeploymentInd() != newDeploymentInd) {
         this.setContentModified();
         this.mEditorData.setDeploymentInd(newDeploymentInd);
      }
   }

   public void setFormulaType(int newFormulaType) throws ValidationException {
      this.validateFormulaType(newFormulaType);
      if(this.mEditorData.getFormulaType() != newFormulaType) {
         this.setContentModified();
         this.mEditorData.setFormulaType(newFormulaType);
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

   public void setFormulaText(String newFormulaText) throws ValidationException {
      if(newFormulaText != null) {
         newFormulaText = StringUtils.rtrim(newFormulaText);
      }

      this.validateFormulaText(newFormulaText);
      if(this.mEditorData.getFormulaText() == null || !this.mEditorData.getFormulaText().equals(newFormulaText)) {
         this.setContentModified();
         this.mEditorData.setFormulaText(newFormulaText);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a CubeFormula");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a CubeFormula");
      }
   }

   public void validateFormulaText(String newFormulaText) throws ValidationException {}

   public void validateDeploymentInd(boolean newDeploymentInd) throws ValidationException {}

   public void validateFormulaType(int newFormulaType) throws ValidationException {}

   public void setFinanceCubeRef(FinanceCubeRef ref) throws ValidationException {
      FinanceCubeRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getFinanceCubeEntityRef(ref);
         } catch (Exception var5) {
            throw new ValidationException(var5.getMessage());
         }
      }

      if(this.mEditorData.getFinanceCubeRef() == null) {
         if(actualRef == null) {
            return;
         }
      } else if(actualRef != null && this.mEditorData.getFinanceCubeRef().getPrimaryKey().equals(actualRef.getPrimaryKey())) {
         return;
      }

      this.mEditorData.setFinanceCubeRef(actualRef);
      this.setContentModified();
      if(actualRef != null) {
         FinanceCubeRefImpl fcRefImpl = (FinanceCubeRefImpl)actualRef;
         EntityList cubeDetails = this.getConnection().getFinanceCubesProcess().getFinanceCubeDetails(fcRefImpl.getFinanceCubePK().getFinanceCubeId());
         if(cubeDetails.getNumRows() != 1) {
            throw new IllegalStateException("Unable to lookup finance cube details for:" + actualRef);
         }

         this.mEditorData.setFinanceCubeFormulaEnabled(((Boolean)cubeDetails.getValueAt(0, "CubeFormulaEnabled")).booleanValue());
      }

   }

   public void setModelRef(ModelRef ref) throws ValidationException {
      ModelRef actualRef = ref;
      if(ref != null) {
         try {
            actualRef = this.getConnection().getListHelper().getModelEntityRef(ref);
         } catch (Exception var7) {
            throw new ValidationException(var7.getMessage());
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
      int modelId = ((ModelRefImpl)this.mEditorData.getModelRef()).getModelPK().getModelId();
      EntityList dimensions = this.getConnection().getModelsProcess().getModelDimensions(modelId);
      ArrayList dimensionRefs = new ArrayList();

      for(int i = 0; i < dimensions.getNumRows(); ++i) {
         dimensionRefs.add((DimensionRef)dimensions.getValueAt(i, "Dimension"));
      }

      this.mEditorData.setDimensionRefs((DimensionRef[])dimensionRefs.toArray(new DimensionRef[0]));
   }

   public EntityList getOwnershipRefs() {
      return ((CubeFormulaEditorSessionImpl)this.getBusinessSession()).getOwnershipRefs();
   }

   public CubeFormula getCubeFormula() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new CubeFormulaAdapter((CubeFormulaEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {
      if(!this.mEditorData.isFinanceCubeFormulaEnabled() && this.mEditorData.isDeploymentInd()) {
         throw new ValidationException("Cube formula are disabled at the finance cube level. \nThe formula must be saved with \'deployed\' set to false.");
      }
   }

   public void saveDeploymentLine(FormulaDeploymentLineImpl line) {
      if(line.getLineIndex() < 0) {
         line.setLineIndex(this.mEditorData.getDeploymentLines().size());
         this.mEditorData.getDeploymentLines().add(line);
      } else {
         this.mEditorData.getDeploymentLines().set(line.getLineIndex(), line);
      }

      this.setContentModified();
   }

   public List<DataTypeRef> getAvailableDataTypes() {
      if(this.mAvailableDataTypes == null) {
         FinanceCubeRefImpl financeCubeRef = (FinanceCubeRefImpl)this.mEditorData.getFinanceCubeRef();
         if(financeCubeRef == null) {
            return Collections.emptyList();
         }

         EntityList dataTypes = this.getConnection().getListHelper().getAllDataTypesForFinanceCube(financeCubeRef.getFinanceCubePK().getFinanceCubeId());
         this.mAvailableDataTypes = new ArrayList();

         for(int i = 0; i < dataTypes.getNumRows(); ++i) {
            DataTypeRef dataTypeRef = (DataTypeRef)dataTypes.getValueAt(i, "DataType");
            boolean readOnly = ((Boolean)dataTypes.getValueAt(i, "ReadOnlyFlag")).booleanValue();
            int subtype = ((Integer)dataTypes.getValueAt(i, "SubType")).intValue();
            if(subtype == 0 && !readOnly) {
               this.mAvailableDataTypes.add(dataTypeRef);
            }
         }
      }

      return this.mAvailableDataTypes;
   }

   public FormulaDeploymentLineEditor getFormulaDeploymentLineEditor(Object key) throws ValidationException {
      FormulaDeploymentLineImpl line;
      if(key == null) {
         line = new FormulaDeploymentLineImpl(this.mEditorData.getDimensionRefs());
         line.setKey(new FormulaDeploymentLinePK(this.getNextId()));
      } else {
         line = (FormulaDeploymentLineImpl)this.mEditorData.getFormulaDeploymentLine(key);
      }

      if(line == null) {
         throw new ValidationException("Unable to locate deployment line for key:" + key);
      } else {
         return new FormulaDeploymentLineEditorImpl(this.getBusinessSession(), this, line);
      }
   }

   public void removeFormulaDeploymentLine(Object key) throws ValidationException {
      if(this.mEditorData.removeFormulaDeploymentLine(key)) {
         this.setContentModified();
      }

   }

   public void removeSubBusinessEditor(SubBusinessEditor editor) throws CPException {}

   public DimensionRef[] getDimensionRefs() {
      return this.mEditorData.getDimensionRefs();
   }

   public String testCompile() throws ValidationException {
      int financeCubeId = ((FinanceCubeRefImpl)this.mEditorData.getFinanceCubeRef()).getFinanceCubePK().getFinanceCubeId();
      int cubeFormulaId = this.mEditorData.getPrimaryKey() != null?((CubeFormulaCK)this.mEditorData.getPrimaryKey()).getCubeFormulaPK().getCubeFormulaId():0;
      String formulaText = this.mEditorData.getFormulaText();
      int formulaType = this.mEditorData.getFormulaType();
      this.getCubeFormulaEditorSession().testCompileFormula(financeCubeId, cubeFormulaId, formulaText, formulaType);
      return "Test compilation successful.";
   }

   private CubeFormulaEditorSessionImpl getCubeFormulaEditorSession() {
      return (CubeFormulaEditorSessionImpl)this.getBusinessSession();
   }

   private int getNextId() {
      return this.mNextId--;
   }
}
