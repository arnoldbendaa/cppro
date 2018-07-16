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
package com.softproideas.app.admin.roles.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.role.Role;
import com.cedar.cp.dto.role.AllRolesELO;
import com.cedar.cp.dto.role.RolePK;
import com.cedar.cp.dto.role.RoleRefImpl;
import com.cedar.cp.dto.role.RoleSecurityPK;
import com.softproideas.app.admin.roles.model.RoleDetailsDTO;
import com.softproideas.app.admin.roles.model.RoleSecuritySelectionImpl;
import com.softproideas.app.core.roles.model.RoleCoreDTO;
import com.softproideas.commons.model.tree.NodeStaticDTO;

public class RolesMapper {

    /**
     * Map all roles
     */
    @SuppressWarnings("unchecked")
    public static List<RoleCoreDTO> mapAllRolesELO(AllRolesELO elo) {
        List<RoleCoreDTO> rolesList = new ArrayList<RoleCoreDTO>();
        for (Iterator<AllRolesELO> it = elo.iterator(); it.hasNext();) {
            AllRolesELO row = it.next();
            RoleCoreDTO roleDTO = new RoleCoreDTO();
            RoleRefImpl role = (RoleRefImpl) row.getRoleEntityRef();
            roleDTO.setRoleId(role.getRolePK().getRoleId());
            roleDTO.setRoleVisId(row.getVisId());
            roleDTO.setRoleDescription(row.getDescription());
            rolesList.add(roleDTO);
        }
        return rolesList;
    }

    /**
     * Map Role to RoleDetailsDTO
     */
    public static RoleDetailsDTO mapRoleDetails(Role role) {
        RoleDetailsDTO rolesDetailsDTO = new RoleDetailsDTO();

        NodeStaticDTO nodeStaticRoot = null;
        if (role.getPrimaryKey() != null) {
            rolesDetailsDTO.setRoleId(((RolePK) (role.getPrimaryKey())).getRoleId());
            nodeStaticRoot = mapTreeNode(false, role.getTreeRoot());
        } else {
            rolesDetailsDTO.setRoleId(-1);
            nodeStaticRoot = mapTreeNode(true, role.getTreeRoot());
        }
        rolesDetailsDTO.setNode(nodeStaticRoot);
        rolesDetailsDTO.setRoleVisId(role.getVisId());
        rolesDetailsDTO.setRoleDescription(role.getDescription());

        return rolesDetailsDTO;
    }

    /**
     * Map treeNode .
     */
    private static NodeStaticDTO mapTreeNode(boolean ifNew, TreeNode treenode) {
        DefaultMutableTreeNode mutableTreeNodeRoot = (DefaultMutableTreeNode) treenode;
        String rootName = (String) mutableTreeNodeRoot.getUserObject();
        NodeStaticDTO nodeStaticRoot = new NodeStaticDTO();
        nodeStaticRoot.setText(rootName);
        nodeStaticRoot.setStateOpened(true);
        nodeStaticRoot.setStateDisabled(true);
        List<NodeStaticDTO> childrenLevelOne = new ArrayList<NodeStaticDTO>();
        int processCount = mutableTreeNodeRoot.getChildCount();
        for (int i = 0; i < processCount; i++) {
            DefaultMutableTreeNode mutableTreeNodeProcess = (DefaultMutableTreeNode) mutableTreeNodeRoot.getChildAt(i);
            String roleSecurityName = (String) mutableTreeNodeProcess.getUserObject();
            NodeStaticDTO nodeStaticProcess = new NodeStaticDTO();
            nodeStaticProcess.setText(roleSecurityName);
            nodeStaticProcess.setId(roleSecurityName);
            nodeStaticProcess.setStateOpened(false);
            childrenLevelOne.add(nodeStaticProcess);
            int roleCount = mutableTreeNodeProcess.getChildCount();
            List<NodeStaticDTO> childrenLevelTwo = new ArrayList<NodeStaticDTO>();
            for (int j = 0; j < roleCount; j++) {
                DefaultMutableTreeNode mutableTreeNodeProcessTwo = (DefaultMutableTreeNode) mutableTreeNodeProcess.getChildAt(j);
                RoleSecuritySelectionImpl roleSecurity = (RoleSecuritySelectionImpl) mutableTreeNodeProcessTwo.getUserObject();
                String replecedName = (String) mutableTreeNodeProcess.getUserObject() + ".";
                NodeStaticDTO nodeStaticProcessTwo = new NodeStaticDTO();
                if (!ifNew) {
                    boolean selected = roleSecurity.isSelected();
                    nodeStaticProcessTwo.setStateSelected(selected);
                }
                nodeStaticProcessTwo.setStateOpened(false);
                EntityRef entityRef = roleSecurity.getRoleSecurity();
                int processId = ((RoleSecurityPK) entityRef.getPrimaryKey()).getRoleSecurityId();
                String processName = entityRef.getNarrative();
                nodeStaticProcessTwo.setId(processId + "");
                nodeStaticProcessTwo.setText(processName.replace(replecedName, ""));
                childrenLevelTwo.add(nodeStaticProcessTwo);
            }
            nodeStaticProcess.setChildren(childrenLevelTwo);
        }
        nodeStaticRoot.setChildren(childrenLevelOne);
        return nodeStaticRoot;

    }
}
