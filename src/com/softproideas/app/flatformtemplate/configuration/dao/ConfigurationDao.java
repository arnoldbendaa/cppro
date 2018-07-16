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
package com.softproideas.app.flatformtemplate.configuration.dao;

import java.util.UUID;

import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationDetailsDTO;
import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationTreeDTO;
import com.softproideas.app.flatformtemplate.configuration.model.MoveEvent;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public interface ConfigurationDao {

    /**
     * Get tree Configurations and its directories.
     */
    ConfigurationTreeDTO browseConfigurations(Boolean disableDirectories) throws ServiceException, DaoException;

    /**
     * Get details of Configuration.
     */
    ConfigurationDetailsDTO fetchConfiguration(UUID configurationUUID) throws ServiceException, DaoException;

    /**
     * Insert Configuration.
     */
    ConfigurationTreeDTO insertConfiguration(ConfigurationTreeDTO template) throws DaoException;

    /**
     * Update details for selected Configuration.
     */
    ResponseMessage updateConfiguration(ConfigurationDetailsDTO template) throws DaoException;

    /**
     * Rename Configuration or Configuration's directory.
     */
    ResponseMessage updateConfigurationName(UUID configurationUUID, String configurationName, int versionNum) throws DaoException;

    /**
     * Delete selected Configuration or Configuration's directory.
     */
    ResponseMessage deleteConfiguration(UUID configurationUUID) throws DaoException;

    ResponseMessage updateConfigurationIndex(MoveEvent moveEvent) throws DaoException;

}
