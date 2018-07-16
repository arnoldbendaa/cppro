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

import com.softproideas.app.admin.financecubes.model.DimensionDTO;

public class FinanceCubeFormulaDetailsDTO extends FinanceCubeFormulaDTO {

    private boolean financeCubeFormulaEnabled;
    private boolean deploymentInd;
    private String formulaText;
    private String formulaMessage;
    private DimensionDTO[] dimensions;
    private List<FormulaDeploymentLineDTO> formulaLine;

    public boolean isFinanceCubeFormulaEnabled() {
        return financeCubeFormulaEnabled;
    }

    public void setFinanceCubeFormulaEnabled(boolean financeCubeFormulaEnabled) {
        this.financeCubeFormulaEnabled = financeCubeFormulaEnabled;
    }

    public boolean isDeploymentInd() {
        return deploymentInd;
    }

    public void setDeploymentInd(boolean deploymentInd) {
        this.deploymentInd = deploymentInd;
    }

    public String getFormulaText() {
        return formulaText;
    }

    public void setFormulaText(String formulaText) {
        this.formulaText = formulaText;
    }

    public String getFormulaMessage() {
        return formulaMessage;
    }

    public void setFormulaMessage(String formulaMessage) {
        this.formulaMessage = formulaMessage;
    }

    public DimensionDTO[] getDimensions() {
        return dimensions;
    }

    public void setDimensions(DimensionDTO[] dimensions) {
        this.dimensions = dimensions;
    }

    public List<FormulaDeploymentLineDTO> getFormulaLine() {
        return formulaLine;
    }

    public void setFormulaLine(List<FormulaDeploymentLineDTO> formulaLine) {
        this.formulaLine = formulaLine;
    }

}
