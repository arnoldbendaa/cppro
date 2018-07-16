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
package com.softproideas.app.admin.financecubeformula.model;

import java.util.List;

import com.softproideas.app.admin.datatypes.model.DataTypeDTO;
import com.softproideas.app.admin.financecubes.model.DimensionDTO;

public class FormulaDeploymentLineDTO {

    private DimensionDTO[] dimensions;
    private int lineIndex;
    private int formulaDeploymentId;
    private List<DataTypeDTO> dataTypes;
    private List<DeploymentDimensionEntries> deploymentDimensionEntries;

    public DimensionDTO[] getDimensions() {
        return dimensions;
    }

    public void setDimensions(DimensionDTO[] dimensionDTOs) {
        this.dimensions = dimensionDTOs;
    }

    public int getLineIndex() {
        return lineIndex;
    }

    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    public int getFormulaDeploymentId() {
        return formulaDeploymentId;
    }

    public void setFormulaDeploymentId(int formulaDeploymentId) {
        this.formulaDeploymentId = formulaDeploymentId;
    }

    public List<DataTypeDTO> getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(List<DataTypeDTO> dataTypes) {
        this.dataTypes = dataTypes;
    }

    public List<DeploymentDimensionEntries> getDeploymentDimensionEntries() {
        return deploymentDimensionEntries;
    }

    public void setDeploymentDimensionEntries(List<DeploymentDimensionEntries> deploymentDimensionEntries) {
        this.deploymentDimensionEntries = deploymentDimensionEntries;
    }

}
