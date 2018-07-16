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
import java.util.List;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.recalculate.RecalculateBatchTaskAssignment;
import com.cedar.cp.dto.dimension.ImmediateChildrenELO;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskAssignmentImpl;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskImpl;
import com.cedar.cp.dto.recalculate.RecalculateBatchTaskPK;
import com.softproideas.commons.model.tree.NodeLazyDTO;

/**
 * <p>Class is responsible for maps different object related to responsibility areas which are related to recalculate batch to data transfer object (and vice-versa)</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class RecalculateBatchesResponsibilityAreaMapper {

    /**
     * Maps list of responsibility areas {@link ImmediateChildrenELO} to list of transfer data object {@link NodeLazyDTO} in recalculate batch. 
     * Impl param is necessary to manage which responsibility areas are selected in recalculate batch.
     */
    public static List<NodeLazyDTO> mapToNodeDTO(RecalculateBatchTaskImpl impl, ImmediateChildrenELO list) {
        // collection of selected responsibility areas - structure element ids
        List<Integer> selectedResponsibilityAreas = impl.getRecalculateBatchTaskRespArea();

        List<NodeLazyDTO> children = new ArrayList<NodeLazyDTO>();
        for (int i = 0; i < list.getNumRows(); i++) {
            NodeLazyDTO child = new NodeLazyDTO();

            String id = "" + list.getValueAt(i, "StructureId") + "/" + list.getValueAt(i, "StructureElementId");
            child.setId(id);

            String name = "" + list.getValueAt(i, "VisId") + " - " + list.getValueAt(i, "Description");
            child.setText(name);

            child.setChildren(!(Boolean) list.getValueAt(i, "Leaf"));
            child.setStateLeaf((Boolean) list.getValueAt(i, "Leaf"));
            child.setStateDisabled(!(Boolean) list.getValueAt(i, "Leaf"));

            if (selectedResponsibilityAreas.contains((Integer) list.getValueAt(i, "StructureElementId"))) {
                child.setStateSelected(true);
            }
            children.add(child);
        }
        return children;
    }

    /**
     * Maps list of responsibility areas (list of identifiers) to list of task assignments {@link RecalculateBatchTaskAssignment} in recalculate batch,
     * which is accepted in next data manipulations.
     */
    public static List<RecalculateBatchTaskAssignment> mapResponsibilityDTOToTaskAssigments(RecalculateBatchTaskImpl impl, List<Integer> responsibilityAreas, int modelId) {
        List<RecalculateBatchTaskAssignment> recalculateBatchTaskAssignments = new ArrayList<RecalculateBatchTaskAssignment>();

        for (Integer respArea: responsibilityAreas) {
            ModelRef modelRef = new ModelRefImpl(new ModelPK(modelId), "");
            StructureElementRef structureRef = new StructureElementRefImpl(new StructureElementPK(0, respArea), "");

            int id = 0 - (impl.getRecalculateBatchTaskAssignments().size() + 1);
            RecalculateBatchTaskAssignmentImpl rbta = new RecalculateBatchTaskAssignmentImpl(new RecalculateBatchTaskPK(id));
            rbta.setOwningModelRef(modelRef);
            rbta.setOwningBudgetLocationRef(structureRef);
            rbta.setParents(null);

            recalculateBatchTaskAssignments.add(rbta);
        }
        return recalculateBatchTaskAssignments;
    }
}