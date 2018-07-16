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
package com.softproideas.app.admin.externalsystems.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.extsys.AllExternalSystemsELO;
import com.cedar.cp.dto.extsys.ExternalSystemEditorSessionCSO;
import com.cedar.cp.dto.extsys.ExternalSystemEditorSessionSSO;
import com.cedar.cp.dto.extsys.ExternalSystemImpl;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.ejb.api.extsys.ExternalSystemEditorSessionServer;
import com.cedar.cp.ejb.impl.extsys.ExternalSystemEditorSessionSEJB;
import com.softproideas.app.admin.externalsystems.mapper.ExternalSystemMapper;
import com.softproideas.app.admin.externalsystems.model.ExternalSystemDTO;
import com.softproideas.app.admin.externalsystems.model.ExternalSystemDetailsDTO;
import com.softproideas.app.admin.externalsystems.model.SubSystemConfigurationDTO;
import com.softproideas.app.admin.externalsystems.util.ExternalSystemValidatorUtil;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Service("externalSystemService")
public class ExternalSystemServiceImpl implements ExternalSystemService {

    private static Logger logger = LoggerFactory.getLogger(ExternalSystemServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;
    ExternalSystemEditorSessionSEJB server = new ExternalSystemEditorSessionSEJB();

    @Override
    public List<ExternalSystemDTO> browseExternalSystems() throws ServiceException {
        AllExternalSystemsELO allExternalSystemsELO = cpContextHolder.getListSessionServer().getAllExternalSystems();
        List<ExternalSystemDTO> externalSystems = ExternalSystemMapper.mapAllExternalSystemsELO(allExternalSystemsELO);
        return externalSystems;
    }

    @Override
    public ExternalSystemDetailsDTO fetchExternalSystemDetails(int externalSystemId) throws ServiceException {
//        ExternalSystemEditorSessionServer server = cpContextHolder.getExternalSystemEditorSessionServer();
        ExternalSystemImpl externalSystemImpl = getExternalSystemFromServer(server, externalSystemId);
        EntityList xactSubsystems = getAllXactSubsystems(server, externalSystemId);
        ExternalSystemDetailsDTO externalSystem = ExternalSystemMapper.mapExternalSystemImpl(externalSystemImpl, xactSubsystems);
        return externalSystem;
    }

    private ExternalSystemImpl getExternalSystemFromServer(ExternalSystemEditorSessionSEJB server, int externalSystemId) throws ServiceException {
//    	ExternalSystemEditorSessionServer
    	try {
            ExternalSystemEditorSessionSSO sso = null;
            if (externalSystemId != -1) { // edit
                ExternalSystemPK externalSystemPK = new ExternalSystemPK(externalSystemId);
                sso = server.getItemData(cpContextHolder.getUserId(),externalSystemPK);
            } else { // create
                sso = server.getNewItemData(cpContextHolder.getUserId());
            }
            ExternalSystemImpl externalSystemImpl = sso.getEditorData();
            return externalSystemImpl;
        } catch (Exception e) {
            logger.error("Error during fetching external system with id =" + externalSystemId + "!");
            throw new ServiceException("Error during fetching external system with id =" + externalSystemId + "!");
        }
    }

    private EntityList getAllXactSubsystems(ExternalSystemEditorSessionSEJB server, int externalSystemId) throws ServiceException {
        try {
            ExternalSystemPK externalSystemPK = new ExternalSystemPK(externalSystemId);
            EntityList entityList = server.queryAllXactSubsystems(externalSystemPK);
            return entityList;
        } catch (Exception e) {
            logger.error("Error during fetching external system with id =" + externalSystemId + "!");
            throw new ServiceException("Error during fetching external system with id =" + externalSystemId + "!");
        }
    }

    @Override
    public SubSystemConfigurationDTO fetchSubSystemConfiguration(int subSystemId) throws ServiceException {
        ExternalSystemEditorSessionServer server = cpContextHolder.getExternalSystemEditorSessionServer();
        EntityList xactAvailableColumns = getAllXactAvailableColumns(server, subSystemId);
        EntityList xactColumnSelection = getXactColumnSelection(server, subSystemId);
        SubSystemConfigurationDTO subSystemConfiguration = ExternalSystemMapper.mapSubSystemConfiguration(xactAvailableColumns, xactColumnSelection);
        return subSystemConfiguration;
    }

    private EntityList getAllXactAvailableColumns(ExternalSystemEditorSessionServer server, int subSystemId) throws ServiceException {
        try {
            EntityList entityList = server.queryAllXactAvailableColumns(subSystemId);
            return entityList;
        } catch (Exception e) {
            logger.error("Error during fetching XactAvailableColumns with id =" + subSystemId + "!");
            throw new ServiceException("Error during fetching XactAvailableColumns with id =" + subSystemId + "!");
        }
    }

    private EntityList getXactColumnSelection(ExternalSystemEditorSessionServer server, int subSystemId) throws ServiceException {
        try {
            EntityList entityList = server.queryXactColumnSelection(subSystemId);
            return entityList;
        } catch (Exception e) {
            logger.error("Error during fetching XactColumnSelection with id =" + subSystemId + "!");
            throw new ServiceException("Error during fetching XactColumnSelection with id =" + subSystemId + "!");
        }
    }

    @Override
    public ResponseMessage save(ExternalSystemDetailsDTO externalSystemDetails) throws ServiceException {
        ResponseMessage message = null;
        int externalSystemId = externalSystemDetails.getExternalSystemId();

//        ExternalSystemEditorSessionServer server = cpContextHolder.getExternalSystemEditorSessionServer();
        ExternalSystemImpl externalSystemImpl = getExternalSystemFromServer(server, externalSystemId);

        ValidationError error = ExternalSystemValidatorUtil.validateExternalSystemDetails(externalSystemDetails);
        String method = (externalSystemId != -1) ? "edit" : "create";

        if (error.getFieldErrors().isEmpty()) {
            externalSystemImpl = ExternalSystemMapper.mapExternalSystemDetailsDTO(externalSystemImpl, externalSystemDetails);
            message = saveExternalSystemToServer(server, externalSystemImpl, method);
        } else {
            error.setMessage("Validation error during " + method.toUpperCase() + " external system.");
            message = error;
        }
        return message;
    }

    private ResponseMessage saveExternalSystemToServer(ExternalSystemEditorSessionSEJB server, ExternalSystemImpl externalSystemImpl, String operation) throws ServiceException {
        try {
            if (operation.equals("edit")) {
                server.update(new ExternalSystemEditorSessionCSO(cpContextHolder.getUserId(), externalSystemImpl));
            } else if (operation.equals("create")) {
                server.insert(new ExternalSystemEditorSessionCSO(cpContextHolder.getUserId(), externalSystemImpl));
            }
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Validation error during " + operation + " external system.");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error("Error during " + operation + " external system.");
            throw new ServiceException("Error during " + operation + " external system.");
        }
    }

    @Override
    public ResponseMessage delete(int externalSystemId) throws ServiceException {
//        ExternalSystemEditorSessionServer server = cpContextHolder.getExternalSystemEditorSessionServer();
        ExternalSystemPK key = new ExternalSystemPK(externalSystemId);
        try {
            server.delete(cpContextHolder.getUserId(),key);
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Validation error during deleting external system (ID = " + externalSystemId + ").");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error("Error during deleting external system (ID = " + externalSystemId + ").");
            throw new ServiceException("Error during deleting external system (ID = " + externalSystemId + ").");
        }
    }

}
