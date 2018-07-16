package com.softproideas.util.validation;

import java.util.Map;

public class GlobalMappedMappingValidator extends MappingValidator {

    public GlobalMappedMappingValidator(String expression) {
        super(expression);
    }
    protected String[] validateMapping(Map<String, String> args){        
        switch (getFunction()) {
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
                return MappingFunctionsValidator.validateGlobalMappedGetCell(args);
            }
            case PUT_CELL: {
                return MappingFunctionsValidator.validateGlobalMappedPutCell(args);
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
                return MappingFunctionsValidator.validateGlobalMappedGetCurrencyLookup(args);
            }
            case GET_PARAMETER_LOOKUP: {
                return MappingFunctionsValidator.validateGlobalMappedGetParameterLookup(args);
            }
            default: {
                String[] msg = { "Something gone wrong, dunno how" };
                return msg;
            }
        }
    }

}
