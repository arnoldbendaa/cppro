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

public class VendorsCommission implements InterfaceComputingDataForDashboard {

    private Map<String, Map<String, String>> data;

    ValueRange range;
    Values[] t;

    Map<Integer, Double> vendorsCommision = new TreeMap<Integer, Double>();
    Map<Integer, BigDecimal> commAmt = new HashMap<Integer, BigDecimal>();
    Map<Integer, BigDecimal> hammerPrice = new HashMap<Integer, BigDecimal>();

    private static final String NAME = "vendorsCommission";

    public VendorsCommission(ValueRange range) {
        this.range = range;
        t = range.getAllRanges();

        for (int i = 0; i < t.length; i++) {
            vendorsCommision.put(t[i].getMin(), 0.0);
            commAmt.put(t[i].getMin(), new BigDecimal("0.0"));
            hammerPrice.put(t[i].getMin(), new BigDecimal("0.0"));
        }
    }

    @Override
    public void update(DashboardEntity l) {
        if (l == null) {
            return;
        }
        BigDecimal commission = l.getCommAmt();
        BigDecimal hammer = l.getHammerPrice();
        if (commission == null || hammer == null) {
            return;
        }
        Values v = range.getRange(hammer.doubleValue());
        BigDecimal commAmtSum = commAmt.get(v.getMin());
        commAmtSum = commAmtSum.add(commission);
        commAmt.put(v.getMin(), commAmtSum);
        BigDecimal hammerSum = hammerPrice.get(v.getMin());
        hammerSum = hammerSum.add(hammer);
        hammerPrice.put(v.getMin(), hammerSum);

    }

    @Override
    public Map<String, Map<String, String>> getData() {
        data = new HashMap<String, Map<String, String>>();

        for (int i = 0; i < t.length; i++) {
            BigDecimal vc = new BigDecimal("0.0");
            BigDecimal commSum = commAmt.get(t[i].getMin());
            BigDecimal hammerSum = hammerPrice.get(t[i].getMin());
            if (commSum.doubleValue() > 0 && hammerSum.doubleValue() > 0) {
                vc = commSum.multiply(new BigDecimal("100.0")).divide(hammerSum, 1, RoundingMode.HALF_EVEN);
                vendorsCommision.put(t[i].getMin(), vc.doubleValue());
            }
        }

        data.put("vc", parseToString(vendorsCommision));
        return data;
    }

    private static Map<String, String> parseToString(Map<Integer, Double> map) {
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
