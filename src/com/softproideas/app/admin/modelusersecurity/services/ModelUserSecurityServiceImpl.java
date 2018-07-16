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
package com.softproideas.app.admin.modelusersecurity.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.dto.budgetlocation.BudgetLocationEditorSessionSSO;
import com.cedar.cp.dto.budgetlocation.BudgetLocationImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.api.base.ListSessionServer;
import com.cedar.cp.ejb.api.budgetlocation.BudgetLocationEditorSessionServer;
import com.softproideas.app.admin.modelusersecurity.mapper.ModelUserSecurityMapper;
import com.softproideas.app.admin.modelusersecurity.model.ModelUserSecurityDTO;
import com.softproideas.app.admin.modelusersecurity.model.ModelUserSecurityDetailsDTO;
import com.softproideas.app.admin.modelusersecurity.model.ModelUserSecuritySaveData;
import com.softproideas.app.core.profile.dao.ProfileDao;
import com.softproideas.app.core.profile.service.ProfileService;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;
import com.softproideas.commons.model.tree.NodeStaticWithIdAndDescriptionDTO;

@Service("modelUserSecurityService")
public class ModelUserSecurityServiceImpl implements ModelUserSecurityService {

    private static Logger logger = LoggerFactory.getLogger(ModelUserSecurityServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;
    
    @Autowired
    ProfileDao profileDao;
    
    @Autowired
    ProfileService profileService;   
    
    /**
     * Get responsibility list of all Models.
     */
    @Override
    public List<ModelUserSecurityDTO> browseModelsUserSecurity() {
        List<ModelUserSecurityDTO> listModelUserSecurity = new ArrayList<ModelUserSecurityDTO>();
        EntityList list = cpContextHolder.getListHelper().getModelUserSecurity();
        listModelUserSecurity = ModelUserSecurityMapper.mapModelUserSecurity(list);
        return listModelUserSecurity;
    }

    /**
     * Get responsibility details for selected Model from getItemData(int modelId).
     */
    @Override
    public ModelUserSecurityDetailsDTO fetchModelUserSecurity(int modelId) throws ServiceException {
        ModelUserSecurityDetailsDTO modelUserSecurityDetailsDTO;
        BudgetLocationEditorSessionServer server = cpContextHolder.getBudgetLocationEditorSessionServer();
        BudgetLocationImpl budgetLocationImpl = getItemData(modelId, server);
        ListSessionServer listSessionServer = cpContextHolder.getListSessionServer();
        modelUserSecurityDetailsDTO = ModelUserSecurityMapper.mapBudgetLocationImpl(budgetLocationImpl, listSessionServer);
        return modelUserSecurityDetailsDTO;
    }

    /**
     * Get responsibility details for selected Model from database.
     */
    private BudgetLocationImpl getItemData(int modelId, BudgetLocationEditorSessionServer server) throws ServiceException {
        try {
            ModelPK modelPK = new ModelPK(modelId);
            BudgetLocationEditorSessionSSO budgetLocationEditorSessionSSO;
            budgetLocationEditorSessionSSO = server.getItemData(modelPK);
            BudgetLocationImpl budgetLocationImpl = budgetLocationEditorSessionSSO.getEditorData();
            return budgetLocationImpl;
        } catch (CPException e) {
            logger.error("Error during get Model!", e);
            throw new ServiceException("Error during get Model Security!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during get Model Security!", e);
            throw new ServiceException("Validation error during get Model Security!", e);
        }
    }

    /**
     * Get tree for selected Model.
     */
    public NodeStaticWithIdAndDescriptionDTO getTree(int modelId) throws ServiceException {
        NodeStaticWithIdAndDescriptionDTO nodeStaticWithIdAndDescription;
        BudgetLocationEditorSessionServer server = cpContextHolder.getBudgetLocationEditorSessionServer();
        BudgetLocationImpl budgetLocationImpl = getItemData(modelId, server);
        ListSessionServer listSessionServer = cpContextHolder.getListSessionServer();
        nodeStaticWithIdAndDescription = ModelUserSecurityMapper.mapBudgetLocationTree(budgetLocationImpl, listSessionServer);
        return nodeStaticWithIdAndDescription;
    }

    /**
     * Save responsibility details for selected Model at update(BudgetLocationImpl budgetLocationImpl, BudgetLocationEditorSessionServer server).
     * @throws SQLException 
     */
    @Override
    public ResponseMessage saveModelDetails(ModelUserSecuritySaveData modelUserSecuritySaveData) throws ServiceException, SQLException {
        BudgetLocationEditorSessionServer server = cpContextHolder.getBudgetLocationEditorSessionServer();
        BudgetLocationImpl budgetLocationImpl = getItemData(modelUserSecuritySaveData.getModelId(), server);
                
        List<UserModelElementAssignment> startListOfUsers = budgetLocationImpl.getModelUserElementAccess();
        budgetLocationImpl = ModelUserSecurityMapper.mapModelUserSecurityDetailsDTO(modelUserSecuritySaveData, budgetLocationImpl);
        List<UserModelElementAssignment> endListOfUsers = budgetLocationImpl.getModelUserElementAccess();
        List<Integer> addedUsers = getUserIdsToDeleteProfiles(endListOfUsers, startListOfUsers);
        List<Integer> userIds = getUserIdsToDeleteProfiles(startListOfUsers, endListOfUsers);
        if (userIds.size()>0){
            String[] parts = budgetLocationImpl.getPrimaryKey().toString().split("=");
            int modelId = Integer.valueOf(parts[1]);
            List<Integer> modelIds = new ArrayList<Integer>();
            modelIds.add(modelId);
            profileService.deleteProfiles(modelIds, userIds); //uncomment to enable automatic delete profiles
        }
        for(Integer addedUser : addedUsers){
          profileService.createProfiles(modelUserSecuritySaveData.getModelId(),addedUser);
        }
        return update(budgetLocationImpl, server);
    }

    private List<Integer> getUserIdsToDeleteProfiles(List<UserModelElementAssignment> startListOfUsers, List<UserModelElementAssignment> endListOfUsers) {
        List<Integer> list = new ArrayList<Integer>();
        List<String> userDetails = new ArrayList<String>();
        for (int i=0;i<startListOfUsers.size();i++){
            for (int j=0;j<endListOfUsers.size();j++){
                if (startListOfUsers.get(i).getUser().getPrimaryKey().toString().equals(endListOfUsers.get(j).getUser().getPrimaryKey().toString())){
                    break;
                }
                if (j == endListOfUsers.size()-1){
                    userDetails.add(startListOfUsers.get(i).getUser().getPrimaryKey().toString());
                }
            }
        }
        for(int i=0;i<userDetails.size();i++){
            String[] parts = userDetails.get(i).split("=");
            list.add(Integer.valueOf(parts[1]));
        }
        return list;
    }

    /**
     * Save responsibility details Model's at database.
     */
    private ResponseMessage update(BudgetLocationImpl budgetLocationImpl, BudgetLocationEditorSessionServer server) throws ServiceException {
        try {
            server.update(budgetLocationImpl);
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (CPException e) {
            logger.error("Error during Model Security edit operation!", e);
            throw new ServiceException("Error during Model Security edit operation!", e);
        } catch (ValidationException e) {
            logger.error("Error during Model Security edit operation!");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }

}
