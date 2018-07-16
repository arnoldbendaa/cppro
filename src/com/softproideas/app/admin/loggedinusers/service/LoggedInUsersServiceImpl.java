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
package com.softproideas.app.admin.loggedinusers.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cedar.cp.api.base.EntityList;
import com.softproideas.app.admin.loggedinusers.mapper.LoggedInUsersMapper;
import com.softproideas.app.admin.loggedinusers.model.LoggedInUserDTO;
import com.softproideas.common.exceptions.ServiceException;
import com.softproideas.commons.context.CPContextHolder;
import com.softproideas.commons.model.ResponseMessage;
import com.softproideas.commons.model.error.ValidationError;

@Service("loggedUsersService")
public class LoggedInUsersServiceImpl implements LoggedInUsersService {

    private static Logger logger = LoggerFactory.getLogger(LoggedInUsersServiceImpl.class);

    @Autowired
    CPContextHolder cpContextHolder;

    /**
     * Returns list of available logged in users
     */
    @Override
    public List<LoggedInUserDTO> browseLoggedInUsers() throws ServiceException {
        try {
            EntityList list = cpContextHolder.getLoggedInUsersProcess().getAllLoggedInUsers();
            return LoggedInUsersMapper.mapToLoggedUsersDTO(list);
        } catch (Exception e) {
            logger.error("Error during fetching Logged Users", e);
            throw new ServiceException("Error during fetching Logged Users", e);
        }
    }

    /**
     * Logout user by name "logged in users"
     */
    @Override
    public ResponseMessage logoutUser(List<String> userNames) throws ServiceException {
        String myName = cpContextHolder.getUserContext().getLogonString();
        for (String userName: userNames) {
            if (myName.equals(userName)) {
                ValidationError error = new ValidationError(" You can't logout yourself");
                return error;
            }
        }
        cpContextHolder.getLoggedInUsersProcess().logoutUsersByUserName(userNames);
        ResponseMessage success = new ResponseMessage(true);
        return success;
    }

}
