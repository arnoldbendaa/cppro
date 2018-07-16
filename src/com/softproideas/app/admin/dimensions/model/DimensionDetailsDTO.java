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
package com.softproideas.app.admin.dimensions.model;

import java.util.List;

public class DimensionDetailsDTO extends DimensionDTO {

    private String externalSystemRefName;
    private List<DimensionElementDTO> dimensionElements;
    private Boolean readOnly;
    private String inUseLabel;
    private Boolean augentMode;
    private int versionNum;
    private boolean submitChangeManagementRequest;

    public String getExternalSystemRefName() {
        return externalSystemRefName;
    }

    public List<DimensionElementDTO> getDimensionElements() {
        return dimensionElements;
    }

    public Boolean isReadOnly() {
        return readOnly;
    }

    public String getInUseLabel() {
        return inUseLabel;
    }

    public Boolean isAugentMode() {
        return augentMode;
    }

    public int getVersionNum() {
        return versionNum;
    }

    public boolean isSubmitChangeManagementRequest() {
        return submitChangeManagementRequest;
    }

    public void setExternalSystemRefName(String externalSystemRefName) {
        this.externalSystemRefName = externalSystemRefName;
    }

    public void setDimensionElements(List<DimensionElementDTO> dimensionElements) {
        this.dimensionElements = dimensionElements;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setInUseLabel(String inUseLabel) {
        this.inUseLabel = inUseLabel;
    }

    public void setAugentMode(Boolean augentMode) {
        this.augentMode = augentMode;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

    public void setSubmitChangeManagementRequest(boolean submitChangeManagementRequest) {
        this.submitChangeManagementRequest = submitChangeManagementRequest;
    }
}
