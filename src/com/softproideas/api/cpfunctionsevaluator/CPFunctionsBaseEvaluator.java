package com.softproideas.api.cpfunctionsevaluator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataEntry.CellNoteRow;
import com.cedar.cp.api.facades.ExtractDataDTO;
import com.cedar.cp.api.facades.ExtractDataDTOImpl;
import com.cedar.cp.api.facades.POIHyperlinkDTO;
import com.cedar.cp.api.facades.ParseException;
import com.cedar.cp.util.Log;
import com.cedar.cp.util.NumberUtils;
import com.cedar.cp.util.Pair;
import com.cedar.cp.util.Timer;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.softproideas.api.cpfunctionsevaluator.DateUtil;
import com.softproideas.util.validation.GlobalMappedMappingValidator;
import com.softproideas.util.validation.MappingArguments;
import com.softproideas.util.validation.MappingFunction;
import com.softproideas.util.validation.MappingValidator;

public abstract class CPFunctionsBaseEvaluator implements CPFunctionsEvaluator {

    private Map<String, String> mParameters = new HashMap<String, String>();
    private String mModelVisId;
    private String mFinanceVisId;
    private String mCompany;
    private int mFinanceCubeId;
    private String[] mStructureVisIds;
    private String[] mNullElementVisIds;
    private int[] mStructureIds;
    private int mModelId;
    private String mCostCenter;
    private String mCostCenterDescription;
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("ddMMMyy");
    private int mCostCenterId;
    private String mBudgetCycleVisId;
    private int mBudgetCycleId;
    private String mRootURL;
    private Map<String, MappingValidator> mValidMappingBatchLinkKeys = new HashMap<String, MappingValidator>();
    public static final String CONTEXT_DIM_VISID_PREFIX = WorkbookProperties.DIMENSION_$_VISID.toString();
    public static final String CONTEXT_DIM_DESCRIPTION_PREFIX = WorkbookProperties.DIMENSION_$_DESCRIPTION.toString();
    public static final String CONTEXT_DATATYPE_PREFIX = WorkbookProperties.DATA_TYPE.toString();

    private String dim2Context = WorkbookProperties.DIMENSION_2_VISID.toString();

    // MODEL_ID=ModelPK|56181
    // MODEL_VISID=1/1
    // DIMENSION_0_VISID=1/1-cc
    // DIMENSION_1_VISID=1/1-exp
    // DIMENSION_2_VISID=1/1-Cal
    // FINANCE_CUBE_ID=FinanceCubeCK|ModelPK|56181|FinanceCubePK|56184
    // FINANCE_CUBE_VISID=1/1-Cube1
    // DIMENSION_0_HIERARCHY_VISID=1/1-cc-Group
    // DIMENSION_1_HIERARCHY_VISID=1/1-exp-PL-GRP
    // DIMENSION_2_HIERARCHY_VISID=1/1-Cal
    //
    // {EXCLUDE_DATA_TYPES=AQ,BM,DN,FR,ZM,
    // INVERT_NUMBERS_VALUE=true,
    // MODEL_VISID=5/1,
    // DIMENSION_0_VISID=Total:BS,
    // DIMENSION_1_VISID=5/1-exp-PL-GRP,
    // DIMENSION_2_VISID=/2011,
    // DATA_TYPE=AV}

    private Pattern patternComma = Pattern.compile(",");
    private Pattern patternSlash = Pattern.compile("/");
    private Pattern patternColon = Pattern.compile(":");
    private CalendarInfo mCalendarInfo;

    private CPFunctionDao dataEntryDAO;

    public CPFunctionsBaseEvaluator(CPFunctionDao dao) {
        this.dataEntryDAO = dao;
    }

    public void setCostCenterId(int costCenterId) {
        this.mCostCenterId = costCenterId;
    }

    public void setCostCenter(String costCenter) {
        this.mCostCenter = costCenter;
        this.mParameters.put(WorkbookProperties.DIMENSION_0_VISID.toString(), costCenter);
    }

    public void setCostCenterDescription(String costCenterDescription) {
        this.mCostCenterDescription = costCenterDescription;
        this.mParameters.put(WorkbookProperties.DIMENSION_0_DESCRIPTION.toString(), costCenterDescription);
    }

    public void setParameters(Map<String, String> parameters) {
        this.mParameters.putAll(parameters);
    }

    @Override
    public String getDataType() {
        return this.mParameters.get(CONTEXT_DATATYPE_PREFIX);
    }

    public Object processExpression(String expression) throws Exception {
        MappingValidator mv = new MappingValidator(expression);
        // if (mv.isValid() == false) {
        // throw new MappingValidationException("Mapping validation Exception", mv.getValidationErrors());
        // }
        if (mv.getFunction().equals(MappingFunction.FINANCE_CUBE)) {
            return processFinanceCubeExpression(mv.buildMapping());
        }
        if (mv.getFunction().equals(MappingFunction.DIM0_IDENTIFIER)) {
            return processDim0IdentifierExpression();
        }
        if (mv.getFunction().equals(MappingFunction.DIM0_DESCRIPTION)) {
            return processDim0DescriptionExpression();
        }
        if (mv.getFunction().equals(MappingFunction.STRUCTURES)) {
            return processStructuresExpression(mv.buildMapping());
        }
        if (mv.getFunction().equals(MappingFunction.CELL)) {
            return processCellExpression(mv.getListOfArguments());
        }
        if (mv.getFunction().equals(MappingFunction.PARAM)) {
            return processParamExpression(mv.buildMapping());
        }
        if (mv.getFunction().equals(MappingFunction.GET_VIS_ID)) {
            return processGetVisIdsExpression(mv.getListOfArguments());
        }
        if (mv.getFunction().equals(MappingFunction.GET_DESCRIPTION)) {
            return processDescriptionExpression(mv.getListOfArguments());
        }
        if (mv.getFunction().equals(MappingFunction.GET_LABEL)) {
            return processGetLabelExpression(mv.getListOfArguments());
        }
        if (mv.getFunction().equals(MappingFunction.FORM_LINK)) {
            return processLinkExpression(mv.buildMapping());
        }
        if (mv.getFunction().equals(MappingFunction.FORM_FULL_LINK)) {
            return processLinkExpression(mv.buildMapping());
        }
        return expression;
    }

    // link key = com.cedar.cp.util.flatform.model.Cell
    public void addBatchExpression(String expression, String cell) throws Exception {
        MappingValidator mv = null;
        if (isGlobal()) {
            mv = new GlobalMappedMappingValidator(expression);
        } else {
            mv = new MappingValidator(expression);
        }
        // if (mv.isValid() == false) {
        // throw new MappingValidationException("Mapping validation Exception", mv.getValidationErrors());
        // }
        if (mv.getFunction() == MappingFunction.FINANCE_CUBE || mv.getFunction() == MappingFunction.STRUCTURES) {
            processExpression(mv.buildMapping());
        } else {
            mValidMappingBatchLinkKeys.put(cell, mv);
        }
    }

    private boolean isGlobal() {
        if (mModelVisId != null && mModelVisId.toUpperCase().startsWith("GL")) {
            return true;
        }
        return false;
    }

    public HashMap<String, Object> submitBatch() throws Exception {

        // if (mStructureVisIds == null) {
        // throw new ValidationException("Must call cp.cedar.structures() before using other functions in sheet.");
        // }

        HashMap<String, Object> results = new HashMap<String, Object>();
        ArrayList<String[]> oaCellAddresses = new ArrayList<String[]>();
        ArrayList<String> oaCellLinks = new ArrayList<String>();
        ArrayList<String[]> currCellAddresses = new ArrayList<String[]>();
        ArrayList<String> currCellLinks = new ArrayList<String>();
        ArrayList<String[]> paramCellAddresses = new ArrayList<String[]>();
        ArrayList<String> paramCellLinks = new ArrayList<String>();
        ArrayList<String[]> auctionCellAddresses = new ArrayList<String[]>();
        ArrayList<String> auctionCellLinks = new ArrayList<String>();
        ArrayList<String[]> cellAddresses = new ArrayList<String[]>();
        ArrayList<String> cellLinks = new ArrayList<String>();

        Map<String, List<String[]>> globalCellAdresses = new HashMap<String, List<String[]>>();

        ExtractDataDTOImpl extractDTO = null;
        ArrayList<ElementQueryDetails> getCellDescAddresses = new ArrayList<ElementQueryDetails>();

        for (String cell: mValidMappingBatchLinkKeys.keySet()) {
            MappingValidator mv = mValidMappingBatchLinkKeys.get(cell);
            switch (mv.getFunction()) {
            // OAExpressions
                case GET_BASE_VAL: {
                    String[] cells = prepareOACellKey(mv, "BASEVAL");
                    oaCellAddresses.add(cells);
                    oaCellLinks.add(cell);
                    break;
                }
                case GET_QUANTITY: {
                    String[] cells = prepareOACellKey(mv, "QTY");
                    oaCellAddresses.add(cells);
                    oaCellLinks.add(cell);
                    break;
                }
                case GET_CUM_BASE_VAL: {
                    String[] cells = prepareOACellKey(mv, "CUMBASEVAL");
                    oaCellAddresses.add(cells);
                    oaCellLinks.add(cell);
                    break;
                }
                case GET_CUM_QUANTITY: {
                    String[] cells = prepareOACellKey(mv, "CUMQTY");
                    oaCellAddresses.add(cells);
                    oaCellLinks.add(cell);
                    break;
                }
                // currencyExpression
                case GET_CURRENCY_LOOKUP: {
                    String[] cells = prepareCurrencyCellKey(mv);
                    currCellAddresses.add(cells);
                    currCellLinks.add(cell);
                    break;
                }
                // parameterExpression
                case GET_PARAMETER_LOOKUP: {
                    String[] cells = prepareParameterCellKey(mv);
                    paramCellAddresses.add(cells);
                    paramCellLinks.add(cell);
                    break;
                }// auctionExpression
                case GET_AUCTION_LOOKUP: {
                    String[] cells = prepareAuctionCellKey(mv);
                    auctionCellAddresses.add(cells);
                    auctionCellLinks.add(cell);
                    break;
                }
                case CELL: {
                    String[] cells = prepareCellKey(mv.getListOfArguments());
                    cellAddresses.add(cells);
                    cellLinks.add(cell);
                    break;
                }
                case GET_GLOB:
                case PUT_CELL:
                case GET_CELL: {
                    if (isGlobal()) {
                        Pair<String, String[]> pair = prepareGetGlobalCellKey(mv);
                        List<String[]> addresses = globalCellAdresses.get(pair.getChild1());
                        if (addresses == null) {
                            addresses = new ArrayList<String[]>();
                        }
                        addresses.add(pair.getChild2());
                        globalCellAdresses.put(pair.getChild1(), addresses);
                        cellLinks.add(cell);
                    } else {
                        String[] cells = prepareGetCellKey(mv);
                        cellAddresses.add(cells);
                        cellLinks.add(cell);
                    }
                    break;
                }
                case GET_DESCRIPTION:
                case GET_LABEL: {
                    if (mv.getListOfArguments().containsKey(MappingArguments.DIM2.toString())) {
                        Object processedValue = processExpression(mv.buildMapping());
                        results.put(cell, processedValue);
                    } else {
                        if (mStructureVisIds == null) {
                            throw new ValidationException("Must call cp.cedar.structures() before using other functions in sheet.");
                        }
                        ElementQueryDetails hierarchyElementVisId = this.prepareGetCellDimVisuals(mv.getListOfArguments());

                        hierarchyElementVisId.mLink = cell;
                        hierarchyElementVisId.mLabelAndDescr = mv.getFunction().equals(MappingFunction.GET_LABEL);

                        if (extractDTO == null) {
                            extractDTO = new ExtractDataDTOImpl();
                        }
                        extractDTO.addElementDescriptionLookup(this.mStructureVisIds[hierarchyElementVisId.mDimensionIndex], hierarchyElementVisId.mVisualId);

                        getCellDescAddresses.add(hierarchyElementVisId);
                    }
                    break;

                }
                default: {
                    Object processedValue = processExpression(mv.buildMapping());
                    results.put(cell, processedValue);
                }
            }
        }
        Log log = new Log(this.getClass());
        // db queries
        EntityList cellValues = null;
        // oa
        if (oaCellAddresses.size() > 0) {
            log.info("oaCellAddresses", "start");
            String[] model = patternSlash.split(this.mModelVisId);

            if (NumberUtils.isNumber(model[0])) {
                int[] ranges = splitList(oaCellAddresses.size(), 100);
                for (int i = 0; i < ranges.length - 1; i++) {
                    List<String[]> temp = oaCellAddresses.subList(ranges[i], ranges[i + 1]);
                    List<String> oaCellLinksPart = oaCellLinks.subList(ranges[i], ranges[i + 1]);
                    cellValues = dataEntryDAO.getOACellValues(Integer.parseInt(model[0]), temp);
                    results.putAll(buildOAResult(cellValues, oaCellLinksPart));
                }
            }
            log.info("oaCellAddresses", "end");
        }
        // currency lookup
        if (currCellAddresses.size() > 0) {
            int[] ranges = splitList(currCellAddresses.size(), 100);
            for (int i = 0; i < ranges.length - 1; i++) {
                List<String[]> temp = currCellAddresses.subList(ranges[i], ranges[i + 1]);
                List<String> currCellLinksPart = currCellLinks.subList(ranges[i], ranges[i + 1]);
                log.info("currCellAddresses size " + temp.size(), "start");
                cellValues = dataEntryDAO.getCurrencyCellValues(temp);
                results.putAll(buildResult(cellValues, currCellLinksPart));
                log.info("currCellAddresses", "end");
            }
        }
        // param lookup
        if (paramCellAddresses.size() > 0) {
            int[] ranges = splitList(paramCellAddresses.size(), 100);
            for (int i = 0; i < ranges.length - 1; i++) {
                List<String[]> temp = paramCellAddresses.subList(ranges[i], ranges[i + 1]);
                List<String> paramCellLinksPart = paramCellLinks.subList(ranges[i], ranges[i + 1]);
                log.info("paramCellAddresses size " + temp.size(), "start");
                cellValues = dataEntryDAO.getParameterCellValues(temp);
                results.putAll(buildResult(cellValues, paramCellLinksPart));
                log.info("paramCellAddresses", "end");
            }
        }
        // auction lookup
        if (auctionCellAddresses.size() > 0) {
            log.info("auctionCellAddresses", "start");
            int[] ranges = splitList(auctionCellAddresses.size(), 100);
            for (int i = 0; i < ranges.length - 1; i++) {
                List<String[]> temp = auctionCellAddresses.subList(ranges[i], ranges[i + 1]);
                List<String> auctionCellLinksPart = auctionCellLinks.subList(ranges[i], ranges[i + 1]);
                cellValues = dataEntryDAO.getAuctionCellValues(temp);
                results.putAll(buildResult(cellValues, auctionCellLinksPart));
            }
            log.info("auctionCellAddresses", "end");
        }
        // cell expression
        if (cellAddresses.size() > 0) {
            log.info("cellAddresses", "start");
            int[] ranges = splitList(cellAddresses.size(), 1000);
            for (int i = 0; i < ranges.length - 1; i++) {
                List<String[]> temp = cellAddresses.subList(ranges[i], ranges[i + 1]);
                List<String> cellLinksPart = cellLinks.subList(ranges[i], ranges[i + 1]);
                ExtractDataDTOImpl DTO = new ExtractDataDTOImpl();
                DTO.setCellKeys(temp);
                DTO = (ExtractDataDTOImpl) prepareCellQuerry(DTO);
                cellValues = DTO.getCellData();
                results.putAll(buildOAResult(cellValues, cellLinksPart));
            }
            log.info("cellAddresses", "end");

        }
        // global cell expression
        if (globalCellAdresses.size() > 0) {
            log.info("globalCellAdresses", "start");
            for (String company: globalCellAdresses.keySet()) {
                int[] ranges = splitList(globalCellAdresses.get(company).size(), 1000);
                for (int i = 0; i < ranges.length - 1; i++) {
                    List<String[]> temp = globalCellAdresses.get(company).subList(ranges[i], ranges[i + 1]);
                    List<String> cellLinksPart = cellLinks.subList(ranges[i], ranges[i + 1]);
                    ExtractDataDTOImpl DTO = new ExtractDataDTOImpl();
                    DTO.setCellKeys(temp);
                    DTO = (ExtractDataDTOImpl) prepareGlobalCellQuerry(DTO, company);
                    cellValues = DTO.getCellData();
                    results.putAll(buildOAResult(cellValues, cellLinksPart));
                }
            }
            log.info("globalCellAdresses", "end");
        }
        // description, label, visid
        if (extractDTO != null) {
            log.info("getCellDescAddresses", "start");
            int[] ranges = splitList(getCellDescAddresses.size(), 1000);
            for (int i = 0; i < ranges.length - 1; i++) {
                List<ElementQueryDetails> temp = getCellDescAddresses.subList(ranges[i], ranges[i + 1]);
                extractDTO = (ExtractDataDTOImpl) prepareCellQuerry(extractDTO);
                results.putAll(buildDescription(temp, extractDTO));
                log.info("getCellDescAddresses", "end");
            }
        }

        mValidMappingBatchLinkKeys.clear();
        return results;
    }

    /**
     * 
     * @param listSize
     * @param partSize
     * @return array of ranges where first element equals 0 and last element equals listSize
     */
    private int[] splitList(int listSize, int partSize) {
        int count = (int) Math.ceil(listSize / (double) partSize);
        int[] ranges = null;
        ranges = new int[count + 1];
        for (int i = 0; i < count; i++) {
            ranges[i] = i * partSize;
        }
        ranges[count] = listSize;
        return ranges;
    }

    private HashMap<String, Object> buildOAResult(EntityList cellValues, List<String> cellLinks) {
        HashMap<String, Object> results = new HashMap<String, Object>();
        Object val;
        for (int i = 0; cellValues != null && i < cellValues.getNumRows(); ++i) {
            int pIndex = ((Integer) cellValues.getValueAt(i, "P_INDEX")).intValue();
            val = cellValues.getValueAt(i, "VAL");
            if (val == null)
                val = "null";
            results.put(cellLinks.get(pIndex), val);
        }

        return results;
    }

    private HashMap<String, Object> buildResult(EntityList cellValues, List<String> cellLinks) {
        HashMap<String, Object> results = new HashMap<String, Object>();
        Object val;
        for (int i = 0; cellValues != null && i < cellValues.getNumRows(); ++i) {
            int pIndex = ((Integer) cellValues.getValueAt(i, "P_INDEX")).intValue();
            val = cellValues.getValueAt(i, "FIELD_VALUE");
            if (val == null)
                val = "null";
            results.put(cellLinks.get(pIndex), val);
        }

        return results;
    }

    private ExtractDataDTO prepareGlobalCellQuerry(ExtractDataDTOImpl extractDTO, String company) throws ValidationException {
        extractDTO.setModelId(this.mModelId);
        extractDTO.setFinanceCubeId(this.mFinanceCubeId);
        extractDTO.setNumDims(this.mNullElementVisIds.length);
        extractDTO.setHierarchyIds(this.mStructureIds);
        extractDTO.setHierarchyVisIds(this.mStructureVisIds);
        extractDTO.setCompany(company);
        return dataEntryDAO.getExtractData(extractDTO);
    }

    private ExtractDataDTO prepareCellQuerry(ExtractDataDTOImpl extractDTO) throws ValidationException {
        extractDTO.setModelId(this.mModelId);
        extractDTO.setFinanceCubeId(this.mFinanceCubeId);
        extractDTO.setNumDims(this.mNullElementVisIds.length);
        extractDTO.setHierarchyIds(this.mStructureIds);
        extractDTO.setHierarchyVisIds(this.mStructureVisIds);
        if (this.mCompany != null) {
            extractDTO.setCompany(this.mCompany);
        }

        return dataEntryDAO.getExtractData(extractDTO);
    }

    private Map<String, String> buildDescription(List<ElementQueryDetails> getCellDescAddresses, ExtractDataDTO extractDataDTO) {
        ConcurrentHashMap<String, String> results = new ConcurrentHashMap<String, String>();
        for (int i = 0; i < getCellDescAddresses.size(); ++i) {
            ElementQueryDetails eqd = (ElementQueryDetails) getCellDescAddresses.get(i);
            String hierarchyVisId = this.mStructureVisIds[eqd.mDimensionIndex];
            String hierarchyElementVisId = eqd.mVisualId;
            String hierarchyElementDescription = extractDataDTO.getElementDescriptionLookup(hierarchyVisId, hierarchyElementVisId);
            if (eqd.mLabelAndDescr) {
                results.put(eqd.mLink, hierarchyElementVisId + ' ' + hierarchyElementDescription);
            } else {
                results.put(eqd.mLink, hierarchyElementDescription);
            }
        }
        return results;
    }

    private String extractCellAddress(String key) {
        String[] address = patternColon.split(key);
        return address[0];
    }

    private String formatCellNoteValue(List<CellNoteRow> cellNoteRows) {
        StringBuilder sb = new StringBuilder();

        for (int i = cellNoteRows.size() - 1; i > -1; --i) {
            CellNoteRow cnr = (CellNoteRow) cellNoteRows.get(i);
            sb.append("(").append(cnr.getUserName());
            sb.append(",").append(this.mDateFormat.format(cnr.getCreated()));
            sb.append(") ").append(cnr.getText());
            if (i != 0) {
                sb.append('\n');
            }
        }

        return sb.toString();
    }

    private String processFinanceCubeExpression(String expression) throws ParseException {
        String[] args = this.extractArgs("cedar.cp.financeCube(", expression);
        if (args.length != 2) {
            throw new ParseException("Invalid number of arguments");
        } else {
            this.mModelVisId = args[0];
            this.mFinanceVisId = args[1];
            return this.lookupModelAndFinanceCube();
        }
    }

    private POIHyperlinkDTO processLinkExpression(String expression) throws ParseException, ValidationException {
        if (this.mModelId == 0) {
            throw new ValidationException("Must call cedar.cp.financeCube(xxx) first");
        } else {
            this.mBudgetCycleVisId = this.extractArgs(expression)[0];
            EntityList bcList = dataEntryDAO.getAllBudgetCyclesWeb();

            for (int sb = 0; sb < bcList.getNumRows(); ++sb) {
                EntityRef result = (EntityRef) bcList.getValueAt(sb, "BudgetCycle");
                if (this.mBudgetCycleVisId.equals(result.getNarrative())) {
                    this.mBudgetCycleId = ((Integer) bcList.getValueAt(sb, "BudgetCycleId")).intValue();
                    break;
                }
            }

            StringBuffer var5 = new StringBuffer();
            var5.append(this.getRootURL());
            var5.append("/reviewBudget.do?modelId=").append(this.mModelId).append("&budgetCycleId=").append(this.mBudgetCycleId);
            var5.append("&topNodeId=").append(this.mCostCenterId);
            POIHyperlinkDTO var6 = new POIHyperlinkDTO();
            var6.setAddress(var5.toString());
            var6.setText(this.mCostCenter + "-" + this.mCostCenterDescription);
            return var6;
        }
    }

    private String getRootURL() {
        if (this.mRootURL == null) {
            EntityList spList = dataEntryDAO.getAllSystemProperties();

            for (int i = 0; i < spList.getNumRows(); ++i) {
                EntityRef ref = (EntityRef) spList.getValueAt(i, "SystemProperty");
                if (ref.getNarrative().equals("WEB: Root URL")) {
                    this.mRootURL = spList.getValueAt(i, "Value").toString();
                    break;
                }
            }
        }

        return this.mRootURL;
    }

    private String lookupModelAndFinanceCube() {
        EntityList modelList = dataEntryDAO.getAllModelsWeb();
        this.mModelId = 0;

        for (int fcList = 0; fcList < modelList.getNumRows(); ++fcList) {
            if (this.mModelVisId.equals(modelList.getValueAt(fcList, "VisId"))) {
                this.mModelId = ((Integer) modelList.getValueAt(fcList, "ModelId")).intValue();
                break;
            }
        }

        if (this.mModelId < 1) {
            return "Unknown model \'" + this.mModelVisId + "\'";
        } else {
            EntityList var4 = dataEntryDAO.getAllFinanceCubesWebForModel(this.mModelId);

            for (int i = 0; i < var4.getNumRows(); ++i) {
                if (this.mFinanceVisId.equals(var4.getValueAt(i, "VisId"))) {
                    this.mFinanceCubeId = ((Integer) var4.getValueAt(i, "FinanceCubeId")).intValue();
                    break;
                }
            }

            return this.mFinanceCubeId < 1 ? "Unknown finance cube \'" + this.mFinanceVisId + "\'" : null;
        }
    }

    public void setCompany(String company) {
        this.mCompany = company;
    }

    public int setModelAndFinanceCubeAndCalendar(String modelVisId, String financeCubeVisId, CalendarInfo calendarInfo) throws ValidationException {
        this.mCalendarInfo = calendarInfo;
        return this.setModelAndFinanceCube(modelVisId, financeCubeVisId);
    }

    public int setModelAndFinanceCube(String modelVisId, String financeCubeVisId) throws ValidationException {
        this.mModelVisId = modelVisId;
        this.mFinanceVisId = financeCubeVisId;
        String msg = this.lookupModelAndFinanceCube();
        if (msg != null) {
            throw new ValidationException(msg);
        } else {
            return dataEntryDAO.getAllDimensionsForModel(this.mModelId).getNumRows();
        }
    }

    public void setHierarchies(String[] hierarchyVisIds) throws ValidationException {
        String msg = this.processStructures(hierarchyVisIds);
        if (msg != null) {
            throw new ValidationException(msg);
        }
    }

    private String processStructuresExpression(String expression) throws ParseException, ValidationException {
        return this.processStructures(this.extractArgs("cedar.cp.structures(", expression));
    }

    private String processStructures(String[] structures) throws ValidationException {
        if (this.mModelId == 0) {
            throw new ValidationException("Must call cedar.cp.financeCube(xxx) first");
        } else {
            EntityList modelDims = dataEntryDAO.getAllDimensionsForModel(this.mModelId);
            if (modelDims.getNumRows() != structures.length) {
                throw new ValidationException("Incorrect number of hierarchy names");
            } else {
                this.mStructureIds = new int[structures.length];
                this.mNullElementVisIds = new String[structures.length];

                for (int i = 0; i < structures.length; ++i) {
                    int dimId = ((Integer) modelDims.getValueAt(i, "DimensionId")).intValue();
                    this.mNullElementVisIds[i] = (String) modelDims.getValueAt(i, "col5");
                    EntityList hierList = dataEntryDAO.getHierarcyDetailsFromDimId(dimId);
                    boolean found = false;

                    for (int h = 0; !found && h < hierList.getNumRows(); ++h) {
                        if (structures[i].equals(hierList.getValueAt(h, "VisId"))) {
                            this.mStructureIds[i] = ((Integer) hierList.getValueAt(h, "HierarchyId")).intValue();
                            found = true;
                        }
                    }

                    if (!found) {
                        return "Unknown structure \'" + structures[i] + "\'";
                    }
                }

                this.mStructureVisIds = structures;
                return null;
            }
        }
    }

    private String processDim0IdentifierExpression() {
        return this.mCostCenter;
    }

    private String processDim0DescriptionExpression() {
        return this.mCostCenterDescription;
    }

    private Object processCellExpression(Map<String, String> arguments) throws ParseException, ValidationException {
        ArrayList<String[]> cellKeyList = new ArrayList<String[]>();
        cellKeyList.add(this.prepareCellKey(arguments));
        EntityList cellValues = dataEntryDAO.getCellValues(this.mFinanceCubeId, this.mNullElementVisIds.length, this.mStructureVisIds, cellKeyList, this.mCompany);
        Object val = null;
        if (cellValues.getNumRows() > 0) {
            val = cellValues.getValueAt(0, "VAL");
        }

        if (val instanceof List) {
            val = this.formatCellNoteValue((List<CellNoteRow>) val);
        }

        return val;
    }

    private String[] prepareOACellKey(MappingValidator mv, String column) throws Exception {
        if (this.mStructureVisIds == null) {
            throw new ValidationException("Must call cedar.cp.structures() first");
        } else {
            Map<String, String> parsedArgMap = mv.getListOfArguments();

            String costCenter = fillCostCenterFromContextIfIsEmpty(parsedArgMap.get(MappingArguments.DIM0.toString()));
            String expenseCode = fillExpenseCodeFromContextIfIsEmpty(parsedArgMap.get(MappingArguments.DIM1.toString()));
            // String dim2Context = "%dim2Identifier%";
            String dateFromContext = (String) mParameters.get(dim2Context);
            String[] date = DateUtil.fillDateFromContextIfIsEmpty(mv, dateFromContext);
            if (date[0] == null) {
                throw new ValidationException("Year is not defined");
            }
            if (date[1] == null || !date[1].contains("pen")) {
                date = DateUtil.calculateDate(1, 12, parsedArgMap, date);
            }
            String[] cellKeyTable = new String[6];// 6 for 4 dims
            cellKeyTable[0] = column; // column to get
            cellKeyTable[1] = costCenter; // dim0 - costcentre
            cellKeyTable[2] = expenseCode; // dim1 - expensecodeq
            cellKeyTable[3] = date[0]; // dim2 - year
            cellKeyTable[4] = date[1]; // dim2 - period
            // if (date.length > 1) {
            // if (!date[0].isEmpty()) {
            // cellKeyTable[3] = date[0]; // dim2 - year
            // cellKeyTable[4] = date[1]; // dim2 - period
            // } else {
            // cellKeyTable[3] = date[1]; // dim2 - year
            // cellKeyTable[4] = date[2]; // dim2 - period
            // }
            // }
            cellKeyTable[5] = parsedArgMap.get(MappingArguments.DIM3.toString()); // dim3 -project
            for (String s: cellKeyTable) {
                if (s == null) {
                    throw new ValidationException("null p exception");
                }
            }
            return cellKeyTable;
        }
    }

    // private Pair<String, String[]> prepareGlobalCurrencyCellKey(MappingValidator mv) throws Exception {
    // String company = null;
    // company = mv.getListOfArguments().get(MappingArguments.CMPY);
    // if(company == null){
    // company = mv.getListOfArguments().get(MappingArguments.COMPANY);
    // }
    // Pair<String, String[]> pair = new Pair<String, String[]>(company, prepareCurrencyCellKey(mv));
    // return pair;
    // }

    private String[] prepareCurrencyCellKey(MappingValidator mv) throws Exception {
        if (this.mStructureVisIds == null) {
            throw new ValidationException("Must call cedar.cp.structures() first");
        } else {
            Map<String, String> parsedArgMap = mv.getListOfArguments();
            String[] cellKeyTable = new String[4];
            String currency = parsedArgMap.get("curr"); // curr

            // String dim2Context = "%dim2Identifier%";
            String dateFromContext = (String) mParameters.get(dim2Context);
            String company = fillCompanyFromContextIfIsEmpty(parsedArgMap.get("cmpy"));
            String[] date = DateUtil.fillDateFromContextIfIsEmpty(mv, dateFromContext);
            if (date[0] == null || currency == null || company == null) {
                throw new ValidationException("Date, currency or company is/are undefined");
            } else if (date[1] == null || date[1].trim().isEmpty()) {
                date[1] = "0";
                date = DateUtil.calculateDate(0, 16, parsedArgMap, date);
            } else if (date[1] == null || !date[1].contains("pen")) {
                date = DateUtil.calculateDate(0, 16, parsedArgMap, date);
            }

            cellKeyTable[0] = currency;
            cellKeyTable[1] = date[0];
            cellKeyTable[2] = date[1];
            cellKeyTable[3] = company;

            return cellKeyTable;
        }
    }

    private String[] prepareAuctionCellKey(MappingValidator mv) throws ParseException, ValidationException {
        if (this.mStructureVisIds == null) {
            throw new ValidationException("Must call cedar.cp.structures() first");
        } else {
            Map<String, String> parsedArgMap = mv.getListOfArguments();

            String[] cellKeyTable = new String[3];
            cellKeyTable[0] = parsedArgMap.get("table");
            cellKeyTable[1] = parsedArgMap.get("key");
            cellKeyTable[2] = parsedArgMap.get("column");

            return cellKeyTable;
        }
    }

    /**
     * @param company
     * @return
     */
    private String fillCompanyFromContextIfIsEmpty(String company) {
        if (company == null || company.equals("?")) {
            company = patternSlash.split(mModelVisId)[0];
        }
        return company;
    }

    // private Pair<String, String[]> prepareGlobalParameterCellKey(MappingValidator mv) throws Exception {
    // String company = null;
    // company = mv.getListOfArguments().get(MappingArguments.CMPY);
    // if(company == null){
    // company = mv.getListOfArguments().get(MappingArguments.COMPANY);
    // }
    // Pair<String, String[]> pair = new Pair<String, String[]>(company, prepareParameterCellKey(mv));
    // return pair;
    // }

    private String[] prepareParameterCellKey(MappingValidator mv) throws Exception {
        if (this.mStructureVisIds == null) {
            throw new ValidationException("Must call cedar.cp.structures() first");
        } else {
            Map<String, String> parsedArgMap = mv.getListOfArguments();

            String[] cellKeyTable = new String[3];
            String company = fillCompanyFromContextIfIsEmpty(parsedArgMap.get("cmpy"));
            String costCenter = null;
            if (parsedArgMap.containsKey(MappingArguments.COST_CENTER.toString())) {
                costCenter = parsedArgMap.get(MappingArguments.COST_CENTER.toString());
            }
            if (parsedArgMap.containsKey(MappingArguments.DIM0.toString())) {
                costCenter = parsedArgMap.get(MappingArguments.DIM0.toString());
            }
            costCenter = fillCostCenterFromContextIfIsEmpty(costCenter);
            String field = parsedArgMap.get("field");

            cellKeyTable[0] = company;
            cellKeyTable[1] = costCenter;
            cellKeyTable[2] = field;

            for (String s: cellKeyTable) {
                if (s == null) {
                    throw new ValidationException("null p exception");
                }
            }
            return cellKeyTable;
        }
    }

    private Pair<String, String[]> prepareGetGlobalCellKey(MappingValidator mv) throws Exception {
        String company = null;
        company = mv.getListOfArguments().get(MappingArguments.CMPY.toString());
        if (company == null) {
            company = mv.getListOfArguments().get(MappingArguments.COMPANY.toString());
        }
        if (company == null){
            throw new ValidationException("No company set for global mapping " + mv.getOrigin());
        }
        Pair<String, String[]> pair = new Pair<String, String[]>(company, prepareGetCellKey(mv));
        return pair;
    }

    private String[] prepareGetCellKey(MappingValidator mv) throws Exception {
        if (this.mStructureVisIds == null) {
            throw new ValidationException("Must call cedar.cp.structures() first");
        }
        Map<String, String> parsedArgMap = mv.getListOfArguments();

        String dataType = parsedArgMap.get("dt");
        if (dataType == null) {
            dataType = this.mParameters.get(WorkbookProperties.DATA_TYPE.toString());
        }

        String valueType = parsedArgMap.get("type");
        if (valueType == null) {
            valueType = "M";
        }

        String costCenter = fillCostCenterFromContextIfIsEmpty(parsedArgMap.get(MappingArguments.DIM0.toString()));
        String expenseCode = fillExpenseCodeFromContextIfIsEmpty(parsedArgMap.get(MappingArguments.DIM1.toString()));
        // String dim2Context = "%dim2Identifier%";
        String dateFromContext = (String) mParameters.get(dim2Context);
        String[] date = DateUtil.fillDateFromContextIfIsEmpty(mv, dateFromContext);
        if (date[0] == null || costCenter == null || expenseCode == null || dataType == null) {
            throw new ValidationException("null p exception");
        }
        if (date[1] == null || !date[1].contains("pen")) {
            date = DateUtil.calculateDate(1, 12, parsedArgMap, date);
        }
        // create cell key (valueType,dim0,dim1,dim2,dataType)
        String[] cellKey = new String[5];
        cellKey[0] = valueType;
        cellKey[1] = costCenter;
        cellKey[2] = expenseCode;
        cellKey[3] = DateUtil.dateToString(date);
        cellKey[4] = dataType;

        return cellKey;
    }

    private String fillCostCenterFromContextIfIsEmpty(String actualCostCenter) {
        if ((actualCostCenter == null) || (actualCostCenter.length() == 0)) {
            String dimContext = WorkbookProperties.DIMENSION_0_VISID.toString();
            String costCenterFromContext = (String) mParameters.get(dimContext);
            actualCostCenter = costCenterFromContext;
        }
        return actualCostCenter;
    }

    private String fillExpenseCodeFromContextIfIsEmpty(String actualExpenseCode) {
        if ((actualExpenseCode == null) || (actualExpenseCode.length() == 0)) {
            String dimContext = WorkbookProperties.DIMENSION_1_VISID.toString();
            String expenseCodeFromContext = (String) mParameters.get(dimContext);
            actualExpenseCode = expenseCodeFromContext;
        }
        return actualExpenseCode;
    }

    private String[] prepareCellKey(Map<String, String> arguments) throws ParseException, ValidationException {
        if (this.mStructureVisIds == null) {
            throw new ValidationException("Must call cedar.cp.structures() first");
        }
        String[] cellKey = new String[2 + this.mStructureVisIds.length];

        if (arguments.containsKey(MappingArguments.TYPE)) {
            cellKey[0] = arguments.get(MappingArguments.TYPE.toString());
        }
        if (arguments.containsKey(MappingArguments.DIM0.toString())) {
            cellKey[1] = arguments.get(MappingArguments.DIM0.toString());
        } else {
            cellKey[1] = this.mCostCenter;
        }
        if (arguments.containsKey(MappingArguments.DIM1.toString())) {
            cellKey[2] = arguments.get(MappingArguments.DIM1.toString());
        }
        if (arguments.containsKey(MappingArguments.DIM2.toString())) {
            cellKey[3] = arguments.get(MappingArguments.DIM2.toString());
        }
        if (arguments.containsKey(MappingArguments.DATA_TYPE.toString())) {
            cellKey[4] = arguments.get(MappingArguments.DATA_TYPE.toString());
        }

        return cellKey;

    }

    private ElementQueryDetails prepareGetCellDimVisuals(Map<String, String> arguments) throws ParseException, ValidationException {
        String visId = null;
        if (arguments.size() != 1) {
            throw new ValidationException("Expected single parameter to getDescr() method found " + arguments.size());
        } else {
            ElementQueryDetails var11 = new ElementQueryDetails();
            for (String s: arguments.keySet()) {
                visId = arguments.get(s);
                var11.dim = s;
            }

            var11.mDimensionIndex = Character.getNumericValue(var11.getDimIndex());
            var11.mVisualId = visId;
            return var11;
        }
    }

    private String processParamExpression(String expression) throws ParseException {
        StringBuilder buffer = new StringBuilder();
        String[] args = this.extractArgs("cedar.cp.param(", expression);
        String[] arr$ = args;
        int len$ = args.length;

        for (int i$ = 0; i$ < len$; ++i$) {
            String key = arr$[i$];
            String param = (String) this.mParameters.get(key);
            if (param != null) {
                if (buffer.length() > 0) {
                    buffer.append(',');
                }

                buffer.append(param);
            }
        }

        return buffer.toString();
    }

    private String getYearVisId(String fullPathVisId) throws ValidationException {
        CalendarElementNode calendarNode = this.mCalendarInfo.findElement(fullPathVisId);
        calendarNode = calendarNode.getYearNode();
        return calendarNode != null ? calendarNode.getVisId() : null;
    }

    private String processGetVisIdsExpression(Map<String, String> arguments) throws Exception {
        String visId = null;

        if (arguments.containsKey(MappingArguments.DIM0.toString())) {
            visId = arguments.get(MappingArguments.DIM0.toString());
            visId = fillCostCenterFromContextIfIsEmpty(visId);
        } else if (arguments.containsKey(MappingArguments.DIM1.toString())) {
            visId = arguments.get(MappingArguments.DIM1.toString());
            visId = fillExpenseCodeFromContextIfIsEmpty(visId);
        } else if (arguments.containsKey(MappingArguments.DIM2.toString())) {
            visId = arguments.get(MappingArguments.DIM2.toString());
            // String dim2Context = "%dim2Identifier%";
            String dateFromContext = (String) mParameters.get(dim2Context);
            String[] date = DateUtil.fillDateFromContextIfIsEmpty(visId, dateFromContext);
            date = DateUtil.calculateDate(1, 12, arguments, date);
            visId = DateUtil.dateToString(date);
        }
        if (visId == null) {
            throw new ValidationException("nul p exception");
        }
        return visId;
    }

    private String processDescriptionExpression(Map<String, String> arguments) throws ParseException, ValidationException {
        int dimIndex = 0;
        int yearOffset = 0;
        int periodOffset = 0;
        String visId = null;
        String description = "";

        if (arguments.containsKey(MappingArguments.DIM0.toString())) {
            visId = arguments.get(MappingArguments.DIM0.toString());
            dimIndex = 0;
        } else if (arguments.containsKey(MappingArguments.DIM1.toString())) {
            visId = arguments.get(MappingArguments.DIM1.toString());
            dimIndex = 1;
        } else if (arguments.containsKey(MappingArguments.DIM2.toString())) {
            visId = arguments.get(MappingArguments.DIM2.toString());
            dimIndex = 2;
            if (arguments.containsKey(MappingArguments.YEAR.toString())) {
                yearOffset = Integer.parseInt(arguments.get(MappingArguments.YEAR.toString()));
            }
            if (arguments.containsKey(MappingArguments.PERIOD.toString())) {
                periodOffset = Integer.parseInt(arguments.get(MappingArguments.PERIOD.toString()));
            }
        }

        if (visId == null) {
            visId = this.getContextDimensionVisId(dimIndex);
        }

        CalendarElementNode var14;
        CalendarElementNode var15;
        if (visId != null && visId.indexOf("?") != -1) {
            String var13 = this.getYearVisId(this.getContextDimensionVisId(dimIndex));
            visId = visId.replaceFirst("\\?", var13);
            var15 = this.mCalendarInfo.findElement(visId);
            if (var15 != null) {
                description = var15.getDescription();
            }
        } else if (visId != null) {
            var14 = this.mCalendarInfo.findElement(visId);
            if (var14 != null) {
                description = var14.getDescription();
            }
        } else {
            description = this.getContextDimensionDescription(dimIndex);
        }

        if (yearOffset != 0 || periodOffset != 0) {
            var14 = this.mCalendarInfo.findElement(visId);
            if (var14 != null) {
                var15 = this.mCalendarInfo.getElementOffsetByYearAndPeriod(var14.getStructureElementId(), yearOffset, periodOffset);
                if (var15 != null) {
                    var14 = var15;
                }
            }

            if (var14 != null) {
                description = var14.getDescription();
            }
        }

        return description;
    }

    private String getContextDimensionDescription(int dimIndex) {
        return (String) this.mParameters.get(WorkbookProperties.DIMENSION_$_DESCRIPTION.toString().replace("$", String.valueOf(dimIndex)));
    }

    private String getContextDimensionVisId(int dimIndex) {
        return (String) this.mParameters.get(WorkbookProperties.DIMENSION_$_VISID.toString().replace("$", String.valueOf(dimIndex)));
    }

    private String processGetLabelExpression(Map<String, String> arguments) throws Exception {
        StringBuilder builder = new StringBuilder();
        String visId = this.processGetVisIdsExpression(arguments);
        String desc = this.processDescriptionExpression(arguments);
        if (visId != null && desc != null) {
            builder.append(visId).append("-").append(desc);
        }

        return builder.toString();
    }

    @Deprecated
    private String[] extractArgs(String type, String expression) throws ParseException {
        String middle = expression.substring(type.length());
        int end = middle.indexOf(41);
        // if (end <= 0 && !expression.startsWith("cedar.cp.getCell(")) {
        if (end <= 0) {
            throw new ParseException("Unable to extract arguments");
        } else {
            String argsString = middle.substring(0, end);
            return patternComma.split(argsString);
        }
    }

    private String[] extractArgs(String expression) throws ParseException {
        int begin = expression.indexOf(40);
        int end = expression.indexOf(41);
        // if (end <= 0 && !expression.startsWith("cedar.cp.getCell(")) {
        if (end <= 0) {
            throw new ParseException("Unable to extract arguments");
        } else {
            String argsString = expression.substring(begin + 1, end);
            return patternComma.split(argsString);
        }
    }
}
