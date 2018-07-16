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
package com.softproideas.app.admin.loggedhistory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.softproideas.app.admin.loggedhistory.model.LoggedHistoryUserDTO;
import com.softproideas.app.admin.loggedhistory.model.LoggedHistoryDTO;
import com.softproideas.app.admin.loggedhistory.service.LoggedHistoryService;
import com.softproideas.commons.model.ResponseMessage;
 
/**
 * @author Mariusz Łukasik
 * @email mariusz.lukasik@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
@RequestMapping(value = "/loggedhistory")
@Controller
public class LoggedHistoryController {

    @Autowired
    LoggedHistoryService loggedHistoryService;

    /**
     * Get list of all Users.
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET)
    public List<LoggedHistoryUserDTO> browseUsers() throws Exception {
        return loggedHistoryService.browseUsers();
    }

    @ResponseBody
    @RequestMapping(value = "/moreRows/{id}/{date}/{offset}", method = RequestMethod.GET)
    public List<LoggedHistoryUserDTO> displayMoreRows(@PathVariable String id, @PathVariable String date, @PathVariable int offset) throws Exception {
        return loggedHistoryService.displayMoreRows(id, date, offset);
    }
    
    @ResponseBody
    @RequestMapping(value = "/display/{periodfrom}/{periodto}", method = RequestMethod.GET)
    public List<LoggedHistoryUserDTO> displayNotLoggedUsersForSearchOption(@PathVariable String periodfrom, @PathVariable String periodto) throws Exception {
        return loggedHistoryService.displayNotLoggedUsersForSearchOption(periodfrom, periodto);
    }
    
    @ResponseBody
    @RequestMapping(value = "/delete/{periodfrom}", method = RequestMethod.DELETE)
    public ResponseMessage deleteHistory(@PathVariable String periodfrom) throws Exception {
        return loggedHistoryService.deleteHistory(periodfrom);
    }    
}