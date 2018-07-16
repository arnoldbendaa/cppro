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
package com.softproideas.app.flatformeditor.form.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.util.CellReference;

import com.cedar.cp.util.flatform.model.workbook.CellDTO;
import com.cedar.cp.util.flatform.model.workbook.CellExtendedDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.flatform.model.workbook.WorksheetDTO;
import com.softproideas.util.validation.GlobalMappedMappingValidator;
import com.softproideas.util.validation.MappingValidator;

public class MappingsValidation {

    public static WorkbookDTO validateWorkbook(WorkbookDTO workbook) {
        WorkbookDTO tempWorkbook = new WorkbookDTO();
        if (workbook == null) {
            // no workbook
        }
        Map<String, String> properties = workbook.getProperties();
        if (properties == null || properties.isEmpty()) {
            // no context
        }
        List<WorksheetDTO> worksheets = workbook.getWorksheets();
        if (worksheets == null || worksheets.isEmpty()) {
            // no worksheets
        }
        List<WorksheetDTO> tempWorksheets = new ArrayList<WorksheetDTO>();
        for (WorksheetDTO w: worksheets) {
            WorksheetDTO tempWorksheet = validateWorksheet(w);
            if (tempWorkbook.isValid() == true && tempWorksheet.isValid() == false) {
                tempWorkbook.setValid(false);
            }
            tempWorksheets.add(tempWorksheet);
        }
        tempWorkbook.setWorksheets(tempWorksheets);
        return tempWorkbook;
    }

    public static WorksheetDTO validateWorksheet(WorksheetDTO worksheet) {
        // List<String> worksheetErrors = new ArrayList<String>();
        WorksheetDTO tempWorksheet = new WorksheetDTO();
        List<? extends CellDTO> cells = worksheet.getCells();

        boolean isGlobal = false;
        boolean isValid = true;
        String modelVisId = worksheet.getProperties().get(WorkbookProperties.MODEL_VISID.toString());
        if (modelVisId.toUpperCase().startsWith("GL")) {
            isGlobal = true;
        }
        List<CellExtendedDTO> tempCells = new ArrayList<CellExtendedDTO>();
        CellDTO[] b = cells.toArray(new CellDTO[0]);
        for(int i = b.length -1; i >=0;i--){
            boolean cellIsValid = validateCell(cells.get(i), isGlobal);
            if (cellIsValid == false) {
                tempCells.add(parseCellDTO(cells.get(i)));
                cells.remove(i);
                isValid = false;
            }
        }
        //tempWorksheet.setCells(tempCells);
        tempWorksheet.setValid(isValid);
        return tempWorksheet;
    }

    public static boolean validateCell(CellDTO cell, boolean isGlobal) {
        String[][] errors = new String[2][];
        String mapping = cell.getInputMapping();
        boolean isValid = true;
        if (mapping != null && !mapping.isEmpty()) {
            MappingValidator mv = null;
            if (isGlobal) {
                mv = new GlobalMappedMappingValidator(mapping);
            } else {
                mv = new MappingValidator(mapping);
            }
            if (mv.isValid()) {
                cell.setInputMapping(mv.buildMapping());
            } else {
                // for (String s: mv.getValidationErrors()) {
                // errors[0] = "input mapping in cell " + CellReference.convertNumToColString(cell.getColumn()) + (cell.getRow() + 1) + " \"" + s + "\"";
                // }
                errors[0] = mv.getValidationErrors();
                errors[1] = new String[0];
                cell.setInputMapping(mapping);
                cell.setText("#MAPPING!");
                isValid = false;
            }
        }
        // mapping = cell.getOutputMapping();
        // if (mapping != null && !mapping.isEmpty()) {
        // MappingValidator mv = null;
        // if (isGlobal) {
        // mv = new GlobalMappedMappingValidator(mapping);
        // } else {
        // mv = new MappingValidator(mapping);
        // }
        // if (mv.isValid()) {
        // cell.setOutputMapping(mv.buildMapping());
        // } else {
        // // for (String s: mv.getValidationErrors()) {
        // // errors[1] = "output mapping in cell " + CellReference.convertNumToColString(cell.getColumn()) + (cell.getRow() + 1) + " \"" + s + "\"";
        // // }
        // errors[1] = mv.getValidationErrors();
        // }
        // }
        // boolean isValid = true;
        // for (String s: errors) {
        // if (s != null && isValid) {
        // isValid = false;
        // break;
        // }
        // }
        // if (isValid == false) {
        cell.setValidationMessages(errors);
        // }
        return isValid;
    }
    
    private static CellExtendedDTO parseCellDTO(CellDTO cell){
        CellExtendedDTO dto = new CellExtendedDTO();
        dto.setColumn(cell.getColumn());
        dto.setInputMapping(cell.getInputMapping());
        dto.setOutputMapping(cell.getOutputMapping());
        dto.setText(cell.getText());
        dto.setTags(cell.getTags());
        dto.setValidationMessages(cell.getValidationMessages());
        dto.setRow(cell.getRow());
        return dto;
    }
}
