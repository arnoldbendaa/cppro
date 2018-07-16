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
package com.softproideas.app.admin.financecubeformula.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.cedar.cp.api.cubeformula.FormulaDeploymentLine;
import com.cedar.cp.api.datatype.DataTypeRef;
import com.cedar.cp.api.dimension.DimensionRef;
import com.cedar.cp.api.dimension.HierarchyNode;
import com.cedar.cp.api.dimension.StructureElementRef;
import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.api.model.ModelRef;
import com.cedar.cp.dto.cubeformula.AllCubeFormulasELO;
import com.cedar.cp.dto.cubeformula.CubeFormulaImpl;
import com.cedar.cp.dto.cubeformula.CubeFormulaRefImpl;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentLineImpl;
import com.cedar.cp.dto.cubeformula.FormulaDeploymentLinePK;
import com.cedar.cp.dto.datatype.AllDataTypesForModelELO;
import com.cedar.cp.dto.datatype.DataTypePK;
import com.cedar.cp.dto.datatype.DataTypeRefImpl;
import com.cedar.cp.dto.dimension.AugHierarchyElementPK;
import com.cedar.cp.dto.dimension.CalendarForModelELO;
import com.cedar.cp.dto.dimension.DimensionPK;
import com.cedar.cp.dto.dimension.DimensionRefImpl;
import com.cedar.cp.dto.dimension.HierarchyCK;
import com.cedar.cp.dto.dimension.HierarchyElementCK;
import com.cedar.cp.dto.dimension.HierarchyElementFeedImpl;
import com.cedar.cp.dto.dimension.HierarchyElementFeedPK;
import com.cedar.cp.dto.dimension.HierarchyElementImpl;
import com.cedar.cp.dto.dimension.StructureElementPK;
import com.cedar.cp.dto.dimension.StructureElementRefImpl;
import com.cedar.cp.dto.dimension.calendar.CalendarImpl;
import com.cedar.cp.dto.model.FinanceCubeRefImpl;
import com.cedar.cp.dto.model.HierarchiesForModelELO;
import com.cedar.cp.dto.model.ModelRefImpl;
import com.cedar.cp.util.Pair;
import com.softproideas.app.admin.datatypes.model.DataTypeDTO;
import com.softproideas.app.admin.financecubeformula.model.DeploymentDimensionEntries;
import com.softproideas.app.admin.financecubeformula.model.FinanceCubeFormulaDTO;
import com.softproideas.app.admin.financecubeformula.model.FinanceCubeFormulaDetailsDTO;
import com.softproideas.app.admin.financecubeformula.model.FormulaDeploymentLineDTO;
import com.softproideas.app.admin.financecubeformula.util.TypeComparator;
import com.softproideas.app.admin.financecubes.model.DimensionDTO;
import com.softproideas.app.admin.hierarchies.model.HierarchyNodeLazyDTO;
import com.softproideas.app.core.financecube.mapper.FinanceCubeCoreMapper;
import com.softproideas.app.core.financecube.model.FinanceCubeCoreDTO;
import com.softproideas.app.core.model.mapper.ModelCoreMapper;
import com.softproideas.app.core.model.model.ModelCoreDTO;

public class FinanceCubeFormulaMapper {

    /**
     * Map AllCubeFormulasELO to List<FinanceCubeFormulaDTO>.
     */
    @SuppressWarnings("unchecked")
    public static List<FinanceCubeFormulaDTO> mapAllCubeFormulasELO(AllCubeFormulasELO elo) {
        List<FinanceCubeFormulaDTO> financeCubeFormulaList = new ArrayList<FinanceCubeFormulaDTO>();
        for (Iterator<AllCubeFormulasELO> it = elo.iterator(); it.hasNext();) {
            AllCubeFormulasELO row = it.next();
            FinanceCubeFormulaDTO financeCubeFormulaDTO = new FinanceCubeFormulaDTO();
            // Model
            ModelRefImpl modelRefImpl = (ModelRefImpl) row.getModelEntityRef();
            ModelCoreDTO modelCoreDTO = new ModelCoreDTO();
            modelCoreDTO.setModelId(modelRefImpl.getModelPK().getModelId());
            modelCoreDTO.setModelVisId(modelRefImpl.getNarrative());
            modelCoreDTO.setModelDescription(modelRefImpl.getDisplayText());
            financeCubeFormulaDTO.setModel(modelCoreDTO);
            // FinanceCubeDTO
            FinanceCubeRefImpl financeCubeRefImpl = (FinanceCubeRefImpl) row.getFinanceCubeEntityRef();
            FinanceCubeCoreDTO financeCubeCoreDTO = new FinanceCubeCoreDTO();
            financeCubeCoreDTO.setFinanceCubeId(financeCubeRefImpl.getFinanceCubePK().getFinanceCubeId());
            financeCubeCoreDTO.setFinanceCubeVisId(financeCubeRefImpl.getNarrative());
            financeCubeCoreDTO.setFinanceCubeDescription(financeCubeRefImpl.getDisplayText());
            financeCubeFormulaDTO.setFinanceCube(financeCubeCoreDTO);
            // CubeFormulaRef
            CubeFormulaRefImpl cubeFormulaRefImpl = (CubeFormulaRefImpl) row.getCubeFormulaEntityRef();
            financeCubeFormulaDTO.setFinanceCubeFormulaId(cubeFormulaRefImpl.getCubeFormulaPK().getCubeFormulaId());
            financeCubeFormulaDTO.setFinanceCubeFormulaVisId(cubeFormulaRefImpl.getNarrative());
            financeCubeFormulaDTO.setDeployed(row.getDeploymentInd());
            financeCubeFormulaDTO.setFinanceCubeFormulaDescription(row.getDescription());
            financeCubeFormulaDTO.setType(row.getFormulaType());
            // add
            financeCubeFormulaList.add(financeCubeFormulaDTO);
        }
        return financeCubeFormulaList;
    }

    /**
     * Map CubeFormulaImpl to FinanceCubeFormulaDetailsDTO.
     * @param calendarImpl 
     * 
     */
    public static FinanceCubeFormulaDetailsDTO mapFinanceCubeFormulaImpl(CubeFormulaImpl financeCubeFormulaImpl, int modelId, int financeCubeId, int financeCubeFormulaId, CalendarImpl calendarImpl) {

        FinanceCubeFormulaDetailsDTO financeCubeDetailsDTO = new FinanceCubeFormulaDetailsDTO();
        // Set Finance Cube Formula Details
        financeCubeDetailsDTO.setFormulaText(financeCubeFormulaImpl.getFormulaText());
        financeCubeDetailsDTO.setFinanceCubeFormulaEnabled(financeCubeFormulaImpl.isFinanceCubeFormulaEnabled());
        financeCubeDetailsDTO.setDeploymentInd(financeCubeFormulaImpl.isDeploymentInd());
        financeCubeDetailsDTO.setFinanceCubeFormulaDescription(financeCubeFormulaImpl.getDescription());
        financeCubeDetailsDTO.setFinanceCubeFormulaVisId(financeCubeFormulaImpl.getVisId());
        financeCubeDetailsDTO.setFinanceCubeFormulaId(financeCubeFormulaId);
        financeCubeDetailsDTO.setType(financeCubeFormulaImpl.getFormulaType());
        financeCubeDetailsDTO.setDeployed(financeCubeFormulaImpl.isDeploymentInd());
        // Model
        ModelRef modelRef = (ModelRef) financeCubeFormulaImpl.getModelRef();
        ModelCoreDTO modelDTO = new ModelCoreDTO();
        String modelVisId = modelRef.getNarrative();
        String modelDescription = modelRef.getDisplayText();
        modelDTO.setModelDescription(modelDescription);
        modelDTO.setModelVisId(modelVisId);
        modelDTO.setModelId(modelId);
        financeCubeDetailsDTO.setModel(modelDTO);
        // FinanceCube
        FinanceCubeRef financeCubeRef = (FinanceCubeRef) financeCubeFormulaImpl.getFinanceCubeRef();
        FinanceCubeCoreDTO financeCubeDTO = new FinanceCubeCoreDTO();
        String financeCubeVisId = financeCubeRef.getNarrative();
        String financeCubeDescription = financeCubeRef.getDisplayText();
        financeCubeDTO.setFinanceCubeDescription(financeCubeDescription);
        financeCubeDTO.setFinanceCubeVisId(financeCubeVisId);
        financeCubeDTO.setFinanceCubeId(financeCubeId);
        financeCubeDetailsDTO.setFinanceCube(financeCubeDTO);
        addDimensionRefImpl(financeCubeDetailsDTO, financeCubeFormulaImpl);
        List<FormulaDeploymentLineDTO> formulaDeploymentLine = mapFormulaDeploymentLine(financeCubeFormulaImpl.getDeploymentLines(), financeCubeDetailsDTO, calendarImpl);
        financeCubeDetailsDTO.setFormulaLine(formulaDeploymentLine);
        return financeCubeDetailsDTO;
    }

    /**
     * Map formula line 
     * @param calendarImpl 
     */
    private static List<FormulaDeploymentLineDTO> mapFormulaDeploymentLine(List<FormulaDeploymentLine> deploymentLines, FinanceCubeFormulaDetailsDTO financeCubeDetailsDTO, CalendarImpl calendarImpl) {
        List<FormulaDeploymentLineDTO> listFormulaDeploymentLine = new ArrayList<FormulaDeploymentLineDTO>();
        for (FormulaDeploymentLine formula: deploymentLines) {
            FormulaDeploymentLineDTO formulaDTO = new FormulaDeploymentLineDTO();
            formulaDTO.setDataTypes(mapDataTypeRefHashSet(formula.getDeploymentDataTypes()));
            formulaDTO.setDimensions(financeCubeDetailsDTO.getDimensions());
            formulaDTO.setLineIndex(formula.getLineIndex());
            formulaDTO.setDeploymentDimensionEntries(mapFormulaDeploymentEntries(formula.getDeploymentEntries(), formulaDTO, calendarImpl));
            FormulaDeploymentLinePK linePK = (FormulaDeploymentLinePK) formula.getKey();
            formulaDTO.setFormulaDeploymentId(linePK.getFormulaDeploymentLineId());
            listFormulaDeploymentLine.add(formulaDTO);
        }
        return listFormulaDeploymentLine;
    }

    /**
     * Map formula dimension line - cc, exp , cal 
     * @param calendarImpl 
     */
    private static List<DeploymentDimensionEntries> mapFormulaDeploymentEntries(Map<DimensionRef, Map<Pair<StructureElementRef, StructureElementRef>, Boolean>> deploymentEntries, FormulaDeploymentLineDTO formulaDTO, CalendarImpl calendarImpl) {
        List<DeploymentDimensionEntries> list = new ArrayList<DeploymentDimensionEntries>();
        for (Entry<DimensionRef, Map<Pair<StructureElementRef, StructureElementRef>, Boolean>> deplEntries: deploymentEntries.entrySet()) {
            for (Entry<Pair<StructureElementRef, StructureElementRef>, Boolean> pair: deplEntries.getValue().entrySet()) {
                DeploymentDimensionEntries entries = new DeploymentDimensionEntries();
                StructureElementPK elementPK = (StructureElementPK) pair.getKey().getChild1().getPrimaryKey();
                entries.setStructureElementId(elementPK.getStructureElementId());
                Enumeration rootChildren = calendarImpl.getRoot().children();
                while (rootChildren.hasMoreElements()) {
                    HierarchyElementImpl x = (HierarchyElementImpl) rootChildren.nextElement();
                    for (int i = 0; i < x.getChildCount(); i++) {
                        HierarchyElementFeedImpl child = (HierarchyElementFeedImpl) x.getChildAt(i);
                        HierarchyElementFeedPK pk = (HierarchyElementFeedPK) child.getPrimaryKey();
                        if (elementPK.getStructureElementId() == pk.getDimensionElementId()) {
                            entries.setParentVisId(x.getVisId());
                        }
                    }
                }
                entries.setStructureElementVisId(pair.getKey().getChild1().getNarrative());
                entries.setStructureId(Integer.toString(elementPK.getStructureId()));
                entries.setValue(pair.getValue());
                entries.setDimension(mapDimensionRef(deplEntries.getKey()));
                list.add(entries);
            }
        }
        return list;

    }

    /**
     * Map dimensionRef like a key for dimension line 
     */
    private static DimensionDTO mapDimensionRef(DimensionRef key) {
        DimensionDTO dimensionDTO = new DimensionDTO();
        dimensionDTO.setDimensionVisId(key.getNarrative());
        DimensionPK dimPK = (DimensionPK) key.getPrimaryKey();
        dimensionDTO.setDimensionId(dimPK.getDimensionId());
        dimensionDTO.setType(key.getType());
        return dimensionDTO;
    }

    /**
     * Map DataTypeRef for formulaDeploymentLine to DataTypeDTO
     */
    private static List<DataTypeDTO> mapDataTypeRefHashSet(Set<DataTypeRef> deploymentDataTypes) {
        List<DataTypeDTO> list = new ArrayList<DataTypeDTO>();
        Iterator<DataTypeRef> iterator = deploymentDataTypes.iterator();
        while (iterator.hasNext()) {
            DataTypeDTO dataTypeDTO = new DataTypeDTO();
            DataTypeRef dataTypeRef = iterator.next();
            dataTypeDTO.setSubType(dataTypeRef.getSubType());
            dataTypeDTO.setDataTypeDescription(dataTypeRef.getDescription());
            DataTypePK dataTypePK = (DataTypePK) dataTypeRef.getPrimaryKey();
            dataTypeDTO.setDataTypeId(dataTypePK.getDataTypeId());
            dataTypeDTO.setMeasureClass(dataTypeRef.getMeasureClass());
            dataTypeDTO.setDataTypeVisId(dataTypeRef.getNarrative());
            list.add(dataTypeDTO);
        }
        return list;
    }

    /**
     * Method addDimensionRefImpl, and sort by dimension type
     */
    public static void addDimensionRefImpl(FinanceCubeFormulaDetailsDTO financeCubeDetailsDTO, CubeFormulaImpl financeCubeFormulaImpl) {
        DimensionRef[] dimensionRefImpl = financeCubeFormulaImpl.getDimensionRefs();
        DimensionDTO[] dimensionDTO = new DimensionDTO[dimensionRefImpl.length];
        int i = 0;
        for (DimensionRef dimension: dimensionRefImpl) {
            dimensionDTO[i] = new DimensionDTO();
            dimensionDTO[i].setDimensionId(((DimensionRefImpl) dimension).getDimensionPK().getDimensionId());
            dimensionDTO[i].setDimensionVisId(dimension.getNarrative());
            dimensionDTO[i].setType(dimension.getType());
            i++;
        }
        Arrays.sort(dimensionDTO, new TypeComparator());
        financeCubeDetailsDTO.setDimensions(dimensionDTO);
    }

    /**
     * Map CubeFormulaDetialsDTO and return new CubeFormulaImpl
     */
    public static CubeFormulaImpl mapCubeFormulaDetailsToImpl(FinanceCubeFormulaDetailsDTO financeCubeFormulaDetailsDTO, CubeFormulaImpl financeCubeFormulaImpl) {
        financeCubeFormulaImpl.setDeploymentInd(financeCubeFormulaDetailsDTO.isDeploymentInd());
        financeCubeFormulaImpl.setFinanceCubeFormulaEnabled(financeCubeFormulaDetailsDTO.isFinanceCubeFormulaEnabled());
        financeCubeFormulaImpl.setFormulaText(financeCubeFormulaDetailsDTO.getFormulaText());
        financeCubeFormulaImpl.setModelRef(ModelCoreMapper.mapModelCoreDTOtoModelRefImpl(financeCubeFormulaDetailsDTO.getModel()));
        financeCubeFormulaImpl.setFinanceCubeRef(FinanceCubeCoreMapper.mapFinanceCubeCoreDTOtoFinanceCubeRefImpl(financeCubeFormulaDetailsDTO.getFinanceCube(), financeCubeFormulaDetailsDTO.getModel()));
        financeCubeFormulaImpl.setVisId(financeCubeFormulaDetailsDTO.getFinanceCubeFormulaVisId());
        financeCubeFormulaImpl.setDescription(financeCubeFormulaDetailsDTO.getFinanceCubeFormulaDescription());
        financeCubeFormulaImpl.setFormulaType(financeCubeFormulaDetailsDTO.getType());
        financeCubeFormulaImpl.setDeploymentLines(mapFormulaDeploymentLineDTO(financeCubeFormulaDetailsDTO.getFormulaLine()));
        return financeCubeFormulaImpl;
    }

    /**
     * Map FormulaDeploymentLineDTO to FormulaDeploymentLine
     */
    private static List<FormulaDeploymentLine> mapFormulaDeploymentLineDTO(List<FormulaDeploymentLineDTO> formulaLine) {
        List<FormulaDeploymentLine> lineList = new ArrayList<FormulaDeploymentLine>();
        for (FormulaDeploymentLineDTO formulaLineDTO: formulaLine) {
            FormulaDeploymentLineImpl lineImpl = new FormulaDeploymentLineImpl(mapDimensionDTO(formulaLineDTO.getDimensions()));
            lineImpl.setDeploymentDataTypes(mapDataTypes(formulaLineDTO.getDataTypes()));
            FormulaDeploymentLinePK linePk = new FormulaDeploymentLinePK(formulaLineDTO.getFormulaDeploymentId());
            lineImpl.setKey(linePk);
            lineImpl.setLineIndex(formulaLineDTO.getLineIndex());
            lineImpl.setDeploymentEntries(mapDeploymentEntries(formulaLineDTO.getDeploymentDimensionEntries()));
            lineList.add(lineImpl);
        }
        return lineList;
    }

    /**
     * Map DeploymentEntries to hashMap Map<DimensionRef, Map<Pair<StructureElementRef, StructureElementRef>, Boolean>>
     */
    private static Map<DimensionRef, Map<Pair<StructureElementRef, StructureElementRef>, Boolean>> mapDeploymentEntries(List<DeploymentDimensionEntries> deploymentDimensionEntries) {
        Map<DimensionRef, Map<Pair<StructureElementRef, StructureElementRef>, Boolean>> deploymentEntries = new HashMap<DimensionRef, Map<Pair<StructureElementRef, StructureElementRef>, Boolean>>();
        DimensionRefImpl dimRefImpl = null;
        Map<Pair<StructureElementRef, StructureElementRef>, Boolean> hashMapPair = new HashMap<Pair<StructureElementRef, StructureElementRef>, Boolean>();
        for (int i = 0; i < deploymentDimensionEntries.size(); i++) {

            DeploymentDimensionEntries obj = deploymentDimensionEntries.get(i);
            if (obj.getDimension().getType() == 2) {
                DimensionPK dimPK = new DimensionPK(obj.getDimension().getDimensionId());
                String dimNarrative = obj.getDimension().getDimensionVisId();
                int dimType = obj.getDimension().getType();
                dimRefImpl = new DimensionRefImpl(dimPK, dimNarrative, dimType);
                StructureElementPK strucElementPK = new StructureElementPK(Integer.parseInt(obj.getStructureId()), obj.getStructureElementId());
                StructureElementRefImpl structureElementRefImpl = new StructureElementRefImpl(strucElementPK, obj.getStructureElementVisId());
                Pair<StructureElementRef, StructureElementRef> pair = new Pair<StructureElementRef, StructureElementRef>(structureElementRefImpl, null);
                hashMapPair.put(pair, true);
                deploymentEntries.put(dimRefImpl, hashMapPair);
            }
        }
        Map<Pair<StructureElementRef, StructureElementRef>, Boolean> hashMapPairExp = new HashMap<Pair<StructureElementRef, StructureElementRef>, Boolean>();
        for (int i = 0; i < deploymentDimensionEntries.size(); i++) {

            DeploymentDimensionEntries obj = deploymentDimensionEntries.get(i);
            if (obj.getDimension().getType() == 1) {
                DimensionPK dimPK = new DimensionPK(obj.getDimension().getDimensionId());
                String dimNarrative = obj.getDimension().getDimensionVisId();
                int dimType = obj.getDimension().getType();
                dimRefImpl = new DimensionRefImpl(dimPK, dimNarrative, dimType);
                StructureElementPK strucElementPK = new StructureElementPK(Integer.parseInt(obj.getStructureId()), obj.getStructureElementId());
                StructureElementRefImpl structureElementRefImpl = new StructureElementRefImpl(strucElementPK, obj.getStructureElementVisId());
                Pair<StructureElementRef, StructureElementRef> pair = new Pair<StructureElementRef, StructureElementRef>(structureElementRefImpl, null);
                hashMapPairExp.put(pair, true);
                deploymentEntries.put(dimRefImpl, hashMapPairExp);
            }
        }
        Map<Pair<StructureElementRef, StructureElementRef>, Boolean> hashMapPairCal = new HashMap<Pair<StructureElementRef, StructureElementRef>, Boolean>();
        for (int i = 0; i < deploymentDimensionEntries.size(); i++) {

            DeploymentDimensionEntries obj = deploymentDimensionEntries.get(i);
            if (obj.getDimension().getType() == 3) {
                DimensionPK dimPK = new DimensionPK(obj.getDimension().getDimensionId());
                String dimNarrative = obj.getDimension().getDimensionVisId();
                int dimType = obj.getDimension().getType();
                dimRefImpl = new DimensionRefImpl(dimPK, dimNarrative, dimType);
                StructureElementPK strucElementPK = new StructureElementPK(Integer.parseInt(obj.getStructureId()), obj.getStructureElementId());
                StructureElementRefImpl structureElementRefImpl = new StructureElementRefImpl(strucElementPK, obj.getStructureElementVisId());
                Pair<StructureElementRef, StructureElementRef> pair = new Pair<StructureElementRef, StructureElementRef>(structureElementRefImpl, null);
                hashMapPairCal.put(pair, true);
                deploymentEntries.put(dimRefImpl, hashMapPairCal);
            }
        }
        return deploymentEntries;
    }

    /**
     * Map DataTypeDTO to hashSet DataTypeRef
     */
    private static Set<DataTypeRef> mapDataTypes(List<DataTypeDTO> dataTypes) {
        HashSet<DataTypeRef> hashSet = new HashSet<DataTypeRef>();
        for (DataTypeDTO dataType: dataTypes) {
            DataTypePK dataTypePk = new DataTypePK((short) dataType.getDataTypeId());
            DataTypeRefImpl x = new DataTypeRefImpl(dataTypePk, dataType.getDataTypeVisId(), dataType.getDataTypeDescription(), dataType.getSubType(), dataType.getMeasureClass(), dataType.getMeasureClass());
            hashSet.add(x);
        }
        return hashSet;
    }

    /**
     * Map DimensionDTO[] to dimensionRef[]
     */
    private static DimensionRef[] mapDimensionDTO(DimensionDTO[] dimensions) {
        DimensionRef[] dimRef = new DimensionRefImpl[dimensions.length];
        int i = 0;
        for (DimensionDTO dimDto: dimensions) {
            DimensionPK dimPK = new DimensionPK(dimDto.getDimensionId());
            String narrative = dimDto.getDimensionVisId();
            int type = dimDto.getType();
            dimRef[i] = new DimensionRefImpl(dimPK, narrative, type);
            i++;
        }
        return dimRef;
    }

    /**
     * Get dataTypes tree
     */
    @SuppressWarnings("unchecked")
    public static List<HierarchyNodeLazyDTO> getDataTypesNode(AllDataTypesForModelELO dataTypesELO) {
        List<HierarchyNodeLazyDTO> dataTypeList = new ArrayList<HierarchyNodeLazyDTO>();
        for (Iterator<AllDataTypesForModelELO> it = dataTypesELO.iterator(); it.hasNext();) {
            HierarchyNodeLazyDTO nodeLazyDTO = new HierarchyNodeLazyDTO();
            AllDataTypesForModelELO row = it.next();
            DataTypeRefImpl dataTypeRefImpl = (DataTypeRefImpl) row.getDataTypeEntityRef();
            nodeLazyDTO.setId("dataType/" + dataTypeRefImpl.getDataTypePK().getDataTypeId());
            nodeLazyDTO.setText(dataTypeRefImpl.getNarrative() + " - " + row.getDataTypeEntityRef().getDescription());
            nodeLazyDTO.setStateLeaf(true);
            nodeLazyDTO.setTextNode(dataTypeRefImpl.getNarrative());
            nodeLazyDTO.setDescription(row.getDataTypeEntityRef().getDescription());
            dataTypeList.add(nodeLazyDTO);
        }

        return dataTypeList;

    }

    /**
     * Get dataTypes root
     * @param disableLevel 
     */
    public static List<HierarchyNodeLazyDTO> dataTypeRoot(int modelId, String disableLevel) {
        List<HierarchyNodeLazyDTO> dataTypeRootName = new ArrayList<HierarchyNodeLazyDTO>();
        HierarchyNodeLazyDTO nodeLazyDTO = new HierarchyNodeLazyDTO();
        nodeLazyDTO.setText("Data Types");
        nodeLazyDTO.setChildren(true);
        modelId=1;
        nodeLazyDTO.setId("dataType/" + modelId);
        nodeLazyDTO.setStateOpened(true);
        if (disableLevel != null && disableLevel.equals("1")) {
            nodeLazyDTO.setStateDisabled(true);
        }
        dataTypeRootName.add(nodeLazyDTO);
        return dataTypeRootName;
    }

    /**
     * Get calendar tree root
     */
    public static List<HierarchyNodeLazyDTO> getCalendarNode(CalendarImpl calendarImpl, String disableLevel) {
        List<HierarchyNodeLazyDTO> calendarRoot = mapCalendarRoot(calendarImpl.getRoot(), disableLevel);
        return calendarRoot;
    }

    /**
     * Create calendar root
     * @param disableLevel 
     */
    private static List<HierarchyNodeLazyDTO> mapCalendarRoot(HierarchyNode root, String disableLevel) {
        List<HierarchyNodeLazyDTO> list = new ArrayList<HierarchyNodeLazyDTO>();
        HierarchyNodeLazyDTO nodeLazy = new HierarchyNodeLazyDTO();
        if (root instanceof HierarchyElementImpl) {
            nodeLazy.setText(root.getVisId() + "-" + root.getDescription());
            nodeLazy.setChildren(true);
            if (disableLevel != null && (disableLevel.equals("1") || disableLevel.equals("2"))) {
                nodeLazy.setStateDisabled(true);
            }
            HierarchyElementCK hierCK = (HierarchyElementCK) root.getPrimaryKey();
            nodeLazy.setId("hierarchyElement/" + hierCK.getHierarchyElementPK().getHierarchyElementId());
            nodeLazy.setStructureId(hierCK.getHierarchyPK().getHierarchyId());
            nodeLazy.setDescription(root.getDescription());
            nodeLazy.setTextNode(root.getVisId());
            list.add(nodeLazy);
        }
        return list;
    }

    /**
     *  Browse calendar root
     */
    private static List<HierarchyNodeLazyDTO> mapNode(HierarchyNode root, int calendarId) {
        List<HierarchyNodeLazyDTO> nodesList = new ArrayList<HierarchyNodeLazyDTO>();
        HierarchyNode findedNode = findElementInNode(root, calendarId);
        for (int i = 0; i < findedNode.getChildCount(); i++) {
            HierarchyNodeLazyDTO nodeLazyDTO = new HierarchyNodeLazyDTO();
            if (findedNode.getChildAt(i) instanceof HierarchyElementFeedImpl) {
                HierarchyElementFeedImpl lastLevel = (HierarchyElementFeedImpl) findedNode.getChildAt(i);
                nodeLazyDTO.setText(lastLevel.getVisId() + " - " + lastLevel.getDescription());
                HierarchyElementFeedPK pk2 = (HierarchyElementFeedPK) lastLevel.getPrimaryKey();
                HierarchyCK hierarchyCK = (HierarchyCK) root.getPrimaryKey();
                nodeLazyDTO.setStructureId(hierarchyCK.getHierarchyPK().getHierarchyId());
                nodeLazyDTO.setStateLeaf(true);
                nodeLazyDTO.setId("dimensionElement/" + Integer.toString(pk2.getDimensionElementId()));
                nodeLazyDTO.setChildren(false);
                nodeLazyDTO.setDescription(lastLevel.getDescription());
                nodeLazyDTO.setTextNode(lastLevel.getVisId());
                nodesList.add(nodeLazyDTO);
            } else {
                HierarchyElementImpl year = (HierarchyElementImpl) findedNode.getChildAt(i);
                nodeLazyDTO.setText(year.getVisId() + " - " + year.getDescription());
                HierarchyElementCK pk = (HierarchyElementCK) year.getPrimaryKey();
                nodeLazyDTO.setChildren(true);
                nodeLazyDTO.setStructureId(pk.getHierarchyPK().getHierarchyId());
                nodeLazyDTO.setId("hierarchyElement/" + Integer.toString(pk.getHierarchyElementPK().getHierarchyElementId()));
                nodeLazyDTO.setDescription(year.getDescription());
                nodeLazyDTO.setTextNode(year.getVisId());
                nodesList.add(nodeLazyDTO);
            }

        }

        return nodesList;
    }

    /**
     * Create calendar Dimension
     * @param disableLevel 
     */
    @SuppressWarnings("unchecked")
    public static List<HierarchyNodeLazyDTO> calendarDimension(CalendarForModelELO calendarForModelELO, String disableLevel) {
        List<HierarchyNodeLazyDTO> calendarRootName = new ArrayList<HierarchyNodeLazyDTO>();
        for (Iterator<CalendarForModelELO> it = calendarForModelELO.iterator(); it.hasNext();) {
            HierarchyNodeLazyDTO nodeLazyDTO = new HierarchyNodeLazyDTO();
            CalendarForModelELO row = it.next();
            nodeLazyDTO.setText(row.getHierarchyEntityRef().getNarrative());
            HierarchyCK hierPK = (HierarchyCK) row.getHierarchyEntityRef().getPrimaryKey();
            nodeLazyDTO.setChildren(true);
            nodeLazyDTO.setStateOpened(true);
            if (disableLevel != null && disableLevel.equals("2")) {
                nodeLazyDTO.setStateDisabled(true);
            }
            nodeLazyDTO.setDescription(row.getHierarchyEntityRef().getDisplayText());
            nodeLazyDTO.setTextNode(row.getHierarchyEntityRef().getNarrative());
            nodeLazyDTO.setId("dimensionElement/" + hierPK.getDimensionPK().getDimensionId());
            calendarRootName.add(nodeLazyDTO);
        }
        return calendarRootName;

    }

    /**
     * Recursive returns tree node
     */
    private static HierarchyNode findElementInNode(HierarchyNode node, int calendarId) {
        HierarchyNode result = null;
        int nodeCalendarId;
        if (node instanceof HierarchyElementImpl) {
            HierarchyElementCK pk = (HierarchyElementCK) node.getPrimaryKey();
            nodeCalendarId = pk.getHierarchyElementPK().getHierarchyElementId();
        } else if (node instanceof HierarchyElementFeedImpl) {
            HierarchyElementFeedPK pk = (HierarchyElementFeedPK) node.getPrimaryKey();
            nodeCalendarId = pk.getHierarchyElementId();
        } else {
            AugHierarchyElementPK pk = (AugHierarchyElementPK) node.getPrimaryKey();
            nodeCalendarId = pk.getHierarchyElementId();
        }
        if (nodeCalendarId == calendarId) {
            result = node;
        } else {
            for (int i = 0; i < node.getChildCount() && result == null; i++) {
                result = findElementInNode((HierarchyNode) node.getChildAt(i), calendarId);
            }
        }
        return result;
    }

    /**
     * Returned  tree node
     */
    public static List<HierarchyNodeLazyDTO> browseCalendarNode(CalendarImpl calendarImpl, int hierarchyElementId) {
        List<HierarchyNodeLazyDTO> node = mapNode(calendarImpl.getRoot(), hierarchyElementId);
        return node;
    }

    /**
     * Returned dimension node
     */
    @SuppressWarnings("unchecked")
    public static List<HierarchyNodeLazyDTO> hierarchyDimension(HierarchiesForModelELO hierarchiesForModelELO, int rowData, String disableLevel) {
        List<HierarchyNodeLazyDTO> list = new ArrayList<HierarchyNodeLazyDTO>();
        for (Iterator<HierarchiesForModelELO> it = hierarchiesForModelELO.iterator(); it.hasNext();) {
            HierarchiesForModelELO row = it.next();
            DimensionRefImpl dimensionRefImpl = (DimensionRefImpl) row.getDimensionEntityRef();
            if (rowData == dimensionRefImpl.getType()) {
                HierarchyNodeLazyDTO nodeLazy = new HierarchyNodeLazyDTO();
                nodeLazy.setText(dimensionRefImpl.getNarrative());
                nodeLazy.setStateOpened(true);
                nodeLazy.setChildren(true);
                if (disableLevel != null && disableLevel.equals("1")) {
                    nodeLazy.setStateDisabled(true);
                }
                nodeLazy.setDescription(dimensionRefImpl.getDisplayText());
                nodeLazy.setTextNode(dimensionRefImpl.getNarrative());
                nodeLazy.setId("dimensionElement/" + dimensionRefImpl.getDimensionPK().getDimensionId());
                list.add(nodeLazy);
            }

        }
        return list;
    }

}
