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

import com.softproideas.app.admin.dimensions.model.DimensionDTO;

public class MappedDimensionDTO extends DimensionDTO {
    
    private List<MappedHierarchyDTO> mappedHierchies;
    private List<MappedDimensionElementDTO> mappedDimensionElements;
    private String pathVisId;
    
    public List<MappedHierarchyDTO> getMappedHierchies() {
        return mappedHierchies;
    }

    public void setMappedHierchies(List<MappedHierarchyDTO> mappedHierchies) {
        this.mappedHierchies = mappedHierchies;
    }

    public List<MappedDimensionElementDTO> getMappedDimensionElements() {
        return mappedDimensionElements;
    }

    public void setMappedDimensionElements(List<MappedDimensionElementDTO> mappedDimensionElements) {
        this.mappedDimensionElements = mappedDimensionElements;
    }

    public String getPathVisId() {
        return pathVisId;
    }

    public void setPathVisId(String pathVisId) {
        this.pathVisId = pathVisId;
    }

}
