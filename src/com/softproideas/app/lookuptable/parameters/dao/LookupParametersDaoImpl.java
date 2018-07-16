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
package com.softproideas.app.lookuptable.parameters.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.softproideas.app.lookuptable.parameters.mapper.LookupParametersMapper;
import com.softproideas.app.lookuptable.parameters.model.LookupParameterChangeDTO;
import com.softproideas.app.lookuptable.parameters.model.LookupParameterDTO;
import com.softproideas.common.exceptions.DaoException;

@Repository("lookupParametersDao")
public class LookupParametersDaoImpl extends JdbcDaoSupport implements LookupParametersDao {

    @Autowired
    @Qualifier("cpDataSource")
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public List<LookupParameterDTO> browseParameters() throws DaoException {
        String query = "select P.*, D.STATUS, D.ROW_INDEX from PARAMETERS_LOOKUP P, PARAMETERS_LOOKUP_DIMENSION D " + "where D.COSTCENTRE = P.COSTCENTRE and P.COMPANY = D.COMPANY " + "order by P.COMPANY asc, D.ROW_INDEX asc";
        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
            return LookupParametersMapper.mapParameters(rows);
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public List<LookupParameterDTO> browseParameters(int company) throws DaoException {
        String query = "select P.*, D.STATUS, D.ROW_INDEX from PARAMETERS_LOOKUP P, PARAMETERS_LOOKUP_DIMENSION D " + "where D.COSTCENTRE = P.COSTCENTRE and P.COMPANY = D.COMPANY and P.COMPANY = ? " + "order by P.COMPANY asc, D.ROW_INDEX asc";

        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, new Object[] { company });
            return LookupParametersMapper.mapParameters(rows);
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    public boolean insertParameter(LookupParameterDTO parameter) throws DaoException {
        String query = "insert into PARAMETERS_LOOKUP (PARAMETER_UUID, COSTCENTRE, COMPANY, FIELD_NAME, FIELD_VALUE) values (?, ?, ?, ?, ?)";
        PreparedStatementSetter pss = prepareStatement(parameter);
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
    public boolean insertParameters(List<LookupParameterDTO> parameters) throws DaoException {
        String query = "insert into PARAMETERS_LOOKUP (PARAMETER_UUID, COSTCENTRE, COMPANY, FIELD_NAME, FIELD_VALUE) values (?, ?, ?, ?, ?)";
        BatchPreparedStatementSetter pss = prepareStatement(parameters);
        int[] result = getJdbcTemplate().batchUpdate(query, pss);
        return result.length > 0;
    }

    @Override
    public boolean updateParametersValues(List<LookupParameterDTO> parameters) throws DaoException {
        String query = "update PARAMETERS_LOOKUP set FIELD_NAME = ?, FIELD_VALUE = ? where PARAMETER_UUID = ? ";

        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (LookupParameterDTO parameter: parameters) {
            String uuid = parameter.getParameterUUID().toString();
            String fieldName = parameter.getFieldName();
            String fieldValue = parameter.getFieldValue();
            batchArgs.add(new Object[] { fieldName, fieldValue, uuid });
        }

        int[] result = getJdbcTemplate().batchUpdate(query, batchArgs);//arnold
//        int[] result = getJdbcTemplate().batchUpdate(query, (BatchPreparedStatementSetter) batchArgs);
//        		(query, batchArgs);

        return result.length >= 1;
    }

    @Override
    public boolean updateChanges(List<LookupParameterChangeDTO> changes) throws DaoException {
        List<LookupParameterChangeDTO> toRemoves = new ArrayList<LookupParameterChangeDTO>();
        List<LookupParameterChangeDTO> toUpdates = new ArrayList<LookupParameterChangeDTO>();
        for (LookupParameterChangeDTO change: changes) {
            if (change.getNewValue().equalsIgnoreCase("null")) {
                toRemoves.add(change);
            } else {
                toUpdates.add(change);
            }
        }
        String query0 = "update PARAMETERS_LOOKUP set FIELD_NAME = ? where FIELD_NAME = ?";
        String query1 = "delete from PARAMETERS_LOOKUP where FIELD_NAME = ?";
        List<Object[]> batchArgs0 = new ArrayList<Object[]>();
        List<Object[]> batchArgs1 = new ArrayList<Object[]>();

        for (LookupParameterChangeDTO toUpdate: toUpdates) {
            String newValue = toUpdate.getNewValue().toString().toUpperCase();
            String oldValue = toUpdate.getOldValue().toString().toUpperCase();
            batchArgs0.add(new Object[] { newValue, oldValue });
        }

        for (LookupParameterChangeDTO toRemove: toRemoves) {
            String oldValue = toRemove.getOldValue().toString().toUpperCase();
            batchArgs1.add(new Object[] { oldValue });
        }
//        int[] result0 = getJdbcTemplate().batchUpdate(query0, (BatchPreparedStatementSetter) batchArgs0);
//        int[] result1 = getJdbcTemplate().batchUpdate(query1, (BatchPreparedStatementSetter) batchArgs1);
        int[] result0 = getJdbcTemplate().batchUpdate(query0, batchArgs0);
        int[] result1 = getJdbcTemplate().batchUpdate(query1, batchArgs1);

        return result0.length >= 1 && result1.length >= 1;
    }

    @Override
    public boolean deleteParameters(List<LookupParameterDTO> parameters) throws DaoException {
        String query = "delete from PARAMETERS_LOOKUP where PARAMETER_UUID = ?";

        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (LookupParameterDTO parameter: parameters) {
            String uuid = parameter.getParameterUUID().toString();
            batchArgs.add(new Object[] { uuid });
        }

        int[] result = getJdbcTemplate().batchUpdate(query,(BatchPreparedStatementSetter)  batchArgs);
        return result.length >= 1;
    }

    private PreparedStatementSetter prepareStatement(final LookupParameterDTO parameter) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, parameter.getParameterUUID().toString());
                ps.setString(2, parameter.getCostCentre());
                ps.setInt(3, parameter.getCompany());

                ps.setString(4, parameter.getFieldName());
                ps.setString(5, parameter.getFieldValue());
            }
        };
        return pss;
    }

    private BatchPreparedStatementSetter prepareStatement(final List<LookupParameterDTO> parameters) {
        BatchPreparedStatementSetter pss = new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                LookupParameterDTO parameter = parameters.get(i);

                ps.setString(1, parameter.getParameterUUID().toString());
                ps.setString(2, parameter.getCostCentre());
                ps.setInt(3, parameter.getCompany());

                ps.setString(4, parameter.getFieldName());
                ps.setString(5, parameter.getFieldValue());
            }

            @Override
            public int getBatchSize() {
                return parameters.size();
            }
        };
        return pss;
    }

}
