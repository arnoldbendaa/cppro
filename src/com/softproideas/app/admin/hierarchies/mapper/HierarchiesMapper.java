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
package com.softproideas.app.admin.hierarchies.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.base.CPConnection.ConnectionContext;
import com.cedar.cp.api.dimension.DimensionElement;
import com.cedar.cp.api.dimension.HierarchyEvent;
import com.cedar.cp.api.dimension.HierarchyNode;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.AllHierarchysELO;
import com.cedar.cp.dto.dimension.AugHierarchyElementCK;
import com.cedar.cp.dto.dimension.AugHierarchyElementImpl;
import com.cedar.cp.dto.dimension.AugHierarchyElementPK;
import com.cedar.cp.dto.dimension.DimensionElementCK;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyElementCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedImpl;
import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;
import com.cedar.cp.dto.dimension.HierarchyImpl;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.dto.dimension.event.NewDimensionElementListEvent;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.softproideas.app.admin.hierarchies.model.HierarchyDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyDetailsDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyDimensionElementTableDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyNodeLazyDTO;
import com.softproideas.app.core.dimension.mapper.DimensionCoreMapper;
import com.softproideas.app.core.dimension.model.DimensionCoreDTO;
import com.softproideas.app.core.model.mapper.ModelCoreMapper;
import com.softproideas.app.core.model.model.ModelCoreDTO;
import com.softproideas.commons.util.DimensionUtil;

/**
 * <p>Share methods using with mapping at hierarchies module</p>
 *
 * @author Szymon Zberaz, Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class HierarchiesMapper {

    /**
     * Map all hierarchies ELO
     */
    public static List<HierarchyDTO> mapAllHierarchies(AllHierarchysELO allHierarchysELO, int hierarchyType) {
        List<HierarchyDTO> hierarchyAccountDTOList = new ArrayList<HierarchyDTO>();
        for (@SuppressWarnings("unchecked")
        Iterator<AllHierarchysELO> it = allHierarchysELO.iterator(); it.hasNext();) {
            AllHierarchysELO row = it.next();
            Integer type = row.getType();
            if (type == hierarchyType) {
                HierarchyDTO hierarchyDTO = new HierarchyDTO();
                HierarchyRefImpl hierarchyRefImpl = (HierarchyRefImpl) row.getHierarchyEntityRef();
                hierarchyDTO.setHierarchyId(hierarchyRefImpl.getHierarchyPK().getHierarchyId());
                hierarchyDTO.setHierarchyVisId(hierarchyRefImpl.getNarrative());
                hierarchyDTO.setHierarchyDescription(row.getDescription());

                ModelRefImpl modelRefImpl = (ModelRefImpl) row.getModelEntityRef();
                ModelCoreDTO model = ModelCoreMapper.mapModelRefToModelCoreDTO(modelRefImpl);
                hierarchyDTO.setModel(model);

                if(type == 1) {
                    hierarchyDTO.setType(true);
                } else {
                    hierarchyDTO.setType(false);
                }

                DimensionRefImpl dimensionRefImpl = (DimensionRefImpl) row.getDimensionEntityRef();
                DimensionCoreDTO dimension = DimensionCoreMapper.mapDimensionRef(dimensionRefImpl);
                hierarchyDTO.setDimension(dimension);

                hierarchyDTO.setHierarchyDescription(row.getDescription());

                hierarchyAccountDTOList.add(hierarchyDTO);
            }
        }
        return hierarchyAccountDTOList;
    }

    /**
     * Map hierarchies details 
     * @param type 
     */
    public static HierarchyDetailsDTO mapHierarchiesDetails(HierarchyImpl hierarchy, CPConnection cpConnection, List<HierarchyEvent> events, boolean type) {

        HierarchyDetailsDTO hierarchyDetailsDTO = new HierarchyDetailsDTO();
        
        DimensionRefImpl dimensionRefImpl = (DimensionRefImpl) hierarchy.getDimensionRef();
        DimensionCoreDTO dimension = DimensionCoreMapper.mapDimensionRef(dimensionRefImpl);
        hierarchyDetailsDTO.setDimension(dimension);

        hierarchyDetailsDTO.setHierarchyId(hierarchy.getHierarchyId());
        hierarchyDetailsDTO.setHierarchyVisId(hierarchy.getVisId());
        hierarchyDetailsDTO.setHierarchyDescription(hierarchy.getDescription());
        // int modelId = 0;
        if (hierarchy.getModel() != null) {
            ModelRef modelRef = (ModelRef) hierarchy.getModel();
            // modelId = ((ModelPK) modelRef.getPrimaryKey()).getModelId();
            ModelCoreDTO model = ModelCoreMapper.mapModelRefToModelCoreDTO(modelRef);
            hierarchyDetailsDTO.setModel(model);
        } else {
            hierarchyDetailsDTO.setModel(new ModelCoreDTO());
        }

        // Source System
        Integer externalSystemRef = hierarchy.getExternalSystemRef();
        hierarchyDetailsDTO.setExternalSystemRefName(DimensionUtil.getExternalSystemRefName(externalSystemRef));
        hierarchyDetailsDTO.setType(type);
        // inUseLabel
        if (hierarchy.getModel() != null && hierarchy.isChangeManagementRequestsPending()) {
            hierarchyDetailsDTO.setReadOnly(true);
            hierarchyDetailsDTO.setInUseLabel("This hierarchy is in use. Change requests are pending thus updates are restricted.");
        } else if (hierarchy.getExternalSystemRef() != null && hierarchy.getModel() != null) {
            hierarchyDetailsDTO.setReadOnly(false);
            hierarchyDetailsDTO.setInUseLabel("This hierarchy is in use and sourced from an external system. \n Updates are thus restricted to the addition of new summary elements.");

        } else if (hierarchy.getModel() != null) {
            hierarchyDetailsDTO.setReadOnly(false);
            hierarchyDetailsDTO.setInUseLabel("This hierarchy is in use. Updates will be applied via a change management request.");
        } else {
            hierarchyDetailsDTO.setReadOnly(false);
            hierarchyDetailsDTO.setInUseLabel(null);
        }
        // elements for move to node;
        hierarchyDetailsDTO.setDimensionElement(mapEvents(events));
        // augentMode
        hierarchyDetailsDTO.setAugentMode(isAugmentMode(cpConnection.getConnectionContext(), hierarchy));

        hierarchyDetailsDTO.setVersionNum(hierarchy.getVersionNum());

        return hierarchyDetailsDTO;
    }

    /**
     * Map dimension element for table
     */
    private static List<HierarchyDimensionElementTableDTO> mapEvents(List<HierarchyEvent> events) {
        List<HierarchyDimensionElementTableDTO> dimensionElement = new ArrayList<HierarchyDimensionElementTableDTO>();
        for (HierarchyEvent hierarchyEvent: events) {
            NewDimensionElementListEvent newDimensionElement = (NewDimensionElementListEvent) hierarchyEvent;
            List<DimensionElement> listElements = newDimensionElement.getDimensionElements();
            for (DimensionElement list: listElements) {
                HierarchyDimensionElementTableDTO dim = new HierarchyDimensionElementTableDTO();
                dim.setAugCreditDebit(list.getAugCreditDebit());
                dim.setDescription(list.getDescription());
                dim.setDimensionElementVisId(list.getVisId());
                dim.setCreditDebit(list.getCreditDebit());
                DimensionElementCK dimensionElementck = (DimensionElementCK) list.getKey();
                dim.setDimensionElementId(dimensionElementck.getDimensionElementPK().getDimensionElementId());
                dim.setDimensionId(dimensionElementck.getDimensionPK().getDimensionId());
                dimensionElement.add(dim);
            }
        }
        return dimensionElement;
    }

    /**
     * Get settings for augentMode
     */
    private static boolean isAugmentMode(ConnectionContext connectionContext, HierarchyImpl hierarchyImpl) {
        return connectionContext == ConnectionContext.INTERACTIVE_WEB && hierarchyImpl.getExternalSystemRef() != null;
    }

    /**
     * Set Node Root for hierarchies details
     */
    public static List<HierarchyNodeLazyDTO> mapHierarchiesForModelELO(HierarchyImpl hierarchy) {
        List<HierarchyNodeLazyDTO> root = mapTreeRoot(hierarchy.getRoot(), hierarchy.getRoot().getVisId(), hierarchy.getRoot().getAugCreditDebit(), hierarchy.getRoot().getCreditDebit());

        return root;

    }

    /**
     * Map tree root
     */
    private static List<HierarchyNodeLazyDTO> mapTreeRoot(HierarchyNode node, String name, int augent2, int creditDebit2) {
        List<HierarchyNodeLazyDTO> hierarchyNodeStaticDTO = new ArrayList<HierarchyNodeLazyDTO>();
        HierarchyNodeLazyDTO nodeModel = new HierarchyNodeLazyDTO();

        nodeModel.setText(name);
        nodeModel.setAugCreditDebit(augent2);
        nodeModel.setCreditDebit(creditDebit2);
        nodeModel.setAugentElement(node.isAugmentElement());
        nodeModel.setTextNode(name);
        nodeModel.setDescription(node.getDescription());
        // nodeModel.setStateDisabled(true);
        nodeModel.setIndex(node.getChildCount());
        if (node.getPrimaryKey() instanceof HierarchyElementCK) {
            HierarchyElementImpl element = (HierarchyElementImpl) node;
            HierarchyElementCK pk = (HierarchyElementCK) element.getPrimaryKey();
            HierarchyCK hierarchyCK = (HierarchyCK) node.getPrimaryKey();
            nodeModel.setStructureId(hierarchyCK.getHierarchyPK().getHierarchyId());
            nodeModel.setId("hierarchyElement/" + Integer.toString(pk.getHierarchyElementPK().getHierarchyElementId()));

        } else if (node.getPrimaryKey() instanceof HierarchyElementFeedCK) {
            HierarchyElementFeedImpl element = (HierarchyElementFeedImpl) node;
            HierarchyElementFeedCK pk = (HierarchyElementFeedCK) element.getPrimaryKey();
            nodeModel.setStructureId(pk.getHierarchyPK().getHierarchyId());
            nodeModel.setId("hierarchyElement/" + Integer.toString(pk.getHierarchyElementFeedPK().getHierarchyElementId()));
        } else if (node.getPrimaryKey() instanceof HierarchyElementFeedPK) {
            HierarchyElementFeedPK pk = (HierarchyElementFeedPK) node.getPrimaryKey();
            HierarchyCK hierarchyCK = (HierarchyCK) node.getPrimaryKey();
            nodeModel.setStructureId(hierarchyCK.getHierarchyPK().getHierarchyId());
            nodeModel.setId("hierarchyElement/" + Integer.toString(pk.getHierarchyElementId()));
        } else {
            AugHierarchyElementImpl element = (AugHierarchyElementImpl) node;
            AugHierarchyElementCK pk = (AugHierarchyElementCK) element.getPrimaryKey();
            HierarchyCK hierarchyCK = (HierarchyCK) node.getPrimaryKey();
            nodeModel.setStructureId(hierarchyCK.getHierarchyPK().getHierarchyId());
            nodeModel.setId(Integer.toString(pk.getAugHierarchyElementPK().getHierarchyElementId()));
        }
        nodeModel.setChildren(true);
        hierarchyNodeStaticDTO.add(nodeModel);

        return hierarchyNodeStaticDTO;

    }

    /**
     * Map hierarchies tree root
     */
    public static List<HierarchyNodeLazyDTO> mapImmediateChildrenELO(HierarchyNode impl, int hierarchyElementId) {
        List<HierarchyNodeLazyDTO> hierarchyNodeStaticDTO = new ArrayList<HierarchyNodeLazyDTO>();
        HierarchyNode findedNode = findElementInNode(impl, hierarchyElementId); // return findedElement
        for (int i = 0; i < findedNode.getChildCount(); i++) {

            HierarchyNodeLazyDTO nodeModel = new HierarchyNodeLazyDTO();
            if (findedNode.getChildCount() == 0) {
                nodeModel.setChildren(false);
            }
            if (findedNode.getChildAt(i) instanceof HierarchyElementImpl) {
                HierarchyElementImpl children = (HierarchyElementImpl) findedNode.getChildAt(i);
                if (children.getVisId().equals("_notMapped")) {
                    continue;
                }
                String description = children.getDescription();
                nodeModel.setText(children.getVisId() + " - " + description);
                nodeModel.setAugCreditDebit(children.getAugCreditDebit());
                nodeModel.setCreditDebit(children.getCreditDebit());
                nodeModel.setAugentElement(children.isAugmentElement());
                nodeModel.setCanMove(children.isAugmentElement());
                HierarchyElementCK pk = (HierarchyElementCK) children.getPrimaryKey();
                nodeModel.setId("hierarchyElement/" + Integer.toString(pk.getHierarchyElementPK().getHierarchyElementId()));
                nodeModel.setChildren(true);
                nodeModel.setStructureId(pk.getHierarchyPK().getHierarchyId());
                nodeModel.setStateLeaf(false);
                nodeModel.setDescription(description);// for label in front
                nodeModel.setTextNode(children.getVisId());
                nodeModel.setFeedImpl(false);
                nodeModel.setIndex(i);
                hierarchyNodeStaticDTO.add(nodeModel);
            } else if (findedNode.getChildAt(i) instanceof HierarchyElementFeedImpl) {
                HierarchyElementFeedImpl children = (HierarchyElementFeedImpl) findedNode.getChildAt(i);
                if (children.getVisId().equals("_notMapped")) {
                    continue;
                }
                String description = children.getDescription();
                nodeModel.setText(children.getVisId() + " - " + description);
                nodeModel.setAugCreditDebit(children.getAugCreditDebit());
                nodeModel.setCreditDebit(children.getCreditDebit());
                nodeModel.setAugentElement(children.isAugmentElement());
                HierarchyElementFeedPK pk = (HierarchyElementFeedPK) children.getPrimaryKey();
                nodeModel.setId("dimensionElement/" + Integer.toString(pk.getDimensionElementId()));
                nodeModel.setChildren(false);
                nodeModel.setStateLeaf(true);
                nodeModel.setDescription(description);
                nodeModel.setTextNode(children.getVisId());// for label in front
                nodeModel.setFeedImpl(true);
                nodeModel.setDisabled(children.isDisabled());
                nodeModel.setIndex(i);
                HierarchyCK hierarchyCK = (HierarchyCK) impl.getPrimaryKey();
                nodeModel.setStructureId(hierarchyCK.getHierarchyPK().getHierarchyId());
                // nodeModel.setIndex(impl.getIndex(findedNode.getChildAt(i)));
                nodeModel.setCanMove(children.isAugmentElement());
                hierarchyNodeStaticDTO.add(nodeModel);
            } else {
                AugHierarchyElementImpl children = (AugHierarchyElementImpl) findedNode.getChildAt(i);
                if(children.getVisId().equals("_notMapped")) {
                    break;
                }
                String description = children.getDescription();
                nodeModel.setText(children.getVisId() + " - " + description);
                nodeModel.setAugCreditDebit(children.getAugCreditDebit());
                nodeModel.setCreditDebit(children.getCreditDebit());
                nodeModel.setAugentElement(children.isAugmentElement());
                AugHierarchyElementPK pk = (AugHierarchyElementPK) children.getPrimaryKey();
                nodeModel.setId("hierarchyElement/" + Integer.toString(pk.getHierarchyElementId()));
                nodeModel.setChildren(true);
                nodeModel.setStateLeaf(false);
                nodeModel.setFeedImpl(false);
                nodeModel.setDescription(description);
                nodeModel.setTextNode(children.getVisId());// for label in front
                nodeModel.setIndex(i);
                HierarchyCK hierarchyCK = (HierarchyCK) impl.getPrimaryKey();
                nodeModel.setStructureId(hierarchyCK.getHierarchyPK().getHierarchyId());
                // nodeModel.setIndex(impl.getIndex(findedNode.getChildAt(i)));
                nodeModel.setCanMove(children.isAugmentElement());
                hierarchyNodeStaticDTO.add(nodeModel);
            }
        }
        return hierarchyNodeStaticDTO;
    }

    /**
     * This method returns element node , because we need child in first level of root
     */
    private static HierarchyNode findElementInNode(HierarchyNode node, int hierarchyElementId) {
        HierarchyNode result = null;
        int nodeHierarchyId;
        if (node instanceof HierarchyElementImpl) {
            HierarchyElementCK pk = (HierarchyElementCK) node.getPrimaryKey();
            nodeHierarchyId = pk.getHierarchyElementPK().getHierarchyElementId();
        } else if (node instanceof HierarchyElementFeedImpl) {
            HierarchyElementFeedPK pk = (HierarchyElementFeedPK) node.getPrimaryKey();
            nodeHierarchyId = pk.getHierarchyElementId();
        } else {
            AugHierarchyElementPK pk = (AugHierarchyElementPK) node.getPrimaryKey();
            nodeHierarchyId = pk.getHierarchyElementId();
        }
        if (nodeHierarchyId == hierarchyElementId) {
            result = node;
        } else {
            for (int i = 0; i < node.getChildCount() && result == null; i++) {
                result = findElementInNode((HierarchyNode) node.getChildAt(i), hierarchyElementId);
            }
        }
        return result;
    }

    /**
     * Map hierarchies details to hierarchyImpl , and create new element
     */
    public static HierarchyImpl mapHierarchyDetailsDTOToHierarchyDetailsImpl(HierarchyImpl hierarchyImpl, HierarchyDetailsDTO hierarchy) {
        hierarchyImpl.setDescription(hierarchy.getHierarchyDescription());
        hierarchyImpl.setDimensionId(hierarchy.getDimension().getDimensionId());
        hierarchyImpl.setHierarchyId(hierarchy.getHierarchyId());
        hierarchyImpl.setVisId(hierarchy.getHierarchyVisId());
        hierarchyImpl.setSubmitChangeManagementRequest(hierarchy.isSubmitChangeManagementRequest());
        hierarchyImpl.setDescription(hierarchy.getHierarchyDescription());

        int modelId = hierarchy.getModel().getModelId();
        if (modelId > 0) {
            ModelPK modelPK = new ModelPK(modelId);
            ModelRefImpl modelRefImpl = new ModelRefImpl(modelPK, null);
            hierarchyImpl.setModel(modelRefImpl);
        }

        String createNewElement = "createNew";
        if (hierarchy.getOperation() != null && hierarchy.getOperation().equals(createNewElement)) {
            // hierarchyImpl.setRoot(root(account.getNodeToEdit(), hierarchyImpl.getRoot(), account));
            DimensionPK pk = new DimensionPK(hierarchy.getDimension().getDimensionId());
            DimensionRefImpl dimensionRefImpl = new DimensionRefImpl(pk, hierarchy.getDimension().getDimensionVisId(), 1);
            hierarchyImpl.setDimensionRef(dimensionRefImpl);
            hierarchyImpl.getTreeModel();
        } else {
            hierarchyImpl.setVersionNum(hierarchy.getVersionNum());
        }
        return hierarchyImpl;
    }

    /**
     * Map available dimension for insert
     */
    public static List<DimensionCoreDTO> mapAvailableDimensionForInsert(EntityList availableDimension) {
        List<DimensionCoreDTO> dimension = new ArrayList<DimensionCoreDTO>();
        int size = availableDimension.getNumRows();
        for (int i = 0; i < size; i++) {
            DimensionCoreDTO dim = new DimensionCoreDTO();
            DimensionRefImpl dimensionRefImpl = (DimensionRefImpl) availableDimension.getValueAt(i, "Dimension");
            dim.setDimensionVisId(dimensionRefImpl.getNarrative());
            dim.setDimensionId(dimensionRefImpl.getDimensionPK().getDimensionId());
            dimension.add(dim);

        }
        return dimension;
    }

}
