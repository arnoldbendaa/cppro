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
package com.softproideas.app.admin.budgetcycles.mapper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.model.BudgetCycleRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.model.AllBudgetCyclesELO;
import com.cedar.cp.dto.model.BudgetCycleCK;
import com.cedar.cp.dto.model.BudgetCycleImpl;
import com.cedar.cp.dto.model.BudgetCyclesForModelELO;
import com.cedar.cp.dto.model.MaxDepthForBudgetHierarchyELO;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDTO;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleDetailsDTO;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleStructureLevelEndDatesDTO;
import com.softproideas.app.admin.budgetcycles.model.BudgetCycleXMLFormDTO;
import com.softproideas.app.core.model.mapper.ModelCoreMapper;
import com.softproideas.app.core.model.model.ModelCoreDTO;

/**
 * <p>Class is responsible for maps different object related to budget cycle to data transfer object (and vice-versa)</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class BudgetCyclesMapper {

    private static String dateFormat = "yyyy-MM-dd";

    /**
     * Maps list of budget cycles {@link AllBudgetCyclesELO} to list of transfer data object related to budget cycles.
     */
    public static List<BudgetCycleDTO> mapAllBudgetCyclesELOToBudgetCycleDTO(AllBudgetCyclesELO elo) {
        List<BudgetCycleDTO> budgetCyclesDTO = new ArrayList<BudgetCycleDTO>();

        for (@SuppressWarnings("unchecked")
        Iterator<AllBudgetCyclesELO> it = elo.iterator(); it.hasNext();) {
            AllBudgetCyclesELO row = it.next();
            BudgetCycleDTO budgetCycleDTO = new BudgetCycleDTO();
            mapBudgetCycleProperty(budgetCycleDTO, row.getBudgetCycleEntityRef());

            ModelCoreDTO model = ModelCoreMapper.mapModelRefToModelCoreDTO(row.getModelEntityRef());
            budgetCycleDTO.setModel(model);

            budgetCycleDTO.setBudgetCycleDescription(row.getDescription());
            budgetCycleDTO.setStatus(row.getStatus());
            budgetCycleDTO.setPeriodFromId(row.getPeriodFrom());
            budgetCycleDTO.setPeriodToId(row.getPeriodTo());
            budgetCycleDTO.setPeriodFromVisId(row.getPeriodFromVisId());
            budgetCycleDTO.setPeriodToVisId(row.getPeriodToVisId());
            budgetCycleDTO.setCategory(row.getCategory());
            budgetCyclesDTO.add(budgetCycleDTO);
        }
        return budgetCyclesDTO;
    }

    /**
     * Maps list of budget cycles {@link BudgetCyclesForModelELO} to list of transfer data object related to budget cycles.
     */
    @SuppressWarnings("unchecked")
    public static List<BudgetCycleDTO> mapBudgetCyclesForModelELOToDTO(BudgetCyclesForModelELO budgetCycles) {
        List<BudgetCycleDTO> budgetCyclesDTO = new ArrayList<BudgetCycleDTO>();

        for (Iterator<BudgetCyclesForModelELO> it = budgetCycles.iterator(); it.hasNext();) {
            BudgetCyclesForModelELO row = it.next();
            BudgetCycleDTO budgetCycleDTO = new BudgetCycleDTO();

            mapBudgetCycleProperty(budgetCycleDTO, row.getBudgetCycleEntityRef());

            ModelCoreDTO model = ModelCoreMapper.mapModelRefToModelCoreDTO(row.getModelEntityRef());
            budgetCycleDTO.setModel(model);

            budgetCycleDTO.setBudgetCycleDescription(row.getDescription());
            // budgetCycleDTO.setPlannedEndDate(row.getPlannedEndDate());
            budgetCyclesDTO.add(budgetCycleDTO);
        }
        return budgetCyclesDTO;
    }

    /**
     * Maps budget cycle data stored in {@link BudgetCycleImpl} to data transfer object {@link BudgetCycleDetailsDTO}
     */
    public static BudgetCycleDetailsDTO mapBudgetCycleImplToBudgetCycleDetailsDTO(BudgetCycleImpl budgetCycleImpl) {
        BudgetCycleDetailsDTO budgetCycleDTO = new BudgetCycleDetailsDTO();

        BudgetCycleCK budgetCycleCK = (BudgetCycleCK) budgetCycleImpl.getPrimaryKey();
        budgetCycleDTO.setBudgetCycleId(budgetCycleCK.getBudgetCyclePK().getBudgetCycleId());
        budgetCycleDTO.setBudgetCycleVisId(budgetCycleImpl.getVisId());
        budgetCycleDTO.setBudgetCycleDescription(budgetCycleImpl.getDescription());
        budgetCycleDTO.setCategory(budgetCycleImpl.getCategory());

        ModelCoreDTO model = ModelCoreMapper.mapModelRefToModelCoreDTO(budgetCycleImpl.getModelRef());
        budgetCycleDTO.setModel(model);

        budgetCycleDTO.setStatus(budgetCycleImpl.getStatus());
        budgetCycleDTO.setPeriodFromId(budgetCycleImpl.getPeriodId());
        budgetCycleDTO.setPeriodToId(budgetCycleImpl.getPeriodIdTo());
        budgetCycleDTO.setPeriodFromVisId(budgetCycleImpl.getPeriodFromVisId());
        budgetCycleDTO.setPeriodToVisId(budgetCycleImpl.getPeriodToVisId());

        budgetCycleDTO.setDefaultXmlFormDataType(budgetCycleImpl.getXmlFormDataType());
        budgetCycleDTO.setDefaultXmlFormId(budgetCycleImpl.getXmlFormId());

        budgetCycleDTO.setPlannedEndDate(mapTimestampToDateString(budgetCycleImpl.getPlannedEndDate(), dateFormat));
        budgetCycleDTO.setStartDate(mapTimestampToDateString(budgetCycleImpl.getStartDate(), dateFormat));
        budgetCycleDTO.setEndDate(mapTimestampToDateString(budgetCycleImpl.getEndDate(), dateFormat));

        List<Long> levelDatesDTO = mapLevelDatesToLevelDatesDTO(budgetCycleImpl.getLevelDates());
        budgetCycleDTO.setLevelDates(levelDatesDTO);

        List<BudgetCycleXMLFormDTO> budgetCyclesXMLForms = mapXMLFormsToBudgetCycleXMLFormDTO(budgetCycleImpl.getXmlForms());
        budgetCycleDTO.setXmlForms(budgetCyclesXMLForms);

        budgetCycleDTO.setVersionNum(budgetCycleImpl.getVersionNum());
        return budgetCycleDTO;
    }

    /**
     * Method rewrites fields (only periods) stored in data transfer object {@link BudgetCycleDetailsDTO} to database object {@link BudgetCycleImpl}
     */
    public static BudgetCycleImpl mapBudgetCycleDTOToBudgetCycleImpl(BudgetCycleImpl budgetCycleImpl, BudgetCycleDTO budgetCycle) {
        budgetCycleImpl.setPeriodId(budgetCycle.getPeriodFromId());
        budgetCycleImpl.setPeriodIdTo(budgetCycle.getPeriodToId());
        budgetCycleImpl.setPeriodFromVisId(budgetCycle.getPeriodFromVisId());
        budgetCycleImpl.setPeriodToVisId(budgetCycle.getPeriodToVisId());
        budgetCycleImpl.setCategory(budgetCycle.getCategory());
        return budgetCycleImpl;
    }

    /**
     * Method rewrites all fields stored in data transfer object {@link BudgetCycleDetailsDTO} to database object {@link BudgetCycleImpl}
     */
    public static BudgetCycleImpl mapBudgetCycleDetailsDTOToBudgetCycleImpl(BudgetCycleImpl budgetCycleImpl, BudgetCycleDetailsDTO budgetCycle, int userId) {
        budgetCycleImpl.setVisId(budgetCycle.getBudgetCycleVisId());
        budgetCycleImpl.setDescription(budgetCycle.getBudgetCycleDescription());
        budgetCycleImpl.setStatus(budgetCycle.getStatus());
        budgetCycleImpl.setCategory(budgetCycle.getCategory());

        budgetCycleImpl.setModelId(budgetCycle.getModel().getModelId());
        ModelPK modelPK = new ModelPK(budgetCycle.getModel().getModelId());
        ModelRef modelRef = new ModelRefImpl(modelPK, budgetCycle.getModel().getModelVisId());
        budgetCycleImpl.setModelRef(modelRef);

        budgetCycleImpl.setPeriodId(budgetCycle.getPeriodFromId());
        budgetCycleImpl.setPeriodIdTo(budgetCycle.getPeriodToId());
        budgetCycleImpl.setPeriodFromVisId(budgetCycle.getPeriodFromVisId());
        budgetCycleImpl.setPeriodToVisId(budgetCycle.getPeriodToVisId());

        budgetCycleImpl.setXmlFormId(budgetCycle.getDefaultXmlFormId());
        budgetCycleImpl.setXmlFormDataType(budgetCycle.getDefaultXmlFormDataType());
        
        budgetCycleImpl.setCategory(budgetCycle.getCategory());

        Timestamp timestamp = mapPlannedEndDateDTOToTimestamp(budgetCycleImpl, budgetCycle);
        if (timestamp != budgetCycleImpl.getPlannedEndDate()) {
            budgetCycleImpl.setPlannedEndDate(timestamp);
            budgetCycleImpl.setDateChanged(true);
        } else {
            budgetCycleImpl.setDateChanged(false);
        }
        // tego nie musimy nadpisywać bo start date i end date są zależne do statusu BudgetCycleEditorSessionSEJB.completeUpdateSetup
        // budgetCycleImpl.setStartDate(budgetCycle.getStartDate());
        // budgetCycleImpl.setEndDate(budgetCycle.getEndDate());

        List<Timestamp> levelDates = mapLevelDatesDTOToLevelDates(budgetCycle.getLevelDates());
        budgetCycleImpl.setLevelDates(levelDates);

        List<Object[]> xmlForms = mapBudgetCycleXMLFormDTOToXMLForms(budgetCycle.getXmlForms());
        budgetCycleImpl.setXmlForms(xmlForms);

        budgetCycleImpl.setVersionNum(budgetCycle.getVersionNum());
        return budgetCycleImpl;
    }

    /**
     * Maps list of xmlForm data {@link BudgetCycleXMLFormDTO} to list of xmlForms object we are matched to xmlForms which are in {@link BudgetCycleImpl} 
     * @return list of objects which are matched to xmlForms in {@link BudgetCycleImpl}
     */
    private static List<Object[]> mapBudgetCycleXMLFormDTOToXMLForms(List<BudgetCycleXMLFormDTO> xmlFormsDTO) {
        List<Object[]> xmlFormsDB = new ArrayList<Object[]>();
        for (BudgetCycleXMLFormDTO xmlFormDTO: xmlFormsDTO) {
            Object[] xmlFormImpl = new Object[2];
            xmlFormImpl[0] = xmlFormDTO.getFlatFormId();
            xmlFormImpl[1] = xmlFormDTO.getDataType();
            xmlFormsDB.add(xmlFormImpl);
        }
        return xmlFormsDB;
    }

    /**
     * Maps list of long numbers to list of timestamps we are matched to {@link BudgetCycleImpl} timestamps 
     */
    private static List<Timestamp> mapLevelDatesDTOToLevelDates(List<Long> levelDatesDTO) {
        List<Timestamp> levelDates = new ArrayList<Timestamp>();
        if (levelDatesDTO == null) {
            return levelDates;
        }
        for (Long levelDate: levelDatesDTO) {
            Timestamp levelDateImpl = new Timestamp(levelDate);
            levelDates.add(levelDateImpl);
        }
        return levelDates;
    }

    /**
     * Maps planned end date store as string in specific format (dateFormat) to timestamp 
     */
    private static Timestamp mapPlannedEndDateDTOToTimestamp(BudgetCycleImpl budgetCycleImpl, BudgetCycleDetailsDTO budgetCycle) {
        long timeStamp = mapDateStringToTimestamp(budgetCycle.getPlannedEndDate(), dateFormat);
        return new Timestamp(timeStamp);
    }
    
    public static long mapDateStringToTimestamp(String dateString, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * Maps list of xmlForms object which are stored in {@link BudgetCycleImpl} to list of xmlForm data {@link BudgetCycleXMLFormDTO} 
     */
    private static List<BudgetCycleXMLFormDTO> mapXMLFormsToBudgetCycleXMLFormDTO(List<Object[]> xmlFormsDB) {
        List<BudgetCycleXMLFormDTO> xmlFormsDTO = new ArrayList<BudgetCycleXMLFormDTO>();
        for (Object[] xmlForm: xmlFormsDB) {
            BudgetCycleXMLFormDTO budgetCyclesXMLFormDTO = new BudgetCycleXMLFormDTO();
            budgetCyclesXMLFormDTO.setFlatFormId((Integer) xmlForm[0]);
            budgetCyclesXMLFormDTO.setDataType((String) xmlForm[1]);
            xmlFormsDTO.add(budgetCyclesXMLFormDTO);
        }
        return xmlFormsDTO;
    }

    /**
     * Maps list of timestamps we are matched to {@link BudgetCycleImpl} timestamps to list of long numbers.
     */
    private static List<Long> mapLevelDatesToLevelDatesDTO(List<Timestamp> levelDates) {
        List<Long> levelDatesDTO = new ArrayList<Long>();
        if (levelDates == null) {
            return levelDatesDTO;
        }
        for (Timestamp timestamp: levelDates) {
            levelDatesDTO.add(timestamp.getTime());
        }
        return levelDatesDTO;
    }

    /**
     * Maps date store as {@link java.sql.Timestamp} to string date with specified format
     * @return formatted string date
     */
    private static String mapTimestampToDateString(Timestamp timestamp, String format) {
        if (timestamp != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.format(timestamp.getTime());
        }
        return null;
    }

    /**
     * Rewrites {@link BudgetCycleRef} properties to id, name of budget cycle in {@link BudgetCycleDTO}
     */
    private static void mapBudgetCycleProperty(BudgetCycleDTO budgetCycleDTO, BudgetCycleRef budgetCycleRef) {
        BudgetCycleCK budgetCycleCK = (BudgetCycleCK) budgetCycleRef.getPrimaryKey();
        budgetCycleDTO.setBudgetCycleId(budgetCycleCK.getBudgetCyclePK().getBudgetCycleId());
        budgetCycleDTO.setBudgetCycleVisId(budgetCycleRef.getNarrative());
    }
    
    /**
     * Maps max depths for budget hierarchy to list of timestamps
     */
    public static ArrayList<Long> mapMaxDepthForBudgetHierarchyELOtoStructureLevelEndDates(BudgetCycleStructureLevelEndDatesDTO budgetCycleStructureLevelEndDatesDTO, MaxDepthForBudgetHierarchyELO elo) {
        Integer maxDepth = Integer.valueOf(0);
        if (elo.getNumRows() > 0) {
            maxDepth = (Integer) elo.getValueAt(0, "col2");
        }

        maxDepth = Integer.valueOf(maxDepth.intValue() + 1);
        long today = System.currentTimeMillis();
        
        String str = budgetCycleStructureLevelEndDatesDTO.getPlannedEndDate();
        long planned =  BudgetCyclesMapper.mapDateStringToTimestamp(str, "yyyy-MM-dd");
        long dif = planned - today;
        long millsPerDay = 86400000L;
        Long numberOfDays = Long.valueOf(dif / millsPerDay);
        Long intervalDays = Long.valueOf(numberOfDays.longValue() / (long) maxDepth.intValue());
        Long intervalDaysMillis = Long.valueOf(intervalDays.longValue() * millsPerDay);
        ArrayList<Long> splitDays = new ArrayList<Long>(maxDepth.intValue());

        for (int i = 0; i < maxDepth.intValue(); ++i) {
            if (i == 0) {
                splitDays.add(new Date(planned).getTime());
            } else {
                long subTime = intervalDaysMillis.longValue() * (long) i;
                splitDays.add(new Date(planned - subTime).getTime());
            }
        }
        return splitDays;
    }
}
