package com.softproideas.util.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.cedar.cp.util.GeneralUtils;

public class MappingArgumentsValidator {

    public static String validateType(String value) {
        String msg = isNullOrEmpty(value, "type");
        if (msg.isEmpty() == false) {
            return msg;
        } else if (containsCellReference(value)) {
            return "";
        } else if (value.matches("^[mbnMBN]{1}$")) {
            return "";
        } else {
            return "Value must be M, N or B";
        }
    }

    public static String validateType(String value, String expected) {
        String msg = validateType(value);
        if ("".equals(msg) == false) {
            return msg;
        } else if (expected == null) {
            // return "Expected is null";
            // } else if (expected.matches("^[mbnMBN]{1}$") == false) {
            // return "Expected must be M, N or B";
            return "";
        } else if (value.equalsIgnoreCase(expected) == false) {
            return "Type must be equal to " + expected.toUpperCase();
        } else {
            return "";
        }
    }

    public static String validateDataType(String value) {
        String msg = isNullOrEmpty(value, "dt");
        if (msg.isEmpty() == false) {
            return msg;
        } else if (containsCellReference(value)) {
            return "";
        } else if (value.length() < 3) {
            return "";
        }
        return "Data type exceeds number of allowed characters which is 2";
    }

    public static String validateDim0(String value) {
        String msg = isNullOrEmpty(value, "dim0");
        if (msg.isEmpty() == false) {
            return msg;
        } else if (containsCellReference(value)) {
            return "";
        } else if (value.length() > 100) {
            return "Dim 0 exceeds number of allowed characters which is 100";
        }

        return "";
    }

    public static String validateDim1(String value) {
        String msg = isNullOrEmpty(value, "dim1");
        if (msg.isEmpty() == false) {
            return msg;
        } else if (containsCellReference(value)) {
            return "";
        } else if (value.length() > 100) {
            return "Dim 1 exceeds number of allowed characters which is 100";
        }
        return "";
    }

    public static String validateDim3(String value) {
        String msg = isNullOrEmpty(value, "dim3");
        if (msg.isEmpty() == false) {
            return "";
        } else if (containsCellReference(value)) {
            return "";
        } else if (value.length() > 100) {
            return "Dim 3 exceeds number of allowed characters which is 100";
        }
        return "";
    }

    public static String validateDim2(String value) {
        String msg = isNullOrEmpty(value, "dim2");
        if (msg.isEmpty() == false) {
            return msg;
        } else if (containsCellReference(value)) {
            return "";
        } else if (value.toLowerCase().matches("^/?([?]{1}|[0-9]{4})(/[0-9]{1,2}|/open){0,1}$")) {
            return "";
        }
        return "Dim 2 is invalid";
    }

    public static String validateYear(String value) {
        String msg = isNullOrEmpty(value, "year");
        if (msg.isEmpty() == false) {
            return msg;
        } else if (containsCellReference(value)) {
            return "";
        } else if (value.matches("^[+-]?[0-9]{1,3}$")) {
            return "";
        }
        return "Year offset must be a number in range of -999 to +999";
    }

    public static String validatePeriod(String value) {
        String msg = isNullOrEmpty(value, "period");
        if (msg.isEmpty() == false) {
            return msg;
        } else if (containsCellReference(value)) {
            return "";
        } else if (value.matches("^[+-]?[0-9]{1,2}$")) {
            return "";
        }
        return "Period offset must be a number in range of -99 to +99";
    }

    public static String validateCompany(String value) {
        String msg = isNullOrEmpty(value, "cmpy");
        if (msg.isEmpty() == false) {
            return msg;
        } else if (containsCellReference(value)) {
            return "";
        } else if (value.matches("^([0-9]{1,9})|([?]{1})$")) {
            return "";
        }
        return "Company must be a number with 9 digits top";
    }

    public static String validateCurrency(String value) {
        String msg = isNullOrEmpty(value, "curr");
        if (msg.isEmpty() == false) {
            return msg;
        }
        if (containsCellReference(value)) {
            return "";
        } else if (value.length() < 5) {
            return "";
        }
        return "Currency must be a string of length 4 max";
    }

    public static String validateDate(String value) {
        String msg = isNullOrEmpty(value, "date");
        if (msg.isEmpty() == false) {
            return msg;
        }
        if (containsCellReference(value)) {
            return "";
        } else if (value.length() > 100) {
            return "Date exceeds number of allowed characters which is 100";
        }
        return "";
    }

    public static String validateCostCentre(String value) {
        String msg = isNullOrEmpty(value, "costc");
        if (msg.isEmpty() == false) {
            return msg;
        } else if (containsCellReference(value)) {
            return "";
        } else if (value.length() > 100) {
            return "Costc exceeds number of allowed characters which is 100";
        }
        return "";
    }

    public static String validateField(String value) {
        String msg = isNullOrEmpty(value, "field");
        if (msg.isEmpty() == false) {
            return msg;
        } else if (containsCellReference(value)) {
            return "";
        } else if (value.length() > 128) {
            return "Max length for field is 128";
        } else {
            return "";
        }
    }

    public static String validateModel(String model) {
        String msg = isNullOrEmpty(model, "model");
        if (msg.isEmpty() == false) {
            return msg;
        }
        return "";
    }

    private static boolean containsCellReference(String mapping) {
        // #Sheet1#A#1
        Pattern pattern = Pattern.compile("#[^#]*#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");
        Matcher matcher = pattern.matcher(mapping);
        while (matcher.find()) {
            return true;
        }
        // #A#1
        pattern = Pattern.compile("#[ABCDEFGHIJKLMNOPQRSTUVWXYZ]*#[0123456789]*");
        matcher = pattern.matcher(mapping);
        while (matcher.find()) {
            return true;
        }
        return false;
    }

    private static String isNullOrEmpty(String value, String type) {
        if (value == null) {
            return "No value for \"" + type + "\"";
        } else if (value.trim().isEmpty()) {
            return "Value for \"" + type + "\" is empty";
        } else {
            return "";
        }
    }
}
