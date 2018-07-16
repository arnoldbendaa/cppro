// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:10
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.datatype;

import com.cedar.cp.api.datatype.DataType;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.dto.datatype.DataTypeImpl;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.impl.datatype.DataTypeEditorSessionImpl;
import java.util.List;

public class DataTypeAdapter implements DataType {

   private DataTypeImpl mEditorData;
   private DataTypeEditorSessionImpl mEditorSessionImpl;


   public DataTypeAdapter(DataTypeEditorSessionImpl e, DataTypeImpl editorData) {
      this.mEditorData = editorData;
      this.mEditorSessionImpl = e;
   }

   public void setPrimaryKey(Object key) {
      this.mEditorData.setPrimaryKey(key);
   }

   protected DataTypeEditorSessionImpl getEditorSessionImpl() {
      return this.mEditorSessionImpl;
   }

   protected DataTypeImpl getEditorData() {
      return this.mEditorData;
   }

   public Object getPrimaryKey() {
      return this.mEditorData.getPrimaryKey();
   }

   void setPrimaryKey(DataTypePK paramKey) {
      this.mEditorData.setPrimaryKey(paramKey);
   }

   public String getVisId() {
      return this.mEditorData.getVisId();
   }

   public String getDescription() {
      return this.mEditorData.getDescription();
   }

   public boolean isReadOnlyFlag() {
      return this.mEditorData.isReadOnlyFlag();
   }

   public boolean isAvailableForImport() {
      return this.mEditorData.isAvailableForImport();
   }

   public boolean isAvailableForExport() {
      return this.mEditorData.isAvailableForExport();
   }

   public int getSubType() {
      return this.mEditorData.getSubType();
   }

   public String getFormulaExpr() {
      return this.mEditorData.getFormulaExpr();
   }

   public Integer getMeasureClass() {
      return this.mEditorData.getMeasureClass();
   }

   public Integer getMeasureLength() {
      return this.mEditorData.getMeasureLength();
   }

   public Integer getMeasureScale() {
      return this.mEditorData.getMeasureScale();
   }

   public String getMeasureValidation() {
      return this.mEditorData.getMeasureValidation();
   }

   public void setVisId(String p) {
      this.mEditorData.setVisId(p);
   }

   public void setDescription(String p) {
      this.mEditorData.setDescription(p);
   }

   public void setReadOnlyFlag(boolean p) {
      this.mEditorData.setReadOnlyFlag(p);
   }

   public void setAvailableForImport(boolean p) {
      this.mEditorData.setAvailableForImport(p);
   }

   public void setAvailableForExport(boolean p) {
      this.mEditorData.setAvailableForExport(p);
   }

   public void setSubType(int p) {
      this.mEditorData.setSubType(p);
   }

   public void setFormulaExpr(String p) {
      this.mEditorData.setFormulaExpr(p);
   }

   public void setMeasureClass(Integer p) {
      this.mEditorData.setMeasureClass(p);
   }

   public void setMeasureLength(Integer p) {
      this.mEditorData.setMeasureLength(p);
   }

   public void setMeasureScale(Integer p) {
      this.mEditorData.setMeasureScale(p);
   }

   public void setMeasureValidation(String p) {
      this.mEditorData.setMeasureValidation(p);
   }

   public List getDataTypeRefs() {
      return this.mEditorData.getDataTypeRefs();
   }

   public DataTypeRef getDataTypeRef() {
      return this.mEditorData.getDataTypeRef();
   }

   public boolean propagatesInDimension(int dimIndex) throws IllegalStateException {
      return this.mEditorData.propagatesInDimension(dimIndex);
   }

   public boolean isFinanceValue() {
      return this.mEditorData.isFinanceValue();
   }

   public boolean isMeasure() {
      return this.mEditorData.isMeasure();
   }

   public boolean isMeasureNumeric() {
      return this.mEditorData.isMeasureNumeric();
   }

   public boolean isMeasureString() {
      return this.mEditorData.isMeasureString();
   }

   public boolean isMeasureBoolean() {
      return this.mEditorData.isMeasureBoolean();
   }

   public boolean isMeasureDate() {
      return this.mEditorData.isMeasureDate();
   }

   public boolean isMeasureDateTime() {
      return this.mEditorData.isMeasureDateTime();
   }

   public boolean isMeasureTime() {
      return this.mEditorData.isMeasureTime();
   }
}
