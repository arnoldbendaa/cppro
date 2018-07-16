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

/**
 * <p>TODO: Comment me</p>
 *
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * <p>2014 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class SubSystemDTO {

    private Integer subSystemId;
    private String subSystemDescription;
    private SubSystemConfigurationDTO subSystemConfiguration;

    public Integer getSubSystemId() {
        return subSystemId;
    }

    public void setSubSystemId(Integer subSystemId) {
        this.subSystemId = subSystemId;
    }

    public String getSubSystemDescription() {
        return subSystemDescription;
    }

    public void setSubSystemDescription(String subSystemDescription) {
        this.subSystemDescription = subSystemDescription;
    }

    public SubSystemConfigurationDTO getSubSystemConfiguration() {
        return subSystemConfiguration;
    }

    public void setSubSystemConfiguration(SubSystemConfigurationDTO subSystemConfiguration) {
        this.subSystemConfiguration = subSystemConfiguration;
    }

    @Override
    public String toString() {
        return "SubSystemDTO [subSystemId=" + subSystemId + ", subSystemDescription=" + subSystemDescription + ", subSystemConfiguration=" + subSystemConfiguration + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((subSystemConfiguration == null) ? 0 : subSystemConfiguration.hashCode());
        result = prime * result + ((subSystemDescription == null) ? 0 : subSystemDescription.hashCode());
        result = prime * result + ((subSystemId == null) ? 0 : subSystemId.hashCode());
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
        SubSystemDTO other = (SubSystemDTO) obj;
        if (subSystemConfiguration == null) {
            if (other.subSystemConfiguration != null)
                return false;
        } else if (!subSystemConfiguration.equals(other.subSystemConfiguration))
            return false;
        if (subSystemDescription == null) {
            if (other.subSystemDescription != null)
                return false;
        } else if (!subSystemDescription.equals(other.subSystemDescription))
            return false;
        if (subSystemId == null) {
            if (other.subSystemId != null)
                return false;
        } else if (!subSystemId.equals(other.subSystemId))
            return false;
        return true;
    }

}
