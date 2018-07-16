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
package com.softproideas.app.lookuptable.project.dao;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.softproideas.app.lookuptable.project.mapper.LookupProjectMapper;
import com.softproideas.app.lookuptable.project.model.LookupProjectDTO;
import com.softproideas.app.lookuptable.project.model.LookupProjectFilterDTO;
import com.softproideas.common.exceptions.DaoException;

import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * 
 * <p>TODO: Comment me</p>
 *
 * @author Jakub Piskorz
 * @email jakub.piskorz@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Repository("lookupProjectDao")
public class LookupProjectDaoImpl extends JdbcDaoSupport implements LookupProjectDao {

    @Autowired
    @Qualifier("cpDataSource")
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
    
    private Integer defaultPageSize = 200;
    
    private Integer defaultPageNumber = 1;
    
    @Override
    public List<LookupProjectDTO> browseProjects(String orderby, List<LookupProjectFilterDTO> filters) throws DaoException {
        List<LookupProjectDTO> projects = browseProjects(defaultPageSize, defaultPageNumber, orderby, filters);
        return projects;
    }

    @Override
    public List<LookupProjectDTO> browseProjects(Integer pageNumber, String orderby, List<LookupProjectFilterDTO> filters) throws DaoException {
        List<LookupProjectDTO> projects = browseProjects(defaultPageSize, pageNumber, orderby, filters);
        return projects;
    }
    
    @Override
    public List<LookupProjectDTO> browseProjects(final Integer pageSize, final Integer pageNumber, String orderby, List<LookupProjectFilterDTO> filters) throws DaoException {

        String filterCondition = getSQLFilterCondition(filters);
        
        String[] orderBy = manageOrderBy(orderby);
        String query =
                    " SELECT "+
                        "company,"+
                        "costcentre,"+
                        "project,"+
                        "expensecode,"+
                        "yearno,"+
                        "period,"+
                        "baseVal,"+
                        "qty,"+
                        "cumBaseVal,"+
                        "CUMQTY" +
                    " FROM oa_pctrans"+
                " UNION ALL"+
                    " SELECT "+
                        "company,"+
                        "costcentre,"+
                        "project,"+
                        "expensecode,"+
                        "yearno,"+
                        "period,"+
                        "baseVal,"+
                        "qty,"+
                        "cumBaseVal, "+
                        "cumQty" +
                    " FROM oa_pctrans_totals ";
        
        if(filterCondition.length() > 0){
            filterCondition = "where " + filterCondition;
        }
        
        String pagingQuery;
        if(orderBy.length == 0) {
            pagingQuery = "SELECT * FROM ( SELECT rownum rnum, a.* from(" + query + ") a "+filterCondition+ " ) where rnum BETWEEN ? AND ?"; // + top + " AND " + (skip + top);
        } else {
            pagingQuery = "SELECT * FROM ( SELECT rownum rnum, a.* from(" + query + " ORDER BY " + orderBy[0] + " "+ orderBy[1]+ " "+") a "+filterCondition+ " ) where rnum BETWEEN ? AND ?"; // + top + " AND " + (skip + top);
        }
      //  pagingQuery = filterCondition.length() == 0 ? pagingQuery : pagingQuery + " AND " + filterCondition;
        
        
        Object[] queryParameters = manageQueryParameters(pageNumber, pageSize, filters);
       // String 
        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(pagingQuery, queryParameters);
            List<LookupProjectDTO> projects = LookupProjectMapper.mapProjects(rows);
            return projects;
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    private Object[] manageQueryParameters(Integer pageNumber, Integer pageSize, List<LookupProjectFilterDTO> filters) {
        Object[] queryParameters = new Object[2 + filters.size()];
        //queryParameters[0] = orderBy;
        int i;
        for( i = 0; i < filters.size(); i++) {
            queryParameters[i] = filters.get(i).getValue().toUpperCase();
        }
        
        queryParameters[i] = ((pageNumber - 1) * pageSize) + 1;
        i++;
        queryParameters[i] = (pageNumber * pageSize);
       
        return queryParameters;
    }

    @Override
    public Integer getRowsCount() {
        String query = "SELECT * FROM GRID_ROW_COUNT gr WHERE gr.VIEW_NAME='PROJECT_LOOKUP'";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
        Integer rowCount = 0;
        if (rows.size() > 0) {
            rowCount = ((BigDecimal)rows.get(0).get("SIZE_VALUE")).intValue();
        }
        return rowCount;
    }
    
    private String[] manageOrderBy(String orderby) {
        if (orderby == null || orderby.isEmpty()) {
            return new String[0];
        }
        List<String> columnValidNames = new ArrayList<String>(){{
            add("company");add("costcentre");add("project");add("expensecode");add("yearno");add("period");add("baseVal");add("qty");add("cumBaseVal");
        }};
        String[] result = new String[2]; 
        if (orderby != null || orderby.length() > 0) {
            if (orderby.endsWith(" desc")) {
                result[0] = orderby.substring(0, orderby.length() - 5);
                result[1] = "DESC";
            } else {
                String[] parts = orderby.split(" ");
                result[0] = parts[0];
                result[1] = "ASC";
            }
        }
        result[0] = columnValidNames.contains(result[0]) ? result[0] : "yearno";
        return result;
    }
    
    public static String getSQLFilterCondition(List<LookupProjectFilterDTO> filters) {
        String condition = "";
        for(int i=0; i < filters.size(); i++) {
            LookupProjectFilterDTO filter = filters.get(i);
            condition += ("UPPER(" + filter.getField() + ") " + filter.getOperator() + " ?");
            if(i != filters.size()-1) {
                condition += " AND ";
            }
        }
        return condition;
    }
    
}