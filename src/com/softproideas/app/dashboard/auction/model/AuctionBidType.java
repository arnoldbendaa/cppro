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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

import com.softproideas.app.dashboard.auction.entity.DashboardEntity;

public class AuctionBidType implements InterfaceComputingDataForDashboard {

    private Map<String, Map<String, String>> data;

    ValueRange range;
    Values[] t;

    Map<String, Map<Integer, Integer>> bidTypes;

    private static final String NAME = "auctionBidType";

    public AuctionBidType(ValueRange range) {
        this.range = range;
        t = range.getAllRanges();

        bidTypes = new HashMap<String, Map<Integer, Integer>>();
        for (BidType b: BidType.values()) {
            if (b.equals(BidType.UNDEFINED)) {
                continue;
            }
            Map<Integer, Integer> bidType = new TreeMap<Integer, Integer>();
            for (int i = 0; i < t.length; i++) {
                bidType.put(t[i].getMin(), 0);
            }
            bidTypes.put(b.toString(), bidType);
        }
    }

    @Override
    public void update(DashboardEntity l) {
        if (l == null) {
            return;
        }
        String bidType = l.getBidType();
        if (bidTypes.containsKey(bidType)) {
            Map<Integer, Integer> bidCounts = bidTypes.get(bidType);
            BigDecimal hammerPrice = l.getHammerPrice();
            if (hammerPrice == null) {
                return;
            }
            Values curr = range.getRange(hammerPrice.doubleValue());
            int bidCount = bidCounts.get(curr.getMin());
            bidCount++;
            bidCounts.put(curr.getMin(), bidCount);
            bidTypes.put(bidType, bidCounts);
        }

    }

    @Override
    public Map<String, Map<String, String>> getData() {
        data = new LinkedHashMap<String, Map<String, String>>();
        Map<String, Map<Integer, Integer>> result = sortByTotals(bidTypes);
        for (String s: result.keySet()) {
            data.put(s, parseToString(bidTypes.get(s)));
        }

        return data;
    }

    private Map<String, Map<Integer, Integer>> sortByTotals(Map<String, Map<Integer, Integer>> map) {
        Map<String, Integer> temp = new LinkedHashMap<String, Integer>();
        // count totals
        for (String s: map.keySet()) {
            int sum = 0;
            for (Integer i: map.get(s).keySet()) {
                sum += map.get(s).get(i);
            }
            temp.put(s, sum);
        }
        Map<String, Integer> sorted = new LinkedHashMap<String, Integer>();
        while (temp.size() > 0) {
            int min = Integer.MAX_VALUE;
            String current = null;
            for (String s: temp.keySet()) {
                if (temp.get(s) <= min) {
                    min = temp.get(s);
                    current = s;
                }
            }
            sorted.put(current, min);
            temp.remove(current);
        }
        Map<String, Map<Integer, Integer>> result = new LinkedHashMap<String, Map<Integer, Integer>>();
        for (String s: sorted.keySet()) {
            result.put(s, map.get(s));
        }
        return result;
    }

    private static Map<String, String> parseToString(Map<Integer, Integer> map) {
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

    public enum BidType {
        INTERNET("internetBid", "I", "Internet bid"),
        TELEPHONE("telephoneBid", "T", "Telephone bid"),
        ABSENT("absentBid", "C", "Absent bid"),
        AUCTION_ROOM("auctionRoomBid", "S", "Auction room bid"),
        LIVE("liveBid", "L", "Live bid"),
        AFTER_AUCTION("afterAuctionBid", "A", "After auction bid"),
        UNDEFINED("undefined", "UND", "Undefined bid");

        private String name;
        private String description;
        private String tag;

        private BidType(String name, String tag, String description) {
            this.name = name;
            this.tag = tag;
            this.description = description;
        }

        public static BidType getBidTypeByName(String type) {
            for (BidType bt: BidType.values()) {
                if (bt.name.equalsIgnoreCase(type)) {
                    return bt;
                }
            }
            return UNDEFINED;
        }

        public static BidType getBidTypeByTag(String tag) {
            for (BidType bt: BidType.values()) {
                if (bt.tag.equalsIgnoreCase(tag)) {
                    return bt;
                }
            }
            return UNDEFINED;
        }

        public String toString() {
            return this.name;
        }
        
        public String getDescription(){
            return this.description;
        }
    }
}
