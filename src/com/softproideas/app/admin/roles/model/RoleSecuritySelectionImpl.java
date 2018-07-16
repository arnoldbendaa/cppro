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
package com.softproideas.app.admin.roles.model;

import java.io.Serializable;

import com.cedar.cp.api.base.EntityRef;
import com.cedar.cp.api.role.RoleSecuritySelection;

@SuppressWarnings("serial")
public class RoleSecuritySelectionImpl implements RoleSecuritySelection, Serializable {

    private boolean mSelected;
    private EntityRef mRoleSecurity;

    public RoleSecuritySelectionImpl() {
    }

    public RoleSecuritySelectionImpl(boolean selected, EntityRef roleSecurity) {
        this.mSelected = selected;
        this.mRoleSecurity = roleSecurity;
    }

    public void setSelected(boolean selected) {
        this.mSelected = selected;
    }

    public void setRoleSecurity(EntityRef roleSecurity) {
        this.mRoleSecurity = roleSecurity;
    }

    public EntityRef getRoleSecurity() {
        return this.mRoleSecurity;
    }

    public boolean isSelected() {
        return this.mSelected;
    }

    public String toString() {
        String[] actions = this.mRoleSecurity.toString().split("\\.");
        return actions != null && actions.length > 1 ? actions[1] : this.mRoleSecurity.toString();
    }
}
