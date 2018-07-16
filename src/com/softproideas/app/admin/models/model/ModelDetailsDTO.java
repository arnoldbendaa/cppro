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

import java.util.List;

import com.softproideas.app.admin.financecubes.model.DimensionDTO;
import com.softproideas.commons.model.Property;

public class ModelDetailsDTO extends ModelDTO {

    private boolean locked;
    private boolean virementEntryEnabled;
    private boolean virementsInUse;
    private int versionNum;
    private DimensionDTO account;
    private DimensionDTO calendar;
    private List<DimensionDTO> business;
    private HierarchyDTO budgetHierarchy;
    private List<Property> properties;
    private List<DimensionDTO> availableAccounts;
    private List<DimensionDTO> availableCalendars;
    private List<DimensionDTO> availableBusiness;
    private List<HierarchyDTO> availableBudgetHierarchy;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isVirementEntryEnabled() {
        return virementEntryEnabled;
    }

    public void setVirementEntryEnabled(boolean virementEntryEnabled) {
        this.virementEntryEnabled = virementEntryEnabled;
    }

    public boolean isVirementsInUse() {
        return virementsInUse;
    }

    public void setVirementsInUse(boolean virementsInUse) {
        this.virementsInUse = virementsInUse;
    }

    public int getVersionNum() {
        return versionNum;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

    public DimensionDTO getAccount() {
        return account;
    }

    public void setAccount(DimensionDTO account) {
        this.account = account;
    }

    public DimensionDTO getCalendar() {
        return calendar;
    }

    public void setCalendar(DimensionDTO calendar) {
        this.calendar = calendar;
    }

    public List<DimensionDTO> getBusiness() {
        return business;
    }

    public void setBusiness(List<DimensionDTO> business) {
        this.business = business;
    }

    public HierarchyDTO getBudgetHierarchy() {
        return budgetHierarchy;
    }

    public void setBudgetHierarchy(HierarchyDTO budgetHierarchy) {
        this.budgetHierarchy = budgetHierarchy;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<DimensionDTO> getAvailableAccounts() {
        return availableAccounts;
    }

    public void setAvailableAccounts(List<DimensionDTO> availableAccounts) {
        this.availableAccounts = availableAccounts;
    }

    public List<DimensionDTO> getAvailableCalendars() {
        return availableCalendars;
    }

    public void setAvailableCalendars(List<DimensionDTO> availableCalendars) {
        this.availableCalendars = availableCalendars;
    }

    public List<DimensionDTO> getAvailableBusiness() {
        return availableBusiness;
    }

    public void setAvailableBusiness(List<DimensionDTO> availableBusiness) {
        this.availableBusiness = availableBusiness;
    }

    public List<HierarchyDTO> getAvailableBudgetHierarchy() {
        return availableBudgetHierarchy;
    }

    public void setAvailableBudgetHierarchy(List<HierarchyDTO> availableBudgetHierarchy) {
        this.availableBudgetHierarchy = availableBudgetHierarchy;
    }

}