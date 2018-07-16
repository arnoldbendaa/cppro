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
package com.softproideas.handler;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.cedar.cp.api.base.ValidationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.DefaultError;
import com.softproideas.commons.model.error.ValidationError;
import com.softproideas.handler.exceptions.ExcelIOException;
import com.softproideas.handler.exceptions.WrongFileExtensionException;
import com.softproideas.handler.exceptions.ZoomOutOfRangeException;
import com.softproideas.util.validation.MappingValidationException;

/**
 * Exception handler for all controllers.
 * 
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private MessageSource messageSource;

    @Autowired
    public ControllerExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * Handles <code>ServiceException</code> that is thrown by the controller method.
     * @param exception the exception that is thrown by the controller method
     * @return an object containing the error messages
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public DefaultError handleServiceException(ServiceException exception) {
        return new DefaultError(exception);
    }

    /**
     * Handles <code>TypeMismatchException</code> that is thrown by the controller method.
     * @param exception the exception that is thrown by the controller method
     * @return an object containing the error messages
     */
    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public DefaultError handleTypeMismatchException(TypeMismatchException exception) {
        return new DefaultError("Type Mismatch Parameter ", exception.getLocalizedMessage());
    }

    /**
     * Handles <code>MissingServletRequestParameterException</code> that is thrown by the controller method.
     * @param exception the exception that is thrown by the controller method
     * @return an object containing the error messages
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public DefaultError handleMissingServletRequestParameter(MissingServletRequestParameterException exception) {
        return new DefaultError("Missing Request Parameter", exception.getLocalizedMessage());
    }

    /**
     * Handles <code>MethodArgumentNotValidException</code> that is thrown by the controller method.
     * @param exception the exception that is thrown by the controller method
     * @return an object containing the error messages
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationError handleMethodArgumentNotValid(MethodArgumentNotValidException exception) {

        String validationMessage = localizeErrorMessage("error.bad.arguments");
        ValidationError validationError = new ValidationError(validationMessage);

        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();

        StringBuilder errorMessage = new StringBuilder("");
        for (FieldError fieldError: fieldErrors) {
            errorMessage.append(fieldError.getCode()).append(".");
            errorMessage.append(fieldError.getObjectName()).append(".");
            errorMessage.append(fieldError.getField());
            String localizedErrorMsg = localizeErrorMessage(fieldError);
            validationError.addFieldError(fieldError.getField(), localizedErrorMsg);
            errorMessage.delete(0, errorMessage.capacity());
        }

        return validationError;
    }

    /**
     * Method return appropriate localized error message from the {@link MessageSource}.
     * @param errorCode key of the error message
     */
    private String localizeErrorMessage(String errorCode) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage(errorCode, null, locale);
        return errorMessage;
    }

    /**
     * Method return appropriate localized error message from the {@link MessageSource}.
     * @param errorCode key of the error message
     */
    private String localizeErrorMessage(FieldError fieldError) {
        Locale locale = LocaleContextHolder.getLocale();
        String errorMessage = messageSource.getMessage(fieldError, locale);
        return errorMessage;
    }

    /**
     * Handles <code>HttpMessageNotReadableException</code> that is thrown by the controller method.
     * @param exception the exception that is thrown by the controller method
     * @return an object containing the error messages
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public DefaultError handleHttpMessageNotReadable(HttpMessageNotReadableException exception) {
        String message = localizeErrorMessage("error.message.not.readble");
        if (exception.getCause() instanceof JsonMappingException) {
            message = localizeErrorMessage("error.json");
        }
        return new DefaultError(message, exception.getMostSpecificCause().getMessage().split("\n")[0]);
    }

    /**
     * Handles <code>ExcelIOException</code> that is thrown by the application.
     * @param exception - the exception that is thrown by the controller method
     * @return an object containing the error messages
     */
    @ExceptionHandler(ExcelIOException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ResponseMessage handleExcelIOException(ExcelIOException e) {
        return new ResponseMessage(false, "cp.profile.error.missingExcelIOService", "Missing ExcelIO service");
    }

    /**
     * Handles <code>ZoomOutOfRangeException</code> that is thrown by the application.
     * @param exception - the exception that is thrown by the controller method
     * @return an object containing the error messages
     */
    @ExceptionHandler(ZoomOutOfRangeException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseMessage handleZoomOutOfRangeExceptionException(ZoomOutOfRangeException e) {
        return new ResponseMessage(false, "cp.profile.error.invalidZoomFactor", "Valid zoom factor values range from 0.1 to 4.0");
    }

    /**
     * Handles <code>WrongFileExtensionException</code> that is thrown by the application.
     * @param exception - the exception that is thrown by the controller method
     * @return an object containing the error messages
     */
    @ExceptionHandler(WrongFileExtensionException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public DefaultError handleWrongFileExtensionException(WrongFileExtensionException e) {
        return new DefaultError("Wrong file extention exception!", e.getMessage());
    }

    /**
     * Handles <code>WrongFileExtensionException</code> that is thrown by the application.
     * @param exception - the exception that is thrown by the controller method
     * @return an object containing the error messages
     */
    @ExceptionHandler(MappingValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public MappingValidationException handleMappingValidationException(MappingValidationException e) {
        return e;
    }

    /**
     * Handles <code>WrongFileExtensionException</code> that is thrown by the application.
     * @param exception - the exception that is thrown by the controller method
     * @return an object containing the error messages
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public DefaultError handleValidationException(ValidationException e) {
        return new DefaultError("Validation Exception", e.getMessage());
    }

    /**
     * Handles <code>Exception</code> that is thrown by the controller method.
     * @param exception the exception that is thrown by the controller method
     * @return an object containing the error messages
     * @throws Exception 
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public DefaultError handleAllExceptions(Exception exception) throws Exception {
        exception.printStackTrace();

        DefaultError error = new DefaultError(exception);
        error.setTitle("Error occured!");
        return error;
    }

}
