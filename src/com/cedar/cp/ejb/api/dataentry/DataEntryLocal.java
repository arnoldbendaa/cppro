// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.dataentry;

import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;
import javax.ejb.EJBLocalObject;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataEntry.CellCalculationDetails;
import com.cedar.cp.api.dataEntry.CellContents;
import com.cedar.cp.api.dataEntry.DataExtract;
import com.cedar.cp.api.dataEntry.FinanceSystemCellData;
import com.cedar.cp.api.dataEntry.MassUpdateParameters;
import com.cedar.cp.api.facades.ExtractDataDTO;
import com.cedar.cp.dto.extsys.ExtSysTransactionQueryParams;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.inputs.FormDataInputModel;

public interface DataEntryLocal extends EJBLocalObject {

   boolean doesCellMatchChartOfAccounts(int var1, int var2, int[] var3, String[] var4, int var5, int var6) throws EJBException;

   DataExtract dataImport(DataExtract var1) throws EJBException, ValidationException;

   DataExtract dataImportPrep(Object var1) throws EJBException, ValidationException;

   DataExtract getDataExtract(EntityRef var1, List var2, List var3, List var4, List var5, boolean var6, boolean var7) throws EJBException;

   List getFinanceCubeDataSlice(String var1, String var2, String var3, int[] var4, int[] var5, int[] var6, List var7, List var8, List var9) throws EJBException;

   List getAllStructureElementsForFinanceCube(int var1, String var2, String var3) throws EJBException;

   Map getFinanceCubeDataForExtract(int var1, int[] var2, String var3) throws EJBException;

   Object getSingleCellValue(int var1, int[] var2, String var3) throws EJBException;

   CellContents getFinanceCubeCell(String var1, String var2, boolean var3, String[] var4, String[] var5, String var6) throws EJBException;

   EntityList getCellValues(int var1, int var2, String[] var3, List<String[]> var4) throws EJBException;

   EntityList getCellValues(int var1, int var2, int[] var3, List<String[]> var4) throws EJBException;
      
   EntityList getOACellValues(int company, List<String[]> cellKeys) throws EJBException;
   
   EntityList getCurrencyCellValues(List<String[]> cellKeys) throws EJBException;
   
   EntityList getParameterCellValues(List<String[]> cellKeys) throws EJBException;
   
   EntityList getAuctionCellValues(List<String[]> cellKeys) throws EJBException;
      
   ExtractDataDTO getExtractData(ExtractDataDTO var1) throws EJBException;

   CellContents getFinanceCubeCell(String var1, int var2, boolean var3, int[] var4, String[] var5, String var6) throws EJBException;

   EntityList getCellNote(int var1, String var2, int var3) throws EJBException;
   
   EntityList getAllNotes(int var1, int var3) throws EJBException;
   
   EntityList getCellNote(int var1, String var2, int var3, String company) throws EJBException;

   EntityList getCellAudit(int var1, String var2, int var3) throws EJBException;

   FinanceSystemCellData getFinanceSystemCellData(int var1, String var2, String var3, int userId, int cmpy) throws EJBException, ValidationException;

   EntityList getSheetNote(int var1, int var2) throws EJBException;

   boolean getXMLFormSecurityAccessFlag(int var1) throws EJBException;

   int getXMLFormIdFromCellCalcId(int var1, int var2) throws EJBException;

   CellCalculationDetails getCellCalculationDetails(int var1, int var2, Map var3, int var4) throws EJBException;

   List<Object> getCellCalculationContext(int var1, int var2) throws EJBException;

   List<Integer> getCellCalcShortIdsForRA(int var1, int var2, int var3) throws EJBException;

   List<Integer> getCellCalcShortIdsForRA(int var1, int var2, int var3, CalendarElementNode var4) throws EJBException;

   FormConfig getXMLFormConfig(Map var1, int var2) throws EJBException;

   FormDataInputModel getCalculationPreviousData(FormConfig var1, int var2, int var3) throws EJBException;

   Map getCalculationSummaryData(FormConfig var1, int var2, int var3) throws EJBException;

   EntityList getImmediateChildren(int userId, Object primaryKey) throws EJBException;
   
   EntityList getImmediateChildrenLimitedByPermission(int dimensionType, int userId, int budgetCycleId, Object primaryKey) throws EJBException;
   
   EntityList getElementsForConstraints(boolean var1, boolean var2, int var3, String var4, int var5, List var6) throws EJBException;

   int issueMassUpdate(int var1, MassUpdateParameters var2) throws EJBException;

   String validateMassUpdate(int var1, int var2, int var3) throws EJBException;

   int issueRecharge(int var1, int var2, int var3, boolean var4, boolean var5, boolean var6, int[] var7) throws EJBException;

   CalendarInfo getCalendarInfoForModel(String var1) throws EJBException;
   
   int getCalYearMonthId(String visId, String calVisIdPrefix, int dimId) throws EJBException;
   
   List<Map<String,String>> getStructureElements( List<Integer> dimsList ) throws EJBException;

   EntityList queryExtSysInfoForCube(int var1) throws ValidationException, EJBException;

   ExtSysTransactionQueryParams queryExtSysParams(int var1, String var2, String var3) throws EJBException;
   
   Map<String,String> getCalendarRangeDetails(int var1) throws ValidationException, EJBException;

   int getFinanceCubeIdFromVisId(String modelVisId, String cubeVisId) throws ValidationException, EJBException;

   String[] getPropertiesForInvoice() throws EJBException;

   boolean getUserReadOnlyAccess(int modelId, int structureElementId, int userId) throws ValidationException, EJBException;

   EntityList getLastNotes(int financeCubeId) throws ValidationException, EJBException;

   void saveCellNoteChange(Object cellNote, int financeCubeId, int userId, int budgetCycleId, String methodType) throws ValidationException, EJBException;
}
