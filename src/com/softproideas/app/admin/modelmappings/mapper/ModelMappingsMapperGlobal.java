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
package com.softproideas.app.admin.modelmappings.mapper;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.calendar.CalendarYearSpec;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.api.model.globalmapping2.GlobalMappedModel2Ref;
import com.cedar.cp.api.model.globalmapping2.MappedCalendar;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarElement;
import com.cedar.cp.api.model.globalmapping2.MappedCalendarYear;
import com.cedar.cp.api.model.globalmapping2.MappedDataType;
import com.cedar.cp.api.model.globalmapping2.MappedDimension;
import com.cedar.cp.api.model.globalmapping2.MappedDimensionElement;
import com.cedar.cp.api.model.globalmapping2.MappedFinanceCube;
import com.cedar.cp.api.model.globalmapping2.MappedHierarchy;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup;
import com.cedar.cp.api.model.globalmapping2.extsys.FinancePeriod;
import com.cedar.cp.api.model.mapping.MappedModelRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.base.MappingKeyImpl;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.datatype.DataTypesForImpExpELO;
import com.cedar.cp.dto.dimension.CalendarHierarchyElementImpl;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;
import com.cedar.cp.dto.dimension.HierarchyElementPK;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarLeafElementKey;
import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.dto.extsys.ExternalSystemRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.globalmapping2.AllGlobalMappedModels2ELO;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2Impl;
import com.cedar.cp.dto.model.globalmapping2.GlobalMappedModel2PK;
import com.cedar.cp.dto.model.globalmapping2.MappedCalendarElementImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedCalendarElementPK;
import com.cedar.cp.dto.model.globalmapping2.MappedCalendarImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedCalendarYearImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedCalendarYearPK;
import com.cedar.cp.dto.model.globalmapping2.MappedDataTypeImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedDataTypePK;
import com.cedar.cp.dto.model.globalmapping2.MappedDimensionElementImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedDimensionElementPK;
import com.cedar.cp.dto.model.globalmapping2.MappedDimensionImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedDimensionPK;
import com.cedar.cp.dto.model.globalmapping2.MappedFinanceCubeImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedFinanceCubePK;
import com.cedar.cp.dto.model.globalmapping2.MappedHierarchyImpl;
import com.cedar.cp.dto.model.globalmapping2.MappedHierarchyPK;
import com.cedar.cp.dto.model.mapping.AllMappedModelsELO;
import com.cedar.cp.dto.model.mapping.MappedModelPK;
import com.softproideas.app.admin.datatypes.model.DataTypeDetailsDTO;
import com.softproideas.app.admin.modelmappings.model.CompanyDTO;
import com.softproideas.app.admin.modelmappings.model.DataTypesTreeNodeDTO;
import com.softproideas.app.admin.modelmappings.model.DimensionAndHierarchiesNodeDTO;
import com.softproideas.app.admin.modelmappings.model.DimensionElementNodeDTO;
import com.softproideas.app.admin.modelmappings.model.ExtSysDataTypesTreeNode;
import com.softproideas.app.admin.modelmappings.model.ExtSysTreeNode;
import com.softproideas.app.admin.modelmappings.model.YearDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedCalendarDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedDataTypeDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedDimensionDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedDimensionElementDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedFinanceCubeDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedHierarchyDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedModelDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedModelDetailsDTO;
import com.softproideas.app.core.externalsystem.model.ExternalSystemCoreDTO;
import com.softproideas.app.core.model.model.ModelCoreDTO;
import com.softproideas.commons.model.tree.NodeStaticDTO;

/**
 * <p>Transforms various data types related to global model mapping.</p>
 *
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class ModelMappingsMapperGlobal {

    @SuppressWarnings("unchecked")
    public static List<MappedModelDTO> mapAllMappedModelsELO(AllMappedModelsELO allMappedModels, AllGlobalMappedModels2ELO allGlobalMappedModels) {
        List<MappedModelDTO> mappedModelsDTO = new ArrayList<MappedModelDTO>();

        // all mapped models
        for (Iterator<AllMappedModelsELO> it = allMappedModels.iterator(); it.hasNext();) {
            AllMappedModelsELO row = it.next();
            MappedModelDTO mappedModelDTO = new MappedModelDTO();
            MappedModelRef mappedModelRef = row.getMappedModelEntityRef();
            MappedModelPK mappedModelPK = (MappedModelPK) mappedModelRef.getPrimaryKey();

            // Model Id
            mappedModelDTO.setModelId(row.getModelId());
            // Mapped Model Id
            mappedModelDTO.setMappedModelId(mappedModelPK.getMappedModelId());
            // VisId
            mappedModelDTO.setMappedModelVisId(row.getVisId());
            // Description
            mappedModelDTO.setMappedModelDescription(row.getDescription());
            // External System VisId
            mappedModelDTO.setExternalSystemVisId(row.getExternalSystemEntityRef().getNarrative());
            // Companies
            mappedModelDTO.setCompanies(row.getCompanyVisId());
            // Ledger
            mappedModelDTO.setLedgerVisId(row.getLedgerVisId());
            // Global
            mappedModelDTO.setGlobal(false);
            
            mappedModelDTO.setActive(false);

            mappedModelsDTO.add(mappedModelDTO);
        }

        // all global mapped models
        for (Iterator<AllGlobalMappedModels2ELO> it = allGlobalMappedModels.iterator(); it.hasNext();) {
            AllGlobalMappedModels2ELO row = it.next();
            MappedModelDTO mappedModelDTO = new MappedModelDTO();
            GlobalMappedModel2Ref mappedGlobalModelRef = row.getMappedModelEntityRef();
            GlobalMappedModel2PK mappedGlobalModelPK = (GlobalMappedModel2PK) mappedGlobalModelRef.getPrimaryKey();

            // Id
            mappedModelDTO.setMappedModelId(mappedGlobalModelPK.getMappedModelId());
            // VisId
            mappedModelDTO.setMappedModelVisId(row.getVisId());
            // Description
            mappedModelDTO.setMappedModelDescription(row.getDescription());
            // External System VisId
            mappedModelDTO.setExternalSystemVisId(row.getExternalSystemEntityRef().getNarrative());
            // Companies
            mappedModelDTO.setCompanies(row.getCompanyVisId());
            // Ledger
            mappedModelDTO.setLedgerVisId(row.getLedgerVisId());
            // Global
            mappedModelDTO.setGlobal(true);

            mappedModelsDTO.add(mappedModelDTO);
        }

        return mappedModelsDTO;
    }

    public static MappedModelDetailsDTO mapMappedModelImpl(int mappedModelId, GlobalMappedModel2Impl mappedModelImpl) {
        MappedModelDetailsDTO mappedModelDetailsDTO = new MappedModelDetailsDTO();
        // Id
        mappedModelDetailsDTO.setMappedModelId(mappedModelId);
        // VisId
        mappedModelDetailsDTO.setMappedModelVisId(mappedModelImpl.getModelVisId());
        // Description
        mappedModelDetailsDTO.setMappedModelDescription(mappedModelImpl.getModelDescription());
        // External System VisId
        mappedModelDetailsDTO.setExternalSystemVisId(mappedModelImpl.getExternalSystemRef().getNarrative());
        // Companies
        mappedModelDetailsDTO.setCompanies(mappedModelImpl.getCompanyVisId());
        // Ledger
        mappedModelDetailsDTO.setLedgerVisId(mappedModelImpl.getLedgerVisId());
        // Companies list
        mappedModelDetailsDTO.setListCompanies(mappedModelImpl.getCompaniesVisId());
        // Version
        mappedModelDetailsDTO.setVersionNum(mappedModelImpl.getVersionNum());
        // Model
        ModelRef modelRef = mappedModelImpl.getOwningModelRef();
        String modelVisId = modelRef.getNarrative();
        ModelPK modelPK = (ModelPK) modelRef.getPrimaryKey();
        ModelCoreDTO modelCoreDTO = new ModelCoreDTO();
        modelCoreDTO.setModelId(modelPK.getModelId());
        modelCoreDTO.setModelVisId(modelVisId);
        mappedModelDetailsDTO.setModel(modelCoreDTO);
        // External System
        ExternalSystemRef externalSystemRef = mappedModelImpl.getExternalSystemRef();
        String externalSystemVisId = externalSystemRef.getNarrative();
        ExternalSystemPK externalSystemPK = (ExternalSystemPK) externalSystemRef.getPrimaryKey();
        ExternalSystemCoreDTO externalSystemCoreDTO = new ExternalSystemCoreDTO();
        externalSystemCoreDTO.setExternalSystemVisId(externalSystemVisId);
        externalSystemCoreDTO.setExternalSystemId(externalSystemPK.getExternalSystemId());
        mappedModelDetailsDTO.setExternalSystem(externalSystemCoreDTO);

        mappedModelDetailsDTO.setMappedDimensions(mapDimensions(mappedModelImpl.getDimensionMappings()));
        mappedModelDetailsDTO.setMappedCalendar(mapCalendar(mappedModelImpl.getMappedCalendar()));
        mappedModelDetailsDTO.setMappedFinanceCubes(mapFinanceCubes(mappedModelImpl.getMappedFinanceCubes()));
        return mappedModelDetailsDTO;
    }

    private static List<MappedFinanceCubeDTO> mapFinanceCubes(List<MappedFinanceCube> mappedFinanceCubes) {
        List<MappedFinanceCubeDTO> list = new ArrayList<MappedFinanceCubeDTO>();

        for (MappedFinanceCube mappedFinanceCube: mappedFinanceCubes) {
            MappedFinanceCubeDTO mappedFinanceCubeDTO = new MappedFinanceCubeDTO();

            int id = ((MappedFinanceCubePK) mappedFinanceCube.getKey()).getMappedFinanceCubeId();
            mappedFinanceCubeDTO.setFinanceCubeId(id);
            mappedFinanceCubeDTO.setFinanceCubeVisId(mappedFinanceCube.getName());
            mappedFinanceCubeDTO.setFinanceCubeDescription(mappedFinanceCube.getDescription());

            mappedFinanceCubeDTO.setMappedDataTypes(mapDataTypes(mappedFinanceCube.getMappedDataTypes()));

            list.add(mappedFinanceCubeDTO);
        }

        return list;
    }

    private static List<MappedDataTypeDTO> mapDataTypes(List<MappedDataType> mappedDataTypes) {
        ArrayList<MappedDataTypeDTO> list = new ArrayList<MappedDataTypeDTO>();

        for (MappedDataType mappedDataType: mappedDataTypes) {
            MappedDataTypeDTO mappedDataTypeDTO = new MappedDataTypeDTO();

            MappedDataTypePK mappedDataTypePK = (MappedDataTypePK) mappedDataType.getKey();
            mappedDataTypeDTO.setMappedDateTypeId(mappedDataTypePK.getMappedDataTypeId());

            DataTypePK dataTypePK = (DataTypePK) mappedDataType.getDataTypeRef().getPrimaryKey();
            mappedDataTypeDTO.setDataTypeId(dataTypePK.getDataTypeId());
            mappedDataTypeDTO.setDataTypeVisId(mappedDataType.getDataTypeRef().getNarrative());

            mappedDataTypeDTO.setValueType(mappedDataType.getExtSysValueType());
            mappedDataTypeDTO.setCurrency(mappedDataType.getExtSysCurrency());
            mappedDataTypeDTO.setBalType(mappedDataType.getExtSysBalType());

            mappedDataTypeDTO.setImpExpStatus(mappedDataType.getImpExpStatus());
            mappedDataTypeDTO.setImpStartYearOffset(mappedDataType.getImpStartYearOffset());
            mappedDataTypeDTO.setImpEndYearOffset(mappedDataType.getImpEndYearOffset());
            mappedDataTypeDTO.setExpStartYearOffset(mappedDataType.getExpStartYearOffset());
            mappedDataTypeDTO.setExpEndYearOffset(mappedDataType.getExpEndYearOffset());
            list.add(mappedDataTypeDTO);
        }
        return list;
    }

    private static MappedCalendarDTO mapCalendar(MappedCalendar mappedCalendar) {
        MappedCalendarDTO calendar = new MappedCalendarDTO();

        calendar.setCalendarVisId(mappedCalendar.getCalendar().getVisId());
        calendar.setCalendarDescription(mappedCalendar.getCalendar().getDescription());

        List<YearDTO> years = new ArrayList<YearDTO>();
        for (CalendarYearSpec calendarYearSpec: mappedCalendar.getCalendar().getYearSpecs()) {
            if (calendarYearSpec instanceof CalendarYearSpecImpl) {
                YearDTO year = new YearDTO();
                CalendarYearSpecImpl impl = (CalendarYearSpecImpl) calendarYearSpec;
                year.setYearId(impl.getId());
                year.setYearVisId("" + impl.getYear());
                year.setSpec(impl.getSpec());
                years.add(year);
            }
        }

        calendar.setYears(years);
        return calendar;
    }

    private static List<MappedDimensionDTO> mapDimensions(List<MappedDimension> dimensionMappings) {
        List<MappedDimensionDTO> dimensions = new ArrayList<MappedDimensionDTO>();

        for (MappedDimension mappedDimension: dimensionMappings) {
            MappedDimensionDTO dimension = mapDimension(mappedDimension);
            dimension.setMappedHierchies(mapMappedHierarchies(mappedDimension.getMappedHierarchies()));
            dimension.setMappedDimensionElements(mapDimnesionElements(mappedDimension.getMappedDimensionElements()));
            dimensions.add(dimension);
        }
        return dimensions;
    }

    private static MappedDimensionDTO mapDimension(MappedDimension mappedDimension) {
        MappedDimensionDTO dimension = new MappedDimensionDTO();

        DimensionPK dimensionPK = (DimensionPK) mappedDimension.getDimension().getPrimaryKey();
        dimension.setDimensionId(dimensionPK.getDimensionId());
        dimension.setDimensionVisId(mappedDimension.getDimensionVisId());
        dimension.setDimensionDescription(mappedDimension.getDimensionDescription());
        dimension.setType(mappedDimension.getDimensionType());
        return dimension;
    }

    private static List<MappedDimensionElementDTO> mapDimnesionElements(SortedSet<MappedDimensionElement> mappedDimensionElements) {
        List<MappedDimensionElementDTO> list = new ArrayList<MappedDimensionElementDTO>();

        Iterator<MappedDimensionElement> iterator = mappedDimensionElements.iterator();
        while (iterator.hasNext()) {
            MappedDimensionElement mappedDimensionElement = iterator.next();
            MappedDimensionElementDTO dimensionElement = new MappedDimensionElementDTO();

            dimensionElement.setVisId1(mappedDimensionElement.getVisId1());
            dimensionElement.setVisId2(mappedDimensionElement.getVisId2());
            dimensionElement.setVisId3(mappedDimensionElement.getVisId3());
            dimensionElement.setMappingType(mappedDimensionElement.getMappingType());

            Hashtable<String, Boolean> selectedCompanies = mappedDimensionElement.getSelectedCompanies();
            List<String> companies = new ArrayList<String>();
            for (String key: selectedCompanies.keySet()) {
                companies.add(key);
            }
            dimensionElement.setSelectedCompanies(companies);

            list.add(dimensionElement);
        }
        return list;
    }

    private static List<MappedHierarchyDTO> mapMappedHierarchies(List<MappedHierarchy> mappedHierarchies) {
        List<MappedHierarchyDTO> list = new ArrayList<MappedHierarchyDTO>();

        for (MappedHierarchy mappedHierarchy: mappedHierarchies) {
            MappedHierarchyDTO hierarchy = new MappedHierarchyDTO();

            HierarchyCK hierarchyCK = (HierarchyCK) mappedHierarchy.getHierarchyRef().getPrimaryKey();
            hierarchy.setHierarchyId(hierarchyCK.getHierarchyPK().getHierarchyId());
            hierarchy.setHierarchyVisId(mappedHierarchy.getNewHierarchyVisId());
            hierarchy.setHierarchyDescription(mappedHierarchy.getNewHierarchyDescription());
            hierarchy.setMappedHierarchyVisId(mappedHierarchy.getHierarchyVisId1());
            hierarchy.setVisId1(mappedHierarchy.getHierarchyVisId1());
            hierarchy.setVisId2(mappedHierarchy.getHierarchyVisId2());

            hierarchy.setResponsibilityAreaHierarchy(mappedHierarchy.isResponsibilityAreaHierarchy());
            Hashtable<String, Boolean> selectedCompanies = mappedHierarchy.getSelectedCompanies();
            List<String> companies = new ArrayList<String>();
            for (String key: selectedCompanies.keySet()) {
                companies.add(key);
            }
            hierarchy.setSelectedCompanies(companies);

            list.add(hierarchy);
        }
        return list;
    }

    public static List<CompanyDTO> mapFinanceCompany(List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany> externalCompanies) {
        List<CompanyDTO> companiesDTO = new ArrayList<CompanyDTO>();

        for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany externalCompany: externalCompanies) {
            CompanyDTO companyDTO = new CompanyDTO();

            EntityRef entityRef = externalCompany.getEntityRef();
            String companyVisId = entityRef.getNarrative();
            MappingKeyImpl key = (MappingKeyImpl) entityRef.getPrimaryKey();
            Integer companyId = Integer.parseInt((String) key.get(0));

            companyDTO.setCompanyId(companyId);
            companyDTO.setCompanyVisId(companyVisId);

            companiesDTO.add(companyDTO);
        }

        return companiesDTO;
    }

    public static List<NodeStaticDTO> mapExternalHierarchiesGlobal(List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy> externalHierarchies) {
        List<NodeStaticDTO> children = new ArrayList<NodeStaticDTO>();
        for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy externalHierarchy: externalHierarchies) {
            DimensionAndHierarchiesNodeDTO node = new DimensionAndHierarchiesNodeDTO();
            String nodeVisId = externalHierarchy.getEntityRef().getNarrative();
            MappingKeyImpl primaryKey = (MappingKeyImpl) externalHierarchy.getEntityRef().getPrimaryKey();

            String visId1 = (String) primaryKey.get(0);
            String visId2 = (primaryKey.get(1) != null) ? (String) primaryKey.get(1) : null;
            List<String> companies = new ArrayList<String>();
            for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany company: externalHierarchy.getFinanceCompanies()) {
                companies.add(company.getCompanyVisId());
            }

            node.setId(visId1);
            node.setStateLeaf(false);
            node.setResponsibilityHierarchy(false);
            node.setText(nodeVisId);
            node.setDescription(nodeVisId);
            node.setVisId1(visId1);
            node.setVisId2(visId2);
            node.setCompanies(companies);
            node.setVisId(externalHierarchy.getSuggestedCPVisId());

            children.add(node);
        }

        return children;
    }

    public static List<NodeStaticDTO> mapExternalHierarchies(List<com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy> externalHierarchies) {
        List<NodeStaticDTO> children = new ArrayList<NodeStaticDTO>();
        for (com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy externalHierarchy: externalHierarchies) {
            DimensionAndHierarchiesNodeDTO node = new DimensionAndHierarchiesNodeDTO();
            String nodeVisId = externalHierarchy.getEntityRef().getNarrative();
            MappingKeyImpl primaryKey = (MappingKeyImpl) externalHierarchy.getEntityRef().getPrimaryKey();

            String visId1 = (String) primaryKey.get(0);
            String visId2 = (primaryKey.get(1) != null) ? (String) primaryKey.get(1) : null;

            node.setId(visId1);
            node.setStateLeaf(false);
            node.setResponsibilityHierarchy(false);
            node.setText(nodeVisId);
            node.setVisId(externalHierarchy.getOnlySuggestedCPVisId());
            node.setDescription(nodeVisId);
            node.setVisId1(visId1);
            node.setVisId2(visId2);

            children.add(node);
        }

        return children;
    }

    public static List<DimensionElementNodeDTO> mapHierarchyChildrenGlobal(List<com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy> externalHierarchies, String selectedHierarchies, com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimension externalDimension) {
        List<DimensionElementNodeDTO> children = new ArrayList<DimensionElementNodeDTO>();
        String[] selected = selectedHierarchies.split(",");
        for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy externalHierarchy: externalHierarchies) {

            String nodeVisId = externalHierarchy.getEntityRef().getNarrative();
            String nodeId = (String) ((MappingKeyImpl) externalHierarchy.getEntityRef().getPrimaryKey()).get(0);
            for (int i = 0; i < selected.length; i++) {
                if (nodeId.equals(selected[i])) {
                    DimensionElementNodeDTO nodeLazy = new DimensionElementNodeDTO();
                    List<String> companies = new ArrayList<String>();
                    for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany company: externalHierarchy.getFinanceCompanies()) {
                        companies.add(company.getCompanyVisId());
                    }
                    nodeLazy.setCompanies(companies);
                    nodeLazy.setText(nodeVisId);
                    nodeLazy.setId("hierarchyId/" + nodeId);
                    nodeLazy.setChildren(true);
                    nodeLazy.setStateDisabled(true);

                    children.add(nodeLazy);
                }
            }
        }
        List<FinanceDimensionElementGroup> externalFinanceDimensionGroup = externalDimension.getFinanceDimensionElementGroups();
        for (FinanceDimensionElementGroup externalDimensionElements: externalFinanceDimensionGroup) {
            externalDimensionElements.getFinanceDimensionElementGroups();
            String nodeVisId = externalDimensionElements.getEntityRef().getNarrative();
            String nodeId = (String) ((MappingKeyImpl) externalDimensionElements.getEntityRef().getPrimaryKey()).get(0);
            DimensionElementNodeDTO nodeLazy = new DimensionElementNodeDTO();
            List<String> companies = new ArrayList<String>();
            for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany company: externalDimensionElements.getFinanceCompanies()) {
                companies.add(company.getCompanyVisId());
            }
            nodeLazy.setCompanies(companies);
            nodeLazy.setText(nodeVisId);
            nodeLazy.setId("groupId/" + nodeId);
            nodeLazy.setChildren(true);
            nodeLazy.setStateDisabled(true);
            children.add(nodeLazy);
        }

        return children;
    }

    public static List<DimensionElementNodeDTO> mapHierarchyChildren(List<com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy> externalHierarchies, String selectedHierarchies, com.cedar.cp.api.model.mapping.extsys.FinanceDimension externalDimension) {
        List<DimensionElementNodeDTO> children = new ArrayList<DimensionElementNodeDTO>();
        String[] selected = selectedHierarchies.split(",");
        for (com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy externalHierarchy: externalHierarchies) {

            String nodeVisId = externalHierarchy.getEntityRef().getNarrative();
            String nodeId = (String) ((MappingKeyImpl) externalHierarchy.getEntityRef().getPrimaryKey()).get(0);
            for (int i = 0; i < selected.length; i++) {
                if (nodeId.equals(selected[i])) {
                    DimensionElementNodeDTO nodeLazy = new DimensionElementNodeDTO();
                    nodeLazy.setText(nodeVisId);
                    nodeLazy.setId("hierarchyId/" + nodeId);
                    nodeLazy.setChildren(true);
                    nodeLazy.setStateDisabled(true);

                    children.add(nodeLazy);
                }
            }
        }
        List<com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup> externalFinanceDimensionGroup = externalDimension.getFinanceDimensionElementGroups();
        for (com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup externalDimensionElements: externalFinanceDimensionGroup) {
            externalDimensionElements.getFinanceDimensionElementGroups();
            String nodeVisId = externalDimensionElements.getEntityRef().getNarrative();
            String nodeId = (String) ((MappingKeyImpl) externalDimensionElements.getEntityRef().getPrimaryKey()).get(0);
            DimensionElementNodeDTO nodeLazy = new DimensionElementNodeDTO();
            nodeLazy.setText(nodeVisId);
            nodeLazy.setId("groupId/" + nodeId);
            nodeLazy.setChildren(true);
            nodeLazy.setStateDisabled(true);
            children.add(nodeLazy);
        }

        return children;
    }

    public static List<DimensionElementNodeDTO> mapGroupChildren(Enumeration children, String groupId) {
        List<DimensionElementNodeDTO> mappedChildren = new ArrayList<DimensionElementNodeDTO>();

        while (children.hasMoreElements()) {
            ExtSysTreeNode child = (ExtSysTreeNode) children.nextElement();
            Object childObject = child.getUserObject();
            // global
            if (childObject instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement) {
                MappingKeyImpl mappingKeyImpl = (MappingKeyImpl) ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement) childObject).getEntityRef().getPrimaryKey();

                String visId1 = (String) mappingKeyImpl.get(0);
                String visId2 = (mappingKeyImpl.get(1) != null) ? (String) mappingKeyImpl.get(1) : null;
                String visId3 = (mappingKeyImpl.get(2) != null) ? (String) mappingKeyImpl.get(2) : null;

                DimensionElementNodeDTO nodeLazy = new DimensionElementNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement) childObject).getEntityRef().getNarrative());
                nodeLazy.setId(groupId + "," + visId1);
                List<String> companies = new ArrayList<String>();
                for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany company: ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElement) childObject).getFinanceCompanies()) {
                    companies.add(company.getCompanyVisId());
                }
                nodeLazy.setCompanies(companies);
                nodeLazy.setChildren(false);
                nodeLazy.setStateLeaf(true);
                nodeLazy.setMapping("Specific element: " + visId1);
                nodeLazy.setMappingType(0);
                nodeLazy.setVisId1(visId1);
                nodeLazy.setVisId2(visId2);
                nodeLazy.setVisId3(visId3);
                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup) {
                MappingKeyImpl mappingKeyImpl = (MappingKeyImpl) ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup) childObject).getEntityRef().getPrimaryKey();

                String visId1 = (String) mappingKeyImpl.get(0);
                String visId2 = (mappingKeyImpl.get(1) != null) ? (String) mappingKeyImpl.get(1) : null;
                String visId3 = (mappingKeyImpl.get(2) != null) ? (String) mappingKeyImpl.get(2) : null;

                DimensionElementNodeDTO nodeLazy = new DimensionElementNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup) childObject).getEntityRef().getNarrative());
                List<String> companies = new ArrayList<String>();
                for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany company: ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceDimensionElementGroup) childObject).getFinanceCompanies()) {
                    companies.add(company.getCompanyVisId());
                }
                if (visId1.trim().equals("Prefixes") || visId1.trim().equals("All")) {
                    nodeLazy.setStateDisabled(true);
                }
                nodeLazy.setCompanies(companies);
                nodeLazy.setId(groupId + "," + visId1);
                nodeLazy.setChildren(true);
                nodeLazy.setMapping("Prefix: " + visId1);
                nodeLazy.setMappingType(1);
                nodeLazy.setVisId1(visId1);
                nodeLazy.setVisId2(visId2);
                nodeLazy.setVisId3(visId3);
                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy) {
                MappingKeyImpl mappingKeyImpl = (MappingKeyImpl) ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy) childObject).getEntityRef().getPrimaryKey();

                String visId1 = (String) mappingKeyImpl.get(0);
                String visId2 = (mappingKeyImpl.get(1) != null) ? (String) mappingKeyImpl.get(1) : null;
                String visId3 = (mappingKeyImpl.get(2) != null) ? (String) mappingKeyImpl.get(2) : null;

                DimensionElementNodeDTO nodeLazy = new DimensionElementNodeDTO();
                List<String> companies = new ArrayList<String>();
                for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany company: ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy) childObject).getFinanceCompanies()) {
                    companies.add(company.getCompanyVisId());
                }
                nodeLazy.setCompanies(companies);
                nodeLazy.setText(((com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchy) childObject).getEntityRef().getNarrative());
                nodeLazy.setId(groupId + "," + visId1);
                nodeLazy.setChildren(true);
                nodeLazy.setMappingType(2);
                nodeLazy.setVisId1(visId1);
                nodeLazy.setVisId2(visId2);
                nodeLazy.setVisId3(visId3);

                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement) {
                MappingKeyImpl mappingKeyImpl = (MappingKeyImpl) ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement) childObject).getEntityRef().getPrimaryKey();

                String visId1 = (String) mappingKeyImpl.get(0);
                String visId2 = (mappingKeyImpl.get(1) != null) ? (String) mappingKeyImpl.get(1) : null;
                String visId3 = (mappingKeyImpl.get(2) != null) ? (String) mappingKeyImpl.get(2) : null;

                DimensionElementNodeDTO nodeLazy = new DimensionElementNodeDTO();
                List<String> companies = new ArrayList<String>();
                for (com.cedar.cp.api.model.globalmapping2.extsys.FinanceCompany company: ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement) childObject).getFinanceCompanies()) {
                    companies.add(company.getCompanyVisId());
                }
                nodeLazy.setCompanies(companies);
                nodeLazy.setText(((com.cedar.cp.api.model.globalmapping2.extsys.FinanceHierarchyElement) childObject).getEntityRef().getNarrative());
                nodeLazy.setId(groupId + "," + visId1);
                nodeLazy.setMapping("Hierarchy element: " + visId1 + " hierarchy: " + visId2);
                nodeLazy.setMappingType(3);
                nodeLazy.setChildren(true);

                nodeLazy.setVisId1(visId1);
                nodeLazy.setVisId2(visId2);
                nodeLazy.setVisId3(visId3);

                mappedChildren.add(nodeLazy);
            }
            // not global
            else if (childObject instanceof com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement) {
                MappingKeyImpl mappingKeyImpl = (MappingKeyImpl) ((com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement) childObject).getEntityRef().getPrimaryKey();

                String visId1 = (String) mappingKeyImpl.get(0);
                String visId2 = (mappingKeyImpl.get(1) != null) ? (String) mappingKeyImpl.get(1) : null;
                String visId3 = (mappingKeyImpl.get(2) != null) ? (String) mappingKeyImpl.get(2) : null;

                DimensionElementNodeDTO nodeLazy = new DimensionElementNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElement) childObject).getEntityRef().getNarrative());
                nodeLazy.setId(groupId + "," + visId1);
                nodeLazy.setChildren(false);
                nodeLazy.setStateLeaf(true);
                nodeLazy.setMapping("Specific element: " + visId1);
                nodeLazy.setMappingType(0);
                nodeLazy.setVisId1(visId1);
                nodeLazy.setVisId2(visId2);
                nodeLazy.setVisId3(visId3);
                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup) {
                MappingKeyImpl mappingKeyImpl = (MappingKeyImpl) ((com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup) childObject).getEntityRef().getPrimaryKey();

                String visId1 = (String) mappingKeyImpl.get(0);
                String visId2 = (mappingKeyImpl.get(1) != null) ? (String) mappingKeyImpl.get(1) : null;
                String visId3 = (mappingKeyImpl.get(2) != null) ? (String) mappingKeyImpl.get(2) : null;

                DimensionElementNodeDTO nodeLazy = new DimensionElementNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.mapping.extsys.FinanceDimensionElementGroup) childObject).getEntityRef().getNarrative());
                if (visId1.trim().equals("Prefixes") || visId1.trim().equals("All")) {
                    nodeLazy.setStateDisabled(true);
                }
                nodeLazy.setId(groupId + "," + visId1);
                nodeLazy.setChildren(true);
                nodeLazy.setMapping("Prefix: " + visId1);
                nodeLazy.setMappingType(1);
                nodeLazy.setVisId1(visId1);
                nodeLazy.setVisId2(visId2);
                nodeLazy.setVisId3(visId3);
                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy) {
                MappingKeyImpl mappingKeyImpl = (MappingKeyImpl) ((com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy) childObject).getEntityRef().getPrimaryKey();

                String visId1 = (String) mappingKeyImpl.get(0);
                String visId2 = (mappingKeyImpl.get(1) != null) ? (String) mappingKeyImpl.get(1) : null;
                String visId3 = (mappingKeyImpl.get(2) != null) ? (String) mappingKeyImpl.get(2) : null;

                DimensionElementNodeDTO nodeLazy = new DimensionElementNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.mapping.extsys.FinanceHierarchy) childObject).getEntityRef().getNarrative());
                nodeLazy.setId(groupId + "," + visId1);
                nodeLazy.setChildren(true);
                nodeLazy.setMappingType(2);
                nodeLazy.setVisId1(visId1);
                nodeLazy.setVisId2(visId2);
                nodeLazy.setVisId3(visId3);

                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement) {
                MappingKeyImpl mappingKeyImpl = (MappingKeyImpl) ((com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement) childObject).getEntityRef().getPrimaryKey();

                String visId1 = (String) mappingKeyImpl.get(0);
                String visId2 = (mappingKeyImpl.get(1) != null) ? (String) mappingKeyImpl.get(1) : null;
                String visId3 = (mappingKeyImpl.get(2) != null) ? (String) mappingKeyImpl.get(2) : null;

                DimensionElementNodeDTO nodeLazy = new DimensionElementNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.mapping.extsys.FinanceHierarchyElement) childObject).getEntityRef().getNarrative());
                nodeLazy.setId(groupId + "," + visId1);
                nodeLazy.setMapping("Hierarchy element: " + visId1 + " hierarchy: " + visId2);
                nodeLazy.setMappingType(3);
                nodeLazy.setChildren(true);

                nodeLazy.setVisId1(visId1);
                nodeLazy.setVisId2(visId2);
                nodeLazy.setVisId3(visId3);

                mappedChildren.add(nodeLazy);
            }
        }
        return mappedChildren;
    }

    public static List<DataTypesTreeNodeDTO> mapDatatypeRootGlobal(ExtSysDataTypesTreeNode extSysDataTypesTreeNode) {
        List<DataTypesTreeNodeDTO> root = new ArrayList<DataTypesTreeNodeDTO>();
        com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger obj = (com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger) extSysDataTypesTreeNode.getUserObject();
        DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
        String narrative = obj.getEntityRef().getNarrative();
        String rootId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger) obj).getEntityRef().getPrimaryKey()).get(0);
        nodeLazy.setText(narrative);
        nodeLazy.setId("dataTypeLevel1/" + rootId);
        nodeLazy.setChildren(true);
        nodeLazy.setStateDisabled(true);
        root.add(nodeLazy);
        return root;
    }

    public static List<DataTypesTreeNodeDTO> mapDatatypeRoot(ExtSysDataTypesTreeNode extSysDataTypesTreeNode) {
        List<DataTypesTreeNodeDTO> root = new ArrayList<DataTypesTreeNodeDTO>();
        com.cedar.cp.api.model.mapping.extsys.FinanceLedger obj = (com.cedar.cp.api.model.mapping.extsys.FinanceLedger) extSysDataTypesTreeNode.getUserObject();
        DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
        String narrative = obj.getEntityRef().getNarrative();
        String rootId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.mapping.extsys.FinanceLedger) obj).getEntityRef().getPrimaryKey()).get(0);
        nodeLazy.setText(narrative);
        nodeLazy.setId("dataTypeLevel1/" + rootId);
        nodeLazy.setChildren(true);
        nodeLazy.setStateDisabled(true);
        root.add(nodeLazy);
        return root;
    }

    public static List<DataTypesTreeNodeDTO> mapDatatypeChildrenGlobal(Enumeration children, String id) {
        List<DataTypesTreeNodeDTO> mappedChildren = new ArrayList<DataTypesTreeNodeDTO>();
        while (children.hasMoreElements()) {
            ExtSysDataTypesTreeNode child = (ExtSysDataTypesTreeNode) children.nextElement();
            Object childObject = child.getUserObject();
            boolean leaf = child.isLeaf();

            // global
            if (childObject instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType) {
                String elementId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType) childObject).getEntityRef().getPrimaryKey()).get(0);
                DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType) childObject).getEntityRef().getNarrative());
                String valueType = ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType) childObject).getValueTypeVisId();
                nodeLazy.setValueType(valueType);
                nodeLazy.setId(id + "," + elementId);
                nodeLazy.setChildren(!leaf);
                nodeLazy.setStateLeaf(leaf);
                nodeLazy.setStateDisabled(!leaf);
                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner) {
                String groupElementId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner) childObject).getEntityRef().getPrimaryKey()).get(0);
                DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner) childObject).getEntityRef().getNarrative());
                nodeLazy.setId(id + "," + groupElementId);
                nodeLazy.setChildren(!leaf);
                nodeLazy.setStateDisabled(!leaf);
                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup) {
                String groupElementId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup) childObject).getEntityRef().getPrimaryKey()).get(0);
                DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup) childObject).getEntityRef().getNarrative());
                nodeLazy.setId(id + "," + groupElementId);
                nodeLazy.setChildren(!leaf);
                nodeLazy.setStateDisabled(!leaf);
                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency) {
                String groupElementId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency) childObject).getEntityRef().getPrimaryKey()).get(1);
                String currency = ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency) childObject).getCurrency();
                String balanceType = ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency) childObject).getBalanceType();
                String valueType = ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency) childObject).getValueType();
                DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
                nodeLazy.setBalanceType(balanceType);
                nodeLazy.setCurrency(currency);
                nodeLazy.setValueType(valueType);
                nodeLazy.setText(((com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency) childObject).getEntityRef().getNarrative());
                nodeLazy.setId(id + "," + groupElementId);
                nodeLazy.setStateDisabled(!leaf);
                nodeLazy.setChildren(!leaf);
                mappedChildren.add(nodeLazy);
            }
        }

        return mappedChildren;
    }

    public static List<DataTypesTreeNodeDTO> mapDatatypeChildren(Enumeration children, String id) {
        List<DataTypesTreeNodeDTO> mappedChildren = new ArrayList<DataTypesTreeNodeDTO>();
        while (children.hasMoreElements()) {
            ExtSysDataTypesTreeNode child = (ExtSysDataTypesTreeNode) children.nextElement();
            Object childObject = child.getUserObject();
            boolean leaf = child.isLeaf();

            // not global
            if (childObject instanceof com.cedar.cp.api.model.mapping.extsys.FinanceValueType) {
                String elementId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.mapping.extsys.FinanceValueType) childObject).getEntityRef().getPrimaryKey()).get(0);
                DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.mapping.extsys.FinanceValueType) childObject).getEntityRef().getNarrative());
                String valueType = ((com.cedar.cp.api.model.mapping.extsys.FinanceValueType) childObject).getValueTypeVisId();
                nodeLazy.setValueType(valueType);
                nodeLazy.setId(id + "," + elementId);
                nodeLazy.setChildren(!leaf);
                nodeLazy.setStateLeaf(leaf);
                nodeLazy.setStateDisabled(!leaf);
                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner) {
                String groupElementId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner) childObject).getEntityRef().getPrimaryKey()).get(0);
                DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner) childObject).getEntityRef().getNarrative());
                nodeLazy.setId(id + "," + groupElementId);
                nodeLazy.setChildren(!leaf);
                nodeLazy.setStateDisabled(!leaf);
                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup) {
                String groupElementId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup) childObject).getEntityRef().getPrimaryKey()).get(0);
                DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup) childObject).getEntityRef().getNarrative());
                nodeLazy.setId(id + "," + groupElementId);
                nodeLazy.setChildren(!leaf);
                nodeLazy.setStateDisabled(!leaf);
                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.mapping.extsys.FinanceCurrency) {
                String groupElementId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.mapping.extsys.FinanceCurrency) childObject).getEntityRef().getPrimaryKey()).get(1);
                String currency = ((com.cedar.cp.api.model.mapping.extsys.FinanceCurrency) childObject).getCurrency();
                String balanceType = ((com.cedar.cp.api.model.mapping.extsys.FinanceCurrency) childObject).getBalanceType();
                String valueType = ((com.cedar.cp.api.model.mapping.extsys.FinanceCurrency) childObject).getValueType();
                DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
                nodeLazy.setBalanceType(balanceType);
                nodeLazy.setCurrency(currency);
                nodeLazy.setValueType(valueType);
                nodeLazy.setText(((com.cedar.cp.api.model.mapping.extsys.FinanceCurrency) childObject).getEntityRef().getNarrative());
                nodeLazy.setId(id + "," + groupElementId);
                nodeLazy.setStateDisabled(!leaf);
                nodeLazy.setChildren(!leaf);
                mappedChildren.add(nodeLazy);
            }
        }

        return mappedChildren;
    }

    public static List<DataTypesTreeNodeDTO> childrenLevel1(ExtSysDataTypesTreeNode extSysDataTypesTreeNode, String id) {
        List<DataTypesTreeNodeDTO> mappedChildren = new ArrayList<DataTypesTreeNodeDTO>();
        Enumeration children = extSysDataTypesTreeNode.children();
        while (children.hasMoreElements()) {
            ExtSysDataTypesTreeNode el = (ExtSysDataTypesTreeNode) children.nextElement();
            Object childObject = el.getUserObject();
            boolean leaf = el.isLeaf();

            // global
            if (childObject instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType) {
                String elementId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType) childObject).getEntityRef().getPrimaryKey()).get(0);
                DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType) childObject).getEntityRef().getNarrative());
                String ids[] = id.split("/");
                nodeLazy.setId("dataTypes/" + ids[1] + "," + elementId);
                ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType) childObject).getValueTypeVisId();
                ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType) childObject).getFinanceCurrencies();
                ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueType) childObject).getFinanceCurrencyGroups();
                nodeLazy.setChildren(!leaf);
                nodeLazy.setStateDisabled(!leaf);
                nodeLazy.setStateLeaf(leaf);
                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner) {
                String elementId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner) childObject).getEntityRef().getPrimaryKey()).get(0);
                DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner) childObject).getEntityRef().getNarrative());
                ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceValueTypeOwner) childObject).getFinanceValueTypes();
                String ids[] = id.split("/");
                nodeLazy.setId("dataTypes/" + ids[1] + "," + elementId);
                nodeLazy.setChildren(!leaf);
                nodeLazy.setStateDisabled(!leaf);
                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup) {
                String elementId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup) childObject).getEntityRef().getPrimaryKey()).get(0);
                DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup) childObject).getEntityRef().getNarrative());
                ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup) childObject).getFinanceCurrencyGroups();
                ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrencyGroup) childObject).getFinanceCurrencies();
                String ids[] = id.split("/");
                nodeLazy.setId("dataTypes/" + ids[1] + "," + elementId);
                nodeLazy.setChildren(!leaf);
                nodeLazy.setStateDisabled(!leaf);
                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency) {
                DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
                String elementId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency) childObject).getEntityRef().getPrimaryKey()).get(1);
                String currency = ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency) childObject).getCurrency();
                String balanceType = ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency) childObject).getBalanceType();
                String valueType = ((com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency) childObject).getValueType();
                nodeLazy.setBalanceType(balanceType);
                nodeLazy.setCurrency(currency);
                nodeLazy.setValueType(valueType);
                String ids[] = id.split("/");
                nodeLazy.setId("dataTypes/" + ids[1] + "," + elementId);
                nodeLazy.setText(((com.cedar.cp.api.model.globalmapping2.extsys.FinanceCurrency) childObject).getEntityRef().getNarrative());
                nodeLazy.setChildren(!leaf);
                nodeLazy.setStateDisabled(!leaf);
                mappedChildren.add(nodeLazy);
            }
            // not global
            else if (childObject instanceof com.cedar.cp.api.model.mapping.extsys.FinanceValueType) {
                String elementId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.mapping.extsys.FinanceValueType) childObject).getEntityRef().getPrimaryKey()).get(0);
                DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.mapping.extsys.FinanceValueType) childObject).getEntityRef().getNarrative());
                String ids[] = id.split("/");
                nodeLazy.setId("dataTypes/" + ids[1] + "," + elementId);
                ((com.cedar.cp.api.model.mapping.extsys.FinanceValueType) childObject).getValueTypeVisId();
                ((com.cedar.cp.api.model.mapping.extsys.FinanceValueType) childObject).getFinanceCurrencies();
                ((com.cedar.cp.api.model.mapping.extsys.FinanceValueType) childObject).getFinanceCurrencyGroups();
                nodeLazy.setChildren(!leaf);
                nodeLazy.setStateDisabled(!leaf);
                nodeLazy.setStateLeaf(leaf);
                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner) {
                String elementId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner) childObject).getEntityRef().getPrimaryKey()).get(0);
                DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner) childObject).getEntityRef().getNarrative());
                ((com.cedar.cp.api.model.mapping.extsys.FinanceValueTypeOwner) childObject).getFinanceValueTypes();
                String ids[] = id.split("/");
                nodeLazy.setId("dataTypes/" + ids[1] + "," + elementId);
                nodeLazy.setChildren(!leaf);
                nodeLazy.setStateDisabled(!leaf);
                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup) {
                String elementId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup) childObject).getEntityRef().getPrimaryKey()).get(0);
                DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
                nodeLazy.setText(((com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup) childObject).getEntityRef().getNarrative());
                ((com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup) childObject).getFinanceCurrencyGroups();
                ((com.cedar.cp.api.model.mapping.extsys.FinanceCurrencyGroup) childObject).getFinanceCurrencies();
                String ids[] = id.split("/");
                nodeLazy.setId("dataTypes/" + ids[1] + "," + elementId);
                nodeLazy.setChildren(!leaf);
                nodeLazy.setStateDisabled(!leaf);
                mappedChildren.add(nodeLazy);
            } else if (childObject instanceof com.cedar.cp.api.model.mapping.extsys.FinanceCurrency) {
                DataTypesTreeNodeDTO nodeLazy = new DataTypesTreeNodeDTO();
                String elementId = (String) ((MappingKeyImpl) ((com.cedar.cp.api.model.mapping.extsys.FinanceCurrency) childObject).getEntityRef().getPrimaryKey()).get(1);
                String currency = ((com.cedar.cp.api.model.mapping.extsys.FinanceCurrency) childObject).getCurrency();
                String balanceType = ((com.cedar.cp.api.model.mapping.extsys.FinanceCurrency) childObject).getBalanceType();
                String valueType = ((com.cedar.cp.api.model.mapping.extsys.FinanceCurrency) childObject).getValueType();
                nodeLazy.setBalanceType(balanceType);
                nodeLazy.setCurrency(currency);
                nodeLazy.setValueType(valueType);
                String ids[] = id.split("/");
                nodeLazy.setId("dataTypes/" + ids[1] + "," + elementId);
                nodeLazy.setText(((com.cedar.cp.api.model.mapping.extsys.FinanceCurrency) childObject).getEntityRef().getNarrative());
                nodeLazy.setChildren(!leaf);
                nodeLazy.setStateDisabled(!leaf);
                mappedChildren.add(nodeLazy);
            }

        }
        return mappedChildren;
    }

    public static List<DataTypeDetailsDTO> mapDataTypesForImplExp(DataTypesForImpExpELO dataTypesForImplExp) {
        List<DataTypeDetailsDTO> list = new ArrayList<DataTypeDetailsDTO>();
        for (Iterator<DataTypesForImpExpELO> it = dataTypesForImplExp.iterator(); it.hasNext();) {
            DataTypesForImpExpELO row = it.next();
            DataTypeDetailsDTO dt = new DataTypeDetailsDTO();
            dt.setAvailableForExport(row.getAvailableForExport());
            dt.setAvailableForImport(row.getAvailableForImport());
            dt.setDataTypeVisId(row.getVisId());
            dt.setDataTypeId(row.getDataTypeId());
            dt.setFormulaExpr(row.getFormulaExpr());
            list.add(dt);
        }
        return list;
    }

    public static GlobalMappedModel2Impl mapNewMappedModelDetailsDTO(MappedModelDetailsDTO mappedModelDetailsDTO, GlobalMappedModel2Impl mappedModelImpl, com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger externalLedger) {
        // Start mapMappedModelDetailsDTO
        Integer counter = -1;

        // ModelId
        mappedModelImpl.setModelId(counter--);

        // Version
        mappedModelImpl.setVersionNum(1);

        // ModelVisId
        String mappedModelVisId = mappedModelDetailsDTO.getMappedModelVisId();
        mappedModelImpl.setModelVisId(mappedModelVisId);

        // ModelDescription
        String mappedModelDescription = mappedModelDetailsDTO.getMappedModelDescription();
        mappedModelImpl.setModelDescription(mappedModelDescription);

        // CompaniesVisId and CompanyVisId
        List<String> mappedCompaniesVisId = mappedModelDetailsDTO.getListCompanies();
        mappedModelImpl.setCompaniesVisId(mappedCompaniesVisId);
        String mappedCompany = mappedModelDetailsDTO.getCompanies();
        mappedModelImpl.setCompanyVisId(mappedCompany);

        // LedgerVisId
        String mappedLedgerVisId = mappedModelDetailsDTO.getLedgerVisId();
        mappedModelImpl.setLedgerVisId(mappedLedgerVisId);

        // ExternalSystemRef
        ExternalSystemCoreDTO externalSystemCoreDTO = mappedModelDetailsDTO.getExternalSystem();
        // externalSystemId
        Integer externalSystemId = externalSystemCoreDTO.getExternalSystemId();
        ExternalSystemPK externalSystemPK = new ExternalSystemPK(externalSystemId);
        // externalSystemVisId
        String externalSystemVisId = externalSystemCoreDTO.getExternalSystemVisId();
        // externalSystemRef
        ExternalSystemRefImpl externalSystemRefImpl = new ExternalSystemRefImpl(externalSystemPK, externalSystemVisId);
        mappedModelImpl.setExternalSystemRef(externalSystemRefImpl);

        // /////////////////////////////////////////////////////////////
        // MappedDimmensions
        // /////////////////////////////////////////////////////////////

        List<MappedDimensionDTO> mappedDimensionsDTO = mappedModelDetailsDTO.getMappedDimensions();
        for (MappedDimensionDTO mappedDimensionDTO: mappedDimensionsDTO) {
            // Create MappedDimensionImpl
            MappedDimensionPK mappedDimensionPK = new MappedDimensionPK(counter--);
            MappedDimensionImpl mappedDimensionImpl = new MappedDimensionImpl(mappedDimensionPK, mappedModelImpl);

            // pathVisId
            String pathVisId = mappedDimensionDTO.getPathVisId();
            mappedDimensionImpl.setPathVisId(pathVisId);

            // dimensionType
            int dimensionType = mappedDimensionDTO.getType();
            mappedDimensionImpl.setDimensionType(dimensionType);

            // dimensionVisId
            String dimensionVisId = mappedDimensionDTO.getDimensionVisId();
            mappedDimensionImpl.setDimensionVisId(dimensionVisId);

            // dimensionDescription
            String dimensionDescription = mappedDimensionDTO.getDimensionDescription();
            mappedDimensionImpl.setDimensionDescription(dimensionDescription);

            // dimensionRef
            DimensionRef dimensionRef = null; // ok
            mappedDimensionImpl.setDimensionRef(dimensionRef);

            // mappedHierarchies => method mapMappedHierarchiesDTO
            List<MappedHierarchyDTO> mappedHierarchiesDTO = mappedDimensionDTO.getMappedHierchies();
            List<MappedHierarchy> mappedHierarchies = mapMappedHierarchiesDTO(mappedHierarchiesDTO, counter);
            mappedDimensionImpl.setMappedHierarchies(mappedHierarchies);

            // mappedDimensionElements => method mapMappedDimensionElementsDTO
            List<MappedDimensionElementDTO> mappedDimensionElementsDTO = mappedDimensionDTO.getMappedDimensionElements();
            SortedSet<MappedDimensionElement> mappedDimensionElements = mapMappedDimensionElementsDTO(mappedDimensionElementsDTO, counter);
            mappedDimensionImpl.setMappedDimensionElements(mappedDimensionElements);

            // nullDimensionElementVisId
            String nullDimensionElementVisId = null; // ok
            mappedDimensionImpl.setNullDimensionElementVisId(nullDimensionElementVisId);

            // nullDimensionElementDescription
            String nullDimensionElementDescription = null; // ok
            mappedDimensionImpl.setNullDimensionElementDescription(nullDimensionElementDescription);

            // nullDimensionElementCreditDebit
            Integer nullDimensionElementCreditDebit = null; // ok
            mappedDimensionImpl.setNullDimensionElementCreditDebit(nullDimensionElementCreditDebit);

            // disabledLeafNodesExcluded
            boolean disabledLeafNodesExcluded = false;
            mappedDimensionImpl.setDisabledLeafNodesExcluded(disabledLeafNodesExcluded);

            // add mappedDimensionImpl
            mappedModelImpl.getDimensionMappings().add(mappedDimensionImpl);
        }

        // /////////////////////////////////////////////////////////////
        // MappedCalendar
        // /////////////////////////////////////////////////////////////

        MappedCalendarDTO mappedCalendarDTO = mappedModelDetailsDTO.getMappedCalendar();
        List<YearDTO> yearsDTO = mappedCalendarDTO.getYears();

        // CalendarImpl!
        CalendarImpl calendarImpl = new CalendarImpl();
        List<CalendarYearSpec> years = new ArrayList<CalendarYearSpec>();
        for (YearDTO yearDTO: yearsDTO) {
            boolean[] spec = yearDTO.getSpec();
            int yearSpec = Integer.parseInt(yearDTO.getYearVisId()); // aktualny rok wybrany przez użytkownika
            CalendarYearSpecImpl calendarYearSpec = new CalendarYearSpecImpl(counter--, yearSpec, spec);
            years.add(calendarYearSpec);

            // year models
            CalendarHierarchyElementImpl node = new CalendarHierarchyElementImpl(new HierarchyElementPK(counter--));
            node.setVisId(String.valueOf(yearSpec));
            node.setDescription(String.valueOf(yearSpec));
            node.setCalElemType(0);
            HierarchyElementImpl year = node;
            calendarImpl.getYearModels().add(new DefaultTreeModel(year));
        }
        calendarImpl.setYears(years);
        calendarImpl.setDescription(mappedModelDetailsDTO.getMappedCalendar().getCalendarDescription());
        calendarImpl.setVisId(mappedModelDetailsDTO.getMappedCalendar().getCalendarVisId());

        // mappedCalendarYears
        List<MappedCalendarYear> mappedCalendarYears = new ArrayList<MappedCalendarYear>();
        List<FinanceCalendarYear> calendarYear = externalLedger.getFinanceCalendarYears();
        for (YearDTO year: yearsDTO) {
            boolean[] spec = year.getSpec();
            Boolean selectedYear = spec[0];
            Boolean selectedMonth = spec[3];
            Boolean selectedOpeningBalance = spec[6];

            int yearMappedCalendarYear = Integer.parseInt(year.getYearVisId());
            // rok wybrany przez użytkownika
            for (FinanceCalendarYear openAccYear: calendarYear) {
                if (openAccYear.getYear() == yearMappedCalendarYear) { // sprawdzamy czy rok jest w openAcc

                    String[] arraysCompanies = new String[openAccYear.getFinanceCompanies().size()];
                    for (int i = 0; i < openAccYear.getFinanceCompanies().size(); i++) {
                        // zbieramy companies z open acc dla okreslonego roku
                        arraysCompanies[i] = (String) ((MappingKeyImpl) openAccYear.getFinanceCompanies().get(i).getEntityRef().getPrimaryKey()).get(0);
                    }

                    // List mappedCalendarElements
                    int j = 0;
                    List<MappedCalendarElement> mappedCalendarElements = new ArrayList<MappedCalendarElement>();
                    for (int i = 0; i < openAccYear.getFinancePeriods().size(); i++) {

                        FinancePeriod financePeriod = openAccYear.getFinancePeriods().get(i);
                        MappedCalendarElementImpl mappedCalendarElementImpl = new MappedCalendarElementImpl();

                        // mappedCalendarElementPK
                        MappedCalendarElementPK mappedCalendarElementPK = new MappedCalendarElementPK(counter--);
                        mappedCalendarElementImpl.setKey(mappedCalendarElementPK);

                        // period
                        int period = financePeriod.getPeriod();
                        mappedCalendarElementImpl.setPeriod(period);

                        // entityRefImpl
                        if ((i == 0 && selectedOpeningBalance) // opening balance
                                || (i > 0 && i < 13 && selectedMonth) // months
                                || (i == 1 && selectedYear && !selectedOpeningBalance && !selectedMonth) // only year
                        ) {
                            String narrative = (String) financePeriod.getEntityRef().getNarrative();
                            CalendarLeafElementKey calendarLeafElementKey = new CalendarLeafElementKey(yearMappedCalendarYear, j);
                            EntityRefImpl entityRefImpl = new EntityRefImpl(calendarLeafElementKey, narrative);
                            mappedCalendarElementImpl.setCalendarElementRef(entityRefImpl);
                            j++;
                        } else {
                            mappedCalendarElementImpl.setCalendarElementRef(null);
                        }

                        mappedCalendarElements.add(mappedCalendarElementImpl); // dodajemy do listy
                    }
                    String companiesString = mapToCompaniesString(arraysCompanies); // zebrane companies zamieniamy na string
                    MappedCalendarYearPK mappedCalendarYearPK = new MappedCalendarYearPK(-yearMappedCalendarYear); // rok na minusie
                    MappedCalendarYearImpl mappedCalendarYearImpl = new MappedCalendarYearImpl(mappedCalendarYearPK, yearMappedCalendarYear, mappedCalendarElements, companiesString);
                    mappedCalendarYears.add(mappedCalendarYearImpl);
                }
            }
        }

        MappedCalendarImpl mappedCalendarImpl = new MappedCalendarImpl(calendarImpl, mappedCalendarYears);
        mappedModelImpl.setMappedCalendar(mappedCalendarImpl);

        // /////////////////////////////////////////////////////////////
        // mappedFinanceCube
        // /////////////////////////////////////////////////////////////
        List<MappedFinanceCube> mappedFinanceCubes = new ArrayList<MappedFinanceCube>();
        List<MappedFinanceCubeDTO> mappedFinanceCubesDTO = mappedModelDetailsDTO.getMappedFinanceCubes();

        for (MappedFinanceCubeDTO financeCubeDTO: mappedFinanceCubesDTO) {
            MappedFinanceCubeImpl mappedFinanceCubeImpl = new MappedFinanceCubeImpl(mappedModelImpl);

            // mappedFinanceCubePK
            MappedFinanceCubePK mappedFinanceCubePK = new MappedFinanceCubePK(counter--);
            mappedFinanceCubeImpl.setKey(mappedFinanceCubePK);

            // mappedFinanceCubeName
            String mappedFinanceCubeVisId = financeCubeDTO.getFinanceCubeVisId();
            mappedFinanceCubeImpl.setName(mappedFinanceCubeVisId);

            // mappedFinanceCubeDescription
            String mappedFinanceCubeDescription = financeCubeDTO.getFinanceCubeDescription();
            mappedFinanceCubeImpl.setDescription(mappedFinanceCubeDescription);

            // mappedDataTypes
            List<MappedDataType> mappedDataTypes = new ArrayList<MappedDataType>();

            List<MappedDataTypeDTO> mappedDataTypesDTO = financeCubeDTO.getMappedDataTypes();
            for (MappedDataTypeDTO mappedDataTypeDTO: mappedDataTypesDTO) {
                MappedDataTypeImpl mappedDataType = new MappedDataTypeImpl(mappedFinanceCubeImpl);

                // mappedDataTypePK
                MappedDataTypePK mappedDataTypePK = new MappedDataTypePK(counter--);
                mappedDataType.setKey(mappedDataTypePK);

                // mappedDataTypeBalType
                String mappedDataTypeBalType = mappedDataTypeDTO.getBalType();
                mappedDataType.setExtSysBalType(mappedDataTypeBalType);

                // mappedDataTypeCurrency
                String mappedDataTypeCurrency = mappedDataTypeDTO.getCurrency();
                mappedDataType.setExtSysCurrency(mappedDataTypeCurrency);

                // dataTypeRef
                int mappedDataTypeId = mappedDataTypeDTO.getDataTypeId();
                DataTypePK dataTypePk = new DataTypePK((short) mappedDataTypeId);
                DataTypeRef dataTypeRef = new DataTypeRefImpl(dataTypePk, mappedDataTypeDTO.getDataTypeVisId(), "", 0, 0, 0);
                mappedDataType.setDataTypeRef(dataTypeRef);

                // mappedDataTypeExpStartYearOffset
                Integer mappedDataTypeExpStartYearOffset = mappedDataTypeDTO.getExpStartYearOffset();
                mappedDataType.setExpStartYearOffset(mappedDataTypeExpStartYearOffset);

                // mappedDataTypeExpEndYearOffset
                Integer mappedDataTypeExpEndYearOffset = mappedDataTypeDTO.getExpEndYearOffset();
                mappedDataType.setExpEndYearOffset(mappedDataTypeExpEndYearOffset);

                // mappedDataTypeImpStartYearOffset
                Integer mappedDataTypeImpStartYearOffset = mappedDataTypeDTO.getImpStartYearOffset();
                mappedDataType.setImpStartYearOffset(mappedDataTypeImpStartYearOffset);

                // mappedDataTypeImpEndYearOffset
                Integer mappedDataTypeImpEndYearOffset = mappedDataTypeDTO.getImpEndYearOffset();
                mappedDataType.setImpEndYearOffset(mappedDataTypeImpEndYearOffset);

                // mappedDataTypeImpExpStatus
                int mappedDataTypeImpExpStatus = mappedDataTypeDTO.getImpExpStatus();
                mappedDataType.setImpExpStatus(mappedDataTypeImpExpStatus);

                // mappedDataTypeValueType
                String mappedDataTypeValueType = mappedDataTypeDTO.getValueType();
                mappedDataType.setExtSysValueType(mappedDataTypeValueType);

                mappedDataTypes.add(mappedDataType);
            }

            mappedFinanceCubeImpl.setMappedDataTypes(mappedDataTypes);

            mappedFinanceCubes.add(mappedFinanceCubeImpl);
        }

        mappedModelImpl.setMappedFinanceCubes(mappedFinanceCubes);

        return mappedModelImpl;
    }

    private static String mapToCompaniesString(String[] arraysCompanies) {
        String companies = "";
        int i = 0;
        for (String string: arraysCompanies) {
            companies += "\"" + string + "\"";
            if (i != arraysCompanies.length) {
                companies += ",";
            }
            i++;
        }
        return companies;
    }

    public static GlobalMappedModel2Impl mapOldMappedModelDetailsDTO(MappedModelDetailsDTO mappedModelDetailsDTO, GlobalMappedModel2Impl mappedModelImpl, com.cedar.cp.api.model.globalmapping2.extsys.FinanceLedger externalLedger) {
        Integer counter = -1;

        // CompaniesVisId and CompanyVisId
        List<String> mappedCompaniesVisId = mappedModelDetailsDTO.getListCompanies();
        mappedModelImpl.setCompaniesVisId(mappedCompaniesVisId);
        String mappedCompany = mappedModelDetailsDTO.getCompanies();
        mappedModelImpl.setCompanyVisId(mappedCompany);

        // /////////////////////////////////////////////////////////////
        // MappedDimmensions
        // /////////////////////////////////////////////////////////////

        List<MappedDimensionDTO> mappedDimensionsDTO = mappedModelDetailsDTO.getMappedDimensions();
        for (MappedDimensionDTO mappedDimensionDTO: mappedDimensionsDTO) {

            // find mappedDimensionImpl
            MappedDimensionImpl mappedDimensionImpl = null;
            List<MappedDimension> mappedDimensionsImpl = mappedModelImpl.getDimensionMappings();
            for (MappedDimension mappedDimension: mappedDimensionsImpl) {
                if (mappedDimension.getDimensionType() == mappedDimensionDTO.getType()) {
                    mappedDimensionImpl = (MappedDimensionImpl) mappedDimension;
                    break;
                }
            }

            // mappedHierarchies => method mapMappedHierarchiesDTO
            List<MappedHierarchy> mappedOldHierarchies = mappedDimensionImpl.getMappedHierarchies();
            List<MappedHierarchyDTO> mappedHierarchiesDTO = mappedDimensionDTO.getMappedHierchies();
            List<MappedHierarchy> mappedHierarchies = mapMappedHierarchiesDTO(mappedOldHierarchies, mappedHierarchiesDTO, counter);
            mappedDimensionImpl.setMappedHierarchies(mappedHierarchies);

            // mappedDimensionElements => method mapMappedDimensionElementsDTO
            SortedSet<MappedDimensionElement> mappedOldDimensionElements = mappedDimensionImpl.getMappedDimensionElements();
            List<MappedDimensionElementDTO> mappedDimensionElementsDTO = mappedDimensionDTO.getMappedDimensionElements();
            SortedSet<MappedDimensionElement> mappedDimensionElements = mapMappedDimensionElementsDTO(mappedOldDimensionElements, mappedDimensionElementsDTO, counter);
            mappedDimensionImpl.setMappedDimensionElements(mappedDimensionElements);
        }

        // /////////////////////////////////////////////////////////////
        // MappedCalendar
        // /////////////////////////////////////////////////////////////
        
        boolean periodChanged = false;
        
        // MappedCalendarDTO
        MappedCalendarDTO mappedCalendarDTO = mappedModelDetailsDTO.getMappedCalendar();
        List<YearDTO> yearsDTO = mappedCalendarDTO.getYears();

        // CalendarImpl
        MappedCalendar mappedCalendarImpl = mappedModelImpl.getMappedCalendar();
        CalendarImpl calendarImpl = (CalendarImpl) mappedCalendarImpl.getCalendar();

        // kasowanie lat (calendar year spec)
        List<CalendarYearSpec> calendarYearsSpec = calendarImpl.getYears();
        List<CalendarYearSpec> calendarYearsSpecToDelete = new ArrayList<CalendarYearSpec>();
        impl: for (CalendarYearSpec calendarYearSpec: calendarYearsSpec) {
            for (YearDTO yearDTO: yearsDTO) {
                int year = Integer.parseInt(yearDTO.getYearVisId());
                if (calendarYearSpec.getYear() == year) {
                    continue impl;
                }
            }
            calendarYearsSpecToDelete.add(calendarYearSpec);
            // jesli jakis rok usuniety, to change management update reuired na true
            setChangeManagementUpdateRequired(calendarImpl);
        }
        calendarYearsSpec.removeAll(calendarYearsSpecToDelete);

        // kasowanie lat (mapped calendar years)
        List<MappedCalendarYear> mappedCalendarYears = mappedCalendarImpl.getMappedCalendarYears();
        List<MappedCalendarYear> mappedCalendarYearsToDelete = new ArrayList<MappedCalendarYear>();
        impl: for (MappedCalendarYear mappedCalendarYear: mappedCalendarYears) {
            for (YearDTO yearDTO: yearsDTO) {
                int year = Integer.parseInt(yearDTO.getYearVisId());
                if (mappedCalendarYear.getYear() == year) {
                    continue impl;
                }
            }
            mappedCalendarYearsToDelete.add(mappedCalendarYear);
            setChangeManagementUpdateRequired(calendarImpl);
        }
        mappedCalendarYears.removeAll(mappedCalendarYearsToDelete);

        // dodawanie/aktualizacja lat (calendar year spec)
        int indexToInsert = 0;
        dto: for (YearDTO yearDTO: yearsDTO) {
            boolean[] spec = yearDTO.getSpec();
            for (CalendarYearSpec calendarYearSpec: calendarYearsSpec) {
                int year = Integer.parseInt(yearDTO.getYearVisId());
                if (calendarYearSpec.getYear() == year) {
                    // aktualizuj (calendar year spec)
                    CalendarYearSpecImpl impl = (CalendarYearSpecImpl) calendarYearSpec;
                    periodChanged = (!java.util.Arrays.equals(impl.getSpec(), spec) && (spec[0] != false || spec[1] != false || spec[2] != false || spec[3] != false || spec[4] != false || spec[5] != false || spec[6] != false || spec[7] != false || spec[8] != false || spec[9] != false));
                    if (periodChanged) {
                        impl.setSpec(spec);
                        setChangeManagementUpdateRequired(calendarImpl);
                    }
                    continue dto;
                }
            }

            // dodaj (calendar year spec)
            int yearSpec = Integer.parseInt(yearDTO.getYearVisId());
            CalendarYearSpecImpl calendarYearSpec = new CalendarYearSpecImpl(counter--, yearSpec, spec);
            if (calendarYearSpec.getYear() < calendarYearsSpec.get((calendarYearsSpec.size() / 2)).getYear()) {
                calendarYearsSpec.add(indexToInsert, calendarYearSpec);
            } else {
                calendarYearsSpec.add(calendarYearSpec);
            }

            // year models
            CalendarHierarchyElementImpl node = new CalendarHierarchyElementImpl(new HierarchyElementPK(counter--));
            node.setVisId(yearDTO.getYearVisId());
            node.setDescription(yearDTO.getYearVisId());
            node.setCalElemType(0);
            HierarchyElementImpl year = node;

            List<TreeModel> tmpYearModels = calendarImpl.getYearModels();

            if (Integer.parseInt(year.getVisId()) < Integer.parseInt(((CalendarHierarchyElementImpl) tmpYearModels.get((tmpYearModels.size() / 2)).getRoot()).getVisId())) {
                calendarImpl.getYearModels().add(indexToInsert, new DefaultTreeModel(year));
            } else {
                calendarImpl.getYearModels().add(new DefaultTreeModel(year));
            }

            setChangeManagementUpdateRequired(calendarImpl);
            indexToInsert++;
        }

        List<FinanceCalendarYear> financeCalendarYears = externalLedger.getFinanceCalendarYears();

        // udodawanie/aktualizacja lat (mapped calendar years)
        int indexToInstertMappedCallendarYearImpl = 0;
        dto: for (YearDTO year: yearsDTO) {
            boolean[] spec = year.getSpec();
            Boolean selectedYear = spec[0];
            Boolean selectedMonth = spec[3];
            Boolean selectedOpeningBalance = spec[6];

            int yearMappedCalendarYear = Integer.parseInt(year.getYearVisId());
            for (FinanceCalendarYear openAccYear: financeCalendarYears) {
                if (openAccYear.getYear() == yearMappedCalendarYear) { // sprawdzamy czy rok jest w openAcc
                    int j = 0;
                    int k = 0;

                    for (MappedCalendarYear mappedCalendarYear: mappedCalendarYears) {
                        int yearNumber = Integer.parseInt(year.getYearVisId());
                        if (mappedCalendarYear.getYear() == yearNumber) {
                            // aktualizuj (mapped calendar years)
                            
                            // nadpisuj wszystkie elementy jesli zmienily sie periody
                            if (periodChanged) {
                                // List mappedCalendarElements
                                List<MappedCalendarElement> mappedCalendarElements = new ArrayList<MappedCalendarElement>();
                                for (int i = 0; i < openAccYear.getFinancePeriods().size(); i++) {
    
                                    FinancePeriod financePeriod = openAccYear.getFinancePeriods().get(i);
                                    MappedCalendarElementImpl mappedCalendarElementImpl = new MappedCalendarElementImpl();
    
                                    // mappedCalendarElementPK
                                    MappedCalendarElementPK mappedCalendarElementPK = new MappedCalendarElementPK(counter--);
                                    mappedCalendarElementImpl.setKey(mappedCalendarElementPK);
    
                                    // period
                                    int period = financePeriod.getPeriod();
                                    mappedCalendarElementImpl.setPeriod(period);
    
                                    // entityRefImpl
                                    if ((i == 0 && selectedOpeningBalance) // opening balance
                                            || (i > 0 && i < 13 && selectedMonth) // months
                                            || (i == 1 && selectedYear && !selectedOpeningBalance && !selectedMonth) // only year
                                    ) {
                                        String narrative = (String) financePeriod.getEntityRef().getNarrative();
                                        CalendarLeafElementKey calendarLeafElementKey = new CalendarLeafElementKey(yearMappedCalendarYear, k);
                                        EntityRefImpl entityRefImpl = new EntityRefImpl(calendarLeafElementKey, narrative);
                                        mappedCalendarElementImpl.setCalendarElementRef(entityRefImpl);
                                        k++;
                                    } else {
                                        mappedCalendarElementImpl.setCalendarElementRef(null);
                                    }
    
                                    mappedCalendarElements.add(mappedCalendarElementImpl); // dodajemy do listy
                                }
                                
                                ((MappedCalendarYearImpl)mappedCalendarYear).setMappedCalendarElements(mappedCalendarElements);
                            }
                            continue dto;
                        }
                    }

                    // dodaj (mapped calendar years)

                    // List mappedCalendarElements
                    List<MappedCalendarElement> mappedCalendarElements = new ArrayList<MappedCalendarElement>();
                    for (int i = 0; i < openAccYear.getFinancePeriods().size(); i++) {

                        FinancePeriod financePeriod = openAccYear.getFinancePeriods().get(i);
                        MappedCalendarElementImpl mappedCalendarElementImpl = new MappedCalendarElementImpl();

                        // mappedCalendarElementPK
                        MappedCalendarElementPK mappedCalendarElementPK = new MappedCalendarElementPK(counter--);
                        mappedCalendarElementImpl.setKey(mappedCalendarElementPK);

                        // period
                        int period = financePeriod.getPeriod();
                        mappedCalendarElementImpl.setPeriod(period);

                        // entityRefImpl
                        if ((i == 0 && selectedOpeningBalance) // opening balance
                                || (i > 0 && i < 13 && selectedMonth) // months
                                || (i == 1 && selectedYear && !selectedOpeningBalance && !selectedMonth) // only year
                        ) {
                            String narrative = (String) financePeriod.getEntityRef().getNarrative();
                            CalendarLeafElementKey calendarLeafElementKey = new CalendarLeafElementKey(yearMappedCalendarYear, j);
                            EntityRefImpl entityRefImpl = new EntityRefImpl(calendarLeafElementKey, narrative);
                            mappedCalendarElementImpl.setCalendarElementRef(entityRefImpl);
                            j++;
                        } else {
                            mappedCalendarElementImpl.setCalendarElementRef(null);
                        }

                        mappedCalendarElements.add(mappedCalendarElementImpl); // dodajemy do listy
                    }
                    MappedCalendarYearPK mappedCalendarYearPK = new MappedCalendarYearPK(-yearMappedCalendarYear); // rok na minusie
                    MappedCalendarYearImpl mappedCalendarYearImpl = new MappedCalendarYearImpl(mappedCalendarYearPK, yearMappedCalendarYear, mappedCalendarElements);

                    if (mappedCalendarYearImpl.getYear() < mappedCalendarYears.get((mappedCalendarYears.size() / 2)).getYear()) {
                        mappedCalendarYears.add(indexToInstertMappedCallendarYearImpl, mappedCalendarYearImpl);
                    } else {
                        mappedCalendarYears.add(mappedCalendarYearImpl);
                    }
                    setChangeManagementUpdateRequired(calendarImpl);
                    indexToInstertMappedCallendarYearImpl++;
                }
            }
        }

        // /////////////////////////////////////////////////////////////
        // mappedFinanceCube
        // /////////////////////////////////////////////////////////////
        List<MappedFinanceCube> mappedFinanceCubes = mappedModelImpl.getMappedFinanceCubes();
        List<MappedFinanceCubeDTO> mappedFinanceCubesDTO = mappedModelDetailsDTO.getMappedFinanceCubes();

        for (MappedFinanceCube mappedFinanceCube: mappedFinanceCubes) {
            MappedFinanceCubeImpl mappedFinanceCubeImpl = (MappedFinanceCubeImpl) mappedFinanceCube;
            for (MappedFinanceCubeDTO mappedFinanceCubeDTO: mappedFinanceCubesDTO) {
                int idDTO = mappedFinanceCubeDTO.getFinanceCubeId();
                int idDB = ((MappedFinanceCubePK) mappedFinanceCube.getKey()).getMappedFinanceCubeId();
                if (idDTO == idDB) {
                    List<MappedDataType> oldMappedDataTypes = mappedFinanceCube.getMappedDataTypes();
                    List<MappedDataTypeDTO> mappedDataTypesDTO = mappedFinanceCubeDTO.getMappedDataTypes();
                    mapDataTypes(oldMappedDataTypes, mappedDataTypesDTO, mappedFinanceCubeImpl, counter);
                } else if (idDTO == -1) {
                    System.out.println("create finance cube??");
                }
            }
        }
        return mappedModelImpl;
    }

    private static void setChangeManagementUpdateRequired(CalendarImpl calendarImpl) {
        if (!calendarImpl.isNew() && calendarImpl.getModel() != null) {
            calendarImpl.setChangeManagementUpdateRequired(true);
        }
    }

    private static void mapDataTypes(List<MappedDataType> mappedDataTypes, List<MappedDataTypeDTO> mappedDataTypesDTO, MappedFinanceCubeImpl mappedFinanceCubeImpl, Integer counter) {
        List<MappedDataType> mappedDataTypeToDelete = new ArrayList<MappedDataType>();
        impl: for (MappedDataType mappedDataType: mappedDataTypes) {
            for (MappedDataTypeDTO mappedDataTypeDTO: mappedDataTypesDTO) {
                short dataTypeId = ((DataTypePK) mappedDataType.getDataTypeRef().getPrimaryKey()).getDataTypeId();
                if (dataTypeId == mappedDataTypeDTO.getDataTypeId()) {
                    continue impl;
                }
            }
            mappedDataTypeToDelete.add(mappedDataType);
        }
        mappedDataTypes.removeAll(mappedDataTypeToDelete);

        dto: for (MappedDataTypeDTO mappedDataTypeDTO: mappedDataTypesDTO) {
            for (MappedDataType mappedDataType: mappedDataTypes) {
                MappedDataTypeImpl mappedDataTypeImpl = (MappedDataTypeImpl) mappedDataType;
                short dataTypeId = ((DataTypePK) mappedDataType.getDataTypeRef().getPrimaryKey()).getDataTypeId();
                if (dataTypeId == mappedDataTypeDTO.getDataTypeId()) {
                    mappedDataTypeImpl.setExtSysBalType(mappedDataTypeDTO.getBalType());
                    mappedDataTypeImpl.setExtSysCurrency(mappedDataTypeDTO.getCurrency());
                    mappedDataTypeImpl.setExpStartYearOffset(mappedDataTypeDTO.getExpStartYearOffset());
                    mappedDataTypeImpl.setExpEndYearOffset(mappedDataTypeDTO.getExpEndYearOffset());
                    mappedDataTypeImpl.setImpStartYearOffset(mappedDataTypeDTO.getImpStartYearOffset());
                    mappedDataTypeImpl.setImpEndYearOffset(mappedDataTypeDTO.getImpEndYearOffset());
                    mappedDataTypeImpl.setImpExpStatus(mappedDataTypeDTO.getImpExpStatus());
                    mappedDataTypeImpl.setExtSysValueType(mappedDataTypeDTO.getValueType());

                    continue dto;
                }
            }

            MappedDataTypeImpl mappedDataType = new MappedDataTypeImpl(mappedFinanceCubeImpl);

            // mappedDataTypePK
            MappedDataTypePK mappedDataTypePK = new MappedDataTypePK(counter--);
            mappedDataType.setKey(mappedDataTypePK);

            // mappedDataTypeBalType
            String mappedDataTypeBalType = mappedDataTypeDTO.getBalType();
            mappedDataType.setExtSysBalType(mappedDataTypeBalType);

            // mappedDataTypeCurrency
            String mappedDataTypeCurrency = mappedDataTypeDTO.getCurrency();
            mappedDataType.setExtSysCurrency(mappedDataTypeCurrency);

            // dataTypeRef
            int mappedDataTypeId = mappedDataTypeDTO.getDataTypeId();
            DataTypePK dataTypePk = new DataTypePK((short) mappedDataTypeId);
            DataTypeRef dataTypeRef = new DataTypeRefImpl(dataTypePk, mappedDataTypeDTO.getDataTypeVisId(), "", 0, 0, 0);
            mappedDataType.setDataTypeRef(dataTypeRef);

            // mappedDataTypeExpStartYearOffset
            Integer mappedDataTypeExpStartYearOffset = mappedDataTypeDTO.getExpStartYearOffset();
            mappedDataType.setExpStartYearOffset(mappedDataTypeExpStartYearOffset);

            // mappedDataTypeExpEndYearOffset
            Integer mappedDataTypeExpEndYearOffset = mappedDataTypeDTO.getExpEndYearOffset();
            mappedDataType.setExpEndYearOffset(mappedDataTypeExpEndYearOffset);

            // mappedDataTypeImpStartYearOffset
            Integer mappedDataTypeImpStartYearOffset = mappedDataTypeDTO.getImpStartYearOffset();
            mappedDataType.setImpStartYearOffset(mappedDataTypeImpStartYearOffset);

            // mappedDataTypeImpEndYearOffset
            Integer mappedDataTypeImpEndYearOffset = mappedDataTypeDTO.getImpEndYearOffset();
            mappedDataType.setImpEndYearOffset(mappedDataTypeImpEndYearOffset);

            // mappedDataTypeImpExpStatus
            int mappedDataTypeImpExpStatus = mappedDataTypeDTO.getImpExpStatus();
            mappedDataType.setImpExpStatus(mappedDataTypeImpExpStatus);

            // mappedDataTypeValueType
            String mappedDataTypeValueType = mappedDataTypeDTO.getValueType();
            mappedDataType.setExtSysValueType(mappedDataTypeValueType);

            mappedDataTypes.add(mappedDataType);
        }
    }

    /**
     * Used in mapOldMappedModelDetailsDTO.
     * Map list MappedHierarchyDTO to MappedHierarchy.
     */
    private static List<MappedHierarchy> mapMappedHierarchiesDTO(List<MappedHierarchy> mappedHierarchies, List<MappedHierarchyDTO> mappedHierarchiesDTO, Integer counter) {

        dto: for (MappedHierarchyDTO mappedHierarchyDTO: mappedHierarchiesDTO) {

            for (MappedHierarchy mappedOldHierarchy: mappedHierarchies) {
                MappedHierarchyImpl mappedOldHierarchyImpl = (MappedHierarchyImpl) mappedOldHierarchy;
                if (mappedHierarchyDTO.getHierarchyVisId().equals(mappedOldHierarchyImpl.getNewHierarchyVisId())) {

                    // set companies
                    List<String> selectedCompanies = mappedHierarchyDTO.getSelectedCompanies();
                    for (String company: selectedCompanies) {
                        mappedOldHierarchyImpl.setSelectedCompany(company, true);
                    }

                    continue dto;
                }
            }
        }

        return mappedHierarchies;
    }

    /**
     * Used in mapMappedModelDetailsDTO.
     * Map list MappedHierarchyDTO to MappedHierarchy.
     */
    private static List<MappedHierarchy> mapMappedHierarchiesDTO(List<MappedHierarchyDTO> mappedHierarchiesDTO, Integer counter) {
        List<MappedHierarchy> mappedHierarchies = new ArrayList<MappedHierarchy>();

        for (MappedHierarchyDTO mappedHierarchyDTO: mappedHierarchiesDTO) {

            // set properties
            MappedHierarchyPK hierarchyPK = new MappedHierarchyPK(counter--);
            HierarchyRef hierarchyRef = null;
            String hierarchyVisId1 = mappedHierarchyDTO.getVisId1();
            String hierarchyVisId2 = mappedHierarchyDTO.getVisId2();
            String newHierarchyVisId = mappedHierarchyDTO.getHierarchyVisId();
            String newHierarchyDescription = mappedHierarchyDTO.getHierarchyDescription();
            boolean responsibilityAreaHierarchy = mappedHierarchyDTO.isResponsibilityAreaHierarchy();

            // create mappedHierarchyImpl
            MappedHierarchyImpl mappedHierarchyImpl = new MappedHierarchyImpl(hierarchyPK, hierarchyRef, hierarchyVisId1, hierarchyVisId2, newHierarchyVisId, newHierarchyDescription, responsibilityAreaHierarchy);

            // set companies
            List<String> selectedCompanies = mappedHierarchyDTO.getSelectedCompanies();
            for (String company: selectedCompanies) {
                mappedHierarchyImpl.setSelectedCompany(company, true);
            }

            mappedHierarchies.add(mappedHierarchyImpl);
        }

        return mappedHierarchies;
    }

    /**
     * Used in method mapOldMappedModelDetailsDTO.
     * Map list MappedDimensionElementDTO to MappedDimensionElement.
     */
    private static SortedSet<MappedDimensionElement> mapMappedDimensionElementsDTO(SortedSet<MappedDimensionElement> mappedDimensionElements, List<MappedDimensionElementDTO> mappedDimensionElementsDTO, Integer counter) {
        List<MappedDimensionElement> mappedDimensionElementsToDelete = new ArrayList<MappedDimensionElement>();
        impl: for (MappedDimensionElement mappedDimensionElement: mappedDimensionElements) {
            MappedDimensionElementImpl mappedDimensionElementImpl = (MappedDimensionElementImpl) mappedDimensionElement;
            for (MappedDimensionElementDTO mappedDimensionElementDTO: mappedDimensionElementsDTO) {
                if (mappedDimensionElementImpl.getVisId1().equals(mappedDimensionElementDTO.getVisId1()) && ((mappedDimensionElementImpl.getVisId2() == null && mappedDimensionElementDTO.getVisId2() == null) || mappedDimensionElementImpl.getVisId2().equals(mappedDimensionElementDTO.getVisId2())) && ((mappedDimensionElementImpl.getVisId3() == null && mappedDimensionElementDTO.getVisId3() == null) || mappedDimensionElementImpl.getVisId3().equals(mappedDimensionElementDTO.getVisId3()))) {
                    continue impl;
                }
            }
            mappedDimensionElementsToDelete.add(mappedDimensionElement);
        }
        mappedDimensionElements.removeAll(mappedDimensionElementsToDelete);

        dto: for (MappedDimensionElementDTO mappedDimensionElementDTO: mappedDimensionElementsDTO) {

            // if mappedDimensionElement already exists -> update companies
            for (MappedDimensionElement mappedDimensionElement: mappedDimensionElements) {
                MappedDimensionElementImpl mappedDimensionElementImpl = (MappedDimensionElementImpl) mappedDimensionElement;
                if (mappedDimensionElementImpl.getVisId1().equals(mappedDimensionElementDTO.getVisId1()) && ((mappedDimensionElementImpl.getVisId2() == null && mappedDimensionElementDTO.getVisId2() == null) || mappedDimensionElementImpl.getVisId2().equals(mappedDimensionElementDTO.getVisId2())) && ((mappedDimensionElementImpl.getVisId3() == null && mappedDimensionElementDTO.getVisId3() == null) || mappedDimensionElementImpl.getVisId3().equals(mappedDimensionElementDTO.getVisId3()))) {

                    // set companies
                    List<String> selectedCompanies = mappedDimensionElementDTO.getSelectedCompanies();
                    for (String company: selectedCompanies) {
                        mappedDimensionElementImpl.setSelectedCompany(company, true);
                    }

                    continue dto;
                }
            }

            // if mappedDimensionElement doesn`t exist -> create new

            // set properties
            MappedDimensionElementPK mappedDimnensionElementPk = new MappedDimensionElementPK(counter--);
            int mappingType = mappedDimensionElementDTO.getMappingType();
            String visId1 = mappedDimensionElementDTO.getVisId1();
            String visId2 = mappedDimensionElementDTO.getVisId2();
            String visId3 = mappedDimensionElementDTO.getVisId3();

            // create mappedHierarchyImpl
            MappedDimensionElementImpl mappedDimensionElementImpl = new MappedDimensionElementImpl(mappedDimnensionElementPk, mappingType, visId1, visId2, visId3);

            // set companies
            List<String> selectedCompanies = mappedDimensionElementDTO.getSelectedCompanies();
            for (String company: selectedCompanies) {
                mappedDimensionElementImpl.setSelectedCompany(company, true);
            }

            mappedDimensionElements.add(mappedDimensionElementImpl);
        }

        return mappedDimensionElements;
    }

    /**
     * Used in method mapMappedModelDetailsDTO.
     * Map list MappedDimensionElementDTO to MappedDimensionElement.
     */
    private static SortedSet<MappedDimensionElement> mapMappedDimensionElementsDTO(List<MappedDimensionElementDTO> mappedDimensionElementsDTO, Integer counter) {
        SortedSet<MappedDimensionElement> mappedDimensionElements = new TreeSet<MappedDimensionElement>();

        for (MappedDimensionElementDTO mappedDimensionElementDTO: mappedDimensionElementsDTO) {

            // set properties
            MappedDimensionElementPK mappedDimnensionElementPk = new MappedDimensionElementPK(counter--);
            int mappingType = mappedDimensionElementDTO.getMappingType();
            String visId1 = mappedDimensionElementDTO.getVisId1();
            String visId2 = mappedDimensionElementDTO.getVisId2();
            String visId3 = mappedDimensionElementDTO.getVisId3();

            // create mappedHierarchyImpl
            MappedDimensionElementImpl mappedDimensionElementImpl = new MappedDimensionElementImpl(mappedDimnensionElementPk, mappingType, visId1, visId2, visId3);

            // set companies
            List<String> selectedCompanies = mappedDimensionElementDTO.getSelectedCompanies();
            for (String company: selectedCompanies) {
                mappedDimensionElementImpl.setSelectedCompany(company, true);
            }

            mappedDimensionElements.add(mappedDimensionElementImpl);
        }

        return mappedDimensionElements;
    }
}
