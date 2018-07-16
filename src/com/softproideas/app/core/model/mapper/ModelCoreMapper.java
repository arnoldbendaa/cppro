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
package com.softproideas.app.core.model.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.ModelImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.softproideas.app.core.model.model.ModelCoreDTO;
import com.softproideas.app.core.model.model.ModelCoreWithGlobalDTO;

public class ModelCoreMapper {

    /**
     * Maps object of {@link ModelRef} to core data transfer object of {@link ModelCoreDTO} .
     */
    public static ModelCoreDTO mapModelRefToModelCoreDTO(ModelRef modelRef) {
        ModelCoreDTO model = new ModelCoreDTO();
        ModelPK modelPK = (ModelPK) modelRef.getPrimaryKey();
        model.setModelId(modelPK.getModelId());
        model.setModelVisId(modelRef.getNarrative());
        return model;
    }

    @SuppressWarnings("unchecked")
    public static List<ModelCoreWithGlobalDTO> mapAllModelsELOToModelsWithGlobalDTO(AllModelsELO list) {
        List<ModelCoreWithGlobalDTO> modelsDTO = new ArrayList<ModelCoreWithGlobalDTO>();

        for (Iterator<AllModelsELO> it = list.iterator(); it.hasNext();) {
            AllModelsELO row = it.next();

            ModelCoreWithGlobalDTO modelDTO = new ModelCoreWithGlobalDTO();
            ModelRef modelRef = row.getModelEntityRef();
            ModelPK modelPK = (ModelPK) modelRef.getPrimaryKey();
            modelDTO.setModelId(modelPK.getModelId());
            modelDTO.setModelVisId(modelRef.getNarrative());
            modelDTO.setModelDescription(row.getDescription());
            if (row.isGlobal() != null && row.isGlobal()) {
                modelDTO.setGlobal(true);
            } else {
                modelDTO.setGlobal(false);
            }
            modelsDTO.add(modelDTO);
        }

        return modelsDTO;
    }

    public static ModelRefImpl mapModelCoreDTOtoModelRefImpl(ModelCoreDTO modelCoreDTO) {
        ModelPK modelPK = new ModelPK(modelCoreDTO.getModelId());
        String modelVisId = modelCoreDTO.getModelVisId();
        ModelRefImpl modelRefImpl = new ModelRefImpl(modelPK, modelVisId);
        return modelRefImpl;
    }

    public static ModelCoreDTO mapModelImpl(ModelImpl modelImpl) {
        ModelCoreDTO modelCoreDTO = new ModelCoreDTO();
        modelCoreDTO.setModelId(((ModelPK) modelImpl.getPrimaryKey()).getModelId());
        modelCoreDTO.setModelVisId(modelImpl.getVisId());
        modelCoreDTO.setModelDescription(modelImpl.getDescription());
        return modelCoreDTO;
    }

    @SuppressWarnings("unchecked")
    public static ModelCoreDTO mapAllModels(AllModelsELO allModels, String modelVisId) {
        for (Iterator<AllModelsELO> it = allModels.iterator(); it.hasNext();) {
            AllModelsELO row = it.next();

            ModelRef modelRef = row.getModelEntityRef();
            if (modelVisId.equals(modelRef.getNarrative())) {
                ModelCoreDTO modelCoreDTO = new ModelCoreDTO();
                ModelPK modelPK = (ModelPK) modelRef.getPrimaryKey();
                modelCoreDTO.setModelId(modelPK.getModelId());
                modelCoreDTO.setModelVisId(modelRef.getNarrative());
                modelCoreDTO.setModelDescription(row.getDescription());
                return modelCoreDTO;
            }
        }
        return null;
    }

}
