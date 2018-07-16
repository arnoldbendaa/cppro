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
package com.softproideas.app.core.structureelement.mapper;

import java.util.ArrayList;
import java.util.List;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.dimension.HierarchyNode;
import com.cedar.cp.dto.dimension.HierarchyElementCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
import com.softproideas.common.models.StructureElementCoreDTO;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class StructureElementCoreMapper {

    /**
     * Map ChildrenForParentELO, LeavesForParentELO, ImmediateChildrenELO to List<StructureElementCoreDTO>.
     */
    public static List<StructureElementCoreDTO> mapStructureElementList(EntityList list) {
        List<StructureElementCoreDTO> listDTO = new ArrayList<StructureElementCoreDTO>();

        for (int i = 0; i < list.getNumRows(); i++) {
            StructureElementCoreDTO structureElementCoreDTO = new StructureElementCoreDTO();
            EntityList row = list.getRowData(i);

            structureElementCoreDTO.setStructureElementId((Integer) row.getValueAt(0, "StructureElementId"));
            structureElementCoreDTO.setStructureElementVisId((String) row.getValueAt(0, "VisId"));
            structureElementCoreDTO.setStructureElementDescription((String) row.getValueAt(0, "Description"));
            
            listDTO.add(structureElementCoreDTO);
        }

        return listDTO;
    }
    
    public static StructureElementCoreDTO mapHierarchyNodeToStructureElementCoreDTO(HierarchyNode node) {
        StructureElementCoreDTO structureElementCoreDTO = new StructureElementCoreDTO();
        
        if (node.getPrimaryKey() instanceof HierarchyElementCK) {
            HierarchyElementCK hierarchyElementCK = (HierarchyElementCK) node.getPrimaryKey();
            structureElementCoreDTO.setStructureElementId(hierarchyElementCK.getHierarchyElementPK().getHierarchyElementId());
        } else if (node.getPrimaryKey() instanceof HierarchyElementFeedPK) {
            HierarchyElementFeedPK hierarchyElementFeedPK = (HierarchyElementFeedPK) node.getPrimaryKey();
            structureElementCoreDTO.setStructureElementId(hierarchyElementFeedPK.getDimensionElementId());
        }
        
        //structureElementCoreDTO.setStructureElementId(hierarchyElementFeedPK.getDimensionElementId());
        structureElementCoreDTO.setStructureElementVisId(node.getVisId());
        structureElementCoreDTO.setStructureElementDescription(node.getDescription());
        return structureElementCoreDTO;
    }

}
