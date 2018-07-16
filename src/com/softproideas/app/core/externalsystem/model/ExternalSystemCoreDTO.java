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
package com.softproideas.app.core.externalsystem.model;

public class ExternalSystemCoreDTO {

    private int externalSystemId;
    private String externalSystemVisId;
    private String externalSystemDescription;

    public int getExternalSystemId() {
        return externalSystemId;
    }

    public void setExternalSystemId(int externalSystemId) {
        this.externalSystemId = externalSystemId;
    }

    public String getExternalSystemVisId() {
        return externalSystemVisId;
    }

    public void setExternalSystemVisId(String externalSystemVisId) {
        this.externalSystemVisId = externalSystemVisId;
    }

    public String getExternalSystemDescription() {
        return externalSystemDescription;
    }

    public void setExternalSystemDescription(String externalSystemDescription) {
        this.externalSystemDescription = externalSystemDescription;
    }

    @Override
    public String toString() {
        return "ExternalSystemCoreDTO [externalSystemId=" + externalSystemId + ", externalSystemVisId=" + externalSystemVisId + ", externalSystemDescription=" + externalSystemDescription + "]";
    }

}
