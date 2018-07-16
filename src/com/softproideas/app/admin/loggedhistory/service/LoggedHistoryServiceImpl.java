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
package com.softproideas.app.admin.loggedhistory.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softproideas.app.admin.loggedhistory.dao.LoggedHistoryDao;
import com.softproideas.app.admin.loggedhistory.model.LoggedHistoryUserDTO;
import com.softproideas.app.admin.loggedhistory.model.LoggedHistoryDTO;
import com.softproideas.common.exceptions.DaoException;
import com.softproideas.commons.model.ResponseMessage;

@Service("loggedHistoryService")
public class LoggedHistoryServiceImpl implements LoggedHistoryService{
    
    @Autowired
    LoggedHistoryDao loggedHistoryDao;

    private static Logger logger = LoggerFactory.getLogger(LoggedHistoryServiceImpl.class);
    
    @Override
    public List<LoggedHistoryUserDTO> browseUsers() {
        try {
            return loggedHistoryDao.browseUsers();
        } catch (DaoException e) {
            return new ArrayList<LoggedHistoryUserDTO>();
        }
    }
    
    
    @Override
    public List<LoggedHistoryUserDTO> displayMoreRows(String id, String date, int offset) {
        try {
            return loggedHistoryDao.getMoreRowsLoggedHistoryForUsers(id, date, offset);
        } catch (DaoException e) {
            return new ArrayList<LoggedHistoryUserDTO>();
        }
    }

    @Override
    public ResponseMessage deleteHistory(String periodFrom) {
        return loggedHistoryDao.deleteHistory(periodFrom);
    }

    @Override
    public List<LoggedHistoryUserDTO> displayNotLoggedUsersForSearchOption(String periodfrom, String periodto) {
        return loggedHistoryDao.displayNotLoggedUsersForSearchOption(periodfrom, periodto);
    }
}