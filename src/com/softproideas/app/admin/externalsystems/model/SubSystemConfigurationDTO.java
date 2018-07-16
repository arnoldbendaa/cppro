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
package com.softproideas.app.admin.externalsystems.model;

import java.util.List;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class SubSystemConfigurationDTO {

    List<SubSystemColumnDTO> availableColumns;
    List<SubSystemColumnDTO> selectedColumns;

    public List<SubSystemColumnDTO> getAvailableColumns() {
        return availableColumns;
    }

    public void setAvailableColumns(List<SubSystemColumnDTO> availableColumns) {
        this.availableColumns = availableColumns;
    }

    public List<SubSystemColumnDTO> getSelectedColumns() {
        return selectedColumns;
    }

    public void setSelectedColumns(List<SubSystemColumnDTO> selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    @Override
    public String toString() {
        return "SubSystemConfigurationDTO [availableColumns=" + availableColumns + ", selectedColumns=" + selectedColumns + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((availableColumns == null) ? 0 : availableColumns.hashCode());
        result = prime * result + ((selectedColumns == null) ? 0 : selectedColumns.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        SubSystemConfigurationDTO other = (SubSystemConfigurationDTO) obj;
        if (availableColumns == null) {
            if (other.availableColumns != null)
                return false;
        } else if (!availableColumns.equals(other.availableColumns))
            return false;
        if (selectedColumns == null) {
            if (other.selectedColumns != null)
                return false;
        } else if (!selectedColumns.equals(other.selectedColumns))
            return false;
        return true;
    }

}
