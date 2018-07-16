// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:20
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.xmlform;

import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.api.base.Destroyable;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.StructureElementKey;
import com.cedar.cp.api.dimension.StructureElementNode;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.udeflookup.UdefLookup;
import com.cedar.cp.api.xmlform.CellPickerInfo;
import com.cedar.cp.api.xmlform.FormDeploymentEditor;
import com.cedar.cp.api.xmlform.XmlForm;
import com.cedar.cp.api.xmlform.XmlFormEditor;
import com.cedar.cp.api.xmlform.XmlFormsProcess;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.StructureElementKeyImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.xmlform.CellPickerInfoImpl;
import com.cedar.cp.dto.xmlform.XmlFormEditorSessionSSO;
import com.cedar.cp.dto.xmlform.XmlFormImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.base.BusinessSessionImpl;
import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.StringUtils;
import com.cedar.cp.util.i18nUtils;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;

public class XmlFormEditorImpl extends BusinessEditorImpl implements XmlFormEditor, Destroyable {

   private FormDeploymentEditorImpl mDeployment;
   private XmlFormEditorSessionSSO mServerSessionData;
   private XmlFormImpl mEditorData;
   private XmlFormAdapter mEditorDataAdapter;

   public void destroy(){
	   mDeployment=null;
	   mServerSessionData=null;
	   mEditorData=null;
	   mEditorDataAdapter=null;
   }
   
   public XmlFormEditorImpl(XmlFormEditorSessionImpl session, XmlFormEditorSessionSSO serverSessionData, XmlFormImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(XmlFormEditorSessionSSO serverSessionData, XmlFormImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setType(int newType) throws ValidationException {
      this.validateType(newType);
      if(this.mEditorData.getType() != newType) {
         this.setContentModified();
         this.mEditorData.setType(newType);
      }
   }

   public void setDesignMode(boolean newDesignMode) throws ValidationException {
      this.validateDesignMode(newDesignMode);
      if(this.mEditorData.isDesignMode() != newDesignMode) {
         this.setContentModified();
         this.mEditorData.setDesignMode(newDesignMode);
      }
   }

   public void setFinanceCubeId(int newFinanceCubeId) throws ValidationException {
      this.validateFinanceCubeId(newFinanceCubeId);
      if(this.mEditorData.getFinanceCubeId() != newFinanceCubeId) {
         this.setContentModified();
         this.mEditorData.setFinanceCubeId(newFinanceCubeId);
      }
   }

   public void setSecurityAccess(boolean newSecurityAccess) throws ValidationException {
      this.validateSecurityAccess(newSecurityAccess);
      if(this.mEditorData.isSecurityAccess() != newSecurityAccess) {
         this.setContentModified();
         this.mEditorData.setSecurityAccess(newSecurityAccess);
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

   public void setDefinition(String newDefinition) throws ValidationException {
      if(newDefinition != null) {
         newDefinition = StringUtils.rtrim(newDefinition);
      }

      this.validateDefinition(newDefinition);
      if(this.mEditorData.getDefinition() == null || !this.mEditorData.getDefinition().equals(newDefinition)) {
         this.setContentModified();
         this.mEditorData.setDefinition(newDefinition);
      }
   }
   
   /**
    * Set excel file
    */
   @Override
   public void setExcelFile(byte[] newExcelFile) throws ValidationException {
      
	  this.validateExcelFile(newExcelFile);
      if(this.mEditorData.getExcelFile() == null || !this.mEditorData.getExcelFile().equals(newExcelFile)) {
         this.setContentModified();
         this.mEditorData.setExcelFile(newExcelFile);
      }
   }
   
   @Override
   public byte[] getExcelFile() {
      return mEditorData.getExcelFile();
   }
   
   /**
    * Set JSON form
    */
   @Override
   public void setJsonForm(String newJsonForm) {
      
      if(this.mEditorData.getJsonForm() == null || !this.mEditorData.getJsonForm().equals(newJsonForm)) {
         this.setContentModified();
         this.mEditorData.setJsonForm(newJsonForm);
      }
   }
   
   @Override
   public String getJsonForm() {
      return mEditorData.getJsonForm();
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 20) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 20 on a XmlForm");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a XmlForm");
      }
   }

   public void validateType(int newType) throws ValidationException {
      Integer testObj = new Integer(newType);
      if(!i18nUtils.getXMLFormTypeMapping().getValues().contains(testObj)) {
         throw new ValidationException("Type new value is invalid");
      }
   }

   public void validateDesignMode(boolean newDesignMode) throws ValidationException {}

   public void validateFinanceCubeId(int newFinanceCubeId) throws ValidationException {}

   public void validateDefinition(String newDefinition) throws ValidationException {}
   
   public void validateExcelFile(byte[] newExcelFile) throws ValidationException {}

   public void validateSecurityAccess(boolean newSecurityAccess) throws ValidationException {}

   public XmlForm getXmlForm() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new XmlFormAdapter((XmlFormEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {
      if(this.mEditorData.getVisId() != null && this.mEditorData.getVisId().trim().length() >= 1) {
         if(this.mEditorData.getDefinition() == null || this.mEditorData.getDefinition().trim().length() < 1) {
            throw new ValidationException("Form definition can not be null");
         }
      } else {
         throw new ValidationException("Identifier can not be empty");
      }
   }

   public FormDeploymentEditor getFormDeploymentEditor() throws ValidationException {
      if(this.mDeployment == null) {
         this.mDeployment = new FormDeploymentEditorImpl((BusinessSessionImpl)this.getBusinessSession(), this.mEditorData.getPrimaryKey(), this.mEditorData.getFinanceCubeId());
      }

      return this.mDeployment;
   }

   public CellPickerInfo getCellPickerInfo() throws ValidationException {
      if(this.mEditorData.getFinanceCubeId() <= 0) {
         throw new ValidationException("A finance cube must be selected");
      } else {
         EntityList financeCubeDetails = this.getConnection().getFinanceCubesProcess().getFinanceCubeDetails(this.mEditorData.getFinanceCubeId());
         if(financeCubeDetails.getNumRows() > 0) {
            return this.getCellPickerInfo((ModelRef)financeCubeDetails.getValueAt(0, "Model"), (FinanceCubeRef)financeCubeDetails.getValueAt(0, "FinanceCube"));
         } else {
            throw new ValidationException("Failed to locate modelref for finance cube id:" + this.mEditorData.getFinanceCubeId());
         }
      }
   }

   public CellPickerInfo getCellPickerInfo(ModelRef modelRef, FinanceCubeRef financeCubeRef) {
      CellPickerInfoImpl cellPickerInfo = new CellPickerInfoImpl();
      int financeCubeId = ((FinanceCubeRefImpl)financeCubeRef).getFinanceCubePK().getFinanceCubeId();
      cellPickerInfo.setModelRef(modelRef);
      EntityList dimAndHierList = this.getConnection().getFinanceCubesProcess().getFinanceCubeAllDimensionsAndHierachies(financeCubeId);
      DimensionRef currentDimensionRef = null;
      HierarchyRef currentHierarchyRef = null;

      for(int dataTypes = 0; dataTypes < dimAndHierList.getNumRows(); ++dataTypes) {
         DimensionRef allDimensions = (DimensionRef)dimAndHierList.getValueAt(dataTypes, "Dimension");
         if(GeneralUtils.isDifferent(currentDimensionRef, allDimensions)) {
            cellPickerInfo.getDimensions().add(allDimensions);
            currentDimensionRef = allDimensions;
         }

         HierarchyRef allHierarchies = (HierarchyRef)dimAndHierList.getValueAt(dataTypes, "Hierarchy");
         if(GeneralUtils.isDifferent(currentHierarchyRef, allHierarchies)) {
            if(cellPickerInfo.getHierarchyMap().get(allDimensions) == null) {
               cellPickerInfo.getHierarchyMap().put(allDimensions, new ArrayList());
            }

            ((List)cellPickerInfo.getHierarchyMap().get(allDimensions)).add(allHierarchies);
            currentHierarchyRef = allHierarchies;
         }
      }

      EntityList var12 = this.getConnection().getListHelper().getAllDataTypesForFinanceCube(financeCubeId);

      for(int var13 = 0; var13 < var12.getNumRows(); ++var13) {
         cellPickerInfo.getDataTypes().add((DataTypeRef)var12.getValueAt(var13, "DataType"));
      }

      EntityList var14 = this.getConnection().getListHelper().getAllDimensions();

      for(int var16 = 0; var16 < var14.getNumRows(); ++var16) {
         cellPickerInfo.getDimensionDescrMap().put((DimensionRef)var14.getValueAt(var16, "Dimension"), (String)var14.getValueAt(var16, "Description"));
      }

      EntityList var15 = this.getConnection().getListHelper().getAllHierarchys();

      for(int i = 0; i < var15.getNumRows(); ++i) {
         cellPickerInfo.getHierarchyDescrMap().put((HierarchyRef)var15.getValueAt(i, "Hierarchy"), (String)var15.getValueAt(i, "Description"));
      }

      return cellPickerInfo;
   }

   public EntityList lookupStructureElement(Object dimensionRef, Object hierarchyRef, String visId, boolean isCal) {
      int hierarchyId = this.coerceStructureId(hierarchyRef);
      if(isCal) {
         CalendarInfo list = this.getConnection().getHierarchysProcess().getCalendarInfo(hierarchyRef);
         CalendarElementNode i = list.findElement(visId);
         if(i != null) {
            ArrayList elements = new ArrayList();
            elements.add(new StructureElementKeyImpl(i.getStructureId(), i.getStructureElementId()));
            return this.getConnection().getHierarchysProcess().queryPathToRoots(elements);
         }
      } else {
         EntityList var9 = this.getConnection().getListHelper().getStructureElementByVisId(visId, this.coerceDimensionId(dimensionRef));

         for(int var10 = 0; var10 < var9.getNumRows(); ++var10) {
            if(this.coerceStructureId(var9.getValueAt(var10, "Hierarchy")) == hierarchyId) {
               return var9.getRowData(var10);
            }
         }
      }

      return null;
   }

   private int coerceDimensionId(Object key) {
      if(key instanceof DimensionRef) {
         key = ((DimensionRef)key).getPrimaryKey();
      }

      if(key instanceof DimensionPK) {
         return ((DimensionPK)key).getDimensionId();
      } else {
         throw new IllegalArgumentException("Unknown dimension key type:" + key);
      }
   }

   private int coerceStructureId(Object sKey) {
      if(sKey instanceof HierarchyRef) {
         sKey = ((HierarchyRef)sKey).getPrimaryKey();
      }

      if(sKey instanceof HierarchyCK) {
         sKey = ((HierarchyCK)sKey).getHierarchyPK();
      }

      if(sKey instanceof HierarchyPK) {
         return ((HierarchyPK)sKey).getHierarchyId();
      } else {
         throw new IllegalArgumentException("Unknown key type for hierarchy:" + sKey);
      }
   }

   private int coerceStructureElementId(Object seKey) {
      if(seKey instanceof StructureElementRef) {
         seKey = ((StructureElementRef)seKey).getPrimaryKey();
      }

      if(seKey instanceof StructureElementPK) {
         return ((StructureElementPK)seKey).getStructureElementId();
      } else if(seKey instanceof StructureElementKey) {
         return ((StructureElementKey)seKey).getId();
      } else if(seKey instanceof StructureElementNode) {
         return ((StructureElementNode)seKey).getStructureElementId();
      } else {
         throw new IllegalArgumentException("Unknown key type for structure element:" + seKey);
      }
   }

   public EntityList queryStructureElements(Object hierarchyKey, Object parentKey, SeQueryType queryType) {
      if(queryType == SeQueryType.IMMEDIATE_CHILDREN) {
         return this.getConnection().getListHelper().getImmediateChildren(this.coerceStructureId(hierarchyKey), this.coerceStructureElementId(parentKey));
      } else if(queryType == SeQueryType.LEAVES) {
         return this.getConnection().getListHelper().getLeavesForParent(this.coerceStructureId(hierarchyKey), this.coerceStructureId(hierarchyKey), this.coerceStructureElementId(parentKey), this.coerceStructureId(hierarchyKey), this.coerceStructureElementId(parentKey));
      } else if(queryType == SeQueryType.CASCADE) {
         return this.getConnection().getListHelper().getChildrenForParent(this.coerceStructureId(hierarchyKey), this.coerceStructureId(hierarchyKey), this.coerceStructureElementId(parentKey), this.coerceStructureId(hierarchyKey), this.coerceStructureElementId(parentKey));
      } else {
         throw new IllegalArgumentException("Unknown seQeuryType:" + queryType);
      }
   }

   public UdefLookup getUdefLookup(String visId) throws ValidationException {
      return ((XmlFormsProcess)this.getBusinessSession().getBusinessProcess()).getUdefLookup(visId);
   }

   public DimensionRef[] getDimensions() {
      if(this.mEditorData.getFinanceCubeId() == 0) {
         return null;
      } else {
         EntityList list = this.getConnection().getListHelper().getFinanceCubeDimensionsAndHierachies(this.mEditorData.getFinanceCubeId());
         ArrayList dimensionRefs = new ArrayList();
         DimensionRef currentDimensionRef = null;

         for(int i = 0; i < list.getNumRows(); ++i) {
            if(currentDimensionRef == null || !list.getValueAt(i, "Dimension").equals(currentDimensionRef)) {
               currentDimensionRef = (DimensionRef)list.getValueAt(i, "Dimension");
               dimensionRefs.add(currentDimensionRef);
            }
         }

         return (DimensionRef[])dimensionRefs.toArray(new DimensionRef[0]);
      }
   }
}
