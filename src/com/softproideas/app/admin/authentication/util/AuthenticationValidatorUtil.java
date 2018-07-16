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

import com.softproideas.app.admin.authentication.model.AuthenticationDetailsDTO;
import com.softproideas.commons.model.error.ValidationError;

public class AuthenticationValidatorUtil {

    /**
     * General validation method for Authentication details. It calls other methods for various fields.
     * @param oldAuthentication     from database
     * @param newAuthentication     new data from html form
     */
    public static ValidationError validateAuthenticationDetails(AuthenticationDetailsDTO newAuthentication) {
        ValidationError error = new ValidationError();
        validateVisId(error, newAuthentication.getAuthenticationVisId());
        validateDescription(error, newAuthentication.getAuthenticationDescription());
        Integer authenticationTechniqueIndex = newAuthentication.getAuthenticationTechnique().getIndex();
        validateAuthenticationTechnique(error, authenticationTechniqueIndex);
        validateSecurityLog(error, newAuthentication.getSecurityLog().getIndex());
        switch (authenticationTechniqueIndex) {
            case 1: // Internal
                Integer minimumPasswordLength = newAuthentication.getInternal().getMinimumPasswordLength();
                Integer minimumAlphas = newAuthentication.getInternal().getMinimumAlphas();
                Integer minimumDigits = newAuthentication.getInternal().getMinimumDigits();
                Integer maximumRepetition = newAuthentication.getInternal().getMaximumRepetition();
                Integer minimumChanges = newAuthentication.getInternal().getMinimumChanges();
                Integer passwordReuseDelta = newAuthentication.getInternal().getPasswordReuseDelta();
                Integer maximumLogonAttempts = newAuthentication.getInternal().getMaximumLogonAttempts();
                Integer passwordExpiry = newAuthentication.getInternal().getPasswordExpiry();
                String passwordMask = newAuthentication.getInternal().getPasswordMask();
                validateMinimumPasswordLength(error, minimumPasswordLength);
                validateMinimumAlphas(error, minimumAlphas);
                validateMinimumDigits(error, minimumDigits);
                validateMaximumRepetition(error, maximumRepetition);
                validateMinimumChanges(error, minimumChanges);
                validatePasswordReuseDelta(error, passwordReuseDelta);
                validateMaximumLogonAttempts(error, maximumLogonAttempts);
                validatePasswordExpiry(error, passwordExpiry);
                validatePasswordMask(error, passwordMask, minimumPasswordLength, minimumChanges, passwordReuseDelta, minimumAlphas, maximumLogonAttempts, minimumDigits);
                break;
            case 2: // External
                validateJaasEntryName(error, newAuthentication.getJaasEntryName());
                break;
            case 3: // Cosign
                validateCosignConfigurationFile(error, newAuthentication.getCosignConfigurationFile());
                break;
            case 4: // NTLM
                validateNtlmNetbiosWins(error, newAuthentication.getNtlm().getNtlmNetbiosWins());
                validateNtlmDomain(error, newAuthentication.getNtlm().getNtlmDomain());
                validateNtlmDomainController(error, newAuthentication.getNtlm().getNtlmDomainController());
                validateNtlmLogLevel(error, newAuthentication.getNtlm().getNtlmLogLevel());
                break;
            case 5: // SSO
                break;
        }
        // validateVisId(error, newAuthentication.getAuthenticationVisId(), null);
        return error;
    }

    public static void validateVisId(ValidationError error, String newVisId) {
        String fieldName = "authenticationVisId";
        if (newVisId != null && newVisId.length() > 20) {
            error.addFieldError(fieldName, "Length (" + newVisId.length() + ") of VisId must not exceed 20 on a AuthenticationPolicy");
        } else if (newVisId == null || newVisId.trim().length() == 0) {
            error.addFieldError(fieldName, "An Identifier must be supplied");
        }
    }

    public static void validateDescription(ValidationError error, String newDescription) {
        String fieldName = "authenticationDescription";
        if (newDescription != null && newDescription.length() > 128) {
            error.addFieldError(fieldName, "Length (" + newDescription.length() + ") of Description must not exceed 128 on a AuthenticationPolicy");
        } else if (newDescription == null || newDescription.trim().length() == 0) {
            error.addFieldError(fieldName, "A Description must be supplied");
        }
    }

    public static void validateAuthenticationTechnique(ValidationError error, int newAuthenticationTechnique) {
        String fieldName = "authenticationTechnique";
        int nat = newAuthenticationTechnique;
        if (nat != 1 && nat != 2 && nat != 3 && nat != 4 && nat != 5) {
            error.addFieldError(fieldName, "Invalid Authentication Technique.");
        }
    }

    public static void validateSecurityLog(ValidationError error, int newSecurityLog) {
        String fieldName = "securityLog";
        int nsl = newSecurityLog;
        if (nsl != 1 && nsl != 2 && nsl != 3 && nsl != 4) {
            error.addFieldError(fieldName, "Invalid Security Log.");
        }
    }

    public static void validatePasswordMask(ValidationError error, String newPasswordMask, Integer newMinimumPasswordLength, Integer newMinimumChanges, Integer newPasswordReuseDelta, Integer newMinimumAlphas, Integer newMaximumLogonAttempts, Integer newMinimumDigits) {
        String fieldName = "passwordMask";
        if (newMinimumPasswordLength != null && newMinimumChanges != null && newPasswordReuseDelta != null && newMinimumAlphas != null && newMaximumLogonAttempts != null && newMinimumDigits != null && newPasswordMask != null) {
            if (newPasswordMask.length() > 18) {
                error.addFieldError(fieldName, "Length (" + newPasswordMask.length() + ") of PasswordMask must not exceed 18 on a AuthenticationPolicy.");
            } else if (newPasswordMask.length() > 0) {
                if (!newPasswordMask.matches("(A|X|N|a|x|n)*")) {
                    error.addFieldError(fieldName, "Password mask syntax error. Use only: \"A\", \"X\", \"N\", \"a\", \"x\" and \"n\"");
                }
                if (newPasswordMask.length() < newMinimumPasswordLength) {
                    error.addFieldError(fieldName, "Password mask must be greater or equal to minimum password length.");
                }

                if (newMinimumChanges > newPasswordMask.length()) {
                    error.addFieldError(fieldName, "Minimum Password Changes must be less than Password Mask's length.");
                }

                int pReuse = countInPasswordMask(newPasswordMask, "A");
                if (pReuse < newMinimumAlphas) {
                    error.addFieldError(fieldName, "Number of Alphas in Password Mask (i.e.: A) must be greater or equal Minimum Alphas.");
                }

                int pLogonAttempt = countInPasswordMask(newPasswordMask, "N");
                if (pLogonAttempt < newMinimumDigits) {
                    error.addFieldError(fieldName, "Number of Digits in Password Mask (i.e.: N) must be greater or equal Minimum Digits.");
                }
            }
        }
    }

    public static void validateMinimumPasswordLength(ValidationError error, Integer minimumPasswordLength) {
        String fieldName = "minimumPasswordLength";
        if (minimumPasswordLength == null || minimumPasswordLength < 0) {
            error.addFieldError(fieldName, "Password length must not be less than 0.");
        }
    }

    public static void validateMinimumAlphas(ValidationError error, Integer newMinimumAlphas) {
        String fieldName = "minimumAlphas";
        if (newMinimumAlphas == null || newMinimumAlphas < 0) {
            error.addFieldError(fieldName, "Number of alphas must not be less than 0.");
        }
    }

    public static void validateMinimumDigits(ValidationError error, Integer newMinimumDigits) {
        String fieldName = "minimumDigits";
        if (newMinimumDigits == null || newMinimumDigits < 0) {
            error.addFieldError(fieldName, "Number of digits must not be less than 0.");
        }
    }

    public static void validateMaximumRepetition(ValidationError error, Integer newMaximumRepetition) {
        String fieldName = "maximumRepetition";
        if (newMaximumRepetition == null || newMaximumRepetition < 0) {
            error.addFieldError(fieldName, "Number of repeat character must not be less than 0.");
        }
    }

    public static void validateMinimumChanges(ValidationError error, Integer newMinimumChanges) {
        String fieldName = "minimumChanges";
        if (newMinimumChanges == null || newMinimumChanges < 0) {
            error.addFieldError(fieldName, "Number of password change must not be less than 0.");
        }
    }

    public static void validatePasswordReuseDelta(ValidationError error, Integer newPasswordReuseDelta) {
        String fieldName = "passwordReuseDelta";
        if (newPasswordReuseDelta == null || newPasswordReuseDelta < 0) {
            error.addFieldError(fieldName, "Count of password reuse must not be less than 0.");
        }
    }

    public static void validateMaximumLogonAttempts(ValidationError error, Integer newMaximumLogonAttempts) {
        String fieldName = "maximumLogonAttempts";
        if (newMaximumLogonAttempts == null || newMaximumLogonAttempts < 0) {
            error.addFieldError(fieldName, "Number of login attempts must not be less than 0.");
        }
    }

    public static void validatePasswordExpiry(ValidationError error, Integer newPasswordExpiry) {
        String fieldName = "passwordExpiry";
        if (newPasswordExpiry == null || newPasswordExpiry < 0) {
            error.addFieldError(fieldName, "Number of password expiry must not be less than 0.");
        }
    }

    public static void validateJaasEntryName(ValidationError error, String newJaasEntryName) {
        String fieldName = "jaasEntryName";
        if (newJaasEntryName != null && newJaasEntryName.length() > 12) {
            error.addFieldError(fieldName, "Length (" + newJaasEntryName.length() + ") of JaasEntryName must not exceed 12 on a AuthenticationPolicy");
        }
    }

    public static void validateCosignConfigurationFile(ValidationError error, String newCosignConfigurationFile) {
        String fieldName = "cosignConfigurationFile";
        if (newCosignConfigurationFile != null && newCosignConfigurationFile.length() > 127) {
            error.addFieldError(fieldName, "Length (" + newCosignConfigurationFile.length() + ") of CosignConfigurationFile must not exceed 127 on a AuthenticationPolicy");
        } else if (newCosignConfigurationFile == null || newCosignConfigurationFile.trim().length() == 0) {
            error.addFieldError(fieldName, "Please supply Cosign Configuration File Location.");
        }
    }

    public static void validateNtlmNetbiosWins(ValidationError error, String newNtlmNetbiosWins) {
        String fieldName = "ntlmNetbiosWins";
        if (newNtlmNetbiosWins != null && newNtlmNetbiosWins.length() > 20) {
            error.addFieldError(fieldName, "Length (" + newNtlmNetbiosWins.length() + ") of NtlmNetbiosWins must not exceed 20 on a AuthenticationPolicy");
        }
    }

    public static void validateNtlmDomain(ValidationError error, String newNtlmDomain) {
        String fieldName = "ntlmDomain";
        if (newNtlmDomain != null && newNtlmDomain.length() > 127) {
            error.addFieldError(fieldName, "Length (" + newNtlmDomain.length() + ") of NtlmDomain must not exceed 127 on a AuthenticationPolicy");
        }
    }

    public static void validateNtlmDomainController(ValidationError error, String newNtlmDomainController) {
        String fieldName = "ntlmDomainController";
        if (newNtlmDomainController != null && newNtlmDomainController.length() > 20) {
            error.addFieldError(fieldName, "Length (" + newNtlmDomainController.length() + ") of NtlmDomainController must not exceed 20 on a AuthenticationPolicy");
        }
    }

    public static void validateNtlmLogLevel(ValidationError error, int newNtlmLogLevel) {
        String fieldName = "ntlmLogLevel";
        int nnll = newNtlmLogLevel;
        if (nnll != 1 && nnll != 2 && nnll != 3) {
            error.addFieldError(fieldName, "NTLM Log Level.");
        }
    }

    private static int countInPasswordMask(String passwordMask, String charToCount) {
        int count = 0;
        if (passwordMask != null && charToCount != null) {
            for (int i = 0; i < passwordMask.length(); ++i) {
                if (charToCount.equalsIgnoreCase(passwordMask.substring(i, i + 1))) {
                    ++count;
                }
            }
        }
        return count;
    }

}
