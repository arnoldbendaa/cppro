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
package com.softproideas.app.admin.hierarchies.util;

import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarchyElementCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
import com.cedar.cp.dto.dimension.HierarchyElementPK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.event.HierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.InsertHierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.MoveHierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.MoveHierarchyElementFeedEvent;
import com.cedar.cp.dto.dimension.event.RemoveHierarchyElementEvent;
import com.cedar.cp.dto.dimension.event.UpdateHierarchyElementEvent;
import com.softproideas.app.admin.hierarchies.model.HierarchyDetailsDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyEventDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyEventType;

/**
 * 
 * <p>Object store share function mapping HierarchyEventDTO to HierarchyElementEvent</p>
 *
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class HierarchiesEventUtil {

    /**
     * Map HierarchyEventDTO list to HierarchyElementEvent list.
     * @param hierarchyImpl
     * @param hierarchy
     * @return
     * @throws ValidationException
     */
    public static ArrayList<HierarchyElementEvent> prepareElementsToSave(HierarchyDetailsDTO hierarchy) throws ValidationException {
        ArrayList<HierarchyElementEvent> events = new ArrayList<HierarchyElementEvent>();
        List<HierarchyEventDTO> hierarchyEventsDTO = hierarchy.getHierarchyEvents();
        
        for(HierarchyEventDTO eventDTO: hierarchyEventsDTO) {
            HierarchyEventType eventType = eventDTO.getEventType();
            if(eventType.equals(HierarchyEventType.INSERT)) {
                InsertHierarchyElementEvent insertHierarchyElementEvent = mapInsertHierarchyEvent(eventDTO, hierarchy);
                events.add(insertHierarchyElementEvent);
            } else if(eventType.equals(HierarchyEventType.MOVE)) {
                MoveHierarchyElementEvent moveHierarchyElement = mapMoveHierarchyEvent(eventDTO, hierarchy);
                events.add(moveHierarchyElement);
            } else if(eventType.equals(HierarchyEventType.MOVE_FEED)) {
                MoveHierarchyElementFeedEvent moveFeedElement = mapMoveHierarchyFeedEvent(eventDTO, hierarchy);
                events.add(moveFeedElement);
            } else if(eventType.equals(HierarchyEventType.REMOVE)) {
                RemoveHierarchyElementEvent removeHierarchyElementEvent = mapRemoveHierarchyEvent(eventDTO, hierarchy);
                events.add(removeHierarchyElementEvent);
            } else if(eventType.equals(HierarchyEventType.UPDATE)) {
                UpdateHierarchyElementEvent updateHierarchyElementEvent = mapUpdateHierarchyEvent(eventDTO, hierarchy);
                events.add(updateHierarchyElementEvent);
            }
        }
        
        return events;
        
    }

    /**
     * @param eventDTO
     * @param hierarchy
     * @return
     * @throws ValidationException 
     */
    private static UpdateHierarchyElementEvent mapUpdateHierarchyEvent(HierarchyEventDTO eventDTO, HierarchyDetailsDTO hierarchy) throws ValidationException {
        HierarchyValidatorUtil.validateEvents(eventDTO.getVisId(), eventDTO.getDescription());
        HierarchyPK hierarchyPK = new HierarchyPK(hierarchy.getHierarchyId());
        HierarchyElementPK hierarchyElementPK = new HierarchyElementPK(eventDTO.getHierarchyElementId());
        DimensionPK dimensionPK = new DimensionPK(hierarchy.getDimension().getDimensionId());
        HierarchyElementCK hierarchyElementCK = new HierarchyElementCK(dimensionPK, hierarchyPK, hierarchyElementPK);
        UpdateHierarchyElementEvent updateHierarchyElementEvent = new UpdateHierarchyElementEvent(hierarchyElementCK, eventDTO.getOrigVisId(), eventDTO.getVisId(), eventDTO.getDescription(), eventDTO.getCreditDebit(), eventDTO.getAugCreditDebit());
        return updateHierarchyElementEvent;
    }

    /**
     * @param eventDTO
     * @param hierarchy
     * @return
     */
    private static RemoveHierarchyElementEvent mapRemoveHierarchyEvent(HierarchyEventDTO eventDTO, HierarchyDetailsDTO hierarchy) {
        HierarchyPK hierarchyPK = new HierarchyPK(hierarchy.getHierarchyId());
        HierarchyElementPK hierarchyElementPK = new HierarchyElementPK(eventDTO.getHierarchyElementId());
        DimensionPK dimensionPK = new DimensionPK(hierarchy.getDimension().getDimensionId());
        HierarchyElementCK hierarchyElementCK = new HierarchyElementCK(dimensionPK, hierarchyPK, hierarchyElementPK);
        RemoveHierarchyElementEvent removeHierarchyElementEvent = new RemoveHierarchyElementEvent(hierarchyElementCK, eventDTO.getVisId());
        return removeHierarchyElementEvent;
    }

    /**
     * @param eventDTO
     * @param hierarchy
     * @return
     */
    private static MoveHierarchyElementFeedEvent mapMoveHierarchyFeedEvent(HierarchyEventDTO eventDTO, HierarchyDetailsDTO hierarchy) {
        HierarchyPK hierarchyPK = new HierarchyPK(hierarchy.getHierarchyId());
        HierarchyElementPK hierarchyElementPK = new HierarchyElementPK(eventDTO.getHierarchyParentElementId());
        DimensionPK dimensionPK = new DimensionPK(hierarchy.getDimension().getDimensionId());
        HierarchyElementCK hierarchyElementParentCK = new HierarchyElementCK(dimensionPK, hierarchyPK, hierarchyElementPK);
        HierarchyElementFeedPK feedPK = new HierarchyElementFeedPK(eventDTO.getHierarchyParentElementId(), eventDTO.getHierarchyElementId());
        MoveHierarchyElementFeedEvent moveFeedElement = new MoveHierarchyElementFeedEvent(hierarchyElementParentCK, eventDTO.getParentVisId(), eventDTO.getIndex(), feedPK, eventDTO.getVisId());
        return moveFeedElement;
    }

    /**
     * @param eventDTO
     * @param hierarchy
     * @return
     */
    private static MoveHierarchyElementEvent mapMoveHierarchyEvent(HierarchyEventDTO eventDTO, HierarchyDetailsDTO hierarchy) {
        HierarchyPK hierarchyPK = new HierarchyPK(hierarchy.getHierarchyId());
        HierarchyElementPK hierarchyElementPK = new HierarchyElementPK(eventDTO.getHierarchyParentElementId());
        DimensionPK dimensionPK = new DimensionPK(hierarchy.getDimension().getDimensionId());
        HierarchyElementCK hierarchyElementParentCK = new HierarchyElementCK(dimensionPK, hierarchyPK, hierarchyElementPK);
        HierarchyElementPK hierarchyElementMovedPK = new HierarchyElementPK(eventDTO.getHierarchyElementId());
        HierarchyElementCK hierarchyElementMovedCK = new HierarchyElementCK(dimensionPK, hierarchyPK, hierarchyElementMovedPK);

        MoveHierarchyElementEvent moveHierarchyElement = new MoveHierarchyElementEvent(hierarchyElementParentCK, eventDTO.getParentVisId(), eventDTO.getIndex(), hierarchyElementMovedCK, eventDTO.getVisId());
        return moveHierarchyElement;
    }

    /**
     * @param eventDTO
     * @return
     * @throws ValidationException 
     */
    private static InsertHierarchyElementEvent mapInsertHierarchyEvent(HierarchyEventDTO eventDTO, HierarchyDetailsDTO hierarchy) throws ValidationException {
        HierarchyValidatorUtil.validateEvents(eventDTO.getVisId(), eventDTO.getDescription());
        HierarchyPK hierarchyPK = new HierarchyPK(hierarchy.getHierarchyId());
        HierarchyElementPK hierarchyElementPK = new HierarchyElementPK(eventDTO.getHierarchyParentElementId());
        DimensionPK dimensionPK = new DimensionPK(hierarchy.getDimension().getDimensionId());
        HierarchyElementCK hierarchyElementCK = new HierarchyElementCK(dimensionPK, hierarchyPK, hierarchyElementPK);
        InsertHierarchyElementEvent insertHierarchyElementEvent = new InsertHierarchyElementEvent(hierarchyElementCK, eventDTO.getParentVisId(), eventDTO.getVisId(), eventDTO.getDescription(), eventDTO.getIndex(), eventDTO.getCreditDebit(), eventDTO.getAugCreditDebit(), null);
        return insertHierarchyElementEvent;
    }

}
