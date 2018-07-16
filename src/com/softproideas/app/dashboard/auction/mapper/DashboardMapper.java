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
package com.softproideas.app.dashboard.auction.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.softproideas.app.dashboard.auction.entity.DashboardEntity;
import com.softproideas.app.dashboard.auction.model.AuctionBidType;

public class DashboardMapper {

    public static List<DashboardEntity> mapAuctions(List<Map<String, Object>> rows) {
        List<DashboardEntity> list = new ArrayList<DashboardEntity>();
        for (Map<String, Object> row: rows) {
            DashboardEntity auction = mapAuction(row);
            list.add(auction);
        }
        return list;
    }

    private static Integer bigDecimalToInteger(BigDecimal bigDecimal) {
        Integer result;
        result = bigDecimal == null ? 0 : bigDecimal.intValue();
        return result;
    }

    private static Double bigDecimalToDouble(BigDecimal bigDecimal) {
        Double result;
        result = bigDecimal == null ? 0 : bigDecimal.doubleValue();
        return result;
    }

    private static BigDecimal getAndSetBigDecimal(BigDecimal bigDecimal) {
        if (bigDecimal == null) {
            return new BigDecimal("0.00");
        } else {
            return bigDecimal;
        }
    }

    private static DashboardEntity mapAuction(Map<String, Object> row) {
        DashboardEntity result = new DashboardEntity();

        BigDecimal hammerPrice = (BigDecimal) row.get("hammerPrice");
        result.setHammerPrice(getAndSetBigDecimal(hammerPrice));

        // BigDecimal sold = (BigDecimal) row.get("sold");
        // result.setSold(bigDecimalToInteger(sold));
        //
        // BigDecimal unsold = (BigDecimal) row.get("unsold");
        // result.setUnsold(bigDecimalToInteger(unsold));
        int sold = result.getHammerPrice().doubleValue() == 0.0 ? 0 : 1;
        result.setSold(sold);

        result.setUnsold(1 - sold);

        BigDecimal premium = (BigDecimal) row.get("premium");
        result.setPremium(getAndSetBigDecimal(premium));

        BigDecimal commAmt = (BigDecimal) row.get("commAmt");
        result.setCommAmt(getAndSetBigDecimal(commAmt));

        BigDecimal insAmt = (BigDecimal) row.get("insAmt");
        result.setInsAmt(getAndSetBigDecimal(insAmt));

        String bidType = (String) row.get("bidType");
        result.setBidType(AuctionBidType.BidType.getBidTypeByTag(bidType).toString());

        BigDecimal estimateLow = (BigDecimal) row.get("estimateLow");
        result.setEstimateLow(getAndSetBigDecimal(estimateLow));

        BigDecimal unsoldLE = result.getHammerPrice().doubleValue() == 0.0 ? result.getEstimateLow() : new BigDecimal("0.00");
        result.setUnsoldLE(unsoldLE);

        return result;
    }

    public static List<Integer> mapUsersIds(List<Map<String, Object>> rows) {
        List<Integer> list = new ArrayList<Integer>();
        for (Map<String, Object> row: rows) {
            Integer userId = mapUserId(row);
            if (userId != null) {
                list.add(userId);
            }
        }
        return list;
    }

    private static Integer mapUserId(Map<String, Object> row) {
        Integer result = null;

        BigDecimal userId = (BigDecimal) row.get("USER_ID");
        if (userId == null) {
            result = null;
        } else {
            result = userId.intValue();
        }
        return result;
    }

}
