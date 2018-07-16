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
import com.softproideas.util.validation.MappingValidator;

public class CPFunctionsAuctionData extends CPFunctionsData {

    private List<String[]> auctionCellAddresses;
    private List<String> auctionCellLinks;
    private CPFunctionDao dataEntryDAO;

    public CPFunctionsAuctionData(CPFunctionDao dataEntryDAO) {
        this.dataEntryDAO = dataEntryDAO;
        auctionCellAddresses = new ArrayList<String[]>();
        auctionCellLinks = new ArrayList<String>();
    }

    @Override
    public void add(MappingValidator mv, String id) throws ValidationException {
        String[] prepareAuctionCellKey = prepareAuctionCellKey(mv);
        auctionCellAddresses.add(prepareAuctionCellKey);
        auctionCellLinks.add(id);
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
        if (auctionCellAddresses.size() > 0) {
            // log.info("auctionCellAddresses", "start");
            int[] ranges = splitList(auctionCellAddresses.size(), 100);
            for (int i = 0; i < ranges.length - 1; i++) {
                List<String[]> temp = auctionCellAddresses.subList(ranges[i], ranges[i + 1]);
                List<String> auctionCellLinksPart = auctionCellLinks.subList(ranges[i], ranges[i + 1]);
                EntityList cellValues = null;
                cellValues = dataEntryDAO.getAuctionCellValues(temp);
                results.putAll(buildResult(cellValues, auctionCellLinksPart));
            }
            // log.info("auctionCellAddresses", "end");
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

    private String[] prepareAuctionCellKey(MappingValidator mv) throws ValidationException {
        Map<String, String> parsedArgMap = mv.getListOfArguments();

        String[] cellKeyTable = new String[3];
        cellKeyTable[0] = parsedArgMap.get("table");
        cellKeyTable[1] = parsedArgMap.get("key");
        cellKeyTable[2] = parsedArgMap.get("column");

        return cellKeyTable;
    }
}
