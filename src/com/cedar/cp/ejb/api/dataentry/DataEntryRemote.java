// Decompiled by:       Fernflower v0.8.6
// Date:                12.08.2012 13:06:17
// Copyright:           2008-2012, Stiver
// Home page:           http://www.neshkov.com/ac_decompiler.html

package com.cedar.cp.ejb.api.dataentry;

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
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBObject;

public interface DataEntryRemote extends EJBObject {

   boolean doesCellMatchChartOfAccounts(int var1, int var2, int[] var3, String[] var4, int var5, int var6) throws RemoteException;

   DataExtract dataImport(DataExtract var1) throws RemoteException, ValidationException;

   DataExtract dataImportPrep(Object var1) throws RemoteException, ValidationException;

   DataExtract getDataExtract(EntityRef var1, List var2, List var3, List var4, List var5, boolean var6, boolean var7) throws RemoteException;

   List getFinanceCubeDataSlice(String var1, String var2, String var3, int[] var4, int[] var5, int[] var6, List var7, List var8, List var9) throws RemoteException;

   List getAllStructureElementsForFinanceCube(int var1, String var2, String var3) throws RemoteException;

   Map getFinanceCubeDataForExtract(int var1, int[] var2, String var3) throws RemoteException;

   Object getSingleCellValue(int var1, int[] var2, String var3) throws RemoteException;

   CellContents getFinanceCubeCell(String var1, String var2, boolean var3, String[] var4, String[] var5, String var6) throws RemoteException;

   EntityList getCellValues(int var1, int var2, String[] var3, List<String[]> var4, String company) throws RemoteException;

   EntityList getCellValues(int var1, int var2, int[] var3, List<String[]> var4, String company) throws RemoteException;
   
   EntityList getCellValues(int var1, int var2, String[] var3, List<String[]> var4) throws RemoteException;

   EntityList getCellValues(int var1, int var2, int[] var3, List<String[]> var4) throws RemoteException;
   
   EntityList getOACellValues(int company, List<String[]> cellKeys) throws RemoteException;
   
   EntityList getCurrencyCellValues(List<String[]> cellKeys) throws RemoteException;
   
   EntityList getParameterCellValues(List<String[]> cellKeys) throws RemoteException;
   
   EntityList getAuctionCellValues(List<String[]> cellKeys) throws RemoteException;

   ExtractDataDTO getExtractData(ExtractDataDTO var1) throws RemoteException;

   CellContents getFinanceCubeCell(String var1, int var2, boolean var3, int[] var4, String[] var5, String var6) throws RemoteException;

   EntityList getCellNote(int var1, String var2, int var3) throws RemoteException;
   
   EntityList getAllNotes(int var1, int var3) throws RemoteException;
   
   EntityList getCellNote(int var1, String var2, int var3, String company) throws RemoteException;

   EntityList getCellAudit(int var1, String var2, int var3) throws RemoteException;

   FinanceSystemCellData getFinanceSystemCellData(int var1, String var2, String var3, int userId, int cmpy) throws RemoteException, ValidationException;

   EntityList getSheetNote(int var1, int var2) throws RemoteException;

   boolean getXMLFormSecurityAccessFlag(int var1) throws RemoteException;

   int getXMLFormIdFromCellCalcId(int var1, int var2) throws RemoteException;

   CellCalculationDetails getCellCalculationDetails(int var1, int var2, Map var3, int var4) throws RemoteException;

   List<Object> getCellCalculationContext(int var1, int var2) throws RemoteException;

   List<Integer> getCellCalcShortIdsForRA(int var1, int var2, int var3) throws RemoteException;

   List<Integer> getCellCalcShortIdsForRA(int var1, int var2, int var3, CalendarElementNode var4) throws RemoteException;

   FormConfig getXMLFormConfig(Map var1, int var2) throws RemoteException;

   FormDataInputModel getCalculationPreviousData(FormConfig var1, int var2, int var3) throws RemoteException;

   Map getCalculationSummaryData(FormConfig var1, int var2, int var3) throws RemoteException;

   EntityList getImmediateChildren(int userId, Object primaryKey) throws RemoteException;
   
   EntityList getImmediateChildrenLimitedByPermission(int dimensionType, int userId, int budgetCycleId, Object primaryKey) throws RemoteException;

   EntityList getElementsForConstraints(boolean var1, boolean var2, int var3, String var4, int var5, List var6) throws RemoteException;

   int issueMassUpdate(int var1, MassUpdateParameters var2) throws RemoteException;

   String validateMassUpdate(int var1, int var2, int var3) throws RemoteException;

   int issueRecharge(int var1, int var2, int var3, boolean var4, boolean var5, boolean var6, int[] var7) throws RemoteException;

   CalendarInfo getCalendarInfoForModel(String var1) throws RemoteException;
   
   int getCalYearMonthId( String visId, String calVisIdPrefix, int dimId) throws RemoteException;
   
   List<Map<String,String>> getStructureElements( List<Integer> dimsList ) throws RemoteException;

   EntityList queryExtSysInfoForCube(int var1) throws ValidationException, RemoteException;

   ExtSysTransactionQueryParams queryExtSysParams(int var1, String var2, String var3) throws RemoteException;
   
   Map<String,String> getCalendarRangeDetails(int var1) throws ValidationException, RemoteException;

   int getFinanceCubeIdFromVisId(String modelVisId, String cubeVisId) throws ValidationException, RemoteException;

   String[] getPropertiesForInvoice() throws RemoteException;

   boolean getUserReadOnlyAccess(int modelId, int structureElementId, int userId) throws ValidationException, RemoteException;

   EntityList getLastNotes(int financeCubeId) throws ValidationException, RemoteException;

   void saveCellNoteChange(Object cellNote, int financeCubeId, int userId, int budgetCycleId, String methodType) throws ValidationException, RemoteException;
}
