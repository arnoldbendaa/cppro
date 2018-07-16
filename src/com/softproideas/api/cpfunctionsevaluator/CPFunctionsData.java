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
package com.softproideas.api.cpfunctionsevaluator;

import java.util.Map;
import java.util.regex.Pattern;

import com.cedar.cp.api.base.ValidationException;
import com.softproideas.util.validation.MappingValidator;

public abstract class CPFunctionsData {
    public final static Pattern patternSlash = Pattern.compile("/");

    public abstract void add(MappingValidator mv, String id) throws ValidationException;

    public abstract void add(String expression, String id) throws ValidationException;

    public abstract Map<String, String> submit() throws ValidationException;

    public static int[] splitList(int listSize, int partSize) {
        int count = (int) Math.ceil(listSize / (double) partSize);
        int[] ranges = null;
        ranges = new int[count + 1];
        for (int i = 0; i < count; i++) {
            ranges[i] = i * partSize;
        }
        ranges[count] = listSize;
        return ranges;
    }

    public static String fillCostCenterFromContextIfIsEmpty(String actualCostCenter, String contextCostCenter) throws ValidationException {
        if ((actualCostCenter == null || actualCostCenter.trim().isEmpty()) && (contextCostCenter == null || contextCostCenter.trim().isEmpty())) {
            throw new ValidationException("No cost center specified for OAExpression");
        }
        if ((actualCostCenter == null) || (actualCostCenter.length() == 0)) {
            return contextCostCenter;
        } else {
            return actualCostCenter;
        }
    }

    public static String fillExpenseCodeFromContextIfIsEmpty(String actualExpenseCode, String contextExpenseCode) throws ValidationException {
        if ((actualExpenseCode == null || actualExpenseCode.trim().isEmpty()) && (contextExpenseCode == null || contextExpenseCode.trim().isEmpty())) {
            throw new ValidationException("No expense code specified for OAExpression");
        }
        if ((actualExpenseCode == null) || (actualExpenseCode.length() == 0)) {
            return contextExpenseCode;
        } else {
            return actualExpenseCode;
        }
    }

    public static boolean isGlobal(String modelVisId) {
        if (modelVisId != null && modelVisId.toUpperCase().startsWith("GL")) {
            return true;
        }
        return false;
    }

}
