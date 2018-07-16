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
package com.softproideas.app.reviewbudget.budget.model;

import java.util.List;
import java.util.Map;

import com.softproideas.app.reviewbudget.dimension.model.ElementDTO;

public class WorkbookToUpdateDTO {

    private int budgetCycleId;
    private List<ElementDTO> selectedDimension;
    private String dataType;
    private Map<String, String> properties;
    private Map<String, String> contextVariables;
    private List<WorksheetToUpdateDTO> worksheets;

    public int getBudgetCycleId() {
        return budgetCycleId;
    }

    public void setBudgetCycleId(int budgetCycleId) {
        this.budgetCycleId = budgetCycleId;
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

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, String> getContextVariables() {
        return contextVariables;
    }

    public void setContextVariables(Map<String, String> contextVariables) {
        this.contextVariables = contextVariables;
    }

    public List<WorksheetToUpdateDTO> getWorksheets() {
        return worksheets;
    }

    public void setWorksheets(List<WorksheetToUpdateDTO> worksheets) {
        this.worksheets = worksheets;
    }

}
