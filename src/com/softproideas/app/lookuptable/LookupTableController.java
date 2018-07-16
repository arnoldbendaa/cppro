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
package com.softproideas.app.lookuptable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * <p>Spring MVC controller responsible for handling requests from web browser based user interface.
 * Controller is available at the url <em>/lookupTable/</em>.</p>
 *
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@Controller
public class LookupTableController {

    @RequestMapping(value = "/currency", method = RequestMethod.GET)
    public ModelAndView currency(HttpServletRequest request) throws Exception {
        ModelAndView mainView = new ModelAndView("currency");
        return mainView;
    }

    @RequestMapping(value = "/parameters", method = RequestMethod.GET)
    public ModelAndView parameters(HttpServletRequest request) throws Exception {
        ModelAndView mainView = new ModelAndView("parameters");
        return mainView;
    }

    @RequestMapping(value = "/auction", method = RequestMethod.GET)
    public ModelAndView auction(HttpServletRequest request) throws Exception {
        ModelAndView mainView = new ModelAndView("auction");
        return mainView;
    }

    @RequestMapping(value = "/project", method = RequestMethod.GET)
    public ModelAndView project(HttpServletRequest request) throws Exception {
        ModelAndView mainView = new ModelAndView("project");
        return mainView;
    }

}
