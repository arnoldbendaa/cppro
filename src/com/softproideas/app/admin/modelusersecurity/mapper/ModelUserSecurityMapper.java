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
package com.softproideas.app.admin.modelusersecurity.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.user.UserRef;
import com.cedar.cp.dto.budgetlocation.BudgetLocationImpl;
import com.cedar.cp.dto.budgetlocation.UserModelElementAssignmentImpl;
import com.cedar.cp.dto.dimension.ImmediateChildrenELO;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.ejb.api.base.ListSessionServer;
import com.softproideas.app.admin.modelusersecurity.model.ModelUserSecurityDTO;
import com.softproideas.app.admin.modelusersecurity.model.ModelUserSecurityDetailsDTO;
import com.softproideas.app.admin.modelusersecurity.model.ModelUserSecuritySaveData;
import com.softproideas.app.admin.modelusersecurity.model.UserDTO;
import com.softproideas.app.admin.modelusersecurity.model.UserModelElementAssignmentDTO;
import com.softproideas.commons.model.tree.NodeStaticWithIdAndDescriptionDTO;

public class ModelUserSecurityMapper {

    public static List<ModelUserSecurityDTO> mapModelUserSecurity(EntityList list) {
        List<ModelUserSecurityDTO> listDTO = new ArrayList<ModelUserSecurityDTO>();

        for (int i = 0; i < list.getNumRows(); i++) {
            ModelUserSecurityDTO modelUserSecurityDTO = new ModelUserSecurityDTO();
            EntityList row = list.getRowData(i);

            modelUserSecurityDTO.setModelId(((ModelRefImpl) row.getValueAt(0, "Model")).getModelPK().getModelId());
            modelUserSecurityDTO.setModelDescription((String) row.getValueAt(0, "ModelDisplay"));
            modelUserSecurityDTO.setNumUsers((Integer) row.getValueAt(0, "NumUsers"));
            modelUserSecurityDTO.setUserSummary((String) row.getValueAt(0, "UserSummary"));

            listDTO.add(modelUserSecurityDTO);
        }
        return listDTO;
    }

    public static ModelUserSecurityDetailsDTO mapBudgetLocationImpl(BudgetLocationImpl budgetLocationImpl, ListSessionServer listSesionServer) {
        ModelUserSecurityDetailsDTO modelUserSecurityDetailsDTO = new ModelUserSecurityDetailsDTO();

        modelUserSecurityDetailsDTO.setModelId(((ModelPK) budgetLocationImpl.getModelRef().getPrimaryKey()).getModelId());
        modelUserSecurityDetailsDTO.setModelVisId(budgetLocationImpl.getModelRef().getNarrative());
        modelUserSecurityDetailsDTO.setModelUserElementAccess(mapModelUserElementAccess((ArrayList<UserModelElementAssignment>) budgetLocationImpl.getModelUserElementAccess()));
        modelUserSecurityDetailsDTO.setNode(mapBudgetLocationTree(budgetLocationImpl, listSesionServer));

        return modelUserSecurityDetailsDTO;
    }

    /**
     * Map AllUsersELO to List<UserDTO>.
     */
    @SuppressWarnings("unchecked")
    public static List<UserDTO> mapAllUsersELO(AllUsersELO elo) {
        List<UserDTO> usersList = new ArrayList<UserDTO>();

        for (Iterator<AllUsersELO> it = elo.iterator(); it.hasNext();) {
            AllUsersELO row = it.next();

            UserRefImpl userRefImpl = (UserRefImpl) row.getUserEntityRef();
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(((UserPK) userRefImpl.getPrimaryKey()).getUserId());
            userDTO.setUserName(userRefImpl.getNarrative());
            userDTO.setUserFullName(row.getFullName());

            usersList.add(userDTO);
        }

        return usersList;
    }

    public static List<UserModelElementAssignmentDTO> mapModelUserElementAccess(ArrayList<UserModelElementAssignment> elo) {
        List<UserModelElementAssignmentDTO> modelElementAssignmentList = new ArrayList<UserModelElementAssignmentDTO>();

        for (UserModelElementAssignment elementList: elo) {
            UserModelElementAssignmentDTO modelElementAssignmentDTO = new UserModelElementAssignmentDTO();

            modelElementAssignmentDTO.setDescription(elementList.getDescription());
            ModelRef modelRef = (ModelRef) elementList.getModel();
            modelElementAssignmentDTO.setModelId(((ModelPK) modelRef.getPrimaryKey()).getModelId());
            modelElementAssignmentDTO.setModelVisId(modelRef.getNarrative());
            StructureElementRef structureElementRef = (StructureElementRef) elementList.getStructureElementRef();
            modelElementAssignmentDTO.setStructureId(((StructureElementPK) structureElementRef.getPrimaryKey()).getStructureId());
            modelElementAssignmentDTO.setStructureVisId(structureElementRef.getNarrative());
            modelElementAssignmentDTO.setStructureElementId(((StructureElementPK) structureElementRef.getPrimaryKey()).getStructureElementId());
            modelElementAssignmentDTO.setReadOnly(elementList.getReadOnly());
            UserRef userRef = (UserRef) elementList.getUser();
            modelElementAssignmentDTO.setUserId(((UserPK) userRef.getPrimaryKey()).getUserId());
            modelElementAssignmentDTO.setUserVisId(userRef.getNarrative());

            modelElementAssignmentList.add(modelElementAssignmentDTO);
        }

        return modelElementAssignmentList;
    }

    /**
     * Map treeNode .
     */
    @SuppressWarnings("unchecked")
    public static NodeStaticWithIdAndDescriptionDTO mapBudgetLocationTree(BudgetLocationImpl budgetLocationImpl, ListSessionServer listSesionServer) {

        StructureElementRefImpl structureElementRefImpl = (StructureElementRefImpl) budgetLocationImpl.getRootElementEntityRef();
        String rootName = structureElementRefImpl.getNarrative();
        NodeStaticWithIdAndDescriptionDTO nodeStaticRoot = new NodeStaticWithIdAndDescriptionDTO();
        nodeStaticRoot.setText(rootName);
        nodeStaticRoot.setId(rootName);
        nodeStaticRoot.setStateOpened(true);
        nodeStaticRoot.setStructureId(structureElementRefImpl.getStructureElementPK().getId());
        nodeStaticRoot.setStructureElementId(structureElementRefImpl.getStructureElementPK().getStructureElementId());
        nodeStaticRoot.setDescription(structureElementRefImpl.getDisplayText());
        List<NodeStaticWithIdAndDescriptionDTO> childrenLevelOne = new ArrayList<NodeStaticWithIdAndDescriptionDTO>();
        ImmediateChildrenELO list = (ImmediateChildrenELO) budgetLocationImpl.getRootChildren();
        for (Iterator<ImmediateChildrenELO> it = list.iterator(); it.hasNext();) {
            ImmediateChildrenELO element = it.next();
            NodeStaticWithIdAndDescriptionDTO nodeStaticProcess = new NodeStaticWithIdAndDescriptionDTO();
            nodeStaticProcess.setText(element.getVisId() + " - " + element.getDescription());
            nodeStaticProcess.setId(element.getVisId());
            nodeStaticProcess.setStateOpened(false);
            nodeStaticProcess.setStructureId(element.getStructureId());
            nodeStaticProcess.setStructureElementId(element.getStructureElementId());
            nodeStaticProcess.setDescription(element.getDescription());
            childrenLevelOne.add(nodeStaticProcess);
            List<NodeStaticWithIdAndDescriptionDTO> childrenLevelTwo = new ArrayList<NodeStaticWithIdAndDescriptionDTO>();
            ImmediateChildrenELO innerList = (ImmediateChildrenELO) listSesionServer.getImmediateChildren(element.getStructureId(), element.getStructureElementId());
            for (Iterator<ImmediateChildrenELO> innerIt = innerList.iterator(); innerIt.hasNext();) {
                ImmediateChildrenELO innerElement = innerIt.next();
                NodeStaticWithIdAndDescriptionDTO nodeStaticProcessTwo = new NodeStaticWithIdAndDescriptionDTO();
                nodeStaticProcessTwo.setStateOpened(false);
                nodeStaticProcessTwo.setStateLeaf(true);
                nodeStaticProcessTwo.setText(innerElement.getVisId() + " - " + innerElement.getDescription());
                nodeStaticProcessTwo.setId(innerElement.getVisId());
                nodeStaticProcessTwo.setStructureId(innerElement.getStructureId());
                nodeStaticProcessTwo.setStructureElementId(innerElement.getStructureElementId());
                nodeStaticProcessTwo.setDescription(innerElement.getDescription());
                childrenLevelTwo.add(nodeStaticProcessTwo);
            }
            nodeStaticProcess.setChildren(childrenLevelTwo);
        }
        nodeStaticRoot.setChildren(childrenLevelOne);
        return nodeStaticRoot;
    }

    public static BudgetLocationImpl mapModelUserSecurityDetailsDTO(ModelUserSecuritySaveData modelUserSecuritySaveData, BudgetLocationImpl budgetLocationImpl) {
        budgetLocationImpl.setModelUserElementAccess(mapUserModelElementAssignmentDTO(modelUserSecuritySaveData.getModelUserElementAccess()));
        budgetLocationImpl.setDeployForms(modelUserSecuritySaveData.isDeployForms());
        return budgetLocationImpl;
    }

    public static List<UserModelElementAssignment> mapUserModelElementAssignmentDTO(List<UserModelElementAssignmentDTO> listUserModelElementAssignmentDTO) {
        List<UserModelElementAssignment> list = new ArrayList<UserModelElementAssignment>();
        
        for(UserModelElementAssignmentDTO listElement: listUserModelElementAssignmentDTO) {
            ModelPK modelPK = new ModelPK(listElement.getModelId());
            ModelRef modelRef = new ModelRefImpl(modelPK, listElement.getModelVisId());
            UserPK userPK = new UserPK(listElement.getUserId());
            UserRef userRef = new UserRefImpl(userPK, listElement.getUserVisId());
            StructureElementPK structurePK = new StructureElementPK(listElement.getStructureId(), listElement.getStructureElementId());
            StructureElementRef structureElementRef = new StructureElementRefImpl(structurePK, listElement.getStructureVisId());
            String description = listElement.getDescription();
            boolean readOnly = listElement.isReadOnly();
            UserModelElementAssignment userModelElementAssignment = new UserModelElementAssignmentImpl(modelRef, userRef, structureElementRef, description, readOnly);
            list.add(userModelElementAssignment);
        }
        return list;
    }

}
