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
package com.softproideas.app.admin.authentication.model;

public class AuthenticationInternalDetailsDTO {

    private Integer minimumPasswordLength;
    private Integer minimumAlphas;
    private Integer minimumDigits;
    private Integer maximumRepetition;
    private Integer minimumChanges;
    private boolean passwordUseridDiffer;
    private String passwordMask;
    private Integer passwordReuseDelta;
    private Integer maximumLogonAttempts;
    private Integer passwordExpiry;

    public Integer getMinimumPasswordLength() {
        return minimumPasswordLength;
    }

    public Integer getMinimumAlphas() {
        return minimumAlphas;
    }

    public Integer getMinimumDigits() {
        return minimumDigits;
    }

    public Integer getMaximumRepetition() {
        return maximumRepetition;
    }

    public Integer getMinimumChanges() {
        return minimumChanges;
    }

    public boolean isPasswordUseridDiffer() {
        return passwordUseridDiffer;
    }

    public String getPasswordMask() {
        return passwordMask;
    }

    public Integer getPasswordReuseDelta() {
        return passwordReuseDelta;
    }

    public Integer getMaximumLogonAttempts() {
        return maximumLogonAttempts;
    }

    public Integer getPasswordExpiry() {
        return passwordExpiry;
    }

    public void setMinimumPasswordLength(Integer minimumPasswordLength) {
        this.minimumPasswordLength = minimumPasswordLength;
    }

    public void setMinimumAlphas(Integer minimumAlphas) {
        this.minimumAlphas = minimumAlphas;
    }

    public void setMinimumDigits(Integer minimumDigits) {
        this.minimumDigits = minimumDigits;
    }

    public void setMaximumRepetition(Integer maximumRepetition) {
        this.maximumRepetition = maximumRepetition;
    }

    public void setMinimumChanges(Integer minimumChanges) {
        this.minimumChanges = minimumChanges;
    }

    public void setPasswordUseridDiffer(boolean passwordUseridDiffer) {
        this.passwordUseridDiffer = passwordUseridDiffer;
    }

    public void setPasswordMask(String passwordMask) {
        this.passwordMask = passwordMask;
    }

    public void setPasswordReuseDelta(Integer passwordReuseDelta) {
        this.passwordReuseDelta = passwordReuseDelta;
    }

    public void setMaximumLogonAttempts(Integer maximumLogonAttempts) {
        this.maximumLogonAttempts = maximumLogonAttempts;
    }

    public void setPasswordExpiry(Integer passwordExpiry) {
        this.passwordExpiry = passwordExpiry;
    }

}
