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
package com.softproideas.app.core.financecube.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.dto.model.AllFinanceCubesELO;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.softproideas.app.core.financecube.model.FinanceCubeCoreDTO;
import com.softproideas.app.core.financecube.model.FinanceCubeModelCoreDTO;
import com.softproideas.app.core.model.mapper.ModelCoreMapper;
import com.softproideas.app.core.model.model.ModelCoreDTO;

public class FinanceCubeCoreMapper {

    public static FinanceCubeModelCoreDTO mapFinanceCubeRefToFinanceCubeCoreDTO(FinanceCubeRef financeCubeRef) {
        FinanceCubeModelCoreDTO financeCube = new FinanceCubeModelCoreDTO();
        FinanceCubeCK financeCubeCK = (FinanceCubeCK) financeCubeRef.getPrimaryKey();
        FinanceCubePK financeCubePK = financeCubeCK.getFinanceCubePK();
        financeCube.setFinanceCubeId(financeCubePK.getFinanceCubeId());
        financeCube.setFinanceCubeVisId(financeCubeRef.getNarrative());
        return financeCube;
    }

    @SuppressWarnings("unchecked")
    public static List<FinanceCubeModelCoreDTO> mapAllFinanseCubesELO(AllFinanceCubesELO allFinanceCubesELO) {
        List<FinanceCubeModelCoreDTO> financeCubesList = new ArrayList<FinanceCubeModelCoreDTO>();
        for (Iterator<AllFinanceCubesELO> it = allFinanceCubesELO.iterator(); it.hasNext();) {
            AllFinanceCubesELO row = it.next();
            FinanceCubeModelCoreDTO financeCubeDTO = mapFinanceCubeRefToFinanceCubeCoreDTO((FinanceCubeRefImpl) row.getFinanceCubeEntityRef());
            financeCubeDTO.setFinanceCubeDescription(row.getDescription());
            ModelCoreDTO modelDTO = ModelCoreMapper.mapModelRefToModelCoreDTO(row.getModelEntityRef());
            financeCubeDTO.setModel(modelDTO);
            financeCubesList.add(financeCubeDTO);
        }
        return financeCubesList;
    }

    public static FinanceCubeRefImpl mapFinanceCubeCoreDTOtoFinanceCubeRefImpl(FinanceCubeCoreDTO financeCubeCoreDTO, ModelCoreDTO modelCoreDTO) {
        FinanceCubePK financeCubePK = new FinanceCubePK(financeCubeCoreDTO.getFinanceCubeId());
        ModelPK modelPK = new ModelPK(modelCoreDTO.getModelId());
        FinanceCubeCK financeCubeCK = new FinanceCubeCK(modelPK, financeCubePK);
        FinanceCubeRefImpl financeCubeRefImpl = new FinanceCubeRefImpl(financeCubeCK, financeCubeCoreDTO.getFinanceCubeVisId());
        return financeCubeRefImpl;
    }

}
