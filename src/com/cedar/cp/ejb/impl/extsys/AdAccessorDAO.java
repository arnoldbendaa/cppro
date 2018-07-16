package com.cedar.cp.ejb.impl.extsys;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.cedar.cp.ejb.impl.base.AbstractDAO;
import com.cedar.cp.util.Log;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class AdAccessorDAO extends AbstractDAO {
    
    Log _log = new Log(getClass());
    private ExternalSystemDAO mOwningDAO;
    
    private static final String getSale = 
              "SELECT"
            + " iSaleNo, "
            + " Substring(sSaleName, 1, 400) as sSaleName,"
            + " Substring(sAuditType, 1, 2) as sAuditType,"
            + " iCompanyNo, "
            + " daStartDate,"
            + " daEndDate,"
            + " iItemsAssigned, "
            + " iItemsLotted, "
            + " iLotsSold,"
            + " iLotsUnsold,"
            + " Substring(sCurrencyID1, 1, 16) as sCurrencyID1,"
            + " iLotsInvoiced, "
            + " iLotsSettled,"
            + " daSettlementDate,"
            + " dCurrencyRate1,"
            + " iLotsWithdrawn,"
            + " lPublishSale,"
            + " Substring(sSaleA, 1, 16) as sSaleA,"
            + " lPublishLots,"
            + " lPublishResults,"
            + " lSaleCancelled,"
            + " iItemsCatalogued, "
            + " lExportDTP, "
            + " Substring(sSmartSaleNo, 1, 16) as sSmartSaleNo,"
            + " iLotsCancelled, "
            + " iLotsNo,"
            + " Substring(sSaleStatus, 1, 16) as sSaleStatus,"
            + " iNoRegs,"
            + " iNoBids,"
            + " Substring(sVenue, 1, 200) as sVenue,"
            + " Substring(sStartTime, 1, 20) as sStartTime,"
            + " iLastPaddleNo,"
            + " iLastBidPaddleNo,"
            + " dPrice1, "
            + " dPrice2,"
            + " dPrice3,"
            + " dPrice4,"
            + " lMailListCreated,"
            + " Substring(sOrigSource, 1, 16) as sOrigSource,"
            + " dMinCommAmt,"
            + " daCurrencyDate,"
            + " sCurrencyID2,"
            + " sCurrencyID3,"
            + " dCurrencyRate2, "
            + " dCurrencyRate3,"
            + " iSettlementDays,"
            + " Substring(sDivision, 1, 16) as sDivision, "
            + " lPrivateTreaty,"
            + " daDueDate,"
            + " Substring(sCatVolume, 1, 40) as sCatVolume,"
            + " lSaleMatched,"
            + " lFullPresale,"
            + " Substring(sDepartment, 1, 16) as sDepartment,"
            + " lMinCommTaxIncl,"
            + " iItemsAgreed,"
            + " iLotsProformad,"
            + " iLotsPaid, "
            + " iLotsReoffered,"
            + " dCatPrice,"
            + " lPublishSections,"
            + " lExtraSale,"
            + " Substring(sSaleURL, 1, 400) as sSaleURL, "
            + " lOfferPreReg,"
            + " lOffsite, "
            + " lOfferCondition,"
            + " Substring(sSaleType, 1, 24) as sSaleType,"
            + " iPublishDays,"
            + " iConsignDays,"
            + " daConsign,"
            + " lBidOnline,"
            + " lBiographies,"
            + " lUseDealerLicence, "
            + " Substring(sBidOnlineStatus, 1, 16) as sBidOnlineStatus "
            + " FROM PUB.Sale";
    
    private static final String getSaleWithMax = 
            "SELECT"
          + " iSaleNo, "
          + " Substring(sSaleName, 1, 400) as sSaleName,"
          + " Substring(sAuditType, 1, 2) as sAuditType,"
          + " iCompanyNo, "
          + " daStartDate,"
          + " daEndDate,"
          + " iItemsAssigned, "
          + " iItemsLotted, "
          + " iLotsSold,"
          + " iLotsUnsold,"
          + " Substring(sCurrencyID1, 1, 16) as sCurrencyID1,"
          + " iLotsInvoiced, "
          + " iLotsSettled,"
          + " daSettlementDate,"
          + " dCurrencyRate1,"
          + " iLotsWithdrawn,"
          + " lPublishSale,"
          + " Substring(sSaleA, 1, 16) as sSaleA,"
          + " lPublishLots,"
          + " lPublishResults,"
          + " lSaleCancelled,"
          + " iItemsCatalogued, "
          + " lExportDTP, "
          + " Substring(sSmartSaleNo, 1, 16) as sSmartSaleNo,"
          + " iLotsCancelled, "
          + " iLotsNo,"
          + " Substring(sSaleStatus, 1, 16) as sSaleStatus,"
          + " iNoRegs,"
          + " iNoBids,"
          + " Substring(sVenue, 1, 200) as sVenue,"
          + " Substring(sStartTime, 1, 20) as sStartTime,"
          + " iLastPaddleNo,"
          + " iLastBidPaddleNo,"
          + " dPrice1, "
          + " dPrice2,"
          + " dPrice3,"
          + " dPrice4,"
          + " lMailListCreated,"
          + " Substring(sOrigSource, 1, 16) as sOrigSource,"
          + " dMinCommAmt,"
          + " daCurrencyDate,"
          + " sCurrencyID2,"
          + " sCurrencyID3,"
          + " dCurrencyRate2, "
          + " dCurrencyRate3,"
          + " iSettlementDays,"
          + " Substring(sDivision, 1, 16) as sDivision, "
          + " lPrivateTreaty,"
          + " daDueDate,"
          + " Substring(sCatVolume, 1, 40) as sCatVolume,"
          + " lSaleMatched,"
          + " lFullPresale,"
          + " Substring(sDepartment, 1, 16) as sDepartment,"
          + " lMinCommTaxIncl,"
          + " iItemsAgreed,"
          + " iLotsProformad,"
          + " iLotsPaid, "
          + " iLotsReoffered,"
          + " dCatPrice,"
          + " lPublishSections,"
          + " lExtraSale,"
          + " Substring(sSaleURL, 1, 400) as sSaleURL, "
          + " lOfferPreReg,"
          + " lOffsite, "
          + " lOfferCondition,"
          + " Substring(sSaleType, 1, 24) as sSaleType,"
          + " iPublishDays,"
          + " iConsignDays,"
          + " daConsign,"
          + " lBidOnline,"
          + " lBiographies,"
          + " lUseDealerLicence, "
          + " Substring(sBidOnlineStatus, 1, 16) as sBidOnlineStatus "
          + " FROM PUB.Sale"
            + " where Sale.iSaleNo > ?";

    private static final String getSaleLotA = 
              "SELECT "
            + " iSaleLotNoUnique,"
            + " dCommAmtAdj, "
            + " dCommAmtRfd, "
            + " dCommTaxAdj, "
            + " dCommTaxRfd, "
            + " dBIFeeAdj, "
            + " dBIFeeRfd, "
            + " dBIFeeTaxAdj, "
            + " dBIFeeTaxRfd,"
            + " dWDFeeAdj, "
            + " dWDFeeRfd, "
            + " dWDFeeTaxAdj, "
            + " dWDFeeTaxRfd, "
            + " dInsAmtAdj,"
            + " dInsAmtRfd, "
            + " dInsTaxAdj, "
            + " dInsTaxRfd, "
            + " dIllustrationFeeRfd, "
            + " dIllustrationFeeTaxRfd, "
            + " dOtherRefundAmt, "
            + " dOtherRefundTax, "
            + " dARLAmtAdj, "
            + " dMerchandiseFeeAdj, "
            + " dMerchandiseFeeRfd, "
            + " dARLAmtRfd, "
            + " dpremtaxrate2, "
            + " dchargeamtbuytax2, "
            + " dchargeamtbuytaxrate2, "
            + " dhammertaxrate2 , "
            + " dhammertax2, "
            + " dpremtax2 "
            + " FROM PUB.SaleLotA";
    
    private static final String getSaleLotAWithMax = 
              "SELECT "
            + " iSaleLotNoUnique,"
            + " dCommAmtAdj, "
            + " dCommAmtRfd, "
            + " dCommTaxAdj, "
            + " dCommTaxRfd, "
            + " dBIFeeAdj, "
            + " dBIFeeRfd, "
            + " dBIFeeTaxAdj, "
            + " dBIFeeTaxRfd,"
            + " dWDFeeAdj, "
            + " dWDFeeRfd, "
            + " dWDFeeTaxAdj, "
            + " dWDFeeTaxRfd, "
            + " dInsAmtAdj,"
            + " dInsAmtRfd, "
            + " dInsTaxAdj, "
            + " dInsTaxRfd, "
            + " dIllustrationFeeRfd, "
            + " dIllustrationFeeTaxRfd, "
            + " dOtherRefundAmt, "
            + " dOtherRefundTax, "
            + " dARLAmtAdj, "
            + " dMerchandiseFeeAdj, "
            + " dMerchandiseFeeRfd, "
            + " dARLAmtRfd, "
            + " dpremtaxrate2, "
            + " dchargeamtbuytax2, "
            + " dchargeamtbuytaxrate2, "
            + " dhammertaxrate2 , "
            + " dhammertax2, "
            + " dpremtax2 "
            + " FROM PUB.SaleLotA"            
            + " where SaleLotA.iSaleLotNoUnique > ?";
    
    private static final String getSaleItem = 
              "SELECT"
            + " iSaleItemNo, "
            + " iSplitNo, "
            + " iReceiptNo, "
            + " iClientNo, "
            + " Substring(sClientRef, 1, 1000) as sClientRef, "
            + " Substring(sDesc, 1, 4000) as sDesc, "
            + " iSaleItemType, "
            + " Substring(sSaleItemStatus, 1, 10) as sSaleItemStatus, "
            + " iCompanyNo, "
            + " iSaleNo, "
            + " Substring(sCurrencyID, 1, 8) as sCurrencyID, "
            + " dEstimateLow, "
            + " dEstimateHigh, "
            + " dReservePrice, "
            + " Substring(cReserveType, 1, 2) as cReserveType, "
            + " iNoImages, "
            + " lCatalogued, "
            + " dIllustrationFee, "
            + " Substring(sNotes, 1, 400) as sNotes,"
            + " lLotted,"
            + " sAuditType,"
            + " dEstimateInitial,"
            + " Substring(sValuationUserID, 1, 32) as sValuationUserID,"
            + " iReceiptLineNo,"
            + " iReOfferNo,"
            + " Substring(sOrig, 1, 40) as sOrig,"
            + " Substring(sOrigSource, 1, 16) as sOrigSource,"
            + " iSaleLotNo,"
            + " Substring(sSaleLotNoA, 1, 16) as sSaleLotNoA,"
            + " lReOffer,"
            + " dFaceValue,"
            + " dBondValue,"
            + " lBonded,"
            + " iSaleLotNoUnique,"
            + " iSaleSectionNo,"
            + " iHammerTaxCode, "
            + " Substring(sTempGroupBy, 1, 100) as sTempGroupBy,"
            + " iTempLotNo, "
            + " Substring(sLocDepartment, 1, 80) as sLocDepartment,"
            + " Substring(sDivision, 1, 8) as sDivision,"
            + " Substring(sDepartment, 1, 16) as sDepartment,"
            + " Substring(sLocDivision, 1, 8) as sLocDivision,"
            + " Substring(sDeptOld, 1, 16) as sDeptOld,"
            + " Substring(sReserveCurrencyID, 1, 8) as sReserveCurrencyID,"
            + " iImportDutyCode,"
            + " sHeaderType,"
            + " iCustomsNo,"
            + " Substring(sLotStatus, 1, 16) as sLotStatus,"
            + " dEstimateLowCur,"
            + " dEstimateHighCur,"
            + " iSalesTaxCode,"
            + " iOtherTaxCode,"
            + " lIllustrated,"
            + " dVendorNet,"
            + " dVendorTax,"
            + " dReservePriceCur,"
            + " dReserveCurrencyRate,"
            + " daReservePriceCurDate,"
            + " dValuation,"
            + " lIllustrationChg, "
            + " dVendorTotal,"
            + " Substring(sValLoc1, 1, 80) as sValLoc1,"
            + " Substring(sValLoc2, 1, 80) as sValLoc2,"
            + " Substring(sValLoc3, 1, 80) as sValLoc3,"
            + " iSettlementNo,"
            + " iVendorPaymentNo,"
            + " iNumLots,"
            + " lFoldable,"
            + " iCollectionNo,"
            + " Substring(sIllustrationType, 1, 16) as sIllustrationType,"
            + " lPrivateTreatyLot,"
            + " lNotPublishedOnWeb,"
            + " lSaleAgreed,"
            + " daImportDate,"
            + " dImportDutyRate,"
            + " dImportDutyAmt,"
            + " daLastScanDate,"
            + " iQuantity,"
            + " iReleaseNo,"
            + " lCites,"
            + " Substring(sArmsType, 1, 16) as sArmsType,"
            + " dReceiptLineNo,"
            + " Substring(sReceiptLineNo, 1, 16) as sReceiptLineNo,"
            + " iNewContractNo,"
            + " iNewContractLineNo,"
            + " lReferDept,"
            + " dEstimateLowCur3, "
            + " dEstimateHighCur3,"
            + " lARLFlag,"
            + " iArtistNo,"
            + " dPurchasePrice,"
            + " Substring(sLotSymbols, 1, 64) as sLotSymbols,"
            + " dEstimateLowGroup,"
            + " dEstimateHighGroup,"
            + " lSaleGuaranteed,"
            + " lUnderCopyright, "
            + " lWithoutReserve,"
            + " lNotForMarketing,"
            + " lValued,"
            + " lPublished,"
            + " lDisplayValuer,"
            + " iNoValueRationale,"
            + " Substring(sNoValueComments, 1, 1000) as sNoValueComments,"
            + " lIvory,"
            + " lPreviewLot"
            + " FROM PUB.SaleItem";
    
    private static final String getSaleItemWithMax = 
            "SELECT"
          + " iSaleItemNo, "
          + " iSplitNo, "
          + " iReceiptNo, "
          + " iClientNo, "
          + " Substring(sClientRef, 1, 1000) as sClientRef, "
          + " Substring(sDesc, 1, 4000) as sDesc, "
          + " iSaleItemType, "
          + " Substring(sSaleItemStatus, 1, 10) as sSaleItemStatus, "
          + " iCompanyNo, "
          + " iSaleNo, "
          + " Substring(sCurrencyID, 1, 8) as sCurrencyID, "
          + " dEstimateLow, "
          + " dEstimateHigh, "
          + " dReservePrice, "
          + " Substring(cReserveType, 1, 2) as cReserveType, "
          + " iNoImages, "
          + " lCatalogued, "
          + " dIllustrationFee, "
          + " Substring(sNotes, 1, 400) as sNotes,"
          + " lLotted,"
          + " sAuditType,"
          + " dEstimateInitial,"
          + " Substring(sValuationUserID, 1, 32) as sValuationUserID,"
          + " iReceiptLineNo,"
          + " iReOfferNo,"
          + " Substring(sOrig, 1, 40) as sOrig,"
          + " Substring(sOrigSource, 1, 16) as sOrigSource,"
          + " iSaleLotNo,"
          + " Substring(sSaleLotNoA, 1, 16) as sSaleLotNoA,"
          + " lReOffer,"
          + " dFaceValue,"
          + " dBondValue,"
          + " lBonded,"
          + " iSaleLotNoUnique,"
          + " iSaleSectionNo,"
          + " iHammerTaxCode, "
          + " Substring(sTempGroupBy, 1, 100) as sTempGroupBy,"
          + " iTempLotNo, "
          + " Substring(sLocDepartment, 1, 80) as sLocDepartment,"
          + " Substring(sDivision, 1, 8) as sDivision,"
          + " Substring(sDepartment, 1, 16) as sDepartment,"
          + " Substring(sLocDivision, 1, 8) as sLocDivision,"
          + " Substring(sDeptOld, 1, 16) as sDeptOld,"
          + " Substring(sReserveCurrencyID, 1, 8) as sReserveCurrencyID,"
          + " iImportDutyCode,"
          + " sHeaderType,"
          + " iCustomsNo,"
          + " Substring(sLotStatus, 1, 16) as sLotStatus,"
          + " dEstimateLowCur,"
          + " dEstimateHighCur,"
          + " iSalesTaxCode,"
          + " iOtherTaxCode,"
          + " lIllustrated,"
          + " dVendorNet,"
          + " dVendorTax,"
          + " dReservePriceCur,"
          + " dReserveCurrencyRate,"
          + " daReservePriceCurDate,"
          + " dValuation,"
          + " lIllustrationChg, "
          + " dVendorTotal,"
          + " Substring(sValLoc1, 1, 80) as sValLoc1,"
          + " Substring(sValLoc2, 1, 80) as sValLoc2,"
          + " Substring(sValLoc3, 1, 80) as sValLoc3,"
          + " iSettlementNo,"
          + " iVendorPaymentNo,"
          + " iNumLots,"
          + " lFoldable,"
          + " iCollectionNo,"
          + " Substring(sIllustrationType, 1, 16) as sIllustrationType,"
          + " lPrivateTreatyLot,"
          + " lNotPublishedOnWeb,"
          + " lSaleAgreed,"
          + " daImportDate,"
          + " dImportDutyRate,"
          + " dImportDutyAmt,"
          + " daLastScanDate,"
          + " iQuantity,"
          + " iReleaseNo,"
          + " lCites,"
          + " Substring(sArmsType, 1, 16) as sArmsType,"
          + " dReceiptLineNo,"
          + " Substring(sReceiptLineNo, 1, 16) as sReceiptLineNo,"
          + " iNewContractNo,"
          + " iNewContractLineNo,"
          + " lReferDept,"
          + " dEstimateLowCur3, "
          + " dEstimateHighCur3,"
          + " lARLFlag,"
          + " iArtistNo,"
          + " dPurchasePrice,"
          + " Substring(sLotSymbols, 1, 64) as sLotSymbols,"
          + " dEstimateLowGroup,"
          + " dEstimateHighGroup,"
          + " lSaleGuaranteed,"
          + " lUnderCopyright, "
          + " lWithoutReserve,"
          + " lNotForMarketing,"
          + " lValued,"
          + " lPublished,"
          + " lDisplayValuer,"
          + " iNoValueRationale,"
          + " Substring(sNoValueComments, 1, 1000) as sNoValueComments,"
          + " lIvory,"
          + " lPreviewLot"
          + " FROM PUB.SaleItem"
          + " where SaleItem.iSaleItemNo > ?";
    
    private static final String getSaleBid = 
              "SELECT "
            + " iSaleNo,"
            + " iSaleLotNo,"
            + " iClientNo,"
            + " iAddressNo,"
            + " Substring(cBidType, 1, 16) as cBidType,"
            + " iPaddleNo,"
            + " dBidAmt,"
            + " iBidPlus,"
            + " Substring(sTelNo1, 1, 80) as sTelNo1,"
            + " Substring(sTelNo2, 1, 80) as sTelNo2,"
            + " Substring(sTelNo3, 1, 80) as sTelNo3,"
            + " Substring(sTelNo4, 1, 80) as sTelNo4, "
            + " sAuditType,"
            + " Substring(sAuditUserID, 1, 100) as sAuditUserID,"
            + " daAuditDate,"
            + " Substring(sAuditTime, 1, 16) as sAuditTime,"
            + " iSaleBidNo,"
            + " Substring(sEmail, 1, 200) as sEmail,"
            + " Substring(sNotes, 1, 400) as sNotes,"
            + " iSaleLotNoUnique,"
            + " sSaleLotNoA,"
            + " daBidDate,"
            + " Substring(sBidTime, 1, 16) as sBidTime,"
            + " lCancelled,"
            + " Substring(sBidDateTime, 1, 38) as sBidDateTime,"
            + " Substring(sDivision, 1, 8) as sDivision "
            + " FROM PUB.SaleBid";
    
    private static final String getSaleBidWithMax = 
            "SELECT "
          + " iSaleNo,"
          + " iSaleLotNo,"
          + " iClientNo,"
          + " iAddressNo,"
          + " Substring(cBidType, 1, 16) as cBidType,"
          + " iPaddleNo,"
          + " dBidAmt,"
          + " iBidPlus,"
          + " Substring(sTelNo1, 1, 80) as sTelNo1,"
          + " Substring(sTelNo2, 1, 80) as sTelNo2,"
          + " Substring(sTelNo3, 1, 80) as sTelNo3,"
          + " Substring(sTelNo4, 1, 80) as sTelNo4, "
          + " sAuditType,"
          + " Substring(sAuditUserID, 1, 100) as sAuditUserID,"
          + " daAuditDate,"
          + " Substring(sAuditTime, 1, 16) as sAuditTime,"
          + " iSaleBidNo,"
          + " Substring(sEmail, 1, 200) as sEmail,"
          + " Substring(sNotes, 1, 400) as sNotes,"
          + " iSaleLotNoUnique,"
          + " sSaleLotNoA,"
          + " daBidDate,"
          + " Substring(sBidTime, 1, 16) as sBidTime,"
          + " lCancelled,"
          + " Substring(sBidDateTime, 1, 38) as sBidDateTime,"
          + " Substring(sDivision, 1, 8) as sDivision "
          + " FROM PUB.SaleBid"
          + " where SaleBid.iSaleBidNo > ?";
    
    private static final String getSaleLotCancel = 
          "SELECT "
        + " iSaleNo,"
        + " iSaleLotNo,"
        + " Substring(sSaleLotNoA, 1, 4) as sSaleLotNoA,"
        + " iReceiptNo,"
        + " iReceiptLineNo,"
        + " iInvoiceNo, "
        + " iInvoiceLineNo,"
        + " iSettlementNo,"
        + " iSettlementLineNo, "
        + " iReceiptNoNew,"
        + " iReceiptLineNoNew, "
        + " Substring(sCancelType, 1, 24) as sCancelType,"
        + " Substring(sCancelNotes, 1, 1000) as sCancelNotes,"
        + " iSaleLotNoUnique,"
        + " iSeqNo,"
        + " iCompanyNo,"
        + " Substring(sDivision, 1, 16) as sDivision,"
        + " Substring(sDepartment, 1, 16) as sDepartment,"
        + " iClientNo,"
        + " iVendorNo,"
        + " iCreditNo,"
        + " iCreditLineNo,"
        + " iProformaInvNo,"
        + " iProformaInvLineNo, "
        + " iClientNoNew "
        + " FROM PUB.SaleLotCancel";
    
    private static final String getSaleLotCancelWithMax = 
            "SELECT "
          + " iSaleNo,"
          + " iSaleLotNo,"
          + " Substring(sSaleLotNoA, 1, 4) as sSaleLotNoA,"
          + " iReceiptNo,"
          + " iReceiptLineNo,"
          + " iInvoiceNo, "
          + " iInvoiceLineNo,"
          + " iSettlementNo,"
          + " iSettlementLineNo, "
          + " iReceiptNoNew,"
          + " iReceiptLineNoNew, "
          + " Substring(sCancelType, 1, 24) as sCancelType,"
          + " Substring(sCancelNotes, 1, 1000) as sCancelNotes,"
          + " iSaleLotNoUnique,"
          + " iSeqNo,"
          + " iCompanyNo,"
          + " Substring(sDivision, 1, 16) as sDivision,"
          + " Substring(sDepartment, 1, 16) as sDepartment,"
          + " iClientNo,"
          + " iVendorNo,"
          + " iCreditNo,"
          + " iCreditLineNo,"
          + " iProformaInvNo,"
          + " iProformaInvLineNo, "
          + " iClientNoNew "
          + " FROM PUB.SaleLotCancel"
          + " where SaleLotCancel.iSeqNo > ?";
    
    private static final String getSaleLot = 
              "SELECT "
            + " iSaleLotNoUnique,"
            + " iSaleSectionNo,"
            + " iSaleNo,"
            + " iSaleLotNo,"
            + " iSaleItemNo,"
            + " Substring(sLotStatus, 1, 8) as sLotStatus,"
            + " dCurrRateSale,"
            + " dCurrRateSettlement,"
            + " dHammerPrice,"
            + " iPaddleNo,"
            + " iClientNo,"
            + " dPassPrice,"
            + " dCommAmt,"
            + " dCommPer,"
            + " dPremAmt,"
            + " dPremPer,"
            + " dInsAmt,"
            + " dInsPer,"
            + " lExtraLot,"
            + " iInvoiceNo,"
            + " iInvoiceLineNo,"
            + " iSettlementNo,"
            + " iSettlementLineNo,"
            + " dOtherChargeAmt,"
            + " dChargeAmtBuy,"
            + " dIllustrationFee,"
            + " Substring(sSaleLotNoA, 1, 20) as sSaleLotNoA,"
            + " dHammerTax,"
            + " dPremTax,"
            + " dCommTax,"
            + " dSalesTax,"
            + " dInsTax,"
            + " dHammerTaxRate,"
            + " dPremTaxRate,"
            + " dCommTaxRate,"
            + " dSalesTaxRate,"
            + " dInsTaxRate,"
            + " dChargeAmtBuyTax,"
            + " dChargeAmtBuyTaxRate,"
            + " iVendorNo,"
            + " iCommTaxCode,"
            + " iHammerTaxCode,"
            + " iPremTaxCode,"
            + " dVendorTotal,"
            + " dBuyerTotal,"
            + " dBuyerNett,"
            + " dBuyerTax,"
            + " dVendorTax,"
            + " dVendorNett,"
            + " dBIFee,"
            + " dWDFee,"
            + " Substring(sLotDesc, 1, 4000) as sLotDesc,"
            + " lBonded,"
            + " dBondValue,"
            + " iPaymentNo,"
            + " iPaymentLineNo,"
            + " dImportDutyRate,"
            + " dImportDuty,"
            + " dVendorHammerTax,"
            + " iProformaInvNo,"
            + " iProformaInvLineNo,"
            + " iInsTaxCode,"
            + " Substring(sSalesTaxFN, 1, 20) as sSalesTaxFN,"
            + " iImportDutyCode,"
            + " iSalesTaxCode,"
            + " dVendorHammerPrice,"
            + " dBIFeeTax,"
            + " dWDFeeTax,"
            + " iReceiptNo,"
            + " iReceiptLineNo,"
            + " dICTax,"
            + " iICSettlementNo,"
            + " iVendorPaymentNo,"
            + " iICClientNo,"
            + " Substring(sICRepUserID, 1, 16) as sICRepUserID,"
            + " dICCommPer,"
            + " dICAmt,"
            + " lHeldLot,"
            + " iVendorHammerTaxCode,"
            + " dVendorHammerTaxRate,"
            + " dFaceValue,"
            + " iChargeAmtBuyTaxCode,"
            + " iHeldLotJobLogNo,"
            + " lWDFeeCharged,"
            + " dOtherChargeTax,"
            + " dIllustrationFeeTax,"
            + " dVendorNettAdj,"
            + " dVendorTaxAdj,"
            + " dVendorNettRefund,"
            + " dVendorTaxRefund,"
            + " Substring(sSaleDivision, 1, 16) as sSaleDivision,"
            + " Substring(sSaleDepartment, 1, 16) as sSaleDepartment,"
            + " Substring(sReceiptDivision, 1, 16) as sReceiptDivision,"
            + " Substring(sRecDepartment, 1, 16) as sRecDepartment,"
            + " iICSettlementLineNo,"
            + " dICTaxRate,"
            + " iAddressNoShipTo,"
            + " iClientResaleNoUnique,"
            + " iSalesTaxNo,"
            + " lManualBuyerCalc,"
            + " lUseResaleLicence,"
            + " Substring(sResaleItemType, 1, 16) as sResaleItemType,"
            + " dICHammerPer,"
            + " dICPremPer,"
            + " dICFlatFee,"
            + " dVendorMerchandiseFeeRate,"
            + " dVendorMerchandiseFee,"
            + " dVendorImportDutyRate,"
            + " dVendorImportDuty, "
            + " dVendorSalesTaxRate,"
            + " dVendorSalesTaxAmt, "
            + " iCompanyNo,"
            + " dBIFeeRate,"
            + " Substring(sResaleLicenceNo, 1, 60) as sResaleLicenceNo,"
            + " sARLType,"
            + " lBuyerOptOut,"
            + " lAfterSale,"
            + " dDiscNet,"
            + " dDiscTax,"
            + " dDiscAmt,"
            + " lDiscFlag,"
            + " lARLFlag,"
            + " dARLAmt,"
            + " iRoyaltyNo,"
            + " Substring(sLotSymbols, 1, 64) as sLotSymbols,"
            + " dHammerPriceGroup,"
            + " cBidType,"
            + " iClientDealerNoUnique, "
            + " lUseDealerLicence, "
            + " Substring(sDealerLicenceNo, 1, 60) as sDealerLicenceNo "
            + " FROM PUB.SaleLot";
    
    private static final String getSaleLotWithMax = 
            "SELECT "
          + " iSaleLotNoUnique,"
          + " iSaleSectionNo,"
          + " iSaleNo,"
          + " iSaleLotNo,"
          + " iSaleItemNo,"
          + " Substring(sLotStatus, 1, 8) as sLotStatus,"
          + " dCurrRateSale,"
          + " dCurrRateSettlement,"
          + " dHammerPrice,"
          + " iPaddleNo,"
          + " iClientNo,"
          + " dPassPrice,"
          + " dCommAmt,"
          + " dCommPer,"
          + " dPremAmt,"
          + " dPremPer,"
          + " dInsAmt,"
          + " dInsPer,"
          + " lExtraLot,"
          + " iInvoiceNo,"
          + " iInvoiceLineNo,"
          + " iSettlementNo,"
          + " iSettlementLineNo,"
          + " dOtherChargeAmt,"
          + " dChargeAmtBuy,"
          + " dIllustrationFee,"
          + " Substring(sSaleLotNoA, 1, 20) as sSaleLotNoA,"
          + " dHammerTax,"
          + " dPremTax,"
          + " dCommTax,"
          + " dSalesTax,"
          + " dInsTax,"
          + " dHammerTaxRate,"
          + " dPremTaxRate,"
          + " dCommTaxRate,"
          + " dSalesTaxRate,"
          + " dInsTaxRate,"
          + " dChargeAmtBuyTax,"
          + " dChargeAmtBuyTaxRate,"
          + " iVendorNo,"
          + " iCommTaxCode,"
          + " iHammerTaxCode,"
          + " iPremTaxCode,"
          + " dVendorTotal,"
          + " dBuyerTotal,"
          + " dBuyerNett,"
          + " dBuyerTax,"
          + " dVendorTax,"
          + " dVendorNett,"
          + " dBIFee,"
          + " dWDFee,"
          + " Substring(sLotDesc, 1, 4000) as sLotDesc,"
          + " lBonded,"
          + " dBondValue,"
          + " iPaymentNo,"
          + " iPaymentLineNo,"
          + " dImportDutyRate,"
          + " dImportDuty,"
          + " dVendorHammerTax,"
          + " iProformaInvNo,"
          + " iProformaInvLineNo,"
          + " iInsTaxCode,"
          + " Substring(sSalesTaxFN, 1, 20) as sSalesTaxFN,"
          + " iImportDutyCode,"
          + " iSalesTaxCode,"
          + " dVendorHammerPrice,"
          + " dBIFeeTax,"
          + " dWDFeeTax,"
          + " iReceiptNo,"
          + " iReceiptLineNo,"
          + " dICTax,"
          + " iICSettlementNo,"
          + " iVendorPaymentNo,"
          + " iICClientNo,"
          + " Substring(sICRepUserID, 1, 16) as sICRepUserID,"
          + " dICCommPer,"
          + " dICAmt,"
          + " lHeldLot,"
          + " iVendorHammerTaxCode,"
          + " dVendorHammerTaxRate,"
          + " dFaceValue,"
          + " iChargeAmtBuyTaxCode,"
          + " iHeldLotJobLogNo,"
          + " lWDFeeCharged,"
          + " dOtherChargeTax,"
          + " dIllustrationFeeTax,"
          + " dVendorNettAdj,"
          + " dVendorTaxAdj,"
          + " dVendorNettRefund,"
          + " dVendorTaxRefund,"
          + " Substring(sSaleDivision, 1, 16) as sSaleDivision,"
          + " Substring(sSaleDepartment, 1, 16) as sSaleDepartment,"
          + " Substring(sReceiptDivision, 1, 16) as sReceiptDivision,"
          + " Substring(sRecDepartment, 1, 16) as sRecDepartment,"
          + " iICSettlementLineNo,"
          + " dICTaxRate,"
          + " iAddressNoShipTo,"
          + " iClientResaleNoUnique,"
          + " iSalesTaxNo,"
          + " lManualBuyerCalc,"
          + " lUseResaleLicence,"
          + " Substring(sResaleItemType, 1, 16) as sResaleItemType,"
          + " dICHammerPer,"
          + " dICPremPer,"
          + " dICFlatFee,"
          + " dVendorMerchandiseFeeRate,"
          + " dVendorMerchandiseFee,"
          + " dVendorImportDutyRate,"
          + " dVendorImportDuty, "
          + " dVendorSalesTaxRate,"
          + " dVendorSalesTaxAmt, "
          + " iCompanyNo,"
          + " dBIFeeRate,"
          + " Substring(sResaleLicenceNo, 1, 60) as sResaleLicenceNo,"
          + " sARLType,"
          + " lBuyerOptOut,"
          + " lAfterSale,"
          + " dDiscNet,"
          + " dDiscTax,"
          + " dDiscAmt,"
          + " lDiscFlag,"
          + " lARLFlag,"
          + " dARLAmt,"
          + " iRoyaltyNo,"
          + " Substring(sLotSymbols, 1, 64) as sLotSymbols,"
          + " dHammerPriceGroup,"
          + " cBidType,"
          + " iClientDealerNoUnique, "
          + " lUseDealerLicence, "
          + " Substring(sDealerLicenceNo, 1, 60) as sDealerLicenceNo "
          + " FROM PUB.SaleLot"
          + " where SaleLot.iSaleLotNoUnique > ?";

    public AdAccessorDAO(DataSource ds, ExternalSystemDAO owner) {
        super(ds);
        mOwningDAO = owner;
    }

    /* (non-Javadoc)
     * 
     * @see com.cedar.cp.ejb.impl.base.AbstractDAO#getEntityName() */
    @Override
    public String getEntityName() {
        return "AdAccessorDAO";
    }

    public int importSale() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            String tableName = "Sale";
            int max = mOwningDAO.getMaxId(tableName);
            if (max == 0) {
                stmt = getConnection().prepareStatement(getSale);
            } else {
                stmt = getConnection().prepareStatement(getSaleWithMax);
                stmt.setInt(1, max);
            }
            stmt.setFetchSize(500);
            resultSet = stmt.executeQuery();
            List rowList = new ArrayList();
            int c = 0;
            while (resultSet.next()) {
                if (rowList.size() == 500) {
                    mOwningDAO.insertIntoSaleTable(rowList);
                    rowList.clear();
                }
                int iSaleNo = resultSet.getInt("iSaleNo");
                String sSaleName = resultSet.getString("sSaleName");
                String sAuditType = resultSet.getString("sAuditType");
                int iCompanyNo = resultSet.getInt("iCompanyNo");
                Timestamp daStartDate = resultSet.getTimestamp("daStartDate");
                Timestamp daEndDate = resultSet.getTimestamp("daEndDate");
                int iItemsAssigned = resultSet.getInt("iItemsAssigned");
                int iItemsLotted = resultSet.getInt("iItemsLotted");
                int iLotsSold = resultSet.getInt("iLotsSold");
                int iLotsUnsold = resultSet.getInt("iLotsUnsold");
                String sCurrencyID1 = resultSet.getString("sCurrencyID1");
                int iLotsInvoiced = resultSet.getInt("iLotsInvoiced");
                int iLotsSettled = resultSet.getInt("iLotsSettled");
                Timestamp daSettlementDate = resultSet.getTimestamp("daSettlementDate");
                double dCurrencyRate1 = resultSet.getDouble("dCurrencyRate1");
                int iLotsWithdrawn = resultSet.getInt("iLotsWithdrawn");
                boolean lPublishSale = resultSet.getBoolean("lPublishSale");
                String sSaleA = resultSet.getString("sSaleA");
                boolean lPublishLots = resultSet.getBoolean("lPublishLots");
                boolean lPublishResults = resultSet.getBoolean("lPublishResults");
                boolean lSaleCancelled = resultSet.getBoolean("lSaleCancelled");
                int iItemsCatalogued = resultSet.getInt("iItemsCatalogued");
                boolean lExportDTP = resultSet.getBoolean("lExportDTP");
                String sSmartSaleNo = resultSet.getString("sSmartSaleNo");
                int iLotsCancelled = resultSet.getInt("iLotsCancelled");
                int iLotsNo = resultSet.getInt("iLotsNo");
                String sSaleStatus = resultSet.getString("sSaleStatus");
                int iNoRegs = resultSet.getInt("iNoRegs");
                int iNoBids = resultSet.getInt("iNoBids");
                String sVenue = resultSet.getString("sVenue");
                String sStartTime = resultSet.getString("sStartTime");
                int iLastPaddleNo = resultSet.getInt("iLastPaddleNo");
                int iLastBidPaddleNo = resultSet.getInt("iLastBidPaddleNo");
                double dPrice1 = resultSet.getDouble("dPrice1");
                double dPrice2 = resultSet.getDouble("dPrice2");
                double dPrice3 = resultSet.getDouble("dPrice3");
                double dPrice4 = resultSet.getDouble("dPrice4");
                boolean lMailListCreated = resultSet.getBoolean("lMailListCreated");
                String sOrigSource = resultSet.getString("sOrigSource");
                double dMinCommAmt = resultSet.getDouble("dMinCommAmt");
                Timestamp daCurrencyDate = resultSet.getTimestamp("daCurrencyDate");
                String sCurrencyID2 = resultSet.getString("sCurrencyID2");
                String sCurrencyID3 = resultSet.getString("sCurrencyID3");
                double dCurrencyRate2 = resultSet.getDouble("dCurrencyRate2");
                double dCurrencyRate3 = resultSet.getDouble("dCurrencyRate3");
                int iSettlementDays = resultSet.getInt("iSettlementDays");
                String sDivision = resultSet.getString("sDivision");
                boolean lPrivateTreaty = resultSet.getBoolean("lPrivateTreaty");
                Timestamp daDueDate = resultSet.getTimestamp("daDueDate");
                String sCatVolume = resultSet.getString("sCatVolume");
                boolean lSaleMatched = resultSet.getBoolean("lSaleMatched");
                boolean lFullPresale = resultSet.getBoolean("lFullPresale");
                String sDepartment = resultSet.getString("sDepartment");
                boolean lMinCommTaxIncl = resultSet.getBoolean("lMinCommTaxIncl");
                int iItemsAgreed = resultSet.getInt("iItemsAgreed");
                int iLotsProformad = resultSet.getInt("iLotsProformad");
                int iLotsPaid = resultSet.getInt("iLotsPaid");
                int iLotsReoffered = resultSet.getInt("iLotsReoffered");
                double dCatPrice = resultSet.getDouble("dCatPrice");
                boolean lPublishSections = resultSet.getBoolean("lPublishSections");
                boolean lExtraSale = resultSet.getBoolean("lExtraSale");
                String sSaleURL = resultSet.getString("sSaleURL");
                boolean lOfferPreReg = resultSet.getBoolean("lOfferPreReg");
                boolean lOffsite = resultSet.getBoolean("lOffsite");
                boolean lOfferCondition = resultSet.getBoolean("lOfferCondition");
                String sSaleType = resultSet.getString("sSaleType");
                int iPublishDays = resultSet.getInt("iPublishDays");
                int iConsignDays = resultSet.getInt("iConsignDays");
                Timestamp daConsign = resultSet.getTimestamp("daConsign");
                boolean lBidOnline = resultSet.getBoolean("lBidOnline");
                boolean lBiographies = resultSet.getBoolean("lBiographies");
                boolean lUseDealerLicence = resultSet.getBoolean("lUseDealerLicence");
                String sBidOnlineStatus = resultSet.getString("sBidOnlineStatus");
                rowList.add(new Object[] { iSaleNo, sSaleName, sAuditType, iCompanyNo, daStartDate, daEndDate, iItemsAssigned, iItemsLotted, iLotsSold, iLotsUnsold, sCurrencyID1, iLotsInvoiced, iLotsSettled, daSettlementDate, dCurrencyRate1, iLotsWithdrawn, lPublishSale, sSaleA, lPublishLots, lPublishResults, lSaleCancelled, iItemsCatalogued, lExportDTP, sSmartSaleNo, iLotsCancelled, iLotsNo, sSaleStatus, iNoRegs, iNoBids, sVenue, sStartTime, iLastPaddleNo, iLastBidPaddleNo, dPrice1, dPrice2,
                        dPrice3, dPrice4, lMailListCreated, sOrigSource, dMinCommAmt, daCurrencyDate, sCurrencyID2, sCurrencyID3, dCurrencyRate2, dCurrencyRate3, iSettlementDays, sDivision, lPrivateTreaty, daDueDate, sCatVolume, lSaleMatched, lFullPresale, sDepartment, lMinCommTaxIncl, iItemsAgreed, iLotsProformad, iLotsPaid, iLotsReoffered, dCatPrice, lPublishSections, lExtraSale, sSaleURL, lOfferPreReg, lOffsite, lOfferCondition, sSaleType, iPublishDays, iConsignDays, daConsign, lBidOnline,
                        lBiographies, lUseDealerLicence, sBidOnlineStatus });
                c++;
            }
            if (rowList.size() > 0) {
                mOwningDAO.insertIntoSaleTable(rowList);
                rowList.clear();
            }

            return c;
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }
    }

    public int importSaleLotA() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            String tableName = "SaleLotA";
            int max = mOwningDAO.getMaxId(tableName);
            if (max == 0) {
                stmt = getConnection().prepareStatement(getSaleLotA);
            } else {
                stmt = getConnection().prepareStatement(getSaleLotAWithMax);
                stmt.setInt(1, max);
            }
            stmt.setFetchSize(1000);
            resultSet = stmt.executeQuery();
            List rowList = new ArrayList();
            int c = 0;
            while (resultSet.next()) {
                if (rowList.size() == 1000) {
                    mOwningDAO.insertIntoSaleLotATable(rowList);
                    rowList.clear();
                }
                int iSaleLotNoUnique = resultSet.getInt("iSaleLotNoUnique");
                double dCommAmtAdj = resultSet.getDouble("dCommAmtAdj");
                double dCommAmtRfd = resultSet.getDouble("dCommAmtRfd");
                double dCommTaxAdj = resultSet.getDouble("dCommTaxAdj");
                double dCommTaxRfd = resultSet.getDouble("dCommTaxRfd");
                double dBIFeeAdj = resultSet.getDouble("dBIFeeAdj");
                double dBIFeeRfd = resultSet.getDouble("dBIFeeRfd");
                double dBIFeeTaxAdj = resultSet.getDouble("dBIFeeTaxAdj");
                double dBIFeeTaxRfd = resultSet.getDouble("dBIFeeTaxRfd");
                double dWDFeeAdj = resultSet.getDouble("dWDFeeAdj");
                double dWDFeeRfd = resultSet.getDouble("dWDFeeRfd");
                double dWDFeeTaxAdj = resultSet.getDouble("dWDFeeTaxAdj");
                double dWDFeeTaxRfd = resultSet.getDouble("dWDFeeTaxRfd");
                double dInsAmtAdj = resultSet.getDouble("dInsAmtAdj");
                double dInsAmtRfd = resultSet.getDouble("dInsAmtRfd");
                double dInsTaxAdj = resultSet.getDouble("dInsTaxAdj");
                double dInsTaxRfd = resultSet.getDouble("dInsTaxRfd");
                double dIllustrationFeeRfd = resultSet.getDouble("dIllustrationFeeRfd");
                double dIllustrationFeeTaxRfd = resultSet.getDouble("dIllustrationFeeTaxRfd");
                double dOtherRefundAmt = resultSet.getDouble("dOtherRefundAmt");
                double dOtherRefundTax = resultSet.getDouble("dOtherRefundTax");
                double dARLAmtAdj = resultSet.getDouble("dARLAmtAdj");
                double dMerchandiseFeeAdj = resultSet.getDouble("dMerchandiseFeeAdj");
                double dMerchandiseFeeRfd = resultSet.getDouble("dMerchandiseFeeRfd");
                double dARLAmtRfd = resultSet.getDouble("dARLAmtRfd");
                double dpremtaxrate2 = resultSet.getDouble("dpremtaxrate2");
                double dchargeamtbuytax2 = resultSet.getDouble("dchargeamtbuytax2");
                double dchargeamtbuytaxrate2 = resultSet.getDouble("dchargeamtbuytaxrate2");
                double dhammertaxrate2 = resultSet.getDouble("dhammertaxrate2");
                double dhammertax2 = resultSet.getDouble("dhammertax2");
                double dpremtax2 = resultSet.getDouble("dpremtax2");
                rowList.add(new Object[] { iSaleLotNoUnique, dCommAmtAdj, dCommAmtRfd, dCommTaxAdj, dCommTaxRfd, dBIFeeAdj, dBIFeeRfd, dBIFeeTaxAdj, dBIFeeTaxRfd, dWDFeeAdj, dWDFeeRfd, dWDFeeTaxAdj, dWDFeeTaxRfd, dInsAmtAdj, dInsAmtRfd, dInsTaxAdj, dInsTaxRfd, dIllustrationFeeRfd, dIllustrationFeeTaxRfd, dOtherRefundAmt, dOtherRefundTax, dARLAmtAdj, dMerchandiseFeeAdj, dMerchandiseFeeRfd, dARLAmtRfd, dpremtaxrate2, dchargeamtbuytax2, dchargeamtbuytaxrate2, dhammertaxrate2, dhammertax2,
                        dpremtax2 });
                c++;
            }
            if (rowList.size() > 0) {
                mOwningDAO.insertIntoSaleLotATable(rowList);
                rowList.clear();
            }
            return c;
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }
    }

    public int importSaleItem() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            String tableName = "SaleItem";
            int max = mOwningDAO.getMaxId(tableName);
            if (max == 0) {
                stmt = getConnection().prepareStatement(getSaleItem);
            } else {
                stmt = getConnection().prepareStatement(getSaleItemWithMax);
                stmt.setInt(1, max);
            }
            stmt.setFetchSize(1000);
            resultSet = stmt.executeQuery();
            List rowList = new ArrayList();
            int c = 0;
            while (resultSet.next()) {
                if (rowList.size() == 1000) {
                    mOwningDAO.insertIntoSaleItemTable(rowList);
                    rowList.clear();
                }
                int iSaleItemNo = resultSet.getInt("iSaleItemNo");
                int iSplitNo = resultSet.getInt("iSplitNo");
                int iReceiptNo = resultSet.getInt("iReceiptNo");
                int iClientNo = resultSet.getInt("iClientNo");
                String sClientRef = resultSet.getString("sClientRef");
                String sDesc = resultSet.getString("sDesc");
                int iSaleItemType = resultSet.getInt("iSaleItemType");
                String sSaleItemStatus = resultSet.getString("sSaleItemStatus");
                int iCompanyNo = resultSet.getInt("iCompanyNo");
                int iSaleNo = resultSet.getInt("iSaleNo");
                String sCurrencyID = resultSet.getString("sCurrencyID");
                int dEstimateLow = resultSet.getInt("dEstimateLow");
                int dEstimateHigh = resultSet.getInt("dEstimateHigh");
                int dReservePrice = resultSet.getInt("dReservePrice");
                String cReserveType = resultSet.getString("cReserveType");
                int iNoImages = resultSet.getInt("iNoImages");
                boolean lCatalogued = resultSet.getBoolean("lCatalogued");
                double dIllustrationFee = resultSet.getDouble("dIllustrationFee");
                String sNotes = resultSet.getString("sNotes");
                boolean lLotted = resultSet.getBoolean("lLotted");
                String sAuditType = resultSet.getString("sAuditType");
                double dEstimateInitial = resultSet.getDouble("dEstimateInitial");
                String sValuationUserID = resultSet.getString("sValuationUserID");
                int iReceiptLineNo = resultSet.getInt("iReceiptLineNo");
                int iReOfferNo = resultSet.getInt("iReOfferNo");
                String sOrig = resultSet.getString("sOrig");
                String sOrigSource = resultSet.getString("sOrigSource");
                int iSaleLotNo = resultSet.getInt("iSaleLotNo");
                String sSaleLotNoA = resultSet.getString("sSaleLotNoA");
                boolean lReOffer = resultSet.getBoolean("lReOffer");
                double dFaceValue = resultSet.getDouble("dFaceValue");
                double dBondValue = resultSet.getDouble("dBondValue");
                boolean lBonded = resultSet.getBoolean("lBonded");
                int iSaleLotNoUnique = resultSet.getInt("iSaleLotNoUnique");
                int iSaleSectionNo = resultSet.getInt("iSaleSectionNo");
                int iHammerTaxCode = resultSet.getInt("iHammerTaxCode");
                String sTempGroupBy = resultSet.getString("sTempGroupBy");
                int iTempLotNo = resultSet.getInt("iTempLotNo");
                String sLocDepartment = resultSet.getString("sLocDepartment");
                String sDivision = resultSet.getString("sDivision");
                String sDepartment = resultSet.getString("sDepartment");
                String sLocDivision = resultSet.getString("sLocDivision");
                String sDeptOld = resultSet.getString("sDeptOld");
                String sReserveCurrencyID = resultSet.getString("sReserveCurrencyID");
                int iImportDutyCode = resultSet.getInt("iImportDutyCode");
                String sHeaderType = resultSet.getString("sHeaderType");
                int iCustomsNo = resultSet.getInt("iCustomsNo");
                String sLotStatus = resultSet.getString("sLotStatus");
                double dEstimateLowCur = resultSet.getDouble("dEstimateLowCur");
                double dEstimateHighCur = resultSet.getDouble("dEstimateHighCur");
                int iSalesTaxCode = resultSet.getInt("iSalesTaxCode");
                int iOtherTaxCode = resultSet.getInt("iOtherTaxCode");
                boolean lIllustrated = resultSet.getBoolean("lIllustrated");
                double dVendorNet = resultSet.getDouble("dVendorNet");
                double dVendorTax = resultSet.getDouble("dVendorTax");
                double dReservePriceCur = resultSet.getDouble("dReservePriceCur");
                double dReserveCurrencyRate = resultSet.getDouble("dReserveCurrencyRate");
                Timestamp daReservePriceCurDate = resultSet.getTimestamp("daReservePriceCurDate");
                double dValuation = resultSet.getDouble("dValuation");
                boolean lIllustrationChg = resultSet.getBoolean("lIllustrationChg");
                double dVendorTotal = resultSet.getDouble("dVendorTotal");
                String sValLoc1 = resultSet.getString("sValLoc1");
                String sValLoc2 = resultSet.getString("sValLoc2");
                String sValLoc3 = resultSet.getString("sValLoc3");
                int iSettlementNo = resultSet.getInt("iSettlementNo");
                int iVendorPaymentNo = resultSet.getInt("iVendorPaymentNo");
                int iNumLots = resultSet.getInt("iNumLots");
                boolean lFoldable = resultSet.getBoolean("lFoldable");
                int iCollectionNo = resultSet.getInt("iCollectionNo");
                String sIllustrationType = resultSet.getString("sIllustrationType");
                boolean lPrivateTreatyLot = resultSet.getBoolean("lPrivateTreatyLot");
                boolean lNotPublishedOnWeb = resultSet.getBoolean("lNotPublishedOnWeb");
                boolean lSaleAgreed = resultSet.getBoolean("lSaleAgreed");
                Timestamp daImportDate = resultSet.getTimestamp("daImportDate");
                double dImportDutyRate = resultSet.getDouble("dImportDutyRate");
                double dImportDutyAmt = resultSet.getDouble("dImportDutyAmt");
                Timestamp daLastScanDate = resultSet.getTimestamp("daLastScanDate");
                int iQuantity = resultSet.getInt("iQuantity");
                int iReleaseNo = resultSet.getInt("iReleaseNo");
                boolean lCites = resultSet.getBoolean("lCites");
                String sArmsType = resultSet.getString("sArmsType");
                double dReceiptLineNo = resultSet.getDouble("dReceiptLineNo");
                String sReceiptLineNo = resultSet.getString("sReceiptLineNo");
                int iNewContractNo = resultSet.getInt("iNewContractNo");
                int iNewContractLineNo = resultSet.getInt("iNewContractLineNo");
                boolean lReferDept = resultSet.getBoolean("lReferDept");
                double dEstimateLowCur3 = resultSet.getDouble("dEstimateLowCur3");
                double dEstimateHighCur3 = resultSet.getDouble("dEstimateHighCur3");
                boolean lARLFlag = resultSet.getBoolean("lARLFlag");
                int iArtistNo = resultSet.getInt("iArtistNo");
                double dPurchasePrice = resultSet.getDouble("dPurchasePrice");
                String sLotSymbols = resultSet.getString("sLotSymbols");
                int dEstimateLowGroup = resultSet.getInt("dEstimateLowGroup");
                int dEstimateHighGroup = resultSet.getInt("dEstimateHighGroup");
                boolean lSaleGuaranteed = resultSet.getBoolean("lSaleGuaranteed");
                boolean lUnderCopyright = resultSet.getBoolean("lUnderCopyright");
                boolean lWithoutReserve = resultSet.getBoolean("lWithoutReserve");
                boolean lNotForMarketing = resultSet.getBoolean("lNotForMarketing");
                boolean lValued = resultSet.getBoolean("lValued");
                boolean lPublished = resultSet.getBoolean("lPublished");
                boolean lDisplayValuer = resultSet.getBoolean("lDisplayValuer");
                int iNoValueRationale = resultSet.getInt("iNoValueRationale");
                String sNoValueComments = resultSet.getString("sNoValueComments");
                boolean lIvory = resultSet.getBoolean("lIvory");
                boolean lPreviewLot = resultSet.getBoolean("lPreviewLot");
                rowList.add(new Object[] { iSaleItemNo, iSplitNo, iReceiptNo, iClientNo, sClientRef, sDesc, iSaleItemType, sSaleItemStatus, iCompanyNo, iSaleNo, sCurrencyID, dEstimateLow, dEstimateHigh, dReservePrice, cReserveType, iNoImages, lCatalogued, dIllustrationFee, sNotes, lLotted, sAuditType, dEstimateInitial, sValuationUserID, iReceiptLineNo, iReOfferNo, sOrig, sOrigSource, iSaleLotNo, sSaleLotNoA, lReOffer, dFaceValue, dBondValue, lBonded, iSaleLotNoUnique, iSaleSectionNo, iHammerTaxCode,
                        sTempGroupBy, iTempLotNo, sLocDepartment, sDivision, sDepartment, sLocDivision, sDeptOld, sReserveCurrencyID, iImportDutyCode, sHeaderType, iCustomsNo, sLotStatus, dEstimateLowCur, dEstimateHighCur, iSalesTaxCode, iOtherTaxCode, lIllustrated, dVendorNet, dVendorTax, dReservePriceCur, dReserveCurrencyRate, daReservePriceCurDate, dValuation, lIllustrationChg, dVendorTotal, sValLoc1, sValLoc2, sValLoc3, iSettlementNo, iVendorPaymentNo, iNumLots, lFoldable, iCollectionNo,
                        sIllustrationType, lPrivateTreatyLot, lNotPublishedOnWeb, lSaleAgreed, daImportDate, dImportDutyRate, dImportDutyAmt, daLastScanDate, iQuantity, iReleaseNo, lCites, sArmsType, dReceiptLineNo, sReceiptLineNo, iNewContractNo, iNewContractLineNo, lReferDept, dEstimateLowCur3, dEstimateHighCur3, lARLFlag, iArtistNo, dPurchasePrice, sLotSymbols, dEstimateLowGroup, dEstimateHighGroup, lSaleGuaranteed, lUnderCopyright, lWithoutReserve, lNotForMarketing, lValued, lPublished,
                        lDisplayValuer, iNoValueRationale, sNoValueComments, lIvory, lPreviewLot });
                c++;
            }
            if (rowList.size() > 0) {
                mOwningDAO.insertIntoSaleItemTable(rowList);
                rowList.clear();
            }
            return c;

        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }
    }
    
    public void insertIntoAuctionLookup() throws SQLException {
        mOwningDAO.insertIntoAuctionLookup();
    }

    public int importSaleBid() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            String tableName = "SaleBid";
            int max = mOwningDAO.getMaxId(tableName);
            if (max == 0) {
                stmt = getConnection().prepareStatement(getSaleBid);
            } else {
                stmt = getConnection().prepareStatement(getSaleBidWithMax);
                stmt.setInt(1, max);
            }
            stmt.setFetchSize(1000);
            resultSet = stmt.executeQuery();
            List rowList = new ArrayList();
            int c = 0;
            while (resultSet.next()) {
                if (rowList.size() == 1000) {
                    mOwningDAO.insertIntoSaleBidTable(rowList);
                    rowList.clear();
                }
                int iSaleNo = resultSet.getInt("iSaleNo");
                int iSaleLotNo = resultSet.getInt("iSaleLotNo");
                int iClientNo = resultSet.getInt("iClientNo");
                int iAddressNo = resultSet.getInt("iAddressNo");
                String cBidType = resultSet.getString("cBidType");
                int iPaddleNo = resultSet.getInt("iPaddleNo");
                double dBidAmt = resultSet.getDouble("dBidAmt");
                int iBidPlus = resultSet.getInt("iBidPlus");
                String sTelNo1 = resultSet.getString("sTelNo1");
                String sTelNo2 = resultSet.getString("sTelNo2");
                String sTelNo3 = resultSet.getString("sTelNo3");
                String sTelNo4 = resultSet.getString("sTelNo4");
                String sAuditType = resultSet.getString("sAuditType");
                String sAuditUserID = resultSet.getString("sAuditUserID");
                Timestamp daAuditDate = resultSet.getTimestamp("daAuditDate");
                String sAuditTime = resultSet.getString("sAuditTime");
                int iSaleBidNo = resultSet.getInt("iSaleBidNo");
                String sEmail = resultSet.getString("sEmail");
                String sNotes = resultSet.getString("sNotes");
                int iSaleLotNoUnique = resultSet.getInt("iSaleLotNoUnique");
                String sSaleLotNoA = resultSet.getString("sSaleLotNoA");
                Timestamp daBidDate = resultSet.getTimestamp("daBidDate");
                String sBidTime = resultSet.getString("sBidTime");
                boolean lCancelled = resultSet.getBoolean("lCancelled");
                String sBidDateTime = resultSet.getString("sBidDateTime");
                String sDivision = resultSet.getString("sDivision");
                rowList.add(new Object[] { iSaleNo, iSaleLotNo, iClientNo, iAddressNo, cBidType, iPaddleNo, dBidAmt, iBidPlus, sTelNo1, sTelNo2, sTelNo3, sTelNo4, sAuditType, sAuditUserID, daAuditDate, sAuditTime, iSaleBidNo, sEmail, sNotes, iSaleLotNoUnique, sSaleLotNoA, daBidDate, sBidTime, lCancelled, sBidDateTime, sDivision });
                c++;
            }
            if (rowList.size() > 0) {
                mOwningDAO.insertIntoSaleBidTable(rowList);
                rowList.clear();
            }
            return c;

        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }
    }

    public int importSaleLot() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            String tableName = "SaleLot";
            int max = mOwningDAO.getMaxId(tableName);
            if (max == 0) {
                stmt = getConnection().prepareStatement(getSaleLot);
            } else {
                stmt = getConnection().prepareStatement(getSaleLotWithMax);
                stmt.setInt(1, max);
            }
            stmt.setFetchSize(1000);
            resultSet = stmt.executeQuery();
            List rowList = new ArrayList();
            int c = 0;
            while (resultSet.next()) {
                if (rowList.size() == 1000) {
                    mOwningDAO.insertIntoSaleLotTable(rowList);
                    rowList.clear();
                }
                int iSaleLotNoUnique = resultSet.getInt("iSaleLotNoUnique");
                int iSaleSectionNo = resultSet.getInt("iSaleSectionNo");
                int iSaleNo = resultSet.getInt("iSaleNo");
                int iSaleLotNo = resultSet.getInt("iSaleLotNo");
                int iSaleItemNo = resultSet.getInt("iSaleItemNo");
                String sLotStatus = resultSet.getString("sLotStatus");
                double dCurrRateSale = resultSet.getDouble("dCurrRateSale");
                double dCurrRateSettlement = resultSet.getDouble("dCurrRateSettlement");
                double dHammerPrice = resultSet.getDouble("dHammerPrice");
                int iPaddleNo = resultSet.getInt("iPaddleNo");
                int iClientNo = resultSet.getInt("iClientNo");
                double dPassPrice = resultSet.getDouble("dPassPrice");
                double dCommAmt = resultSet.getDouble("dCommAmt");
                double dCommPer = resultSet.getDouble("dCommPer");
                double dPremAmt = resultSet.getDouble("dPremAmt");
                double dPremPer = resultSet.getDouble("dPremPer");
                double dInsAmt = resultSet.getDouble("dInsAmt");
                double dInsPer = resultSet.getDouble("dInsPer");
                boolean lExtraLot = resultSet.getBoolean("lExtraLot");
                int iInvoiceNo = resultSet.getInt("iInvoiceNo");
                int iInvoiceLineNo = resultSet.getInt("iInvoiceLineNo");
                int iSettlementNo = resultSet.getInt("iSettlementNo");
                int iSettlementLineNo = resultSet.getInt("iSettlementLineNo");
                int dOtherChargeAmt = resultSet.getInt("dOtherChargeAmt");
                double dChargeAmtBuy = resultSet.getDouble("dChargeAmtBuy");
                double dIllustrationFee = resultSet.getDouble("dIllustrationFee");
                String sSaleLotNoA = resultSet.getString("sSaleLotNoA");
                double dHammerTax = resultSet.getDouble("dHammerTax");
                double dPremTax = resultSet.getDouble("dPremTax");
                double dCommTax = resultSet.getDouble("dCommTax");
                double dSalesTax = resultSet.getDouble("dSalesTax");
                double dInsTax = resultSet.getDouble("dInsTax");
                double dHammerTaxRate = resultSet.getDouble("dHammerTaxRate");
                double dPremTaxRate = resultSet.getDouble("dPremTaxRate");
                double dCommTaxRate = resultSet.getDouble("dCommTaxRate");
                double dSalesTaxRate = resultSet.getDouble("dSalesTaxRate");
                double dInsTaxRate = resultSet.getDouble("dInsTaxRate");
                double dChargeAmtBuyTax = resultSet.getDouble("dChargeAmtBuyTax");
                double dChargeAmtBuyTaxRate = resultSet.getDouble("dChargeAmtBuyTaxRate");
                int iVendorNo = resultSet.getInt("iVendorNo");
                int iCommTaxCode = resultSet.getInt("iCommTaxCode");
                int iHammerTaxCode = resultSet.getInt("iHammerTaxCode");
                int iPremTaxCode = resultSet.getInt("iPremTaxCode");
                double dVendorTotal = resultSet.getDouble("dVendorTotal");
                double dBuyerTotal = resultSet.getDouble("dBuyerTotal");
                double dBuyerNett = resultSet.getDouble("dBuyerNett");
                double dBuyerTax = resultSet.getDouble("dBuyerTax");
                double dVendorTax = resultSet.getDouble("dVendorTax");
                double dVendorNett = resultSet.getDouble("dVendorNett");
                double dBIFee = resultSet.getDouble("dBIFee");
                double dWDFee = resultSet.getDouble("dWDFee");
                String sLotDesc = resultSet.getString("sLotDesc");
                boolean lBonded = resultSet.getBoolean("lBonded");
                double dBondValue = resultSet.getDouble("dBondValue");
                int iPaymentNo = resultSet.getInt("iPaymentNo");
                int iPaymentLineNo = resultSet.getInt("iPaymentLineNo");
                double dImportDutyRate = resultSet.getDouble("dImportDutyRate");
                double dImportDuty = resultSet.getDouble("dImportDuty");
                double dVendorHammerTax = resultSet.getDouble("dVendorHammerTax");
                int iProformaInvNo = resultSet.getInt("iProformaInvNo");
                int iProformaInvLineNo = resultSet.getInt("iProformaInvLineNo");
                int iInsTaxCode = resultSet.getInt("iInsTaxCode");
                String sSalesTaxFN = resultSet.getString("sSalesTaxFN");
                int iImportDutyCode = resultSet.getInt("iImportDutyCode");
                int iSalesTaxCode = resultSet.getInt("iSalesTaxCode");
                double dVendorHammerPrice = resultSet.getDouble("dVendorHammerPrice");
                double dBIFeeTax = resultSet.getDouble("dBIFeeTax");
                double dWDFeeTax = resultSet.getDouble("dWDFeeTax");
                int iReceiptNo = resultSet.getInt("iReceiptNo");
                int iReceiptLineNo = resultSet.getInt("iReceiptLineNo");
                double dICTax = resultSet.getDouble("dICTax");
                int iICSettlementNo = resultSet.getInt("iICSettlementNo");
                int iVendorPaymentNo = resultSet.getInt("iVendorPaymentNo");
                int iICClientNo = resultSet.getInt("iICClientNo");
                String sICRepUserID = resultSet.getString("sICRepUserID");
                double dICCommPer = resultSet.getDouble("dICCommPer");
                double dICAmt = resultSet.getDouble("dICAmt");
                boolean lHeldLot = resultSet.getBoolean("lHeldLot");
                int iVendorHammerTaxCode = resultSet.getInt("iVendorHammerTaxCode");
                double dVendorHammerTaxRate = resultSet.getDouble("dVendorHammerTaxRate");
                double dFaceValue = resultSet.getDouble("dFaceValue");
                int iChargeAmtBuyTaxCode = resultSet.getInt("iChargeAmtBuyTaxCode");
                int iHeldLotJobLogNo = resultSet.getInt("iHeldLotJobLogNo");
                boolean lWDFeeCharged = resultSet.getBoolean("lWDFeeCharged");
                double dOtherChargeTax = resultSet.getDouble("dOtherChargeTax");
                double dIllustrationFeeTax = resultSet.getDouble("dIllustrationFeeTax");
                double dVendorNettAdj = resultSet.getDouble("dVendorNettAdj");
                double dVendorTaxAdj = resultSet.getDouble("dVendorTaxAdj");
                double dVendorNettRefund = resultSet.getDouble("dVendorNettRefund");
                double dVendorTaxRefund = resultSet.getDouble("dVendorTaxRefund");
                String sSaleDivision = resultSet.getString("sSaleDivision");
                String sSaleDepartment = resultSet.getString("sSaleDepartment");
                String sReceiptDivision = resultSet.getString("sReceiptDivision");
                String sRecDepartment = resultSet.getString("sRecDepartment");
                int iICSettlementLineNo = resultSet.getInt("iICSettlementLineNo");
                int dICTaxRate = resultSet.getInt("dICTaxRate");
                int iAddressNoShipTo = resultSet.getInt("iAddressNoShipTo");
                int iClientResaleNoUnique = resultSet.getInt("iClientResaleNoUnique");
                int iSalesTaxNo = resultSet.getInt("iSalesTaxNo");
                boolean lManualBuyerCalc = resultSet.getBoolean("lManualBuyerCalc");
                boolean lUseResaleLicence = resultSet.getBoolean("lUseResaleLicence");
                String sResaleItemType = resultSet.getString("sResaleItemType");
                double dICHammerPer = resultSet.getDouble("dICHammerPer");
                double dICPremPer = resultSet.getDouble("dICPremPer");
                double dICFlatFee = resultSet.getDouble("dICFlatFee");
                double dVendorMerchandiseFeeRate = resultSet.getDouble("dVendorMerchandiseFeeRate");
                double dVendorMerchandiseFee = resultSet.getDouble("dVendorMerchandiseFee");
                double dVendorImportDutyRate = resultSet.getDouble("dVendorImportDutyRate");
                double dVendorImportDuty = resultSet.getDouble("dVendorImportDuty");
                double dVendorSalesTaxRate = resultSet.getDouble("dVendorSalesTaxRate");
                double dVendorSalesTaxAmt = resultSet.getDouble("dVendorSalesTaxAmt");
                int iCompanyNo = resultSet.getInt("iCompanyNo");
                double dBIFeeRate = resultSet.getDouble("dBIFeeRate");
                String sResaleLicenceNo = resultSet.getString("sResaleLicenceNo");
                String sARLType = resultSet.getString("sARLType");
                boolean lBuyerOptOut = resultSet.getBoolean("lBuyerOptOut");
                boolean lAfterSale = resultSet.getBoolean("lAfterSale");
                double dDiscNet = resultSet.getDouble("dDiscNet");
                double dDiscTax = resultSet.getDouble("dDiscTax");
                double dDiscAmt = resultSet.getDouble("dDiscAmt");
                boolean lDiscFlag = resultSet.getBoolean("lDiscFlag");
                boolean lARLFlag = resultSet.getBoolean("lARLFlag");
                double dARLAmt = resultSet.getDouble("dARLAmt");
                int iRoyaltyNo = resultSet.getInt("iRoyaltyNo");
                String sLotSymbols = resultSet.getString("sLotSymbols");
                double dHammerPriceGroup = resultSet.getDouble("dHammerPriceGroup");
                String cBidType = resultSet.getString("cBidType");
                int iClientDealerNoUnique = resultSet.getInt("iClientDealerNoUnique");
                boolean lUseDealerLicence = resultSet.getBoolean("lUseDealerLicence");
                String sDealerLicenceNo = resultSet.getString("sDealerLicenceNo");
                rowList.add(new Object[] { iSaleLotNoUnique, iSaleSectionNo, iSaleNo, iSaleLotNo, iSaleItemNo, sLotStatus, dCurrRateSale, dCurrRateSettlement, dHammerPrice, iPaddleNo, iClientNo, dPassPrice, dCommAmt, dCommPer, dPremAmt, dPremPer, dInsAmt, dInsPer, lExtraLot, iInvoiceNo, iInvoiceLineNo, iSettlementNo, iSettlementLineNo, dOtherChargeAmt, dChargeAmtBuy, dIllustrationFee, sSaleLotNoA, dHammerTax, dPremTax, dCommTax, dSalesTax, dInsTax, dHammerTaxRate, dPremTaxRate, dCommTaxRate,
                        dSalesTaxRate, dInsTaxRate, dChargeAmtBuyTax, dChargeAmtBuyTaxRate, iVendorNo, iCommTaxCode, iHammerTaxCode, iPremTaxCode, dVendorTotal, dBuyerTotal, dBuyerNett, dBuyerTax, dVendorTax, dVendorNett, dBIFee, dWDFee, sLotDesc, lBonded, dBondValue, iPaymentNo, iPaymentLineNo, dImportDutyRate, dImportDuty, dVendorHammerTax, iProformaInvNo, iProformaInvLineNo, iInsTaxCode, sSalesTaxFN, iImportDutyCode, iSalesTaxCode, dVendorHammerPrice, dBIFeeTax, dWDFeeTax, iReceiptNo,
                        iReceiptLineNo, dICTax, iICSettlementNo, iVendorPaymentNo, iICClientNo, sICRepUserID, dICCommPer, dICAmt, lHeldLot, iVendorHammerTaxCode, dVendorHammerTaxRate, dFaceValue, iChargeAmtBuyTaxCode, iHeldLotJobLogNo, lWDFeeCharged, dOtherChargeTax, dIllustrationFeeTax, dVendorNettAdj, dVendorTaxAdj, dVendorNettRefund, dVendorTaxRefund, sSaleDivision, sSaleDepartment, sReceiptDivision, sRecDepartment, iICSettlementLineNo, dICTaxRate, iAddressNoShipTo, iClientResaleNoUnique,
                        iSalesTaxNo, lManualBuyerCalc, lUseResaleLicence, sResaleItemType, dICHammerPer, dICPremPer, dICFlatFee, dVendorMerchandiseFeeRate, dVendorMerchandiseFee, dVendorImportDutyRate, dVendorImportDuty, dVendorSalesTaxRate, dVendorSalesTaxAmt, iCompanyNo, dBIFeeRate, sResaleLicenceNo, sARLType, lBuyerOptOut, lAfterSale, dDiscNet, dDiscTax, dDiscAmt, lDiscFlag, lARLFlag, dARLAmt, iRoyaltyNo, sLotSymbols, dHammerPriceGroup, cBidType, iClientDealerNoUnique, lUseDealerLicence,
                        sDealerLicenceNo });
                c++;
            }
            if (rowList.size() > 0) {
                mOwningDAO.insertIntoSaleLotTable(rowList);
                rowList.clear();
            }
            return c;

        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }
    }

    public int importSaleLotCancel() throws SQLException {
        PreparedStatement stmt = null;
        ResultSet resultSet = null;
        try {
            String tableName = "SaleLotCancel";
            int max = mOwningDAO.getMaxId(tableName);
            if (max == 0) {
                stmt = getConnection().prepareStatement(getSaleLotCancel);
            } else {
                stmt = getConnection().prepareStatement(getSaleLotCancelWithMax);
                stmt.setInt(1, max);
            }
            stmt.setFetchSize(1000);
            resultSet = stmt.executeQuery();
            List rowList = new ArrayList();
            int c = 0;
            while (resultSet.next()) {
                if (rowList.size() == 1000) {
                    mOwningDAO.insertIntoSaleLotCancelTable(rowList);
                    rowList.clear();
                }
                int iSaleNo = resultSet.getInt("iSaleNo");
                int iSaleLotNo = resultSet.getInt("iSaleLotNo");
                String sSaleLotNoA = resultSet.getString("sSaleLotNoA");
                int iReceiptNo = resultSet.getInt("iReceiptNo");
                int iReceiptLineNo = resultSet.getInt("iReceiptLineNo");
                int iInvoiceNo = resultSet.getInt("iInvoiceNo");
                int iInvoiceLineNo = resultSet.getInt("iInvoiceLineNo");
                int iSettlementNo = resultSet.getInt("iSettlementNo");
                int iSettlementLineNo = resultSet.getInt("iSettlementLineNo");
                int iReceiptNoNew = resultSet.getInt("iReceiptNoNew");
                int iReceiptLineNoNew = resultSet.getInt("iReceiptLineNoNew");
                String sCancelType = resultSet.getString("sCancelType");
                String sCancelNotes = resultSet.getString("sCancelNotes");
                int iSaleLotNoUnique = resultSet.getInt("iSaleLotNoUnique");
                int iSeqNo = resultSet.getInt("iSeqNo");
                int iCompanyNo = resultSet.getInt("iCompanyNo");
                String sDivision = resultSet.getString("sDivision");
                String sDepartment = resultSet.getString("sDepartment");
                int iClientNo = resultSet.getInt("iClientNo");
                int iVendorNo = resultSet.getInt("iVendorNo");
                int iCreditNo = resultSet.getInt("iCreditNo");
                int iCreditLineNo = resultSet.getInt("iCreditLineNo");
                int iProformaInvNo = resultSet.getInt("iProformaInvNo");
                int iProformaInvLineNo = resultSet.getInt("iProformaInvLineNo");
                int iClientNoNew = resultSet.getInt("iClientNoNew");
                rowList.add(new Object[] { iSaleNo, iSaleLotNo, sSaleLotNoA, iReceiptNo, iReceiptLineNo, iInvoiceNo, iInvoiceLineNo, iSettlementNo, iSettlementLineNo, iReceiptNoNew, iReceiptLineNoNew, sCancelType, sCancelNotes, iSaleLotNoUnique, iSeqNo, iCompanyNo, sDivision, sDepartment, iClientNo, iVendorNo, iCreditNo, iCreditLineNo, iProformaInvNo, iProformaInvLineNo, iClientNoNew });
                c++;
            }
            if (rowList.size() > 0) {
                mOwningDAO.insertIntoSaleLotCancelTable(rowList);
                rowList.clear();
            }

            return c;
        } finally {
            closeResultSet(resultSet);
            closeStatement(stmt);
            closeConnection();
        }
    }

}
