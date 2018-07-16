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
package com.softproideas.app.admin.financecubes.mapper;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.model.RollUpRule;
import com.cedar.cp.api.model.RollUpRuleLine;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.AllDimensionsForModelELO;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.model.AllFinanceCubesELO;
import com.cedar.cp.dto.model.FinanceCubeImpl;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.ModelPK;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.dto.model.RollUpRuleImpl;
import com.cedar.cp.dto.model.RollUpRuleLineImpl;
import com.softproideas.app.admin.datatypes.util.DataTypesUtil;
import com.softproideas.app.admin.financecubes.model.DataTypeWithMeasureLenghtDTO;
import com.softproideas.app.admin.financecubes.model.DataTypeWithTimestampDTO;
import com.softproideas.app.admin.financecubes.model.DimensionDTO;
import com.softproideas.app.admin.financecubes.model.FinanceCubeDetailsDTO;
import com.softproideas.app.admin.financecubes.model.RollUpRuleDTO;
import com.softproideas.app.admin.financecubes.model.RollUpRuleLineDTO;
import com.softproideas.app.core.financecube.model.FinanceCubeModelCoreDTO;
import com.softproideas.app.core.model.model.ModelCoreDTO;

public class FinanceCubesMapper {

    /**
     * Map AllFinanceCubesELO to List<FinanceCubeDTO>.
     */
    @SuppressWarnings("unchecked")
    public static List<FinanceCubeModelCoreDTO> mapAllFinanseCubesELO(AllFinanceCubesELO elo) {
        List<FinanceCubeModelCoreDTO> financeCubesList = new ArrayList<FinanceCubeModelCoreDTO>();

        for (Iterator<AllFinanceCubesELO> it = elo.iterator(); it.hasNext();) {
            AllFinanceCubesELO row = it.next();

            FinanceCubeModelCoreDTO financeCubeDTO = new FinanceCubeModelCoreDTO();
            ModelCoreDTO modelCoreDTO = new ModelCoreDTO();

            FinanceCubeRefImpl propertyFinanceCube = (FinanceCubeRefImpl) row.getFinanceCubeEntityRef();
            financeCubeDTO.setFinanceCubeId(propertyFinanceCube.getFinanceCubePK().getFinanceCubeId());
            financeCubeDTO.setFinanceCubeVisId(propertyFinanceCube.getNarrative());

            ModelRefImpl propertyModel = (ModelRefImpl) row.getModelEntityRef();
            modelCoreDTO.setModelId(propertyModel.getModelPK().getModelId());
            modelCoreDTO.setModelVisId(propertyModel.getNarrative());
            financeCubeDTO.setModel(modelCoreDTO);

            String description = row.getDescription();
            financeCubeDTO.setFinanceCubeDescription(description);

            financeCubesList.add(financeCubeDTO);
        }

        return financeCubesList;
    }

    /**
     * Map FinanceCubeImpl to FinanceCubeDetailsDTO.
     */
    public static FinanceCubeDetailsDTO mapFinanceCubeImpl(FinanceCubeImpl financeCubeImpl, int modelId, int financeCubeId) {
        FinanceCubeDetailsDTO financeCubeDetailsDTO = new FinanceCubeDetailsDTO();
        ModelCoreDTO modelCoreDTO = new ModelCoreDTO();

        financeCubeDetailsDTO.setInsideChangeManagement(financeCubeImpl.isInsideChangeManagement());
        financeCubeDetailsDTO.setChangeManagementOutstanding(financeCubeImpl.isChangeManagementOutstanding());

        financeCubeDetailsDTO.setFinanceCubeId(financeCubeId);
        financeCubeDetailsDTO.setFinanceCubeVisId(financeCubeImpl.getVisId());

        modelCoreDTO.setModelId(modelId);
        modelCoreDTO.setModelVisId(financeCubeImpl.getModelRef().getNarrative());
        financeCubeDetailsDTO.setModel(modelCoreDTO);

        financeCubeDetailsDTO.setFinanceCubeDescription(financeCubeImpl.getDescription());
        financeCubeDetailsDTO.setHasData(financeCubeImpl.isHasData());

        financeCubeDetailsDTO.setAudited(financeCubeImpl.isAudited());
        financeCubeDetailsDTO.setCubeFormulaEnabled(financeCubeImpl.isCubeFormulaEnabled());

        if (financeCubeImpl.getLockedByTaskId() != null) {
            financeCubeDetailsDTO.setLockedByTaskId(financeCubeImpl.getLockedByTaskId());
        } else {
            financeCubeDetailsDTO.setLockedByTaskId(-1);
        }
        financeCubeDetailsDTO.setInternalKey("[ModelId=" + modelId + "][FinanceCubeId=" + financeCubeId + "]");

        List<DataTypeWithTimestampDTO> list = mapDataTypeHashMap(financeCubeImpl.getSelectedDataTypeRefs());
        financeCubeDetailsDTO.setDataTypes(list);

        addDimensionRefImpl(financeCubeDetailsDTO, financeCubeImpl);
        addRollUpRuleLineDTOTable(financeCubeDetailsDTO, financeCubeImpl);
        setMappedDataTypesDTO(financeCubeDetailsDTO, financeCubeImpl);

        financeCubeDetailsDTO.setVersionNum(financeCubeImpl.getVersionNum());

        return financeCubeDetailsDTO;
    }

    /**
     * Map Map<DataTypeRef, Timestamp> to List<DataTypeWithTimestampDTO>. Map<DataTypeRef, Timestamp> is property of FinanceCubeImpl.
     * DataTypeWithTimestampDTO is property of FinanceCubeDetailsDTO.
     */
    public static List<DataTypeWithTimestampDTO> mapDataTypeHashMap(Map<DataTypeRef, Timestamp> dataTypeHashMap) {
        List<DataTypeWithTimestampDTO> listDataTypeDTO = new ArrayList<DataTypeWithTimestampDTO>();

        for (DataTypeRef iterator: dataTypeHashMap.keySet()) {
            DataTypeWithTimestampDTO dataTypeWithTimestampDTO = new DataTypeWithTimestampDTO();

            dataTypeWithTimestampDTO.setDataTypeId(((DataTypeRefImpl) iterator).getDataTypePK().getDataTypeId());
            dataTypeWithTimestampDTO.setDataTypeVisId(iterator.getNarrative());
            dataTypeWithTimestampDTO.setDataTypeDescription(iterator.getDescription());
            dataTypeWithTimestampDTO.setMeasureClass(iterator.getMeasureClass());
            dataTypeWithTimestampDTO.setMeasureClassName(DataTypesUtil.getMeasureClassName(iterator.getMeasureClass()));
            dataTypeWithTimestampDTO.setSubType(iterator.getSubType());
            dataTypeWithTimestampDTO.setSubTypeName(DataTypesUtil.getSubTypeName(iterator.getSubType()));
            dataTypeWithTimestampDTO.setMeasureLength(iterator.getMeasureLength());
            dataTypeWithTimestampDTO.setCubeLastUpdated(dataTypeHashMap.get(iterator));

            listDataTypeDTO.add(dataTypeWithTimestampDTO);
        }
        return listDataTypeDTO;
    }

    /**
     * Map DimensionRef[] to DimensionDTO[]. DimensionRef is property of FinanceCubeImpl. DimensionDTO is property of FinanceCubeDetailsDTO.
     */
    public static void addDimensionRefImpl(FinanceCubeDetailsDTO financeCubeDetailsDTO, FinanceCubeImpl financeCubeImpl) {
        DimensionRef[] dimensionRefImpl = financeCubeImpl.getDimensions();
        DimensionDTO[] dimensionDTO = new DimensionDTO[dimensionRefImpl.length];
        int i = 0;
        for (DimensionRef dimension: dimensionRefImpl) {
            dimensionDTO[i] = new DimensionDTO();
            dimensionDTO[i].setDimensionId(((DimensionRefImpl) dimension).getDimensionPK().getDimensionId());
            dimensionDTO[i].setDimensionVisId(dimension.getNarrative());
            dimensionDTO[i++].setType(dimension.getType());
        }
        financeCubeDetailsDTO.setDimensions(dimensionDTO);
    }

    /**
     * Map List<RollUpRuleLine> to List<RollUpRuleLineDTO>. List<RollUpRuleLine> is property of FinanceCubeImpl.
     * List<RollUpRuleLineDTO> is property of FinanceCubeDetailsDTO.
     */
    private static void addRollUpRuleLineDTOTable(FinanceCubeDetailsDTO financeCubeDetailsDTO, FinanceCubeImpl financeCubeImpl) {
        List<RollUpRuleLineDTO> listRollUpRuleLineDTO = new ArrayList<RollUpRuleLineDTO>();
        List<RollUpRuleLine> listRollUpRuleLine = null;
        listRollUpRuleLine = financeCubeImpl.getRollUpRuleLines();
        for (RollUpRuleLine listRollUpRuleLineElement: listRollUpRuleLine) {
            RollUpRuleLineDTO rollUpRuleLineDTO = new RollUpRuleLineDTO();
            DimensionRef[] dimension = financeCubeImpl.getDimensions();

            setDataTypeToRollUpRuleLineDTO(rollUpRuleLineDTO, listRollUpRuleLineElement);
            addDimensionsToRollUpRuleLineDTO(dimension, rollUpRuleLineDTO, listRollUpRuleLineElement);

            listRollUpRuleLineDTO.add(rollUpRuleLineDTO);
        }
        financeCubeDetailsDTO.setRollUpRuleLines(listRollUpRuleLineDTO);
    }

    /**
     * Map DataTypeRefImpl to DataTypeWithMeasureLenghtDTO and set DataTypeWithMeasureLenghtDTO for RollUpRuleLineDTO. DataTypeRefImpl is property of RollUpRuleLine.
     */
    private static void setDataTypeToRollUpRuleLineDTO(RollUpRuleLineDTO rollUpRuleLineDTO, RollUpRuleLine listRollUpRuleLineElement) {
        DataTypeWithMeasureLenghtDTO dataTypeWithMeasureLenghtDTO = new DataTypeWithMeasureLenghtDTO();

        dataTypeWithMeasureLenghtDTO.setDataTypeId(((DataTypePK) listRollUpRuleLineElement.getDataType().getPrimaryKey()).getDataTypeId());
        dataTypeWithMeasureLenghtDTO.setDataTypeVisId(listRollUpRuleLineElement.getDataType().getNarrative());
        dataTypeWithMeasureLenghtDTO.setDataTypeDescription(listRollUpRuleLineElement.getDataType().getDescription());
        dataTypeWithMeasureLenghtDTO.setMeasureClass(listRollUpRuleLineElement.getDataType().getMeasureClass());
        dataTypeWithMeasureLenghtDTO.setMeasureClassName(DataTypesUtil.getMeasureClassName(listRollUpRuleLineElement.getDataType().getMeasureClass()));
        dataTypeWithMeasureLenghtDTO.setSubType(listRollUpRuleLineElement.getDataType().getSubType());
        dataTypeWithMeasureLenghtDTO.setSubTypeName(DataTypesUtil.getSubTypeName(listRollUpRuleLineElement.getDataType().getSubType()));
        dataTypeWithMeasureLenghtDTO.setMeasureLength(listRollUpRuleLineElement.getDataType().getMeasureLength());
        rollUpRuleLineDTO.setDataType(dataTypeWithMeasureLenghtDTO);
    }

    /**
     * Map DimensionRef[] to DimensionDTO[] and add DimensionDTO[] to RollUpRuleLineDTO. DimensionRef[] is property of RollUpRuleLine.
     */
    private static void addDimensionsToRollUpRuleLineDTO(DimensionRef[] dimension, RollUpRuleLineDTO rollUpRuleLineDTO, RollUpRuleLine listRollUpRuleLineElement) {
        boolean selected;
        int lenght = dimension.length;
        RollUpRuleDTO[] rollUpRuleDTO = new RollUpRuleDTO[lenght];
        for (int i = 0; i < lenght; i++) {
            rollUpRuleDTO[i] = new RollUpRuleDTO();
        }
        // set account in first step, business in second step and calendar in last step dimensions
        for (int i = 0; i < lenght; i++) {
            selected = listRollUpRuleLineElement.rollsUp(dimension[i]);
            rollUpRuleDTO[i].setRollUp(selected);
            DimensionDTO dimensionDTO = new DimensionDTO();
            dimensionDTO.setDimensionId(((DimensionRefImpl) dimension[i]).getDimensionPK().getDimensionId());
            dimensionDTO.setDimensionVisId(dimension[i].getNarrative());
            dimensionDTO.setType(dimension[i].getType());
            rollUpRuleDTO[i].setDimension(dimensionDTO);
        }
        rollUpRuleLineDTO.setRollUpRules(rollUpRuleDTO);
    }

    /**
     * Map Set<DataTypeRefImpl> to List<DataTypeWithMeasureLenghtDTO> and set List<DataTypeWithMeasureLenghtDTO> for FinanceCubeDetailsDTO
     * as value property mappedDataTypes. DataTypeRefImpl is property of FinanceCubeImpl.
     */
    @SuppressWarnings("unchecked")
    private static void setMappedDataTypesDTO(FinanceCubeDetailsDTO financeCubeDetailsDTO, FinanceCubeImpl financeCubeImpl) {
        Set<DataTypeRefImpl> dataTypesRef = financeCubeImpl.getMappedDataTypes();
        List<DataTypeWithMeasureLenghtDTO> dataTypesMappedList = new ArrayList<DataTypeWithMeasureLenghtDTO>();
        for (DataTypeRefImpl dataTypeElement: dataTypesRef) {
            DataTypeWithMeasureLenghtDTO dataTypesMapped = new DataTypeWithMeasureLenghtDTO();
            dataTypesMapped.setDataTypeId(((DataTypePK) dataTypeElement.getPrimaryKey()).getDataTypeId());
            dataTypesMapped.setDataTypeVisId(dataTypeElement.getNarrative());
            dataTypesMapped.setDataTypeDescription(dataTypeElement.getDescription());
            dataTypesMapped.setMeasureClass(dataTypeElement.getMeasureClass());
            dataTypesMapped.setMeasureClassName(DataTypesUtil.getMeasureClassName(dataTypeElement.getMeasureClass()));
            dataTypesMapped.setSubType(dataTypeElement.getSubType());
            dataTypesMapped.setSubTypeName(DataTypesUtil.getSubTypeName(dataTypeElement.getSubType()));
            dataTypesMapped.setMeasureLength(dataTypeElement.getMeasureLength());
            dataTypesMappedList.add(dataTypesMapped);
        }
        financeCubeDetailsDTO.setMappedDataTypes(dataTypesMappedList);
    }

    /**
     * Map FinanceCubeDetailsDTO to FinanceCubeImpl.
     */
    public static FinanceCubeImpl mapFinanceCubeDTO(FinanceCubeDetailsDTO financeCubeDetailsDTO, FinanceCubeImpl financeCubeImpl) {
        financeCubeImpl.setSubmitChangeManagement(financeCubeDetailsDTO.isSubmitChangeManagement());
        financeCubeImpl.setInsideChangeManagement(financeCubeDetailsDTO.isInsideChangeManagement());
        financeCubeImpl.setChangeManagementOutstanding(financeCubeDetailsDTO.isChangeManagementOutstanding());
        financeCubeImpl.setVisId(financeCubeDetailsDTO.getFinanceCubeVisId());
        financeCubeImpl.setDescription(financeCubeDetailsDTO.getFinanceCubeDescription());
        financeCubeImpl.setAudited(financeCubeDetailsDTO.isAudited());
        financeCubeImpl.setCubeFormulaEnabled(financeCubeDetailsDTO.isCubeFormulaEnabled());

        addDataTypeTable(financeCubeDetailsDTO, financeCubeImpl);
        addRollUpRuleTable(financeCubeDetailsDTO, financeCubeImpl);
        setMappedDataTypes(financeCubeDetailsDTO, financeCubeImpl);

        financeCubeImpl.setVersionNum(financeCubeDetailsDTO.getVersionNum());

        return financeCubeImpl;
    }

    /**
     * Map List<DataTypeWithTimestampDTO> to Map<DataTypeRef, Timestamp> dataTypeHashMap and add to FinanceCubeImpl. First step: clear old data.
     * Second step: create elements DataTypeRef's and put it with Timestamps from actually data. DataTypeWithTimestampDTO is property of FinanceCubeDetailsDTO.
     */
    private static void addDataTypeTable(FinanceCubeDetailsDTO financeCubeDetailsDTO, FinanceCubeImpl financeCubeImpl) {
        List<DataTypeWithTimestampDTO> listDataTypeWithTimestampDTO = financeCubeDetailsDTO.getDataTypes();
        financeCubeImpl.getSelectedDataTypeRefs().clear();
        for (DataTypeWithTimestampDTO listElement: listDataTypeWithTimestampDTO) {
            DataTypePK key = new DataTypePK((short) listElement.getDataTypeId());
            DataTypeRef dataTypeRef = new DataTypeRefImpl(key, listElement.getDataTypeVisId(), listElement.getDataTypeDescription(), listElement.getSubType(), listElement.getMeasureClass(), listElement.getMeasureLength());
            financeCubeImpl.addSelectedDataTypeRef(dataTypeRef, listElement.getCubeLastUpdated());
        }
    }

    /**
     * Map List<RollUpRuleLineDTO> to List<RollUpRuleLine>. List<RollUpRuleLine> is property of FinanceCubeImpl.
     * List<RollUpRuleLineDTO> is property of FinanceCubeDetailsDTO.
     */
    private static void addRollUpRuleTable(FinanceCubeDetailsDTO financeCubeDetailsDTO, FinanceCubeImpl financeCubeImpl) {
        List<RollUpRuleLineDTO> listRollUpRuleLineDTO = financeCubeDetailsDTO.getRollUpRuleLines();
        List<RollUpRuleLine> listRollUpRuleLine = financeCubeImpl.getRollUpRuleLines();
        listRollUpRuleLine.clear();
        for (RollUpRuleLineDTO listElement: listRollUpRuleLineDTO) {
            List<RollUpRule> listRollUpRule = new ArrayList<RollUpRule>();
            DataTypeWithMeasureLenghtDTO dataType = listElement.getDataType();
            DataTypeRefImpl dataTypeRefImpl = new DataTypeRefImpl(new DataTypePK((short) dataType.getDataTypeId()), dataType.getDataTypeVisId(), dataType.getDataTypeDescription(), dataType.getSubType(), dataType.getMeasureClass(), dataType.getMeasureLength());
            RollUpRuleLine rollUpRuleLine = new RollUpRuleLineImpl(financeCubeImpl, dataTypeRefImpl, listRollUpRule);
            RollUpRuleDTO rollUpRuleElement;
            DimensionRefImpl dimension;
            int lenght = listElement.getRollUpRules().length;
            for (int i = 0; i < lenght; i++) {
                rollUpRuleElement = listElement.getRollUpRules()[i];
                dimension = new DimensionRefImpl(new DimensionPK(rollUpRuleElement.getDimension().getDimensionId()), rollUpRuleElement.getDimension().getDimensionVisId(), rollUpRuleElement.getDimension().getType());
                listRollUpRule.add(new RollUpRuleImpl((RollUpRuleLineImpl) rollUpRuleLine, dimension, rollUpRuleElement.isRollUp()));
            }
            listRollUpRuleLine.add(rollUpRuleLine);
        }
    }

    /**
     * Map List<DataTypeWithMeasureLenghtDTO> to Set<DataTypeRefImpl> and set Set<DataTypeRefImpl> for FinanceCubeImpl
     * as value property mappedDataTypes. List<DataTypeWithMeasureLenghtDTO> is property of FinanceCubeDetailsDTO.
     */
    private static void setMappedDataTypes(FinanceCubeDetailsDTO financeCubeDetailsDTO, FinanceCubeImpl financeCubeImpl) {
        List<DataTypeWithMeasureLenghtDTO> listDataTypeWithMeasureLenghtDTO = financeCubeDetailsDTO.getMappedDataTypes();
        Set<DataTypeRef> mappedSet = new HashSet<DataTypeRef>();
        financeCubeImpl.getMappedDataTypes().clear();
        for (DataTypeWithMeasureLenghtDTO listElement: listDataTypeWithMeasureLenghtDTO) {
            DataTypePK key = new DataTypePK((short) listElement.getDataTypeId());
            DataTypeRef dataTypeRef = new DataTypeRefImpl(key, listElement.getDataTypeVisId(), listElement.getDataTypeDescription(), listElement.getSubType(), listElement.getMeasureClass(), listElement.getMeasureLength());
            mappedSet.add(dataTypeRef);
        }
        financeCubeImpl.setMappedDataTypes(mappedSet);
    }

    /**
     * Special map FinanceCubeDetailsDTO to FinanceCubeImpl when Finance Cube is created and it's necesary set Model.
     */
    public static FinanceCubeImpl mapFinanceCubeDTO_WithModel(FinanceCubeDetailsDTO financeCubeDetailsDTO, FinanceCubeImpl financeCubeImpl) {
        financeCubeImpl = mapFinanceCubeDTO(financeCubeDetailsDTO, financeCubeImpl);
        ModelPK key = new ModelPK(financeCubeDetailsDTO.getModel().getModelId());
        financeCubeImpl.setModelRef(new ModelRefImpl(key, financeCubeDetailsDTO.getModel().getModelVisId()));

        return financeCubeImpl;
    }

    /**
     * Map AllDimensionsForModelELO to List<DimensionDTO>.
     */
    @SuppressWarnings("unchecked")
    public static List<DimensionDTO> mapAllDimensionsForModel(AllDimensionsForModelELO elo) {
        List<DimensionDTO> dimensionList = new ArrayList<DimensionDTO>();

        for (Iterator<AllDimensionsForModelELO> it = elo.iterator(); it.hasNext();) {
            AllDimensionsForModelELO row = it.next();

            DimensionDTO dimensionDTO = new DimensionDTO();
            dimensionDTO.setDimensionId(row.getDimensionId());
            dimensionDTO.setDimensionVisId(row.getVisId());
            dimensionDTO.setType(row.getType());

            dimensionList.add(dimensionDTO);
        }

        return dimensionList;
    }

}
