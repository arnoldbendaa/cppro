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

import java.util.Map;

import com.softproideas.app.dashboard.auction.entity.DashboardEntity;

public interface InterfaceComputingDataForDashboard {

    public void update(DashboardEntity dto);

    public Map<String, Map<String, String>> getData();

    public String getName();

    public enum GraphType {
        
        AUCTION_BID_TYPE("auctionBidType", "Bid type"),
        NO_OF_LOTS_V_INVOME("incomeVsNumberOfLots", "No of lots v Income"),
        NO_OF_LOTS_SOLD("numberOfLotsSold", "Number of lots sold"),
        SALE_RESULT("saleResult", "Sale Result"),
        VALUE_OF_LOTS_SOLD("valueOfLotsSold", "Value of lots sold"),
        VENDORS_COMMISSION("vendorsCommission", "Vendor Commission"),
        UNDEFINED("undefined", "Undefined");
        
        private String name;
        private String description;

        private GraphType(String name, String description) {
            this.name = name;
            this.description = description;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public String getDescription() {
            return this.description;
        }

        public static GraphType getGraphType(String type) {
            for (GraphType gt: GraphType.values()) {
                if (gt.name.equalsIgnoreCase(type)) {
                    return gt;
                }
            }
            return UNDEFINED;
        }
    }
}
