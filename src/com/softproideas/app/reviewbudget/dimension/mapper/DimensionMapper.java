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
package com.softproideas.app.reviewbudget.dimension.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.dimension.HierarchyNode;
import com.cedar.cp.dto.dimension.AllDimensionsForModelELO;
import com.cedar.cp.dto.dimension.HierarchyElementCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedImpl;
import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;
import com.softproideas.app.reviewbudget.dimension.model.DimensionDTO;
import com.softproideas.app.reviewbudget.dimension.model.ElementDTO;
import com.softproideas.app.reviewbudget.dimension.model.HierarchyDTO;
import com.softproideas.commons.model.TreeNodeDTO;

/**
 * DTO Mapper for dimension information
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com 2014 All rights reserved to Softpro Ideas Group
 */
public class DimensionMapper {

    /**
     * Method maps element with number (elementNumber) from elementsList (which is EntityList) to elementDTO object
     */
    public static ElementDTO mapToElementDTO(EntityList elementsList, int elementNumber) {
        ElementDTO elementDTO = new ElementDTO();
        elementDTO.setStructureId(((Integer) elementsList.getValueAt(elementNumber, "StructureId")).intValue());
        elementDTO.setId(((Integer) elementsList.getValueAt(elementNumber, "StructureElementId")).intValue());

        List<String> headings = Arrays.asList(elementsList.getHeadings());
        if (headings.contains("VisId")) {
            elementDTO.setName((String) elementsList.getValueAt(elementNumber, "VisId"));
            elementDTO.setDescription((String) elementsList.getValueAt(elementNumber, "Description"));
        } else if (headings.contains("StructureElementVisId")) {
            elementDTO.setName((String) elementsList.getValueAt(elementNumber, "StructureElementVisId"));
            elementDTO.setDescription((String) elementsList.getValueAt(elementNumber, "StructureElementDescription"));
        }
        elementDTO.setLeaf(((Boolean) elementsList.getValueAt(elementNumber, "Leaf")).booleanValue());
        
        elementDTO.setSelectable(false);
        if (headings.contains("FullRights"))
            elementDTO.setSelectable(((Boolean) elementsList.getValueAt(elementNumber, "FullRights")));
        
        // if user have permissions to select that dimension and recalculate excel
        elementDTO.setFullRights(true);
        if (headings.contains("FullRights"))
            elementDTO.setFullRights(((Boolean) elementsList.getValueAt(elementNumber, "FullRights")));
        
        return elementDTO;
    }

    /**
     * Method maps data type element with number (elementNumber) from elementsList (which is EntityList) to elementDTO object
     */
    public static ElementDTO mapDataTypeToElementDTO(EntityList dataTypeList, int elementNumber) {
        ElementDTO elementDTO = new ElementDTO();
        elementDTO.setId(((Short) dataTypeList.getValueAt(elementNumber, "DataTypeId")).intValue());
        elementDTO.setName(dataTypeList.getValueAt(elementNumber, "DataType").toString());
        elementDTO.setDescription((String) dataTypeList.getValueAt(elementNumber, "Description"));
        elementDTO.setLeaf(true);
        elementDTO.setSelectable(true);
        elementDTO.setFullRights(true);
        return elementDTO;
    }
       
    /**
     * Method maps hierarchyNode to elementDTO object. Method used during loading dimension details to finance system cell data modal.
     */
    public static ElementDTO mapHierarchyNodeToElementDTO(HierarchyNode hierarchyNode) {
        ElementDTO elementDTO = new ElementDTO();
        if (hierarchyNode instanceof HierarchyElementImpl) {
            hierarchyNode = (HierarchyElementImpl) hierarchyNode;
            elementDTO.setId(((HierarchyElementCK) hierarchyNode.getPrimaryKey()).getHierarchyElementPK().getHierarchyElementId());
        }
        if (hierarchyNode instanceof HierarchyElementFeedImpl) {
            hierarchyNode = (HierarchyElementFeedImpl) hierarchyNode;
            elementDTO.setId(((HierarchyElementFeedPK) hierarchyNode.getPrimaryKey()).getDimensionElementId());
        }
        elementDTO.setLeaf(hierarchyNode.isLeaf());
        elementDTO.setName(hierarchyNode.getVisId());
        elementDTO.setDescription(hierarchyNode.getDescription());
        
        return elementDTO;
    }

    /**
     * Method maps hierarchy (from database) with number (hierarchyNumber) from hierarchies list (which is EntityList) to hierarchyDTO object
     */
    public static HierarchyDTO mapToHierarchyDTO(EntityList hierarchiesList, int hierarchyNumber) {
        HierarchyDTO hierarchyDTO = new HierarchyDTO();
        hierarchyDTO.setId(((Integer) hierarchiesList.getValueAt(hierarchyNumber, "HierarchyId")).intValue());
        hierarchyDTO.setName((String) hierarchiesList.getValueAt(hierarchyNumber, "HierarchyVisId"));
        hierarchyDTO.setDescription((String) hierarchiesList.getValueAt(hierarchyNumber, "HierarchyDescription"));
        return hierarchyDTO;
    }

    /**
     * Method maps dimension (from database) with number (dimensionNumber) from dimension list (which is EntityList) to dimensionDTO object
     */
    public static DimensionDTO mapToDimensionDTO(EntityList dimensionsList, int dimensionNumber) {
        DimensionDTO dimensionDTO = new DimensionDTO();
        dimensionDTO.setId(((Integer) dimensionsList.getValueAt(dimensionNumber, "DimensionId")).intValue());
        dimensionDTO.setName((String) dimensionsList.getValueAt(dimensionNumber, "VisId"));
        dimensionDTO.setDescription((String) dimensionsList.getValueAt(dimensionNumber, "Description"));
        return dimensionDTO;
    }
    
    public static List<DimensionDTO> mapAllDimensionsForModelELO(AllDimensionsForModelELO list) {
        List<DimensionDTO> dimensionsDTO = new ArrayList<DimensionDTO>();

        for (int i = 0; i < list.getNumRows(); i++) {
            DimensionDTO dimensionDTO = new DimensionDTO();

            // dimensionId
            int dimensionId = ((Integer) list.getValueAt(i, "DimensionId")).intValue();
            dimensionDTO.setId(dimensionId);
            // name
            String dimensionName = (String) list.getValueAt(i, "VisId");
            dimensionDTO.setName(dimensionName);
            // description
            String dimensionDescription = (String) list.getValueAt(i, "Description");
            dimensionDTO.setDescription(dimensionDescription);

            dimensionsDTO.add(dimensionDTO);
        }

        return dimensionsDTO;
    }

    /**
     * Method maps element object and children to TreeNodeDTO object. Helps in creating tree structure for dimension
     */
    public static TreeNodeDTO mapToTreeNodeDTO(Object element, List<TreeNodeDTO> children) {
        TreeNodeDTO treeNode = new TreeNodeDTO();
        treeNode.setChildren(children);
        treeNode.setElement(element);
        return treeNode;
    }
}
