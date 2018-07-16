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
package com.softproideas.app.admin.formdashboard.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softproideas.app.admin.formdashboard.dao.FormDashboardDAO;
import com.softproideas.app.admin.profiles.service.ProfilesServiceImpl;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.common.models.FormDashboardDTO;

@Service("formDashboardService")
public class FormDashboardServiceImpl implements FormDashboardService {

    @Autowired
    FormDashboardDAO formDashboardDAO;
    
    private static Logger logger = LoggerFactory.getLogger(ProfilesServiceImpl.class);

    @Override
    public List<FormDashboardDTO> browseDashboardHierarchies() throws ServiceException {
        try {
            return formDashboardDAO.browseHierarchyForms();
        } catch (Exception e) {
            logger.error("Error while fetching ! ", e);
            throw new ServiceException("Error while fetching !", e);
        }
    }

    @Override
    public List<FormDashboardDTO> browseDashboardFreeForms() throws ServiceException {
        try {
            return formDashboardDAO.browseFreeForms();
        } catch (Exception e) {
            logger.error("Error while fetching ! ", e);
            throw new ServiceException("Error while fetching !", e);
        }
    }

    
}