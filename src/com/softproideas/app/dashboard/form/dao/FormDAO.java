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
package com.softproideas.app.dashboard.form.dao;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cedar.cp.api.base.ValidationException;
import com.softproideas.app.dashboard.form.model.DashboardDTO;
import com.softproideas.app.dashboard.form.model.HierarchyElement;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.commons.model.ResponseMessage;

public interface FormDAO {

    boolean deleteFreeForm(UUID id) throws DaoException;

    DashboardDTO getFreeFormByUUID(UUID id) throws DaoException, IOException, ClassNotFoundException;

    ResponseMessage insertOrUpdateFreeForm(DashboardDTO freeForm) throws DaoException, ValidationException;

    HierarchyElement getContextData(String structureElementVisId, int modelId);
    
    Integer exchangeForModelId(Integer financeCubeId);

}
