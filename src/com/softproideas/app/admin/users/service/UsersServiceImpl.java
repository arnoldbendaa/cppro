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
package com.softproideas.app.admin.users.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.ImmediateChildrenELO;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.BudgetHierarchyRootNodeForModelELO;
import com.cedar.cp.dto.role.AllHiddenRolesELO;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.user.UserEditorSessionCSO;
import com.cedar.cp.dto.user.UserEditorSessionSSO;
import com.cedar.cp.dto.user.UserImpl;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.xmlform.AllXmlFormsELO;
import com.cedar.cp.ejb.api.user.UserEditorSessionServer;
import com.cedar.cp.ejb.impl.user.UserEditorSessionSEJB;
import com.softproideas.app.admin.profiles.model.ProfileDTO;
import com.softproideas.app.admin.profiles.service.ProfilesService;
import com.softproideas.app.admin.users.mapper.UsersMapper;
import com.softproideas.app.admin.users.model.UserDetailsDTO;
import com.softproideas.app.admin.users.util.UsersValidatorUtil;
import com.softproideas.app.core.flatform.model.FlatFormExtendedCoreDTO;
import com.softproideas.app.core.profile.service.ProfileService;
import com.softproideas.app.core.users.model.UserCoreDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.common.models.FormDeploymentDataDTO;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;
import com.softproideas.commons.model.tree.NodeLazyDTO;

@Service("usersService")
public class UsersServiceImpl implements UsersService {

    private static Logger logger = LoggerFactory.getLogger(UsersServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    @Autowired
    ProfilesService profilesService;

    @Autowired
    ProfileService profileService;

    UserEditorSessionSEJB server = new UserEditorSessionSEJB();
    /**
     * Returns list of available users
     */
    @Override
    public List<UserCoreDTO> browseUsers() throws ServiceException {
        AllUsersELO allUsersELO = cpContextHolder.getListSessionServer().getAllUsers();
        return UsersMapper.mapAllUsersELO(allUsersELO);
    }

    /**
     * Method save changing or create new data user details
     * @throws SQLException 
     */
    public ResponseMessage save(int userId, UserDetailsDTO user) throws ServiceException, SQLException {
        ResponseMessage message = null;
//        UserEditorSessionServer server = cpContextHolder.getUserEditorSessionServer();
        UserImpl userImpl = getItemData(userId, server);
        List<Object[]> beforeSave = userImpl.getUserAssignments();
        ValidationError error = UsersValidatorUtil.validateUserDetails(userImpl, user);
        List<FlatFormExtendedCoreDTO> oldFormList = UsersMapper.mapListObjectXml(userImpl.getUserXmlForms());
        String method;
        if (user.getUserId() != -1) {
            method = "edit";
        } else {
            method = "create";
        }
        if (error.getFieldErrors().isEmpty()) {
            userImpl = UsersMapper.mapUserImpl(userImpl, user);
            userId = saveOrUpdateUser(userImpl, server, userId);

            message = new ResponseMessage(true);
        } else {
            error.setMessage("Error during " + method + " User.");
            message = error;
        }
        List<Object[]> afterSave = userImpl.getUserAssignments();
        // create new mobile profile for user
        message = createMobileProfileForUser(user, userId);
        // delete profiles
        message = deleteMobileProfiles(user, userId);

        updateProfilesAfterUserSave(oldFormList, user.getXmlForm(), beforeSave, afterSave, userId);

        return message;
    }

    private ResponseMessage deleteMobileProfiles(UserDetailsDTO user, int userId) {
        ResponseMessage message = new ResponseMessage(true);
        try {
            List<ProfileDTO> profiles = user.getMobileProfiles();
            for (ProfileDTO p: profiles) {
                if (p.isForDeletion() == true) {
                    profilesService.deleteProfile(userId, p.getProfileId());
                }
            }
        } catch (Exception e) {
            if (message.getMessage() != null) {
                message.setMessage(message.getMessage() + ",\n" + e.getMessage());
            } else {
                message.setMessage(e.getMessage());
            }
            message.setError(true);
            e.printStackTrace();
        }
        return message;
    }

    private ResponseMessage createMobileProfileForUser(UserDetailsDTO user, int userId) {
        ResponseMessage message = new ResponseMessage(true);
        if (user.getProfilesToSave() != null) {
            try {
                List<FormDeploymentDataDTO> profilesToSave = user.getProfilesToSave();
                for (FormDeploymentDataDTO p: profilesToSave) {
                    profilesService.createMobileProfileForUser(userId, p);
                }
            } catch (Exception e) {
                if (message.getMessage() != null) {
                    message.setMessage(message.getMessage() + ",\n" + e.getMessage());
                } else {
                    message.setMessage(e.getMessage());
                }
                message.setError(true);
                e.printStackTrace();
            }
        }
        return message;
    }

    private int saveOrUpdateUser(UserImpl userImpl, UserEditorSessionSEJB server, int userId) {
    	
        if (userImpl.getPrimaryKey() == null) {
            UserPK usr = null;
            try {
                usr = server.insert(new UserEditorSessionCSO(userId, userImpl));
            } catch (CPException e) {
                e.printStackTrace();
            } catch (ValidationException e) {
                e.printStackTrace();
            }
            userId = usr.getUserId();
        } else {
            try {
                server.update(new UserEditorSessionCSO(userId, userImpl));
            } catch (CPException e) {
                e.printStackTrace();
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        }
        return userId;
    }

    private void updateProfilesAfterUserSave(List<FlatFormExtendedCoreDTO> oldFormList, List<FlatFormExtendedCoreDTO> newFormList, List<Object[]> beforeSave, List<Object[]> afterSave, int userId) throws ServiceException, SQLException {

        List<Integer> modelsIds = getModelIdsForProfiles(afterSave, beforeSave);
        List<Integer> listDeleted = getModelIdsForProfiles(beforeSave, afterSave);
        
        if (listDeleted.size() > 0) {
            List<Integer> userIds = new ArrayList<Integer>();
            userIds.add(userId);
            profileService.deleteProfiles(listDeleted, userIds); // uncomment to enable automatic delete profiles
        }

        for (Integer modelId: modelsIds) {
            profileService.createProfiles(modelId, userId);
        }
        
        List<Integer> formToSubstractList = new ArrayList<Integer>();
        List<Integer> formToAddList = new ArrayList<Integer>();

        // Create new profiles
        newFormList: for (FlatFormExtendedCoreDTO newFormRefImpl: newFormList) {
            for (FlatFormExtendedCoreDTO oldUserRefImpl: oldFormList) {
                if (newFormRefImpl.getFlatFormId() == oldUserRefImpl.getFlatFormId()) {
                    continue newFormList; // exists in old and new userlist
                }
            }
            formToAddList.add(newFormRefImpl.getFlatFormId());
        }

        // Delete old profiles
        oldFormList: for (FlatFormExtendedCoreDTO oldFormRefImpl: oldFormList) {
            for (FlatFormExtendedCoreDTO newUserRefImpl: newFormList) {
                if (newUserRefImpl.getFlatFormId() == oldFormRefImpl.getFlatFormId()) {
                    continue oldFormList; // exists in old and new userlist
                }
            }
            formToSubstractList.add(oldFormRefImpl.getFlatFormId());
        }
        if (formToAddList.size() > 0) {
            profileService.insertProfileForUser(formToAddList, userId, modelsIds);
        }
        if (formToSubstractList.size() > 0) {
            profileService.deleteProfilesForUser(formToSubstractList, userId);
        }
    }

    /**
     * Method returns user details in DTO
     */
    @Override
    public UserDetailsDTO fetchUsersDetails(int userId) throws ServiceException {
//        UserEditorSessionServer server = cpContextHolder.getUserEditorSessionServer();
        UserImpl userImpl = getItemData(userId, server);
        AllXmlFormsELO availableXmlForm = cpContextHolder.getListSessionServer().getAllXmlForms();
        AllHiddenRolesELO elo = cpContextHolder.getListSessionServer().getAllHiddenRoles();
        UserDetailsDTO usersDetailsDTO = UsersMapper.mapUserDetails(userImpl, elo, availableXmlForm);
        List<ProfileDTO> mobileProfiles = profilesService.browseMobileProfiles(userId);
        usersDetailsDTO.setMobileProfiles(mobileProfiles);
        return usersDetailsDTO;
    }

    /**
     * Method return user item data
     */
    private UserImpl getItemData(int userId, UserEditorSessionSEJB server) throws ServiceException {
        try {
            UserEditorSessionSSO userEditorSessionSSO;
            if (userId != -1) {
                UserPK userPK = new UserPK(userId);
                userEditorSessionSSO = server.getItemData(userId,userPK);
            } else {
                userEditorSessionSSO = server.getNewItemData(userId);
            }
            UserImpl userImpl = userEditorSessionSSO.getEditorData();
            return userImpl;
        } catch (CPException e) {
            logger.error("Error during get users!", e);
            throw new ServiceException("Error during get users!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during get users!", e);
            throw new ServiceException("Validation error during get users!", e);
        }
    }

    /**
     * Delete user from database.
     */
    @Override
    public ResponseMessage delete(int userId) throws ServiceException {
        UserPK userPK = new UserPK(userId);
        try {
//            cpContextHolder.getUserEditorSessionServer().delete(userPK);
        	server.delete(cpContextHolder.getUserId(),userPK);
            profileService.deleteAllProfilesForUser(userId);
            ResponseMessage responseMessage = new ResponseMessage(true);
            return responseMessage;
        } catch (ValidationException e) {
            logger.error("Validation error during dalete user with Id =" + e + "!");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error("Error during delete user  =" + e + "!");
            throw new ServiceException("Error during dalete user with Id =" + e + "!");
        }
    }

    /**
     * Get User Assignment for user
     * @param userId
     * @return
     * @throws ServiceException
     */
    // private UserImpl getUserImpl(int userId) throws ServiceException {
    // try {
    // UserEditorSessionServer server = cpContextHolder.getUserEditorSessionServer();
    // UserPK userPK = new UserPK(userId);
    // UserEditorSessionSSO userEditorSessionSSO = server.getItemData(userPK);
    // UserImpl userImpl = userEditorSessionSSO.getEditorData();
    // return userImpl;
    // } catch (CPException e) {
    // logger.error("Error during get users!", e);
    // throw new ServiceException("Error during get users!", e);
    // } catch (ValidationException e) {
    // logger.error("Validation error during get users!", e);
    // throw new ServiceException("Validation error during get users!", e);
    // }
    // }

    /**
     * Get modelRootSecurity for user
     */
    @Override
    public List<NodeLazyDTO> browseSecurityModelRoot() throws ServiceException {
        AllModelsELO modelsList = cpContextHolder.getListSessionServer().getAllModels();
        List<NodeLazyDTO> modelRoot = UsersMapper.mapAllModelsELO(modelsList);
        return modelRoot;
    }

    /**
     * Get modelsSecurity for user
     */
    @Override
    public List<NodeLazyDTO> browseSecurityModels(int userId, int modelId) throws ServiceException {
//        UserEditorSessionServer server = cpContextHolder.getUserEditorSessionServer();
        UserImpl userImpl = getItemData(userId, server);
        BudgetHierarchyRootNodeForModelELO hierarchiesforModelELO = cpContextHolder.getListSessionServer().getBudgetHierarchyRootNodeForModel(modelId);
        List<NodeLazyDTO> models = UsersMapper.mapHierarchiesForModelELO(hierarchiesforModelELO, userImpl);
        return models;
    }

    /**
     * Get structure and structureElement - Security  for user
     */
    @Override
    public List<NodeLazyDTO> browseSecurityStructureElement(int userId, int structureId, int structureElementId) throws ServiceException {
//        UserEditorSessionServer server = cpContextHolder.getUserEditorSessionServer();
        UserImpl userImpl = getItemData(userId, server);
        ImmediateChildrenELO structureElement = cpContextHolder.getListSessionServer().getImmediateChildren(structureId, structureElementId);
        List<NodeLazyDTO> element = UsersMapper.mapImmediateChildrenELO(structureElement, userImpl);
        return element;
    }

    private List<Integer> getModelIdsForProfiles(List<Object[]> longerListOfModels, List<Object[]> shorterListOfModels) {
        List<Integer> list = new ArrayList<Integer>();

        for (int i = 0; i < longerListOfModels.size(); i++) {
            if (shorterListOfModels.size() == 0) {
                for (int k = 0; k < longerListOfModels.size(); k++) {
                    list.add((Integer) longerListOfModels.get(k)[0]);
                }
            }
            for (int j = 0; j < shorterListOfModels.size(); j++) {
                if (longerListOfModels.get(i)[0].equals(shorterListOfModels.get(j)[0])) {
                    break;
                }
                if (j == shorterListOfModels.size() - 1) {
                    list.add((Integer) longerListOfModels.get(i)[0]);
                }
            }
        }
        return list;
    }

}