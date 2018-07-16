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
package com.softproideas.app.core.dictionary.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softproideas.app.core.dictionary.mapper.DictionaryMapper;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.models.DictionaryDTO;
import com.softproideas.common.models.DictionaryPropertiesDTO;

@Repository("dictionaryDao")
public class DictionaryDaoImpl extends JdbcDaoSupport implements DictionaryDao {

    @Autowired
    @Qualifier("cpDataSource")
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static Logger logger = LoggerFactory.getLogger(DictionaryDaoImpl.class);

    @Override
    public List<DictionaryDTO> browseDictionaries() throws DaoException {
        String query = "select * from DICTIONARY order by TYPE asc, ROW_INDEX asc";
        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
            return DictionaryMapper.mapDictionaries(rows);
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public List<DictionaryDTO> browseDictionaries(String type) throws DaoException {
        String query = "select * from DICTIONARY where TYPE = ? order by ROW_INDEX asc";
        try {

            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, new Object[] { type });

            return DictionaryMapper.mapDictionaries(rows);
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public DictionaryDTO fetchDictionary(String key) throws DaoException {
        String query = "select * from DICTIONARY where KEY = ? order by ROW_INDEX asc";
        try {
            Map<String, Object> row = getJdbcTemplate().queryForMap(query, key);
            return DictionaryMapper.mapDictionary(row);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public boolean insertDictionary(DictionaryDTO dictionary) throws DaoException {
        String query = "insert into DICTIONARY (TYPE, VALUE, DESCRIPTION, KEY, ROW_INDEX) values (?, ?, ?, ?, ?)";
        try {
            PreparedStatementSetter pss = prepareStatement(dictionary);
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
    public boolean insertDictionaries(List<DictionaryDTO> dictionaries) throws DaoException {
        String query = "insert into DICTIONARY (TYPE, VALUE, DESCRIPTION, KEY, ROW_INDEX, PROPERTIES_JSON) values (?, ?, ?, ?, ?, ?)";
        BatchPreparedStatementSetter pss = prepareStatement(dictionaries);
        int[] result = getJdbcTemplate().batchUpdate(query, pss);
        return result.length > 0;
    }

    @Override
    public boolean updateDictionary(DictionaryDTO dictionary) throws DaoException {
        String query = "update DICTIONARY set DESCRIPTION = ? where KEY = ?";

        String key = dictionary.getKey();
        String description = dictionary.getDescription();

        int result = getJdbcTemplate().update(query, new Object[] { description, key });
        return result >= 1;
    }

    @Override
    public boolean updateDictionaries(List<DictionaryDTO> dictionaries) throws DaoException {
        String query = "update DICTIONARY set DESCRIPTION = ?, ROW_INDEX = ?, PROPERTIES_JSON = ? where KEY = ?";

        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (DictionaryDTO lookupCurrency: dictionaries) {
            String key = lookupCurrency.getKey();
            String description = lookupCurrency.getDescription();
            String properties = toJSON(lookupCurrency.getDictionaryProperties());
            int rowIndex = lookupCurrency.getRowIndex();
            batchArgs.add(new Object[] { description, rowIndex, properties, key });
        }

//        int[] result = getJdbcTemplate().batchUpdate(query, (BatchPreparedStatementSetter) batchArgs);
        int[] result = getJdbcTemplate().batchUpdate(query, batchArgs);
        return result.length >= 1;
    }

    @Override
    public boolean deleteDictionary(String key) throws DaoException {
        String query = "delete from DICTIONARY where KEY = ?";
        int result = getJdbcTemplate().update(query, key);
        return result >= 1;
    }

    @Override
    public boolean deleteDictionaries(List<DictionaryDTO> dictionaries) throws DaoException {
        String query = "delete from DICTIONARY where KEY = ?";

        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (DictionaryDTO dictionary: dictionaries) {
            String key = dictionary.getKey();
            batchArgs.add(new Object[] { key });
        }

        //int[] result = getJdbcTemplate().batchUpdate(query, (BatchPreparedStatementSetter)batchArgs);
        //arnold
        int[] result = getJdbcTemplate().batchUpdate(query, batchArgs);
        return result.length >= 1;
    }

    private PreparedStatementSetter prepareStatement(final DictionaryDTO dictionary) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, dictionary.getType().toLowerCase());
                ps.setString(2, dictionary.getValue());
                ps.setString(3, dictionary.getDescription());
                ps.setString(4, dictionary.getKey().toLowerCase());
                ps.setInt(5, dictionary.getRowIndex());
               // ps.setString(6, dictionary.getProperties());
                ps.setString(6, toJSON(dictionary.getDictionaryProperties()));

                // ps.setClob(6, (Clob) dictionary.getProperties());
            }
        };
        return pss;
    }

    private BatchPreparedStatementSetter prepareStatement(final List<DictionaryDTO> dictionaries) {
        BatchPreparedStatementSetter pss = new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                DictionaryDTO dictionary = dictionaries.get(i);
                ps.setString(1, dictionary.getType().toLowerCase());
                ps.setString(2, dictionary.getValue());
                ps.setString(3, dictionary.getDescription());
                ps.setString(4, dictionary.getKey().toLowerCase());
                ps.setInt(5, dictionary.getRowIndex());
                ps.setString(6, toJSON(dictionary.getDictionaryProperties()));
            }

            @Override
            public int getBatchSize() {
                return dictionaries.size();
            }
        };
        return pss;
    }

    private static String toJSON(DictionaryPropertiesDTO p) {
        // DictionaryPropertiesDTO p = new DictionaryPropertiesDTO();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = new String();
        try {
            jsonString = mapper.writeValueAsString(p);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

}
