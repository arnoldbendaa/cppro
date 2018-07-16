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
package com.softproideas.app.admin.users.util;

import com.cedar.cp.dto.user.UserImpl;
import com.softproideas.app.admin.users.model.UserDetailsDTO;
import com.softproideas.commons.model.error.ValidationError;

public class UsersValidatorUtil {

    public static ValidationError validateUserDetails(UserImpl oldUser, UserDetailsDTO newUser) {
        ValidationError error = new ValidationError();
        validateName(error, newUser.getUserName());
        validateFullName(error, newUser.getUserFullName());
        validateEMailAddress(error, newUser.getEmailAddress());
        validatePasswordBytes(error, newUser.getPassword(), newUser.getConfirmPassword());
        validateExternalSystemUserName(error, newUser.getExternalSystemUserName());
        validateLogonAlias(error, newUser.getLogonAlias());
        //validateRoles(error, newUser.getRoles().size())
        return error;
    }

    public static void validateName(ValidationError error, String newName) {
        String fieldName = "roleVisId";
        if (newName != null && newName.length() > 255) {
            error.addFieldError(fieldName, "Length (" + newName.length() + ") of Name must not exceed 255 on a User");
        } else if (newName == null || newName.length() == 0) {
            error.addFieldError(fieldName, "Please supply an Identifier");
        }
    }

    public static void validateFullName(ValidationError error, String newFullName) {
        String fieldName = "userFullName";
        if (newFullName != null && newFullName.length() > 255) {
            error.addFieldError(fieldName, "Length (" + newFullName.length() + ") of FullName must not exceed 255 on a User");
        } else if (newFullName == null || newFullName.length() == 0) {
            error.addFieldError(fieldName, "Full Name cannot be empty");
        }
    }

    public static void validateEMailAddress(ValidationError error, String newEMailAddress) {
        String fieldName = "emailAddress";
        if (newEMailAddress != null && newEMailAddress.length() > 255) {
            error.addFieldError(fieldName, "Length (" + newEMailAddress.length() + ") of EMailAddress must not exceed 255 on a User");
        }
    }

    public static void validatePasswordBytes(ValidationError error, String newPassword, String confirmNewPassword) {
        if(newPassword != null) {
            String fieldName = "password";
            if (!newPassword.equals(confirmNewPassword)) {
                error.addFieldError(fieldName, "Password is not confirmed properly at last ");
            }
            if (newPassword != null && newPassword.length() > 100) {
                error.addFieldError(fieldName, "Length (" + newPassword.length() + ") of PasswordBytes must not exceed 100 on a User");
            } else if (newPassword != null && newPassword.length() < 4) {
                error.addFieldError(fieldName, "Password's length must be at least 4 chars");
            }
        }
    }

    public static void validateExternalSystemUserName(ValidationError error, String newExternalSystemUserName) {
        String fieldName = "externalSystemUserName";
        if (newExternalSystemUserName != null && newExternalSystemUserName.length() > 255) {
            error.addFieldError(fieldName, "Length (" + newExternalSystemUserName.length() + ") of ExternalSystemUserName must not exceed 255 on a User");
        }
    }

    public static void validateLogonAlias(ValidationError error, String newLogonAlias) {
        String fieldName = "logonAlias";
        if (newLogonAlias != null && newLogonAlias.length() > 255) {
            error.addFieldError(fieldName, "Length (" + newLogonAlias.length() + ") of LogonAlias must not exceed 255 on a User");
        }
    }

    public static void validateRoles(ValidationError error, int length) {
        String fieldName = "roles";
        if (length == 0) {
            error.addFieldError(fieldName, "One or more roles must be selected");
        }
    }

}
