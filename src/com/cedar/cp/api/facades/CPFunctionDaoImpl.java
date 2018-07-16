package com.cedar.cp.api.facades;

import java.util.List;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.DriverManager;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.InvalidCredentialsException;
import com.cedar.cp.api.base.ListHelper;
import com.cedar.cp.api.base.UserDisabledException;
import com.cedar.cp.api.base.UserLicenseException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataEntry.DataEntryProcess;
import com.cedar.cp.util.xmlform.CalendarInfo;

public class CPFunctionDaoImpl implements CPFunctionDao {

    private DataEntryProcess mDataEntryProcess;
    private ListHelper mListHelper;

    public CPFunctionDaoImpl(String userId, String password, String uri) throws UserDisabledException, InvalidCredentialsException, UserLicenseException, ValidationException {
        this(DriverManager.getConnection(uri, userId, password, CPConnection.ConnectionContext.INTERACTIVE_WEB));
    }

    public CPFunctionDaoImpl(CPConnection conn) {
        this.mDataEntryProcess = conn.getDataEntryProcess();
        this.mListHelper = conn.getListHelper();
    }

    public EntityList getAllModelsWeb() {
        return this.mListHelper.getAllModelsWeb();
    }

    public EntityList getAllBudgetCyclesWeb() {
        return this.mListHelper.getAllBudgetCyclesWeb();
    }

    public EntityList getAllSystemProperties() {
        return this.mListHelper.getAllSystemPropertys();
    }

    public EntityList getAllFinanceCubesWebForModel(int modelId) {
        return this.mListHelper.getAllFinanceCubesWebForModel(modelId);
    }

    public EntityList getAllDimensionsForModel(int modelId) {
        return this.mListHelper.getAllDimensionsForModel(modelId);
    }

    public EntityList getHierarcyDetailsFromDimId(int dimId) {
        return this.mListHelper.getHierarcyDetailsFromDimId(dimId);
    }

    public EntityList getCellValues(int fcId, int numDims, String[] structureVisIds, List<String[]> cellKeys, String company) throws ValidationException {
        return this.mDataEntryProcess.getCellValues(fcId, numDims, structureVisIds, cellKeys, company);
    }

    public EntityList getCellValues(int fcId, int numDims, String[] structureVisIds, List<String[]> cellKeys) throws ValidationException {
        return this.mDataEntryProcess.getCellValues(fcId, numDims, structureVisIds, cellKeys);
    }

    public EntityList getOACellValues(int company, List<String[]> cellKeys) throws ValidationException {
        return this.mDataEntryProcess.getOACellValues(company, cellKeys);
    }

    public EntityList getCurrencyCellValues(List<String[]> cellKeys) throws ValidationException {
        return this.mDataEntryProcess.getCurrencyCellValues(cellKeys);
    }

    public EntityList getParameterCellValues(List<String[]> cellKeys) throws ValidationException {
        return this.mDataEntryProcess.getParameterCellValues(cellKeys);
    }

    public EntityList getAuctionCellValues(List<String[]> cellKeys) throws ValidationException {
        return this.mDataEntryProcess.getAuctionCellValues(cellKeys);
    }

    public ExtractDataDTO getExtractData(ExtractDataDTO extractDataDTO) throws ValidationException {
        return this.mDataEntryProcess.getExtractData(extractDataDTO);
    }

    public CalendarInfo getCalendarInfo(String modelVisId) throws ValidationException {
        return this.mDataEntryProcess.getCalendarInfoForModel(modelVisId);
    }

    public EntityList getStructureElementIdForModel(int modelId) throws ValidationException {
        return this.mListHelper.getStructureElementIdFromModel(modelId);
    }

}
