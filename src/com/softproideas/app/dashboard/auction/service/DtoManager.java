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
package com.softproideas.app.dashboard.auction.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.softproideas.app.dashboard.auction.entity.DashboardEntity;
import com.softproideas.app.dashboard.auction.model.AuctionBidType;
import com.softproideas.app.dashboard.auction.model.IncomeVsNumberOfLots;
import com.softproideas.app.dashboard.auction.model.InterfaceComputingDataForDashboard;
import com.softproideas.app.dashboard.auction.model.NumberOfLotsSold;
import com.softproideas.app.dashboard.auction.model.SaleRusult;
import com.softproideas.app.dashboard.auction.model.ValueOfLotsSold;
import com.softproideas.app.dashboard.auction.model.ValueRange;
import com.softproideas.app.dashboard.auction.model.VendorsCommission;

public class DtoManager {

    private List<InterfaceComputingDataForDashboard> data;

    public DtoManager(ValueRange range) {
        data = new ArrayList<InterfaceComputingDataForDashboard>();
        data.add(new AuctionBidType(range));
        data.add(new IncomeVsNumberOfLots(range));
        data.add(new NumberOfLotsSold(range));
        data.add(new SaleRusult());
        data.add(new ValueOfLotsSold(range));
        data.add(new VendorsCommission(range));
    }
    
    public DtoManager(List<InterfaceComputingDataForDashboard> data){
        this.data = data;
    }

    public Map<String, Map<String, Map<String, String>>> createResult(List<DashboardEntity> lookupAuctionDTO) {
        for (DashboardEntity dto: lookupAuctionDTO) {
            for (InterfaceComputingDataForDashboard m: data) {
                m.update(dto);
            }
        }
        Map<String, Map<String, Map<String, String>>> result = new HashMap<String, Map<String, Map<String, String>>>();
        for (InterfaceComputingDataForDashboard m: data) {
            result.put(m.getName(), m.getData());
        }
        return result;
    }
}
