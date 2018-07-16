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

import com.softproideas.app.core.externalsystem.model.ExternalSystemCoreDTO;
import com.softproideas.app.core.model.model.ModelCoreDTO;

public class MappedModelDetailsDTO extends MappedModelDTO {

    private List<MappedDimensionDTO> mappedDimensions;
    private List<MappedFinanceCubeDTO> mappedFinanceCubes;
    private MappedCalendarDTO mappedCalendar;
    private ModelCoreDTO model;
    private ExternalSystemCoreDTO externalSystem;
    private List<String> listCompanies;
    private int versionNum;
    private String validationError;

    public List<MappedDimensionDTO> getMappedDimensions() {
        return mappedDimensions;
    }

    public void setMappedDimensions(List<MappedDimensionDTO> mappedDimensions) {
        this.mappedDimensions = mappedDimensions;
    }

    public List<MappedFinanceCubeDTO> getMappedFinanceCubes() {
        return mappedFinanceCubes;
    }

    public void setMappedFinanceCubes(List<MappedFinanceCubeDTO> mappedFinanceCubes) {
        this.mappedFinanceCubes = mappedFinanceCubes;
    }

    public MappedCalendarDTO getMappedCalendar() {
        return mappedCalendar;
    }

    public void setMappedCalendar(MappedCalendarDTO mappedCalendar) {
        this.mappedCalendar = mappedCalendar;
    }

    public ModelCoreDTO getModel() {
        return model;
    }

    public void setModel(ModelCoreDTO model) {
        this.model = model;
    }

    public ExternalSystemCoreDTO getExternalSystem() {
        return externalSystem;
    }

    public void setExternalSystem(ExternalSystemCoreDTO externalSystem) {
        this.externalSystem = externalSystem;
    }

    public List<String> getListCompanies() {
        return listCompanies;
    }

    public void setListCompanies(List<String> listCompanies) {
        this.listCompanies = listCompanies;
    }

    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

    public String getValidationError() {
        return validationError;
    }

    public void setValidationError(String validationError) {
        this.validationError = validationError;
    }

}
