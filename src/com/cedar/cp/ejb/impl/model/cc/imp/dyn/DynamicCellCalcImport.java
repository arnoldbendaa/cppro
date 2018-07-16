// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:26:41
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.impl.model.cc.imp.dyn;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.ejb.impl.model.cc.imp.CellCalcUpdateType;
import com.cedar.cp.ejb.impl.model.cc.imp.dyn.ColumnMappingEntry;
import com.cedar.cp.util.Pair;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class DynamicCellCalcImport implements Serializable {

   private ModelRefImpl mModelRef;
   private FinanceCubeRefImpl mFinanceCubeRef;
   private BudgetCycleRefImpl mBudgetCycleRef;
   private CalendarInfo mCalendarInfo;
   private DimensionRefImpl[] mDimensionRefs;
   private CellCalcUpdateType mImportType;
   private Set<String> mDeployments = new HashSet();
   private List<Pair<CalendarElementNode, CalendarElementNode>> mCalendarFilter = new ArrayList();
   private Map<String, DimensionRefImpl> mContextColumns = new HashMap();
   private String mContextColumnDataType;
   private Set<String> mCellCalcRowKeys = new HashSet();
   private String mAutoMap;
   private List<String> mImportColumnNames = new ArrayList();
   private Map<String, ColumnMappingEntry> mReverseColumnMapping = new HashMap<String, ColumnMappingEntry>();
   private Map<String, ColumnMappingEntry> mColumnMapping = new HashMap();
   private int mImportGridId;
   private int mNextCellCalcShortId;
   private Set<Pair<Integer, Integer>> mUpdatedCellCalcIds = new HashSet();


   public ModelRefImpl getModelRef() {
      return this.mModelRef;
   }

   public void setModelRef(ModelRefImpl modelRef) {
      this.mModelRef = modelRef;
   }

   public FinanceCubeRefImpl getFinanceCubeRef() {
      return this.mFinanceCubeRef;
   }

   public void setFinanceCubeRef(FinanceCubeRefImpl financeCubeRef) {
      this.mFinanceCubeRef = financeCubeRef;
   }

   public BudgetCycleRefImpl getBudgetCycleRef() {
      return this.mBudgetCycleRef;
   }

   public void setBudgetCycleRef(BudgetCycleRefImpl budgetCycleRef) {
      this.mBudgetCycleRef = budgetCycleRef;
   }

   public CalendarInfo getCalendarInfo() {
      return this.mCalendarInfo;
   }

   public void setCalendarInfo(CalendarInfo calendarInfo) {
      this.mCalendarInfo = calendarInfo;
   }

   public Set<String> getDeployments() {
      return this.mDeployments;
   }

   public void setDeployments(Set<String> deployments) {
      this.mDeployments = deployments;
   }

   public List<Pair<CalendarElementNode, CalendarElementNode>> getCalendarFilter() {
      return this.mCalendarFilter;
   }

   public void setCalendarFilter(List<Pair<CalendarElementNode, CalendarElementNode>> calendarFilter) {
      this.mCalendarFilter = calendarFilter;
   }

   public Map<String, DimensionRefImpl> getContextColumns() {
      return this.mContextColumns;
   }

   public void registerContextColumn(String importColumn, DimensionRefImpl dimensionRef, boolean dataType) {
      if(dataType) {
         this.mContextColumnDataType = importColumn;
      } else {
         this.mContextColumns.put(importColumn, dimensionRef);
      }

   }

   public String getContextColumnDataType() {
      return this.mContextColumnDataType;
   }

   public void setContextColumnDataType(String contextColumnDataType) {
      this.mContextColumnDataType = contextColumnDataType;
   }

   public Set<String> getCellCalcRowKeys() {
      return this.mCellCalcRowKeys;
   }

   public void setCellCalcRowKeys(Set<String> cellCalcRowKeys) {
      this.mCellCalcRowKeys = cellCalcRowKeys;
   }

   public String getAutoMap() {
      return this.mAutoMap;
   }

   public void setAutoMap(String autoMap) {
      this.mAutoMap = autoMap;
   }

   public List<String> getImportColumnNames() {
      return this.mImportColumnNames;
   }

   public void setImportColumnNames(List<String> importColumnNames) {
      this.mImportColumnNames = importColumnNames;
   }

   public CellCalcUpdateType getImportType() {
      return this.mImportType;
   }

   public void setImportType(CellCalcUpdateType importType) {
      this.mImportType = importType;
   }

   public Map<String, ColumnMappingEntry> getColumnMapping() {
      return this.mColumnMapping;
   }

   public void setColumnMapping(Map<String, ColumnMappingEntry> columnMapping) {
      this.mColumnMapping = columnMapping;
   }

   public String getContextColumnForDimension(DimensionRefImpl dimRef) {
      Iterator i$ = this.mContextColumns.entrySet().iterator();

      Entry entry;
      do {
         if(!i$.hasNext()) {
            return null;
         }

         entry = (Entry)i$.next();
      } while(!((DimensionRefImpl)entry.getValue()).equals(dimRef));

      return (String)entry.getKey();
   }

   public int getDataTypeImportColumnIndex() {
      return this.mContextColumnDataType != null?this.mImportColumnNames.indexOf(this.mContextColumnDataType):-1;
   }

   public int[] getContextColumnIndexes() throws ValidationException {
      DimensionRefImpl[] dimRefs = this.getDimensionRefs();
      int[] contextColumnIndexes = new int[dimRefs.length];

      for(int i = 0; i < contextColumnIndexes.length; ++i) {
         String importColumnName = this.getContextColumnForDimension(dimRefs[i]);
         contextColumnIndexes[i] = this.getImportColumnIndex(importColumnName);
         if(contextColumnIndexes[i] == -1) {
            throw new ValidationException("Failed to locate context column:" + importColumnName + ". Check import definition.");
         }
      }

      return contextColumnIndexes;
   }

   public int getImportColumnIndex(String importColumnName) {
      return this.mImportColumnNames.indexOf(importColumnName);
   }

   public DimensionRefImpl[] getDimensionRefs() {
      return this.mDimensionRefs;
   }

   public void setDimensionRefs(DimensionRefImpl[] dimensionRefs) {
      this.mDimensionRefs = dimensionRefs;
   }

   public Map<String, String> getCellCalcRowDataMap(Map<String, String> importRowDataMap) throws ValidationException {
      HashMap result = new HashMap();
      Iterator i$ = importRowDataMap.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         ColumnMappingEntry mappingEntry = (ColumnMappingEntry)this.mColumnMapping.get(entry.getKey());
         if(mappingEntry == null) {
            if(this.mAutoMap.equalsIgnoreCase("all")) {
               result.put(entry.getKey(), entry.getValue());
            }
         } else if(mappingEntry.getCalcColumn() != null) {
            result.put(mappingEntry.getCalcColumn(), entry.getValue());
         }
      }

      return result;
   }

   public Map<String, String> getCellCalcRowAddressMap(Map<String, String> importRowDataMap) throws ValidationException {
      if(this.mCellCalcRowKeys.isEmpty()) {
         return Collections.EMPTY_MAP;
      } else {
         HashMap result = new HashMap();
         Iterator i$ = this.mCellCalcRowKeys.iterator();

         while(i$.hasNext()) {
            String calcKeyFieldName = (String)i$.next();
            String keyValue = (String)importRowDataMap.get(this.getImportColumnName(calcKeyFieldName));
            result.put(calcKeyFieldName, keyValue);
         }

         return result;
      }
   }

   public String getImportColumnName(String cellCalcColumnName) {
      if(this.mReverseColumnMapping.isEmpty()) {
         this.initReverseColumnMapping();
      }

      ColumnMappingEntry mappingEntry = (ColumnMappingEntry)this.mReverseColumnMapping.get(cellCalcColumnName);
      return mappingEntry != null?mappingEntry.getImportColumn():null;
   }

   private void initReverseColumnMapping() {
      this.mReverseColumnMapping.clear();
      Iterator i$ = this.mColumnMapping.entrySet().iterator();

      while(i$.hasNext()) {
         Entry entry = (Entry)i$.next();
         this.mReverseColumnMapping.put(((ColumnMappingEntry)entry.getValue()).getCalcColumn(), (ColumnMappingEntry) entry.getValue());
      }

   }

   public int getImportGridId() {
      return this.mImportGridId;
   }

   public void setImportGridId(int importGridId) {
      this.mImportGridId = importGridId;
   }

   public int getNextCellCalcShortId() {
      return this.mNextCellCalcShortId;
   }

   public void setNextCellCalcShortId(int nextCellCalcShortId) {
      this.mNextCellCalcShortId = nextCellCalcShortId;
   }

   public Set<Pair<Integer, Integer>> getUpdatedCellCalcIds() {
      return this.mUpdatedCellCalcIds;
   }

   public void setUpdatedCellCalcIds(Set<Pair<Integer, Integer>> updatedCellCalcIds) {
      this.mUpdatedCellCalcIds = updatedCellCalcIds;
   }

   public boolean isPreProcessingRequired() {
      Iterator i$ = this.mColumnMapping.values().iterator();

      ColumnMappingEntry entry;
      do {
         if(!i$.hasNext()) {
            return false;
         }

         entry = (ColumnMappingEntry)i$.next();
      } while(entry.getConversionCode() == null || entry.getConversionCode().trim().length() == 0);

      return true;
   }
}
