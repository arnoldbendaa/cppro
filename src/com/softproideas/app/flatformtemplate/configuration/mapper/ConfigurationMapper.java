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
package com.softproideas.app.flatformtemplate.configuration.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationDetailsDTO;
import com.softproideas.app.flatformtemplate.configuration.model.ConfigurationTreeDTO;
import com.softproideas.app.flatformtemplate.configuration.model.DimensionForFlatFormTemplateDTO;
import com.softproideas.app.flatformtemplate.configuration.model.TotalDTO;
import com.softproideas.commons.model.tree.NodeStateDTO;
import com.softproideas.commons.util.MapperUtil;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class ConfigurationMapper {

    public static ConfigurationTreeDTO mapXmlFormConfigurations(List<Map<String, Object>> rows, Boolean disableDirectories) {
        ConfigurationTreeDTO root = new ConfigurationTreeDTO();
        // Get root
        for (Map<String, Object> row: rows) {
            String parent = (String) row.get("PARENT_UUID");
            if (parent == null) {
                root.setParentUUID(null);
                root.setConfigurationUUID(UUID.fromString((String) row.get("TMPL_CONF_UUID")));
                String configurationVisId = (String) row.get("VIS_ID");
                root.setConfigurationVisId(configurationVisId);
                root.setText(configurationVisId);
                NodeStateDTO state = new NodeStateDTO();
                state.setOpened(true);
                if (disableDirectories) {
                    state.setDisabled(true);
                    state.setLeaf(false);
                }
                root.setState(state);
                boolean isDirectory = ((String) row.get("IS_DIRECTORY")).equalsIgnoreCase("Y");
                root.setDirectory(isDirectory);
                Integer versionNum = MapperUtil.mapBigDecimal(row.get("VERSION_NUM"));
                root.setVersionNum(versionNum);
                root.setType("directory");
                break;
            }
        }

        // Populate tree
        List<ConfigurationTreeDTO> children = populateTree(rows, root.getConfigurationUUID(), disableDirectories);
        root.setChildren(children);

        return root;
    }

    private static List<ConfigurationTreeDTO> populateTree(List<Map<String, Object>> rows, UUID configurationUUID, Boolean disableDirectories) {

        List<ConfigurationTreeDTO> children = new ArrayList<ConfigurationTreeDTO>();

        for (Map<String, Object> row: rows) {
            String parentStringUUID = (String) row.get("PARENT_UUID");
            if (parentStringUUID != null) {
                UUID parentUUID = UUID.fromString(parentStringUUID);
                if (parentUUID != null && parentUUID.equals(configurationUUID)) {
                    ConfigurationTreeDTO child = new ConfigurationTreeDTO();
                    child.setParentUUID(parentUUID);
                    UUID configurationId = UUID.fromString((String) row.get("TMPL_CONF_UUID"));
                    child.setConfigurationUUID(configurationId);
                    String configurationVisId = (String) row.get("VIS_ID");
                    child.setConfigurationVisId(configurationVisId);
                    child.setText(configurationVisId);
                    boolean isDirectory = ((String) row.get("IS_DIRECTORY")).equalsIgnoreCase("Y");
                    child.setDirectory(isDirectory);
                    Integer versionNum = MapperUtil.mapBigDecimal(row.get("VERSION_NUM"));
                    child.setVersionNum(versionNum);
                    List<ConfigurationTreeDTO> grandChildren = populateTree(rows, configurationId, disableDirectories);
                    child.setChildren(grandChildren);
                    String type = isDirectory ? "directory" : "configuration";
                    child.setType(type);

                    if (disableDirectories) {
                        if (type.equals("directory")) {
                            NodeStateDTO state = new NodeStateDTO();
                            state.setDisabled(true);
                            state.setLeaf(false);
                            child.setState(state);
                        } else {
                            NodeStateDTO state = new NodeStateDTO();
                            state.setDisabled(false);
                            state.setLeaf(true);
                            child.setState(state);
                        }
                    }

                    children.add(child);
                }
            }
        }

        return children;
    }

    public static ConfigurationDetailsDTO mapXmlFormConfigurationDetails(UUID configurationUUID, Map<String, Object> row, List<Map<String, Object>> rowsDimension, List<Map<String, Object>> rowsTotal) {
        ConfigurationDetailsDTO configuration = new ConfigurationDetailsDTO();
        HashMap<String, DimensionForFlatFormTemplateDTO> dimensionMap = new HashMap<String, DimensionForFlatFormTemplateDTO>();
        List<TotalDTO> totalList = new ArrayList<TotalDTO>();
        // int maxIndex = 0;
        configuration.setConfigurationUUID(configurationUUID);
        String configurationVisId = (String) row.get("VIS_ID");
        configuration.setConfigurationVisId(configurationVisId);
        String parentUUID = (String) row.get("PARENT_UUID");
        configuration.setParentUUID(UUID.fromString(parentUUID));
        Integer versionNum = MapperUtil.mapBigDecimal(row.get("VERSION_NUM"));
        configuration.setVersionNum(versionNum);
        boolean isDirectory = ((String) row.get("IS_DIRECTORY")).equalsIgnoreCase("Y");
        configuration.setDirectory(isDirectory);

        for (Map<String, Object> rowDim: rowsDimension) {
            DimensionForFlatFormTemplateDTO dimension = new DimensionForFlatFormTemplateDTO();
            UUID dimensionUUID = UUID.fromString((String) rowDim.get("TMPL_CONF_DIM_UUID"));
            dimension.setDimensionUUID(dimensionUUID);
            String dimensionVisId = (String) rowDim.get("VIS_ID");
            dimension.setDimensionVisId(dimensionVisId);
            String sheetName = (String) rowDim.get("SHEET_NAME");
            dimension.setSheetName(sheetName);
            String modelVisId = (String) rowDim.get("MODEL_VIS_ID");
            dimension.setModelVisId(modelVisId);
            Integer index = MapperUtil.mapBigDecimal(rowDim.get("CHILD_INDEX"));
            // if (index > maxIndex) {
            // maxIndex = index;
            // }
            dimension.setIndex(index);
            boolean hidden = ((String) rowDim.get("IS_HIDDEN")).equals("1");
            dimension.setHidden(hidden);
            ArrayList<String> excludedDimensions;
            String excludedDimensionsString = ((String) rowDim.get("EXCLUDED_DIMENSIONS"));
            if (excludedDimensionsString == null) {
                excludedDimensions = null;
            } else if (excludedDimensionsString.equals("[]")) {
                excludedDimensions = new ArrayList<String>();
            } else {
                excludedDimensionsString = excludedDimensionsString.substring(1, excludedDimensionsString.length() - 1);
                excludedDimensions = new ArrayList<String>(Arrays.asList(excludedDimensionsString.split(", ")));
            }
            dimension.setExcludedDimensions(excludedDimensions);
            dimensionMap.put(dimensionUUID.toString(), dimension);
        }

        List<DimensionForFlatFormTemplateDTO> dimensionList = new ArrayList<DimensionForFlatFormTemplateDTO>();
        dimensionList.addAll(dimensionMap.values());
        configuration.setDimensions(dimensionList);

        for (Map<String, Object> rowTot: rowsTotal) {
            TotalDTO total = new TotalDTO();
            UUID totalUUID = UUID.fromString((String) rowTot.get("TMPL_CONF_TOTAL_UUID"));
            total.setTotalUUID(totalUUID);
            String scheetName = (String) rowTot.get("SHEET_NAME");
            total.setSheetName(scheetName);
            Integer index = MapperUtil.mapBigDecimal(rowTot.get("CHILD_INDEX"));
            // if (index > maxIndex) {
            // maxIndex = index;
            // }
            total.setIndex(index);
            boolean hidden = ((String) rowTot.get("IS_HIDDEN")).equals("1");
            total.setHidden(hidden);
            boolean grandTotal = ((String) rowTot.get("IS_GRAND")).equals("1");
            total.setGrandTotal(grandTotal);
            List<DimensionForFlatFormTemplateDTO> totalDimensionList = new ArrayList<DimensionForFlatFormTemplateDTO>();
            String listDimensionUUID = (String) rowTot.get("DIMS");
            if (listDimensionUUID != null) {
                String[] tableTotalUUIDs = listDimensionUUID.split(",");
                for (int i = 0; i < tableTotalUUIDs.length; i++) {
                    DimensionForFlatFormTemplateDTO dimension = dimensionMap.get(tableTotalUUIDs[i]);
                    totalDimensionList.add(dimension);
                }
                total.setDimensionList(totalDimensionList);
            }
            totalList.add(total);
        }

        configuration.setTotals(totalList);

        return configuration;
    }

}
