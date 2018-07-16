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
package com.softproideas.app.core.dimension.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.dto.dimension.HierarcyDetailsFromDimIdELO;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.softproideas.app.core.dimension.mapper.DimensionCoreMapper;
import com.softproideas.app.core.dimension.model.DimensionCoreDTO;
import com.softproideas.app.core.dimension.model.DimensionWithHierarchiesCoreDTO;
import com.softproideas.app.core.hierarchy.mapper.HierarchyCoreMapper;
import com.softproideas.app.core.hierarchy.model.HierarchyCoreDTO;
import com.softproideas.commons.context.CPContextHolder;

@Service("coreDimensionService")
public class CoreDimensionServiceImpl implements CoreDimensionService {

    @Autowired
    CPContextHolder cpContextHolder;
    
    @Override
    public List<DimensionCoreDTO> fetchModelDimensions(int modelId) {
        ModelDimensionsELO modelDimensionsELO = cpContextHolder.getListSessionServer().getModelDimensions(modelId);
        List<DimensionCoreDTO> modelDimensions = DimensionCoreMapper.mapModelDimensions(modelDimensionsELO);
        return modelDimensions;
    }

    @Override
    public List<DimensionWithHierarchiesCoreDTO> fetchModelDimensionsWithHierarchies(int modelId) {
        List<DimensionWithHierarchiesCoreDTO> modelDimensionWithHierarchiesCoreDTO = new ArrayList<DimensionWithHierarchiesCoreDTO>();
        ModelDimensionsELO modelDimensionsELO = cpContextHolder.getListSessionServer().getModelDimensions(modelId);
        // map and add dimension to list
        for (Iterator<ModelDimensionsELO> dimensionsIterator = modelDimensionsELO.iterator(); dimensionsIterator.hasNext();) {
            ModelDimensionsELO dimension = dimensionsIterator.next();
            DimensionWithHierarchiesCoreDTO dimensionDTO = new DimensionWithHierarchiesCoreDTO();
            int dimensionId = ((DimensionPK) dimension.getDimensionEntityRef().getPrimaryKey()).getDimensionId();
            dimensionDTO.setDimensionId(dimensionId);
            dimensionDTO.setDimensionVisId(dimension.getDimensionEntityRef().getNarrative());
            dimensionDTO.setDimensionDescription(dimension.getDescription());
            dimensionDTO.setType(dimension.getDimensionType());
            // map and add hierarchy to dimension
            HierarcyDetailsFromDimIdELO hierarcyDetails = cpContextHolder.getListSessionServer().getHierarcyDetailsFromDimId(dimensionId);
            List<HierarchyCoreDTO> hirarchyList = new ArrayList<HierarchyCoreDTO>();
            for (Iterator<HierarcyDetailsFromDimIdELO> hierarchiesIterator = hierarcyDetails.iterator(); hierarchiesIterator.hasNext();) {
                HierarcyDetailsFromDimIdELO hierarchy = hierarchiesIterator.next();
                HierarchyCoreDTO hierarchyDTO = HierarchyCoreMapper.mapHierarchyRefImplToHierarchyCoreDTO((HierarchyRefImpl) hierarchy.getHierarchyEntityRef());
                hierarchyDTO.setHierarchyDescription(hierarchy.getDescription());
                hirarchyList.add(hierarchyDTO);
            }
            dimensionDTO.setHierarchies(hirarchyList);
            modelDimensionWithHierarchiesCoreDTO.add(dimensionDTO);
        }
        return modelDimensionWithHierarchiesCoreDTO;
    }
}
