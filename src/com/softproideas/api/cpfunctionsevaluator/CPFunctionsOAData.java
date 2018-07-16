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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.util.NumberUtils;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.softproideas.util.validation.MappingArguments;
import com.softproideas.util.validation.MappingFunction;
import com.softproideas.util.validation.MappingValidator;

public class CPFunctionsOAData extends CPFunctionsData {

    private List<String[]> oaCellAddresses = new ArrayList<String[]>();
    private List<String> oaCellLinks = new ArrayList<String>();
    private Map<String, String> workbookProperties;
    private CPFunctionDao dataEntryDAO;
    private String modelVisId;

    public CPFunctionsOAData(CPFunctionDao dataEntryDAO, String modelVisId, Map<String, String> workbookProperties) {
        this.dataEntryDAO = dataEntryDAO;
        this.modelVisId = modelVisId;
        this.workbookProperties = workbookProperties;
    }

    public void add(MappingValidator mv, String id) throws ValidationException {
        String[] prepareOACellKey = prepareOACellKey(mv);
        oaCellAddresses.add(prepareOACellKey);
        oaCellLinks.add(id);
    }

    public void add(String expression, String id) throws ValidationException {
        MappingValidator mv = null;
        mv = new MappingValidator(expression);
        add(mv, id);
    }

    public Map<String, String> submit() throws ValidationException {
        Map<String, String> results = new HashMap<String, String>();
        if (oaCellAddresses.size() > 0) {
            // Log log = new Log(this.getClass());
            // log.info("oaCellAddresses", "start");
            String[] model = patternSlash.split(modelVisId);
            if (NumberUtils.isNumber(model[0])) {
                int[] ranges = splitList(oaCellAddresses.size(), 100);
                for (int i = 0; i < ranges.length - 1; i++) {
                    List<String[]> temp = oaCellAddresses.subList(ranges[i], ranges[i + 1]);
                    List<String> oaCellLinksPart = oaCellLinks.subList(ranges[i], ranges[i + 1]);
                    EntityList cellValues = null;
                    cellValues = dataEntryDAO.getOACellValues(Integer.parseInt(model[0]), temp);
                    results.putAll(buildOAResult(cellValues, oaCellLinksPart));
                }
            }
            // log.info("oaCellAddresses", "end");
        }
        return results;
    }

    private String[] prepareOACellKey(MappingValidator mv) throws ValidationException {
        Map<String, String> parsedArgMap = mv.getListOfArguments();

        String costCenterFromContext = workbookProperties.get(WorkbookProperties.DIMENSION_0_VISID.toString());
        String costCenter = fillCostCenterFromContextIfIsEmpty(parsedArgMap.get(MappingArguments.DIM0.toString()), costCenterFromContext);

        String expenseCodeFromContext = workbookProperties.get(WorkbookProperties.DIMENSION_1_VISID.toString());
        String expenseCode = fillExpenseCodeFromContextIfIsEmpty(parsedArgMap.get(MappingArguments.DIM1.toString()), expenseCodeFromContext);

        String dim2Context = WorkbookProperties.DIMENSION_2_VISID.toString();
        String dateFromContext = workbookProperties.get(dim2Context);
        String[] date = DateUtil.fillDateFromContextIfIsEmpty(mv, dateFromContext);
        if (date[0] == null) {
            throw new ValidationException("Year is not defined");
        }
        if (date[1] == null || !date[1].contains("pen")) {
            date = DateUtil.calculateDate(1, 12, parsedArgMap, date);
        }
        String[] cellKeyTable = new String[6];// 6 for 4 dims
        cellKeyTable[0] = getColumn(mv.getFunction()); // column to get
        cellKeyTable[1] = costCenter; // dim0 - costcentre
        cellKeyTable[2] = expenseCode; // dim1 - expensecodeq
        cellKeyTable[3] = date[0]; // dim2 - year
        cellKeyTable[4] = date[1]; // dim2 - period
        cellKeyTable[5] = parsedArgMap.get(MappingArguments.DIM3.toString()); // dim3 -project
        for (String s: cellKeyTable) {
            if (s == null) {
                throw new ValidationException("Dim 3 is not defined for OAExpression");
            }
        }
        return cellKeyTable;
    }

    private String getColumn(MappingFunction function) throws ValidationException {
        switch (function) {
            case GET_BASE_VAL:
                return "BASEVAL";
            case GET_QUANTITY:
                return "QTY";
            case GET_CUM_BASE_VAL:
                return "CUMBASEVAL";
            case GET_CUM_QUANTITY:
                return "CUMQTY";
            default:
                throw new ValidationException("Wrong function set for OAExpression");
        }
    }

    private Map<String, String> buildOAResult(EntityList cellValues, List<String> cellLinks) {
        Map<String, String> results = new HashMap<String, String>();
        Object val;
        for (int i = 0; cellValues != null && i < cellValues.getNumRows(); ++i) {
            int pIndex = ((Integer) cellValues.getValueAt(i, "P_INDEX")).intValue();
            val = cellValues.getValueAt(i, "VAL");
            if (val == null)
                val = "null";
            results.put(cellLinks.get(pIndex), val.toString());
        }

        return results;
    }

}
