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
/**
 * 
 */
package com.softproideas.app.core.businesschooser.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.budgetlocation.UserModelElementAssignment;
import com.cedar.cp.dto.budgetlocation.BudgetLocationImpl;
import com.cedar.cp.dto.dimension.ImmediateChildrenELO;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.user.UserPK;
import com.cedar.cp.ejb.api.base.ListSessionServer;
import com.softproideas.commons.model.tree.NodeStaticWithIdAndDescriptionDTO;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class BusinessChooserMapper {

    /**
     * Map treeNode .
     */
    @SuppressWarnings("unchecked")
    public static NodeStaticWithIdAndDescriptionDTO mapBudgetLocationTree(BudgetLocationImpl budgetLocationImpl, ListSessionServer listSesionServer, int user) {
        boolean accessLevelZero = false, accessElementLevelOne = false, accessElementLevelTwo = false;

        List<UserModelElementAssignment> elo = limitList(budgetLocationImpl.getModelUserElementAccess(), user);

        StructureElementRefImpl structureElementRefImpl = (StructureElementRefImpl) budgetLocationImpl.getRootElementEntityRef();
        String root1Name = budgetLocationImpl.getDimensionRef().getDisplayText();
        NodeStaticWithIdAndDescriptionDTO nodeStaticRoot = new NodeStaticWithIdAndDescriptionDTO();
        nodeStaticRoot.setText(root1Name);
        nodeStaticRoot.setId(root1Name);
        nodeStaticRoot.setStateOpened(true);
        nodeStaticRoot.setStateDisabled(true);
        nodeStaticRoot.setStructureId(1);
        nodeStaticRoot.setStructureElementId(2);
        nodeStaticRoot.setDescription(budgetLocationImpl.getDimensionRef().getDisplayText());

        String root2Name = structureElementRefImpl.getNarrative();
        NodeStaticWithIdAndDescriptionDTO nodeStaticInnerRoot = new NodeStaticWithIdAndDescriptionDTO();
        nodeStaticInnerRoot.setText(root2Name);
        nodeStaticInnerRoot.setId(root2Name);
        nodeStaticInnerRoot.setStateOpened(false);
        nodeStaticInnerRoot.setStateDisabled(true);
        nodeStaticInnerRoot.setStructureId(structureElementRefImpl.getStructureElementPK().getId());
        nodeStaticInnerRoot.setStructureElementId(structureElementRefImpl.getStructureElementPK().getStructureElementId());
        nodeStaticInnerRoot.setDescription(structureElementRefImpl.getDisplayText());
        nodeStaticInnerRoot.setStructureVisId(root2Name);
        for (UserModelElementAssignment elementAccessList: elo) {
            if (nodeStaticInnerRoot.getStructureElementId() == ((StructureElementPK) elementAccessList.getStructureElementPK()).getStructureElementId()) {
                accessLevelZero = true;
                nodeStaticInnerRoot.setStateDisabled(false);
            }
        }

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
            nodeStaticProcess.setStructureVisId(element.getVisId());
            if (accessLevelZero == true) {
                childrenLevelOne.add(nodeStaticProcess);
            } else {
                for (UserModelElementAssignment elementAccessList: elo) {
                    if (nodeStaticProcess.getStructureElementId() == ((StructureElementPK) elementAccessList.getStructureElementPK()).getStructureElementId()) {
                        accessElementLevelOne = true;
                        childrenLevelOne.add(nodeStaticProcess);
                    }
                }
            }
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
                nodeStaticProcessTwo.setStructureVisId(innerElement.getVisId());
                if (accessLevelZero == true || accessElementLevelOne == true) {
                    childrenLevelTwo.add(nodeStaticProcessTwo);
                } else {
                    for (UserModelElementAssignment elementAccessList: elo) {
                        if (nodeStaticProcessTwo.getStructureElementId() == ((StructureElementPK) elementAccessList.getStructureElementPK()).getStructureElementId()) {
                            accessElementLevelTwo = true;
                            childrenLevelTwo.add(nodeStaticProcessTwo);
                        }
                    }
                }
            }
            nodeStaticProcess.setChildren(childrenLevelTwo);
            if (accessElementLevelOne == false && accessElementLevelTwo == true) {
                nodeStaticProcess.setStateDisabled(true);
                childrenLevelOne.add(nodeStaticProcess);
                accessElementLevelTwo = false;
            } else {
                accessElementLevelOne = false;
            }
        }
        nodeStaticInnerRoot.setChildren(childrenLevelOne);

        List<NodeStaticWithIdAndDescriptionDTO> childrenLevelZero = new ArrayList<NodeStaticWithIdAndDescriptionDTO>();
        childrenLevelZero.add(nodeStaticInnerRoot);
        nodeStaticRoot.setChildren(childrenLevelZero);

        return nodeStaticRoot;
    }

    private static List<UserModelElementAssignment> limitList(List<UserModelElementAssignment> list, int user) {
        for (Iterator<UserModelElementAssignment> iter = list.listIterator(); iter.hasNext();) {
            UserModelElementAssignment listElement = iter.next();
            if (user != ((UserPK) listElement.getUser().getPrimaryKey()).getUserId()) {
                iter.remove();
            }
        }

        return list;
    }

    public static List<String> generateSecurityList(BudgetLocationImpl budgetLocationImpl, ListSessionServer listSesionServer, int user) {
        boolean accessLevelZero = false, accessElementLevelOne = false;
        List<String> securityList = new ArrayList<String>();

        List<UserModelElementAssignment> elo = limitList(budgetLocationImpl.getModelUserElementAccess(), user);

        StructureElementRefImpl structureElementRefImpl = (StructureElementRefImpl) budgetLocationImpl.getRootElementEntityRef();

        String root2Name = structureElementRefImpl.getNarrative();
        NodeStaticWithIdAndDescriptionDTO nodeStaticInnerRoot = new NodeStaticWithIdAndDescriptionDTO();
        nodeStaticInnerRoot.setStructureElementId(structureElementRefImpl.getStructureElementPK().getStructureElementId());
        for (UserModelElementAssignment elementAccessList: elo) {
            if (nodeStaticInnerRoot.getStructureElementId() == ((StructureElementPK) elementAccessList.getStructureElementPK()).getStructureElementId()) {
                accessLevelZero = true;
                securityList.add(root2Name);
            }
        }

        ImmediateChildrenELO list = (ImmediateChildrenELO) budgetLocationImpl.getRootChildren();
        for (Iterator<ImmediateChildrenELO> it = list.iterator(); it.hasNext();) {
            ImmediateChildrenELO element = it.next();
            NodeStaticWithIdAndDescriptionDTO nodeStaticProcess = new NodeStaticWithIdAndDescriptionDTO();
            nodeStaticProcess.setStructureElementId(element.getStructureElementId());
            if (accessLevelZero == true) {
                securityList.add(element.getVisId());
            } else {
                for (UserModelElementAssignment elementAccessList: elo) {
                    if (nodeStaticProcess.getStructureElementId() == ((StructureElementPK) elementAccessList.getStructureElementPK()).getStructureElementId()) {
                        accessElementLevelOne = true;
                        securityList.add(element.getVisId());
                    }
                }
            }
            ImmediateChildrenELO innerList = (ImmediateChildrenELO) listSesionServer.getImmediateChildren(element.getStructureId(), element.getStructureElementId());
            for (Iterator<ImmediateChildrenELO> innerIt = innerList.iterator(); innerIt.hasNext();) {
                ImmediateChildrenELO innerElement = innerIt.next();
                NodeStaticWithIdAndDescriptionDTO nodeStaticProcessTwo = new NodeStaticWithIdAndDescriptionDTO();
                nodeStaticProcessTwo.setStructureElementId(innerElement.getStructureElementId());
                if (accessLevelZero == true || accessElementLevelOne == true) {
                    securityList.add(innerElement.getVisId());
                } else {
                    for (UserModelElementAssignment elementAccessList: elo) {
                        if (nodeStaticProcessTwo.getStructureElementId() == ((StructureElementPK) elementAccessList.getStructureElementPK()).getStructureElementId()) {
                            securityList.add(innerElement.getVisId());
                        }
                    }
                }
            }
            accessElementLevelOne = false;
        }

        return securityList;
    }

}
