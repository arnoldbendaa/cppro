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
package com.softproideas.commons.util;

import java.math.BigDecimal;
import java.util.Map;

import com.softproideas.commons.model.error.ValidationError;

public class MapperUtil {

    public static Integer mapBigDecimal(Object object) {
        BigDecimal bigDecimal = (BigDecimal) object;
        Integer integer = bigDecimal == null ? null : bigDecimal.intValue();
        return integer;
    }
    
    public static ValidationError validateVersionNum(int currentVersionNum, Map<String, Object> currentDBRow) {
        int expectedVersionNum = Integer.parseInt(String.valueOf(currentDBRow.get("VERSION_NUM")));
        ValidationError error = new ValidationError();
        if (currentVersionNum != expectedVersionNum) {
            error.setError(true);
            error.setMessage("Invalid version - currentVersionNum " + currentVersionNum + ", expectedVersionNum " + expectedVersionNum);
        } else {
            error.setError(false);
        }
        return error;
    }
    
}
