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
package com.softproideas.app.admin.report.externaldestinations.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.report.destination.external.AllExternalDestinationsELO;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationEditorSessionCSO;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationEditorSessionSSO;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationImpl;
import com.cedar.cp.dto.report.destination.external.ExternalDestinationPK;
import com.cedar.cp.ejb.api.report.destination.external.ExternalDestinationEditorSessionServer;
import com.cedar.cp.ejb.impl.report.destination.external.ExternalDestinationEditorSessionSEJB;
import com.softproideas.app.admin.changemanagement.service.ChangeManagementServiceImpl;
import com.softproideas.app.admin.report.externaldestinations.mapper.ExternalDestinationsMapper;
import com.softproideas.app.admin.report.externaldestinations.model.ExternalDestinationDTO;
import com.softproideas.app.admin.report.externaldestinations.model.ExternalDestinationDetailsDTO;
import com.softproideas.app.admin.report.util.InternalExternalDestinationValidatorUtil;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

@Service("externalDestinationsService")
public class ExternalDestinationsServiceImpl implements ExternalDestinationsService {

    private static Logger logger = LoggerFactory.getLogger(ChangeManagementServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;
    ExternalDestinationEditorSessionSEJB server = new ExternalDestinationEditorSessionSEJB();

    @Override
    public List<ExternalDestinationDTO> browseExternalDestinations() throws ServiceException {
        AllExternalDestinationsELO allExternalDestinations = cpContextHolder.getListSessionServer().getAllExternalDestinations();
        List<ExternalDestinationDTO> externalDestinationsDTOList = ExternalDestinationsMapper.mapAllExternalDestinationsELO(allExternalDestinations);
        return externalDestinationsDTOList;
    }

    @Override
    public ExternalDestinationDetailsDTO fetchReportDetails(int reportId) throws ServiceException {
//        ExternalDestinationEditorSessionServer server = cpContextHolder.getExternalDestinationEditorSessionServer();
        ExternalDestinationImpl externalDestination = getExternalDestinationFromServer(reportId, server);
        ExternalDestinationDetailsDTO externalDestinationDetailsDTO = ExternalDestinationsMapper.mapExternalDestination(externalDestination);
        return externalDestinationDetailsDTO;
    }

    /**
     * Get ExternalDestination details (for update) or new ExternalDestination details object (for insert)
     */
    private ExternalDestinationImpl getExternalDestinationFromServer(int reportId, ExternalDestinationEditorSessionSEJB server) throws ServiceException {
        String errorMsg = "Error during browsing External Destination with id =" + reportId + "!";
        try {
            ExternalDestinationPK externalDestinationPK = new ExternalDestinationPK(reportId);
            ExternalDestinationEditorSessionSSO externalDestinationEditorSessionSSO;
            if (reportId != -1) {
                // edit
                externalDestinationEditorSessionSSO = server.getItemData(cpContextHolder.getUserId(),externalDestinationPK);
            } else {
                // create
                externalDestinationEditorSessionSSO = server.getNewItemData(cpContextHolder.getUserId());
            }
            ExternalDestinationImpl externalDestination = externalDestinationEditorSessionSSO.getEditorData();
            return externalDestination;
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
    public ResponseMessage save(ExternalDestinationDetailsDTO report) throws ServiceException {
        ResponseMessage message = null;
//        ExternalDestinationEditorSessionServer server = cpContextHolder.getExternalDestinationEditorSessionServer();
        ExternalDestinationImpl reportImpl = getExternalDestinationFromServer(report.getReportId(), server);
        ValidationError error = InternalExternalDestinationValidatorUtil.validateExternalDestinationDetails(report);
        String method;
        if (report.getReportId() != -1) {
            // edit Report
            method = "edit";
        } else {
            // create Report
            method = "create";
        }
        if (error.getFieldErrors().isEmpty()) {
            reportImpl = ExternalDestinationsMapper.mapExternalDestinationDetailsDTOToExternalDestinationDetailsImpl(reportImpl, report);
            message = save(reportImpl, method, server);
        } else {
            error.setMessage("Error during " + method + " External Destination.");
            message = error;
        }
        return message;
    }

    /**
     * Save Report details: update or insert
     */
    private ResponseMessage save(ExternalDestinationImpl reportImpl, String operation, ExternalDestinationEditorSessionSEJB server) throws ServiceException {
        String errorMsg = "Error during " + operation + " External Destination.";
        try {
            if (operation.equals("edit")) {
                server.update(new ExternalDestinationEditorSessionCSO(cpContextHolder.getUserId(), reportImpl));
            } else if (operation.equals("create")) {
                server.insert(new ExternalDestinationEditorSessionCSO(cpContextHolder.getUserId(), reportImpl));
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
        String errorMsg = "Error during delete External Destination.";
        try {
//            ExternalDestinationEditorSessionServer server = cpContextHolder.getExternalDestinationEditorSessionServer();
            ExternalDestinationPK externalDestinationPK = new ExternalDestinationPK(reportId);
            server.delete(cpContextHolder.getUserId(),externalDestinationPK);
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
