package com.softproideas.util.validation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MappingValidator {

    public static final String PREFIX = "cedar.cp.";
    public static final char ARG_SEPARATOR = ',';
    public static final char DATE_SEPARATOR = ';';
    public static final char OPEN_FORMULA = '(';
    public static final char CLOSE_FORMULA = ')';

    private String expression;
    private String trimedExpression;
    private List<String> errMsgs;
    private MappingFunction function = MappingFunction.UNDEFINED;
    private Map<String, String> args;

    public MappingValidator(String expression) {
        this.expression = expression;
        init();
    }

    public String[] getValidationErrors() {
        if (errMsgs.isEmpty()) {
            return new String[0];
        }
        return errMsgs.toArray(new String[0]);
    }

    public MappingFunction getFunction() {
        return this.function;
    }

    public boolean isValid() {
        return errMsgs.isEmpty();
    }

    public Map<String, String> getListOfArguments() {
        return new LinkedHashMap<String, String>(args);
    }

    /**
     * If expresison is valid returns formated expressions without apostrophes,
     * white spaces, extra character at the begining and ending,
     * and with semicolons replaced with colons.
     * 
     * @return formated expression or null if expression is not valid.
     */
    public String buildMapping() {
        if (isValid() && trimedExpression == null) {
            trimedExpression = buildMapping(PREFIX, function, args);
        }
        return trimedExpression;
    }

    /**
     * 
     * @return original expression.
     */
    public String getOrigin() {
        return expression;
    }

    private void init() {
        errMsgs = new ArrayList<String>();
        if (expression == null || expression.length() == 0) {
            errMsgs.add("No expression defined");
            return;
        }
        function = getFunction(expression, errMsgs);
        if (errMsgs.isEmpty() == true) {
            String stringArguments = null;
            stringArguments = getArguments(expression, errMsgs);
            args = parseStringArgumentsToMapOfArguments(stringArguments);
            String[] msgs = validateMapping(new HashMap<String, String>(args));
            if (msgs != null && msgs.length > 0) {
                for (String s: msgs) {
                    errMsgs.add(s);
                }
            }
        }
    }

    /**
     * 
     * @param args - map of mapping arguments and values
     * @return array of errors
     */
    protected String[] validateMapping(Map<String, String> args) {
        switch (function) {
            case GET_AUCTION_LOOKUP:
            case FINANCE_CUBE:
            case FORM_LINK:
            case FORM_FULL_LINK:
            case CELL:
            case PARAM:
            case STRUCTURES: {
                if (args == null) {
                    String[] msg = { "some error processing unknown mapping" };
                    return msg;
                } else {
                    return new String[0];
                }
            }
            case GET_CELL: {
                return MappingFunctionsValidator.validateGetCell(args);
            }
            case PUT_CELL: {
                return MappingFunctionsValidator.validatePutCell(args);
            }
            case DIM0_IDENTIFIER:
            case DIM0_DESCRIPTION:
            case DIM1_IDENTIFIER:
            case DIM1_DESCRIPTION:
            case DIM2_IDENTIFIER:
            case DIM2_DESCRIPTION: {
                String msg = MappingFunctionsValidator.validateDimIdOrDescription(args);
                if (!msg.isEmpty()) {
                    String[] errmsg = { msg };
                    return errmsg;
                } else {
                    return new String[0];
                }
            }
            case GET_DESCRIPTION:
            case GET_LABEL:
            case GET_VIS_ID: {
                return MappingFunctionsValidator.validateGetVisId(args);
            }
            case GET_BASE_VAL: {
                return MappingFunctionsValidator.validateGetBaseVal(args);
            }
            case GET_CUM_QUANTITY: {
                return MappingFunctionsValidator.validateGetCumQuantity(args);
            }
            case GET_QUANTITY: {
                return MappingFunctionsValidator.validateGetQuantity(args);
            }
            case GET_CUM_BASE_VAL: {
                return MappingFunctionsValidator.validateGetCumBaseVal(args);
            }
            case GET_CURRENCY_LOOKUP: {
                return MappingFunctionsValidator.validateGetCurrencyLookup(args);
            }
            case GET_PARAMETER_LOOKUP: {
                return MappingFunctionsValidator.validateGetParameterLookup(args);
            }
            case GET_GLOB:{
                return MappingFunctionsValidator.validateGetGlob(args);
            }
            default: {
                String[] msg = { "Something gone wrong, dunno how" };
                return msg;
            }
        }
    }

    /**
     * Extracts function from the expression.
     * 
     * @param expression - mapping expression,
     * @param errMsgs - placeholder for error messages,
     * @return returns MappingFunction enum or null if failed to extract function.
     */
    public static MappingFunction getFunction(String expression, List<String> errMsgs) {
        int prefixInset = expression.indexOf(PREFIX);
        int openFormulaInset = expression.indexOf(OPEN_FORMULA);
        if (prefixInset < 0) {
            if (errMsgs != null) {
                errMsgs.add("Can't find \"" + PREFIX + "\" formula");
            }
            return MappingFunction.UNDEFINED;
        } else if (openFormulaInset < 0) {
            if (errMsgs != null) {
                errMsgs.add("No opening bracet");
            }
            return MappingFunction.UNDEFINED;
        } else if (prefixInset > openFormulaInset) {
            if (errMsgs != null) {
                errMsgs.add("Function must be declared after prefix and before opening bracet");
            }
            return MappingFunction.UNDEFINED;
        }
        return MappingFunction.getMapping(expression.substring(prefixInset + PREFIX.length(), openFormulaInset));
    }

    /**
     * Extracts function arguments from the expression.
     * 
     * @param expression - mapping expression,
     * @param errMsgs - placeholder for error messages,
     * @return returns string of arguments or null if failed to extract function arguments.
     */
    public static String getArguments(String expression, List<String> errMsgs) {
        int openFormulaInset = expression.indexOf(OPEN_FORMULA);
        int closeFormulaInset = expression.indexOf(CLOSE_FORMULA);
        if (openFormulaInset < 0) {
            if (errMsgs != null) {
                errMsgs.add("No opening bracet");
            }
            return null;
        } else if (closeFormulaInset < 0) {
            if (errMsgs != null) {
                errMsgs.add("No clossing bracet");
            }
            return null;
        } else if (openFormulaInset > closeFormulaInset) {
            if (errMsgs != null) {
                errMsgs.add("argumetns must be inside openig and closing bracets");
            }
            return null;
        }
        return expression.substring(openFormulaInset + 1, closeFormulaInset);
    }

    public static String buildMapping(String prefix, MappingFunction mappingFunction, Map<String, String> mappingArguments) {
        if (mappingArguments == null || mappingFunction == MappingFunction.UNDEFINED) {
            return null;
        }
        if (mappingArguments.isEmpty()) {
            return prefix + mappingFunction.toString() + "()";
        }
        StringBuilder args = new StringBuilder();

        Iterator<String> i = mappingArguments.keySet().iterator();
        while (i.hasNext()) {
            String value = i.next();
            if (mappingArguments.get(value) == null) {
                args.append(value);
            } else {
                args.append(value + "=" + mappingArguments.get(value));
            }
            if (i.hasNext()) {
                args.append(",");
            }
        }
        return prefix + mappingFunction.toString() + "(" + args.toString() + ")";
    }

    /**
     * Gets map of argumets and values from function arguments string, removes apostrophes and white spaces.
     * 
     * @param args - function arguments string,
     * @return return map of arguments name and values.
     */
    public static Map<String, String> parseStringArgumentsToMapOfArguments(String args) {
        if (args == null || args.trim().length() == 0) {
            return new LinkedHashMap<String, String>();
        }
        args = args.replaceAll("\"", "");
        Map<String, String> arguments = new LinkedHashMap<String, String>();
        StringBuilder sb = new StringBuilder();
        String arg = null;
        // String value = null;
        boolean isValue = false;
        for (int i = 0; i < args.length(); i++) {
            String value = null;
            if (args.charAt(i) == '=') {
                // is argument
                if (isValue == false) {
                    arg = sb.toString();
                    if (arg == null || arg.trim().length() == 0) {
                        return new LinkedHashMap<String, String>();
                    } else {
                        arg = arg.trim().toLowerCase();
                    }
                    sb = sb.delete(0, sb.capacity());
                    isValue = true;
                }
                // can't have argument=value=value
                else {
                    return new LinkedHashMap<String, String>();
                }
            } else if (args.charAt(i) == ',' || args.charAt(i) == ';') {
                // is value
                if (isValue == true) {
                    value = sb.toString();
                    if (value != null) {
                        value = value.trim();
                    }
                    sb = sb.delete(0, sb.capacity());
                    isValue = false;
                }
                // argument with no value
                else {
                    arg = sb.toString();
                    if (arg == null || arg.trim().length() == 0) {
                        return new LinkedHashMap<String, String>();
                    } else {
                        arg = arg.trim().toLowerCase();
                    }
                    sb = sb.delete(0, sb.capacity());
                }
                if (arguments.containsKey(arg) == true) {
                    return new LinkedHashMap<String, String>();
                } else {
                    arguments.put(arg, value);
                }
            } else {
                sb.append(args.charAt(i));
            }
            if (i == args.length() - 1) {
                // is value
                if (isValue == true) {
                    value = sb.toString();
                    if (value != null) {
                        value = value.trim();
                    }
                }
                // is argument with no value
                else {
                    arg = sb.toString();
                    if (arg == null || arg.length() == 0) {
                        return new LinkedHashMap<String, String>();
                    } else {
                        arg = arg.trim().toLowerCase();
                    }
                }
                if (arguments.containsKey(arg) == true) {
                    return new LinkedHashMap<String, String>();
                } else {
                    arguments.put(arg, value);
                }
            }
        }

        return arguments;
    }
}
