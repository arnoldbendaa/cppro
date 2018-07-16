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
package com.softproideas.app.lookuptable.auction.dao;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.softproideas.app.lookuptable.auction.mapper.LookupAuctionMapper;
import com.softproideas.app.lookuptable.auction.model.DashboardAuctionDTO;
import com.softproideas.app.lookuptable.auction.model.FlatFormDTO;
import com.softproideas.app.lookuptable.auction.model.LookupAuctionDTO;
import com.softproideas.app.lookuptable.auction.model.LookupAuctionFilterDTO;
import com.softproideas.common.exceptions.DaoException;

import cppro.utils.DBUtils;

/**
 * 
 * <p>TODO: Comment me</p>
 *
 * @author Jakub Piskorz
 * @email jakub.piskorz@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Repository("lookupAuctionDao")
public class LookupAuctionDaoImpl extends JdbcDaoSupport implements LookupAuctionDao {

    @Autowired
    @Qualifier("cpDataSource")
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public static final Integer defaultPageSize = 200;

    public static final Integer defaultPageNumber = 1;

    @Override
    public List<LookupAuctionDTO> browseAuctions(int auctionNumber) throws DaoException {
        String query = "SELECT * FROM SALELOTITEM WHERE auctionNo=?";

        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, auctionNumber);
            List<LookupAuctionDTO> auctions = LookupAuctionMapper.mapAuctions(rows);
            return auctions;
        } catch (DataAccessException e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }
    
    @Override
    public List<LookupAuctionDTO> browseAuctions(String orderby, List<LookupAuctionFilterDTO> filters) throws DaoException {
        List<LookupAuctionDTO> auctions = browseAuctions(defaultPageSize, defaultPageNumber, orderby, filters);
        return auctions;
    }
    
    @Override
    public List<LookupAuctionDTO> browseAuctions(Integer pageNumber, String orderby, List<LookupAuctionFilterDTO> filters) throws DaoException {
        List<LookupAuctionDTO> auctions = browseAuctions(defaultPageSize, pageNumber, orderby, filters);
        return auctions;
    }

    @Override
    public List<LookupAuctionDTO> browseAuctions(final Integer pageSize, final Integer pageNumber, String orderby, List<LookupAuctionFilterDTO> filters) throws DaoException {

        String filterCondition = getSQLFilterCondition(filters);

        List<String> columnValidNames = new ArrayList<String>() {
            {
                add("sold");
                add("unsold");
                add("auctionNo");
                add("lotNo");
                add("sfx");
                add("sellerNo");
                add("contractNo");
                add("contractLineNo");
                add("status");
                add("buyer");
                add("paddleNo");
                add("hammerPrice");
                add("premium");
                add("buyerNett");
                add("commAmt");
                add("insAmt");
                add("bIFee");
                add("wDFee");
                add("illustFee");
                add("chgAmt");
                add("passPrice");
                add("iCAmount");
                add("lotDescription");
                add("bidType");
                add("iCRepUserID");
                add("valuationUserID");
                add("estimateLow");
                add("estimateHigh");
                add("reservePrice");
            }
        };

        String[] orderBy = manageOrderBy(orderby, columnValidNames, "lotNo");
        String query = "SELECT * FROM SALELOTITEM";
        query = filterCondition.length() == 0 ? query : query + " WHERE " + filterCondition;
        if (orderBy.length > 0) {
            //query = query + " ORDER BY \"" + orderBy[0] + "\" " + orderBy[1];
            query = query + " ORDER BY " + orderBy[0] + " " + orderBy[1];
        }
        String pagingQuery = "SELECT * FROM ( SELECT rownum rnum, a.* from(" + query + ") a) where rnum BETWEEN ? AND ?";

        Object[] queryParameters = manageQueryParameters(pageNumber, pageSize, filters);

        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(pagingQuery, queryParameters);
            List<LookupAuctionDTO> auctions = LookupAuctionMapper.mapAuctions(rows);
            return auctions;
        } catch (DataAccessException e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public List<DashboardAuctionDTO> browseAuctionsForCompany(String orderby, List<LookupAuctionFilterDTO> filters, int company) throws DaoException {
        List<DashboardAuctionDTO> auctions = browseAuctionsForCompany(defaultPageSize, defaultPageNumber, orderby, filters, company);
        return auctions;
    }

    @Override
    public List<DashboardAuctionDTO> browseAuctionsForCompany(Integer pageNumber, String orderby, List<LookupAuctionFilterDTO> filters, int company) throws DaoException {
        List<DashboardAuctionDTO> auctions = browseAuctionsForCompany(defaultPageSize, pageNumber, orderby, filters, company);
        return auctions;
    }

    @Override
    public List<DashboardAuctionDTO> browseAuctionsForCompany(final Integer pageSize, final Integer pageNumber, String orderby, List<LookupAuctionFilterDTO> filters, int company) throws DaoException {

        String filterCondition = "";
        for (int i = 0; i < filters.size(); i++) {
            LookupAuctionFilterDTO filter = filters.get(i);

            if ("DAENDDATE".equalsIgnoreCase(filter.getField())) {
                Date date = new Date(Long.valueOf(filter.getValue()));
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                filter.setValue(df.format(date) + " 00:00:00");
                filterCondition += "\"" + (filter.getField() + "\" " + filter.getOperator() + "to_timestamp(? , 'dd-mm-yyyy hh24:mi:ss')");
            } else {
                filterCondition += "\"" + (filter.getField() + "\" " + filter.getOperator() + " ?");
            }
            if (i != filters.size() - 1) {
                filterCondition += " AND ";
            }
        }

        List<String> columnValidNames = new ArrayList<String>() {
            {
                add("ISALENO");
                add("SSALENAME");
                add("DAENDDATE");
                add("SSALESTATUS");
            }
        };

        String[] orderBy = manageOrderBy(orderby.toUpperCase(), columnValidNames, "DAENDDATE");
        String query = "SELECT \"ISALENO\", \"SSALENAME\", \"DAENDDATE\", \"SSALESTATUS\" FROM \"AUCTION_SALE\" WHERE \"ICOMPANYNO\"=?";
        query = filterCondition.length() == 0 ? query : query + " AND " + filterCondition.toUpperCase();
        if (orderBy.length > 0) {
            query = query + " ORDER BY \"" + orderBy[0] + "\" " + orderBy[1];
        }
        String pagingQuery = "SELECT * FROM ( SELECT rownum rnum, a.* from(" + query + ") a) where rnum BETWEEN ? AND ?";

        Object[] queryParameters = manageQueryParameters(pageNumber, pageSize, filters);
        Object[] temp = new Object[queryParameters.length + 1];
        temp[0] = company;
        for (int i = 0; i < queryParameters.length; i++) {
            temp[i + 1] = queryParameters[i];
        }

        try {
            List<Map<String, Object>> rows = DBUtils.getJDBCTempate().queryForList(pagingQuery, temp);
            List<DashboardAuctionDTO> auctions = LookupAuctionMapper.mapAuctionsForCompany(rows);
            return auctions;
        } catch (DataAccessException e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    
//    
//    @Override
//    public List<LookupAuctionDTO> fetchFlatForms2(String orderby, List<LookupAuctionFilterDTO> filters) throws DaoException {
//        List<LookupAuctionDTO> auctions = browseAuctions(defaultPageSize, defaultPageNumber, orderby, filters);
//        return auctions;
//    }
//    
//    @Override
//    public List<LookupAuctionDTO> fetchFlatForms2(Integer pageNumber, String orderby, List<LookupAuctionFilterDTO> filters) throws DaoException {
//        List<LookupAuctionDTO> auctions = browseAuctions(defaultPageSize, pageNumber, orderby, filters);
//        return auctions;
//    }

    @Override
    public List<FlatFormDTO> fetchFlatFormsForFinanceCubeId(final Integer pageSize, final Integer pageNumber, String orderby, List<LookupAuctionFilterDTO> filters, int financeCubeId) throws DaoException {
// select 0 ,XML_FORM.XML_FORM_ID ,XML_FORM.VIS_ID ,FINANCE_CUBE.FINANCE_CUBE_ID ,FINANCE_CUBE.VIS_ID  ,XML_FORM.TYPE   ,XML_FORM.DESCRIPTION ,XML_FORM.FINANCE_CUBE_ID      ,FINANCE_CUBE.VIS_ID      ,XML_FORM.UPDATED_TIME from XML_FORM    ,FINANCE_CUBE where FINANCE_CUBE.model_id in (select distinct model_id from budget_user where user_id = 2) and  XML_FORM.TYPE IN (6,7) and FINANCE_CUBE.FINANCE_CUBE_ID = XML_FORM.FINANCE_CUBE_ID order by FINANCE_CUBE.VIS_ID, XML_FORM.VIS_ID
        //XML_FORM.XML_FORM_ID, XML_FORM.VIS_ID, XML_FORM.DESCRIPTION
        //String filterCondition = getSQLFilterCondition(filters);
        
        String filterCondition = "";
        for (int i = 0; i < filters.size(); i++) {
            LookupAuctionFilterDTO filter = filters.get(i);

//            private int type;
//            private String updateTime;
//            private FinanceCubeCoreDTO financeCube;
//            TODO parse dto fields to table columns
            if ("UPDATED_TIME".equalsIgnoreCase(filter.getField())) {
                Date date = new Date(Long.valueOf(filter.getValue()));
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                filter.setValue(df.format(date));
                filterCondition += "trunc(XML_FORM." + (filter.getField().toUpperCase() + ") " + filter.getOperator() + " to_timestamp(? , 'dd-mm-yyyy')");
            } else {
                filterCondition += "UPPER(XML_FORM." + (filter.getField().toUpperCase() + ")" + " " + filter.getOperator() + " ?");
            }
            if (i != filters.size() - 1) {
                filterCondition += " AND ";
            }
        }

        List<String> columnValidNames = new ArrayList<String>() {
            {
                add("UPDATED_TIME");
                add("VIS_ID");
                add("XML_FORM_ID");
                add("DESCRIPTION");
            }
        };

        String[] orderBy = manageOrderBy(orderby.toUpperCase(), columnValidNames, "UPDATED_TIME");
        String query = "select XML_FORM.XML_FORM_ID, XML_FORM.VIS_ID, XML_FORM.DESCRIPTION, XML_FORM.UPDATED_TIME from XML_FORM "
                + "where XML_FORM.FINANCE_CUBE_ID = ? and XML_FORM.TYPE IN (6,7)";

        query = filterCondition.length() == 0 ? query : query + " AND " + filterCondition;
        if (orderBy.length > 0) {
            query = query + " ORDER BY XML_FORM." + orderBy[0].toUpperCase() + " " + orderBy[1];
        }
        String pagingQuery = "SELECT * FROM ( SELECT rownum rnum, a.* from(" + query + ") a) where rnum BETWEEN ? AND ?";

        Object[] queryParameters = manageQueryParameters(pageNumber, pageSize, filters);

        Object[] temp = new Object[queryParameters.length + 1];
        temp[0] = financeCubeId;
        for (int i = 0; i < queryParameters.length; i++) {
            temp[i + 1] = queryParameters[i];
        }
        
        try {
            List<Map<String, Object>> rows = DBUtils.getJDBCTempate().queryForList(pagingQuery, temp);
            List<FlatFormDTO> auctions = LookupAuctionMapper.mapFlatFormsForFinanceCubeId(rows);
            return auctions;
        } catch (DataAccessException e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }
    
    public Object[] manageQueryParameters(Integer pageNumber, Integer pageSize, List<LookupAuctionFilterDTO> filters) {
        Object[] queryParameters = new Object[2 + filters.size()];
        for (int i = 0; i < filters.size(); i++) {
            if ("XML_FORM_ID".equalsIgnoreCase(filters.get(i).getField())) {
                queryParameters[i] = Integer.valueOf(filters.get(i).getValue());
            } else {
                queryParameters[i] = filters.get(i).getValue().toUpperCase();
            }
        }
        queryParameters[filters.size()] = ((pageNumber - 1) * pageSize) + 1;
        queryParameters[filters.size() + 1] = (pageNumber * pageSize);
        return queryParameters;
    }

    @Override
    public Integer getRowsCount() throws DaoException {
        String query = "SELECT * FROM GRID_ROW_COUNT gr WHERE gr.VIEW_NAME='AUCTION_LOOKUP'";
        List<Map<String, Object>> rows;
        try {
            rows = getJdbcTemplate().queryForList(query);
        } catch (DataAccessException e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
        Integer rowCount = 0;
        if (rows.size() > 0) {
            rowCount = ((BigDecimal) rows.get(0).get("SIZE_VALUE")).intValue();
        }
        return rowCount;
    }

    public String[] manageOrderBy(String orderby, List<String> columnValidNames, String defaultColumn) {

        String[] result = new String[2];
        if (orderby != null && orderby.length() > 0) {
            if (orderby.endsWith(" DESC")) {
                result[0] = orderby.substring(0, orderby.length() - " DESC".length());
                result[1] = "DESC";
            } else {
                result[0] = orderby.substring(0, orderby.length() - " ASC".length());
                result[1] = "ASC";
            }
            if (columnValidNames.contains(result[0]) == false) {
                if (defaultColumn != null || columnValidNames.contains(defaultColumn)) {
                    result[0] = defaultColumn;
                    result[1] = "DESC";
                } else {
                    return new String[0];
                }
            }
        } else {
            if (defaultColumn != null && columnValidNames.contains(defaultColumn)) {
                result[0] = defaultColumn;
                result[1] = "DESC";
            } else {
                return new String[0];
            }
        }
        return result;
    }

    public static String getSQLFilterCondition(List<LookupAuctionFilterDTO> filters) {
        String condition = "";
        for (int i = 0; i < filters.size(); i++) {
            LookupAuctionFilterDTO filter = filters.get(i);

            condition += "\"" + (filter.getField() + "\" " + filter.getOperator() + " ?");
            if (i != filters.size() - 1) {
                condition += " AND ";
            }
        }
        return condition;
    }

}
