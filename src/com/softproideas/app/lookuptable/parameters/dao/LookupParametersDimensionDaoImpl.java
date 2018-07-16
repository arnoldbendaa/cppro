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

import com.softproideas.app.lookuptable.parameters.mapper.LookupParametersDimensionMapper;
import com.softproideas.app.lookuptable.parameters.model.LookupParameterDimensionDTO;
import com.softproideas.common.enums.LookupParameterStatus;
import com.softproideas.common.exceptions.DaoException;

@Repository("lookupParametersDimensionDao")
public class LookupParametersDimensionDaoImpl extends JdbcDaoSupport implements LookupParametersDimensionDao {

    @Autowired
    @Qualifier("cpDataSource")
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public List<LookupParameterDimensionDTO> browseDimensions() throws DaoException {
        String query = "select * from PARAMETERS_LOOKUP_DIMENSION order by COMPANY asc, STATUS desc, ROW_INDEX asc";
        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
            return LookupParametersDimensionMapper.mapDimensions(rows);
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public List<LookupParameterDimensionDTO> browseDimensions(int company) throws DaoException {
        String query = "select * from PARAMETERS_LOOKUP_DIMENSION where COMPANY = ? order by STATUS desc, ROW_INDEX asc";
        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, new Object[] { company });
            return LookupParametersDimensionMapper.mapDimensions(rows);
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    public boolean insertDimension(LookupParameterDimensionDTO dimension) throws DaoException {
        String query = "insert into PARAMETERS_LOOKUP_DIMENSION (COSTCENTRE, STATUS, COMPANY, ROW_INDEX, IS_LEAF) values (?, ?, ?, ?, ?)";
        PreparedStatementSetter pss = prepareStatement(dimension);
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
    public boolean insertDimensions(List<LookupParameterDimensionDTO> dimensions) throws DaoException {
        String query = "insert into PARAMETERS_LOOKUP_DIMENSION (HIERARCHY_GROUP, COSTCENTRE, STATUS, COMPANY, ROW_INDEX, IS_LEAF) values (?, ?, ?, ?, ?, ?)";
        BatchPreparedStatementSetter pss = prepareStatement(dimensions);
        int[] result = getJdbcTemplate().batchUpdate(query, pss);
        return result.length > 0;
    }

    @Override
    public boolean updateDimensions(List<LookupParameterDimensionDTO> dimensions) throws DaoException {
        String query = "update PARAMETERS_LOOKUP_DIMENSION set STATUS = ?, ROW_INDEX = ? where COSTCENTRE = ? and COMPANY = ? and HIERARCHY_GROUP = ?";

        List<Object[]> batchArgs = new ArrayList<Object[]>();
        for (LookupParameterDimensionDTO dimension: dimensions) {
            LookupParameterStatus status = dimension.getStatus();
            int rowIndex = dimension.getRowIndex();
            String costCentre = dimension.getCostCentre();
            int company = dimension.getCompany();
            String hierarchy = dimension.getGroup();
            batchArgs.add(new Object[] { status.getValue(), rowIndex, costCentre, company, hierarchy});
        }

        int[] result = getJdbcTemplate().batchUpdate(query, (BatchPreparedStatementSetter) batchArgs);
        return result.length >= 1;
    }
    
    @Override
    public boolean deleteDimensions(List<LookupParameterDimensionDTO> dimensions) throws DaoException {
        String query = "delete from PARAMETERS_LOOKUP_DIMENSION where COSTCENTRE = ? AND COMPANY = ? AND HIERARCHY_GROUP = ? ";
        List<Object[]> batchArgs = new ArrayList<Object[]>();
        
        for (LookupParameterDimensionDTO dimension: dimensions) {
            String costCentre = dimension.getCostCentre();
            int company = dimension.getCompany();
            String hierarchyGroup = dimension.getGroup();
            batchArgs.add(new Object[] { costCentre, company, hierarchyGroup });
        }

        int[] result = getJdbcTemplate().batchUpdate(query,(BatchPreparedStatementSetter)  batchArgs);
        return result.length >= 1;
    
    }
    

    private PreparedStatementSetter prepareStatement(final LookupParameterDimensionDTO dimension) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, dimension.getCostCentre());
                ps.setInt(2, dimension.getStatus().getValue());
                ps.setInt(3, dimension.getCompany());
                ps.setInt(4, dimension.getRowIndex());
                if (dimension.isLeaf()) {
                    ps.setString(5, "Y");
                } else {
                    ps.setString(5, "N");
                }
            }
        };
        return pss;
    }

    private BatchPreparedStatementSetter prepareStatement(final List<LookupParameterDimensionDTO> dimensions) {
        BatchPreparedStatementSetter pss = new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                LookupParameterDimensionDTO dimension = dimensions.get(i);

                ps.setString(1, dimension.getGroup());
                ps.setString(2, dimension.getCostCentre());
                ps.setInt(3, dimension.getStatus().getValue());
                ps.setInt(4, dimension.getCompany());
                ps.setInt(5, dimension.getRowIndex());
                if (dimension.isLeaf()) {
                    ps.setString(6, "Y");
                } else {
                    ps.setString(6, "N");
                }
            }

            @Override
            public int getBatchSize() {
                return dimensions.size();
            }
        };
        return pss;
    }

}
