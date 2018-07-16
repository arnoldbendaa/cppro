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
package com.softproideas.app.admin.dimensions.calendar.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.dto.dimension.AllHierarchysELO;
import com.cedar.cp.dto.dimension.AvailableDimensionsELO;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.calendar.CalendarEditorSessionCSO;
import com.cedar.cp.dto.dimension.calendar.CalendarEditorSessionSSO;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.ejb.api.dimension.calendar.CalendarEditorSessionServer;
import com.cedar.cp.ejb.impl.datatype.DataTypeEditorSessionSEJB;
import com.cedar.cp.ejb.impl.dimension.calendar.CalendarEditorSessionSEJB;
import com.cedar.cp.util.StringUtils;
import com.softproideas.app.admin.dimensions.calendar.mapper.CalendarMapper;
import com.softproideas.app.admin.dimensions.calendar.model.CalendarDTO;
import com.softproideas.app.admin.dimensions.calendar.model.CalendarDetailsDTO;
import com.softproideas.app.admin.dimensions.util.DimensionValidatorUtil;
import com.softproideas.app.reviewbudget.dimension.model.DimensionDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

@Service("calendarService")
public class CalendarServiceImpl implements CalendarService {
    private static Logger logger = LoggerFactory.getLogger(CalendarServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;
    CalendarEditorSessionSEJB server = new CalendarEditorSessionSEJB();
    @Override
    public List<CalendarDTO> browseCalendars() throws ServiceException {
        AllHierarchysELO allDimensions = cpContextHolder.getListSessionServer().getHierarchiesForLoggedUser();
        AvailableDimensionsELO availableDimensions = cpContextHolder.getListSessionServer().getAvailableDimensions();
        List<CalendarDTO> calendarDTOList = CalendarMapper.mapAllCalendarELO(allDimensions, availableDimensions, cpContextHolder);
        return calendarDTOList;
    }

    @Override
    public CalendarDetailsDTO fetchCalendarDetails(Integer dimensionId, Integer hierarchyId) throws ServiceException, ValidationException {
//        CalendarEditorSessionServer server = cpContextHolder.getCalendarEditorSessionServer();
        CalendarImpl dimension = getDimensionFromServer(dimensionId, hierarchyId, server);
        if (dimension.getDimensionRef().getType() == 3) {
            CalendarDetailsDTO calendarDetailsDTO = CalendarMapper.mapDimension(dimension, cpContextHolder);
            return calendarDetailsDTO;
        } else {
            throw new ServiceException("Error during browsing Calendar with id=" + dimensionId);
        }
    }

    /**
     * Get Calendar details (for update) or new Calendar details object (for insert)
     */
    private CalendarImpl getDimensionFromServer(Integer dimensionId, Integer hierarchyId, CalendarEditorSessionSEJB server) throws ServiceException {
        String errorMsg = "Error during browsing Dimension with id=" + dimensionId + "!";
        try {
            DimensionPK dimensionPK = new DimensionPK(dimensionId);
            HierarchyPK hierarchyPK = new HierarchyPK(hierarchyId);
            CalendarEditorSessionSSO calendarEditorSessionSSO;
            if (dimensionId != -1) {
                HierarchyCK hierarchyCK = new HierarchyCK(dimensionPK, hierarchyPK);
                calendarEditorSessionSSO = server.getItemData(cpContextHolder.getUserId(),hierarchyCK);
            } else {
                calendarEditorSessionSSO = server.getNewItemData(cpContextHolder.getUserId());
            }
            CalendarImpl dimension = calendarEditorSessionSSO.getEditorData();
            return dimension;
        } catch (CPException e) {
            logger.error(errorMsg);
            throw new ServiceException(errorMsg);
        } catch (ValidationException e) {
            logger.error("Validation" + errorMsg);
            throw new ServiceException("Validation" + errorMsg);
        }
    }

    @Override
    public List<DimensionDTO> browseCalendarsForModelVisId(String modelVisId) {
        HashSet<String> modelVisIds = new HashSet<String>();
        modelVisIds.add(modelVisId);

        HashMap<String, ArrayList<HierarchyRef>> calendar = cpContextHolder.getListSessionServer().getCalendarForModels(modelVisIds);
        return CalendarMapper.mapHashMapWithHierarchyRefToDTO(calendar);
    }

    /**
     * Validate and map Dimension details (for update or insert)
     */
    @Override
    public ResponseMessage save(CalendarDetailsDTO calendar) throws ServiceException {
        ResponseMessage message = null;
        
        // trim dimension visId (visId is the same as "dimensionNarrative" and "identifier")
        String oldDimensionNarrative = calendar.getDimensionVisId();
        if (oldDimensionNarrative != null) {
            calendar.setDimensionVisId(StringUtils.rtrim(oldDimensionNarrative));
        }
//        CalendarEditorSessionServer server = cpContextHolder.getCalendarEditorSessionServer();

        CalendarImpl dimensionImpl = getDimensionFromServer(calendar.getDimensionId(), calendar.getHierarchy().getHierarchyId(), server);
        ValidationError error = DimensionValidatorUtil.validateCalendarDetails(dimensionImpl, calendar);

        String method;
        if (calendar.getDimensionId() != -1) {
            method = "edit";
        } else {
            method = "create";
        }
        if (error.getFieldErrors().isEmpty()) {
            dimensionImpl = CalendarMapper.mapCalendarDetailsDTOToCalendarDetailsImpl(dimensionImpl, calendar);
            message = save(dimensionImpl, method, server);
        } else {
            error.setMessage("Error during " + method + " Dimension Calendar.");
            message = error;
        }
        return message;
    }

    /**
     * Save Dimension details: update or insert
     */
    private ResponseMessage save(CalendarImpl dimensionImpl, String operation, CalendarEditorSessionSEJB server) throws ServiceException {
        String errorMsg = "Error during " + operation + " Dimension Calendar.";
        try {
            if (operation.equals("edit")) {
                server.update(new CalendarEditorSessionCSO(cpContextHolder.getUserId(), dimensionImpl));
            } else if (operation.equals("create")) {
                server.insert(new CalendarEditorSessionCSO(cpContextHolder.getUserId(), dimensionImpl));
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
    public ResponseMessage delete(int dimensionId, int hierarchyId) throws ServiceException {
        String errorMsg = "Error during delete Dimension Calendar.";
        try {
//            CalendarEditorSessionServer server = cpContextHolder.getCalendarEditorSessionServer();

            DimensionPK dimensionPK = new DimensionPK(dimensionId);
            HierarchyPK hierarchyPK = new HierarchyPK(hierarchyId);
            HierarchyCK hierarchyCK = new HierarchyCK(dimensionPK, hierarchyPK);
            server.delete(cpContextHolder.getUserId(),hierarchyCK);
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
