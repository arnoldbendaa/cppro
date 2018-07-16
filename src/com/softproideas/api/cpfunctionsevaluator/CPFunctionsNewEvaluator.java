/*******************************************************************************
 * Copyright ©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
 * Reserved.
 *
 * Republication, redistribution, granting a license to other parties, using,
 * copying, modifying this software and its documentation is prohibited without the
 * prior written consent of IT Services Jacek Kurasiewicz.
 * Contact The Office of IT Services Jacek Kurasiewicz, ul. Koszykowa 60/62 lok.
 * 43, 00-673 Warszawa, jk@softpro.pl, +48 512-25-67-67, for commercial licensing
 * opportunities.
 *
 * IN NO EVENT SHALL IT SERVICES JACEK KURASIEWICZ BE LIABLE TO ANY PARTY FOR
 * DIRECT, INDIRECT, SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST
 * PROFITS, ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
 * IT SERVICES JACEK KURASIEWICZ HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 *
 * IT SERVICES JACEK KURASIEWICZ SPECIFICALLY DISCLAIMS ANY WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE. THE SOFTWARE AND ACCOMPANYING DOCUMENTATION, IF ANY,
 * PROVIDED HEREUNDER IS PROVIDED "AS IS". IT SERVICES JACEK KURASIEWICZ HAS NO
 * OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS, OR
 * MODIFICATIONS.
 *******************************************************************************/
package com.softproideas.api.cpfunctionsevaluator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.facades.ExtractDataDTOImpl;
import com.cedar.cp.util.Pair;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.softproideas.util.validation.GlobalMappedMappingValidator;
import com.softproideas.util.validation.MappingArguments;
import com.softproideas.util.validation.MappingFunction;
import com.softproideas.util.validation.MappingValidator;

public class CPFunctionsNewEvaluator implements CPFunctionsEvaluator {

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

    public CPFunctionsNewEvaluator(CPFunctionDao dao) {
        this.dataEntryDAO = dao;
    }

    @Override
    public Object processExpression(String expression) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    private enum ExpresionTypes {
        CURRENCY, OPEN_ACCOUNT, PARAMETERS, AUCTION, CELL, GLOBAL_CELL, DESCRIPTION
    }

    private Map<ExpresionTypes, CPFunctionsData> expressions;

    @Override
    public void addBatchExpression(String expression, String cell) throws Exception {
        if (dataEntryDAO == null) {
            throw new Exception("System error: dataEntryDAO is not set");
        }
        if (expressions == null) {
            expressions = new HashMap<CPFunctionsNewEvaluator.ExpresionTypes, CPFunctionsData>();
        }
        MappingValidator mv = null;
        boolean isGlobal = CPFunctionsData.isGlobal(mModelVisId);
        if (isGlobal) {
            mv = new GlobalMappedMappingValidator(expression);
        } else {
            mv = new MappingValidator(expression);
        }
        //TODO factory
        switch (mv.getFunction()) {
            case GET_BASE_VAL:
            case GET_QUANTITY:
            case GET_CUM_BASE_VAL:
            case GET_CUM_QUANTITY: {
                CPFunctionsData data = null;
                if (expressions.containsKey(ExpresionTypes.OPEN_ACCOUNT) == false) {
                    data = new CPFunctionsOAData(dataEntryDAO, mModelVisId, mParameters);
                    expressions.put(ExpresionTypes.OPEN_ACCOUNT, data);
                } else {
                    data = expressions.get(ExpresionTypes.OPEN_ACCOUNT);
                }
                data.add(mv, cell);
                break;
            }
            // currencyExpression
            case GET_CURRENCY_LOOKUP: {
                CPFunctionsData data = null;
                if (expressions.containsKey(ExpresionTypes.CURRENCY) == false) {
                    data = new CPFunctionsCurrencyData(dataEntryDAO, mModelVisId, mParameters);
                    expressions.put(ExpresionTypes.CURRENCY, data);
                } else {
                    data = expressions.get(ExpresionTypes.CURRENCY);
                }
                data.add(mv, cell);
                break;
            }
            // parameterExpression
            case GET_PARAMETER_LOOKUP: {
                CPFunctionsData data = null;
                if (expressions.containsKey(ExpresionTypes.PARAMETERS) == false) {
                    data = new CPFunctionsParametersData(dataEntryDAO, mModelVisId, mParameters);
                    expressions.put(ExpresionTypes.PARAMETERS, data);
                } else {
                    data = expressions.get(ExpresionTypes.PARAMETERS);
                }
                data.add(mv, cell);
                break;
            }// auctionExpression
            case GET_AUCTION_LOOKUP: {
                CPFunctionsData data = null;
                if (expressions.containsKey(ExpresionTypes.AUCTION) == false) {
                    data = new CPFunctionsAuctionData(dataEntryDAO);
                    expressions.put(ExpresionTypes.AUCTION, data);
                } else {
                    data = expressions.get(ExpresionTypes.AUCTION);
                }
                data.add(mv, cell);
                break;
            }
            case GET_GLOB:
            case PUT_CELL:
            case GET_CELL: {
                if (mFinanceVisId == null || mFinanceVisId.trim().isEmpty()) {
                    throw new ValidationException("financeCubeVisId is not set");
                }
                if (isGlobal) {
                    CPFunctionsData data = null;
                    if (expressions.containsKey(ExpresionTypes.GLOBAL_CELL) == false) {
                        data = new CPFunctionsGlobalCellData(dataEntryDAO, mModelVisId, mFinanceVisId, mParameters, mStructureVisIds);
                        expressions.put(ExpresionTypes.GLOBAL_CELL, data);
                    } else {
                        data = expressions.get(ExpresionTypes.GLOBAL_CELL);
                    }
                    data.add(mv, cell);
                } else {
                    CPFunctionsData data = null;
                    if (expressions.containsKey(ExpresionTypes.CELL) == false) {
                        data = new CPFunctionsCellData(dataEntryDAO, mModelVisId, mFinanceVisId, mParameters, mStructureVisIds);
                        expressions.put(ExpresionTypes.CELL, data);
                    } else {
                        data = expressions.get(ExpresionTypes.CELL);
                    }
                    data.add(mv, cell);
                }
                break;
            }
            case GET_VIS_ID:
            case GET_DESCRIPTION:
            case GET_LABEL: {
                CPFunctionsData data = null;
                if (expressions.containsKey(ExpresionTypes.DESCRIPTION) == false) {
                    data = new CPFunctionDescriptionData(dataEntryDAO, mModelVisId, mParameters, mStructureVisIds);
                    expressions.put(ExpresionTypes.DESCRIPTION, data);
                } else {
                    data = expressions.get(ExpresionTypes.DESCRIPTION);
                }
                data.add(mv, cell);
                break;

            }
            default: {

            }
        }
    }

    @Override
    public HashMap<String, Object> submitBatch() throws Exception {
        if (this.mStructureVisIds == null) {
            throw new ValidationException("No hierarchies in worksheets are set for model vis. ID. " + this.mModelVisId);
        }
        HashMap<String, Object> results = new HashMap<String, Object>();
        for (CPFunctionsData cp: expressions.values()) {
            Map<String, String> part = cp.submit();
            results.putAll(part);
        }
        return results;
    }

    @Override
    public int setModelAndFinanceCubeAndCalendar(String modelVisId, String financeCubeVisId, CalendarInfo calendarInfo) throws ValidationException {
        this.mCalendarInfo = calendarInfo;
        return this.setModelAndFinanceCube(modelVisId, financeCubeVisId);
    }

    @Override
    public int setModelAndFinanceCube(String modelVisId, String financeCubeVisId) throws ValidationException {
        this.mModelVisId = modelVisId;
        this.mFinanceVisId = financeCubeVisId;
        // String msg = this.lookupModelAndFinanceCube();
        // if (msg != null) {
        // throw new ValidationException(msg);
        // } else {
        // return dataEntryDAO.getAllDimensionsForModel(this.mModelId).getNumRows();
        // }
        return 3;
    }

    @Override
    public void setHierarchies(String[] hierarchyVisIds) throws ValidationException {
        mStructureVisIds = hierarchyVisIds;
    }

    @Override
    public void setCostCenterId(int costCenterId) {
        this.mCostCenterId = costCenterId;

    }

    @Override
    public void setCostCenter(String costCenter) {
        this.mCostCenter = costCenter;
        this.mParameters.put(WorkbookProperties.DIMENSION_0_VISID.toString(), costCenter);
    }

    @Override
    public void setCostCenterDescription(String costCenterDescription) {
        this.mCostCenterDescription = costCenterDescription;
        this.mParameters.put(WorkbookProperties.DIMENSION_0_DESCRIPTION.toString(), costCenterDescription);
    }

    @Override
    public void setParameters(Map<String, String> parameters) {
        if (mParameters == null) {
            mParameters = new HashMap<String, String>();
        }
        this.mParameters.putAll(parameters);
    }

    @Override
    public void setCompany(String company) {
        this.mCompany = company;

    }

    @Override
    public String getDataType() {
        return this.mParameters.get(CONTEXT_DATATYPE_PREFIX);
    }

}
