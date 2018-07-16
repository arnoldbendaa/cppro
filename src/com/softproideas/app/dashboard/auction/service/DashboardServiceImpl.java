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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.utc.common.CPContext;
import com.softproideas.app.core.model.model.ModelCoreWithGlobalDTO;
import com.softproideas.app.core.model.service.ModelCoreService;
import com.softproideas.app.core.model.service.ModelCoreServiceImpl;
import com.softproideas.app.dashboard.auction.dao.DashboardDao;
import com.softproideas.app.dashboard.auction.dao.DashboardDaoImpl;
import com.softproideas.app.dashboard.auction.entity.DashboardEntity;
import com.softproideas.app.dashboard.auction.model.ValueRange;
import com.softproideas.app.dashboard.auction.model.AuctionBidType.BidType;
import com.softproideas.app.dashboard.auction.model.InterfaceComputingDataForDashboard.GraphType;
import com.softproideas.app.lookuptable.auction.dao.LookupAuctionDao;
import com.softproideas.app.lookuptable.auction.dao.LookupAuctionDaoImpl;
import com.softproideas.app.lookuptable.auction.model.DashboardAuctionDTO;
import com.softproideas.app.lookuptable.auction.model.LookupAuctionDTO;
import com.softproideas.app.lookuptable.auction.model.LookupAuctionFilterDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;

@Service("dashboardService")
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    DashboardDao dao;

    @Autowired
    ModelCoreService modelCoreService;
    int userId;
    public void Init(int userId,CPContext context){
    	this.userId = userId;
    	modelCoreService = new ModelCoreServiceImpl();
    	modelCoreService.Init(userId, context);
    	dao = new DashboardDaoImpl();
    	lookupAuctionDao = new LookupAuctionDaoImpl();
    }

    // @Autowired
    // XlsTemplate xlsTemplate;

    @Override
    public Map<String, Map<String, Map<String, String>>> browseAuctions(int auctionNumber) throws ServiceException {
        List<DashboardEntity> auctions;
        try {
            auctions = dao.browseAuctions(auctionNumber);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        Map<String, Map<String, Map<String, String>>> result = new HashMap<String, Map<String, Map<String, String>>>();
        int[] curr = { 0, 1000, 2500, 5000, 10000, 25000, 50000, 100000, 250000 };
        ValueRange range = new ValueRange(curr);
        DtoManager mngr = new DtoManager(range);
        result = mngr.createResult(auctions);
        // result.put("saleResult", DataBuilder.getSaleRusult(auctions));
        // result.put("valueOfLotsSold", DataBuilder.getValueOfLotsSold(auctions, curr));
        // result.put("numberOfLotsSold", DataBuilder.getNumberOfLotsSold(auctions, curr));
        // result.put("auctionBidType", DataBuilder.getAuctionBidType(auctions, curr));
        // result.put("incomeVsNumberOfLots", DataBuilder.getIncomeVsNumberOfLots(auctions, curr));
        // result.put("vendorsCommission", DataBuilder.getVendorsCommission(auctions, curr));

        return result;
    }

    @Override
    public Set<String> fetchModelsForLoggedUser() throws ServiceException {
        List<ModelCoreWithGlobalDTO> modelsDTO = modelCoreService.browseModelsForLoggedUser();
        Set<String> modelIds = new TreeSet<String>();
        for (ModelCoreWithGlobalDTO dto: modelsDTO) {
            if (dto == null) {
                continue;
            }
            if (dto.isGlobal() == false) {
                modelIds.add(dto.getModelVisId() + " - " + dto.getModelDescription());
                // String modelId = dto.getModelVisId();
                // if(modelId == null || modelId.trim().isEmpty()){
                // continue;
                // }
                // int index = modelId.indexOf("/");
                // String id = null;
                // if(index > 0){
                // id = modelId.substring(0, index);
                // }
                // id = id.trim();
                // if(id.matches("\\d+")){5
                // temp.put(Integer.valueOf(id), dto.getModelDescription());
                // }
            }
        }
        return modelIds;
    }

    @Override
    public boolean checkAuctionPrivileges(Integer auctionNumber) throws ServiceException, DaoException {
        Set<String> models = fetchModelsForLoggedUser();
        ArrayList<Integer> companies = new ArrayList<Integer>();
        for (String model: models) {
            int company = getCompanyFromDescription(model);
            companies.add(company);
        }
        boolean isAllow = dao.checkAuctionInCompanies(auctionNumber, companies);
        return isAllow;
    }

    @Override
    public boolean checkFormPrivileges(String formUUID, Integer currentUserId) throws ServiceException, DaoException {
        // security by model
        Set<String> models = fetchModelsForLoggedUser();
        ArrayList<Integer> companies = new ArrayList<Integer>();
        for (String model: models) {
            int company = getCompanyFromDescription(model);
            companies.add(company);
        }
        
        if (!dao.checkFormInCompanies(formUUID, companies)) {
            return false;
        } else {
            // security by userId linked with form
            List<Integer> usersIds = dao.fetchUsersIdsForForm(formUUID);
            if (usersIds.size() == 0) {
                return true;
            } else {
                if (usersIds.contains(currentUserId)) {
                    return true;
                }else{
                    return false;
                }
                
            }
        }
    }

    @Override
    public void fetchExcelMobileFlatForm(Workbook workbook, int auctionNumber) throws ServiceException {
        // workbook.setForceFormulaRecalculation(true);

        List<DashboardEntity> auctions;
        try {
            auctions = dao.browseAuctions(auctionNumber);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }

        // Map<String, Map<String, Map<String, String>>> result = new HashMap<String, Map<String, Map<String, String>>>();
        int[] curr = { 0, 1000, 2500, 5000, 10000, 25000, 50000, 100000, 250000 };
        ValueRange range = new ValueRange(curr);
        DtoManager mngr = new DtoManager(range);

        Map<String, Map<String, Map<String, String>>> data = mngr.createResult(auctions);

        Map<String, String> actual = data.get(GraphType.SALE_RESULT.toString()).get("actual");

        // set title
        Sheet sheet = workbook.getSheet(GraphType.SALE_RESULT.getDescription());
        sheet.getRow(34).getCell(1).setCellValue(GraphType.SALE_RESULT.getDescription());

        double sum = 0.0;
        int row = 36;
        for (String s: actual.keySet()) {
            sheet.getRow(row).getCell(3).setCellValue(Double.valueOf(actual.get(s)));
            row++;

            if ("hammer".equals(s) == false) {
                sum += Double.valueOf(actual.get(s));
            }
        }
        // set total
        sheet.getRow(row).getCell(3).setCellValue(sum);

        Map<String, String> unsold = data.get(GraphType.VALUE_OF_LOTS_SOLD.toString()).get("unsold");
        Map<String, String> sold = data.get(GraphType.VALUE_OF_LOTS_SOLD.toString()).get("sold");

        // set title
        sheet = workbook.getSheet(GraphType.VALUE_OF_LOTS_SOLD.getDescription());
        sheet.getRow(34).getCell(1).setCellValue(GraphType.VALUE_OF_LOTS_SOLD.getDescription());

        row = 36;
        double soldSum = 0;
        double unsoldSum = 0;
        for (Integer i: range.getLabels().keySet()) {
            // set currency labels
            sheet.getRow(row).getCell(1).setCellValue(range.getLabels().get(i));

            sheet.getRow(row).getCell(2).setCellValue(Double.valueOf(unsold.get(i.toString())));
            sheet.getRow(row).getCell(3).setCellValue(Double.valueOf(sold.get(i.toString())));
            row++;

            // count totals
            soldSum += Integer.valueOf(sold.get(i.toString()));
            unsoldSum += Integer.valueOf(unsold.get(i.toString()));
        }
        // set totals
        sheet.getRow(row).getCell(2).setCellValue(unsoldSum);
        sheet.getRow(row).getCell(3).setCellValue(soldSum);

        unsold = data.get(GraphType.NO_OF_LOTS_SOLD.toString()).get("unsold");
        sold = data.get(GraphType.NO_OF_LOTS_SOLD.toString()).get("sold");

        // set title
        sheet = workbook.getSheet(GraphType.NO_OF_LOTS_SOLD.getDescription());
        sheet.getRow(34).getCell(1).setCellValue(GraphType.NO_OF_LOTS_SOLD.getDescription());

        row = 36;
        soldSum = 0;
        unsoldSum = 0;
        for (Integer i: range.getLabels().keySet()) {
            // set currency labels
            sheet.getRow(row).getCell(1).setCellValue(range.getLabels().get(i));

            sheet.getRow(row).getCell(2).setCellValue(Double.valueOf(unsold.get(i.toString())));
            sheet.getRow(row).getCell(3).setCellValue(Double.valueOf(sold.get(i.toString())));
            row++;

            // count totals
            soldSum += Integer.valueOf(sold.get(i.toString()));
            unsoldSum += Integer.valueOf(unsold.get(i.toString()));
        }
        // set totals
        sheet.getRow(row).getCell(2).setCellValue(unsoldSum);
        sheet.getRow(row).getCell(3).setCellValue(soldSum);

        Map<String, String> income = data.get(GraphType.NO_OF_LOTS_V_INVOME.toString()).get("income");
        Map<String, String> noLots = data.get(GraphType.NO_OF_LOTS_V_INVOME.toString()).get("noLots");

        // set title
        sheet = workbook.getSheet(GraphType.NO_OF_LOTS_V_INVOME.getDescription());
        sheet.getRow(34).getCell(1).setCellValue(GraphType.NO_OF_LOTS_V_INVOME.getDescription());

        row = 36;
        for (Integer i: range.getLabels().keySet()) {
            // set currency labels
            sheet.getRow(row).getCell(1).setCellValue(range.getLabels().get(i));

            sheet.getRow(row).getCell(2).setCellValue(Double.valueOf(income.get(i.toString())));
            sheet.getRow(row).getCell(3).setCellValue(Double.valueOf(noLots.get(i.toString())));
            row++;
        }
        Map<String, String> vc = data.get(GraphType.VENDORS_COMMISSION.toString()).get("vc");

        // set title
        sheet = workbook.getSheet(GraphType.VENDORS_COMMISSION.getDescription());
        sheet.getRow(34).getCell(1).setCellValue(GraphType.VENDORS_COMMISSION.getDescription());

        row = 36;
        for (Integer i: range.getLabels().keySet()) {
            // set currency labels
            sheet.getRow(row).getCell(1).setCellValue(range.getLabels().get(i));

            sheet.getRow(row).getCell(2).setCellValue(Double.valueOf(vc.get(i.toString())));
            row++;
        }

        Map<String, Map<String, String>> bidTypes = data.get(GraphType.AUCTION_BID_TYPE.toString());

        // set title
        sheet = workbook.getSheet(GraphType.AUCTION_BID_TYPE.getDescription());
        sheet.getRow(34).getCell(1).setCellValue(GraphType.AUCTION_BID_TYPE.getDescription());

        int col = 2;
        for (String bidType: bidTypes.keySet()) {
            row = 36;
            sheet.getRow(35).getCell(col).setCellValue(BidType.getBidTypeByName(bidType).getDescription());
            for (String s: bidTypes.get(bidType).keySet()) {
                sheet.getRow(row).getCell(col).setCellValue(Double.valueOf(bidTypes.get(bidType).get(s)));
                row++;
            }
            col++;
        }
        row = 36;
        for (Integer i: range.getLabels().keySet()) {
            // set currency labels
            sheet.getRow(row).getCell(1).setCellValue(range.getLabels().get(i));
            row++;
        }

    }

    @Autowired
    LookupAuctionDao lookupAuctionDao;

    @Override
    public List<DashboardAuctionDTO> browseAuctionsForCompany(final Integer pageSize, final Integer pageNumber, String orderby, String[] filters, String modelDescription) throws ServiceException {
        try {
            List<LookupAuctionFilterDTO> filterObjects = createFilterList(filters);
            return lookupAuctionDao.browseAuctionsForCompany(pageSize, pageNumber, orderby, filterObjects, getCompanyFromDescription(modelDescription));
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<DashboardAuctionDTO> browseAuctionsForCompany(String orderby, String[] filters, String modelDescription) throws ServiceException {
        try {
            List<LookupAuctionFilterDTO> filterObjects = createFilterList(filters);
            List<DashboardAuctionDTO> auctions = lookupAuctionDao.browseAuctionsForCompany(orderby, filterObjects, getCompanyFromDescription(modelDescription));
            return auctions;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public List<DashboardAuctionDTO> browseAuctionsForCompany(Integer pageNumber, String orderby, String[] filters, String modelDescription) throws ServiceException {
        try {
            List<LookupAuctionFilterDTO> filterObjects = createFilterList(filters);
            List<DashboardAuctionDTO> auctions = lookupAuctionDao.browseAuctionsForCompany(pageNumber, orderby, filterObjects, getCompanyFromDescription(modelDescription));
            return auctions;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    private int getCompanyFromDescription(String modelDescription) {
        int index = modelDescription.indexOf("-");
        String model = null;
        if (index > 0) {
            model = modelDescription.substring(0, index);
            model = model.trim();
            if (model.matches("\\d+")) {
                return Integer.valueOf(model);
            }
        }
        return -1;
    }

    // @Override
    // public List<DashboardAuctionDTO> browseAuctionsForCompany(final Integer pageSize, final Integer pageNumber, String orderby, String[] filters, int company) throws ServiceException {
    // try {
    // List<LookupAuctionFilterDTO> filterObjects = createFilterList(filters);
    // return lookupAuctionDao.browseAuctionsForCompany(pageSize, pageNumber, orderby, filterObjects, company);
    // } catch (DaoException e) {
    // throw new ServiceException(e.getMessage(), e);
    // }
    //
    // }

    private List<LookupAuctionFilterDTO> createFilterList(String[] filters) {
        List<LookupAuctionFilterDTO> filterObjects = new ArrayList<LookupAuctionFilterDTO>();
        if (filters != null && filters.length != 0) {
            for (String filter: filters) {
                LookupAuctionFilterDTO filterObject = new LookupAuctionFilterDTO(filter);
                filterObjects.add(filterObject);
            }
        }
        return filterObjects;
    }

}
