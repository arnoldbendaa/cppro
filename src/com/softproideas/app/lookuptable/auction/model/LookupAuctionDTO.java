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
package com.softproideas.app.lookuptable.auction.model;

public class LookupAuctionDTO {

    private Integer unsoldPassPrice;
    private Integer unsoldLE;
    private Integer sold;
    private Integer unsold;
    private Integer auctionNo;
    private Integer lotNo;
    private String sfx;
    private Integer sellerNo;
    private Integer contractNo;
    private Integer contractLineNo;
    private String status;
    private Integer buyer;
    private Integer paddleNo;
    private Integer hammerPrice;
    private Integer premium;
    private Integer buyerNett;
    private Integer commAmt;
    private Integer insAmt;
    private Integer bIFee;
    private Integer wDFee;
    private Integer illustFee;
    private Integer chgAmt;
    private Integer passPrice;
    private Integer iCAmount;
    private String lotDescription;
    private String bidType;
    private String iCRepUserID;
    private String valuationUserID;
    private Integer estimateLow;
    private Integer estimateHigh;
    private Integer reservePrice;
    
    public Integer getUnsoldPassPrice() {
        return unsoldPassPrice;
    }
    public void setUnsoldPassPrice(Integer unsoldPassPrice) {
        this.unsoldPassPrice = unsoldPassPrice;
    }
    public Integer getUnsoldLE() {
        return unsoldLE;
    }
    public void setUnsoldLE(Integer unsoldLE) {
        this.unsoldLE = unsoldLE;
    }
    public Integer getSold() {
        return sold;
    }
    public void setSold(Integer sold) {
        this.sold = sold;
    }
    public Integer getUnsold() {
        return unsold;
    }
    public void setUnsold(Integer unsold) {
        this.unsold = unsold;
    }
    public Integer getAuctionNo() {
        return auctionNo;
    }
    public void setAuctionNo(Integer auctionNo) {
        this.auctionNo = auctionNo;
    }
    public Integer getLotNo() {
        return lotNo;
    }
    public void setLotNo(Integer lotNo) {
        this.lotNo = lotNo;
    }
    public String getSfx() {
        return sfx;
    }
    public void setSfx(String sfx) {
        this.sfx = sfx;
    }
    public Integer getSellerNo() {
        return sellerNo;
    }
    public void setSellerNo(Integer sellerNo) {
        this.sellerNo = sellerNo;
    }
    public Integer getContractNo() {
        return contractNo;
    }
    public void setContractNo(Integer contractNo) {
        this.contractNo = contractNo;
    }
    public Integer getContractLineNo() {
        return contractLineNo;
    }
    public void setContractLineNo(Integer contractLineNo) {
        this.contractLineNo = contractLineNo;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Integer getBuyer() {
        return buyer;
    }
    public void setBuyer(Integer buyer) {
        this.buyer = buyer;
    }
    public Integer getPaddleNo() {
        return paddleNo;
    }
    public void setPaddleNo(Integer paddleNo) {
        this.paddleNo = paddleNo;
    }
    public Integer getHammerPrice() {
        return hammerPrice;
    }
    public void setHammerPrice(Integer hammerPrice) {
        this.hammerPrice = hammerPrice;
    }
    public Integer getPremium() {
        return premium;
    }
    public void setPremium(Integer premium) {
        this.premium = premium;
    }
    public Integer getBuyerNett() {
        return buyerNett;
    }
    public void setBuyerNett(Integer buyerNett) {
        this.buyerNett = buyerNett;
    }
    public Integer getCommAmt() {
        return commAmt;
    }
    public void setCommAmt(Integer commAmt) {
        this.commAmt = commAmt;
    }
    public Integer getInsAmt() {
        return insAmt;
    }
    public void setInsAmt(Integer insAmt) {
        this.insAmt = insAmt;
    }
    public Integer getbIFee() {
        return bIFee;
    }
    public void setbIFee(Integer bIFee) {
        this.bIFee = bIFee;
    }
    public Integer getwDFee() {
        return wDFee;
    }
    public void setwDFee(Integer wDFee) {
        this.wDFee = wDFee;
    }
    public Integer getIllustFee() {
        return illustFee;
    }
    public void setIllustFee(Integer illustFee) {
        this.illustFee = illustFee;
    }
    public Integer getChgAmt() {
        return chgAmt;
    }
    public void setChgAmt(Integer chgAmt) {
        this.chgAmt = chgAmt;
    }
    public Integer getPassPrice() {
        return passPrice;
    }
    public void setPassPrice(Integer passPrice) {
        this.passPrice = passPrice;
    }
    public Integer getiCAmount() {
        return iCAmount;
    }
    public void setiCAmount(Integer iCAmount) {
        this.iCAmount = iCAmount;
    }
    public String getLotDescription() {
        return lotDescription;
    }
    public void setLotDescription(String lotDescription) {
        this.lotDescription = lotDescription;
    }
    public String getBidType() {
        return bidType;
    }
    public void setBidType(String bidType) {
        this.bidType = bidType;
    }
    public String getiCRepUserID() {
        return iCRepUserID;
    }
    public void setiCRepUserID(String iCRepUserID) {
        this.iCRepUserID = iCRepUserID;
    }
    public String getValuationUserID() {
        return valuationUserID;
    }
    public void setValuationUserID(String valuationUserID) {
        this.valuationUserID = valuationUserID;
    }
    public Integer getEstimateLow() {
        return estimateLow;
    }
    public void setEstimateLow(Integer estimateLow) {
        this.estimateLow = estimateLow;
    }
    public Integer getEstimateHigh() {
        return estimateHigh;
    }
    public void setEstimateHigh(Integer estimateHigh) {
        this.estimateHigh = estimateHigh;
    }
    public Integer getReservePrice() {
        return reservePrice;
    }
    public void setReservePrice(Integer reservePrice) {
        this.reservePrice = reservePrice;
    }
    
    
    
}
