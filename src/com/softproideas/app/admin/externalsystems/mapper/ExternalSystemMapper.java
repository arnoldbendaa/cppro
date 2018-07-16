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
package com.softproideas.app.admin.externalsystems.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.extsys.ExternalSystemRef;
import com.cedar.cp.dto.base.EntityRefImpl;
import com.cedar.cp.dto.extsys.AllExternalSystemsELO;
import com.cedar.cp.dto.extsys.ExternalSystemImpl;
import com.cedar.cp.dto.extsys.ExternalSystemPK;
import com.softproideas.app.admin.externalsystems.model.ExternalSystemDTO;
import com.softproideas.app.admin.externalsystems.model.ExternalSystemDetailsDTO;
import com.softproideas.app.admin.externalsystems.model.SubSystemColumnDTO;
import com.softproideas.app.admin.externalsystems.model.SubSystemConfigurationDTO;
import com.softproideas.app.admin.externalsystems.model.SubSystemDTO;
import com.softproideas.commons.model.Property;

/**
 * <p>Transforms various data types related to external system model.</p>
 *
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@SuppressWarnings("unchecked")
public class ExternalSystemMapper {

    private static final Map<Integer, String> systemTypeString;
    private static final Map<String, Integer> systemTypeInteger;
    static {
        systemTypeString = new HashMap<Integer, String>();
        systemTypeString.put(3, "eFin");
        systemTypeString.put(5, "e5");
        systemTypeString.put(10, "Open Accounts");
        systemTypeString.put(20, "Generic");
        systemTypeInteger = new HashMap<String, Integer>();
        systemTypeInteger.put("eFin", 3);
        systemTypeInteger.put("e5", 5);
        systemTypeInteger.put("Open Accounts", 10);
        systemTypeInteger.put("Generic", 20);
    }

    public static List<ExternalSystemDTO> mapAllExternalSystemsELO(AllExternalSystemsELO elo) {
        List<ExternalSystemDTO> dataTypesDTOList = new ArrayList<ExternalSystemDTO>();

        for (Iterator<AllExternalSystemsELO> it = elo.iterator(); it.hasNext();) {
            ExternalSystemDTO externalSystemDTO = new ExternalSystemDTO();

            AllExternalSystemsELO row = it.next();
            ExternalSystemRef ref = row.getExternalSystemEntityRef();
            ExternalSystemPK pk = (ExternalSystemPK) ref.getPrimaryKey();

            // Id
            externalSystemDTO.setExternalSystemId(pk.getExternalSystemId());
            // VisId
            externalSystemDTO.setExternalSystemVisId(row.getVisId());
            // Description
            externalSystemDTO.setExternalSystemDescription(row.getDescription());
            // Location
            externalSystemDTO.setLocation(row.getLocation());
            // Enabled
            externalSystemDTO.setEnabled(row.getEnabled());
            // SystemType
            int systemType = row.getSystemType();
            externalSystemDTO.setSystemType(systemTypeString.get(systemType));

            dataTypesDTOList.add(externalSystemDTO);
        }

        return dataTypesDTOList;
    }

    public static ExternalSystemDetailsDTO mapExternalSystemImpl(ExternalSystemImpl externalSystemImpl, EntityList xactSubsystems) {
        ExternalSystemDetailsDTO externalSystemDTO = new ExternalSystemDetailsDTO();
        ExternalSystemPK pk = (ExternalSystemPK) externalSystemImpl.getPrimaryKey();

        // Id
        externalSystemDTO.setExternalSystemId(pk.getExternalSystemId());
        // VisId
        externalSystemDTO.setExternalSystemVisId(externalSystemImpl.getVisId());
        // Description
        externalSystemDTO.setExternalSystemDescription(externalSystemImpl.getDescription());
        // Location
        externalSystemDTO.setLocation(externalSystemImpl.getLocation());
        // Enabled
        externalSystemDTO.setEnabled(externalSystemImpl.isEnabled());
        // SystemType
        int systemType = externalSystemImpl.getSystemType();
        externalSystemDTO.setSystemType(systemTypeString.get(systemType));
        // ConnectorClass
        externalSystemDTO.setConnectorClass(externalSystemImpl.getConnectorClass());
        // ImportSource
        externalSystemDTO.setImportSource(externalSystemImpl.getImportSource());
        // ExportTarget
        externalSystemDTO.setExportTarget(externalSystemImpl.getExportTarget());
        // VersionNum
        externalSystemDTO.setVersionNum(externalSystemImpl.getVersionNum());
        // SubSystems
        List<SubSystemDTO> subSystemsDTO = new ArrayList<SubSystemDTO>();
        for (int i = 0; i < xactSubsystems.getNumRows(); i++) {
            SubSystemDTO subSystemDTO = new SubSystemDTO();
            EntityList row = xactSubsystems.getRowData(i);

            subSystemDTO.setSubSystemId(((BigDecimal) row.getValueAt(0, "SUBSYSTEM_ID")).intValue());
            subSystemDTO.setSubSystemDescription((String) row.getValueAt(0, "DESCRIPTION"));

            subSystemsDTO.add(subSystemDTO);
        }
        externalSystemDTO.setSubSystems(subSystemsDTO);
        
        // Properties
        List<Property> properties = new ArrayList<Property>();
        Map<String, String> propertiesMap = externalSystemImpl.getProperties();
        for (Entry<String, String> mapElement: propertiesMap.entrySet()) {
            Property property = new Property();
            property.setName(mapElement.getKey());
            property.setValue(mapElement.getValue());
            properties.add(property);
        }
        externalSystemDTO.setProperties(properties);
        
        return externalSystemDTO;
    }

    public static SubSystemConfigurationDTO mapSubSystemConfiguration(EntityList xactAvailableColumns, EntityList xactColumnSelection) {
        SubSystemConfigurationDTO subSystemConfiguration = new SubSystemConfigurationDTO();
        List<SubSystemColumnDTO> selectedColumns = new ArrayList<SubSystemColumnDTO>();
        List<SubSystemColumnDTO> availableColumns = new ArrayList<SubSystemColumnDTO>();
        
        List<String> excludes = new ArrayList<String>();
        // Selected Columns
        for (int i = 0; i < xactColumnSelection.getNumRows(); i++) {
            EntityList row = xactColumnSelection.getRowData(i);
            
            String description = (String) row.getValueAt(0, "DESCRIPTION");
            excludes.add(description);
            SubSystemColumnDTO subSystemColumnDTO = new SubSystemColumnDTO();
            subSystemColumnDTO.setTableId((BigDecimal) row.getValueAt(0, "TABLE_ID"));
            subSystemColumnDTO.setColumnSeq((BigDecimal) row.getValueAt(0, "COLUMN_SEQ"));
            subSystemColumnDTO.setDescription(description);
            subSystemColumnDTO.setNumTotal((String) row.getValueAt(0, "IS_TOTAL"));
            selectedColumns.add(subSystemColumnDTO);
        }
        subSystemConfiguration.setSelectedColumns(selectedColumns);
        
        // Available Columns
        for (int i = 0; i < xactAvailableColumns.getNumRows(); i++) {
            EntityList row = xactAvailableColumns.getRowData(i);

            String description = (String) row.getValueAt(0, "DESCRIPTION");
            if (!excludes.contains(description)) { // add only not selected columns
                SubSystemColumnDTO subSystemColumnDTO = new SubSystemColumnDTO();
                subSystemColumnDTO.setTableId((BigDecimal) row.getValueAt(0, "TABLE_ID"));
                subSystemColumnDTO.setColumnSeq((BigDecimal) row.getValueAt(0, "COLUMN_SEQ"));
                subSystemColumnDTO.setDescription(description);
                subSystemColumnDTO.setNumTotal((String) row.getValueAt(0, "IS_NUM"));
                availableColumns.add(subSystemColumnDTO);
            }

        }
        subSystemConfiguration.setAvailableColumns(availableColumns);
        
        return subSystemConfiguration;
    }

    public static ExternalSystemImpl mapExternalSystemDetailsDTO(ExternalSystemImpl externalSystemImpl, ExternalSystemDetailsDTO externalSystemDetails) {
        // VisId
        externalSystemImpl.setVisId(externalSystemDetails.getExternalSystemVisId());
        // Description
        externalSystemImpl.setDescription(externalSystemDetails.getExternalSystemDescription());
        // Location
        if (externalSystemImpl.getLocation() == null || externalSystemImpl.getLocation().isEmpty()) { // create
            externalSystemImpl.setLocation(externalSystemDetails.getLocation());
        }
        // System Type
        if (externalSystemImpl.getSystemType() == 0) { // create
            externalSystemImpl.setSystemType(systemTypeInteger.get("Generic"));
        }
        // Enabled
        externalSystemImpl.setEnabled(externalSystemDetails.isEnabled());
        // ConnectorClass
        externalSystemImpl.setConnectorClass(externalSystemDetails.getConnectorClass());
        // VersionNum
        externalSystemImpl.setVersionNum(externalSystemDetails.getVersionNum());
        
        // SubSystems
        for (SubSystemDTO subSystem : externalSystemDetails.getSubSystems()) {
            SubSystemConfigurationDTO subSystemConfigurationDTO = subSystem.getSubSystemConfiguration();
            if (subSystemConfigurationDTO != null && subSystemConfigurationDTO.getAvailableColumns() != null && subSystemConfigurationDTO.getSelectedColumns() != null) {
                // available columns
                List<List<Object>> availableColumns = new ArrayList<List<Object>>();
                for (SubSystemColumnDTO columnDTO : subSystemConfigurationDTO.getAvailableColumns()) {
                    List<Object> column = new ArrayList<Object>();
                    column.add(columnDTO.getTableId());
                    column.add(columnDTO.getColumnSeq());
                    column.add(columnDTO.getDescription());
                    column.add(columnDTO.getNumTotal());
                    availableColumns.add(column);
                }
                externalSystemImpl.addAvailableColumns(subSystem.getSubSystemId(), availableColumns);
                // selected columns
                List<List<Object>> selectedColumns = new ArrayList<List<Object>>();
                for (SubSystemColumnDTO columnDTO : subSystemConfigurationDTO.getSelectedColumns()) {
                    List<Object> column = new ArrayList<Object>();
                    column.add(columnDTO.getTableId());
                    column.add(columnDTO.getColumnSeq());
                    column.add(columnDTO.getDescription());
                    column.add(columnDTO.getNumTotal());
                    selectedColumns.add(column);
                }
                externalSystemImpl.addSelectedColumns(subSystem.getSubSystemId(), selectedColumns);
            }
        }

        // Properties
        Map<String, String> propertiesMap = new HashMap<String, String>();
        List<Property> properties = externalSystemDetails.getProperties();
        for (Property property: properties) {
            propertiesMap.put(property.getName(), property.getValue());
        }
        externalSystemImpl.setProperties(propertiesMap);
        
        return externalSystemImpl;
    }

    public static com.cedar.cp.dto.model.globalmapping2.extsys.ExternalSystemImpl mapExternalSystemDTOGlobal(ExternalSystemDTO externalSystem) {
        if (externalSystem != null) {
            // EntityRefImpl
            ExternalSystemPK key = new ExternalSystemPK(externalSystem.getExternalSystemId());
            EntityRefImpl extSysEntityRef = new EntityRefImpl(key, externalSystem.getExternalSystemVisId());
            // System Type
            int systemType = systemTypeInteger.get(externalSystem.getSystemType());
            // Description
            String description = externalSystem.getExternalSystemDescription();
            // Location
            String location = externalSystem.getLocation();
            // Enabled
            boolean enabled = externalSystem.isEnabled();
    
            com.cedar.cp.dto.model.globalmapping2.extsys.ExternalSystemImpl externalSystemImpl = new com.cedar.cp.dto.model.globalmapping2.extsys.ExternalSystemImpl(systemType, extSysEntityRef, location, description, enabled);
            return externalSystemImpl;
        } else {
            return null;
        }
    }
    
    public static com.cedar.cp.dto.model.mapping.extsys.ExternalSystemImpl mapExternalSystemDTO(ExternalSystemDTO externalSystem) {
        if (externalSystem != null) {
            // EntityRefImpl
            ExternalSystemPK key = new ExternalSystemPK(externalSystem.getExternalSystemId());
            EntityRefImpl extSysEntityRef = new EntityRefImpl(key, externalSystem.getExternalSystemVisId());
            // System Type
            int systemType = systemTypeInteger.get(externalSystem.getSystemType());
            // Description
            String description = externalSystem.getExternalSystemDescription();
            // Location
            String location = externalSystem.getLocation();
            // Enabled
            boolean enabled = externalSystem.isEnabled();
    
            com.cedar.cp.dto.model.mapping.extsys.ExternalSystemImpl externalSystemImpl = new com.cedar.cp.dto.model.mapping.extsys.ExternalSystemImpl(systemType, extSysEntityRef, location, description, enabled);
            return externalSystemImpl;
        } else {
            return null;
        }
    }
}
