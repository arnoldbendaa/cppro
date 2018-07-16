// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:04:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.api.dataEntry;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataEntry.CellCalculationDetails;
import com.cedar.cp.api.dataEntry.CellContents;
import com.cedar.cp.api.dataEntry.DataExportParameters;
import com.cedar.cp.api.dataEntry.DataExtract;
import com.cedar.cp.api.dataEntry.FinanceSystemCellData;
import com.cedar.cp.api.dataEntry.MassUpdateParameters;
import com.cedar.cp.api.facades.ExtractDataDTO;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.FormConfig;
import com.cedar.cp.util.xmlform.inputs.FormDataInputModel;

import java.util.List;
import java.util.Map;

public interface DataEntryProcess {

   int NEW_CELL_CALCULATION_OFFSET = -500000001;


   void executeForegroundCubeUpdate(String var1) throws ValidationException, CPException;

   void executeForegroundFlatFormUpdate(String var1) throws ValidationException, CPException;

   DataExportParameters getDataExportParameters(EntityRef var1) throws CPException;

   boolean doesCellMatchChartOfAccounts(int var1, int var2, int[] var3, String[] var4, int var5, int var6) throws ValidationException, CPException;

   DataExtract dataImport(DataExtract var1) throws ValidationException, CPException;

   DataExtract dataImportPrep(Object var1) throws ValidationException, CPException;

   DataExtract getDataExtract(EntityRef var1, List var2, List var3, List var4, List var5, boolean var6, boolean var7) throws ValidationException, CPException;

   List getAllStructureElementsForFinanceCube(int var1, String var2, String var3) throws ValidationException, CPException;

   Map getFinanceCubeDataForExtract(int var1, int[] var2, String var3) throws ValidationException, CPException;

   Object getSingleCellValue(int var1, int[] var2, String var3) throws ValidationException, CPException;

   CellContents getFinanceCubeCell(String var1, String var2, boolean var3, String[] var4, String[] var5, String var6) throws ValidationException, CPException;

   CellContents getFinanceCubeCell(String var1, int var2, boolean var3, int[] var4, String[] var5, String var6) throws ValidationException, CPException;

   EntityList getCellValues(int var1, int var2, String[] var3, List<String[]> var4, String company) throws ValidationException;

   EntityList getCellValues(int var1, int var2, int[] var3, List<String[]> var4, String company) throws ValidationException;
   
   EntityList getCellValues(int var1, int var2, String[] var3, List<String[]> var4) throws ValidationException;

   EntityList getCellValues(int var1, int var2, int[] var3, List<String[]> var4) throws ValidationException;
   
   EntityList getOACellValues(int company, List<String[]> cellKeys) throws ValidationException;
   
   EntityList getCurrencyCellValues(List<String[]> cellKeys) throws ValidationException;
   
   EntityList getParameterCellValues(List<String[]> cellKeys) throws ValidationException;
   
   EntityList getAuctionCellValues(List<String[]> cellKeys) throws ValidationException;

   ExtractDataDTO getExtractData(ExtractDataDTO var1) throws ValidationException;

   List getFinanceCubeDataSlice(String var1, String var2, String var3, int[] var4, int[] var5, int[] var6, List var7, List var8, List var9) throws ValidationException, CPException;

   /**
    * 
    * @param financeCubeId
    * @param cellPk numeric dimensions splitted with ","
    * @param userId
    * @return
    * @throws ValidationException
    * @throws CPException
    */
   EntityList getCellNote(int financeCubeId, String cellPk, int userId) throws ValidationException, CPException;
   
   EntityList getAllNotes(int var1, int var2) throws ValidationException, CPException;
   
   /**
    * 
    * @param financeCubeId
    * @param cellPk numeric dimensions splitted with ","
    * @param userId
    * @param company
    * @return
    * @throws ValidationException
    * @throws CPException
    */
   EntityList getCellNote(int financeCubeId, String cellPk, int userId, String company) throws ValidationException, CPException;

   EntityList getCellAudit(int var1, String var2, int var3) throws ValidationException, CPException;

   FinanceSystemCellData getFinanceSystemCellData(Integer var1, String var2, String var3, String mCellPk, String mYtd, int var4, int cmpy) throws ValidationException, CPException;

   EntityList getSheetNote(int var1, int var2) throws ValidationException, CPException;

   boolean getXMLFormSecurityAccessFlag(int var1) throws ValidationException, CPException;

   int getXMLFormIdFromCellCalcId(int var1, int var2) throws ValidationException, CPException;

   CellCalculationDetails getCellCalculationDetails(int var1, int var2, Map var3, int var4) throws ValidationException, CPException;

   List<Object> getCellCalculationContext(int var1, int var2) throws ValidationException, CPException;

   List<Integer> getCellCalcShortIdsForRA(int var1, int var2, int var3) throws ValidationException, CPException;

   List<Integer> getCellCalcShortIdsForRA(int var1, int var2, int var3, CalendarElementNode var4) throws ValidationException, CPException;

   FormConfig getXMLFormConfig(Map var1, int var2) throws ValidationException, CPException;

   FormDataInputModel getCalculationPreviousData(FormConfig var1, int var2, int var3) throws ValidationException, CPException;

   Map getCalculationSummaryData(FormConfig var1, int var2, int var3) throws ValidationException, CPException;

   EntityList getImmediateChildren(int userId, Object primaryKey) throws ValidationException, CPException;
   
   EntityList getImmediateChildrenLimitedByPermission(int dimensionType, int userId, int budgetCycleId, Object primaryKey) throws ValidationException, CPException;

   EntityList getElementsForConstraints(boolean var1, boolean var2, int var3, String var4, int var5, List var6) throws ValidationException, CPException;

   int issueMassUpdate(int var1, MassUpdateParameters var2) throws ValidationException, CPException;

   String validateMassUpdate(int var1, int var2, int var3) throws ValidationException, CPException;

   int issueRecharge(int var1, int var2, int var3, boolean var4, boolean var5, boolean var6, int[] var7) throws ValidationException, CPException;

   int issueRecharge(int var1, int var2, EntityRef var3, boolean var4, boolean var5, boolean var6, int[] var7) throws ValidationException, CPException;

   CalendarInfo getCalendarInfoForModel(String var1) throws ValidationException, CPException;
   
   int getCalYearMonthId(String visId, String calVisIdPrefix, int dimId) throws ValidationException, CPException;
   
   public List<Map<String,String>> getStructureElements( List<Integer> dimsList ) throws ValidationException, CPException;
   
   Map<String,String> getGetCalendarRangeDetails( int id ) throws ValidationException, CPException;
   
   public String[] getPropertiesForInvoice();
   
   public boolean getUserReadOnlyAccess(int modelId, int structureElementId) throws ValidationException;

   EntityList getLastNotes(int financeCubeId) throws ValidationException, CPException;
}
