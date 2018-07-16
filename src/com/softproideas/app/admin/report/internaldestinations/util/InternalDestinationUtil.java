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
package com.softproideas.app.admin.report.internaldestinations.util;

import java.util.ArrayList;
import java.util.Arrays;

import com.softproideas.app.admin.report.internaldestinations.model.ReportMessageTypeDTO;

public class InternalDestinationUtil {

    private static String[] messageTypeNames = { "System Message", "Email Message", "System and Email Message" };

    public static ArrayList<ReportMessageTypeDTO> getMessageTypes() {
        ArrayList<ReportMessageTypeDTO> list = new ArrayList<ReportMessageTypeDTO>();
        for (int i = 0; i < messageTypeNames.length; i++) {
            ReportMessageTypeDTO obj = new ReportMessageTypeDTO(i, messageTypeNames[i]);
            list.add(obj);
        }
        return list;
    }

    public static String getMessageTypeName(Integer messageType) {
        if (messageType != null) {
            return messageTypeNames[messageType];
        } else {
            return "";
        }
    }

    public static Integer getMessageTypeNumber(String messageTypeName) {
        return Arrays.asList(messageTypeNames).indexOf(messageTypeName);
    }

}
