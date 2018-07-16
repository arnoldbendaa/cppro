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
package com.softproideas.app.core.workbook.mapper.review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.util.flatform.model.Cell;
import com.cedar.cp.util.flatform.model.Workbook;
import com.cedar.cp.util.flatform.model.Worksheet;
import com.cedar.cp.util.flatform.model.workbook.CellDTO;
import com.cedar.cp.util.flatform.model.workbook.ReviewBudgetCellDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookDTO;
import com.cedar.cp.util.flatform.model.workbook.WorkbookProperties;
import com.cedar.cp.util.flatform.model.workbook.WorksheetDTO;
import com.cedar.cp.util.flatform.model.workbook.XCellFormCellDTO;
import com.cedar.cp.util.flatform.model.workbook.notes.CellNotesDTO;
import com.cedar.cp.util.flatform.model.workbook.util.CellUtil;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray;
import com.cedar.cp.util.sparse.LinkedListSparse2DArray.CellLink;
import com.softproideas.app.reviewbudget.dimension.model.ElementDTO;
import com.softproideas.commons.util.DimensionUtil;

/**
 * <p>Controller for mapping workbook data to DTO.</p>
 * 
 * @author Łukasz Puła, Szymon Walczak
 * @email lukasz.pula@softproideas.com, szymon.walczak@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class WorkbookMapper {

    /**
     * Map <code>Workbook</code> to <code>WorkbookDTO</code>. <code>WorkbookDTO</code> contains a list of <code>WorksheetDTO</code>. <code>WorksheetDTO</code> contains a list of <code>CellDTO</code>. <code>CellDTO</code> contains a list of matching <code>NoteDTO</code>.
     */
    public static WorkbookDTO mapToWorkbookDTO(Workbook workbook, HashMap<Integer, List<CellNotesDTO>> lastNotes, List<ElementDTO> selectedDimensions, String contextDataType) {
        WorkbookDTO workbookDTO = new WorkbookDTO();

        // 1. set properties for workbookDTO
        if (workbook.getProperties() != null) {
            for (String key: workbook.getProperties().keySet()) {
                String property = workbook.getProperty(key);
                workbookDTO.getProperties().put(key, property);
            }
        }
        
        // 2. set worksheetsDTO for workbookDTO
        List<WorksheetDTO> worksheetsDTO = new ArrayList<WorksheetDTO>();
        for (Worksheet worksheet: workbook.getWorksheets()) {
            // 2.1 get notes related to finance cube
            List<CellNotesDTO> notesForFinanceCube;
            if (worksheet.getProperties() != null) {
                String cubeId = worksheet.getProperties().get(WorkbookProperties.FINANCE_CUBE_ID.toString());
                if (cubeId == null || cubeId.equals("")) {
                    continue;
                }
                Integer financeCubeId = Integer.parseInt(cubeId.substring(cubeId.lastIndexOf('|') + 1));
                notesForFinanceCube = lastNotes.get(financeCubeId);
            } else {
                notesForFinanceCube = new ArrayList<CellNotesDTO>();
            }
            // 2.2 map to worksheetDTO
            WorksheetDTO worksheetDTO = mapToWorksheetDTO(worksheet, notesForFinanceCube, selectedDimensions, contextDataType);
            worksheetsDTO.add(worksheetDTO);
        }
        workbookDTO.setWorksheets(worksheetsDTO);
        
        //set Validation
        workbookDTO.setValid(workbook.isValid());

        return workbookDTO;
    }

    /**
     * Map <code>Worksheet</code> to <code>WorksheetDTO</code>.
     */
    public static WorksheetDTO mapToWorksheetDTO(Worksheet worksheet, List<CellNotesDTO> lastNotes, List<ElementDTO> selectedDimensions, String contextDataType) {
        WorksheetDTO worksheetDTO = new WorksheetDTO();

        // 1. set name for worksheetDTO
        worksheetDTO.setName(worksheet.getName());

        // 2. set properties for worksheetDTO
        if (worksheet.getProperties() != null) {
            HashMap<String, String> properties = new HashMap<String, String>();
            for (String key: worksheet.getProperties().keySet()) {
                properties.put(key, worksheet.getProperties().get(key));
            }
            worksheetDTO.setProperties(properties);
        }

        // 3. set cells for worksheetDTO
        Iterator<LinkedListSparse2DArray.CellLink<Cell>> cellIterator = worksheet.iterator();
        List<CellDTO> cells = new ArrayList<CellDTO>();
        
        while (cellIterator.hasNext()) {
            CellLink<Cell> cellLink = cellIterator.next();
            Cell cell = cellLink.getData();
            if (cell != null) {
                CellDTO mapToCellDTO = mapToCellDTO(cell, filterCellNotesDTO(selectedDimensions, contextDataType, cell, lastNotes));
                cells.add(mapToCellDTO);
            }
        }
        worksheetDTO.setCells(cells);
        
        //set validation
        worksheetDTO.setValid(worksheet.isValid());
        
        return worksheetDTO;
    }

    /**
     * Map <code>Cell</code> to <code>CellDTO</code>.
     */
    public static CellDTO mapToCellDTO(Cell cell, CellNotesDTO lastNote) {
        XCellFormCellDTO cellDTO = new XCellFormCellDTO();
        cellDTO.setRow(cell.getRow());
        cellDTO.setColumn(cell.getColumn());
        cellDTO.setInputMapping(cell.getInputMapping());
        cellDTO.setOutputMapping(cell.getOutputMapping());
        cellDTO.setTags(cell.getTags());
        
        cellDTO.setNotes(lastNote);
        cellDTO.setInvertedValue(cell.isInvertedValue());
        String value = cell.getText();
        
        CellUtil.setFormattedValue(value, cellDTO);
        
        //set validation
        cellDTO.setValidationMessages(cell.getValidationMessages());
        return cellDTO;
    }

    /**
     * Checks if cell and note matches
     * @param dimensionsFromContext dim0, dim1, dim2
     * @return CellNotesDTO if matches or null
     */
    private static CellNotesDTO filterCellNotesDTO(List<ElementDTO> dimensionsFromContext, String contextDataType, Cell cell, List<CellNotesDTO> lastNotes) {
        if (lastNotes == null) {
            return null;
        }
        for (CellNotesDTO lastNote : lastNotes) {

            String cellDimesnion = "";
            String noteDimension = "";
            boolean dim0AndDim1Equal = true;

            // check matching of each dimension
            for (int dim = 0; dim < 3; dim++) {

                // get dim from input or output or context and compare
                if (noteDimension != null && cellDimesnion != null && noteDimension.equals(cellDimesnion)) {
                    cellDimesnion = DimensionUtil.getKeyFromMapping("dim" + dim, cell.getInputMapping(), cell.getOutputMapping());
                    if (cellDimesnion.isEmpty()) {
                        cellDimesnion = dimensionsFromContext.get(dim).getName();
                    }
                    noteDimension = (String) lastNote.getDimensions().get(dim);
                } else {
                    dim0AndDim1Equal = false;
                    break;
                }
            }
            // dim2 can be with "?", check it
            if (dim0AndDim1Equal) {

                // get calendar offsets from mapping
                String year = DimensionUtil.getKeyFromMapping("year", cell.getInputMapping(), cell.getOutputMapping());
                String period = DimensionUtil.getKeyFromMapping("period", cell.getInputMapping(), cell.getOutputMapping());
                int yearOffset = 0, periodOffset = 0;
                if (!year.isEmpty()) {
                    try {
                        yearOffset = Integer.parseInt(year);
                    } catch (NumberFormatException e) {
                    }
                }
                if (!period.isEmpty()) {
                    try {
                        periodOffset = Integer.parseInt(period);
                    } catch (NumberFormatException e) {
                    }
                }

                if (DimensionUtil.dim2Evaluator(cellDimesnion, dimensionsFromContext.get(2).getName(), yearOffset, periodOffset).equals(noteDimension)) {

                    // dimensions equal, check dataType
                    String cellDataType = DimensionUtil.getKeyFromMapping("dt", cell.getInputMapping(), cell.getOutputMapping());
                    if (cellDataType.isEmpty()) {
                        cellDataType = contextDataType;
                    }
                    if ((lastNote.getDataType()).equals(cellDataType)) {

                        // data type equals, check company (global mapped model)
                        String company = DimensionUtil.getKeyFromMapping("cmpy", cell.getInputMapping(), cell.getOutputMapping());
                        if (company.isEmpty()) {
                            // no company in mapping
                            company = null;
                        } else {
                            String noteCompany = lastNote.getCompany();
                            if (!company.equals(noteCompany)) {
                                break;
                            }
                        }

                        // MATCHES!
                        return lastNote;
                    }
                }
            }
        }
        return null;
    }
    
    public static void filterCellNotesDTO(List<ElementDTO> dimensionsFromContext, String contextDataType, ReviewBudgetCellDTO cell, List<CellNotesDTO> lastNotes) {
        if (lastNotes == null) {
            return;
        }
        for (CellNotesDTO lastNote : lastNotes) {

            String cellDimesnion = "";
            String noteDimension = "";
            boolean dim0AndDim1Equal = true;

            // check matching of each dimension
            for (int dim = 0; dim < 3; dim++) {

                // get dim from input or output or context and compare
                if (noteDimension != null && cellDimesnion != null && noteDimension.equals(cellDimesnion)) {
                    cellDimesnion = DimensionUtil.getKeyFromMapping("dim" + dim, cell.getInputMapping(), cell.getOutputMapping());
                    if (cellDimesnion.isEmpty()) {
                        cellDimesnion = dimensionsFromContext.get(dim).getName();
                    }
                    noteDimension = (String) lastNote.getDimensions().get(dim);
                } else {
                    dim0AndDim1Equal = false;
                    break;
                }
            }
            // dim2 can be with "?", check it
            if (dim0AndDim1Equal) {

                // get calendar offsets from mapping
                String year = DimensionUtil.getKeyFromMapping("year", cell.getInputMapping(), cell.getOutputMapping());
                String period = DimensionUtil.getKeyFromMapping("period", cell.getInputMapping(), cell.getOutputMapping());
                int yearOffset = 0, periodOffset = 0;
                if (!year.isEmpty()) {
                    try {
                        yearOffset = Integer.parseInt(year);
                    } catch (NumberFormatException e) {
                    }
                }
                if (!period.isEmpty()) {
                    try {
                        periodOffset = Integer.parseInt(period);
                    } catch (NumberFormatException e) {
                    }
                }

                if (DimensionUtil.dim2Evaluator(cellDimesnion, dimensionsFromContext.get(2).getName(), yearOffset, periodOffset).equals(noteDimension)) {

                    // dimensions equal, check dataType
                    String cellDataType = DimensionUtil.getKeyFromMapping("dt", cell.getInputMapping(), cell.getOutputMapping());
                    if (cellDataType.isEmpty()) {
                        cellDataType = contextDataType;
                    }
                    if ((lastNote.getDataType()).equals(cellDataType)) {

                        // data type equals, check company (global mapped model)
                        String company = DimensionUtil.getKeyFromMapping("cmpy", cell.getInputMapping(), cell.getOutputMapping());
                        if (company.isEmpty()) {
                            // no company in mapping
                            company = null;
                        } else {
                            String noteCompany = lastNote.getCompany();
                            if (!company.equals(noteCompany)) {
                                break;
                            }
                        }

                        // MATCHES!
                        cell.setNotes(lastNote);
                    }
                }
            }
        }
    }
}