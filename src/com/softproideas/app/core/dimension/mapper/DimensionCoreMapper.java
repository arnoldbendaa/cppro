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
package com.softproideas.app.core.dimension.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.model.ModelDimensionsELO;
import com.softproideas.app.core.dimension.model.DimensionCoreDTO;

public class DimensionCoreMapper {

    public static DimensionCoreDTO mapDimensionRef(DimensionRef dimensionRef) {
        DimensionCoreDTO dimension = new DimensionCoreDTO();
        DimensionPK dimensionPK = (DimensionPK) dimensionRef.getPrimaryKey();
        dimension.setDimensionId(dimensionPK.getDimensionId());
        dimension.setDimensionVisId(dimensionRef.getNarrative());
        return dimension;
    }

    @SuppressWarnings("unchecked")
    public static List<DimensionCoreDTO> mapModelDimensions(ModelDimensionsELO modelDimensionsELO) {
        List<DimensionCoreDTO> modelDimensions = new ArrayList<DimensionCoreDTO>();
        for (Iterator<ModelDimensionsELO> it = modelDimensionsELO.iterator(); it.hasNext();) {
            ModelDimensionsELO dimension = it.next();
            DimensionCoreDTO dimensionDTO = mapDimensionRef(dimension.getDimensionEntityRef());
            dimensionDTO.setDimensionDescription(dimension.getDescription());
            modelDimensions.add(dimensionDTO);
        }
        return modelDimensions;
    }

}
