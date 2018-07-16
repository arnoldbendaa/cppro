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
package com.softproideas.app.dashboard.auction.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.softproideas.app.dashboard.auction.entity.DashboardEntity;
import com.softproideas.app.dashboard.auction.mapper.DashboardMapper;
import com.softproideas.app.lookuptable.auction.model.LookupAuctionFilterDTO;
import com.softproideas.common.exceptions.DaoException;

@Repository("dashboardDao")
public class DashboardDaoImpl extends JdbcDaoSupport implements DashboardDao {

    @Autowired
    @Qualifier("cpDataSource")
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public List<DashboardEntity> browseAuctions(int auctionNumber) throws DaoException {
        String query = "SELECT \"hammerPrice\", \"premium\", \"commAmt\", \"insAmt\", \"bidType\", \"estimateLow\" FROM SALELOTITEM WHERE \"auctionNo\"=? AND \"aucsaleitem_rowid\" IS NOT NULL";

        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, auctionNumber);
            List<DashboardEntity> auctions = DashboardMapper.mapAuctions(rows);
            return auctions;
        } catch (DataAccessException e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public boolean checkAuctionInCompanies(Integer auctionNumber, ArrayList<Integer> companies) throws DaoException {
        // String query = "select * from \"AUCTION_SALE\" where \"ISALENO\" = ? and \"ICOMPANYNO\" IN (  )";
        String companiesForQuery = companies.toString();
        companiesForQuery = companiesForQuery.substring(1, companiesForQuery.length() - 1);
        if (companiesForQuery.length() == 0) {
            companiesForQuery.concat("-1");
        }
        String query = "select * from \"AUCTION_SALE\" where \"ISALENO\" = ? and \"ICOMPANYNO\" IN (" + companiesForQuery + ")";
        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, auctionNumber.intValue());
            if (rows.size() > 0) {
                return true;
            }
        } catch (DataAccessException e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }

        return false;
    }

    @Override
    public boolean checkFormInCompanies(String formUUID, ArrayList<Integer> companies) throws DaoException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < companies.size(); i++) {
            sb.append("'" + companies.get(i).toString() + "'");
            if (i != companies.size() - 1) {
                sb.append(",");
            }
        }

        String companiesForQuery = sb.toString();
        if (companiesForQuery.length() == 0) {
            companiesForQuery.concat("'-1'");
        }
        // @formatter:off
        String query= "select MM.COMPANY_VIS_ID from DASHBOARD db"
        + " left join XML_FORM xmlf ON (db.FORM_ID = xmlf.XML_FORM_ID)"
        + " left join FINANCE_CUBE fc ON (xmlf.FINANCE_CUBE_ID = fc.FINANCE_CUBE_ID)"
        + " left join MAPPED_MODEL mm ON(fc.MODEL_ID = mm.MODEL_ID)"
        + " where db.DASHBOARD_UUID = ?"
        + " and MM.COMPANY_VIS_ID IN (" + companiesForQuery + ")";
        // @formatter:on
        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, formUUID);
            if (rows.size() > 0) {
                return true;
            }
        } catch (DataAccessException e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }

        return false;
    }

    @Override
    public List<Integer> fetchUsersIdsForForm(String formUUID) throws DaoException {
        // @formatter:off
        String query= "select xful.USER_ID from DASHBOARD db "
                + "left join XML_FORM_USER_LINK xful "
                + "on(db.FORM_ID = xful.XML_FORM_ID) "
                + "where db.DASHBOARD_UUID = ? ";
        // @formatter:on
        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, formUUID);
            List<Integer> usersIds = DashboardMapper.mapUsersIds(rows);
            return usersIds;
        } catch (DataAccessException e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }

    }

    // @Override
    // public List<DashboardDTO> browseAuctionsForCompany(final Integer pageSize, final Integer pageNumber, String orderby, List<LookupAuctionFilterDTO> filters, int company) throws DaoException {
    // String query = "SELECT ISALENO, SSALENAME, DAENDDATE FROM AUCTION_SALE WHERE ICOMPANY=?";
    //
    // String filterCondition = getSQLFilterCondition(filters);
    //
    // List<String> columnValidNames = new ArrayList<String>(){{
    // add("sold");add("unsold");add("auctionNo");add("lotNo");add("sfx");add("sellerNo");add("contractNo");add("contractLineNo");add("status");add("buyer");add("paddleNo");
    // add("hammerPrice");add("premium");add("buyerNett");add("commAmt");add("insAmt");add("bIFee");add("wDFee");add("illustFee");add("chgAmt");add("passPrice");add("iCAmount");
    // add("lotDescription");add("bidType");add("iCRepUserID");add("valuationUserID");add("estimateLow");add("estimateHigh");add("reservePrice");
    // }};
    //
    // String[] orderBy = manageOrderBy(orderby, columnValidNames, "lotNo");
    // String query = "SELECT * FROM SALELOTITEM";
    // query = filterCondition.length() == 0 ? query : query + " WHERE " + filterCondition;
    // if (orderBy.length > 0) {
    // query = query + " ORDER BY \"" + orderBy[0] + "\" " + orderBy[1];
    // }
    // String pagingQuery = "SELECT * FROM ( SELECT rownum rnum, a.* from(" + query + ") a) where rnum BETWEEN ? AND ?";
    //
    // Object[] queryParameters = manageQueryParameters(pageNumber, pageSize, filters);
    //
    //
    // try {
    // List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, auctionNumber);
    // List<DashboardDTO> auctions = DashboardMapper.mapAuctions(rows);
    // return auctions;
    // } catch (DataAccessException e) {
    // String errorMsg = "Error while executing select query";
    // logger.error(errorMsg, e);
    // throw new DaoException(errorMsg, e);
    // }
    // }
}
