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
package com.softproideas.commons.model.error;

import java.util.ArrayList;
import java.util.List;

import com.softproideas.commons.model.ResponseMessage;

/**
 * The class <code>ValidationError</code> contains an error message and list of field validation errors.
 * 
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
public class ValidationError extends ResponseMessage {

    /* The error message */
    private String message;

    /* List of field validation errors. */
    private List<FieldError> fieldErrors = new ArrayList<FieldError>();


    /**
     * Constructs a new <code>ValidationError</code>.
     */
    public ValidationError() {
        super(false);
    }

    /**
     * Constructs a new <code>ValidationError</code> with the specified error message.
     */
    public ValidationError(String message) {
        super(false);
        this.message = message;
    }

    /**
     * Return the error message.
     */
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Return the list of field validation errors.
     */
    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    /**
     * Adds a field validation error.
     */
    public void addFieldError(String fieldName, String fieldMessage) {
        FieldError fieldError = new FieldError(fieldName, fieldMessage);
        fieldErrors.add(fieldError);
    }

}
