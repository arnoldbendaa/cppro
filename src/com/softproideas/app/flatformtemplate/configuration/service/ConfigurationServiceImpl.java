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
package com.softproideas.app.flatformtemplate.configuration.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.softproideas.app.flatformtemplate.configuration.dao.ConfigurationDao;
import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationDetailsDTO;
import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationTreeDTO;
import com.softproideas.app.flatformtemplate.configuration.model.MoveEvent;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;

@Service("configurationService")
public class ConfigurationServiceImpl implements ConfigurationService {
    
    @Autowired
    ConfigurationDao configurationDao;

    /**
     * Get tree Configurations and its directories.
     */
    @Override
    public ConfigurationTreeDTO browseConfigurations(Boolean disableDirectories) throws ServiceException {
        try {
            return configurationDao.browseConfigurations(disableDirectories);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Get details of Configuration.
     */
    @Override
    public ConfigurationDetailsDTO fetchConfiguration(UUID configurationUUID) throws ServiceException {
        try {
            return configurationDao.fetchConfiguration(configurationUUID);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Insert Configuration.
     */
    @Override
    public ConfigurationTreeDTO insertConfiguration(ConfigurationTreeDTO configuration) throws ServiceException {
        try {
            ConfigurationTreeDTO returnedconfiguration = configurationDao.insertConfiguration(configuration);
            return returnedconfiguration;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Update details for selected Configuration.
     */
    @Transactional(rollbackFor=ServiceException.class, propagation=Propagation.REQUIRED)
    @Override
    public ResponseMessage updateConfiguration(ConfigurationDetailsDTO configuration) throws ServiceException {
        ResponseMessage responseMessage;
        try {
            responseMessage = configurationDao.updateConfiguration(configuration);
            return responseMessage;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    
    @Transactional(rollbackFor = ServiceException.class, propagation = Propagation.REQUIRED)
    @Override
    public ResponseMessage updateConfigurationIndex(final MoveEvent moveEvent) throws ServiceException {
        ResponseMessage responseMessage;
        try {
            responseMessage = configurationDao.updateConfigurationIndex(moveEvent);
            return responseMessage;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }
    /**
     * Rename Configuration or Configuration's directory.
     */
    @Override
    public ResponseMessage updateConfigurationName(UUID configurationUUID, String configurationName, int versionNum) throws ServiceException {
        ResponseMessage responseMessage;
        try {
            responseMessage = configurationDao.updateConfigurationName(configurationUUID, configurationName, versionNum);
            return responseMessage;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

    /**
     * Delete selected Configuration or Configuration's directory.
     */
    @Override
    public ResponseMessage deleteConfiguration(UUID configurationUUID) throws ServiceException {
        ResponseMessage responseMessage;
        try {
            responseMessage = configurationDao.deleteConfiguration(configurationUUID);
            return responseMessage;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
