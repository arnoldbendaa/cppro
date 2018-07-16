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
package com.softproideas.app.admin.financecubes.model;

import java.util.List;

import com.softproideas.app.core.financecube.model.FinanceCubeModelCoreDTO;

public class FinanceCubeDetailsDTO extends FinanceCubeModelCoreDTO {

    private boolean submitChangeManagement;
    private boolean hasData;
    private boolean audited;
    private boolean cubeFormulaEnabled;
    private int lockedByTaskId;
    private String internalKey;
    private List<DataTypeWithTimestampDTO> dataTypes;
    private DimensionDTO[] dimensions;
    private List<RollUpRuleLineDTO> rollUpRuleLines;
    private List<DataTypeWithMeasureLenghtDTO> mappedDataTypes;
    private List<DataTypeWithMeasureLenghtDTO> aggregatedDataTypes;
    private boolean insideChangeManagement;
    private boolean changeManagementOutstanding;
    private int versionNum;

    public boolean isSubmitChangeManagement() {
        return submitChangeManagement;
    }

    public void setSubmitChangeManagement(boolean submitChangeManagement) {
        this.submitChangeManagement = submitChangeManagement;
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    public boolean isAudited() {
        return audited;
    }

    public void setAudited(boolean audited) {
        this.audited = audited;
    }

    public boolean isCubeFormulaEnabled() {
        return cubeFormulaEnabled;
    }

    public void setCubeFormulaEnabled(boolean cubeFormulaEnabled) {
        this.cubeFormulaEnabled = cubeFormulaEnabled;
    }

    public int getLockedByTaskId() {
        return lockedByTaskId;
    }

    public void setLockedByTaskId(int lockedByTaskId) {
        this.lockedByTaskId = lockedByTaskId;
    }

    public String getInternalKey() {
        return internalKey;
    }

    public void setInternalKey(String internalKey) {
        this.internalKey = internalKey;
    }

    public List<DataTypeWithTimestampDTO> getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(List<DataTypeWithTimestampDTO> dataTypes) {
        this.dataTypes = dataTypes;
    }

    public DimensionDTO[] getDimensions() {
        return dimensions;
    }

    public void setDimensions(DimensionDTO[] dimensions) {
        this.dimensions = dimensions;
    }

    public List<RollUpRuleLineDTO> getRollUpRuleLines() {
        return rollUpRuleLines;
    }

    public void setRollUpRuleLines(List<RollUpRuleLineDTO> rollUpRuleLines) {
        this.rollUpRuleLines = rollUpRuleLines;
    }

    public List<DataTypeWithMeasureLenghtDTO> getMappedDataTypes() {
        return mappedDataTypes;
    }

    public void setMappedDataTypes(List<DataTypeWithMeasureLenghtDTO> mappedDataTypes) {
        this.mappedDataTypes = mappedDataTypes;
    }

    public List<DataTypeWithMeasureLenghtDTO> getAggregatedDataTypes() {
        return aggregatedDataTypes;
    }

    public void setAggregatedDataTypes(List<DataTypeWithMeasureLenghtDTO> aggregatedDataTypes) {
        this.aggregatedDataTypes = aggregatedDataTypes;
    }

    public boolean isInsideChangeManagement() {
        return insideChangeManagement;
    }

    public void setInsideChangeManagement(boolean insideChangeManagement) {
        this.insideChangeManagement = insideChangeManagement;
    }

    public boolean isChangeManagementOutstanding() {
        return changeManagementOutstanding;
    }

    public void setChangeManagementOutstanding(boolean changeManagementOutstanding) {
        this.changeManagementOutstanding = changeManagementOutstanding;
    }

    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

}
