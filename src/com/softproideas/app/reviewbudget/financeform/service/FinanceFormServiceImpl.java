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
package com.softproideas.app.reviewbudget.financeform.service;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import javax.ejb.EJBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.model.BudgetCyclesProcess;
import com.cedar.cp.api.model.OnDemandFinanceFormData;
import com.cedar.cp.api.model.ReviewBudgetDetails;
import com.cedar.cp.api.model.ReviewBudgetSelection;
import com.cedar.cp.api.model.cc.RuntimeCubeDeployment;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.cedar.cp.util.xmlform.FinanceCubeInput;
import com.softproideas.app.reviewbudget.budget.model.ReviewBudgetDTO;
import com.softproideas.app.reviewbudget.financeform.mapper.FinanceFormMapper;
import com.softproideas.app.reviewbudget.financesystem.service.FinanceSystemServiceImpl;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;

/**
 * 
 * @author Marcin Grochulski, Łukasz Puła
 * @email magro@softproideas.com, lukasz.pula@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
@Service("financeFormService")
public class FinanceFormServiceImpl implements FinanceFormService {

    private static Logger logger = LoggerFactory.getLogger(FinanceSystemServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    @SuppressWarnings("unchecked")
    private OnDemandFinanceFormData getFinanceForm(ReviewBudgetDetails reviewBudgetDetails, int topNodeId, int dataEntryProfileId, int modelId, int budgetCycleId, Map<Integer, Integer> selectionsMap, String dataType, int sheetProtectionLevel, int childrenDepth, boolean secondaryStructure, boolean noData) throws Exception {
        logger.debug(Thread.currentThread().getName() + " GetFinanceFormDataAction started");
        BudgetCyclesProcess process = cpContextHolder.getCPContext().getCPConnection().getBudgetCyclesProcess();
        
        System.out.println(cpContextHolder.getCPContextId());
        
        // prepere tree Structure Indexs
        List<Object> cpTreeStructureIndexes = reviewBudgetDetails.getTreeStructureIndexes();
        int[] treeStructureIndexes;
        if (cpTreeStructureIndexes != null) {
            treeStructureIndexes = new int[cpTreeStructureIndexes.size()];
        } else {
            treeStructureIndexes = new int[0];
        }
        // mFinanceCubeInput -> xAxisIndex

        FinanceCubeInput financeCubeInput = (FinanceCubeInput) reviewBudgetDetails.getContextVariables().get("cedar.financeCubeInput.key");
        int firstAxisDimension;
        if (financeCubeInput != null) {
            List<Object> AxisDimensions = financeCubeInput.getAxisDimensionIndexes();
            firstAxisDimension = ((Integer) AxisDimensions.get(0)).intValue();
        } else {
            firstAxisDimension = 0;
        }

        // ReviewBudgetSelection
        ReviewBudgetSelection[] selections = reviewBudgetDetails.getDimensionSelections();
        int[] structureElementIds = new int[selections.length];

        for (int relevantCellCalculations = 0; relevantCellCalculations < structureElementIds.length; ++relevantCellCalculations) {
            structureElementIds[relevantCellCalculations] = selections[relevantCellCalculations].getCurrentOption();
        }

        // relevantCellCalcs
        int[] relevantCellCalcs = new int[reviewBudgetDetails.getCubeDeployments().size()];

        for (int data = 0; data < relevantCellCalcs.length; ++data) {
            RuntimeCubeDeployment formData = (RuntimeCubeDeployment) reviewBudgetDetails.getCubeDeployments().get(data);
            relevantCellCalcs[data] = formData.getOwnerId();
        }

        // calendar Info
        CalendarInfo calendarInfo = (CalendarInfo) reviewBudgetDetails.getContextVariables().get(WorkbookProperties.CALENDAR_INFO.toString() + modelId);

        try {
            OnDemandFinanceFormData financeFormData = process.getFinanceFormDataRows(cpContextHolder.getCPContext().getUserContext().getUserId(), // ok
                    modelId, budgetCycleId, financeCubeInput, 0, treeStructureIndexes, cpContextHolder.getCPContextId(), firstAxisDimension, structureElementIds, childrenDepth, secondaryStructure, noData, relevantCellCalcs,// ?
                    reviewBudgetDetails.getDataTypes(), reviewBudgetDetails.getSecurityAccessDetails(), calendarInfo);

            financeCubeInput = null;
            logger.debug(Thread.currentThread().getName() + " GetFinanceFormDataAction ended");
            return financeFormData;
        } catch (Exception e) {
            Throwable t = e.getCause();
            if (t instanceof RemoteException) {
                RemoteException re = (RemoteException) t;
                t = re.getCause();
                if (t instanceof EJBException) {
                    EJBException ejb = (EJBException) t;
                    throw ejb.getCausedByException();
                }
            }
            throw e;
        }
    }

    @Override
    public ReviewBudgetDTO fetchFinanceForm(ReviewBudgetDetails reviewBudgetDetails, int topNodeId, int dataEntryProfileId, int modelId, int budgetCycleId, Map<Integer, Integer> selectionsMap, String dataType) throws ServiceException {
        try {
            OnDemandFinanceFormData rootData = this.getFinanceForm(reviewBudgetDetails, topNodeId, dataEntryProfileId, modelId, budgetCycleId, selectionsMap, dataType, 0, 0, false, false);
            OnDemandFinanceFormData childrenData = this.getFinanceForm(reviewBudgetDetails, topNodeId, dataEntryProfileId, modelId, budgetCycleId, selectionsMap, dataType, 0, 999, false, false);
            return FinanceFormMapper.mapToFinanceFormDTO(rootData, childrenData, reviewBudgetDetails);
        } catch (Exception e) {
            logger.error("Error during fetching finance form!", e.getMessage());
            throw new ServiceException("Error during fetching finance form!");
        }
    }
}