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
package com.softproideas.commons.model;

import java.util.HashMap;
import java.util.Map;

/**
 * The class <code>ResponseMessage</code> contains the response type. Success (success==true) or error (success==false).
 * Parent class for other, as for example <code>DefaultError</code> or <code>ValidationError</code>.
 * 
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
public class ResponseMessage {
    
    private String message;
    
    private String title;
    
    private boolean success;
    
    private Map<String, Object> data;

    /**
     * Constructs a new <code>Message</code>.
     */
    public ResponseMessage() {
    }

    /**
     * Constructs a new <code>Message</code> with the specified flag (success = true/false).
     */
    public ResponseMessage(boolean success) {
        this.success = success;
    }
    
    /**
     * Constructs a new <code>Message</code> with the specified flag (success = true/false) and the specified message.
     */
    public ResponseMessage(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
    
	/**
     * Constructs Spring MVC style message
     * 
     * @param success indicates if method invocation succeeded
     * @param message formal information
     * @param title nice explanation to be displayed to user
     * @param data additional data to be sent with a response
     */
    public ResponseMessage(boolean success, String message, String title,Map<String, Object> data) {
		super();
		this.success = success;
		this.message = message;
		this.title = title;
		this.data = data;
	}

	/**
     * Constructs Spring MVC style message
     * 
     * @param success indicates if method invocation succeeded
     * @param message formal information
     * @param title nice explanation to be displayed to user
     */
    public ResponseMessage(boolean success, String message, String title) {
		super();
		this.success = success;
		this.message = message;
		this.title = title;
	}

	public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isError() {
        return !success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setError(boolean success) {
        this.success = !success;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Map<String, Object> getData() {
		if(data == null)
			data = new HashMap<String, Object>();
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}