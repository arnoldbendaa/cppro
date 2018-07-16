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
package com.softproideas.app.admin.budgetcycles.model;

import com.softproideas.app.core.budgetcycle.model.BudgetCycleCoreDTO;
import com.softproideas.app.core.model.model.ModelCoreDTO;

public class BudgetCycleDTO extends BudgetCycleCoreDTO {

    private ModelCoreDTO model;
    private int status;
    private int periodFromId;
    private int periodToId;
    private String periodFromVisId;
    private String periodToVisId;
    private String category;

    public ModelCoreDTO getModel() {
        return model;
    }

    public void setModel(ModelCoreDTO model) {
        this.model = model;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPeriodFromId() {
        return periodFromId;
    }

    public void setPeriodFromId(int periodFromId) {
        this.periodFromId = periodFromId;
    }

    public int getPeriodToId() {
        return periodToId;
    }

    public void setPeriodToId(int periodToId) {
        this.periodToId = periodToId;
    }

    public String getPeriodFromVisId() {
        return periodFromVisId;
    }

    public void setPeriodFromVisId(String periodFromVisId) {
        this.periodFromVisId = periodFromVisId;
    }

    public String getPeriodToVisId() {
        return periodToVisId;
    }

    public void setPeriodToVisId(String periodToVisId) {
        this.periodToVisId = periodToVisId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "BudgetCycleDTO [model=" + model + ", status=" + status + ", periodFromId=" + periodFromId + ", periodToId=" + periodToId + ", periodFromVisId=" + periodFromVisId + ", periodToVisId=" + periodToVisId + "]";
    }

}
