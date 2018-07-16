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
package com.softproideas.app.admin.report.internaldestinations.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.destination.internal.AllInternalDestinationsELO;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationEditorSessionCSO;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationEditorSessionSSO;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationImpl;
import com.cedar.cp.dto.report.destination.internal.InternalDestinationPK;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.ejb.api.report.destination.internal.InternalDestinationEditorSessionServer;
import com.cedar.cp.ejb.impl.report.destination.internal.InternalDestinationEditorSessionSEJB;
import com.softproideas.app.admin.changemanagement.service.ChangeManagementServiceImpl;
import com.softproideas.app.admin.report.internaldestinations.mapper.InternalDestinationsMapper;
import com.softproideas.app.admin.report.internaldestinations.model.InternalDestinationDTO;
import com.softproideas.app.admin.report.internaldestinations.model.InternalDestinationDetailsDTO;
import com.softproideas.app.admin.report.util.InternalExternalDestinationValidatorUtil;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

@Service("internalDestinationsService")
public class InternalDestinationsServiceImpl implements InternalDestinationsService {

    private static Logger logger = LoggerFactory.getLogger(ChangeManagementServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;
    InternalDestinationEditorSessionSEJB server = new InternalDestinationEditorSessionSEJB();

    @Override
    public List<InternalDestinationDTO> browseInternalDestinations() throws ServiceException {
        AllInternalDestinationsELO allInternalDestinations = cpContextHolder.getListSessionServer().getAllInternalDestinations();
        List<InternalDestinationDTO> internalDestinationsDTOList = InternalDestinationsMapper.mapAllInternalDestinationsELO(allInternalDestinations);
        return internalDestinationsDTOList;
    }

    @Override
    public InternalDestinationDetailsDTO fetchReportDetails(int reportId) throws ServiceException {
//        InternalDestinationEditorSessionServer server = cpContextHolder.getInternalDestinationEditorSessionServer();
        InternalDestinationImpl internalDestination = getInternalDestinationFromServer(reportId, server);
        AllUsersELO allUsersELO = cpContextHolder.getListSessionServer().getAllUsers();
        InternalDestinationDetailsDTO internalDestinationDetailsDTO = InternalDestinationsMapper.mapInternalDestination(internalDestination, allUsersELO);
        return internalDestinationDetailsDTO;
    }

    /**
     * Get InternalDestination details (for update) or new InternalDestination details object (for insert)
     */
    private InternalDestinationImpl getInternalDestinationFromServer(int reportId, InternalDestinationEditorSessionSEJB server) throws ServiceException {
        String errorMsg = "Error during browsing Internal Destination with id =" + reportId + "!";
        try {
            InternalDestinationPK internalDestinationPK = new InternalDestinationPK(reportId);
            InternalDestinationEditorSessionSSO internalDestinationEditorSessionSSO;
            if (reportId != -1) {
                // edit
                internalDestinationEditorSessionSSO = server.getItemData(cpContextHolder.getUserId(),internalDestinationPK);
            } else {
                // create
                internalDestinationEditorSessionSSO = server.getNewItemData(cpContextHolder.getUserId());
            }
            InternalDestinationImpl internalDestination = internalDestinationEditorSessionSSO.getEditorData();
            return internalDestination;
        } catch (CPException e) {
            logger.error(errorMsg);
            throw new ServiceException(errorMsg);
        } catch (ValidationException e) {
            logger.error("Validation " + errorMsg);
            throw new ServiceException("Validation " + errorMsg);
        }
    }

    /**
     * Validate and map Report details (for update or insert)
     */
    @Override
    public ResponseMessage save(InternalDestinationDetailsDTO report) throws ServiceException {
        ResponseMessage message = null;
//        InternalDestinationEditorSessionServer server = cpContextHolder.getInternalDestinationEditorSessionServer();
        InternalDestinationImpl reportImpl = getInternalDestinationFromServer(report.getReportId(), server);
        ValidationError error = InternalExternalDestinationValidatorUtil.validateInternalDestinationDetails(report);
        String method;
        if (report.getReportId() != -1) {
            // edit Report
            method = "edit";
        } else {
            // create Report
            method = "create";
        }
        if (error.getFieldErrors().isEmpty()) {
            reportImpl = InternalDestinationsMapper.mapInternalDestinationDetailsDTOToInternalDestinationDetailsImpl(reportImpl, report);
            message = save(reportImpl, method, server);
        } else {
            error.setMessage("Error during " + method + " Internal Destination.");
            message = error;
        }
        return message;
    }

    /**
     * Save Report details: update or insert
     */
    private ResponseMessage save(InternalDestinationImpl reportImpl, String operation, InternalDestinationEditorSessionSEJB server) throws ServiceException {
        String errorMsg = "Error during " + operation + " Internal Destination.";
        try {
            if (operation.equals("edit")) {
                server.update(new InternalDestinationEditorSessionCSO(cpContextHolder.getUserId(), reportImpl));
            } else if (operation.equals("create")) {
                server.insert(new InternalDestinationEditorSessionCSO(cpContextHolder.getUserId(), reportImpl));
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
     * Delete Report
     */
    @Override
    public ResponseMessage delete(int reportId) throws ServiceException {
        String errorMsg = "Error during delete Internal Destination.";
        try {
//            InternalDestinationEditorSessionServer server = cpContextHolder.getInternalDestinationEditorSessionServer();
            InternalDestinationPK internalDestinationPK = new InternalDestinationPK(reportId);
            server.delete(cpContextHolder.getUserId(),internalDestinationPK);
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
