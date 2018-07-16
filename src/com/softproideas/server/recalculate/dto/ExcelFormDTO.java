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

package com.softproideas.server.recalculate.dto;

import java.util.List;
import java.util.Map;

import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;

public class ExcelFormDTO {
    private WorkbookDTO workbook;
    private String jsonForm;
    private byte[] excelFile;
    
    protected Map<String, String> contextVariables;
    protected List<ElementDTO> selectedDimension;
    protected String dataType;
    protected boolean userReadOnlyAccess;

    public WorkbookDTO getWorkbook() {
        return workbook;
    }

    public void setWorkbook(WorkbookDTO workbook) {
        this.workbook = workbook;
    }

    public String getJsonForm() {
        return jsonForm;
    }

    public void setJsonForm(String jsonForm) {
        this.jsonForm = jsonForm;
    }

    public byte[] getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(byte[] excellFile) {
        this.excelFile = excellFile;
    }

    public Map<String, String> getContextVariables() {
        return contextVariables;
    }

    public void setContextVariables(Map<String, String> contextVariables) {
        this.contextVariables = contextVariables;
    }

    public List<ElementDTO> getSelectedDimension() {
        return selectedDimension;
    }

    public void setSelectedDimension(List<ElementDTO> selectedDimension) {
        this.selectedDimension = selectedDimension;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isUserReadOnlyAccess() {
        return userReadOnlyAccess;
    }

    public void setUserReadOnlyAccess(boolean userReadOnlyAccess) {
        this.userReadOnlyAccess = userReadOnlyAccess;
    }
}
