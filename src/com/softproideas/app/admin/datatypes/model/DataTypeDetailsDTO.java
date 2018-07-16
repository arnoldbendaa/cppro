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
package com.softproideas.app.admin.datatypes.model;

public class DataTypeDetailsDTO extends DataTypeDTO {

    private Integer measureLength;
    private Integer measureScale;
    private String measureValidation;
    private Boolean readOnlyFlag;
    private Boolean availableForImport;
    private Boolean availableForExport;
    private String formulaExpr;
    private int versionNum;

    public Integer getMeasureLength() {
        return measureLength;
    }

    public void setMeasureLength(Integer measureLength) {
        this.measureLength = measureLength;
    }

    public Integer getMeasureScale() {
        return measureScale;
    }

    public void setMeasureScale(Integer measureScale) {
        this.measureScale = measureScale;
    }

    public String getMeasureValidation() {
        return measureValidation;
    }

    public void setMeasureValidation(String measureValidation) {
        this.measureValidation = measureValidation;
    }

    public Boolean getReadOnlyFlag() {
        return readOnlyFlag;
    }

    public void setReadOnlyFlag(Boolean readOnlyFlag) {
        this.readOnlyFlag = readOnlyFlag;
    }

    public Boolean getAvailableForImport() {
        return availableForImport;
    }

    public void setAvailableForImport(Boolean availableForImport) {
        this.availableForImport = availableForImport;
    }

    public Boolean getAvailableForExport() {
        return availableForExport;
    }

    public void setAvailableForExport(Boolean availableForExport) {
        this.availableForExport = availableForExport;
    }

    public String getFormulaExpr() {
        return formulaExpr;
    }

    public void setFormulaExpr(String formulaExpr) {
        this.formulaExpr = formulaExpr;
    }

    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

}
