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
package com.softproideas.app.admin.recalculatebatches.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.recalculate.RecalculateBatchTaskRef;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.ImmediateChildrenELO;
import com.cedar.cp.dto.model.HierarchiesForModelELO;
import com.cedar.cp.dto.recalculate.AllRecalculateBatchTasksELO;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskEditorSessionCSO;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskEditorSessionSSO;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskImpl;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskRefImpl;
import com.cedar.cp.ejb.api.recalculate.RecalculateBatchTaskEditorSessionServer;
import com.cedar.cp.ejb.impl.recalculate.RecalculateBatchTaskEditorSessionSEJB;
import com.softproideas.app.admin.recalculatebatches.mapper.RecalculateBatchesMapper;
import com.softproideas.app.admin.recalculatebatches.mapper.RecalculateBatchesProfileMapper;
import com.softproideas.app.admin.recalculatebatches.mapper.RecalculateBatchesResponsibilityAreaMapper;
import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchDTO;
import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchDetailsDTO;
import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchProfileDTO;
import com.softproideas.app.admin.recalculatebatches.util.RecalculateBatchesProfileUtil;
import com.softproideas.app.admin.recalculatebatches.util.RecalculateBatchesUtil;
import com.softproideas.app.admin.recalculatebatches.util.RecalculateBatchesValidatorUtil;
import com.softproideas.app.core.profile.model.ProfileDTO;
import com.softproideas.app.core.profile.service.ProfileService;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;
import com.softproideas.commons.model.tree.NodeLazyDTO;

@Service("recalculateBatchesService")
public class RecalculateBatchesServiceImpl implements RecalculateBatchesService {

    private static Logger logger = LoggerFactory.getLogger(RecalculateBatchesServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    RecalculateBatchTaskEditorSessionSEJB server = new RecalculateBatchTaskEditorSessionSEJB();
    @Autowired
    ProfileService profileService;

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.recalculatebatches.service.RecalculateBatchesService#browseAllRecalculateBatchTasks()
     */
    @Override
    public List<RecalculateBatchDTO> browseAllRecalculateBatchTasks() throws ServiceException {
        AllRecalculateBatchTasksELO allRecalculateBatchTasks = cpContextHolder.getListSessionServer().getAllRecalculateBatchTasks();
        List<RecalculateBatchDTO> recalculateBatchsDTOList = RecalculateBatchesMapper.mapAllRecalculateBatchTasksELO(allRecalculateBatchTasks);
        return recalculateBatchsDTOList;
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.recalculatebatches.service.RecalculateBatchesService#fetchRecalculateBatch(int)
     */
    @Override
    public RecalculateBatchDetailsDTO fetchRecalculateBatch(int recalculateBatchId) throws ServiceException {
//        RecalculateBatchTaskEditorSessionServer server = cpContextHolder.getRecalculateBatchTaskEditorSessionServer();
        RecalculateBatchTaskImpl recalculateBatchTaskImpl = getRecaluclateBatchFromServer(server, recalculateBatchId);
        RecalculateBatchDetailsDTO recalculateBatchDTO = RecalculateBatchesMapper.mapToRecalculateBatchDTO(recalculateBatchTaskImpl);
        return recalculateBatchDTO;
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.recalculatebatches.service.RecalculateBatchesService#save(com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchDetailsDTO)
     */
    @Override
    public ResponseMessage save(RecalculateBatchDetailsDTO recalculateBatch) throws ServiceException {
        ResponseMessage message = null;

//        RecalculateBatchTaskEditorSessionServer server = cpContextHolder.getRecalculateBatchTaskEditorSessionServer();

        RecalculateBatchTaskImpl recalculateBatchTaskImpl = getRecaluclateBatchFromServer(server, recalculateBatch.getRecalculateBatchId());
        ValidationError error = RecalculateBatchesValidatorUtil.validateRecalculateBatchDetails(recalculateBatchTaskImpl, recalculateBatch);
        String method = (recalculateBatch.getRecalculateBatchId() != -1) ? "edit" : "create";

        if (error.getFieldErrors().isEmpty()) {
            recalculateBatchTaskImpl = RecalculateBatchesMapper.mapRecalculateBatchDTOToRecalculateBatchTaskImpl(recalculateBatchTaskImpl, recalculateBatch, cpContextHolder.getUserId());
            message = save(server, recalculateBatchTaskImpl, method);
        } else {
            error.setMessage("Something WRONG during (" + method.toUpperCase() + ") recalculate batch.");
            message = error;
        }
        return message;
    }

    /**
     * Method retrieves the newest details of recalculate batch from database.
     */
    private RecalculateBatchTaskImpl getRecaluclateBatchFromServer(RecalculateBatchTaskEditorSessionSEJB server, int recalculateBatchId) throws ServiceException {
        try {
            RecalculateBatchTaskEditorSessionSSO sso = null;

            if (recalculateBatchId != -1) {
                RecalculateBatchTaskPK recalculateBatchTaskPK = new RecalculateBatchTaskPK(recalculateBatchId);
                sso = server.getItemData(cpContextHolder.getUserId(),recalculateBatchTaskPK);
            } else {
                sso = server.getNewItemData(cpContextHolder.getUserId());
            }

            RecalculateBatchTaskImpl recalculateBatchTaskImpl = sso.getEditorData();
            return recalculateBatchTaskImpl;
        } catch (CPException e) {
            logger.error("Error during browsing batch recalculate with id =" + recalculateBatchId + "!");
            throw new ServiceException("Error during browsing batch recalculate with id =" + recalculateBatchId + "!");
        } catch (ValidationException e) {
            logger.error("Validation error during browsing batch recalculate with id =" + recalculateBatchId + "!");
            throw new ServiceException("Validation error during browsing batch recalculate with id =" + recalculateBatchId + "!");
        }
    }

    /**
     * Methods saves (insert or update) the newest object of recalculate batch in database.
     */
    private ResponseMessage save(RecalculateBatchTaskEditorSessionSEJB server, RecalculateBatchTaskImpl recalculateBatchTaskImpl, String operation) throws ServiceException {
        try {
            if (operation.equals("edit")) {
                server.update(new RecalculateBatchTaskEditorSessionCSO(cpContextHolder.getUserId(), recalculateBatchTaskImpl));
            } else if (operation.equals("create")) {
                server.insert(new RecalculateBatchTaskEditorSessionCSO(cpContextHolder.getUserId(), recalculateBatchTaskImpl));
            }
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Validation error during " + operation + " recalculate batch.");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error("Error during " + operation + " recalculate Batch.");
            throw new ServiceException("Error during " + operation + " recalculate batch.");
        }
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.recalculatebatches.service.RecalculateBatchesService#delete(int)
     */
    @Override
    public ResponseMessage delete(int recalculateBatchId) throws ServiceException {
//        RecalculateBatchTaskEditorSessionServer server = cpContextHolder.getRecalculateBatchTaskEditorSessionServer();
        RecalculateBatchTaskPK recalculateBatchTaskPK = new RecalculateBatchTaskPK(recalculateBatchId);

        try {
            server.delete(cpContextHolder.getUserId(),recalculateBatchTaskPK);
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Validation error during deleting recalculate batch (ID = " + recalculateBatchId + ").");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error("Error during deleting recalculate batch (ID = " + recalculateBatchId + ").");
            throw new ServiceException("Error during deleting recalculate batch (ID = " + recalculateBatchId + ").");
        }
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.recalculatebatches.service.RecalculateBatchesService#issueRecalculateBatchTask(com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchDetailsDTO)
     */
    @Override
    public int issueRecalculateBatchTask(RecalculateBatchDetailsDTO recalculateBatch) throws ServiceException {
        RecalculateBatchTaskPK recalculateBatchTaskPK = new RecalculateBatchTaskPK(recalculateBatch.getRecalculateBatchId());
        RecalculateBatchTaskRef recalculateBatchTaskRef = new RecalculateBatchTaskRefImpl(recalculateBatchTaskPK, recalculateBatch.getRecalculateBatchVisId());

        try {
            RecalculateBatchTaskEditorSessionServer server = cpContextHolder.getRecalculateBatchTaskEditorSessionServer();
            int taskId = server.issueRecalculateBatchTask(recalculateBatchTaskRef, cpContextHolder.getUserId());
            return taskId;
        } catch (CPException e) {
        	e.printStackTrace();
            logger.error("Error during issue recalculate batch task with id =" + recalculateBatch.getRecalculateBatchId() + "!");
            throw new ServiceException("Error during issue recalculate batch task with id =" + recalculateBatch.getRecalculateBatchId() + "!");
        } catch (ValidationException e) {
            logger.error("Validation error during issue recalculate batch task with id =" + recalculateBatch.getRecalculateBatchId() + "!");
            throw new ServiceException("Validation error during issue recalculate batch task with id =" + recalculateBatch.getRecalculateBatchId() + "!");
        }
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.recalculatebatches.service.RecalculateBatchesService#browseResponsibilityAreaChildren(int, int, int)
     */
    @Override
    public List<NodeLazyDTO> browseResponsibilityAreaChildren(int recalculateBatchId, int structureId, int structureElementId) throws ServiceException {
//        RecalculateBatchTaskEditorSessionServer server = cpContextHolder.getRecalculateBatchTaskEditorSessionServer();

        RecalculateBatchTaskImpl recalculateBatchTaskImpl = getRecaluclateBatchFromServer(server, recalculateBatchId);
        ImmediateChildrenELO list = cpContextHolder.getListSessionServer().getImmediateChildren(structureId, structureElementId);

        List<NodeLazyDTO> children = RecalculateBatchesResponsibilityAreaMapper.mapToNodeDTO(recalculateBatchTaskImpl, list);
        return children;
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.recalculatebatches.service.RecalculateBatchesService#browseResponsibilityAreaRoot(int, int)
     */
    @Override
    public List<NodeLazyDTO> browseResponsibilityAreaRoot(int recalculateBatchId, int modelId) throws ServiceException {
//        RecalculateBatchTaskEditorSessionServer server = cpContextHolder.getRecalculateBatchTaskEditorSessionServer();

        RecalculateBatchTaskImpl recalculateBatchTaskImpl = getRecaluclateBatchFromServer(server, recalculateBatchId);
        int modelIdTemp = RecalculateBatchesUtil.manageModelId(recalculateBatchTaskImpl, modelId);

        // get responsibility areas for batch recalculate (depends on model)
        HierarchiesForModelELO respAreas = cpContextHolder.getListSessionServer().getHierarchiesForModel(modelIdTemp); // get resp. area data
        HierarchyRef hierRef = (HierarchyRef) respAreas.getValueAt(0, "Hierarchy");
        int structureId = ((HierarchyPK) hierRef.getPrimaryKey()).getHierarchyId();
        int structureElementId = 0;

        return browseResponsibilityAreaChildren(recalculateBatchId, structureId, structureElementId);
    }

    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.recalculatebatches.service.RecalculateBatchesService#browseRecalculateBatchProfiles(int, int, int)
     */
    @Override
    public List<RecalculateBatchProfileDTO> browseRecalculateBatchProfiles(int recalculateBatchId, int modelId, int budgetCycleId) throws ServiceException {
//        RecalculateBatchTaskEditorSessionServer server = cpContextHolder.getRecalculateBatchTaskEditorSessionServer();

        RecalculateBatchTaskImpl impl = getRecaluclateBatchFromServer(server, recalculateBatchId);
        List<ProfileDTO> profilesDTO;
        try {
            profilesDTO = profileService.browseProfiles(modelId, budgetCycleId, 0);
        } catch (ServiceException e) {
            logger.error("Error during browse profile for Recalculate Batch with modelId = " + modelId + " and budgetCycleId = " + budgetCycleId);
            throw new ServiceException("Error during browse profile for Recalculate Batch with modelId = " + modelId + " and budgetCycleId = " + budgetCycleId);
        }
        List<RecalculateBatchProfileDTO> recalculateBatchProfilesDTO = RecalculateBatchesProfileMapper.mapProfilesDTOToBatchRecalculateProfileDTO(profilesDTO);

        // manage which of profiles are selected (to recalculate/submit) - only selected profiles will be recalculated
        recalculateBatchProfilesDTO = RecalculateBatchesProfileUtil.manageSelectedProfiles(recalculateBatchProfilesDTO, impl.getRecalculateBatchTaskForms());
        return recalculateBatchProfilesDTO;
    }
}
