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
package com.softproideas.app.core.profile.service;

import java.sql.SQLException;
import java.util.List;

import com.cedar.cp.utc.common.CPContext;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDetailsDTO;
//import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDetailsDTO;
import com.softproideas.app.core.form.model.FormDTO;
import com.softproideas.app.core.profile.model.ProfileDTO;
import com.softproideas.common.exceptions.ServiceException;

public interface ProfileService {

    /**
     * Browses profiles for logged user with modelId and budgetCycleId
     */
    List<ProfileDTO> browseProfiles(int modelId, int budgetCycleId, int selectedStructureElementId) throws ServiceException;

    /**
     * Browse form list for logged user with modelId and budgetCycleId
     */
    List<FormDTO> browseFormList(int modelId, int budgetCycleId) throws ServiceException;

    void deleteProfilesForForm(int flatFormId, List<Integer> userIdList, boolean b) throws ServiceException;

    void insertProfileForForm(int flatFormId, List<Integer> userIdList, boolean b) throws ServiceException;

    void deleteProfiles(List<Integer> modelIds, List<Integer> userIds) throws ServiceException;

    void createProfiles(Integer modelId, Integer userId) throws SQLException, ServiceException;

    /**
     * Delete all profiles defined by userId and list of flatFormId.
     */
    void deleteProfilesForUser(List<Integer> flatFormIdList, int userId) throws ServiceException;

    /**
     * Create profile at database for Flat Form and User defined by userId and  list of flatFormId.
     */
    void insertProfileForUser(List<Integer> flatFormIdList, int userId, List<Integer> modelsIdsFlatFormsInsertedBefore) throws ServiceException;

    /**
     * Delete all profiles Flat Forms lose last assign to Budget Cycle.
     */
    void deleteProfilesForBudgetCycle(List<Integer> flatFormIdList, int budgetCycleId) throws ServiceException;

    /**
     * Create profile at database for Flat Forms and Users when Flat Forms was connection witch Budget Cycle.
     */
    void insertProfileForBudgetCycle(List<Integer> formToAddList, BudgetCycleDetailsDTO budgetCycle) throws ServiceException;//arnold
    
    void deleteAllProfilesForUser(int userId) throws ServiceException;
    
    void deleteAllProfilesForBudgetCycle(int budgetCycleId) throws ServiceException;

	void Init(CPContext context);
}
