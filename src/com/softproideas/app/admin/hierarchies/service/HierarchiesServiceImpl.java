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
package com.softproideas.app.admin.hierarchies.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyEvent;
import com.cedar.cp.api.dimension.HierarchyNode;
import com.cedar.cp.dto.dimension.AllHierarchysELO;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionSelectionEvent;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyEditorSessionCSO;
import com.cedar.cp.dto.dimension.HierarchyEditorSessionSSO;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.event.HierarchyElementEvent;
import com.cedar.cp.ejb.api.dimension.HierarchyEditorSessionServer;
import com.cedar.cp.ejb.impl.dimension.HierarchyEditorSessionSEJB;
import com.softproideas.app.admin.hierarchies.mapper.HierarchiesMapper;
import com.softproideas.app.admin.hierarchies.model.HierarchyDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyDetailsDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyNodeLazyDTO;
import com.softproideas.app.admin.hierarchies.util.HierarchiesEventUtil;
import com.softproideas.app.admin.hierarchies.util.HierarchyValidatorUtil;
import com.softproideas.app.core.dimension.model.DimensionCoreDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

/**
 * <p>This service is aimed to take care of hierarchies CRUD operations,
 * fetch all available dimensions for create new hierarchy
 * and browse Node</p>
 *
 * @author Szymon Zberaz, Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Service("hierarchiesService")
public class HierarchiesServiceImpl implements HierarchiesService {
    private static Logger logger = LoggerFactory.getLogger(HierarchiesServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    
    /**
     * Returns list of Account hierarchies 
     */
    @Override
    public List<HierarchyDTO> browseHierarchy(boolean type) throws ServiceException {
        AllHierarchysELO allHierarchysELO = cpContextHolder.getListSessionServer().getAllHierarchys();
        int typeNumber;
        if(type) {
            typeNumber = 1; //type is account
        } else {
            typeNumber = 2; //type is business
        }
        List<HierarchyDTO> hierarchiesDTO = HierarchiesMapper.mapAllHierarchies(allHierarchysELO, typeNumber);
        return hierarchiesDTO;
    }

    /**
     * Returns account hierarchies details DTO
     */
    @SuppressWarnings("unchecked")
    @Override
    public HierarchyDetailsDTO fetchHierarchyDetails(Integer dimensionId, Integer hierarchyId, boolean type) throws ServiceException {
//        HierarchyEditorSessionServer server = cpContextHolder.getHierarchyEditorSessionServer();
    	HierarchyEditorSessionSEJB server =new HierarchyEditorSessionSEJB();
        HierarchyEditorSessionSSO sso = getHierarchySSO(dimensionId, hierarchyId, HierarchyEditorSessionServer.server, type);
 //       HierarchyEditorSessionSSO sso = getHierarchySSO(dimensionId, hierarchyId, server, type);
        HierarchyImpl hierarchy = sso.getEditorData();
        List<HierarchyEvent> events = sso.getEvents();
        CPConnection cpConnection = cpContextHolder.getCPConnection();
        HierarchyDetailsDTO hierarchyDetailsDTO = HierarchiesMapper.mapHierarchiesDetails(hierarchy, cpConnection, events, type);
        return hierarchyDetailsDTO;
    }

    /**
     * Method get getHierarchyFromServer
     */
    private HierarchyEditorSessionSSO getHierarchySSO(Integer dimensionId, Integer hierarchyId, HierarchyEditorSessionSEJB server, boolean type) throws ServiceException {
        String errorMsg;
        if(type == true) {
            errorMsg = "Error during browsing Hierarchy Account with id=" + hierarchyId + "!";
        } else {
            errorMsg = "Error during browsing Hierarchy Business with id=" + hierarchyId + "!";
        }
        try {
            DimensionPK dimensionPK = new DimensionPK(dimensionId);
            HierarchyPK hierarchyPK = new HierarchyPK(hierarchyId);
            HierarchyEditorSessionSSO hierarchyEditorSessionSSO;
            if (hierarchyId != -1 && dimensionId != -1) {
                HierarchyCK hierarchyCK = new HierarchyCK(dimensionPK, hierarchyPK);
                hierarchyEditorSessionSSO = server.getItemData(cpContextHolder.getUserId(),hierarchyCK);
            } else {
                hierarchyEditorSessionSSO = server.getNewItemData(cpContextHolder.getUserId());
            }

            return hierarchyEditorSessionSSO;
        } catch (CPException e) {
            logger.error(errorMsg);
            throw new ServiceException(errorMsg, e);
        } catch (ValidationException e) {
            logger.error("Validation" + errorMsg);
            throw new ServiceException("Validation" + errorMsg, e);
        }
    }

    /**
     * Get details for selected Hierarchies from database.
     */
    private HierarchyImpl getHierarchyFromServer(Integer dimensionId, Integer hierarchyId, HierarchyEditorSessionSEJB server, boolean type) throws ServiceException {
        HierarchyEditorSessionSSO sso = getHierarchySSO(dimensionId, hierarchyId, server, type);
        HierarchyImpl hierarchyImpl = sso.getEditorData();
        return hierarchyImpl;
    }

    /**
     * Get root for Hierarchies
     */
    @Override
	public List<HierarchyNodeLazyDTO> browseHierarchysNode(Integer dimensionId, Integer hierarchyId, boolean type) throws ServiceException {
//      HierarchyEditorSessionServer server = cpContextHolder.getHierarchsyEditorSessionServer();
    	HierarchyEditorSessionSEJB server =new HierarchyEditorSessionSEJB();
        HierarchyImpl hierarchy = getHierarchyFromServer(dimensionId, hierarchyId, HierarchyEditorSessionServer.server, type);
 //       HierarchyImpl hierarchy = getHierarchyFromServer(dimensionId, hierarchyId, server, type);
      List<HierarchyNodeLazyDTO> root = HierarchiesMapper.mapHierarchiesForModelELO(hierarchy);
      return root;
    }

    /**
     * Get children for root
     */
    @Override
    public List<HierarchyNodeLazyDTO> browseRoot(Integer dimensionId, Integer hierarchyId, Integer hierarchyElementId, boolean type) throws ServiceException {
//        HierarchyEditorSessionServer server = cpContextHolder.getHierarchyEditorSessionServer();
        HierarchyImpl hierarchy = getHierarchyFromServer(dimensionId, hierarchyId, HierarchyEditorSessionServer.server, type);
        HierarchyNode impl = hierarchy.getRoot();
        List<HierarchyNodeLazyDTO> node = HierarchiesMapper.mapImmediateChildrenELO(impl, hierarchyElementId);
        return node;
    }

    /**
     * Save changing or create new data account hierarchies details
     */
    @Override
    public ResponseMessage save(HierarchyDetailsDTO hierarchy, boolean type) throws ServiceException, ValidationException {
        try {
            ResponseMessage message = null;
            HierarchyImpl hierarchyImpl = null;
            ArrayList<HierarchyElementEvent> clientEvents = null;
//            HierarchyEditorSessionServer server = cpContextHolder.getHierarchyEditorSessionServer();
            String method;
            int dimensionId = hierarchy.getDimension().getDimensionId();
            
            if (hierarchy.getHierarchyId() == -1) {
                method = "create";
                hierarchyImpl = getHierarchyFromServer(dimensionId, -1, HierarchyEditorSessionServer.server, type);
                hierarchyImpl = HierarchiesMapper.mapHierarchyDetailsDTOToHierarchyDetailsImpl(hierarchyImpl, hierarchy);
                clientEvents = HierarchiesEventUtil.prepareElementsToSave(hierarchy);
            } else {
                method = "edit";
                hierarchyImpl = getHierarchyFromServer(dimensionId, hierarchy.getHierarchyId(), HierarchyEditorSessionServer.server, type);
                hierarchyImpl = HierarchiesMapper.mapHierarchyDetailsDTOToHierarchyDetailsImpl(hierarchyImpl, hierarchy);
                // Dimension Elements are not saved in Dimension insert() or update() method. System creates task to do it. clientEvents is data for that task.
                clientEvents = HierarchiesEventUtil.prepareElementsToSave(hierarchy);
            }

            ValidationError error = HierarchyValidatorUtil.validateHierarchyDetails(hierarchy);
            if (error.getFieldErrors().isEmpty()) {
                message = save(hierarchyImpl, clientEvents, method, HierarchyEditorSessionServer.server, hierarchy);
            } else {
                error.setMessage("Error during Hierarchy " + method + " operation!");
                return error;
            }

            return message;
        } catch (ValidationException e) {
            ValidationError error = new ValidationError();
            error.addFieldError("hierarchyElement", e.getMessage());
            return error;
        }
    }

    /**
     * Returns ResponseMessage, add events to server processEvents.
     * Update or insert hierarchies
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private ResponseMessage save(HierarchyImpl hierarchyImpl, ArrayList<HierarchyElementEvent> clientEvents, String operation, HierarchyEditorSessionSEJB server, HierarchyDetailsDTO hierarchy) throws ServiceException {
        String errorMsg;
        if(hierarchy.getType()) {
            errorMsg = "Error during " + operation + " Hierarchy Account.";
        } else {
            errorMsg = "Error during " + operation + " Hierarchy Business.";
        }
        try {
            // process events
            if(operation.equals("create")) {
                DimensionSelectionEvent dimRef = new DimensionSelectionEvent(hierarchyImpl.getDimensionRef());
                ArrayList listDimensionEvent = new ArrayList();
                listDimensionEvent.add(dimRef);
                server.processEvents(listDimensionEvent);
                server.processEvents(listDimensionEvent);
            }
            for(HierarchyElementEvent event: clientEvents) {
                ArrayList list = new ArrayList();
                list.add(event);
                server.processEvents(list);
            }
            
            // save
            if (hierarchyImpl.getPrimaryKey() != null && hierarchyImpl.getHierarchyId() != -1) {
                server.update(new HierarchyEditorSessionCSO(cpContextHolder.getUserId(), hierarchyImpl));
            } else if (operation.equals("create")) {
                server.insert(new HierarchyEditorSessionCSO(cpContextHolder.getUserId(), hierarchyImpl));
            }
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (ValidationException e) {
            logger.error("Validation " + errorMsg);
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error(errorMsg + " " + e.getMessage());
            throw new ServiceException(errorMsg, e);
        }
    }

    /**
     * Get list of available dimension for insert
     */
    @Override
    public List<DimensionCoreDTO> fetchAvailableDimension(boolean type) throws ServiceException {
//        HierarchyEditorSessionServer server = cpContextHolder.getHierarchyEditorSessionServer();
        int typeNumber;
        if(type) {
            typeNumber = 1; //type is account
        } else {
            typeNumber = 2; //type is business
        }
        List<DimensionCoreDTO> availableList = HierarchiesMapper.mapAvailableDimensionForInsert(HierarchyEditorSessionServer.server.getAvailableDimensionsForInsert(typeNumber));
        return availableList;
    }

    /**
     * Delete Hierarchies from database.
     */
    @Override
    public ResponseMessage delete(Integer dimensionId, Integer hierarchyId) throws ServiceException {
        DimensionPK dimensionPK = new DimensionPK(dimensionId);
        HierarchyPK hierarchyPK = new HierarchyPK(hierarchyId);
        HierarchyCK hierarchyCK = new HierarchyCK(dimensionPK, hierarchyPK);
        try {
//            cpContextHolder.getHierarchyEditorSessionServer().delete(hierarchyCK);
        	HierarchyEditorSessionServer.server.delete(cpContextHolder.getUserId(), hierarchyCK);
            ResponseMessage responseMessage = new ResponseMessage(true);
            return responseMessage;
        } catch (ValidationException e) {
            logger.error("Validation error during dalete hierarchies with Id =" + e + "!");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error("Error during delete hierarchies  =" + e + "!");
            throw new ServiceException("Error during dalete hierarchies with Id =" + e + "!");
        }
    }

}
