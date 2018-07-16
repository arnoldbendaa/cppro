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
package com.softproideas.app.admin.formdashboard.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.softproideas.app.admin.formdashboard.mapper.FormDashboardMapper;
import com.softproideas.app.lookuptable.parameters.mapper.LookupParametersMapper;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.models.FormDashboardDTO;
import com.softproideas.commons.context.CPContextHolder;

@Repository("formDashboardDao")
public class FormDashboardDAOImpl extends JdbcDaoSupport implements FormDashboardDAO {

	@Autowired
	@Qualifier("cpDataSource")
	DataSource dataSource;

	@PostConstruct
	private void initialize() {
		setDataSource(dataSource);
	}

	@Autowired
	CPContextHolder cpContextHolder;

	@Override
	public List<FormDashboardDTO> browseFreeForms() throws DaoException {
		// @formatter:off
		String query = "select "
				+ "d.DASHBOARD_UUID, d.DASHBOARD_TITLE, d.UPDATED_TIME, d.FORM_ID, d.MODEL_ID, f.VIS_ID "
				+ "from DASHBOARD d " + "join XML_FORM f on (d.FORM_ID=f.XML_FORM_ID) "
				+ "where d.DASHBOARD_TYPE = 'FREEFORM'";
		// @formatter:on
		try {
			List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
			List<Integer> filterXmlForms = browseAllowXmlFormIdsForUser(cpContextHolder.getUserId());
			List<Integer> filterModelsIds = browseAllowModelId(cpContextHolder.getUserId());
			return FormDashboardMapper.mapForms(rows, filterXmlForms, filterModelsIds,
					cpContextHolder.isSystemAdministrator());
		} catch (Exception e) {
			String errorMsg = "Error while executing select query";
			logger.error(errorMsg, e);
			throw new DaoException(errorMsg, e);
		}
	}

	@Override
	public List<FormDashboardDTO> browseHierarchyForms() throws DaoException {
		// @formatter:off
		String query = "select "
				+ "d.DASHBOARD_UUID, d.DASHBOARD_TITLE, d.UPDATED_TIME, d.FORM_ID, d.MODEL_ID, f.VIS_ID "
				+ "from DASHBOARD d " + "join XML_FORM f on (d.FORM_ID=f.XML_FORM_ID) "
				+ "where DASHBOARD_TYPE = 'HIERARCHY'";
		// @formatter:on
		try {
			List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
			List<Integer> filterXmlForms = browseAllowXmlFormIdsForUser(cpContextHolder.getUserId());
			List<Integer> filterModelsIds = browseAllowModelId(cpContextHolder.getUserId());
			return FormDashboardMapper.mapForms(rows, filterXmlForms, filterModelsIds,
					cpContextHolder.isSystemAdministrator());
		} catch (Exception e) {
			String errorMsg = "Error while executing select query";
			logger.error(errorMsg, e);
			throw new DaoException(errorMsg, e);
		}
	}

	@Override
	public List<Integer> browseAllowXmlFormIdsForUser(Integer userId) throws DaoException {
		// @formatter:off
		String query = "select XML_FORM_ID from XML_FORM " + "MINUS "
				+ "  (select DISTINCT XML_FORM_ID from XML_FORM_USER_LINK " + "   MINUS "
				+ "   select DISTINCT XML_FORM_ID from XML_FORM_USER_LINK where USER_ID = ?)";
		// @formatter:on
		try {
			List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, userId);
			List<Integer> returned = FormDashboardMapper.mapXmlFormIdsForUser(rows);
			return returned;
		} catch (Exception e) {
			String errorMsg = "Error while executing select query";
			logger.error(errorMsg, e);
			throw new DaoException(errorMsg, e);
		}
	}

	@Override
	public List<Integer> browseAllowModelId(Integer userId) throws DaoException {
		// @formatter:off
		String query = "SELECT DISTINCT m.model_id " + "FROM   usr U " + "       left join budget_user bu "
				+ "              ON ( u.user_id = BU.user_id ) " + "       left join model m"
				+ "              ON ( BU.model_id = M.model_id )" + "WHERE  U.user_id = ?";
		// @formatter:on
		try {
			List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query, userId);
			List<Integer> returned = FormDashboardMapper.mapModelIdsForUser(rows);
			return returned;
		} catch (Exception e) {
			String errorMsg = "Error while executing select query";
			logger.error(errorMsg, e);
			throw new DaoException(errorMsg, e);
		}
	}
}
