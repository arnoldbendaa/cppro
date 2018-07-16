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
package com.softproideas.app.admin.structuredisplayname.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.softproideas.app.admin.structuredisplayname.mapper.StructureDisplayNameMapper;
import com.softproideas.app.admin.structuredisplayname.model.StructureDisplayNameData;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.commons.model.ResponseMessage;

/**
 * <p>This dao is aimed to take care of download and save Display Name.</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Repository("structureDisplayNameDao")
public class StructureDisplayNameDaoImpl extends JdbcDaoSupport implements StructureDisplayNameDao {

    @Autowired
    @Qualifier("cpDataSource")
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static Logger logger = LoggerFactory.getLogger(StructureDisplayNameDaoImpl.class);

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.structuredisplayname.dao.StructureDisplayNameDao#browseCurrencies() */
    @Override
    public List<StructureDisplayNameData> fetchDisplayNameData(int hierarchyId, int parentId) throws DaoException {
        String query = "select STRUCTURE_ELEMENT_ID, DISPLAY_NAME from STRUCTURE_ELEMENT where STRUCTURE_ID = ? and PARENT_ID = ?";
        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, hierarchyId, parentId);
            return StructureDisplayNameMapper.mapDisplayName(rows);
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.structuredisplayname.dao.StructureDisplayNameDao#saveDisplayNames(java.util.List) */
    @Override
    public ResponseMessage saveDisplayNames(List<StructureDisplayNameData> displayName) {
        ResponseMessage responseMessage = new ResponseMessage(true);
        updateDisplayNames(displayName);
        return responseMessage;
    }

    private void updateDisplayNames(final List<StructureDisplayNameData> displayNames) {
        String query = "update STRUCTURE_ELEMENT set DISPLAY_NAME = ? where STRUCTURE_ID = ? and STRUCTURE_ELEMENT_ID = ?";

        getJdbcTemplate().batchUpdate(query, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                StructureDisplayNameData displayName = displayNames.get(i);
                ps.setString(1, displayName.getStructureElementDisplayName());
                ps.setInt(2, displayName.getStructureId());
                ps.setInt(3, displayName.getStructureElementId());
            }

            @Override
            public int getBatchSize() {
                return displayNames.size();
            }

        });
    }

}
