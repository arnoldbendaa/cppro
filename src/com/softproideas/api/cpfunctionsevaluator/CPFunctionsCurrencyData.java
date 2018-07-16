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
import com.softproideas.util.validation.MappingValidator;

public class CPFunctionsCurrencyData extends CPFunctionsData {
    private List<String[]> currCellAddresses = new ArrayList<String[]>();
    private List<String> currCellLinks = new ArrayList<String>();
    private Map<String, String> workbookProperties;
    private CPFunctionDao dataEntryDAO;
    private String modelVisId;

    public CPFunctionsCurrencyData(CPFunctionDao dataEntryDAO, String modelVisId, Map<String, String> workbookProperties) {
        this.dataEntryDAO = dataEntryDAO;
        this.modelVisId = modelVisId;
        this.workbookProperties = workbookProperties;
    }

    @Override
    public void add(MappingValidator mv, String id) throws ValidationException {
        String[] prepareCurrencyCellKey = prepareCurrencyCellKey(mv);
        currCellAddresses.add(prepareCurrencyCellKey);
        currCellLinks.add(id);
    }

    @Override
    public void add(String expression, String id) throws ValidationException {
        MappingValidator mv = null;
        mv = new MappingValidator(expression);
        add(mv, id);
    }

    @Override
    public Map<String, String> submit() throws ValidationException {
        HashMap<String, String> results = new HashMap<String, String>();
        if (currCellAddresses.size() > 0) {
            int[] ranges = splitList(currCellAddresses.size(), 100);
            for (int i = 0; i < ranges.length - 1; i++) {
                List<String[]> temp = currCellAddresses.subList(ranges[i], ranges[i + 1]);
                List<String> currCellLinksPart = currCellLinks.subList(ranges[i], ranges[i + 1]);
                // log.info("currCellAddresses size " + temp.size(), "start");
                EntityList cellValues = null;
                cellValues = dataEntryDAO.getCurrencyCellValues(temp);
                results.putAll(buildResult(cellValues, currCellLinksPart));
                // log.info("currCellAddresses", "end");
            }
        }
        return results;
    }

    private HashMap<String, String> buildResult(EntityList cellValues, List<String> cellLinks) {
        HashMap<String, String> results = new HashMap<String, String>();
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

    private String[] prepareCurrencyCellKey(MappingValidator mv) throws ValidationException {
            Map<String, String> parsedArgMap = mv.getListOfArguments();
            String[] cellKeyTable = new String[4];
            String currency = parsedArgMap.get("curr"); // curr

            String dim2Context = WorkbookProperties.DIMENSION_2_VISID.toString();
            String dateFromContext = workbookProperties.get(dim2Context);
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

    private String fillCompanyFromContextIfIsEmpty(String company) throws ValidationException {
        if (company == null || company.trim().isEmpty() || company.equals("?")) {
            company = patternSlash.split(modelVisId)[0];
        }
        return company;
    }

}
