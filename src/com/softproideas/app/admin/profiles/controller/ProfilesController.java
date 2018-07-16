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
package com.softproideas.app.admin.profiles.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.forms.flatforms.model.FormUndeploymentDataDTO;
import com.softproideas.app.admin.profiles.model.ProfileDTO;
import com.softproideas.app.admin.profiles.model.ProfileDetailsDTO;
import com.softproideas.app.admin.profiles.service.ProfilesService;
import com.softproideas.common.models.FormDeploymentDataDTO;
import com.softproideas.commons.model.ResponseMessage;

/**
 * @author Mariusz Łukasik
 * @email mariusz.lukasik@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@RequestMapping(value = "/profiles")
@Controller
public class ProfilesController {
    private static Logger logger = LoggerFactory.getLogger(ProfilesController.class);

    @Autowired
    private ProfilesService profilesService;

    /**
     * Get mobile profiles from database.
     */
    @ResponseBody
    @RequestMapping(value = "/mobile", method = RequestMethod.GET)
    public List<ProfileDTO> browseMobileProfiles() throws Exception {
        try {
            List<ProfileDTO> mobileProfiles = profilesService.browseMobileProfiles();
            return mobileProfiles;
        } catch (Exception e) {
            logger.error("Error during fetching profiles!", e);
            throw new Exception("Error during fetching profiles!", e);
        }
    }

    /**
     * Get web profiles from database.
     */
    @ResponseBody
    @RequestMapping(value = "/web", method = RequestMethod.GET)
    public List<ProfileDTO> browseWebProfiles() throws Exception {
        try {
            List<ProfileDTO> webProfiles = profilesService.browseWebProfiles();
            return webProfiles;
        } catch (Exception e) {
            logger.error("Error during fetching profiles!", e);
            throw new Exception("Error during fetching profiles!", e);
        }
    }

    /**
     * Get mobile profiles from database.
     */
    @ResponseBody
    @RequestMapping(value = "/mobile/{page}/{offset}", method = RequestMethod.GET)
    public List<ProfileDTO> browsePageMobileProfiles(@PathVariable int page, @PathVariable int offset) throws Exception {
        try {
            List<ProfileDTO> mobileProfiles = profilesService.browsePageMobileProfiles(page, offset);
            return mobileProfiles;
        } catch (Exception e) {
            logger.error("Error during fetching profiles!", e);
            throw new Exception("Error during fetching profiles!", e);
        }
    }

    /**
     * Get web profiles from database.
     */
    @ResponseBody
    @RequestMapping(value = "/web/{page}/{offset}", method = RequestMethod.GET)
    public List<ProfileDTO> browsePageWebProfiles(@PathVariable int page, @PathVariable int offset) throws Exception {
        try {
            List<ProfileDTO> webProfiles = profilesService.browsePageWebProfiles(page, offset);
            return webProfiles;
        } catch (Exception e) {
            logger.error("Error during fetching profiles!", e);
            throw new Exception("Error during fetching profiles!", e);
        }
    }

    // @ResponseBody
    // @RequestMapping(value = "/mobileProfiles", method = RequestMethod.GET)
    // public List<ProfilesDTO> browseProfiles(@RequestParam(value = "userId", required = true) int userId) throws Exception{
    // try {
    // List<ProfilesDTO> returned = profilesService.browseMobileProfiles(userId);
    // return returned;
    // } catch (Exception e) {
    // logger.error("Error during fetching mobile profiles for user!", e);
    // throw new Exception("Error during fetching mobile profiles for user!", e);
    // }
    // }
    //
    @ResponseBody
    @RequestMapping(value = "/userId/{userId}/profile/{profileId}", method = RequestMethod.DELETE)
    public void deleteProfiles(@PathVariable int userId, @PathVariable("profileId") int profileId) throws Exception {
        try {
            profilesService.deleteProfile(userId, profileId);
        } catch (Exception e) {
            logger.error("Error during deleting mobile profiles for user!", e);
            throw new Exception("Error during deleting mobile profiles for user!", e);
        }
    }

    
    @ResponseBody
    @RequestMapping(value = "/profile/{profileId}", method = RequestMethod.GET)
    public ProfileDetailsDTO getProfileDetails(@PathVariable("profileId") int profileId) throws Exception {
        try {
            return profilesService.getProfileDetails(profileId);
        } catch (Exception e) {
            logger.error("Error during browse mobile profile!", e);
            throw new Exception("Error during browse mobile profile!", e);
        }
    }

    
    /**
     * Deploy Flat Form.
     */
    @ResponseBody
    @RequestMapping(value = "/deploy", method = RequestMethod.POST)
    public ResponseMessage deployFlatForm(@RequestBody FormDeploymentDataDTO formDeploymentDataDTO) throws Exception {
        return profilesService.deployFlatForm(formDeploymentDataDTO);
    }

    /**
     * Update Profile.
     */
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseMessage updateProfile(@RequestBody FormDeploymentDataDTO formDeploymentDataDTO) throws Exception {
        return profilesService.updateProfile(formDeploymentDataDTO);
    }
    
    /**
     * Undeploy Flat Form.
     */
    @ResponseBody
    @RequestMapping(value = "/undeploy", method = RequestMethod.POST)
    public ResponseMessage undeployFlatForm(@RequestBody FormUndeploymentDataDTO flatFormUndeployment) throws Exception {
        return profilesService.undeployFlatForm(flatFormUndeployment);
    }

}
