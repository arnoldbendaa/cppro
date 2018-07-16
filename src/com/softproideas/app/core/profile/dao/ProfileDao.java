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
/**
 * 
 */
package com.softproideas.app.core.profile.dao;

import java.sql.SQLException;
import java.util.List;

import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDetailsDTO;
//import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDetailsDTO;
import com.softproideas.app.core.profile.model.UserIdXmlForm;
import com.softproideas.common.exceptions.DaoException;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public interface ProfileDao {

    /**
     * Delete all profiles from database defined by flatFormId and userId.
     * @param isInUserTable 
     */
    void deleteProfilesForForm(int flatFormId, List<Integer> userIdList, boolean isInUserTable);

    /**
     * Create profile at database for Flat Form and User defined by flatFormId and userId.
     * @param isInUserTable 
     */
    void insertProfileForForm(int flatFormId, List<Integer> userIdList, boolean isInUserTable) throws DaoException;

    /**
     * Delete all profiles defined by userId and list of flatFormId.
     */
    void deleteProfilesForUser(List<Integer> flatFormIdList, int userId) throws DaoException;

    /**
     * Create profile at database for Flat Form and User defined by userId and  list of flatFormId.
     */
    void insertProfileForUser(List<Integer> flatFormIdList, int userId, List<Integer> modelsIdsFlatFormsInsertedBefore) throws DaoException;

    void deleteProfiles(List<Integer> modelId, List<Integer> userIds) throws DaoException;

    List<UserIdXmlForm> getXmlFormsForCreateMissingProfiles(Integer modelId, Integer userId) throws DaoException;

    void createProfilesByModel(List<UserIdXmlForm> pairs, Integer modelId) throws SQLException;

    /**
     * Delete all profiles Flat Forms lose last assign to Budget Cycle.
     */
    void deleteProfilesForBudgetCycle(List<Integer> flatFormIdList, int budgetCycleId) throws DaoException;

    /**
     * Create profile at database for Flat Forms and Users when Flat Forms was connection witch Budget Cycle.
     */
    void insertProfileForBudgetCycle(List<Integer> flatFormIdList, BudgetCycleDetailsDTO budgetCycle) throws DaoException;//arnold

	void deleteAllProfilesForUser(int userId) throws DaoException;

	void deleteAllProfilesForBudgetCycle(int budgetCycleId) throws DaoException;
}
