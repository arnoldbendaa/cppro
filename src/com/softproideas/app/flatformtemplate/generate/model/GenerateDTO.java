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
package com.softproideas.app.flatformtemplate.generate.model;

import java.util.List;
import java.util.UUID;

import com.softproideas.app.core.financecube.model.FinanceCubeModelCoreDTO;

public class GenerateDTO {

    private UUID templateUUID;
    private UUID configurationUUID;
    private List<FinanceCubeModelCoreDTO> financeCubeModels;
    private String name;
    private String description;
    private Boolean override;
    
    // saving process consists of two phase. 
    // during first phase is checked if duplicates of flat form names exists and return list of these duplicates.
    private boolean lastRequest;

    public UUID getTemplateUUID() {
        return templateUUID;
    }

    public void setTemplateUUID(UUID templateUUID) {
        this.templateUUID = templateUUID;
    }

    public UUID getConfigurationUUID() {
        return configurationUUID;
    }

    public void setConfigurationUUID(UUID configurationUUID) {
        this.configurationUUID = configurationUUID;
    }

    public List<FinanceCubeModelCoreDTO> getFinanceCubeModels() {
        return financeCubeModels;
    }

    public void setFinanceCubeModels(List<FinanceCubeModelCoreDTO> financeCubeModels) {
        this.financeCubeModels = financeCubeModels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getOverride() {
        return override;
    }

    public void setOverride(Boolean override) {
        this.override = override;
    }

    public boolean isLastRequest() {
        return lastRequest;
    }

    public void setLastRequest(boolean lastRequest) {
        this.lastRequest = lastRequest;
    }

}
