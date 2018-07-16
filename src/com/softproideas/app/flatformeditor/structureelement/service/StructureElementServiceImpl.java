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
package com.softproideas.app.flatformeditor.structureelement.service;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.tree.TreeNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyNode;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyEditorSessionSSO;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.dto.dimension.HierarcyDetailsFromDimIdELO;
import com.cedar.cp.dto.dimension.calendar.CalendarInfoImpl;
import com.cedar.cp.ejb.api.dimension.HierarchyEditorSessionServer;
import com.cedar.cp.util.Pair;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.softproideas.app.core.dimension.model.DimensionCoreDTO;
import com.softproideas.app.core.structureelement.mapper.StructureElementCoreMapper;
import com.softproideas.app.flatformeditor.datatypes.service.DataTypesServiceImpl;
import com.softproideas.app.flatformeditor.structureelement.mapper.StructureElementMapper;
import com.softproideas.app.flatformeditor.structureelement.model.DateStructureElementDTO;
import com.softproideas.app.flatformeditor.structureelement.model.FillDimensionOption;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.common.models.StructureElementCoreDTO;
import com.softproideas.commons.context.CPContextHolder;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Service("structureElementService")
public class StructureElementServiceImpl implements StructureElementService {
    private static Logger logger = LoggerFactory.getLogger(DataTypesServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    /* (non-Javadoc)
     * 
     * @see com.softproideas.app.flatformeditor.structureelement.service.StructureElementService#browseStructureElements(int, int) */
    @Override
    public List<StructureElementCoreDTO> browseStructureElements(int structureId, String structureElementIds, String typeOfQuery) throws ServiceException {
        ArrayList<Integer> listSelectedStructureElements = new ArrayList<Integer>();
        String[] tableSelectedStructureElements = structureElementIds.split(",");
        for(String structureElement: tableSelectedStructureElements) {
            int transform = Integer.parseInt(structureElement);
            listSelectedStructureElements.add(transform);
        }
        List<StructureElementCoreDTO> listStructureElement = new ArrayList<StructureElementCoreDTO>();

        for(int i = 0; i < listSelectedStructureElements.size(); ++i) {
            if (typeOfQuery.equals("Immediate Children")) {
                listStructureElement.addAll(StructureElementCoreMapper.mapStructureElementList(cpContextHolder.getListHelper().getImmediateChildren(structureId, listSelectedStructureElements.get(i))));
            } else if (typeOfQuery.equals("Leaves")) {
                listStructureElement.addAll(StructureElementCoreMapper.mapStructureElementList(cpContextHolder.getListHelper().getLeavesForParent(structureId, structureId, listSelectedStructureElements.get(i), structureId, listSelectedStructureElements.get(i))));
            } else if (typeOfQuery.equals("Cascade")) {
                listStructureElement.addAll(StructureElementCoreMapper.mapStructureElementList(cpContextHolder.getListHelper().getChildrenForParent(structureId, structureId, listSelectedStructureElements.get(i), structureId, listSelectedStructureElements.get(i))));
            } else {
                logger.error("Unknown Qeury Type:" + typeOfQuery);
                throw new ServiceException("Unknown Qeury Type:" + typeOfQuery);
            }
        }
        return listStructureElement;
    }

    @Override
    public List<StructureElementCoreDTO> browseNextStructureElements(FillDimensionOption option, List<DimensionCoreDTO> dimensions) throws ServiceException {
        int dimensionId = dimensions.get(option.getDimensionIndex()).getDimensionId();
        HierarcyDetailsFromDimIdELO hierarcyDetailsFromDimIdELO = cpContextHolder.getListSessionServer().getHierarcyDetailsFromDimId(dimensionId);

        HierarchyRefImpl hierarchyRef = (HierarchyRefImpl) hierarcyDetailsFromDimIdELO.getValueAt(0, "Hierarchy");
        HierarchyCK key = (HierarchyCK) hierarchyRef.getPrimaryKey();
        HierarchyEditorSessionServer server = cpContextHolder.getHierarchyEditorSessionServer();
        HierarchyEditorSessionSSO sso = null;
        try {
            sso = server.getItemData(key);
        } catch (CPException e) {
            server.removeSession();
            logger.error("Couldn't find hierarchy with ID = " + key.getHierarchyPK().getHierarchyId());
            throw new ServiceException("Couldn't find hierarchy with ID = " + key.getHierarchyPK().getHierarchyId());
        } catch (ValidationException e) {
            server.removeSession();
            logger.error("Couldn't find hierarchy with ID = " + key.getHierarchyPK().getHierarchyId());
            throw new ServiceException("Couldn't find hierarchy with ID = " + key.getHierarchyPK().getHierarchyId());
        } 
        
        List<StructureElementCoreDTO> list = new ArrayList<StructureElementCoreDTO>();
        if (sso == null) {
            server.removeSession();
            return list;
        }
        
        HierarchyImpl hierarchy = sso.getEditorData();
        HierarchyNode findElement = hierarchy.findElement(option.getDimensionVisId());
       
        HierarchyNode dim;
        StructureElementCoreDTO structureElementCoreDTO;
        
        if (findElement != null) {
            TreeNode oldParentNode, rootNode;
            int oldParentNodeIndex;
            TreeNode parentNode = findElement.getParent();
            if (parentNode != null) {
                int i = parentNode.getIndex(findElement) + 1;
                for (int j = 0; j < option.getOffset(); j++) {

                    if (i < parentNode.getChildCount()) {
                        dim = (HierarchyNode) parentNode.getChildAt(i);
                        structureElementCoreDTO = StructureElementCoreMapper.mapHierarchyNodeToStructureElementCoreDTO(dim);
                        list.add(structureElementCoreDTO);
                        i++;
                    } else {
                        if (!findElement.isLeaf()) {
                            break;
                        }
                        // Find next leaf
                        boolean nextLeaf = false;
                        while (nextLeaf == false) {
                            oldParentNode = parentNode;
                            parentNode = parentNode.getParent();
                            if (parentNode != null) {
                                oldParentNodeIndex = parentNode.getIndex(oldParentNode);

                                if ((parentNode.getChildCount() == (oldParentNodeIndex + 1)) & (parentNode.getParent() == null)) {
                                    rootNode = parentNode;
                                    int childIndex = 0;
                                    while (!parentNode.isLeaf()) {
                                        parentNode = rootNode.getChildAt(childIndex);
                                        while (parentNode.getChildCount() > 0) {
                                            parentNode = parentNode.getChildAt(0);
                                        }
                                        childIndex++;
                                    }
                                } else if ((parentNode.getChildCount() != (oldParentNodeIndex + 1))) {
                                    rootNode = parentNode;
                                    int childIndex = oldParentNodeIndex + 1;
                                    while (!parentNode.isLeaf()) {
                                        parentNode = rootNode.getChildAt(childIndex);
                                        while (parentNode.getChildCount() > 0) {
                                            parentNode = parentNode.getChildAt(0);
                                        }
                                        childIndex++;
                                    }
                                    nextLeaf = true;
                                }
                            } else {
                                nextLeaf = false;
                            }
                        }
                        parentNode = parentNode.getParent();
                        i = 0;
                        dim = (HierarchyNode) parentNode.getChildAt(i);
                        structureElementCoreDTO = StructureElementCoreMapper.mapHierarchyNodeToStructureElementCoreDTO(dim);
                        list.add(structureElementCoreDTO);
                        i++;
                    }
                }
            }
        }
        server.removeSession();
        return list;
    }

    /* (non-Javadoc)
     * @see com.softproideas.app.flatformeditor.structureelement.service.StructureElementService#getCalendarStructureElements(int, int, java.lang.String)
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<DateStructureElementDTO> getCalendarStructureElements(int modelId, String structureElementIds, String typeOfQuery) throws ServiceException {
        EntityList calendarElo = cpContextHolder.getListHelper().getCalendarForModel(modelId);
        if (calendarElo.getNumRows() == 0) {
            logger.error("No Calendar for this Model");
            throw new ServiceException("No Calendar for this Model");
        } else {
            ArrayList<DateStructureElementDTO> resultList = new ArrayList<DateStructureElementDTO>();
            ArrayList<Integer> listSelectedStructureElements = new ArrayList<Integer>();
            String[] tableSelectedStructureElements = structureElementIds.split(",");
            for(String structureElement: tableSelectedStructureElements) {
                int transform = Integer.parseInt(structureElement);
                listSelectedStructureElements.add(transform);
            }
            int hierarchyId = ((Integer) calendarElo.getValueAt(0, "HierarchyId")).intValue();
            EntityList elems = cpContextHolder.getListHelper().getAllStructureElements(hierarchyId);
            CalendarInfoImpl calendarInfo = CalendarInfoImpl.getCalendarInfo(Integer.valueOf(hierarchyId), elems);
            ArrayList<Pair> list = new ArrayList<Pair>();

            for(int i = 0; i < listSelectedStructureElements.size(); ++i) {
               CalendarElementNode var27 = calendarInfo.getById(listSelectedStructureElements.get(i));
               int var28 = var27.getElemType();
               this.validateCalendarExpression(var28, typeOfQuery);
               Enumeration child;
               Pair child1;
               CalendarElementNode childNode;
               if(typeOfQuery.equals("Immediate Children")) {
                  child = var27.children();

                  while(child.hasMoreElements()) {
                     childNode = (CalendarElementNode)child.nextElement();
                     child1 = new Pair(childNode.getFullPathVisId(), childNode.getDescription());
                     list.add(child1);
                  }
               } else if(typeOfQuery.equals("Cascade")) {
                  child = var27.depthFirstEnumeration();

                  while(child.hasMoreElements()) {
                     childNode = (CalendarElementNode)child.nextElement();
                     child1 = new Pair(childNode.getFullPathVisId(), childNode.getDescription());
                     list.add(child1);
                  }
               } else if(typeOfQuery.equals("Leaves")) {
                  child = var27.depthFirstEnumeration();

                  while(child.hasMoreElements()) {
                     childNode = (CalendarElementNode)child.nextElement();
                     if(childNode.isLeaf()) {
                        child1 = new Pair(childNode.getFullPathVisId(), childNode.getDescription());
                        list.add(child1);
                     }
                  }
               } else {
                  Pair var29 = new Pair(var27.getFullPathVisId(), var27.getDescription());
                  list.add(var29);
               }
            }
            
            resultList = StructureElementMapper.mapPairList(list);
            return resultList;
        }
    }

    private void validateCalendarExpression(int calendarType, Object typeOfQuery) throws ServiceException {
       if(calendarType == 100 && !(typeOfQuery.equals("Immediate Children") || typeOfQuery.equals("Contextual") || typeOfQuery.equals("Leaves"))) {
           logger.error("The query mode of Root Calendar must be Immediate Children Leaves or Contextual");                                                              //Old version exception text: The query mode of Root Calendar must not be None or Cascade;
           throw new ServiceException("The query mode of Root Calendar must be Immediate Children, Leaves or Contextual");
       }
    }


}
