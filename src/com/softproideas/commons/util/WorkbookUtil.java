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
package com.softproideas.commons.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cedar.cp.util.XmlUtils;
import com.cedar.cp.util.flatform.model.workbook.CellDTO;
import com.cedar.cp.util.flatform.model.workbook.CellExtendedDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.flatform.model.workbook.WorksheetDTO;
import com.cedar.cp.util.xmlform.CalendarElementNode;
import com.cedar.cp.util.xmlform.CalendarInfo;
import com.softproideas.app.reviewbudget.budget.model.CellToUpdateDTO;
import com.softproideas.app.reviewbudget.budget.model.WorkbookToUpdateDTO;
import com.softproideas.app.reviewbudget.budget.model.WorksheetToUpdateDTO;

/**
 * Class builds string (template of workbook) which is used to update data in database.
 * Class is used during recalculating whole Excel document
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
public class WorkbookUtil {
    
    /**
     * Create (build) XML for workbook which is updated in database.
     */
    public static String buildXMLUpdates(WorkbookToUpdateDTO workbookToUpdate, Map<String, CalendarInfo> calendarInfoMap) throws Exception {
        String calendarContext = "";
        if (workbookToUpdate.getProperties() != null) {
            calendarContext = workbookToUpdate.getProperties().get(WorkbookProperties.DIMENSION_2_VISID.toString());
        }
        StringBuffer xml = new StringBuffer();
        xml.append("<WorkbookUpdate>");
        xml.append("<UserId>{0}</UserId>");
        xml.append("<BudgetCycleId>{1}</BudgetCycleId>");

        // adds parameters (from context) demanded to update workbook
        xml.append("<Parameters>");
        xml.append("<Parameter name=\"FormType\" value=\"6\" />");
        xml.append("<Parameter name=\"INVERT_NUMBERS_VALUE\" value=\"" + workbookToUpdate.getProperties().get(WorkbookProperties.INVERT_NUMBERS_VALUE.toString()) + "\" />");
        xml.append("<Parameter name=\"EXCLUDE_DATA_TYPES\" value=\"" + workbookToUpdate.getProperties().get(WorkbookProperties.EXCLUDE_DATA_TYPES.toString()) + "\" />");
        for (Map.Entry<String, String> parameter: workbookToUpdate.getProperties().entrySet())
            xml.append("<Parameter name=\"" + parameter.getKey() + "\" value=\"" + XmlUtils.escapeStringForXML(parameter.getValue()) + "\" />");
        xml.append("</Parameters>");

        Iterator<WorksheetToUpdateDTO> iteratorWorksheet = workbookToUpdate.getWorksheets().iterator();

        while (iteratorWorksheet.hasNext()) {
            WorksheetToUpdateDTO worksheetDTO = iteratorWorksheet.next();
            xml.append("<Worksheet name=\"" + XmlUtils.escapeStringForXML(worksheetDTO.getName()) + "\" >");

            // adds properties to current worksheet
            xml.append("<Properties>");
            for (Map.Entry<String, String> property: worksheetDTO.getProperties().entrySet())
                xml.append("<Property name=\"" + property.getKey() + "\" value=\"" + XmlUtils.escapeStringForXML(property.getValue()) + "\" />");
            xml.append("</Properties>");

            xml.append("<Cells>");
            for (CellToUpdateDTO cell: worksheetDTO.getCells()) {
                String outputMapping = cell.getOutputMapping();
                if (outputMapping != null && !outputMapping.isEmpty()) {
                    boolean isPutCell = false;
                    String postAddress = getPostAddressForOutputMapping(outputMapping, worksheetDTO.getProperties().get(WorkbookProperties.MODEL_VISID.toString()), calendarContext, calendarInfoMap);
                    Object value = cell.getValue();
                    String row = String.valueOf(cell.getRow() + 1);
                    String col = cell.getColumn();
                    xml.append("<Cell row=\"" + row + "\" col=\"" + col + "\" addr=\"" + postAddress + "\" value=\"" + value + "\"");
                    if (outputMapping.indexOf("cedar.cp.putCell(") != -1) {
                        isPutCell = true;
                    }
                    xml.append(" putCell=\"").append(isPutCell).append("\"");
                    xml.append("/>");
                }
            }
            xml.append("</Cells>");
            xml.append("</Worksheet>");
        }
        xml.append("</WorkbookUpdate>");
        return xml.toString();
    }
    
    /**
     * Build address (dimensions) from output mapping. Missing dimensions are taken from context. 
     */
    private static String getPostAddressForOutputMapping(String outputMapping, String modelVisId, String calendarContext, Map<String, CalendarInfo> calendarInfoMap) {
        if (outputMapping != null && outputMapping.trim().length() > 0) {
            if (outputMapping.indexOf("cedar.cp.post(") != -1) {
                outputMapping = outputMapping.substring("cedar.cp.post(".length());
                return outputMapping.replaceAll("\\)", "");
            }

            if (outputMapping.indexOf("cedar.cp.putCell(") != -1) {
                outputMapping = outputMapping.substring("cedar.cp.putCell(".length());
                outputMapping = outputMapping.replaceAll("\\)", "");
                outputMapping = outputMapping.replaceAll("\"", "");
                String[] address = outputMapping.split(",");
                StringBuilder sb = new StringBuilder();

                for (int i = 0; i < address.length; ++i) {
                    String param = address[i];
                    String calendarVisId = null;
                    boolean isCalendarDimension = false;
                    int yearOffset = 0;
                    int periodOffset = 0;
                    String[] attributes = param.split(";");
                    String[] node = attributes;
                    int adjustedNode = attributes.length;

                    for (int j = 0; j < adjustedNode; ++j) {
                        String attribute = node[j];
                        String[] values = attribute.split("=");
                        if (values[0].startsWith("dim")) {
                            int dimensionNumber = Integer.parseInt(values[0].substring(3, 4));
                            if (dimensionNumber == 2) {
                                isCalendarDimension = true;
                                calendarVisId = values[1];
                            }
                        } else if (values[0].equalsIgnoreCase("year")) {
                            isCalendarDimension = true;
                            try {
                                yearOffset = Integer.parseInt(values[1]);
                            } catch (NumberFormatException exception) {
                                yearOffset = 0;
                            }
                        } else if (values[0].equalsIgnoreCase("period")) {
                            isCalendarDimension = true;
                            try {
                                periodOffset = Integer.parseInt(values[1]);
                            } catch (NumberFormatException exception) {
                                periodOffset = 0;
                            }
                        }
                    }

                    if (isCalendarDimension) {
                        sb.append("dim2=");
                        CalendarElementNode calendarNode;
                        if (calendarVisId != null) {
                            int var19 = calendarVisId.indexOf(63); //??
                            if (var19 != -1)
                                calendarVisId = calendarContext.substring(0,5) + calendarVisId.substring(var19 + 1);
                            
                            calendarNode = calendarInfoMap.get(modelVisId).findElement(calendarVisId);
                            if (calendarNode != null && (yearOffset != 0 || periodOffset != 0)) {
                                CalendarElementNode var22 = calendarInfoMap.get(modelVisId).getElementOffsetByYearAndPeriod(calendarNode.getStructureElementId(), yearOffset, periodOffset);
                                if (var22 != null)
                                    calendarNode = var22;
                            }
                            
                            if (calendarNode != null)
                                sb.append(calendarNode.getFullPathVisId());
                            else
                                sb.append(calendarVisId);
                            
                        } else {
                            CalendarElementNode var21 = calendarInfoMap.get(modelVisId).findElement(calendarContext);
                            if (var21 != null) {
                                if (yearOffset != 0 || periodOffset != 0) {
                                    calendarNode = calendarInfoMap.get(modelVisId).getElementOffsetByYearAndPeriod(var21.getStructureElementId(), yearOffset, periodOffset);
                                    if (calendarNode != null)
                                        var21 = calendarNode;
                                }
                                sb.append(var21.getFullPathVisId());
                            } else {
                                sb.append(calendarContext);
                            }
                        }
                    } else {
                        sb.append(param);
                    }

                    if (i < address.length - 1)
                        sb.append(",");
                }
                outputMapping = sb.toString();
            }
        }

        return XmlUtils.escapeStringForXML(outputMapping);
    }
    
    public static WorkbookDTO convertWorkbookDTOToExtendedVersion(WorkbookDTO workbook){
        for (WorksheetDTO worksheet: workbook.getWorksheets()) {
            List<CellDTO> cells = new ArrayList<CellDTO>();
            for (CellDTO cell: worksheet.getCells()) {
                cells.add(parseCellDTO(cell));
            }
            worksheet.setCells(cells);
        }
        return workbook;
    }
    
    public static CellDTO parseCellDTO(CellDTO cell) {
         CellDTO dto = new CellExtendedDTO();
         dto.setColumn(cell.getColumn());
         dto.setInputMapping(cell.getInputMapping());
         dto.setOutputMapping(cell.getOutputMapping());
         dto.setText(cell.getText());
         dto.setTags(cell.getTags());
         dto.setValidationMessages(cell.getValidationMessages());
         dto.setRow(cell.getRow());
         dto.setText(cell.getText());         
        return dto;
    }
}
