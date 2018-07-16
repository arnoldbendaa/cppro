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
package com.softproideas.app.lookuptable.auction.mapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.softproideas.app.lookuptable.auction.model.DashboardAuctionDTO;
import com.softproideas.app.lookuptable.auction.model.FlatFormDTO;
import com.softproideas.app.lookuptable.auction.model.LookupAuctionDTO;

public class LookupAuctionMapper {

    public static List<LookupAuctionDTO> mapAuctions(List<Map<String, Object>> rows) {
        List<LookupAuctionDTO> list = new ArrayList<LookupAuctionDTO>();
        for (Map<String, Object> row: rows) {
            LookupAuctionDTO auction = mapAuction(row);
            list.add(auction);
        }
        return list;
    }

    public static List<DashboardAuctionDTO> mapAuctionsForCompany(List<Map<String, Object>> rows) {
        List<DashboardAuctionDTO> list = new ArrayList<DashboardAuctionDTO>();
        for (Map<String, Object> row: rows) {
            DashboardAuctionDTO auction = mapAuctionForCompany(row);
            list.add(auction);
        }
        return list;
    }

    private static DashboardAuctionDTO mapAuctionForCompany(Map<String, Object> row) {
        DashboardAuctionDTO auction = new DashboardAuctionDTO();

        BigDecimal isaleno = (BigDecimal) row.get("ISALENO");
        auction.setIsaleno(bigDecimalToInteger(isaleno));

        auction.setSsalename((String) row.get("SSALENAME"));

        java.sql.Timestamp daenddate = (Timestamp) row.get("DAENDDATE");
        
        String status = (String) row.get("SSALESTATUS");
       
        status = status.toLowerCase();
        status = Character.toString(status.charAt(0)).toUpperCase()+status.substring(1);
        auction.setSsalestatus(status);
        
        auction.setDaenddate(daenddate);

        return auction;
    }
    
    public static List<FlatFormDTO> mapFlatFormsForFinanceCubeId(List<Map<String, Object>> rows) {
        List<FlatFormDTO> list = new ArrayList<FlatFormDTO>();
        for (Map<String, Object> row: rows) {
            FlatFormDTO flatForm = mapFlatFormsForFinanceCubeId(row);
            list.add(flatForm);
        }
        return list;
    }

    private static FlatFormDTO mapFlatFormsForFinanceCubeId(Map<String, Object> row) {
        FlatFormDTO flatForm = new FlatFormDTO();

//        private int XML_FORM_ID;
//        private int VIS_ID;
//        private String DESCRIPTION;
//        private Timestamp UPDATED_TIME;
//      TODO parse dto fields to table columns
        
        BigDecimal XML_FORM_ID = (BigDecimal) row.get("XML_FORM_ID");
        flatForm.setXml_form_id(bigDecimalToInteger(XML_FORM_ID));

        flatForm.setDescription((String) row.get("DESCRIPTION"));
        
//        BigDecimal VIS_ID = (BigDecimal) row.get("VIS_ID");
//        flatForm.setVIS_ID(bigDecimalToInteger(VIS_ID));
        
        flatForm.setVis_id((String) row.get("VIS_ID"));

        java.sql.Timestamp UPDATED_TIME = (Timestamp) row.get("UPDATED_TIME");
        //DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        flatForm.setUpdated_time(UPDATED_TIME);

        return flatForm;
    }
    

    private static Integer bigDecimalToInteger(BigDecimal bigDecimal) {
        Integer result;
        result = bigDecimal == null ? null : bigDecimal.intValue();
        return result;
    }

    private static LookupAuctionDTO mapAuction(Map<String, Object> row) {
        LookupAuctionDTO auction = new LookupAuctionDTO();
        //
        // BigDecimal status = (BigDecimal) row.get("STATUS");
        // lookupParameter.setStatus(LookupParameterStatus.valueOf(status.intValue()));

        BigDecimal sold = (BigDecimal) row.get("sold");
        auction.setSold(bigDecimalToInteger(sold));

        BigDecimal unsold = (BigDecimal) row.get("unsold");
        auction.setUnsold(bigDecimalToInteger(unsold));

        BigDecimal auctionNo = (BigDecimal) row.get("auctionNo");
        auction.setAuctionNo(bigDecimalToInteger(auctionNo));

        BigDecimal lotNo = (BigDecimal) row.get("lotNo");
        auction.setLotNo(bigDecimalToInteger(lotNo));

        String sfx = (String) row.get("sfx");
        auction.setSfx(sfx);

        BigDecimal sellerNo = (BigDecimal) row.get("sellerNo");
        auction.setSellerNo(bigDecimalToInteger(sellerNo));

        BigDecimal contractNo = (BigDecimal) row.get("contractNo");
        auction.setContractNo(bigDecimalToInteger(contractNo));

        BigDecimal contractLineNo = (BigDecimal) row.get("contractLineNo");
        auction.setContractLineNo(bigDecimalToInteger(contractLineNo));

        String status = (String) row.get("status");
        auction.setStatus(status);

        BigDecimal buyer = (BigDecimal) row.get("buyer");
        auction.setBuyer(bigDecimalToInteger(buyer));

        BigDecimal paddleNo = (BigDecimal) row.get("paddleNo");
        auction.setPaddleNo(bigDecimalToInteger(paddleNo));

        BigDecimal hammerPrice = (BigDecimal) row.get("hammerPrice");
        auction.setHammerPrice(bigDecimalToInteger(hammerPrice));

        BigDecimal premium = (BigDecimal) row.get("premium");
        auction.setPremium(bigDecimalToInteger(premium));

        BigDecimal buyerNett = (BigDecimal) row.get("buyerNett");
        auction.setBuyerNett(bigDecimalToInteger(buyerNett));

        BigDecimal commAmt = (BigDecimal) row.get("commAmt");
        auction.setCommAmt(bigDecimalToInteger(commAmt));

        BigDecimal insAmt = (BigDecimal) row.get("insAmt");
        auction.setInsAmt(bigDecimalToInteger(insAmt));

        BigDecimal biFee = (BigDecimal) row.get("bIFee");
        auction.setbIFee(bigDecimalToInteger(biFee));

        BigDecimal wdFee = (BigDecimal) row.get("wDFee");
        auction.setwDFee(bigDecimalToInteger(wdFee));

        BigDecimal illustFee = (BigDecimal) row.get("illustFee");
        auction.setIllustFee(bigDecimalToInteger(illustFee));

        BigDecimal chgAmt = (BigDecimal) row.get("chgAmt");
        auction.setChgAmt(bigDecimalToInteger(chgAmt));

        BigDecimal passPrice = (BigDecimal) row.get("passPrice");
        auction.setPassPrice(bigDecimalToInteger(passPrice));

        BigDecimal icAmmount = (BigDecimal) row.get("iCAmount");
        auction.setiCAmount(bigDecimalToInteger(icAmmount));

        String lotDescription = (String) row.get("lotDescription");
        auction.setLotDescription(lotDescription);

        String bidType = (String) row.get("bidType");
        auction.setBidType(bidType);

        String icRepUserID = (String) row.get("iCRepUserID");
        auction.setiCRepUserID(icRepUserID);

        String valuationUserID = (String) row.get("valuationUserID");
        auction.setValuationUserID(valuationUserID);

        BigDecimal estimateLow = (BigDecimal) row.get("estimateLow");
        auction.setEstimateLow(bigDecimalToInteger(estimateLow));

        BigDecimal estimateHigh = (BigDecimal) row.get("estimateHigh");
        auction.setEstimateHigh(bigDecimalToInteger(estimateHigh));

        BigDecimal reservePrice = (BigDecimal) row.get("reservePrice");
        auction.setReservePrice(bigDecimalToInteger(reservePrice));

        return auction;
    }

}
