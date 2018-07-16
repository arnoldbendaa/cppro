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
package com.softproideas.app.admin.changemanagement.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.cm.AllChangeMgmtsELO;
import com.cedar.cp.dto.cm.ChangeMgmtEditorSessionSSO;
import com.cedar.cp.dto.cm.ChangeMgmtPK;
import com.cedar.cp.dto.model.ModelEditorSessionSSO;
import com.cedar.cp.dto.model.ModelImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.ejb.api.cm.ChangeManagementServer;
import com.cedar.cp.ejb.api.cm.ChangeMgmtEditorSessionServer;
import com.cedar.cp.ejb.api.model.ModelEditorSessionServer;
import com.softproideas.app.admin.changemanagement.mapper.ChangeMgmtMapper;
import com.softproideas.app.admin.changemanagement.model.ChangeMgmtDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

@Service("changeManagementService")
public class ChangeManagementServiceImpl implements ChangeManagementService {
    private static Logger logger = LoggerFactory.getLogger(ChangeManagementServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    @Override
    public List<ChangeMgmtDTO> browseChangeMgmts() throws ServiceException {
        AllChangeMgmtsELO allChangeMgmts = cpContextHolder.getListSessionServer().getAllChangeMgmts();
        List<ChangeMgmtDTO> changeMgmtsDTOList = ChangeMgmtMapper.mapAllChangeMgmtsELO(allChangeMgmts);
        return changeMgmtsDTOList;
    }

    @Override
    public ResponseMessage submit(int changeMgmtId) throws ServiceException {
        String errorMsg = "Error during submit Change Management.";
        try {
            ChangeMgmtEditorSessionServer server = cpContextHolder.getChangeMgmtEditorSessionServer();
            ChangeMgmtPK changeMgmtPK = new ChangeMgmtPK((short) changeMgmtId);
            // server.delete(changeMgmtPK);
            // ChangeMgmtsProcess var12 = (ChangeMgmtsProcess)this.getBusinessProcess();
            // ChangeManagementSession var13 = var12.getChangeManagementSession();

            ChangeMgmtEditorSessionSSO changeMgmt = server.getItemData(changeMgmtPK);
            ModelRef relatedModelRef = changeMgmt.getEditorData().getRelatedModelRef();
            ChangeManagementServer changeManagementServer = new ChangeManagementServer(cpContextHolder.getCPConnection());
            changeManagementServer.issueUpdateTask(relatedModelRef);

            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Validation " + errorMsg);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
        // return null;
    }

    @Override
    public ResponseMessage delete(int changeMgmtId) throws ServiceException {
        String errorMsg = "Error during delete Change Management.";
        try {
            ChangeMgmtEditorSessionServer server = cpContextHolder.getChangeMgmtEditorSessionServer();
            ChangeMgmtPK changeMgmtPK = new ChangeMgmtPK((short) changeMgmtId);
            server.delete(changeMgmtPK);
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Validation " + errorMsg);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error(errorMsg);
            throw new ServiceException(errorMsg);
        }
    }

    /**
     * TODO: Jeśli nie uzywana metoda to usunąc
     */
    @SuppressWarnings("unused")
    private ModelImpl getModelForChangeMgmt(int modelId, ModelEditorSessionServer server) throws ServiceException {
        try {
            ModelPK modelPK = new ModelPK(modelId);
            ModelEditorSessionSSO modelEditorSessionSSO;
            modelEditorSessionSSO = server.getItemData(modelPK);
            ModelImpl modelImpl = modelEditorSessionSSO.getEditorData();
            return modelImpl;
        } catch (CPException e) {
            logger.error("Error during get Model!", e);
            throw new ServiceException("Error during get Model!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during get Model!", e);
            throw new ServiceException("Validation error during get Model!", e);
        }
    }

}
