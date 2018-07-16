package com.cedar.cp.util.spreadsheet;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util for operations related to dimension 0,1,2 and data type.
 * @author Szymon Walczak
 * @email szymon.walczak@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
public class DimensionUtil {

    /**
     * Takes value for specified key from mapping
     * @param mapping input or output mapping
     * @param key "dim0" etc.
     * @return
     */
    public static String getKeyFromMapping(String mapping, String key) {
        String checkedMappingString = checkMapping(mapping, 0);
        
        // cut by comma
        if (!checkedMappingString.isEmpty()) {
            StringTokenizer stringTokenizer = new StringTokenizer(checkedMappingString, "(,)");
            stringTokenizer.nextToken();
            
            // found tokens
            while (stringTokenizer.hasMoreTokens()) {
                String dimToken = stringTokenizer.nextToken();
                // find key
                if (dimToken.startsWith(key)) {
                    // get value in quotes
                    StringTokenizer stringTokenizer2 = new StringTokenizer(dimToken, "\"");
                    stringTokenizer2.nextToken();
                    if (stringTokenizer2.hasMoreTokens())
                        return stringTokenizer2.nextToken();
                }
            }
        }
        return "";
    }

    /**
     * Checks if the given string is valid mapping string, fixes characters
     * 
     * @param mapping string to check
     * @param typeMapping 0 = input 1 = output 2 = input or output
     * @return valid string mapping or empty string if not valid
     */
    public static String checkMapping(String mapping, int typeMapping) {
        if (mapping != null && (typeMapping >= 0) && (typeMapping <= 2)) {
            boolean isInput = false;
            boolean isOutput = false;
            
            // if special case, return without change
            if (mapping.startsWith("cedar.cp.financeCube(") || mapping.startsWith("cedar.cp.dim0Identifier()") || mapping.startsWith("cedar.cp.dim0Description()") || mapping.startsWith("cedar.cp.dim1Identifier()") || mapping.startsWith("cedar.cp.dim1Description()") || mapping.startsWith("cedar.cp.dim2Identifier()")
                    || mapping.startsWith("cedar.cp.dim2Description()") || mapping.startsWith("cedar.cp.param(") || mapping.startsWith("cedar.cp.structures(") || mapping.startsWith("cedar.cp.getVisId(") || mapping.startsWith("cedar.cp.getDescription(") || mapping.startsWith("cedar.cp.getLabel(") || mapping.startsWith("cedar.cp.formLink(")) {
                return mapping;
            }
            
            // find input mapping
            if ((typeMapping == 0) || (typeMapping == 2)) {
                Pattern patternInputMapping = Pattern.compile("^cedar\\.cp\\.(getCell|getGlob|cell|getBaseVal|getQuantity|getCumBaseVal|getCumQuantity|getCurrencyLookup|getParameterLookup|getAuctionLookup)\\(.*\\)$");
                Matcher matcher = patternInputMapping.matcher(mapping);
                isInput = matcher.find();
            }
            
            // find output mapping
            if ((typeMapping == 1) || (typeMapping == 2)) {
                Pattern patternInputMapping = Pattern.compile("^cedar\\.cp\\.(putCell|post)\\(.*\\)$");
                Matcher matcher = patternInputMapping.matcher(mapping);
                isOutput = matcher.find();
            }

            // fix special characters
            if (isInput || isOutput) {
                mapping = mapping.replaceAll("(\"|&quot;)", ""); // delete " or &quot;
                mapping = mapping.replaceAll(";", ","); // replace ; to ,
                mapping = mapping.replaceAll("=", "=\""); // add " after =
                mapping = mapping.replaceAll(",", "\","); // add " before ,
                mapping = mapping.replaceAll("\\)$", "\"\\)"); // add " before last )
                return mapping;
            } else {
                // no mapping
                return "";
            }
        } else {
            // not valid
            return "";
        }
    }

    /**
     * Changes dim2 from mapping to final year and period using dimension from context
     * @param dimension dim2 with possible ;year=-x,period=y
     * @param contextDim Calendar dimension "/year/period"
     * @param yearOffset increase or decrease year
     * @param periodOffset increase or decrease period
     * @return valid dim2
     */
    public static String dim2Evaluator(String dimension, String contextDim, int yearOffset, int periodOffset) {
        if (dimension != null && contextDim != null) {
            
            String[] dimAndOffset = dimension.split(";");
            if (dimAndOffset.length > 1) {
                // get offsets
                if (dimAndOffset[1].indexOf("year") != -1) {
                    // find year and period
                    String[] yearOrPeriodParams = dimAndOffset[1].split(",");
                    // find name and value
                    yearOffset += getValueFromParam(yearOrPeriodParams[0], "year");
                    if (yearOrPeriodParams.length > 1) {
                        // period
                        periodOffset += getValueFromParam(yearOrPeriodParams[1], "period");
                    }
                } else if (dimAndOffset[1].indexOf("period") != -1) {
                    periodOffset += getValueFromParam(dimAndOffset[1], "period");
                }
                // get rid of offsets
                dimension = dimAndOffset[0];
            }
            if (dimAndOffset.length > 2) {
                if (dimAndOffset[2].indexOf("period") != -1) {
                    periodOffset += getValueFromParam(dimAndOffset[2], "period");
                }
            }
            
            int period = 0;
            int year = 0;
            String[] yearAndPeriod = dimension.split("/");
            
            
            if (dimension.indexOf("?") != -1) {
                // replace with context values
                
                String[] contextYearAndPeriod = contextDim.split("/");
                String tempContextPeriod = contextYearAndPeriod[contextYearAndPeriod.length - 1];
                String tempContextYear = contextYearAndPeriod[contextYearAndPeriod.length - 2];
                
                try {                   
                    String tempPeriod = yearAndPeriod[yearAndPeriod.length - 1];
                    if (tempPeriod.equals("?")) {
                        // replace with context period
                        period = Integer.parseInt(tempContextPeriod);
                    } else {
                        period = Integer.parseInt(tempPeriod);
                    }

                    String tempYear = yearAndPeriod[yearAndPeriod.length - 2];
                    if (tempYear.equals("?")) {
                        // replace with context year
                        year = Integer.parseInt(tempContextYear);
                    } else {
                        year = Integer.parseInt(tempYear);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                period += periodOffset;
                year += yearOffset;
            } else {
                try {
                    // get offsets if any
                    period = Integer.parseInt(yearAndPeriod[yearAndPeriod.length - 1]);
                    year = Integer.parseInt(yearAndPeriod[yearAndPeriod.length - 2]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                period += periodOffset;
                year += yearOffset;
            }
            // change year if period offset is too big
            while (period < 1) {
                year -= 1;
                period += 12;
            }
            while (period > 12) {
                year += 1;
                period -= 12;
            }
            return "/" + year + "/" + period;
        }
        return "";
    }
    
    /**
     * cuts expression like "name=value" and returns numeric value
     * @param expression
     * @param key
     * @return numeric value for specified key from expression
     */
    private static int getValueFromParam(String expression, String key) {
        Integer result = 0;
        String[] paramPeriod = expression.split("=");
        if (paramPeriod[0].equalsIgnoreCase(key)) {
            try {
                result = Integer.parseInt(paramPeriod[1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        
        if (result == null) {
            return 0;
        } else {
            return result;
        }
    }
	
    /**
     * Returns value for specified key from one of mapping
     * @param key
     * @param inputMapping used as default
     * @param outputMapping if inputMapping is null using this mapping
     * @return value for specified key or "" if no key or mapping found
     */
    public static String getKeyFromMapping(String key, String inputMapping, String outputMapping) {
        if (inputMapping != null) {
            return DimensionUtil.getKeyFromMapping(inputMapping, key);
        }
        if (outputMapping != null) {
            return DimensionUtil.getKeyFromMapping(outputMapping, key);
        }
        return "";
    }

}
