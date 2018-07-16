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
package com.softproideas.app.admin.systemproperties.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.systemproperty.AllSystemPropertysELO;
import com.cedar.cp.dto.systemproperty.SystemPropertyEditorSessionCSO;
import com.cedar.cp.dto.systemproperty.SystemPropertyEditorSessionSSO;
import com.cedar.cp.dto.systemproperty.SystemPropertyImpl;
import com.cedar.cp.dto.systemproperty.SystemPropertyPK;
import com.cedar.cp.ejb.api.systemproperty.SystemPropertyEditorSessionServer;
import com.cedar.cp.ejb.impl.systemproperty.SystemPropertyEditorSessionSEJB;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;
import com.softproideas.app.admin.systemproperties.mapper.SystemPropertiesMapper;
import com.softproideas.app.admin.systemproperties.model.SystemPropertyDTO;
import com.softproideas.app.admin.systemproperties.model.SystemPropertyDetailsDTO;

@Service("systemPropertiesService")
public class SystemPropertiesServiceImpl implements SystemPropertiesService {
    private static Logger logger = LoggerFactory.getLogger(SystemPropertiesServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    SystemPropertyEditorSessionSEJB server = new SystemPropertyEditorSessionSEJB();
    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.systemproperties.services.SystemPropertiesService#browseSystemProperties()
     */
    @Override
    public List<SystemPropertyDTO> browseSystemProperties() throws ServiceException {
        AllSystemPropertysELO allSystemPropertysELO = cpContextHolder.getListSessionServer().getAllSystemPropertys();
        return SystemPropertiesMapper.mapAllSystemPropertysELO(allSystemPropertysELO);
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.systemproperties.services.SystemPropertiesService#saveSystemPropertyDetails(int, com.softproideas.app.admin.systemproperties.model.SystemPropertyDetailsDTO)
     */
    @Override
    public ResponseMessage saveSystemPropertyDetails(int systemPropertyId, SystemPropertyDetailsDTO systemPropertyDetailsDTO) throws ServiceException {
        SystemPropertyImpl systemPropertyImpl = getItemData(systemPropertyId);
        systemPropertyImpl = SystemPropertiesMapper.mapSystemPropertyDetailsDTO(systemPropertyImpl, systemPropertyDetailsDTO);
        ValidationError error = validate(systemPropertyImpl);
        if (error != null) {
            return error;
        }
        return update(systemPropertyImpl);
    }

    /**
     * Validation method for System Property's details.
     */
    private ValidationError validate(SystemPropertyImpl systemPropertyImpl) {
        if (!systemPropertyImpl.getValue().matches(systemPropertyImpl.getValidateExp())) {
            return new ValidationError(systemPropertyImpl.getValidateTxt());
        }
        return null;
    }

    /**
     * Save details for selected System Property at database.
     */
    private ResponseMessage update(SystemPropertyImpl systemPropertyImpl) throws ServiceException {
        try {
//            SystemPropertyEditorSessionServer server = cpContextHolder.getSystemPropertyEditorSessionServer();
            server.update(new SystemPropertyEditorSessionCSO(cpContextHolder.getUserId(), systemPropertyImpl));
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (CPException e) {
            logger.error("Error during update System Property with key =" + systemPropertyImpl.getPrimaryKey() + "!");
            throw new ServiceException("Error during update System Property with key =" + systemPropertyImpl.getPrimaryKey() + "!");
        } catch (ValidationException e) {
            logger.error("Validation error during update System Property with key =" + systemPropertyImpl.getPrimaryKey() + "!");
            throw new ServiceException("Validation error during update System Property with key =" + systemPropertyImpl.getPrimaryKey() + "!");
        }
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.systemproperties.services.SystemPropertiesService#fetchSystemPropertyDetails(int)
     */
    @Override
    public SystemPropertyDetailsDTO fetchSystemPropertyDetails(int systemPropertyId) throws ServiceException {
        SystemPropertyImpl systemPropertyImpl = getItemData(systemPropertyId);
        SystemPropertyDetailsDTO systemPropertyDetailsDTO = SystemPropertiesMapper.mapSystemPropertyDetails(systemPropertyImpl);
        return systemPropertyDetailsDTO;
    }

    /**
     * Get details for selected System Property from the database.
     */
    private SystemPropertyImpl getItemData(int systemPropertyId) throws ServiceException {
        try {
//            SystemPropertyEditorSessionServer server = cpContextHolder.getSystemPropertyEditorSessionServer();
            SystemPropertyPK systemPropertyPK = new SystemPropertyPK(systemPropertyId);
            SystemPropertyEditorSessionSSO systemPropertyEditorSessionSSO;
            systemPropertyEditorSessionSSO = server.getItemData(cpContextHolder.getUserId(),systemPropertyPK);
            SystemPropertyImpl systemPropertyImpl = systemPropertyEditorSessionSSO.getEditorData();
            return systemPropertyImpl;
        } catch (CPException e) {
            logger.error("Error during get System Property!", e);
            throw new ServiceException("Error during get System Property!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during get System Property!", e);
            throw new ServiceException("Validation error during get System Property!", e);
        }
    }

}