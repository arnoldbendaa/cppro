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
package com.softproideas.app.admin.authentication.util;

import java.util.ArrayList;
import java.util.Arrays;

import com.softproideas.app.admin.authentication.model.AuthenticationTechniqueDTO;
import com.softproideas.app.admin.authentication.model.SecurityLogDTO;

public class AuthenticationUtil {

    private static String[] authenticationTechniqueNames = { "", "Internal", "External", "Cosign", "NTLM", "SSO" };

    private static String[] securityLogNames = { "", "NONE", "FAILED", "FAILED_AND_SUCCESSFUL", "ALL" };

    public static ArrayList<AuthenticationTechniqueDTO> getAuthenticationTechniques() {
        ArrayList<AuthenticationTechniqueDTO> list = new ArrayList<AuthenticationTechniqueDTO>();
        for (int i = 1; i < authenticationTechniqueNames.length; i++) { // first element is unused
            AuthenticationTechniqueDTO obj = new AuthenticationTechniqueDTO(i, authenticationTechniqueNames[i]);
            list.add(obj);
        }
        return list;
    }

    public static String getAuthenticationTechniqueName(Integer authenticationTechnique) {
        if (authenticationTechnique != null) {
            return authenticationTechniqueNames[authenticationTechnique];
        } else {
            return "";
        }
    }

    public static Integer getAuthenticationTechniqueNumber(String authenticationTechniqueName) {
        return Arrays.asList(authenticationTechniqueNames).indexOf(authenticationTechniqueName);
    }

    public static ArrayList<SecurityLogDTO> getSecurityLogs() {
        ArrayList<SecurityLogDTO> list = new ArrayList<SecurityLogDTO>();
        for (int i = 1; i < securityLogNames.length; i++) { // first element is unused
            SecurityLogDTO obj = new SecurityLogDTO(i, securityLogNames[i]);
            list.add(obj);
        }
        return list;
    }

    public static String getSecurityLogName(Integer securityLog) {
        if (securityLog != null) {
            return securityLogNames[securityLog];
        } else {
            return "";
        }
    }

    public static Integer getSecurityLogNumber(String securityLogName) {
        return Arrays.asList(securityLogNames).indexOf(securityLogName);
    }

}
