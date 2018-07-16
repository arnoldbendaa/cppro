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
package com.softproideas.app.admin.systemproperties.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.systemproperty.SystemProperty;
import com.cedar.cp.dto.systemproperty.AllSystemPropertysELO;
import com.cedar.cp.dto.systemproperty.SystemPropertyImpl;
import com.cedar.cp.dto.systemproperty.SystemPropertyPK;
import com.cedar.cp.dto.systemproperty.SystemPropertyRefImpl;
import com.softproideas.app.admin.systemproperties.model.*;

public class SystemPropertiesMapper {

    /**
     * Maps list of all available System Properties of list of transfer data object related to System Properties.
     */
    @SuppressWarnings("unchecked")
    public static List<SystemPropertyDTO> mapAllSystemPropertysELO(AllSystemPropertysELO elo) {
        List<SystemPropertyDTO> systemPropertiesList = new ArrayList<SystemPropertyDTO>();

        for (Iterator<AllSystemPropertysELO> it = elo.iterator(); it.hasNext();) {
            AllSystemPropertysELO row = it.next();

            SystemPropertyDTO systemPropertyDTO = new SystemPropertyDTO();

            SystemPropertyRefImpl property = (SystemPropertyRefImpl) row.getSystemPropertyEntityRef();
            systemPropertyDTO.setSystemPropertyId(property.getSystemPropertyPK().getSystemPropertyId());
            systemPropertyDTO.setProperty(property.getNarrative());

            String value = row.getValue();
            systemPropertyDTO.setValue(value);

            String description = row.getDescription();
            systemPropertyDTO.setDescription(description);

            systemPropertiesList.add(systemPropertyDTO);
        }
        return systemPropertiesList;
    }

    public static SystemPropertyDetailsDTO mapSystemPropertyDetails(SystemProperty systemProperty) {
        SystemPropertyDetailsDTO systemPropertyDTO = new SystemPropertyDetailsDTO();

        systemPropertyDTO.setSystemPropertyId(((SystemPropertyPK) (systemProperty.getPrimaryKey())).getSystemPropertyId());
        systemPropertyDTO.setProperty(systemProperty.getProperty());
        systemPropertyDTO.setValue(systemProperty.getValue());
        systemPropertyDTO.setDescription(systemProperty.getDescription());
        systemPropertyDTO.setValidateExp(systemProperty.getValidateExp());
        systemPropertyDTO.setValidateTxt(systemProperty.getValidateTxt());
        systemPropertyDTO.setVersionNum(((SystemPropertyImpl) systemProperty).getVersionNum());

        return systemPropertyDTO;
    }

    public static SystemPropertyImpl mapSystemPropertyDetailsDTO(SystemPropertyImpl systemPropertyImpl, SystemPropertyDetailsDTO systemPropertyDetailsDTO) {
        systemPropertyImpl.setValue(systemPropertyDetailsDTO.getValue());
        systemPropertyImpl.setVersionNum(systemPropertyDetailsDTO.getVersionNum());

        return systemPropertyImpl;
    }
}
