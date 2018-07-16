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
package com.softproideas.app.admin.roles.service;

import java.util.HashSet;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPException;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.dto.role.AllRolesELO;
import com.cedar.cp.dto.role.RoleEditorSessionCSO;
import com.cedar.cp.dto.role.RoleEditorSessionSSO;
import com.cedar.cp.dto.role.RoleImpl;
import com.cedar.cp.dto.role.RolePK;
import com.cedar.cp.dto.role.RoleSecurityPK;
import com.cedar.cp.dto.role.RoleSecurityRefImpl;
import com.cedar.cp.ejb.api.role.RoleEditorSessionServer;
import com.cedar.cp.ejb.impl.role.RoleEditorSessionSEJB;
import com.softproideas.app.admin.roles.mapper.RolesMapper;
import com.softproideas.app.admin.roles.model.RoleDetailsDTO;
import com.softproideas.app.admin.roles.model.RoleSecuritySelectionImpl;
import com.softproideas.app.core.roles.model.RoleCoreDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;
import com.softproideas.commons.model.tree.NodeStaticDTO;

@Service("rolesService")
public class RolesServiceImpl implements RolesService {
    private static Logger logger = LoggerFactory.getLogger(RolesServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    RoleEditorSessionSEJB server = new RoleEditorSessionSEJB();
    /**
     * Returns list of available roles
     */
    @Override
    public List<RoleCoreDTO> browseRoles() throws ServiceException {
        AllRolesELO allRolesELO = cpContextHolder.getListSessionServer().getAllRoles();
        return RolesMapper.mapAllRolesELO(allRolesELO);
    }

    /**
     * Method save changing or create new data role details and TreeNode 
     */
    @Override
    public ResponseMessage save(int roleId, RoleDetailsDTO role) throws ServiceException {
        ResponseMessage error = validateVisIdAndDescription(role.getRoleVisId(), role.getRoleDescription());
        if(!error.isSuccess()) {
            return error;
        }
        boolean isAnySelected = false;
        RoleImpl roleImpl = getItemData(roleId);
        roleImpl.setVisId(role.getRoleVisId());
        roleImpl.setDescription(role.getRoleDescription());
        NodeStaticDTO nodeStaticRoot = role.getNode();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(nodeStaticRoot.getText());
        List<NodeStaticDTO> nodesStaticProcess = nodeStaticRoot.getChildren();
        for (NodeStaticDTO nodeStaticProcess: nodesStaticProcess) {
            String processName = nodeStaticProcess.getText();
            DefaultMutableTreeNode processNode = new DefaultMutableTreeNode(processName);
            List<NodeStaticDTO> nodesStaticLevelTwo = nodeStaticProcess.getChildren();
            for (NodeStaticDTO nodeStaticLevelTwo: nodesStaticLevelTwo) {
                boolean selected = nodeStaticLevelTwo.getState().isSelected();
                if(selected) {
                    isAnySelected = true;
                }
                String narrative = nodeStaticLevelTwo.getText();
                RoleSecurityPK key = new RoleSecurityPK(Integer.parseInt(nodeStaticLevelTwo.getId()));
                RoleSecurityRefImpl roleSecurity = new RoleSecurityRefImpl(key, narrative);
                RoleSecuritySelectionImpl selection = new RoleSecuritySelectionImpl(selected, roleSecurity);
                DefaultMutableTreeNode mutableTreeNodeProcessTwo = new DefaultMutableTreeNode(selection);
                processNode.add(mutableTreeNodeProcessTwo);
            }
            root.add(processNode);
        }
        roleImpl.setTreeNode(root);
        if(isAnySelected) {
            return save(roleImpl);
        } else {
            return new ResponseMessage(false, "Error! One or more security string must be selected!");
        }
    }
    
    ResponseMessage validateVisIdAndDescription(String visId, String description){
        boolean isError = false;
        ValidationError error =  new ValidationError("Error!");
        if(visId.length() == 0) {
            error.addFieldError("roleVisId", " Length VisId can't be 0!");
            isError = true;
        }
        if (visId.length() > 20) {
            error.addFieldError("roleVisId", " Length VisId must not exceed 20!");
            isError = true;
        }
        if(description.length() == 0) {
            error.addFieldError("roleDescription", " Length Description can't be 0!");
            isError = true;
        }
        if (description.length() > 60) {
            error.addFieldError("roleDescription", " Length Description must not exceed 60!");
            isError = true;
        }
        if(isError == true) {
            return error;
        } else {
            return new ResponseMessage(true);
        }
    }

    /**
     * Returns ResponseMessage , after insert or update role.
     */
    private ResponseMessage save(RoleImpl roleImpl) throws ServiceException {
        try {
//            RoleEditorSessionServer server = cpContextHolder.getRoleEditorSessionServer();
            if (roleImpl.getPrimaryKey() == null) {
                server.insert(new RoleEditorSessionCSO(cpContextHolder.getUserId(), roleImpl));
            } else {
                server.update(new RoleEditorSessionCSO(cpContextHolder.getUserId(), roleImpl));
            }
            ResponseMessage success = new ResponseMessage(true);
            return success;
        } catch (CPException e) {
            logger.error("Error during update role with key =" + roleImpl.getPrimaryKey() + "!");
            throw new ServiceException("Error during update role with key =" + roleImpl.getPrimaryKey() + "!");
        } catch (ValidationException e) {
            return new ResponseMessage(false, e.getMessage());
        }
    }

    /**
     * TODO: opis metody
     */
    @Override
    public RoleDetailsDTO fetchRoleDetails(int roleId) throws ServiceException {
        RoleImpl roleImpl = getItemData(roleId);
        RoleDetailsDTO roleDetailsDTO = RolesMapper.mapRoleDetails(roleImpl);
        return roleDetailsDTO;
    }

    /**
     * Get item data and edit, or create new data if roleId == -1 
     */
    private RoleImpl getItemData(int roleId) throws ServiceException {
        try {
//            RoleEditorSessionServer server = cpContextHolder.getRoleEditorSessionServer();

            RoleEditorSessionSSO roleEditorSessionSSO;
            if (roleId != -1) {
                RolePK rolePK = new RolePK(roleId);
                roleEditorSessionSSO = server.getItemData(cpContextHolder.getUserId(),rolePK);
            } else {
                roleEditorSessionSSO = server.getNewItemData(cpContextHolder.getUserId());
            }
            RoleImpl roleImpl = roleEditorSessionSSO.getEditorData();
            setTreeRoot(roleImpl);
            return roleImpl;
        } catch (CPException e) {
            logger.error("Error during get roles!", e);
            throw new ServiceException("Error during get roles!", e);
        } catch (ValidationException e) {
            logger.error("Validation error during get roles!", e);
            throw new ServiceException("Validation error during get roles!", e);
        }
    }

    /**
     * Method set treeRoot
     */
    private void setTreeRoot(RoleImpl roleImpl) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Security Strings");
        CPConnection conn = cpContextHolder.getCPConnection();
        EntityList available = conn.getListHelper().getAllSecurityRoles();
        Object selected = null;
        if (roleImpl.getPrimaryKey() == null) {
            selected = conn.getListHelper().getAllSecurityRoles();
        } else {
            selected = conn.getListHelper().getAllSecurityRolesForRole(((RolePK) roleImpl.getPrimaryKey()).getRoleId());
        }
        int size = ((EntityList) selected).getNumRows();
        HashSet<Object> selectedList = new HashSet<Object>(size);
        for (int ref = 0; ref < size; ++ref) {
            selectedList.add(((EntityList) selected).getValueAt(ref, "RoleSecurity"));
        }
        size = available.getNumRows();
        EntityRef var15 = null;
        String[] securityAction = null;
        String oldProcess = null;
        DefaultMutableTreeNode group = null;
        RoleSecuritySelectionImpl actionObject = null;
        DefaultMutableTreeNode action = null;
        for (int i = 0; i < size; ++i) {
            var15 = (EntityRef) available.getValueAt(i, "RoleSecurity");
            securityAction = var15.getNarrative().split("\\.");
            String process = securityAction[0];
            if (oldProcess == null || !oldProcess.equals(process)) {
                group = new DefaultMutableTreeNode(process);
                root.add(group);
            }
            actionObject = new RoleSecuritySelectionImpl();
            actionObject.setRoleSecurity(var15);
            if (selectedList.contains(var15)) {
                actionObject.setSelected(true);
            } else {
                actionObject.setSelected(false);
            }
            action = new DefaultMutableTreeNode(actionObject);
            group.add(action);
            oldProcess = process;
        }
        roleImpl.setTreeNode(root);
    }

    /**
     * Returns ResponseMessage after delete role.
     */
    @Override
    public ResponseMessage delete(int roleId) throws ServiceException {
        RolePK roleIds = new RolePK(roleId);
        try {
//            cpContextHolder.getRoleEditorSessionServer().delete(roleIds);
        	server.delete(cpContextHolder.getUserId(), roleIds);
            
            ResponseMessage responseMessage = new ResponseMessage(true);
            return responseMessage;
        } catch (ValidationException e) {
            logger.error("Validation error during dalete role  =" + e + "!");
            ValidationError error = new ValidationError(e.getMessage());
            return error;
        } catch (CPException e) {
            logger.error("Error during delete role  =" + e + "!");
            throw new ServiceException("Error during dalete role =" + e + "!");
        }
    }

}
