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
package com.softproideas.app.admin.hierarchies.model;

/**
 * 
 * <p>Object store hierarchy event data</p>
 *
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class HierarchyEventDTO {

    private int index;
    private String parentVisId;
    private String description;
    private String visId;
    private int creditDebit;
    private int augCreditDebit;
    private boolean canMove;
    private boolean isAugentElement;
    private String textNode;
    private boolean isFeedImpl;
    private boolean disabled;
    private int hierarchyElementId;
    private int hierarchyParentElementId;
    private String origVisId;
    private HierarchyEventType eventType;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getParentVisId() {
        return parentVisId;
    }

    public void setParentVisId(String parentVisId) {
        this.parentVisId = parentVisId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVisId() {
        return visId;
    }

    public void setVisId(String visId) {
        this.visId = visId;
    }

    public int getCreditDebit() {
        return creditDebit;
    }

    public void setCreditDebit(int creditDebit) {
        this.creditDebit = creditDebit;
    }

    public int getAugCreditDebit() {
        return augCreditDebit;
    }

    public void setAugCreditDebit(int augCreditDebit) {
        this.augCreditDebit = augCreditDebit;
    }

    public boolean isCanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }

    public boolean isAugentElement() {
        return isAugentElement;
    }

    public void setAugentElement(boolean isAugentElement) {
        this.isAugentElement = isAugentElement;
    }

    public String getTextNode() {
        return textNode;
    }

    public void setTextNode(String textNode) {
        this.textNode = textNode;
    }

    public boolean isFeedImpl() {
        return isFeedImpl;
    }

    public void setFeedImpl(boolean isFeedImpl) {
        this.isFeedImpl = isFeedImpl;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public int getHierarchyElementId() {
        return hierarchyElementId;
    }

    public void setHierarchyElementId(int hierarchyElementId) {
        this.hierarchyElementId = hierarchyElementId;
    }

    public int getHierarchyParentElementId() {
        return hierarchyParentElementId;
    }

    public void setHierarchyParentElementId(int hierarchyParentElementId) {
        this.hierarchyParentElementId = hierarchyParentElementId;
    }

    public String getOrigVisId() {
        return origVisId;
    }

    public void setOrigVisId(String origVisId) {
        this.origVisId = origVisId;
    }

    public HierarchyEventType getEventType() {
        return eventType;
    }

    public void setEventType(HierarchyEventType eventType) {
        this.eventType = eventType;
    }

}
