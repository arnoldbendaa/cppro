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
package com.softproideas.app.admin.models.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.datatype.AllDataTypesELO;
import com.cedar.cp.dto.dimension.AvailableDimensionsELO;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.dto.model.AllFinanceCubesELO;
import com.cedar.cp.dto.model.AllModelsELO;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.softproideas.app.admin.financecubes.model.DimensionDTO;
import com.softproideas.app.admin.models.model.FinanceCubeModelDTO;
import com.softproideas.app.admin.models.model.HierarchyDTO;
import com.softproideas.app.admin.models.model.ModelDTO;
import com.softproideas.app.admin.models.model.ModelDetailsDTO;
import com.softproideas.app.core.datatype.mapper.DataTypeCoreMapper;
import com.softproideas.app.core.datatype.model.DataTypeCoreDTO;
import com.softproideas.app.core.model.mapper.ModelCoreMapper;
import com.softproideas.app.core.model.model.ModelCoreDTO;
import com.softproideas.commons.model.Property;

public class ModelsMapper {

    public static List<ModelDTO> mapToModelsDTO(EntityList list) {
        List<ModelDTO> modelsDTO = new ArrayList<ModelDTO>();

        for (int i = 0; i < list.getNumRows(); i++) {
            ModelDTO modelDTO = new ModelDTO();

            mapModelProperty(modelDTO, list.getValueAt(i, "Model"));
            mapModelIdProperty(modelDTO, list.getValueAt(i, "ModelId"));
            mapVisIdProperty(modelDTO, list.getValueAt(i, "VisId"));
            mapDescriptionProperty(modelDTO, list.getValueAt(i, "Description"));

            modelsDTO.add(modelDTO);
        }
        return modelsDTO;
    }

    @SuppressWarnings("unchecked")
    public static List<ModelCoreDTO> mapAllModelsELOToModelsDTO(AllModelsELO list) {
        List<ModelCoreDTO> modelsDTO = new ArrayList<ModelCoreDTO>();

        for (Iterator<AllModelsELO> it = list.iterator(); it.hasNext();) {
            AllModelsELO row = it.next();

            ModelCoreDTO modelDTO = ModelCoreMapper.mapModelRefToModelCoreDTO(row.getModelEntityRef());
            modelDTO.setModelDescription(row.getDescription());
            modelsDTO.add(modelDTO);
        }

        return modelsDTO;
    }

/*    @SuppressWarnings("unchecked")
    public static List<ModelCoreWithGlobalDTO> mapAllModelsELOToModelsWithGlobalDTO(AllModelsELO list) {
        List<ModelCoreWithGlobalDTO> modelsDTO = new ArrayList<ModelCoreWithGlobalDTO>();

        for (Iterator<AllModelsELO> it = list.iterator(); it.hasNext();) {
            AllModelsELO row = it.next();

            ModelCoreWithGlobalDTO modelDTO = new ModelCoreWithGlobalDTO();
            ModelRef modelRef = row.getModelEntityRef();
            ModelPK modelPK = (ModelPK) modelRef.getPrimaryKey();
            modelDTO.setModelId(modelPK.getModelId());
            modelDTO.setModelVisId(modelRef.getNarrative());
            modelDTO.setModelDescription(row.getDescription());
            if (row.isGlobal() != null && row.isGlobal()) {
                modelDTO.setGlobal(true);
            } else {
                modelDTO.setGlobal(false);
            }
            modelsDTO.add(modelDTO);
        }

        return modelsDTO;
    }*/

    @SuppressWarnings("unchecked")
    public static List<DataTypeCoreDTO> mapAllDataTypesELOToDataTypesDTO(AllDataTypesELO list) {
        List<DataTypeCoreDTO> dataTypesDTO = new ArrayList<DataTypeCoreDTO>();

        for (Iterator<AllDataTypesELO> it = list.iterator(); it.hasNext();) {
            AllDataTypesELO row = it.next();

            DataTypeCoreDTO dataTypeDTO = DataTypeCoreMapper.mapDataTypeRefToDataTypeCoreDTO(row.getDataTypeEntityRef());
            dataTypeDTO.setDataTypeDescription(row.getDescription());
            dataTypesDTO.add(dataTypeDTO);
        }

        return dataTypesDTO;
    }

    private static void mapModelProperty(ModelDTO modelDTO, Object model) {
        if (model != null && model instanceof ModelRef) {
            ModelRef modelRef = (ModelRef) model;
            ModelPK modelPK = (ModelPK) modelRef.getPrimaryKey();
            modelDTO.setModelId(modelPK.getModelId());

            String name = modelRef.getNarrative();
            modelDTO.setModelVisId(name);
        }
    }

    private static void mapDescriptionProperty(ModelDTO modelDTO, Object description) {
        if (description != null && description instanceof String) {
            modelDTO.setModelDescription((String) description);
        }
    }

    private static void mapVisIdProperty(ModelDTO modelDTO, Object visId) {
        if (visId != null && visId instanceof String) {
            modelDTO.setModelVisId((String) visId);
        }
    }

    private static void mapModelIdProperty(ModelDTO modelDTO, Object modelId) {
        if (modelId != null && modelId instanceof Integer) {
            modelDTO.setModelId((Integer) modelId);
        }
    }

    public static List<FinanceCubeModelDTO> mapAllFinanseCubesELOWithModel(AllFinanceCubesELO elo) {
        List<FinanceCubeModelDTO> fcModelList = new ArrayList<FinanceCubeModelDTO>();

        for (@SuppressWarnings("unchecked")
        Iterator<AllFinanceCubesELO> it = elo.iterator(); it.hasNext();) {
            AllFinanceCubesELO row = it.next();

            FinanceCubeModelDTO financeCubeModelDTO = new FinanceCubeModelDTO();

            FinanceCubeRefImpl propertyFinanceCube = (FinanceCubeRefImpl) row.getFinanceCubeEntityRef();
            financeCubeModelDTO.setFinanceCubeId(propertyFinanceCube.getFinanceCubePK().getFinanceCubeId());
            financeCubeModelDTO.setFinanceCubeVisId(propertyFinanceCube.getNarrative());

            ModelRefImpl propertyModel = (ModelRefImpl) row.getModelEntityRef();
            financeCubeModelDTO.setModelId(propertyModel.getModelPK().getModelId());
            financeCubeModelDTO.setModelVisId(propertyModel.getNarrative());

            String description = row.getDescription();
            financeCubeModelDTO.setDescription(description);

            fcModelList.add(financeCubeModelDTO);
        }

        return fcModelList;
    }

    @SuppressWarnings("unchecked")
    public static ModelDetailsDTO mapModelImpl(ModelImpl modelImpl, AvailableDimensionsELO dimensionList, HierarchyRef[] hierarchyRef) {
        ModelDetailsDTO modelDTO = new ModelDetailsDTO();
        DimensionDTO account = new DimensionDTO();
        List<DimensionDTO> availableAccounts = new ArrayList<DimensionDTO>();
        DimensionDTO calendar = new DimensionDTO();
        List<DimensionDTO> availableCalendars = new ArrayList<DimensionDTO>();
        HierarchyDTO hierarchy = new HierarchyDTO();
        List<HierarchyDTO> availableHierarchys = new ArrayList<HierarchyDTO>();
        List<DimensionDTO> BusinessList = new ArrayList<DimensionDTO>();
        List<DimensionDTO> availableBusiness = new ArrayList<DimensionDTO>();
        List<Property> properties = new ArrayList<Property>();

        modelDTO.setModelId(((ModelPK) modelImpl.getPrimaryKey()).getModelId());
        modelDTO.setModelDescription(modelImpl.getDescription());

        modelDTO.setLocked(modelImpl.isLocked());
        modelDTO.setVirementEntryEnabled(modelImpl.isVirementEntryEnabled());
        modelDTO.setVirementsInUse(modelImpl.isVirementsInUse());
        modelDTO.setModelVisId(modelImpl.getVisId());
        modelDTO.setVersionNum(modelImpl.getVersionNum());

        mapDimensionRefImpl((DimensionRefImpl) modelImpl.getAccountRef(), account);
        modelDTO.setAccount(account);

        mapDimensionRefImpl((DimensionRefImpl) modelImpl.getCalendarRef(), calendar);
        modelDTO.setCalendar(calendar);

        mapHierarchyRefImpl(modelImpl.getBudgetHierarchyRef(), hierarchy);
        modelDTO.setBudgetHierarchy(hierarchy);

        for (DimensionRef dimensionElement: (List<DimensionRef>) modelImpl.getSelectedDimensionRefs()) {
            DimensionDTO dimensionElementDTO = new DimensionDTO();
            mapDimensionRefImpl((DimensionRefImpl) dimensionElement, dimensionElementDTO);
            BusinessList.add(dimensionElementDTO);
            for (HierarchyRef hierarchyElement: hierarchyRef) {
                HierarchyCK hierarchyCK = (HierarchyCK) hierarchyElement.getPrimaryKey();
                if (hierarchyCK.getDimensionPK().equals(dimensionElement.getPrimaryKey())) {
                    HierarchyDTO newHierarchy = new HierarchyDTO();
                    newHierarchy.setHierarchyId(((HierarchyCK) hierarchyElement.getPrimaryKey()).getHierarchyPK().getHierarchyId());
                    newHierarchy.setHierarchyVisId(hierarchyElement.getNarrative());
                    newHierarchy.setDimensionId(hierarchyCK.getDimensionPK().getDimensionId());
                    availableHierarchys.add(newHierarchy);
                }
            }
        }
        modelDTO.setBusiness(BusinessList);

        Map<String, String> propertiesMap = modelImpl.getProperties();
        for (Entry<String, String> mapElement: propertiesMap.entrySet()) {
            Property property = new Property();
            property.setName(mapElement.getKey());
            property.setValue(mapElement.getValue());
            properties.add(property);
        }
        modelDTO.setProperties(properties);

        for (Iterator<AvailableDimensionsELO> it = dimensionList.iterator(); it.hasNext();) {
            AvailableDimensionsELO row = it.next();
            int type = row.getType();
            if (type == 1) {
                DimensionDTO dimensionElementDTO1 = new DimensionDTO();
                DimensionRef dimensionEntityRef = row.getDimensionEntityRef();
                mapDimensionRefImpl((DimensionRefImpl) dimensionEntityRef, dimensionElementDTO1);
                availableAccounts.add(dimensionElementDTO1);
            } else if (type == 3) {
                DimensionDTO dimensionElementDTO2 = new DimensionDTO();
                DimensionRef dimensionEntityRef = row.getDimensionEntityRef();
                mapDimensionRefImpl((DimensionRefImpl) dimensionEntityRef, dimensionElementDTO2);
                availableCalendars.add(dimensionElementDTO2);
            } else {
                DimensionDTO dimensionElementDTO3 = new DimensionDTO();
                DimensionRef dimensionEntityRef = row.getDimensionEntityRef();
                mapDimensionRefImpl((DimensionRefImpl) dimensionEntityRef, dimensionElementDTO3);
                availableBusiness.add(dimensionElementDTO3);
                for (HierarchyRef hierarchyElement: hierarchyRef) {
                    HierarchyCK hierarchyCK = (HierarchyCK) hierarchyElement.getPrimaryKey();
                    if (hierarchyCK.getDimensionPK().equals(dimensionEntityRef.getPrimaryKey())) {
                        HierarchyDTO newHierarchy = new HierarchyDTO();
                        newHierarchy.setHierarchyId(((HierarchyCK) hierarchyElement.getPrimaryKey()).getHierarchyPK().getHierarchyId());
                        newHierarchy.setHierarchyVisId(hierarchyElement.getNarrative());
                        newHierarchy.setDimensionId(hierarchyCK.getDimensionPK().getDimensionId());
                        availableHierarchys.add(newHierarchy);
                    }
                }
            }

            modelDTO.setAvailableAccounts(availableAccounts);
            modelDTO.setAvailableCalendars(availableCalendars);
            modelDTO.setAvailableBudgetHierarchy(availableHierarchys);
            modelDTO.setAvailableBusiness(availableBusiness);
        }
        return modelDTO;
    }

    @SuppressWarnings("unchecked")
    public static void mapAvailableDimensionAndHierarchyAndSetProperties(ModelDetailsDTO modelDTO, AvailableDimensionsELO dimensionList, HierarchyRef[] hierarchyRef) {
        List<DimensionDTO> availableAccounts = new ArrayList<DimensionDTO>();
        List<DimensionDTO> availableCalendars = new ArrayList<DimensionDTO>();
        List<HierarchyDTO> availableHierarchys = new ArrayList<HierarchyDTO>();
        List<DimensionDTO> availableBusiness = new ArrayList<DimensionDTO>();
        List<Property> properties = new ArrayList<Property>();

        for (Iterator<AvailableDimensionsELO> it = dimensionList.iterator(); it.hasNext();) {
            AvailableDimensionsELO row = it.next();
            int type = row.getType();
            if (type == 1) {
                DimensionDTO dimensionElementDTO1 = new DimensionDTO();
                DimensionRef dimensionEntityRef = row.getDimensionEntityRef();
                mapDimensionRefImpl((DimensionRefImpl) dimensionEntityRef, dimensionElementDTO1);
                availableAccounts.add(dimensionElementDTO1);
            } else if (type == 3) {
                DimensionDTO dimensionElementDTO2 = new DimensionDTO();
                DimensionRef dimensionEntityRef = row.getDimensionEntityRef();
                mapDimensionRefImpl((DimensionRefImpl) dimensionEntityRef, dimensionElementDTO2);
                availableCalendars.add(dimensionElementDTO2);
            } else {
                DimensionDTO dimensionElementDTO3 = new DimensionDTO();
                DimensionRef dimensionEntityRef = row.getDimensionEntityRef();
                mapDimensionRefImpl((DimensionRefImpl) dimensionEntityRef, dimensionElementDTO3);
                availableBusiness.add(dimensionElementDTO3);
                for (HierarchyRef hierarchyElement: hierarchyRef) {
                    HierarchyCK hierarchyCK = (HierarchyCK) hierarchyElement.getPrimaryKey();
                    if (hierarchyCK.getDimensionPK().equals(dimensionEntityRef.getPrimaryKey())) {
                        HierarchyDTO newHierarchy = new HierarchyDTO();
                        newHierarchy.setHierarchyId(((HierarchyCK) hierarchyElement.getPrimaryKey()).getHierarchyPK().getHierarchyId());
                        newHierarchy.setHierarchyVisId(hierarchyElement.getNarrative());
                        newHierarchy.setDimensionId(hierarchyCK.getDimensionPK().getDimensionId());
                        availableHierarchys.add(newHierarchy);
                    }
                }
            }

        }
        modelDTO.setAvailableAccounts(availableAccounts);
        modelDTO.setAvailableCalendars(availableCalendars);
        modelDTO.setAvailableBudgetHierarchy(availableHierarchys);
        modelDTO.setAvailableBusiness(availableBusiness);
        modelDTO.setProperties(properties);

    }

    private static void mapHierarchyRefImpl(HierarchyRef budgetHierarchyRef, HierarchyDTO hierarchyDTO) {
        hierarchyDTO.setHierarchyId(((HierarchyPK) budgetHierarchyRef.getPrimaryKey()).getHierarchyId());
        hierarchyDTO.setHierarchyVisId(budgetHierarchyRef.getNarrative());
    }

    public static void mapDimensionRefImpl(DimensionRefImpl dimensionRefImpl, DimensionDTO dimensionDTO) {
        dimensionDTO.setDimensionId(dimensionRefImpl.getDimensionPK().getDimensionId());
        dimensionDTO.setDimensionVisId(dimensionRefImpl.getNarrative());
        dimensionDTO.setType(dimensionRefImpl.getType());
    }

    @SuppressWarnings("unchecked")
    public static ModelImpl mapModelDTO(ModelDetailsDTO modelDetailsDTO, ModelImpl modelImpl) {
        Map<String, String> map = new HashMap<String, String>();
        modelImpl.setVisId(modelDetailsDTO.getModelVisId());
        modelImpl.setDescription(modelDetailsDTO.getModelDescription());
        modelImpl.setVirementEntryEnabled(modelDetailsDTO.isVirementEntryEnabled());

        DimensionPK dimensionPK = new DimensionPK(modelDetailsDTO.getAccount().getDimensionId());
        DimensionRef accound = new DimensionRefImpl(dimensionPK, modelDetailsDTO.getAccount().getDimensionVisId(), modelDetailsDTO.getAccount().getType());
        modelImpl.setAccountRef(accound);

        dimensionPK = new DimensionPK(modelDetailsDTO.getCalendar().getDimensionId());
        DimensionRef calendar = new DimensionRefImpl(dimensionPK, modelDetailsDTO.getCalendar().getDimensionVisId(), modelDetailsDTO.getCalendar().getType());
        modelImpl.setCalendarRef(calendar);

        dimensionPK = new DimensionPK(modelDetailsDTO.getBusiness().get(0).getDimensionId());
        DimensionRef business = new DimensionRefImpl(dimensionPK, modelDetailsDTO.getBusiness().get(0).getDimensionVisId(), modelDetailsDTO.getBusiness().get(0).getType());
        List<DimensionRef> dimensions = modelImpl.getSelectedDimensionRefs();
        dimensions.clear();
        dimensions.add(business);

        HierarchyPK hierarchyPK = new HierarchyPK(modelDetailsDTO.getBudgetHierarchy().getHierarchyId());
        dimensionPK = new DimensionPK(modelDetailsDTO.getBudgetHierarchy().getDimensionId());
        HierarchyCK hierarchyCK = new HierarchyCK(dimensionPK, hierarchyPK); // DimensionPK is property of DimensionRef business.
        HierarchyRef hierarchy = new HierarchyRefImpl(hierarchyCK, modelDetailsDTO.getBudgetHierarchy().getHierarchyVisId());
        modelImpl.setBudgetHierarchyRef(hierarchy);

        for (Property listElement: modelDetailsDTO.getProperties()) {
            map.put(listElement.getName(), listElement.getValue());
        }

        modelImpl.setProperties(map);
        modelImpl.setVersionNum(modelDetailsDTO.getVersionNum());

        return modelImpl;
    }

}
