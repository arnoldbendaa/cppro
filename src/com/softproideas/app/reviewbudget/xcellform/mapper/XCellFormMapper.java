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
package com.softproideas.app.reviewbudget.xcellform.mapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.cedar.cp.api.base.EntityList;
import com.cedar.cp.api.model.ReviewBudgetDetails;
import com.cedar.cp.util.flatform.model.workbook.notes.CellNotesDTO;
import com.softproideas.app.core.workbook.mapper.review.WorkbookMapper;
import com.softproideas.app.reviewbudget.dimension.model.ElementDTO;
import com.softproideas.app.reviewbudget.xcellform.model.XCellFormDTO;

public class XCellFormMapper {

    /**
     * Map <code>ReviewBudgetDetails</code> to <code>ReviewBudgetDTO</code>.
     */
    public static XCellFormDTO mapToXCellFormDTO(ReviewBudgetDetails reviewBudgetDetails, HashMap<Integer, List<CellNotesDTO>> notes) {
        XCellFormDTO xCellFormDTO = new XCellFormDTO();
        List<ElementDTO> selectedDimensions = mapToSelectedDimensions(reviewBudgetDetails.getSelectionLabels());

        xCellFormDTO.setJsonForm(reviewBudgetDetails.getJsonForm());
        xCellFormDTO.setWorkbook(WorkbookMapper.mapToWorkbookDTO(reviewBudgetDetails.getWorkbook(), notes, selectedDimensions, reviewBudgetDetails.getDataType()));
        
        return xCellFormDTO;
    }

    /**
     * Map <code>EntityList</code> selectionLabels which contains <code>StructureElementELO</code> 
     * to a list of <code>ElementDTO</code>.
     */
    private static List<ElementDTO> mapToSelectedDimensions(EntityList[] selectionLabels) {
        List<ElementDTO> result = new ArrayList<ElementDTO>();

        for (int i = 0; i < selectionLabels.length; ++i) {
            ElementDTO selectedDimension = new ElementDTO();
            if (selectionLabels[i] != null) {
                EntityList el = selectionLabels[i];
                selectedDimension.setId((Integer) el.getValueAt(0, "StructureElementId"));
                selectedDimension.setStructureId((Integer) el.getValueAt(0, "StructureId"));
                selectedDimension.setName((String) el.getValueAt(0, "VisId"));
                selectedDimension.setDescription((String) el.getValueAt(0, "Description"));
                selectedDimension.setLeaf((Boolean) el.getValueAt(0, "Leaf"));
            } else {
                selectedDimension.setName("?");
                selectedDimension.setDescription("");
                selectedDimension.setLeaf(false);
            }
            result.add(selectedDimension);
        }
        return result;
    }

}
