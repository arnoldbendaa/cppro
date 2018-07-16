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
package com.softproideas.app.admin.budgetcycles.mapper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.xmlform.AllXmlFormsELO;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.softproideas.app.core.financecube.model.FinanceCubeCoreDTO;
import com.softproideas.app.core.flatform.model.FlatFormExtendedCoreDTO;

public class FormMapper {

    /**
     * Maps <code>AllXmlFormsELO</code> to list of <code>BudgetCycleFormDTO</code>s
     */
    public static List<FlatFormExtendedCoreDTO> mapAllXmlFormsELOToFormDTO(AllXmlFormsELO elo) {
        List<FlatFormExtendedCoreDTO> xmlFormsList = new ArrayList<FlatFormExtendedCoreDTO>();

        for (@SuppressWarnings("unchecked")
        Iterator<AllXmlFormsELO> it = elo.iterator(); it.hasNext();) {
            AllXmlFormsELO row = it.next();
            FlatFormExtendedCoreDTO xmlFormDTO = new FlatFormExtendedCoreDTO();

            XmlFormRef xmlFormRef = row.getXmlFormEntityRef();
            xmlFormDTO.setFlatFormId(((XmlFormPK) xmlFormRef.getPrimaryKey()).getXmlFormId());

            xmlFormDTO.setFlatFormVisId(xmlFormRef.getNarrative());
            xmlFormDTO.setFlatFormDescription(row.getDescription());

            String format = "MMM dd, yyyy";
            String dateString = mapTimestampToDateString(row.getUpdatedTime(), format);
            xmlFormDTO.setUpdateTime(dateString);
            
            FinanceCubeCoreDTO financeCubeCoreDTO = new FinanceCubeCoreDTO();
            int financeCubeId = ((FinanceCubePK) row.getFinanceCube().getPrimaryKey()).getFinanceCubeId();
            financeCubeCoreDTO.setFinanceCubeId(financeCubeId);
            financeCubeCoreDTO.setFinanceCubeVisId(row.getFinanceCube().getNarrative());
            xmlFormDTO.setFinanceCube(financeCubeCoreDTO);

            xmlFormsList.add(xmlFormDTO);
        }
        return xmlFormsList;
    }

    /**
     * Map time/date stored in time stamp to string date in specified format
     */
    private static String mapTimestampToDateString(Timestamp timestamp, String format) {
        if (timestamp != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.format(timestamp.getTime());
        }
        return null;
    }
}
