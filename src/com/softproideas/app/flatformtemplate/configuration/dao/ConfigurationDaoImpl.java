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
/**
 * 
 */
package com.softproideas.app.flatformtemplate.configuration.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.softproideas.app.flatformtemplate.configuration.mapper.ConfigurationMapper;
import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationDTO;
import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationDetailsDTO;
import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationTreeDTO;
import com.softproideas.app.flatformtemplate.configuration.model.ConnectionUUID;
import com.softproideas.app.flatformtemplate.configuration.model.DimensionForFlatFormTemplateDTO;
import com.softproideas.app.flatformtemplate.configuration.model.MoveEvent;
import com.softproideas.app.flatformtemplate.configuration.model.TotalDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;
import com.softproideas.commons.util.MapperUtil;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Repository("configurationDao")
public class ConfigurationDaoImpl extends JdbcDaoSupport implements ConfigurationDao {

    @Autowired
    @Qualifier("cpDataSource")
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    @Override
    public ConfigurationTreeDTO browseConfigurations(Boolean disableDirectories) throws ServiceException, DaoException {
        try {
            String query = "select TMPL_CONF_UUID, VIS_ID, VERSION_NUM, PARENT_UUID, IS_DIRECTORY, CHILD_INDEX from XML_FORM_TMPL_CONF order by CHILD_INDEX asc";
            // IS_DIRECTORY DESC, TMPL_CONF_ID asc
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
            ConfigurationTreeDTO configurationRoot = ConfigurationMapper.mapXmlFormConfigurations(rows, disableDirectories);

            return configurationRoot;
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public ConfigurationDetailsDTO fetchConfiguration(UUID configurationUUID) throws ServiceException, DaoException {
        try {
            String query = "select VIS_ID, VERSION_NUM, PARENT_UUID, IS_DIRECTORY from XML_FORM_TMPL_CONF where TMPL_CONF_UUID = ?";
            Map<String, Object> row = getJdbcTemplate().queryForMap(query, configurationUUID.toString());
            String query2 = "select TMPL_CONF_DIM_UUID, VIS_ID, SHEET_NAME, MODEL_VIS_ID, CHILD_INDEX, IS_HIDDEN, EXCLUDED_DIMENSIONS from XML_FORM_TMPL_CONF_DIM where TMPL_CONF_UUID = ?";
            List<Map<String, Object>> rowsDimension = getJdbcTemplate().queryForList(query2, configurationUUID.toString());
            String query3 = "select t.TMPL_CONF_TOTAL_UUID, t.SHEET_NAME, t.CHILD_INDEX, t.IS_HIDDEN, t.IS_GRAND, listagg(dt.TMPL_CONF_DIM_UUID, ',') within group (order by dt.TMPL_CONF_DIM_UUID) as DIMS FROM XML_FORM_TMPL_CONF_TOTAL t left join XML_FORM_TMPL_CONF_DIM_TOTAL dt on dt.TMPL_CONF_TOTAL_UUID = t.TMPL_CONF_TOTAL_UUID where t.TMPL_CONF_UUID = ? group by t.TMPL_CONF_TOTAL_UUID, t.SHEET_NAME, t.CHILD_INDEX, t.IS_HIDDEN, t.IS_GRAND";
            List<Map<String, Object>> rowsTotal = getJdbcTemplate().queryForList(query3, configurationUUID.toString());
            ConfigurationDetailsDTO configuration = ConfigurationMapper.mapXmlFormConfigurationDetails(configurationUUID, row, rowsDimension, rowsTotal);
            return configuration;
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public ConfigurationTreeDTO insertConfiguration(ConfigurationTreeDTO configuration) throws DaoException {
        try {
            if (!isDirectory(configuration.getParentUUID())) {
                throw new DaoException("Can't insert child for Configuration (not Directory)");
            }

            UUID parentUUID = configuration.getParentUUID();
            String queryDecrementIndexes = "UPDATE XML_FORM_TMPL_CONF SET CHILD_INDEX = CHILD_INDEX + 1 WHERE PARENT_UUID = ? AND CHILD_INDEX >= ?";
            PreparedStatementSetter pssDecrementIndexes = prepareUpdateIndexesStatement(parentUUID, configuration.getIndex());
            getJdbcTemplate().update(queryDecrementIndexes, pssDecrementIndexes);

            UUID configurationUUID = UUID.randomUUID();
            String query = "insert into XML_FORM_TMPL_CONF (VIS_ID, VERSION_NUM, PARENT_UUID, IS_DIRECTORY, TMPL_CONF_UUID, CHILD_INDEX) values (?, ?, ?, ?, ?, ?)";
            PreparedStatementSetter pss = prepareStatementForConfiguration(configuration, configurationUUID, 0, true);
            int result = getJdbcTemplate().update(query, pss);
            if (result >= 1) {
                configuration.setConfigurationUUID(configurationUUID);
            } else {
                throw new DaoException("Error while executing insert query");
            }
            return configuration;
        } catch (Exception e) {
            String errorMsg = "Error while executing insert query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    private PreparedStatementSetter prepareUpdateIndexesStatement(final UUID parentUUID, final int index) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, parentUUID.toString());
                ps.setInt(2, index);
            }
        };
        return pss;
    }

    private PreparedStatementSetter prepareChangeParrentStatement(final UUID configurationUUID, final UUID parentUUID, final int index) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, parentUUID.toString());
                ps.setInt(2, index);
                ps.setString(3, configurationUUID.toString());
            }
        };
        return pss;
    }

    private Boolean isDirectory(UUID configurationUUID) {
        String testQuery = "select IS_DIRECTORY from XML_FORM_TMPL_CONF where TMPL_CONF_UUID = ?";
        Map<String, Object> row = getJdbcTemplate().queryForMap(testQuery, configurationUUID.toString());
        boolean isDirectory = ((String) row.get("IS_DIRECTORY")).equalsIgnoreCase("Y");
        return isDirectory;
    }

    @Override
    public ResponseMessage updateConfigurationIndex(final MoveEvent moveEvent) throws DaoException {
        try {
            String queryDecrementIndexes = "UPDATE XML_FORM_TMPL_CONF SET CHILD_INDEX = CHILD_INDEX - 1 WHERE PARENT_UUID = ? AND CHILD_INDEX > ?";
            String queryIncrementIndexes = "UPDATE XML_FORM_TMPL_CONF SET CHILD_INDEX = CHILD_INDEX + 1 WHERE PARENT_UUID = ? AND CHILD_INDEX >= ?";
            String queryChangeParrent = "UPDATE XML_FORM_TMPL_CONF SET PARENT_UUID = ?, CHILD_INDEX = ? WHERE TMPL_CONF_UUID = ?";
            ResponseMessage message = new ResponseMessage(true);

            PreparedStatementSetter pss1 = prepareUpdateIndexesStatement(moveEvent.getOldParentUUID(), moveEvent.getOldIndex());
            PreparedStatementSetter pss2 = prepareUpdateIndexesStatement(moveEvent.getNewParentUUID(), moveEvent.getNewIndex());
            PreparedStatementSetter pss3 = prepareChangeParrentStatement(moveEvent.getConfigurationUUID(), moveEvent.getNewParentUUID(), moveEvent.getNewIndex());

            getJdbcTemplate().update(queryDecrementIndexes, pss1);
            getJdbcTemplate().update(queryIncrementIndexes, pss2);
            getJdbcTemplate().update(queryChangeParrent, pss3);

            return message;
        } catch (Exception e) {
            String errorMsg = "Error while executing update query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public ResponseMessage updateConfiguration(ConfigurationDetailsDTO configuration) throws DaoException {
        try {
            ResponseMessage message = new ResponseMessage();
            String query = "update XML_FORM_TMPL_CONF set VIS_ID = ?, VERSION_NUM = ?, PARENT_UUID = ?, IS_DIRECTORY = ? where TMPL_CONF_UUID = ?";
            int currentVersionNum = configuration.getVersionNum();
            // validate versionNum
            Map<String, Object> currentDBRow = getJdbcTemplate().queryForMap("select VERSION_NUM from XML_FORM_TMPL_CONF where TMPL_CONF_UUID = ?", configuration.getConfigurationUUID().toString());
            ValidationError validationError = MapperUtil.validateVersionNum(currentVersionNum, currentDBRow);
            if (validationError.isError()) {
                return validationError;
            }
            PreparedStatementSetter pss = prepareStatementForConfiguration(configuration, configuration.getConfigurationUUID(), currentVersionNum + 1, false);
            int result = getJdbcTemplate().update(query, pss);
            if (result >= 1) {
                List<ConnectionUUID> connections = prepareListToUpdate(configuration);
                List<DimensionForFlatFormTemplateDTO> dimensionList = configuration.getDimensions();
                List<TotalDTO> totalList = configuration.getTotals();
                if (dimensionList != null) {
                    updateDimensions(dimensionList, configuration.getConfigurationUUID().toString());
                }
                if (totalList != null) {
                    updateTotals(totalList, configuration.getConfigurationUUID().toString());
                }
                updateConnectionsTotalsWithDimensions(connections, configuration.getConfigurationUUID().toString());
                message.setSuccess(true);
            } else {
                message.setError(true);
            }
            return message;
        } catch (Exception e) {
            String errorMsg = "Error while executing update queries";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    /**
     * Add UUID for recently created elements in Configurations. Elements can be: Dimension, Total, Connection. Connection determines attachment Dimension for Total.
     * @param configuration
     */
    private List<ConnectionUUID> prepareListToUpdate(ConfigurationDetailsDTO configuration) {
        UUID dimensionUUID;
        UUID totalUUID;
        List<DimensionForFlatFormTemplateDTO> dimensionList = configuration.getDimensions();
        List<TotalDTO> totalList = configuration.getTotals();
        List<ConnectionUUID> connectionUUIDs = new ArrayList<ConnectionUUID>();
        List<DimensionForFlatFormTemplateDTO> dimensionTotalList;
        for (TotalDTO total: totalList) {
            if (total.getTotalUUID() == null) {
                totalUUID = UUID.randomUUID();
                total.setTotalUUID(totalUUID);
            } else {
                total.setOldTotal(true);
            }
        }
        for (DimensionForFlatFormTemplateDTO dimension: dimensionList) {
            if (dimension.getDimensionUUID() == null) {
                dimensionUUID = UUID.randomUUID();
                dimension.setDimensionUUID(dimensionUUID);
            } else {
                dimension.setOldDimension(true);
            }
            for (TotalDTO total: totalList) {

                dimensionTotalList = total.getDimensionList();
                if (dimensionTotalList != null) {
                    for (DimensionForFlatFormTemplateDTO dimensionTotal: dimensionTotalList) {
                        if (dimension.getDimensionVisId().equals(dimensionTotal.getDimensionVisId()) && dimension.getModelVisId().equals(dimensionTotal.getModelVisId())) {
                            ConnectionUUID connection = new ConnectionUUID();
                            connection.setLeft(total.getTotalUUID());
                            connection.setRight(dimension.getDimensionUUID());
                            connectionUUIDs.add(connection);
                        }
                    }
                }
            }
        }
        return connectionUUIDs;
    }

    /**
     * Updating list of Dimension.
     * @param dimensionList
     * @param configurationUUID_String
     */
    private void updateDimensions(List<DimensionForFlatFormTemplateDTO> dimensionList, String configurationUUID_String) {
        List<DimensionForFlatFormTemplateDTO> oldDimensionList = new ArrayList<DimensionForFlatFormTemplateDTO>();
        List<DimensionForFlatFormTemplateDTO> newDimensionList = new ArrayList<DimensionForFlatFormTemplateDTO>();
        splitDimesnionList(dimensionList, oldDimensionList, newDimensionList);

        deleteBatchDimensions(oldDimensionList, configurationUUID_String);
        insertBatchDimensions(newDimensionList, configurationUUID_String);
        updateBatchDimensions(oldDimensionList, configurationUUID_String);
    }

    /**
     * Division list of Dimension to newDimension list and oldDiemsnion list.
     * @param dimensionList
     * @param oldDimensionList
     * @param newDimensionList
     */
    private void splitDimesnionList(List<DimensionForFlatFormTemplateDTO> dimensionList, List<DimensionForFlatFormTemplateDTO> oldDimensionList, List<DimensionForFlatFormTemplateDTO> newDimensionList) {
        for (DimensionForFlatFormTemplateDTO dimension: dimensionList) {
            if (dimension.isOldDimension()) {
                oldDimensionList.add(dimension);
            } else {
                newDimensionList.add(dimension);
            }
        }
    }

    /**
     * Updating list of Totals.
     * @param totalList
     * @param configurationUUID_String
     */
    private void updateTotals(List<TotalDTO> totalList, String configurationUUID_String) {
        List<TotalDTO> oldTotalList = new ArrayList<TotalDTO>();
        List<TotalDTO> newTotalList = new ArrayList<TotalDTO>();
        splitTotalList(totalList, oldTotalList, newTotalList);

        deleteBatchTotals(oldTotalList, configurationUUID_String);
        insertBatchTotals(newTotalList, configurationUUID_String);
        updateBatchTotals(oldTotalList, configurationUUID_String);
    }

    /**
     * Division list of Totals to new Total list and old Total List.
     * @param totalList
     * @param oldTotalList
     * @param newTotalList
     */
    private void splitTotalList(List<TotalDTO> totalList, List<TotalDTO> oldTotalList, List<TotalDTO> newTotalList) {
        for (TotalDTO total: totalList) {
            if (total.isOldTotal()) {
                oldTotalList.add(total);
            } else {
                newTotalList.add(total);
            }
        }
    }

    /**
     * Update Connection determines attachment Dimension for Total.
     * @param totalList
     */
    private void updateConnectionsTotalsWithDimensions(List<ConnectionUUID> connections, String configurationUUID_String) {
        String queryDeletingDimensions = "delete from XML_FORM_TMPL_CONF_DIM_TOTAL where TMPL_CONF_UUID = ?";
        Object[] configurationUUID_ForUpdate = new Object[1];
        int[] types = new int[1];
        configurationUUID_ForUpdate[0] = configurationUUID_String;
        types[0] = Types.VARCHAR;
        getJdbcTemplate().update(queryDeletingDimensions, configurationUUID_ForUpdate, types);
        insertBatchDimensionsForTotals(connections, configurationUUID_String);
    }

    @Override
    public ResponseMessage updateConfigurationName(UUID configurationUUID, String configurationName, int versionNum) throws DaoException {
        try {
            ResponseMessage message = new ResponseMessage();
            String query = "update XML_FORM_TMPL_CONF set VIS_ID = ?, VERSION_NUM = ? where TMPL_CONF_UUID = ?";
            // validate versionNum
            Map<String, Object> currentDBRow = getJdbcTemplate().queryForMap("select VERSION_NUM from XML_FORM_TMPL_CONF where TMPL_CONF_UUID = ?", configurationUUID.toString());
            ValidationError validationError = MapperUtil.validateVersionNum(versionNum, currentDBRow);
            if (validationError.isError()) {
                return validationError;
            }
            PreparedStatementSetter pss = prepareStatementForConfigurationName(configurationUUID, configurationName, versionNum + 1);
            int result = getJdbcTemplate().update(query, pss);
            if (result >= 1) {
                message.setSuccess(true);
            } else {
                message.setError(true);
            }
            return message;
        } catch (Exception e) {
            String errorMsg = "Error while executing update name query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public ResponseMessage deleteConfiguration(UUID configurationUUID) throws DaoException {
        try {
            String query = "delete from XML_FORM_TMPL_CONF where TMPL_CONF_UUID = ?";
            ResponseMessage message = new ResponseMessage();
            int result = getJdbcTemplate().update(query, configurationUUID.toString());
            if (result >= 1) {
                message.setSuccess(true);
            } else {
                message.setError(true);
            }
            return message;
        } catch (Exception e) {
            String errorMsg = "Error while executing delete query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    private PreparedStatementSetter prepareStatementForConfiguration(final ConfigurationDTO configuration, final UUID configurationUUID, final int versionNum, final boolean isChildIndex) {
        final String isDirectory = (configuration.isDirectory()) ? "Y" : "N";
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, configuration.getConfigurationVisId());
                ps.setInt(2, versionNum);
                if (configuration.getParentUUID() != null) {
                    ps.setString(3, configuration.getParentUUID().toString());
                } else {
                    ps.setNull(3, 0);
                }
                ps.setString(4, isDirectory);
                ps.setString(5, configurationUUID.toString());
                if (isChildIndex) {
                    ps.setInt(6, configuration.getIndex());
                }
            }
        };
        return pss;
    };

    private PreparedStatementSetter prepareStatementForConfigurationName(final UUID configurationUUID, final String configurationVisId, final int versionNum) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, configurationVisId);
                ps.setInt(2, versionNum);
                ps.setString(3, configurationUUID.toString());
            }
        };
        return pss;
    };

    public void insertBatchDimensions(final List<DimensionForFlatFormTemplateDTO> dimensions, final String configurationUUID_String) {
        if (dimensions.size() > 0) {
            String query = "insert into XML_FORM_TMPL_CONF_DIM (TMPL_CONF_UUID, TMPL_CONF_DIM_UUID, VIS_ID, SHEET_NAME, MODEL_VIS_ID, CHILD_INDEX, IS_HIDDEN, EXCLUDED_DIMENSIONS) values (?, ?, ?, ?, ?, ?, ?, ?)";

            getJdbcTemplate().batchUpdate(query, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    DimensionForFlatFormTemplateDTO dimension = dimensions.get(i);
                    ps.setString(1, configurationUUID_String);
                    ps.setString(2, dimension.getDimensionUUID().toString());
                    ps.setString(3, dimension.getDimensionVisId());
                    ps.setString(4, dimension.getSheetName());
                    ps.setString(5, dimension.getModelVisId());
                    ps.setInt(6, dimension.getIndex());
                    ps.setBoolean(7, dimension.isHidden());
                    if(dimension.getExcludedDimensions() == null) {
                        ps.setString(8, null);
                    } else {
                        ps.setString(8, dimension.getExcludedDimensions().toString());
                    }
                }

                @Override
                public int getBatchSize() {
                    return dimensions.size();
                }

            });
        }
    }

    public void insertBatchTotals(final List<TotalDTO> totals, final String configurationUUID_String) {
        String query = "insert into XML_FORM_TMPL_CONF_TOTAL (TMPL_CONF_UUID, TMPL_CONF_TOTAL_UUID, SHEET_NAME, CHILD_INDEX, IS_HIDDEN, IS_GRAND) values (?, ?, ?, ?, ?, ?)";

        getJdbcTemplate().batchUpdate(query, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TotalDTO total = totals.get(i);
                ps.setString(1, configurationUUID_String);
                ps.setString(2, total.getTotalUUID().toString());
                ps.setString(3, total.getSheetName());
                ps.setInt(4, total.getIndex());
                ps.setBoolean(5, total.isHidden());
                ps.setBoolean(6, total.isGrandTotal());
            }

            @Override
            public int getBatchSize() {
                return totals.size();
            }

        });
    }

    public void insertBatchDimensionsForTotals(final List<ConnectionUUID> connectionUUIDs, final String configurationUUID_String) {
        String query = "insert into XML_FORM_TMPL_CONF_DIM_TOTAL (TMPL_CONF_TOTAL_UUID, TMPL_CONF_DIM_UUID, TMPL_CONF_UUID) values (?, ?, ?)";

        getJdbcTemplate().batchUpdate(query, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ConnectionUUID connection = connectionUUIDs.get(i);
                ps.setString(1, connection.getLeft().toString());
                ps.setString(2, connection.getRight().toString());
                ps.setString(3, configurationUUID_String);
            }

            @Override
            public int getBatchSize() {
                return connectionUUIDs.size();
            }

        });
    }

    public void updateBatchDimensions(final List<DimensionForFlatFormTemplateDTO> dimensions, final String configurationUUID_String) {
        if (dimensions.size() > 0) {
            String query = "update XML_FORM_TMPL_CONF_DIM set VIS_ID = ?, SHEET_NAME = ?, CHILD_INDEX = ?, IS_HIDDEN = ?, EXCLUDED_DIMENSIONS = ? where TMPL_CONF_DIM_UUID = ?";

            getJdbcTemplate().batchUpdate(query, new BatchPreparedStatementSetter() {

                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    DimensionForFlatFormTemplateDTO dimension = dimensions.get(i);
                    ps.setString(1, dimension.getDimensionVisId());
                    ps.setString(2, dimension.getSheetName());
                    ps.setInt(3, dimension.getIndex());
                    ps.setBoolean(4, dimension.isHidden());
                    if(dimension.getExcludedDimensions() != null) {
                        ps.setString(5, dimension.getExcludedDimensions().toString());
                    } else {
                        ps.setString(5, null);
                    }
                    ps.setString(6, dimension.getDimensionUUID().toString());
                }

                @Override
                public int getBatchSize() {
                    return dimensions.size();
                }

            });
        }
    }

    public void updateBatchTotals(final List<TotalDTO> totals, final String configurationUUID_String) {
        String query = "update XML_FORM_TMPL_CONF_TOTAL set SHEET_NAME = ?, CHILD_INDEX = ?, IS_HIDDEN = ?, IS_GRAND = ? where TMPL_CONF_TOTAL_UUID = ?";

        getJdbcTemplate().batchUpdate(query, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                TotalDTO total = totals.get(i);
                ps.setString(1, total.getSheetName());
                ps.setInt(2, total.getIndex());
                ps.setBoolean(3, total.isHidden());
                ps.setBoolean(4, total.isGrandTotal());
                ps.setString(5, total.getTotalUUID().toString());
            }

            @Override
            public int getBatchSize() {
                return totals.size();
            }

        });
    }

    /**
     * Delete all Dimensions not occur at dimensionListWithUUID.
     * @param dimensionListWithUUID
     * @param configurationUUID
     */
    private void deleteBatchDimensions(List<DimensionForFlatFormTemplateDTO> dimensionListWithUUID, String configurationUUID_String) {
        Object[] dimensionUUIDs = new Object[dimensionListWithUUID.size() + 1];
        dimensionUUIDs[0] = configurationUUID_String;
        if (dimensionListWithUUID.size() > 0) {
            String queryDeletingDimensions = "delete from XML_FORM_TMPL_CONF_DIM where TMPL_CONF_UUID = ? and TMPL_CONF_DIM_UUID not in (?";
            UUID dimensionTemporaryUUID = dimensionListWithUUID.get(0).getDimensionUUID();
            if (dimensionTemporaryUUID != null) {
                dimensionUUIDs[1] = dimensionTemporaryUUID.toString();
            } else {
                dimensionUUIDs[1] = "non";
            }
            int i = 2;
            for (DimensionForFlatFormTemplateDTO dimension: dimensionListWithUUID.subList(1, dimensionListWithUUID.size())) {
                queryDeletingDimensions += ", ?";
                dimensionTemporaryUUID = dimension.getDimensionUUID();
                if (dimensionTemporaryUUID != null) {
                    dimensionUUIDs[i] = dimensionTemporaryUUID.toString();
                } else {
                    dimensionUUIDs[i] = "non";
                }
                i++;
            }
            queryDeletingDimensions += ")";
            getJdbcTemplate().update(queryDeletingDimensions, dimensionUUIDs);
        } else {
            String queryDeletingDimensions = "delete from XML_FORM_TMPL_CONF_DIM where TMPL_CONF_UUID = ?";
            getJdbcTemplate().update(queryDeletingDimensions, configurationUUID_String);
        }
    }

    /**
     * Delete all Totals not occur at totalListWithUUID.
     * @param totalListWithUUID
     * @param configurationUUID
     */
    private void deleteBatchTotals(List<TotalDTO> totalListWithUUID, String configurationUUID_String) {
        Object[] totalUUIDs = new Object[totalListWithUUID.size() + 1];
        totalUUIDs[0] = configurationUUID_String.toString();
        if (totalListWithUUID.size() > 0) {
            String queryDeletingTotals = "delete from XML_FORM_TMPL_CONF_TOTAL where TMPL_CONF_UUID = ? and TMPL_CONF_TOTAL_UUID NOT IN (?";
            UUID totalTemporaryUUID = totalListWithUUID.get(0).getTotalUUID();
            if (totalTemporaryUUID != null) {
                totalUUIDs[1] = totalTemporaryUUID.toString();
            } else {
                totalUUIDs[1] = "non";
            }
            int i = 2;
            for (TotalDTO total: totalListWithUUID.subList(1, totalListWithUUID.size())) {
                queryDeletingTotals += ", ?";
                totalTemporaryUUID = total.getTotalUUID();
                if (totalTemporaryUUID != null) {
                    totalUUIDs[i] = totalTemporaryUUID.toString();
                } else {
                    totalUUIDs[i] = "non";
                }
                i++;
            }
            queryDeletingTotals += ")";
            getJdbcTemplate().update(queryDeletingTotals, totalUUIDs);
        } else {
            String queryDeletingTotals = "delete from XML_FORM_TMPL_CONF_TOTAL  where TMPL_CONF_UUID = ?";
            getJdbcTemplate().update(queryDeletingTotals, configurationUUID_String.toString());
        }
    }

}
