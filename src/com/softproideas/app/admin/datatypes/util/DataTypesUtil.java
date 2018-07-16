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
package com.softproideas.app.admin.datatypes.util;

import java.util.ArrayList;
import java.util.Arrays;

import com.softproideas.app.admin.datatypes.model.DataTypesMeasureClassDTO;
import com.softproideas.app.admin.datatypes.model.DataTypesSubTypeDTO;

/**
 * @author Piotr Markiewicz
 * @email piotr.markiewicz@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class DataTypesUtil {

    private static String[] measureClassNames = { "String", "Numeric", "Time", "Date", "Date & Time", "Boolean" };
    // TODO
    // Maybe it's necessary to add last element (empty string) - like it was in previous version of Admin Panel:
    // private static String[] measureClassNames2 = {"String", "Numeric", "Time", "Date", "Date & Time", "Boolean", ""};
    // Old Panel probably don't use this empty value, but I'm not a hundred percent sure.

    private static String[] subTypeNames = { "Financial Value", "Budget Transfer Temporary", "Budget Transfer Permanent", "Virtual", "Measure" };

    public static ArrayList<DataTypesMeasureClassDTO> getMeasureClasses() {
        ArrayList<DataTypesMeasureClassDTO> list = new ArrayList<DataTypesMeasureClassDTO>();
        for (int i = 0; i < measureClassNames.length; i++) {
            DataTypesMeasureClassDTO obj = new DataTypesMeasureClassDTO();
            obj.setIndex(i);
            obj.setName(measureClassNames[i]);
            list.add(obj);
        }
        return list;
    }

    public static String getMeasureClassName(Integer measureClass) {
        if (measureClass != null) {
            return measureClassNames[measureClass];
        } else {
            return "";
        }
    }

    public static Integer getMeasureClassNumber(String measureClassName) {
        return Arrays.asList(measureClassNames).indexOf(measureClassName);
    }

    public static ArrayList<DataTypesSubTypeDTO> getSubTypes() {
        ArrayList<DataTypesSubTypeDTO> list = new ArrayList<DataTypesSubTypeDTO>();
        for (int i = 0; i < subTypeNames.length; i++) {
            DataTypesSubTypeDTO obj = new DataTypesSubTypeDTO();
            obj.setIndex(i);
            obj.setName(subTypeNames[i]);
            list.add(obj);
        }
        return list;
    }

    public static String getSubTypeName(Integer subType) {
        if (subType != null) {
            return subTypeNames[subType];
        } else {
            return "";
        }
    }

    public static Integer getSubTypeNumber(String subTypeName) {
        return Arrays.asList(subTypeNames).indexOf(subTypeName);
    }

}
