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
package com.softproideas.app.dashboard.auction.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.softproideas.app.dashboard.auction.entity.DashboardEntity;

//getValueOfLotsSold
public class ValueOfLotsSold implements InterfaceComputingDataForDashboard {

    private Map<String, Map<String, String>> data;

    ValueRange range;
    Values[] t;

    Map<Integer, BigDecimal> unsold;
    Map<Integer, BigDecimal> sold;

    private static final String NAME = "valueOfLotsSold";

    public ValueOfLotsSold(ValueRange range) {
        this.range = range;
        t = range.getAllRanges();

        unsold = new TreeMap<Integer, BigDecimal>();
        sold = new TreeMap<Integer, BigDecimal>();

        for (int i = 0; i < t.length; i++) {
            sold.put(t[i].getMin(), new BigDecimal("0.0"));
            unsold.put(t[i].getMin(), new BigDecimal("0.0"));
        }
    }

    @Override
    public void update(DashboardEntity dto) {
        if (dto == null) {
            return;
        }
        getAndSetValues(unsold, range, dto.getUnsoldLE());
        getAndSetValues(sold, range, dto.getHammerPrice());
    }

    @Override
    public Map<String, Map<String, String>> getData() {
        data = new HashMap<String, Map<String, String>>();
        data.put("sold", parseToString(sold));
        data.put("unsold", parseToString(unsold));
        return data;
    }

    private static void getAndSetValues(Map<Integer, BigDecimal> items, ValueRange range, BigDecimal value) {
        if (value == null) {
            return;
        }
        Values vs = range.getRange(value.doubleValue());
        BigDecimal us = items.get(vs.getMin());
        us = us.add(value);
        items.put(vs.getMin(), us);
    }

    private static Map<String, String> parseToString(Map<Integer, BigDecimal> map) {
        Map<String, String> temp = new LinkedHashMap<String, String>();
        for (Integer i: map.keySet()) {
            temp.put(i.toString(), map.get(i).divide(new BigDecimal("1000"), 0, RoundingMode.HALF_EVEN).toString());
        }
        return temp;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return NAME;
    }
}
