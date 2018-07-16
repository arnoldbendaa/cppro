package com.softproideas.api.cpfunctionsevaluator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dataEntry.CellNoteRow;
import com.cedar.cp.api.facades.POIHyperlinkDTO;
import com.cedar.cp.api.facades.ParseException;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.softproideas.util.validation.MappingArguments;
import com.softproideas.util.validation.MappingValidator;

public class CPFunctions {

    private Map<String, String> mParameters = new HashMap<String, String>();
    
    private String mModelVisId; 
    private CalendarInfo mCalendarInfo;
    private Pattern patternSlash = Pattern.compile("/");
    
    public String[] prepareOACellKey(MappingValidator mv, String column, String address) throws ValidationException {
            Map<String, String> parsedArgMap = mv.getListOfArguments();

            String costCenter = fillCostCenterFromContextIfIsEmpty(parsedArgMap.get(MappingArguments.DIM0.toString()));
            String expenseCode = fillExpenseCodeFromContextIfIsEmpty(parsedArgMap.get(MappingArguments.DIM1.toString()));
            String dateFromContext = mParameters.get(WorkbookProperties.DIMENSION_2_VISID.toString());
            String[] date = DateUtil.fillDateFromContextIfIsEmpty(mv, dateFromContext);
            if (date[0] == null) {
                throw new ValidationException("Year is not defined");
            }
            date = DateUtil.calculateDate(1, 12, parsedArgMap, date);
            String[] cellKeyTable = new String[6];// 6 for 4 dims
            cellKeyTable[0] = column; // column to get
            cellKeyTable[1] = costCenter; // dim0 - costcentre
            cellKeyTable[2] = expenseCode; // dim1 - expensecodeq
            cellKeyTable[3] = date[0]; // dim2 - year
            cellKeyTable[4] = date[1]; // dim2 - month
            cellKeyTable[5] = parsedArgMap.get(MappingArguments.DIM3.toString()); // dim3 -project
            for (String s: cellKeyTable) {
                if (s == null) {
                    throw new ValidationException("null p exception");
                }
            }
            return cellKeyTable;
    }

    public String[] prepareCurrencyCellKey(MappingValidator mv, String address) throws ValidationException {
            Map<String, String> parsedArgMap = mv.getListOfArguments();
            String[] cellKeyTable = new String[4];
            String currency = parsedArgMap.get("curr");
            String dateFromContext = (String) mParameters.get(WorkbookProperties.DIMENSION_2_VISID.toString());
            String company = fillCompanyFromContextIfIsEmpty(parsedArgMap.get("cmpy"));
            String[] date = DateUtil.fillDateFromContextIfIsEmpty(mv, dateFromContext);
            if (date[0] == null || date[1] == null || currency == null || company == null) {
                throw new ValidationException("null p exception");
            }
            date = DateUtil.calculateDate(0, 16, parsedArgMap, date);

            cellKeyTable[0] = currency;
            cellKeyTable[1] = date[0];
            cellKeyTable[2] = date[1];
            cellKeyTable[3] = company;

            return cellKeyTable;
        
    }

    public String[] prepareAuctionCellKey(MappingValidator mv){
            Map<String, String> parsedArgMap = mv.getListOfArguments();

            String[] cellKeyTable = new String[3];
            cellKeyTable[0] = parsedArgMap.get("table");
            cellKeyTable[1] = parsedArgMap.get("key");
            cellKeyTable[2] = parsedArgMap.get("column");

            return cellKeyTable;
        
    }

    public String[] prepareParameterCellKey(MappingValidator mv, String address) throws ValidationException{
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
             
            for(String s: cellKeyTable){
                if(s == null){
                    throw new ValidationException("null p exception");
                }
            }
            return cellKeyTable;
        
    }

    public String[] prepareGetCellKey(MappingValidator mv, String address) throws ValidationException {
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
        String dateFromContext = (String) mParameters.get(WorkbookProperties.DIMENSION_2_VISID.toString());
        String[] date = DateUtil.fillDateFromContextIfIsEmpty(mv, dateFromContext);
        if (date[0] == null || costCenter == null || expenseCode == null || dataType == null) {
            throw new ValidationException("null p exception");
        }
        date = DateUtil.calculateDate(1, 12, parsedArgMap, date);

        // create cell key (valueType,dim0,dim1,dim2,dataType)
        String[] cellKey = new String[5];
        cellKey[0] = valueType;
        cellKey[1] = costCenter;
        cellKey[2] = expenseCode;
        cellKey[3] = DateUtil.dateToString(date);
        cellKey[4] = dataType;

        return cellKey;
    }

    public String[] prepareCellKey(Map<String, String> arguments){
        String[] cellKey = new String[5];

        if (arguments.containsKey(MappingArguments.TYPE)) {
            cellKey[0] = arguments.get(MappingArguments.TYPE.toString());
        }
        if (arguments.containsKey(MappingArguments.DIM0.toString())) {
            cellKey[1] = arguments.get(MappingArguments.DIM0.toString());
        } else {
            cellKey[1] = this.mParameters.get(WorkbookProperties.DIMENSION_0_VISID.toString());
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
    


    public String processGetVisIdsExpression(Map<String, String> arguments) throws ValidationException{
        String visId = null;

        if (arguments.containsKey(MappingArguments.DIM0.toString())) {
            visId = arguments.get(MappingArguments.DIM0.toString());
            visId = fillCostCenterFromContextIfIsEmpty(visId);
        } else if (arguments.containsKey(MappingArguments.DIM1.toString())) {
            visId = arguments.get(MappingArguments.DIM1.toString());
            visId = fillExpenseCodeFromContextIfIsEmpty(visId);
        } else if (arguments.containsKey(MappingArguments.DIM2.toString())) {
            visId = arguments.get(MappingArguments.DIM2.toString());
            String dateFromContext = (String) mParameters.get(WorkbookProperties.DIMENSION_2_VISID.toString());
            String[] date = DateUtil.fillDateFromContextIfIsEmpty(visId, dateFromContext);
            date = DateUtil.calculateDate(1, 12, arguments, date);
            visId = DateUtil.dateToString(date);
        }
        if (visId == null) {
            throw new ValidationException("nul p exception");
        }
        return visId;
    }

    public String processDescriptionExpression(Map<String, String> arguments) throws ValidationException{
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
            visId = mParameters.get(WorkbookProperties.DIMENSION_$_VISID.toString().replace("$", String.valueOf(dimIndex)));
        }

        CalendarElementNode var14;
        CalendarElementNode var15;
        if (dimIndex == 2 && visId != null && visId.indexOf("?") != -1) {
            String var13 = this.getYearVisId(mParameters.get(WorkbookProperties.DIMENSION_2_VISID.toString()));
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
            description = mParameters.get(WorkbookProperties.DIMENSION_$_DESCRIPTION.toString().replace("$", String.valueOf(dimIndex)));
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
    
    private String mFinanceVisId;
    private int mModelId;
    private Object mBudgetCycleVisId;
    private int mBudgetCycleId;
    private Object mCostCenterId;
    private String mCostCenter;
    private String mCostCenterDescription;
    private String mRootURL;
    private int mFinanceCubeId;
    
    private CPFunctionDao dao;

    private int[] mStructureIds;

    private String[] mNullElementVisIds;

    private String[] mStructureVisIds;

    private String mCompany;
    
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
            EntityList bcList = dao.getAllBudgetCyclesWeb();

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
            EntityList spList = dao.getAllSystemProperties();

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
        EntityList modelList = dao.getAllModelsWeb();
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
            EntityList var4 = dao.getAllFinanceCubesWebForModel(this.mModelId);

            for (int i = 0; i < var4.getNumRows(); ++i) {
                if (this.mFinanceVisId.equals(var4.getValueAt(i, "VisId"))) {
                    this.mFinanceCubeId = ((Integer) var4.getValueAt(i, "FinanceCubeId")).intValue();
                    break;
                }
            }

            return this.mFinanceCubeId < 1 ? "Unknown finance cube \'" + this.mFinanceVisId + "\'" : null;
        }
    }
    
    private String processStructuresExpression(String expression) throws ParseException, ValidationException {
        return this.processStructures(this.extractArgs("cedar.cp.structures(", expression));
    }

    private String processStructures(String[] structures) throws ValidationException {
        if (this.mModelId == 0) {
            throw new ValidationException("Must call cedar.cp.financeCube(xxx) first");
        } else {
            EntityList modelDims = dao.getAllDimensionsForModel(this.mModelId);
            if (modelDims.getNumRows() != structures.length) {
                throw new ValidationException("Incorrect number of hierarchy names");
            } else {
                this.mStructureIds = new int[structures.length];
                this.mNullElementVisIds = new String[structures.length];

                for (int i = 0; i < structures.length; ++i) {
                    int dimId = ((Integer) modelDims.getValueAt(i, "DimensionId")).intValue();
                    this.mNullElementVisIds[i] = (String) modelDims.getValueAt(i, "col5");
                    EntityList hierList = dao.getHierarcyDetailsFromDimId(dimId);
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
        EntityList cellValues = dao.getCellValues(this.mFinanceCubeId, this.mNullElementVisIds.length, this.mStructureVisIds, cellKeyList, this.mCompany);
        Object val = null;
        if (cellValues.getNumRows() > 0) {
            val = cellValues.getValueAt(0, "VAL");
        }

        if (val instanceof List) {
            val = this.formatCellNoteValue((List<CellNoteRow>) val);
        }

        return val;
    }
    private SimpleDateFormat mDateFormat = new SimpleDateFormat("ddMMMyy");
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
    private Pattern patternComma = Pattern.compile(",");
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
    
    
    private String getYearVisId(String fullPathVisId){
        CalendarElementNode calendarNode = this.mCalendarInfo.findElement(fullPathVisId);
        calendarNode = calendarNode.getYearNode();
        return calendarNode != null ? calendarNode.getVisId() : null;
    }
    
    private String fillCompanyFromContextIfIsEmpty(String company) {
        if (company == null || company.equals("?")) {
            company = patternSlash.split(mModelVisId)[0];
        }
        return company;
    }
    
    private String fillCostCenterFromContextIfIsEmpty(String actualCostCenter) {
        if ((actualCostCenter == null) || (actualCostCenter.length() == 0)) {
            String costCenterFromContext = mParameters.get(WorkbookProperties.DIMENSION_0_VISID.toString());
            actualCostCenter = costCenterFromContext;
        }
        return actualCostCenter;
    }

    private String fillExpenseCodeFromContextIfIsEmpty(String actualExpenseCode) {
        if ((actualExpenseCode == null) || (actualExpenseCode.length() == 0)) {
            String expenseCodeFromContext = mParameters.get(WorkbookProperties.DIMENSION_1_VISID.toString());
            actualExpenseCode = expenseCodeFromContext;
        }
        return actualExpenseCode;
    }
    
    public void addParameter(String key, String value){
        mParameters.put(key, value);
    }

    public void setmParameters(Map<String, String> mParameters) {
        this.mParameters.putAll(mParameters);
    }

    public void setmModelVisId(String mModelVisId) {
        this.mModelVisId = mModelVisId;
    }

    public void setmCalendarInfo(CalendarInfo mCalendarInfo) {
        this.mCalendarInfo = mCalendarInfo;
    }
}
