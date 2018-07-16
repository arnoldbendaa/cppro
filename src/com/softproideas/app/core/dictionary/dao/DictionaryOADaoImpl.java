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

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.softproideas.app.core.dictionary.mapper.DictionaryOAMapper;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.models.DictionaryDTO;

@Repository("dictionaryOADao")
public class DictionaryOADaoImpl extends JdbcDaoSupport implements DictionaryOADao {
    
    @Autowired
    @Qualifier("oaDataSource")
    DataSource dataSource;
    
    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
    
    @Override
    public List<DictionaryDTO> browseCompanies() throws DaoException {
        String query = "select C1.KCO  as COMPANY, C1.NAME as DESCRIPTION, C2.basecurr as PROPERTIES from PUB.MNCOMPANY C1, PUB.OA_COMPANIES C2 where C1.KCO = C2.COMPANY and C1.DEL <> 1 and C2.DEL <> 1 and C2.MAXPER <> 0 order by C1.KCO";
        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
            return DictionaryOAMapper.mapCompanies(rows);
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }

}
