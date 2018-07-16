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
package com.softproideas.app.admin.modelmappings.model.details;

import java.util.List;

import com.softproideas.app.core.hierarchy.model.HierarchyCoreDTO;

public class MappedHierarchyDTO extends HierarchyCoreDTO {
    private String visId1;
    private String visId2;

    private boolean isResponsibilityAreaHierarchy;
    private String mappedHierarchyVisId;
    private List<String> selectedCompanies;

    public String getVisId1() {
        return visId1;
    }

    public void setVisId1(String visId1) {
        this.visId1 = visId1;
    }

    public String getVisId2() {
        return visId2;
    }

    public void setVisId2(String visId2) {
        this.visId2 = visId2;
    }

    public boolean isResponsibilityAreaHierarchy() {
        return isResponsibilityAreaHierarchy;
    }

    public void setResponsibilityAreaHierarchy(boolean isResponsibilityAreaHierarchy) {
        this.isResponsibilityAreaHierarchy = isResponsibilityAreaHierarchy;
    }

    public String getMappedHierarchyVisId() {
        return mappedHierarchyVisId;
    }

    public void setMappedHierarchyVisId(String mappedHierarchyVisId) {
        this.mappedHierarchyVisId = mappedHierarchyVisId;
    }

    public List<String> getSelectedCompanies() {
        return selectedCompanies;
    }

    public void setSelectedCompanies(List<String> selectedCompanies) {
        this.selectedCompanies = selectedCompanies;
    }

}
