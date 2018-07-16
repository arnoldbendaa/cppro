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
package com.softproideas.app.admin.dataeditor.mapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.cedar.cp.api.model.FinanceCubeRef;
import com.cedar.cp.dto.model.FinanceCubeCK;
import com.cedar.cp.dto.model.FinanceCubePK;
import com.softproideas.app.admin.dataeditor.model.DataEditorRow;
import com.softproideas.app.admin.financecubes.util.FinanceCubesUtil;
import com.softproideas.app.core.financecube.model.FinanceCubeModelCoreDTO;

/**
 * <p>Class is responsible for maps different object related to data editor to data transfer object (and vice-versa)</p>
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * <p>2014 All rights reserved to Softpro Ideas Group</p>
 */
public class DataEditorMapper {

    /**
     * Maps database data which contains information like (finance cubes, dimension elements, data types) to data editor row. 
     */
    public static List<DataEditorRow> mapDataEditorDataToDataEditorRows(List<Object[]> tmp, List<FinanceCubeModelCoreDTO> financeCubes) {
        List<DataEditorRow> rows = new ArrayList<DataEditorRow>();

        if (tmp == null) {
            return rows;
        }

        for (Object[] rowDB: tmp) {
            DataEditorRow row = new DataEditorRow();

            FinanceCubeRef fcRef = (FinanceCubeRef) rowDB[0];
            FinanceCubeCK fcCK = (FinanceCubeCK) fcRef.getPrimaryKey();
            FinanceCubePK fcPK = (FinanceCubePK) fcCK.getFinanceCubePK();
            row.setFinanceCubeId(fcPK.getFinanceCubeId());
            row.setFinanceCubeVisId(fcRef.getNarrative());

            FinanceCubeModelCoreDTO financeCube = FinanceCubesUtil.findFinanceCubeById(financeCubes, row.getFinanceCubeId());
            int modelId = financeCube.getModel().getModelId();
            row.setModelId(modelId);

            row.setCostCenter((String) rowDB[1]);
            row.setDim0((Integer) rowDB[2]);
            row.setExpenseCode((String) rowDB[3]);
            row.setDim1((Integer) rowDB[4]);
            row.setYear((Integer) rowDB[5]);
            row.setPeriod((Integer) rowDB[6]);
            row.setDim2((Integer) rowDB[9]);
            row.setDataType((String) rowDB[7]);

            if (rowDB[8] != null) {
                row.setValue(rowDB[8]);
                // DecimalFormat df = new DecimalFormat("0.00");
                // df.setMaximumFractionDigits(2);
                // row.setValue(df.format(rowDB[8]));
            } else {
                row.setValue(rowDB[8]);
            }

            // String id = "financeCubeId="+row.getFinanceCubeId()+",dim0="+row.getDim0()+",dim1="+row.getDim1()+",year="+row.getYear()+",period="+row.getPeriod()+",dataType="+row.getDataType();
            String id = "" + row.getFinanceCubeId() + "," + row.getDim0() + "," + row.getDim1() + "," + row.getYear() + "," + row.getPeriod() + "," + row.getDataType();
            row.setDataEditorRowId(id);

            rows.add(row);
        }
        return rows;
    }

    /**
     * Maps data from workbook object to date editor rows. We only maps first sheet which contains data to import.
     */
    public static List<DataEditorRow> mapWorkbookToDataEditorRows(Workbook workbook) {
        List<DataEditorRow> rowsDTO = new ArrayList<DataEditorRow>();

        Sheet sheet = workbook.getSheetAt(0); // Map only first sheet which contains data to import
        Row rowWithDataType = sheet.getRow(1);
        Cell cellWithDataType = rowWithDataType.getCell(12);
        String dataType = cellWithDataType.getStringCellValue(); // Data type is in specific place to read

        Iterator<Row> rowIterator = sheet.iterator();
        int rowIndex = -1;
        int columnIndex = -1;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            rowIndex = row.getRowNum();

            if (rowIndex < 2) {
                continue;
            }

            DataEditorRow rowDTO = new DataEditorRow();
            Boolean filledRowDTO = true;
            Iterator<Cell> cellIterator = row.cellIterator();
            
            fillRowDTO: 
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                columnIndex = cell.getColumnIndex();

                if (columnIndex >= 15) {
                    continue;
                }

                Double doubleValue = null;
                String stringValue = null;

                switch (columnIndex) {
                    case 0: // Finance Cube
                        stringValue = cell.getStringCellValue();
                        if (stringValue.isEmpty()) {
                            filledRowDTO = false;
                            break fillRowDTO;
                        } else {
                            rowDTO.setFinanceCubeVisId(stringValue);
                        }
                        break;
                    case 1: // Cost Center
                        stringValue = cell.getStringCellValue();
                        if (stringValue.isEmpty()) {
                            filledRowDTO = false;
                            break fillRowDTO;
                        } else {
                            rowDTO.setCostCenter(stringValue);
                        }
                        break;
                    case 2: // Expense Code
                        stringValue = cell.getStringCellValue();
                        if (stringValue.isEmpty()) {
                            filledRowDTO = false;
                            break fillRowDTO;
                        } else {
                            rowDTO.setExpenseCode(stringValue);
                        }
                        break;
                    case 7: // Year
                        doubleValue = cell.getNumericCellValue();
                        if (doubleValue == null || doubleValue.isNaN()) {
                            filledRowDTO = false;
                            break fillRowDTO;
                        } else {
                            rowDTO.setYear(((Double) doubleValue).intValue());
                        }
                        break;
                    case 8: // Period
                        doubleValue = cell.getNumericCellValue();
                        rowDTO.setPeriod(((Double) doubleValue).intValue());
                        break;
                    case 12: // Value
                        try {
                            stringValue = cell.getStringCellValue();
                            rowDTO.setValue(stringValue);
                        } catch (IllegalStateException e) {
                            doubleValue = cell.getNumericCellValue();
                            doubleValue = (int) (doubleValue * 100 + 0.5) / 100.0;

                            rowDTO.setValue(doubleValue);
                            // DecimalFormat df = new DecimalFormat("0.00");
                            // df.setMaximumFractionDigits(2);
                            // rowDTO.setValue(df.format(doubleValue));
                        }
                        break;
                }
            }
            if (filledRowDTO) {
                rowDTO.setDataType(dataType);
                rowsDTO.add(rowDTO);
            }
        }
        return rowsDTO;
    }
}
