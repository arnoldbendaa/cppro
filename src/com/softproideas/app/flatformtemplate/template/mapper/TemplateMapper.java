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
package com.softproideas.app.flatformtemplate.template.mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.editor.WorkbookMapper;
import com.cedar.cp.util.flatform.model.workbook.util.CellType;
import com.softproideas.app.core.users.mapper.UserCoreMapper;
import com.softproideas.app.core.users.model.UserCoreDTO;
import com.softproideas.app.flatformtemplate.template.model.TemplateDetailsDTO;
import com.softproideas.commons.model.SelectListElementDTO;
import com.softproideas.commons.model.tree.NodeStateDTO;
import com.softproideas.commons.util.MapperUtil;

public class TemplateMapper {

    // if change this array, change its copy in FlatFormTemplateCommonsService.js (var templatesTypes) too
    // and add icon to object in getTypesConfig() in the same file
    private static String[] templateTypes = { "directory", "standard", "hierarchy based" };

    public static String mapType(Integer typeInteger) {
        if (typeInteger < 0 || typeInteger > templateTypes.length) {
            return "";
        }
        return templateTypes[typeInteger];
    }

    public static Integer mapType(String typeString) {
        return Arrays.asList(templateTypes).indexOf(typeString);
    }

    public static ArrayList<SelectListElementDTO> getTemplateTypes() {
        ArrayList<SelectListElementDTO> list = new ArrayList<SelectListElementDTO>();
        for (int i = 0; i < templateTypes.length; i++) {
            SelectListElementDTO obj = new SelectListElementDTO();
            obj.setIndex(i);
            obj.setName(templateTypes[i]);
            list.add(obj);
        }
        return list;
    }

    public static List<TemplateDetailsDTO> mapXmlFormTemplates(List<Map<String, Object>> rows) throws Exception {
        List<TemplateDetailsDTO> xmlFormTemplates = new ArrayList<TemplateDetailsDTO>();
        for (Map<String, Object> row: rows) {
            TemplateDetailsDTO template = mapXmlFormTemplate(row, true, false);
            xmlFormTemplates.add(template);
        }
        return xmlFormTemplates;
    }

    public static TemplateDetailsDTO mapXmlFormTemplatesTree(List<Map<String, Object>> rows, Boolean disableDirectories) throws Exception {
        TemplateDetailsDTO root = new TemplateDetailsDTO();
        // Get root
        for (Map<String, Object> row: rows) {
            String parentStringUUID = (String) row.get("PARENT_UUID");
            if (parentStringUUID == null) {
                root = mapXmlFormTemplate(row, false, disableDirectories);
                root.setStateOpened(true);
                break;
            }
        }
        // Populate tree
        List<TemplateDetailsDTO> children = populateTree(rows, root.getTemplateUUID(), disableDirectories);
        root.setChildren(children);
        return root;
    }

    private static List<TemplateDetailsDTO> populateTree(List<Map<String, Object>> rows, UUID templateUUID, Boolean disableDirectories) throws Exception {

        List<TemplateDetailsDTO> children = new ArrayList<TemplateDetailsDTO>();

        for (Map<String, Object> row: rows) {
            String parentStringUUID = (String) row.get("PARENT_UUID");
            if (parentStringUUID != null) {
                UUID parentUUID = UUID.fromString(parentStringUUID);
                if (parentUUID != null && parentUUID.equals(templateUUID)) {
                    TemplateDetailsDTO child = new TemplateDetailsDTO();
                    child = mapXmlFormTemplate(row, false, disableDirectories);

                    List<TemplateDetailsDTO> grandChildren = populateTree(rows, child.getTemplateUUID(), disableDirectories);
                    child.setChildren(grandChildren);

                    children.add(child);
                }
            }
        }
        Collections.sort(children, new TemplateDetailsComparator());
        return children;
    }

    public static TemplateDetailsDTO mapXmlFormTemplate(Map<String, Object> row, Boolean withJson, Boolean disableDirectories) throws Exception {
        TemplateDetailsDTO template = new TemplateDetailsDTO();
        template.setTemplateUUID(UUID.fromString((String) row.get("TMPL_UUID")));
        String visId = String.valueOf(row.get("VIS_ID"));
        template.setVisId(visId);

        String defId = String.valueOf(row.get("DEFINITION"));

        String workbookStringValue = (!defId.equals("null")) ? defId : null;

        WorkbookDTO workbook;
        if (workbookStringValue != null) {
            workbook = WorkbookMapper.mapDefinitionXML(workbookStringValue, CellType.BASIC);
        } else {
            workbook = new WorkbookDTO();
        }
        template.setWorkbook(workbook);
        Object description = row.get("DESCRIPTION");
        if (description == null) {
            template.setDescription("");
            template.setText(visId);
        } else {
            template.setDescription(String.valueOf(description));
            template.setText(visId + " - " + description);
        }
        template.setIndex(MapperUtil.mapBigDecimal(row.get("CHILD_INDEX")));
        template.setVersionNum(MapperUtil.mapBigDecimal(row.get("VERSION_NUM")));
        String jsonFormString = String.valueOf(row.get("JSON_FORM"));
        String jsonFormStringValue = (!jsonFormString.equals("null")) ? jsonFormString : null;
        if (withJson) {
            template.setJsonForm(jsonFormStringValue); // TODO: metoda jest uzywana podczas tworzenai drzewka -> tam nie potrzebna sa nam jsonform
        }
        String parentUUIDString = (String) row.get("PARENT_UUID");
        if (parentUUIDString != null) {
            UUID parentUUID = UUID.fromString(parentUUIDString);
            template.setParentUUID(parentUUID);
        }
        Integer typeInteger = MapperUtil.mapBigDecimal(row.get("TYPE"));
        String type = mapType(typeInteger);
        template.setType(type);
        if (disableDirectories) {
            if (type.equals("directory")) {
                NodeStateDTO state = new NodeStateDTO();
                state.setDisabled(true);
                state.setLeaf(false);
                template.setState(state);
            } else {
                NodeStateDTO state = new NodeStateDTO();
                state.setDisabled(false);
                state.setLeaf(true);
                template.setState(state);
            }
        }
        return template;
    }

    public static TemplateDetailsDTO mapXmlFormTemplated(Map<String, Object> row, Boolean withJson, Boolean disableDirectories, List<Integer> list, AllUsersELO allUsersELO) throws Exception {
        TemplateDetailsDTO template = mapXmlFormTemplate(row, withJson, disableDirectories);

        // users and availableUsers
        HashMap<String, List<UserCoreDTO>> preparedUsers = UserCoreMapper.prepareTemplateUsers(list, allUsersELO);
        template.setUsers(preparedUsers.get("usersDTOList"));
        template.setAvailableUsers(preparedUsers.get("availableUsersDTOList"));

        return template;
    }
}
