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
package com.softproideas.app.admin.models.model;

/**
 * <p>Connects Finance Cube with Model. Mainly to be use with filters or to much id with visId.</p>
 * 
 * @author Jacek Kurasiewicz
 * @email jk@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class FinanceCubeModelDTO {

    private int modelId;
    private String modelVisId;
    private int financeCubeId;
    private String financeCubeVisId;
    private String description;

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public String getModelVisId() {
        return modelVisId;
    }

    public void setModelVisId(String modelVisId) {
        this.modelVisId = modelVisId;
    }

    public int getFinanceCubeId() {
        return financeCubeId;
    }

    public void setFinanceCubeId(int financeCubeId) {
        this.financeCubeId = financeCubeId;
    }

    public String getFinanceCubeVisId() {
        return financeCubeVisId;
    }

    public void setFinanceCubeVisId(String financeCubeVisId) {
        this.financeCubeVisId = financeCubeVisId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "FinanceCubeModelDTO [modelId=" + modelId + ", modelVisId=" + modelVisId + ", financeCubeId=" + financeCubeId + ", financeCubeVisId=" + financeCubeVisId + ", description=" + description + "]";
    }

}
