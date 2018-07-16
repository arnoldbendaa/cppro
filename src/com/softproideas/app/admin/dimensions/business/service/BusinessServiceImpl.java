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
package com.softproideas.app.admin.dimensions.business.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.AllDimensionsELO;
import com.cedar.cp.dto.dimension.AvailableDimensionsELO;
import com.cedar.cp.dto.dimension.DimensionEditorSessionCSO;
import com.cedar.cp.dto.dimension.DimensionEditorSessionSSO;
import com.cedar.cp.dto.dimension.DimensionElementEvent;
import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.ejb.api.dimension.DimensionEditorSessionServer;
import com.cedar.cp.ejb.impl.dimension.DimensionEditorSessionSEJB;
import com.cedar.cp.util.StringUtils;
import com.softproideas.app.admin.dimensions.mapper.DimensionMapper;
import com.softproideas.app.admin.dimensions.model.DimensionDTO;
import com.softproideas.app.admin.dimensions.model.DimensionDetailsDTO;
import com.softproideas.app.admin.dimensions.util.DimensionUtil;
import com.softproideas.app.admin.dimensions.util.DimensionValidatorUtil;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

@Service("businessService")
public class BusinessServiceImpl implements BusinessService {
    private static Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;
    DimensionEditorSessionSEJB server = new DimensionEditorSessionSEJB();

    @Override
    public List<DimensionDTO> browseBusinesses() throws ServiceException {
        AllDimensionsELO allDimensions = cpContextHolder.getListSessionServer().getDimensionsForLoggedUser();
        AvailableDimensionsELO availableDimensions = cpContextHolder.getListSessionServer().getAvailableDimensions();
        List<DimensionDTO> businessDTOList = DimensionMapper.mapAllDimensionsELO(allDimensions, availableDimensions, 2);
        return businessDTOList;
    }

    @Override
    public DimensionDetailsDTO fetchBusinessDetails(int dimensionId) throws ServiceException {
//        DimensionEditorSessionServer server = cpContextHolder.getDimensionEditorSessionServer();
        DimensionImpl dimension = getDimensionFromServer(dimensionId, server);
        CPConnection cpConnection = cpContextHolder.getCPConnection();
        if (dimension.getType() == 2) {
            DimensionDetailsDTO businessDetailsDTO = DimensionMapper.mapDimension(dimension, cpConnection);
            return businessDetailsDTO;
        } else {
            throw new ServiceException("Error during browsing Business with id=" + dimensionId);
        }
    }

    /**
     * Get Business details (for update) or new Business details object (for insert)
     */
    private DimensionImpl getDimensionFromServer(int dimensionId, DimensionEditorSessionSEJB server) throws ServiceException {
        String errorMsg = "Error during browsing Dimension with id=" + dimensionId + "!";
        try {
            DimensionPK dimensionPK = new DimensionPK(dimensionId);
            DimensionEditorSessionSSO dimensionEditorSessionSSO;
            if (dimensionId != -1) {
                // edit Dimension
                dimensionEditorSessionSSO = server.getItemData(cpContextHolder.getUserId(),dimensionPK);
            } else {
                // create Dimension
                dimensionEditorSessionSSO = server.getNewItemData(cpContextHolder.getUserId());
            }
            DimensionImpl dimension = dimensionEditorSessionSSO.getEditorData();
            return dimension;
        } catch (CPException e) {
            logger.error(errorMsg);
            throw new ServiceException(errorMsg);
        } catch (ValidationException e) {
            logger.error("Validation" + errorMsg);
            throw new ServiceException("Validation" + errorMsg);
        }
    }

    /**
     * Validate and map Dimension details (for update or insert)
     */
    @Override
    public ResponseMessage save(DimensionDetailsDTO business) throws ServiceException {
        ResponseMessage message = null;
        // trim dimension visId (visId is the same as "dimensionNarrative" and "identifier")
        String oldDimensionNarrative = business.getDimensionVisId();
        if (oldDimensionNarrative != null) {
            business.setDimensionVisId(StringUtils.rtrim(oldDimensionNarrative));
        }
//        DimensionEditorSessionServer server = cpContextHolder.getDimensionEditorSessionServer();

        DimensionImpl dimensionImpl = getDimensionFromServer(business.getDimensionId(), server);
        ValidationError error = DimensionValidatorUtil.validateDimensionDetails(dimensionImpl, business);

        String method;
        if (business.getDimensionId() != -1) {
            // edit Dimension
            method = "edit";
        } else {
            // create Dimension
            method = "create";
        }
        if (error.getFieldErrors().isEmpty()) {
            dimensionImpl = DimensionMapper.mapDimensionDetailsDTOToDimensionDetailsImpl(dimensionImpl, business);
            // Dimension Elements are not saved in Dimension insert() or update() method. System creates task to do it. clientEvents is data for that task.
            HashMap<String, ArrayList<DimensionElementEvent>> clientEvents = DimensionUtil.prepareDimensionElementsToSave(dimensionImpl, business);
            message = save(dimensionImpl, clientEvents, method, server);
        } else {
            error.setMessage("Error during " + method + " Dimension Business.");
            message = error;
        }
        return message;
    }

    /**
     * Save Dimension details: update or insert
     */
    private ResponseMessage save(DimensionImpl dimensionImpl, HashMap<String, ArrayList<DimensionElementEvent>> clientEvents, String operation, DimensionEditorSessionSEJB server) throws ServiceException {
        String errorMsg = "Error during " + operation + " Dimension Business.";
        try {
            if (operation.equals("edit")) {
                dimensionImpl.setConnectionContext(cpContextHolder.getCPConnection().getConnectionContext());
                // call tasks with "insert", "update" and/or "remove" events on Dimension Elements
                server.processEvents(clientEvents.get("remove"));
                server.processEvents(clientEvents.get("update"));
                server.processEvents(clientEvents.get("insert"));
                server.update(new DimensionEditorSessionCSO(cpContextHolder.getUserId(), dimensionImpl));
            } else if (operation.equals("create")) {
                // TODO set set ConnectionContext ?
                dimensionImpl.setConnectionContext(cpContextHolder.getCPConnection().getConnectionContext());
                // call tasks with "insert", "update" and/or "remove" events on Dimension Elements
                server.processEvents(clientEvents.get("remove"));
                server.processEvents(clientEvents.get("update"));
                server.processEvents(clientEvents.get("insert"));
                server.insert(new DimensionEditorSessionCSO(cpContextHolder.getUserId(), dimensionImpl));
            }
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
     * Delete Dimension
     */
    @Override
    public ResponseMessage delete(int dimensionId) throws ServiceException {
        String errorMsg = "Error during delete Dimension Business.";
        try {
//            DimensionEditorSessionServer server = cpContextHolder.getDimensionEditorSessionServer();
            DimensionPK dimensionPK = new DimensionPK(dimensionId);
            server.delete(cpContextHolder.getUserId(),dimensionPK);
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

}
