// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcRowUpdate$1;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcUpdateType;
import com.cedar.cp.util.GeneralUtils;
import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.inputs.DefaultFormDataInputModel;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CellCalcRowUpdate implements Serializable {

   private CellCalcUpdateType mUpdateType;
   private Integer mIndex;
   private Map<String, String> mAddress = new HashMap();
   private Map<String, String> mValues = new HashMap();


   public CellCalcUpdateType getUpdateType() {
      return this.mUpdateType;
   }

   public void setUpdateType(CellCalcUpdateType updateType) {
      this.mUpdateType = updateType;
   }

   public Map<String, String> getAddress() {
      return this.mAddress;
   }

   public void setAddress(Map<String, String> address) {
      this.mAddress = address;
   }

   public Map<String, String> getValues() {
      return this.mValues;
   }

   public void setValues(Map<String, String> values) {
      this.mValues = values;
   }

   public Integer getIndex() {
      return this.mIndex;
   }

   public void setIndex(Integer index) {
      this.mIndex = index;
   }

   public void applyUpdate(FormConfig formConfig, DefaultFormDataInputModel formModel) throws ValidationException {
      switch(CellCalcRowUpdate$1.$SwitchMap$com$cedar$cp$ejb$impl$model$cc$imp$CellCalcUpdateType[this.mUpdateType.ordinal()]) {
      case 1:
         this.insert(formConfig, formModel);
         break;
      case 2:
         this.merge(formConfig, formModel);
         break;
      case 3:
         this.replace(formConfig, formModel);
         break;
      case 4:
         this.update(formConfig, formModel);
         break;
      case 5:
         this.delete(formConfig, formModel);
      }

   }

   private void delete(FormConfig formConfig, DefaultFormDataInputModel formModel) throws ValidationException {
      List targetRows = this.findRows(formModel);
      if(targetRows.isEmpty()) {
         throw new ValidationException("No row data found on DELETE for address:" + this.getAddressInCVSFormat());
      } else {
         Iterator i$ = targetRows.iterator();

         while(i$.hasNext()) {
            int i = ((Integer)i$.next()).intValue();
            formModel.deleteRow(i);
         }

      }
   }

   private void update(FormConfig formConfig, DefaultFormDataInputModel formModel) throws ValidationException {
      List targetRows = this.findRows(formModel);
      if(targetRows.isEmpty()) {
         throw new ValidationException("No row data found on UPDATE for address:" + this.getAddressInCVSFormat());
      } else {
         Iterator i$ = targetRows.iterator();

         while(i$.hasNext()) {
            int i = ((Integer)i$.next()).intValue();
            this.applyValuesToRow(formModel, i);
         }

      }
   }

   private void replace(FormConfig formConfig, DefaultFormDataInputModel formModel) throws ValidationException {
      List targetRows = this.findRows(formModel);
      if(targetRows.isEmpty()) {
         this.insert(formConfig, formModel);
      } else {
         Iterator i$ = targetRows.iterator();

         while(i$.hasNext()) {
            int i = ((Integer)i$.next()).intValue();
            this.deleteNonAddressValues(formConfig, formModel, i);
            this.applyValuesToRow(formModel, i);
         }
      }

   }

   private void deleteNonAddressValues(FormConfig formConfig, DefaultFormDataInputModel formModel, int row) {
      for(int columnIndex = 0; columnIndex < formModel.getColumnCount(); ++columnIndex) {
         String columnId = formModel.getColumnId(columnIndex);
         if(!this.getAddress().keySet().contains(columnId)) {
            formModel.setValueAt((Object)null, row, columnIndex);
         }
      }

   }

   private void merge(FormConfig formConfig, DefaultFormDataInputModel formModel) throws ValidationException {
      List targetRows = this.findRows(formModel);
      if(targetRows.isEmpty()) {
         this.insert(formConfig, formModel);
      } else {
         Iterator i$ = targetRows.iterator();

         while(i$.hasNext()) {
            int i = ((Integer)i$.next()).intValue();
            this.applyValuesToRow(formModel, i);
         }
      }

   }

   private List<Integer> findRows(DefaultFormDataInputModel formModel) throws ValidationException {
      ArrayList result = new ArrayList();

      for(int i = 0; i < formModel.getRowCount(); ++i) {
         if(this.rowEqual(formModel, i)) {
            result.add(Integer.valueOf(i));
         }
      }

      return result;
   }

   private boolean rowEqual(DefaultFormDataInputModel formModel, int row) throws ValidationException {
      if(this.mIndex != null) {
         return row == this.mIndex.intValue();
      } else if(this.mAddress.isEmpty()) {
         return false;
      } else {
         Iterator i$ = this.mAddress.entrySet().iterator();

         Object addressValue;
         Object rowValue;
         do {
            if(!i$.hasNext()) {
               return true;
            }

            Entry entry = (Entry)i$.next();
            String id = (String)entry.getKey();
            String valueString = (String)entry.getValue();
            Column column = formModel.getColumnMapping(id);
            if(column == null) {
               throw new ValidationException("Column id [" + id + "] not found.");
            }

            if(column.getType().equalsIgnoreCase("string")) {
               addressValue = valueString;
            } else {
               addressValue = BigDecimal.valueOf(Double.parseDouble(valueString));
            }

            rowValue = formModel.getValueAt(row, formModel.getColumnIndex(column));
         } while(!GeneralUtils.isDifferent(addressValue, rowValue));

         return false;
      }
   }

   private void applyValuesToRow(DefaultFormDataInputModel formModel, int targetRow) throws ValidationException {
      Column column;
      Object value;
      for(Iterator i$ = this.mValues.entrySet().iterator(); i$.hasNext(); formModel.setValueAt(value, targetRow, formModel.getColumnIndex(column))) {
         Entry entry = (Entry)i$.next();
         String id = (String)entry.getKey();
         String valueString = (String)entry.getValue();
         column = formModel.getColumnMapping(id);
         if(column == null) {
            throw new ValidationException("Column id [" + id + " not found.");
         }

         value = null;
         if(column.getType().equalsIgnoreCase("string")) {
            value = valueString;
         } else if(valueString != null) {
            value = BigDecimal.valueOf(Double.parseDouble(valueString));
         }
      }

   }

   private void insert(FormConfig formConfig, DefaultFormDataInputModel formModel) throws ValidationException {
      int newRowIndex = formModel.addRow();

      Iterator i$;
      Entry entry;
      String id;
      String valueString;
      Column column;
      Object value;
      for(i$ = this.mAddress.entrySet().iterator(); i$.hasNext(); formModel.setValueAt(value, newRowIndex, formModel.getColumnIndex(column))) {
         entry = (Entry)i$.next();
         id = (String)entry.getKey();
         valueString = (String)entry.getValue();
         column = formModel.getColumnMapping(id);
         if(column == null) {
            throw new ValidationException("Column id [" + id + "] not found.");
         }

         value = null;
         if(column.getType().equalsIgnoreCase("string")) {
            value = valueString;
         } else if(valueString != null) {
            value = BigDecimal.valueOf(Double.parseDouble(valueString));
         }
      }

      for(i$ = this.mValues.entrySet().iterator(); i$.hasNext(); formModel.setValueAt(value, newRowIndex, formModel.getColumnIndex(column))) {
         entry = (Entry)i$.next();
         id = (String)entry.getKey();
         valueString = (String)entry.getValue();
         column = formModel.getColumnMapping(id);
         if(column == null) {
            throw new ValidationException("Column id [" + id + "] not found.");
         }

         value = null;
         if(column.getType().equalsIgnoreCase("string")) {
            value = valueString;
         } else if(valueString != null) {
            value = BigDecimal.valueOf(Double.parseDouble(valueString));
         }
      }

   }

   public String getAddressInCVSFormat() {
      StringBuilder sb = new StringBuilder();
      if(this.mIndex != null) {
         sb.append("rowIndex:").append(this.mIndex);
      } else {
         Iterator i$ = this.getAddress().entrySet().iterator();

         while(i$.hasNext()) {
            Entry entry = (Entry)i$.next();
            sb.append((String)entry.getKey());
            sb.append("=");
            sb.append((String)entry.getValue());
            sb.append(' ');
         }
      }

      return sb.toString();
   }
}
