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
package com.softproideas.app.admin.dimensions.calendar.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.base.ValidationException;
import com.cedar.cp.api.dimension.HierarchyRef;
import com.cedar.cp.api.dimension.calendar.CalendarYearSpec;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.dimension.AllHierarchysELO;
import com.cedar.cp.dto.dimension.AvailableDimensionsELO;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyPK;
import com.cedar.cp.dto.dimension.HierarchyRefImpl;
import com.cedar.cp.dto.dimension.HierarcyDetailsFromDimIdELO;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarYearSpecImpl;
import com.softproideas.app.admin.dimensions.calendar.model.CalendarDTO;
import com.softproideas.app.admin.dimensions.calendar.model.CalendarDetailsDTO;
import com.softproideas.app.admin.dimensions.calendar.model.CalendarElementDTO;
import com.softproideas.app.core.hierarchy.model.HierarchyCoreDTO;
import com.softproideas.app.core.model.mapper.ModelCoreMapper;
import com.softproideas.app.core.model.model.ModelCoreDTO;
import com.softproideas.app.reviewbudget.dimension.model.DimensionDTO;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.util.DimensionUtil;

public class CalendarMapper {

    public static List<CalendarDTO> mapAllCalendarELO(AllHierarchysELO allDimensions, AvailableDimensionsELO availableDimensions, CPContextHolder cpContextHolder) {
        List<CalendarDTO> calendarDTOList = new ArrayList<CalendarDTO>();

        // dimensions assigned to model
        for (@SuppressWarnings("unchecked")
        Iterator<AllHierarchysELO> it = allDimensions.iterator(); it.hasNext();) {
            AllHierarchysELO row = it.next();
            Integer type = row.getType();
            // Type 3 = Calendar (cal)
            // Types: 1 = Account (exp), 2 = Business (cc) are supported in other mapper
            if (type == 3) {
                CalendarDTO calendarDTO = new CalendarDTO();

                DimensionRefImpl dimensionRefImpl = (DimensionRefImpl) row.getDimensionEntityRef();
                Integer dimensionID = dimensionRefImpl.getDimensionPK().getDimensionId();
                calendarDTO.setDimensionId(dimensionID);
                calendarDTO.setDimensionVisId(dimensionRefImpl.getNarrative());
                
                ModelRef modelRef = (ModelRef) row.getModelEntityRef();
                ModelCoreDTO model = ModelCoreMapper.mapModelRefToModelCoreDTO(modelRef);
                calendarDTO.setModel(model);

                calendarDTO.setType(type);
                calendarDTO.setDimensionDescription(row.getDescription());
                // set Hierarchy details
                HierarcyDetailsFromDimIdELO hierarcyDetailsFromDimIdELO = cpContextHolder.getListSessionServer().getHierarcyDetailsFromDimId(dimensionID);
                HierarchyCoreDTO hierarchy = new HierarchyCoreDTO();
                if (hierarcyDetailsFromDimIdELO.size() > 0) {
                    hierarchy.setHierarchyId((Integer) hierarcyDetailsFromDimIdELO.getValueAt(0, "HierarchyId"));
                    hierarchy.setHierarchyVisId(((HierarchyRefImpl) hierarcyDetailsFromDimIdELO.getValueAt(0, "Hierarchy")).getNarrative());              
                }    
                calendarDTO.setHierarchy(hierarchy);
                calendarDTOList.add(calendarDTO);
            }
        }

        // dimensions without model
        for (@SuppressWarnings("unchecked")
        Iterator<AvailableDimensionsELO> it2 = availableDimensions.iterator(); it2.hasNext();) {
            AvailableDimensionsELO row = it2.next();
            Integer type = row.getType();
            // Type 3 = Calendar (cal)
            // Types: 1 = Account (exp), 2 = Business (cc) are supported in other mapper
            if (type == 3) {
                CalendarDTO calendarDTO = new CalendarDTO();

                DimensionRefImpl dimensionRefImpl = (DimensionRefImpl) row.getDimensionEntityRef();
                Integer dimensionID = dimensionRefImpl.getDimensionPK().getDimensionId();
                calendarDTO.setDimensionId(dimensionID);
                calendarDTO.setDimensionVisId(dimensionRefImpl.getNarrative());        

                calendarDTO.setModel(new ModelCoreDTO());

                calendarDTO.setType(type);
                // set Hierarchy details
                HierarcyDetailsFromDimIdELO hierarcyDetailsFromDimIdELO = cpContextHolder.getListSessionServer().getHierarcyDetailsFromDimId(dimensionID);
                HierarchyCoreDTO hierarchy = new HierarchyCoreDTO();
                if (hierarcyDetailsFromDimIdELO.size() > 0) {
                    hierarchy.setHierarchyId((Integer) hierarcyDetailsFromDimIdELO.getValueAt(0, "HierarchyId"));
                    hierarchy.setHierarchyVisId(((HierarchyRefImpl) hierarcyDetailsFromDimIdELO.getValueAt(0, "Hierarchy")).getNarrative());
                }
//                for(@SuppressWarnings("unchecked")
//                Iterator<HierarcyDetailsFromDimIdELO> it3 = hierarcyDetailsFromDimIdELO.iterator(); it3.hasNext();) {
//                    HierarcyDetailsFromDimIdELO hier = it3.next();
//                    hierarchy.setHierarchyId(hier.getHierarchyId());
//                    hierarchy.setHierarchyVisId(hier.getVisId() + "fsd");
//                }
                calendarDTO.setHierarchy(hierarchy);
                calendarDTO.setDimensionDescription(row.getDescription());

                calendarDTOList.add(calendarDTO);
            }
        }

        return calendarDTOList;
    }

    public static CalendarDetailsDTO mapDimension(CalendarImpl dimension, CPContextHolder cpContextHolder) throws ValidationException {
        CalendarDetailsDTO calendarDetailsDTO = new CalendarDetailsDTO();

        int dimensionID = dimension.getDimensionId();
        calendarDetailsDTO.setDimensionId(dimensionID);
        calendarDetailsDTO.setDimensionVisId(dimension.getVisId());

        if (dimension.getModel() != null) {
            ModelRef modelRef = (ModelRef) dimension.getModel();
            ModelCoreDTO model = ModelCoreMapper.mapModelRefToModelCoreDTO(modelRef);
            calendarDetailsDTO.setModel(model);
        } else {
            calendarDetailsDTO.setModel(new ModelCoreDTO());
        }

        // Source System
        Integer externalSystemRef = dimension.getExternalSystemRef();
        calendarDetailsDTO.setExternalSystemRefName(DimensionUtil.getExternalSystemRefName(externalSystemRef));

        calendarDetailsDTO.setType(3);
        calendarDetailsDTO.setDimensionDescription(dimension.getDescription());
        calendarDetailsDTO.setVersionNum(dimension.getVersionNum());

        // set Hierarchy details
        HierarcyDetailsFromDimIdELO hierarcyDetailsFromDimIdELO = cpContextHolder.getListSessionServer().getHierarcyDetailsFromDimId(dimensionID);
        HierarchyCoreDTO hierarchy = new HierarchyCoreDTO();
        hierarchy.setHierarchyId((Integer) hierarcyDetailsFromDimIdELO.getValueAt(0, "HierarchyId"));
        hierarchy.setHierarchyVisId(((HierarchyRefImpl) hierarcyDetailsFromDimIdELO.getValueAt(0, "Hierarchy")).getNarrative());
        calendarDetailsDTO.setHierarchy(hierarchy);

        // set id, name and spec for years
        List<CalendarElementDTO> allYears = new ArrayList<CalendarElementDTO>();
        int startYear = dimension.getStartYear();
        int endYear = dimension.getEndYear();
        for (int currentYear = startYear; currentYear <= endYear; currentYear++) {
            CalendarElementDTO calendarElementDTO = new CalendarElementDTO();
            calendarElementDTO.setYear(currentYear);
            CalendarYearSpecImpl spec = dimension.getYearSpec(currentYear);
            int currentYearId = spec.getId();
            calendarElementDTO.setYearId(currentYearId);
            boolean[] newSpec = new boolean[10];
            for (int i = 0; i < 10; i++) {
                newSpec[i] = spec.get(i);
            }
            calendarElementDTO.setSpec(newSpec);
            allYears.add(calendarElementDTO);
        }
        calendarDetailsDTO.setYears(allYears);

        if (dimension.getModel() != null && dimension.isChangeManagementRequestsPending()) {
            calendarDetailsDTO.setReadOnly(true);
            // message ends with "*" - it means message will be red on frontend
            calendarDetailsDTO.setInUseLabel("This calendar is in use. \n Change requests are pending. Updates are restricted.*");
        } else if (dimension.getExternalSystemRef() != null && dimension.getModel() != null) {
            calendarDetailsDTO.setReadOnly(true);
            calendarDetailsDTO.setInUseLabel("This calendar is in use and sourced from an external system. \n Updates are restricted. \n Please use the import/export mapping wizard to update this calendar.");
        } else if (dimension.getModel() != null) {
            calendarDetailsDTO.setReadOnly(false);
            calendarDetailsDTO.setInUseLabel("This calendar is in use in a model. \n Updates will be applied via a change management request.");
        } else {
            calendarDetailsDTO.setReadOnly(false);
            calendarDetailsDTO.setInUseLabel(null);
        }

        return calendarDetailsDTO;
    }

    public static CalendarImpl mapCalendarDetailsDTOToCalendarDetailsImpl(CalendarImpl impl, CalendarDetailsDTO dimension) {
        // override some fields values
        impl.setVisId(dimension.getDimensionVisId());
        impl.setDescription(dimension.getDimensionDescription());
        impl.setVersionNum(dimension.getVersionNum());
        impl.setSubmitChangeManagementRequest(dimension.isSubmitChangeManagementRequest());
        
        List<CalendarElementDTO> years = dimension.getYears();
        impl.getYears().clear();
        for (CalendarElementDTO yearDTO: years) {
            CalendarYearSpec cys = new CalendarYearSpecImpl(yearDTO.getYearId(), yearDTO.getYear(), yearDTO.getSpec());
            impl.getYears().add(cys);
        }
        return impl;
    }

    public static List<DimensionDTO> mapHashMapWithHierarchyRefToDTO(HashMap<String, ArrayList<HierarchyRef>> calendar) {
        List<DimensionDTO> calendars = new ArrayList<DimensionDTO>();
        if (calendar != null) {
            List<String> keys = new ArrayList<String>(calendar.keySet());

            for (String key: keys) {
                ArrayList<HierarchyRef> hierarchyRefs = calendar.get(key);
                for (HierarchyRef hierarchyRef: hierarchyRefs) {
                    DimensionDTO dimension = new DimensionDTO();
                    dimension.setId(((HierarchyPK) hierarchyRef.getPrimaryKey()).getHierarchyId());
                    dimension.setName(hierarchyRef.getNarrative());
                    calendars.add(dimension);
                }
            }
        }
        return calendars;
    }
}
