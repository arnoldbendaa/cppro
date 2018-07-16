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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import com.softproideas.commons.model.ResponseMessage;

/**
 * The class <code>DefaultError</code> contains the title and error message.
 * The message can contain stack trace.
 * 
 * @author Jarosław Kaczmarski
 * @email jarok@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
public class DefaultError extends ResponseMessage {

    /* The error title */
    private String title;

    /* The error message (default stack trace) */
    private String message;


    /**
     * Constructs a new <code>DefaultError</code>.
     */
    public DefaultError() {
        super(false);
    }

    /**
     * Constructs a new <code>DefaultError</code> with the specified title and error message.
     */
    public DefaultError(String title, String message) {
        super(false);
        this.title = title;
        this.message = message;
    }

    /**
     * Constructs a new <code>DefaultError</code> from <code>Throwable</code> object.
     */
    public DefaultError(Throwable ex) {
        super(false);
        this.title = ex.getMessage();
        this.message = getStackTrace(ex);
    }

    /**
     * Return the error title.
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Set the error title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Return the error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Return stack trace from <cod>Throwable</code> object.
     */
    private String getStackTrace(Throwable ex) {
        Writer result = new StringWriter();
        ex.printStackTrace(new PrintWriter(result));
        return result.toString();
    }

}
