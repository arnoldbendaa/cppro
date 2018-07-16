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
package com.cedar.cp.util.flatform.model.workbook.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import com.cedar.cp.util.flatform.model.workbook.CellExtendedDTO;

public class CellUtil {
    /**
     * Manages fields depends on type (formula, text, number) stored in cell.getText().
     */
    public static void setFormattedValue(String value, CellExtendedDTO cellDTO) {
        if (value != null && !value.isEmpty() && !value.equals("null")) {
            if (isFormula(value)) {
                cellDTO.setFormula(value);
            } else if (isDouble(value)) {
                cellDTO.setValue(Double.parseDouble(value));
            } else if (isDate(value)) {
                cellDTO.setDate(parseToProperDateFormat(value));
            } else {
                cellDTO.setText(value);
            }
        }
    }

    public static Boolean isFormula(String value) {
        return value.startsWith("=");
    }

    public static Boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (NumberFormatException e) {
        }
        return false;
    }

    public static Boolean isDate(String value) {
        return parseToProperDateFormat(value) != null;
    }

    public static String parseToProperDateFormat(String value) {
        DateFormat dateFormatSended = new SimpleDateFormat("dd/MM/yyyy");

        DateFormat dateFormat1 = new SimpleDateFormat("dd-MMM-yy");
        try {
            Date date = dateFormat1.parse(value);
            return dateFormatSended.format(date);
        } catch (ParseException e) {
        }

        DateFormat dateFormat2 = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date date = dateFormat2.parse(value);
            return dateFormatSended.format(date);
        } catch (ParseException e) {
        }

        return null;
    }

    public static boolean isYellowColor(Cell cell) {
        if (cell == null || cell.getCellStyle() == null) {
            return false;
        }
        if (!(cell.getCellStyle() instanceof XSSFCellStyle)) {
            return false;
        }
        XSSFCellStyle cellStyle = (XSSFCellStyle) cell.getCellStyle();
        if (cellStyle == null) {
            return false;
        }
        XSSFColor color = cellStyle.getFillForegroundXSSFColor();
        if (color == null) {
            return false;
        }
        String rgbHex = color.getARGBHex().substring(2);
        return rgbHex.equals("FFFF00");
    }
}
