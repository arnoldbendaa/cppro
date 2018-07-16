// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:07:04
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.base.cube.flatform;

import com.cedar.cp.ejb.base.cube.flatform.DimensionLookup;
import com.cedar.cp.ejb.base.cube.flatform.Property;
import com.cedar.cp.ejb.base.cube.flatform.WorksheetUpdate;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WorkbookUpdate {

   private int mUserId;
   private int mBudgetCycleId;
   private Map<String, String> mParameters = new HashMap();
   private List<WorksheetUpdate> mWorksheetUpdates = new ArrayList();


   public int getUserId() {
      return this.mUserId;
   }

   public void setUserId(int userId) {
      this.mUserId = userId;
   }

   public int getBudgetCycleId() {
      return this.mBudgetCycleId;
   }

   public void setBudgetCycleId(int budgetCycleId) {
      this.mBudgetCycleId = budgetCycleId;
   }
   
	public int getFormType() {
		return this.mParameters.get("FormType")!=null?Integer.parseInt( this.mParameters.get("FormType") ):-1;
	}

   public Map<String, String> getParameters() {
      return this.mParameters;
   }

   public void addParameter(Property property) {
      this.mParameters.put(property.getName(), property.getValue());
   }

   public void addWorksheetUpdate(WorksheetUpdate worksheetUpdate) {
      this.mWorksheetUpdates.add(worksheetUpdate);
   }

   public List<WorksheetUpdate> getWorksheetUpdates() {
      return this.mWorksheetUpdates;
   }

   public void replaceParameterMacros() {
      String costCentreContextVisId = this.getDimensionVisIdContext(0);
      Iterator i$ = this.mWorksheetUpdates.iterator();

      while(i$.hasNext()) {
         WorksheetUpdate worksheet = (WorksheetUpdate)i$.next();
         worksheet.replaceParameterMacros(this.mParameters);
      }

   }

   public void insertContextVariables() {
      if(this.mParameters != null && this.mParameters.size() > 0) {
         Iterator i$ = this.getWorksheetUpdates().iterator();

         while(i$.hasNext()) {
            WorksheetUpdate worksheetUpdate = (WorksheetUpdate)i$.next();
            worksheetUpdate.insertContextVariables(this.mParameters);
         }
      }

   }

   public String getDimensionVisIdContext(int dimensionIndex) {
      return (String)this.mParameters.get(WorkbookProperties.DIMENSION_$_VISID.toString().replace("$", String.valueOf(dimensionIndex)));
   }

   public Map<String, DimensionLookup> queryLookupInfo() {
      HashMap lookups = new HashMap();
      Iterator i$ = this.mWorksheetUpdates.iterator();

      while(i$.hasNext()) {
         WorksheetUpdate sheet = (WorksheetUpdate)i$.next();
         sheet.queryLookupInfo(lookups);
      }

      return lookups;
   }

   public Set<String> queryDataTypes() {
      HashSet dataTypes = new HashSet();
      Iterator i$ = this.getWorksheetUpdates().iterator();

      while(i$.hasNext()) {
         WorksheetUpdate worksheet = (WorksheetUpdate)i$.next();
         worksheet.queryDataTypes(dataTypes);
      }

      return dataTypes;
   }
}
