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
package com.softproideas.app.lookuptable.currency.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.softproideas.app.lookuptable.currency.mapper.LookupCurrencyMapper;
import com.softproideas.app.lookuptable.currency.model.LookupCurrencyChangeDTO;
import com.softproideas.app.lookuptable.currency.model.LookupCurrencyDTO;
import com.softproideas.common.exceptions.DaoException;

/**
 * 
 * <p>TODO: Comment me</p>
 *
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Repository("lookupCurrencyDao")
public class LookupCurrencyDaoImpl extends JdbcDaoSupport implements LookupCurrencyDao {
    
    @Autowired
    @Qualifier("cpDataSource")
    DataSource dataSource;
    
    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static Logger logger = LoggerFactory.getLogger(LookupCurrencyDaoImpl.class);

    @Override
    public List<LookupCurrencyDTO> browseCurrencies() throws DaoException {
        String query = "select * from CURRENCY_LOOKUP order by COMPANY asc, YEAR asc, PERIOD asc";
        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
            return LookupCurrencyMapper.mapCurrencies(rows);
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }
    
    @Override
    public List<LookupCurrencyDTO> browseCurrencies(int company, int year) throws DaoException {
        String query = "select * from CURRENCY_LOOKUP where COMPANY = ? and YEAR = ? order by PERIOD asc";
        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, new Object[] { company, year });
            return LookupCurrencyMapper.mapCurrencies(rows);
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }
    
    @Override
    public LookupCurrencyDTO fetchCurrency(UUID currencyUUID) throws DaoException {
        String query = "select * from CURRENCY_LOOKUP where CURRENCY_UUID = ?";
        try {
            Map<String, Object> row = getJdbcTemplate().queryForMap(query, currencyUUID.toString());
            return LookupCurrencyMapper.mapCurrency(row);
        } catch (EmptyResultDataAccessException e) {
            return null;    
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }
    
    @Override
    public LookupCurrencyDTO fetchCurrency(LookupCurrencyDTO lookupCurrency) throws DaoException {
        String query = "select * from CURRENCY_LOOKUP where YEAR = ? and PERIOD = ? and COMPANY = ? and CURRENCY = ? ";
        int year = lookupCurrency.getYear();
        int period = lookupCurrency.getPeriod();
        int company = lookupCurrency.getCompany();
        String currency = lookupCurrency.getCurrency();
        
        try {            
            Map<String, Object> row = getJdbcTemplate().queryForMap(query, new Object[] { year, period, company, currency });
            return LookupCurrencyMapper.mapCurrency(row);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }
    
    @Override
    public boolean insertCurrency(LookupCurrencyDTO lookupCurrency) throws DaoException {
        String query = "insert into CURRENCY_LOOKUP (YEAR, PERIOD, COMPANY, CURRENCY, FIELD_VALUE, CURRENCY_UUID) values (?, ?, ?, ?, ?, ?)";
        PreparedStatementSetter pss = prepareStatement(lookupCurrency);
        try {
            int result = getJdbcTemplate().update(query, pss);
            return result >= 1;
        } catch (DuplicateKeyException e) {
            String errorMsg = "Error while executing insert query - DUPLICATE ENTRY";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        } catch (Exception e) {
            String errorMsg = "Error while executing insert query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }
    
    @Override
    public boolean insertCurrencies(List<LookupCurrencyDTO> currencies) throws DaoException {
        String query = "insert into CURRENCY_LOOKUP (YEAR, PERIOD, COMPANY, CURRENCY, FIELD_VALUE, CURRENCY_UUID) values (?, ?, ?, ?, ?, ?)";
        BatchPreparedStatementSetter pss = prepareStatement(currencies);
        int[] result = getJdbcTemplate().batchUpdate(query, pss);
        return result.length > 0;
    }
    
    @Override
    public boolean updateCurrency(LookupCurrencyDTO lookupCurrencyDTO) throws DaoException {
        String query = "update CURRENCY_LOOKUP set YEAR = ?, PERIOD = ?, COMPANY = ?, CURRENCY = ?, FIELD_VALUE = ? where CURRENCY_UUID = ?";
        PreparedStatementSetter pss = prepareStatement(lookupCurrencyDTO);
        int result = getJdbcTemplate().update(query, pss);
        return result >= 1;
    }
    
    @Override
    public boolean updateCurrencyValue(LookupCurrencyDTO lookupCurrencyDTO) throws DaoException {
        String query = "update CURRENCY_LOOKUP set FIELD_VALUE = ? where CURRENCY_UUID = ?";
       
        String uuid = lookupCurrencyDTO.getCurrencyUUID().toString();
        String fieldValue = lookupCurrencyDTO.getFieldValue();
        
        int result = getJdbcTemplate().update(query, new Object[] { fieldValue, uuid });
        return result >= 1;
    }
    

    @Override
    public boolean updateCurrenciesValue(List<LookupCurrencyDTO> currencies) throws DaoException {
        String query = "update CURRENCY_LOOKUP set FIELD_VALUE = ? where CURRENCY_UUID = ?";
        
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (LookupCurrencyDTO lookupCurrency: currencies) {
            String uuid = lookupCurrency.getCurrencyUUID().toString();
            String fieldValue = lookupCurrency.getFieldValue();
            batchArgs.add(new Object[] { fieldValue, uuid });
        }
        
        int[] result = getJdbcTemplate().batchUpdate(query, (BatchPreparedStatementSetter) batchArgs);
        return result.length >= 1;
    }
    
    @Override
    public boolean deleteCurrency(UUID currencyUUID) throws DaoException {
        String query = "delete from CURRENCY_LOOKUP where CURRENCY_UUID = ?";
        int result = getJdbcTemplate().update(query, currencyUUID.toString());
        return result >= 1;
    }
    
    @Override
    public boolean deleteCurrencies(List<LookupCurrencyDTO> currencies) throws DaoException {
        String query = "delete from CURRENCY_LOOKUP where CURRENCY_UUID = ?";
        
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (LookupCurrencyDTO lookupCurrency: currencies) {
            String uuid = lookupCurrency.getCurrencyUUID().toString();
            batchArgs.add(new Object[] { uuid });
        }
        
        int[] result = getJdbcTemplate().batchUpdate(query,(BatchPreparedStatementSetter)  batchArgs);
        return result.length >= 1;
    }
    
    private PreparedStatementSetter prepareStatement(final LookupCurrencyDTO lookupCurrency) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setInt(1, lookupCurrency.getYear());
                ps.setInt(2, lookupCurrency.getPeriod());
                ps.setInt(3, lookupCurrency.getCompany());
                ps.setString(4, lookupCurrency.getCurrency());
                ps.setString(5, lookupCurrency.getFieldValue());
                ps.setString(6, lookupCurrency.getCurrencyUUID().toString());
            }
        };
        return pss;
    }

    private BatchPreparedStatementSetter prepareStatement(final List<LookupCurrencyDTO> currencies) {
        BatchPreparedStatementSetter pss = new BatchPreparedStatementSetter() {
            
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                LookupCurrencyDTO lookupCurrency = currencies.get(i);
                ps.setInt(1, lookupCurrency.getYear());
                ps.setInt(2, lookupCurrency.getPeriod());
                ps.setInt(3, lookupCurrency.getCompany());
                ps.setString(4, lookupCurrency.getCurrency());
                ps.setString(5, lookupCurrency.getFieldValue());
                ps.setString(6, lookupCurrency.getCurrencyUUID().toString());
            }

            @Override
            public int getBatchSize() {
                return currencies.size();
            }
        };
        return pss;
    }

    /* (non-Javadoc)
     * @see com.softproideas.app.lookuptable.currency.dao.LookupCurrencyDao#updateChanges(java.util.List)
     */
    @Override
    public boolean updateChanges(List<LookupCurrencyChangeDTO> changes) throws DaoException {
        List<LookupCurrencyChangeDTO> toRemoves = new ArrayList<LookupCurrencyChangeDTO>();
        List<LookupCurrencyChangeDTO> toUpdates = new ArrayList<LookupCurrencyChangeDTO>();
        for (LookupCurrencyChangeDTO change: changes) {
            if (change.getNewValue().equalsIgnoreCase("null")) {
                toRemoves.add(change);
            } else {
                toUpdates.add(change);
            }
        }
        String updateQuery = "update CURRENCY_LOOKUP set CURRENCY = ? where CURRENCY = ?";
        String deleteQuery = "delete from CURRENCY_LOOKUP where CURRENCY = ?";
        List<Object[]> batchArgs0 = new ArrayList<Object[]>();
        List<Object[]> batchArgs1 = new ArrayList<Object[]>();

        for (LookupCurrencyChangeDTO toUpdate: toUpdates) {
            String newValue = toUpdate.getNewValue().toString().toUpperCase();
            String oldValue = toUpdate.getOldValue().toString().toUpperCase();
            batchArgs0.add(new Object[] { newValue, oldValue });
        }

        for (LookupCurrencyChangeDTO toRemove: toRemoves) {
            String oldValue = toRemove.getOldValue().toString().toUpperCase();
            batchArgs1.add(new Object[] { oldValue });
        }
        int[] result0 = getJdbcTemplate().batchUpdate(updateQuery, (BatchPreparedStatementSetter) batchArgs0);
        int[] result1 = getJdbcTemplate().batchUpdate(deleteQuery,(BatchPreparedStatementSetter)  batchArgs1);
        return result0.length >= 1 && result1.length >= 1;
    }

}
