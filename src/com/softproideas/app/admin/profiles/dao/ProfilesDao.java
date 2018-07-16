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
package com.softproideas.app.admin.profiles.dao;

import java.util.List;

import com.softproideas.app.admin.profiles.model.MobileProfileDTO;
import com.softproideas.app.admin.profiles.model.ProfileDTO;
import com.softproideas.app.admin.profiles.model.ProfileDetailsDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.common.models.FormDeploymentDataDTO;
import com.softproideas.commons.model.ResponseMessage;

public interface ProfilesDao {

    List<ProfileDTO> browseMobileProfiles() throws DaoException;
    List<ProfileDTO> browseWebProfiles() throws DaoException;
    List<ProfileDTO> browseMobileProfiles(int userId) throws DaoException;
    ProfileDetailsDTO getProfile(int profileId) throws DaoException;
    List<MobileProfileDTO> getProfilesForUser(int userId) throws DaoException;
    List<ProfileDTO> browsePageMobileProfiles(int page, int offset) throws DaoException;
    List<ProfileDTO> browsePageWebProfiles(int page, int offset) throws DaoException;
    boolean insertMobileProfilesForUser(int userId, FormDeploymentDataDTO profile) throws DaoException;
    int updateProfileSeq() throws DaoException;
    ProfileDetailsDTO getProfileDetails(int profileId) throws DaoException;
    ResponseMessage updateProfile(FormDeploymentDataDTO profile) throws DaoException;

}
