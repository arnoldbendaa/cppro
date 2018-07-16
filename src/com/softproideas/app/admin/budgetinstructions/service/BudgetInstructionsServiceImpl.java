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
package com.softproideas.app.admin.budgetinstructions.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsELO;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionEditorSessionSSO;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionImpl;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import com.cedar.cp.dto.dimension.ImmediateChildrenELO;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.BudgetCyclesForModelELO;
import com.cedar.cp.dto.model.BudgetHierarchyRootNodeForModelELO;
import com.cedar.cp.ejb.api.budgetinstruction.BudgetInstructionEditorSessionServer;
import com.softproideas.app.admin.budgetinstructions.mapper.BudgetInstructionsMapper;
import com.softproideas.app.admin.budgetinstructions.model.BudgetInstructionDTO;
import com.softproideas.app.admin.budgetinstructions.model.BudgetInstructionDetailsDTO;
import com.softproideas.app.admin.budgetinstructions.util.BudgetInstructionsValidatorUtil;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;
import com.softproideas.commons.model.tree.NodeLazyDTO;

@Service("budgetInstructionsService")
public class BudgetInstructionsServiceImpl implements BudgetInstructionsService {

    private static Logger logger = LoggerFactory.getLogger(BudgetInstructionsServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    /**
     * Returns list of available budget instruction
     */
    @Override
    public List<BudgetInstructionDTO> browseBudgetInstructions() throws ServiceException {
        AllBudgetInstructionsELO allBudgetInstructionsELO = cpContextHolder.getListSessionServer().getAllBudgetInstructions();
        return BudgetInstructionsMapper.mapAllBudgetInstructionsELO(allBudgetInstructionsELO);
    }

    /**
     * Returns  budget instruction details
     */
    @Override
    public BudgetInstructionDetailsDTO fetchBudgetInstructionDetails(int budgetInstructionsId) throws ServiceException {
        BudgetInstructionEditorSessionServer server = cpContextHolder.getBudgetInstructionEditorSessionServer();
        BudgetInstructionImpl budgetInstructionImpl = getItemData(budgetInstructionsId, server);
        BudgetInstructionDetailsDTO budgetInstructionDetailsDTO = BudgetInstructionsMapper.mapBudgetInstructionDetailsDTO(budgetInstructionImpl);
        return budgetInstructionDetailsDTO;
    }

    /**
     * Get model root for budget instruction
     */
    @Override
    public List<NodeLazyDTO> browseSecurityModelRoot(int budgetInstructionsId) throws ServiceException {
        BudgetInstructionEditorSessionServer server = cpContextHolder.getBudgetInstructionEditorSessionServer();
        AllModelsELO modelsList = cpContextHolder.getListSessionServer().getAllModels();
        BudgetInstructionImpl budgetInstructionImpl = getItemData(budgetInstructionsId, server);
        List<NodeLazyDTO> modelRoot = BudgetInstructionsMapper.mapAllModelsELO(modelsList, budgetInstructionImpl);
        return modelRoot;
    }

    /**
     * Get folders for model id
     */
    @Override
    public List<NodeLazyDTO> browseRoot(int budgetInstructionsId, Integer modelId) throws ServiceException {
        BudgetInstructionEditorSessionServer server = cpContextHolder.getBudgetInstructionEditorSessionServer();
        BudgetInstructionImpl budgetInstructionImpl = getItemData(budgetInstructionsId, server);
        List<NodeLazyDTO> modelBrowse = BudgetInstructionsMapper.mapHierarchiesAndCyclesNode(budgetInstructionImpl, modelId);
        return modelBrowse;
    }

    /**
     * Get budget cycles items
     */
    @Override
    public List<NodeLazyDTO> browseBudgetCycles(int budgetInstructionsId, Integer modelId) throws ServiceException {
        BudgetInstructionEditorSessionServer server = cpContextHolder.getBudgetInstructionEditorSessionServer();
        BudgetCyclesForModelELO cycles = cpContextHolder.getListSessionServer().getBudgetCyclesForModel(modelId);
        BudgetInstructionImpl budgetInstructionImpl = getItemData(budgetInstructionsId, server);
        List<NodeLazyDTO> browseeBudgetCycle = BudgetInstructionsMapper.mapBudgetCyclesNode(cycles, budgetInstructionImpl);
        return browseeBudgetCycle;
    }

    /**
     * Get responsibility area for model id
     */
    @Override
    public List<NodeLazyDTO> browseResponsibilityAreasModels(int budgetInstructionsId, Integer modelId) throws ServiceException {
        BudgetInstructionEditorSessionServer server = cpContextHolder.getBudgetInstructionEditorSessionServer();
        BudgetHierarchyRootNodeForModelELO hierarchiesforModelELO = cpContextHolder.getListSessionServer().getBudgetHierarchyRootNodeForModel(modelId);
        BudgetInstructionImpl budgetInstructionImpl = getItemData(budgetInstructionsId, server);
        List<NodeLazyDTO> models = BudgetInstructionsMapper.mapHierarchiesForModelELO(hierarchiesforModelELO, budgetInstructionImpl);
        return models;

    }

    /**
     * Get structure and structure element - assignment for budget instruction
     */
    @Override
    public List<NodeLazyDTO> browseSecurityStructureElement(int budgetInstructionsId, Integer structureId, Integer structureElementId) throws ServiceException {
        BudgetInstructionEditorSessionServer server = cpContextHolder.getBudgetInstructionEditorSessionServer();
        ImmediateChildrenELO structureElement = cpContextHolder.getListSessionServer().getImmediateChildren(structureId, structureElementId);
        BudgetInstructionImpl budgetInstructionImpl = getItemData(budgetInstructionsId, server);
        List<NodeLazyDTO> element = BudgetInstructionsMapper.mapImmediateChildrenELO(structureElement, budgetInstructionImpl);
        return element;
    }

    /**
     * Method save changing or create new data budget instruction details
     */
    @Override
    public ResponseMessage save(BudgetInstructionDetailsDTO budgetInstruction) throws ServiceException {
        ResponseMessage message = null;
        BudgetInstructionEditorSessionServer server = cpContextHolder.getBudgetInstructionEditorSessionServer();
        BudgetInstructionImpl budgetInstructionImpl = getItemData(budgetInstruction.getBudgetInstructionId(), server);

        ValidationError error = BudgetInstructionsValidatorUtil.validateUserDetails(budgetInstructionImpl, budgetInstruction);
        String method;
        if (budgetInstruction.getBudgetInstructionId() != -1) {
            method = "edit";
        } else {
            method = "create";
        }
        if (error.getFieldErrors().isEmpty()) {
            budgetInstructionImpl = BudgetInstructionsMapper.mapBudgetInstructionImpl(budgetInstructionImpl, budgetInstruction);
            message = save(budgetInstructionImpl, server);
        } else {
            error.setMessage("Error during " + method + " BudgetInstruction.");
            message = error;
        }
        return message;
    }

    /**
     * Returns response message , update details
     */
    private ResponseMessage save(BudgetInstructionImpl budgetInstructionImpl, BudgetInstructionEditorSessionServer server) throws ServiceException {
        try {
            if (budgetInstructionImpl.getPrimaryKey() == null) {
                server.insert(budgetInstructionImpl);
            } else {
                server.update(budgetInstructionImpl);
            }
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Error during update budgetInstruction ");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        }
    }

    /**
     * Returns response message, delete budget instruction from database
     */
    @Override
    public ResponseMessage delete(int budgetInstructionsId) throws ServiceException {
        BudgetInstructionPK budgetInstructionPK = new BudgetInstructionPK(budgetInstructionsId);
        try {
            cpContextHolder.getBudgetInstructionEditorSessionServer().delete(budgetInstructionPK);
            ResponseMessage responseMessage = new ResponseMessage(true);
            return responseMessage;
        } catch (ValidationException e) {
            logger.error("Validation error during dalete budget instruction with Id =" + budgetInstructionsId + e + "!");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error("Error during delete budget instruction = ", e + "!");
            throw new ServiceException("Error during dalete budget instruction =" + e + "!");
        }

    }

    /**
     * Get budget instruction item data
     */
    private BudgetInstructionImpl getItemData(int budgetInstructionsId, BudgetInstructionEditorSessionServer server) throws ServiceException {
        try {
            BudgetInstructionEditorSessionSSO budgetInstructionEditorSessionSSO;
            BudgetInstructionPK budgetInstructionPK = new BudgetInstructionPK(budgetInstructionsId);
            if (budgetInstructionsId != -1) {
                budgetInstructionEditorSessionSSO = server.getItemData(budgetInstructionPK);
            } else {
                budgetInstructionEditorSessionSSO = server.getNewItemData();
            }
            BudgetInstructionImpl userImpl = budgetInstructionEditorSessionSSO.getEditorData();

            return userImpl;
        } catch (CPException e) {
            logger.error("Error during get BudgetInstruction!", e);
            throw new ServiceException("Error during get BudgetInstruction!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during get BudgetInstruction!", e);
            throw new ServiceException("Validation error during get BudgetInstruction!", e);
        }
    }

}
