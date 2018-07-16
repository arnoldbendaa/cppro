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
/**
 * 
 */
package com.softproideas.app.flatformtemplate.configuration.model;

import java.util.ArrayList;
import java.util.UUID;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class DimensionForFlatFormTemplateDTO {
    private UUID dimensionUUID;
    private String dimensionVisId;
    private String sheetName;
    private String modelVisId;
    private int index;
    private boolean hidden;
    private boolean oldDimension;
    private ArrayList<String> excludedDimensions;

    public UUID getDimensionUUID() {
        return dimensionUUID;
    }

    public void setDimensionUUID(UUID dimensionUUID) {
        this.dimensionUUID = dimensionUUID;
    }

    public String getDimensionVisId() {
        return dimensionVisId;
    }

    public void setDimensionVisId(String dimensionVisId) {
        this.dimensionVisId = dimensionVisId;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getModelVisId() {
        return modelVisId;
    }

    public void setModelVisId(String modelVisId) {
        this.modelVisId = modelVisId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isOldDimension() {
        return oldDimension;
    }

    public void setOldDimension(boolean oldDimension) {
        this.oldDimension = oldDimension;
    }

    public ArrayList<String> getExcludedDimensions() {
        return excludedDimensions;
    }

    public void setExcludedDimensions(ArrayList<String> excludedDimensions) {
        this.excludedDimensions = excludedDimensions;
    }
}
