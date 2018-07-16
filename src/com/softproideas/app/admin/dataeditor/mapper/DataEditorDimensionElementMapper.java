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
package com.softproideas.app.admin.dataeditor.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.dto.dimension.AllDimensionElementsForModelELO;
import com.softproideas.app.admin.dataeditor.model.DimensionDataForModelDTO;
import com.softproideas.app.core.dimensionElement.model.DimensionElementCoreDTO;

/**
 * <p>Class is responsible for maps different object related to data editor dimension element to data transfer object (and vice-versa)</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class DataEditorDimensionElementMapper {

    /**
     * Maps list of dimension elements {@link AllDimensionElementsForModelELO} to list of transfer data object related to data which is important in data editor module.
     * @return map which contain keys - modelIds, value of {@link DimensionDataForModelDTO} 
     */
    public static HashMap<Integer, DimensionDataForModelDTO> mapAllDimensionElementsForModelELOToDTO(AllDimensionElementsForModelELO elo) {
        HashMap<Integer, DimensionDataForModelDTO> map = new HashMap<Integer, DimensionDataForModelDTO>();

        for (@SuppressWarnings("unchecked")
        Iterator<AllDimensionElementsForModelELO> it = elo.iterator(); it.hasNext();) {
            AllDimensionElementsForModelELO row = it.next();

            int modelId = row.getModelId();
            DimensionDataForModelDTO modelData = null;
            List<DimensionElementCoreDTO> costCenters = null;
            List<DimensionElementCoreDTO> expenseCodes = null;

            if (!map.containsKey(modelId)) {
                modelData = new DimensionDataForModelDTO();

                costCenters = new ArrayList<DimensionElementCoreDTO>(); // type 2
                modelData.setCostCenters(costCenters);

                expenseCodes = new ArrayList<DimensionElementCoreDTO>(); // type 1
                modelData.setExpenseCodes(expenseCodes);

                map.put(modelId, modelData);
            } else {
                modelData = map.get(modelId);
                costCenters = modelData.getCostCenters();
                expenseCodes = modelData.getExpenseCodes();
            }

            DimensionElementCoreDTO node = null;
            if (row.getDimensionType() == 2) {
                node = new DimensionElementCoreDTO();
                node.setDimensionElementId(row.getDimensionElementId());
                node.setDimensionElementVisId(row.getDimensionElementVisId());
                costCenters.add(node);
            } else if (row.getDimensionType() == 1) {
                node = new DimensionElementCoreDTO();
                node.setDimensionElementId(row.getDimensionElementId());
                node.setDimensionElementVisId(row.getDimensionElementVisId());
                expenseCodes.add(node);
            }
        }
        return map;
        //
        // for (int modelId: modelIds) {
        // DimensionDataForModelDTO modelData = new DimensionDataForModelDTO();
        //
        // HierarchiesForModelELO hierarchies = (HierarchiesForModelELO) cpContextHolder.getListSessionServer().getHierarchiesForModel(modelId);
        //
        // List<DimensionElementDTO> costCentres = new ArrayList<DimensionElementDTO>();
        // HierarchyRef hierarchyRef = (HierarchyRef) hierarchies.getDataAsArray()[0][3];
        // int hierarchyId = ((HierarchyPK) hierarchyRef.getPrimaryKey()).getHierarchyId();
        //
        // for (Object[] costCenter: cpContextHolder.getCPConnection().getListHelper().getAllLeafStructureElements(hierarchyId).getDataAsArray()) {
        // int structureElementId = ((StructureElementPK) ((StructureElementRefImpl) costCenter[0]).getPrimaryKey()).getStructureElementId();
        // String structureElementVisId = ((StructureElementRefImpl) costCenter[0]).getNarrative();
        // DimensionElementDTO node = new DimensionElementDTO();
        // node.setId(structureElementId);
        // node.setName(structureElementVisId);
        // costCentres.add(node);
        // }
        // modelData.setCostCenters(costCentres);
        //
        // List<DimensionElementDTO> expenseCodes = new ArrayList<DimensionElementDTO>();
        // DimensionRef dimRef = (DimensionRef) hierarchies.getDataAsArray()[1][1];
        // DimensionPK dimPK = (DimensionPK) dimRef.getPrimaryKey();
        //
        // Dimension dimensions = null;
        // try {
        // AllDimensionElementsForDimensionELO elo = cpContextHolder.getListSessionServer().getAllDimensionElementsForDimension(dimPK.getDimensionId());
        // dimensions = cpContextHolder.getDimensionEditorSessionServer().getItemData(dimRef.getPrimaryKey()).getEditorData();
        //
        // } catch (ValidationException e) {
        // logger.error("Error during ");
        // throw new ServiceException("Error during ");
        // }
        // if (dimensions != null) {
        // for (Object exp: dimensions.getDimensionElements().toArray()) {
        // int id = ((DimensionElementCK) ((DimensionElementImpl) exp).getEntityRef().getPrimaryKey()).getDimensionElementPK().getDimensionElementId();
        // String name = ((DimensionElementImpl) exp).getVisId();
        // DimensionElementDTO node = new DimensionElementDTO();
        // node.setId(id);
        // node.setName(name);
        // expenseCodes.add(node);
        // }
        // modelData.setExpenseCodes(expenseCodes);
        //
        // map.put(modelId, modelData);
        // }
        // }
        // return map;
    }
}
