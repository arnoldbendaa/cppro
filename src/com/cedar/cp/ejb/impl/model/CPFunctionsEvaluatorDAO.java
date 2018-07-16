package com.cedar.cp.ejb.impl.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.facades.CPFunctionDao;
import com.cedar.cp.api.facades.ExtractDataDTO;
import com.cedar.cp.dto.dimension.calendar.CalendarInfoImpl;
import com.cedar.cp.ejb.impl.dataentry.DataEntryDAO;
import com.cedar.cp.ejb.impl.dimension.DimensionDAO;
import com.cedar.cp.ejb.impl.dimension.HierarchyDAO;
import com.cedar.cp.ejb.impl.dimension.StructureElementDAO;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyDAO;
import com.cedar.cp.util.xmlform.CalendarInfo;

public class CPFunctionsEvaluatorDAO implements CPFunctionDao {
    private DataEntryDAO mDataEntryDAO;
    private ModelDAO mModelDAO;
    private StructureElementDAO mStructureElementDAO;
    private BudgetCycleDAO mBudgetCycleDAO;
    private SystemPropertyDAO mSystemPropertyDAO;
    private FinanceCubeDAO mFinanceCubeDAO;
    private DimensionDAO mDimensionDAO;
    private HierarchyDAO mHierarchyDAO;
    private Map<String, CalendarInfo> mCalendarInfoMap = new HashMap();

    private DataEntryDAO getDataEntryDAO() {
        if (this.mDataEntryDAO == null) {
            this.mDataEntryDAO = new DataEntryDAO();
        }

        return this.mDataEntryDAO;
    }

    private ModelDAO getModelDAO() {
        if (this.mModelDAO == null) {
            this.mModelDAO = new ModelDAO();
        }

        return this.mModelDAO;
    }

    private StructureElementDAO getStructureElementDAO() {
        if (this.mStructureElementDAO == null) {
            this.mStructureElementDAO = new StructureElementDAO();
        }

        return this.mStructureElementDAO;
    }

    private BudgetCycleDAO getBudgetCycleDAO() {
        if (this.mBudgetCycleDAO == null) {
            this.mBudgetCycleDAO = new BudgetCycleDAO();
        }

        return this.mBudgetCycleDAO;
    }

    private SystemPropertyDAO getSystemPropertyDAO() {
        if (this.mSystemPropertyDAO == null) {
            this.mSystemPropertyDAO = new SystemPropertyDAO();
        }

        return this.mSystemPropertyDAO;
    }

    private FinanceCubeDAO getFinanceCubeDAO() {
        if (this.mFinanceCubeDAO == null) {
            this.mFinanceCubeDAO = new FinanceCubeDAO();
        }

        return this.mFinanceCubeDAO;
    }

    private DimensionDAO getDimensionDAO() {
        if (this.mDimensionDAO == null) {
            this.mDimensionDAO = new DimensionDAO();
        }

        return this.mDimensionDAO;
    }

    private HierarchyDAO getHierarchyDAO() {
        if (this.mHierarchyDAO == null) {
            this.mHierarchyDAO = new HierarchyDAO();
        }

        return this.mHierarchyDAO;
    }

    public EntityList getAllModelsWeb() {
        return this.getModelDAO().getAllModelsWeb();
    }

    public EntityList getAllBudgetCyclesWeb() {
        return this.getBudgetCycleDAO().getAllBudgetCyclesWeb();
    }

    public EntityList getAllSystemProperties() {
        return this.getSystemPropertyDAO().getAllSystemPropertys();
    }

    public EntityList getAllFinanceCubesWebForModel(int modelId) {
        return this.getFinanceCubeDAO().getAllFinanceCubesWebForModel(modelId);
    }

    public EntityList getAllDimensionsForModel(int modelId) {
        return this.getDimensionDAO().getAllDimensionsForModel(modelId);
    }

    public EntityList getHierarcyDetailsFromDimId(int dimId) {
        return this.getHierarchyDAO().getHierarcyDetailsFromDimId(dimId);
    }

    public EntityList getCellValues(int fcId, int numDims, String[] structureVisIds, List<String[]> cellKeys, String company) throws ValidationException {
        return this.getDataEntryDAO().getCellValues(fcId, numDims, structureVisIds, cellKeys, company);
    }

    public EntityList getCellValues(int fcId, int numDims, String[] structureVisIds, List<String[]> cellKeys) throws ValidationException {
        return this.getDataEntryDAO().getCellValues(fcId, numDims, structureVisIds, cellKeys);
    }

    public EntityList getOACellValues(int company, List<String[]> cellKeys) throws ValidationException {
        return this.getDataEntryDAO().getOACellValues(company, cellKeys);
    }

    public EntityList getCurrencyCellValues(List<String[]> cellKeys) throws ValidationException {
        return this.getDataEntryDAO().getCurrencyCellValues(cellKeys);
    }

    public EntityList getParameterCellValues(List<String[]> cellKeys) throws ValidationException {
        return this.getDataEntryDAO().getParameterCellValues(cellKeys);
    }

    public EntityList getAuctionCellValues(List<String[]> cellKeys) throws ValidationException {
        return this.getDataEntryDAO().getAuctionCellValues(cellKeys);
    }

    public CalendarInfo getCalendarInfo(String modelVisId) throws ValidationException {
        CalendarInfo calendarInfo = (CalendarInfo) this.mCalendarInfoMap.get(modelVisId);
        if (calendarInfo != null) {
            return calendarInfo;
        } else {
            CalendarInfoImpl calendarInfo1 = this.getStructureElementDAO().getCalendarInfoForModelVisId(modelVisId);
            this.mCalendarInfoMap.put(modelVisId, calendarInfo1);
            return calendarInfo1;
        }
    }

    public EntityList getStructureElementIdForModel(int modelId) throws ValidationException {
        return this.getStructureElementDAO().getStructureElementIdFromModel(modelId);
    }

    public ExtractDataDTO getExtractData(ExtractDataDTO extractDataDTO) throws ValidationException {
        return ExtractDataHelper.getExtractData(extractDataDTO);
    }
}
