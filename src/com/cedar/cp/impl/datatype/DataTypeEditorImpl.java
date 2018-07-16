// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.datatype;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.api.datatype.DataTypeEditor;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.dto.datatype.DataTypeEditorSessionSSO;
import com.cedar.cp.dto.datatype.DataTypeImpl;
import com.cedar.cp.dto.datatype.VirtualExprParser;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.datatype.DataTypeAdapter;
import com.cedar.cp.impl.datatype.DataTypeEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import com.cedar.cp.util.i18nUtils;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class DataTypeEditorImpl extends BusinessEditorImpl implements DataTypeEditor {

   private EntityList mAvailableDataTypes;
   private DataTypeEditorSessionSSO mServerSessionData;
   private DataTypeImpl mEditorData;
   private DataTypeAdapter mEditorDataAdapter;


   public DataTypeEditorImpl(DataTypeEditorSessionImpl session, DataTypeEditorSessionSSO serverSessionData, DataTypeImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(DataTypeEditorSessionSSO serverSessionData, DataTypeImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setReadOnlyFlag(boolean newReadOnlyFlag) throws ValidationException {
      this.validateReadOnlyFlag(newReadOnlyFlag);
      if(this.mEditorData.isReadOnlyFlag() != newReadOnlyFlag) {
         this.setContentModified();
         this.mEditorData.setReadOnlyFlag(newReadOnlyFlag);
      }
   }

   public void setAvailableForImport(boolean newAvailableForImport) throws ValidationException {
      this.validateAvailableForImport(newAvailableForImport);
      if(this.mEditorData.isAvailableForImport() != newAvailableForImport) {
         this.setContentModified();
         this.mEditorData.setAvailableForImport(newAvailableForImport);
      }
   }

   public void setAvailableForExport(boolean newAvailableForExport) throws ValidationException {
      this.validateAvailableForExport(newAvailableForExport);
      if(this.mEditorData.isAvailableForExport() != newAvailableForExport) {
         this.setContentModified();
         this.mEditorData.setAvailableForExport(newAvailableForExport);
      }
   }

   public void setSubType(int newSubType) throws ValidationException {
      this.validateSubType(newSubType);
      if(this.mEditorData.getSubType() != newSubType) {
         if(newSubType == 3) {
            this.mEditorData.setReadOnlyFlag(true);
            this.mEditorData.setAvailableForExport(true);
            this.mEditorData.setAvailableForImport(false);
         } else if(newSubType == 4) {
            this.setMeasureClass(Integer.valueOf(0));
         }

         this.setContentModified();
         this.mEditorData.setSubType(newSubType);
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

   public void setFormulaExpr(String newFormulaExpr) throws ValidationException {
      if(newFormulaExpr != null) {
         newFormulaExpr = StringUtils.rtrim(newFormulaExpr);
      }

      this.validateFormulaExpr(newFormulaExpr);
      if(this.mEditorData.getFormulaExpr() == null || !this.mEditorData.getFormulaExpr().equals(newFormulaExpr)) {
         this.setContentModified();
         this.mEditorData.setFormulaExpr(newFormulaExpr);
      }
   }

   public void setMeasureClass(Integer newMeasureClass) throws ValidationException {
      this.validateMeasureClass(newMeasureClass);
      if(this.mEditorData.getMeasureClass() == null || !this.mEditorData.getMeasureClass().equals(newMeasureClass)) {
         if(this.mEditorData.isDeployed()) {
            throw new ValidationException("A measure cannot change its class once it has been deployed to a finance cube");
         } else {
            this.setDefaultMeasureValues(newMeasureClass);
            this.setContentModified();
            this.mEditorData.setMeasureClass(newMeasureClass);
         }
      }
   }

   public void setMeasureLength(Integer newMeasureLength) throws ValidationException {
      this.validateMeasureLength(newMeasureLength);
      if(this.mEditorData.getMeasureLength() == null || !this.mEditorData.getMeasureLength().equals(newMeasureLength)) {
         this.setContentModified();
         this.mEditorData.setMeasureLength(newMeasureLength);
      }
   }

   public void setMeasureScale(Integer newMeasureScale) throws ValidationException {
      this.validateMeasureScale(newMeasureScale);
      if(this.mEditorData.getMeasureScale() == null || !this.mEditorData.getMeasureScale().equals(newMeasureScale)) {
         this.setContentModified();
         this.mEditorData.setMeasureScale(newMeasureScale);
      }
   }

   public void setMeasureValidation(String newMeasureValidation) throws ValidationException {
      if(newMeasureValidation != null) {
         newMeasureValidation = StringUtils.rtrim(newMeasureValidation);
      }

      this.validateMeasureValidation(newMeasureValidation);
      if(this.mEditorData.getMeasureValidation() == null || !this.mEditorData.getMeasureValidation().equals(newMeasureValidation)) {
         this.setContentModified();
         this.mEditorData.setMeasureValidation(newMeasureValidation);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 2) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 2 on a DataType");
      } else if(newVisId.length() != 2) {
         throw new ValidationException("VisId must be 2 characters");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 128) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 128 on a DataType");
      }
   }

   public void validateReadOnlyFlag(boolean newReadOnlyFlag) throws ValidationException {
      if(this.mEditorData.getSubType() == 3 && !newReadOnlyFlag) {
         throw new ValidationException("Virtual Data Types must be read only.");
      }
   }

   public void validateAvailableForImport(boolean newAvailableForImport) throws ValidationException {
      if(this.mEditorData.getSubType() == 3 && newAvailableForImport) {
         throw new ValidationException("Virtual Data Types cannot be imported.");
      } else if(this.mEditorData.getSubType() == 4 && newAvailableForImport && this.mEditorData.getMeasureClass().intValue() != 1) {
         throw new ValidationException("Non-numeric measures are not available for import via the normal FMS Import/Export module.");
      }
   }

   public void validateAvailableForExport(boolean newAvailableForExport) throws ValidationException {
      if(this.mEditorData.getSubType() == 4 && newAvailableForExport && this.mEditorData.getMeasureClass().intValue() != 1) {
         throw new ValidationException("Non-numeric measures are not available for export via the normal FMS Import/Export module.");
      }
   }

   public void validateSubType(int newSubType) throws ValidationException {
      if(this.mEditorData.isDeployed() && this.mEditorData.getSubType() != newSubType && (this.mEditorData.getSubType() == 3 || newSubType == 3)) {
         throw new ValidationException("A datatype may not be change from/to virtual type once deployed.");
      } else if(this.mEditorData.isDeployed() && this.mEditorData.getSubType() != newSubType && (this.mEditorData.getSubType() == 4 || newSubType == 4)) {
         throw new ValidationException("A datatype may not be changed from/to a measure type once deployed.");
      } else if(newSubType != 0 && newSubType != 2 && newSubType != 1 && newSubType != 3 && newSubType != 4) {
         throw new ValidationException("Invalid sub type:" + newSubType);
      }
   }

   public void validateFormulaExpr(String newFormulaExpr) throws ValidationException {
      if(newFormulaExpr != null && newFormulaExpr.length() > 2000) {
         throw new ValidationException("length (" + newFormulaExpr.length() + ") of FormulaExpr must not exceed 2000 on a DataType");
      } else {
         if(newFormulaExpr != null && newFormulaExpr.trim().length() > 0) {
            VirtualExprParser parser = new VirtualExprParser(newFormulaExpr);
            Iterator i = parser.getIDs().iterator();

            while(i.hasNext()) {
               String token = (String)i.next();
               if(token.length() != 2) {
                  throw new ValidationException("Data type ref must be two characters in length");
               }

               this.validateDataTypeRef(token);
            }
         }

      }
   }

   public void validateMeasureClass(Integer newMeasureClass) throws ValidationException {
      if(newMeasureClass != null) {
         switch(newMeasureClass.intValue()) {
         case 0:
         case 1:
         case 2:
         case 3:
         case 4:
         case 5:
            break;
         default:
            throw new ValidationException("Unexpected measure class:" + newMeasureClass);
         }
      }

   }

   public void validateMeasureLength(Integer newMeasureLength) throws ValidationException {
      if(this.mEditorData.isDeployed()) {
         throw new ValidationException("A measure data type cannot change its length once deployed.");
      }
   }

   public void validateMeasureScale(Integer newMeasureScale) throws ValidationException {
      if(this.mEditorData.isDeployed()) {
         throw new ValidationException("A measure data type cannot change its scale once deployed.");
      } else {
         Integer testObj = new Integer(newMeasureScale.intValue());
         if(!i18nUtils.getDataTypeScaleMapping().getValues().contains(testObj)) {
            throw new ValidationException("Scale new value is invalid");
         }
      }
   }

   public void validateMeasureValidation(String newMeasureValidation) throws ValidationException {
      if(newMeasureValidation != null && newMeasureValidation.length() > 2048) {
         throw new ValidationException("length (" + newMeasureValidation.length() + ") of MeasureValidation must not exceed 2048 on a DataType");
      } else {
         if(newMeasureValidation != null && newMeasureValidation.trim().length() > 0) {
            try {
               Pattern.compile(newMeasureValidation);
            } catch (PatternSyntaxException var3) {
               throw new ValidationException("Invalid regular expression:" + var3.getMessage());
            }
         }

      }
   }

   public DataType getDataType() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new DataTypeAdapter((DataTypeEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {
      if(this.mEditorData.getSubType() == 3 && (this.mEditorData.getFormulaExpr() == null || this.mEditorData.getFormulaExpr().trim().length() == 0)) {
         throw new ValidationException("A formula expression must be supplied for virtual data types.");
      } else {
         if(this.mEditorData.getSubType() == 4) {
            if(this.mEditorData.getMeasureClass() == null) {
               throw new ValidationException("A measure class must be defined for a measure data type.");
            }

            switch(this.mEditorData.getMeasureClass().intValue()) {
            case 0:
               if(this.mEditorData.getMeasureLength() == null) {
                  throw new ValidationException("A string class measure data type must have its max length defined.");
               }
               break;
            case 1:
               if(this.mEditorData.getMeasureScale() == null) {
                  throw new ValidationException("A numeric class measure data type must have its scale defined.");
               }
            }
         }

      }
   }

   private void validateDataTypeRef(String name) throws ValidationException {
      if(this.mAvailableDataTypes == null) {
         this.mAvailableDataTypes = this.getConnection().getListHelper().getAllDataTypes();
      }

      for(int i = 0; i < this.mAvailableDataTypes.getNumRows(); ++i) {
         DataTypeRef dtf = (DataTypeRef)this.mAvailableDataTypes.getValueAt(i, "DataType");
         if(name.equals(dtf.getNarrative())) {
            if(dtf.getSubType() == 3) {
               throw new ValidationException("A virtual data type may only refer to non-virtual data types. " + name + " is a virtual data type.");
            }

            return;
         }
      }

      throw new ValidationException("Unknown data type reference :" + name);
   }

   private void setDefaultMeasureValues(Integer measureClass) {
      if(measureClass != null) {
         switch(measureClass.intValue()) {
         case 0:
            this.mEditorData.setMeasureLength(Integer.valueOf(20));
            this.mEditorData.setMeasureScale((Integer)null);
            break;
         case 1:
            this.mEditorData.setMeasureLength(Integer.valueOf(18));
            this.mEditorData.setMeasureScale(Integer.valueOf(2));
            this.mEditorData.setMeasureValidation((String)null);
            break;
         case 2:
         case 3:
         case 4:
         case 5:
            this.mEditorData.setMeasureLength((Integer)null);
            this.mEditorData.setMeasureScale((Integer)null);
            this.mEditorData.setMeasureValidation((String)null);
         }

         this.mEditorData.setAvailableForExport(false);
         this.mEditorData.setAvailableForImport(false);
      } else {
         this.mEditorData.setMeasureLength((Integer)null);
         this.mEditorData.setMeasureScale((Integer)null);
         this.mEditorData.setMeasureValidation((String)null);
      }

   }
}
