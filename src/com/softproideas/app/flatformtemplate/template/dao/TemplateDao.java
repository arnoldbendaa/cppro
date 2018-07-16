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

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import com.cedar.cp.dto.user.AllUsersELO;
import com.softproideas.app.flatformtemplate.template.model.MoveEvent;
import com.softproideas.app.flatformtemplate.template.model.TemplateDetailsDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;

public interface TemplateDao {

    TemplateDetailsDTO browseTemplates(Boolean disableDirectories) throws ServiceException, SQLException, DaoException;

    TemplateDetailsDTO fetchTemplate(UUID templateUUID, AllUsersELO allUsersELO) throws ServiceException, SQLException, DaoException;

    TemplateDetailsDTO insertTemplate(TemplateDetailsDTO template) throws DaoException;

    ResponseMessage updateTemplate(TemplateDetailsDTO template, Boolean fullUpdate) throws DaoException;

    ResponseMessage deleteTemplate(UUID templateUUID) throws DaoException;

    List<TemplateDetailsDTO> browseTemplatesForDirectory(UUID templateUUID) throws SQLException, DaoException;

    /**
     * This function update index when element is moved on tree.
     * @param template
     * @return
     * @throws DaoException
     */
    ResponseMessage updateTemplateIndex(MoveEvent event) throws DaoException;

}