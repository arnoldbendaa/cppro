package com.cedar.cp.impl.model.globalmapping2;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.SubBusinessEditor;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarYear;
import com.cedar.cp.api.model.globalmapping2.MappedDataType;
import com.cedar.cp.api.model.globalmapping2.MappedDataTypeEditor;
import com.cedar.cp.api.model.globalmapping2.MappedFinanceCube;
import com.cedar.cp.api.model.globalmapping2.MappedFinanceCubeEditor;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany;
import com.cedar.cp.dto.datatype.DataTypeImpl;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.datatype.DataTypesForImpExpELO;
import com.cedar.cp.dto.model.globalmapping2.MappedDataTypeImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedDataTypePK;
import com.cedar.cp.dto.model.globalmapping2.MappedFinanceCubeImpl;
import com.cedar.cp.ejb.api.base.ListSessionServer;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.model.globalmapping2.MappedDataTypeEditorImpl;
import com.cedar.cp.impl.model.globalmapping2.MappedFinanceCubeEditorImpl$1;
import com.cedar.cp.impl.model.globalmapping2.GlobalMappedModel2EditorImpl;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MappedFinanceCubeEditorImpl extends SubBusinessEditorImpl implements MappedFinanceCubeEditor, SubBusinessEditorOwner {

   private MappedFinanceCubeImpl mMappedFinanceCube;


   public MappedFinanceCubeEditorImpl(BusinessSession sess, GlobalMappedModel2EditorImpl owner, MappedFinanceCubeImpl mappedFinanceCube) {
      super(sess, owner);

      try {
         this.mMappedFinanceCube = (MappedFinanceCubeImpl)mappedFinanceCube.clone();
      } catch (Exception var5) {
         throw new IllegalStateException("Failed to clone MappedFinanceCubeImpl", var5);
      }
   }

   public void setVisId(String visId) throws ValidationException {
      if(this.fieldHasChanged(visId, this.mMappedFinanceCube.getName())) {
         this.validateFieldPresent(visId);
         this.mMappedFinanceCube.setName(visId);
         this.setContentModified();
      }
   }

   public void setDescription(String description) throws ValidationException {
      if(this.fieldHasChanged(description, this.mMappedFinanceCube.getDescription())) {
         this.validateFieldPresent(description);
         this.mMappedFinanceCube.setDescription(description);
         this.setContentModified();
      }
   }

   public void setCompany(String companyVisId) throws ValidationException {
	   if(this.fieldHasChanged(companyVisId, this.mMappedFinanceCube.getFinanceCompany())) {
		   this.mMappedFinanceCube.setFinanceCompany(companyVisId);
		   this.setContentModified();
	   }
   }

   public void deleteMappedDataType(Object key) throws ValidationException {
      Iterator i$ = this.mMappedFinanceCube.getMappedDataTypes().iterator();

      MappedDataType mdt;
      do {
         if(!i$.hasNext()) {
            throw new ValidationException("Unable to locate mapped data type with key:" + key);
         }

         mdt = (MappedDataType)i$.next();
      } while(!mdt.getKey().equals(key));

      this.mMappedFinanceCube.getMappedDataTypes().remove(mdt);
      this.setContentModified();
   }

   public MappedDataTypeEditor getMappedDataTypeEditor(Object key) throws ValidationException {
      MappedDataTypeImpl mdt;
      if(key != null) {
         mdt = (MappedDataTypeImpl)this.mMappedFinanceCube.findMappedDataType(key);
         if(mdt == null) {
            throw new ValidationException("Unable to locate mapped data type with key:" + key);
         }
      } else {
         mdt = new MappedDataTypeImpl((MappedFinanceCubeImpl)this.getMappedFinanceCube());
         mdt.setKey(new MappedDataTypePK(this.getMappedModelEditor().getNextKey()));
      }

      return new MappedDataTypeEditorImpl(this.getBusinessSession(), this, mdt);
   }

   public MappedFinanceCube getMappedFinanceCube() {
      return this.mMappedFinanceCube;
   }

   public GlobalMappedModel2EditorImpl getMappedModelEditor() {
      return (GlobalMappedModel2EditorImpl)this.getOwner();
   }

   public List<DataType> getAvailableDataTypes(Object key) {
      HashMap dataTypes = new HashMap();
      ListSessionServer lss = new ListSessionServer(this.getConnection());
      DataTypesForImpExpELO allImpExpDataTypes = lss.getDataTypesForImpExp();

      for(int result = 0; result < allImpExpDataTypes.getNumRows(); ++result) {
         DataTypeRefImpl mdt = (DataTypeRefImpl)allImpExpDataTypes.getValueAt(result, "DataType");
         DataTypeImpl dt = new DataTypeImpl(mdt.getDataTypePK());
         dt.setAvailableForImport(((Boolean)allImpExpDataTypes.getValueAt(result, "AvailableForImport")).booleanValue());
         dt.setAvailableForExport(((Boolean)allImpExpDataTypes.getValueAt(result, "AvailableForExport")).booleanValue());
         dt.setSubType(((Integer)allImpExpDataTypes.getValueAt(result, "SubType")).intValue());
         dt.setFormulaExpr((String)allImpExpDataTypes.getValueAt(result, "FormulaExpr"));
         dt.setDescription((String)allImpExpDataTypes.getValueAt(result, "Description"));
         dt.setVisId((String)allImpExpDataTypes.getValueAt(result, "VisId"));
         dt.setReadOnlyFlag(((Boolean)allImpExpDataTypes.getValueAt(result, "ReadOnlyFlag")).booleanValue());
         dataTypes.put(dt.getVisId(), dt);
      }

      Iterator var9 = this.getMappedFinanceCube().getMappedDataTypes().iterator();

      while(var9.hasNext()) {
         MappedDataType var10 = (MappedDataType)var9.next();
         if(key == null || !var10.getKey().equals(key)) {
            dataTypes.remove(var10.getDataTypeRef().getNarrative());
         }
      }

      ArrayList var8 = new ArrayList();
      var8.addAll(dataTypes.values());
      Collections.sort(var8, new MappedFinanceCubeEditorImpl$1(this));
      return var8;
   }

   void update(MappedDataTypeImpl updatedDataType) {
      MappedDataTypeImpl origDataType = (MappedDataTypeImpl)this.mMappedFinanceCube.findMappedDataType(updatedDataType.getKey());
      if(origDataType != null) {
         int index = this.mMappedFinanceCube.getMappedDataTypes().indexOf(origDataType);
         this.mMappedFinanceCube.getMappedDataTypes().remove(origDataType);
         this.mMappedFinanceCube.getMappedDataTypes().add(index, updatedDataType);
      } else {
         this.mMappedFinanceCube.getMappedDataTypes().add(updatedDataType);
      }

      this.setContentModified();
   }

   protected void saveModifications() throws ValidationException {
      if(this.isNew() || this.isContentModified()) {
         if(this.isFieldBlank(this.getMappedFinanceCube().getName())) {
            throw new ValidationException("A visual id must be supplied for the mapped finance cube");
         }

         if(this.isFieldBlank(this.getMappedFinanceCube().getDescription())) {
            throw new ValidationException("A description must be supplied for the mapped finance cube");
         }

         if(this.getMappedFinanceCube().getMappedDataTypes().isEmpty()) {
            throw new ValidationException("At least one data type must also be mapped");
         }

         this.getMappedModelEditor().update(this.mMappedFinanceCube);
      }

   }

   public List<MappedCalendarYear> getMappedCalendarYears() {
      return this.getMappedModelEditor().getMappedModel().getMappedCalendar().getMappedCalendarYears();
   }

   private boolean isNew() {
      return this.mMappedFinanceCube.isNew();
   }

   public void removeSubBusinessEditor(SubBusinessEditor editor) throws CPException {}

   protected void undoModifications() throws CPException {}
}
