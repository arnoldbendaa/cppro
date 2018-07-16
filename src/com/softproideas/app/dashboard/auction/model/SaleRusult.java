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

import com.softproideas.app.dashboard.auction.entity.DashboardEntity;

public class SaleRusult implements InterfaceComputingDataForDashboard {
    
    private BigDecimal hammer = new BigDecimal("0.00");
    private BigDecimal premium = new BigDecimal("0.00");
    private BigDecimal commission = new BigDecimal("0.00");
    private BigDecimal insurance = new BigDecimal("0.00");

    private Map<String, Map<String, String>> data;

    private static final String NAME = "saleResult";
    
    @Override
    public void update(DashboardEntity dto) {
        if (dto == null) {
            return;
        }
        if (dto.getHammerPrice() != null) {
            hammer = hammer.add(dto.getHammerPrice());
        }
        if (dto.getPremium() != null) {
            premium = premium.add(dto.getPremium());
        }
        if (dto.getCommAmt() != null) {
            commission = commission.add(dto.getCommAmt());
        }
        if (dto.getInsAmt() != null) {
            insurance = insurance.add(dto.getInsAmt());
        }

    }

    @Override
    public Map<String, Map<String, String>> getData() {
        hammer = hammer.divide(new BigDecimal("1000"), 0, RoundingMode.HALF_EVEN);
        premium = premium.divide(new BigDecimal("1000"), 0, RoundingMode.HALF_EVEN);
        commission = commission.divide(new BigDecimal("1000"), 0, RoundingMode.HALF_EVEN);
        insurance = insurance.divide(new BigDecimal("1000"), 0, RoundingMode.HALF_EVEN);

        Map<String, String> actual = new LinkedHashMap<String, String>();
        actual.put("hammer", hammer.toString());
        actual.put("premium", premium.toString());
        actual.put("commission", commission.toString());
        actual.put("insurance", insurance.toString());

        data = new HashMap<String, Map<String, String>>();
        data.put("actual", actual);
        return data;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return NAME;
    }

}
