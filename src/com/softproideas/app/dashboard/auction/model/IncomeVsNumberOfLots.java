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

public class IncomeVsNumberOfLots implements InterfaceComputingDataForDashboard {

    private Map<String, Map<String, String>> data;

    ValueRange range;
    Values[] t;

    Map<Integer, BigDecimal> income = new TreeMap<Integer, BigDecimal>();
    Map<Integer, Integer> noLots = new TreeMap<Integer, Integer>();
    Map<Integer, Integer> soldItems = new TreeMap<Integer, Integer>();

    int countSoldItemsForAllValueRanges = 0;

    private static final String NAME = "incomeVsNumberOfLots";

    public IncomeVsNumberOfLots(ValueRange range) {
        this.range = range;
        t = range.getAllRanges();

        for (int i = 0; i < t.length; i++) {
            income.put(t[i].getMin(), new BigDecimal("0.0"));
            noLots.put(t[i].getMin(), 0);
            soldItems.put(t[i].getMin(), 0);
        }
    }

    @Override
    public void update(DashboardEntity l) {

        if (l == null) {
            return;
        }
        BigDecimal hammerPrice = l.getHammerPrice();
        BigDecimal premium = l.getPremium();
        if (hammerPrice == null || premium == null || premium.intValue() <= 0) {
            return;
        }
        Values curr = range.getRange(hammerPrice.doubleValue());

        BigDecimal sum = income.get(curr.getMin());
        sum = sum.add(premium);
        income.put(curr.getMin(), sum);
        int countSoldItemsForRange = soldItems.get(curr.getMin());
        countSoldItemsForRange++;
        soldItems.put(curr.getMin(), countSoldItemsForRange);

        countSoldItemsForAllValueRanges++;

    }

    @Override
    public Map<String, Map<String, String>> getData() {
        data = new LinkedHashMap<String, Map<String, String>>();

        if (countSoldItemsForAllValueRanges > 0) {
            for (int i: soldItems.keySet()) {
                BigDecimal b = new BigDecimal(soldItems.get(i)).multiply(income.get(i)).divide(new BigDecimal("1000"), 0, RoundingMode.HALF_EVEN).divide(new BigDecimal(countSoldItemsForAllValueRanges), 1, RoundingMode.HALF_EVEN);
                noLots.put(i, b.intValue());
            }
        }
        data.put("income", parseDecimalToString(income));
        data.put("noLots", parseIntToString(noLots));
        return data;
    }

    private static Map<String, String> parseDecimalToString(Map<Integer, BigDecimal> map) {
        Map<String, String> temp = new LinkedHashMap<String, String>();
        for (Integer i: map.keySet()) {
            temp.put(i.toString(), map.get(i).divide(new BigDecimal("1000"), 0, RoundingMode.HALF_EVEN).toString());
        }
        return temp;
    }

    private static Map<String, String> parseIntToString(Map<Integer, Integer> map) {
        Map<String, String> temp = new LinkedHashMap<String, String>();
        for (Integer i: map.keySet()) {
            temp.put(i.toString(), map.get(i).toString());
        }
        return temp;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return NAME;
    }

}
