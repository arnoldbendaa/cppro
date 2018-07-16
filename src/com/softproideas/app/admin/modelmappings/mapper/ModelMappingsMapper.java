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
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.calendar.CalendarYearSpec;
import com.cedar.cp.api.model.mapping.MappedCalendar;
import com.cedar.cp.api.model.mapping.MappedCalendarElement;
import com.cedar.cp.api.model.mapping.MappedCalendarYear;
import com.cedar.cp.api.model.mapping.MappedDataType;
import com.cedar.cp.api.model.mapping.MappedDimension;
import com.cedar.cp.api.model.mapping.MappedDimensionElement;
import com.cedar.cp.api.model.mapping.MappedFinanceCube;
import com.cedar.cp.api.model.mapping.MappedHierarchy;
import com.cedar.cp.api.model.mapping.extsys.FinanceCalendarYear;
import com.cedar.cp.api.model.mapping.extsys.FinanceLedger;
import com.cedar.cp.api.model.mapping.extsys.FinancePeriod;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.CalendarHierarchyElementImpl;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;
import com.cedar.cp.dto.dimension.HierarchyElementPK;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarLeafElementKey;
import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.cedar.cp.dto.extsys.ExternalSystemRefImpl;
import com.cedar.cp.dto.model.mapping.MappedCalendarElementImpl;
import com.cedar.cp.dto.model.mapping.MappedCalendarElementPK;
import com.cedar.cp.dto.model.mapping.MappedCalendarImpl;
import com.cedar.cp.dto.model.mapping.MappedCalendarYearImpl;
import com.cedar.cp.dto.model.mapping.MappedCalendarYearPK;
import com.cedar.cp.dto.model.mapping.MappedDataTypeImpl;
import com.cedar.cp.dto.model.mapping.MappedDataTypePK;
import com.cedar.cp.dto.model.mapping.MappedDimensionElementImpl;
import com.cedar.cp.dto.model.mapping.MappedDimensionElementPK;
import com.cedar.cp.dto.model.mapping.MappedDimensionImpl;
import com.cedar.cp.dto.model.mapping.MappedDimensionPK;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubeImpl;
import com.cedar.cp.dto.model.mapping.MappedFinanceCubePK;
import com.cedar.cp.dto.model.mapping.MappedHierarchyImpl;
import com.cedar.cp.dto.model.mapping.MappedHierarchyPK;
import com.cedar.cp.dto.model.mapping.MappedModelImpl;
import com.softproideas.app.admin.modelmappings.model.YearDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedCalendarDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedDataTypeDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedDimensionDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedDimensionElementDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedFinanceCubeDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedHierarchyDTO;
import com.softproideas.app.admin.modelmappings.model.details.MappedModelDetailsDTO;
import com.softproideas.app.core.externalsystem.model.ExternalSystemCoreDTO;

/**
 * <p>Transforms various data types related to mapping model.</p>
 *
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class ModelMappingsMapper {

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SAVE //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static MappedModelImpl mapNewMappedModelDetailsDTO(MappedModelDetailsDTO mappedModelDetailsDTO, MappedModelImpl mappedModelImpl, FinanceLedger externalLedger) {
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

        // CompanyVisId
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
        List<FinanceCalendarYear> calendarYear = externalLedger.getFinanceCompany().getFinanceCalendarYears();
        for (YearDTO year: yearsDTO) {
            boolean[] spec = year.getSpec();
            Boolean selectedYear = spec[0];
            Boolean selectedMonth = spec[3];
            Boolean selectedOpeningBalance = spec[6];

            int yearMappedCalendarYear = Integer.parseInt(year.getYearVisId());
            // rok wybrany przez użytkownika
            for (FinanceCalendarYear openAccYear: calendarYear) {
                if (openAccYear.getYear() == yearMappedCalendarYear) { // sprawdzamy czy rok jest w openAcc

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
                    MappedCalendarYearPK mappedCalendarYearPK = new MappedCalendarYearPK(-yearMappedCalendarYear); // rok na minusie
                    MappedCalendarYearImpl mappedCalendarYearImpl = new MappedCalendarYearImpl(mappedCalendarYearPK, yearMappedCalendarYear, mappedCalendarElements);
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

            mappedDimensionElements.add(mappedDimensionElementImpl);
        }

        return mappedDimensionElements;
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

            mappedHierarchies.add(mappedHierarchyImpl);
        }

        return mappedHierarchies;
    }

    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // UPDATE ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static MappedModelImpl mapOldMappedModelDetailsDTO(MappedModelDetailsDTO mappedModelDetailsDTO, MappedModelImpl mappedModelImpl, FinanceLedger externalLedger) {
        Integer counter = -1;

        // CompaniesVisId and CompanyVisId
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

        List<FinanceCalendarYear> financeCalendarYears = externalLedger.getFinanceCompany().getFinanceCalendarYears();

        // dodawanie/aktualizacja lat (mapped calendar years)
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
                            // aktualizuj (mapped calendar year)
                           
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
            mappedDimensionElements.add(mappedDimensionElementImpl);
        }

        return mappedDimensionElements;
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
}
