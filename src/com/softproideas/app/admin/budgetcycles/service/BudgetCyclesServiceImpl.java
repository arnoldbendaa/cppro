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
package com.softproideas.app.admin.budgetcycles.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.model.AllBudgetCyclesELO;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCycleEditorSessionSSO;
import com.cedar.cp.dto.model.BudgetCycleImpl;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.BudgetCyclesForModelELO;
import com.cedar.cp.dto.model.MaxDepthForBudgetHierarchyELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.xmlform.AllXmlFormsELO;
import com.cedar.cp.ejb.api.model.BudgetCycleEditorSessionServer;
import com.softproideas.app.admin.budgetcycles.mapper.BudgetCyclesMapper;
import com.softproideas.app.admin.budgetcycles.mapper.FormMapper;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDTO;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDetailsDTO;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleStructureLevelEndDatesDTO;
import com.softproideas.app.admin.budgetcycles.util.BudgetCyclesValidator;
import com.softproideas.app.core.flatform.model.FlatFormExtendedCoreDTO;
import com.softproideas.app.core.profile.service.ProfileService;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.DefaultError;
import com.softproideas.commons.model.error.FieldError;
import com.softproideas.commons.model.error.ValidationError;

@Service("budgetCyclesService")
public class BudgetCyclesServiceImpl implements BudgetCyclesService {

    private static Logger logger = LoggerFactory.getLogger(BudgetCyclesServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    @Autowired
    ProfileService profileService;

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.budgetcycles.service.BudgetCyclesService#browseBudgetCycles() */
    @Override
    public List<BudgetCycleDTO> browseBudgetCycles() {
        AllBudgetCyclesELO budgetCycles = cpContextHolder.getListSessionServer().getAllBudgetCyclesForLoggedUser();
        List<BudgetCycleDTO> budgetCyclesDTO = BudgetCyclesMapper.mapAllBudgetCyclesELOToBudgetCycleDTO(budgetCycles);
        return budgetCyclesDTO;
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.budgetcycles.service.BudgetCyclesService#browseBudgetCyclesForModel(int) */
    @Override
    public List<BudgetCycleDTO> browseBudgetCyclesForModel(int modelId) {
        BudgetCyclesForModelELO budgetCycles = cpContextHolder.getListSessionServer().getBudgetCyclesForModel(modelId);
        List<BudgetCycleDTO> budgetCyclesDTO = BudgetCyclesMapper.mapBudgetCyclesForModelELOToDTO(budgetCycles);
        return budgetCyclesDTO;
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.budgetcycles.service.BudgetCyclesService#fetchBudgetCycle(int, int) */
    @Override
    public BudgetCycleDetailsDTO fetchBudgetCycle(int budgetCycleId, int modelId) throws ServiceException {
        BudgetCycleEditorSessionServer server = cpContextHolder.getBudgetCycleEditorSessionServer();

        BudgetCycleImpl budgetCycle = getBudgetCycleFromServer(server, budgetCycleId, modelId);
        BudgetCycleDetailsDTO budgetCycleDetailsDTO = BudgetCyclesMapper.mapBudgetCycleImplToBudgetCycleDetailsDTO(budgetCycle);
        return budgetCycleDetailsDTO;
    }

    /**
     * Method retrieves the newest details of budget cycle from database. ModelId is necessary to fetch details for budget cycle.
     */
    private BudgetCycleImpl getBudgetCycleFromServer(BudgetCycleEditorSessionServer server, int budgetCycleId, int modelId) throws ServiceException {

        BudgetCycleEditorSessionSSO sso = null;
        try {
            if (budgetCycleId != -1) {
                ModelPK modelPK = new ModelPK(modelId);
                BudgetCyclePK budgetCyclePK = new BudgetCyclePK(budgetCycleId);
                BudgetCycleCK budgetCycleCK = new BudgetCycleCK(modelPK, budgetCyclePK);

                sso = server.getItemData(budgetCycleCK);
            } else {
                sso = server.getNewItemData();
            }
            BudgetCycleImpl budgetCycleImpl = sso.getEditorData();
            return budgetCycleImpl;
        } catch (CPException e) {
            logger.error("Error during browsing budget cycle with id =" + budgetCycleId + "!");
            throw new ServiceException("Error during browsing budget cycle with id =" + budgetCycleId + "!");
        } catch (ValidationException e) {
            logger.error("Validation error during browsing budget cycle with id =" + budgetCycleId + "!");
            throw new ServiceException("Validation error during browsing budget cycle with id =" + budgetCycleId + "!");
        }
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.budgetcycles.service.BudgetCyclesService#save(com.softproideas.app.admin.budgetcycles.model.BudgetCycleDetailsDTO) */
    @Override
    public ResponseMessage save(BudgetCycleDetailsDTO budgetCycle) throws ServiceException {
        ResponseMessage message = null;

        BudgetCycleEditorSessionServer server = cpContextHolder.getBudgetCycleEditorSessionServer();

        BudgetCycleImpl budgetCycleImpl = getBudgetCycleFromServer(server, budgetCycle.getBudgetCycleId(), budgetCycle.getModel().getModelId());
        ValidationError error = BudgetCyclesValidator.validateBudgetCycleDetails(budgetCycle);
        String method = (budgetCycle.getBudgetCycleId() != -1) ? "edit" : "create";
        
        List<Object[]> beforeSave = budgetCycleImpl.getXmlForms();

        if (error.getFieldErrors().isEmpty()) {
            budgetCycleImpl = BudgetCyclesMapper.mapBudgetCycleDetailsDTOToBudgetCycleImpl(budgetCycleImpl, budgetCycle, cpContextHolder.getUserId());
            message = save(server, budgetCycleImpl, method);
            List<Object[]> afterSave = budgetCycleImpl.getXmlForms();
            updateProfiles(beforeSave, afterSave, budgetCycle);
        } else {
            error.setMessage("Something WRONG during (" + method.toUpperCase() + ") budget cycle.");
            message = error;
        }
        return message;
    }

    /**
     * Methods saves (insert or update) the newest object of budget cycle in database.
     */
    private ResponseMessage save(BudgetCycleEditorSessionServer server, BudgetCycleImpl budgetCycleImpl, String operation) throws ServiceException {
        try {
            if (operation.equals("edit")) {
                server.update(budgetCycleImpl);
            } else if (operation.equals("create")) {
                server.insert(budgetCycleImpl);
            }
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Validation error during " + operation + " budget cycle.");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error("Error during " + operation + " budget cycle.");
            throw new ServiceException("Error during " + operation + " budget cycle.", e);
        }
    }
    
    private void updateProfiles(List<Object[]> oldFormList, List<Object[]> newFormList, BudgetCycleDetailsDTO budgetCycle) throws ServiceException {
        
        List<Integer> formToSubstractList = new ArrayList<Integer>();
        List<Integer> formToAddList = new ArrayList<Integer>();

        // Create new profiles
        newFormList: for (Object[] newFormRefImpl: newFormList) {
            for (Object[] oldFormRefImpl: oldFormList) {
                if (((Integer) newFormRefImpl[0]).equals((Integer) oldFormRefImpl[0])) {
                    continue newFormList; // exists in old and new userlist
                }
            }
            formToAddList.add((Integer) newFormRefImpl[0]);
        }

        // Delete old profiles
        oldFormList: for (Object[] oldFormRefImpl: oldFormList) {
            for (Object[] newFormRefImpl: newFormList) {
                if (((Integer) newFormRefImpl[0]).equals((Integer) oldFormRefImpl[0])) {
                    continue oldFormList; // exists in old and new userlist
                }
            }
            formToSubstractList.add((Integer) oldFormRefImpl[0]);
        }
        
        if(formToAddList.size() > 0) {
            profileService.insertProfileForBudgetCycle(formToAddList, budgetCycle);
        }
        if (formToSubstractList.size() > 0) {
            profileService.deleteProfilesForBudgetCycle(formToSubstractList, budgetCycle.getBudgetCycleId());
        }
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.budgetcycles.service.BudgetCyclesService#delete(int, int) */
    @Override
    public ResponseMessage delete(int budgetCycleId, int modelId) throws ServiceException {
        BudgetCycleEditorSessionServer server = cpContextHolder.getBudgetCycleEditorSessionServer();

        BudgetCycleImpl budgetCycleImpl = getBudgetCycleFromServer(server, budgetCycleId, modelId);
        BudgetCycleCK budgetCycleCK = (BudgetCycleCK) budgetCycleImpl.getPrimaryKey();

        if (budgetCycleImpl.getStatus() != 2) {
            DefaultError error = new DefaultError();
            error.setError(true);
            error.setTitle("You can't delete budget cycle (ID = " + budgetCycleId + "). Budget cycle is not completed.");
            return error;
        }

        try {
            server.delete(budgetCycleCK);
            profileService.deleteAllProfilesForBudgetCycle(budgetCycleId);
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Validation error during deleting budget cycle (ID = " + budgetCycleId + ").");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error("Error during deleting budget cycle (ID = " + budgetCycleId + ").");
            throw new ServiceException("Error during deleting budget cycle (ID = " + budgetCycleId + ").");
        }
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.budgetcycles.service.BudgetCyclesService#updatePeriodForBudgetCycle(com.softproideas.app.admin.budgetcycles.model.BudgetCycleDTO) */
    @Override
    public ResponseMessage updatePeriodForBudgetCycle(BudgetCycleDTO budgetCycle) throws ServiceException {
        ResponseMessage message = null;

        BudgetCycleEditorSessionServer server = cpContextHolder.getBudgetCycleEditorSessionServer();

        BudgetCycleImpl budgetCycleImpl = getBudgetCycleFromServer(server, budgetCycle.getBudgetCycleId(), budgetCycle.getModel().getModelId());
        ValidationError error = BudgetCyclesValidator.validateBudgetCycle(budgetCycle);

        if (error.getFieldErrors().isEmpty()) {
            budgetCycleImpl = BudgetCyclesMapper.mapBudgetCycleDTOToBudgetCycleImpl(budgetCycleImpl, budgetCycle);
            message = save(server, budgetCycleImpl, "edit");
        } else {
            String errorInfo = "Error during update budget cycle. ";
            for (FieldError fieldError: error.getFieldErrors()) {
                errorInfo += fieldError.getFieldMessage() + " ";
            }
            error.setMessage(errorInfo);
            message = error;
        }
        return message;
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.budgetcycles.service.BudgetCyclesService#issueBudgetStateRebuildTask(com.softproideas.app.admin.budgetcycles.model.BudgetCycleDTO) */
    @Override
    public int issueBudgetStateRebuildTask(BudgetCycleDTO budgetCycle) {
        // EntityRef er = (EntityRef)coll.getDataAsArray()[row][0];
        // String message = "Are you sure you want to submit " + er.getNarrative() + " for state rebuild";
        // int result = JOptionPane.showConfirmDialog(this.this$0.getFrame(), message, "Submit BudgetState Rebuild Task", 2, 1);
        // if(result == 0) {
        // int id = -1;
        //
        // try {
        // id = BudgetCycleListProcess.accessMethod900(this.this$0).getConnection().getBudgetCyclesProcess().issueBudgetStateRebuildTask(er, BudgetCycleListProcess.accessMethod800(this.this$0).getConnection().getUserContext().getUserId());
        // } catch (ValidationException var9) {
        // JOptionPane.showMessageDialog(BudgetCycleListProcess.accessMethod1000(this.this$0), var9.getMessage(), "Error", 0);
        // }
        //
        // if(id > 0) {
        // JOptionPane.showMessageDialog(BudgetCycleListProcess.accessMethod1100(this.this$0), "Task has been submitted with id :" + id, "Complete", 1);
        // }
        // }
        return 0;
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.budgetcycles.service.BudgetCyclesService#issueBudgetStateTask(com.softproideas.app.admin.budgetcycles.model.BudgetCycleDTO) */
    @Override
    public int issueBudgetStateTask(BudgetCycleDTO budgetCycle) throws ServiceException {
        // mainPanel.add(new JLabel("DaysBefore Param :"));
        // JTextField daysBefore = new JTextField("3");
        // mainPanel.add(daysBefore);
        // JOptionPane pane = new JOptionPane(mainPanel, 1, 2);
        // JDialog textDialog = pane.createDialog(this.this$0.getFrame(), "Submit BudgetState Task");
        // textDialog.pack();
        // textDialog.setVisible(true);
        // if(Integer.valueOf(0).equals(pane.getValue())) {
        // int id = -1;
        //
        // try {
        // id = BudgetCycleListProcess.accessMethod100(this.this$0).getConnection().getBudgetCyclesProcess().issueBudgetStateTask(Integer.parseInt(daysBefore.getText()), BudgetCycleListProcess.accessMethod000(this.this$0).getConnection().getUserContext().getUserId());
        // } catch (NumberFormatException var8) {
        // JOptionPane.showMessageDialog(BudgetCycleListProcess.accessMethod200(this.this$0), var8.getMessage(), "Error", 0);
        // } catch (ValidationException var9) {
        // JOptionPane.showMessageDialog(BudgetCycleListProcess.accessMethod300(this.this$0), var9.getMessage(), "Error", 0);
        // }
        //
        // if(id > 0) {
        // JOptionPane.showMessageDialog(BudgetCycleListProcess.accessMethod400(this.this$0), "Task has been submitted with id :" + id, "Complete", 1);
        // }
        // }
        return 0;
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.admin.budgetcycles.service.BudgetCyclesService#browseXmlFormsForModel(int) */
    @Override
    public List<FlatFormExtendedCoreDTO> browseXmlFormsForModel(int modelId) {
        AllXmlFormsELO xmlFormsELO = cpContextHolder.getListSessionServer().getAllXmlFormsForModel(modelId);
        return FormMapper.mapAllXmlFormsELOToFormDTO(xmlFormsELO);
    }
    
    /*
     * (non-Javadoc)
     * @see com.softproideas.app.admin.budgetcycles.service.BudgetCyclesService#browseStructureLevelDatesForModel(com.softproideas.app.admin.budgetcycles.model.BudgetCycleStructureLevelEndDatesDTO)
     */
    @Override
    public List<Long> browseStructureLevelDatesForModel(BudgetCycleStructureLevelEndDatesDTO budgetCycleStructureLevelEndDatesDTO) {
        MaxDepthForBudgetHierarchyELO elo = cpContextHolder.getListSessionServer().getMaxDepthForBudgetHierarchy(budgetCycleStructureLevelEndDatesDTO.getModelId());
        ArrayList<Long> splitDays = BudgetCyclesMapper.mapMaxDepthForBudgetHierarchyELOtoStructureLevelEndDates(budgetCycleStructureLevelEndDatesDTO, elo);        
        return splitDays;
    }
}
