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
package com.softproideas.app.reviewbudget.financeform.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cedar.cp.api.model.OnDemandFinanceFormData;
import com.cedar.cp.api.model.ReviewBudgetDetails;
import com.cedar.cp.util.flatform.model.workbook.FinanceFormCellDTO;
import com.cedar.cp.util.flatform.model.workbook.ReviewBudgetCellDTO;
import com.cedar.cp.util.xmlform.Column;
import com.cedar.cp.util.xmlform.inputs.FormInputModel;
import com.softproideas.app.reviewbudget.financeform.model.FinanceFormDTO;
import com.softproideas.app.reviewbudget.financeform.model.FinanceFormTreeDTO;

/**
 * 
 * @author Łukasz Puła
 * @email lukasz.pula@softproideas.com
 * 2014 All rights reserved to Softpro Ideas Group
 */
public class FinanceFormMapper {
    
    public static FinanceFormDTO mapToFinanceFormDTO(OnDemandFinanceFormData rootData, OnDemandFinanceFormData childrenData, ReviewBudgetDetails reviewBudgetDetails) {
        FinanceFormDTO financeFormDTO = new FinanceFormDTO();
        List<FinanceFormTreeDTO> rows = new ArrayList<FinanceFormTreeDTO>();
        financeFormDTO.setRows(rows); 
        
        FormInputModel financeFormListRoot = rootData.getFormInputModel();
        for (int index = 0; index < financeFormListRoot.getRowCount(); index++) {
            mapToFinanceFormTreeDTO(financeFormListRoot, index, rows);
        }
        
        FormInputModel financeFormListChildren = childrenData.getFormInputModel();
        for (int index = 0; index < financeFormListChildren.getRowCount(); index++) {
            mapToFinanceFormTreeDTO(financeFormListChildren, index, rows);
        }
                
        financeFormDTO.setTitles(mapToFinanceFormTitles(reviewBudgetDetails));
        generateFormulas(rows);
        return financeFormDTO; 
    }

    @SuppressWarnings("unchecked")
    private static List<String> mapToFinanceFormTitles(ReviewBudgetDetails reviewBudgetDetails) {
        Iterator<Object> iter = reviewBudgetDetails.getColumns().iterator();
        List<String> headings = new ArrayList<String>();
        
        while(iter.hasNext()) {
            Object o = iter.next();
            if(o instanceof Column) {
               Column group = (Column)o;
               headings.add(group.getHeading());
            }
        }
        return headings;
    }
    
    /**
     * Maps data row from formInputList to finance form tree data object
     * @param formsList
     * @param rowNumber
     * @param rows
     */
    private static void mapToFinanceFormTreeDTO(FormInputModel formsList, int rowNumber, List<FinanceFormTreeDTO> rows) {
        FinanceFormTreeDTO element = new FinanceFormTreeDTO();
        
        element.setName((String) formsList.getValueAt(rowNumber, 0) + " " +(String) formsList.getValueAt(rowNumber, 1));
        element.setLeaf(((String)formsList.getValueAt(rowNumber, 4)).equals("Y"));
        
        int depth = ((BigDecimal) formsList.getValueAt(rowNumber, 3)).intValue();
        element.setDepth(depth);
        
        List<ReviewBudgetCellDTO> cells = new ArrayList<ReviewBudgetCellDTO>();
        
        FinanceFormCellDTO cell = new FinanceFormCellDTO();
        if (element.isLeaf() && formsList.getValueAt(rowNumber, 12) != null) {
            double av = ((BigDecimal) formsList.getValueAt(rowNumber, 12)).doubleValue();
            cell.setValue(av);
        }
        cells.add(cell);
        
        cell = new FinanceFormCellDTO();
        if (element.isLeaf() && formsList.getValueAt(rowNumber, 16) != null) {
            double av = ((BigDecimal) formsList.getValueAt(rowNumber, 16)).doubleValue();
            cell.setValue(av);
        }
        cells.add(cell);
        
        cell = new FinanceFormCellDTO();
        if (element.isLeaf() && formsList.getValueAt(rowNumber, 20) != null) {
            double av = ((BigDecimal) formsList.getValueAt(rowNumber, 20)).doubleValue();
            cell.setValue(av);
        }
        cells.add(cell);
        element.setCells(cells);
        
        rows.add(element);
    }
    
    /**
     * Generate formulas for 'catalogs' in tree structure
     * @param rows
     */
    private static void generateFormulas(List<FinanceFormTreeDTO> rows) {
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).isLeaf())
                continue;
            
            List<Integer> findedRows = collectListRowsToFormula(rows, i, rows.get(i).getDepth());
            if (findedRows.size() > 0) {
                List<ReviewBudgetCellDTO> cells = rows.get(i).getCells();
                cells.get(0).setFormula(generateSumFormula(findedRows, "B"));
                cells.get(1).setFormula(generateSumFormula(findedRows, "C"));
                cells.get(2).setFormula(generateSumFormula(findedRows, "D"));
            }
        }
    }
    
    /**
     * Collects indexes of children for element with rowNumber
     * @param rows
     * @param rowNumber
     * @param depth
     * @return
     */
    private static List<Integer> collectListRowsToFormula(List<FinanceFormTreeDTO> rows, int rowNumber, int depth) {
        List<Integer> findedRows = new ArrayList<Integer>();
        int findedDepth = depth+1;
        int actualDepth;
        for (int index = rowNumber+1; index < rows.size(); index++) {
            actualDepth = rows.get(index).getDepth();
            if (actualDepth == depth) {
                break;
            } else if (actualDepth == findedDepth) {
                findedRows.add(index+1);
            }
        }
        return findedRows;
    }
    
    /**
     * Generates summing formula for cell stored in one column
     * @param listOfRows
     * @param columnName
     * @return
     */
    private static String generateSumFormula(List<Integer> listOfRows, String columnName) {
        String formula = "=SUM(";
        for (Integer integer: listOfRows)
            formula += columnName + integer.toString() + ",";
        formula += "0)";
        return formula;
    }
}
