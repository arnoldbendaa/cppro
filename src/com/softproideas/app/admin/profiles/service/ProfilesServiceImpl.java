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
package com.softproideas.app.admin.profiles.service;

import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.user.DataEntryProfileCK;
import com.cedar.cp.dto.user.DataEntryProfilePK;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.xmlform.FormDeploymentDataImpl;
import com.cedar.cp.dto.xmlform.FormDeploymentTaskRequest;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.ejb.api.user.DataEntryProfileEditorSessionServer;
import com.cedar.cp.ejb.api.xmlform.XmlFormEditorSessionServer;
import com.cedar.cp.ejb.impl.user.DataEntryProfileEditorSessionSEJB;
import com.cedar.cp.util.task.TaskMessageFactory;
import com.softproideas.app.admin.forms.flatforms.mapper.FormDeploymentMapper;
import com.softproideas.app.admin.forms.flatforms.model.FormUndeploymentDataDTO;
import com.softproideas.app.admin.forms.flatforms.util.FormDeploymentValidator;
import com.softproideas.app.admin.profiles.dao.ProfilesDao;
import com.softproideas.app.admin.profiles.model.MobileProfileDTO;
import com.softproideas.app.admin.profiles.model.ProfileDTO;
import com.softproideas.app.admin.profiles.model.ProfileDetailsDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.common.models.FormDeploymentDataDTO;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

@Service("profilesService")
public class ProfilesServiceImpl implements ProfilesService {

    @Autowired
    ProfilesDao profilesDao;
    @Autowired
    CPContextHolder cpContextHolder;

    DataEntryProfileEditorSessionSEJB server = new DataEntryProfileEditorSessionSEJB();
    private static Logger logger = LoggerFactory.getLogger(ProfilesServiceImpl.class);

    @Override
    public List<ProfileDTO> browseMobileProfiles() throws ServiceException {
        try {
            List<ProfileDTO> mobileProfiles = profilesDao.browseMobileProfiles();
            return mobileProfiles;
        } catch (Exception e) {
            logger.error("Error while fetching profiles! ", e);
            throw new ServiceException("Error while fetching profiles!", e);
        }
    }

    @Override
    public List<ProfileDTO> browseWebProfiles() throws ServiceException {
        try {
            List<ProfileDTO> webProfiles = profilesDao.browseWebProfiles();
            return webProfiles;
        } catch (Exception e) {
            logger.error("Error while fetching profiles! ", e);
            throw new ServiceException("Error while fetching profiles!", e);
        }
    }

    @Override
    public List<ProfileDTO> browsePageMobileProfiles(int page, int offset) throws ServiceException {
        try {
            List<ProfileDTO> mobileProfiles = profilesDao.browsePageMobileProfiles(page, offset);
            return mobileProfiles;
        } catch (Exception e) {
            logger.error("Error during fetching profiles! ", e);
            throw new ServiceException("Error during fetching profiles!", e);
        }
    }

    @Override
    public List<ProfileDTO> browsePageWebProfiles(int page, int offset) throws ServiceException {
        try {
            List<ProfileDTO> webProfiles = profilesDao.browsePageWebProfiles(page, offset);
            return webProfiles;
        } catch (Exception e) {
            logger.error("Error during fetching profiles! ", e);
            throw new ServiceException("Error during fetching profiles!", e);
        }
    }

    @Override
    public List<ProfileDTO> browseMobileProfiles(int userId) throws ServiceException {
        try {
            List<ProfileDTO> mobileProfiles = profilesDao.browseMobileProfiles(userId);
            return mobileProfiles;
        } catch (Exception e) {
            logger.error("Error while fetching mobile profiles for user!", e);
            throw new ServiceException("Error while fetching mobile profiles for user!", e);
        }

    }

    @Override
    public boolean deleteProfile(int userId, int profileId) throws ServiceException {

        try {
//            DataEntryProfileEditorSessionServer server = cpContextHolder.getDataEntryProfileEditorSessionServer();

            UserPK userPrimaryKey = new UserPK(userId);
            DataEntryProfilePK dataEntryPrimaryKey = new DataEntryProfilePK(profileId);
            DataEntryProfileCK dataEntryProfileCK = new DataEntryProfileCK(userPrimaryKey, dataEntryPrimaryKey);

            server.delete(userId,dataEntryProfileCK);
            return true;
        } catch (Exception e) {
            logger.error("Error while deleting mobile profiles for user!", e);
            throw new ServiceException("Error while deleting mobile profiles for user!", e);
        }

    }

    @Override
    public ProfileDetailsDTO getProfile(int profileId) throws ServiceException {
        try {
            ProfileDetailsDTO profile = profilesDao.getProfile(profileId);
            return profile;
        } catch (Exception e) {
            logger.error("Error while fetching profile!", e);
            throw new ServiceException("Error while fetching profile for you!", e);
        }

    }

    @Override
    public List<MobileProfileDTO> browseMobileProfilesForUser(int userId) throws ServiceException {
        try {
            List<MobileProfileDTO> mobileProfiles = profilesDao.getProfilesForUser(userId);
            return mobileProfiles;
        } catch (Exception e) {
            logger.error("Error while fetching mobile profiles for user!", e);
            throw new ServiceException("Error while fetching mobile profiles for user!", e);
        }
    }
    
    @Override
    public ResponseMessage deployFlatForm(FormDeploymentDataDTO formDeploymentDataDTO) throws NamingException, Exception {
        ValidationError error = FormDeploymentValidator.validateFormDeployment(formDeploymentDataDTO);
        if (error.getFieldErrors().isEmpty()) {
            
            try {
                //XmlFormEditorSessionServer server = cpContextHolder.getXmlFormEditorSessionServer();
                FormDeploymentTaskRequest taskReq = new FormDeploymentTaskRequest();
                taskReq.setDto(formDeploymentDataDTO);
                int taskId = TaskMessageFactory.issueNewTask(new InitialContext(), true, taskReq, cpContextHolder.getUserId());                
                return new ResponseMessage(true, "" + taskId);
            } catch (CPException e) {
                logger.error("Error during process Form Deployment!", e);
                throw new ServiceException("Error during process Form Deployment!", e);
            } catch (ValidationException e) {
                logger.error("Validation error during process Form Deployment!", e);
                throw new ServiceException("Validation error during process Form Deployment!", e);
            }
        } else {
            error.setMessage("Something WRONG during deploying forms.");
            return error;
        }
    }
    
    @Override
    public ResponseMessage undeployFlatForm(FormUndeploymentDataDTO flatFormUndeployment) throws CPException, ValidationException {
        XmlFormEditorSessionServer server = cpContextHolder.getXmlFormEditorSessionServer();
        XmlFormPK xmlFormPK = new XmlFormPK(flatFormUndeployment.getFlatFormId());
        Boolean mobile = flatFormUndeployment.getMobile();
        String subject = flatFormUndeployment.getSubject();
        String messageText = flatFormUndeployment.getMessageText();
        server.deleteFormProfiles(xmlFormPK, subject, messageText, mobile);
        ResponseMessage responseMessage = new ResponseMessage(true);
        return responseMessage;
    }

    @Override
    public boolean createMobileProfileForUser(int userId, FormDeploymentDataDTO profile) throws ServiceException {
        try {
            return profilesDao.insertMobileProfilesForUser(userId, profile);
        } catch (Exception e) {
            logger.error("Error while creating mobile profiles for user!", e);
            throw new ServiceException("Error while creating mobile profiles for user!", e);
        }
    }

    @Override
    public ProfileDetailsDTO getProfileDetails(int profileId) throws DaoException {
        
        return profilesDao.getProfileDetails(profileId);
    }

    @Override
    public ResponseMessage updateProfile(FormDeploymentDataDTO profile) throws DaoException {
        return profilesDao.updateProfile(profile);
    }

}
