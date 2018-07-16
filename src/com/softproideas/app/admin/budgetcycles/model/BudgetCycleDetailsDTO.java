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
package com.softproideas.app.admin.budgetcycles.model;

import java.util.List;

public class BudgetCycleDetailsDTO extends BudgetCycleDTO {

    private int defaultXmlFormId;
    private String defaultXmlFormDataType; // moze byc do wywalenia
    private String plannedEndDate;
    private String startDate;
    private String endDate;
    private List<Long> levelDates;
    private List<BudgetCycleXMLFormDTO> xmlForms;
    private int versionNum;

    public int getDefaultXmlFormId() {
        return defaultXmlFormId;
    }

    public void setDefaultXmlFormId(int defaultXmlFormId) {
        this.defaultXmlFormId = defaultXmlFormId;
    }

    public String getDefaultXmlFormDataType() {
        return defaultXmlFormDataType;
    }

    public void setDefaultXmlFormDataType(String defaultXmlFormDataType) {
        this.defaultXmlFormDataType = defaultXmlFormDataType;
    }

    public String getPlannedEndDate() {
        return plannedEndDate;
    }

    public void setPlannedEndDate(String plannedEndDate) {
        this.plannedEndDate = plannedEndDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Long> getLevelDates() {
        return levelDates;
    }

    public void setLevelDates(List<Long> levelDates) {
        this.levelDates = levelDates;
    }

    public List<BudgetCycleXMLFormDTO> getXmlForms() {
        return xmlForms;
    }

    public void setXmlForms(List<BudgetCycleXMLFormDTO> xmlForms) {
        this.xmlForms = xmlForms;
    }

    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

    @Override
    public String toString() {
        return "BudgetCycleDetailsDTO [defaultXmlFormId=" + defaultXmlFormId + ", defaultXmlFormDataType=" + defaultXmlFormDataType + ", plannedEndDate=" + plannedEndDate + ", startDate=" + startDate + ", endDate=" + endDate + ", levelDates=" + levelDates + ", xmlForms=" + xmlForms + ", versionNum=" + versionNum + "]";
    }
}
