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
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.softproideas.util.validation.MappingArguments;
import com.softproideas.util.validation.MappingValidator;

public class CPFunctionsParametersData extends CPFunctionsData {
    private List<String[]> paramCellAddresses = new ArrayList<String[]>();
    private List<String> paramCellLinks = new ArrayList<String>();
    private Map<String, String> workbookProperties;
    private CPFunctionDao dataEntryDAO;
    private String modelVisId;

    public CPFunctionsParametersData(CPFunctionDao dataEntryDAO, String modelVisId, Map<String, String> workbookProperties) {
        this.dataEntryDAO = dataEntryDAO;
        this.modelVisId = modelVisId;
        this.workbookProperties = workbookProperties;
    }

    @Override
    public void add(MappingValidator mv, String id) throws ValidationException {
        String[] prepareParameterCellKey = prepareParameterCellKey(mv);
        paramCellAddresses.add(prepareParameterCellKey);
        paramCellLinks.add(id);
    }

    @Override
    public void add(String expression, String id) throws ValidationException {
        MappingValidator mv = null;
        mv = new MappingValidator(expression);
        add(mv, id);
    }

    @Override
    public Map<String, String> submit() throws ValidationException {
        Map<String, String> results = new HashMap<String, String>();
        if (paramCellAddresses.size() > 0) {
            int[] ranges = splitList(paramCellAddresses.size(), 100);
            for (int i = 0; i < ranges.length - 1; i++) {
                List<String[]> temp = paramCellAddresses.subList(ranges[i], ranges[i + 1]);
                List<String> paramCellLinksPart = paramCellLinks.subList(ranges[i], ranges[i + 1]);
                // log.info("paramCellAddresses size " + temp.size(), "start");
                EntityList cellValues = null;
                cellValues = dataEntryDAO.getParameterCellValues(temp);
                results.putAll(buildResult(cellValues, paramCellLinksPart));
                // log.info("paramCellAddresses", "end");
            }
        }
        return results;
    }

    private Map<String, String> buildResult(EntityList cellValues, List<String> cellLinks) {
        Map<String, String> results = new HashMap<String, String>();
        Object val;
        for (int i = 0; cellValues != null && i < cellValues.getNumRows(); ++i) {
            int pIndex = ((Integer) cellValues.getValueAt(i, "P_INDEX")).intValue();
            val = cellValues.getValueAt(i, "FIELD_VALUE");
            if (val == null)
                val = "null";
            results.put(cellLinks.get(pIndex), val.toString());
        }

        return results;
    }

    private String[] prepareParameterCellKey(MappingValidator mv) throws ValidationException {
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
        String costCenterFromContext = workbookProperties.get(WorkbookProperties.DIMENSION_0_VISID.toString());
        costCenter = fillCostCenterFromContextIfIsEmpty(costCenter, costCenterFromContext);
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

    private String fillCompanyFromContextIfIsEmpty(String company) {
        if (company == null || company.trim().isEmpty() || company.equals("?")) {
            company = patternSlash.split(modelVisId)[0];
        }
        return company;
    }

}
