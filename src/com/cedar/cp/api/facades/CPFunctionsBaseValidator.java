/**
 * 
 */
package com.cedar.cp.api.facades;

import com.cedar.cp.api.base.MappingValidationException;
import com.cedar.cp.util.NumberUtils;

/**
 * <p>TODO: Comment me</p>
 *
 * @author Paweł Marzęda
 * @email pawma@softproideas.com
 * <p>2015 All rights reserved to IT Services Jacek Kurasiewicz</p>
 */
public class CPFunctionsBaseValidator {

    /**
     * @param costCenter
     * @param address 
     * @throws Exception 
     */
    public static void validateCostCenter(String costCenter, String address) throws Exception {
        if (costCenter == null) {
            throw new MappingValidationException(address + ": Cost Center is not defined!");
        } else if (costCenter.length() > 20) {
            throw new MappingValidationException(address + ": Cost Center is too long!");
        }
    }
    
    /**
     * @param expenseCode
     * @param address 
     * @throws Exception 
     */
    public static void validateExpenseCode(String expenseCode, String address) throws Exception {
        if (expenseCode == null) {
            throw new MappingValidationException(address + ": Expense Code is not defined!");
        } else if (expenseCode.length() > 20) {
            throw new MappingValidationException(address + ": Expense Code is too long!");
        }
    }

    /**
     * @param date
     * @param address 
     * @throws Exception 
     */
    public static void validateDateWithNoObligateMonth(String[] date, String address) throws Exception {
        if(date[1] == null) {
            throw new MappingValidationException(address + ": Year of date is not defined");
        }
        else if (date[1].length() != 4) {
            throw new MappingValidationException(address + ": Year of date is wrong");
        }
        if (date.length == 3 && date[2] != null && date[2].length() > 1 && date[2].equals("10") && date[2].equals("11") && date[2].equals("12")) {
            throw new MappingValidationException(address + ": Month of date is too long!");
        }
    }
    
    public static void validateDateWithObligateMonth(String[] date, String address) throws Exception {
        if(date[1] == null) {
            throw new MappingValidationException(address + ": Year of date is not defined");
        }
        else if (date[1].length() != 4) {
            throw new MappingValidationException(address + ": Year of date is wrong");
        }
        if(date.length != 3 || date[2] == null) {
            throw new MappingValidationException(address + ": Month of date is not defined");
        }
        else if (date[2].length() > 1 && date[2].equals("10") && date[2].equals("11") && date[2].equals("12")) {
            throw new MappingValidationException(address + ": Month of date is too long!");
        }
    }

    /**
     * @param currency
     * @param address 
     * @throws Exception 
     */
    public static void validateCurrency(String currency, String address) throws Exception {
        if (currency == null) {
            throw new MappingValidationException(address + ": Currency is not defined!");
        } else if (currency.length() > 4) {
            throw new MappingValidationException(address + ": Currency is too long!");
        }
    }

    /**
     * @param company
     * @param address 
     * @throws Exception 
     */
    public static void validateCompany(String company, String address) throws Exception {
        if (company == null) {
            throw new MappingValidationException(address + ": Company is not defined!");
        } else if (!NumberUtils.isNumber(company)) {
            throw new MappingValidationException(address + ": Company number is wrong!");
        } else if (company.length() > 5) {
            throw new MappingValidationException(address + ": Company is too long!");
        }
    }

    /**
     * @param field
     * @param address
     * @throws MappingValidationException 
     */
    public static void validateField(String field, String address) throws MappingValidationException {
        if (field == null) {
            throw new MappingValidationException(address + ": Field is not defined!");
        } else if (field.length() > 50) {
            throw new MappingValidationException(address + ": Field is too long!");
        }
    }

    /**
     * @param yearOffset
     * @param address 
     * @throws Exception 
     */
    public static void validateYearOffset(String yearOffset, String address) throws Exception {
        if (yearOffset != null && !NumberUtils.isNumber(yearOffset)) {
            throw new MappingValidationException(address + ": Year Offset number is wrong!");
        }
    }

    /**
     * @param periodOffset
     * @param address 
     * @throws Exception 
     */
    public static void validatePeriodOffset(String periodOffset, String address) throws Exception {
        if (periodOffset != null && !NumberUtils.isNumber(periodOffset)) {
            throw new MappingValidationException(address + ": Period Offset number is wrong!");
        }
    }
}
