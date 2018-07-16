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
package com.softproideas.app.admin.loggedhistory.mapper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.softproideas.app.admin.loggedhistory.model.LoggedHistoryUserDTO;

public class LoggedHistoryMapper {

    public static List<LoggedHistoryUserDTO> mapLoggedHistories(List<Map<String, Object>> rows) {
        List<LoggedHistoryUserDTO> list = new ArrayList<LoggedHistoryUserDTO>();
        for (Map<String, Object> row: rows) {
            LoggedHistoryUserDTO loggedHistory = mapUserLoggedHistory(row);
            list.add(loggedHistory);
        }
        return list;
    }

    public static LoggedHistoryUserDTO mapUserLoggedHistory(Map<String, Object> row) {
        LoggedHistoryUserDTO loggedHistory = new LoggedHistoryUserDTO();
        
        // name
        String userName = (String) row.get("NAME");
        loggedHistory.setUserName(userName);
        
        // full name
        String fullName = (String) row.get("FULL_NAME");
        loggedHistory.setFullName(fullName);
        
        // date and hour
        Timestamp time = (Timestamp) row.get("LOGGED_TIME");
        Date dateTime = new Date(time.getTime());
        
        String date = new SimpleDateFormat("yyyy-MM-dd").format(dateTime);
        String hour = new SimpleDateFormat("HH:mm:ss").format(dateTime);

        loggedHistory.setHour(hour);
        loggedHistory.setDate(date);

        return loggedHistory;
    }
    
    
    
    public static List<LoggedHistoryUserDTO> mapNotLoggedUsers(List<Map<String, Object>> rows) {
        List<LoggedHistoryUserDTO> list = new ArrayList<LoggedHistoryUserDTO>();
        for (Map<String, Object> row: rows) {
            LoggedHistoryUserDTO loggedHistory = mapNotLoggedUser(row);
            list.add(loggedHistory);
        }
        return list;
    }    
    
    public static LoggedHistoryUserDTO mapNotLoggedUser(Map<String, Object> row) {
        LoggedHistoryUserDTO loggedHistory = new LoggedHistoryUserDTO();
        
        // name
        String userName = (String) row.get("NAME");
        loggedHistory.setUserName(userName);
        
        // full name
        String fullName = (String) row.get("FULL_NAME");
        loggedHistory.setFullName(fullName);
        
        // date and hour

        loggedHistory.setHour("N/A");
        loggedHistory.setDate("N/A");

        return loggedHistory;
    }
    
    
}
