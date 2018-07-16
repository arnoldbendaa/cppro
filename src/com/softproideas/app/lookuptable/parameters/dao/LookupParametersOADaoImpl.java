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

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.softproideas.app.lookuptable.parameters.mapper.LookupParametersOAMapper;
import com.softproideas.app.lookuptable.parameters.model.LookupDimensionOADTO;
import com.softproideas.common.exceptions.DaoException;

@Repository("lookupParametersOADao")
public class LookupParametersOADaoImpl extends JdbcDaoSupport implements LookupParametersOADao {

    @Autowired
    @Qualifier("oaDataSource")
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }
    
    @Override
    public List<LookupDimensionOADTO> browseDimensions(int company) throws DaoException {
        String query = "  select\n" +
                "   oa_hisetu.\"hier-id\" HIER_ID " +
                "   ,replace(oa_anlysis.anltyp,':',' ')||':'||\n" +
                "   oa_hierarchy.anlcode PARENT\n" +
                "  ,replace(oa_anlysis.anltyp,':',' ')||':'||\n" +
                "   oa_hierarchy.anlcode ELEM\n" +
                "  ,replace(oa_anlysis.anltyp,':',' ')||':'||\n" +
                "   oa_hierarchy.anlcode||' - '||oa_anlysis.description DESCR\n" +
                "  ,' ' IS_LEAF\n" +
                "  ,-1 STATUS\n " +
                "  from\n" +
                "   pub.oa_hierarchy\n" +
                "  ,pub.oa_anlysis\n" +
                "  ,pub.oa_hisetu\n" +
                "  where\n" +
                "      oa_hierarchy.company    = " + company + "\n" +
                "  and oa_hierarchy.del        <> 1\n" +
                "  and oa_hisetu.company       = oa_hierarchy.company\n" +
                "  and oa_hisetu.\"hier-id\"     = oa_hierarchy.\"hier-id\" \n" +
                "  and oa_hisetu.htype          = 'N1'\n" +
                "  and oa_anlysis.company      = oa_hierarchy.company\n" +
                "  and (\n" +
                "          (oa_hierarchy.hlev = 1 and oa_anlysis.anltyp = pro_element(hilevs,1,1) )\n" +
                "      or  (oa_hierarchy.hlev = 2 and oa_anlysis.anltyp = pro_element(hilevs,2,2) )\n" +
                "      or  (oa_hierarchy.hlev = 3 and oa_anlysis.anltyp = pro_element(hilevs,3,3) )\n" +
                "      or  (oa_hierarchy.hlev = 4 and oa_anlysis.anltyp = pro_element(hilevs,4,4) )\n" +
                "      or  (oa_hierarchy.hlev = 5 and oa_anlysis.anltyp = pro_element(hilevs,5,5) )\n" +
                "     )\n" +
                "  and oa_anlysis.anlcode = oa_hierarchy.anlcode\n" +
                "union all\n" +
                "  select\n" +
                "   oa_hisetu.\"hier-id\" HIER_ID " +
                "   ,replace(oa_gladetl.anltyp,':',' ')||':'||oa_gladetl.anlcode PARENT\n" +
                "  ,oa_gladetl.glcode ELEM\n" +
                "  ,oa_gladetl.glcode||' - '||oa_costcentres.name DESCR\n" +
                "  ,'Y' IS_LEAF\n" +
                "  ,cast(oa_costcentres.accsus as int) STATUS\n" +
                "  from\n" +
                "   pub.oa_gladetl\n" +
                "  ,pub.oa_costcentres\n" +
                "  ,pub.oa_hisetu\n" +
                "  where\n" +
                "      oa_gladetl.company   = " + company + "\n" +
                "  and oa_gladetl.rectyp    = 'CO'\n" +
                "  and oa_hisetu.company    = oa_gladetl.company\n" +
                "  and oa_hisetu.htype          = 'N1'\n" +
                "  and oa_gladetl.anltyp    = pro_element(hilevs,1,1) \n" +
                "  and oa_costcentres.company     = oa_gladetl.company\n" +
                "  and oa_costcentres.costcentre     <> ' '\n" +
                "  and oa_costcentres.del         <> 1\n" +
                "  and oa_costcentres.costcentre     = oa_gladetl.glcode\n" +
                "  and oa_gladetl.anlcode\n" +
                "      in (select anlcode from pub.oa_hierarchy\n" +
                "          where oa_hierarchy.company    = oa_hisetu.company\n" +
                "            and oa_hierarchy.\"hier-id\"  = oa_hisetu.\"hier-id\" \n" +
                "            and hlev = 1)\n" +
                "  order by 1, 2, 5, 3";
        
        try {
            List<Map<String, Object>> rows = getJdbcTemplate().queryForList(query);
            return LookupParametersOAMapper.mapDimensions(rows);
        } catch (Exception e) {
            String errorMsg = "Error while executing select query";
            logger.error(errorMsg, e);
            throw new DaoException(errorMsg, e);
        }
    }
    

}
