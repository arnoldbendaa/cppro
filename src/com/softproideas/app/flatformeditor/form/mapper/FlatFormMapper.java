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
package com.softproideas.app.flatformeditor.form.mapper;

import java.util.HashMap;
import java.util.List;

import com.cedar.cp.dto.user.AllUsersELO;
import com.cedar.cp.dto.xmlform.XmlFormImpl;
import com.cedar.cp.dto.xmlform.XmlFormPK;
import com.cedar.cp.util.flatform.model.workbook.editor.WorkbookMapper;
import com.cedar.cp.util.flatform.model.workbook.util.CellType;
import com.softproideas.app.core.users.mapper.UserCoreMapper;
import com.softproideas.app.core.users.model.UserCoreDTO;
import com.softproideas.app.flatformeditor.form.model.FlatFormDetailsDTO;
import com.softproideas.app.flatformeditor.form.model.FlatFormDetailsWithExcellFileDTO;

/**
 * <p>
 * Transforms various data types to and from {@link FlatFormDetailsDTO}
 * </p>
 * 
 * @author Jacek Kurasiewicz
 * @email jk@softproideas.com
 *        <p>
 *        2014 All rights reserved to IT Services Jacek Kurasiewicz
 *        </p>
 */
public class FlatFormMapper {

    /**
     * Converts XmlFormImpl to FlatFormDTO
     * 
     * @param impl
     *            XmlFormImpl coming from EJB service
     * @return
     * @throws Exception 
     */
    @SuppressWarnings("unchecked")
    public static FlatFormDetailsDTO mapXmlFormEditorSessionSSO(XmlFormImpl impl, AllUsersELO allUsersELO, CellType cellType, boolean fetchExcellFile) throws Exception {
        FlatFormDetailsDTO result = null;
        if(fetchExcellFile){
            result = new FlatFormDetailsWithExcellFileDTO();
        }
        else{
            result = new FlatFormDetailsDTO();
        }
        if (impl.getPrimaryKey() != null) {
            result.setFlatFormId(((XmlFormPK) impl.getPrimaryKey()).getXmlFormId());
            result.setJsonForm(impl.getJsonForm());
            result.setXmlForm(WorkbookMapper.mapDefinitionXML(impl.getDefinition(), cellType));// result.setXmlForm(impl.getDefinition());
            result.setType(impl.getType());
        } else {
            result.setFlatFormId(-1);
            int rowCount = 200;
            int colCount = 20;
            int colWidth = 62;
            int rowHeight = 20;            
            String jsonForm = "{\"spread\":{\"version\":\"2.0\",\"sheetCount\":1,\"startSheetIndex\":0,\"activeSheetIndex\":0,\"namedStyles\":[{\"name\":\"__builtInStyle15\",\"locked\":true,\"wordWrap\":false,\"hAlign\":3,\"vAlign\":2,\"textDecoration\":0,\"font\":\"normal normal 10px SansSerif\"}],\"sheets\":{\"Sheet1\":{\"name\":\"Sheet1\",\"defaults\":{\"colWidth\":" + colWidth + ",\"rowHeight\":" + rowHeight + ",\"colHeaderRowHeight\":20.0,\"rowHeaderColWidth\":40.0},\"gridline\":{\"showVerticalGridline\":true,\"showHorizontalGridline\":true,\"color\":\"rgba(208,215,229,1)\"},\"data\":{\"rowCount\":" + rowCount + ",\"colCount\":" + colCount + ",\"dataTable\":{\"0\":{\"0\":{\"value\":\"\",\"style\":\"__builtInStyle15\"}}}},\"selections\":{\"activeSelectedRangeIndex\":0,\"0\":{\"col\":0,\"row\":0,\"colCount\":1,\"rowCount\":1},\"length\":1},\"rows\":[],\"columns\":[],\"rowCount\":1,\"columnCount\":2,\"visible\":true,\"_index\":0,\"colRangeGroup\":{\"itemsCount\":2},\"rowRangeGroup\":{\"itemsCount\":2}}}}}";
            result.setJsonForm(jsonForm);
            String xmlDefinition = "<workbook>" +
                    "<properties>" +
                        "<entry name=\"PROTECTED\" value=\"false\"/>" +
                        "<entry name=\"INVERT_NUMBERS_VALUE\" value=\"true\"/>" +
                    "</properties>" +
                    "<worksheet name=\"Sheet1\" showGrid=\"true\" hidden=\"false\" cellFormatVersion=\"0\"/>" +
                "</workbook>";
            result.setXmlForm(WorkbookMapper.mapDefinitionXML(xmlDefinition, CellType.BASIC));
            result.setType(6);
        }
        if(fetchExcellFile == true){
            ((FlatFormDetailsWithExcellFileDTO) result).setExcellFile(impl.getExcelFile());
        }
        result.setVisId(impl.getVisId());
        result.setFinanceCubeId(impl.getFinanceCubeId());
        result.setDescription(impl.getDescription());
        result.setSecurityAccess(impl.isSecurityAccess());
        result.setVersionNum(impl.getVersionNum());
        result.setDesignMode(impl.isDesignMode());
        

        // users and availableUsers
        HashMap<String, List<UserCoreDTO>> preparedUsers = UserCoreMapper.prepareCurrentAndAvailableUsers(impl.getUserList(), allUsersELO);
        result.setUsers(preparedUsers.get("usersDTOList"));
        result.setAvailableUsers(preparedUsers.get("availableUsersDTOList"));

        return result;
    }

}
