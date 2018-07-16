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
package com.softproideas.app.admin.hierarchies.model;

import java.util.List;

import com.softproideas.app.admin.structuredisplayname.model.StructureDisplayNameData;
import com.softproideas.app.core.dimension.model.DimensionCoreDTO;

/**
 * 
 * <p>Object store hierarchy details data</p>
 *
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class HierarchyDetailsDTO extends HierarchyDTO {

    private String externalSystemRefName;
    private List<HierarchyEventDTO> hierarchyEvents;
    private Boolean readOnly;
    private String inUseLabel;
    private Boolean augentMode;
    private int versionNum;
    private boolean submitChangeManagementRequest;
    private String operation;
    private List<DimensionCoreDTO> availableDimensionForInsert;
    private List<HierarchyDimensionElementTableDTO> dimensionElement;
    private List<StructureDisplayNameData> displayNameEvents;

    public String getExternalSystemRefName() {
        return externalSystemRefName;
    }

    public void setExternalSystemRefName(String externalSystemRefName) {
        this.externalSystemRefName = externalSystemRefName;
    }

    public List<HierarchyEventDTO> getHierarchyEvents() {
        return hierarchyEvents;
    }

    public void setHierarchyEvents(List<HierarchyEventDTO> hierarchyEvents) {
        this.hierarchyEvents = hierarchyEvents;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getInUseLabel() {
        return inUseLabel;
    }

    public void setInUseLabel(String inUseLabel) {
        this.inUseLabel = inUseLabel;
    }

    public Boolean getAugentMode() {
        return augentMode;
    }

    public void setAugentMode(Boolean augentMode) {
        this.augentMode = augentMode;
    }

    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

    public boolean isSubmitChangeManagementRequest() {
        return submitChangeManagementRequest;
    }

    public void setSubmitChangeManagementRequest(boolean submitChangeManagementRequest) {
        this.submitChangeManagementRequest = submitChangeManagementRequest;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public List<DimensionCoreDTO> getAvailableDimensionForInsert() {
        return availableDimensionForInsert;
    }

    public void setAvailableDimensionForInsert(List<DimensionCoreDTO> availableDimensionForInsert) {
        this.availableDimensionForInsert = availableDimensionForInsert;
    }

    public List<HierarchyDimensionElementTableDTO> getDimensionElement() {
        return dimensionElement;
    }

    public void setDimensionElement(List<HierarchyDimensionElementTableDTO> dimensionElement) {
        this.dimensionElement = dimensionElement;
    }

    public List<StructureDisplayNameData> getDisplayNameEvents() {
        return displayNameEvents;
    }

    public void setDisplayNameEvents(List<StructureDisplayNameData> displayNameEvents) {
        this.displayNameEvents = displayNameEvents;
    }

}
