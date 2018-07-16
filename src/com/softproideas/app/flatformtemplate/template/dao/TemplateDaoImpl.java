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
package com.softproideas.app.flatformtemplate.template.dao;

import java.math.BigDecimal;
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
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.util.flatform.model.workbook.editor.WorkbookMapper;
import com.softproideas.app.core.users.model.UserCoreDTO;
import com.softproideas.app.flatformtemplate.template.mapper.TemplateMapper;
import com.softproideas.app.flatformtemplate.template.model.MoveEvent;
import com.softproideas.app.flatformtemplate.template.model.TemplateDetailsDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;
import com.softproideas.commons.util.MapperUtil;

@Repository("templateDao")
public class TemplateDaoImpl extends JdbcDaoSupport implements TemplateDao {

    @Autowired
    @Qualifier("cpDataSource")
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    private static Logger logger = LoggerFactory.getLogger(TemplateDaoImpl.class);

    @Override
    public TemplateDetailsDTO browseTemplates(Boolean disableDirectories) throws ServiceException, SQLException, DaoException {
        try {
            String query = "select TMPL_UUID, VIS_ID, DESCRIPTION, VERSION_NUM, JSON_FORM, PARENT_UUID, TYPE, DEFINITION, CHILD_INDEX from xml_form_tmpl order by TYPE asc, TMPL_ID asc";
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
            TemplateDetailsDTO templateList = TemplateMapper.mapXmlFormTemplatesTree(rows, disableDirectories);
            return templateList;
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public TemplateDetailsDTO fetchTemplate(UUID templateUUID, AllUsersELO allUsersELO) throws ServiceException, SQLException, DaoException {
        try {
            String query = "select TMPL_UUID, VIS_ID, DESCRIPTION, VERSION_NUM, JSON_FORM, PARENT_UUID, TYPE, DEFINITION, CHILD_INDEX from xml_form_tmpl WHERE TMPL_UUID = ?";
            Map<String, Object> row = getJdbcTemplate().queryForMap(query, templateUUID.toString());
            TemplateDetailsDTO template = new TemplateDetailsDTO();
            List<Integer> userIds = new ArrayList<Integer>();
            if (allUsersELO != null) {
                userIds = fetchUserIds(templateUUID);
            }
            template = TemplateMapper.mapXmlFormTemplated(row, true, false, userIds, allUsersELO);
            return template;
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    private List<Integer> fetchUserIds(UUID templateUUID) {
        List<Integer> userIds = new ArrayList<Integer>();
        String query = "select USER_ID from xml_form_tmpl_user_link WHERE TMPL_UUID = ?";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, templateUUID.toString());
        for (Map<String, Object> map: rows) {
            Integer userId = ((BigDecimal) map.get("USER_ID")).intValue();
            userIds.add(userId);
        }
        return userIds;
    }

    @Override
    public TemplateDetailsDTO insertTemplate(final TemplateDetailsDTO template) throws DaoException {
        try {
            UUID parentUUID = template.getParentUUID();
            ResponseMessage message = new ResponseMessage();
            // String typeDirectory = TemplateMapper.mapType(0);
            // TODO:zamienic na zapytanie
            // if (!fetchTemplate(parentUUID).getType().equals(typeDirectory)) {
            // throw new DaoException("Can't insert child for Template (not Directory)");
            // }

            String queryDecrementIndexes = "UPDATE XML_FORM_TMPL SET CHILD_INDEX = CHILD_INDEX + 1 WHERE PARENT_UUID = ? AND CHILD_INDEX >= ?";
            PreparedStatementSetter pssDecrementIndexes = prepareUpdateIndexesStatement(parentUUID, template.getIndex());
            getJdbcTemplate().update(queryDecrementIndexes, pssDecrementIndexes);

            String query = "INSERT INTO xml_form_tmpl (VIS_ID, DESCRIPTION, VERSION_NUM, JSON_FORM, PARENT_UUID, TYPE, DEFINITION, CHILD_INDEX, TMPL_UUID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatementSetter pss = prepareStatement(template, null, 0);
            int result = getJdbcTemplate().update(query, pss);
            if (result >= 1) {
                Integer type = TemplateMapper.mapType(template.getType());
                String selectQuery = "select TMPL_UUID from xml_form_tmpl WHERE VIS_ID = ? AND PARENT_UUID = ? AND TYPE = ? AND ROWNUM = 1 ORDER BY TMPL_UUID DESC";
                Map<String, Object> row = getJdbcTemplate().queryForMap(selectQuery, template.getVisId(), parentUUID.toString(), type);
                UUID tmplUUID = UUID.fromString((String) row.get("TMPL_UUID"));
                message.setSuccess(true);
                template.setTemplateUUID(tmplUUID);
            } else {
                message.setError(true);
            }
            return template;// message;
        } catch (Exception e) {
            String errorMsg = "Error while executing insert query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    @Override
    public ResponseMessage updateTemplate(final TemplateDetailsDTO template, Boolean fullUpdate) throws DaoException {
        try {
            final UUID templateUUID = template.getTemplateUUID();
            ResponseMessage message = new ResponseMessage();

            validateIfRoot(templateUUID, "update");

            int currentVersionNum = template.getVersionNum();
            // validate versionNum
            Map<String, Object> currentDBRow = getJdbcTemplate().queryForMap("select VERSION_NUM from xml_form_tmpl WHERE TMPL_UUID = ?", templateUUID.toString());
            ValidationError validationError = MapperUtil.validateVersionNum(currentVersionNum, currentDBRow);
            if (validationError.isError()) {
                return validationError;
            }
            String query;
            PreparedStatementSetter pss;
            if (fullUpdate) {
                query = "UPDATE XML_FORM_TMPL SET VIS_ID = ?, DESCRIPTION = ?, VERSION_NUM = ?, JSON_FORM = ?, PARENT_UUID = ?, TYPE = ?, DEFINITION = ? WHERE TMPL_UUID = ?";
                pss = prepareFullUpdateStatement(template, templateUUID, currentVersionNum + 1);
            } else {
                query = "UPDATE XML_FORM_TMPL SET VIS_ID = ?, DESCRIPTION = ?, VERSION_NUM = ? WHERE TMPL_UUID = ?";
                pss = prepareSimpleUpdateStatement(template, templateUUID, currentVersionNum + 1);
            }
            updateSelectedUsers(template);
            // update template
            int result = getJdbcTemplate().update(query, pss);
            if (result >= 1) {
                message.setSuccess(true);
            } else {
                message.setError(true);
            }
            return message;
        } catch (Exception e) {
            String errorMsg = "Error while executing update query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

    private void updateSelectedUsers(final TemplateDetailsDTO template) {

        final UUID templateUUID = template.getTemplateUUID();
        String queryDeletingTemplates = "delete from XML_FORM_TMPL_USER_LINK  where TMPL_UUID = ?";
        String queryUpdateTemplates = "insert into XML_FORM_TMPL_USER_LINK (USER_ID, TMPL_UUID) values (?, ?)";
        BatchPreparedStatementSetter upd = updateBatchInsertUserTemplate(template, templateUUID);
        getJdbcTemplate().update(queryDeletingTemplates, templateUUID.toString());
        getJdbcTemplate().batchUpdate(queryUpdateTemplates, upd);
    }

    private BatchPreparedStatementSetter updateBatchInsertUserTemplate(final TemplateDetailsDTO template, final UUID templateUUID) {
        BatchPreparedStatementSetter pss = new BatchPreparedStatementSetter() {
            List<UserCoreDTO> users = template.getUsers();

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                UserCoreDTO user = users.get(i);
                ps.setInt(1, user.getUserId());
                ps.setString(2, templateUUID.toString());
            }

            @Override
            public int getBatchSize() {

                return users.size();
            }

        };
        return pss;
    }

    @Override
    public ResponseMessage updateTemplateIndex(final MoveEvent moveEvent) throws DaoException {
        try {
            String queryDecrementIndexes = "UPDATE XML_FORM_TMPL SET CHILD_INDEX = CHILD_INDEX - 1 WHERE PARENT_UUID = ? AND CHILD_INDEX > ?";
            String queryIncrementIndexes = "UPDATE XML_FORM_TMPL SET CHILD_INDEX = CHILD_INDEX + 1 WHERE PARENT_UUID = ? AND CHILD_INDEX >= ?";
            String queryChangeParrent = "UPDATE XML_FORM_TMPL SET PARENT_UUID = ?, CHILD_INDEX = ? WHERE TMPL_UUID = ?";
            ResponseMessage message = new ResponseMessage(true);

            PreparedStatementSetter pss1 = prepareUpdateIndexesStatement(moveEvent.getOldParentUUID(), moveEvent.getOldIndex());
            PreparedStatementSetter pss2 = prepareUpdateIndexesStatement(moveEvent.getNewParentUUID(), moveEvent.getNewIndex());
            PreparedStatementSetter pss3 = prepareChangeParrentStatement(moveEvent.getTemplateUUID(), moveEvent.getNewParentUUID(), moveEvent.getNewIndex());

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
    public ResponseMessage deleteTemplate(UUID templateUUID) throws DaoException {
        try {
            ResponseMessage message = new ResponseMessage();

            validateIfRoot(templateUUID, "delete");

            String queryDecrementIndexes = "UPDATE XML_FORM_TMPL SET CHILD_INDEX = CHILD_INDEX - 1 WHERE PARENT_UUID = (SELECT PARENT_UUID FROM XML_FORM_TMPL WHERE TMPL_UUID = ?) AND CHILD_INDEX > (SELECT CHILD_INDEX FROM XML_FORM_TMPL WHERE TMPL_UUID = ?)";
            PreparedStatementSetter pssDecrementIndexes = prepareUpdateIndexesStatement(templateUUID);
            getJdbcTemplate().update(queryDecrementIndexes, pssDecrementIndexes);

            String query = "DELETE FROM XML_FORM_TMPL WHERE TMPL_UUID = ?";
            int result = getJdbcTemplate().update(query, templateUUID.toString());
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

    private PreparedStatementSetter prepareStatement(final TemplateDetailsDTO template, final UUID templateUUID, final int versionNum) {
        final Integer type = TemplateMapper.mapType(template.getType());
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, template.getVisId());
                ps.setString(2, template.getDescription());
                ps.setInt(3, versionNum);
                ps.setString(4, template.getJsonForm());
                if (template.getParentUUID() != null) {
                    ps.setString(5, template.getParentUUID().toString());
                } else {
                    ps.setNull(5, 0);
                }
                ps.setInt(6, type);
                if (type != 0) {
                    ps.setString(7, WorkbookMapper.mapWorkbookDTOToXmlString(template.getWorkbook()));
                } else {
                    ps.setNull(7, 0);
                }
                ps.setInt(8, template.getIndex());
                if (templateUUID == null) {
                    ps.setString(9, UUID.randomUUID().toString());
                } else {
                    ps.setString(9, templateUUID.toString());
                }

            }
        };
        return pss;
    };

    private PreparedStatementSetter prepareFullUpdateStatement(final TemplateDetailsDTO template, final UUID templateUUID, final int versionNum) {
        final Integer type = TemplateMapper.mapType(template.getType());
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, template.getVisId());
                ps.setString(2, template.getDescription());
                ps.setInt(3, versionNum);
                ps.setString(4, template.getJsonForm());
                if (template.getParentUUID() != null) {
                    ps.setString(5, template.getParentUUID().toString());
                } else {
                    ps.setNull(5, 0);
                }
                ps.setInt(6, type);
                ps.setString(7, WorkbookMapper.mapWorkbookDTOToXmlString(template.getWorkbook()));
                if (templateUUID == null) {
                    ps.setString(8, UUID.randomUUID().toString());
                } else {
                    ps.setString(8, templateUUID.toString());
                }
            }
        };
        return pss;
    };

    private PreparedStatementSetter prepareSimpleUpdateStatement(final TemplateDetailsDTO template, final UUID templateUUID, final int versionNum) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, template.getVisId());
                ps.setString(2, template.getDescription());
                ps.setInt(3, versionNum);
                ps.setString(4, templateUUID.toString());
            }
        };
        return pss;
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

    private PreparedStatementSetter prepareUpdateIndexesStatement(final UUID templateUUID) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, templateUUID.toString());
                ps.setString(2, templateUUID.toString());
            }
        };
        return pss;
    }

    private PreparedStatementSetter prepareChangeParrentStatement(final UUID templateUUID, final UUID parentUUID, final int index) {
        PreparedStatementSetter pss = new PreparedStatementSetter() {
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, parentUUID.toString());
                ps.setInt(2, index);
                ps.setString(3, templateUUID.toString());
            }
        };
        return pss;
    }

    private void validateIfRoot(UUID templateUUID, String operation) throws DaoException {
        String selectQuery = "select PARENT_UUID from xml_form_tmpl WHERE TMPL_UUID = ?";
        Map<String, Object> row = getJdbcTemplate().queryForMap(selectQuery, templateUUID.toString());
        String parentUUIDString = (String) row.get("PARENT_UUID");
        if (parentUUIDString == null) {
            throw new DaoException("Can't " + operation + " root");
        }
    }

    @Override
    public List<TemplateDetailsDTO> browseTemplatesForDirectory(UUID templateUUID) throws SQLException, DaoException {
        try {
            String query = "select TMPL_UUID, VIS_ID, DESCRIPTION, VERSION_NUM, JSON_FORM, PARENT_UUID, TYPE, DEFINITION, CHILD_INDEX from xml_form_tmpl where PARENT_UUID = ? and TYPE <> 0";
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, templateUUID.toString());
            List<TemplateDetailsDTO> templateList = TemplateMapper.mapXmlFormTemplates(rows);
            return templateList;
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    };

}
