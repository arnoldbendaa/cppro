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
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class ValueRange {

    private Set<Values> values;
    private char currencySymbol = 163; // pound sterling
    private String postfix = "k";

    public ValueRange(int[] values) {
        setValues(values);
    }

    public void setValues(int[] values) {
        this.values = new LinkedHashSet<Values>();
        if (values == null || values.length == 0) {
            return;
        }
        Arrays.sort(values);
        for (int i = 0; i < values.length; i++) {
            if (i == values.length - 1) {
                this.values.add(new Values(values[i], Integer.MAX_VALUE));
            } else {
                this.values.add(new Values(values[i], values[i + 1]));
            }
        }
    }

    public Values[] getAllRanges() {
        return values.toArray(new Values[0]);
    }

    public Values getRange(double value) {
        for (Values v: values) {
            if (v.isInRange(value)) {
                return v;
            }
        }
        return new Values(0, 0);
    }

    public Map<Integer, String> getLabels() {
        Map<Integer, String> labels = new TreeMap<Integer, String>();
        for (Values v: values) {
            labels.put(v.getMin(), createLabel(v));
        }
        return labels;
    }

    private String createLabel(Values value) {
        if (value.getMax() == Integer.MAX_VALUE) {
            String min = new BigDecimal(value.getMin()).divide(new BigDecimal("1000"), 1, RoundingMode.HALF_EVEN).toString();
            if (min.endsWith(".0")) {
                min = min.substring(0, min.length() - 2);
            }
            return "Over " + String.valueOf(currencySymbol) + min + postfix;
        } else {
            String min = new BigDecimal(value.getMin()).divide(new BigDecimal("1000"), 1, RoundingMode.HALF_EVEN).toString();
            if (min.endsWith(".0")) {
                min = min.substring(0, min.length() - 2);
            }
            String max = new BigDecimal(value.getMax()).divide(new BigDecimal("1000"), 1, RoundingMode.HALF_EVEN).toString();
            if (max.endsWith(".0")) {
                max = max.substring(0, max.length() - 2);
            }
            return String.valueOf(currencySymbol) + min + "-" + max + postfix;
        }
    }

    public static void main(String[] args) {
        int[] curr = { 0, 1000, 2500, 60000, 5000, 10000, 25000, 3000, 50000, 100000, 250000 };
        ValueRange vr = new ValueRange(curr);
        for (Integer i: vr.getLabels().keySet()) {
            System.out.println(vr.getLabels().get(i));
        }
    }

    public char getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(char currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getPostfix() {
        return postfix;
    }

    public void setPostfix(String postfix) {
        this.postfix = postfix;
    }
}
