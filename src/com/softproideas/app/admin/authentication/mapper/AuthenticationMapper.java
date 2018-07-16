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
package com.softproideas.app.admin.authentication.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.authenticationpolicy.AuthenticationPolicyRef;
import com.cedar.cp.dto.authenticationpolicy.AllAuthenticationPolicysELO;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyImpl;
import com.cedar.cp.dto.authenticationpolicy.AuthenticationPolicyPK;
import com.cedar.cp.dto.user.UsersWithSecurityStringELO;
import com.softproideas.app.admin.authentication.model.AdminUserDTO;
import com.softproideas.app.admin.authentication.model.AuthenticationDTO;
import com.softproideas.app.admin.authentication.model.AuthenticationDetailsDTO;
import com.softproideas.app.admin.authentication.model.AuthenticationInternalDetailsDTO;
import com.softproideas.app.admin.authentication.model.AuthenticationNtlmDetailsDTO;
import com.softproideas.app.admin.authentication.model.AuthenticationTechniqueDTO;
import com.softproideas.app.admin.authentication.model.SecurityLogDTO;
import com.softproideas.app.admin.authentication.util.AuthenticationUtil;

public class AuthenticationMapper {

    @SuppressWarnings("unchecked")
    public static List<AuthenticationDTO> mapAllAuthenticationsELO(AllAuthenticationPolicysELO list) {
        List<AuthenticationDTO> authenticationsDTOList = new ArrayList<AuthenticationDTO>();

        for (Iterator<AllAuthenticationPolicysELO> it = list.iterator(); it.hasNext();) {
            AllAuthenticationPolicysELO row = it.next();
            AuthenticationDTO authenticationDTO = new AuthenticationDTO();

            AuthenticationPolicyRef ref = (AuthenticationPolicyRef) row.getAuthenticationPolicyEntityRef();

            authenticationDTO.setAuthenticationId(((AuthenticationPolicyPK) ref.getPrimaryKey()).getAuthenticationPolicyId());
            authenticationDTO.setAuthenticationVisId(row.getVisId());
            authenticationDTO.setAuthenticationDescription(row.getDescription());

            Integer authenticationTechniqueNumber = row.getAuthenticationTechnique();
            String authenticationTechniqueName = AuthenticationUtil.getAuthenticationTechniqueName(authenticationTechniqueNumber);
            AuthenticationTechniqueDTO atDTO = new AuthenticationTechniqueDTO(authenticationTechniqueNumber, authenticationTechniqueName);
            authenticationDTO.setAuthenticationTechnique(atDTO);

            authenticationDTO.setActive(row.getActive());

            authenticationsDTOList.add(authenticationDTO);
        }

        return authenticationsDTOList;
    }

    public static AuthenticationDetailsDTO mapAuthentication(AuthenticationPolicyImpl authentication) {
        AuthenticationDetailsDTO authenticationDetailsDTO = new AuthenticationDetailsDTO();

        // all types
        authenticationDetailsDTO.setActive(authentication.isActive());
        authenticationDetailsDTO.setVersionNum(authentication.getVersionNum());
        authenticationDetailsDTO.setAuthenticationId(((AuthenticationPolicyPK) authentication.getPrimaryKey()).getAuthenticationPolicyId());
        authenticationDetailsDTO.setAuthenticationVisId(authentication.getVisId());
        authenticationDetailsDTO.setAuthenticationDescription(authentication.getDescription());
        authenticationDetailsDTO.setSecurityAdministrator(authentication.getSecurityAdministrator());
        // securityLog field
        Integer securityNumber = authentication.getSecurityLog();
        String securityName = AuthenticationUtil.getSecurityLogName(securityNumber);
        SecurityLogDTO securityDTO = new SecurityLogDTO(securityNumber, securityName);
        authenticationDetailsDTO.setSecurityLog(securityDTO);
        // authenticationTechnique field
        Integer techniqueNumber = authentication.getAuthenticationTechnique();
        String techniqueName = AuthenticationUtil.getAuthenticationTechniqueName(techniqueNumber);
        AuthenticationTechniqueDTO techniqueDTO = new AuthenticationTechniqueDTO(techniqueNumber, techniqueName);
        authenticationDetailsDTO.setAuthenticationTechnique(techniqueDTO);

        // Internal
        AuthenticationInternalDetailsDTO internalDTO = new AuthenticationInternalDetailsDTO();
        internalDTO.setMinimumPasswordLength(authentication.getMinimumPasswordLength());
        internalDTO.setMinimumAlphas(authentication.getMinimumAlphas());
        internalDTO.setMinimumDigits(authentication.getMinimumDigits());
        internalDTO.setMaximumRepetition(authentication.getMaximumRepetition());
        internalDTO.setMinimumChanges(authentication.getMinimumChanges());
        internalDTO.setPasswordUseridDiffer(authentication.isPasswordUseridDiffer());
        internalDTO.setPasswordMask(authentication.getPasswordMask());
        internalDTO.setPasswordReuseDelta(authentication.getPasswordReuseDelta());
        internalDTO.setMaximumLogonAttempts(authentication.getMaximumLogonAttempts());
        internalDTO.setPasswordExpiry(authentication.getPasswordExpiry());
        authenticationDetailsDTO.setInternal(internalDTO);

        // External
        authenticationDetailsDTO.setJaasEntryName(authentication.getJaasEntryName());

        // Cosign
        authenticationDetailsDTO.setCosignConfigurationFile(authentication.getCosignConfigurationFile());

        // NTLM
        AuthenticationNtlmDetailsDTO ntlmDTO = new AuthenticationNtlmDetailsDTO();
        ntlmDTO.setNtlmDomain(authentication.getNtlmDomain());
        ntlmDTO.setNtlmDomainController(authentication.getNtlmDomainController());
        ntlmDTO.setNtlmLogLevel(authentication.getNtlmLogLevel());
        ntlmDTO.setNtlmNetbiosWins(authentication.getNtlmNetbiosWins());
        authenticationDetailsDTO.setNtlm(ntlmDTO);

        // SSO - nothing special

        return authenticationDetailsDTO;
    }

    public static AuthenticationPolicyImpl mapAuthenticationDetailsDTOToAuthenticationDetailsImpl(AuthenticationPolicyImpl impl, AuthenticationDetailsDTO authentication) {
        // all types
        impl.setVersionNum(authentication.getVersionNum());
        impl.setVisId(authentication.getAuthenticationVisId());
        impl.setDescription(authentication.getAuthenticationDescription());
        impl.setSecurityAdministrator(authentication.getSecurityAdministrator());
        impl.setSecurityLog(authentication.getSecurityLog().getIndex());
        Integer authenticationTechniqueIndex = authentication.getAuthenticationTechnique().getIndex();
        impl.setAuthenticationTechnique(authenticationTechniqueIndex);
        // "active" is not set, because it is changed by separate function

        switch (authenticationTechniqueIndex) {
            case 1: // Internal
                impl.setMinimumPasswordLength(authentication.getInternal().getMinimumPasswordLength());
                impl.setMinimumAlphas(authentication.getInternal().getMinimumAlphas());
                impl.setMinimumDigits(authentication.getInternal().getMinimumDigits());
                impl.setMaximumRepetition(authentication.getInternal().getMaximumRepetition());
                impl.setMinimumChanges(authentication.getInternal().getMinimumChanges());
                impl.setPasswordUseridDiffer(authentication.getInternal().isPasswordUseridDiffer());
                impl.setPasswordMask(authentication.getInternal().getPasswordMask());
                impl.setPasswordReuseDelta(authentication.getInternal().getPasswordReuseDelta());
                impl.setMaximumLogonAttempts(authentication.getInternal().getMaximumLogonAttempts());
                impl.setPasswordExpiry(authentication.getInternal().getPasswordExpiry());
                break;
            case 2: // External
                impl.setJaasEntryName(authentication.getJaasEntryName());
                break;
            case 3: // Cosign
                // TODO needed?
                // impl.setCosignConfigurationFile(authentication.getCosignConfigurationFile());
                break;
            case 4: // NTLM
                impl.setNtlmDomain(authentication.getNtlm().getNtlmDomain());
                impl.setNtlmDomainController(authentication.getNtlm().getNtlmDomainController());
                impl.setNtlmLogLevel(authentication.getNtlm().getNtlmLogLevel());
                impl.setNtlmNetbiosWins(authentication.getNtlm().getNtlmNetbiosWins());
                break;
            case 5: // SSO
                break;
        }

        return impl;
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<AdminUserDTO> mapUsersWithSecurityStringELO(UsersWithSecurityStringELO usersWithSecurityStringELO) {
        ArrayList<AdminUserDTO> list = new ArrayList<AdminUserDTO>();
        // Authentication can have admin user with id 0 (=no admin)
        AdminUserDTO nullUserDTO = new AdminUserDTO(0, "(no admin)");
        list.add(nullUserDTO);
        // all users
        for (Iterator<UsersWithSecurityStringELO> it = usersWithSecurityStringELO.iterator(); it.hasNext();) {
            UsersWithSecurityStringELO user = it.next();
            AdminUserDTO userDTO = new AdminUserDTO(user.getUserId(), user.getName());
            list.add(userDTO);
        }
        return list;
    }

}
