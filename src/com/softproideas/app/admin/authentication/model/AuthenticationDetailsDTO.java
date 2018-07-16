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

public class AuthenticationDetailsDTO extends AuthenticationDTO {

    private int versionNum;
    private int securityAdministrator;
    private SecurityLogDTO securityLog;
    private AuthenticationInternalDetailsDTO internal;
    private String jaasEntryName;
    private String cosignConfigurationFile;
    private AuthenticationNtlmDetailsDTO ntlm;

    public int getVersionNum() {
        return versionNum;
    }

    public int getSecurityAdministrator() {
        return securityAdministrator;
    }

    public SecurityLogDTO getSecurityLog() {
        return securityLog;
    }

    public AuthenticationInternalDetailsDTO getInternal() {
        return internal;
    }

    public String getJaasEntryName() {
        return jaasEntryName;
    }

    public String getCosignConfigurationFile() {
        return cosignConfigurationFile;
    }

    public AuthenticationNtlmDetailsDTO getNtlm() {
        return ntlm;
    }

    public void setVersionNum(int versionNum) {
        this.versionNum = versionNum;
    }

    public void setSecurityAdministrator(int securityAdministrator) {
        this.securityAdministrator = securityAdministrator;
    }

    public void setSecurityLog(SecurityLogDTO securityLog) {
        this.securityLog = securityLog;
    }

    public void setInternal(AuthenticationInternalDetailsDTO internal) {
        this.internal = internal;
    }

    public void setJaasEntryName(String jaasEntryName) {
        this.jaasEntryName = jaasEntryName;
    }

    public void setCosignConfigurationFile(String cosignConfigurationFile) {
        this.cosignConfigurationFile = cosignConfigurationFile;
    }

    public void setNtlm(AuthenticationNtlmDetailsDTO ntlm) {
        this.ntlm = ntlm;
    }

}
