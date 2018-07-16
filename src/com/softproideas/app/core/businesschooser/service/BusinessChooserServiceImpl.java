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
package com.softproideas.app.core.businesschooser.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.budgetlocation.BudgetLocationEditorSessionSSO;
import com.cedar.cp.dto.budgetlocation.BudgetLocationImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.api.base.ListSessionServer;
import com.cedar.cp.ejb.api.budgetlocation.BudgetLocationEditorSessionServer;
import com.softproideas.app.admin.modelusersecurity.services.ModelUserSecurityServiceImpl;
import com.softproideas.app.core.businesschooser.mapper.BusinessChooserMapper;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.tree.NodeStaticWithIdAndDescriptionDTO;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Service("businessChooserService")
public class BusinessChooserServiceImpl implements BusinessChooserService {

    private static Logger logger = LoggerFactory.getLogger(ModelUserSecurityServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

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
            logger.error("Error during get Business Tree!", e);
            throw new ServiceException("Error during get Business Tree!", e);
        } catch (ValidationException e) {
            logger.error("Error during get Business Tree!", e);
            throw new ServiceException("Error during get Business Tree!", e);
        }
    }

    /**
     *
     */
    @Override
    public NodeStaticWithIdAndDescriptionDTO fetchBusinessTree(int modelId) throws ServiceException {
        NodeStaticWithIdAndDescriptionDTO nodeStaticWithIdAndDescriptionDTO;
        BudgetLocationEditorSessionServer server = cpContextHolder.getBudgetLocationEditorSessionServer();
        BudgetLocationImpl budgetLocationImpl = getItemData(modelId, server);
        ListSessionServer listSessionServer = cpContextHolder.getListSessionServer();
        nodeStaticWithIdAndDescriptionDTO = BusinessChooserMapper.mapBudgetLocationTree(budgetLocationImpl, listSessionServer, cpContextHolder.getUserId());
        return nodeStaticWithIdAndDescriptionDTO;
    }

    /**
     *
     */
    @Override
    public List<String> fetchSecurityListForUserAndModel(int modelId) throws ServiceException {
        BudgetLocationEditorSessionServer server = cpContextHolder.getBudgetLocationEditorSessionServer();
        BudgetLocationImpl budgetLocationImpl = getItemData(modelId, server);
        ListSessionServer listSessionServer = cpContextHolder.getListSessionServer();
        List<String> securityList = BusinessChooserMapper.generateSecurityList(budgetLocationImpl, listSessionServer, cpContextHolder.getUserId());
        return securityList;
    }

}
