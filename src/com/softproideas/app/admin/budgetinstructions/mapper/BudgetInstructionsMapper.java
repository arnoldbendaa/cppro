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
package com.softproideas.app.admin.budgetinstructions.mapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.budgetinstruction.AllBudgetInstructionsELO;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentImpl;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionAssignmentPK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionImpl;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionPK;
import com.cedar.cp.dto.budgetinstruction.BudgetInstructionRefImpl;
import com.cedar.cp.dto.dimension.ImmediateChildrenELO;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.BudgetCyclePK;
import com.cedar.cp.dto.model.BudgetCycleRefImpl;
import com.cedar.cp.dto.model.BudgetCyclesForModelELO;
import com.cedar.cp.dto.model.BudgetHierarchyRootNodeForModelELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.softproideas.app.admin.budgetinstructions.model.BudgetInstructionAssignmentsNodeDTO;
import com.softproideas.app.admin.budgetinstructions.model.BudgetInstructionDTO;
import com.softproideas.app.admin.budgetinstructions.model.BudgetInstructionDetailsDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.tree.NodeLazyDTO;

public class BudgetInstructionsMapper {

    /**
     * Map all budget instruction
     */
    public static List<BudgetInstructionDTO> mapAllBudgetInstructionsELO(AllBudgetInstructionsELO elo) {
        List<BudgetInstructionDTO> list = new ArrayList<BudgetInstructionDTO>();
        for (@SuppressWarnings("unchecked")
        Iterator<AllBudgetInstructionsELO> it = elo.iterator(); it.hasNext();) {
            AllBudgetInstructionsELO row = it.next();
            BudgetInstructionDTO budgetInstructionDTO = new BudgetInstructionDTO();
            BudgetInstructionRefImpl budgetInstructionRefImpl = (BudgetInstructionRefImpl) row.getBudgetInstructionEntityRef();
            budgetInstructionDTO.setBudgetInstructionId(budgetInstructionRefImpl.getBudgetInstructionPK().getBudgetInstructionId());
            budgetInstructionDTO.setBudgetInstructionVisId(budgetInstructionRefImpl.getNarrative());
            budgetInstructionDTO.setBudgetInstructionDocumentRef(row.getDocumentRef());
            list.add(budgetInstructionDTO);
        }
        return list;
    }

    /**
     * Map budget instruction details DTO 
     */
    @SuppressWarnings("unchecked")
    public static BudgetInstructionDetailsDTO mapBudgetInstructionDetailsDTO(BudgetInstructionImpl budgetInstructionImpl) throws ServiceException {
        BudgetInstructionDetailsDTO budgetInstructionDetailsDTO = new BudgetInstructionDetailsDTO();
        if (budgetInstructionImpl.getPrimaryKey() != null) {
            budgetInstructionDetailsDTO.setBudgetInstructionId(((BudgetInstructionPK) budgetInstructionImpl.getPrimaryKey()).getBudgetInstructionId());
        } else {
            budgetInstructionDetailsDTO.setBudgetInstructionId(-1);
        }

        List<Object> selected = budgetInstructionImpl.getBudgetInstructionAssignments();
        budgetInstructionDetailsDTO.setAssignments(mapListObject(selected));
        budgetInstructionDetailsDTO.setBudgetInstructionVisId(budgetInstructionImpl.getVisId());
        budgetInstructionDetailsDTO.setBudgetInstructionDocumentRef(budgetInstructionImpl.getDocumentRef());
        return budgetInstructionDetailsDTO;
    }

    /**
     * Map budget instruction impl 
     */
    public static BudgetInstructionImpl mapBudgetInstructionImpl(BudgetInstructionImpl budgetInstructionImpl, BudgetInstructionDetailsDTO budgetInstruction) throws ServiceException {
        try {
            budgetInstructionImpl.setDocumentRef(budgetInstruction.getBudgetInstructionDocumentRef());
            budgetInstructionImpl.setVisId(budgetInstruction.getBudgetInstructionVisId());
            List<Object> node = mapBudgetInstructionAssigmentsNodeDTO(budgetInstruction.getAssignments());
            if (budgetInstruction.getBudgetInstructionDocument() == null) {
                budgetInstructionImpl.setDocument(budgetInstructionImpl.getDocument());
            } else {
                budgetInstructionImpl.setDocument(budgetInstruction.getBudgetInstructionDocument().getBytes());
            }
            budgetInstructionImpl.setBudgetInstructionAssignments(node);
            return budgetInstructionImpl;
        } catch (IOException e) {
            throw new ServiceException("Error during dalete budget instruction =" + e + "!");
        }

    }

    /**
     * Map all models for node
     */
    public static List<NodeLazyDTO> mapAllModelsELO(AllModelsELO modelsList, BudgetInstructionImpl budgetInstructionImpl) {
        List<NodeLazyDTO> nodesList = new ArrayList<NodeLazyDTO>();
        for (int i = 0; i < modelsList.getNumRows(); i++) {
            NodeLazyDTO nodeModel = new NodeLazyDTO();
            ModelRef modelRef = (ModelRef) modelsList.getValueAt(i, "Model");
            String description = (String) modelsList.getValueAt(i, "Description");
            description = " " + description;
            String modelName = modelRef.getNarrative() + " " + "-" + description;
            nodeModel.setText(modelName);
            ModelPK modelPK = (ModelPK) modelRef.getPrimaryKey();
            String modelId = "modelId/" + modelPK.getModelId();
            nodeModel.setId(modelId);
            nodeModel.setChildren(true);
            nodeModel.setStateDisabled(true);
            nodesList.add(nodeModel);
        }
        return nodesList;

    }

    /**
     * Separate items for 2 folders.
     */
    public static List<NodeLazyDTO> mapHierarchiesAndCyclesNode(BudgetInstructionImpl budgetInstructionImpl, Integer modelId) {
        ModelPK modelPK = new ModelPK(modelId);
        List<NodeLazyDTO> list = new ArrayList<NodeLazyDTO>();
        NodeLazyDTO budgetCycles = buildBudgetCycles("budgetCycles/" + modelPK.getModelId(), "Budget Cycles");
        NodeLazyDTO responsibilityAreas = buildResponsibilityAreas("respAreas/" + modelPK.getModelId(), "ResponsibilityAreas");
        list.add(budgetCycles);
        list.add(responsibilityAreas);
        return list;

    }

    /**
     * Create empty folder for node (Budget Cycles)
     */
    private static NodeLazyDTO buildBudgetCycles(String id, String cyclesText) {
        NodeLazyDTO nodeLazyDTO = new NodeLazyDTO();
        nodeLazyDTO.setId(id);
        nodeLazyDTO.setText(cyclesText);
        nodeLazyDTO.setChildren(true);
        nodeLazyDTO.setStateDisabled(true);
        return nodeLazyDTO;
    }

    /**
     * Create empty folder for node(Responsibility Area)
     */
    private static NodeLazyDTO buildResponsibilityAreas(String id, String areasText) {
        NodeLazyDTO nodeLazyDTO = new NodeLazyDTO();
        nodeLazyDTO.setId(id);
        nodeLazyDTO.setText(areasText);
        nodeLazyDTO.setChildren(true);
        nodeLazyDTO.setStateDisabled(true);
        return nodeLazyDTO;
    }

    /**
     * Map all budget cycles for node
     */
    public static List<NodeLazyDTO> mapBudgetCyclesNode(BudgetCyclesForModelELO cycles, BudgetInstructionImpl budgetInstructionImpl) {
        @SuppressWarnings("unchecked")
        List<Object> selectedItems = budgetInstructionImpl.getBudgetInstructionAssignments();
        List<NodeLazyDTO> nodesList = new ArrayList<NodeLazyDTO>();
        for (int i = 0; i < cycles.getNumRows(); i++) {
            NodeLazyDTO nodeModel = new NodeLazyDTO();
            BudgetCycleRefImpl modelRef = (BudgetCycleRefImpl) cycles.getValueAt(i, "BudgetCycle");
            String description = (String) cycles.getValueAt(i, "Description");
            String narrative = modelRef.getNarrative();
            nodeModel.setText(narrative + " " + "-" + " " + description);
            nodeModel.setId(Integer.toString(modelRef.getBudgetCyclePK().getBudgetCycleId()));
            nodeModel.setChildren(false);
            nodeModel.setStateDisabled(false);
            int selectedBudgetCycles = modelRef.getBudgetCyclePK().getBudgetCycleId();
            for (Object x: selectedItems) {
                BudgetCycleRef budgetCycleRef = ((BudgetInstructionAssignmentImpl) x).getOwningBudgetCycleRef();
                if (budgetCycleRef == null) {
                    nodeModel.setStateSelected(false);
                } else {
                    BudgetCyclePK budgetCyclePK = (BudgetCyclePK) budgetCycleRef.getPrimaryKey();
                    int budgetCycleId = (Integer) budgetCyclePK.getBudgetCycleId();
                    if (selectedBudgetCycles == budgetCycleId) {
                        nodeModel.setStateSelected(true);
                    }
                }
            }

            nodesList.add(nodeModel);
        }
        return nodesList;
    }

    /**
     * Map all hierarchies for model (Responsibility Area)
     */
    @SuppressWarnings("unused")
    public static List<NodeLazyDTO> mapHierarchiesForModelELO(BudgetHierarchyRootNodeForModelELO hierarchiesforModelELO, BudgetInstructionImpl budgetInstructionImpl) {
        List<NodeLazyDTO> nodesList = new ArrayList<NodeLazyDTO>();
        for (int i = 0; i < hierarchiesforModelELO.getNumRows(); i++) {
            NodeLazyDTO nodeHierarchyModel = new NodeLazyDTO();
            String hierarchyName = (String) hierarchiesforModelELO.getValueAt(i, "VisId");
            nodeHierarchyModel.setText(hierarchyName);
            int structureId = (Integer) hierarchiesforModelELO.getValueAt(i, "StructureId");
            int structureElementId = (Integer) hierarchiesforModelELO.getValueAt(i, "StructureElementId");
            String id = "structureId/" + structureId + "/structureElemenId/" + structureElementId;
            nodeHierarchyModel.setId(id);
            nodeHierarchyModel.setChildren(true);
            nodesList.add(nodeHierarchyModel);
            break;
        }
        return nodesList;
    }

    /**
     * Map children for hierarchies
     */
    public static List<NodeLazyDTO> mapImmediateChildrenELO(ImmediateChildrenELO childrenList, BudgetInstructionImpl budgetInstructionImpl) {
        @SuppressWarnings("unchecked")
        List<Object> selectedItems = budgetInstructionImpl.getBudgetInstructionAssignments();
        List<NodeLazyDTO> nodesList = new ArrayList<NodeLazyDTO>();
        for (int i = 0; i < childrenList.getNumRows(); i++) {
            NodeLazyDTO immediateChildren = new NodeLazyDTO();
            String childrenName = (String) childrenList.getValueAt(i, "VisId") + " - " + childrenList.getValueAt(i, "Description");
            immediateChildren.setText(childrenName);
            int structureId = (Integer) childrenList.getValueAt(i, "StructureId");
            int structureElementId = (Integer) childrenList.getValueAt(i, "StructureElementId");
            String id = "structureId/" + structureId + "/structureElemenId/" + structureElementId;
            immediateChildren.setId(id);
            immediateChildren.setChildren(!(Boolean) childrenList.getValueAt(i, "Leaf"));
            for (Object x: selectedItems) {
                StructureElementRef structureElementRef = ((BudgetInstructionAssignmentImpl) x).getOwningBudgetLocationRef();
                if (structureElementRef == null) {
                    immediateChildren.setStateSelected(false);
                } else {
                    StructureElementPK structurePK = (StructureElementPK) structureElementRef.getPrimaryKey();
                    int selectedStructureID = structurePK.getStructureId();
                    int selectedStrctureElementId = structurePK.getStructureElementId();
                    if (structureId == selectedStructureID && selectedStrctureElementId == structureElementId) {
                        immediateChildren.setStateSelected(true);
                        break;
                    } else {
                        immediateChildren.setStateSelected(false);
                    }
                }
            }
            nodesList.add(immediateChildren);
        }

        return nodesList;
    }

    /**
     * Map selected items 
     */
    public static List<Object> mapBudgetInstructionAssigmentsNodeDTO(List<BudgetInstructionAssignmentsNodeDTO> node) {
        List<Object> list = new ArrayList<Object>();
        int index = -2;
        for (BudgetInstructionAssignmentsNodeDTO budgetCol: node) {

            String narrative = budgetCol.getNarrative();
            int modelId = budgetCol.getModelId();
            int structureId = budgetCol.getStructureId();
            int strucureElementId = budgetCol.getStructureElementId();
            int budgetCycleId = budgetCol.getBudgetCycleId();
            int assignmentId = budgetCol.getAssignmentId();
            if (assignmentId == 0) {
                assignmentId = index--;
            }
            BudgetInstructionAssignmentPK assignmentPK = new BudgetInstructionAssignmentPK(assignmentId);
            BudgetInstructionAssignmentImpl impl = new BudgetInstructionAssignmentImpl(assignmentPK);

            if (modelId == -1) {
                ModelRefImpl modelRefImpl = null;
                impl.setOwningModelRef(modelRefImpl);
            } else {
                ModelPK modelPK = new ModelPK(modelId);
                ModelRefImpl modelRefImpl = new ModelRefImpl(modelPK, narrative);
                impl.setOwningModelRef(modelRefImpl);
            }
            if (strucureElementId == -1 && structureId == -1) {
                StructureElementRefImpl structureElementRefImpl = null;
                impl.setOwningBudgetLocationRef(structureElementRefImpl);
            } else {
                StructureElementPK key = new StructureElementPK(structureId, strucureElementId);
                StructureElementRefImpl structureElementRefImpl = new StructureElementRefImpl(key, narrative);
                impl.setOwningBudgetLocationRef(structureElementRefImpl);
            }
            if (budgetCycleId == -1) {
                BudgetCycleRefImpl budgetCycleRefimpl = null;
                impl.setOwningBudgetCycleRef(budgetCycleRefimpl);
            } else {
                BudgetCyclePK budgetCyclePK = new BudgetCyclePK(budgetCycleId);
                BudgetCycleRefImpl budgetCycleRefimpl = new BudgetCycleRefImpl(budgetCyclePK, narrative);
                impl.setOwningBudgetCycleRef(budgetCycleRefimpl);
            }

            list.add(impl);
        }

        return list;
    }

    /**
     * Map selected items
     */
    public static List<BudgetInstructionAssignmentsNodeDTO> mapListObject(List<Object> selected) {
        List<BudgetInstructionAssignmentsNodeDTO> list = new ArrayList<BudgetInstructionAssignmentsNodeDTO>();
        for (Object x: selected) {
            BudgetInstructionAssignmentsNodeDTO budgetInstructionAssignmentsNodeDTO = new BudgetInstructionAssignmentsNodeDTO();
            ModelRefImpl modelRefImpl = (ModelRefImpl) ((BudgetInstructionAssignmentImpl) x).getOwningModelRef();
            if (modelRefImpl == null) {
                int modelId = -1;
                budgetInstructionAssignmentsNodeDTO.setModelId(modelId);
            } else {
                int modelId = modelRefImpl.getModelPK().getModelId();
                budgetInstructionAssignmentsNodeDTO.setModelId(modelId);
            }
            BudgetCycleRef budgetCycleRef = ((BudgetInstructionAssignmentImpl) x).getOwningBudgetCycleRef();
            if (budgetCycleRef == null) {
                int budgetCycleId = -1;
                budgetInstructionAssignmentsNodeDTO.setBudgetCycleId(budgetCycleId);
            } else {
                BudgetCyclePK budgetCyclePK = (BudgetCyclePK) budgetCycleRef.getPrimaryKey();
                int budgetCycleId = budgetCyclePK.getBudgetCycleId();
                budgetInstructionAssignmentsNodeDTO.setBudgetCycleId(budgetCycleId);
            }
            StructureElementRef structureElementRef = ((BudgetInstructionAssignmentImpl) x).getOwningBudgetLocationRef();
            if (structureElementRef == null) {
                int structureId = -1;
                int structureElementId = -1;
                budgetInstructionAssignmentsNodeDTO.setStructureElementId(structureElementId);
                budgetInstructionAssignmentsNodeDTO.setStructureId(structureId);
                String narrative = "";
                budgetInstructionAssignmentsNodeDTO.setNarrative(narrative);
            } else {
                StructureElementPK structurePK = (StructureElementPK) structureElementRef.getPrimaryKey();
                int structureId = structurePK.getStructureId();
                int structureElementId = structurePK.getStructureElementId();
                budgetInstructionAssignmentsNodeDTO.setStructureElementId(structureElementId);
                budgetInstructionAssignmentsNodeDTO.setStructureId(structureId);
                String narrative = structureElementRef.getNarrative();
                budgetInstructionAssignmentsNodeDTO.setNarrative(narrative);
            }
            int assignmentId = ((BudgetInstructionAssignmentImpl) x).getId();
            budgetInstructionAssignmentsNodeDTO.setAssignmentId(assignmentId);

            list.add(budgetInstructionAssignmentsNodeDTO);
        }

        return list;
    }

}
