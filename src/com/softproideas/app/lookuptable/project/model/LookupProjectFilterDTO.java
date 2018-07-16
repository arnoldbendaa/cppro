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
package com.softproideas.app.lookuptable.project.model;

public class LookupProjectFilterDTO {

    public static enum Operator {
        eq, eqString,                                   /* equal */
        neq, neqString,                                 /* not equal */
        gt,                                             /* greater than */
        gte,                                            /* greater than or equal */
        lt,                                             /* lower than */
        lte,                                            /* lower than or equal */
        startswithString, endswithString,               /* startswith, endswith */
        containsString, doesnotcontainString            /* contains, doesnotcontain */
    }
    // dlatego tyle ich bo wrzucilem do enuma tez odpowiedniki do typu np. start with -> st
    
    private String field;
    private String operator;
    private String value;
    
    public LookupProjectFilterDTO(String filter) {
        String fieldTMP = filter.substring(0, filter.indexOf(" "));
        filter = filter.substring(filter.indexOf(" ") + 1);
        String operatorTMP = filter.substring(0, filter.indexOf(" "));
        filter = filter.substring(filter.indexOf(" ") + 1);
        String valueTMP = filter;
        parseLookupProjectFilter(fieldTMP, valueTMP, Operator.valueOf(operatorTMP));
    }
    
    private void parseLookupProjectFilter(String field, String value, Operator operator) {
        String operatorString = "";
        //field = "\"" + field + "\"";
        if(operator.equals(Operator.eq)) {
            operatorString = "=";
        } else if(operator.equals(Operator.neq)) {
            operatorString = "!=";
        } else if(operator.equals(Operator.gt)) {
            operatorString = ">";
        } else if(operator.equals(Operator.gte)) {
            operatorString = ">=";
        } else if(operator.equals(Operator.lt)) {
            operatorString = "<";
        } else if(operator.equals(Operator.lte)) {
            operatorString = "<=";
        } else if(operator.equals(Operator.eqString)) {
            operatorString = "=";
            value = "" + value + "";
        } else if(operator.equals(Operator.startswithString)) {
            operatorString = "LIKE";
            value = "" + value + "%";
        } else if(operator.equals(Operator.endswithString)) {
            operatorString = "LIKE";
            value = "%" + value + "";
        } else if(operator.equals(Operator.containsString)) {
            operatorString = "LIKE";
            value = "%" + value + "%";
        } else if(operator.equals(Operator.neqString)) {
            operatorString = "<>";
            value = "" + value + "";
        } else if(operator.equals(Operator.doesnotcontainString)) {
            operatorString = "NOT LIKE";
            value = "%" + value + "%";
        }
        setField(field);
        setValue(value);
        setOperator(operatorString);
    }
    
    public LookupProjectFilterDTO() {
        
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

}
