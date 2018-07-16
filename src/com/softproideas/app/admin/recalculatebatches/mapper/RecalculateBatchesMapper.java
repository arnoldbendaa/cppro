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
package com.softproideas.app.admin.recalculatebatches.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.recalculate.RecalculateBatchTaskAssignment;
import com.cedar.cp.api.recalculate.RecalculateBatchTaskRef;
import com.cedar.cp.api.user.DataEntryProfileRef;
import com.cedar.cp.dto.recalculate.AllRecalculateBatchTasksELO;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskImpl;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;
import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchDTO;
import com.softproideas.app.admin.recalculatebatches.model.RecalculateBatchDetailsDTO;
import com.softproideas.app.core.model.mapper.ModelCoreMapper;
import com.softproideas.app.core.model.model.ModelCoreDTO;

/**
 * <p>Class is responsible for maps different object related to recalculate batch to data transfer object (and vice-versa)</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class RecalculateBatchesMapper {

    /**
     * Maps list of recalculate batches {@link AllRecalculateBatchTasksELO} to list of transfer data object related to recalculate batches.
     */
    public static List<RecalculateBatchDTO> mapAllRecalculateBatchTasksELO(AllRecalculateBatchTasksELO elo) {
        List<RecalculateBatchDTO> list = new ArrayList<RecalculateBatchDTO>();

        for (@SuppressWarnings("unchecked")
        Iterator<AllRecalculateBatchTasksELO> it = elo.iterator(); it.hasNext();) {
            AllRecalculateBatchTasksELO row = it.next();
            RecalculateBatchDTO recalculateBatchDTO = new RecalculateBatchDTO();
            RecalculateBatchTaskRef ref = row.getRecalculateBatchTaskEntityRef();

            int id = ((RecalculateBatchTaskPK) ref.getPrimaryKey()).getRecalculateBatchTaskId();
            recalculateBatchDTO.setRecalculateBatchId(id);

            recalculateBatchDTO.setRecalculateBatchVisId(elo.getIdentifier());
            recalculateBatchDTO.setRecalculateBatchDescription(elo.getDescription());

            ModelCoreDTO model = ModelCoreMapper.mapModelRefToModelCoreDTO(elo.getModel());
            recalculateBatchDTO.setModel(model);

            list.add(recalculateBatchDTO);
        }
        return list;
    }

    /**
     * Maps recalculate batch data stored in {@link RecalculateBatchTaskImpl} to data transfer object {@link RecalculateBatchDetailsDTO}
     */
    public static RecalculateBatchDetailsDTO mapToRecalculateBatchDTO(RecalculateBatchTaskImpl impl) {
        RecalculateBatchDetailsDTO recalculateBatchDTO = new RecalculateBatchDetailsDTO();

        int id = ((RecalculateBatchTaskPK) impl.getPrimaryKey()).getRecalculateBatchTaskId();
        recalculateBatchDTO.setRecalculateBatchId(id);
        recalculateBatchDTO.setRecalculateBatchVisId(impl.getIdentifier());
        recalculateBatchDTO.setRecalculateBatchDescription(impl.getDescription());

        // maps saved model (related to recalculate batch) to transfer object - user could change it
        ModelCoreDTO model = new ModelCoreDTO();
        model.setModelId(impl.getModelId());
        recalculateBatchDTO.setModel(model);

        // maps saved budget cycle (related to recalculate batch and choosed model) to transfer object - user could change it
        recalculateBatchDTO.setBudgetCycleId(impl.getBudgetCycleId());

        recalculateBatchDTO.setResponsibilityAreas(impl.getRecalculateBatchTaskRespArea());
        return recalculateBatchDTO;
    }

    /**
     * Method rewrites all fields stored in data transfer object {@link RecalculateBatchDetailsDTO} to database object {@link RecalculateBatchTaskImpl}
     */
    public static RecalculateBatchTaskImpl mapRecalculateBatchDTOToRecalculateBatchTaskImpl(RecalculateBatchTaskImpl impl, RecalculateBatchDetailsDTO recalculateBatchDTO, int userId) {
        impl.setIdentifier(recalculateBatchDTO.getRecalculateBatchVisId());
        impl.setDescription(recalculateBatchDTO.getRecalculateBatchDescription());
        impl.setModelId(recalculateBatchDTO.getModel().getModelId());
        impl.setBudgetCycleId(recalculateBatchDTO.getBudgetCycleId());

        // map correct selected profiles
        List<DataEntryProfileRef> dataEntryProfileRefs = RecalculateBatchesProfileMapper.mapProfilesDTOToProfilesRef(recalculateBatchDTO.getProfiles(), userId);
        impl.setSelectedRecalculateBatchTaskForms(dataEntryProfileRefs);

        // map correct responsibility areas
        int modelId = recalculateBatchDTO.getModel().getModelId();
        List<RecalculateBatchTaskAssignment> taskAssignments = RecalculateBatchesResponsibilityAreaMapper.mapResponsibilityDTOToTaskAssigments(impl, recalculateBatchDTO.getResponsibilityAreas(), modelId);
        impl.setRecalculateBatchTaskAssignments(taskAssignments);
        return impl;
    }
}