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
package com.softproideas.app.admin.users.mapper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.xmlform.XmlFormRef;
import com.cedar.cp.dto.dimension.ImmediateChildrenELO;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.BudgetHierarchyRootNodeForModelELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.role.AllHiddenRolesELO;
import com.cedar.cp.dto.role.RolePK;
import com.cedar.cp.dto.role.RoleRefImpl;
import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.user.UserImpl;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.dto.user.UserRefImpl;
import com.cedar.cp.dto.xmlform.AllXmlFormsELO;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.dto.xmlform.XmlFormRefImpl;
import com.cedar.cp.util.Cryptography;
import com.softproideas.app.admin.users.model.UserDetailsDTO;
import com.softproideas.app.admin.users.model.UserSecurityNodeDTO;
import com.softproideas.app.core.financecube.model.FinanceCubeCoreDTO;
import com.softproideas.app.core.flatform.model.FlatFormExtendedCoreDTO;
import com.softproideas.app.core.roles.model.RoleCoreDTO;
import com.softproideas.app.core.users.model.UserCoreDTO;
import com.softproideas.commons.model.tree.NodeLazyDTO;
import com.softproideas.commons.model.tree.NodeStaticDTO;

public class UsersMapper {

    /**
     * Map all users
     */
    @SuppressWarnings("unchecked")
    public static List<UserCoreDTO> mapAllUsersELO(AllUsersELO elo) {
        List<UserCoreDTO> usersList = new ArrayList<UserCoreDTO>();
        for (Iterator<AllUsersELO> it = elo.iterator(); it.hasNext();) {
            AllUsersELO row = it.next();
            UserCoreDTO userDTO = new UserCoreDTO();
            UserRefImpl userRefImpl = (UserRefImpl) row.getUserEntityRef();
            userDTO.setUserId(userRefImpl.getUserPK().getUserId());
            userDTO.setUserDisabled(row.getUserDisabled());
            userDTO.setUserFullName(row.getFullName());
            userDTO.setUserName(userRefImpl.getNarrative());
            usersList.add(userDTO);
        }
        return usersList;
    }

    /**
     *  Map UserDetailsDTO to UserImpl.
     */
    public static UserImpl mapUserImpl(UserImpl userImpl, UserDetailsDTO userDTO) {
        userImpl.setName(userDTO.getUserName());
        userImpl.setFullName(userDTO.getUserFullName());
        userImpl.setEMailAddress(userDTO.getEmailAddress());
        List<RoleRefImpl> roles = UsersMapper.mapRoleDTO(userDTO.getRoles(), false);
        userImpl.setRoles(roles);
        userImpl.setLogonAlias(userDTO.getLogonAlias());
        if (userDTO.getPassword() != null && userDTO.getConfirmPassword() != null) {
            String passwordBytes = Cryptography.encrypt(userDTO.getPassword(), "fc30");
            userImpl.setPasswordBytes(passwordBytes);
            userImpl.setPassword(userDTO.getPassword());
        }
        userImpl.setExternalSystemUserName(userDTO.getExternalSystemUserName());
        userImpl.setButtonsVisible(userDTO.isAreButtonsVisible());
        userImpl.setPasswordNeverExpires(userDTO.isPasswordNeverExpires());
        userImpl.setUserDisabled(userDTO.isUserDisabled());
        userImpl.setNewFeaturesEnabled(userDTO.isNewFeaturesEnabled());
        userImpl.setShowBudgetActivity(userDTO.isShowBudgetActivity());
        userImpl.setNewView(userDTO.isNewView());
        userImpl.setUserReadOnly(userDTO.isUserReadOnly());
        userImpl.setRoadMapAvailable(userDTO.getRoadMapAvailable());
        List<Object[]> security = UsersMapper.mapUserSecurityNodeDTO(userDTO.getUserSecurityAssignments(), userDTO.getUserId());
        userImpl.setUserAssignments(security);
        // userImpl.setUserAdminAppRoles(userDTO.getUserAdminApp());
        userImpl.setUserAdminAppRoles(UsersMapper.mapRoleDTO(userDTO.getHiddenRoles(), true));
        List<List<Object>> selectXmlForm = UsersMapper.mapUserXmlDTOfromList(userDTO.getXmlForm());
        userImpl.setUserXmlForms(selectXmlForm);
        return userImpl;
    }

    /**
     * Map UserImpl to UserDetailsDTO
     */
    @SuppressWarnings({ "unchecked" })
    public static UserDetailsDTO mapUserDetails(UserImpl user, AllHiddenRolesELO elo, AllXmlFormsELO availableXmlForm) {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        boolean isUserCreated;
        if (user.getPrimaryKey() != null) {
            userDetailsDTO.setUserId(((UserPK) (user.getPrimaryKey())).getUserId());
            isUserCreated = false;
        } else {
            userDetailsDTO.setUserId(-1);
            isUserCreated = true;
        }
        userDetailsDTO.setUserName(user.getName());
        userDetailsDTO.setUserFullName(user.getFullName());
        userDetailsDTO.setUserDisabled(user.isUserDisabled());
        userDetailsDTO.setEmailAddress(user.getEMailAddress());
        List<RoleCoreDTO> roles = mapRoleRefImpl(user.getRoles());
        userDetailsDTO.setRoles(roles);
        userDetailsDTO.setLogonAlias(user.getLogonAlias());
        // userDetailsDTO.setConfirmPassword(user.getPassword());
        // userDetailsDTO.setPassword(user.getPassword());
        userDetailsDTO.setExternalSystemUserName(user.getExternalSystemUserName());
        userDetailsDTO.setPasswordNeverExpires(user.isPasswordNeverExpires());
        userDetailsDTO.setUserReadOnly(user.isUserReadOnly());
        userDetailsDTO.setNewFeaturesEnabled(user.isNewFeaturesEnabled());
        userDetailsDTO.setAreButtonsVisible(user.areButtonsVisible());
        userDetailsDTO.setShowBudgetActivity(user.isShowBudgetActivity());
        userDetailsDTO.setNewView(user.isNewView());
        userDetailsDTO.setRoadMapAvailable(user.getRoadMapAvailable());
        List<UserSecurityNodeDTO> selected = mapListObject(user.getUserAssignments());
        userDetailsDTO.setUserSecurityAssignments(selected);
        List<RoleRefImpl> hiddenRoles = user.getUserAdminAppRoles();
        List<NodeStaticDTO> nodeAdminApp = UsersMapper.mapAdminAppRootModel(elo, hiddenRoles, isUserCreated);
        userDetailsDTO.setUserAdminApp(nodeAdminApp);
        userDetailsDTO.setHiddenRoles(UsersMapper.mapRoleRefImpl(hiddenRoles));
        List<List<Object>> xmlForms = user.getUserXmlForms();
        userDetailsDTO.setXmlForm(UsersMapper.mapListObjectXml(xmlForms));
        userDetailsDTO.setAvailableXmlForm(UsersMapper.mapUserAvailableXmlFormDTO(availableXmlForm));
        return userDetailsDTO;
    }

    /**
     * Map ListObjectXML-Forms List<UserXmlFormDTO> for user.
     */
    public static List<FlatFormExtendedCoreDTO> mapListObjectXml(List<List<Object>> forms) {
        List<FlatFormExtendedCoreDTO> list = new ArrayList<FlatFormExtendedCoreDTO>();
        for (List<Object> obj: forms) {
            FlatFormExtendedCoreDTO userXmlFormDTO = new FlatFormExtendedCoreDTO();
            String name = (String) obj.get(1).toString();

            FinanceCubeCoreDTO financeCubeCoreDTO = new FinanceCubeCoreDTO();
            financeCubeCoreDTO.setFinanceCubeVisId(name);
            userXmlFormDTO.setFinanceCube(financeCubeCoreDTO);

            Timestamp time = (Timestamp) obj.get(5);
            String format = "MMM dd, yyyy";
            userXmlFormDTO.setUpdateTime(mapTimestampToDateString(time, format));

            XmlFormRefImpl xmlId = (XmlFormRefImpl) obj.get(0);
            userXmlFormDTO.setFlatFormVisId(xmlId.getNarrative());

            String description = (String) obj.get(3);
            userXmlFormDTO.setFlatFormDescription(description);

            int formId = (Integer) obj.get(4);
            userXmlFormDTO.setFlatFormId(formId);
            list.add(userXmlFormDTO);
        }
        return list;
    }

    /**
    * Map UserXMLformDTO to List<List<Object>> for user.
    */
    public static List<List<Object>> mapUserXmlDTOfromList(List<FlatFormExtendedCoreDTO> list) {
        List<List<Object>> object = new ArrayList<List<Object>>();
        for (FlatFormExtendedCoreDTO x: list) {
            String financeCubeName = x.getFinanceCube().getFinanceCubeVisId();
            String xmlFormId = x.getFlatFormVisId();
            String description = x.getFlatFormDescription();
            String time = x.getUpdateTime();
            int formId = x.getFlatFormId();

            List<Object> element = new ArrayList<Object>();
            element.add(financeCubeName);
            element.add(xmlFormId);
            element.add(description);
            element.add(time);
            element.add(formId);

            object.add(element);
        }
        return object;
    }

    /**
     * Map RoleMapRef to RoleDTO
     */
    private static List<RoleCoreDTO> mapRoleRefImpl(List<RoleRefImpl> roles) {
        List<RoleCoreDTO> list = new ArrayList<RoleCoreDTO>();
        for (RoleRefImpl role: roles) {
            RoleCoreDTO roleDTO = new RoleCoreDTO();
            roleDTO.setRoleId(role.getRolePK().getRoleId());
            roleDTO.setRoleVisId(role.getNarrative());
            roleDTO.setRoleDescription(role.getDisplayText());
            list.add(roleDTO);
        }
        return list;
    }

    /**
     * Map RoleDTO to RoleRefImpl
     */
    public static List<RoleRefImpl> mapRoleDTO(List<RoleCoreDTO> roles, boolean hidden) {
        List<RoleRefImpl> list = new ArrayList<RoleRefImpl>();
        if (hidden) {
            boolean moreThanAdminAccessAndBudgesCycleCategories = false;
            for (int i = 0; i < roles.size(); i++) {
                if (!(roles.get(0).getRoleId() == 9 || roles.get(i).getRoleVisId().equals("Budget Cycle - Management Accounts") || roles.get(i).getRoleVisId().equals("Budget Cycle - Forecast") || roles.get(i).getRoleVisId().equals("Budget Cycle - Budget") || roles.get(i).getRoleVisId().equals("Dashboard Hierarchy Open") || roles.get(i).getRoleVisId().equals("Dashboard Free Form Open") || roles.get(i).getRoleVisId().equals("Auction Open"))) {
                    moreThanAdminAccessAndBudgesCycleCategories = true;
                }
            }
            if (moreThanAdminAccessAndBudgesCycleCategories && hidden) {
                RolePK rolePK = new RolePK(9);
                RoleRefImpl role = new RoleRefImpl(rolePK, "AdminAccess");
                list.add(role);
            }
        }
        for (RoleCoreDTO role: roles) {
            RolePK rolePK = new RolePK(role.getRoleId());
            String narrative = role.getRoleVisId();
            RoleRefImpl roleRefImpl = new RoleRefImpl(rolePK, narrative);
            list.add(roleRefImpl);
        }
        return list;
    }

    /**
     * Map allModelsELO , TreeNode - models name
     */
    public static List<NodeLazyDTO> mapAllModelsELO(AllModelsELO modelsList) {
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
            // nodeModel.setStateLeaf(false);
            nodeModel.setStateDisabled(true);
            nodesList.add(nodeModel);
        }
        return nodesList;
    }

    /**
     * Map hierarchiesForModelELO , TreeNode - hierarchies 
     */
    public static List<NodeLazyDTO> mapHierarchiesForModelELO(BudgetHierarchyRootNodeForModelELO hierarchyList, UserImpl userImpl) {
        List<NodeLazyDTO> nodesList = new ArrayList<NodeLazyDTO>();
        for (int i = 0; i < hierarchyList.getNumRows(); i++) {
            NodeLazyDTO nodeHierarchyModel = new NodeLazyDTO();
            String hierarchyName = (String) hierarchyList.getValueAt(i, "VisId");
            nodeHierarchyModel.setText(hierarchyName);
            int structureId = (Integer) hierarchyList.getValueAt(i, "StructureId");
            int structureElementId = (Integer) hierarchyList.getValueAt(i, "StructureElementId");
            String id = "structureId/" + structureId + "/structureElemenId/" + structureElementId;
            nodeHierarchyModel.setId(id);
            nodeHierarchyModel.setChildren(true);

            List<Object[]> selected = userImpl.getUserAssignments();
            StructureElementRef structureElementRef = (StructureElementRef) hierarchyList.getValueAt(i, "StructureElement");
            StructureElementRef selectedStructureElementRef;
            for (Object[] selectedElement: selected) {
                selectedStructureElementRef = (StructureElementRef) selectedElement[2];
                if (structureElementRef.equals(selectedStructureElementRef)) {
                    nodeHierarchyModel.setStateSelected(true);
                }
            }

            nodesList.add(nodeHierarchyModel);
        }
        return nodesList;
    }

    /**
     * Map immediateChildrenELO , TreeNode - ImmediateChildren - name
     */
    public static List<NodeLazyDTO> mapImmediateChildrenELO(ImmediateChildrenELO childrenList, UserImpl userImpl) {
        List<Object[]> selected = userImpl.getUserAssignments();
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
            immediateChildren.setStateSelected(false);
            for (Object[] selectedElement: selected) {
                Object selectedData = selectedElement[2];
                StructureElementRefImpl refImpl = (StructureElementRefImpl) selectedData;
                int selectedStrucId = refImpl.getStructureId();
                StructureElementPK strEle = refImpl.getStructureElementPK();
                int selectedStrucElementId = strEle.getStructureElementId();
                if (selectedStrucId == structureId && selectedStrucElementId == structureElementId) {
                    immediateChildren.setStateSelected(true);
                    break;
                }
            }
            nodesList.add(immediateChildren);
        }
        return nodesList;
    }

    /**
     * Map ListObject List<UserSecurityNodeDTO> for user
     */
    public static List<UserSecurityNodeDTO> mapListObject(List<Object[]> selected) {
        List<UserSecurityNodeDTO> list = new ArrayList<UserSecurityNodeDTO>();

        for (Object[] data: selected) {
            UserSecurityNodeDTO userSecDTO = new UserSecurityNodeDTO();
            userSecDTO.setModelId((Integer) data[0]);
            StructureElementRef structureElementRef = (StructureElementRefImpl) data[2];
            StructureElementPK structureElementPK = (StructureElementPK) structureElementRef.getPrimaryKey();
            userSecDTO.setStructureElementId(structureElementPK.getStructureElementId());
            userSecDTO.setStructureId(structureElementPK.getStructureId());
            userSecDTO.setNarrative(structureElementRef.getNarrative());
            list.add(userSecDTO);

        }

        return list;
    }

    /**
     * Map UserSecurityNodeDTO List<Object[]> for user.
     */
    public static List<Object[]> mapUserSecurityNodeDTO(List<UserSecurityNodeDTO> node, int userId) {
        List<Object[]> list = new ArrayList<Object[]>();
        for (UserSecurityNodeDTO usnd: node) {
            String narrative = usnd.getNarrative();
            int modelId = usnd.getModelId();
            int structureId = usnd.getStructureId();
            int strucureElementId = usnd.getStructureElementId();
            Object[] element = new Object[3];
            element[0] = modelId;
            element[1] = userId;
            StructureElementPK key = new StructureElementPK(structureId, strucureElementId);
            StructureElementRefImpl impl = new StructureElementRefImpl(key, narrative);
            element[2] = impl;

            list.add(element);
        }
        return list;
    }

    /**
     * Build AdminApp models list, userDetails tab.
     */
    public static List<NodeStaticDTO> mapAdminAppRootModel(AllHiddenRolesELO elo, List<RoleRefImpl> selected, boolean isUserCreated) {
        List<NodeStaticDTO> nodesList = new ArrayList<NodeStaticDTO>();
        NodeStaticDTO root = buildAdminAppNode(0, "Activities", elo, selected);
        root.setStateOpened(true);
        root.setStateDisabled(true);
        root.setStateLeaf(true);
        nodesList.add(root);

        NodeStaticDTO rootBudgetCycleCategorySecurityTree = buildBudgetCycleCategoryNode(10000, "Budget Cycle - Categories", elo, selected, isUserCreated);
        rootBudgetCycleCategorySecurityTree.setStateOpened(true);
        rootBudgetCycleCategorySecurityTree.setStateDisabled(true);
        rootBudgetCycleCategorySecurityTree.setStateLeaf(true);
        nodesList.add(rootBudgetCycleCategorySecurityTree);
        
        NodeStaticDTO rootDashboardAppSecurityTree = buildDashboardAppNode(20000, "Dashboard App", elo, selected, isUserCreated);
        rootDashboardAppSecurityTree.setStateOpened(true);
        rootDashboardAppSecurityTree.setStateDisabled(true);
        rootDashboardAppSecurityTree.setStateLeaf(true);
        nodesList.add(rootDashboardAppSecurityTree);
        
        
        return nodesList;
    }

    private static boolean isMainAdminAppTree(String name) {
        if (name.equals("Budget Cycle - Management Accounts") || name.equals("Budget Cycle - Forecast") || name.equals("Budget Cycle - Budget")) {
            return false;
        } else {
            return true;
        }
    }
    
    private static boolean isMainAdminAppTree2(String name) {
        if (name.equals("Dashboard Hierarchy Open") || name.equals("Dashboard Free Form Open") || name.equals("Auction Open")) {
            return false;
        } else {
            return true;
        }
    }

    /**
    * Build AdminApp Node part AdminApp models list, userDetails tab.
    */
    private static NodeStaticDTO buildAdminAppNode(int roleId, String roleText, AllHiddenRolesELO allRoles, List<RoleRefImpl> selected) {
        NodeStaticDTO node = new NodeStaticDTO();

        node.setId(Integer.toString(roleId));
        node.setText(roleText);

        List<NodeStaticDTO> children = new ArrayList<NodeStaticDTO>();
        for (int i = 0; i < allRoles.getNumRows(); i++) {
            int parentId = (Integer) allRoles.getValueAt(i, "Parent");
            if (parentId == roleId) {
                RoleRefImpl roleImpl = (RoleRefImpl) allRoles.getValueAt(i, "Role");
                int id = (roleImpl.getRolePK().getRoleId());
                String narrative = roleImpl.getNarrative();
                if (isMainAdminAppTree(narrative) && isMainAdminAppTree2(narrative)) {
                    NodeStaticDTO child = buildAdminAppNode(id, narrative, allRoles, selected);
                    for (RoleRefImpl x: selected) {
                        if (id == x.getRolePK().getRoleId()) {
                            child.setStateSelected(true);

                        }
                    }
                    children.add(child);
                }
            }
        }
        node.setChildren(children);
        return node;

    }

    /**
    * Build BudgetCycleCategory Node part AdminApp models list, userDetails tab.
    */
    private static NodeStaticDTO buildBudgetCycleCategoryNode(int roleId, String roleText, AllHiddenRolesELO allRoles, List<RoleRefImpl> selected, boolean isUserCreated) {
        NodeStaticDTO node = new NodeStaticDTO();

        node.setId(Integer.toString(roleId));
        node.setText(roleText);

        List<NodeStaticDTO> children = new ArrayList<NodeStaticDTO>();
        for (int i = 0; i < allRoles.getNumRows() && children.size() < 3; i++) {
            RoleRefImpl roleImpl = (RoleRefImpl) allRoles.getValueAt(i, "Role");
            int id = (roleImpl.getRolePK().getRoleId());
            String narrative = roleImpl.getNarrative();
            if (!isMainAdminAppTree(narrative)) {
                NodeStaticDTO child = new NodeStaticDTO();
                child.setId(Integer.toString(id));
                child.setText(narrative);
                for (RoleRefImpl x: selected) {
                    if (id == x.getRolePK().getRoleId()) {
                        child.setStateSelected(true);

                    }
                }
                // Access to Budget category: Budget Cycle - Budget is default selected when User is created.
                if (isUserCreated && narrative.equals("Budget Cycle - Budget")) {
                    child.setStateSelected(true);
                    selected.add(roleImpl);
                }
                children.add(child);
            }
        }
        node.setChildren(children);
        return node;

    }
    /**
    * Build Dashboard Node part AdminApp models list, userDetails tab.
    */
    private static NodeStaticDTO buildDashboardAppNode(int roleId, String roleText, AllHiddenRolesELO allRoles, List<RoleRefImpl> selected, boolean isUserCreated) {
        NodeStaticDTO node = new NodeStaticDTO();

        node.setId(Integer.toString(roleId));
        node.setText(roleText);

        List<NodeStaticDTO> children = new ArrayList<NodeStaticDTO>();
        for (int i = 0; i < allRoles.getNumRows() && children.size() < 3; i++) {
            RoleRefImpl roleImpl = (RoleRefImpl) allRoles.getValueAt(i, "Role");
            int id = (roleImpl.getRolePK().getRoleId());
            String narrative = roleImpl.getNarrative();
            if (!isMainAdminAppTree2(narrative)) {
                NodeStaticDTO child = new NodeStaticDTO();
                child.setId(Integer.toString(id));
                child.setText(narrative);
                for (RoleRefImpl x: selected) {
                    if (id == x.getRolePK().getRoleId()) {
                        child.setStateSelected(true);

                    }
                }
                children.add(child);
            }
        }
        node.setChildren(children);
        return node;

    }

    /**
     * List of available XML forms.
     */
    @SuppressWarnings("unchecked")
    public static List<FlatFormExtendedCoreDTO> mapUserAvailableXmlFormDTO(AllXmlFormsELO availableForm) {
        List<FlatFormExtendedCoreDTO> list = new ArrayList<FlatFormExtendedCoreDTO>();
        for (Iterator<AllXmlFormsELO> it = availableForm.iterator(); it.hasNext();) {
            AllXmlFormsELO row = it.next();
            FlatFormExtendedCoreDTO userXmlFormDTO = new FlatFormExtendedCoreDTO();
            XmlFormRef xmlFormRef = (XmlFormRef) row.getXmlFormEntityRef();

            userXmlFormDTO.setFlatFormVisId(xmlFormRef.getDisplayText());
            userXmlFormDTO.setFlatFormId(((XmlFormPK) xmlFormRef.getPrimaryKey()).getXmlFormId());
            userXmlFormDTO.setFlatFormDescription(row.getDescription());

            String format = "MMM dd, yyyy";
            userXmlFormDTO.setUpdateTime(mapTimestampToDateString(row.getUpdatedTime(), format));

            FinanceCubeRef ref = (FinanceCubeRef) row.getFinanceCube();
            String name = ref.getNarrative();
            FinanceCubeCoreDTO financeCubeCoreDTO = new FinanceCubeCoreDTO();
            financeCubeCoreDTO.setFinanceCubeVisId(name);
            userXmlFormDTO.setFinanceCube(financeCubeCoreDTO);
            list.add(userXmlFormDTO);

        }
        return list;
    }

    private static String mapTimestampToDateString(Timestamp timestamp, String dateFormat) {
        if (timestamp != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
            return simpleDateFormat.format(timestamp.getTime());
        }
        return null;
    }

}