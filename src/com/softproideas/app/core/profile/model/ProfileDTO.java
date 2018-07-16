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
package com.softproideas.app.core.profile.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import com.softproideas.commons.model.enums.FormType;

public class ProfileDTO {

    private int profileId;
    private boolean defaultProfile;

    @NotEmpty
    @Length(max = 120)
    @SafeHtml(whitelistType = WhiteListType.NONE)
    private String name;

    @Length(max = 128)
    @SafeHtml(whitelistType = WhiteListType.NONE)
    private String description;

    private int formId;
    private int structureId0; // dim1 structureId
    private int structureId1; // dim2 structureId
    private int structureElementId0; // dim1 id
    private int structureElementId1; // dim2 id
    private String elementLabel0; // dim1 name
    private String elementLabel1; // dim2 name
    private String dataType; // name
    private FormType formType;

    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public boolean isDefaultProfile() {
        return defaultProfile;
    }

    public void setDefaultProfile(boolean defaultProfile) {
        this.defaultProfile = defaultProfile;
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

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public int getStructureId0() {
        return structureId0;
    }

    public void setStructureId0(int structureId0) {
        this.structureId0 = structureId0;
    }

    public int getStructureId1() {
        return structureId1;
    }

    public void setStructureId1(int structureId1) {
        this.structureId1 = structureId1;
    }

    public int getStructureElementId0() {
        return structureElementId0;
    }

    public void setStructureElementId0(int structureElementId0) {
        this.structureElementId0 = structureElementId0;
    }

    public int getStructureElementId1() {
        return structureElementId1;
    }

    public void setStructureElementId1(int structureElementId1) {
        this.structureElementId1 = structureElementId1;
    }

    public String getElementLabel0() {
        return elementLabel0;
    }

    public void setElementLabel0(String elementLabel0) {
        this.elementLabel0 = elementLabel0;
    }

    public String getElementLabel1() {
        return elementLabel1;
    }

    public void setElementLabel1(String elementLabel1) {
        this.elementLabel1 = elementLabel1;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public FormType getFormType() {
        return formType;
    }

    public void setFormType(FormType formType) {
        this.formType = formType;
    }

    @Override
    public String toString() {
        return "ProfileDTO [profileId=" + profileId + ", defaultProfile=" + defaultProfile + ", name=" + name + ", description=" + description + ", formId=" + formId + ", structureId0=" + structureId0 + ", structureId1=" + structureId1 + ", structureElementId0=" + structureElementId0 + ", structureElementId1=" + structureElementId1 + ", elementLabel0=" + elementLabel0 + ", elementLabel1=" + elementLabel1 + ", dataType=" + dataType + ", formType=" + formType + "]";
    }

}
