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
package com.softproideas.app.admin.loggedhistory.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.softproideas.app.admin.loggedhistory.mapper.LoggedHistoryMapper;
import com.softproideas.app.admin.loggedhistory.model.LoggedHistoryUserDTO;
import com.softproideas.app.admin.loggedhistory.model.LoggedHistoryDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.commons.model.ResponseMessage;

@Repository("loggedHistoryDao")
public class LoggedHistoryDaoImpl extends JdbcDaoSupport implements LoggedHistoryDao {

    @Autowired
    @Qualifier("cpDataSource")
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static Logger logger = LoggerFactory.getLogger(LoggedHistoryDaoImpl.class);

    @Override
    public List<LoggedHistoryUserDTO> browseUsers() throws DaoException {
        String query = "select DISTINCT * from USR_LOGGED_HISTORY order by NAME ASC";
        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
            return LoggedHistoryMapper.mapLoggedHistories(rows);
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    
    @Override
    public List<LoggedHistoryUserDTO> getMoreRowsLoggedHistoryForUsers(String id, String date, int offset) throws DaoException {
        String query = "";
        List<Map<String, Object>> rows;
        try {
            
            if (id.equalsIgnoreCase("All")) {
                query = "select lh.NAME, lh.LOGGED_TIME, u.FULL_NAME from USR_LOGGED_HISTORY lh LEFT JOIN USR u ON (lh.NAME=u.NAME) order by lh.LOGGED_TIME DESC";
                rows = getJdbcTemplate().queryForList(query);
            } else if (id.equalsIgnoreCase("Last")) { // last 500
                query = "SELECT * FROM  (SELECT ROW_NUMBER() OVER(order by lh.LOGGED_TIME DESC) AS rn,    lh.NAME,    lh.LOGGED_TIME,    u.FULL_NAME  FROM USR_LOGGED_HISTORY lh  LEFT JOIN USR u  ON (lh.NAME =u.NAME)  ) WHERE rn <= 500";
                rows = getJdbcTemplate().queryForList(query);
            } else {
                query = "SELECT *  FROM  (SELECT ROW_NUMBER() OVER(order by lh.LOGGED_TIME DESC) AS rn,  lh.NAME,  lh.LOGGED_TIME, u.FULL_NAME FROM USR_LOGGED_HISTORY lh LEFT JOIN USR u  ON (lh.NAME   =u.NAME)  WHERE lh.LOGGED_TIME < to_timestamp(?, 'yyyy-mm-dd hh24:mi:ss')  AND lh.NAME = ?)WHERE rn <= "+offset+""; 
                rows = getJdbcTemplate().queryForList(query, new Object[] {date, id});
            }
            List<LoggedHistoryUserDTO> a = LoggedHistoryMapper.mapLoggedHistories(rows);
            return a;
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }
    
    
    
    
    

    @Override
    public ResponseMessage deleteHistory(String periodFrom) {
        ResponseMessage message = new ResponseMessage();
        String date = periodFrom + " 00:00:00";
        try {
            String query = "delete from USR_LOGGED_HISTORY where LOGGED_TIME <= to_timestamp(?, 'dd-mm-yyyy hh24:mi:ss')";
            getJdbcTemplate().update(query, date);
            message.setSuccess(true);
        } catch (Exception e) {
            String errorMsg = "Error while executing delete query";
            message.setSuccess(false);
            logger.error(errorMsg, e);
        }
        return message;
    }

    @Override
    public List<LoggedHistoryUserDTO> displayNotLoggedUsersForSearchOption(String periodfrom, String periodto) {
        List<Map<String, Object>> rows = null;
        String query = "";
        String dateFrom = periodfrom + " 00:00:00";
        String dateTo = periodto + " 00:00:00";
        
        try {
            query = "select u.NAME, u.FULL_NAME  from USR u where u.NAME not in (SELECT lh.NAME    FROM USR_LOGGED_HISTORY lh  where lh.logged_time <  to_timestamp(?, 'dd-mm-yyyy hh24:mi:ss') and lh.logged_time >  to_timestamp(?, 'dd-mm-yyyy hh24:mi:ss'))order by upper(u.name) asc";
            rows = getJdbcTemplate().queryForList(query, new Object[] {dateTo, dateFrom});
           
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
        }
        
        return LoggedHistoryMapper.mapNotLoggedUsers(rows);
    }
}