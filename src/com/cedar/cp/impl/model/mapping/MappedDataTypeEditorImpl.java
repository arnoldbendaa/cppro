// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:14
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.model.mapping;

import com.cedar.cp.api.base.BusinessSession;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.model.mapping.MappedCalendarYear;
import com.cedar.cp.api.model.mapping.MappedDataType;
import com.cedar.cp.api.model.mapping.MappedDataTypeEditor;
import com.cedar.cp.dto.model.mapping.MappedDataTypeImpl;
import com.cedar.cp.impl.base.SubBusinessEditorImpl;
import com.cedar.cp.impl.base.SubBusinessEditorOwner;
import com.cedar.cp.impl.model.mapping.MappedFinanceCubeEditorImpl;
import java.util.List;

public class MappedDataTypeEditorImpl extends SubBusinessEditorImpl implements MappedDataTypeEditor {

   private MappedDataTypeImpl mMappedDataType;


   public MappedDataTypeEditorImpl(BusinessSession sess, SubBusinessEditorOwner owner, MappedDataTypeImpl mappedDataType) {
      super(sess, owner);

      try {
         this.mMappedDataType = (MappedDataTypeImpl)mappedDataType.clone();
      } catch (CloneNotSupportedException var5) {
         throw new IllegalStateException("Failed to clone MappedDataTypeImpl", var5);
      }
   }

   public MappedDataType getMappedDataType() {
      return this.mMappedDataType;
   }

   public List<DataType> getAvailableDataTypes() {
      return this.getMappedFinanceCubeEditor().getAvailableDataTypes(this.getMappedDataType().getKey());
   }

   public void setDataTypeRef(DataTypeRef dataTypeRef) {
      if(this.fieldHasChanged(dataTypeRef, this.mMappedDataType.getDataTypeRef())) {
         this.mMappedDataType.setDataTypeRef(dataTypeRef);
         this.setContentModified();
      }

   }

   public void setExtSysValueType(String valueType) throws ValidationException {
      if(this.fieldHasChanged(valueType, this.mMappedDataType.getExtSysValueType())) {
         this.validateFieldPresent(valueType);
         this.mMappedDataType.setExtSysValueType(valueType);
         this.setContentModified();
      }

   }

   public void setExtSysCurrency(String curreny) {
      if(this.fieldHasChanged(curreny, this.mMappedDataType.getExtSysCurrency())) {
         this.mMappedDataType.setExtSysCurrency(curreny);
         this.setContentModified();
      }

   }

   public void setExtSysBalType(String balType) {
      if(this.fieldHasChanged(balType, this.mMappedDataType.getExtSysBalType())) {
         this.mMappedDataType.setExtSysBalType(balType);
         this.setContentModified();
      }

   }

   public void setImpExpState(int state) throws ValidationException {
      if(this.fieldHasChanged(Integer.valueOf(state), Integer.valueOf(this.mMappedDataType.getImpExpStatus()))) {
         this.mMappedDataType.setImpExpStatus(state);
         switch(state) {
         case 0:
            this.mMappedDataType.setExpStartYearOffset((Integer)null);
            this.mMappedDataType.setExpEndYearOffset((Integer)null);
            if(this.mMappedDataType.getImpStartYear() == null) {
               this.setDefaultImportDates();
            }
            break;
         case 1:
            this.mMappedDataType.setImpStartYearOffset((Integer)null);
            this.mMappedDataType.setImpEndYearOffset((Integer)null);
            if(this.mMappedDataType.getExpStartYear() == null) {
               this.setDefaultExportDates();
            }
            break;
         case 2:
            if(this.mMappedDataType.getImpStartYear() == null) {
               this.setDefaultImportDates();
            }

            if(this.mMappedDataType.getExpStartYear() == null) {
               this.setDefaultExportDates();
            }
         }

         this.setContentModified();
      }

   }

   private void setDefaultExportDates() throws ValidationException {
      List years = this.getMappedCalendarYears();
      this.setExpYearRange((MappedCalendarYear)years.get(0), (MappedCalendarYear)years.get(years.size() - 1));
   }

   private void setDefaultImportDates() throws ValidationException {
      List years = this.getMappedCalendarYears();
      this.setImpYearRange((MappedCalendarYear)years.get(0), (MappedCalendarYear)years.get(years.size() - 1));
   }

   private MappedFinanceCubeEditorImpl getMappedFinanceCubeEditor() {
      return (MappedFinanceCubeEditorImpl)this.getOwner();
   }

   protected void saveModifications() throws ValidationException {
      if(this.isContentModified()) {
         switch(this.mMappedDataType.getImpExpStatus()) {
         case 0:
            this.mMappedDataType.setExpStartYearOffset((Integer)null);
            this.mMappedDataType.setExpEndYearOffset((Integer)null);
            break;
         case 1:
            this.mMappedDataType.setImpStartYearOffset((Integer)null);
            this.mMappedDataType.setImpEndYearOffset((Integer)null);
         }

         this.getMappedFinanceCubeEditor().update(this.mMappedDataType);
      }

   }

   protected void undoModifications() throws CPException {}

   public List<MappedCalendarYear> getMappedCalendarYears() {
      return this.getMappedFinanceCubeEditor().getMappedModelEditor().getMappedModel().getMappedCalendar().getMappedCalendarYears();
   }

   public void setImpYearRange(MappedCalendarYear startYear, MappedCalendarYear endYear) throws ValidationException {
      List years = this.getMappedCalendarYears();
      int startOffset = startYear != null?years.indexOf(startYear) + 1 - years.size():years.size() - 1;
      int endOffset = endYear != null?years.indexOf(endYear) + 1 - years.size():0;
      if(startOffset > endOffset) {
         throw new ValidationException("Start year must be before or the same as end year");
      } else {
         if(this.fieldHasChanged(Integer.valueOf(startOffset), this.mMappedDataType.getImpStartYearOffset())) {
            this.mMappedDataType.setImpStartYearOffset(Integer.valueOf(startOffset));
            this.setContentModified();
         }

         if(this.fieldHasChanged(Integer.valueOf(endOffset), this.mMappedDataType.getImpEndYearOffset())) {
            this.mMappedDataType.setImpEndYearOffset(Integer.valueOf(endOffset));
            this.setContentModified();
         }

      }
   }

   public void setExpYearRange(MappedCalendarYear startYear, MappedCalendarYear endYear) throws ValidationException {
      List years = this.getMappedCalendarYears();
      int startOffset = startYear != null?years.indexOf(startYear) + 1 - years.size():years.size() - 1;
      int endOffset = endYear != null?years.indexOf(endYear) + 1 - years.size():0;
      if(startOffset > endOffset) {
         throw new ValidationException("Start year must be before or the same as end year");
      } else {
         if(this.fieldHasChanged(Integer.valueOf(startOffset), this.mMappedDataType.getExpStartYearOffset())) {
            this.mMappedDataType.setExpStartYearOffset(Integer.valueOf(startOffset));
            this.setContentModified();
         }

         if(this.fieldHasChanged(Integer.valueOf(endOffset), this.mMappedDataType.getExpEndYearOffset())) {
            this.mMappedDataType.setExpEndYearOffset(Integer.valueOf(endOffset));
            this.setContentModified();
         }

      }
   }
}
