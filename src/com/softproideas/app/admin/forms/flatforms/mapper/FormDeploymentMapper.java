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
package com.softproideas.app.admin.forms.flatforms.mapper;

import java.util.HashMap;
import java.util.List;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.StructureElementKeyImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.xmlform.FormDeploymentDataImpl;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.softproideas.common.models.FormDeploymentDataDTO;
import com.softproideas.common.models.StructureElementCoreDTO;

public class FormDeploymentMapper {

    
//    public static FormDeploymentDataImpl mapToFormDeploymentDataImpl(FormDeploymentDataDTO dto) {
//        FormDeploymentDataImpl impl = new FormDeploymentDataImpl();
//
//        XmlFormPK xmlFormPK = new XmlFormPK(dto.getFlatFormId());
//        impl.setKey(xmlFormPK);
//        impl.setFinanceCubeId(dto.getFinanceCubeId());
//        impl.setModelId(dto.getModelId());
//        impl.setIdentifier(dto.getIdentifier());
//        impl.setDescription(dto.getDescription());
//        impl.setBudgetCycled(dto.getBudgetCycleId());
//        if (dto.getMobile()) {
//            impl.setMobile('Y');
//        } else {
//            impl.setMobile('N');
//        }
//        // mapper to budget location
//        List<StructureElementCoreDTO> businessElementsDTO = dto.getBusinessElements();
//        HashMap<StructureElementKeyImpl, Boolean> businessElements = new HashMap<StructureElementKeyImpl, Boolean>();
//        int i = 0;
//        for (StructureElementCoreDTO structureElementDTO: businessElementsDTO) {
//            StructureElementKeyImpl structureElementKeyImpl = new StructureElementKeyImpl(structureElementDTO.getStructureId(), structureElementDTO.getStructureElementId());
//            businessElements.put(structureElementKeyImpl, true);
//            i++;
//        }
//        impl.setBusinessElements(businessElements);
//
//        // mapper for additional deployment locations
//        List<StructureElementCoreDTO> restElements = dto.getRestElements();
//        businessElementsDTO.addAll(restElements);
//
//        HashMap<Integer, EntityRef> selections = new HashMap<Integer, EntityRef>();
//        i = 0;
//        for (StructureElementCoreDTO structureElementDTO: businessElementsDTO) {
//            StructureElementRefImpl structureImpl = null;
//            if (structureElementDTO != null) {
//                if (structureElementDTO.getStructureId() != 0) {
//                    StructureElementPK structureElementPK = new StructureElementPK(structureElementDTO.getStructureId(), structureElementDTO.getStructureElementId());
//                    structureImpl = new StructureElementRefImpl(structureElementPK, structureElementDTO.getStructureElementVisId());
//                    selections.put(Integer.valueOf(i + 1), structureImpl);
//                } else {
//                    DataTypePK dataTypePK = new DataTypePK((short) structureElementDTO.getStructureElementId());
//                    DataTypeRefImpl dataTypeRefImpl = new DataTypeRefImpl(dataTypePK, structureElementDTO.getStructureElementVisId(), null, 0, null, null);
//                    selections.put(Integer.valueOf(i + 1), dataTypeRefImpl);
//                }
//            } else {
//                selections.put(Integer.valueOf(i + 1), null);
//            }
//            i++;
//        }
//        impl.setSelection(selections);
//
//        impl.setMailType(dto.getMailType());
//        impl.setMailContent(dto.getMailContent());
//        return impl;
//    }

}
