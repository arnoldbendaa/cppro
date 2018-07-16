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
package com.softproideas.app.admin.dimensions.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.base.CPConnection;
import com.cedar.cp.api.base.CPConnection.ConnectionContext;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.AllDimensionsELO;
import com.cedar.cp.dto.dimension.AvailableDimensionsELO;
import com.cedar.cp.dto.dimension.DimensionElementCK;
import com.cedar.cp.dto.dimension.DimensionElementImpl;
import com.cedar.cp.dto.dimension.DimensionImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.softproideas.app.admin.dimensions.model.DimensionDTO;
import com.softproideas.app.admin.dimensions.model.DimensionDetailsDTO;
import com.softproideas.app.admin.dimensions.model.DimensionElementDTO;
import com.softproideas.app.core.model.mapper.ModelCoreMapper;
import com.softproideas.app.core.model.model.ModelCoreDTO;
import com.softproideas.commons.util.DimensionUtil;

public class DimensionMapper {

    public static List<DimensionDTO> mapAllDimensionsELO(AllDimensionsELO list, AvailableDimensionsELO availableDimensions, int dimensionType) {
        List<DimensionDTO> dimensionsDTOList = new ArrayList<DimensionDTO>();

        // dimensions assigned to model
        for (@SuppressWarnings("unchecked")
        Iterator<AllDimensionsELO> it = list.iterator(); it.hasNext();) {
            AllDimensionsELO row = it.next();
            Integer type = row.getType();
            // Type: 1 = Account (exp), 2 = Business (cc)
            // Type 3 = Calendar (cal) is supported in other mapper
            if (type == dimensionType) {
                DimensionDTO dimensionDTO = new DimensionDTO();

                DimensionRefImpl dimensionRefImpl = (DimensionRefImpl) row.getDimensionEntityRef();
                dimensionDTO.setDimensionId(dimensionRefImpl.getDimensionPK().getDimensionId());
                dimensionDTO.setDimensionVisId(dimensionRefImpl.getNarrative());

                ModelRefImpl modelRefImpl = (ModelRefImpl) row.getModelEntityRef();
                ModelCoreDTO model = ModelCoreMapper.mapModelRefToModelCoreDTO(modelRefImpl);
                dimensionDTO.setModel(model);

                dimensionDTO.setType(type);
                dimensionDTO.setDimensionDescription(row.getDescription());
                dimensionDTO.setSequence(row.getDimensionSeqNum());
                dimensionDTO.setHierarchies(row.getCol4());

                dimensionsDTOList.add(dimensionDTO);
            }
        }

        // dimensions without model
        for (@SuppressWarnings("unchecked")
        Iterator<AvailableDimensionsELO> it2 = availableDimensions.iterator(); it2.hasNext();) {
            AvailableDimensionsELO row = it2.next();
            Integer type = row.getType();
            // Type: 1 = Account (exp), 2 = Business (cc)
            // Type 3 = Calendar (cal) is supported in other mapper
            if (type == dimensionType) {
                DimensionDTO dimensionDTO = new DimensionDTO();

                DimensionRefImpl dimensionRefImpl = (DimensionRefImpl) row.getDimensionEntityRef();
                dimensionDTO.setDimensionId(dimensionRefImpl.getDimensionPK().getDimensionId());
                dimensionDTO.setDimensionVisId(dimensionRefImpl.getNarrative());

                dimensionDTO.setModel(new ModelCoreDTO());

                dimensionDTO.setType(type);
                dimensionDTO.setDimensionDescription(row.getDescription());
                dimensionDTO.setSequence(null);
                dimensionDTO.setHierarchies(null);

                dimensionsDTOList.add(dimensionDTO);
            }
        }

        return dimensionsDTOList;
    }

    public static DimensionDetailsDTO mapDimension(DimensionImpl dimension, CPConnection cpConnection) {
        DimensionDetailsDTO dimensionDetailsDTO = new DimensionDetailsDTO();

        dimensionDetailsDTO.setDimensionId(((DimensionPK) dimension.getPrimaryKey()).getDimensionId());
        dimensionDetailsDTO.setDimensionVisId(dimension.getEntityRef().getNarrative());

        if (dimension.getModelRef() != null) {
            ModelRef modelRef = (ModelRef) dimension.getModelRef();
            ModelCoreDTO model = ModelCoreMapper.mapModelRefToModelCoreDTO(modelRef);
            dimensionDetailsDTO.setModel(model);
        } else {
            dimensionDetailsDTO.setModel(new ModelCoreDTO());
        }

        // Source System
        Integer externalSystemRef = dimension.getExternalSystemRef();
        dimensionDetailsDTO.setExternalSystemRefName(DimensionUtil.getExternalSystemRefName(externalSystemRef));

        dimensionDetailsDTO.setType(dimension.getType());
        dimensionDetailsDTO.setDimensionDescription(dimension.getDescription());
        // Sequence and hierarchies not used, but exists in DimensionDTO. That values don't matter here.
        dimensionDetailsDTO.setSequence(-1);
        dimensionDetailsDTO.setHierarchies(-1);
        dimensionDetailsDTO.setVersionNum(dimension.getVersionNum());
        dimensionDetailsDTO.setSubmitChangeManagementRequest(dimension.isSubmitChangeManagementRequest());

        if (dimension.getModelRef() != null && dimension.changeManagementRequestsPending()) {
            dimensionDetailsDTO.setReadOnly(true);
            // message ends with "*" - it means message will be red on frontend
            dimensionDetailsDTO.setInUseLabel("This dimension is in use. \n Change requests are pending. Updates are restricted.*");
        } else if (dimension.getExternalSystemRef() != null && dimension.getModelRef() != null) {
            dimensionDetailsDTO.setReadOnly(false);
            dimensionDetailsDTO.setInUseLabel("This dimension is in use and sourced from an external system. \n Updates are restricted. \n Updates will be applied via a change management request.");
        } else if (dimension.getModelRef() != null) {
            dimensionDetailsDTO.setReadOnly(false);
            dimensionDetailsDTO.setInUseLabel("This dimension is in use. \n Updates will be applied via a change management request.");
        } else {
            dimensionDetailsDTO.setReadOnly(false);
            dimensionDetailsDTO.setInUseLabel(null);
        }

        dimensionDetailsDTO.setAugentMode(isAugmentMode(cpConnection.getConnectionContext(), dimension));

        @SuppressWarnings("unchecked")
        Collection<DimensionElementImpl> dimensionElements = dimension.getDimensionElements();
        List<DimensionElementDTO> dimensionElementsDTOList = mapDimensionElements(dimensionElements);
        dimensionDetailsDTO.setDimensionElements(dimensionElementsDTOList);

        return dimensionDetailsDTO;
    }

    private static List<DimensionElementDTO> mapDimensionElements(Collection<DimensionElementImpl> dimensionElements) {
        List<DimensionElementDTO> dimensionElementsDTOList = new ArrayList<DimensionElementDTO>();
        Integer i = 0; // TODO remove
        for (DimensionElementImpl dimensionElementImpl: dimensionElements) {
            // TODO remove
            if (++i > 20) {
                // break;
            }
            DimensionElementDTO dimensionElementDTO = new DimensionElementDTO();

            dimensionElementDTO.setDimensionElementDescription(dimensionElementImpl.getDescription());
            dimensionElementDTO.setDimensionElementId(((DimensionElementCK) dimensionElementImpl.getKey()).getDimensionElementPK().getDimensionElementId());
            // Identifier
            dimensionElementDTO.setDimensionElementVisId(dimensionElementImpl.getVisId());
            // Credit/Debit: credit 1, debit 2, (?) -1 TODO
            dimensionElementDTO.setCreditDebit(dimensionElementImpl.getCreditDebit());
            // Credit/Debit Override: credit 1, debit 2, no override 0
            dimensionElementDTO.setAugCreditDebit(dimensionElementImpl.getAugCreditDebit());
            dimensionElementDTO.setDisabled(dimensionElementImpl.isDisabled());
            dimensionElementDTO.setLeaf(dimensionElementImpl.isLeaf());// now not used TODO maybe remove
            dimensionElementDTO.setNotPlannable(dimensionElementImpl.isNotPlannable());
            dimensionElementDTO.setNullElement(dimensionElementImpl.isNullElement());

            dimensionElementsDTOList.add(dimensionElementDTO);
        }
        return dimensionElementsDTOList;
    }

    public static DimensionImpl mapDimensionDetailsDTOToDimensionDetailsImpl(DimensionImpl impl, DimensionDetailsDTO dimension) {
        // override some fields values
        impl.setVisId(dimension.getDimensionVisId());
        impl.setDescription(dimension.getDimensionDescription());
        impl.setType(dimension.getType());
        impl.setVersionNum(dimension.getVersionNum());
        impl.setSubmitChangeManagementRequest(dimension.isSubmitChangeManagementRequest());
        return impl;
    }

    /**
     * Check if Dimension is in Augment mode. If true, we show i.e.: "Credit/Debit Override" field.
     */
    private static boolean isAugmentMode(ConnectionContext connectionContext, DimensionImpl dimension) {
        return connectionContext == ConnectionContext.INTERACTIVE_WEB && dimension.getExternalSystemRef() != null;
    }

}
