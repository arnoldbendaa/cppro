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
package com.softproideas.app.admin.structuredisplayname.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softproideas.app.admin.hierarchies.model.HierarchyNodeLazyDTO;
import com.softproideas.app.admin.structuredisplayname.dao.StructureDisplayNameDao;
import com.softproideas.app.admin.structuredisplayname.model.StructureDisplayNameData;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;

/**
 * <p>This service is aimed to take care of download and save Display Name.</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Service("structureDisplayNameService")
public class StructureDisplayNameServiceImpl implements StructureDisplayNameService {
    private static Logger logger = LoggerFactory.getLogger(StructureDisplayNameServiceImpl.class);

    @Autowired
    StructureDisplayNameDao structureDisplayNameDao;

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.structuredisplayname.service.StructureDisplayNameService#browseCurrencies(int, int) */
    @Override
    public List<HierarchyNodeLazyDTO> includeDisplayNames(List<HierarchyNodeLazyDTO> structureElementList, int hierarchyId, int parenId) throws ServiceException {
        try {
            List<StructureDisplayNameData> structureDisplayNameWithIdList = structureDisplayNameDao.fetchDisplayNameData(hierarchyId, parenId);
            for (HierarchyNodeLazyDTO structureElement: structureElementList) {
                for (StructureDisplayNameData displayNameWithId: structureDisplayNameWithIdList) {
                    if (structureElement.getId().startsWith("hierarchyElement") || structureElement.getId().startsWith("dimensionElement")) {
                        String[] hierarchyParam = structureElement.getId().split("/");
                        Integer hierarchyElementId = Integer.parseInt(hierarchyParam[1]);
                        if (hierarchyElementId == displayNameWithId.getStructureElementId()) {
                            structureElement.setDisplayName(displayNameWithId.getStructureElementDisplayName());
                        }
                    }
                }
            }
            return structureElementList;
        } catch (DaoException e) {
            logger.error(e.getMessage());
            throw new ServiceException(e.getMessage());
        }
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.structuredisplayname.service.StructureDisplayNameService#save(com.softproideas.app.admin.structuredisplayname.model.StructureDisplayNameWithId) */
    @Override
    public ResponseMessage saveDisplayNames(List<StructureDisplayNameData> displayName) {
        ResponseMessage responseMessage = structureDisplayNameDao.saveDisplayNames(displayName);
        return responseMessage;
    }

}
