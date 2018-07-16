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
package com.softproideas.app.admin.changemanagement.model;

import java.sql.Timestamp;

import com.softproideas.app.core.model.model.ModelCoreDTO;

public class ChangeMgmtDTO {

    private int changeMgmtId;
    private ModelCoreDTO model;
    private Timestamp created;
    private String sourceSystem;
    private int taskId;

    public int getChangeMgmtId() {
        return changeMgmtId;
    }

    public ModelCoreDTO getModel() {
        return model;
    }

    public Timestamp getCreated() {
        return created;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setChangeMgmtId(int changeMgmtId) {
        this.changeMgmtId = changeMgmtId;
    }

    public void setModel(ModelCoreDTO model) {
        this.model = model;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

}
