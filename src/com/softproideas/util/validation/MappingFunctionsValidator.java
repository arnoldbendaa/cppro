package com.softproideas.util.validation;

import java.util.ArrayList;
import java.util.Map;

public class MappingFunctionsValidator {

    // public static String validateFinanceCube(String arguments){
    // Map<String, String> args = getListOfArguments(arguments);
    // if(args == null || args.size() != 2){
    // return "";
    // }
    // else{
    // for(String s:args.keySet()){
    // if(s.length() < 21 && args.get(s) == null){
    // return "";
    // }
    // else{
    // return "";
    // }
    // }
    // }
    // return "";
    // }
    public static String[] validateGetDescription(Map<String, String> args) {
        return validateGetVisId(args);
    }

    public static String[] validateGetLabel(Map<String, String> args) {
        return validateGetVisId(args);
    }

    public static String[] validateGetVisId(Map<String, String> args) {
        ArrayList<String> msgs = new ArrayList<String>();
        if (args == null || args.size() == 0) {
            msgs.add("No arguments found");
        } else if (args.size() > 1) {
            if (args.containsKey(MappingArguments.DIM2.toString()) == false) {
                msgs.add("year and/or period must be used with dim2");
            }
            for (String s: args.keySet()) {
                String msg = null;
                if (args.get(s) == null) {
                    MappingArguments ma = MappingArguments.getArgument(s);
                    if (ma == MappingArguments.YEAR) {
                        msg = "Year is empty";
                    } else if (ma == MappingArguments.PERIOD) {
                        msg = "Period is empty";
                    } else if (ma != MappingArguments.DIM2 && ma != MappingArguments.DATE) {
                        msg = "Can't mix in \"" + ma.toString() + "\"";
                    }
                } else {
                    MappingArguments ma = MappingArguments.getArgument(s);
                    if (ma == MappingArguments.DIM2 || ma == MappingArguments.DATE) {
                        msg = MappingArgumentsValidator.validateDim2(args.get(s));
                    } else if (ma == MappingArguments.PERIOD) {
                        msg = MappingArgumentsValidator.validatePeriod(args.get(s));
                    } else if (ma == MappingArguments.YEAR) {
                        msg = MappingArgumentsValidator.validateYear(args.get(s));
                    } else if (ma == MappingArguments.UNDEFINED) {
                        msg = "Undefined mapping arguments";
                    } else {
                        msg = "Can't mix in \"" + ma.toString() + "\"";
                    }
                }
                if (msg != null && !msg.isEmpty()) {
                    msgs.add(msg);
                }
            }

        } else {
            for (String s: args.keySet()) {
                String msg = null;
                MappingArguments ma = MappingArguments.getArgument(s);
                if (ma == MappingArguments.DIM2 || ma == MappingArguments.DATE) {
                    if (args.get(s) != null) {
                        msg = MappingArgumentsValidator.validateDim2(args.get(s));
                    }
                } else if (ma == MappingArguments.DIM0 || ma == MappingArguments.DIM1 || ma == MappingArguments.DIM3) {
                    if (args.get(s) != null) {
                        msg = MappingArgumentsValidator.validateDim0(args.get(s));
                    }
                } else if (ma == MappingArguments.UNDEFINED) {
                    msg = "Undefined mapping arguments";
                } else {
                    msg = "Can't use \"" + ma.toString() + "\"";
                }
                if (msg != null && !msg.isEmpty()) {
                    msgs.add(msg);
                }
            }
        }
        return msgs.toArray(new String[0]);
    }

    public static String[] validateGetCell(Map<String, String> args) {
        ArrayList<String> msgs = new ArrayList<String>();
        if (args == null) {
            msgs.add("No args");
        } else {
            for (String s: args.keySet()) {
                String msg = null;
                MappingArguments ma = MappingArguments.getArgument(s);
                if (args.get(s) == null) {
                    switch (ma) {
                        case DIM0:
                        case DIM1:
                        case DATE:
                        case DIM2:
                        case TYPE:
                        case DATA_TYPE:
                            break;
                        case YEAR:
                        case PERIOD:{
                            msg = "No value for \"" + s + "\" defined";
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                } else {
                    switch (ma) {
                        case DIM0: {
                            msg = MappingArgumentsValidator.validateDim0(args.get(s));
                            break;
                        }
                        case DIM1: {
                            msg = MappingArgumentsValidator.validateDim1(args.get(s));
                            break;
                        }
                        case DATE:
                        case DIM2: {
                            msg = MappingArgumentsValidator.validateDim2(args.get(s));
                            break;
                        }
                        case YEAR: {
                            msg = MappingArgumentsValidator.validateYear(args.get(s));
                        }
                        case PERIOD: {
                            msg = MappingArgumentsValidator.validatePeriod(args.get(s));
                        }
                        case DATA_TYPE: {
                            msg = MappingArgumentsValidator.validateDataType(args.get(s));
                            break;
                        }
                        case TYPE: {
                            msg = MappingArgumentsValidator.validateType(args.get(s));
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                }
                if (msg != null && !msg.isEmpty()) {
                    msgs.add(msg);
                }
            }

        }
        return msgs.toArray(new String[0]);
    }

    public static String[] validateGetQuantity(Map<String, String> args) {
        return validateGetBaseVal(args);
    }

    public static String[] validateGetCumBaseVal(Map<String, String> args) {
        return validateGetBaseVal(args);
    }

    public static String[] validateGetCumQuantity(Map<String, String> args) {
        return validateGetBaseVal(args);
    }

    public static String[] validateGetBaseVal(Map<String, String> args) {
        ArrayList<String> msgs = new ArrayList<String>();
        if (args == null) {
            msgs.add("No args");
        } else if (args.containsKey(MappingArguments.DIM3.toString()) == false) {
            msgs.add("Dim3 is required but not found");
        } else {
            for (String s: args.keySet()) {
                String msg = null;
                MappingArguments ma = MappingArguments.getArgument(s);
                if (args.get(s) == null) {
                    switch (ma) {
                        case DIM0:
                        case DIM1:
                        case DATE:
                        case DIM2:
                        case TYPE:
                        case DATA_TYPE:
                            break;
                        case DIM3: {
                            msg = "Dim3 must hava the value set";
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                } else {
                    switch (ma) {
                        case DIM0: {
                            msg = MappingArgumentsValidator.validateDim0(args.get(s));
                            break;
                        }
                        case DIM1: {
                            msg = MappingArgumentsValidator.validateDim1(args.get(s));
                            break;
                        }
                        case DATE:
                        case DIM2: {
                            msg = MappingArgumentsValidator.validateDim2(args.get(s));
                            break;
                        }
                        case DIM3: {
                            msg = MappingArgumentsValidator.validateDim3(args.get(s));
                            break;
                        }
                        case YEAR: {
                            msg = MappingArgumentsValidator.validateYear(args.get(s));
                        }
                        case PERIOD: {
                            msg = MappingArgumentsValidator.validatePeriod(args.get(s));
                        }
                        case DATA_TYPE: {
                            msg = MappingArgumentsValidator.validateDataType(args.get(s));
                            break;
                        }
                        case TYPE: {
                            msg = MappingArgumentsValidator.validateType(args.get(s));
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                }
                if (msg != null && !msg.isEmpty()) {
                    msgs.add(msg);
                }
            }

        }
        return msgs.toArray(new String[0]);
    }

    public static String[] validatePutCell(Map<String, String> args) {
        ArrayList<String> msgs = new ArrayList<String>();
        if (args == null) {
            msgs.add("No args");
        } else {
            for (String s: args.keySet()) {
                String msg = null;
                MappingArguments ma = MappingArguments.getArgument(s);
                if (args.get(s) == null) {
                    switch (ma) {
                        case DIM0:
                        case DIM1:
                        case DATE:
                        case DIM2:
                        case TYPE:
                        case DATA_TYPE:
                            break;
                        case YEAR:
                        case PERIOD:{
                            msg = "No value for \"" + s + "\" defined";
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                } else {
                    switch (ma) {
                        case DIM0: {
                            msg = MappingArgumentsValidator.validateDim0(args.get(s));
                            break;
                        }
                        case DIM1: {
                            msg = MappingArgumentsValidator.validateDim1(args.get(s));
                            break;
                        }
                        case DATE:
                        case DIM2: {
                            msg = MappingArgumentsValidator.validateDim2(args.get(s));
                            break;
                        }
                        case YEAR: {
                            msg = MappingArgumentsValidator.validateYear(args.get(s));
                        }
                        case PERIOD: {
                            msg = MappingArgumentsValidator.validatePeriod(args.get(s));
                        }
                        case DATA_TYPE: {
                            msg = MappingArgumentsValidator.validateDataType(args.get(s));
                            break;
                        }
                        case TYPE: {
                            msg = MappingArgumentsValidator.validateType(args.get(s), "M");
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                }
                if (msg != null && !msg.isEmpty()) {
                    msgs.add(msg);
                }
            }

        }
        return msgs.toArray(new String[0]);
    }

    public static String[] validateGlobalMappedGetCell(Map<String, String> args) {
        ArrayList<String> msgs = new ArrayList<String>();
        if (args == null) {
            msgs.add("No args");
        } else {
            for (String s: args.keySet()) {
                String msg = null;
                MappingArguments ma = MappingArguments.getArgument(s);
                if (args.get(s) == null) {
                    switch (ma) {
                        case DIM0:
                        case DIM1:
                        case DATE:
                        case DIM2:
                        case TYPE:
                        case COMPANY:
                        case CMPY:
                        case DATA_TYPE:
                            break;
                        case PERIOD:
                        case YEAR: {
                            msg = "No value for \"" + s + "\" defined";
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                } else {
                    switch (ma) {
                        case DIM0: {
                            msg = MappingArgumentsValidator.validateDim0(args.get(s));
                            break;
                        }
                        case DIM1: {
                            msg = MappingArgumentsValidator.validateDim1(args.get(s));
                            break;
                        }
                        case DATE:
                        case DIM2: {
                            msg = MappingArgumentsValidator.validateDim2(args.get(s));
                            break;
                        }
                        case YEAR: {
                            msg = MappingArgumentsValidator.validateYear(args.get(s));
                        }
                        case PERIOD: {
                            msg = MappingArgumentsValidator.validatePeriod(args.get(s));
                        }
                        case DATA_TYPE: {
                            msg = MappingArgumentsValidator.validateDataType(args.get(s));
                            break;
                        }
                        case CMPY:
                        case COMPANY: {
                            msg = MappingArgumentsValidator.validateCompany(args.get(s));
                            break;
                        }
                        case TYPE: {
                            msg = MappingArgumentsValidator.validateType(args.get(s));
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                }
                if (msg != null && !msg.isEmpty()) {
                    msgs.add(msg);
                }
            }

        }
        return msgs.toArray(new String[0]);
    }

    public static String[] validateGlobalMappedPutCell(Map<String, String> args) {
        ArrayList<String> msgs = new ArrayList<String>();
        if (args == null) {
            msgs.add("No args");
        }else {
            for (String s: args.keySet()) {
                String msg = null;
                MappingArguments ma = MappingArguments.getArgument(s);
                if (args.get(s) == null) {
                    switch (ma) {
                        case DIM0:
                        case DIM1:
                        case DATE:
                        case DIM2:
                        case TYPE:
                        case COMPANY:
                        case CMPY:
                        case DATA_TYPE:
                            break;
                        case PERIOD:
                        case YEAR: {
                            msg = "No value for \"" + s + "\" defined";
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                } else {
                    switch (ma) {
                        case DIM0: {
                            msg = MappingArgumentsValidator.validateDim0(args.get(s));
                            break;
                        }
                        case DIM1: {
                            msg = MappingArgumentsValidator.validateDim1(args.get(s));
                            break;
                        }
                        case DATE:
                        case DIM2: {
                            msg = MappingArgumentsValidator.validateDim2(args.get(s));
                            break;
                        }
                        case YEAR: {
                            msg = MappingArgumentsValidator.validateYear(args.get(s));
                        }
                        case PERIOD: {
                            msg = MappingArgumentsValidator.validatePeriod(args.get(s));
                        }
                        case DATA_TYPE: {
                            msg = MappingArgumentsValidator.validateDataType(args.get(s));
                            break;
                        }
                        case CMPY:
                        case COMPANY: {
                            msg = MappingArgumentsValidator.validateCompany(args.get(s));
                            break;
                        }
                        case TYPE: {
                            msg = MappingArgumentsValidator.validateType(args.get(s), "M");
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                }
                if (msg != null && !msg.isEmpty()) {
                    msgs.add(msg);
                }
            }

        }
        return msgs.toArray(new String[0]);
    }

    public static String[] validateGlobalMappedGetCurrencyLookup(Map<String, String> args) {
        ArrayList<String> msgs = new ArrayList<String>();
        if (args == null) {
            msgs.add("No args");
        } else if (args.containsKey(MappingArguments.CURRENCY.toString()) == false) {
            msgs.add("Currency is not defined");
        } else {
            for (String s: args.keySet()) {
                String msg = null;
                MappingArguments ma = MappingArguments.getArgument(s);
                if (args.get(s) == null) {
                    switch (ma) {
                        case DATE:
                        case DIM2:
                        case COMPANY:
                        case CMPY:
                            break;
                        case YEAR:
                        case PERIOD:
                        case CURRENCY: {
                            msg = "\"" + ma.toString() + "\" must have value defined";
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                } else {
                    switch (ma) {
                        case DATE:
                        case DIM2: {
                            msg = MappingArgumentsValidator.validateDim2(args.get(s));
                            break;
                        }
                        case YEAR: {
                            msg = MappingArgumentsValidator.validateYear(args.get(s));
                        }
                        case PERIOD: {
                            msg = MappingArgumentsValidator.validatePeriod(args.get(s));
                        }
                        case CURRENCY: {
                            msg = MappingArgumentsValidator.validateCurrency(args.get(s));
                            break;
                        }
                        case CMPY:
                        case COMPANY: {
                            msg = MappingArgumentsValidator.validateCompany(args.get(s));
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                }
                if (msg != null && !msg.isEmpty()) {
                    msgs.add(msg);
                }
            }

        }
        return msgs.toArray(new String[0]);
    }

    public static String[] validateGlobalMappedGetParameterLookup(Map<String, String> args) {
        ArrayList<String> msgs = new ArrayList<String>();
        if (args == null) {
            msgs.add("No args");
        }else {
            for (String s: args.keySet()) {
                String msg = null;
                MappingArguments ma = MappingArguments.getArgument(s);
                if (args.get(s) == null) {
                    switch (ma) {
                        case DIM0:
                        case COST_CENTER:
                        case CMPY:
                        case COMPANY:
                            break;
                        case FIELD:{
                            msg = "No value for \"" + s + "\" defined";
                            break;
                        }                            
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                } else {
                    switch (ma) {
                        case DIM0:
                        case COST_CENTER: {
                            msg = MappingArgumentsValidator.validateCostCentre(args.get(s));
                            break;
                        }
                        case CMPY:
                        case COMPANY: {
                            msg = MappingArgumentsValidator.validateCompany(args.get(s));
                            break;
                        }
                        case FIELD: {
                            msg = MappingArgumentsValidator.validateField(args.get(s));
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                }
                if (msg != null && !msg.isEmpty()) {
                    msgs.add(msg);
                }
            }

        }
        return msgs.toArray(new String[0]);
    }

    public static String[] validateGetCurrencyLookup(Map<String, String> args) {
        ArrayList<String> msgs = new ArrayList<String>();
        if (args == null) {
            msgs.add("No args");
        } else if (args.containsKey(MappingArguments.CURRENCY.toString()) == false) {
            msgs.add("Currency is not defined");
        } else {
            for (String s: args.keySet()) {
                String msg = null;
                MappingArguments ma = MappingArguments.getArgument(s);
                if (args.get(s) == null) {
                    switch (ma) {
                        case DATE:
                        case DIM2:
                        case COMPANY:
                        case CMPY:
                            break;
                        case YEAR:
                        case PERIOD:
                        case CURRENCY: {
                            msg = "\"" + ma.toString() + "\" must have value defined";
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                } else {
                    switch (ma) {
                        case DATE:
                        case DIM2: {
                            msg = MappingArgumentsValidator.validateDim2(args.get(s));
                            break;
                        }
                        case YEAR: {
                            msg = MappingArgumentsValidator.validateYear(args.get(s));
                        }
                        case PERIOD: {
                            msg = MappingArgumentsValidator.validatePeriod(args.get(s));
                        }
                        case CURRENCY: {
                            msg = MappingArgumentsValidator.validateCurrency(args.get(s));
                            break;
                        }
                        case CMPY:
                        case COMPANY: {
                            msg = MappingArgumentsValidator.validateCompany(args.get(s));
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                }
                if (msg != null && !msg.isEmpty()) {
                    msgs.add(msg);
                }
            }

        }
        return msgs.toArray(new String[0]);
    }

    public static String[] validateGetParameterLookup(Map<String, String> args) {
        ArrayList<String> msgs = new ArrayList<String>();
        if (args == null) {
            msgs.add("No args");
        } else {
            for (String s: args.keySet()) {
                String msg = null;
                MappingArguments ma = MappingArguments.getArgument(s);
                if (args.get(s) == null && ma == MappingArguments.FIELD) {
                    msg = "No value for \"" + s + "\" defined";
                } else if (args.get(s) == null) {
                    switch (ma) {
                        case DIM0:
                        case COST_CENTER:
                        case CMPY:
                        case COMPANY:
                            break;
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                } else {
                    switch (ma) {
                        case DIM0:
                        case COST_CENTER: {
                            msg = MappingArgumentsValidator.validateCostCentre(args.get(s));
                            break;
                        }
                        case CMPY:
                        case COMPANY: {
                            msg = MappingArgumentsValidator.validateCompany(args.get(s));
                            break;
                        }
                        case FIELD: {
                            msg = MappingArgumentsValidator.validateField(args.get(s));
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                }
                if (msg != null && !msg.isEmpty()) {
                    msgs.add(msg);
                }
            }

        }
        return msgs.toArray(new String[0]);
    }

    public static String validateDimIdOrDescription(Map<String, String> args) {
        if (args != null && args.size() > 0) {
            return "Can't have any arguments";
        } else {
            return "";
        }
    }

    public static String[] validateGetGlob(Map<String, String> args) {
        ArrayList<String> msgs = new ArrayList<String>();
        if (args == null) {
            msgs.add("No args");
        } else if (args.containsKey(MappingArguments.MODEL.toString()) == false) {
            msgs.add("Model is not defined");
        } else {
            for (String s: args.keySet()) {
                String msg = null;
                MappingArguments ma = MappingArguments.getArgument(s);
                if (args.get(s) == null) {
                    switch (ma) {
                        case DIM0:
                        case DIM1:
                        case DATE:
                        case DIM2:
                        case TYPE:
                        case DATA_TYPE:
                            break;
                        case YEAR:
                        case PERIOD:
                        case MODEL: {
                            msg = "No value for \"" + s + "\" defined";
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                } else {
                    switch (ma) {
                        case DIM0: {
                            msg = MappingArgumentsValidator.validateDim0(args.get(s));
                            break;
                        }
                        case DIM1: {
                            msg = MappingArgumentsValidator.validateDim1(args.get(s));
                            break;
                        }
                        case DATE:
                        case DIM2: {
                            msg = MappingArgumentsValidator.validateDim2(args.get(s));
                            break;
                        }
                        case YEAR: {
                            msg = MappingArgumentsValidator.validateYear(args.get(s));
                        }
                        case PERIOD: {
                            msg = MappingArgumentsValidator.validatePeriod(args.get(s));
                        }
                        case DATA_TYPE: {
                            msg = MappingArgumentsValidator.validateDataType(args.get(s));
                            break;
                        }
                        case TYPE: {
                            msg = MappingArgumentsValidator.validateType(args.get(s));
                            break;
                        }
                        case MODEL: {
                            msg = MappingArgumentsValidator.validateModel(args.get(s));
                            break;
                        }
                        case UNDEFINED: {
                            msg = "\"" + s + "\" is not a valid argument name";
                            break;
                        }
                        default: {
                            msg = "Can't use \"" + ma.toString() + "\"";
                        }
                    }
                }
                if (msg != null && !msg.isEmpty()) {
                    msgs.add(msg);
                }
            }

        }
        return msgs.toArray(new String[0]);
    }
}
