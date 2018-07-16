/*******************************************************************************
 * Copyright Â©2015. IT Services Jacek Kurasiewicz, Warsaw, Poland. All Rights
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
package com.softproideas.app.reviewbudget.budget.service;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.user.AllUsersAssignmentsELO;
import com.cedar.cp.utc.common.CPContext;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.google.gson.Gson;
import com.softproideas.app.core.workbook.model.FlatFormExtractor;
import com.softproideas.app.core.workbook.model.FlatFormExtractorDao;
import com.softproideas.app.flatformeditor.form.service.FormService;
import com.softproideas.app.reviewbudget.budget.mapper.BudgetResponsibilityAreaMapper;
import com.softproideas.app.reviewbudget.budget.model.BudgetResponsibilityAreasDTO;
import com.softproideas.app.reviewbudget.budget.model.ReviewBudgetDTO;
import com.softproideas.app.reviewbudget.budget.model.WorkbookToUpdateDTO;
import com.softproideas.app.reviewbudget.budget.model.WorksheetToUpdateDTO;
import com.softproideas.app.reviewbudget.dao.BudgetCycleDAO;
import com.softproideas.app.reviewbudget.dimension.model.ElementDTO;
import com.softproideas.app.reviewbudget.financeform.service.FinanceFormService;
import com.softproideas.app.reviewbudget.note.service.NoteService;
import com.softproideas.app.reviewbudget.note.service.NoteServiceImpl;
import com.softproideas.app.reviewbudget.xcellform.model.XCellFormDTO;
import com.softproideas.app.reviewbudget.xcellform.service.XCellFormService;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.util.WorkbookUtil;

import cppro.utils.MyLog;

/**
 * Service for managing budget data.
 * 
 * @author Szymon Walczak, Å�ukasz PuÅ‚a
 * @email szymon.walczak@softproideas.com, lukasz.pula@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
@Service("budgetService")
public class BudgetServiceImpl implements BudgetService {

    private static Logger logger = LoggerFactory.getLogger(BudgetServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;
    int userId;
    public void Init(int userId,CPContext context){
    	this.userId=userId;
        if(cpContextHolder.getCPContext()==null){
        	cpContextHolder.init(context);
        }
        noteService = new NoteServiceImpl();
        noteService.Init(context);

    }
    @Autowired
    NoteService noteService;

    @Autowired
    XCellFormService xCellFormService;

    @Autowired
    FinanceFormService financeFormService;

    @Autowired
    FormService formService;

    @Autowired
    BudgetCycleDAO budgetCycleDAO;

    @Autowired
    FlatFormExtractorDao flatFormExtractorDao;

    @Autowired
    ThreadPoolTaskExecutor taskExecutor;

    /* (non-Javadoc)
     * 
     * @see com.softproideas.spreadsheet.webapp.service.BudgetService#fetchReviewBudget(int, int, int, int, java.util.Map, java.lang.String) */
    @Override
    public ReviewBudgetDTO fetchReviewBudget(int topNodeId, int modelId, int budgetCycleId, int dataEntryProfileId, Map<Integer, Integer> selectionsMap, String dataType) throws ServiceException {
        XCellFormDTO xCellFormDTO = null;
        try {
            xCellFormDTO = budgetCycleDAO.getXCellFormDTO(dataEntryProfileId);
        } catch (InvalidResultSetAccessException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            throw new ServiceException("error while fetching review budget dto", e2);
        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            throw new ServiceException("error while fetching review budget dto", e2);
        }
        fetchReviewBudget(xCellFormDTO, topNodeId, modelId, selectionsMap, dataType);
//        MyLog.info(new Gson().toJson(xCellFormDTO.getWorkbook().getWorksheets()));
        return xCellFormDTO;
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.spreadsheet.webapp.service.BudgetService#fetchReviewBudgetDetails(int, int, int, int, java.util.Map, java.lang.String) */
    @Override
    public ReviewBudgetDTO fetchReviewBudgetDetails(int topNodeId, int modelId, int budgetCycleId, int dataEntryProfileId, Map<Integer, Integer> selectionsMap, String dataType) throws ServiceException {

        XCellFormDTO xCellFormDTO = null;
        try {
            xCellFormDTO = budgetCycleDAO.getXCellFormDTOMobile(dataEntryProfileId);
        } catch (InvalidResultSetAccessException e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            throw new ServiceException("error while fetching review budget dto");
        } catch (Exception e2) {
            // TODO Auto-generated catch block
            e2.printStackTrace();
            throw new ServiceException("error while fetching review budget dto");
        }
        fetchReviewBudget(xCellFormDTO, topNodeId, modelId, selectionsMap, dataType);
        return xCellFormDTO;
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.spreadsheet.webapp.service.BudgetService#updateWorkbookData(com.softproideas.spreadsheet.webapp.model.dto.workbook.update.WorkbookToUpdateDTO, int, int) */
    @Override
    public String updateWorkbookData(WorkbookToUpdateDTO workbookToUpdate, int modelId, int topNodeId) throws ServiceException {
        boolean userReadOnlyAccess = fetchUserReadOnlyAccess(modelId, topNodeId);
        if (userReadOnlyAccess) {
            logger.error("Error during the updateWorkbookData - You haven't got permissions to save workbook (modelId=" + modelId + ", topNodeId=" + topNodeId);
            throw new ServiceException("You haven't got permissions to save workbook.");
        }
        try {
            // 1. set calendar info for workbook to update
            Map<String, CalendarInfo> calendarInfoMap = new HashMap<String, CalendarInfo>();

            // from workbook
            String modelVisId = (String) workbookToUpdate.getProperties().get(WorkbookProperties.MODEL_VISID.toString());
            CalendarInfo calendarInfo = cpContextHolder.getDataEntryProcess().getCalendarInfoForModel(modelVisId);
            calendarInfoMap.put(modelVisId, calendarInfo);

            // from worksheets
            for (WorksheetToUpdateDTO worksheet: workbookToUpdate.getWorksheets()) {
                modelVisId = worksheet.getProperties().get(WorkbookProperties.MODEL_VISID.toString());
                if (modelVisId != null && !calendarInfoMap.containsKey(modelVisId)) {
                    calendarInfo = cpContextHolder.getDataEntryProcess().getCalendarInfoForModel(modelVisId);
                    calendarInfoMap.put(modelVisId, calendarInfo);
                }
            }

            // 2. build xml template
            String xmlToUpdate = WorkbookUtil.buildXMLUpdates(workbookToUpdate, calendarInfoMap);
            String userId = String.valueOf(cpContextHolder.getUserId());
            String budgetCycleId = String.valueOf(workbookToUpdate.getBudgetCycleId());
            String xml = MessageFormat.format(xmlToUpdate, new Object[] { userId, budgetCycleId });
            // System.out.println(xml);

            // 3. send xml to process (to database)
            cpContextHolder.getDataEntryProcess().executeForegroundFlatFormUpdate(xml);
        } catch (ValidationException e) {
            logger.error("Validation error during the updateWorkbookData (modelId=" + modelId + ", topNodeId=" + topNodeId + ")", e);
            throw new ServiceException(e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error during the updateWorkbookData (modelId=" + modelId + ", topNodeId=" + topNodeId + ")", e);
            throw new ServiceException("Something went wrong during updating workbook data", e);
        }
        return "success";
    }

    /* (non-Javadoc)
     * 
     * @see com.softproideas.spreadsheet.webapp.service.BudgetService#fetchUserBudgetResponsibilityAreas(int) */
    @Override
    public BudgetResponsibilityAreasDTO fetchUserBudgetResponsibilityAreas(int modelId) throws ServiceException {
        String modelVisId = getModelVisId(modelId);
        AllUsersAssignmentsELO allUsersAssignmentsELO = (AllUsersAssignmentsELO) cpContextHolder.getUsersProcess().getAllUserAssignments("%", "%", "%", "%");
        BudgetResponsibilityAreasDTO budgetResponsibilityAreasDTO = BudgetResponsibilityAreaMapper.mapAllUsersAssignmentsELO(modelVisId, allUsersAssignmentsELO);
        return budgetResponsibilityAreasDTO;
    }

    private void fetchReviewBudget(XCellFormDTO xCellFormDTO, int topNodeId, int modelId, Map<Integer, Integer> selectionsMap, String dataType) throws ServiceException {

    	if (selectionsMap == null) {

        }
        if (selectionsMap.containsKey(0) == false || selectionsMap.get(0) == 0) {
            selectionsMap.put(0, topNodeId);
        }
        if (selectionsMap.containsKey(2) == false || selectionsMap.get(2) == 0) {
            Integer dim2 = (Integer) xCellFormDTO.getContextVariables().get(2);
            selectionsMap.put(2, dim2);
        }
        xCellFormDTO.setContextVariables(null);
        Map<String, String> contextVariables = new HashMap<String, String>();
        List<ElementDTO> selectedDimensions = budgetCycleDAO.getSelectedDimensions(selectionsMap, modelId);// uzupelnic jesli nie ma
        for (int i = 0; i < selectedDimensions.size(); i++) {
            contextVariables.put(WorkbookProperties.DIMENSION_$_VISID.toString().replace("$", String.valueOf(i)), selectedDimensions.get(i).getName());
        }

        xCellFormDTO.setSelectedDimension(selectedDimensions);

        if (dataType != null && dataType.trim().isEmpty() == false) {
            xCellFormDTO.setDataType(dataType);
        }

        contextVariables.put(WorkbookProperties.MODEL_ID.toString(), String.valueOf(modelId));

        WorkbookDTO workbook = xCellFormDTO.getWorkbook();
        workbook.getProperties().putAll(contextVariables);

        extractWorkbook(workbook);

        noteService.fetchLastNotesForFinanceCube(workbook, selectedDimensions);

    }

    private void extractWorkbook(WorkbookDTO workbook) throws ServiceException {
        try {
            if (taskExecutor == null) {
                CPConnection connection = cpContextHolder.getCPContext().getCPConnection();
                FlatFormExtractor flatFormExtractor = new FlatFormExtractor(connection, flatFormExtractorDao);
                flatFormExtractor.getValuesForMappings(workbook, connection);
            } else {
                CPConnection connection = cpContextHolder.getCPContext().getCPConnection();
                FlatFormExtractor flatFormExtractor = new FlatFormExtractor(connection, flatFormExtractorDao);
                flatFormExtractor.getValuesForMappings(workbook, connection);
            }
        } catch (Exception e) {
        	e.printStackTrace();
            throw new ServiceException(e);
            
        }
    }

    private boolean fetchUserReadOnlyAccess(int modelId, int structureElementId) throws ServiceException {
        boolean readOnly;
        try {
            readOnly = cpContextHolder.getDataEntryProcess().getUserReadOnlyAccess(modelId, structureElementId);
        } catch (ValidationException e) {
            logger.error("Error during the fetchUserReadOnlyAccess (modelId=" + modelId + ", structureElementId=" + structureElementId + ")", e);
            throw new ServiceException(e.getMessage(), e);
        }
        return readOnly;
    }

    /**
     * Returns modelVisId related with the specified modelId.
     */
    private String getModelVisId(int modelId) throws ServiceException {
        String modelVisId = null;
        EntityList entity = cpContextHolder.getListHelper().getFinanceCubesForModel(modelId); // FinanceCubesForModelELO
        if (entity.getNumRows() == 0) {
            logger.error("Error during the fetchUserBudgetResponsibilityAreas - Can't find modelId in database (modelId=" + modelId + ")");
            throw new ServiceException("Can't find modelId in database.");
        } else {
            ModelRef modelRef = (ModelRef) entity.getValueAt(0, "Model");
            modelVisId = modelRef.getNarrative();
        }
        return modelVisId;
    }
    
    
}
