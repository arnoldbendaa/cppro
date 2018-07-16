// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:05:19
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.impl.udeflookup;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.udeflookup.UdefColumn;
import com.cedar.cp.api.udeflookup.UdefLookup;
import com.cedar.cp.api.udeflookup.UdefLookupEditor;
import com.cedar.cp.dto.udeflookup.UdefColumnImpl;
import com.cedar.cp.dto.udeflookup.UdefLookupEditorSessionSSO;
import com.cedar.cp.dto.udeflookup.UdefLookupImpl;
import com.cedar.cp.impl.base.BusinessEditorImpl;
import com.cedar.cp.impl.udeflookup.UdefLookupAdapter;
import com.cedar.cp.impl.udeflookup.UdefLookupEditorSessionImpl;
import com.cedar.cp.util.StringUtils;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class UdefLookupEditorImpl extends BusinessEditorImpl implements UdefLookupEditor {

   private UdefLookupEditorSessionSSO mServerSessionData;
   private UdefLookupImpl mEditorData;
   private UdefLookupAdapter mEditorDataAdapter;


   public UdefLookupEditorImpl(UdefLookupEditorSessionImpl session, UdefLookupEditorSessionSSO serverSessionData, UdefLookupImpl editorData) {
      super(session);
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void updateEditorData(UdefLookupEditorSessionSSO serverSessionData, UdefLookupImpl editorData) {
      this.mServerSessionData = serverSessionData;
      this.mEditorData = editorData;
   }

   public void setAutoSubmit(boolean newAutoSubmit) throws ValidationException {
      this.validateAutoSubmit(newAutoSubmit);
      if(this.mEditorData.isAutoSubmit() != newAutoSubmit) {
         this.setContentModified();
         this.mEditorData.setAutoSubmit(newAutoSubmit);
      }
   }

   public void setScenario(boolean newScenario) throws ValidationException {
      this.validateScenario(newScenario);
      if(this.mEditorData.isScenario() != newScenario) {
         this.setContentModified();
         this.mEditorData.setScenario(newScenario);
      }
   }

   public void setTimeLvl(int newTimeLvl) throws ValidationException {
      this.validateTimeLvl(newTimeLvl);
      if(this.mEditorData.getTimeLvl() != newTimeLvl) {
         this.setContentModified();
         this.mEditorData.setTimeLvl(newTimeLvl);
      }
   }

   public void setYearStartMonth(int newYearStartMonth) throws ValidationException {
      this.validateYearStartMonth(newYearStartMonth);
      if(this.mEditorData.getYearStartMonth() != newYearStartMonth) {
         this.setContentModified();
         this.mEditorData.setYearStartMonth(newYearStartMonth);
      }
   }

   public void setTimeRange(boolean newTimeRange) throws ValidationException {
      this.validateTimeRange(newTimeRange);
      if(this.mEditorData.isTimeRange() != newTimeRange) {
         this.setContentModified();
         this.mEditorData.setTimeRange(newTimeRange);
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

   public void setGenTableName(String newGenTableName) throws ValidationException {
      if(newGenTableName != null) {
         newGenTableName = StringUtils.rtrim(newGenTableName);
      }

      this.validateGenTableName(newGenTableName);
      if(this.mEditorData.getGenTableName() == null || !this.mEditorData.getGenTableName().equals(newGenTableName)) {
         this.setContentModified();
         this.mEditorData.setGenTableName(newGenTableName);
      }
   }

   public void setLastSubmit(Timestamp newLastSubmit) throws ValidationException {
      this.validateLastSubmit(newLastSubmit);
      if(this.mEditorData.getLastSubmit() == null || !this.mEditorData.getLastSubmit().equals(newLastSubmit)) {
         this.setContentModified();
         this.mEditorData.setLastSubmit(newLastSubmit);
      }
   }

   public void setDataUpdated(Timestamp newDataUpdated) throws ValidationException {
      this.validateDataUpdated(newDataUpdated);
      if(this.mEditorData.getDataUpdated() == null || !this.mEditorData.getDataUpdated().equals(newDataUpdated)) {
         this.setContentModified();
         this.mEditorData.setDataUpdated(newDataUpdated);
      }
   }

   public void validateVisId(String newVisId) throws ValidationException {
      if(newVisId != null && newVisId.length() > 27) {
         throw new ValidationException("length (" + newVisId.length() + ") of VisId must not exceed 27 on a UdefLookup");
      }
   }

   public void validateDescription(String newDescription) throws ValidationException {
      if(newDescription != null && newDescription.length() > 256) {
         throw new ValidationException("length (" + newDescription.length() + ") of Description must not exceed 256 on a UdefLookup");
      }
   }

   public void validateGenTableName(String newGenTableName) throws ValidationException {
      if(newGenTableName != null && newGenTableName.length() > 30) {
         throw new ValidationException("length (" + newGenTableName.length() + ") of GenTableName must not exceed 30 on a UdefLookup");
      }
   }

   public void validateAutoSubmit(boolean newAutoSubmit) throws ValidationException {}

   public void validateScenario(boolean newScenario) throws ValidationException {}

   public void validateTimeLvl(int newTimeLvl) throws ValidationException {}

   public void validateYearStartMonth(int newYearStartMonth) throws ValidationException {}

   public void validateTimeRange(boolean newTimeRange) throws ValidationException {}

   public void validateLastSubmit(Timestamp newLastSubmit) throws ValidationException {}

   public void validateDataUpdated(Timestamp newDataUpdated) throws ValidationException {}

   public UdefLookup getUdefLookup() {
      if(this.mEditorDataAdapter == null) {
         this.mEditorDataAdapter = new UdefLookupAdapter((UdefLookupEditorSessionImpl)this.getBusinessSession(), this.mEditorData);
      }

      return this.mEditorDataAdapter;
   }

   public void saveModifications() throws ValidationException {
      this.saveValidation();
   }

   private void saveValidation() throws ValidationException {
      Iterator iter = this.mEditorData.getColumnDef().iterator();
      HashSet colNames = new HashSet();
      if(this.mEditorData.getColumnDef().size() < 1) {
         throw new ValidationException("At least 1 column must be specified");
      } else {
         UdefColumn col;
         do {
            if(!iter.hasNext()) {
               return;
            }

            col = (UdefColumn)iter.next();
            if(col.getName() == null || col.getName().length() == 0) {
               throw new ValidationException("Column Name is a required Field");
            }

            if(col.getType() == 0) {
               if(col.getSize() == null || col.getSize().intValue() < 1) {
                  throw new ValidationException(col.getName() + " - String: size must be > 0");
               }

               if(col.getSize().intValue() > 3999) {
                  throw new ValidationException(col.getName() + " - String: max size is 3999");
               }

               if(col.getDP() != null) {
                  throw new ValidationException(col.getName() + " - String: DP column must be empty");
               }
            }

            if(col.getType() == 1) {
               if(col.getSize() != null) {
                  throw new ValidationException(col.getName() + " - Number: Size column must be empty");
               }

               if(col.getDP() == null || col.getDP().intValue() < 0 || col.getDP().intValue() > 18) {
                  throw new ValidationException(col.getName() + " - Number: DP column must be between 0 and 18");
               }
            }

            if(col.getType() == 2) {
               if(col.getSize() != null) {
                  throw new ValidationException(col.getName() + " - Boolean: Size column must be empty");
               }

               if(col.getDP() != null) {
                  throw new ValidationException(col.getName() + " - Boolean: DP column must be empty");
               }
            }

            if(col.getType() == 3) {
               if(col.getSize() != null) {
                  throw new ValidationException(col.getName() + " - Date: Size column must be empty");
               }

               if(col.getDP() != null) {
                  throw new ValidationException(col.getName() + " - Date: DP column must be empty");
               }
            }
         } while(colNames.add(col.getName()));

         throw new ValidationException("Duplicate Column Name : " + col.getName());
      }
   }

   public void addColumnDef() {
      UdefColumnImpl col = new UdefColumnImpl();
      col.setState(1);
      this.mEditorData.getColumnDef().add(col);
      this.setContentModified();
   }

   public void removeColumnDef(int[] rows) {
      int[] arr$ = rows;
      int len$ = rows.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         int row = arr$[i$];
         UdefColumn col = (UdefColumn)this.mEditorData.getColumnDef().get(row);
         if(col.getKey() != null) {
            this.mEditorData.addRemoveKey(col);
         }

         this.mEditorData.getColumnDef().remove(row);
      }

      this.setContentModified();
   }

   public void addDataRow() {
      Object[] row = new Object[this.mEditorData.getColumnDef().size()];
      this.mEditorData.getTableData().add(row);
      this.setContentModified();
   }

   public void setTableData(List data) {
      this.mEditorData.setTableData(data);
      this.setContentModified();
   }

   public void removeDataRow(int[] rows) {
      int[] arr$ = rows;
      int len$ = rows.length;

      for(int i$ = 0; i$ < len$; ++i$) {
         int row = arr$[i$];
         this.mEditorData.getTableData().remove(row);
         this.setContentModified();
      }

   }

   public void dataChanged() {
      this.setContentModified();
   }
}
